package com.github.schmittjoaopedro.vrp.thesis.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

public class CsvReader {

    public static String[][] readCsvFromDirectory(String directory) {
        String[][] data = null;
        try {
            String fileContent = FileUtils.readFileToString(Paths.get(directory).toFile(), "UTF-8");
            data = readDataFromString(fileContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static String[][] readCsvFromResources(String path) {
        String[][] data = null;
        try {
            String fileContent = IOUtils.toString(CsvReader.class.getClassLoader().getResourceAsStream(path), "UTF-8");
            data = readDataFromString(fileContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    private static String[][] readDataFromString(String fileContent) {
        String data[][] = null;
        String lines[] = fileContent.replaceAll("\r", StringUtils.EMPTY).split("\n");
        String header[] = lines[0].split(";");
        data = new String[lines.length][header.length];
        data[0] = header;
        for (int i = 1; i < lines.length; i++) {
            data[i] = lines[i].split(";");
        }
        return data;
    }

}
