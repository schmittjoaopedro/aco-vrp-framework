package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;

import java.util.ArrayList;

public class SolutionUtils {

    public static void copyFromTo(Solution from, Solution to) {
        to.tours = new ArrayList<>();
        to.requestIds = new ArrayList<>();
        to.tourCosts = new ArrayList<>();
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
            to.requestIds.add(i, (ArrayList<Integer>) from.requestIds.get(i).clone());
            if (from.tourCosts.size() > i) {
                to.tourCosts.add(i, from.tourCosts.get(i));
            }
        }
        to.visited = from.visited.clone();
        to.removedRequests = from.removedRequests.clone();
        to.nodeIndexes = from.nodeIndexes.clone();
        to.totalCost = from.totalCost;
        to.totalWaitingTime = from.totalWaitingTime;
        to.totalScheduleDuration = from.totalScheduleDuration;
        to.feasible = from.feasible;
        to.maxTime = from.maxTime;
        to.toVisit = from.toVisit;
    }

    public static Solution createSolution(Instance instance) {
        Solution solution = new Solution();
        clearSolution(solution, instance);
        return solution;
    }

    public static Solution copy(Solution solution) {
        Solution solutionNew = new Solution();
        copyFromTo(solution, solutionNew);
        return solutionNew;
    }

    public static void clearSolution(Solution solution, Instance instance) {
        solution.tours = new ArrayList<>();
        solution.requestIds = new ArrayList<>();
        solution.tourCosts = new ArrayList<>();
        solution.visited = new boolean[instance.numNodes];
        solution.removedRequests = new boolean[instance.numRequests];
        solution.nodeIndexes = new Solution.NodeIndex[instance.numNodes];
        solution.totalCost = 0.0;
        solution.totalScheduleDuration = 0.0;
        solution.totalWaitingTime = 0.0;
        solution.feasible = false;
        solution.maxTime = 0.0;
        solution.toVisit = instance.numTasks;
        // Instantiate empty vehicles
        for (int i = 0; i < instance.numVehicles(); ++i) {
            solution.tours.add(new ArrayList<>());
            solution.requestIds.add(new ArrayList<>());
            solution.tours.get(i).add(0);
            solution.tours.get(i).add(0);
        }
        for (int i = 0; i < instance.numRequests; i++) {
            solution.removedRequests[i] = true;
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

    public static Solution getBest(Solution sol1, Solution sol2) {
        Solution bestSol = null;
        boolean isNV1Minimized = sol1.tours.size() < sol2.tours.size();
        boolean isTC1Minimized = sol1.tours.size() == sol2.tours.size() && MathUtils.round(sol1.totalCost) < MathUtils.round(sol2.totalCost);
        boolean isNV2Minimized = sol1.tours.size() > sol2.tours.size();
        boolean isTC2Minimized = sol1.tours.size() == sol2.tours.size() && MathUtils.round(sol1.totalCost) > MathUtils.round(sol2.totalCost);
        if (!sol1.feasible && sol2.feasible) {
            bestSol = sol2;
        } else if (sol1.feasible && !sol2.feasible) {
            bestSol = sol1;
        } else if (isNV1Minimized || isTC1Minimized) {
            bestSol = sol1;
        } else if (isNV2Minimized || isTC2Minimized) {
            bestSol = sol2;
        }
        return bestSol;
    }

    public static boolean allRequestsAreAttended(Solution solution) {
        boolean allAttended = true;
        for (boolean removed : solution.removedRequests) {
            if (removed) {
                allAttended = false;
                break;
            }
        }
        return allAttended;
    }

}
