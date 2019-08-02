package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Random;

public class CostMinimizer {

    private Instance instance;

    private Random random;

    private Solution feasibleSolutionBest;

    private Solution solutionBest;

    private Solution solution;

    private InsertionHeuristic insertionHeuristic;

    public CostMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {
        insertionHeuristic = new InsertionHeuristic(instance);
        solution = insertionHeuristic.createInitialSolution();
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);
    }

    public void optimize(int iteration) {

    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }
}
