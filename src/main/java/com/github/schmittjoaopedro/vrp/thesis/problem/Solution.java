package com.github.schmittjoaopedro.vrp.thesis.problem;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;

import java.util.ArrayList;

public class Solution {

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requestIds;

    public ArrayList<Double> tourCosts;

    public ArrayList<double[]> departureTime;

    public ArrayList<double[]> arrivalTime;

    public ArrayList<double[]> departureSlackTimes;

    public ArrayList<double[]> arrivalSlackTimes;

    public ArrayList<double[]> waitingTimes;

    public ArrayList<double[]> delays;

    public double[] capacity;

    public boolean[] visited;

    public boolean[] visitedRequests;

    public double totalCost;

    public boolean feasible;

    public double maxTime;

    public int toVisit;

    @Override
    public String toString() {
        return "[F = " + feasible + ", NV = " + tours.size() + ", TC = " + MathUtils.round(totalCost) + "]";
    }
}
