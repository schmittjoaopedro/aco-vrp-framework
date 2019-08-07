package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.Random;

public class ALNS {

    // Update Weights of each heuristic after a segment
    public static void updateAdaptiveWeight(double[] weight, double[] score, int[] nb, double reactionFactor) {
        for (int i = 0; i < weight.length; ++i) {
            weight[i] = weight[i] * (1 - reactionFactor) + reactionFactor * (score[i] / Math.max(nb[i], 1));
            score[i] = 0;
            nb[i] = 0;
        }
    }


    // Check if the new Solution is Accepted
    public static boolean accept(Solution sNew, Solution s, double temp, Instance instance, Random random) {
        double obj = s.objFunction(instance);
        double objNew = sNew.objFunction(instance);
        if (objNew <= obj) return true;
        double probability = Math.exp((obj - objNew) / temp);
        return (random.nextDouble() <= probability);
    }

    // Calculate Starting Temperature
    public static double calcTemp(double objValue, double tolerance) {
        return objValue * tolerance / Math.log(2);
    }

    // Select a number using Roulette Wheel Selection
    public static int rouletteSelection(double[] weight, Random random) {
        double[] sumWeight = new double[weight.length];
        double randomNumber = random.nextDouble();
        sumWeight[0] = weight[0];
        for (int i = 1; i < weight.length; ++i) {
            sumWeight[i] = sumWeight[i - 1] + weight[i];
        }
        randomNumber = randomNumber * sumWeight[weight.length - 1];
        for (int i = 0; i < weight.length; ++i) {
            if (randomNumber < sumWeight[i]) return i;
        }
        return weight.length - 1;
    }
}
