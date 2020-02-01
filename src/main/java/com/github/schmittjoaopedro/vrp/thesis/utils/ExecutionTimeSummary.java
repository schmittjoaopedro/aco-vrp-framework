package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExecutionTimeSummary {

    private static final String EXECUTION_TIME_ROOT_DATA = "C:\\Temp\\UDESC-servers\\execution_time\\output\\";
    private static final String LITERATURE_ROOT_DATA = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\";
    private static final String[] PROBLEM_SIZES = {"100", "200", "400", "600", "800", "1000"};
    private static final String[] PROBLEM_GROUPS = {"LC1", "LC2", "LR1", "LR2", "LRC1", "LRC2"};

    public static void main(String[] args) {
        System.out.println(getHeader());
        for (String problemSize : PROBLEM_SIZES) {
            for (String problemGroup : PROBLEM_GROUPS) {
                printProblemResults(problemSize, problemGroup);
            }
        }
    }

    private static void printProblemResults(String problemSize, String problemGroup) {
        List<String> instances = getInstances(problemSize, problemGroup);
        StringBuilder output = new StringBuilder(problemSize + ";" + problemGroup + ";");
        addStaticExecutionTimes(problemSize, instances, output);
        for (String urgency : InstanceUtils.dynamic_urgency_suffixes) {
            addDynamicExecutionTimes(problemSize, instances, output, urgency);
        }
        for (String urgency : InstanceUtils.dynamic_apriori_suffixes) {
            addDynamicExecutionTimes(problemSize, instances, output, urgency);
        }
        System.out.println(output);
    }

    private static void addDynamicExecutionTimes(String problemSize, List<String> instances, StringBuilder output, String dynamism) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        for (String instance : instances) {
            String[][] csvData = CsvReader.readCsvFromDirectory(getDynamicPath(problemSize, dynamism, instance));
            statistics.addValue(milliToMin(Double.valueOf(csvData[1][6])));
        }
        output.append(statistics.getMean() + ";" + statistics.getStandardDeviation() + ";");
    }

    private static String getDynamicPath(String problemSize, String dynamism, String instanceName) {
        return Paths.get(EXECUTION_TIME_ROOT_DATA,
                "dynamic",
                problemSize + "-tasks",
                instanceName + "_" + dynamism + "_summary.csv").toString();
    }

    private static void addStaticExecutionTimes(String problemSize, List<String> instances, StringBuilder output) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        for (String instance : instances) {
            String[][] csvData = CsvReader.readCsvFromDirectory(getStaticPath(problemSize, instance));
            statistics.addValue(milliToMin(Double.valueOf(csvData[1][6])));
        }
        output.append(statistics.getMean() + ";" + statistics.getStandardDeviation() + ";");
    }

    private static String getStaticPath(String problemSize, String instanceName) {
        return Paths.get(EXECUTION_TIME_ROOT_DATA,
                "static",
                "pdp_" + problemSize,
                instanceName + "_summary.csv").toString();
    }

    private static String getHeader() {
        return String.format("size;group;mean static;sd static;mean a=0.1;sd a=0.1;mean a=0.25;sd a=0.25;mean a=0.5;" +
                "sd a=0.5;mean a=0.75;sd a=0.75;mean a=1.0;sd a=1.0;mean q=0.9;sd q=0.9;mean q=0.75;sd q=0.75;" +
                "mean q=0.5;sd q=0.5;mean q=0.25;sd q=0.25;mean q=0.0;sd q=0.0");
    }

    private static List<String> getInstances(String problemSize, String problemGroup) {
        List<String> instances = new ArrayList<>();
        String[][] csvData = CsvReader.readCsvFromDirectory(LITERATURE_ROOT_DATA + problemSize + "-tasks.csv");
        for (String[] csvLine : csvData) {
            if (InstanceUtils.group(csvLine[0]) != null && InstanceUtils.group(csvLine[0]).startsWith(problemGroup)) {
                instances.add(csvLine[0]);
            }
        }
        return instances;
    }

    private static double milliToMin(double milli) {
        return milli / 1000.0 / 60.0;
    }

}
