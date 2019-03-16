package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("Duplicates")
public class OptimalRequestSolver {

    private List<Request> pickupRequests;

    private Request deliveryRequest;

    private ProblemInstance instance;

    private int bestRoute[];

    private double bestCost;

    public OptimalRequestSolver(int requestId, ProblemInstance instance) {
        this.instance = instance;
        this.pickupRequests = instance.pickups.get(requestId);
        this.deliveryRequest = instance.delivery.get(requestId);
    }

    public void optimize() {
        double currentTime, currentCapacity;
        Set<Request> visitedPickups = new HashSet<>();
        bestRoute = new int[1 + pickupRequests.size() + 1 + 1]; // depot + pickups + delivery + depot
        bestCost = Double.MAX_VALUE;
        int[] currentRoute = bestRoute.clone();
        for (Request request : pickupRequests) {
            currentTime = instance.distances[instance.depot.nodeId][request.nodeId];
            currentTime = Math.max(currentTime, request.twStart);
            currentTime += request.serviceTime;
            currentCapacity = request.demand;
            visitedPickups.add(request);
            currentRoute[0] = instance.depot.nodeId;
            currentRoute[1] = request.nodeId;
            optimizedBranch(request, currentTime, currentCapacity, visitedPickups, currentRoute, 2);
            visitedPickups.remove(request);
        }
    }

    public ArrayList<Integer> getTour() {
        ArrayList<Integer> newTour = new ArrayList<>();
        for (int i : getBestRoute()) {
            newTour.add(i);
        }
        return newTour;
    }

    private void optimizedBranch(Request request, double currentTime, double currentCapacity, Set<Request> visitedPickups, int[] currSequence, int phase) {
        double nextTime, nextCapacity;
        boolean allPickupsProcessed = true;
        for (Request child : pickupRequests) {
            if (!visitedPickups.contains(child)) {
                allPickupsProcessed = false;
                nextTime = currentTime + instance.distances[request.nodeId][child.nodeId];
                nextTime = Math.max(nextTime, child.twStart);
                nextTime += child.serviceTime;
                nextCapacity = currentCapacity + child.demand;
                if (nextTime < child.twEnd && nextCapacity < instance.vehicleCapacity) { // Is feasible route then add to the request
                    visitedPickups.add(child);
                    currSequence[phase] = child.nodeId;
                    optimizedBranch(child, nextTime, nextCapacity, visitedPickups, currSequence, phase + 1);
                    visitedPickups.remove(child);
                }
            }
        }
        if (allPickupsProcessed) {
            nextTime = currentTime + instance.distances[request.nodeId][deliveryRequest.nodeId];
            nextTime = Math.max(nextTime, deliveryRequest.twStart);
            nextTime += deliveryRequest.serviceTime;
            nextCapacity = currentCapacity + deliveryRequest.demand;
            if (nextTime < deliveryRequest.twEnd && nextCapacity < instance.vehicleCapacity && nextTime < bestCost) {
                nextTime += instance.distances[deliveryRequest.nodeId][instance.depot.nodeId];
                currSequence[phase] = deliveryRequest.nodeId;
                currSequence[phase + 1] = instance.depot.nodeId;
                if (nextTime < instance.depot.twEnd && nextTime < bestCost) {
                    bestCost = nextTime;
                    bestRoute = currSequence.clone();
                }
            }
        }
    }

    public int[] getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(int[] bestRoute) {
        this.bestRoute = bestRoute;
    }

    public double getBestCost() {
        return bestCost;
    }

    public void setBestCost(double bestCost) {
        this.bestCost = bestCost;
    }
}
