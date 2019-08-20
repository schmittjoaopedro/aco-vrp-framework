package com.github.schmittjoaopedro.google;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.nio.file.Paths;
import java.util.*;

public class RealProblemSolver15 {

    private static final String mapsDirectory;

    private static Map<Integer, Integer> nodesMap = new HashMap<>();

    private static JSONArray geocode;

    private static JSONArray distance;

    static {
        mapsDirectory = Paths.get(RealProblemSolver15.class.getClassLoader().getResource("maps/day15").getFile().substring(1)).toString();
    }

    public static void main(String[] args) {
        Instance instance = getInstance();

        Solver solver = new Solver(instance, new Random(1), 10000, true, true);
        solver.setPrintConsole(true);
        solver.init();
        solver.run();

        Solution solutionBest = solver.getSolutionBest();
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            double time = solutionBest.tourCosts.get(i) / 60.0 / 60.0;
            List<Integer> route = new ArrayList<>();
            System.out.println("Vehicle " + i + ", Time (hs) = " + Maths.round(time, 2) + ", Route = [");
            for (int j = 0; j < solutionBest.tours.get(i).size(); j++) {
                int routeId = nodesMap.get(solutionBest.tours.get(i).get(j));
                System.out.println("\t" + routeId + " -> " + geocode.getString(routeId));
            }
            System.out.println("]\nRequests: " + StringUtils.join(solutionBest.requestIds.get(i)));
            System.out.println("");
        }
    }

    public static Instance getInstance() {
        Instance instance = new Instance();
        geocode = readFile("geocode.json");
        distance = readFile("distance.json");

        instance.name = "Maps scenario";
        instance.vehiclesCapacity = 1000.0;
        instance.numVehicles = 50;

        // Requests extracted from PDF
        List<Request> realRequests = new ArrayList<>();
        realRequests.add(createRequest(0, 0, 1, 0.71));
        realRequests.add(createRequest(1, 0, 2, 7.15));
        realRequests.add(createRequest(2, 3, 4, 1.00));
        realRequests.add(createRequest(3, 0, 5, 12.60));
        realRequests.add(createRequest(4, 3, 6, 2.00));
        realRequests.add(createRequest(5, 7, 8, 2.00));
        realRequests.add(createRequest(6, 9, 10, 80.00));
        realRequests.add(createRequest(7, 11, 12, 30.00));
        realRequests.add(createRequest(8, 13, 14, 11.00));
        realRequests.add(createRequest(9, 12, 11, 1.00));
        realRequests.add(createRequest(10, 15, 11, 1.00));
        realRequests.add(createRequest(11, 16, 11, 1.00));
        realRequests.add(createRequest(12, 17, 18, 10.00));
        realRequests.add(createRequest(13, 0, 19, 0.20));
        realRequests.add(createRequest(14, 0, 20, 3.00));
        realRequests.add(createRequest(15, 21, 22, 37.65));
        realRequests.add(createRequest(16, 23, 24, 60.00));
        realRequests.add(createRequest(17, 25, 26, 53.23));
        realRequests.add(createRequest(18, 3, 27, 1.0));
        realRequests.add(createRequest(19, 28, 11, 1.0));
        realRequests.add(createRequest(20, 29, 11, 1.0));
        realRequests.add(createRequest(21, 30, 11, 1.0));
        realRequests.add(createRequest(22, 31, 11, 1.0));
        realRequests.add(createRequest(23, 10, 11, 1.0));
        realRequests.add(createRequest(24, 32, 11, 1.0));
        realRequests.add(createRequest(25, 33, 11, 1.0));
        realRequests.add(createRequest(26, 34, 11, 1.0));

        List<Request> normalizedRequests = new ArrayList<>();
        int j = 1;
        List<Task> tasks = new ArrayList<>();
        List<Task> pickupTasks = new ArrayList<>();
        List<Task> deliveryTasks = new ArrayList<>();
        for (int i = 0; i < realRequests.size(); i++) {
            normalizedRequests.add(createRequest(i, j++, j++, realRequests.get(i).deliveryTask.demand));
            tasks.add(normalizedRequests.get(i).pickupTask);
            tasks.add(normalizedRequests.get(i).deliveryTask);
            pickupTasks.add(normalizedRequests.get(i).pickupTask);
            deliveryTasks.add(normalizedRequests.get(i).deliveryTask);
            nodesMap.put(normalizedRequests.get(i).pickupTask.nodeId, realRequests.get(i).pickupTask.nodeId);
            nodesMap.put(normalizedRequests.get(i).deliveryTask.nodeId, realRequests.get(i).deliveryTask.nodeId);
        }
        instance.requests = normalizedRequests.toArray(new Request[]{});
        instance.tasks = tasks.toArray(new Task[]{});
        instance.pickupTasks = pickupTasks.toArray(new Task[]{});
        instance.deliveryTasks = deliveryTasks.toArray(new Task[]{});
        instance.numRequests = instance.requests.length;
        instance.numTasks = instance.tasks.length;
        instance.numNodes = instance.tasks.length + 1;

        instance.depot = new Depot();
        instance.depot.nodeId = 0;
        instance.depot.twStart = 0.0;
        instance.depot.twEnd = 45000;
        nodesMap.put(0, 11);

        instance.maxDistance = 0.0;

        // Load distances
        instance.distances = new double[instance.numNodes][instance.numNodes];
        for (int i = 0; i < instance.numNodes; i++) {
            for (int k = 0; k < instance.numNodes; k++) {
                int pkpNode = nodesMap.get(Integer.valueOf(i));
                int dvrNode = nodesMap.get(Integer.valueOf(k));
                instance.distances[i][k] = distance.getJSONArray(pkpNode).getDouble(dvrNode);
            }
        }

        for (int i = 0; i < instance.numRequests; i++) {
            Request req = instance.requests[i];
            double dist = instance.dist(instance.depot.nodeId, req.pickupTask.nodeId);
            dist += instance.serviceTime(req.pickupTask.nodeId);
            dist += instance.dist(req.pickupTask.nodeId, req.deliveryTask.nodeId);
            dist += instance.serviceTime(req.pickupTask.nodeId);
            dist += instance.dist(req.deliveryTask.nodeId, instance.depot.nodeId);
            instance.maxDistance = Math.max(instance.maxDistance, dist + 2.0);
        }

        instance.depot.twEnd = instance.maxDistance;
        for (int i = 0; i < instance.numTasks; i++) {
            instance.tasks[i].twEnd = instance.maxDistance;
            instance.maxDemand = Math.max(instance.maxDemand, instance.tasks[i].demand);
        }

        return instance;
    }

    public static Request createRequest(int requestId, int pkpNodeId, int dvrNodeId, double demand) {
        Request request = new Request();
        request.requestId = requestId;
        request.announceTime = 0.0;
        request.pickupTask = new Task();
        request.pickupTask.nodeId = pkpNodeId;
        request.pickupTask.requestId = requestId;
        request.pickupTask.isPickup = true;
        request.pickupTask.demand = -demand;
        request.pickupTask.serviceTime = 10000;
        request.pickupTask.twStart = 0.0;
        request.pickupTask.twEnd = 45000; // 12.5 hs converted to seconds
        request.deliveryTask = new Task();
        request.deliveryTask.nodeId = dvrNodeId;
        request.deliveryTask.requestId = requestId;
        request.deliveryTask.isDeliver = true;
        request.deliveryTask.demand = demand;
        request.deliveryTask.serviceTime = 10000;
        request.deliveryTask.twStart = 0.0;
        request.deliveryTask.twEnd = 45000; // 12.5 hs converted to seconds
        return request;
    }

    public static JSONArray readFile(String file) {
        try {
            String jsonTxt = FileUtils.readFileToString(Paths.get(mapsDirectory, file).toFile(), "UTF-8");
            return new JSONArray(jsonTxt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
