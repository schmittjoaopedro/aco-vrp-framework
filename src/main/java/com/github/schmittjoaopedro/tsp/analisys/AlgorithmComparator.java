package com.github.schmittjoaopedro.tsp.analisys;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_3OPT_MADTSP;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_MADTSP;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_US_MADTSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.tools.TrialExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmComparator {

    private static final String resultsPath = "C:\\Temp";
    private static int numIterations = 1000;

    public static void main(String[] args) throws Exception {
        ExperimentResultWriter resultWriter = new ExperimentResultWriter();
        resultWriter.initialize(numIterations);
        for (String test : new String[]{"KroA100.tsp", "KroA150.tsp", "KroA200.tsp"}) {
            for (int freq : new int[]{10, 100}) {
                for (double mag : new double[]{0.1, 0.5, 0.75}) {
//                    for (double rho : new double[]{0.02, 0.2, 0.4, 0.6, 0.8}) {
//                        for (double beta : new double[]{2.0, 3.0, 4.0, 5.0}) {
                    executeMMASTest(resultWriter, test, mag, freq, 0.02, 1.0, 5.0);
                    executeMMASUSTest(resultWriter, test, mag, freq, 0.8, 1.0, 5.0);
                    executeMMAS3OPTTest(resultWriter, test, mag, freq, 0.02, 1.0, 5.0);
//                        }
//                    }
                }
            }
        }
        resultWriter.compileResults(resultsPath, "ALL_RESULTS_COMPILED.csv");
    }

    private static void executeMMASTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_MADTSP mmas_adtsp = new MMAS_MADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
            mmas_adtsp.setDbgpSeed(i);
            mmas_adtsp.setMmasSeed(i);
            mmas_adtsp.setStatisticInterval(1);
            mmas_adtsp.setShowLog(false);
            algs.add(mmas_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((MMAS_MADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMASUSTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_US_MADTSP mmas_adtsp = new MMAS_US_MADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
            mmas_adtsp.setDbgpSeed(i);
            mmas_adtsp.setMmasSeed(i);
            mmas_adtsp.setStatisticInterval(1);
            mmas_adtsp.setShowLog(false);
            algs.add(mmas_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((MMAS_US_MADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMAS3OPTTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_3OPT_MADTSP mmas_adtsp = new MMAS_3OPT_MADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
            mmas_adtsp.setDbgpSeed(i);
            mmas_adtsp.setMmasSeed(i);
            mmas_adtsp.setStatisticInterval(1);
            mmas_adtsp.setShowLog(false);
            algs.add(mmas_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((MMAS_3OPT_MADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }
}
