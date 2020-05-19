package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

import com.github.schmittjoaopedro.vrp.thesis.CallCenter;
import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.IterationCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.StopCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.TimeCriteria;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.statistic.ExperimentStatistics;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

// Overall algorithm
public class TABU_SA_DPDP {

    private int K; // The maximum number of children in a K-ary tree

    private StopCriteria stopCriteria;

    private Instance instance;

    private MetaHeuristic metaHeuristic;

    private InsertionOperator insertionHeuristic;

    private Solution solutionBest;

    private long computationTime;

    private boolean print = true;

    private CallCenter callCenter;

    private ExperimentStatistics experimentStatistics;

    public TABU_SA_DPDP(Instance instance, Random random) {
        this.instance = instance;
        this.insertionHeuristic = new RegretInsertion(random, instance, (sol, inst) -> 1);
        this.K = (int) (20.0 * instance.numVehicles / 3.0 + 1.0) + 1;
        stopCriteria = IterationCriteria.of(this.K);
        this.metaHeuristic = new MetaHeuristic(50.0, 0.95, 4, instance, random, stopCriteria);
        experimentStatistics = new ExperimentStatistics();
    }

    public TABU_SA_DPDP(Instance instance, Random random, StopCriteria stopCriteria) {
        this.instance = instance;
        this.insertionHeuristic = new RegretInsertion(random, instance, (sol, inst) -> 1);
        this.stopCriteria = stopCriteria;
        this.metaHeuristic = new MetaHeuristic(50.0, 0.95, 4, instance, random, stopCriteria);
        experimentStatistics = new ExperimentStatistics();
    }

    public void run() {
        computationTime = System.currentTimeMillis();
        solutionBest = SolutionUtils.createSolution(instance);
        solutionBest = insertNewRequests(solutionBest);
        stopCriteria.reset();
        while (stopCriteria.isContinue()) {
            Solution solutionNew = metaHeuristic.tabuEmbeddedSa(solutionBest);
            if (SolutionUtils.getBest(solutionNew, solutionBest) == solutionNew) {
                solutionBest = solutionNew;
                if (stopCriteria instanceof IterationCriteria) {
                    stopCriteria.reset();
                }
                if (print) {
                    System.out.println("Thread-" + Thread.currentThread().getId() + " -> " + solutionBest);
                }
            } else {
                stopCriteria.update();
            }
        }
        computationTime = System.currentTimeMillis() - computationTime;
        experimentStatistics.totalTime = computationTime;
        experimentStatistics.numSolutionsEvaluation = instance.numEvaluatedFunction;
        instance.solutionEvaluation(solutionBest);
        experimentStatistics.solutionBest = solutionBest;
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

    private String getSummaryResults() {
        double[] startTimes = new double[solutionBest.tours.size()];
        for (int i = 0; i < startTimes.length; i++) {
            startTimes[i] = instance.lastIdleTime(solutionBest.tours.get(i).get(1));
        }
        Optional.ofNullable(callCenter).ifPresent(c -> c.rollbackOriginalInformation(solutionBest));
        String msg = "\nInstance = " + instance.name;
        msg += "\nBest solution feasibility = " + solutionBest.feasible + "\nRoutes";
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            ArrayList<Integer> route = solutionBest.tours.get(i);
            double startTime = startTimes[i];
            if (instance.movingVehicle) {
                msg += "\nStart time " + MathUtils.round(startTime, 2) + " Route = " + StringUtils.join(route, " ");
            } else {
                msg += "\n" + StringUtils.join(route, " ");
            }
        }
        msg += "\nRequests";
        for (ArrayList requests : solutionBest.requestIds) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nNum. vehicles = " + solutionBest.tours.size();
        msg += "\nTotal cost = " + solutionBest.totalCost;
        return msg;
    }

    public ExperimentStatistics getExperimentStatistics() {
        return experimentStatistics;
    }
}
