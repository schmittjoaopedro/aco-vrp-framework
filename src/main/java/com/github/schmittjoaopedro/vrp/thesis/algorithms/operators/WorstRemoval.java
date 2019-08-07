package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class WorstRemoval {

    private Instance instance;

    private Random random;

    private double worstRandomDegree = 3;

    public WorstRemoval(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    // Remove Customer base on Cost
    public void remove(Solution solution, int q) {
        for (int i = 0; i < solution.tours.size(); ++i) {
            solution.findRoute(i);
        }
        // Calculates the cost difference for each route for each request removed
        double[] costs = new double[instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            Request request = instance.requests[i];
            if (solution.visited[request.pickupTask.nodeId]) {
                int removeRoute = solution.nodeVehicle[request.pickupTask.nodeId].getLeft();
                Pair<Integer, Integer> pos = Pair.of(solution.nodeVehicle[request.pickupTask.nodeId].getRight(), solution.nodeVehicle[request.deliveryTask.nodeId].getRight());
                costs[request.requestId] = removeCost(pos, solution.tours.get(removeRoute));
            }
        }
        while (q > 0) { // While there are requests to remove
            // Create a heap to hold requests with expensive costs in front
            Queue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 0; i < instance.numRequests; ++i) {
                Request request = instance.requests[i];
                if (solution.visited[request.pickupTask.nodeId]) {
                    heap.add(Pair.of(-costs[request.requestId], -request.pickupTask.nodeId));
                }
            }
            if (heap.isEmpty()) break;
            double y = random.nextDouble();
            y = Math.pow(y, worstRandomDegree);
            int toRemove = (int) (y * (double) heap.size());
            int removePickupNode = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePickupNode = -heap.peek().getRight();
                heap.poll();
            }
            Integer requestId = instance.getTask(removePickupNode).requestId;
            int removeDeliveryNode = instance.requests[requestId].deliveryTask.nodeId;
            int removeRoute = solution.nodeVehicle[removePickupNode].getLeft();
            int pickupIdx = solution.nodeVehicle[removePickupNode].getRight();
            int deliveryIdx = solution.nodeVehicle[removeDeliveryNode].getRight();
            solution.visited[removePickupNode] = false;
            solution.visited[removeDeliveryNode] = false;
            solution.tours.get(removeRoute).remove(deliveryIdx);
            solution.tours.get(removeRoute).remove(pickupIdx);
            solution.requestIds.get(removeRoute).remove(requestId);
            --q;
            solution.findRoute(removeRoute);
            for (int i = 1; i < solution.tours.get(removeRoute).size() - 1; ++i) {
                int p = solution.tours.get(removeRoute).get(i);
                if (instance.getTask(p).isPickup) {
                    Request request = instance.requests[instance.getTask(p).requestId];
                    removeDeliveryNode = request.deliveryTask.nodeId;
                    Pair<Integer, Integer> pos = Pair.of(solution.nodeVehicle[p].getRight(), solution.nodeVehicle[removeDeliveryNode].getRight());
                    costs[request.requestId] = removeCost(pos, solution.tours.get(removeRoute));
                }
            }
        }
    }

    // Calculate cost to remove customer from route
    private double removeCost(Pair<Integer, Integer> pos, ArrayList<Integer> route) {
        double oldCost = instance.calcRouteCost(route);
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        int delPos = pos.getRight();
        newRoute.remove(delPos);
        delPos = pos.getLeft();
        newRoute.remove(delPos);
        double cost = instance.calcRouteCost(newRoute);
        return oldCost - cost;
    }

}
