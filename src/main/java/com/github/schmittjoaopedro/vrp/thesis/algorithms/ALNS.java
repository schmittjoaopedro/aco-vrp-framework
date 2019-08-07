package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.List;
import java.util.Random;

public class ALNS {

    // Update Weights of each heuristic after a segment
    public static void updateAdaptiveWeight(List<Operator> operators, double reactionFactor) {
        for (int i = 0; i < operators.size(); ++i) {
            Operator operator = operators.get(i);
            double weight = operator.getWeight() * (1 - reactionFactor) + reactionFactor * (operator.getScore() / Math.max(operator.getCount(), 1));
            operator.setWeight(weight);
            operator.setScore(0.0);
            operator.setCount(0.0);
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
    public static int rouletteSelection(List<Operator> operators, Random random) {
        double[] sumWeight = new double[operators.size()];
        double randomNumber = random.nextDouble();
        sumWeight[0] = operators.get(0).getWeight();
        for (int i = 1; i < operators.size(); ++i) {
            sumWeight[i] = sumWeight[i - 1] + operators.get(i).getWeight();
        }
        randomNumber = randomNumber * sumWeight[operators.size() - 1];
        for (int i = 0; i < operators.size(); ++i) {
            if (randomNumber < sumWeight[i]) return i;
        }
        return operators.size() - 1;
    }
}
