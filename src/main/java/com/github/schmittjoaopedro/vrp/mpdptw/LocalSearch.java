package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;
import java.util.List;

public class LocalSearch {

    private ProblemInstance instance;

    public LocalSearch(ProblemInstance instance) {
        this.instance = instance;
    }

    public Ant relocate(Ant ant) {
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, improvedAnt);
        AntUtils.copyFromTo(ant, tempAnt);
        boolean improvement = true;
        int node, removalIdx;
        List<Integer> tour, tourCloned;
        ProblemInstance.FitnessResult originalCost;
        while (improvement) {
            improvement = false;
            for (int vehicle = 0; vehicle < tempAnt.tours.size(); vehicle++) {
                tour = tempAnt.tours.get(vehicle);
                tourCloned = new ArrayList<>(tour);
                originalCost = instance.restrictionsEvaluation(tour);
                for (int nodeIdx = 1; nodeIdx < tour.size() - 1; nodeIdx++) { // Ignore depot at first and last position
                    node = tourCloned.get(nodeIdx);
                    removalIdx = removeNode(tour, node);
                    if (instance.requests[node - 1].isPickup) {
                        if (bestPickupInsertion(originalCost, tour, node, removalIdx)) {
                            originalCost = instance.restrictionsEvaluation(tour);
                            AntUtils.copyFromTo(tempAnt, improvedAnt);
                            improvement = true;
                        }
                    } else if (instance.requests[node - 1].isDeliver) { // Or try best delivery insertion
                        if (bestDeliveryInsertion(originalCost, tour, node, removalIdx)) {
                            originalCost = instance.restrictionsEvaluation(tour);
                            AntUtils.copyFromTo(tempAnt, improvedAnt);
                            improvement = true;
                        }
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        return improvedAnt;
    }

    private int removeNode(List<Integer> tour, int node) {
        int nodeIdx = 0;
        for (int i = 0; i < tour.size(); i++) {
            if (tour.get(i) == node) {
                tour.remove(i);
                nodeIdx = i;
            }
        }
        return nodeIdx;
    }

    private boolean bestPickupInsertion(ProblemInstance.FitnessResult originalCost, List<Integer> tour, int pickupNode, int bestInsertion) {
        ProblemInstance.FitnessResult insertionCost;
        ProblemInstance.FitnessResult bestInsertionCost = originalCost;
        int deliveryIdx = 0;
        boolean improvement = false;
        for (int i = 1; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.requests[tour.get(i) - 1].isDeliver && // First check if this is a delivery point
                    instance.requests[pickupNode - 1].requestId == instance.requests[tour.get(i) - 1].requestId) { // Check if this is the same pickup-delivery request
                deliveryIdx = i;
                break;
            }
        }
        for (int i = 1; i <= deliveryIdx; i++) {
            tour.add(i, pickupNode);
            insertionCost = instance.restrictionsEvaluation(tour);
            if (bestInsertionCost.feasible) {
                if (insertionCost.feasible && insertionCost.cost < bestInsertionCost.cost) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                }
            } else {
                if (insertionCost.feasible) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                } else if (insertionCost.cost < bestInsertionCost.cost) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                } else if (insertionCost.cost == bestInsertionCost.cost && insertionCost.timeWindowPenalty < bestInsertionCost.timeWindowPenalty) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                }
            }
            tour.remove(i);
        }
        tour.add(bestInsertion, pickupNode);
        return improvement;
    }

    private boolean bestDeliveryInsertion(ProblemInstance.FitnessResult originalCost, List<Integer> tour, int deliveryNode, int bestInsertion) {
        ProblemInstance.FitnessResult insertionCost;
        ProblemInstance.FitnessResult bestInsertionCost = originalCost;
        int lastPickupIdx = 0;
        boolean improvement = false;
        for (int i = 1; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.requests[tour.get(i) - 1].isPickup && // First check if this is a delivery point
                    instance.requests[tour.get(i) - 1].requestId == instance.requests[deliveryNode - 1].requestId) { // Check if this is the same pickup-delivery request
                lastPickupIdx = i;
            }
        }
        for (int i = lastPickupIdx + 1; i < tour.size(); i++) {
            tour.add(i, deliveryNode);
            insertionCost = instance.restrictionsEvaluation(tour);
            if (bestInsertionCost.feasible) {
                if (insertionCost.feasible && insertionCost.cost < bestInsertionCost.cost) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                }
            } else {
                if (insertionCost.feasible) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                } else if (insertionCost.cost < bestInsertionCost.cost) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                } else if (insertionCost.cost == bestInsertionCost.cost && insertionCost.timeWindowPenalty < bestInsertionCost.timeWindowPenalty) {
                    bestInsertionCost = insertionCost;
                    bestInsertion = i;
                    improvement = true;
                }
            }
            tour.remove(i);
        }
        tour.add(bestInsertion, deliveryNode);
        return improvement;
    }

}