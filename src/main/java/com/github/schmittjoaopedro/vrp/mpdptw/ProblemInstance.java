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

    public FitnessResult tourCost(List<Integer> tour) {
        FitnessResult fitnessResult = new FitnessResult();
        double currentTime = 0.0;
        double capacity = 0.0;
        double demand, twStart, twEnd, serviceTime;
        int curr, next;
        for (int i = 0; i < tour.size() - 1; i++) {
            curr = tour.get(i);
            next = tour.get(i + 1);
            if (next == depot.nodeId) {
                twStart = depot.twStart;
                twEnd = depot.twEnd;
                demand = 0.0;
                serviceTime = 0.0;
            } else {
                twStart = requests[next - 1].twStart;
                twEnd = requests[next - 1].twEnd;
                demand = requests[next - 1].demand;
                serviceTime = requests[next - 1].serviceTime;
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
            ProblemInstance.FitnessResult fitnessResult = tourCost(ant.tours.get(k));
            ant.tourLengths.add(k, fitnessResult.cost);
            ant.totalCost += fitnessResult.cost;
            ant.feasible &= fitnessResult.feasible;
            ant.timeWindowPenalty += fitnessResult.timeWindowPenalty;
            ant.capacityPenalty += fitnessResult.capacityPenalty;
        }
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
