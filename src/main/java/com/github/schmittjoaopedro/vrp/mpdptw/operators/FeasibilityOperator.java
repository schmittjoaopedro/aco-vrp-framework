package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.*;

import java.util.ArrayList;

public class FeasibilityOperator {

    private ProblemInstance instance;

    public FeasibilityOperator(ProblemInstance instance) {
        this.instance = instance;
    }

    public Solution feasibility(Solution ant) {
        int requestId, bestVehicle;
        ArrayList<Integer> tour, bestTour = null, requests, newTour;
        ProblemInstance.FitnessResult fitness, fitnessInsertion;
        Solution improvedAnt = SolutionUtils.createEmptyAnt(instance);
        SolutionUtils.copyFromTo(ant, improvedAnt);
        for (int k1 = 0; k1 < improvedAnt.tours.size(); k1++) {
            tour = improvedAnt.tours.get(k1);
            requests = improvedAnt.requests.get(k1);
            fitness = instance.restrictionsEvaluation(tour);
            while (!fitness.feasible && requests.size() > 1) {
                requestId = removeCostlyRequest(tour, requests, fitness);
                fitness = instance.restrictionsEvaluation(tour);
                bestVehicle = -1;
                for (int k2 = 0; k2 < improvedAnt.tours.size(); k2++) {
                    if (k1 != k2 && instance.restrictionsEvaluation(improvedAnt.tours.get(k2)).feasible) {
                        newTour = new ArrayList<>(improvedAnt.tours.get(k2));
                        fitnessInsertion = insertCheapestRequest(newTour, requestId);
                        if (fitnessInsertion.feasible) {
                            bestVehicle = k2;
                            bestTour = newTour;
                        }
                    }
                }
                if (bestVehicle != -1) {
                    improvedAnt.tours.set(bestVehicle, bestTour);
                    improvedAnt.requests.get(bestVehicle).add(requestId);
                    bestTour = null;
                } else {
                    OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requestId, instance);
                    optimalRequestSolver.optimize();
                    newTour = new ArrayList<>();
                    for (int i : optimalRequestSolver.getBestRoute()) {
                        newTour.add(i);
                    }
                    improvedAnt.tours.add(newTour);
                    improvedAnt.requests.add(new ArrayList<>());
                    improvedAnt.requests.get(improvedAnt.requests.size() - 1).add(requestId);
                }
            }
            if (requests.size() == 1 && !instance.restrictionsEvaluation(tour).feasible) {
                OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requests.get(0), instance);
                optimalRequestSolver.optimize();
                newTour = new ArrayList<>();
                for (int i : optimalRequestSolver.getBestRoute()) {
                    newTour.add(i);
                }
                improvedAnt.tours.set(k1, newTour);
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        return improvedAnt;
    }

    private int removeCostlyRequest(ArrayList<Integer> tour, ArrayList<Integer> requests, ProblemInstance.FitnessResult tourFitness) {
        ProblemInstance.FitnessResult newCost, bestRemove = tourFitness;
        ArrayList<Integer> tourCloned;
        Integer delRequest = 0;
        int reqId;
        for (int r = 0; r < requests.size(); r++) {
            reqId = requests.get(r);
            tourCloned = new ArrayList<>(tour);
            int n = 1;
            while (n < tourCloned.size() - 1) {
                if (instance.getRequestId(tourCloned.get(n)) == reqId) {
                    tourCloned.remove(n);
                } else {
                    n++;
                }
            }
            newCost = instance.restrictionsEvaluation(tourCloned);
            if (bestRemove.feasible) {
                if (newCost.feasible && bestRemove.cost > newCost.cost) {
                    bestRemove = newCost;
                    delRequest = reqId;
                }
            } else {
                if (bestRemove.cost > newCost.cost) {
                    bestRemove = newCost;
                    delRequest = reqId;
                }
            }
        }
        int n = 1;
        while (n < tour.size() - 1) {
            if (instance.getRequestId(tour.get(n)) == delRequest) {
                tour.remove(n);
            } else {
                n++;
            }
        }
        requests.remove(delRequest);
        return delRequest;
    }

    private ProblemInstance.FitnessResult insertCheapestRequest(ArrayList<Integer> tour, int requestId) {
        int node, prev, curr, bestIdx, lastPickup = 1;
        double lost;
        // Insert pickup points
        for (Request req : instance.getPickups(requestId)) {
            node = req.nodeId;
            lost = Double.MAX_VALUE;
            bestIdx = 1;
            for (int n = 1; n < tour.size(); n++) {
                prev = tour.get(n - 1);
                curr = tour.get(n);
                if (instance.dist(prev, node) + instance.dist(node, curr) < lost) {
                    lost = instance.dist(prev, node) + instance.dist(node, curr);
                    bestIdx = n;
                }
            }
            tour.add(bestIdx, node);
        }
        // Insert delivery
        for (int i = 1; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.getRequest(tour.get(i)).isPickup && // First check if this is a delivery point
                    requestId == instance.getRequest(tour.get(i)).requestId) { // Check if this is the same pickup-delivery request
                lastPickup = i;
            }
        }
        lastPickup++; // adjust insertion point
        lost = Double.MAX_VALUE;
        node = instance.getDelivery(requestId).nodeId;
        bestIdx = 1;
        for (int n = lastPickup; n < tour.size(); n++) {
            prev = tour.get(n - 1);
            curr = tour.get(n);
            if (instance.dist(prev, node) + instance.dist(node, curr) < lost) {
                lost = instance.dist(prev, node) + instance.dist(node, curr);
                bestIdx = n;
            }
        }
        tour.add(bestIdx, node);
        return instance.restrictionsEvaluation(tour);
    }

}
