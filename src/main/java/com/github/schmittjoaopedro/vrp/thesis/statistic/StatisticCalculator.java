package com.github.schmittjoaopedro.vrp.thesis.statistic;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.IterationStatistics;
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

    private LinkedList<ExperimentStatistics> experimentStatistics = new LinkedList<>();

    private int maxIterations;

    public StatisticCalculator(String testName) {
        this.testName = testName;
        this.testResult = new TestResult();
    }

    public synchronized void addStatisticsResult(ExperimentStatistics experimentStatistics) {
        this.experimentStatistics.add(experimentStatistics);
    }

    public void calculateInstanceStatistics() {
        Collections.sort(experimentStatistics);
        // Calculate bsf
        ExperimentStatistics bestExperiment = experimentStatistics.get(0);
        testResult.bestNv = bestExperiment.solutionBest.tours.size();
        testResult.bestTc = bestExperiment.solutionBest.totalCost;
        testResult.bestFeasible = bestExperiment.solutionBest.feasible ? 1 : 0;
        testResult.executionTime = bestExperiment.totalTime;
        testResult.totalIterations = bestExperiment.totalIterations;
        testResult.solutionEvaluations = bestExperiment.numSolutionsEvaluation;
        // Calculate summary
        DescriptiveStatistics bsfNvStats = new DescriptiveStatistics();
        DescriptiveStatistics bsfTcStats = new DescriptiveStatistics();
        DescriptiveStatistics bsfFcStats = new DescriptiveStatistics();
        DescriptiveStatistics timeStats = new DescriptiveStatistics();
        DescriptiveStatistics iterationStats = new DescriptiveStatistics();
        DescriptiveStatistics solutionEvaluationStats = new DescriptiveStatistics();
        for (int i = 0; i < experimentStatistics.size(); i++) {
            bsfNvStats.addValue(experimentStatistics.get(i).solutionBest.tours.size());
            bsfTcStats.addValue(experimentStatistics.get(i).solutionBest.totalCost);
            bsfFcStats.addValue(experimentStatistics.get(i).solutionBest.feasible ? 1.0 : 0.0);
            timeStats.addValue(experimentStatistics.get(i).totalTime);
            iterationStats.addValue(experimentStatistics.get(i).totalIterations);
            solutionEvaluationStats.addValue(experimentStatistics.get(i).numSolutionsEvaluation);
        }
        testResult.meanBestNv = bsfNvStats.getMean();
        testResult.sdBestNv = bsfNvStats.getStandardDeviation();
        testResult.meanBestTc = bsfTcStats.getMean();
        testResult.sdBestTc = bsfTcStats.getStandardDeviation();
        testResult.meanBestFeasible = bsfFcStats.getMean();
        testResult.sdBestFeasible = bsfFcStats.getStandardDeviation();
        testResult.meanExecutionTime = timeStats.getMean();
        testResult.sdExecutionTime = timeStats.getStandardDeviation();
        testResult.meanIterations = iterationStats.getMean();
        testResult.sdIterations = iterationStats.getStandardDeviation();
        testResult.meanSolutionEvaluations = solutionEvaluationStats.getMean();
        testResult.sdSolutionEvaluations = solutionEvaluationStats.getStandardDeviation();
    }

    public void writeTestResultToCsv(String directory, boolean bsf, boolean summary, boolean all) {
        if (bsf) {
            CsvFile bsfFile = new CsvFile(testName + "_bsf");
            bsfFile.addData("tc");
            bsfFile.addData("nv");
            bsfFile.addData("feasible");
            bsfFile.addData("execTime");
            bsfFile.addData("totalIterations");
            bsfFile.addData("solutionEvaluations");
            bsfFile.newRow();
            bsfFile.addData(testResult.bestTc);
            bsfFile.addData(testResult.bestNv);
            bsfFile.addData(testResult.bestFeasible);
            bsfFile.addData(testResult.executionTime);
            bsfFile.addData(testResult.totalIterations);
            bsfFile.addData(testResult.solutionEvaluations);
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
            summaryFile.addData("mean_total_iterations");
            summaryFile.addData("sd_solution_evaluations");
            summaryFile.addData("mean_total_iterations");
            summaryFile.addData("sd_solution_evaluations");
            summaryFile.newRow();
            summaryFile.addData(testResult.meanBestNv);
            summaryFile.addData(testResult.sdBestNv);
            summaryFile.addData(testResult.meanBestTc);
            summaryFile.addData(testResult.sdBestTc);
            summaryFile.addData(testResult.meanBestFeasible);
            summaryFile.addData(testResult.sdBestFeasible);
            summaryFile.addData(testResult.meanExecutionTime);
            summaryFile.addData(testResult.sdExecutionTime);
            summaryFile.addData(testResult.meanIterations);
            summaryFile.addData(testResult.sdIterations);
            summaryFile.addData(testResult.meanSolutionEvaluations);
            summaryFile.addData(testResult.sdSolutionEvaluations);
            summaryFile.newRow();
            summaryFile.saveToDisk(directory);
        }
        if (all) {
            CsvFile detailedFile = new CsvFile(testName + "_all");
            detailedFile.addData("trial");
            detailedFile.addData("tc");
            detailedFile.addData("nv");
            detailedFile.addData("feasible");
            detailedFile.addData("execTime");
            detailedFile.addData("totalIterations");
            detailedFile.addData("numSolutionsEvaluation");
            detailedFile.newRow();
            int trial = 0;
            for (ExperimentStatistics experiment : experimentStatistics) {
                trial++;
                detailedFile.addData(trial);
                detailedFile.addData(experiment.solutionBest.totalCost);
                detailedFile.addData(experiment.solutionBest.tours.size());
                detailedFile.addData(experiment.solutionBest.feasible);
                detailedFile.addData(experiment.totalTime);
                detailedFile.addData(experiment.totalIterations);
                detailedFile.addData(experiment.numSolutionsEvaluation);
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

        public long totalIterations;

        public long solutionEvaluations;

        public double meanBestNv;

        public double sdBestNv;

        public double meanBestTc;

        public double sdBestTc;

        public double meanBestFeasible;

        public double sdBestFeasible;

        public double meanExecutionTime;

        public double sdExecutionTime;

        public double meanIterations;

        public double sdIterations;

        public double meanSolutionEvaluations;

        public double sdSolutionEvaluations;

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
