package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.alns.Solution;
import com.github.schmittjoaopedro.vrp.preprocessing.InfeasibleRequestsPairs;

import java.util.*;

public class ProblemInstance {

    public int noNodes;

    public int noReq;

    public int noMaxVehicles;

    public double vehicleCapacity;

    public Depot depot;

    public double[][] distances;

    public double maxDistance;

    public boolean requestsFeasibility[][];

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
            capacity += demand;
            if (currentTime > twEnd) {
                fitnessResult.timeWindowPenalty += currentTime - twEnd;
                fitnessResult.feasible = false;
            }
            if (capacity > vehicleCapacity) {
                fitnessResult.capacityPenalty += capacity - vehicleCapacity;
                fitnessResult.feasible = false;
            }
            currentTime += serviceTime;
        }
        fitnessResult.cost = currentTime;
        return fitnessResult;
    }

    public void restrictionsEvaluation(Ant ant) {
        restrictionsEvaluation(ant, true);
    }

    public void restrictionsEvaluation(Ant ant, boolean noReqsRestriction) {
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
        if (attendedRequests != noReq && noReqsRestriction) {
            ant.feasible = false;
        }
        double total = 0.0;
        for (int k = 0; k < ant.tours.size(); k++) {
            ant.tourLengths.set(k, costEvaluation(ant.tours.get(k)));
            total += ant.tourLengths.get(k);
        }
        ant.totalCost = total;
    }

    public void restrictionsEvaluation(Solution solution) {
        solution.totalCost = 0.0;
        solution.feasible = true;
        solution.tourLengths.clear();
        for (int k = 0; k < solution.tours.size(); k++) {
            isPrecedenceValid(solution.tours.get(k), solution.requests.get(k));
            ProblemInstance.FitnessResult fitnessResult = restrictionsEvaluation(solution.tours.get(k));
            solution.tourLengths.add(k, fitnessResult.cost);
            solution.totalCost += fitnessResult.cost;
            solution.feasible &= fitnessResult.feasible;
        }
        int attendedRequests = 0;
        for (int i = 0; i < solution.requests.size(); i++) {
            attendedRequests += solution.requests.get(i).size();
        }
        solution.feasible &= solution.tours.size() < noMaxVehicles;
        if (attendedRequests != noReq) {
            solution.feasible = false;
            throw new RuntimeException("Infeasible number of requests");
        }
        double total = 0.0;
        for (int k = 0; k < solution.tours.size(); k++) {
            solution.tourLengths.set(k, costEvaluation(solution.tours.get(k)));
            total += solution.tourLengths.get(k);
        }
        solution.totalCost = total;
    }

    public void isPrecedenceValid(ArrayList<Integer> antTour, ArrayList<Integer> requests) {
        int node;
        Request req;
        Set<Integer> requestsTemp = new HashSet<>();
        Map<Integer, Integer> lastPickupPosition = new HashMap<>();
        Map<Integer, Integer> deliveryPosition = new HashMap<>();
        Map<Integer, Integer> totalPickups = new HashMap<>();
        for (int i = 1; i < antTour.size() - 1; i++) {
            node = antTour.get(i);
            req = this.requests[node - 1];
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
        if (requestsTemp.size() != requests.size()) {
            throw new RuntimeException("Invalid number of requests");
        }
        for (int reqId : requests) {
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
    public boolean isFeasible(int i, int j) {
        boolean feasible;
        int reqJ, reqI;
        if (i == 0 && j == 0) {
            feasible = true;
        } else if (i == 0 && j != 0) {
            // from depot we only can go to pickups
            reqJ = j - 1;
            feasible = requests[reqJ].isPickup;
        } else if (i != 0 && j == 0) {
            // from deliveries we can go to depot
            reqI = i - 1;
            feasible = requests[reqI].isDeliver;
        } else {
            reqI = i - 1;
            reqJ = j - 1;
            feasible = requests[reqI].twStart + requests[reqI].serviceTime + distances[i][j] < requests[reqJ].twEnd; // is time feasible
            if (requestsFeasibility != null && feasible && requests[reqI].requestId != requests[reqJ].requestId) {
                feasible = requestsFeasibility[requests[reqI].requestId][requests[reqJ].requestId];
            }
        }
        return feasible;
    }

    public void requestPairsFeasibility(Random random) {
        InfeasibleRequestsPairs infeasibleRequestsPairs = new InfeasibleRequestsPairs(this, random);
        this.requestsFeasibility = infeasibleRequestsPairs.calculateFeasibilityPairs();
    }

    /**
     * Accordingly: Lu, Q., & Dessouky, M. M. (2006). Discrete Optimization A new insertion-based construction
     * heuristic for solving the pickup and delivery problem with time windows, 175, 672–687.
     */
    public double[] calculateSlackTimesLu(ArrayList<Integer> route) {
        int node, prev, reqId;
        double ei, si, li, cost = 0.0;
        double arrivalTimes[] = new double[route.size()];
        double waitingTimes[] = new double[route.size()];
        for (int k = 1; k < route.size(); k++) {
            prev = route.get(k - 1);
            node = route.get(k);
            if (node == depot.nodeId) {
                ei = depot.twStart;
                si = 0.0;
            } else {
                reqId = node - 1;
                ei = requests[reqId].twStart;
                si = requests[reqId].serviceTime;
            }
            arrivalTimes[k] = cost + distances[prev][node];
            waitingTimes[k] = Math.max(0, ei - arrivalTimes[k]);
            cost = Math.max(ei, arrivalTimes[k]);
            cost += si;
        }
        double[] slackTimes = new double[route.size()];
        for (int k = route.size() - 1; k >= 0; k--) {
            node = route.get(k);
            if (node == depot.nodeId) {
                ei = depot.twStart;
                li = depot.twEnd;
            } else {
                reqId = node - 1;
                ei = requests[reqId].twStart;
                li = requests[reqId].twEnd;
            }
            if (k == route.size() - 1) { // Is last node
                slackTimes[k] = li - Math.max(arrivalTimes[k], ei);
            } else { // Intermediary nodes
                slackTimes[k] = Math.min(li - Math.max(arrivalTimes[k], ei), slackTimes[k + 1] + waitingTimes[k + 1]);
            }
        }
        return slackTimes;
    }

    /**
     * Savelsbergh MW. The vehicle routing problem with time windows: Minimizing route duration.
     * ORSA journal on computing. 1992 May;4(2):146-54.
     */
    public double[] slackTimesSavelsbergh(ArrayList<Integer> route, boolean sumWaitingTimes) {
        double[] slackTimes = new double[route.size()];
        double[] departureTimes = new double[route.size()];
        double[] waitingTimes = new double[route.size()];
        int node, prev, reqId;
        double ei, si, li, arrivalTime = 0.0, waitingTime;
        for (int k = 0; k < route.size(); k++) {
            node = route.get(k);
            if (node == depot.nodeId) {
                ei = depot.twStart;
                si = 0.0;
            } else {
                reqId = node - 1;
                ei = requests[reqId].twStart;
                si = requests[reqId].serviceTime;
            }
            if (k > 0) {
                prev = route.get(k - 1);
                arrivalTime = departureTimes[k - 1] + distances[prev][node]; // Calculate cost to next vertex
            }
            waitingTime = Math.max(0, ei - arrivalTime); // Calculate waiting time
            departureTimes[k] = arrivalTime + waitingTime + si; // Departure time of the current position
            if (k > 0) {
                waitingTimes[k] = waitingTime;
            }
        }
        double departureTime, cost;
        for (int i = 0; i < route.size(); i++) {
            departureTime = departureTimes[i];
            slackTimes[i] = Double.MAX_VALUE;
            cost = 0.0;
            for (int k = i; k < route.size(); k++) {
                node = route.get(k);
                if (node == depot.nodeId) {
                    li = depot.twEnd;
                    si = 0.0;
                } else {
                    reqId = node - 1;
                    li = requests[reqId].twEnd;
                    si = requests[reqId].serviceTime;
                }
                if (k - i > 0) {
                    prev = route.get(k - 1);
                    cost = cost + distances[prev][node] + si;
                }
                slackTimes[i] = Math.min(slackTimes[i], li - (departureTime + cost));
            }
        }
        if (sumWaitingTimes) {
            for (int i = 0; i < slackTimes.length; i++) {
                slackTimes[i] = slackTimes[i] + waitingTimes[i];
            }
        }
        return slackTimes;
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
