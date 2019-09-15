package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.StatisticCalculator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import org.apache.commons.cli.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Runner {

    public static final String INPUT_DIR = "inputDir";
    public static final String OUTPUT_DIR = "outputDir";
    public static final String MOVING_VEHICLE = "movingVehicle";
    public static final String LOCAL_SEARCH = "localSearch";
    public static final String PARALLEL = "parallel";
    public static final String HELP = "help";
    public static final String NUM_TRIALS = "numTrials";
    public static final String MAX_ITERATIONS = "maxIterations";

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
            File[] instances = getInstances(inputDir);
            for (File instance : instances) {
                String instanceName = instance.getName().substring(0, instance.getName().indexOf("."));
                StatisticCalculator statisticCalculator = new StatisticCalculator(instanceName, maxIterations);
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numTrials);
                for (int i = 0; i < numTrials; i++) {
                    System.out.println("Starting to process " + instanceName + ", trial " + i);
                    threadPoolExecutor.submit(createSampleExecution(instance, maxIterations, commandLine, statisticCalculator));
                }
                threadPoolExecutor.shutdown();
                while (!threadPoolExecutor.awaitTermination(2, TimeUnit.HOURS)) ;
                statisticCalculator.calculateInstanceStatistics();
                statisticCalculator.writeTestResultToCsv(outputDir, true, true, true);
                System.out.println("Num threads = " + Thread.activeCount());
                System.out.println("Finishing to process all instances of " + instanceName);
            }
        }
    }

    private static Runnable createSampleExecution(File instanceFile,
                                                  Integer maxIterations,
                                                  CommandLine commandLine,
                                                  StatisticCalculator statisticCalculator) throws Exception {
        Instance instance = Reader.getInstance(instanceFile);
        long seed = (long) (1000.0 * Math.random());
        Solver solver = new Solver(instance, new Random(seed), maxIterations, true, true);
        solver.setPrintConsole(false);
        solver.enableStatisticsCollector();
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
        options.addOption(MOVING_VEHICLE, false, "Enable moving vehicle");
        options.addOption(LOCAL_SEARCH, false, "Enable local search");
        options.addOption(PARALLEL, false, "Enable algorithm objectives (NV and TC) to execute in parallel");
        options.addOption(HELP, false, "Print help");
        return options;
    }
}
