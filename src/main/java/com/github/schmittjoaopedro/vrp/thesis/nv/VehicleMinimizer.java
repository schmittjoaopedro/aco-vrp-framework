package com.github.schmittjoaopedro.vrp.thesis.nv;

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

    private Set<Long> visitedList = new HashSet<>();

    private Solution solutionBest;

    private RegretOperator regretOperator;

    private double[] removalWeight;
    private double[] insertingWeight;
    private double[] noiseWeight;
    private double[] removalScore;
    private double[] insertingScore;
    private double[] noiseScore;
    private int[] removalCount;
    private int[] insertingCount;
    private int[] noiseCount;

    public VehicleMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {
        regretOperator = new RegretOperator(random, instance);
        resetAdaptiveWeight();
        visitedList.clear();
        solutionBest = SolutionUtils.createSolution(instance);
        regretOperator.regretInsert(solutionBest, 1, 0);
        SolutionUtils.removeEmptyVehicles(solutionBest);
        instance.solutionEvaluation(solutionBest);
        System.out.println("Initial solution = " + solutionBest);
    }

    public void optimize(int iteration) {

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

    public Solution getSolutionBest() {
        return solutionBest;
    }
}
