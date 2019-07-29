package com.github.schmittjoaopedro.vrp.thesis.problem;

import java.util.ArrayList;

public class SolutionUtils {

    public static void copyFromTo(Solution from, Solution to) {
        to.tours = new ArrayList<>();
        to.requestIds = new ArrayList<>();
        to.tourCosts = new ArrayList<>();
        to.departureTime = new ArrayList<>();
        to.arrivalTime = new ArrayList<>();
        to.departureSlackTimes = new ArrayList<>();
        to.arrivalSlackTimes = new ArrayList<>();
        to.waitingTimes = new ArrayList<>();
        to.delays = new ArrayList<>();
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
            to.requestIds.add(i, (ArrayList<Integer>) from.requestIds.get(i).clone());
            to.tourCosts.add(i, from.tourCosts.get(i));
            if (from.departureTime.size() > 0) {
                to.departureTime.add(i, from.departureTime.get(i).clone());
                to.arrivalTime.add(i, from.arrivalTime.get(i).clone());
                to.departureSlackTimes.add(i, from.departureSlackTimes.get(i).clone());
                to.arrivalSlackTimes.add(i, from.arrivalSlackTimes.get(i).clone());
                to.waitingTimes.add(i, from.waitingTimes.get(i).clone());
                to.delays.add(i, from.delays.get(i).clone());
            }
        }
        to.capacity = from.capacity.clone();
        to.visited = from.visited.clone();
        to.visitedRequests = from.visitedRequests.clone();
        to.totalCost = from.totalCost;
        to.feasible = from.feasible;
        to.maxTime = from.maxTime;
        to.toVisit = from.toVisit;
    }

    public static Solution createSolution(Instance instance) {
        Solution solution = new Solution();
        clearSolution(solution, instance);
        return solution;
    }

    public static void clearSolution(Solution solution, Instance instance) {
        solution.tours = new ArrayList<>();
        solution.requestIds = new ArrayList<>();
        solution.tourCosts = new ArrayList<>();
        solution.departureTime = new ArrayList<>();
        solution.arrivalTime = new ArrayList<>();
        solution.departureSlackTimes = new ArrayList<>();
        solution.arrivalSlackTimes = new ArrayList<>();
        solution.waitingTimes = new ArrayList<>();
        solution.delays = new ArrayList<>();
        solution.capacity = new double[instance.numNodes];
        solution.visited = new boolean[instance.numNodes];
        solution.visitedRequests = new boolean[instance.numRequests];
        solution.totalCost = 0.0;
        solution.feasible = false;
        solution.maxTime = 0.0;
        solution.toVisit = instance.numTasks;
        // Instantiate empty vehicles
        for (int i = 0; i < instance.numVehicles; ++i) {
            solution.tours.add(new ArrayList<>());
            solution.requestIds.add(new ArrayList<>());
            solution.tours.get(i).add(0);
            solution.tours.get(i).add(0);
        }
    }

    public static void removeEmptyVehicles(Solution solution) {
        int v = solution.tours.size() - 1;
        while (v >= 0) {
            if (solution.tours.get(v).size() == 2) {
                solution.tours.remove(v);
                solution.requestIds.remove(v);
            }
            v--;
        }
    }

}
