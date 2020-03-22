package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class StaticInstancesSummary {

    private static final String BASE_LIT_DIR = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\";
    private static final String BASE_DIR = "C:\\Temp\\UDESC-servers\\results\\static_output\\";
    private static final String DIRECTORY = BASE_DIR + "pdp_1000";
    private static final String literatureData = BASE_LIT_DIR + "1000-tasks.csv";
    private static final String[] instances = InstanceUtils.instances_1000;
    private static Map<String, Double> literatureNv = new HashMap<>();
    private static Map<String, Double> literatureTc = new HashMap<>();
    private static final String[] PROBLEM_SIZES = {"100", "200", "400", "600", "800", "1000"};

    public static void main(String[] args) throws Exception {
        //loadLiteratureResults();
        //printBestSoFarResults();
        //printBsfByProblemType();
        //printAverageResults();
        //printBestSoFarGap();
        //printAverageGap();
        //printGapByDistributionType();
        //printGapByTimeWindowsSize();
        printIterationCurvesByProblemSize();
        //printIterationCurvesByProblemType();
        //printCompactedResultsByProblemSizeAndGeographicalDistributionType();
        //printIterationTotalCostCurvesByProblemType();
        //printIterationCostMinimizerTemperatureCurvesByProblemType();

    }

    private static void printIterationCostMinimizerTemperatureCurvesByProblemType() throws Exception {
        System.out.println("Iterations Average Total Cost By Problem Type");
        Map<String, List<Double>> meanNvs = new HashMap<>();
        Map<String, List<Double>> meanTcs = new HashMap<>();
        String distTypes[] = {"LC1", "LR1", "LRC1", "LC2", "LR2", "LRC2"};
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dist : distTypes) {
            Set<String> instancesPath = new HashSet<>();
            for (String size : instSizes) {
                CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + size + "-tasks.csv");
                for (CSVRecord record : literature.getRecords()) {
                    String instanceName = size.equals("100") ? record.get("instance") : StringUtils.upperCase(record.get("instance"));
                    if (instanceName.toUpperCase().startsWith(dist)) {
                        instancesPath.add(Paths.get(BASE_DIR + "pdp_" + size, instanceName + "_iteration.csv").toAbsolutePath().toString());
                    }
                }
            }
            Double[] nvValues = new Double[24999];
            Double[] tcValues = new Double[24999];
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] = 0.0;
                tcValues[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    nvValues[i - 1] += Double.valueOf(data[i][2]);
                    tcValues[i - 1] += Double.valueOf(data[i][24]);
                }
            }
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] /= instancesPath.size();
                tcValues[i] /= instancesPath.size();
            }
            meanNvs.put(dist, Arrays.asList(nvValues));
            meanTcs.put(dist, Arrays.asList(tcValues));
        }
        for (String dist : distTypes) {
            System.out.printf("BSF(%s);LOCAL(%s);", dist, dist);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dist : distTypes) {
                System.out.printf(Locale.US, "%.2f;%.2f;", meanNvs.get(dist).get(i), meanTcs.get(dist).get(i));
            }
            System.out.println("");
        }
    }

    private static void printIterationTotalCostCurvesByProblemType() throws Exception {
        System.out.println("Iterations Average Total Cost By Problem Type");
        Map<String, List<Double>> meanNvs = new HashMap<>();
        Map<String, List<Double>> meanTcs = new HashMap<>();
        String distTypes[] = {"LC1", "LR1", "LRC1", "LC2", "LR2", "LRC2"};
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dist : distTypes) {
            Set<String> instancesPath = new HashSet<>();
            for (String size : instSizes) {
                CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + size + "-tasks.csv");
                for (CSVRecord record : literature.getRecords()) {
                    String instanceName = size.equals("100") ? record.get("instance") : StringUtils.upperCase(record.get("instance"));
                    if (instanceName.toUpperCase().startsWith(dist)) {
                        instancesPath.add(Paths.get(BASE_DIR + "pdp_" + size, instanceName + "_iteration.csv").toAbsolutePath().toString());
                    }
                }
            }
            Double[] nvValues = new Double[24999];
            Double[] tcValues = new Double[24999];
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] = 0.0;
                tcValues[i] = 0.0;
            }
            for (String instance : instancesPath) {
                String data[][] = CsvReader.readCsvFromDirectory(instance);
                for (int i = 1; i < data.length; i++) {
                    nvValues[i - 1] += Double.valueOf(data[i][2]);
                    tcValues[i - 1] += Double.valueOf(data[i][22]);
                }
            }
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] /= instancesPath.size();
                tcValues[i] /= instancesPath.size();
            }
            meanNvs.put(dist, Arrays.asList(nvValues));
            meanTcs.put(dist, Arrays.asList(tcValues));
        }
        for (String dist : distTypes) {
            System.out.printf("BSF(%s);LOCAL(%s);", dist, dist);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dist : distTypes) {
                System.out.printf(Locale.US, "%.2f;%.2f;", meanNvs.get(dist).get(i), meanTcs.get(dist).get(i));
            }
            System.out.println("");
        }
    }

    private static void printCompactedResultsByProblemSizeAndGeographicalDistributionType() throws Exception {
        Map<String, GroupedResult> resultMap = new HashMap<>();
        for (String problemSize : PROBLEM_SIZES) {
            CSVParser literatureData = CsvReader.readCSV(BASE_LIT_DIR + problemSize + "-tasks.csv");
            for (CSVRecord literature : literatureData.getRecords()) {
                // Grouped result
                String geoDistType = InstanceUtils.distributionType(literature.get("instance"));
                String key = problemSize + "-" + geoDistType;
                GroupedResult groupedResult = resultMap.computeIfAbsent(key, k -> new GroupedResult());
                groupedResult.problemSize = problemSize;
                groupedResult.geoDistributionType = geoDistType;
                // Literature
                groupedResult.literatureNv += Double.valueOf(literature.get("nv"));
                groupedResult.literatureTc += Double.valueOf(literature.get("tc"));
                groupedResult.count++;
                // Algorithm
                String instanceName = InstanceUtils.fileName(literature.get("instance"));
                CSVParser algorithmData = CsvReader.readCSV(BASE_DIR + "pdp_" + problemSize + "\\" + instanceName + "_summary.csv");
                CSVRecord algorithm = algorithmData.getRecords().get(0);
                groupedResult.algorithmNvMean += Double.valueOf(algorithm.get("mean_bsf_nv"));
                groupedResult.algorithmNvSd += Double.valueOf(algorithm.get("sd_bsf_nv"));
                groupedResult.algorithmTcMean += Double.valueOf(algorithm.get("mean_bsf_tc"));
                groupedResult.algorithmTcSd += Double.valueOf(algorithm.get("sd_bsf_tc"));
            }
        }
        resultMap.forEach((key, value) -> {
            value.literatureNv /= value.count;
            value.literatureTc /= value.count;
            value.algorithmNvMean /= value.count;
            value.algorithmNvSd /= value.count;
            value.algorithmTcMean /= value.count;
            value.algorithmTcSd /= value.count;
        });
        System.out.println("Size;GeoDistributionType;Literature NV;Algorithm NV Mean;Algorithm NV Sd;Literature TC;Algorithm TC Mean;Algorithm TC Sd");
        GroupedResult overallMean = new GroupedResult();
        for (String problemSize : PROBLEM_SIZES) {
            for (String geoDistType : new String[]{"LC", "LRC", "LR"}) {
                GroupedResult groupedResult = resultMap.get(problemSize + "-" + geoDistType);
                System.out.printf(Locale.US, "%s & %s & %.2f & %.2f$\\pm$%.2f & %.2f & %.2f$\\pm$%.2f \\\\\n",
                        groupedResult.problemSize, groupedResult.geoDistributionType,
                        groupedResult.literatureNv, groupedResult.algorithmNvMean, groupedResult.algorithmNvSd,
                        groupedResult.literatureTc, groupedResult.algorithmTcMean, groupedResult.algorithmTcSd);
                overallMean.literatureNv += groupedResult.literatureNv;
                overallMean.literatureTc += groupedResult.literatureTc;
                overallMean.algorithmNvMean += groupedResult.algorithmNvMean;
                overallMean.algorithmTcMean += groupedResult.algorithmTcMean;
                overallMean.algorithmNvSd += groupedResult.algorithmNvSd;
                overallMean.algorithmTcSd += groupedResult.algorithmTcSd;
                overallMean.count++;
            }
        }
        overallMean.literatureNv /= overallMean.count;
        overallMean.literatureTc /= overallMean.count;
        overallMean.algorithmNvMean /= overallMean.count;
        overallMean.algorithmTcMean /= overallMean.count;
        overallMean.algorithmNvSd /= overallMean.count;
        overallMean.algorithmTcSd /= overallMean.count;
        System.out.printf(Locale.US, "%.2f & %.2f$\\pm$%.2f & %.2f & %.2f$\\pm$%.2f \\\\\n",
                overallMean.literatureNv, overallMean.algorithmNvMean, overallMean.algorithmNvSd,
                overallMean.literatureTc, overallMean.algorithmTcMean, overallMean.algorithmTcSd);
    }

    private static void printBsfByProblemType() throws Exception {
        System.out.println("Iterations Average Cost By Problem Type");
        Map<String, Double> meanNvs = new HashMap<>();
        Map<String, Double> meanTcs = new HashMap<>();
        String distTypes[] = {"LC", "LRC", "LR"};
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        double nvTotal = .0;
        double tcTotal = .0;
        for (String size : instSizes) {
            for (String dist : distTypes) {
                Set<String> instancesPath = new HashSet<>();
                CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + size + "-tasks.csv");
                for (CSVRecord record : literature.getRecords()) {
                    String instanceName = size.equals("100") ? record.get("instance") : StringUtils.upperCase(record.get("instance"));
                    if (InstanceUtils.distributionType(record.get("instance")).equals(dist)) {
                        instancesPath.add(Paths.get(BASE_DIR + "pdp_" + size, instanceName + "_bsf.csv").toAbsolutePath().toString());
                    }
                }
                double nvValue = 0.0;
                double tcValue = 0.0;
                for (String instance : instancesPath) {
                    String data[][] = CsvReader.readCsvFromDirectory(instance);
                    for (int i = 1; i < data.length; i++) {
                        tcValue += Double.valueOf(data[i][0]);
                        nvValue += Double.valueOf(data[i][1]);
                    }
                }
                nvValue /= instancesPath.size();
                tcValue /= instancesPath.size();
                nvTotal += nvValue;
                tcTotal += tcValue;
                meanNvs.put(size + "-" + dist, nvValue);
                meanTcs.put(size + "-" + dist, tcValue);
            }
        }
        for (String size : instSizes) {
            for (String dist : distTypes) {
                System.out.printf(Locale.US, "NV(%s) = (%.2f)\t\tTC(%s) = (%.2f)\n", size + "-" + dist, meanNvs.get(size + "-" + dist),
                        size + "-" + dist, meanTcs.get(size + "-" + dist));
            }
        }
        double numGroups = 18;
        System.out.printf(Locale.US, "Mean NV = %.2f Mean TC = %.2f", (nvTotal / numGroups), (tcTotal / numGroups));
    }

    private static void printIterationCurvesByProblemType() throws Exception {
        System.out.println("Iterations Average Cost By Problem Type");
        Map<String, List<Double>> meanNvs = new HashMap<>();
        Map<String, List<Double>> meanTcs = new HashMap<>();
        String distTypes[] = {"LC1", "LR1", "LRC1", "LC2", "LR2", "LRC2"};
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dist : distTypes) {
            Set<String> instancesPath = new HashSet<>();
            for (String size : instSizes) {
                CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + size + "-tasks.csv");
                for (CSVRecord record : literature.getRecords()) {
                    String instanceName = size.equals("100") ? record.get("instance") : StringUtils.upperCase(record.get("instance"));
                    if (instanceName.toUpperCase().startsWith(dist)) {
                        instancesPath.add(Paths.get(BASE_DIR + "pdp_" + size, instanceName + "_iteration.csv").toAbsolutePath().toString());
                    }
                }
            }
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
            meanNvs.put(dist, Arrays.asList(nvValues));
            meanTcs.put(dist, Arrays.asList(tcValues));
        }
        for (String dist : distTypes) {
            System.out.printf("NV(%s);TC(%s);", dist, dist);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dist : distTypes) {
                System.out.printf(Locale.US, "%.2f;%.2f;", meanNvs.get(dist).get(i), meanTcs.get(dist).get(i));
            }
            System.out.println("");
        }
    }

    private static void printIterationCurvesByProblemSize() throws Exception {
        System.out.println("Iterations Average Cost By Problem Size");
        Map<String, List<Double>> meanNvs = new HashMap<>();
        Map<String, List<Double>> meanTcs = new HashMap<>();
        Map<String, List<Double>> meanLocalTcs = new HashMap<>();
        Map<String, List<Double>> meanTempTcs = new HashMap<>();
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dir : instSizes) {
            CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + dir + "-tasks.csv");
            Set<String> instances = new HashSet<>();
            literatureTc.clear();
            for (CSVRecord record : literature.getRecords()) {
                instances.add(dir.equals("100") ? record.get("instance") : StringUtils.upperCase(record.get("instance")));
            }
            Double[] nvValues = new Double[24999];
            Double[] tcValues = new Double[24999];
            Double[] tcLocalValues = new Double[24999];
            Double[] tcTempValues = new Double[24999];
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] = 0.0;
                tcValues[i] = 0.0;
                tcLocalValues[i] = 0.0;
                tcTempValues[i] = 0.0;
            }
            for (String instance : instances) {
                String data[][] = CsvReader.readCsvFromDirectory(Paths.get(BASE_DIR + "pdp_" + dir, instance + "_iteration.csv").toAbsolutePath().toString());
                for (int i = 1; i < data.length; i++) {
                    nvValues[i - 1] += Double.valueOf(data[i][0]);
                    tcValues[i - 1] += Double.valueOf(data[i][2]);
                    tcLocalValues[i - 1] += Double.valueOf(data[i][22]);
                    tcTempValues[i - 1] += Double.valueOf(data[i][24]);
                }
            }
            for (int i = 0; i < nvValues.length; i++) {
                nvValues[i] /= instances.size();
                tcValues[i] /= instances.size();
                tcLocalValues[i] /= instances.size();
                tcTempValues[i] /= instances.size();
            }
            meanNvs.put(dir, Arrays.asList(nvValues));
            meanTcs.put(dir, Arrays.asList(tcValues));
            meanLocalTcs.put(dir, Arrays.asList(tcLocalValues));
            meanTempTcs.put(dir, Arrays.asList(tcTempValues));
        }
        for (String dir : instSizes) {
            System.out.printf("NV(%s);TC(%s);TC_LOCAL(%s);TEMP(%s);", dir, dir, dir, dir);
        }
        System.out.println("");
        for (int i = 0; i < 24999; i++) {
            for (String dir : instSizes) {
                System.out.printf(Locale.US, "%.2f;%.2f;%.2f;%.2f;", meanNvs.get(dir).get(i), meanTcs.get(dir).get(i),
                        meanLocalTcs.get(dir).get(i), meanTempTcs.get(dir).get(i));
            }
            System.out.println("");
        }
    }

    private static void printGapByTimeWindowsSize() throws Exception {
        System.out.println("Average Gap By Instance Type");
        Map<String, Double> count = new HashMap<>();
        Map<String, Double> litNv = new HashMap<>();
        Map<String, Double> nvs = new HashMap<>();
        Map<String, Double> sdNvs = new HashMap<>();
        Map<String, Double> litTc = new HashMap<>();
        Map<String, Double> tcs = new HashMap<>();
        Map<String, Double> sdTcs = new HashMap<>();
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dir : instSizes) {
            CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + dir + "-tasks.csv");
            literatureNv.clear();
            literatureTc.clear();
            for (CSVRecord record : literature.getRecords()) {
                literatureNv.put(record.get("instance"), Double.valueOf(record.get("nv")));
                literatureTc.put(record.get("instance"), Double.valueOf(record.get("tc")));
            }
            List<String> instances = new ArrayList<>(literatureNv.keySet().stream().map(x -> dir.equals("100") ? x : StringUtils.upperCase(x)).collect(Collectors.toList()));
            for (String instance : instances) {
                String data[][] = CsvReader.readCsvFromDirectory(Paths.get(BASE_DIR + "pdp_" + dir, instance + "_summary.csv").toAbsolutePath().toString());
                //double gapNvMean = 1.0 - literatureNv.get(instance.toLowerCase()) / Double.valueOf(data[1][0]);
                //double gapNvMean = MathUtils.gap(literatureNv.get(instance.toLowerCase()), Double.valueOf(data[1][0]));
                //double gapNvSd = Double.valueOf(data[1][1]) / Double.valueOf(data[1][0]);
                //double gapTcMean = 1.0 - literatureTc.get(instance.toLowerCase()) / Double.valueOf(data[1][2]);
                //double gapTcMean = MathUtils.gap(literatureTc.get(instance.toLowerCase()), Double.valueOf(data[1][2]));
                //double gapTcSd = Double.valueOf(data[1][3]) / Double.valueOf(data[1][2]);
                String prefix;
                if (StringUtils.upperCase(instance).startsWith("LC1") || StringUtils.upperCase(instance).startsWith("LRC1") || StringUtils.upperCase(instance).startsWith("LR1")) {
                    prefix = "Short";
                } else {
                    prefix = "Large";
                }
                /*meanNvs.computeIfAbsent(prefix, key -> new ArrayList<>()).add(gapNvMean);
                sdNvs.computeIfAbsent(prefix, key -> new ArrayList<>()).add(gapNvSd);
                meanTcs.computeIfAbsent(prefix, key -> new ArrayList<>()).add(gapTcMean);
                sdTcs.computeIfAbsent(prefix, key -> new ArrayList<>()).add(gapTcSd);*/

                litNv.computeIfAbsent(prefix, k -> 0.0);
                nvs.computeIfAbsent(prefix, k -> 0.0);
                sdNvs.computeIfAbsent(prefix, k -> 0.0);
                litTc.computeIfAbsent(prefix, k -> 0.0);
                tcs.computeIfAbsent(prefix, k -> 0.0);
                sdTcs.computeIfAbsent(prefix, k -> 0.0);
                count.computeIfAbsent(prefix, k -> 0.0);

                litNv.put(prefix, litNv.get(prefix) + literatureNv.get(instance.toLowerCase()));
                nvs.put(prefix, nvs.get(prefix) + Double.valueOf(data[1][0]));
                sdNvs.put(prefix, sdNvs.get(prefix) + Double.valueOf(data[1][1]));
                litTc.put(prefix, litTc.get(prefix) + literatureTc.get(instance.toLowerCase()));
                tcs.put(prefix, tcs.get(prefix) + Double.valueOf(data[1][2]));
                sdTcs.put(prefix, sdTcs.get(prefix) + Double.valueOf(data[1][3]));
                count.put(prefix, count.get(prefix) + 1.0);

            }
        }
        /*double meanNvShort = meanNvs.get("Short").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdNvShort = sdNvs.get("Short").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanNvLarge = meanNvs.get("Large").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdNvLarge = sdNvs.get("Large").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanTcShort = meanTcs.get("Short").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdTcShort = sdTcs.get("Short").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanTcLarge = meanTcs.get("Large").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdTcLarge = sdTcs.get("Large").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;*/

        double meanNvShort = MathUtils.gap(litNv.get("Short") / count.get("Short"), nvs.get("Short") / count.get("Short"));
        double sdNvShort = (sdNvs.get("Short") / count.get("Short")) / (nvs.get("Short") / count.get("Short"));
        double meanNvLarge = MathUtils.gap(litNv.get("Large") / count.get("Large"), nvs.get("Large") / count.get("Large"));
        double sdNvLarge = (sdNvs.get("Large") / count.get("Large")) / (nvs.get("Large") / count.get("Large"));

        double meanTcShort = MathUtils.gap(litTc.get("Short") / count.get("Short"), tcs.get("Short") / count.get("Short"));
        double sdTcShort = (sdTcs.get("Short") / count.get("Short")) / (tcs.get("Short") / count.get("Short"));
        double meanTcLarge = MathUtils.gap(litTc.get("Large") / count.get("Large"), tcs.get("Large") / count.get("Large"));
        double sdTcLarge = (sdTcs.get("Large") / count.get("Large")) / (tcs.get("Large") / count.get("Large"));

        System.out.printf(Locale.US, "Short = NV(%.4f ±%.4f), TC(%.4f ±%.4f)\n", meanNvShort * 100.0, sdNvShort * 100.0, meanTcShort * 100.0, sdTcShort * 100.0);
        System.out.printf(Locale.US, "Large = NV(%.4f ±%.4f), TC(%.4f ±%.4f)\n", meanNvLarge * 100.0, sdNvLarge * 100.0, meanTcLarge * 100.0, sdTcLarge * 100.0);
    }

    private static void printGapByDistributionType() throws Exception {
        System.out.println("Average Gap By Instance Type");
        Map<String, Double> count = new HashMap<>();
        Map<String, Double> litNv = new HashMap<>();
        Map<String, Double> nvs = new HashMap<>();
        Map<String, Double> sdNvs = new HashMap<>();
        Map<String, Double> litTc = new HashMap<>();
        Map<String, Double> tcs = new HashMap<>();
        Map<String, Double> sdTcs = new HashMap<>();
        String instSizes[] = {"100", "200", "400", "600", "800", "1000"};
        for (String dir : instSizes) {
            CSVParser literature = CsvReader.readCSV(BASE_LIT_DIR + dir + "-tasks.csv");
            literatureNv.clear();
            literatureTc.clear();
            for (CSVRecord record : literature.getRecords()) {
                literatureNv.put(record.get("instance"), Double.valueOf(record.get("nv")));
                literatureTc.put(record.get("instance"), Double.valueOf(record.get("tc")));
            }
            List<String> instances = new ArrayList<>(literatureNv.keySet().stream().map(x -> dir.equals("100") ? x : StringUtils.upperCase(x)).collect(Collectors.toList()));
            for (String instance : instances) {
                String data[][] = CsvReader.readCsvFromDirectory(Paths.get(BASE_DIR + "pdp_" + dir, instance + "_summary.csv").toAbsolutePath().toString());
                //double gapNvMean = 1.0 - literatureNv.get(instance.toLowerCase()) / Double.valueOf(data[1][0]);
                //double gapNvMean = MathUtils.gap(literatureNv.get(instance.toLowerCase()), Double.valueOf(data[1][0]));
                //double gapNvSd = Double.valueOf(data[1][1]) / Double.valueOf(data[1][0]);
                //double gapTcMean = 1.0 - literatureTc.get(instance.toLowerCase()) / Double.valueOf(data[1][2]);
                //double gapTcMean = MathUtils.gap(literatureTc.get(instance.toLowerCase()), Double.valueOf(data[1][2]));
                //double gapTcSd = Double.valueOf(data[1][3]) / Double.valueOf(data[1][2]);
                String prefix;
                if (StringUtils.upperCase(instance).startsWith("LC")) {
                    prefix = "LC";
                } else if (StringUtils.upperCase(instance).startsWith("LRC")) {
                    prefix = "LRC";
                } else {
                    prefix = "LR";
                }
                litNv.computeIfAbsent(prefix, k -> 0.0);
                nvs.computeIfAbsent(prefix, k -> 0.0);
                sdNvs.computeIfAbsent(prefix, k -> 0.0);
                litTc.computeIfAbsent(prefix, k -> 0.0);
                tcs.computeIfAbsent(prefix, k -> 0.0);
                sdTcs.computeIfAbsent(prefix, k -> 0.0);
                count.computeIfAbsent(prefix, k -> 0.0);

                litNv.put(prefix, litNv.get(prefix) + literatureNv.get(instance.toLowerCase()));
                nvs.put(prefix, nvs.get(prefix) + Double.valueOf(data[1][0]));
                sdNvs.put(prefix, sdNvs.get(prefix) + Double.valueOf(data[1][1]));
                litTc.put(prefix, litTc.get(prefix) + literatureTc.get(instance.toLowerCase()));
                tcs.put(prefix, tcs.get(prefix) + Double.valueOf(data[1][2]));
                sdTcs.put(prefix, sdTcs.get(prefix) + Double.valueOf(data[1][3]));
                count.put(prefix, count.get(prefix) + 1.0);
            }
        }
        /*double meanNvLc = nvs.get("LC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdNvLc = sdNvs.get("LC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanNvLrc = nvs.get("LRC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdNvLrc = sdNvs.get("LRC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanNvLr = nvs.get("LR").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdNvLr = sdNvs.get("LR").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanTcLc = tcs.get("LC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdTcLc = sdTcs.get("LC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanTcLrc = tcs.get("LRC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdTcLrc = sdTcs.get("LRC").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double meanTcLr = tcs.get("LR").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;
        double sdTcLr = sdTcs.get("LR").stream().mapToDouble(f -> f.doubleValue()).sum() / 100.0;*/

        double meanNvLc = MathUtils.gap(litNv.get("LC") / count.get("LC"), nvs.get("LC") / count.get("LC"));
        double sdNvLc = (sdNvs.get("LC") / count.get("LC")) / (nvs.get("LC") / count.get("LC"));
        double meanNvLrc = MathUtils.gap(litNv.get("LRC") / count.get("LRC"), nvs.get("LRC") / count.get("LRC"));
        double sdNvLrc = (sdNvs.get("LRC") / count.get("LRC")) / (nvs.get("LRC") / count.get("LRC"));
        double meanNvLr = MathUtils.gap(litNv.get("LR") / count.get("LR"), nvs.get("LR") / count.get("LR"));
        double sdNvLr = (sdNvs.get("LR") / count.get("LR")) / (nvs.get("LR") / count.get("LR"));

        double meanTcLc = MathUtils.gap(litTc.get("LC") / count.get("LC"), tcs.get("LC") / count.get("LC"));
        double sdTcLc = (sdTcs.get("LC") / count.get("LC")) / (tcs.get("LC") / count.get("LC"));
        double meanTcLrc = MathUtils.gap(litTc.get("LRC") / count.get("LRC"), tcs.get("LRC") / count.get("LRC"));
        double sdTcLrc = (sdTcs.get("LRC") / count.get("LRC")) / (tcs.get("LRC") / count.get("LRC"));
        double meanTcLr = MathUtils.gap(litTc.get("LR") / count.get("LR"), tcs.get("LR") / count.get("LR"));
        double sdTcLr = (sdTcs.get("LR") / count.get("LR")) / (tcs.get("LR") / count.get("LR"));

        System.out.printf(Locale.US, "LC = NV(%.4f ±%.4f), TC(%.4f ±%.4f)\n", meanNvLc * 100.0, sdNvLc * 100.0, meanTcLc * 100.0, sdTcLc * 100.0);
        System.out.printf(Locale.US, "LRC = NV(%.4f ±%.4f), TC(%.4f ±%.4f)\n", meanNvLrc * 100.0, sdNvLrc * 100.0, meanTcLrc * 100.0, sdTcLrc * 100.0);
        System.out.printf(Locale.US, "LR = NV(%.4f ±%.4f), TC(%.4f ±%.4f)\n", meanNvLr * 100.0, sdNvLr * 100.0, meanTcLr * 100.0, sdTcLr * 100.0);
    }

    private static void printAverageGap() {
        double litNv = 0.0;
        double litTc = 0.0;
        double meanNv;
        double sdNv;
        double meanTc;
        double sdTc;
        System.out.println("GAP SUMMARY");
        meanNv = 0.0;
        sdNv = 0.0;
        meanTc = 0.0;
        sdTc = 0.0;
        for (String instance : instances) {
            String data[][] = CsvReader.readCsvFromDirectory(Paths.get(DIRECTORY, instance + "_summary.csv").toAbsolutePath().toString());
            //meanNv += 1.0 - literatureNv.get(instance.toLowerCase()) / Double.valueOf(data[1][0]);
            //meanNv += MathUtils.gap(literatureNv.get(instance.toLowerCase()), Double.valueOf(data[1][0]));
            litNv += literatureNv.get(instance.toLowerCase());
            meanNv += Double.valueOf(data[1][0]);
            sdNv += Double.valueOf(data[1][1]) / Double.valueOf(data[1][0]);
            //meanTc += 1.0 - literatureTc.get(instance.toLowerCase()) / Double.valueOf(data[1][2]);
            //meanTc += MathUtils.gap(literatureTc.get(instance.toLowerCase()), Double.valueOf(data[1][2]));
            litTc += literatureTc.get(instance.toLowerCase());
            meanTc += Double.valueOf(data[1][2]);
            sdTc += Double.valueOf(data[1][3]) / Double.valueOf(data[1][2]);
        }
        litNv /= instances.length;
        meanNv /= instances.length;
        sdNv /= instances.length;
        litTc /= instances.length;
        meanTc /= instances.length;
        sdTc /= instances.length;
        meanNv = MathUtils.gap(litNv, meanNv);
        meanTc = MathUtils.gap(litTc, meanTc);
        sdNv *= 100.0;
        sdTc *= 100.0;
        meanNv *= 100.0;
        meanTc *= 100.0;
        System.out.printf(Locale.US, "NV %.2f%% ±%.2f%%, TC = %.2f%% ±%.2f%%\n", meanNv, sdNv, meanTc, sdTc);
    }

    private static void printBestSoFarGap() {
        System.out.println("GAP BSF");
        double litNv = 0.0;
        double meanNv = 0.0;
        double sdNv = 0.0;
        double litTc = 0.0;
        double meanTc = 0.0;
        double sdTc = 0.0;
        double count = 0;
        for (String instance : instances) {
            String data[][] = CsvReader.readCsvFromDirectory(Paths.get(DIRECTORY, instance + "_bsf.csv").toAbsolutePath().toString());
            //meanNv += 1.0 - literatureNv.get(instance.toLowerCase()) / Double.valueOf(data[1][1]);
            //meanTc += 1.0 - literatureTc.get(instance.toLowerCase()) / Double.valueOf(data[1][0]);
            litNv += literatureNv.get(instance.toLowerCase());
            meanNv += Double.valueOf(data[1][1]);
            litTc += literatureTc.get(instance.toLowerCase());
            meanTc += Double.valueOf(data[1][0]);
            count++;
        }
        litNv /= count;
        meanNv /= count;
        litTc /= count;
        meanTc /= count;
        meanNv = MathUtils.gap(litNv, meanNv);
        meanTc = MathUtils.gap(litTc, meanTc);
        meanNv *= 100.0;
        meanTc *= 100.0;
        System.out.printf(Locale.US, "NV %.2f%%, TC = %.2f%%\n", meanNv, meanTc);
    }

    private static void printAverageResults() {
        System.out.println("SUMMARY");
        for (String instance : instances) {
            String data[][] = CsvReader.readCsvFromDirectory(Paths.get(DIRECTORY, instance + "_summary.csv").toAbsolutePath().toString());
            System.out.printf(Locale.US, "%s;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f\n",
                    instance,
                    Double.valueOf(data[1][0]),
                    Double.valueOf(data[1][1]),
                    Double.valueOf(data[1][2]),
                    Double.valueOf(data[1][3]),
                    Double.valueOf(data[1][4]),
                    Double.valueOf(data[1][5]),
                    Double.valueOf(data[1][6]) / 1000,
                    Double.valueOf(data[1][7]) / 1000);
        }
    }

    private static void printBestSoFarResults() {
        System.out.println("BSF");
        for (String instance : instances) {
            String data[][] = CsvReader.readCsvFromDirectory(Paths.get(DIRECTORY, instance + "_bsf.csv").toAbsolutePath().toString());
            System.out.printf(Locale.US, "%s;%.2f;%.2f;%.2f;%.2f\n",
                    instance,
                    Double.valueOf(data[1][0]),
                    Double.valueOf(data[1][1]),
                    Double.valueOf(data[1][2]),
                    Double.valueOf(data[1][3]) / 1000);
        }
    }

    private static void loadLiteratureResults() throws IOException {
        CSVParser literature = CsvReader.readCSV(literatureData);
        for (CSVRecord record : literature.getRecords()) {
            literatureNv.put(record.get("instance"), Double.valueOf(record.get("nv")));
            literatureTc.put(record.get("instance"), Double.valueOf(record.get("tc")));
        }
    }

    static class GroupedResult {
        String problemSize;
        String geoDistributionType;
        double literatureNv = 0.0;
        double literatureTc = 0.0;
        double algorithmNvMean = 0.0;
        double algorithmTcMean = 0.0;
        double algorithmNvSd = 0.0;
        double algorithmTcSd = 0.0;
        int count = 0;
    }
}
