package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalSearch {

    private ProblemInstance instance;

    private Random random;

    private RemovalOperator removalOperator;

    private InsertionOperator insertionOperator;

    private RelocateNodeOperator relocateNodeOperator;

    private RelocateRequestOperator relocateRequestOperator;

    public LocalSearch(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.removalOperator = new RemovalOperator(instance, random);
        this.insertionOperator = new InsertionOperator(instance, random);
        this.relocateRequestOperator = new RelocateRequestOperator(instance, random);
        this.relocateNodeOperator = new RelocateNodeOperator(instance);
    }

    public Ant optimize(Ant ant) {
        Ant tempAnt = ant;
        boolean improvement = true;
        double oldCost = ant.totalCost + ant.timeWindowPenalty;
        double newCost;
        while (improvement) {
            improvement = false;
            tempAnt = relocateNodeOperator.relocate(tempAnt);
            tempAnt = optimize(tempAnt, RemovalMethod.Random, PickupMethod.Random);
            tempAnt = relocateRequestOperator.relocate(tempAnt);
            newCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
            if (newCost < oldCost) {
                oldCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                improvement = true;
            }
        }
        return tempAnt;
    }

    public Ant optimize(Ant ant, RemovalMethod removalMethod, PickupMethod pickupMethod) {
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, tempAnt);
        AntUtils.copyFromTo(ant, improvedAnt);
        boolean improvement = true;
        double oldCost = ant.totalCost + ant.timeWindowPenalty;
        double improvedCost = oldCost;
        double tempCost;
        boolean improved = false;
        while (improvement) {
            List<Req> removedRequests = removeRequests(tempAnt, removalMethod);
            insertionOperator.insertRequests(tempAnt.tours, tempAnt.requests, removedRequests, pickupMethod, InsertionMethod.Greedy);
            instance.restrictionsEvaluation(tempAnt);
            tempCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
            improvement = tempCost < improvedCost || (!improvedAnt.feasible && tempAnt.feasible);
            if (improvement) {
                AntUtils.copyFromTo(tempAnt, improvedAnt);
                improvedCost = tempCost;
                improved = true;
            }
        }
        if (improved) {
            return improvedAnt;
        } else {
            return ant;
        }
    }

    private List<Req> removeRequests(Ant tempAnt, RemovalMethod removalType) {
        List<Req> removedRequests = new ArrayList<>();
        switch (removalType) {
            case Random:
                removedRequests = removalOperator.removeRandomRequest(tempAnt.tours, tempAnt.requests, generateNoRemovalRequests());
                break;
            case Shaw:
                removedRequests = removalOperator.removeShawRequests(tempAnt.tours, tempAnt.requests, generateNoRemovalRequests());
                break;
            case ExpensiveNode:
                removedRequests = removalOperator.removeMostExpensiveNodes(tempAnt.tours, tempAnt.requests, generateNoRemovalRequests());
                break;
            case ExpensiveRequest:
                removedRequests = removalOperator.removeExpensiveRequests(tempAnt.tours, tempAnt.requests, generateNoRemovalRequests());
                break;
        }

        return removedRequests;
    }

    private int generateNoRemovalRequests() {
        int min = (int) Math.min(6, 0.15 * instance.noReq);
        int max = (int) Math.min(18, 0.4 * instance.noReq) + 1;
        return min + (int) (random.nextDouble() * (max - min));
    }
}
