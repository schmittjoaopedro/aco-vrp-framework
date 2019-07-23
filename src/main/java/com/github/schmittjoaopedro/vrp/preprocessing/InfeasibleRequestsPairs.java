package com.github.schmittjoaopedro.vrp.preprocessing;

import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.RandomRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfeasibleRequestsPairs {

    private ProblemInstance instance;

    private Random random;

    private InsertionOperator insertionOperator;

    private RelocateNodeOperator relocateNodeOperator;

    public InfeasibleRequestsPairs(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionOperator = new InsertionOperator(instance, random);
        this.relocateNodeOperator = new RelocateNodeOperator(instance);
    }

    public boolean[][] calculateFeasibilityPairs() {
        boolean[][] requestsPairs = new boolean[instance.getNumReq()][instance.getNumReq()];
        for (int i = 0; i < instance.getNumReq(); i++) {
            int noF = 0;
            for (int j = i + 1; j < instance.getNumReq(); j++) {
                requestsPairs[i][j] = isFeasible(i, j);
                requestsPairs[j][i] = requestsPairs[i][j];
                if (!requestsPairs[i][j]) {
                    noF++;
                }
            }
            System.out.println(noF);
        }
        return requestsPairs;
    }

    private boolean isFeasible(int req1, int req2) {
        // Simulate vehicle requests
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);
        // Route
        ArrayList<Integer> route1 = getOptimalRoute(req1);
        ArrayList<Integer> route2 = getOptimalRoute(req2);
        ArrayList<Integer> routeMerged = new ArrayList<>();
        routeMerged.add(0);
        routeMerged.addAll(route1.subList(1, route1.size() - 1));
        routeMerged.addAll(route2.subList(1, route2.size() - 1));
        routeMerged.add(0);
        // Temporary ant
        Solution solution = SolutionUtils.createNewSolution(instance);
        solution.tours.add(routeMerged);
        solution.requests.add(requests);
        instance.restrictionsEvaluation(solution);
        // Try to find feasible route
        solution = optimize(solution);
        instance.restrictionsEvaluation(solution, false);
        return solution.feasible;
    }

    public Solution optimize(Solution solution) {
        Solution tempSol = solution;
        boolean improvement = true;
        double oldCost = solution.totalCost + solution.timeWindowPenalty;
        double newCost;
        while (improvement) {
            improvement = false;
            tempSol = relocateNodeOperator.relocate(tempSol);
            tempSol = optimizeInner(tempSol);
            newCost = tempSol.totalCost + tempSol.timeWindowPenalty;
            if (tempSol.feasible) break;
            if (newCost < oldCost) {
                oldCost = tempSol.totalCost + tempSol.timeWindowPenalty;
                improvement = true;
            }
        }
        return tempSol;
    }

    public Solution optimizeInner(Solution solution) {
        Solution tempSol = SolutionUtils.createNewSolution(instance);
        Solution improvedSol = SolutionUtils.createNewSolution(instance);
        SolutionUtils.copyFromTo(solution, tempSol);
        SolutionUtils.copyFromTo(solution, improvedSol);
        boolean improvement = true;
        boolean improved = false;
        while (improvement) {
            List<Req> removedRequests = new RandomRemoval(random, instance).removeRequests(tempSol, 1);
            insertionOperator.insertRequests(tempSol, removedRequests, PickupMethod.Random, 0);
            instance.restrictionsEvaluation(tempSol);
            improvement = instance.getBest(improvedSol, tempSol) == tempSol;
            if (improvement) {
                SolutionUtils.copyFromTo(tempSol, improvedSol);
                improved = true;
                if (tempSol.feasible) break;
            }
        }
        if (improved) {
            return improvedSol;
        } else {
            return solution;
        }
    }

    private ArrayList<Integer> getOptimalRoute(int requestId) {
        OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requestId, instance);
        optimalRequestSolver.optimize();
        ArrayList<Integer> newTour = new ArrayList<>();
        for (int i : optimalRequestSolver.getBestRoute()) {
            newTour.add(i);
        }
        return newTour;
    }

}
