package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class VRPTW {

    // Maximum number of vehicle allowed for constructing the solution or the size of the vehicle fleet.
    private int numVehicles;

    // The capacity of each vehicle
    private int capacity;

    // ArrayList which keeps all the customer requests defined in the VRPTW instance.
    private ArrayList<Request> requests;

    /**
     * ArrayList which keeps the ids of the available requests known at the current time
     * initially it contains the ids of the apriori requests, having available time = 0
     */
    private ArrayList<Integer> idAvailableRequests;

    /**
     * ArrayList which keeps the list of dynamic requests, which are not known in advance, having
     * available time > 0.
     */
    private ArrayList<Request> dynamicRequests;

    // Number of cities/customers, except the depot.
    private int n;

    private Problem problem;

    public void computeNNLists(Ants ants, Utilities utilities) {
        int i;
        int size = getN();
        double[] distanceVector = new double[size + 1];
        int[] helpVector = new int[size + 1];
        int nn = ants.getNnAnts();
        if (nn >= size + 1) {
            nn = size - 2;
        }
        int[][] nNear = new int[size + 1][nn];
        int[][] nNearAll = new int[size + 1][nn]; // Include also the depot city
        for (int node = 0; node < size + 1; node++) { // Compute candidate-sets for all nodes
            for (i = 0; i < size + 1; i++) { // Copy distances from nodes to the others
                distanceVector[i] = getProblem().getDistance()[node][i];
                helpVector[i] = i;
            }
            distanceVector[node] = Integer.MAX_VALUE; // City itself is not nearest neighbor
            utilities.sort2(distanceVector, helpVector, 0, size);
            int count1 = 0;
            i = -1;
            while (count1 < nn) {
                i++;
                // Include in the nn_list of a node only the nodes that are known (available)
                if (helpVector[i] != 0) {
                    nNear[node][count1] = helpVector[i];
                    count1++;
                } else {
                    continue;
                }
            }
            int count2 = 0;
            i = -1;
            while (count2 < nn) {
                i++;
                // Include in the nn_list of a node only the nodes that are known (available)
                nNearAll[node][count2] = helpVector[i];
                count2++;
            }
        }
        getProblem().setNnList(nNear);
        getProblem().setNnListAll(nNearAll);
    }

    public void computeDistances(DistanceType distanceType, double scalingValue) {
        int size = getN();
        // Include also the depot city in the distance matrix: it will correspond to index 0 for row and column
        double[][] distances = new double[size + 1][size + 1];
        for (int i = 0; i < size + 1; i++) {
            for (int j = 0; j < size + 1; j++) {
                if (distanceType == DistanceType.EUC_2D) {
                    distances[i][j] = getEuclideanDistance(getProblem(), i, j);
                    if (scalingValue != 0) {
                        distances[i][j] *= scalingValue;
                    }
                } else {
                    throw new RuntimeException("Distance type not supported!");
                }
            }
        }
        getProblem().setDistance(distances);
    }

    // Compute the tour length of tour t taking also into account the depot city (depot at beginning and end positions)
    public double computeTourLength(ArrayList<Integer> tour) {
        double sum = 0;
        if (tour.size() > 1) {
            for (int i = 0; i < tour.size() - 1; i++) {
                sum += problem.getDistance()[tour.get(i) + 1][tour.get(i + 1) + 1];
            }
        }
        return sum;
    }

    public double computeTourLengthDepotOnlyInFirstPosition(ArrayList<Integer> tour) {
        double sum = 0;
        if (tour.size() > 1) {
            sum += getProblem().getDistance()[0][tour.get(1) + 1];
            for (int i = 1; i < tour.size() - 2; i++) {
                sum += problem.getDistance()[tour.get(i) + 1][tour.get(i + 1) + 1];
            }
            sum += problem.getDistance()[tour.get(tour.size() - 2) + 1][0];
        }
        return sum;
    }

    private double getEuclideanDistance(Problem problem, int i, int j) {
        double xd = problem.getNodes()[i].getX() - problem.getNodes()[j].getX();
        double yd = problem.getNodes()[i].getY() - problem.getNodes()[j].getY();
        return Math.sqrt(xd * xd + yd * yd);
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public void setNumVehicles(int numVehicles) {
        this.numVehicles = numVehicles;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public ArrayList<Integer> getIdAvailableRequests() {
        return idAvailableRequests;
    }

    public void setIdAvailableRequests(ArrayList<Integer> idAvailableRequests) {
        this.idAvailableRequests = idAvailableRequests;
    }

    public ArrayList<Request> getDynamicRequests() {
        return dynamicRequests;
    }

    public void setDynamicRequests(ArrayList<Request> dynamicRequests) {
        this.dynamicRequests = dynamicRequests;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
