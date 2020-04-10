package com.github.schmittjoaopedro.vrp.thesis.algorithms.lns;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.ShawRemoval;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Random;

public class CostMinimizerLNS extends LNS {

    private double tolerance = 0.05;

    private double coolingRate = 0.99975;

    private double removeControl = 0.4;

    public CostMinimizerLNS(Instance instance, Random random) {
        super(instance, random);
        // Define operators
        insertionOperator = new RegretInsertion(random, instance, (sol, inst) -> 1);
        removalOperator = new ShawRemoval(instance, random);
        // Create initial solution
        solution = SolutionUtils.createSolution(instance);
    }

    @Override
    public void init(Solution solutionBase) {
        solution = insertNewRequests(insertionOperator, solutionBase);
        executeLocalSearch(solution);
        SolutionUtils.removeEmptyVehicles(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);
        solutionBest = SolutionUtils.copy(solution);

        // Prepare algorithm structures and initial parameters
        TABU_LIST.clear();
        T = calculateStartingTemperature(solution.totalCost, tolerance);
    }

    @Override
    public void optimize(int iteration) {
        Solution solutionNew = SolutionUtils.copy(solution);

        int q = generateRandomNumRemoveRequests(removeControl);
        removeRequests(solutionNew, q);
        insertRequests(solution);
        instance.solutionEvaluation(solutionNew);

        if (accept(solutionNew, solution, T)) {
            Integer hash = solutionNew.getHash();
            if (!TABU_LIST.contains(hash)) {
                TABU_LIST.add(hash);
                if (solutionNew.calculateObjective(instance) < solutionBest.calculateObjective(instance)) {
                    if (solutionNew.feasible) {
                        SolutionUtils.removeEmptyVehicles(solutionNew);
                        executeLocalSearch(solutionNew);
                        SolutionUtils.removeEmptyVehicles(solutionNew);
                        feasibleSolutionBest = SolutionUtils.copy(solutionNew);
                    }
                    solutionBest = SolutionUtils.copy(solutionNew);
                }
            }
            solution = solutionNew;
        }
        T = T * coolingRate;
    }

    @Override
    public void resetToInitialSolution(Solution solution) {
        setBaseSolution(solution);
        T = calculateStartingTemperature(solution.totalCost, tolerance);
    }

}
