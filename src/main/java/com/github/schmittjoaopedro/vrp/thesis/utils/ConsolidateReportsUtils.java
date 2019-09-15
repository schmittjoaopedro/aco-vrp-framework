package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.Runner;

import java.nio.file.Paths;
import java.util.Locale;

public class ConsolidateReportsUtils {

    private static final String DIRECTORY = "C:\\Temp\\ALNS TWO-STAGE - preliminary analysis thesis";

    private static String[] dynamic_suffixes = {
            "a_0.1", "a_0.25", "a_0.5", "a_0.75", "a_1.0",
            "q_0_0.1", "q_0_0.25", "q_0_0.5", "q_0_0.75", "q_0_1.0",
    };

    public static String[] instances_100 = {
            "lc101", "lc102", "lc103", "lc104", "lc105", "lc106", "lc107", "lc108", "lc109",
            "lc201", "lc202", "lc203", "lc204", "lc205", "lc206", "lc207", "lc208",
            "lr101", "lr102", "lr103", "lr104", "lr105", "lr106", "lr107", "lr108", "lr109", "lr110", "lr111", "lr112",
            "lr201", "lr202", "lr203", "lr204", "lr205", "lr206", "lr207", "lr208", "lr209", "lr210", "lr211",
            "lrc101", "lrc102", "lrc103", "lrc104", "lrc105", "lrc106", "lrc107", "lrc108",
            "lrc201", "lrc202", "lrc203", "lrc204", "lrc205", "lrc206", "lrc207", "lrc208"
    };

    public static String[] instances_200 = {
            "LC1_2_1", "LC1_2_2", "LC1_2_3", "LC1_2_4", "LC1_2_5", "LC1_2_6", "LC1_2_7", "LC1_2_8", "LC1_2_9", "LC1_2_10",
            "LC2_2_1", "LC2_2_2", "LC2_2_3", "LC2_2_4", "LC2_2_5", "LC2_2_6", "LC2_2_7", "LC2_2_8", "LC2_2_9", "LC2_2_10",
            "LR1_2_1", "LR1_2_2", "LR1_2_3", "LR1_2_4", "LR1_2_5", "LR1_2_6", "LR1_2_7", "LR1_2_8", "LR1_2_9", "LR1_2_10",
            "LR2_2_1", "LR2_2_2", "LR2_2_3", "LR2_2_4", "LR2_2_5", "LR2_2_6", "LR2_2_7", "LR2_2_8", "LR2_2_9", "LR2_2_10",
            "LRC1_2_1", "LRC1_2_2", "LRC1_2_3", "LRC1_2_4", "LRC1_2_5", "LRC1_2_6", "LRC1_2_7", "LRC1_2_8", "LRC1_2_9", "LRC1_2_10",
            "LRC2_2_1", "LRC2_2_2", "LRC2_2_3", "LRC2_2_4", "LRC2_2_5", "LRC2_2_6", "LRC2_2_7", "LRC2_2_8", "LRC2_2_9", "LRC2_2_10"
    };

    public static String[] instances_400 = {
            "LC1_4_1", "LC1_4_2", "LC1_4_3", "LC1_4_4", "LC1_4_5", "LC1_4_6", "LC1_4_7", "LC1_4_8", "LC1_4_9", "LC1_4_10",
            "LC2_4_1", "LC2_4_2", "LC2_4_3", "LC2_4_4", "LC2_4_5", "LC2_4_6", "LC2_4_7", "LC2_4_8", "LC2_4_9", "LC2_4_10",
            "LR1_4_1", "LR1_4_2", "LR1_4_3", "LR1_4_4", "LR1_4_5", "LR1_4_6", "LR1_4_7", "LR1_4_8", "LR1_4_9", "LR1_4_10",
            "LR2_4_1", "LR2_4_2", "LR2_4_3", "LR2_4_4", "LR2_4_5", "LR2_4_6", "LR2_4_7", "LR2_4_8", "LR2_4_9", "LR2_4_10",
            "LRC1_4_1", "LRC1_4_2", "LRC1_4_3", "LRC1_4_4", "LRC1_4_5", "LRC1_4_6", "LRC1_4_7", "LRC1_4_8", "LRC1_4_9", "LRC1_4_10",
            "LRC2_4_1", "LRC2_4_2", "LRC2_4_3", "LRC2_4_4", "LRC2_4_5", "LRC2_4_6", "LRC2_4_7", "LRC2_4_8", "LRC2_4_9", "LRC2_4_10"
    };

    public static String[] instances_600 = {
            "LC1_6_1", "LC1_6_2", "LC1_6_3", "LC1_6_4", "LC1_6_5", "LC1_6_6", "LC1_6_7", "LC1_6_8", "LC1_6_9", "LC1_6_10",
            "LC2_6_1", "LC2_6_2", "LC2_6_3", "LC2_6_4", "LC2_6_5", "LC2_6_6", "LC2_6_7", "LC2_6_8", "LC2_6_9", "LC2_6_10",
            "LR1_6_1", "LR1_6_2", "LR1_6_3", "LR1_6_4", "LR1_6_5", "LR1_6_6", "LR1_6_7", "LR1_6_8", "LR1_6_9", "LR1_6_10",
            "LR2_6_1", "LR2_6_2", "LR2_6_3", "LR2_6_4", "LR2_6_5", "LR2_6_6", "LR2_6_7", "LR2_6_8", "LR2_6_9", "LR2_6_10",
            "LRC1_6_1", "LRC1_6_2", "LRC1_6_3", "LRC1_6_4", "LRC1_6_5", "LRC1_6_6", "LRC1_6_7", "LRC1_6_8", "LRC1_6_9", "LRC1_6_10",
            "LRC2_6_1", "LRC2_6_2", "LRC2_6_3", "LRC2_6_4", "LRC2_6_5", "LRC2_6_6", "LRC2_6_7", "LRC2_6_8", "LRC2_6_9", "LRC2_6_10"
    };

    public static String[] instances_800 = {
            "LC1_8_1", "LC1_8_2", "LC1_8_3", "LC1_8_4", "LC1_8_5", "LC1_8_6", "LC1_8_7", "LC1_8_8", "LC1_8_9", "LC1_8_10",
            "LC2_8_1", "LC2_8_2", "LC2_8_3", "LC2_8_4", "LC2_8_5", "LC2_8_6", "LC2_8_7", "LC2_8_8", "LC2_8_9", "LC2_8_10",
            "LR1_8_1", "LR1_8_2", "LR1_8_3", "LR1_8_4", "LR1_8_5", "LR1_8_6", "LR1_8_7", "LR1_8_8", "LR1_8_9", "LR1_8_10",
            "LR2_8_1", "LR2_8_2", "LR2_8_3", "LR2_8_4", "LR2_8_5", "LR2_8_6", "LR2_8_7", "LR2_8_8", "LR2_8_9", "LR2_8_10",
            "LRC1_8_1", "LRC1_8_2", "LRC1_8_3", "LRC1_8_4", "LRC1_8_5", "LRC1_8_6", "LRC1_8_7", "LRC1_8_8", "LRC1_8_9", "LRC1_8_10",
            "LRC2_8_1", "LRC2_8_2", "LRC2_8_3", "LRC2_8_4", "LRC2_8_5", "LRC2_8_6", "LRC2_8_7", "LRC2_8_8", "LRC2_8_9", "LRC2_8_10"
    };

    public static String[] instances_1000 = {
            "LC1_10_1", "LC1_10_2", "LC1_10_3", "LC1_10_4", "LC1_10_5", "LC1_10_6", "LC1_10_7", "LC1_10_8", "LC1_10_9", "LC1_10_10",
            "LC2_10_1", "LC2_10_2", "LC2_10_3", "LC2_10_4", "LC2_10_5", "LC2_10_6", "LC2_10_7", "LC2_10_8", "LC2_10_9", "LC2_10_10",
            "LR1_10_1", "LR1_10_2", "LR1_10_3", "LR1_10_4", "LR1_10_5", "LR1_10_6", "LR1_10_7", "LR1_10_8", "LR1_10_9", "LR1_10_10",
            "LR2_10_1", "LR2_10_2", "LR2_10_3", "LR2_10_4", "LR2_10_5", "LR2_10_6", "LR2_10_7", "LR2_10_8", "LR2_10_9", "LR2_10_10",
            "LRC1_10_1", "LRC1_10_2", "LRC1_10_3", "LRC1_10_4", "LRC1_10_5", "LRC1_10_6", "LRC1_10_7", "LRC1_10_8", "LRC1_10_9", "LRC1_10_10",
            "LRC2_10_1", "LRC2_10_2", "LRC2_10_3", "LRC2_10_4", "LRC2_10_5", "LRC2_10_6", "LRC2_10_7", "LRC2_10_10"
    };

    public static void main(String[] args) throws Exception {
        String[] instances = instances_800;
        String[][] benchmarkData = CsvReader.readCsvFromResources("pdptw/800-tasks.csv");
        int numRows = instances.length + 1;
        int numCols = 1 + 3 * (5 + 5); // INSTANCE + (NV + TC + FC) * (URGENCY + A-PRIORI)
        String[][] algorithmData = new String[numRows][numCols];
        // Fill first column
        algorithmData[0][0] = "instance";
        for (int j = 1; j < numRows; j++) {
            algorithmData[j][0] = instances[j - 1];
        }
        // Fill first row
        int k = 1;
        for (int j = 0; j < dynamic_suffixes.length; j++) {
            algorithmData[0][k++] = dynamic_suffixes[j] + "_nv";
            algorithmData[0][k++] = dynamic_suffixes[j] + "_tc";
            algorithmData[0][k++] = dynamic_suffixes[j] + "_fc";
        }
        // Fill body data
        for (int i = 1; i < numRows; i++) {
            if (!algorithmData[i][0].equalsIgnoreCase(benchmarkData[i - 1][0])) {
                throw new NullPointerException("Not sync column");
            }
            String[][] instanceData = CsvReader.readCsvFromDirectory(Paths.get(DIRECTORY, instances[i - 1], instances[i - 1] + ".csv").toString());
            k = 1;
            for (int j = 0; j < dynamic_suffixes.length; j++) {
                algorithmData[i][k] = instanceData[1][k++];
                algorithmData[i][k] = instanceData[1][k++];
                algorithmData[i][k] = instanceData[1][k++];
            }
        }
        // Calculate mean for literature
        double benchmarkNvMean = 0.0;
        double benchmarkTcMean = 0.0;
        for (int i = 0; i < benchmarkData.length; i++) {
            benchmarkNvMean += Double.valueOf(benchmarkData[i][1]);
            benchmarkTcMean += Double.valueOf(benchmarkData[i][2]);
        }
        benchmarkNvMean /= benchmarkData.length;
        benchmarkTcMean /= benchmarkData.length;
        System.out.println("Benchmark NV = " + MathUtils.round(benchmarkNvMean, 2) + " TC = " + MathUtils.round(benchmarkTcMean, 2));
        k = 1;
        for (int j = 0; j < dynamic_suffixes.length; j++) {
            double nvMean = 0.0;
            double tcMean = 0.0;
            double fcMean = 0.0;
            for (int i = 1; i < numRows; i++) {
                nvMean += Double.valueOf(algorithmData[i][k]);
            }
            k++;
            for (int i = 1; i < numRows; i++) {
                tcMean += Double.valueOf(algorithmData[i][k]);
            }
            k++;
            for (int i = 1; i < numRows; i++) {
                fcMean += Double.valueOf(algorithmData[i][k]);
            }
            k++;
            nvMean /= benchmarkData.length;
            tcMean /= benchmarkData.length;
            fcMean /= benchmarkData.length;
            System.out.printf(Locale.US, "Mean for %s, NV = %.2f TC = %.2f and FC = %.2f\n", dynamic_suffixes[j], nvMean, tcMean, fcMean);
            double gapNv = (1.0 - (benchmarkNvMean / nvMean)) * 100.0;
            double gabTc = (1.0 - (benchmarkTcMean / tcMean)) * 100.0;
            double gapFC = fcMean * 100.0;
            System.out.printf(Locale.US, "GAP for %s, NV = %.1f%% TC = %.1f%% and FC = %.1f%%\n\n", dynamic_suffixes[j], gapNv, gabTc, gapFC);
        }
    }
}
