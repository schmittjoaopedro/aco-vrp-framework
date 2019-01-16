package com.github.schmittjoaopedro.tsp.analisys;

import com.github.schmittjoaopedro.tsp.algorithms.*;
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
    private static int numTrials = 30;

    public static void main(String[] args) throws Exception {

        String[] tspInstances = {"KroA100.tsp", "KroA150.tsp", "KroA200.tsp"};
        int[] frequencies = {10, 100};
        double[] magnitudes = {0.1, 0.5, 0.75};
//        double[] rhos = {0.02, 0.2, 0.4, 0.6, 0.8};
//        double[] alphas = {1.0};
//        double[] betas = {2.0, 3.0, 4.0, 5.0};

        ExperimentResultWriter resultWriter = new ExperimentResultWriter();
        resultWriter.initialize(numIterations);
        for (String test : tspInstances) {
            for (int freq : frequencies) {
                for (double mag : magnitudes) {
//                    for (double rho : rhos) {
//                        for (double alpha : alphas) {
//                            for (double beta : betas) {
                    executeMMASTest(resultWriter, test, mag, freq, 0.02, 1.0, 5.0);
                    executeMMAS3OPTTest(resultWriter, test, mag, freq, 0.2, 1.0, 5.0);
                    executeMMASUSTest(resultWriter, test, mag, freq, 0.8, 1.0, 5.0);
                    executeMMASMEMTest(resultWriter, test, mag, freq, 0.8, 1.0, 5.0);
                    executeMMASMEMUSTest(resultWriter, test, mag, freq, 0.8, 1.0, 5.0);
//                            }
//                        }
//                    }
                }
            }
        }
        resultWriter.compileResults(resultsPath, "ALL_RESULTS_COMPILED.csv");
    }

    private static void executeMMASTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = numTrials;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
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
            results.add(((MMAS_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMASUSTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = numTrials;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_US_ADTSP mmas_adtsp = new MMAS_US_ADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
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
            results.add(((MMAS_US_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMAS3OPTTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = numTrials;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_3OPT_ADTSP mmas_adtsp = new MMAS_3OPT_ADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
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
            results.add(((MMAS_3OPT_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMASMEMTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = numTrials;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_MEM_ADTSP mmas_adtsp = new MMAS_MEM_ADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
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
            results.add(((MMAS_MEM_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }

    private static void executeMMASMEMUSTest(ExperimentResultWriter resultWriter, String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = numTrials;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_MEM_US_ADTSP mmas_adtsp = new MMAS_MEM_US_ADTSP(graph, rho, numIterations, mag, freq, alpha, beta);
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
            results.add(((MMAS_MEM_US_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        resultWriter.computeResults(resultsPath, algs.get(0).getClass().getSimpleName(), testInstance, mag, freq, rho, alpha, beta, result);
    }
}
