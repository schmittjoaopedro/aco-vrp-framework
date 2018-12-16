package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Things to do:
 * -> Read points from DVRPTW file format
 * -> Read parcels from DVRPTW file format
 * -> How to create vehicles dynamically?
 * -> Create route solutions using NN strategy
 */
public class RINSIM_ACS_DVRPTW {

    private String problemName;

    private int numVehicles = 0;

    private int vehicleCapacity = 0;

    public RINSIM_ACS_DVRPTW(File problemFile) throws Exception {
        Iterator<String> problemDataLines = Arrays.asList(FileUtils.readFileToString(problemFile, "UTF-8").split("\n")).iterator();
        setProblemName(problemFile);
        setVehicleInformation(problemDataLines);
    }

    private void setVehicleInformation(Iterator<String> problemDataLines) {
        String[] temp;
        while (!problemDataLines.next().contains("VEHICLE")) ;
        problemDataLines.next(); // Skip line with "NUMBER		 CAPACITY";
        temp = getTabulatedData(problemDataLines.next());
        numVehicles = Integer.valueOf(temp[0]);
        vehicleCapacity = Integer.valueOf(temp[1]);
        if (numVehicles == 0 || vehicleCapacity == 0) {
            throw new RuntimeException("No vehicle information");
        }
    }

    private void setProblemName(File problemFile) {
        problemName = problemFile.getName();
        if (problemName.contains(".txt")) {
            problemName = problemName.replace(".txt", StringUtils.EMPTY);
        }
    }

    private String[] getTabulatedData(String line) {
        String temp[] = line.split("\t");
        List<String> normalizedData = new ArrayList<>();
        for (String inf : temp) {
            inf = inf.replace("\n", StringUtils.EMPTY);
            inf = inf.replace("\r", StringUtils.EMPTY);
            inf = inf.trim();
            if (!inf.isEmpty()) {
                normalizedData.add(inf);
            }
        }
        return normalizedData.toArray(new String[]{});
    }

}
