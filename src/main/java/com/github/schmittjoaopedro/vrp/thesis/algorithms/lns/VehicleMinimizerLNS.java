package com.github.schmittjoaopedro.vrp.thesis.algorithms.lns;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.ShawRemoval;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Random;

public class VehicleMinimizerLNS extends LNS {

    private double tolerance = 0.35;

    private double coolingRate = 0.9999;

    private double removeControl = 0.4;

    public VehicleMinimizerLNS(Instance instance, Random random) {
        super(instance, random);
        // Set heuristics
        insertionOperator = new RegretInsertion(random, instance, (sol, inst) -> 1);
        removalOperator = new ShawRemoval(instance, random);
        // Create initial solution
        solution = SolutionUtils.createSolution(instance);
    }

    @Override
    public void init(Solution solutionBase) {
        solution = insertNewRequests(insertionOperator, solutionBase);
        feasibleSolutionBest = SolutionUtils.copy(solution);

        // Prepare algorithm structures and initial parameters
        TABU_LIST.clear();
        removeLastVehicle(solution);
        instance.solutionEvaluation(solution);
        T = calculateStartingTemperature(solution.totalCost, tolerance);
        solutionBest = SolutionUtils.copy(solution);
    }

    @Override
    public void optimize(int iteration) {
        Solution solutionNew = SolutionUtils.copy(solution);

        int q = generateRandomNumRemoveRequests(removeControl);
        removeRequests(solutionNew, q);
        insertRequests(solutionNew);
        instance.solutionEvaluation(solutionNew);

        if (accept(solutionNew, solution, T)) {
            Integer hash = solutionNew.getHash();
            if (!TABU_LIST.contains(hash)) {
                TABU_LIST.add(hash);
                if (solutionNew.calculateObjective(instance) < solutionBest.calculateObjective(instance)) {
                    solutionBest = SolutionUtils.copy(solutionNew);
                }
            }
            solution = solutionNew;
        }
        T = T * coolingRate;

        // If a feasible solution is found, copy this solution as new feasible best, reduce
        // one vehicle to start. Therefore, the alg. will start to search for a new feasible solution
        // with one less vehicle.
        if (solution.feasible) {
            feasibleSolutionBest = SolutionUtils.copy(solution);
            removeLastVehicle(solution);
            instance.solutionEvaluation(solution);
            solutionBest = SolutionUtils.copy(solution);
            T = calculateStartingTemperature(solution.totalCost, tolerance);
        }
    }

    @Override
    public void resetToInitialSolution(Solution solution) {

    }

    public void removeLastVehicle(Solution solution) {
        if (solution.tours.size() > 1) {
            int vehicle = solution.tours.size() - 1;
            solution.tours.remove(vehicle);
            solution.requestIds.remove(vehicle);
            instance.solutionEvaluation(solution);
        }
    }

}
