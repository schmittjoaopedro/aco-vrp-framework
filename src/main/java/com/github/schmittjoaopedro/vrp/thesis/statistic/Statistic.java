package com.github.schmittjoaopedro.vrp.thesis.statistic;

import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

public class Statistic {

    public double processCompletion;

    public int solver_best_nv;

    public double solver_best_tc;

    public int solver_best_fc;

    public int vehicle_minimizer_best_nv;

    public double vehicle_minimizer_best_tc;

    public int vehicle_minimizer_local_nv;

    public double vehicle_minimizer_local_tc;

    public double vehicle_minimizer_temperature;

    public int cost_minimizer_best_nv;

    public double cost_minimizer_best_tc;

    public int cost_minimizer_local_nv;

    public double cost_minimizer_local_tc;

    public double cost_minimizer_temperature;

    public long numEvaluatedFunction;

    public Statistic(double processCompletion,
                     long numEvaluatedFunction,
                     Solution solverBest,
                     Solution nvBest,
                     Solution nvLocal,
                     double nvTemp,
                     Solution tcBest,
                     Solution tcLocal,
                     double tcTemp) {
        this.processCompletion = processCompletion;
        this.numEvaluatedFunction = numEvaluatedFunction;
        solver_best_nv = solverBest.tours.size();
        solver_best_tc = solverBest.totalCost;
        solver_best_fc = solverBest.feasible ? 1 : 0;
        vehicle_minimizer_best_nv = nvBest.tours.size();
        vehicle_minimizer_best_tc = nvBest.totalCost;
        vehicle_minimizer_local_nv = nvLocal.tours.size();
        vehicle_minimizer_local_tc = nvLocal.totalCost;
        vehicle_minimizer_temperature = nvTemp;
        cost_minimizer_best_nv = tcBest.tours.size();
        cost_minimizer_best_tc = tcBest.totalCost;
        cost_minimizer_local_nv = tcLocal.tours.size();
        cost_minimizer_local_tc = tcLocal.totalCost;
        cost_minimizer_temperature = tcTemp;
    }
}
