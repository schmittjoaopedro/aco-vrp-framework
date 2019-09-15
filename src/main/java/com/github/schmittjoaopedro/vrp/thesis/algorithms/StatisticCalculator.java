package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;

public class StatisticCalculator {

    private String testName;

    private TestResult testResult;

    private LinkedList<Statistic> statistics = new LinkedList<>();

    private int maxIterations;

    public StatisticCalculator(String testName, int maxIterations) {
        this.testName = testName;
        this.maxIterations = maxIterations;
        this.testResult = new TestResult();
    }

    public synchronized void addStatisticsResult(Statistic statistic) {
        statistics.add(statistic);
    }

    public void calculateInstanceStatistics() {
        Collections.sort(statistics);
        // Calculate bsf
        Statistic bestSolution = statistics.get(0);
        testResult.bestNv = bestSolution.solutionBest.tours.size();
        testResult.bestTc = bestSolution.solutionBest.totalCost;
        testResult.bestFeasible = bestSolution.solutionBest.feasible ? 1 : 0;
        testResult.executionTime = bestSolution.executionTime;
        // Calculate summary
        DescriptiveStatistics bsfNvStats = new DescriptiveStatistics();
        DescriptiveStatistics bsfTcStats = new DescriptiveStatistics();
        DescriptiveStatistics bsfFcStats = new DescriptiveStatistics();
        DescriptiveStatistics timeStats = new DescriptiveStatistics();
        for (int i = 0; i < statistics.size(); i++) {
            bsfNvStats.addValue(statistics.get(i).solutionBest.tours.size());
            bsfTcStats.addValue(statistics.get(i).solutionBest.totalCost);
            bsfFcStats.addValue(statistics.get(i).solutionBest.feasible ? 1.0 : 0.0);
            timeStats.addValue(statistics.get(i).executionTime);
        }
        testResult.meanBestNv = bsfNvStats.getMean();
        testResult.sdBestNv = bsfNvStats.getStandardDeviation();
        testResult.meanBestTc = bsfTcStats.getMean();
        testResult.sdBestTc = bsfTcStats.getStandardDeviation();
        testResult.meanBestFeasible = bsfFcStats.getMean();
        testResult.sdBestFeasible = bsfFcStats.getStandardDeviation();
        testResult.meanExecutionTime = timeStats.getMean();
        testResult.sdExecutionTime = timeStats.getStandardDeviation();
        // Iterations statistics
        testResult.iterationStatistics = new IterationStatistics(maxIterations);
        for (int i = 0; i < maxIterations; i++) {
            testResult.iterationStatistics.mean_global_nv[i] = mean(statistics, "global_nv", i);
            testResult.iterationStatistics.sd_global_nv[i] = sd(statistics, "global_nv", i);
            testResult.iterationStatistics.mean_global_tc[i] = mean(statistics, "global_tc", i);
            testResult.iterationStatistics.sd_global_tc[i] = sd(statistics, "global_tc", i);
            testResult.iterationStatistics.mean_global_fc[i] = mean(statistics, "global_fc", i);
            testResult.iterationStatistics.sd_global_fc[i] = sd(statistics, "global_fc", i);
            testResult.iterationStatistics.mean_vehicle_minimizer_best_nv[i] = mean(statistics, "vehicle_minimizer_best_nv", i);
            testResult.iterationStatistics.sd_vehicle_minimizer_best_nv[i] = sd(statistics, "vehicle_minimizer_best_nv", i);
            testResult.iterationStatistics.mean_vehicle_minimizer_best_tc[i] = mean(statistics, "vehicle_minimizer_best_tc", i);
            testResult.iterationStatistics.sd_vehicle_minimizer_best_tc[i] = sd(statistics, "vehicle_minimizer_best_tc", i);
            testResult.iterationStatistics.mean_vehicle_minimizer_local_nv[i] = mean(statistics, "vehicle_minimizer_local_nv", i);
            testResult.iterationStatistics.sd_vehicle_minimizer_local_nv[i] = sd(statistics, "vehicle_minimizer_local_nv", i);
            testResult.iterationStatistics.mean_vehicle_minimizer_local_tc[i] = mean(statistics, "vehicle_minimizer_local_tc", i);
            testResult.iterationStatistics.sd_vehicle_minimizer_local_tc[i] = sd(statistics, "vehicle_minimizer_local_tc", i);
            testResult.iterationStatistics.mean_vehicle_minimizer_temperature[i] = mean(statistics, "vehicle_minimizer_temperature", i);
            testResult.iterationStatistics.sd_vehicle_minimizer_temperature[i] = sd(statistics, "vehicle_minimizer_temperature", i);
            testResult.iterationStatistics.mean_cost_minimizer_best_nv[i] = mean(statistics, "cost_minimizer_best_nv", i);
            testResult.iterationStatistics.sd_cost_minimizer_best_nv[i] = sd(statistics, "cost_minimizer_best_nv", i);
            testResult.iterationStatistics.mean_cost_minimizer_best_tc[i] = mean(statistics, "cost_minimizer_best_tc", i);
            testResult.iterationStatistics.sd_cost_minimizer_best_tc[i] = sd(statistics, "cost_minimizer_best_tc", i);
            testResult.iterationStatistics.mean_cost_minimizer_local_nv[i] = mean(statistics, "cost_minimizer_local_nv", i);
            testResult.iterationStatistics.sd_cost_minimizer_local_nv[i] = sd(statistics, "cost_minimizer_local_nv", i);
            testResult.iterationStatistics.mean_cost_minimizer_local_tc[i] = mean(statistics, "cost_minimizer_local_tc", i);
            testResult.iterationStatistics.sd_cost_minimizer_local_tc[i] = sd(statistics, "cost_minimizer_local_tc", i);
            testResult.iterationStatistics.mean_cost_minimizer_temperature[i] = mean(statistics, "cost_minimizer_temperature", i);
            testResult.iterationStatistics.sd_cost_minimizer_temperature[i] = sd(statistics, "cost_minimizer_temperature", i);
        }
    }

    public void writeTestResultToCsv(String directory, boolean bsf, boolean summary, boolean iteration) {
        if (bsf) {
            CsvFile bsfFile = new CsvFile(testName + "_bsf");
            bsfFile.addData("tc");
            bsfFile.addData("nv");
            bsfFile.addData("feasible");
            bsfFile.addData("execTime");
            bsfFile.newRow();
            bsfFile.addData(testResult.bestTc);
            bsfFile.addData(testResult.bestNv);
            bsfFile.addData(testResult.bestFeasible);
            bsfFile.addData(testResult.executionTime);
            bsfFile.newRow();
            bsfFile.saveToDisk(directory);
        }
        if (summary) {
            CsvFile summaryFile = new CsvFile(testName + "_summary");
            summaryFile.addData("mean_bsf_nv");
            summaryFile.addData("sd_bsf_nv");
            summaryFile.addData("mean_bsf_tc");
            summaryFile.addData("sd_bsf_tc");
            summaryFile.addData("mean_bsf_fc");
            summaryFile.addData("sd_bsf_fc");
            summaryFile.addData("mean_exec_time");
            summaryFile.addData("sd_exec_time");
            summaryFile.newRow();
            summaryFile.addData(testResult.meanBestNv);
            summaryFile.addData(testResult.sdBestNv);
            summaryFile.addData(testResult.meanBestTc);
            summaryFile.addData(testResult.sdBestTc);
            summaryFile.addData(testResult.meanBestFeasible);
            summaryFile.addData(testResult.sdBestFeasible);
            summaryFile.addData(testResult.meanExecutionTime);
            summaryFile.addData(testResult.sdExecutionTime);
            summaryFile.newRow();
            summaryFile.saveToDisk(directory);
        }
        if (iteration) {
            CsvFile detailedFile = new CsvFile(testName + "_iteration");
            detailedFile.addData("mean_global_nv");
            detailedFile.addData("sd_global_nv");
            detailedFile.addData("mean_global_tc");
            detailedFile.addData("sd_global_tc");
            detailedFile.addData("mean_global_fc");
            detailedFile.addData("sd_global_fc");
            detailedFile.addData("mean_vehicle_minimizer_best_nv");
            detailedFile.addData("sd_vehicle_minimizer_best_nv");
            detailedFile.addData("mean_vehicle_minimizer_best_tc");
            detailedFile.addData("sd_vehicle_minimizer_best_tc");
            detailedFile.addData("mean_vehicle_minimizer_local_nv");
            detailedFile.addData("sd_vehicle_minimizer_local_nv");
            detailedFile.addData("mean_vehicle_minimizer_local_tc");
            detailedFile.addData("sd_vehicle_minimizer_local_tc");
            detailedFile.addData("mean_vehicle_minimizer_temperature");
            detailedFile.addData("sd_vehicle_minimizer_temperature");
            detailedFile.addData("mean_cost_minimizer_best_nv");
            detailedFile.addData("sd_cost_minimizer_best_nv");
            detailedFile.addData("mean_cost_minimizer_best_tc");
            detailedFile.addData("sd_cost_minimizer_best_tc");
            detailedFile.addData("mean_cost_minimizer_local_nv");
            detailedFile.addData("sd_cost_minimizer_local_nv");
            detailedFile.addData("mean_cost_minimizer_local_tc");
            detailedFile.addData("sd_cost_minimizer_local_tc");
            detailedFile.addData("mean_cost_minimizer_temperature");
            detailedFile.addData("sd_cost_minimizer_temperature");
            detailedFile.newRow();
            IterationStatistics iterationStatistics = testResult.iterationStatistics;
            for (int i = 1; i < maxIterations; i++) {
                detailedFile.addData(iterationStatistics.mean_global_nv[i]);
                detailedFile.addData(iterationStatistics.sd_global_nv[i]);
                detailedFile.addData(iterationStatistics.mean_global_tc[i]);
                detailedFile.addData(iterationStatistics.sd_global_tc[i]);
                detailedFile.addData(iterationStatistics.mean_global_fc[i]);
                detailedFile.addData(iterationStatistics.sd_global_fc[i]);
                detailedFile.addData(iterationStatistics.mean_vehicle_minimizer_best_nv[i]);
                detailedFile.addData(iterationStatistics.sd_vehicle_minimizer_best_nv[i]);
                detailedFile.addData(iterationStatistics.mean_vehicle_minimizer_best_tc[i]);
                detailedFile.addData(iterationStatistics.sd_vehicle_minimizer_best_tc[i]);
                detailedFile.addData(iterationStatistics.mean_vehicle_minimizer_local_nv[i]);
                detailedFile.addData(iterationStatistics.sd_vehicle_minimizer_local_nv[i]);
                detailedFile.addData(iterationStatistics.mean_vehicle_minimizer_local_tc[i]);
                detailedFile.addData(iterationStatistics.sd_vehicle_minimizer_local_tc[i]);
                detailedFile.addData(iterationStatistics.mean_vehicle_minimizer_temperature[i]);
                detailedFile.addData(iterationStatistics.sd_vehicle_minimizer_temperature[i]);
                detailedFile.addData(iterationStatistics.mean_cost_minimizer_best_nv[i]);
                detailedFile.addData(iterationStatistics.sd_cost_minimizer_best_nv[i]);
                detailedFile.addData(iterationStatistics.mean_cost_minimizer_best_tc[i]);
                detailedFile.addData(iterationStatistics.sd_cost_minimizer_best_tc[i]);
                detailedFile.addData(iterationStatistics.mean_cost_minimizer_local_nv[i]);
                detailedFile.addData(iterationStatistics.sd_cost_minimizer_local_nv[i]);
                detailedFile.addData(iterationStatistics.mean_cost_minimizer_local_tc[i]);
                detailedFile.addData(iterationStatistics.sd_cost_minimizer_local_tc[i]);
                detailedFile.addData(iterationStatistics.mean_cost_minimizer_temperature[i]);
                detailedFile.addData(iterationStatistics.sd_cost_minimizer_temperature[i]);
                detailedFile.newRow();
            }
            detailedFile.saveToDisk(directory);
        }
    }

    private double mean(LinkedList<Statistic> statistics, String fieldName, int iteration) {
        double mean = 0.0;
        for (Statistic statistic : statistics) {
            mean += getFromDoubleArray(statistic, fieldName, iteration);
        }
        return Maths.round(mean / statistics.size(), 2);
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
        return Maths.round(dev);
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

    protected class TestResult {

        public String name;

        public int bestNv;

        public double bestTc;

        public int bestFeasible;

        public long executionTime;

        public double meanBestNv;

        public double sdBestNv;

        public double meanBestTc;

        public double sdBestTc;

        public double meanBestFeasible;

        public double sdBestFeasible;

        public double meanExecutionTime;

        public double sdExecutionTime;

        private IterationStatistics iterationStatistics;

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
