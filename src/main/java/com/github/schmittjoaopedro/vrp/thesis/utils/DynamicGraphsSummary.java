package com.github.schmittjoaopedro.vrp.thesis.utils;

import java.io.File;
import java.util.*;

public class DynamicGraphsSummary {

    public static final String DYNAMIC_RESULTS_DIR = "C:\\Temp\\UDESC-servers\\results\\dynamic_output\\";
    public static final String STATIC_RESULTS_DIR = "C:\\Temp\\UDESC-servers\\results\\static_output\\";
    private static final String LITERATURE_RESULTS_DIR = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\";
    private static final String[] PROBLEM_SIZES = {"100", "200", "400", "600", "800", "1000"};
    private static final String[] DISTRIBUTION_TYPES = {"LC", "LR", "LRC"};
    private static final String[] PROBLEM_GROUPS = {"LC1", "LC2", "LR1", "LR2", "LRC1", "LRC2"};

    public static void main(String[] args) throws Exception {
        //printStaticCurvesByProblemDistributionType("NV", STATIC_RESULTS_DIR + "pdp_%s", null);
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.5");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.5");
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.5");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.5");

        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.25");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.75");

        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.25");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.75");

        //printStaticCurvesByProblemDistributionType("NV", STATIC_RESULTS_DIR + "pdp_%s", null);
        //printStaticCurvesByProblemDistributionType("TC", STATIC_RESULTS_DIR + "pdp_%s", null);

        // Low
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.1");
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_1.0");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.1");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_1.0");

        // Medium
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.5");
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.5");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.5");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.5");

        // High
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_1.0");
        //printStaticCurvesByProblemDistributionType("NV", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.1");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "a_1.0");
        //printStaticCurvesByProblemDistributionType("TC", DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.1");

        //printCurvesNvVersusTc(DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.25");
        //printCurvesNvVersusTc(DYNAMIC_RESULTS_DIR + "%s-tasks", "a_0.75");
        //printCurvesNvVersusTc(DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.25");
        //printCurvesNvVersusTc(DYNAMIC_RESULTS_DIR + "%s-tasks", "q_0_0.75");
    }

    public static void printCurvesNvVersusTc(String pathFmt, String dynamic) {
        int numIterations = 24999;
        Map<String, Double[]> objNvMap = new HashMap<>();
        Map<String, Double[]> objTcMap = new HashMap<>();
        for (String distType : DISTRIBUTION_TYPES) {
            List<String> instancesPath = new ArrayList<>();
            for (String size : PROBLEM_SIZES) {
                for (File file : new File(String.format(pathFmt, size)).listFiles()) {
                    String fileName = file.getName();
                    if (fileName.contains("_iteration") && InstanceUtils.distributionType(fileName).equals(distType) && Objects.equals(InstanceUtils.dynamism(fileName), dynamic)) {
                        instancesPath.add(file.getAbsolutePath());
                    }
                }
            }
            System.out.println("Processing " + distType + " and NV and TC");
            Double[] objNv = new Double[numIterations];
            Double[] objTc = new Double[numIterations];
            for (int i = 0; i < numIterations; i++) {
                objNv[i] = 0.0;
                objTc[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    objNv[i - 1] += Double.valueOf(data[i][0]);
                    objTc[i - 1] += Double.valueOf(data[i][2]);
                }
            }
            for (int i = 0; i < numIterations; i++) {
                objNv[i] /= instancesPath.size();
                objTc[i] /= instancesPath.size();
            }
            objNvMap.put(distType, objNv);
            objTcMap.put(distType, objTc);
        }
        for (String group : DISTRIBUTION_TYPES) {
            System.out.printf("%s(NV);%s(TC);", group, group);
        }
        System.out.println();
        for (int i = 0; i < numIterations; i++) {
            for (String group : DISTRIBUTION_TYPES) {
                Double[] objNv = objNvMap.get(group);
                Double[] objTc = objTcMap.get(group);
                System.out.printf(Locale.US, "%.2f;%.2f;", objNv[i], objTc[i]);
            }
            System.out.println();
        }
    }

    public static void printStaticCurvesByProblemDistributionType(String objective, String pathFmt, String dynamic) {
        int numIterations = 24999;
        Map<String, Double[]> objBsfGroups = new HashMap<>();
        Map<String, Double[]> objLocalGroups = new HashMap<>();
        Map<String, Double[]> temperatureGroups = new HashMap<>();
        for (String distType : DISTRIBUTION_TYPES) {
            List<String> instancesPath = new ArrayList<>();
            for (String size : PROBLEM_SIZES) {
                for (File file : new File(String.format(pathFmt, size)).listFiles()) {
                    String fileName = file.getName();
                    if (fileName.contains("_iteration") && InstanceUtils.distributionType(fileName).equals(distType) && Objects.equals(InstanceUtils.dynamism(fileName), dynamic)) {
                        instancesPath.add(file.getAbsolutePath());
                    }
                }
            }
            System.out.println("Processing " + distType + " and " + objective + " files");
            Double[] objBsf = new Double[numIterations];
            Double[] objLocal = new Double[numIterations];
            Double[] temperature = new Double[numIterations];
            for (int i = 0; i < numIterations; i++) {
                objBsf[i] = 0.0;
                objLocal[i] = 0.0;
                temperature[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    if (objective.equals("NV")) {
                        objBsf[i - 1] += Double.valueOf(data[i][0]);
                        objLocal[i - 1] += Double.valueOf(data[i][10]);
                        temperature[i - 1] += Double.valueOf(data[i][14]);
                    } else {
                        objBsf[i - 1] += Double.valueOf(data[i][2]);
                        objLocal[i - 1] += Double.valueOf(data[i][22]);
                        temperature[i - 1] += Double.valueOf(data[i][24]);
                    }
                }
            }
            for (int i = 0; i < numIterations; i++) {
                objBsf[i] /= instancesPath.size();
                objLocal[i] /= instancesPath.size();
                temperature[i] /= instancesPath.size();
            }
            objBsfGroups.put(distType, objBsf);
            objLocalGroups.put(distType, objLocal);
            temperatureGroups.put(distType, temperature);
        }
        for (String group : DISTRIBUTION_TYPES) {
            System.out.printf("%s(BSF);%s(Local);%s(Temperature);", group, group, group);
        }
        System.out.println();
        for (int i = 0; i < numIterations; i++) {
            for (String group : DISTRIBUTION_TYPES) {
                Double[] objBsf = objBsfGroups.get(group);
                Double[] objLocal = objLocalGroups.get(group);
                Double[] temperature = temperatureGroups.get(group);
                System.out.printf(Locale.US, "%.2f;%.2f;%.2f;", objBsf[i], objLocal[i], temperature[i]);
            }
            System.out.println();
        }
    }

    public static void printStaticCurvesByProblemDistributionTypeAndWindowsSize(String objective, String pathFmt, String dynamic) {
        int numIterations = 24999;
        Map<String, Double[]> objBsfGroups = new HashMap<>();
        Map<String, Double[]> objLocalGroups = new HashMap<>();
        Map<String, Double[]> temperatureGroups = new HashMap<>();
        for (String group : PROBLEM_GROUPS) {
            List<String> instancesPath = new ArrayList<>();
            for (String size : PROBLEM_SIZES) {
                for (File file : new File(String.format(pathFmt, size)).listFiles()) {
                    String fileName = file.getName();
                    if (fileName.contains("_iteration") && InstanceUtils.group(fileName).equals(group) && Objects.equals(InstanceUtils.dynamism(fileName), dynamic)) {
                        instancesPath.add(file.getAbsolutePath());
                    }
                }
            }
            System.out.println("Processing " + group + " and " + objective + " files");
            Double[] objBsf = new Double[numIterations];
            Double[] objLocal = new Double[numIterations];
            Double[] temperature = new Double[numIterations];
            for (int i = 0; i < numIterations; i++) {
                objBsf[i] = 0.0;
                objLocal[i] = 0.0;
                temperature[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    if (objective.equals("NV")) {
                        objBsf[i - 1] += Double.valueOf(data[i][0]);
                        objLocal[i - 1] += Double.valueOf(data[i][10]);
                        temperature[i - 1] += Double.valueOf(data[i][14]);
                    } else {
                        objBsf[i - 1] += Double.valueOf(data[i][2]);
                        objLocal[i - 1] += Double.valueOf(data[i][22]);
                        temperature[i - 1] += Double.valueOf(data[i][24]);
                    }
                }
            }
            for (int i = 0; i < numIterations; i++) {
                objBsf[i] /= instancesPath.size();
                objLocal[i] /= instancesPath.size();
                temperature[i] /= instancesPath.size();
            }
            objBsfGroups.put(group, objBsf);
            objLocalGroups.put(group, objLocal);
            temperatureGroups.put(group, temperature);
        }
        for (String group : PROBLEM_GROUPS) {
            System.out.printf("%s(BSF);%s(Local);%s(Temperature);", group, group, group);
        }
        System.out.println();
        for (int i = 0; i < numIterations; i++) {
            for (String group : PROBLEM_GROUPS) {
                Double[] objBsf = objBsfGroups.get(group);
                Double[] objLocal = objLocalGroups.get(group);
                Double[] temperature = temperatureGroups.get(group);
                System.out.printf(Locale.US, "%.2f;%.2f;%.2f;", objBsf[i], objLocal[i], temperature[i]);
            }
            System.out.println();
        }
    }
}
