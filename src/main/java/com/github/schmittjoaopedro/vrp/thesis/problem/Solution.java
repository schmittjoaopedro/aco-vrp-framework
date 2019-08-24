package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Solution {

    private static final DecimalFormat COST_FORMAT;

    static {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.US);
        formatSymbols.setDecimalSeparator('.');
        COST_FORMAT = new DecimalFormat("#.00", formatSymbols);
    }

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requestIds;

    public ArrayList<Double> tourCosts;

    public boolean[] visited;

    public boolean[] visitedRequests;

    public double totalCost;

    public boolean feasible;

    public double maxTime;

    public int toVisit;

    public NodeIndex[] nodeIndexes;

    // Insert 2 nodes to route at pos
    public void insert(Instance instance, int requestId, int vehicle, int pickupPos, int deliveryPos) {
        Request request = instance.requests[requestId];
        tours.get(vehicle).add(pickupPos, request.pickupTask.nodeId);
        tours.get(vehicle).add(deliveryPos, request.deliveryTask.nodeId);
        visited[request.pickupTask.nodeId] = true;
        visited[request.deliveryTask.nodeId] = true;
        visitedRequests[requestId] = true;
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
            visitedRequests[requestId] = false;
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

    // Number of unvisited customers
    public int requestBankSize(Instance instance) {
        int ret = 0;
        for (int i = 0; i < instance.numRequests; ++i)
            if (!visitedRequests[i]) ++ret;
        return ret;
    }

    // Total Objective value of the solution
    public double calculateObjective(Instance instance) {
        double ret = 0;
        for (int i = 0; i < tours.size(); ++i) {
            ret += instance.calculateTotalDistance(tours.get(i));
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

    public void indexVehicle(int vehicle) {
        int node;
        for (int routeIndex = 1; routeIndex < tours.get(vehicle).size() - 1; ++routeIndex) {
            node = tours.get(vehicle).get(routeIndex);
            nodeIndexes[node] = new NodeIndex(vehicle, routeIndex);
        }
    }

    public double calculateRequestRemovalGain(Instance instance, Request request) {
        int vehicle = getVehicle(request.pickupTask.nodeId);
        int pickupPos = getTourPosition(request.pickupTask.nodeId);
        int prevPickupNode = tours.get(vehicle).get(pickupPos - 1);
        int nextPickupNode = tours.get(vehicle).get(pickupPos + 1);
        int deliveryPos = getTourPosition(request.deliveryTask.nodeId);
        int prevDeliveryNode = tours.get(vehicle).get(deliveryPos - 1);
        int nextDeliveryNode = tours.get(vehicle).get(deliveryPos + 1);
        double requestCost;
        boolean adjacentNodes = nextPickupNode == request.deliveryTask.nodeId;
        if (adjacentNodes) {
            requestCost = instance.dist(prevPickupNode, request.pickupTask.nodeId) +
                    instance.dist(request.pickupTask.nodeId, request.deliveryTask.nodeId) +
                    instance.dist(request.deliveryTask.nodeId, nextDeliveryNode) -
                    instance.dist(prevPickupNode, nextDeliveryNode);
        } else {
            requestCost = instance.dist(prevPickupNode, request.pickupTask.nodeId) +
                    instance.dist(request.pickupTask.nodeId, nextPickupNode) -
                    instance.dist(prevPickupNode, nextPickupNode) +
                    instance.dist(prevDeliveryNode, request.deliveryTask.nodeId) +
                    instance.dist(request.deliveryTask.nodeId, nextDeliveryNode) -
                    instance.dist(prevDeliveryNode, nextDeliveryNode);
        }
        return requestCost;
    }

    public int getVehicle(int nodeId) {
        return nodeIndexes[nodeId].vehicle;
    }

    public int getTourPosition(int nodeId) {
        return nodeIndexes[nodeId].tourPosition;
    }

    @Override
    public String toString() {
        return "[F = " + feasible +
                ", NV = " + StringUtils.leftPad(String.valueOf(tours.size()), 3) +
                ", TC = " + StringUtils.leftPad(COST_FORMAT.format(MathUtils.round(totalCost)), 9) + "]";
    }

    protected class NodeIndex {

        protected int vehicle;

        protected int tourPosition;

        public NodeIndex(int vehicle, int tourPosition) {
            this.vehicle = vehicle;
            this.tourPosition = tourPosition;
        }
    }
}
