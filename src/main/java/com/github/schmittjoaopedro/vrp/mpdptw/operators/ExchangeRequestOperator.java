package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;

import java.util.ArrayList;
import java.util.Random;

/*
 * Exchange: here we select two requests from two different routes, and we try to insert them into each other's routes
 * at the smallest cost. If a better solution can be found, the exchange is accepted, otherwise we go back to the
 * previous solution. This is executed for all pairs of requests until no improvements can be found.
 */
public class ExchangeRequestOperator {

    private ProblemInstance instance;

    private InsertionOperator insertionOperator;

    public ExchangeRequestOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.insertionOperator = new InsertionOperator(instance, random);
    }

    public Solution exchange(Solution solution) {
        Solution improvedSol = SolutionUtils.copy(solution);
        Solution tempSol = SolutionUtils.copy(solution);
        boolean improvement = true;
        int v1, v2;
        double improvedCost = Maths.round(improvedSol.totalCost + improvedSol.timeWindowPenalty);
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (int r1 = 0; r1 < instance.getNumReq(); r1++) {
                if (instance.isFullyIdle(r1)) {
                    v1 = SolutionUtils.findRequestVehicleOwner(tempSol, r1);
                    v1RouteOrigin = new ArrayList<>(tempSol.tours.get(v1));
                    SolutionUtils.removeRequest(instance, tempSol, v1, r1);
                    for (int r2 = 0; r2 < instance.getNumReq(); r2++) {
                        if (instance.isFullyIdle(r2)) {
                            v2 = SolutionUtils.findRequestVehicleOwner(tempSol, r2);
                            if (r1 != r2 && v1 != v2) {
                                if (insertionOperator.insertRequestOnVehicle(tempSol, v1, r2, PickupMethod.Random, InsertionMethod.Greedy)) {
                                    v2RouteOrigin = new ArrayList<>(tempSol.tours.get(v2));
                                    SolutionUtils.removeRequest(instance, tempSol, v2, r2);
                                    tempSol.requests.get(v1).add(r2);
                                    if (insertionOperator.insertRequestOnVehicle(tempSol, v2, r1, PickupMethod.Random, InsertionMethod.Greedy)) {
                                        tempSol.requests.get(v2).add(r1);
                                        tempCost = Maths.round(tempSol.totalCost + tempSol.timeWindowPenalty);
                                        if (tempCost < improvedCost) {
                                            improvedSol = SolutionUtils.copy(tempSol);
                                            improvedCost = tempCost;
                                            improvement = true;
                                        }
                                        SolutionUtils.removeRequest(instance, tempSol, v2, r1);
                                    }
                                    SolutionUtils.removeRequest(instance, tempSol, v1, r2);
                                    tempSol.tours.set(v2, v2RouteOrigin);
                                    tempSol.requests.get(v2).add(r2);
                                }
                            }
                        }
                    }
                    tempSol.tours.set(v1, v1RouteOrigin);
                    tempSol.requests.get(v1).add(r1);
                }
            }
            if (improvement) {
                tempSol = SolutionUtils.copy(improvedSol);
            }
        }
        instance.solutionEvaluation(improvedSol);
        return SolutionUtils.getBest(solution, improvedSol);
    }

}
