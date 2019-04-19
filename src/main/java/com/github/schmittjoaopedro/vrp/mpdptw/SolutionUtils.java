package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.utils.Maths;

import java.util.ArrayList;

public class SolutionUtils {

    public static Solution copy(Solution source) {
        Solution copySolution = new Solution();
        copyFromTo(source, copySolution);
        return copySolution;
    }

    public static void copyFromTo(Solution from, Solution to) {
        to.tours = new ArrayList<>();
        to.requests = new ArrayList<>();
        to.tourCosts = new ArrayList<>();
        to.departureTime = new ArrayList<>();
        to.arrivalTime = new ArrayList<>();
        to.departureSlackTimes = new ArrayList<>();
        to.arrivalSlackTimes = new ArrayList<>();
        to.waitingTimes = new ArrayList<>();
        to.delays = new ArrayList<>();
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
            to.requests.add(i, (ArrayList<Integer>) from.requests.get(i).clone());
            to.tourCosts.add(i, from.tourCosts.get(i));
            if (from.departureTime.size() > 0) { // TODO: Remove after refactored
                to.departureTime.add(i, from.departureTime.get(i).clone());
                to.arrivalTime.add(i, from.arrivalTime.get(i).clone());
                to.departureSlackTimes.add(i, from.departureSlackTimes.get(i).clone());
                to.arrivalSlackTimes.add(i, from.arrivalSlackTimes.get(i).clone());
                to.waitingTimes.add(i, from.waitingTimes.get(i).clone());
                to.delays.add(i, from.delays.get(i).clone());
            }
        }
        to.visited = from.visited.clone();
        to.capacity = from.capacity.clone();
        to.visitedRequests = from.visitedRequests.clone();
        to.toVisit = from.toVisit;
        to.totalCost = from.totalCost;
        to.feasible = from.feasible;
        to.timeWindowPenalty = from.timeWindowPenalty;
        to.capacityPenalty = from.capacityPenalty;
    }

    public static void antEmptyMemory(Solution solution, ProblemInstance instance) {
        solution.tours = new ArrayList<>();
        solution.requests = new ArrayList<>();
        solution.tourCosts = new ArrayList<>();
        solution.departureTime = new ArrayList<>();
        solution.arrivalTime = new ArrayList<>();
        solution.departureSlackTimes = new ArrayList<>();
        solution.waitingTimes = new ArrayList<>();
        solution.arrivalSlackTimes = new ArrayList<>();
        solution.delays = new ArrayList<>();
        solution.visited = new boolean[instance.getNumNodes()];
        solution.capacity = new double[instance.getNumNodes()];
        solution.visitedRequests = new boolean[instance.getNumReq()];
        solution.toVisit = instance.getNumNodes();
        solution.totalCost = Double.MAX_VALUE;
        solution.feasible = true;
        solution.timeWindowPenalty = 0.0;
        solution.capacityPenalty = 0.0;
    }

    public static Solution createEmptyAnt(ProblemInstance instance) {
        Solution solution = new Solution();
        SolutionUtils.antEmptyMemory(solution, instance);
        return solution;
    }

    public static void addEmptyVehicle(Solution solution) {
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(0);
        tour.add(0);
        solution.tours.add(tour);
        solution.requests.add(new ArrayList<>());
        solution.tourCosts.add(0.0);
        solution.departureTime.add(new double[0]);
        solution.arrivalTime.add(new double[0]);
        solution.departureSlackTimes.add(new double[0]);
        solution.waitingTimes.add(new double[0]);
        solution.arrivalSlackTimes.add(new double[0]);
        solution.delays.add(new double[0]);
    }

    public static boolean removeEmptyVehicles(Solution solution) {
        int position = 0;
        boolean removed = false;
        while (solution.tours.size() > position) {
            if (solution.requests.get(position).isEmpty()) {
                solution.tours.remove(position);
                solution.requests.remove(position);
                solution.tourCosts.remove(position);
                solution.departureTime.remove(position);
                solution.arrivalTime.remove(position);
                solution.departureSlackTimes.remove(position);
                solution.waitingTimes.remove(position);
                solution.arrivalSlackTimes.remove(position);
                solution.delays.remove(position);
                removed = true;
            } else {
                position++;
            }
        }
        return removed;
    }

    public static void removeRequest(ProblemInstance instance, Solution solution, int vehicle, Integer requestId) {
        int nodeIdx = 0;
        int node;
        while (nodeIdx < solution.tours.get(vehicle).size()) {
            node = solution.tours.get(vehicle).get(nodeIdx);
            if (node != instance.getDepot().nodeId && instance.getRequestId(node) == requestId) {
                solution.tours.get(vehicle).remove(nodeIdx);
            } else {
                nodeIdx++;
            }
        }
        solution.requests.get(vehicle).remove(requestId);
    }

    public static int findRequestVehicleOwner(Solution solution, Integer requestId) {
        int vehicle = 0;
        for (int k = 0; k < solution.requests.size(); k++) {
            if (solution.requests.get(k).contains(requestId)) {
                vehicle = k;
                break;
            }
        }
        return vehicle;
    }

    public static Solution getBest(Solution oldSol, Solution newSol) {
        boolean isBetterCost = Maths.round(newSol.totalCost + newSol.timeWindowPenalty) < Maths.round(oldSol.totalCost + oldSol.timeWindowPenalty);
        if (oldSol.feasible) {
            return newSol.feasible && isBetterCost ? newSol : oldSol;
        } else {
            return isBetterCost ? newSol : oldSol;
        }

    }


    public static boolean containsEmptyVehicle(Solution solution) {
        for (ArrayList<Integer> reqs : solution.requests) {
            if (reqs.isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
