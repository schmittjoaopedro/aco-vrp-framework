package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.Req;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class DataReader {

    public static ProblemInstance getProblemInstance(File file) throws IOException {
        ProblemInstance instance = new ProblemInstance();
        String[] lineData;
        // Load distances
        Graph graph = new Graph();
        FileInputStream fisTargetFile = new FileInputStream(file);
        String fileContent[] = IOUtils.toString(fisTargetFile, "UTF-8").split("\n");
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
        List<Request> allRequests = new ArrayList<>();
        Map<Integer, List<Request>> pickups = new HashMap<>();
        Map<Integer, Request> delivery = new HashMap<>();
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
                if (request.isPickup) {
                    if (!pickups.containsKey(request.requestId)) {
                        pickups.put(request.requestId, new ArrayList<>());
                    }
                    pickups.get(request.requestId).add(request);
                } else {
                    if (!delivery.containsKey(request.requestId)) {
                        delivery.put(request.requestId, request);
                    }
                }
                allRequests.add(request);
            }
        }
        // Load count information
        instance.setRequests(allRequests.toArray(new Request[]{}));
        instance.setNumReq(delivery.size());
        // Load requests information
        List<Request>[] pickupsArray = new ArrayList[instance.getNumReq()];
        Request[] deliveriesArray = new Request[instance.getNumReq()];
        instance.setPickups(pickupsArray);
        instance.setDelivery(deliveriesArray);
        for (int i = 0; i < instance.getNumReq(); i++) {
            pickupsArray[i] = pickups.get(i);
            deliveriesArray[i] = delivery.get(i);
        }
        // Load vehicle information
        lineData = fileContent[0].split(" ");
        instance.setNumMaxVehicles(Integer.valueOf(lineData[0]));
        instance.setVehicleCapacity(Double.parseDouble(lineData[1]));
        instance.calculateMaxDistance();
        // Create valid edges between nodes
        for (int i = 0; i < instance.getNumNodes(); i++) {
            instance.getNeighbors().add(new ArrayList<>());
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
                instance.getNeighbors().get(i).add(j);
            }
        }
        return instance;
    }

}
