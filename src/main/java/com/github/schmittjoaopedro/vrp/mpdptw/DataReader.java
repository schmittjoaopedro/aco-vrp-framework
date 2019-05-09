package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataReader {

    public static ProblemInstance getMpdptwInstance(File file) throws IOException {
        FileInputStream fisTargetFile = new FileInputStream(file);
        return getMpdptwInstance(file.getName(), fisTargetFile);
    }

    public static ProblemInstance getMpdptwInstance(String fileName, InputStream stream) throws IOException {
        ProblemInstance instance = new ProblemInstance();
        instance.setFileName(fileName);
        String[] lineData;
        // Load distances
        Graph graph = new Graph();
        String fileContent[] = IOUtils.toString(stream, "UTF-8").split("\n");
        // In the instance files, the second line represents the depot (here considered id 0) and
        // line 3 represents the first request ID (here considered id 1).
        for (int i = 1; i < fileContent.length; i++) {
            lineData = fileContent[i].split(" ");
            Vertex vertex = new Vertex(i - 1);
            vertex.setX(Double.valueOf(lineData[1]));
            vertex.setY(Double.valueOf(lineData[2]));
            graph.addVertex(vertex);
        }
        double[][] distances = new double[graph.getVertexCount()][graph.getVertexCount()];
        Iterator<Vertex> iter1 = graph.getVertices();
        int count = 0;
        while (iter1.hasNext()) {
            Vertex i = iter1.next();
            Iterator<Vertex> iter2 = graph.getVertices();
            while (iter2.hasNext()) {
                Vertex j = iter2.next();
                if (!i.getId().equals(j.getId())) {
                    Edge edge = new Edge(count++);
                    edge.setFrom(i);
                    i.getAdj().put(j.getId(), edge);
                    edge.setTo(j);
                    edge.setCost(Maths.getEuclideanDistance(i, j));
                    distances[i.getId()][j.getId()] = edge.getCost();
                    graph.addEdge(edge);
                }
            }
        }
        instance.setNumNodes(distances.length);
        instance.setDistances(distances);
        // Load requests
        int numReq = 0;
        List<Request> allRequests = new ArrayList<>();
        for (int i = 1; i < fileContent.length; i++) {
            lineData = fileContent[i].replaceAll("\r", StringUtils.EMPTY).split(" ");
            if (i == 1) {
                instance.setDepot(new Depot());
                instance.getDepot().nodeId = Integer.parseInt(lineData[0]); // Depot start at zero index
                instance.getDepot().x = Double.parseDouble(lineData[1]);
                instance.getDepot().y = Double.parseDouble(lineData[2]);
                instance.getDepot().twStart = Double.parseDouble(lineData[4]);
                instance.getDepot().twEnd = Double.parseDouble(lineData[5]);
            } else {
                Request request = new Request();
                request.nodeId = Integer.parseInt(lineData[0]) + 1;
                request.x = Double.parseDouble(lineData[1]);
                request.y = Double.parseDouble(lineData[2]);
                request.demand = Double.parseDouble(lineData[3]);
                request.twStart = Double.parseDouble(lineData[4]);
                request.twEnd = Double.parseDouble(lineData[5]);
                request.serviceTime = Double.parseDouble(lineData[6]);
                request.isPickup = "0".equals(lineData[7]); // We assume that pickups are marked with zero
                request.isDeliver = !request.isPickup;
                request.requestId = Integer.parseInt(lineData[8]);
                if (request.isDeliver) {
                    numReq++;
                }
                allRequests.add(request);
                instance.getIdleRequests().add(request.requestId);
            }
        }
        // Load requests information
        instance.setRequests(allRequests.toArray(new Request[]{}));
        instance.setNumReq(numReq);
        instance.updateRequestStructures();
        // Load vehicle information
        lineData = fileContent[0].split(" ");
        instance.setNumMaxVehicles(Integer.valueOf(lineData[0]));
        instance.setVehicleCapacity(Double.parseDouble(lineData[1]));
        instance.calculateMaxValues();
        // Create valid edges between nodes
        for (int i = 0; i < instance.getNumNodes(); i++) {
            for (int j = 0; j < instance.getNumNodes(); j++) {
                // Nodes linked with itself aren't valid
                if (i == j) {
                    continue;
                }
                // Depot's linked with deliveries aren't valid
                if (i == instance.getDepot().nodeId && instance.getRequest(j).isDeliver) {
                    continue;
                }
                // Pickups linked with depot aren't valid
                if (j == instance.getDepot().nodeId && instance.getRequest(i).isPickup) {
                    continue;
                }
                // Check if time between two requests is feasible
                if (!instance.isFeasible(i, j)) {
                    continue;
                }
            }
        }
        instance.setCapacityRestricted(false);
        return instance;
    }

    public static ProblemInstance getPdptwInstance(File file) throws IOException {
        ProblemInstance instance = new ProblemInstance();
        instance.setFileName(file.getName());
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
        int noNode = fileContent.length - 1; // Ignore header
        int noReq = noNode - 1; // Ignore depot
        Request[] requests = new Request[noReq];
        int noPickups = 0, noDeliveries = 0;

        // Header information
        lineData = fileContent[0].split("\t");
        instance.setNumMaxVehicles(Integer.valueOf(lineData[0]));
        instance.setVehicleCapacity(Double.valueOf(lineData[1]));
        instance.setNumNodes(noNode);

        // Read pickup information
        double[] xCoords = new double[noNode];
        double[] yCoords = new double[noNode];
        for (int i = 1; i < fileContent.length; i++) {
            lineData = fileContent[i].split("\t");
            if (i == 1) {
                Depot depot = new Depot();
                depot.nodeId = Integer.valueOf(lineData[0]);
                depot.x = Double.valueOf(lineData[1]);
                depot.y = Double.valueOf(lineData[2]);
                depot.twStart = Double.valueOf(lineData[4]);
                depot.twEnd = Double.valueOf(lineData[5]);
                instance.setDepot(depot);
                xCoords[i - 1] = depot.x;
                yCoords[i - 1] = depot.y;
            } else {
                Request request = new Request();
                request.nodeId = Integer.valueOf(lineData[0]);
                request.x = Double.valueOf(lineData[1]);
                request.y = Double.valueOf(lineData[2]);
                request.demand = Double.valueOf(lineData[3]);
                request.twStart = Double.valueOf(lineData[4]);
                request.twEnd = Double.valueOf(lineData[5]);
                request.serviceTime = Double.valueOf(lineData[6]);
                requests[request.nodeId - 1] = request;
                xCoords[i - 1] = request.x;
                yCoords[i - 1] = request.y;
                if (lineData.length == 10) {
                    request.announceTime = Double.valueOf(lineData[9]);
                }
            }
        }
        int reqId = 0;
        for (int i = 2; i < fileContent.length; i++) {
            lineData = fileContent[i].split("\t");
            int nodeId = Integer.valueOf(lineData[0]);
            int deliveryIndex = Integer.valueOf(lineData[8]);
            if (deliveryIndex > 0) {
                noPickups++;
                instance.getIdleRequests().add(reqId);
                requests[nodeId - 1].requestId = reqId;
                requests[nodeId - 1].isPickup = true;
                requests[deliveryIndex - 1].requestId = reqId;
                requests[deliveryIndex - 1].isDeliver = true;
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
        instance.setNumReq(noDeliveries);
        instance.setRequests(requests);
        instance.updateRequestStructures();
        double[][] distances = new double[noNode][noNode];
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances.length; j++) {
                distances[i][j] = Maths.getEuclideanDistance(xCoords[i], xCoords[j], yCoords[i], yCoords[j]);
            }
        }
        instance.setDistances(distances);
        instance.setCapacityRestricted(true);
        return instance;
    }

}
