package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.List;
import java.util.Map;

public class ProblemInstance {

    public int noNodes;

    public int noReq;

    public int noMaxVehicles;

    public double vehicleCapacity;

    public Depot depot;

    public double[][] distances;

    public Request[] requests;

    public Map<Integer, List<Request>> pickups;

    public Map<Integer, Request> delivery;

    public FitnessResult fitnessEvaluation(List<Integer> tour) {
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
    public void fitnessEvaluation(Ant ant) {
        ant.totalCost = 0.0;
        ant.feasible = true;
        ant.timeWindowPenalty = 0.0;
        ant.capacityPenalty = 0.0;
        for (int k = 0; k < ant.tours.size(); k++) {
            ProblemInstance.FitnessResult fitnessResult = fitnessEvaluation(ant.tours.get(k));
            ant.tourLengths.add(k, fitnessResult.cost);
            ant.totalCost += fitnessResult.cost;
            ant.feasible &= fitnessResult.feasible;
            ant.timeWindowPenalty += fitnessResult.timeWindowPenalty;
            ant.capacityPenalty += fitnessResult.capacityPenalty;
        }
        ant.feasible &= ant.tours.size() < noMaxVehicles;
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

    class FitnessResult {

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
