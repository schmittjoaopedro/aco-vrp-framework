package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public static Instance getInput(String directory, String fileName) {
        Instance instance = null;
        try {
            List<String> fileLines = FileUtils.readLines(Paths.get(directory, fileName).toFile(), "UTF-8");
            String lineData[] = getLineData(fileLines, 0);
            instance = new Instance();
            instance.initialVehicleNumber = Integer.parseInt(lineData[0]);
            instance.vehicleCapacity = Double.parseDouble(lineData[1]);
            instance.vehicleSpeed = Math.max(1.0, Double.valueOf(lineData[2]));
            ArrayList<Request> requestList = new ArrayList<>();
            for (int i = 1; i < fileLines.size(); i++) {
                lineData = getLineData(fileLines, i);
                if (lineData.length > 0) {
                    Request request = new Request();
                    request.id = Integer.parseInt(lineData[0]);
                    request.xPos = Double.parseDouble(lineData[1]);
                    request.yPos = Double.parseDouble(lineData[2]);
                    request.amount = Double.parseDouble(lineData[3]);
                    request.lowerTime = Double.parseDouble(lineData[4]);
                    request.upperTime = Double.parseDouble(lineData[5]);
                    request.serviceTime = Double.parseDouble(lineData[6]);
                    request.pickup = Integer.parseInt(lineData[7]);
                    request.delivery = Integer.parseInt(lineData[8]);
                    ++instance.requestNumber;
                    requestList.add(request);
                    instance.maxAmount = Math.max(instance.maxAmount, request.amount);
                }
            }
            instance.requestList = requestList.toArray(new Request[]{});
            instance.distances = new double[instance.requestNumber][instance.requestNumber];
            for (int i = 0; i < instance.requestNumber; i++) {
                for (int j = 0; j < instance.requestNumber; j++) {
                    instance.distances[i][j] = Math.sqrt((instance.requestList[i].xPos - instance.requestList[j].xPos) * (instance.requestList[i].xPos - instance.requestList[j].xPos) +
                            (instance.requestList[i].yPos - instance.requestList[j].yPos) * (instance.requestList[i].yPos - instance.requestList[j].yPos));
                    instance.maxDist = Math.max(instance.maxDist, instance.distances[i][j]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return instance;
    }

    private static String[] getLineData(List<String> fileLines, int row) {
        String line = fileLines.get(row);
        line = line.replaceAll("\r", StringUtils.EMPTY);
        line = line.replaceAll("\n", StringUtils.EMPTY);
        return line.split("\t");
    }

}
