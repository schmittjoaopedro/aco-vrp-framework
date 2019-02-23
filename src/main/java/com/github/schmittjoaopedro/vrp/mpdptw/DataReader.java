package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataReader {

    public static ProblemInstance getProblemInstance(File file) throws IOException {
        ProblemInstance problemInstance = new ProblemInstance();
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
        problemInstance.distances = new double[graph.getVertexCount()][graph.getVertexCount()];
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
                    problemInstance.distances[i.getId()][j.getId()] = edge.getCost();
                    graph.addEdge(edge);
                }
            }
        }
        problemInstance.noNodes = problemInstance.distances.length;
        // Load requests
        List<Request> allRequests = new ArrayList<>();
        problemInstance.pickups = new HashMap<>();
        problemInstance.delivery = new HashMap<>();
        for (int i = 1; i < fileContent.length; i++) {
            lineData = fileContent[i].split(" ");
            if (i == 1) {
                problemInstance.depot = new Depot();
                problemInstance.depot.nodeId = Integer.parseInt(lineData[0]); // Depot start at zero index
                problemInstance.depot.x = Double.parseDouble(lineData[1]);
                problemInstance.depot.y = Double.parseDouble(lineData[2]);
                problemInstance.depot.twStart = Double.parseDouble(lineData[4]);
                problemInstance.depot.twEnd = Double.parseDouble(lineData[5]);
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
                    if (!problemInstance.pickups.containsKey(request.requestId)) {
                        problemInstance.pickups.put(request.requestId, new ArrayList<>());
                    }
                    problemInstance.pickups.get(request.requestId).add(request);
                } else {
                    if (!problemInstance.delivery.containsKey(request.requestId)) {
                        problemInstance.delivery.put(request.requestId, request);
                    }
                }
                allRequests.add(request);
            }
        }
        problemInstance.requests = allRequests.toArray(new Request[]{});
        problemInstance.noReq = problemInstance.delivery.size();
        // Load vehicle information
        lineData = fileContent[0].split(" ");
        problemInstance.noMaxVehicles = Integer.valueOf(lineData[0]);
        problemInstance.vehicleCapacity = Double.parseDouble(lineData[1]);
        return problemInstance;
    }

}
