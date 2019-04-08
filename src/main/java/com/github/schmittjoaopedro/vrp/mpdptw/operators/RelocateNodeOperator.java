package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;

import java.util.ArrayList;
import java.util.List;

public class RelocateNodeOperator {

    private ProblemInstance instance;

    private InsertionOperator insertionOperator;

    public RelocateNodeOperator(ProblemInstance instance) {
        this.instance = instance;
        this.insertionOperator = new InsertionOperator(instance, null);
    }

    public Solution relocate(Solution ant) {
        Solution improvedSol = SolutionUtils.createEmptyAnt(instance);
        Solution tempSol = SolutionUtils.createEmptyAnt(instance);
        SolutionUtils.copyFromTo(ant, improvedSol);
        SolutionUtils.copyFromTo(ant, tempSol);
        boolean improvement = true;
        int node, removalIdx;
        List<Integer> tour, tourCloned;
        ProblemInstance.FitnessResult originalCost;
        while (improvement) {
            improvement = false;
            for (int vehicle = 0; vehicle < tempSol.tours.size(); vehicle++) {
                tour = tempSol.tours.get(vehicle);
                tourCloned = new ArrayList<>(tour);
                originalCost = instance.restrictionsEvaluation(tour);
                for (int nodeIdx = 1; nodeIdx < tour.size() - 1; nodeIdx++) { // Ignore depot at first and last position
                    node = tourCloned.get(nodeIdx);
                    removalIdx = removeNode(tour, node);
                    if (instance.getRequest(node).isPickup) {
                        if (bestPickupInsertion(originalCost, tour, node, removalIdx, 1)) {
                            originalCost = instance.restrictionsEvaluation(tour);
                            SolutionUtils.copyFromTo(tempSol, improvedSol);
                            improvement = true;
                        }
                    } else if (instance.getRequest(node).isDeliver) { // Or try best delivery insertion
                        if (bestDeliveryInsertion(originalCost, tour, node, removalIdx, 1)) {
                            originalCost = instance.restrictionsEvaluation(tour);
                            SolutionUtils.copyFromTo(tempSol, improvedSol);
                            improvement = true;
                        }
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedSol);
        return SolutionUtils.getBest(ant, improvedSol);
    }

    public ArrayList<Integer> relocate(ArrayList<Integer> tour, int startAt, boolean stopOnFeasible) {
        ArrayList<Integer> tempTour = new ArrayList<>(tour);
        ArrayList<Integer> bestTour = new ArrayList<>(tour);
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            ProblemInstance.FitnessResult newCost, bestCost;
            for (int n1 = startAt; n1 < tempTour.size() - 1; n1++) { // Ignore depot at first and last position
                int node1 = bestTour.get(n1);
                bestCost = instance.restrictionsEvaluation(tempTour);
                int bestPos = removeNode(tempTour, node1); // To insert back we consider plus 1
                int innerLimit = tempTour.size();
                int innerStartAt = startAt;
                Request req = instance.getRequest(node1);
                if (req.isDeliver) {
                    innerStartAt = getLastPickupIdx(tempTour, req) + 1; // To start after the last pickup position
                    innerStartAt = Math.max(innerStartAt, startAt);
                } else {
                    innerLimit = getLastDeliverIdx(tempTour, req) + 1; // Until before delivery point
                }
                for (int n2 = innerStartAt; n2 < innerLimit; n2++) {
                    tempTour.add(n2, node1);
                    newCost = instance.restrictionsEvaluation(tempTour);
                    if (isBetter(bestCost, newCost)) {
                        bestPos = n2;
                        bestCost = newCost;
                        improvement = true;
                    }
                    tempTour.remove(n2);
                    if (bestCost.feasible && stopOnFeasible) {
                        improvement = false;
                        break;
                    }
                }
                tempTour.add(bestPos, node1);
                if (bestCost.feasible && stopOnFeasible) {
                    improvement = false;
                    break;
                }
            }
            bestTour = tempTour;
        }
        return bestTour;
    }

    private boolean isBetter(ProblemInstance.FitnessResult original, ProblemInstance.FitnessResult testing) {
        if (original.feasible) {
            return testing.feasible && testing.cost < original.cost;
        } else {
            return testing.feasible || (testing.cost + testing.timeWindowPenalty < original.cost + original.timeWindowPenalty);
        }
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

    private boolean bestPickupInsertion(ProblemInstance.FitnessResult originalCost, List<Integer> tour, int pickupNode, int bestInsertion, int startAt) {
        ProblemInstance.FitnessResult insertionCost;
        ProblemInstance.FitnessResult bestInsertionCost = originalCost;
        int deliveryIdx = 0;
        boolean improvement = false;
        for (int i = startAt; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.getRequest(tour.get(i)).isDeliver && // First check if this is a delivery point
                    instance.getRequestId(pickupNode) == instance.getRequestId(tour.get(i))) { // Check if this is the same pickup-delivery request
                deliveryIdx = i;
                break;
            }
        }
        for (int i = startAt; i <= deliveryIdx; i++) {
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

    private boolean bestDeliveryInsertion(ProblemInstance.FitnessResult originalCost, List<Integer> tour, int deliveryNode, int bestInsertion, int startAt) {
        ProblemInstance.FitnessResult insertionCost;
        ProblemInstance.FitnessResult bestInsertionCost = originalCost;
        boolean improvement = false;
        int lastPickupIdx = getLastPickupIdx(tour, instance.getRequest(deliveryNode));
        lastPickupIdx = Math.max(lastPickupIdx, startAt);
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

    private int getLastPickupIdx(List<Integer> tour, Request request) {
        int lastPickupIdx = 0;
        for (int i = 1; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.getRequest(tour.get(i)).isPickup && // First check if this is a pickup point
                    instance.getRequestId(tour.get(i)) == request.requestId) { // Check if this is the same pickup-delivery request
                lastPickupIdx = i;
            }
        }
        return lastPickupIdx;
    }

    private int getLastDeliverIdx(List<Integer> tour, Request request) {
        int lastDeliveryIdx = 0;
        for (int i = 1; i < tour.size() - 1; i++) { // Ignore depot at first and last position
            if (instance.getRequest(tour.get(i)).isDeliver && // First check if this is a delivery point
                    instance.getRequestId(tour.get(i)) == request.requestId) { // Check if this is the same pickup-delivery request
                lastDeliveryIdx = i;
            }
        }
        return lastDeliveryIdx;
    }

}
