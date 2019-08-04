package com.github.schmittjoaopedro.vrp.thesis.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Instance {

    public String name;

    public int numNodes;

    public int numTasks;

    public int numRequests;

    public int numVehicles;

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

    public double[] objWeight = {1.0, 0.0, 100000.0};

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

    public Task getTask(int node) {
        if (node == depot.nodeId) {
            return null;
        } else {
            return tasks[node - 1];
        }
    }

    public void solutionEvaluation(Solution solution) {
        solution.tourCosts = new ArrayList<>(solution.tours.size());
        solution.departureTime = new ArrayList<>(solution.tours.size());
        solution.arrivalTime = new ArrayList<>(solution.tours.size());
        solution.departureSlackTimes = new ArrayList<>(solution.tours.size());
        solution.arrivalSlackTimes = new ArrayList<>(solution.tours.size());
        solution.waitingTimes = new ArrayList<>(solution.tours.size());
        solution.delays = new ArrayList<>(solution.tours.size());
        solution.toVisit = numTasks;
        solution.totalCost = 0.0;
        solution.feasible = true;
        solution.capacity = new double[numNodes];
        solution.visited = new boolean[numNodes];
        solution.visitedRequests = new boolean[numRequests];
        int[] numNodesByRequest = new int[numRequests];
        Double[] pickupByRequestTime = new Double[numRequests];
        Double[] deliveryByRequestTime = new Double[numRequests];
        // For each vehicle
        for (int k = 0; k < solution.tours.size(); k++) {
            List<Integer> tour = solution.tours.get(k);
            solution.arrivalTime.add(new double[tour.size()]);
            solution.departureTime.add(new double[tour.size()]);
            solution.waitingTimes.add(new double[tour.size()]);
            solution.delays.add(new double[tour.size()]);
            Double currentTime = depot.twStart;
            Double tourCost = 0.0;
            Double capacity = 0.0;
            int curr, next;
            Task task;
            LinkedList<Integer> attendedRequests = new LinkedList<>();
            solution.waitingTimes.get(k)[0] = 0.0;
            solution.delays.get(k)[0] = 0.0;
            solution.arrivalTime.get(k)[0] = currentTime;
            solution.departureTime.get(k)[0] = currentTime;
            for (int i = 0; i < tour.size() - 1; i++) {
                curr = tour.get(i);
                next = tour.get(i + 1);
                solution.visited[curr] = true;
                tourCost += dist(curr, next);
                currentTime += dist(curr, next);
                solution.arrivalTime.get(k)[i + 1] = currentTime;
                solution.waitingTimes.get(k)[i + 1] = Math.max(0, twStart(next) - solution.arrivalTime.get(k)[i + 1]);
                currentTime = Math.max(currentTime, twStart(next));
                capacity += demand(next);
                solution.capacity[next] = capacity;
                // For precedence and attendance restrictions
                task = getTask(next);
                if (task != null) { // Ignore node depot
                    solution.visitedRequests[task.requestId] = true;
                    if (task.isPickup) {
                        numNodesByRequest[task.requestId]++;
                        pickupByRequestTime[task.requestId] = currentTime;
                    } else {
                        attendedRequests.add(task.requestId);
                        deliveryByRequestTime[task.requestId] = currentTime;
                    }
                }
                // Check time windows feasibility
                if (currentTime > twEnd(next)) {
                    solution.delays.get(k)[i + 1] = currentTime - twEnd(next);
                    solution.feasible = false;
                } else {
                    solution.delays.get(k)[i + 1] = 0.0;
                }
                // Check capacity feasibility
                if (capacity > vehiclesCapacity) {
                    solution.feasible = false;
                }
                currentTime += serviceTime(next);
                solution.departureTime.get(k)[i + 1] = currentTime;
                // Check if nodes and requests lists are consistent
                if (curr != 0 && !solution.requestIds.get(k).contains(getTask(curr).requestId)) {
                    solution.feasible = false;
                }
            }
            for (Integer requestId : attendedRequests) {
                // Check if all nodes of each request is attended by the same vehicle
                if (numNodesByRequest[requestId] != 1) { // pair-wise for PDPTW
                    solution.feasible = false;
                }
                solution.toVisit -= numNodesByRequest[requestId];
                // Check if all pickups are not attended after the delivery
                // There are case where the same node is delivery and pickup without service time(l_4_100_1.txt). Therefore,
                // we must to use greater inequality, as the pickup and delivery times will be the same.
                if (pickupByRequestTime[requestId] > deliveryByRequestTime[requestId]) {
                    solution.feasible = false;
                } else {
                    solution.toVisit--;
                }
            }
            // Calculate slack times accordingly: Savelsbergh MW. The vehicle routing problem with time windows: Minimizing
            // route duration. ORSA journal on computing. 1992 May;4(2):146-54.
            Double slackTime = Double.MAX_VALUE;
            solution.departureSlackTimes.add(new double[tour.size()]);
            solution.arrivalSlackTimes.add(new double[tour.size()]);
            for (int i = tour.size() - 1; i >= 0; i--) {
                curr = tour.get(i);
                slackTime = Math.min(slackTime, twEnd(curr) - solution.departureTime.get(k)[i] + serviceTime(curr));
                solution.departureSlackTimes.get(k)[i] = slackTime;
                slackTime += solution.waitingTimes.get(k)[i];
                solution.arrivalSlackTimes.get(k)[i] = slackTime;
            }
            solution.tourCosts.add(tourCost);
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

    public void solutionEvaluation(Solution solution, int k) {
        solution.totalCost -= solution.tourCosts.get(k);
        int tourSize = solution.tours.get(k).size();
        List<Integer> tour = solution.tours.get(k);
        solution.arrivalTime.set(k, new double[tourSize]);
        solution.departureTime.set(k, new double[tourSize]);
        solution.waitingTimes.set(k, new double[tourSize]);
        solution.delays.set(k, new double[tourSize]);
        Double currentTime = depot.twStart;
        Double tourCost = 0.0;
        Double capacity = 0.0;
        Double routePenalty = 0.0;
        int curr, next;
        Task task;
        solution.waitingTimes.get(k)[0] = 0.0;
        solution.delays.get(k)[0] = 0.0;
        solution.arrivalTime.get(k)[0] = currentTime;
        solution.departureTime.get(k)[0] = currentTime;
        for (int i = 0; i < tourSize - 1; i++) {
            curr = tour.get(i);
            next = tour.get(i + 1);
            tourCost += dist(curr, next);
            currentTime += dist(curr, next);
            solution.arrivalTime.get(k)[i + 1] = currentTime;
            solution.waitingTimes.get(k)[i + 1] = Math.max(0, twStart(next) - solution.arrivalTime.get(k)[i + 1]);
            currentTime = Math.max(currentTime, twStart(next));
            capacity += demand(next);
            solution.capacity[next] = capacity;
            solution.delays.get(k)[i + 1] = Math.max(0.0, currentTime - twEnd(next));
            currentTime += serviceTime(next);
            solution.departureTime.get(k)[i + 1] = currentTime;
        }
        Double slackTime = Double.MAX_VALUE;
        solution.departureSlackTimes.set(k, new double[tour.size()]);
        solution.arrivalSlackTimes.set(k, new double[tour.size()]);
        for (int i = tour.size() - 1; i >= 0; i--) {
            curr = tour.get(i);
            slackTime = Math.min(slackTime, twEnd(curr) - solution.departureTime.get(k)[i] + serviceTime(curr));
            solution.departureSlackTimes.get(k)[i] = slackTime;
            slackTime += solution.waitingTimes.get(k)[i];
            solution.arrivalSlackTimes.get(k)[i] = slackTime;
        }
        solution.tourCosts.set(k, tourCost);
        solution.totalCost += tourCost;
    }

    public boolean isFeasibleTour(List<Integer> tour) {
        boolean feasible = true;
        double currentTime = 0.0;
        double capacity = 0.0;
        int curr, next;
        for (int i = 0; i < tour.size() - 1; i++) {
            curr = tour.get(i);
            next = tour.get(i + 1);
            currentTime += distances[curr][next];
            currentTime = Math.max(currentTime, twStart(next));
            capacity += demand(next);
            if (currentTime > twEnd(next)) {
                feasible = false;
            }
            if (capacity > vehiclesCapacity) {
                feasible = false;
            }
            currentTime += serviceTime(next);
        }
        return feasible;
    }

    public double costEvaluation(List<Integer> tour) {
        double cost = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            cost += distances[tour.get(i)][tour.get(i + 1)];
        }
        return cost;
    }

    public double costEvaluation(List<Integer> tour, Integer requestToIgnore) {
        double cost = 0.0;
        int from, to;
        int i = 0;
        while (i < tour.size()) {
            from = tour.get(i);
            i++;
            to = tour.get(i);
            while (to != depot.nodeId && getTask(to).requestId == requestToIgnore) {
                i++;
                to = tour.get(i);
            }
            cost += distances[from][to];
            if (to == depot.nodeId) {
                break;
            }
        }
        return cost;
    }

    public double costEvaluation(List<Integer> tour, Integer requestToIgnore, int nodeToIgnore) {
        double cost = 0.0;
        int from, to;
        int i = 0;
        while (i < tour.size()) {
            from = tour.get(i);
            i++;
            to = tour.get(i);
            while (to != depot.nodeId && getTask(to).requestId == requestToIgnore && to == nodeToIgnore) {
                i++;
                to = tour.get(i);
            }
            cost += distances[from][to];
            if (to == depot.nodeId) {
                break;
            }
        }
        return cost;
    }

    // Calculate cost of 1 route
    public double calcRouteCost(ArrayList<Integer> route) {
        double sumDist = 0, sumTime = 0;
        double currentCapacity = 0;
        for (int i = 1; i < route.size(); ++i) {
            currentCapacity += demand(route.get(i));
            if (currentCapacity > vehiclesCapacity) return Double.MAX_VALUE;
            sumDist += distances[route.get(i)][route.get(i - 1)];
            sumTime = sumTime + distances[route.get(i)][route.get(i - 1)] / vehicleSpeed;
            sumTime = Math.max(sumTime, twStart(route.get(i)));
            if (sumTime > twEnd(route.get(i))) {
                return Double.MAX_VALUE;
            }
            sumTime += serviceTime(route.get(i));
        }
        return objWeight[0] * sumDist + objWeight[1] * sumTime;
    }
}
