package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InfeasibleSolutionException;

import java.util.ArrayList;
import java.util.LinkedList;

public class Instance {

    public String name;

    public int numNodes;

    public int numTasks;

    public int numRequests;

    public int numVehicles;

    public int extraVehicles;

    public double vehiclesCapacity;

    public double[][] distances;

    public double maxDistance;

    public double maxDemand;

    public double vehicleSpeed = 1.0;

    public Depot depot;

    public Request[] requests;

    public Task[] tasks;

    public Task[] pickupTasks;

    public Task[] deliveryTasks;

    public boolean movingVehicle;

    public double[] objWeight = {1.0, 0.0, 100000.0};

    public double currentTime = 0.0;

    public double dist(int i, int j) {
        return distances[i][j];
    }

    public double twStart(int node) {
        if (node == depot.nodeId) {
            return depot.twStart;
        } else {
            return tasks[node - 1].twStart;
        }
    }

    public double twEnd(int node) {
        if (node == depot.nodeId) {
            return depot.twEnd;
        } else {
            return tasks[node - 1].twEnd;
        }
    }

    public double demand(int node) {
        if (node == depot.nodeId) {
            return 0.0;
        } else {
            return tasks[node - 1].demand;
        }
    }

    public double serviceTime(int node) {
        if (node == depot.nodeId) {
            return 0.0;
        } else {
            return tasks[node - 1].serviceTime;
        }
    }

    public double announceTime(int node) {
        if (depot.nodeId == node) {
            return 0.0;
        } else {
            return requests[getTask(node).requestId].announceTime;
        }
    }

    public double startVisitTime(int node) {
        if (!movingVehicle) {
            return 0.0;
        }
        if (depot.nodeId == node) {
            return currentTime;
        } else {
            return requests[getTask(node).requestId].startVisitTime;
        }
    }

    public Task getTask(int node) {
        if (node == depot.nodeId) {
            return null;
        } else {
            return tasks[node - 1];
        }
    }

    public boolean isDepot(int node) {
        return depot.nodeId == node;
    }

    public int numVehicles() {
        return numVehicles + extraVehicles;
    }

    public void solutionEvaluation(Solution solution) {
        solution.tourCosts = new ArrayList<>(solution.tours.size());
        solution.toVisit = numTasks;
        solution.totalCost = 0.0;
        solution.feasible = true;
        solution.visited = new boolean[numNodes];
        solution.removedRequests = new boolean[numRequests];
        for (int i = 0; i < numRequests; i++) {
            solution.removedRequests[i] = true;
        }
        int[] numNodesByRequest = new int[numRequests];
        int[] parityConstraint = new int[numRequests];
        Double[] pickupByRequestTime = new Double[numRequests];
        Double[] deliveryByRequestTime = new Double[numRequests];
        // For each vehicle
        for (int k = 0; k < solution.tours.size(); k++) {
            solution.tourCosts.add(0.0);
            if (solution.requestIds.get(k).isEmpty()) continue;
            ArrayList<Integer> tour = solution.tours.get(k);
            //Double currentTime = announceTime(tour.get(1));
            Double currentTime = startVisitTime(tour.get(1));
            Double tourCost = 0.0;
            Double capacity = 0.0;
            Task task;
            int curr, next;
            LinkedList<Integer> attendedRequests = new LinkedList<>();
            for (int i = 0; i < tour.size() - 1; i++) {
                curr = tour.get(i);
                next = tour.get(i + 1);
                // Is infeasible visit a route before they announce time
                if (currentTime < announceTime(next)) {
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Announce time infeasibility");
                }
                solution.visited[curr] = true;
                tourCost += dist(curr, next);
                currentTime += dist(curr, next);
                currentTime = Math.max(currentTime, twStart(next));
                capacity += demand(next);
                // For precedence and attendance restrictions
                task = getTask(next);
                if (task != null) { // Ignore node depot
                    solution.removedRequests[task.requestId] = false;
                    if (task.isPickup) {
                        numNodesByRequest[task.requestId]++;
                        pickupByRequestTime[task.requestId] = currentTime;
                        parityConstraint[task.requestId] = k;
                    } else { // Is delivery
                        attendedRequests.add(task.requestId);
                        deliveryByRequestTime[task.requestId] = currentTime;
                        // Check if delivery is made by the same vehicle of pickup
                        if (k != parityConstraint[task.requestId]) {
                            solution.feasible = false;
                            throw new InfeasibleSolutionException("Parity infeasibility");
                        }
                    }
                }
                // Check time windows feasibility
                if (currentTime > twEnd(next)) {
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Time window infeasibility");
                }
                // Check capacity feasibility
                if (capacity > vehiclesCapacity) {
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Capacity infeasibility");
                }
                currentTime += serviceTime(next);
                // Check if nodes and requests lists are consistent
                if (curr != 0 && !solution.requestIds.get(k).contains(getTask(curr).requestId)) {
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Node-request synchronization infeasibility");
                }
            }
            for (Integer requestId : attendedRequests) {
                // Check if all nodes of each request is attended by the same vehicle
                if (numNodesByRequest[requestId] != 1) { // pair-wise for PDPTW
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Pair-wire infeasibility");
                }
                solution.toVisit -= numNodesByRequest[requestId];
                // Check if all pickups are not attended after the delivery
                // There are case where the same node is delivery and pickup without service time(l_4_100_1.txt). Therefore,
                // we must to use greater inequality, as the pickup and delivery times will be the same.
                if (pickupByRequestTime[requestId] > deliveryByRequestTime[requestId]) {
                    solution.feasible = false;
                    throw new InfeasibleSolutionException("Precedence infeasibility");
                } else {
                    solution.toVisit--;
                }
            }
            solution.tourCosts.set(k, tourCost);
            solution.totalCost += tourCost;
        }
        // Check that all requests were attended
        if (solution.toVisit != 0) {
            solution.feasible = false;
        }
        // Check if number of vehicles is not extrapolated
        if (solution.tours.size() > numVehicles) {
            solution.feasible = false;
        }
    }

    public double calculateTotalDistance(ArrayList<Integer> tour) {
        double totalDistance = 0, totalTime = 0, currentCapacity = 0;
        int prev, curr;
        for (int i = 1; i < tour.size(); ++i) {
            prev = tour.get(i - 1);
            curr = tour.get(i);
            currentCapacity += demand(curr);
            if (currentCapacity > vehiclesCapacity) return Double.MAX_VALUE;
            totalDistance += distances[prev][curr];
            totalTime = totalTime + distances[prev][curr] / vehicleSpeed;
            totalTime = Math.max(totalTime, twStart(curr));
            if (totalTime > twEnd(curr)) {
                return Double.MAX_VALUE;
            }
            totalTime += serviceTime(curr);
        }
        return totalDistance;
    }
}
