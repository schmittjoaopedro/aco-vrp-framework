package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.Ant;
import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.Solution;

import java.util.Random;

/*
 * Relocate: in relocate improvement, we select a request and try to insert it into every other other route. The accepted
 * move is teh one leading the greatest decrease in cost among all routes. If no improvement solution ca be found, we test
 * the next request. This is executed for all requests until no improvements can be found.
 */
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
        int vehicle;
        double improvedCost, tempCost;
        while (improvement) {
            improvement = false;
            for (int requestId = 0; requestId < instance.getNumReq(); requestId++) {
                AntUtils.copyFromTo(improvedAnt, tempAnt);
                vehicle = AntUtils.findRequestVehicleOwner(tempAnt, requestId);
                AntUtils.removeRequest(instance, tempAnt, vehicle, requestId);
                AntUtils.removeEmptyVehicles(tempAnt);
                AntUtils.addEmptyVehicle(tempAnt);
                for (int k = 0; k < tempAnt.tours.size(); k++) {
                    if (insertionOperator.insertRequestOnVehicle(requestId, tempAnt.tours.get(k), PickupMethod.Random, InsertionMethod.Greedy)) {
                        tempAnt.requests.get(k).add(requestId);
                        instance.restrictionsEvaluation(tempAnt);
                        improvedCost = improvedAnt.totalCost + improvedAnt.timeWindowPenalty;
                        tempCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                        if (tempCost < improvedCost) {
                            AntUtils.removeEmptyVehicles(tempAnt);
                            AntUtils.copyFromTo(tempAnt, improvedAnt);
                            improvement = true;
                        }
                        AntUtils.removeRequest(instance, tempAnt, k, requestId);
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        return AntUtils.getBetterAnt(ant, improvedAnt);
    }

    public Solution relocate(Solution solution) {
        Solution tempSol;
        Solution improvedSol = solution.copy();
        boolean improvement = true;
        int vehicle;
        double improvedCost, tempCost;
        while (improvement) {
            improvement = false;
            for (int requestId = 0; requestId < instance.getNumReq(); requestId++) {
                tempSol = improvedSol.copy();
                vehicle = tempSol.findRequestVehicleOwner(requestId);
                tempSol.removeRequest(instance, vehicle, requestId);
                tempSol.removeEmptyVehicles();
                tempSol.addEmptyVehicle();
                for (int k = 0; k < tempSol.tours.size(); k++) {
                    if (insertionOperator.insertRequestOnVehicle(requestId, tempSol.tours.get(k), PickupMethod.Random, InsertionMethod.Greedy)) {
                        tempSol.requests.get(k).add(requestId);
                        instance.restrictionsEvaluation(tempSol);
                        improvedCost = improvedSol.totalCost;
                        tempCost = tempSol.totalCost;
                        if (tempCost < improvedCost) {
                            tempSol.removeEmptyVehicles();
                            improvedSol = tempSol.copy();
                            improvement = true;
                        }
                        tempSol.removeRequest(instance, k, requestId);
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedSol);
        return solution.getBestSolution(improvedSol);
    }

}
