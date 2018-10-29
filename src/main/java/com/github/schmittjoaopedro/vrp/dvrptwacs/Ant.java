package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

/**
 * ACO algorithms for the TSP
 * <p>
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 * <p>
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 * <p>
 * And adapted from the version for DVRPTW proposed in: https://github.com/ralucanecula/DVRPTW_ACS
 */
public class Ant {

    /**
     * For each of the m salesmen (or vehicle) an ant will construct a tour, so that a candidate solution
     * constructed by an ant will be represented by a list of tours, one for each salesman (or vehicle).
     */
    private ArrayList<ArrayList<Integer>> tours;

    private ArrayList<Double> tourLengths;

    private double totalTourLength;

    private double longestTourLength;

    // Tabu list to indicate the visited clients;
    private boolean[] visited;

    /**
     * Contains the beginning of service for each customer, including the depot, taking into
     * account the opening time (beginning) of the time window, the service time and the
     * distance between customers.
     */
    private double[] beginService;

    /**
     * Current number of used vehicles (= number of routes/tours) for constructing a feasible solution
     * that serve all the customers' requests and satisfy the time windows and capacity constraints.
     */
    private int usedVehicles;

    /**
     * Stores for each partial tour/route under construction the current time taking into account
     * the beginning time of service for the last visited customer on the tour.
     */
    private ArrayList<Double> currentTime;

    /**
     * Used by the insertion heuristic when checking the feasibility of an insertion.
     * It stores for every customer i already assigned to a route the earliest time a delivery
     * can be made at i.
     */
    private ArrayList<ArrayList<Double>> earliestTime;

    /**
     * Used by the insertion heuristic when checking the feasibility of an insertion.
     * It stores for every customer i already assigned to a route the latest time a delivery
     * can be made at i.
     */
    private ArrayList<ArrayList<Double>> latestTime;

    /**
     * Stores for each partial tour/route under construction the current quantity of goods
     * (given by the demand of each request) transported on the route.
     */
    private ArrayList<Double> currentQuantity;

    // The index of the longest tour
    private int indexLongestTour;

    // Cities left to be visited by an ant (initially toVisit = n, which is the number of cities from the mTSP instance).
    private int toVisit;

    // Stores the cost of each solution according to the considered objectives (2 in this case).
    private double costObjectives[];

    /**
     * It is true if a new empty tour was added in the ant solution to service the remaining available
     * unrouted/unvisited customers.
     */
    private boolean addedEmptyTour;

    public Ant() {
        super();
    }

    public void emptyMemory(VRPTW instance) {
        setTotalTourLength(0);
        setLongestTourLength(Integer.MAX_VALUE);
        setIndexLongestTour(0);
        setAddedEmptyTour(false);
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            getCostObjectives()[indexObj] = 0;
        }
        getTourLengths().clear();
        getCurrentQuantity().clear();
        getCurrentTime().clear();
        // Clear all the elements (cities) from the tours of an ant
        for (int i = 0; i < getUsedVehicles(); i++) {
            getTours().get(i).clear();
        }
        getTours().clear();
        if (getEarliestTime() != null) {
            getEarliestTime().clear();
        }
        if (getLatestTime() != null) {
            getLatestTime().clear();
        }
        setUsedVehicles(1);
        getTourLengths().add(0.0);
        getCurrentQuantity().add(0.0);
        getCurrentTime().add(0.0);
        getTours().add(new ArrayList<>());
        for (int j = 0; j < instance.getN(); j++) {
            getVisited()[j] = false;
        }
        for (int j = 0; j < (instance.getN() + 1); j++) {
            getBeginService()[j] = 0;
        }
        // The another node is the depot, which is by default visited by each salesman and added in its tour
        setToVisit(instance.getIdAvailableRequests().size());
    }

    public double computeToursAmplitude() {
        double min = getTourLengths().get(0);
        double max = getTourLengths().get(0);
        for (int i = 1; i < getTours().size(); i++) {
            if (getTourLengths().get(i) < min) {
                min = getTourLengths().get(i);
            }
            if (getTourLengths().get(i) > max) {
                max = getTourLengths().get(i);
            }
        }
        return (max - min);
    }

    public ArrayList<ArrayList<Integer>> getTours() {
        return tours;
    }

    public void setTours(ArrayList<ArrayList<Integer>> tours) {
        this.tours = tours;
    }

    public ArrayList<Double> getTourLengths() {
        return tourLengths;
    }

    public void setTourLengths(ArrayList<Double> tourLengths) {
        this.tourLengths = tourLengths;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public void setVisited(boolean[] visited) {
        this.visited = visited;
    }

    public double[] getBeginService() {
        return beginService;
    }

    public void setBeginService(double[] beginService) {
        this.beginService = beginService;
    }

    public int getUsedVehicles() {
        return usedVehicles;
    }

    public void setUsedVehicles(int usedVehicles) {
        this.usedVehicles = usedVehicles;
    }

    public ArrayList<Double> getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(ArrayList<Double> currentTime) {
        this.currentTime = currentTime;
    }

    public ArrayList<ArrayList<Double>> getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(ArrayList<ArrayList<Double>> earliestTime) {
        this.earliestTime = earliestTime;
    }

    public ArrayList<ArrayList<Double>> getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(ArrayList<ArrayList<Double>> latestTime) {
        this.latestTime = latestTime;
    }

    public ArrayList<Double> getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(ArrayList<Double> currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public double getTotalTourLength() {
        return totalTourLength;
    }

    public void setTotalTourLength(double totalTourLength) {
        this.totalTourLength = totalTourLength;
    }

    public double getLongestTourLength() {
        return longestTourLength;
    }

    public void setLongestTourLength(double longestTourLength) {
        this.longestTourLength = longestTourLength;
    }

    public int getIndexLongestTour() {
        return indexLongestTour;
    }

    public void setIndexLongestTour(int indexLongestTour) {
        this.indexLongestTour = indexLongestTour;
    }

    public int getToVisit() {
        return toVisit;
    }

    public void setToVisit(int toVisit) {
        this.toVisit = toVisit;
    }

    public double[] getCostObjectives() {
        return costObjectives;
    }

    public void setCostObjectives(double[] costObjectives) {
        this.costObjectives = costObjectives;
    }

    public boolean isAddedEmptyTour() {
        return addedEmptyTour;
    }

    public void setAddedEmptyTour(boolean addedEmptyTour) {
        this.addedEmptyTour = addedEmptyTour;
    }
}
