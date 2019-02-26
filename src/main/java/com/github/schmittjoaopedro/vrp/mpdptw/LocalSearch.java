package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.coelho.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.coelho.RemovalOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.coelho.Req;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalSearch {

    private ProblemInstance instance;

    private Random random;

    private RemovalOperator removalOperator;

    private InsertionOperator insertionOperator;

    private LocalSearchRelocate localSearchRelocate;

    public LocalSearch(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.removalOperator = new RemovalOperator(instance, random);
        this.insertionOperator = new InsertionOperator(instance, random);
        this.localSearchRelocate = new LocalSearchRelocate(instance);
    }

    public Ant optimize(Ant ant) {
        Ant tempAnt = ant;
        boolean improvement = true;
        double oldCost = ant.totalCost;
        while (improvement) {
            improvement = false;
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Random, InsertionOperator.PickupMethod.Random);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Shaw, InsertionOperator.PickupMethod.Random);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveNode, InsertionOperator.PickupMethod.Random);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveRequest, InsertionOperator.PickupMethod.Random);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Random, InsertionOperator.PickupMethod.Simple);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Shaw, InsertionOperator.PickupMethod.Simple);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveNode, InsertionOperator.PickupMethod.Simple);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveRequest, InsertionOperator.PickupMethod.Simple);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Random, InsertionOperator.PickupMethod.Expensive);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Shaw, InsertionOperator.PickupMethod.Expensive);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveNode, InsertionOperator.PickupMethod.Expensive);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveRequest, InsertionOperator.PickupMethod.Expensive);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Random, InsertionOperator.PickupMethod.Cheapest);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.Shaw, InsertionOperator.PickupMethod.Cheapest);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveNode, InsertionOperator.PickupMethod.Cheapest);
            tempAnt = optimize(tempAnt, RemovalOperator.Type.ExpensiveRequest, InsertionOperator.PickupMethod.Cheapest);
            if (tempAnt.totalCost < oldCost) {
                oldCost = tempAnt.totalCost;
                improvement = true;
            }
        }
        return tempAnt;
    }

    public Ant optimize(Ant ant, RemovalOperator.Type removalMethod, InsertionOperator.PickupMethod pickupMethod) {
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, tempAnt);
        AntUtils.copyFromTo(ant, improvedAnt);
        instance.restrictionsEvaluation(tempAnt);
        instance.restrictionsEvaluation(improvedAnt);
        boolean improvement = true;
        double oldCost = improvedAnt.totalCost;
        while (improvement) {
            List<Req> removedRequests = removeRequests(tempAnt, removalMethod);
            insertionOperator.insertRequests(tempAnt.tours, tempAnt.requests, removedRequests, pickupMethod, InsertionOperator.InsertionMethod.Greedy);
            Ant tempAnt2 = localSearchRelocate.relocate(tempAnt);
            instance.restrictionsEvaluation(tempAnt);
            if (tempAnt2.totalCost < tempAnt.totalCost) {
                tempAnt = tempAnt2;
            }
            improvement = tempAnt.totalCost < improvedAnt.totalCost;
            if (improvement) {
                AntUtils.copyFromTo(tempAnt, improvedAnt);
            }
        }
        if (improvedAnt.totalCost < oldCost) {
            return improvedAnt;
        } else {
            return ant;
        }
    }

    private List<Req> removeRequests(Ant tempAnt, RemovalOperator.Type removalType) {
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
