package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.statistic.ExperimentResultWriter;
import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.statistic.TrialExecutor;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MPDPTW_ALNS {

    private static final String rootDirectory = "mpdptw";

    private static int maxIterations = 100000;

    private static final String resultsPath = "C:\\Temp\\mpdptw";

    public static void main(String[] args) throws Exception {
        /*ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, new Random(1));
        alns.setGenerateFile(true);
        alns.setShowLog(true);
        alns.run();*/
        MPDPTW_ALNS mpdptw_alns = new MPDPTW_ALNS();
        mpdptw_alns.mpdptw_test();
    }

    private void mpdptw_test() throws Exception {
        //for (String noVert : new String[]{"400", "100", "50", "25"}) {
        for (String noVert : new String[]{/*"25", "50", "100",*/ "400"}) {
            for (String typ : new String[]{/*"l",*/ "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    ExperimentResultWriter resultWriter = new ExperimentResultWriter();
                    resultWriter.initializeALNS(maxIterations);
                    String problem = typ + "_" + reqSize + "_" + noVert;
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        executeInstance(resultWriter, problem + "_" + id);
                    }
                    resultWriter.compileResults(resultsPath, problem + ".csv");
                }
            }
        }
    }

    private void executeInstance(ExperimentResultWriter resultWriter, String problem) throws Exception {
        int trials = 10;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            InputStream in = getClass().getClassLoader().getResourceAsStream(rootDirectory + "/" + problem + ".txt");
            ProblemInstance instance = DataReader.getMpdptwInstance(problem, in);
            ALNS alns = new ALNS(instance, maxIterations, new Random(i));
            alns.setGenerateFile(false);
            alns.setShowLog(false);
            algs.add(alns);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        System.out.println("Starting " + problem);
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((ALNS) algs.get(i)).getIterationStatistics());
        }
        GlobalStatistics globalStatistics = trialExecutor.getGlobalStatistics(results);
        List<IterationStatistic> unifiedStatistics = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResultsALNS(resultsPath, algs.get(0).getClass().getSimpleName(), problem, globalStatistics, unifiedStatistics);
    }

}
