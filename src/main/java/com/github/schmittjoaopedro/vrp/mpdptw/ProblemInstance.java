package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.*;

public class ProblemInstance {

    public int noNodes;

    public int noReq;

    public int noMaxVehicles;

    public double vehicleCapacity;

    public Depot depot;

    public double[][] distances;

    public double maxDistance;

    public Request[] requests;

    public Map<Integer, List<Request>> pickups;

    public Map<Integer, Request> delivery;

    public void calculateMaxDistance() {
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances.length; j++) {
                if (i != j) {
                    if (distances[i][j] > maxDistance) {
                        maxDistance = distances[i][j];
                    }
                }
            }
        }
    }

    public FitnessResult restrictionsEvaluation(List<Integer> tour) {
        FitnessResult fitnessResult = new FitnessResult();
        double currentTime = 0.0;
        double capacity = 0.0;
        double demand, twStart, twEnd, serviceTime;
        int curr, next, reqId;
        for (int i = 0; i < tour.size() - 1; i++) {
            curr = tour.get(i);
            next = tour.get(i + 1);
            if (next == depot.nodeId) {
                twStart = depot.twStart;
                twEnd = depot.twEnd;
                demand = 0.0;
                serviceTime = 0.0;
            } else {
                reqId = next - 1;
                twStart = requests[reqId].twStart;
                twEnd = requests[reqId].twEnd;
                demand = requests[reqId].demand;
                serviceTime = requests[reqId].serviceTime;
            }
            currentTime += distances[curr][next];
            currentTime = Math.max(currentTime, twStart);
            currentTime += serviceTime;
            capacity += demand;
            if (currentTime > twEnd) {
                fitnessResult.timeWindowPenalty += currentTime - twEnd;
                fitnessResult.feasible = false;
            }
            if (capacity > vehicleCapacity) {
                fitnessResult.capacityPenalty += capacity - vehicleCapacity;
                fitnessResult.feasible = false;
            }
        }
        fitnessResult.cost = currentTime;
        return fitnessResult;
    }

    // TODO: Cost function must to consider the service time?
    public void restrictionsEvaluation(Ant ant) {
        ant.totalCost = 0.0;
        ant.feasible = true;
        ant.timeWindowPenalty = 0.0;
        ant.capacityPenalty = 0.0;
        ant.tourLengths.clear();
        for (int k = 0; k < ant.tours.size(); k++) {
            isPrecedenceValid(ant.tours.get(k), ant.requests.get(k));
            ProblemInstance.FitnessResult fitnessResult = restrictionsEvaluation(ant.tours.get(k));
            ant.tourLengths.add(k, fitnessResult.cost);
            ant.totalCost += fitnessResult.cost;
            ant.feasible &= fitnessResult.feasible;
            ant.timeWindowPenalty += fitnessResult.timeWindowPenalty;
            ant.capacityPenalty += fitnessResult.capacityPenalty;
        }
        int attendedRequests = 0;
        for (int i = 0; i < ant.requests.size(); i++) {
            attendedRequests += ant.requests.get(i).size();
        }
        ant.feasible &= ant.tours.size() < noMaxVehicles;
        if (attendedRequests != noReq) {
            ant.feasible = false;
            throw new RuntimeException("Infeasible number of requests");
        }
        double total = 0.0;
        for (int k = 0; k < ant.tours.size(); k++) {
            ant.tourLengths.set(k, costEvaluation(ant.tours.get(k)));
            total += ant.tourLengths.get(k);
        }
        ant.totalCost = total;
    }

    public void isPrecedenceValid(ArrayList<Integer> antTour, ArrayList<Integer> antRequests) {
        int node;
        Request req;
        Set<Integer> requestsTemp = new HashSet<>();
        Map<Integer, Integer> lastPickupPosition = new HashMap<>();
        Map<Integer, Integer> deliveryPosition = new HashMap<>();
        Map<Integer, Integer> totalPickups = new HashMap<>();
        for (int i = 1; i < antTour.size() - 1; i++) {
            node = antTour.get(i);
            req = requests[node - 1];
            if (req.isDeliver) {
                requestsTemp.add(req.requestId);
                deliveryPosition.put(req.requestId, i);
            } else {
                Integer last = lastPickupPosition.get(req.requestId);
                Integer total = totalPickups.get(req.requestId);
                if (last == null || i > last) {
                    last = i;
                }
                lastPickupPosition.put(req.requestId, last);
                if (total == null) {
                    total = 1;
                } else {
                    total++;
                }
                totalPickups.put(req.requestId, total);
            }
        }
        if (requestsTemp.size() != antRequests.size()) {
            throw new RuntimeException("Invalid number of requests");
        }
        for (int reqId : antRequests) {
            if (!requestsTemp.contains(reqId)) {
                throw new RuntimeException("Invalid assigned request");
            }
            if (totalPickups.get(reqId) != pickups.get(reqId).size()) {
                throw new RuntimeException("Invalid number of pickups assigned");
            }
            if (deliveryPosition.get(reqId) < lastPickupPosition.get(reqId)) {
                throw new RuntimeException("Invalid precedence of pickups and deliveries assigned");
            }
        }
    }


    // Check if the end time window of node j is achievable departing from node i
    public boolean isTimeFeasible(int i, int j) {
        boolean feasible;
        // requests does not index the node depot, therefore we have to adjust the indexes to ignore the depot
        if (i == 0 || j == 0) {
            feasible = true;
        } else {
            int reqI = i - 1;
            int reqJ = j - 1;
            feasible = requests[reqI].twStart + requests[reqI].serviceTime + distances[i][j] < requests[reqJ].twEnd;
        }
        return feasible;
    }

    public boolean isTimeFeasible(double currentTime, int i, int j) {
        int reqJ = j - 1;
        return currentTime + distances[i][j] + requests[reqJ].serviceTime < requests[reqJ].twEnd;
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
        for (int i = 0; i < tour.size() - 1; i++) {
            from = tour.get(i);
            to = tour.get(i + 1);
            if (to != depot.nodeId && requests[to - 1].requestId == requestToIgnore) {
                i++;
                to = tour.get(i + 1);
            }
            cost += distances[from][to];
        }
        return cost;
    }

    public class FitnessResult {

        public double cost;

        public double timeWindowPenalty;

        public double capacityPenalty;

        public boolean feasible;

        public FitnessResult() {
            feasible = true;
            timeWindowPenalty = 0.0;
            capacityPenalty = 0.0;
            cost = 0.0;
        }
    }
}
