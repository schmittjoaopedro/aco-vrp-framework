package com.github.schmittjoaopedro.analisys;

import com.github.schmittjoaopedro.algorithms.MMAS_ADTSP;
import com.github.schmittjoaopedro.algorithms.MMAS_MEM_MADTSP;
import com.github.schmittjoaopedro.algorithms.MMAS_MEM_US_ADTSP;
import com.github.schmittjoaopedro.algorithms.MMAS_US_MADTSP;
import com.github.schmittjoaopedro.tools.IterationStatistic;
import com.github.schmittjoaopedro.tools.TrialExecutor;
import org.apache.commons.io.FileUtils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmComparator {

    private static final String resultsPath = "C:\\Temp";

    public static void main(String[] args) throws Exception {
        for (String test : new String[]{"KroA100", "KroA150", "KroA200"}) {

            executeMMASUSTest(test, 0.1, 10);
            executeMMASMEMTest(test, 0.1, 10);

            executeMMASUSTest(test, 0.1, 100);
            executeMMASMEMTest(test, 0.1, 100);

            executeMMASUSTest(test, 0.5, 10);
            executeMMASMEMTest(test, 0.5, 10);

            executeMMASUSTest(test, 0.5, 100);
            executeMMASMEMTest(test, 0.5, 100);

            executeMMASUSTest(test, 0.75, 10);
            executeMMASMEMTest(test, 0.75, 10);

            executeMMASUSTest(test, 0.75, 100);
            executeMMASMEMTest(test, 0.75, 100);
        }
    }

    private static void executeMMASTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance + ".tsp").getFile(), 0.8, 1000, mag, freq);
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
            MMAS_MEM_MADTSP mmas_mem_madtsp = new MMAS_MEM_MADTSP(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance + ".tsp").getFile(), 0.8, 1000, mag, freq);
            mmas_mem_madtsp.setDbgpSeed(1);
            mmas_mem_madtsp.setMmasSeed(i);
            mmas_mem_madtsp.setStatisticInterval(1);
            mmas_mem_madtsp.setShowLog(false);
            algs.add(mmas_mem_madtsp);
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
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_MEM_MOV_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

    private static void executeMMASMEMUSTest(String testInstance, double mag, int freq) throws Exception {
        int trials = 30;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            MMAS_MEM_US_ADTSP mmas_mem_us_adtsp = new MMAS_MEM_US_ADTSP(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance + ".tsp").getFile(), 0.8, 1000, mag, freq);
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
            results.add(((MMAS_MEM_US_ADTSP) algs.get(i)).getIterationStatistics());
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
            MMAS_US_MADTSP mmas_us_madtsp = new MMAS_US_MADTSP(AlgorithmComparator.class.getClassLoader().getResource("tsp/" + testInstance + ".tsp").getFile(), 0.8, 1000, mag, freq);
            mmas_us_madtsp.setDbgpSeed(1);
            mmas_us_madtsp.setMmasSeed(i);
            mmas_us_madtsp.setStatisticInterval(1);
            mmas_us_madtsp.setShowLog(false);
            algs.add(mmas_us_madtsp);
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
        FileUtils.writeStringToFile(Paths.get(resultsPath, "MMAS_US_MOV_" + testInstance + "_mag_" + mag + "_freq_" + freq + ".txt").toFile(), finalResult.toString(), "UTF-8");
    }

}
