package com.github.schmittjoaopedro.vrp.thesis.algorithms;

public class StatisticMeanSd {

    public double[] mean_global_nv;

    public double[] sd_global_nv;

    public double[] mean_global_tc;

    public double[] sd_global_tc;

    public double[] mean_global_fc;

    public double[] sd_global_fc;

    public double[] mean_vehicle_minimizer_best_nv;

    public double[] sd_vehicle_minimizer_best_nv;

    public double[] mean_vehicle_minimizer_best_tc;

    public double[] sd_vehicle_minimizer_best_tc;

    public double[] mean_vehicle_minimizer_local_nv;

    public double[] sd_vehicle_minimizer_local_nv;

    public double[] mean_vehicle_minimizer_local_tc;

    public double[] sd_vehicle_minimizer_local_tc;

    public double[] mean_vehicle_minimizer_temperature;

    public double[] sd_vehicle_minimizer_temperature;

    public double[] mean_cost_minimizer_best_nv;

    public double[] sd_cost_minimizer_best_nv;

    public double[] mean_cost_minimizer_best_tc;

    public double[] sd_cost_minimizer_best_tc;

    public double[] mean_cost_minimizer_local_nv;

    public double[] sd_cost_minimizer_local_nv;

    public double[] mean_cost_minimizer_local_tc;

    public double[] sd_cost_minimizer_local_tc;

    public double[] mean_cost_minimizer_temperature;

    public double[] sd_cost_minimizer_temperature;

    public StatisticMeanSd(int maxIterations) {
        mean_global_nv = new double[maxIterations];
        sd_global_nv = new double[maxIterations];
        mean_global_tc = new double[maxIterations];
        sd_global_tc = new double[maxIterations];
        mean_global_fc = new double[maxIterations];
        sd_global_fc = new double[maxIterations];
        mean_vehicle_minimizer_best_nv = new double[maxIterations];
        sd_vehicle_minimizer_best_nv = new double[maxIterations];
        mean_vehicle_minimizer_best_tc = new double[maxIterations];
        sd_vehicle_minimizer_best_tc = new double[maxIterations];
        mean_vehicle_minimizer_local_nv = new double[maxIterations];
        sd_vehicle_minimizer_local_nv = new double[maxIterations];
        mean_vehicle_minimizer_local_tc = new double[maxIterations];
        sd_vehicle_minimizer_local_tc = new double[maxIterations];
        mean_vehicle_minimizer_temperature = new double[maxIterations];
        sd_vehicle_minimizer_temperature = new double[maxIterations];
        mean_cost_minimizer_best_nv = new double[maxIterations];
        sd_cost_minimizer_best_nv = new double[maxIterations];
        mean_cost_minimizer_best_tc = new double[maxIterations];
        sd_cost_minimizer_best_tc = new double[maxIterations];
        mean_cost_minimizer_local_nv = new double[maxIterations];
        sd_cost_minimizer_local_nv = new double[maxIterations];
        mean_cost_minimizer_local_tc = new double[maxIterations];
        sd_cost_minimizer_local_tc = new double[maxIterations];
        mean_cost_minimizer_temperature = new double[maxIterations];
        sd_cost_minimizer_temperature = new double[maxIterations];
    }

}
