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

    public static void main(String[] args) throws Exception {
        String[] instances = Runner.instances_800;
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
