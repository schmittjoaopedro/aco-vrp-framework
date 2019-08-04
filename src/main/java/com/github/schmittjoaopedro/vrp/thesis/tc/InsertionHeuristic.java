package com.github.schmittjoaopedro.vrp.thesis.tc;


import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

public class InsertionHeuristic {

    private Instance instance;

    private InsertionMethod insertionMethod;

    public InsertionHeuristic(Instance instance) {
        this.instance = instance;
        this.insertionMethod = new InsertionMethod(instance);
    }

    public Solution createInitialSolution() {
        Solution solution = SolutionUtils.createSolution(instance);
        instance.solutionEvaluation(solution);
        for (Request request : instance.requests) {
            int kMax = solution.tours.size();
            for (int k = 0; k < kMax; k++) {
                Position position = insertionMethod.calculateInsertion(request, solution, k);
                if (position.cost < Double.MAX_VALUE) {
                    solution.insertRequest(instance, request.requestId, k, position.pickupPos, position.deliveryPos);
                    instance.solutionEvaluation(solution);
                    break;
                }
            }
        }
        return solution;
    }

}
