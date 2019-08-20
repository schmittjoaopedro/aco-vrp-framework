package com.github.schmittjoaopedro.google;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.nio.file.Paths;
import java.util.*;

public class RealProblemSolver16 {

    private static final String mapsDirectory;

    private static Map<Integer, Integer> nodesMap = new HashMap<>();

    private static JSONArray geocode;

    private static JSONArray duration;

    private static JSONArray distance;

    private static int WORKING_DAY = (int) (8.8 * 60.0 * 60.0);

    private static int SERVICE_TIME = (int) (10.0 * 60.0);

    static {
        mapsDirectory = Paths.get(RealProblemSolver16.class.getClassLoader().getResource("maps/day16").getFile().substring(1)).toString();
    }

    public static void main(String[] args) {
        Instance instance = getInstance();

        printOriginalRouteCost();

        long algTime = System.currentTimeMillis();

        Solver solver = new Solver(instance, new Random(1), 10000, true, true);
        solver.setPrintConsole(true);
        solver.init();
        solver.run();

        System.out.println("Algorithm time (ms) = " + (System.currentTimeMillis() - algTime));

        Solution solutionBest = solver.getSolutionBest();

        ArrayList<Double> distances = new ArrayList<>();
        double totalDistances = 0.0;
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            double distance = 0.0;
            for (int j = 1; j < solutionBest.tours.get(i).size(); j++) {
                distance += RealProblemSolver16.distance.getJSONArray(nodesMap.get(solutionBest.tours.get(i).get(j - 1))).getDouble(nodesMap.get(solutionBest.tours.get(i).get(j)));
            }
            distances.add(distance);
            totalDistances += distance;
        }

        System.out.println("Original durations " + (solutionBest.totalCost / 60.0 / 60.0));
        System.out.println("Original distances " + (totalDistances / 1000.0));
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            double time = solutionBest.tourCosts.get(i) / 60.0 / 60.0;
            System.out.println("Vehicle " + i + ", Time (hs) = " + Maths.round(time, 2) + ", Distance (km) " + Maths.round(distances.get(i), 2) + ", Route = [");
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
        duration = readFile("duration.json");
        distance = readFile("distance.json");

        instance.name = "Maps scenario";
        instance.vehiclesCapacity = 10000.0;
        instance.numVehicles = 50;

        // Requests extracted from PDF
        int nodeId = 1;
        int requestId = 0;
        List<Request> realRequests = new ArrayList<>();
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.34));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.08));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1.95));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.55));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 3.5));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2.28));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.13));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1.17));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1.67));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 15.6));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.3));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 35.5));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.44));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1.16));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.06));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 7.15));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.2));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2.1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.55));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.59));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 3.96));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 3.81));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2.1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 0.71));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 21));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2.1));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 4.55));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 2));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 43));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 25));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 138));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 7));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 31.8));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 3));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 19.7));
        realRequests.add(createRequest(requestId++, 0, nodeId++, 1));

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
        instance.depot.twEnd = WORKING_DAY;
        nodesMap.put(0, 0);

        instance.maxDistance = 0.0;

        // Load distances
        instance.distances = new double[instance.numNodes][instance.numNodes];
        for (int i = 0; i < instance.numNodes; i++) {
            for (int k = 0; k < instance.numNodes; k++) {
                int pkpNode = nodesMap.get(Integer.valueOf(i));
                int dvrNode = nodesMap.get(Integer.valueOf(k));
                instance.distances[i][k] = duration.getJSONArray(pkpNode).getDouble(dvrNode);
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
        instance.maxDistance = WORKING_DAY;

        //instance.depot.twEnd = instance.maxDistance;
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
        request.pickupTask.serviceTime = SERVICE_TIME;
        request.pickupTask.twStart = 0.0;
        request.pickupTask.twEnd = WORKING_DAY; // 12.5 hs converted to seconds
        request.deliveryTask = new Task();
        request.deliveryTask.nodeId = dvrNodeId;
        request.deliveryTask.requestId = requestId;
        request.deliveryTask.isDeliver = true;
        request.deliveryTask.demand = demand;
        request.deliveryTask.serviceTime = SERVICE_TIME;
        request.deliveryTask.twStart = 0.0;
        request.deliveryTask.twEnd = WORKING_DAY; // 12.5 hs converted to seconds
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

    public static void printOriginalRouteCost() {
        System.out.println("\n============ CLIENT ROUTE ============");
        List<List<Integer>> routes = new ArrayList<>();
        List<Double> durations = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        routes.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 0));
        routes.add(Arrays.asList(0, 29, 30, 31, 32, 33, 34, 35, 0));
        routes.add(Arrays.asList(0, 36, 37, 38, 39, 40, 0));
        double totalDuration = 0.0;
        double totalDistances = 0.0;
        for (int i = 0; i < routes.size(); i++) {
            double duration = 0.0;
            double distance = 0.0;
            for (int j = 1; j < routes.get(i).size(); j++) {
                duration += RealProblemSolver16.duration.getJSONArray(routes.get(i).get(j - 1)).getDouble(routes.get(i).get(j));
                distance += RealProblemSolver16.distance.getJSONArray(routes.get(i).get(j - 1)).getDouble(routes.get(i).get(j));
            }
            durations.add(duration);
            distances.add(distance);
            totalDuration += duration;
            totalDistances += distance;
        }
        System.out.println("Original durations " + (totalDuration / 60.0 / 60.0));
        System.out.println("Original distances " + (totalDistances / 1000.0));
        for (int i = 0; i < routes.size(); i++) {
            double time = durations.get(i) / 60.0 / 60.0;
            double distance = distances.get(i) / 1000.0;
            System.out.println("Vehicle " + i + ", Time (hs) = " + Maths.round(time, 2) + ", Distance (km) " + Maths.round(distance, 2) + ", Route = [");
            for (int j = 0; j < routes.get(i).size(); j++) {
                int routeId = Integer.valueOf(String.valueOf(routes.get(i).get(j)));
                System.out.println("\t" + routeId + " -> " + geocode.getString(routeId));
            }
            System.out.println("");
        }
        System.out.println("\n============ ALNS ROUTE ============");
    }

}
