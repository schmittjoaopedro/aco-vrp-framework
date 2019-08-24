package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.*;
import java.util.stream.Collectors;

public class CallCenter {

    private Instance instance;

    private double startTime;

    private double endTime;

    private int dynamicPointer = 0;

    private Request[] dynamicRequests;

    private Map<Integer, Integer> requestLinks = new HashMap<>();

    private Map<Integer, Integer> taskLinks = new HashMap<>();

    public CallCenter(Instance instance, double startTime, double endTime) {
        this.instance = instance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /*
     * Takes the problem instance, and adapt it to the algorithm based on announce time (dynamic instances).
     * To adapt the problem instance, we order the request based on the announce time and re-assign the requestIds
     * and nodeIds in incremental order. The idea is to keep the problem restrictions but increasing the instance
     * requests accordingly the announce time.
     */
    public void loadStaticRequests() {
        // Sort the requests based on the announce time (dynamic requests)
        dynamicRequests = new Request[instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            dynamicRequests[i] = instance.requests[i];
        }
        Arrays.sort(dynamicRequests, Comparator.comparing(Request::getAnnounceTime).thenComparing(Request::getRequestId));

        // Map request and node IDs in incremental order. Besides that, keeps original IDs mapping.
        // This is important to rollback original order at the end of execution.
        int nodeId = 1;
        Request req;
        for (int i = 0; i < dynamicRequests.length; i++) {
            req = dynamicRequests[i];
            requestLinks.put(i, req.requestId);
            taskLinks.put(nodeId, req.pickupTask.nodeId);
            taskLinks.put(nodeId + 1, req.deliveryTask.nodeId);
            req.requestId = i;
            req.pickupTask.requestId = i;
            req.deliveryTask.requestId = i;
            req.pickupTask.nodeId = nodeId;
            req.deliveryTask.nodeId = nodeId + 1;
            nodeId += 2;
        }

        // Reset all structures. At this point all requests are considered unknown
        instance.numRequests = 0;
        instance.requests = new Request[0];
        instance.numTasks = 0;
        instance.tasks = new Task[0];
        instance.maxDistance = 0.0;
        instance.maxDemand = 0.0;
        instance.numNodes = 1; // Keeps only the depot
        instance.distances = new double[0][0];
        instance.deliveryTasks = new Task[0];
        instance.pickupTasks = new Task[0];

        // Load static (a-priori) information
        loadNewRequests(0.0);
    }

    /*
     * Add new requests to the problem based on the announce time compared with the current time.
     */
    public List<Integer> loadNewRequests(double currentTime) {

        // Check for new available requests based on the passing time.
        List<Request> newRequests = new ArrayList<>();
        while (dynamicPointer < dynamicRequests.length) {
            if (getAlgorithmTime(currentTime) >= getProblemTime(dynamicRequests[dynamicPointer].announceTime)) {
                newRequests.add(dynamicRequests[dynamicPointer]);
                dynamicPointer++;
            } else {
                break;
            }
        }

        // If new requests are available
        if (newRequests.size() > 0) {

            // Re-size structures to comport new requests
            instance.numRequests = instance.numRequests + newRequests.size();
            instance.numTasks = instance.numRequests * 2;
            instance.numNodes = instance.numTasks + 1; // Depot
            Request[] requests = new Request[instance.numRequests];
            Task[] pickupTasks = new Task[instance.numRequests];
            Task[] deliveryTasks = new Task[instance.numRequests];
            Task[] allTasks = new Task[instance.numTasks];

            // Add already known request nodes
            for (int i = 0; i < instance.requests.length; i++) {
                requests[i] = instance.requests[i];
                pickupTasks[i] = requests[i].pickupTask;
                deliveryTasks[i] = requests[i].deliveryTask;
                allTasks[requests[i].pickupTask.nodeId - 1] = requests[i].pickupTask;
                allTasks[requests[i].deliveryTask.nodeId - 1] = requests[i].deliveryTask;
                instance.maxDemand = Math.max(instance.maxDemand, pickupTasks[i].demand);
            }

            // Append new requests into the problem (continuing where the static already known requests ended)
            for (int i = instance.requests.length; i < instance.numRequests; i++) {
                requests[i] = newRequests.get(i - instance.requests.length);
                pickupTasks[i] = requests[i].pickupTask;
                deliveryTasks[i] = requests[i].deliveryTask;
                allTasks[requests[i].pickupTask.nodeId - 1] = requests[i].pickupTask;
                allTasks[requests[i].deliveryTask.nodeId - 1] = requests[i].deliveryTask;
                instance.maxDemand = Math.max(instance.maxDemand, pickupTasks[i].demand);
            }
            instance.requests = requests;
            instance.tasks = allTasks;
            instance.pickupTasks = pickupTasks;
            instance.deliveryTasks = deliveryTasks;

            // Update distances matrix. Task ID's are indexed ignoring the depot existence, therefore we must
            // to decrement the node ID in one to find the relative task.
            double maxDist = 0.0;
            double[][] distances = new double[instance.numNodes][instance.numNodes];
            for (int i = 0; i < distances.length; i++) {
                for (int j = 0; j < distances.length; j++) {
                    double xi, xj, yi, yj;
                    if (i == 0) {
                        xi = instance.depot.x;
                        yi = instance.depot.y;
                    } else {
                        xi = instance.tasks[i - 1].x;
                        yi = instance.tasks[i - 1].y;
                    }
                    if (j == 0) {
                        xj = instance.depot.x;
                        yj = instance.depot.y;
                    } else {
                        xj = instance.tasks[j - 1].x;
                        yj = instance.tasks[j - 1].y;
                    }
                    distances[i][j] = MathUtils.getEuclideanDistance(xi, xj, yi, yj);
                    maxDist = Math.max(maxDist, distances[i][j]);
                }
            }
            instance.distances = distances;
            instance.maxDistance = maxDist;
        }
        return newRequests.stream().map(Request::getRequestId).collect(Collectors.toList());
    }

    /*
     * Rollback original ID's to compare the solution with the literature results.
     */
    public void rollbackOriginalInformation(Solution solution) {
        // Convert solution ID's for the original problem ID's
        ArrayList<Integer> tour, requests;
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            requests = solution.requestIds.get(k);
            for (int i = 1; i < tour.size() - 1; i++) {
                tour.set(i, taskLinks.get(tour.get(i)));
            }
            for (int i = 0; i < requests.size(); i++) {
                requests.set(i, requestLinks.get(requests.get(i)));
            }
        }
    }

    private double getAlgorithmTime(double currentTime) {
        return (currentTime - startTime) / (endTime - startTime);
    }

    private double getProblemTime(double currentTime) {
        return (currentTime - instance.depot.twStart) / (instance.depot.twEnd - instance.depot.twStart);
    }

}
