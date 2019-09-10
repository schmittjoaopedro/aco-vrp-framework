package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatisticCalculator {

    private LinkedList<String> testNames = new LinkedList<>();

    private Map<String, LinkedList<Statistic>> testStatistics = new HashMap<>();

    private Map<String, TestResult> testResults = new HashMap<>();

    private String directory;

    private int maxIterations;

    public StatisticCalculator(String directory, int maxIterations) {
        this.directory = directory;
        this.maxIterations = maxIterations;
    }

    public synchronized void addStatisticsResult(Statistic statistic) {
        if (!testStatistics.containsKey(statistic.instance.name)) {
            testNames.add(statistic.instance.name);
            testStatistics.put(statistic.instance.name, new LinkedList<>());
        }
        testStatistics.get(statistic.instance.name).add(statistic);
    }

    public void consolidateSingleTestCase(String testName, boolean saveOnDisk) {
        TestResult testResult = new TestResult();
        defineBestSolution(testResult, testStatistics.get(testName));
        testResults.put(testName, testResult);
        if (saveOnDisk) {
            writeTestResultToCsv(testResult, testName);
        }
    }

    public void consolidateAllTestCases(String fileName) {
        CsvFile csvFile = new CsvFile(fileName);
        TestResult testResult;

        csvFile.addData("instance");
        for (String testName : testNames) {
            csvFile.addData(testName + "_nv");
            csvFile.addData(testName + "_tc");
            csvFile.addData(testName + "_fc");
        }
        csvFile.newRow();

        csvFile.addData("bsf");
        for (String testName : testNames) {
            testResult = testResults.get(testName);
            csvFile.addData(testResult.bestNv);
            csvFile.addData(testResult.bestTc);
            csvFile.addData(testResult.bestFeasible);
        }
        csvFile.newRow();

        for (int i = 0; i < maxIterations; i++) {
            csvFile.addData(i);
            for (String testName : testNames) {
                testResult = testResults.get(testName);
                csvFile.addData(testResult.meanSdStatistic.mean_global_nv[i]);
                csvFile.addData(testResult.meanSdStatistic.mean_global_tc[i]);
                csvFile.addData(testResult.meanSdStatistic.mean_global_fc[i]);
            }
            csvFile.newRow();
        }
        csvFile.saveToDisk(directory);
    }

    private void defineBestSolution(TestResult testResult, LinkedList<Statistic> statistics) {
        Collections.sort(statistics);
        Statistic bestSolution = statistics.get(0);
        testResult.bestNv = bestSolution.solutionBest.tours.size();
        testResult.bestTc = bestSolution.solutionBest.totalCost;
        testResult.meanSdStatistic = new StatisticMeanSd(maxIterations);
        for (int i = 0; i < maxIterations; i++) {
            testResult.meanSdStatistic.mean_global_nv[i] = mean(statistics, "global_nv", i);
            testResult.meanSdStatistic.sd_global_nv[i] = sd(statistics, "global_nv", i);
            testResult.meanSdStatistic.mean_global_tc[i] = mean(statistics, "global_tc", i);
            testResult.meanSdStatistic.sd_global_tc[i] = sd(statistics, "global_tc", i);
            testResult.meanSdStatistic.mean_global_fc[i] = mean(statistics, "global_fc", i);
            testResult.meanSdStatistic.sd_global_fc[i] = sd(statistics, "global_fc", i);
            testResult.meanSdStatistic.mean_vehicle_minimizer_best_nv[i] = mean(statistics, "vehicle_minimizer_best_nv", i);
            testResult.meanSdStatistic.sd_vehicle_minimizer_best_nv[i] = sd(statistics, "vehicle_minimizer_best_nv", i);
            testResult.meanSdStatistic.mean_vehicle_minimizer_best_tc[i] = mean(statistics, "vehicle_minimizer_best_tc", i);
            testResult.meanSdStatistic.sd_vehicle_minimizer_best_tc[i] = sd(statistics, "vehicle_minimizer_best_tc", i);
            testResult.meanSdStatistic.mean_vehicle_minimizer_local_nv[i] = mean(statistics, "vehicle_minimizer_local_nv", i);
            testResult.meanSdStatistic.sd_vehicle_minimizer_local_nv[i] = sd(statistics, "vehicle_minimizer_local_nv", i);
            testResult.meanSdStatistic.mean_vehicle_minimizer_local_tc[i] = mean(statistics, "vehicle_minimizer_local_tc", i);
            testResult.meanSdStatistic.sd_vehicle_minimizer_local_tc[i] = sd(statistics, "vehicle_minimizer_local_tc", i);
            testResult.meanSdStatistic.mean_vehicle_minimizer_temperature[i] = mean(statistics, "vehicle_minimizer_temperature", i);
            testResult.meanSdStatistic.sd_vehicle_minimizer_temperature[i] = sd(statistics, "vehicle_minimizer_temperature", i);
            testResult.meanSdStatistic.mean_cost_minimizer_best_nv[i] = mean(statistics, "cost_minimizer_best_nv", i);
            testResult.meanSdStatistic.sd_cost_minimizer_best_nv[i] = sd(statistics, "cost_minimizer_best_nv", i);
            testResult.meanSdStatistic.mean_cost_minimizer_best_tc[i] = mean(statistics, "cost_minimizer_best_tc", i);
            testResult.meanSdStatistic.sd_cost_minimizer_best_tc[i] = sd(statistics, "cost_minimizer_best_tc", i);
            testResult.meanSdStatistic.mean_cost_minimizer_local_nv[i] = mean(statistics, "cost_minimizer_local_nv", i);
            testResult.meanSdStatistic.sd_cost_minimizer_local_nv[i] = sd(statistics, "cost_minimizer_local_nv", i);
            testResult.meanSdStatistic.mean_cost_minimizer_local_tc[i] = mean(statistics, "cost_minimizer_local_tc", i);
            testResult.meanSdStatistic.sd_cost_minimizer_local_tc[i] = sd(statistics, "cost_minimizer_local_tc", i);
            testResult.meanSdStatistic.mean_cost_minimizer_temperature[i] = mean(statistics, "cost_minimizer_temperature", i);
            testResult.meanSdStatistic.sd_cost_minimizer_temperature[i] = sd(statistics, "cost_minimizer_temperature", i);
        }
    }

    private double mean(LinkedList<Statistic> statistics, String fieldName, int iteration) {
        double mean = 0.0;
        for (Statistic statistic : statistics) {
            mean += getFromDoubleArray(statistic, fieldName, iteration);
        }
        return mean / statistics.size();
    }

    private double sd(LinkedList<Statistic> statistics, String fieldName, int iteration) {
        double dev = 0.0;
        double mean = mean(statistics, fieldName, iteration);
        if (statistics.size() > 1) {
            for (Statistic statistic : statistics) {
                double value = getFromDoubleArray(statistic, fieldName, iteration);
                dev += (value - mean) * (value - mean);
            }
            dev = Math.sqrt(dev / (double) (statistics.size() - 1));
        }
        return dev;
    }

    private double getFromDoubleArray(Statistic statistic, String fieldName, int iteration) {
        double value;
        try {
            Object valueObj = FieldUtils.readField(statistic, fieldName, true);
            if (valueObj instanceof int[]) {
                value = ((int[]) valueObj)[iteration];
            } else {
                value = ((double[]) valueObj)[iteration];
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return value;
    }

    private void writeTestResultToCsv(TestResult testResult, String testName) {
        CsvFile csvFile = new CsvFile(testName);
        csvFile.addData("bsf_nv");
        csvFile.addData("bsf_tc");
        csvFile.addData("mean_global_nv");
        csvFile.addData("sd_global_nv");
        csvFile.addData("mean_global_tc");
        csvFile.addData("sd_global_tc");
        csvFile.addData("mean_vehicle_minimizer_best_nv");
        csvFile.addData("sd_vehicle_minimizer_best_nv");
        csvFile.addData("mean_vehicle_minimizer_best_tc");
        csvFile.addData("sd_vehicle_minimizer_best_tc");
        csvFile.addData("mean_vehicle_minimizer_local_nv");
        csvFile.addData("sd_vehicle_minimizer_local_nv");
        csvFile.addData("mean_vehicle_minimizer_local_tc");
        csvFile.addData("sd_vehicle_minimizer_local_tc");
        csvFile.addData("mean_vehicle_minimizer_temperature");
        csvFile.addData("sd_vehicle_minimizer_temperature");
        csvFile.addData("mean_cost_minimizer_best_nv");
        csvFile.addData("sd_cost_minimizer_best_nv");
        csvFile.addData("mean_cost_minimizer_best_tc");
        csvFile.addData("sd_cost_minimizer_best_tc");
        csvFile.addData("mean_cost_minimizer_local_nv");
        csvFile.addData("sd_cost_minimizer_local_nv");
        csvFile.addData("mean_cost_minimizer_local_tc");
        csvFile.addData("sd_cost_minimizer_local_tc");
        csvFile.addData("mean_cost_minimizer_temperature");
        csvFile.addData("sd_cost_minimizer_temperature");
        csvFile.newRow();
        StatisticMeanSd statisticMeanSd = testResult.meanSdStatistic;
        for (int i = 1; i < maxIterations; i++) {
            if (i == 1) {
                csvFile.addData(testResult.bestNv);
                csvFile.addData(testResult.bestTc);
            } else {
                csvFile.addData(StringUtils.EMPTY);
                csvFile.addData(StringUtils.EMPTY);
            }
            csvFile.addData(statisticMeanSd.mean_global_nv[i]);
            csvFile.addData(statisticMeanSd.sd_global_nv[i]);
            csvFile.addData(statisticMeanSd.mean_global_tc[i]);
            csvFile.addData(statisticMeanSd.sd_global_tc[i]);
            csvFile.addData(statisticMeanSd.mean_vehicle_minimizer_best_nv[i]);
            csvFile.addData(statisticMeanSd.sd_vehicle_minimizer_best_nv[i]);
            csvFile.addData(statisticMeanSd.mean_vehicle_minimizer_best_tc[i]);
            csvFile.addData(statisticMeanSd.sd_vehicle_minimizer_best_tc[i]);
            csvFile.addData(statisticMeanSd.mean_vehicle_minimizer_local_nv[i]);
            csvFile.addData(statisticMeanSd.sd_vehicle_minimizer_local_nv[i]);
            csvFile.addData(statisticMeanSd.mean_vehicle_minimizer_local_tc[i]);
            csvFile.addData(statisticMeanSd.sd_vehicle_minimizer_local_tc[i]);
            csvFile.addData(statisticMeanSd.mean_vehicle_minimizer_temperature[i]);
            csvFile.addData(statisticMeanSd.sd_vehicle_minimizer_temperature[i]);
            csvFile.addData(statisticMeanSd.mean_cost_minimizer_best_nv[i]);
            csvFile.addData(statisticMeanSd.sd_cost_minimizer_best_nv[i]);
            csvFile.addData(statisticMeanSd.mean_cost_minimizer_best_tc[i]);
            csvFile.addData(statisticMeanSd.sd_cost_minimizer_best_tc[i]);
            csvFile.addData(statisticMeanSd.mean_cost_minimizer_local_nv[i]);
            csvFile.addData(statisticMeanSd.sd_cost_minimizer_local_nv[i]);
            csvFile.addData(statisticMeanSd.mean_cost_minimizer_local_tc[i]);
            csvFile.addData(statisticMeanSd.sd_cost_minimizer_local_tc[i]);
            csvFile.addData(statisticMeanSd.mean_cost_minimizer_temperature[i]);
            csvFile.addData(statisticMeanSd.sd_cost_minimizer_temperature[i]);
            csvFile.newRow();
        }
        csvFile.saveToDisk(directory);
    }

    protected class TestResult {

        public String name;

        public int bestNv;

        public double bestTc;

        public int bestFeasible;

        private StatisticMeanSd meanSdStatistic;

    }

    protected class CsvFile {

        private static final char CSV_SEPARATOR = ';';
        private static final char LINE_SEPARATOR = '\n';

        private StringBuilder csvContent = new StringBuilder();

        private String fileName;

        public CsvFile(String fileName) {
            this.fileName = fileName;
        }

        protected void addData(Object value) {
            csvContent.append(String.valueOf(value).replaceAll(",", ".")).append(CSV_SEPARATOR);
        }

        protected void newRow() {
            csvContent.append(LINE_SEPARATOR);
        }

        protected void saveToDisk(String directory) {
            try {
                File file = Paths.get(directory, fileName + ".csv").toFile();
                FileUtils.writeStringToFile(file, csvContent.toString(), "UTF-8");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
