package com.github.schmittjoaopedro.vrp.thesis.utils;

import java.nio.file.Paths;
import java.util.Locale;

public class SummaryUtils {

    private static final String DIRECTORY = "C:\\Temp\\UDESC-servers\\results\\static_fix_regret\\pdp_1000";

    public static void main(String[] args) {
        String[] instances = InstanceUtils.instances_1000;
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

}
