package com.github.schmittjoaopedro.analisys;

import com.github.schmittjoaopedro.algorithms.*;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.tools.IterationStatistic;
import com.github.schmittjoaopedro.tools.TrialExecutor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmComparator {

    private static final String resultsPath = "C:\\Temp";

    public static void main(String[] args) throws Exception {
        for (String test : new String[]{"joinville78.vrp", "joinville125.vrp"}) {
            for (int freq : new int[]{10, 100}) {
                for (double mag : new double[]{0.1, 0.5, 0.75}) {
                    executeMMASMEMUSTest(test, mag, freq);
                    executeMMASUSTest(test, mag, freq);
                    executeMMASMEMTest(test, mag, freq);
                }
            }
        }
    }

    private static void executeMMASTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromTSP(file);
            MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 1000, mag, freq);
            mmas_adtsp.setDbgpSeed(1);
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
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.toString()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMASMEMTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromJoinville(file);
            MMAS_MEM_MADTSP mmas_mem_adtsp = new MMAS_MEM_MADTSP(graph, 0.8, 1000, mag, freq);
            mmas_mem_adtsp.setDbgpSeed(1);
            mmas_mem_adtsp.setMmasSeed(i);
            mmas_mem_adtsp.setStatisticInterval(1);
            mmas_mem_adtsp.setShowLog(false);
            algs.add(mmas_mem_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((MMAS_MEM_MADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.toString()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_MEM_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMASMEMUSTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromJoinville(file);
            MMAS_MEM_US_MADTSP mmas_mem_us_adtsp = new MMAS_MEM_US_MADTSP(graph, 0.8, 1000, mag, freq);
            mmas_mem_us_adtsp.setDbgpSeed(1);
            mmas_mem_us_adtsp.setMmasSeed(i);
            mmas_mem_us_adtsp.setStatisticInterval(1);
            mmas_mem_us_adtsp.setShowLog(false);
            algs.add(mmas_mem_us_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            results.add(((MMAS_MEM_US_MADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);
        StringBuilder finalResult = new StringBuilder();
        for (IterationStatistic iter : result) {
            finalResult.append(iter.toString()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_MEM_US_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMASUSTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            File file = new File(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance).getFile());
            Graph graph = GraphFactory.createGraphFromJoinville(file);
            MMAS_US_MADTSP mmas_us_adtsp = new MMAS_US_MADTSP(graph, 0.8, 1000, mag, freq);
            mmas_us_adtsp.setDbgpSeed(1);
            mmas_us_adtsp.setMmasSeed(i);
            mmas_us_adtsp.setStatisticInterval(1);
            mmas_us_adtsp.setShowLog(false);
            algs.add(mmas_us_adtsp);
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
            finalResult.append(iter.toString()).append('\n');
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_US_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

}