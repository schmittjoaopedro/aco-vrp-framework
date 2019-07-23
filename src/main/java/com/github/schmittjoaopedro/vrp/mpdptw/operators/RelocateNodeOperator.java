package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;

import java.util.ArrayList;
import java.util.List;

public class RelocateNodeOperator {

    private ProblemInstance instance;

    public RelocateNodeOperator(ProblemInstance instance) {
        this.instance = instance;
    }

    public Solution relocate(Solution ant) {
        Solution improvedSol = SolutionUtils.createNewSolution(instance);
        Solution tempSol = SolutionUtils.createNewSolution(instance);
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
        instance.solutionEvaluation(improvedSol);
        return instance.getBest(ant, improvedSol);
    }

    public ArrayList<Integer> relocate(ArrayList<Integer> tour, int startAt) {
        return relocate(tour, startAt, false);
    }

    public ArrayList<Integer> relocate(ArrayList<Integer> tour, int startAt, boolean stopOnFeasible) {
        ArrayList<Integer> tempTour = new ArrayList<>(tour);
        ArrayList<Integer> bestTour = new ArrayList<>(tour);
        ProblemInstance.FitnessResult newCost, bestCost;
        bestCost = instance.restrictionsEvaluation(tempTour);
        if (bestCost.feasible) { // The current tour already is feasible
            return new ArrayList<>(tour);
        }
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int n1 = startAt; n1 < tempTour.size() - 1; n1++) { // Ignore depot at first and last position
                int node1 = bestTour.get(n1);
                Request req = instance.getRequest(node1);
                Node processingNode = processNode(tempTour, node1, req, startAt); // To insert back we consider plus 1
                for (int n2 = processingNode.startAt; n2 < processingNode.endAt; n2++) {
                    tempTour.add(n2, node1);
                    newCost = instance.restrictionsEvaluation(tempTour);
                    if (isBetter(bestCost, newCost)) {
                        processingNode.position = n2;
                        bestCost = newCost;
                        improvement = true;
                    }
                    tempTour.remove(n2);
                    if (bestCost.feasible && stopOnFeasible) {
                        improvement = false;
                        break;
                    }
                }
                tempTour.add(processingNode.position, node1);
                if (bestCost.feasible && stopOnFeasible) {
                    improvement = false;
                    break;
                }
            }
            bestTour = tempTour;
        }
        return bestTour;
    }

    private Node processNode(List<Integer> tour, int node, Request req, int startAt) {
        Node nodeObj = new Node();
        nodeObj.startAt = startAt;
        nodeObj.endAt = tour.size() - 1; // As we now that one node will be removed, we decrement one value
        int i = startAt;
        while (i < tour.size() - 1) {
            if (req.requestId == instance.getRequest(tour.get(i)).requestId) {
                if (req.isPickup) {
                    if (instance.getRequest(tour.get(i)).isDeliver) {
                        nodeObj.endAt = i + 1;
                        break; // We already removed the pickup to achieve the delivery point, therefore, we can stop processing
                    }
                } else if (req.isDeliver) {
                    if (instance.getRequest(tour.get(i)).isPickup) {
                        nodeObj.startAt = Math.max(startAt, i + 1);
                    }
                }
            }
            if (tour.get(i) == node) {
                tour.remove(i);
                nodeObj.position = i;
                if (req.isDeliver) {
                    break; // For delivery, we known that all pickups were evaluated, therefore, we can stop processing
                }
            } else {
                i++;
            }
        }
        return nodeObj;
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

    class Node {
        int startAt;
        int endAt;
        int position;
    }
}
