package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.ArrayList;

public class LocalSearch {

    private Instance instance;

    private InsertionMethod insertionMethod;

    public LocalSearch(Instance instance) {
        this.instance = instance;
        this.insertionMethod = new InsertionMethod(instance);
    }

    public Solution relocate(Solution solution) {
        Solution improvedSol = SolutionUtils.createSolution(instance);
        Solution tempSol = SolutionUtils.createSolution(instance);
        SolutionUtils.copyFromTo(solution, improvedSol);
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (Request request : instance.requests) {
                SolutionUtils.copyFromTo(improvedSol, tempSol);
                int vehicle = SolutionUtils.findRequestVehicleOwner(tempSol, request.requestId);
                if (vehicle != -1) {
                    tempSol.removeRequest(vehicle, request);
                    instance.solutionEvaluation(tempSol, vehicle);
                }
                for (int k = 0; k < tempSol.tours.size(); k++) {
                    Position position = insertionMethod.calculateInsertion(request, tempSol, k);
                    if (position.cost < Double.MAX_VALUE) {
                        tempSol.insertRequest(instance, request.requestId, k, position.pickupPos, position.deliveryPos);
                        instance.solutionEvaluation(tempSol, k);
                        double improvedCost = MathUtils.round(improvedSol.totalCost);
                        double tempCost = MathUtils.round(tempSol.totalCost);
                        if (tempCost < improvedCost) {
                            SolutionUtils.copyFromTo(tempSol, improvedSol);
                            improvement = true;
                        }
                        tempSol.removeRequest(k, request);
                        instance.solutionEvaluation(tempSol, k);
                    }
                }
            }
        }
        if (improvedSol.feasible) {
            SolutionUtils.removeEmptyVehicles(improvedSol);
            instance.solutionEvaluation(improvedSol);
            improvedSol = SolutionUtils.getBest(solution, improvedSol);
            if (improvedSol == null) improvedSol = solution;
        } else {
            improvedSol = solution;
        }
        return improvedSol;
    }

    public Solution exchange(Solution solution) {
        Solution improvedSol = SolutionUtils.copy(solution);
        Solution tempSol = SolutionUtils.copy(solution);
        boolean improvement = true;
        int v1, v2;
        double improvedCost = MathUtils.round(improvedSol.totalCost);
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (Request r1 : instance.requests) {
                v1 = SolutionUtils.findRequestVehicleOwner(tempSol, r1.requestId);
                if (v1 != -1) {
                    v1RouteOrigin = new ArrayList<>(tempSol.tours.get(v1));
                    tempSol.removeRequest(v1, r1);
                    instance.solutionEvaluation(tempSol, v1);
                    for (Request r2 : instance.requests) {
                        v2 = SolutionUtils.findRequestVehicleOwner(tempSol, r2.requestId);
                        if (r1 != r2 && v1 != v2 && v2 != -1) {
                            Position v1Pos = insertionMethod.calculateInsertion(r2, tempSol, v1);
                            if (v1Pos.cost < Double.MAX_VALUE) {
                                v2RouteOrigin = new ArrayList<>(tempSol.tours.get(v2));
                                tempSol.removeRequest(v2, r2);
                                instance.solutionEvaluation(tempSol, v2);
                                Position v2Pos = insertionMethod.calculateInsertion(r1, tempSol, v2);
                                if (v2Pos.cost < Double.MAX_VALUE) {
                                    tempSol.insertRequest(instance, r2.requestId, v1, v1Pos.pickupPos, v1Pos.deliveryPos);
                                    instance.solutionEvaluation(tempSol, v1);
                                    tempSol.insertRequest(instance, r1.requestId, v2, v2Pos.pickupPos, v2Pos.deliveryPos);
                                    instance.solutionEvaluation(tempSol, v2);
                                    tempCost = MathUtils.round(tempSol.totalCost);
                                    instance.solutionEvaluation(tempSol);
                                    if (tempCost < improvedCost) {
                                        improvedSol = SolutionUtils.copy(tempSol);
                                        improvedCost = tempCost;
                                        improvement = true;
                                    }
                                    tempSol.removeRequest(v2, r1);
                                }
                                tempSol.removeRequest(v1, r2);
                                tempSol.tours.set(v2, v2RouteOrigin);
                                tempSol.requestIds.get(v2).add(r2.requestId);
                            }
                        }
                    }
                    tempSol.tours.set(v1, v1RouteOrigin);
                    tempSol.requestIds.get(v1).add(r1.requestId);
                }
            }
            if (improvement) {
                tempSol = SolutionUtils.copy(improvedSol);
            }
        }
        if (improvedSol.feasible) {
            SolutionUtils.removeEmptyVehicles(improvedSol);
            instance.solutionEvaluation(improvedSol);
            improvedSol = SolutionUtils.getBest(solution, improvedSol);
            if (improvedSol == null) improvedSol = solution;
        } else {
            improvedSol = solution;
        }
        return improvedSol;
    }
}
