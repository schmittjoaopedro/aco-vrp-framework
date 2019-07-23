package com.github.schmittjoaopedro.vrp.mpdptw.alns;

public class DetailedStatistics {

    public double s_best_TC;

    public long s_best_NV;

    public double s_current_TC;

    public long s_current_NV;

    public double s_new_TC;

    public long s_new_NV;

    public double temperature;

    public String[] insertMethodNames;

    public double[] insertMethodWeights;

    public String[] removalMethodNames;

    public double[] removalMethodWeights;

    public double noise_true;

    public double noise_false;

    public long hash_solution_count;

    public long fullEvaluationCount;

    public long partialEvaluationCount;

    public long costEvaluationCount;

    public long bsfAcceptCount;

    public long currentAcceptCount;

    public long worstAcceptCount;

    public long localSearchUsed;

    public long localSearchImproved;

    public long iterationTime;


}
