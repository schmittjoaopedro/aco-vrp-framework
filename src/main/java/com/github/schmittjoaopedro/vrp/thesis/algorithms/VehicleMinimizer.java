package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.noise.NoiseOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.RandomRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.ShawRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.WorstRemoval;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Random;

public class VehicleMinimizer extends ALNS {

    private int segment = 100;

    private double tolerance = 0.35;

    private double coolingRate = 0.9999;

    private double removeControl = 0.4;

    private RegretInsertion greedyInsertion;

    private double[] sigmas = {33, 9, 13};

    private double reactionFactor = 0.1;

    public VehicleMinimizer(Instance instance, Random random) {
        super(instance, random);
    }

    public void init() {
        // Create initial solution
        greedyInsertion = new RegretInsertion(random, instance, (solution, instance) -> 1);
        solution = SolutionUtils.createSolution(instance);
        greedyInsertion.insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        executeLocalSearch(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);

        // Add insertion heuristics
        insertionOperators.add(greedyInsertion);
        insertionOperators.add(new RegretInsertion(random, instance, (solution, instance) -> 2));
        insertionOperators.add(new RegretInsertion(random, instance, (solution, instance) -> 3));
        insertionOperators.add(new RegretInsertion(random, instance, (solution, instance) -> 4));
        insertionOperators.add(new RegretInsertion(random, instance, (solution, instance) -> solution.requestBankSize(instance)));
        // Add removal heuristics
        removalOperators.add(new RandomRemoval(instance, random));
        removalOperators.add(new WorstRemoval(instance, random));
        removalOperators.add(new ShawRemoval(instance, random));
        // Add noise heuristics
        noiseOperators.add(new NoiseOperator(0));
        noiseOperators.add(new NoiseOperator(1));

        // Prepare algorithm structures and initial parameters
        resetOperatorsWeights();
        TABU_LIST.clear();
        removeLastVehicle(solution);
        instance.solutionEvaluation(solution);
        T = calculateStartingTemperature(solution.totalCost, tolerance);
        solutionBest = SolutionUtils.copy(solution);
    }

    public void optimize(int iteration) {
        Solution solutionNew = SolutionUtils.copy(solution);

        int q = generateRandomNumRemoveRequests(removeControl);
        int noiseHeuristic = rouletteSelection(noiseOperators);
        int removeHeuristic = removeRequests(solutionNew, q);
        int insertHeuristic = insertRequests(solutionNew, noiseHeuristic);
        increaseOperatorsUsageCount(removeHeuristic, insertHeuristic, noiseHeuristic);
        instance.solutionEvaluation(solutionNew);

        if (accept(solutionNew, solution, T)) {
            Integer hash = solutionNew.getHash();
            if (!TABU_LIST.contains(hash)) {
                TABU_LIST.add(hash);
                if (solutionNew.calculateObjective(instance) < solutionBest.calculateObjective(instance)) {
                    executeLocalSearch(solutionNew);
                    solutionBest = SolutionUtils.copy(solutionNew);
                    increaseOperatorsScore(removeHeuristic, insertHeuristic, noiseHeuristic, sigmas[0]);
                } else if (solutionNew.calculateObjective(instance) < solution.calculateObjective(instance)) {
                    increaseOperatorsScore(removeHeuristic, insertHeuristic, noiseHeuristic, sigmas[1]);
                } else {
                    increaseOperatorsScore(removeHeuristic, insertHeuristic, noiseHeuristic, sigmas[2]);
                }
            }
            solution = solutionNew;
        }
        T = T * coolingRate;
        if (iteration % segment == 0) {
            updateOperatorWeights(reactionFactor);
        }

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

    public void removeLastVehicle(Solution solution) {
        int vehicle = solution.tours.size() - 1;
        solution.tours.remove(vehicle);
        solution.requestIds.remove(vehicle);
        instance.solutionEvaluation(solution);
    }

}
