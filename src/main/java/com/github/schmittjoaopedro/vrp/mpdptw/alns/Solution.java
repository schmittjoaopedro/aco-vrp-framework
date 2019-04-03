package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;

import java.util.ArrayList;

public class Solution {


    public ArrayList<ArrayList<Integer>> tours = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> requests = new ArrayList<>();

    public ArrayList<Double> tourLengths = new ArrayList<>();

    public double totalCost;

    public boolean feasible;

    public Solution copy() {
        Solution solutionCopy = new Solution();
        for (ArrayList<Integer> tour : tours) {
            solutionCopy.tours.add(new ArrayList<>(tour));
        }
        for (ArrayList<Integer> requests : requests) {
            solutionCopy.requests.add(new ArrayList<>(requests));
        }
        for (Double tourLength : tourLengths) {
            solutionCopy.tourLengths.add(tourLength);
        }
        solutionCopy.totalCost = totalCost;
        solutionCopy.feasible = feasible;
        return solutionCopy;
    }

    public int findRequestVehicleOwner(Integer requestId) {
        int vehicle = 0;
        for (int k = 0; k < requests.size(); k++) {
            if (requests.get(k).contains(requestId)) {
                vehicle = k;
                break;
            }
        }
        return vehicle;
    }

    public void removeRequest(ProblemInstance instance, int vehicle, Integer requestId) {
        int nodeIdx = 0;
        int node;
        while (nodeIdx < tours.get(vehicle).size()) {
            node = tours.get(vehicle).get(nodeIdx);
            if (node != instance.getDepot().nodeId && instance.getRequestId(node) == requestId) {
                tours.get(vehicle).remove(nodeIdx);
            } else {
                nodeIdx++;
            }
        }
        requests.get(vehicle).remove(requestId);
    }

    public void removeEmptyVehicles() {
        int position = 0;
        while (tours.size() > position) {
            if (requests.get(position).isEmpty()) {
                tours.remove(position);
                requests.remove(position);
                tourLengths.remove(position);
            } else {
                position++;
            }
        }
    }

    public void addEmptyVehicle() {
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(0);
        tour.add(0);
        tours.add(tour);
        requests.add(new ArrayList<>());
        tourLengths.add(0.0);
    }

    public Solution getBestSolution(Solution newSolution) {
        if (feasible) {
            return newSolution.feasible && newSolution.totalCost < totalCost ? newSolution : this;
        } else {
            return newSolution.totalCost < totalCost ? newSolution : this;
        }
    }

}
