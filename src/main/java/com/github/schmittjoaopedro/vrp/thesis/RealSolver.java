package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.problem.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.io.File;
import java.util.*;

public class RealSolver {

    private Map<Integer, Integer> nodesMap = new HashMap<>();

    private JSONArray geoCodeJSON;

    private JSONArray distanceJSON;

    private JSONArray durationJSON;

    private File geoCodeFile;

    private File distanceFile;

    private File durationFile;

    private double vehiclesCapacity;

    private int numVehicles;

    private double workingDayTime;

    private double serviceTime;

    private boolean boundWorkingDayTimeByMaxTimeWindows;

    private List<RealRequest> realRequestsList = new LinkedList<>();

    private long algTime;

    private Solution solutionBest;

    public void optimize() {
        algTime = System.currentTimeMillis();
        Instance instance = getInstance();
        Solver solver = new Solver(instance, new Random(1), 10000, true, true);
        solver.setPrintConsole(true);
        solver.disableCallCenter();
        solver.init();
        solver.run();
        solutionBest = solver.getSolutionBest();
        algTime = System.currentTimeMillis() - algTime;
        System.out.println("");
    }

    public String getTechnicalSolution() {
        StringBuilder technicalSolution = new StringBuilder();
        technicalSolution.append(String.format("Algorithm time (ms) = %s\n", algTime));
        ArrayList<Double> distances = new ArrayList<>();
        double totalDistances = 0.0;
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            double distance = 0.0;
            for (int j = 1; j < solutionBest.tours.get(i).size(); j++) {
                distance += distanceJSON.getJSONArray(nodesMap.get(solutionBest.tours.get(i).get(j - 1))).getDouble(nodesMap.get(solutionBest.tours.get(i).get(j)));
            }
            distances.add(distance);
            totalDistances += distance;
        }

        technicalSolution.append(String.format(Locale.US, "Optimized durations %.2f (h)\n", (solutionBest.totalCost / 60.0 / 60.0)));
        technicalSolution.append(String.format(Locale.US, "Optimized distances %.2f (km)\n", (totalDistances / 1000.0)));
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            double time = solutionBest.tourCosts.get(i) / 60.0 / 60.0;
            double dist = distances.get(i) / 1000.0;
            technicalSolution.append(String.format(Locale.US, "\nVehicle %d, Time = %.2f (hs), Distance %.2f (km), Route = [\n", i, time, dist));
            String currentGeocode = null;
            for (int j = 0; j < solutionBest.tours.get(i).size(); j++) {
                int routeId = nodesMap.get(solutionBest.tours.get(i).get(j));
                if (!geoCodeJSON.getString(routeId).equals(currentGeocode)) {
                    currentGeocode = geoCodeJSON.getString(routeId);
                    technicalSolution.append(String.format("\t%d -> %s\n", routeId, currentGeocode));
                }
            }
            technicalSolution.append(String.format("]\nRequests: %s\n", StringUtils.join(solutionBest.requestIds.get(i))));
        }
        return technicalSolution.toString();
    }

    public Instance getInstance() {
        Instance instance = new Instance();
        geoCodeJSON = readFile(geoCodeFile);
        distanceJSON = readFile(distanceFile);
        durationJSON = readFile(durationFile);

        instance.name = "Maps scenario";
        instance.vehiclesCapacity = vehiclesCapacity;
        instance.numVehicles = numVehicles;

        // Requests extracted from PDF
        List<Request> realRequests = new ArrayList<>();
        for (RealRequest realRequest : realRequestsList) {
            realRequests.add(createRequest(realRequest.requestId, realRequest.pickupId, realRequest.deliveryId, realRequest.demand));
        }
        List<Request> normalizedRequests = new ArrayList<>();
        int j = 1;
        List<Task> tasks = new ArrayList<>();
        List<Task> pickupTasks = new ArrayList<>();
        List<Task> deliveryTasks = new ArrayList<>();
        for (int i = 0; i < realRequests.size(); i++) {
            normalizedRequests.add(createRequest(i, j++, j++, realRequests.get(i).pickupTask.demand));
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
        instance.depot.twEnd = workingDayTime;
        nodesMap.put(0, 0);

        instance.maxDistance = 0.0;

        // Load distances
        instance.distances = new double[instance.numNodes][instance.numNodes];
        for (int i = 0; i < instance.numNodes; i++) {
            for (int k = 0; k < instance.numNodes; k++) {
                int pkpNode = nodesMap.get(Integer.valueOf(i));
                int dvrNode = nodesMap.get(Integer.valueOf(k));
                instance.distances[i][k] = durationJSON.getJSONArray(pkpNode).getDouble(dvrNode);
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

        if (boundWorkingDayTimeByMaxTimeWindows) {
            instance.depot.twEnd = instance.maxDistance;
        }
        for (int i = 0; i < instance.numTasks; i++) {
            if (boundWorkingDayTimeByMaxTimeWindows) {
                instance.tasks[i].twEnd = instance.maxDistance;
            }
            instance.maxDemand = Math.max(instance.maxDemand, instance.tasks[i].demand);
        }

        return instance;
    }

    public Request createRequest(int requestId, int pkpNodeId, int dvrNodeId, double demand) {
        Request request = new Request();
        request.requestId = requestId;
        request.announceTime = 0.0;
        request.pickupTask = new Task();
        request.pickupTask.nodeId = pkpNodeId;
        request.pickupTask.requestId = requestId;
        request.pickupTask.isPickup = true;
        request.pickupTask.demand = demand;
        request.pickupTask.serviceTime = serviceTime;
        request.pickupTask.twStart = 0.0;
        request.pickupTask.twEnd = workingDayTime;
        request.pickupTask.status = Task.Status.Idle;
        request.deliveryTask = new Task();
        request.deliveryTask.nodeId = dvrNodeId;
        request.deliveryTask.requestId = requestId;
        request.deliveryTask.isDeliver = true;
        request.deliveryTask.demand = -demand;
        request.deliveryTask.serviceTime = serviceTime;
        request.deliveryTask.twStart = 0.0;
        request.deliveryTask.twEnd = workingDayTime;
        request.deliveryTask.status = Task.Status.Idle;
        return request;
    }

    public JSONArray readFile(File file) {
        try {
            String jsonTxt = FileUtils.readFileToString(file, "UTF-8");
            return new JSONArray(jsonTxt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addRealRequest(int pickupId, int deliveryId, double demand) {
        RealRequest realRequest = new RealRequest();
        realRequest.requestId = realRequestsList.size();
        realRequest.pickupId = pickupId;
        realRequest.deliveryId = deliveryId;
        realRequest.demand = demand;
        realRequestsList.add(realRequest);
    }

    public void setGeoCodeFile(File geoCodeFile) {
        this.geoCodeFile = geoCodeFile;
    }

    public void setDistanceFile(File distanceFile) {
        this.distanceFile = distanceFile;
    }

    public void setVehiclesCapacity(double vehiclesCapacity) {
        this.vehiclesCapacity = vehiclesCapacity;
    }

    public void setNumVehicles(int numVehicles) {
        this.numVehicles = numVehicles;
    }

    public void setWorkingDayTime(double workingDayTime) {
        this.workingDayTime = workingDayTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setBoundWorkingDayTimeByMaxTimeWindows(boolean boundWorkingDayTimeByMaxTimeWindows) {
        this.boundWorkingDayTimeByMaxTimeWindows = boundWorkingDayTimeByMaxTimeWindows;
    }

    public void setDurationFile(File durationFile) {
        this.durationFile = durationFile;
    }

    public static class RealRequest {

        public int requestId;

        public int pickupId;

        public int deliveryId;

        public double demand;

    }
}
