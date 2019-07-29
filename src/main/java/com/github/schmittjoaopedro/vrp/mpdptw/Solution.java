package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.utils.Maths;

import java.math.BigInteger;
import java.util.ArrayList;

public class Solution {

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requests;

    public ArrayList<Double> tourCosts;

    public ArrayList<double[]> departureTime;

    public ArrayList<double[]> arrivalTime;

    public ArrayList<double[]> departureSlackTimes;

    public ArrayList<double[]> arrivalSlackTimes;

    public ArrayList<double[]> waitingTimes;

    public ArrayList<double[]> delays;

    public boolean[] visited;

    public double[] capacity;

    public boolean[] visitedRequests;

    public double[] visitTime;

    public int toVisit;

    public double totalCost;

    public boolean feasible;

    public double timeWindowPenalty;

    public double capacityPenalty;

    public double maxTime;

    public double getCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "[F = " + feasible + ", NV = " + tours.size() + ", TC = " + Maths.round(totalCost, 2) + "]";
    }
}
