package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CsvGrouperUtils {

    private static final String STATIC_FOLDER = "C:\\Temp\\UDESC-servers\\results\\static_output\\pdp_400\\";
    private static final String DYNAMIC_FOLDER = "C:\\Temp\\UDESC-servers\\results\\dynamic_output\\400-tasks\\400-tasks\\";
    private static final String[] colNames = {"static", "q_0_0.1", "q_0_0.25", "q_0_0.5", "q_0_0.75", "q_0_1.0"};

    public static void main(String[] args) throws Exception {
        //String prefixes[] = {"LC1_4_1", "LC1_4_2", "LC1_4_3", "LC1_4_4", "LC1_4_5", "LC1_4_6", "LC1_4_7", "LC1_4_8", "LC1_4_9", "LC1_4_10"};
        String[] prefixes = new String[10];
        for (int i = 0; i < 10; i++) {
            prefixes[i] = "LR2_4_" + (i + 1);
        }
        double[][] table = new double[colNames.length][24999];
        for (String prefix : prefixes) {
            List<Double>[] data = readTable(prefix);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].size(); j++) {
                    table[i][j] += data[i].get(j);
                }
            }
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] /= prefixes.length;
            }
        }
        print(table);
    }

    private static List<Double>[] readTable(String prefix) throws Exception {
        List<Double>[] table = new ArrayList[colNames.length];
        table[0] = readColumnData("mean_global_nv", STATIC_FOLDER + prefix + "_iteration.csv");
        table[1] = readColumnData("mean_global_nv", DYNAMIC_FOLDER + prefix + "_" + colNames[1] + "_iteration.csv");
        table[2] = readColumnData("mean_global_nv", DYNAMIC_FOLDER + prefix + "_" + colNames[2] + "_iteration.csv");
        table[3] = readColumnData("mean_global_nv", DYNAMIC_FOLDER + prefix + "_" + colNames[3] + "_iteration.csv");
        table[4] = readColumnData("mean_global_nv", DYNAMIC_FOLDER + prefix + "_" + colNames[4] + "_iteration.csv");
        table[5] = readColumnData("mean_global_nv", DYNAMIC_FOLDER + prefix + "_" + colNames[5] + "_iteration.csv");
        return table;
    }

    private static void print(List<Double>[] table) {
        System.out.printf(Locale.US, "iter;%s;%s;%s;%s;%s;%s\n", colNames[0], colNames[1], colNames[2], colNames[3], colNames[4], colNames[5]);
        for (int i = 0; i < table[0].size(); i++) {
            System.out.printf(Locale.US, "%d;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f\n", i,
                    table[0].get(i),
                    table[1].get(i),
                    table[2].get(i),
                    table[3].get(i),
                    table[4].get(i),
                    table[5].get(i));
        }
    }

    private static void print(double[][] table) {
        System.out.printf(Locale.US, "iter;%s;%s;%s;%s;%s;%s\n", colNames[0], colNames[1], colNames[2], colNames[3], colNames[4], colNames[5]);
        for (int i = 0; i < table[0].length; i++) {
            System.out.printf(Locale.US, "%d;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f\n", i,
                    table[0][i],
                    table[1][i],
                    table[2][i],
                    table[3][i],
                    table[4][i],
                    table[5][i]);
        }
    }

    public static List<Double> readColumnData(String column, String filePath) throws Exception {
        List<Double> data = new ArrayList<>();
        CSVParser csvParser = CsvReader.readCSV(filePath);
        for (CSVRecord csvRecord : csvParser.getRecords()) {
            data.add(Double.valueOf(csvRecord.get(column)));
        }
        return data;
    }

}
