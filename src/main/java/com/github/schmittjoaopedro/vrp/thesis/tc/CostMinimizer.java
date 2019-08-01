package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.Random;

public class CostMinimizer {

    private Instance instance;

    private Random random;

    private Solution feasibleSolutionBest;

    public CostMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {

    }

    public void optimize(int iteration) {

    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }
}
