package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch.LocalSearch;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.noise.NoiseOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.*;

public abstract class ALNS {

    protected Instance instance;

    protected Random random;

    protected final Set<Integer> TABU_LIST = new HashSet<>();

    protected Solution feasibleSolutionBest;

    protected Solution solutionBest;

    protected Solution solution;

    protected double T;

    protected List<Operator> noiseOperators = new ArrayList<>();

    protected List<Operator> insertionOperators = new ArrayList<>();

    protected List<Operator> removalOperators = new ArrayList<>();

    protected LocalSearch localSearch;

    protected boolean useLocalSearch = false;

    public ALNS(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.localSearch = new LocalSearch(instance);
    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }

    protected void resetOperatorsWeights() {
        noiseOperators.forEach(Operator::resetOperator);
        removalOperators.forEach(Operator::resetOperator);
        insertionOperators.forEach(Operator::resetOperator);
    }

    protected void increaseOperatorsUsageCount(int removeHeuristic, int insertHeuristic, int noiseHeuristic) {
        removalOperators.get(removeHeuristic).increaseCount();
        insertionOperators.get(insertHeuristic).increaseCount();
        noiseOperators.get(noiseHeuristic).increaseCount();
    }

    protected void increaseOperatorsScore(int removeHeuristic, int insertHeuristic, int noiseHeuristic, double score) {
        removalOperators.get(removeHeuristic).addScore(score);
        insertionOperators.get(insertHeuristic).addScore(score);
        noiseOperators.get(noiseHeuristic).addScore(score);
    }

    protected int insertRequests(Solution solution, int noiseHeuristic) {
        int insertHeuristic = rouletteSelection(insertionOperators);
        int useNoise = ((NoiseOperator) noiseOperators.get(noiseHeuristic)).getUseNoise();
        ((InsertionOperator) insertionOperators.get(insertHeuristic)).insert(solution, useNoise);
        return insertHeuristic;
    }

    protected int removeRequests(Solution solution, int q) {
        int removeHeuristic = rouletteSelection(removalOperators);
        ((RemovalOperator) removalOperators.get(removeHeuristic)).remove(solution, q);
        return removeHeuristic;
    }

    protected void updateOperatorWeights(double reactionFactor) {
        updateOperatorWeight(removalOperators, reactionFactor);
        updateOperatorWeight(insertionOperators, reactionFactor);
        updateOperatorWeight(noiseOperators, reactionFactor);
    }

    protected void updateOperatorWeight(List<Operator> operators, double reactionFactor) {
        for (Operator operator : operators) {
            double weight = operator.getWeight() * (1.0 - reactionFactor) + reactionFactor * (operator.getScore() / Math.max(operator.getCount(), 1.0));
            operator.setWeight(weight);
            operator.setScore(0.0);
            operator.setCount(0.0);
        }
    }

    protected boolean accept(Solution solutionNew, Solution solution, double temperature) {
        boolean accept;
        double objectiveValueDiff = solution.calculateObjective(instance) - solutionNew.calculateObjective(instance);
        if (objectiveValueDiff >= 0) { // New solution is better than the current one
            accept = true;
        } else {
            // Simulated annealing function
            accept = random.nextDouble() <= Math.exp(objectiveValueDiff / temperature);
        }
        return accept;
    }

    protected double calculateStartingTemperature(double objectiveValue, double tolerance) {
        return objectiveValue * tolerance / Math.log(2);
    }

    protected int generateRandomNumRemoveRequests(double removeControl) {
        return (int) Math.max(4, random.nextDouble() * Math.min(100, (int) (removeControl * instance.numRequests)));
    }

    protected int rouletteSelection(List<Operator> operators) {
        int selectedOperator = operators.size() - 1;
        double sum = 0.0;
        double[] sumWeight = new double[operators.size()];
        for (int i = 0; i < operators.size(); ++i) {
            sum += operators.get(i).getWeight();
            sumWeight[i] = sum;
        }
        double randomNumber = random.nextDouble() * sumWeight[operators.size() - 1];
        for (int i = 0; i < operators.size(); ++i) {
            if (randomNumber < sumWeight[i]) {
                selectedOperator = i;
                break;
            }
        }
        return selectedOperator;
    }

    protected void executeLocalSearch(Solution solution) {
        if (useLocalSearch) {
            Solution newSolution = localSearch.applyImprovement(solution);
            if (SolutionUtils.getBest(solution, newSolution) == newSolution) {
                SolutionUtils.copyFromTo(newSolution, solution);
            }
        }
    }

    public void setUseLocalSearch(boolean useLocalSearch) {
        this.useLocalSearch = useLocalSearch;
    }

}
