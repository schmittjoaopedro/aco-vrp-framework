package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.RandomRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.ShawRemoval;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.WorstRemoval;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class VehicleMinimizer {

    private Instance instance;

    private Random random;

    private double tolerance = 0.35;

    private double coolingRate = 0.9999;

    private double removeControl = 0.4;

    private Set<Integer> visitedList = new HashSet<>();

    private Solution feasibleSolutionBest;

    private Solution solutionBest;

    private Solution solution;

    private RegretInsertion regretInsertion;

    private RandomRemoval randomRemoval;

    private WorstRemoval worstRemoval;

    private ShawRemoval shawRemoval;

    private double[] scoreWeight = {33, 9, 13};

    private double reactionFactor = 0.1;

    private double[] removalWeight;
    private double[] insertingWeight;
    private double[] noiseWeight;
    private double[] removalScore;
    private double[] insertingScore;
    private double[] noiseScore;
    private int[] removalCount;
    private int[] insertingCount;
    private int[] noiseCount;

    private double T;

    public VehicleMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {
        regretInsertion = new RegretInsertion(random, instance);
        solution = SolutionUtils.createSolution(instance);
        regretInsertion.regretInsert(solution, 1, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);

        randomRemoval = new RandomRemoval(instance, random);
        worstRemoval = new WorstRemoval(instance, random);
        shawRemoval = new ShawRemoval(instance, random);

        resetAdaptiveWeight();
        visitedList.clear();
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
        int useNoise = ALNS.rouletteSelection(noiseWeight, random);
        int removeHeuristic = requestRemove(sNew, q, removalWeight);
        int insertHeuristic = requestInsert(sNew, q, insertingWeight, useNoise);
        instance.solutionEvaluation(sNew);

        // Calculate Score for each heuristic
        ++removalCount[removeHeuristic];
        ++insertingCount[insertHeuristic];
        ++noiseCount[useNoise];
        if (ALNS.accept(sNew, solution, T, instance, random)) {
            Integer hashNb = sNew.getHash();
            if (!visitedList.contains(hashNb)) {
                visitedList.add(hashNb);
                if (sNew.objFunction(instance) < solutionBest.objFunction(instance)) {
                    solutionBest = SolutionUtils.copy(sNew);
                    removalScore[removeHeuristic] += scoreWeight[0];
                    insertingScore[insertHeuristic] += scoreWeight[0];
                    noiseScore[useNoise] += scoreWeight[0];
                } else if (sNew.objFunction(instance) < solution.objFunction(instance)) {
                    removalScore[removeHeuristic] += scoreWeight[1];
                    insertingScore[insertHeuristic] += scoreWeight[1];
                    noiseScore[useNoise] += scoreWeight[1];
                } else {
                    removalScore[removeHeuristic] += scoreWeight[2];
                    insertingScore[insertHeuristic] += scoreWeight[2];
                    noiseScore[useNoise] += scoreWeight[2];
                }
            }
            solution = sNew;
        }
        T = T * coolingRate;
        if (iteration % 100 == 0) {
            //printWeights(temp, sBest);
            ALNS.updateAdaptiveWeight(removalWeight, removalScore, removalCount, reactionFactor);
            ALNS.updateAdaptiveWeight(insertingWeight, insertingScore, insertingCount, reactionFactor);
            ALNS.updateAdaptiveWeight(noiseWeight, noiseScore, noiseCount, reactionFactor);
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
        removalWeight = new double[]{1, 1, 1};
        insertingWeight = new double[]{1, 1, 1, 1, 1};
        noiseWeight = new double[]{1, 1};
        removalScore = new double[]{0, 0, 0};
        insertingScore = new double[]{0, 0, 0, 0, 0};
        noiseScore = new double[]{0, 0};
        removalCount = new int[]{0, 0, 0};
        insertingCount = new int[]{0, 0, 0, 0, 0};
        noiseCount = new int[]{0, 0};
    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }

    // Insert unvisited requests from solution and return used heuristic
    int requestInsert(Solution s, int q, double[] insertingWeight, int useNoise) {
        int insertHeuristic = ALNS.rouletteSelection(insertingWeight, random);
        if (insertHeuristic == 0) regretInsertion.regretInsert(s, 1, useNoise); // Basic Greedy insert
        else if (insertHeuristic == 1) regretInsertion.regretInsert(s, 2, useNoise);
        else if (insertHeuristic == 2) regretInsertion.regretInsert(s, 3, useNoise);
        else if (insertHeuristic == 3) regretInsertion.regretInsert(s, 4, useNoise);
        else regretInsertion.regretInsert(s, s.requestBankSize(instance), useNoise); // Regret-m insert
        return insertHeuristic;
    }

    // Remove requests from solution and return used heuristic
    private int requestRemove(Solution solution, int q, double[] removalWeight) {
        int removeHeuristic = ALNS.rouletteSelection(removalWeight, random);
        if (removeHeuristic == 0) randomRemoval.remove(solution, q);
        else if (removeHeuristic == 1) worstRemoval.remove(solution, q);
        else shawRemoval.remove(solution, q);
        return removeHeuristic;
    }

}
