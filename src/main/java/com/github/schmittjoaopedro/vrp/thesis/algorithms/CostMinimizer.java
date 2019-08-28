package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.noise.NoiseOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.RandomRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.ShawRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal.WorstRemoval;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.ArrayList;
import java.util.Random;

public class CostMinimizer extends ALNS {

    private int segment = 100;

    private double tolerance = 0.05;

    private double coolingRate = 0.99975;

    private double removeControl = 0.4;

    private RegretInsertion greedyInsertion;

    private double[] sigmas = {33, 9, 13};

    private double reactionFactor = 0.1;

    public CostMinimizer(Instance instance, Random random) {
        super(instance, random);
        // Add insertion heuristics
        greedyInsertion = new RegretInsertion(random, instance, (sol, inst) -> 1);
        insertionOperators.add(greedyInsertion);
        insertionOperators.add(new RegretInsertion(random, instance, (sol, inst) -> 2));
        insertionOperators.add(new RegretInsertion(random, instance, (sol, inst) -> 3));
        insertionOperators.add(new RegretInsertion(random, instance, (sol, inst) -> 4));
        insertionOperators.add(new RegretInsertion(random, instance, (sol, inst) -> sol.removeRequestsNumber(inst)));
        // Add removal heuristics
        removalOperators.add(new RandomRemoval(instance, random));
        removalOperators.add(new WorstRemoval(instance, random));
        removalOperators.add(new ShawRemoval(instance, random));
        // Add noise heuristics
        noiseOperators.add(new NoiseOperator(0));
        noiseOperators.add(new NoiseOperator(1));
        // Create initial solution
        solution = SolutionUtils.createSolution(instance);
    }

    public void init(Solution solutionBase) {
        Solution newSolution = SolutionUtils.createSolution(instance);
        if (solutionBase != null) {
            for (int k = 0; k < solutionBase.tours.size(); k++) {
                newSolution.tours.set(k, new ArrayList<>(solutionBase.tours.get(k)));
                newSolution.requestIds.set(k, new ArrayList<>(solutionBase.requestIds.get(k)));
            }
        }
        solution = newSolution;
        instance.solutionEvaluation(solution);
        greedyInsertion.insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        executeLocalSearch(solution);
        SolutionUtils.removeEmptyVehicles(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);
        solutionBest = SolutionUtils.copy(solution);

        // Prepare algorithm structures and initial parameters
        resetOperatorsWeights();
        TABU_LIST.clear();
        T = calculateStartingTemperature(solution.totalCost, tolerance);
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
                    if (solutionNew.feasible) {
                        SolutionUtils.removeEmptyVehicles(solutionNew);
                        executeLocalSearch(solutionNew);
                        SolutionUtils.removeEmptyVehicles(solutionNew);
                        feasibleSolutionBest = SolutionUtils.copy(solutionNew);
                    }
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
    }

    public void resetToInitialSolution(Solution solution) {
        setBaseSolution(solution);
        T = calculateStartingTemperature(solution.totalCost, tolerance);
    }
}
