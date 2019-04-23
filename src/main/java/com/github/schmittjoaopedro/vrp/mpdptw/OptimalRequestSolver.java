package com.github.schmittjoaopedro.vrp.mpdptw;

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
        this.pickupRequests = instance.getPickups(requestId);
        this.deliveryRequest = instance.getDelivery(requestId);
    }

    public void optimize() {
        double currentTime, currentCapacity;
        Set<Request> visitedPickups = new HashSet<>();
        bestRoute = new int[1 + pickupRequests.size() + 1 + 1]; // depot + pickups + delivery + depot
        bestCost = Double.MAX_VALUE;
        int[] currentRoute = bestRoute.clone();
        for (Request request : pickupRequests) {
            currentTime = instance.dist(instance.getDepot().nodeId, request.nodeId);
            currentTime = Math.max(currentTime, request.twStart);
            currentTime += request.serviceTime;
            currentCapacity = request.demand;
            visitedPickups.add(request);
            currentRoute[0] = instance.getDepot().nodeId;
            currentRoute[1] = request.nodeId;
            optimizedBranch(request, currentTime, currentCapacity, visitedPickups, currentRoute, 2);
            visitedPickups.remove(request);
        }
    }

    private void optimizedBranch(Request request, double currentTime, double currentCapacity, Set<Request> visitedPickups, int[] currSequence, int phase) {
        double nextTime, nextCapacity;
        boolean allPickupsProcessed = true;
        for (Request child : pickupRequests) {
            if (!visitedPickups.contains(child)) {
                allPickupsProcessed = false;
                nextTime = currentTime + instance.dist(request.nodeId, child.nodeId);
                nextTime = Math.max(nextTime, child.twStart);
                nextCapacity = currentCapacity + child.demand;
                if (nextTime < child.twEnd && nextCapacity < instance.getVehicleCapacity()) { // Is feasible route then add to the request
                    nextTime += child.serviceTime;
                    visitedPickups.add(child);
                    currSequence[phase] = child.nodeId;
                    optimizedBranch(child, nextTime, nextCapacity, visitedPickups, currSequence, phase + 1);
                    visitedPickups.remove(child);
                }
            }
        }
        if (allPickupsProcessed) {
            nextTime = currentTime + instance.dist(request.nodeId, deliveryRequest.nodeId);
            nextTime = Math.max(nextTime, deliveryRequest.twStart);
            nextCapacity = currentCapacity + deliveryRequest.demand;
            if (nextTime < deliveryRequest.twEnd && nextCapacity < instance.getVehicleCapacity() && nextTime < bestCost) {
                nextTime += deliveryRequest.serviceTime;
                nextTime += instance.dist(deliveryRequest.nodeId, instance.getDepot().nodeId);
                currSequence[phase] = deliveryRequest.nodeId;
                currSequence[phase + 1] = instance.getDepot().nodeId;
                if (nextTime < instance.getDepot().twEnd && nextTime < bestCost) {
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
