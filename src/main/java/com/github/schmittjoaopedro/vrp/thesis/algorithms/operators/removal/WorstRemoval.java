package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.Arrays;
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
            if (!solution.removedRequests[request.requestId]) {
                requestsCosts[request.requestId] = solution.calculateRequestRemovalGain(instance, request);
            }
        }
        while (q > 0) {
            Queue<RequestRemoval> heap = new PriorityQueue<>();
            for (int i = 0; i < instance.numRequests; ++i) {
                Request request = instance.requests[i];
                if (!solution.removedRequests[request.requestId]) {
                    heap.add(new RequestRemoval(-requestsCosts[request.requestId], -request.pickupTask.nodeId));
                }
            }
            if (heap.isEmpty()) break;
            double y = Math.pow(random.nextDouble(), randomDegree);
            int toRemove = (int) (y * (double) heap.size());
            int removePickupNode = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePickupNode = -heap.peek().pickupNodeId;
                heap.poll();
            }
            Integer requestId = instance.getTask(removePickupNode).requestId;
            int vehicle = solution.getVehicle(removePickupNode);
            solution.remove(Arrays.asList(requestId), instance);
            --q;
            solution.indexVehicle(vehicle);
            for (int i = 1; i < solution.tours.get(vehicle).size() - 1; ++i) {
                int p = solution.tours.get(vehicle).get(i);
                if (instance.getTask(p).isPickup) {
                    Request request = instance.requests[instance.getTask(p).requestId];
                    requestsCosts[request.requestId] = solution.calculateRequestRemovalGain(instance, request);
                }
            }
        }
    }

    protected class RequestRemoval implements Comparable<RequestRemoval> {

        protected double cost;

        protected int pickupNodeId;

        public RequestRemoval(double cost, int pickupNodeId) {
            this.cost = cost;
            this.pickupNodeId = pickupNodeId;
        }

        @Override
        public int compareTo(RequestRemoval other) {
            int compare = Double.compare(cost, other.cost);
            if (compare == 0) {
                compare = Integer.compare(pickupNodeId, other.pickupNodeId);
            }
            return compare;
        }
    }

}
