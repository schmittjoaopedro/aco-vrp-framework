package com.github.schmittjoaopedro.vrp.thesis.statistic;

import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.LinkedList;
import java.util.List;

public class ExperimentStatistics implements Comparable<ExperimentStatistics> {

    public long totalTime;

    public long totalIterations;

    public long numSolutionsEvaluation;

    public Solution solutionBest;

    public List<Statistic> statistics = new LinkedList<>();

    public void add(double processCompletion,
                    long numEvaluatedFunction,
                    Solution solverBest,
                    Solution nvBest,
                    Solution nvLocal,
                    double nvTemp,
                    Solution tcBest,
                    Solution tcLocal,
                    double tcTemp) {
        statistics.add(new Statistic(processCompletion, numEvaluatedFunction, solverBest, nvBest, nvLocal, nvTemp, tcBest, tcLocal, tcTemp));
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    @Override
    public int compareTo(ExperimentStatistics o) {
        int comp = Integer.compare(solutionBest.tours.size(), o.solutionBest.tours.size());
        if (comp == 0) {
            comp = Double.compare(solutionBest.totalCost, o.solutionBest.totalCost);
        }
        return comp;
    }
}
