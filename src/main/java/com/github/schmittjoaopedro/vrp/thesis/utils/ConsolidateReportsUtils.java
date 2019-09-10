package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.Runner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

public class ConsolidateReportsUtils {

    private static final String DIRECTORY = "C:\\Temp\\ALNS TWO-STAGE - preliminary analysis thesis";

    private static String[] dynamic_suffixes = {
            "a_0.1", "a_0.25", "a_0.5", "a_0.75", "a_1.0",
            "q_0_0.1", "q_0_0.25", "q_0_0.5", "q_0_0.75", "q_0_1.0",
    };

    public static void main(String[] args) {
        String[] instances = Runner.instances_600;
        int numCols = dynamic_suffixes.length * 2 + 1;
        int numRows = instances.length + 1;
        String[][] consolidatedReport = new String[numRows][numCols];
        for (int i = 1; i < numCols;) {
            int idx = (i - 1) / 2;
            consolidatedReport[0][i] = dynamic_suffixes[idx] + "_nv";
            consolidatedReport[0][i + 1] = dynamic_suffixes[idx] + "_tc";
            i += 2;
        }
        for (int i = 0; i < instances.length; i++) {
            String fileData[] = readFileData(instances[i]);
            consolidatedReport[i][0] = instances[i];
            for (int j = 0; j < fileData.length; j++) {
                consolidatedReport[i][j + 1] = fileData[j];
            }
        }
        for (int i = 0; i < instances.length; i++) {
            System.out.println(StringUtils.join(consolidatedReport[i], ';'));
        }
    }

    public static String[] readFileData(String instance) {
        String parsedData[] = new String[0];
        try {
            String fileContent = FileUtils.readFileToString(Paths.get(DIRECTORY, instance, instance + ".csv").toFile(), "UTF-8");
            String data[] = fileContent.split("\n")[1].split(";");
            parsedData = new String[data.length - 1];
            for (int i = 0; i < parsedData.length; i++) {
                parsedData[i] = data[i + 1];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parsedData;
    }
}
