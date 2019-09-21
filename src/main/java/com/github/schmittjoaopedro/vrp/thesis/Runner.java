package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.StatisticCalculator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Runner {

    public static final Logger LOGGER;

    static {
        System.setProperty("logFilename", "run_" + System.currentTimeMillis() + ".log");
        LOGGER = LogManager.getLogger(Runner.class);
    }

    public static final String INPUT_DIR = "inputDir";
    public static final String OUTPUT_DIR = "outputDir";
    public static final String MOVING_VEHICLE = "movingVehicle";
    public static final String LOCAL_SEARCH = "localSearch";
    public static final String PARALLEL = "parallel";
    public static final String HELP = "help";
    public static final String NUM_TRIALS = "numTrials";
    public static final String MAX_ITERATIONS = "maxIterations";
    public static final String NUM_CPU = "numCpu";
    public static final double NUM_THREADS_PER_RUN = 3.0;

    public static void main(String[] args) throws Exception {
        CommandLine commandLine = parseCommandLine(args);
        if (commandLine.hasOption(INPUT_DIR) &&
                commandLine.hasOption(OUTPUT_DIR) &&
                commandLine.hasOption(NUM_TRIALS) &&
                commandLine.hasOption(MAX_ITERATIONS)) {
            String inputDir = commandLine.getOptionValue(INPUT_DIR);
            String outputDir = commandLine.getOptionValue(OUTPUT_DIR);
            Integer numTrials = Integer.valueOf(commandLine.getOptionValue(NUM_TRIALS));
            Integer maxIterations = Integer.valueOf(commandLine.getOptionValue(MAX_ITERATIONS));
            Integer numCpus = Integer.valueOf(commandLine.getOptionValue(NUM_CPU));
            File[] instances = getInstances(inputDir);
            for (double p = 0; p < instances.length; p++) {
                File instance = instances[(int) p];
                String instanceName = instance.getName().substring(0, instance.getName().lastIndexOf("."));
                StatisticCalculator statisticCalculator = new StatisticCalculator(instanceName, maxIterations);
                double numSegments = calculateNumTrialSegments(numCpus, numTrials, commandLine);
                double trialSegmentSize = numTrials / numSegments;
                LOGGER.info("Starting to process {}. Active threads {}. Segments {}. Trials per segment {}", instanceName, Thread.activeCount(), numSegments, trialSegmentSize);
                for (int i = 0; i < numSegments; i++) {
                    executeThreadPool(commandLine, (int) trialSegmentSize, maxIterations, instance, statisticCalculator, i);
                }
                statisticCalculator.calculateInstanceStatistics();
                statisticCalculator.writeTestResultToCsv(outputDir, true, true, true);
                LOGGER.info("Finishing to process {}. Num active threads {}.", instanceName, Thread.activeCount());
                LOGGER.info("Processed {}%", MathUtils.round(((p + 1.0) / instances.length * 100.0), 2));
            }
        }
    }

    private static void executeThreadPool(CommandLine commandLine, Integer numTrials, Integer maxIterations, File instance, StatisticCalculator statisticCalculator, int segment) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numTrials);
        for (int i = 0; i < numTrials; i++) {
            threadPoolExecutor.submit(createSampleExecution(instance, maxIterations, commandLine, statisticCalculator, i == 0));
        }
        threadPoolExecutor.shutdown();
        LOGGER.info("Active threads {} threads. Segment {}", Thread.activeCount(), segment);
        while (!threadPoolExecutor.awaitTermination(2, TimeUnit.HOURS)) ;
    }

    private static double calculateNumTrialSegments(double numCpu, double numTrials, CommandLine commandLine) {
        if (commandLine.hasOption(PARALLEL)) {
            return Math.ceil((numTrials * NUM_THREADS_PER_RUN) / numCpu);
        } else {
            return Math.ceil(numTrials / numCpu);
        }
    }

    private static Runnable createSampleExecution(File instanceFile,
                                                  Integer maxIterations,
                                                  CommandLine commandLine,
                                                  StatisticCalculator statisticCalculator,
                                                  boolean logThreadInfo) throws Exception {
        Instance instance = Reader.getInstance(instanceFile);
        long seed = (long) (1000.0 * Math.random());
        Solver solver = new Solver(instance, new Random(seed), maxIterations, true, true);
        solver.setPrintConsole(false);
        solver.enableStatisticsCollector();
        if (logThreadInfo) {
            solver.enableThreadInfo();
        }
        if (commandLine.hasOption(LOCAL_SEARCH)) {
            solver.enableLocalSearch();
        }
        if (commandLine.hasOption(MOVING_VEHICLE)) {
            solver.enableVehicleControlCenter();
        }
        if (commandLine.hasOption(PARALLEL)) {
            solver.enableParallelExecution();
        }
        return () -> {
            solver.init();
            solver.run();
            statisticCalculator.addStatisticsResult(solver.getStatistic());
        };
    }

    private static File[] getInstances(String inputDir) {
        File folder = Paths.get(inputDir).toFile();
        return folder.listFiles();
    }

    private static CommandLine parseCommandLine(String[] args) throws Exception {
        Options options = createOptions();
        CommandLineParser commandLineParser = new DefaultParser();
        CommandLine commandLine = commandLineParser.parse(options, args);
        if (commandLine.getOptions().length == 0 || commandLine.hasOption("help")) {
            new HelpFormatter().printHelp("help", options);
        }
        return commandLine;
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption(INPUT_DIR, true, "Instances input directory");
        options.addOption(OUTPUT_DIR, true, "Results output directory");
        options.addOption(NUM_TRIALS, true, "Number of trials by test instance");
        options.addOption(MAX_ITERATIONS, true, "Number of maximum iterations to execute");
        options.addOption(NUM_CPU, true, "Num CPU's");
        options.addOption(MOVING_VEHICLE, false, "Enable moving vehicle");
        options.addOption(LOCAL_SEARCH, false, "Enable local search");
        options.addOption(PARALLEL, false, "Enable algorithm objectives (NV and TC) to execute in parallel");
        options.addOption(HELP, false, "Print help");
        return options;
    }
}
