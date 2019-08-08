package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution {

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requestIds;

    public ArrayList<Double> tourCosts;

    public ArrayList<double[]> departureTime;

    public ArrayList<double[]> arrivalTime;

    public ArrayList<double[]> departureSlackTimes;

    public ArrayList<double[]> arrivalSlackTimes;

    public ArrayList<double[]> waitingTimes;

    public ArrayList<double[]> delays;

    public double[] visitedTime;

    public double[] startTime;

    public double[] waitingTime;

    public double[] capacity;

    public boolean[] visited;

    public boolean[] visitedRequests;

    public Pair<Integer, Integer>[] nodeVehicle;

    public double totalCost;

    public boolean feasible;

    public double maxTime;

    public int toVisit;

    private double[] relateWeight = {9, 3, 2, 5};

    // Insert 2 nodes to route at pos
    public void insert(Instance instance, int requestId, int vehicle, int pickupPos, int deliveryPos) {
        Request request = instance.requests[requestId];
        tours.get(vehicle).add(pickupPos, request.pickupTask.nodeId);
        tours.get(vehicle).add(deliveryPos, request.deliveryTask.nodeId);
        visited[request.pickupTask.nodeId] = true;
        visited[request.deliveryTask.nodeId] = true;
        requestIds.get(vehicle).add(requestId);
    }

    // Remove all node from removeList to request bank
    public void remove(List<Integer> removeRequestsList, Instance instance) {
        for (int i = 0; i < removeRequestsList.size(); ++i) {
            int requestId = removeRequestsList.get(i);
            int pickupNode = instance.pickupTasks[requestId].nodeId;
            int deliveryNode = instance.deliveryTasks[requestId].nodeId;
            visited[pickupNode] = false;
            visited[deliveryNode] = false;
        }
        for (int i = 0; i < tours.size(); ++i) {
            ArrayList<Integer> newRoute = new ArrayList<>();
            for (int j = 0; j < tours.get(i).size(); ++j) {
                if (visited[tours.get(i).get(j)]) {
                    newRoute.add(tours.get(i).get(j));
                }
            }
            tours.set(i, newRoute);
        }
        for (Integer i : removeRequestsList) {
            for (int j = 0; j < requestIds.size(); j++) {
                if (requestIds.get(j).contains(i)) {
                    requestIds.get(j).remove(i);
                    break;
                }
            }
        }
    }

    // Find position of node i in solution
    public void findRoute(int i) {
        for (int j = 1; j < tours.get(i).size() - 1; ++j) {
            nodeVehicle[tours.get(i).get(j)] = Pair.of(i, j);
        }
    }

    // Calculate time visiting each node
    public void findVisitedTime(Instance instance, int r) {
        double time = 0;
        int startPos = 1;
        visitedTime[0] = 0;
        for (int j = startPos; j < tours.get(r).size() - 1; ++j) {
            time += instance.dist(tours.get(r).get(j - 1), tours.get(r).get(j)) / instance.vehicleSpeed + instance.serviceTime(tours.get(r).get(j - 1));
            visitedTime[tours.get(r).get(j)] = time;
            startTime[tours.get(r).get(j)] = Math.max(time, instance.twStart(tours.get(r).get(j)));
            time = startTime[tours.get(r).get(j)];
            waitingTime[tours.get(r).get(j)] = Math.max(0., time - visitedTime[tours.get(r).get(j)]);
            maxTime = Math.max(maxTime, time);
        }
    }

    // Calculate relateness of 2 nodes in the solution
    public double relatedness(Instance instance, Request reqA, Request reqB) {
        double ret = relateWeight[0] * (instance.dist(reqA.pickupTask.nodeId, reqB.pickupTask.nodeId) + instance.dist(reqA.deliveryTask.nodeId, reqB.deliveryTask.nodeId)) / instance.maxDistance;
        ret += relateWeight[1] * (Math.abs(visitedTime[reqA.pickupTask.nodeId] - visitedTime[reqB.pickupTask.nodeId]) + Math.abs(visitedTime[reqA.deliveryTask.nodeId] - visitedTime[reqB.deliveryTask.nodeId])) / maxTime;
        ret += relateWeight[2] * Math.abs(reqA.pickupTask.demand - reqB.pickupTask.demand) / instance.maxDemand;
        return ret;
    }

    // Number of unvisited customers
    public int requestBankSize(Instance instance) {
        int ret = 0;
        for (int i = 0; i < instance.numRequests; ++i)
            if (!visited[i]) ++ret;
        return ret;
    }

    // Total Objective value of the solution
    public double calculateObjective(Instance instance) {
        double ret = 0;
        for (int i = 0; i < tours.size(); ++i) {
            ret += instance.calcRouteCost(tours.get(i));
        }
        for (int i = 1; i < instance.numNodes; ++i) {
            if (!visited[i]) ret += instance.objWeight[2];
        }
        return ret;
    }

    public int getHash() {
        ArrayList<ArrayList<Integer>> clone = new ArrayList<>(tours);
        clone.sort(Comparator.<ArrayList<Integer>, Integer>comparing(a -> a.size()).thenComparing(b -> b.get(1)));
        return StringUtils.join(clone).hashCode();
    }


    @Override
    public String toString() {
        return "[F = " + feasible + ", NV = " + tours.size() + ", TC = " + MathUtils.round(totalCost) + "]";
    }
}
