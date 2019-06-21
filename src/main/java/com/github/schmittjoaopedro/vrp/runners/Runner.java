package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.statistic.ExperimentResultWriter;
import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.statistic.TrialExecutor;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Runner {

    private static int maxIterations = 25000;

    public static void main(String[] args) throws Exception {
        String inputFolder = args[0];
        String outputFolder = args[1];
        for (File file : new File(inputFolder).listFiles()) {
            ExperimentResultWriter resultWriter = new ExperimentResultWriter();
            resultWriter.initializeALNS(maxIterations);
            executeInstance(resultWriter, file, outputFolder);
            resultWriter.compileResults(outputFolder, "RESULTS-" + file.getName().replaceAll(".txt", StringUtils.EMPTY) + ".csv");
        }
    }

    private static void executeInstance(ExperimentResultWriter resultWriter, File file, String outputFolder) throws Exception {
        int trials = 10;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            ProblemInstance instance = DataReader.getPdptwInstance(file);
            ALNS alns = new ALNS(instance, maxIterations, new Random(i));
            alns.setGenerateFile(false);
            alns.setShowLog(false);
            algs.add(alns);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        System.out.println("Starting " + file.getName());
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            System.out.println("Computing results of trial " + i + " instance " + file.getName());
            results.add(((ALNS) algs.get(i)).getIterationStatistics());
        }
        GlobalStatistics globalStatistics = trialExecutor.getGlobalStatistics(results);
        List<IterationStatistic> unifiedStatistics = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResultsALNS(outputFolder, algs.get(0).getClass().getSimpleName(), file.getName().replaceAll(".txt", StringUtils.EMPTY), globalStatistics, unifiedStatistics);
    }

}
