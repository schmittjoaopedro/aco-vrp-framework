package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;

import java.util.Random;

/*
 * Relocate: in relocate improvement, we select a request and try to insert it into every other other route. The accepted
 * move is the one leading the greatest decrease in cost among all routes. If no improvement solution ca be found, we test
 * the next request. This is executed for all requests until no improvements can be found.
 */
public class RelocateRequestOperator {

    private ProblemInstance instance;

    private InsertionMethod insertionMethod;

    public RelocateRequestOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.insertionMethod = new InsertionMethod(instance, random, true);
    }

    public Solution relocate(Solution solution) {
        Solution improvedSol = SolutionUtils.createNewSolution(instance);
        Solution tempSol = SolutionUtils.createNewSolution(instance);
        SolutionUtils.copyFromTo(solution, improvedSol);
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int requestId = 0; requestId < instance.getNumReq(); requestId++) {
                SolutionUtils.copyFromTo(improvedSol, tempSol);
                int vehicle = SolutionUtils.findRequestVehicleOwner(tempSol, requestId);
                if (instance.isFullyIdle(requestId)) { // Request fully not visited yet
                    SolutionUtils.removeRequest(instance, tempSol, vehicle, requestId);
                    SolutionUtils.removeEmptyVehicles(tempSol);
                    SolutionUtils.addEmptyVehicle(tempSol);
                    instance.solutionEvaluation(tempSol, vehicle);
                    for (int k = 0; k < tempSol.tours.size(); k++) {
                        if (insertionMethod.insertRequestOnVehicle(tempSol, k, requestId, PickupMethod.Random)) {
                            tempSol.requests.get(k).add(requestId);
                            double improvedCost = Maths.round(improvedSol.totalCost + improvedSol.timeWindowPenalty);
                            double tempCost = Maths.round(tempSol.totalCost + tempSol.timeWindowPenalty);
                            if (tempCost < improvedCost) {
                                SolutionUtils.removeEmptyVehicles(tempSol);
                                SolutionUtils.copyFromTo(tempSol, improvedSol);
                                SolutionUtils.addEmptyVehicle(tempSol);
                                improvement = true;
                            }
                            SolutionUtils.removeRequest(instance, tempSol, k, requestId);
                            instance.solutionEvaluation(tempSol, k);
                        }
                    }
                } else if (instance.getDelivery(requestId).isIdle()) {
                    if (insertionMethod.improveRequestOnVehicle(tempSol, vehicle, requestId, PickupMethod.Random)) {
                        double improvedCost = Maths.round(improvedSol.totalCost + improvedSol.timeWindowPenalty);
                        double tempCost = Maths.round(tempSol.totalCost + tempSol.timeWindowPenalty);
                        if (tempCost < improvedCost) {
                            SolutionUtils.copyFromTo(tempSol, improvedSol);
                            improvement = true;
                        }
                    }
                }
            }
        }
        instance.solutionEvaluation(improvedSol);
        return instance.getBest(solution, improvedSol);
    }

}
