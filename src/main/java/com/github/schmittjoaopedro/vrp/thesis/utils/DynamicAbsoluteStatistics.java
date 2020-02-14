package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DynamicAbsoluteStatistics {

    private static final String LITERATURE_DIR = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\";
    private static final String DYNAMIC_DIR = "C:\\Temp\\UDESC-servers\\results\\dynamic_output\\";
    private static final String PROBLEM_SIZES[] = {"100", "200", "400", "600", "800", "1000"};

    public static void main(String[] args) throws Exception {
        printBsfAbsoluteResultsByProblemSize();
        //printMeanAbsoluteResultsByProblemSize();
    }


    public static void printBsfAbsoluteResultsByProblemSize() throws Exception {
        System.out.println("Instance;objective;literature;a=0.1;a=0.25;a=0.5;a=0.75;a=1.0;q=0.9;q=0.75;q=0.5;q=0.25;q=0.0");
        for (String problemSize : PROBLEM_SIZES) {
            Map<String, Result> resultByInstance = new HashMap<>();
            CSVParser literatureCsv = CsvReader.readCSV(LITERATURE_DIR + problemSize + "-tasks.csv");
            for (CSVRecord literatureRecord : literatureCsv.getRecords()) {
                Result result = resultByInstance.computeIfAbsent(literatureRecord.get("instance"), inst -> new Result(inst));
                result.nv = Double.valueOf(literatureRecord.get("nv"));
                result.tc = Double.valueOf(literatureRecord.get("tc"));
            }
            for (String instance : resultByInstance.keySet()) {
                Result result = resultByInstance.get(instance);
                for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
                    String dynamism = InstanceUtils.dynamic_urgency_suffixes[i];
                    CSVRecord dynamicResult = CsvReader.readCSV(DYNAMIC_DIR + problemSize + "-tasks\\" + instance + "_" + dynamism + "_bsf.csv").getRecords().get(0);
                    result.nv_a[i] = Double.valueOf(dynamicResult.get("nv"));
                    result.tc_a[i] = Double.valueOf(dynamicResult.get("tc"));
                    result.fc_a[i] = Double.valueOf(dynamicResult.get("feasible"));
                }
                for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
                    String dynamism = InstanceUtils.dynamic_apriori_suffixes[i];
                    CSVRecord dynamicResult = CsvReader.readCSV(DYNAMIC_DIR + problemSize + "-tasks\\" + instance + "_" + dynamism + "_bsf.csv").getRecords().get(0);
                    result.nv_q[i] = Double.valueOf(dynamicResult.get("nv"));
                    result.tc_q[i] = Double.valueOf(dynamicResult.get("tc"));
                    result.fc_q[i] = Double.valueOf(dynamicResult.get("feasible"));
                }
            }
            Result finalResult = new Result("summary");
            resultByInstance.forEach((key, value) -> {
                finalResult.nv += value.nv;
                finalResult.tc += value.tc;
                for (int i = 0; i < finalResult.tc_a.length; i++) {
                    finalResult.tc_a[i] += value.tc_a[i];
                    finalResult.tc_q[i] += value.tc_q[i];
                    finalResult.nv_a[i] += value.nv_a[i];
                    finalResult.nv_q[i] += value.nv_q[i];
                    finalResult.fc_a[i] += value.fc_a[i];
                    finalResult.fc_q[i] += value.fc_q[i];
                }
            });
            int size = resultByInstance.size();
            finalResult.nv /= size;
            finalResult.tc /= size;
            for (int i = 0; i < finalResult.tc_a.length; i++) {
                finalResult.tc_a[i] /= size;
                finalResult.tc_q[i] /= size;
                finalResult.nv_a[i] /= size;
                finalResult.nv_q[i] /= size;
                finalResult.fc_a[i] /= size;
                finalResult.fc_q[i] /= size;
            }
            System.out.printf(Locale.US, "%s & NV & %.2f & %.2f & %.2f & %.2f & %.2f & %.2f \\\\ %.2f & %.2f & %.2f & %.2f & %.2f \\\\\n",
                    problemSize, finalResult.nv,
                    finalResult.nv_a[0],
                    finalResult.nv_a[1],
                    finalResult.nv_a[2],
                    finalResult.nv_a[3],
                    finalResult.nv_a[4],
                    finalResult.nv_q[0],
                    finalResult.nv_q[1],
                    finalResult.nv_q[2],
                    finalResult.nv_q[3],
                    finalResult.nv_q[4]);
            System.out.printf(Locale.US, "%s & TC & %.2f & %.2f & %.2f & %.2f & %.2f & %.2f \\\\ %.2f & %.2f & %.2f & %.2f & %.2f \\\\\n",
                    problemSize, finalResult.tc,
                    finalResult.tc_a[0],
                    finalResult.tc_a[1],
                    finalResult.tc_a[2],
                    finalResult.tc_a[3],
                    finalResult.tc_a[4],
                    finalResult.tc_q[0],
                    finalResult.tc_q[1],
                    finalResult.tc_q[2],
                    finalResult.tc_q[3],
                    finalResult.tc_q[4]);
            System.out.printf(Locale.US, "%s & FC & 1 & %.2f & %.2f & %.2f & %.2f & %.2f \\\\ %.2f & %.2f & %.2f & %.2f & %.2f \\\\\n",
                    problemSize,
                    finalResult.fc_a[0],
                    finalResult.fc_a[1],
                    finalResult.fc_a[2],
                    finalResult.fc_a[3],
                    finalResult.fc_a[4],
                    finalResult.fc_q[0],
                    finalResult.fc_q[1],
                    finalResult.fc_q[2],
                    finalResult.fc_q[3],
                    finalResult.fc_q[4]);
        }
    }


    public static void printMeanAbsoluteResultsByProblemSize() throws Exception {
        System.out.println("Instance;objective;literature;a=0.1;a=0.25;a=0.5;a=0.75;a=1.0;q=0.9;q=0.75;q=0.5;q=0.25;q=0.0");
        for (String problemSize : PROBLEM_SIZES) {
            Map<String, Result> resultByInstance = new HashMap<>();
            CSVParser literatureCsv = CsvReader.readCSV(LITERATURE_DIR + problemSize + "-tasks.csv");
            for (CSVRecord literatureRecord : literatureCsv.getRecords()) {
                Result result = resultByInstance.computeIfAbsent(literatureRecord.get("instance"), inst -> new Result(inst));
                result.nv = Double.valueOf(literatureRecord.get("nv"));
                result.tc = Double.valueOf(literatureRecord.get("tc"));
            }
            for (String instance : resultByInstance.keySet()) {
                Result result = resultByInstance.get(instance);
                for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
                    String dynamism = InstanceUtils.dynamic_urgency_suffixes[i];
                    CSVRecord dynamicResult = CsvReader.readCSV(DYNAMIC_DIR + problemSize + "-tasks\\" + instance + "_" + dynamism + "_summary.csv").getRecords().get(0);
                    result.nv_a[i] = Double.valueOf(dynamicResult.get("mean_bsf_nv"));
                    result.nv_a_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_nv"));
                    result.tc_a[i] = Double.valueOf(dynamicResult.get("mean_bsf_tc"));
                    result.tc_a_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_tc"));
                    result.fc_a[i] = Double.valueOf(dynamicResult.get("mean_bsf_fc"));
                    result.fc_a_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_fc"));
                }
                for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
                    String dynamism = InstanceUtils.dynamic_apriori_suffixes[i];
                    CSVRecord dynamicResult = CsvReader.readCSV(DYNAMIC_DIR + problemSize + "-tasks\\" + instance + "_" + dynamism + "_summary.csv").getRecords().get(0);
                    result.nv_q[i] = Double.valueOf(dynamicResult.get("mean_bsf_nv"));
                    result.nv_q_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_nv"));
                    result.tc_q[i] = Double.valueOf(dynamicResult.get("mean_bsf_tc"));
                    result.tc_q_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_tc"));
                    result.fc_q[i] = Double.valueOf(dynamicResult.get("mean_bsf_fc"));
                    result.fc_q_sd[i] = Double.valueOf(dynamicResult.get("sd_bsf_fc"));
                }
            }
            Result finalResult = new Result("summary");
            resultByInstance.forEach((key, value) -> {
                finalResult.nv += value.nv;
                finalResult.tc += value.tc;
                for (int i = 0; i < finalResult.tc_a.length; i++) {
                    finalResult.tc_a[i] += value.tc_a[i];
                    finalResult.tc_a_sd[i] += value.tc_a_sd[i];
                    finalResult.tc_q[i] += value.tc_q[i];
                    finalResult.tc_q_sd[i] += value.tc_q_sd[i];
                    finalResult.nv_a[i] += value.nv_a[i];
                    finalResult.nv_a_sd[i] += value.nv_a_sd[i];
                    finalResult.nv_q[i] += value.nv_q[i];
                    finalResult.nv_q_sd[i] += value.nv_q_sd[i];
                    finalResult.fc_a[i] += value.fc_a[i];
                    finalResult.fc_a_sd[i] += value.fc_a_sd[i];
                    finalResult.fc_q[i] += value.fc_q[i];
                    finalResult.fc_q_sd[i] += value.fc_q_sd[i];
                }
            });
            int size = resultByInstance.size();
            finalResult.nv /= size;
            finalResult.tc /= size;
            for (int i = 0; i < finalResult.tc_a.length; i++) {
                finalResult.tc_a[i] /= size;
                finalResult.tc_a_sd[i] /= size;
                finalResult.tc_q[i] /= size;
                finalResult.tc_q_sd[i] /= size;
                finalResult.nv_a[i] /= size;
                finalResult.nv_a_sd[i] /= size;
                finalResult.nv_q[i] /= size;
                finalResult.nv_q_sd[i] /= size;
                finalResult.fc_a[i] /= size;
                finalResult.fc_a_sd[i] /= size;
                finalResult.fc_q[i] /= size;
                finalResult.fc_q_sd[i] /= size;
            }
            System.out.printf(Locale.US, "%s;NV;%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f\n",
                    problemSize, finalResult.nv,
                    finalResult.nv_a[0], finalResult.nv_a_sd[0],
                    finalResult.nv_a[1], finalResult.nv_a_sd[1],
                    finalResult.nv_a[2], finalResult.nv_a_sd[2],
                    finalResult.nv_a[3], finalResult.nv_a_sd[3],
                    finalResult.nv_a[4], finalResult.nv_a_sd[4],
                    finalResult.nv_q[0], finalResult.nv_q_sd[0],
                    finalResult.nv_q[1], finalResult.nv_q_sd[1],
                    finalResult.nv_q[2], finalResult.nv_q_sd[2],
                    finalResult.nv_q[3], finalResult.nv_q_sd[3],
                    finalResult.nv_q[4], finalResult.nv_q_sd[4]);
            System.out.printf(Locale.US, "%s;TC;%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f\n",
                    problemSize, finalResult.tc,
                    finalResult.tc_a[0], finalResult.tc_a_sd[0],
                    finalResult.tc_a[1], finalResult.tc_a_sd[1],
                    finalResult.tc_a[2], finalResult.tc_a_sd[2],
                    finalResult.tc_a[3], finalResult.tc_a_sd[3],
                    finalResult.tc_a[4], finalResult.tc_a_sd[4],
                    finalResult.tc_q[0], finalResult.tc_q_sd[0],
                    finalResult.tc_q[1], finalResult.tc_q_sd[1],
                    finalResult.tc_q[2], finalResult.tc_q_sd[2],
                    finalResult.tc_q[3], finalResult.tc_q_sd[3],
                    finalResult.tc_q[4], finalResult.tc_q_sd[4]);
            System.out.printf(Locale.US, "%s;FC;1;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f;%.2f$\\pm$%.2f\n",
                    problemSize,
                    finalResult.fc_a[0], finalResult.fc_a_sd[0],
                    finalResult.fc_a[1], finalResult.fc_a_sd[1],
                    finalResult.fc_a[2], finalResult.fc_a_sd[2],
                    finalResult.fc_a[3], finalResult.fc_a_sd[3],
                    finalResult.fc_a[4], finalResult.fc_a_sd[4],
                    finalResult.fc_q[0], finalResult.fc_q_sd[0],
                    finalResult.fc_q[1], finalResult.fc_q_sd[1],
                    finalResult.fc_q[2], finalResult.fc_q_sd[2],
                    finalResult.fc_q[3], finalResult.fc_q_sd[3],
                    finalResult.fc_q[4], finalResult.fc_q_sd[4]);
        }
    }

    private static class Result {
        private String instanceName;
        private int count;

        private double nv;
        private double tc;

        private double nv_a[] = new double[5];
        private double tc_a[] = new double[5];
        private double fc_a[] = new double[5];

        private double nv_q[] = new double[5];
        private double tc_q[] = new double[5];
        private double fc_q[] = new double[5];

        private double nv_a_sd[] = new double[5];
        private double tc_a_sd[] = new double[5];
        private double fc_a_sd[] = new double[5];

        private double nv_q_sd[] = new double[5];
        private double tc_q_sd[] = new double[5];
        private double fc_q_sd[] = new double[5];

        public Result(String instanceName) {
            this.instanceName = instanceName;
        }
    }
}
