package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;

import java.util.Random;

public class InsertionHeuristic extends InsertionOperator {

    private InsertionMethod insertionMethod;

    public InsertionHeuristic(ProblemInstance instance, Random random) {
        super(instance, random);
        this.insertionMethod = new InsertionMethod(instance, random);
    }

    public Solution createInitialSolution() {
        Solution solution = SolutionUtils.createNewSolution(instance);
        for (int r = 0; r < instance.getNumReq(); r++) {
            addRequest(solution, r);
        }
        instance.solutionEvaluation(solution);
        return solution;
    }

    public void addRequest(Solution solution, int r) {
        boolean found = false;
        int kMax = solution.tours.size();
        for (int k = 0; k < kMax; k++) {
            if (insertionMethod.insertRequestOnVehicle(solution, k, r, InsertionMethod.PickupMethod.Random)) {
                solution.requests.get(k).add(r);
                found = true;
                break;
            }
        }
        if (!found) {
            SolutionUtils.addEmptyVehicle(solution);
            OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(r, instance);
            optimalRequestSolver.optimize();
            int lastK = solution.tours.size() - 1;
            solution.tours.get(lastK).clear();
            for (int i = 0; i < optimalRequestSolver.getBestRoute().length; i++) {
                solution.tours.get(lastK).add(optimalRequestSolver.getBestRoute()[i]);
            }
            solution.requests.get(lastK).add(r);
            instance.solutionEvaluation(solution, lastK);
        }
    }

}
