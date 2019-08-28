package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static Instance getInstance(File file) throws IOException {
        Instance instance = new Instance();
        instance.name = file.getName().replaceAll(".txt", StringUtils.EMPTY);
        String[] lineData;
        FileInputStream fisTargetFile = new FileInputStream(file);
        String rawContent[] = IOUtils.toString(fisTargetFile, "UTF-8").split("\r\n");
        // Remove invalid last line (normalization step for PDPTW with 200 tasks
        int lastLine = 0;
        while (lastLine < rawContent.length) {
            if ("-1".equals(rawContent[lastLine])) {
                break;
            }
            lastLine++;
        }
        String fileContent[] = new String[lastLine];
        System.arraycopy(rawContent, 0, fileContent, 0, lastLine);
        // Dimensional information
        int numNodes = fileContent.length - 1; // Ignore header
        instance.numNodes = numNodes;
        int numTasks = numNodes - 1; // Ignore depot
        Task[] tasks = new Task[numTasks];
        int noPickups = 0, noDeliveries = 0;

        // Header information
        lineData = fileContent[0].split("\t");
        instance.numVehicles = Integer.valueOf(lineData[0]);
        instance.vehiclesCapacity = Double.valueOf(lineData[1]);
        instance.numTasks = numTasks;

        // Read pickup information
        double[] xCoords = new double[numNodes];
        double[] yCoords = new double[numNodes];
        for (int i = 1; i < fileContent.length; i++) {
            lineData = fileContent[i].split("\t");
            if (i == 1) {
                Depot depot = new Depot();
                depot.nodeId = Integer.valueOf(lineData[0]);
                depot.x = Double.valueOf(lineData[1]);
                depot.y = Double.valueOf(lineData[2]);
                depot.twStart = Double.valueOf(lineData[4]);
                depot.twEnd = Double.valueOf(lineData[5]);
                instance.depot = depot;
                xCoords[i - 1] = depot.x;
                yCoords[i - 1] = depot.y;
            } else {
                Task task = new Task();
                task.status = Task.Status.Idle;
                task.nodeId = Integer.valueOf(lineData[0]);
                task.x = Double.valueOf(lineData[1]);
                task.y = Double.valueOf(lineData[2]);
                task.demand = Double.valueOf(lineData[3]);
                task.twStart = Double.valueOf(lineData[4]);
                task.twEnd = Double.valueOf(lineData[5]);
                task.serviceTime = Double.valueOf(lineData[6]);
                tasks[task.nodeId - 1] = task;
                xCoords[i - 1] = task.x;
                yCoords[i - 1] = task.y;
            }
        }
        instance.tasks = tasks;

        double maxDemand = 0.0;
        int reqId = 0;
        List<Request> requests = new ArrayList<>();
        for (int i = 2; i < fileContent.length; i++) {
            lineData = fileContent[i].split("\t");
            int nodeId = Integer.valueOf(lineData[0]);
            int deliveryIndex = Integer.valueOf(lineData[8]);
            if (deliveryIndex > 0) {
                noPickups++;
                tasks[nodeId - 1].requestId = reqId;
                tasks[nodeId - 1].isPickup = true;
                tasks[deliveryIndex - 1].requestId = reqId;
                tasks[deliveryIndex - 1].isDeliver = true;
                Request request = new Request();
                request.pickupTask = tasks[nodeId - 1];
                request.deliveryTask = tasks[deliveryIndex - 1];
                request.requestId = reqId;
                if (lineData.length == 10) {
                    request.announceTime = Double.valueOf(lineData[9]);
                }
                requests.add(request);
                maxDemand = Math.max(maxDemand, request.pickupTask.demand);
                reqId++;
                if (Integer.valueOf(fileContent[deliveryIndex + 1].split("\t")[7]) != nodeId) {
                    throw new RuntimeException("Pickup " + deliveryIndex + " does not delivery to " + nodeId);
                }
            } else {
                noDeliveries++;
            }
        }
        if (noPickups != noDeliveries) {
            throw new RuntimeException("Number of pickups and deliveries are different");
        }
        instance.maxDemand = maxDemand;
        instance.numRequests = requests.size();
        instance.requests = requests.toArray(new Request[]{});
        instance.pickupTasks = new Task[instance.numRequests];
        instance.deliveryTasks = new Task[instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            instance.pickupTasks[i] = instance.requests[i].pickupTask;
            instance.deliveryTasks[i] = instance.requests[i].deliveryTask;
        }

        double maxDist = 0.0;
        double[][] distances = new double[numNodes][numNodes];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances.length; j++) {
                distances[i][j] = MathUtils.getEuclideanDistance(xCoords[i], xCoords[j], yCoords[i], yCoords[j]);
                maxDist = Math.max(maxDist, distances[i][j]);
            }
        }
        instance.distances = distances;
        instance.maxDistance = maxDist;

        return instance;
    }

}
