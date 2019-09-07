package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

public class Statistic implements Comparable<Statistic> {

    public Instance instance;

    public Solution solutionBest;

    public int maxIterations;

    public int[] global_nv;

    public double[] global_tc;

    public int[] vehicle_minimizer_best_nv;

    public double[] vehicle_minimizer_best_tc;

    public int[] vehicle_minimizer_local_nv;

    public double[] vehicle_minimizer_local_tc;

    public double[] vehicle_minimizer_temperature;

    public int[] cost_minimizer_best_nv;

    public double[] cost_minimizer_best_tc;

    public int[] cost_minimizer_local_nv;

    public double[] cost_minimizer_local_tc;

    public double[] cost_minimizer_temperature;

    public Statistic(int maxIterations, Instance instance) {
        this.maxIterations = maxIterations;
        this.instance = instance;
        global_nv = new int[maxIterations];
        global_tc = new double[maxIterations];
        vehicle_minimizer_best_nv = new int[maxIterations];
        vehicle_minimizer_best_tc = new double[maxIterations];
        vehicle_minimizer_local_nv = new int[maxIterations];
        vehicle_minimizer_local_tc = new double[maxIterations];
        vehicle_minimizer_temperature = new double[maxIterations];
        cost_minimizer_best_nv = new int[maxIterations];
        cost_minimizer_best_tc = new double[maxIterations];
        cost_minimizer_local_nv = new int[maxIterations];
        cost_minimizer_local_tc = new double[maxIterations];
        cost_minimizer_temperature = new double[maxIterations];
    }

    @Override
    public int compareTo(Statistic o) {
        int comp = Integer.compare(solutionBest.tours.size(), o.solutionBest.tours.size());
        if (comp == 0) {
            comp = Double.compare(solutionBest.totalCost, o.solutionBest.totalCost);
        }
        return comp;
    }
}
