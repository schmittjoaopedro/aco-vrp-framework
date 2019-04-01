package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.Ant;
import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.Solution;

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

    public Ant exchange(Ant ant) {
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, improvedAnt);
        AntUtils.copyFromTo(improvedAnt, tempAnt);
        boolean improvement = true;
        int v1, v2;
        double improvedCost = improvedAnt.totalCost + improvedAnt.timeWindowPenalty;
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (int r1 = 0; r1 < instance.noReq; r1++) {
                v1 = AntUtils.findRequestVehicleOwner(tempAnt, r1);
                v1RouteOrigin = new ArrayList<>(tempAnt.tours.get(v1));
                AntUtils.removeRequest(instance, tempAnt, v1, r1);
                for (int r2 = 0; r2 < instance.noReq; r2++) {
                    if (r1 != r2) {
                        v2 = AntUtils.findRequestVehicleOwner(tempAnt, r2);
                        v2RouteOrigin = new ArrayList<>(tempAnt.tours.get(v2));
                        AntUtils.removeRequest(instance, tempAnt, v2, r2);
                        if (insertionOperator.insertRequestOnVehicle(r2, tempAnt.tours.get(v1), PickupMethod.Random, InsertionMethod.Greedy)) {
                            tempAnt.requests.get(v1).add(r2);
                            if (insertionOperator.insertRequestOnVehicle(r1, tempAnt.tours.get(v2), PickupMethod.Random, InsertionMethod.Greedy)) {
                                tempAnt.requests.get(v2).add(r1);
                                instance.restrictionsEvaluation(tempAnt);
                                tempCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                                if (tempCost < improvedCost) {
                                    AntUtils.copyFromTo(tempAnt, improvedAnt);
                                    improvedCost = tempCost;
                                    improvement = true;
                                }
                                AntUtils.removeRequest(instance, tempAnt, v2, r1);
                            }
                            AntUtils.removeRequest(instance, tempAnt, v1, r2);
                        }
                        tempAnt.tours.set(v2, v2RouteOrigin);
                        tempAnt.requests.get(v2).add(r2);
                    }
                }
                tempAnt.tours.set(v1, v1RouteOrigin);
                tempAnt.requests.get(v1).add(r1);
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        return AntUtils.getBetterAnt(ant, improvedAnt);
    }

    public Solution exchange(Solution solution) {
        Solution improvedSol = solution.copy();
        Solution tempSol = improvedSol.copy();
        boolean improvement = true;
        int v1, v2;
        double improvedCost = improvedSol.totalCost;
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (int r1 = 0; r1 < instance.noReq; r1++) {
                v1 = tempSol.findRequestVehicleOwner(r1);
                v1RouteOrigin = new ArrayList<>(tempSol.tours.get(v1));
                tempSol.removeRequest(instance, v1, r1);
                for (int r2 = 0; r2 < instance.noReq; r2++) {
                    if (r1 != r2) {
                        v2 = tempSol.findRequestVehicleOwner(r2);
                        v2RouteOrigin = new ArrayList<>(tempSol.tours.get(v2));
                        tempSol.removeRequest(instance, v2, r2);
                        if (insertionOperator.insertRequestOnVehicle(r2, tempSol.tours.get(v1), PickupMethod.Random, InsertionMethod.Greedy)) {
                            tempSol.requests.get(v1).add(r2);
                            if (insertionOperator.insertRequestOnVehicle(r1, tempSol.tours.get(v2), PickupMethod.Random, InsertionMethod.Greedy)) {
                                tempSol.requests.get(v2).add(r1);
                                instance.restrictionsEvaluation(tempSol);
                                tempCost = tempSol.totalCost;
                                if (tempCost < improvedCost) {
                                    improvedSol = tempSol.copy();
                                    improvedCost = tempCost;
                                    improvement = true;
                                }
                                tempSol.removeRequest(instance, v2, r1);
                            }
                            tempSol.removeRequest(instance, v1, r2);
                        }
                        tempSol.tours.set(v2, v2RouteOrigin);
                        tempSol.requests.get(v2).add(r2);
                    }
                }
                tempSol.tours.set(v1, v1RouteOrigin);
                tempSol.requests.get(v1).add(r1);
            }
        }
        instance.restrictionsEvaluation(improvedSol);
        return solution.getBestSolution(improvedSol);
    }

}
