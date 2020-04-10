package com.github.schmittjoaopedro.vrp.thesis.algorithms.lns;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch.LocalSearch;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class LNS implements LNSOptimizer {

    protected Instance instance;

    protected Random random;

    protected final Set<Integer> TABU_LIST = new HashSet<>();

    protected Solution feasibleSolutionBest;

    protected Solution solutionBest;

    protected Solution solution;

    protected double T;

    protected InsertionOperator insertionOperator;

    protected RemovalOperator removalOperator;

    protected LocalSearch localSearch;

    protected boolean useLocalSearch = false;

    public LNS(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.localSearch = new LocalSearch(instance);
    }

    protected void insertRequests(Solution solution) {
        insertionOperator.insert(solution, 0);
    }

    protected void removeRequests(Solution solution, int q) {
        removalOperator.remove(solution, q);
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

    protected void executeLocalSearch(Solution solution) {
        if (useLocalSearch) {
            Solution newSolution = localSearch.applyImprovement(solution);
            if (SolutionUtils.getBest(solution, newSolution) == newSolution) {
                SolutionUtils.copyFromTo(newSolution, solution);
            }
        }
    }

    protected Solution insertNewRequests(InsertionOperator insertionOperator, Solution solutionBase) {
        Solution solution = copyBaseSolution(solutionBase);
        while (solution.toVisit > 0) {
            insertionOperator.insert(solution, 0);
            SolutionUtils.removeEmptyVehicles(solution);
            instance.solutionEvaluation(solution);
            // If the fleet is insufficient to attend new requests. Add a new vehicle to this operation.
            if (solution.toVisit > 0) {
                instance.extraVehicles++;
                solution = copyBaseSolution(solutionBase);
            }
        }
        return solution;
    }

    protected Solution copyBaseSolution(Solution solutionBase) {
        Solution newSolution = SolutionUtils.createSolution(instance);
        if (solutionBase != null) {
            for (int k = 0; k < solutionBase.tours.size(); k++) {
                newSolution.tours.set(k, new ArrayList<>(solutionBase.tours.get(k)));
                newSolution.requestIds.set(k, new ArrayList<>(solutionBase.requestIds.get(k)));
            }
        }
        instance.solutionEvaluation(newSolution);
        return newSolution;
    }

    @Override
    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }

    @Override
    public void setBaseSolution(Solution newSolution) {
        solution = SolutionUtils.copy(newSolution);
        solutionBest = SolutionUtils.copy(newSolution);
        feasibleSolutionBest = SolutionUtils.copy(newSolution);
    }

    @Override
    public void setUseLocalSearch(boolean useLocalSearch) {
        this.useLocalSearch = useLocalSearch;
    }

    @Override
    public Solution getSolutionBest() {
        return solutionBest;
    }

    @Override
    public Solution getSolutionLocal() {
        return solution;
    }

    @Override
    public double getTemperature() {
        return T;
    }

}
