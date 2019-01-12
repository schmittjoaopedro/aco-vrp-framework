package com.github.schmittjoaopedro.tsp.analisys;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_3OPT_MADTSP;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_MADTSP;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_US_MADTSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.tools.TrialExecutor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmComparator {

    private static final String resultsPath = "C:\\Temp";

    public static void main(String[] args) throws Exception {
        for (String test : new String[]{"KroA100.tsp", "KroA150.tsp", "KroA200.tsp"}) {
            for (int freq : new int[]{10, 100}) {
                for (double mag : new double[]{0.1, 0.5, 0.75}) {
//                    for (double rho : new double[]{0.02, 0.2, 0.4, 0.6, 0.8}) {
//                        for (double beta : new double[]{2.0, 3.0, 4.0, 5.0}) {
                    executeMMASTest(test, mag, freq, 0.02, 1.0, 5.0);
                    executeMMASUSTest(test, mag, freq, 0.8, 1.0, 5.0);
                    executeMMAS3OPTTest(test, mag, freq, 0.02, 1.0, 5.0);
//                        }
//                    }
                }
            }
        }
    }

    private static void executeMMASTest(String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_MADTSP mmas_adtsp = new MMAS_MADTSP(graph, rho, 1000, mag, freq, alpha, beta);
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
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.getBestSoFar()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath,
                testInstance +
                        "_freq_" + freq +
                        "_mag_" + mag +
                        "_rho_" + rho +
                        "_alpha_" + alpha +
                        "_beta_" + beta +
                        "_" + algs.get(0).getClass().getSimpleName() +
                        ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMASUSTest(String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_US_MADTSP mmas_adtsp = new MMAS_US_MADTSP(graph, rho, 1000, mag, freq, alpha, beta);
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
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.getBestSoFar()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath,
                testInstance +
                        "_freq_" + freq +
                        "_mag_" + mag +
                        "_rho_" + rho +
                        "_alpha_" + alpha +
                        "_beta_" + beta +
                        "_" + algs.get(0).getClass().getSimpleName() +
                        ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMAS3OPTTest(String testInstance, double mag, int freq, double rho, double alpha, double beta) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_3OPT_MADTSP mmas_adtsp = new MMAS_3OPT_MADTSP(graph, rho, 1000, mag, freq, alpha, beta);
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
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.getBestSoFar()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath,
                testInstance +
                        "_freq_" + freq +
                        "_mag_" + mag +
                        "_rho_" + rho +
                        "_alpha_" + alpha +
                        "_beta_" + beta +
                        "_" + algs.get(0).getClass().getSimpleName() +
                        ".txt").toFile(), finalResult.toString(), "UTF-8");
    }
}
