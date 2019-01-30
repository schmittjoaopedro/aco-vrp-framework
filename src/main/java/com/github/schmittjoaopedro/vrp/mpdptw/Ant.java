package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class Ant {

    public ArrayList<ArrayList<Integer>> tours;

    public boolean[] visited;

    public boolean[] visitedRequests;

    public double[] departureTime;

    public ArrayList<Double> tourLengths;

    public double totalCost;

    public boolean feasible;

}
