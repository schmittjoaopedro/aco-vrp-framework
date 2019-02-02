package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class Ant {

    public ArrayList<ArrayList<Integer>> tours;

    public ArrayList<ArrayList<Integer>> requests;

    public boolean[] visited;

    public boolean[] visitedRequests;

    public double[] departureTime;

    public double[] demands;

    public int toVisit;

    public ArrayList<Double> tourLengths;

    public double totalCost;

    public boolean feasible;

    public double twPenalty;

    public double capacityPenalty;

}
