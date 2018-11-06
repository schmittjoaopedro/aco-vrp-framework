package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class Ant {

    //for each of the m salesmen an ant will construct a tour, so that a candidate solution constructed by
    //an ant will be represented by a list of tours, one for each salesman
    public ArrayList<ArrayList<Integer>> tours;

    public boolean[] visited;

    public ArrayList<Double> tourLengths;

    //contains the beginning of service for each customer, including the depot, taking into
    //account the opening time (beginning) of the time window, the service time and the
    //distance between customers
    public double[] beginService;

    //current number of used vehicles (= number of routes/tours) for constructing a feasible solution
    //that serve all the customers' requests and satisfy the time windows and capacity constraints
    public int usedVehicles;

    //stores for each partial tour/route under construction the current time
    //taking into account the beginning time of service for the last visited customer on the tour
    public ArrayList<Double> currentTime;

    //used by the insertion heuristic when checking the feasibility of an insertion
    //it stores for every customer i already assigned to a route the earliest time a delivery
    //can be made at i
    public ArrayList<ArrayList<Double>> earliestTime;

    //used by the insertion heuristic when checking the feasibility of an insertion
    //it stores for every customer i already assigned to a route the latest time a delivery
    //can be made at i
    public ArrayList<ArrayList<Double>> latestTime;

    //stores for each partial tour/route under construction the current quantity of goods
    //(given by the demand of each request) transported on the route
    public ArrayList<Double> currentQuantity;

    public double totalTourLength;

    public double longestTourLength;

    public int indexLongestTour;  //the index of the longest tour

    //cities left to be visited by an ant (initially toVisit = n, which is the number of cities from the mTSP instance)
    public int toVisit;

    //stores the cost of each solution according to the considered objectives (2 in this case)
    public double costObjectives[];

    //it is true if a new empty tour was added in the ant solution to service the remaining available
    //unrouted/unvisited customers
    public boolean addedEmptyTour;

}
