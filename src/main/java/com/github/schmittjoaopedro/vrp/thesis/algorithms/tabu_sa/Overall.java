package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Random;

public class Overall {

    private int K; // The maximum number of children in a K-ary tree

    private int noImprovements = 0;

    private Instance instance;

    private MetaHeuristic metaHeuristic;

    private InsertionOperator insertionHeuristic;

    private Solution solutionBest;

    private long computationTime;

    private boolean print = true;

    public Overall(Instance instance, Random random) {
        this.instance = instance;
        this.metaHeuristic = new MetaHeuristic(50.0, 0.95, 4, instance, random);
        this.insertionHeuristic = new RegretInsertion(random, instance, (sol, inst) -> 1);
        this.K = (int) (20.0 * instance.numVehicles / 3.0 + 1.0);
    }

    public void run() {
        computationTime = System.currentTimeMillis();
        solutionBest = SolutionUtils.createSolution(instance);
        solutionBest = insertNewRequests(solutionBest);
        while (noImprovements < K) {
            Solution solutionNew = metaHeuristic.tabuEmbeddedSa(solutionBest);
            if (SolutionUtils.getBest(solutionNew, solutionBest) == solutionNew) {
                solutionBest = solutionNew;
                noImprovements = 0;
                if (print) {
                    System.out.println("New best: " + solutionBest);
                }
            } else {
                noImprovements++;
            }
        }
        computationTime = System.currentTimeMillis() - computationTime;
        if (print) {
            System.out.println("Best solution found: " + solutionBest);
        }
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }

    public long getComputationTimeInSeconds() {
        return computationTime / 1000;
    }

    protected Solution insertNewRequests(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        while (solution.toVisit > 0) {
            insertionHeuristic.insert(solution, 0);
            SolutionUtils.removeEmptyVehicles(solution);
            instance.solutionEvaluation(solution);
            // If the fleet is insufficient to attend new requests. Add a new vehicle to this operation.
            if (solution.toVisit > 0) {
                instance.extraVehicles++;
                solution = SolutionUtils.copy(solutionBase);
            }
        }
        return solution;
    }
}
