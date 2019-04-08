package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;

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

    public Solution relocate(Solution solution) {
        Solution improvedSol = SolutionUtils.createEmptyAnt(instance);
        Solution tempSol = SolutionUtils.createEmptyAnt(instance);
        SolutionUtils.copyFromTo(solution, improvedSol);
        boolean improvement = true;
        int vehicle;
        double improvedCost, tempCost;
        while (improvement) {
            improvement = false;
            for (int requestId = 0; requestId < instance.getNumReq(); requestId++) {
                SolutionUtils.copyFromTo(improvedSol, tempSol);
                vehicle = SolutionUtils.findRequestVehicleOwner(tempSol, requestId);
                SolutionUtils.removeRequest(instance, tempSol, vehicle, requestId);
                SolutionUtils.removeEmptyVehicles(tempSol);
                SolutionUtils.addEmptyVehicle(tempSol);
                for (int k = 0; k < tempSol.tours.size(); k++) {
                    if (insertionOperator.insertRequestOnVehicle(requestId, tempSol.tours.get(k), PickupMethod.Random, InsertionMethod.Greedy)) {
                        tempSol.requests.get(k).add(requestId);
                        instance.restrictionsEvaluation(tempSol);
                        improvedCost = improvedSol.totalCost + improvedSol.timeWindowPenalty;
                        tempCost = tempSol.totalCost + tempSol.timeWindowPenalty;
                        if (tempCost < improvedCost) {
                            SolutionUtils.removeEmptyVehicles(tempSol);
                            SolutionUtils.copyFromTo(tempSol, improvedSol);
                            improvement = true;
                        }
                        SolutionUtils.removeRequest(instance, tempSol, k, requestId);
                    }
                }
            }
        }
        instance.restrictionsEvaluation(improvedSol);
        return SolutionUtils.getBest(solution, improvedSol);
    }

}
