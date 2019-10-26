package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConsolidateReportsUtils {

    private static final String resultsDirectory = "C:\\Temp\\UDESC-servers\\results\\dynamic_output\\1000-tasks";
    private static final String literatureData = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdptw\\1000-tasks.csv";
    //private static final String[] groups = {"lc1", "lc2", "lr1", "lr2", "lrc1", "lrc2"};
    private static final String[] groups = {"LC1", "LC2", "LR1", "LR2", "LRC1", "LRC2"};

    public static void main(String[] args) throws Exception {
        calculateBsfStatistics();
        calculateAverageStatistics();
        printFullAverageResults();
    }

    public static void calculateBsfStatistics() throws Exception {
        System.out.println("BSF");
        CSVParser literature = CsvReader.readCSV(literatureData);
        Map<String, Result> resultMap = new HashMap<>();
        // Load literature results data
        for (CSVRecord literatureRecord : literature.getRecords()) {
            String currentInstance = literatureRecord.get("instance");
            Result res = resultMap.computeIfAbsent(currentInstance, curInst -> new Result(curInst));
            res.nv = Double.valueOf(literatureRecord.get("nv"));
            res.tc = Double.valueOf(literatureRecord.get("tc"));
            for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_urgency_suffixes[i] + "_bsf.csv");
                CSVRecord urgRec = resultCSV.getRecords().get(0);
                res.nv_a[i] = Double.valueOf(urgRec.get("nv"));
                res.tc_a[i] = Double.valueOf(urgRec.get("tc"));
                res.fc_a[i] = Double.valueOf(urgRec.get("feasible"));
            }
            for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_apriori_suffixes[i] + "_bsf.csv");
                CSVRecord urgRec = resultCSV.getRecords().get(0);
                res.nv_q[i] = Double.valueOf(urgRec.get("nv"));
                res.tc_q[i] = Double.valueOf(urgRec.get("tc"));
                res.fc_q[i] = Double.valueOf(urgRec.get("feasible"));
            }
        }
        System.out.println("Loaded results");
        for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
            double meanNv = 0.0;
            double meanTc = 0.0;
            double meanFc = 0.0;
            for (Result result : resultMap.values()) {
                meanNv += (1.0 - (result.nv / result.nv_a[i])) * 100.0;
                meanTc += (1.0 - (result.tc / result.tc_a[i])) * 100.0;
                meanFc += result.fc_a[i] * 100.0;
            }
            meanNv /= resultMap.values().size();
            meanTc /= resultMap.values().size();
            meanFc /= resultMap.values().size();
            System.out.printf(Locale.US, "%s, NV = %.1f%%, TC = %.1f%%, FC = %.1f%%\n", InstanceUtils.dynamic_urgency_suffixes[i], meanNv, meanTc, meanFc);
        }
        for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
            double meanNv = 0.0;
            double meanTc = 0.0;
            double meanFc = 0.0;
            for (Result result : resultMap.values()) {
                meanNv += (1.0 - (result.nv / result.nv_q[i])) * 100.0;
                meanTc += (1.0 - (result.tc / result.tc_q[i])) * 100.0;
                meanFc += result.fc_q[i] * 100.0;
            }
            meanNv /= resultMap.values().size();
            meanTc /= resultMap.values().size();
            meanFc /= resultMap.values().size();
            System.out.printf(Locale.US, "%s, NV = %.1f%%, TC = %.1f%%, FC = %.1f%%\n", InstanceUtils.dynamic_apriori_suffixes[i], meanNv, meanTc, meanFc);
        }
    }

    public static void calculateAverageStatistics() throws Exception {
        System.out.println("Average");
        CSVParser literature = CsvReader.readCSV(literatureData);
        Map<String, Result> resultMap = new HashMap<>();
        // Load literature results data
        for (CSVRecord literatureRecord : literature.getRecords()) {
            String currentInstance = literatureRecord.get("instance");
            Result res = resultMap.computeIfAbsent(currentInstance, curInst -> new Result(curInst));
            res.nv = Double.valueOf(literatureRecord.get("nv"));
            res.tc = Double.valueOf(literatureRecord.get("tc"));
            for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_urgency_suffixes[i] + "_summary.csv");
                CSVRecord urgRec = resultCSV.getRecords().get(0);
                res.nv_a[i] = Double.valueOf(urgRec.get("mean_bsf_nv"));
                res.tc_a[i] = Double.valueOf(urgRec.get("mean_bsf_tc"));
                res.fc_a[i] = Double.valueOf(urgRec.get("mean_bsf_fc"));
                res.nv_a_sd[i] = Double.valueOf(urgRec.get("sd_bsf_nv"));
                res.tc_a_sd[i] = Double.valueOf(urgRec.get("sd_bsf_tc"));
                res.fc_a_sd[i] = Double.valueOf(urgRec.get("sd_bsf_fc"));
            }
            for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_apriori_suffixes[i] + "_summary.csv");
                CSVRecord aprioriRec = resultCSV.getRecords().get(0);
                res.nv_q[i] = Double.valueOf(aprioriRec.get("mean_bsf_nv"));
                res.tc_q[i] = Double.valueOf(aprioriRec.get("mean_bsf_tc"));
                res.fc_q[i] = Double.valueOf(aprioriRec.get("mean_bsf_fc"));
                res.nv_q_sd[i] = Double.valueOf(aprioriRec.get("sd_bsf_nv"));
                res.tc_q_sd[i] = Double.valueOf(aprioriRec.get("sd_bsf_tc"));
                res.fc_q_sd[i] = Double.valueOf(aprioriRec.get("sd_bsf_fc"));
            }
        }
        System.out.println("Loaded results");
        for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
            double meanNv = 0.0;
            double meanTc = 0.0;
            double meanFc = 0.0;
            for (Result result : resultMap.values()) {
                meanNv += (1.0 - (result.nv / result.nv_a[i])) * 100.0;
                meanTc += (1.0 - (result.tc / result.tc_a[i])) * 100.0;
                meanFc += result.fc_a[i] * 100.0;
            }
            meanNv /= resultMap.values().size();
            meanTc /= resultMap.values().size();
            meanFc /= resultMap.values().size();
            System.out.printf(Locale.US, "%s, NV = %.1f%%, TC = %.1f%%, FC = %.1f%%\n", InstanceUtils.dynamic_urgency_suffixes[i], meanNv, meanTc, meanFc);
        }
        for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
            double meanNv = 0.0;
            double meanTc = 0.0;
            double meanFc = 0.0;
            for (Result result : resultMap.values()) {
                meanNv += (1.0 - (result.nv / result.nv_q[i])) * 100.0;
                meanTc += (1.0 - (result.tc / result.tc_q[i])) * 100.0;
                meanFc += result.fc_q[i] * 100.0;
            }
            meanNv /= resultMap.values().size();
            meanTc /= resultMap.values().size();
            meanFc /= resultMap.values().size();
            System.out.printf(Locale.US, "%s, NV = %.1f%%, TC = %.1f%%, FC = %.1f%%\n", InstanceUtils.dynamic_apriori_suffixes[i], meanNv, meanTc, meanFc);
        }
    }

    public static void printFullAverageResults() throws Exception {
        System.out.println("Average");
        CSVParser literature = CsvReader.readCSV(literatureData);
        Map<String, Result> resultMap = new HashMap<>();
        // Load literature results data
        for (CSVRecord literatureRecord : literature.getRecords()) {
            String currentInstance = literatureRecord.get("instance");
            String group = null;
            for (String g : groups) {
                if (currentInstance.toUpperCase().startsWith(g)) {
                    group = g;
                    break;
                }
            }
            Result res = resultMap.computeIfAbsent(group, curInst -> new Result(curInst));
            res.count++;
            res.nv += Double.valueOf(literatureRecord.get("nv"));
            res.tc += Double.valueOf(literatureRecord.get("tc"));
            for (int i = 0; i < InstanceUtils.dynamic_urgency_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_urgency_suffixes[i] + "_summary.csv");
                CSVRecord urgRec = resultCSV.getRecords().get(0);
                res.nv_a[i] += Double.valueOf(urgRec.get("mean_bsf_nv"));
                res.tc_a[i] += Double.valueOf(urgRec.get("mean_bsf_tc"));
                res.fc_a[i] += Double.valueOf(urgRec.get("mean_bsf_fc"));
                res.nv_a_sd[i] += Double.valueOf(urgRec.get("sd_bsf_nv"));
                res.tc_a_sd[i] += Double.valueOf(urgRec.get("sd_bsf_tc"));
                res.fc_a_sd[i] += Double.valueOf(urgRec.get("sd_bsf_fc"));
            }
            for (int i = 0; i < InstanceUtils.dynamic_apriori_suffixes.length; i++) {
                CSVParser resultCSV = CsvReader.readCSV(resultsDirectory + "\\" + currentInstance + "_" + InstanceUtils.dynamic_apriori_suffixes[i] + "_summary.csv");
                CSVRecord aprioriRec = resultCSV.getRecords().get(0);
                res.nv_q[i] += Double.valueOf(aprioriRec.get("mean_bsf_nv"));
                res.tc_q[i] += Double.valueOf(aprioriRec.get("mean_bsf_tc"));
                res.fc_q[i] += Double.valueOf(aprioriRec.get("mean_bsf_fc"));
                res.nv_q_sd[i] += Double.valueOf(aprioriRec.get("sd_bsf_nv"));
                res.tc_q_sd[i] += Double.valueOf(aprioriRec.get("sd_bsf_tc"));
                res.fc_q_sd[i] += Double.valueOf(aprioriRec.get("sd_bsf_fc"));
            }
        }
        for (Result r : resultMap.values()) {
            r.nv /= r.count;
            r.tc /= r.count;
            for (int i = 0; i < 5; i++) {
                r.nv_a[i] /= r.count;
                r.tc_a[i] /= r.count;
                r.fc_a[i] /= r.count;
                r.nv_a_sd[i] /= r.count;
                r.tc_a_sd[i] /= r.count;
                r.fc_a_sd[i] /= r.count;
                r.nv_q[i] /= r.count;
                r.tc_q[i] /= r.count;
                r.fc_q[i] /= r.count;
                r.nv_q_sd[i] /= r.count;
                r.tc_q_sd[i] /= r.count;
                r.fc_q_sd[i] /= r.count;
            }
        }
        System.out.println("Loaded results");
        System.out.println("Instance;NV;TC;NV(a=0.1);TC(a=0.1);FC(a=0.1);NV(a=0.25);TC(a=0.25);FC(a=0.25);NV(a=0.5);TC(a=0.5);FC(a=0.5);NV(a=0.75);TC(a=0.75);FC(a=0.75);" +
                "NV(a=1.0);TC(a=1.0);FC(a=1.0);NV(q=0.1);TC(q=0.1);FC(q=0.1);NV(q=0.25);TC(q=0.25);FC(q=0.25);NV(q=0.5);TC(q=0.5);FC(q=0.5);NV(q=0.75);TC(q=0.75);FC(q=0.75);" +
                "NV(q=1.0);TC(q=1.0);FC(q=1.0);");
        for (String g : groups) {
            Result r = resultMap.get(g);
            System.out.printf(Locale.US, "%s;%.1f;%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;" +
                    "%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;%.1f±%.1f;\n",g,r.nv, r.tc,
                    r.nv_a[0],r.nv_a_sd[0],r.tc_a[0],r.tc_a_sd[0],r.fc_a[0],r.fc_a_sd[0],
                    r.nv_a[1],r.nv_a_sd[1],r.tc_a[1],r.tc_a_sd[1],r.fc_a[1],r.fc_a_sd[1],
                    r.nv_a[2],r.nv_a_sd[2],r.tc_a[2],r.tc_a_sd[2],r.fc_a[2],r.fc_a_sd[2],
                    r.nv_a[3],r.nv_a_sd[3],r.tc_a[3],r.tc_a_sd[3],r.fc_a[3],r.fc_a_sd[3],
                    r.nv_a[4],r.nv_a_sd[4],r.tc_a[4],r.tc_a_sd[4],r.fc_a[4],r.fc_a_sd[4],
                    r.nv_q[0],r.nv_q_sd[0],r.tc_q[0],r.tc_q_sd[0],r.fc_q[0],r.fc_q_sd[0],
                    r.nv_q[1],r.nv_q_sd[1],r.tc_q[1],r.tc_q_sd[1],r.fc_q[1],r.fc_q_sd[1],
                    r.nv_q[2],r.nv_q_sd[2],r.tc_q[2],r.tc_q_sd[2],r.fc_q[2],r.fc_q_sd[2],
                    r.nv_q[3],r.nv_q_sd[3],r.tc_q[3],r.tc_q_sd[3],r.fc_q[3],r.fc_q_sd[3],
                    r.nv_q[4],r.nv_q_sd[4],r.tc_q[4],r.tc_q_sd[4],r.fc_q[4],r.fc_q_sd[4]);
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
