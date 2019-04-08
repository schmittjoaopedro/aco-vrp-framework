package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class Solution {

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requests;

    public ArrayList<Double> tourCosts;

    public ArrayList<ArrayList<Double>> departureTime;

    public ArrayList<ArrayList<Double>> arrivalTime;

    public ArrayList<ArrayList<Double>> departureSlackTimes;

    public ArrayList<ArrayList<Double>> arrivalSlackTimes;

    public ArrayList<ArrayList<Double>> waitingTimes;

    public ArrayList<ArrayList<Double>> delays;

    public boolean[] visited;

    public double[] capacity;

    public boolean[] visitedRequests;

    public int toVisit;

    public double totalCost;

    public boolean feasible;

    public double timeWindowPenalty;

    public double capacityPenalty;

    public double getCost() {
        return totalCost;
    }

}
