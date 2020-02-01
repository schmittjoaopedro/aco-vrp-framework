package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.*;

public class DynamicInstancesSummary {

    public static final String DYNAMIC_RESULTS_DIR = "C:\\Temp\\UDESC-servers\\results\\dynamic_output\\";
    private static final String LITERATURE_RESULTS_DIR = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\";
    private static final String[] PROBLEM_SIZES = {"100", "200", "400", "600", "800", "1000"};
    private static final String[] PROBLEM_GROUPS = {"LC1", "LC2", "LR1", "LR2", "LRC1", "LRC2"};
    private static final String[] DISTRIBUTION_GROUPS = {"LC", "LRC", "LR"};

    public static void main(String[] args) {
        calculateGapByLocationDistributionType();
        //calculateGapByProblemSize();
        //printIterationResultsByDynamicsDegree();
        //printIterationTemperatureByDynamicsDegree();
    }

    public static void printIterationResultsByDynamicsDegree() {
        String[] dynamics = {"a_0.1", "a_0.25", "a_0.5", "a_0.75", "a_1.0", "q_0_0.1", "q_0_0.25", "q_0_0.5", "q_0_0.75", "q_0_1.0"};
        Map<String, List<Double>> meanNvs = new HashMap<>();
        Map<String, List<Double>> meanTcs = new HashMap<>();
        for (String dynamic : dynamics) {
            List<String> instancesPath = new ArrayList<>();
            for (String size : PROBLEM_SIZES) {
                for (File file : new File(DYNAMIC_RESULTS_DIR + size + "-tasks").listFiles()) {
                    if (file.getName().contains(dynamic + "_iteration")) {
                        instancesPath.add(file.getAbsolutePath());
                    }
                }
            }
            System.out.println("Processing " + dynamic + " " + instancesPath.size() + " files");
            Double[] nvValues = new Double[24999];
            Double[] tcValues = new Double[24999];
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] = 0.0;
                tcValues[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    nvValues[i - 1] += Double.valueOf(data[i][0]);
                    tcValues[i - 1] += Double.valueOf(data[i][2]);
                }
            }
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] /= instancesPath.size();
                tcValues[i] /= instancesPath.size();
            }
            meanNvs.put(dynamic, Arrays.asList(nvValues));
            meanTcs.put(dynamic, Arrays.asList(tcValues));
        }
        for (String dynamic : dynamics) {
            System.out.printf("NV(%s);", dynamic);
        }
        for (String dynamic : dynamics) {
            System.out.printf("TC(%s);", dynamic);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dynamic : dynamics) {
                System.out.printf(Locale.US, "%.2f;", meanNvs.get(dynamic).get(i));
            }
            for (String dynamic : dynamics) {
                System.out.printf(Locale.US, "%.2f;", meanTcs.get(dynamic).get(i));
            }
            System.out.println("");
        }
    }

    public static void printIterationTemperatureByDynamicsDegree() {
        String[] dynamics = {"a_0.1", "a_0.25", "a_0.5", "a_0.75", "a_1.0", "q_0_0.1", "q_0_0.25", "q_0_0.5", "q_0_0.75", "q_0_1.0"};
        Map<String, List<Double>> meanTemperaturesAlnsNv = new HashMap<>();
        Map<String, List<Double>> meanTemperaturesAlnsTc = new HashMap<>();
        for (String dynamic : dynamics) {
            List<String> instancesPath = new ArrayList<>();
            for (String size : PROBLEM_SIZES) {
                for (File file : new File(DYNAMIC_RESULTS_DIR + size + "-tasks").listFiles()) {
                    if (file.getName().contains(dynamic + "_iteration")) {
                        instancesPath.add(file.getAbsolutePath());
                    }
                }
            }
            System.out.println("Processing " + dynamic + " " + instancesPath.size() + " files");
            Double[] nvValues = new Double[24999];
            Double[] tcValues = new Double[24999];
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] = 0.0;
                tcValues[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    nvValues[i - 1] += Double.valueOf(data[i][14]);
                    tcValues[i - 1] += Double.valueOf(data[i][24]);
                }
            }
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] /= instancesPath.size();
                tcValues[i] /= instancesPath.size();
            }
            meanTemperaturesAlnsNv.put(dynamic, Arrays.asList(nvValues));
            meanTemperaturesAlnsTc.put(dynamic, Arrays.asList(tcValues));
        }
        for (String dynamic : dynamics) {
            System.out.printf("NV(%s);", dynamic);
        }
        for (String dynamic : dynamics) {
            System.out.printf("TC(%s);", dynamic);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dynamic : dynamics) {
                System.out.printf(Locale.US, "%.2f;", meanTemperaturesAlnsNv.get(dynamic).get(i));
            }
            for (String dynamic : dynamics) {
                System.out.printf(Locale.US, "%.2f;", meanTemperaturesAlnsTc.get(dynamic).get(i));
            }
            System.out.println("");
        }
    }

    public static void calculateGapByProblemSize() {
        Map<String, Double> gapsNv = new HashMap<>();
        Map<String, Double> gapsSdNv = new HashMap<>();
        Map<String, Double> gapsTc = new HashMap<>();
        Map<String, Double> gapsSdTc = new HashMap<>();
        Map<String, Integer> gapsCount = new HashMap<>();
        // Literature results
        for (String problemSize : PROBLEM_SIZES) {
            CSVParser literature = CsvReader.readCSV(LITERATURE_RESULTS_DIR + problemSize + "-tasks.csv");
            for (CSVRecord record : literature) {
                String group = problemSize + "_lit";
                gapsNv.put(group, gapsNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(record.get("nv")));
                gapsTc.put(group, gapsTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(record.get("tc")));
                gapsCount.put(group, gapsCount.computeIfAbsent(group, g -> 0) + 1);
            }
        }
        // Dynamic results
        for (String problemSize : PROBLEM_SIZES) {
            for (File file : new File(DYNAMIC_RESULTS_DIR + problemSize + "-tasks\\").listFiles()) {
                String fileName = file.getName();
                if (fileName.contains("summary")) {
                    String group = problemSize + "_" + InstanceUtils.dynamism(fileName);
                    String[][] data = CsvReader.readCsvFromDirectory(file.getPath());
                    gapsNv.put(group, gapsNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][0]));
                    gapsSdNv.put(group, gapsSdNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][1]));
                    gapsTc.put(group, gapsTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][2]));
                    gapsSdTc.put(group, gapsSdTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][3]));
                    gapsCount.put(group, gapsCount.computeIfAbsent(group, g -> 0) + 1);
                }
            }
        }

        gapsNv.forEach((k, v) -> gapsNv.put(k, v / gapsCount.get(k)));
        gapsSdNv.forEach((k, v) -> gapsSdNv.put(k, v / gapsCount.get(k)));
        gapsTc.forEach((k, v) -> gapsTc.put(k, v / gapsCount.get(k)));
        gapsSdTc.forEach((k, v) -> gapsSdTc.put(k, v / gapsCount.get(k)));

        System.out.println("objective;distribution_type;literature;a_0.1;a_0.25;a_0.5;a_0.75;a_1.0;q_0.1;q_0.25;q_0.5;q_0.75;q_1.0");
        for (String g : PROBLEM_SIZES) {
            System.out.printf(Locale.US, "mean_nv;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g, gapsNv.get(g + "_lit"),
                    gapsNv.get(g + "_a_0.1"), gapsNv.get(g + "_a_0.25"), gapsNv.get(g + "_a_0.5"), gapsNv.get(g + "_a_0.75"), gapsNv.get(g + "_a_1.0"),
                    gapsNv.get(g + "_q_0_0.1"), gapsNv.get(g + "_q_0_0.25"), gapsNv.get(g + "_q_0_0.5"), gapsNv.get(g + "_q_0_0.75"), gapsNv.get(g + "_q_0_1.0"));
        }
        for (String g : PROBLEM_SIZES) {
            System.out.printf(Locale.US, "mean_tc;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g, gapsTc.get(g + "_lit"),
                    gapsTc.get(g + "_a_0.1"), gapsTc.get(g + "_a_0.25"), gapsTc.get(g + "_a_0.5"), gapsTc.get(g + "_a_0.75"), gapsTc.get(g + "_a_1.0"),
                    gapsTc.get(g + "_q_0_0.1"), gapsTc.get(g + "_q_0_0.25"), gapsTc.get(g + "_q_0_0.5"), gapsTc.get(g + "_q_0_0.75"), gapsTc.get(g + "_q_0_1.0"));
        }
        for (String g : PROBLEM_SIZES) {
            System.out.printf(Locale.US, "sd_nv;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g,
                    gapsSdNv.get(g + "_a_0.1"), gapsSdNv.get(g + "_a_0.25"), gapsSdNv.get(g + "_a_0.5"), gapsSdNv.get(g + "_a_0.75"), gapsSdNv.get(g + "_a_1.0"),
                    gapsSdNv.get(g + "_q_0_0.1"), gapsSdNv.get(g + "_q_0_0.25"), gapsSdNv.get(g + "_q_0_0.5"), gapsSdNv.get(g + "_q_0_0.75"), gapsSdNv.get(g + "_q_0_1.0"));
        }
        for (String g : PROBLEM_SIZES) {
            System.out.printf(Locale.US, "sd_tc;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g,
                    gapsSdTc.get(g + "_a_0.1"), gapsSdTc.get(g + "_a_0.25"), gapsSdTc.get(g + "_a_0.5"), gapsSdTc.get(g + "_a_0.75"), gapsSdTc.get(g + "_a_1.0"),
                    gapsSdTc.get(g + "_q_0_0.1"), gapsSdTc.get(g + "_q_0_0.25"), gapsSdTc.get(g + "_q_0_0.5"), gapsSdTc.get(g + "_q_0_0.75"), gapsSdTc.get(g + "_q_0_1.0"));
        }
    }

    public static void calculateGapByLocationDistributionType() {
        Map<String, Double> gapsNv = new HashMap<>();
        Map<String, Double> sdNv = new HashMap<>();
        Map<String, Double> gapsTc = new HashMap<>();
        Map<String, Double> sdTc = new HashMap<>();
        Map<String, Integer> gapsCount = new HashMap<>();
        // Literature results
        for (String problemSize : PROBLEM_SIZES) {
            CSVParser literature = CsvReader.readCSV(LITERATURE_RESULTS_DIR + problemSize + "-tasks.csv");
            for (CSVRecord record : literature) {
                String group = InstanceUtils.distributionType(record.get("instance")) + "_lit";
                gapsNv.put(group, gapsNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(record.get("nv")));
                gapsTc.put(group, gapsTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(record.get("tc")));
                sdNv.put(group, 0.0);
                sdTc.put(group, 0.0);
                gapsCount.put(group, gapsCount.computeIfAbsent(group, g -> 0) + 1);
            }
        }
        // Dynamic urgency results
        for (String problemSize : PROBLEM_SIZES) {
            for (File file : new File(DYNAMIC_RESULTS_DIR + problemSize + "-tasks\\").listFiles()) {
                String fileName = file.getName();
                if (fileName.contains("summary")) {
                    String group = InstanceUtils.distributionType(fileName) + "_" + InstanceUtils.dynamism(fileName);
                    String[][] data = CsvReader.readCsvFromDirectory(file.getPath());
                    gapsNv.put(group, gapsNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][0]));
                    gapsTc.put(group, gapsTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][2]));
                    sdNv.put(group, sdNv.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][1]));
                    sdTc.put(group, sdTc.computeIfAbsent(group, g -> 0.0) + Double.valueOf(data[1][3]));
                    gapsCount.put(group, gapsCount.computeIfAbsent(group, g -> 0) + 1);
                }
            }
        }

        gapsNv.forEach((k, v) -> gapsNv.put(k, v / gapsCount.get(k)));
        gapsTc.forEach((k, v) -> gapsTc.put(k, v / gapsCount.get(k)));
        sdNv.forEach((k, v) -> sdNv.put(k, v / gapsCount.get(k)));
        sdTc.forEach((k, v) -> sdTc.put(k, v / gapsCount.get(k)));

        System.out.println("objective;distribution_type;" +
                "literature(u);literature(sd);" +
                "a_0.1(u);a_0.1(sd);" +
                "a_0.25(u);a_0.25(sd);" +
                "a_0.5(u);a_0.5(sd);" +
                "a_0.75(u);a_0.75(sd);" +
                "a_1.0(u);a_1.0(sd);" +
                "q_0.1(u);q_0.1(sd);" +
                "q_0.25(u);q_0.25(sd);" +
                "q_0.5(u);q_0.5(sd);" +
                "q_0.75(u);q_0.75(sd);" +
                "q_1.0(u);q_1.0(sd)");
        for (String g : DISTRIBUTION_GROUPS) {
            System.out.printf(Locale.US, "nv;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g,
                    gapsNv.get(g + "_lit"), sdNv.get(g + "_lit"),
                    gapsNv.get(g + "_a_0.1"), sdNv.get(g + "_a_0.1"),
                    gapsNv.get(g + "_a_0.25"), sdNv.get(g + "_a_0.25"),
                    gapsNv.get(g + "_a_0.5"), sdNv.get(g + "_a_0.5"),
                    gapsNv.get(g + "_a_0.75"), sdNv.get(g + "_a_0.75"),
                    gapsNv.get(g + "_a_1.0"), sdNv.get(g + "_a_1.0"),
                    gapsNv.get(g + "_q_0_0.1"), sdNv.get(g + "_q_0_0.1"),
                    gapsNv.get(g + "_q_0_0.25"), sdNv.get(g + "_q_0_0.25"),
                    gapsNv.get(g + "_q_0_0.5"), sdNv.get(g + "_q_0_0.5"),
                    gapsNv.get(g + "_q_0_0.75"), sdNv.get(g + "_q_0_0.75"),
                    gapsNv.get(g + "_q_0_1.0"), sdNv.get(g + "_q_0_1.0"));
        }
        for (String g : DISTRIBUTION_GROUPS) {
            System.out.printf(Locale.US, "tc;%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;\n", g,
                    gapsTc.get(g + "_lit"), sdTc.get(g + "_lit"),
                    gapsTc.get(g + "_a_0.1"), sdTc.get(g + "_a_0.1"),
                    gapsTc.get(g + "_a_0.25"), sdTc.get(g + "_a_0.25"),
                    gapsTc.get(g + "_a_0.5"), sdTc.get(g + "_a_0.5"),
                    gapsTc.get(g + "_a_0.75"), sdTc.get(g + "_a_0.75"),
                    gapsTc.get(g + "_a_1.0"), sdTc.get(g + "_a_1.0"),
                    gapsTc.get(g + "_q_0_0.1"), sdTc.get(g + "_q_0_0.1"),
                    gapsTc.get(g + "_q_0_0.25"), sdTc.get(g + "_q_0_0.25"),
                    gapsTc.get(g + "_q_0_0.5"), sdTc.get(g + "_q_0_0.5"),
                    gapsTc.get(g + "_q_0_0.75"), sdTc.get(g + "_q_0_0.75"),
                    gapsTc.get(g + "_q_0_1.0"), sdTc.get(g + "_q_0_1.0"));
        }
    }
}
