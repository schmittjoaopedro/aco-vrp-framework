package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class WorstRemoval extends RemovalOperator {

    private Instance instance;

    private Random random;

    private double randomDegree = 3;

    public WorstRemoval(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    @Override
    public void remove(Solution solution, int q) {
        for (int k = 0; k < solution.tours.size(); ++k) {
            solution.indexVehicle(k);
        }
        // Calculates the cost difference for each request removed
        double[] requestsCosts = new double[instance.numRequests];
        for (int r = 0; r < instance.numRequests; r++) {
            Request request = instance.requests[r];
            if (solution.visited[request.pickupTask.nodeId]) {
                requestsCosts[request.requestId] = calculateRequestRemovalGain(solution, request);
            }
        }
        while (q > 0) { // While there are requests to remove
            // Create a heap to hold requests with expensive costs in front
            Queue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 0; i < instance.numRequests; ++i) {
                Request request = instance.requests[i];
                if (solution.visited[request.pickupTask.nodeId]) {
                    heap.add(Pair.of(-requestsCosts[request.requestId], -request.pickupTask.nodeId));
                }
            }
            if (heap.isEmpty()) break;
            double y = random.nextDouble();
            y = Math.pow(y, randomDegree);
            int toRemove = (int) (y * (double) heap.size());
            int removePickupNode = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePickupNode = -heap.peek().getRight();
                heap.poll();
            }
            Integer requestId = instance.getTask(removePickupNode).requestId;
            int removeDeliveryNode = instance.requests[requestId].deliveryTask.nodeId;
            int removeRoute = solution.getVehicle(removePickupNode);
            int pickupIdx = solution.getTourPosition(removePickupNode);
            int deliveryIdx = solution.getTourPosition(removeDeliveryNode);
            solution.visited[removePickupNode] = false;
            solution.visited[removeDeliveryNode] = false;
            solution.tours.get(removeRoute).remove(deliveryIdx);
            solution.tours.get(removeRoute).remove(pickupIdx);
            solution.requestIds.get(removeRoute).remove(requestId);
            --q;
            solution.indexVehicle(removeRoute);
            for (int i = 1; i < solution.tours.get(removeRoute).size() - 1; ++i) {
                int p = solution.tours.get(removeRoute).get(i);
                if (instance.getTask(p).isPickup) {
                    Request request = instance.requests[instance.getTask(p).requestId];
                    requestsCosts[request.requestId] = calculateRequestRemovalGain(solution, request);
                }
            }
        }
    }

    private double calculateRequestRemovalGain(Solution solution, Request request) {
        int vehicle = solution.getVehicle(request.pickupTask.nodeId);
        int pickupPos = solution.getTourPosition(request.pickupTask.nodeId);
        int prevPickupNode = solution.tours.get(vehicle).get(pickupPos - 1);
        int nextPickupNode = solution.tours.get(vehicle).get(pickupPos + 1);
        int deliveryPos = solution.getTourPosition(request.deliveryTask.nodeId);
        int prevDeliveryNode = solution.tours.get(vehicle).get(deliveryPos - 1);
        int nextDeliveryNode = solution.tours.get(vehicle).get(deliveryPos + 1);
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

}
