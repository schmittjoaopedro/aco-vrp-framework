package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.*;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.*;

public class VehicleMinimizer {

    private Instance instance;

    private Random random;

    private double tolerance = 0.35;

    private double coolingRate = 0.9999;

    private double removeControl = 0.4;

    private final Set<Integer> TABU_LIST = new HashSet<>();

    private Solution feasibleSolutionBest;

    private Solution solutionBest;

    private Solution solution;

    private RegretInsertion greedyInsertion;

    private double[] scoreWeight = {33, 9, 13};

    private double reactionFactor = 0.1;

    private double T;

    private List<Operator> noiseOperators = new ArrayList<>();

    private List<Operator> insertionOperators = new ArrayList<>();

    private List<Operator> removalOperators = new ArrayList<>();

    public VehicleMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {
        // Create initial solution
        greedyInsertion = new RegretInsertion(random, instance, (solution, instance) -> 1);
        solution = SolutionUtils.createSolution(instance);
        greedyInsertion.insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
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
        resetAdaptiveWeight();
        TABU_LIST.clear();
        decreaseVehicleNumber(solution);
        instance.solutionEvaluation(solution);
        T = ALNS.calcTemp(solution.totalCost, tolerance);
        solutionBest = SolutionUtils.copy(solution);
    }

    public void optimize(int iteration) {
        // Copy current solution
        Solution sNew = SolutionUtils.copy(solution);

        // Generate random Q
        int q = 0;
        while (q < 4) {
            q = (int) (random.nextDouble() * Math.min(100, (int) (removeControl * instance.numRequests)));
        }

        // Noise, remove and insert
        int noiseHeuristic = ALNS.rouletteSelection(noiseOperators, random);
        int removeHeuristic = requestRemove(sNew, q);
        int insertHeuristic = requestInsert(sNew, noiseHeuristic);
        instance.solutionEvaluation(sNew);

        // Calculate Score for each heuristic
        removalOperators.get(removeHeuristic).increaseCount();
        insertionOperators.get(insertHeuristic).increaseCount();
        noiseOperators.get(noiseHeuristic).increaseCount();
        if (ALNS.accept(sNew, solution, T, instance, random)) {
            Integer hashNb = sNew.getHash();
            if (!TABU_LIST.contains(hashNb)) {
                TABU_LIST.add(hashNb);
                if (sNew.objFunction(instance) < solutionBest.objFunction(instance)) {
                    solutionBest = SolutionUtils.copy(sNew);
                    removalOperators.get(removeHeuristic).addScore(scoreWeight[0]);
                    insertionOperators.get(insertHeuristic).addScore(scoreWeight[0]);
                    noiseOperators.get(noiseHeuristic).addScore(scoreWeight[0]);
                } else if (sNew.objFunction(instance) < solution.objFunction(instance)) {
                    removalOperators.get(removeHeuristic).addScore(scoreWeight[1]);
                    insertionOperators.get(insertHeuristic).addScore(scoreWeight[1]);
                    noiseOperators.get(noiseHeuristic).addScore(scoreWeight[1]);
                } else {
                    removalOperators.get(removeHeuristic).addScore(scoreWeight[2]);
                    insertionOperators.get(insertHeuristic).addScore(scoreWeight[2]);
                    noiseOperators.get(noiseHeuristic).addScore(scoreWeight[2]);
                }
            }
            solution = sNew;
        }
        T = T * coolingRate;
        if (iteration % 100 == 0) {
            ALNS.updateAdaptiveWeight(removalOperators, reactionFactor);
            ALNS.updateAdaptiveWeight(insertionOperators, reactionFactor);
            ALNS.updateAdaptiveWeight(noiseOperators, reactionFactor);
        }

        if (solution.feasible) {
            feasibleSolutionBest = SolutionUtils.copy(solution);
            decreaseVehicleNumber(solution);
            instance.solutionEvaluation(solution);
            solutionBest = SolutionUtils.copy(solution);
            T = ALNS.calcTemp(solution.totalCost, tolerance);
        }

    }

    public void decreaseVehicleNumber(Solution solution) {
        int vehicle = solution.tours.size() - 1;
        solution.tours.remove(vehicle);
        solution.requestIds.remove(vehicle);
        instance.solutionEvaluation(solution);
    }

    private void resetAdaptiveWeight() {
        noiseOperators.forEach(Operator::resetOperator);
        removalOperators.forEach(Operator::resetOperator);
        insertionOperators.forEach(Operator::resetOperator);
    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }

    private int requestInsert(Solution solution, int noiseHeuristic) {
        int insertHeuristic = ALNS.rouletteSelection(insertionOperators, random);
        int useNoise = ((NoiseOperator) noiseOperators.get(noiseHeuristic)).getUseNoise();
        ((InsertionOperator) insertionOperators.get(insertHeuristic)).insert(solution, useNoise);
        return insertHeuristic;
    }

    private int requestRemove(Solution solution, int q) {
        int removeHeuristic = ALNS.rouletteSelection(removalOperators, random);
        ((RemovalOperator) removalOperators.get(removeHeuristic)).remove(solution, q);
        return removeHeuristic;
    }

}
