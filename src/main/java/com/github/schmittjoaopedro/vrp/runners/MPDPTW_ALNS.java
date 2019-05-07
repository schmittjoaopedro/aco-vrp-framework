package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.tsp.analisys.ExperimentResultWriter;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.tools.TrialExecutor;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MPDPTW_ALNS {

    private static final String rootDirectory;

    private static int maxIterations = 10000;

    private static final String resultsPath = "C:\\Temp\\mpdptw";

    static {
        rootDirectory = Paths.get(MPDPTW_ALNS.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws Exception {
        /*ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, new Random(1));
        alns.setGenerateFile(true);
        alns.setShowLog(true);
        alns.run();*/
        mpdptw_test();
    }

    private static void mpdptw_test() throws Exception {
        //for (String noVert : new String[]{"400", "100", "50", "25"}) {
        for (String noVert : new String[]{"25", "50", "100", "400"}) {
            ExperimentResultWriter resultWriter = new ExperimentResultWriter();
            resultWriter.initializeALNS(maxIterations);
            for (String typ : new String[]{"l", "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        String problem = typ + "_" + reqSize + "_" + noVert + "_" + id;
                        executeInstance(resultWriter, problem);
                    }
                }
            }
            resultWriter.compileResults(resultsPath, "instance_" + noVert + ".csv");
        }
    }

    private static void executeInstance(ExperimentResultWriter resultWriter, String problem) throws Exception {
        int trials = 2;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem + ".txt").toFile());
            ALNS alns = new ALNS(instance, maxIterations, new Random(1));
            alns.setGenerateFile(false);
            alns.setShowLog(true);
            algs.add(alns);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((ALNS) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResultsALNS(resultsPath, algs.get(0).getClass().getSimpleName(), problem, result);
    }

}
