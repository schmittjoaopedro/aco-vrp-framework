package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.Ant;
import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;

import java.util.ArrayList;
import java.util.Random;

public class RelocateRequestOperator {

    private ProblemInstance instance;

    private InsertionOperator insertionOperator;

    public RelocateRequestOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.insertionOperator = new InsertionOperator(instance, random);
    }

    public Ant relocate(Ant ant) {
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, improvedAnt);
        boolean improvement = true;
        Req req;
        int vehicle;
        double improvedCost, tempCost;
        while (improvement) {
            improvement = false;
            for (int requestId = 0; requestId < instance.noReq; requestId++) {
                AntUtils.copyFromTo(improvedAnt, tempAnt);
                vehicle = findRequestVehicleOwner(tempAnt, requestId);
                req = new Req(vehicle, requestId);
                removeRequest(tempAnt, vehicle, requestId);
                AntUtils.removeEmptyVehicles(tempAnt);
                AntUtils.addEmptyVehicle(tempAnt);
                for (int k = 0; k < tempAnt.tours.size(); k++) {
                    if (k != vehicle) {
                        if (insertionOperator.insertRequestOnVehicle(req, tempAnt.tours.get(k), PickupMethod.Random, InsertionMethod.Greedy)) {
                            tempAnt.requests.get(k).add(requestId);
                            instance.restrictionsEvaluation(tempAnt);
                            improvedCost = improvedAnt.totalCost + improvedAnt.timeWindowPenalty;
                            tempCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                            if (tempCost < improvedCost) {
                                AntUtils.removeEmptyVehicles(tempAnt);
                                AntUtils.copyFromTo(tempAnt, improvedAnt);
                                improvement = true;
                            }
                            removeRequest(tempAnt, k, requestId);
                        }
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        double improvedAntCost = improvedAnt.totalCost + improvedAnt.timeWindowPenalty;
        double antCost = ant.totalCost + ant.timeWindowPenalty;
        boolean improved = improvedAntCost < antCost || (!ant.feasible && improvedAnt.feasible);
        return improved ? improvedAnt : ant;
    }

    private void removeRequest(Ant ant, int vehicle, Integer requestId) {
        int nodeIdx = 0;
        int node;
        while (nodeIdx < ant.tours.get(vehicle).size()) {
            node = ant.tours.get(vehicle).get(nodeIdx);
            if (node != instance.depot.nodeId && instance.requests[node - 1].requestId == requestId) {
                ant.tours.get(vehicle).remove(nodeIdx);
            } else {
                nodeIdx++;
            }
        }
        ant.requests.get(vehicle).remove(requestId);
    }

    private int findRequestVehicleOwner(Ant ant, Integer requestId) {
        int vehicle = 0;
        for (int k = 0; k < ant.requests.size(); k++) {
            if (ant.requests.get(k).contains(requestId)) {
                vehicle = k;
                break;
            }
        }
        return vehicle;
    }

}
