package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.PickupMethod;

import java.util.Random;

public class InsertionHeuristic {

    private ProblemInstance instance;

    private InsertionOperator insertionOperator;

    private Random random;

    public InsertionHeuristic(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionOperator = new InsertionOperator(instance, random);
    }

    public Solution createInitialSolution() {
        Solution solution = new Solution();
        for (int r = 0; r < instance.getNumReq(); r++) {
            boolean found = false;
            int kMax = solution.tours.size();
            for (int k = 0; k < kMax; k++) {
                if (insertionOperator.insertRequestOnVehicle(r, solution.tours.get(k), PickupMethod.Random, InsertionMethod.Greedy)) {
                    solution.requests.get(k).add(r);
                    found = true;
                    break;
                }
            }
            if (!found) {
                solution.addEmptyVehicle();
                OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(r, instance);
                optimalRequestSolver.optimize();
                int lastK = solution.tours.size() - 1;
                solution.tours.get(lastK).clear();
                for (int i = 0; i < optimalRequestSolver.getBestRoute().length; i++) {
                    solution.tours.get(lastK).add(optimalRequestSolver.getBestRoute()[i]);
                }
                solution.requests.get(lastK).add(r);
            }
        }
        instance.restrictionsEvaluation(solution);
        return solution;
    }

}
