package com.github.schmittjoaopedro.vrp.dvrpacs;

import java.util.ArrayList;

/**
 * ACO algorithms for the TSP
 * <p>
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 * <p>
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 */
public class Ants {

    /**
     * It indicates that node at position/index i is committed if the value at position i is true; the
     * depot node is considered to be committed by default and it's not included in this array.
     */
    private boolean[] committedNodes;

    private Ant ants[];

    private Ant bestSoFarAnt;

    private Ant restartBestAnt;

    private double pheromone[][];

    // Number of ants.
    private int numAnts;

    // Length of nearest neighbor lists for the ants' solution construction.
    private int numNearestAnts;

    // Parameter for evaporation used in global pheromone update.
    private double rho;

    // Parameter for evaporation used in local pheromone update.
    private double localRho;

    // Importance of trail.
    private double alpha;

    // Importance of heuristic evaluate.
    private double beta;

    // Probability of best choice in tour construction.
    private double q0;

    // Every u_gb iterations update with best-so-far ant.
    private int uGb;

    // Initial pheromone level in ACS.
    private double trail0;

    public Ants() {
        super();
    }

    public void initPheromoneTrails(double initialTrail) {
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j <= i; j++) {
                pheromone[i][j] = initialTrail;
                pheromone[j][i] = initialTrail;
            }
        }
    }

    public void allocateAnts(int n, int availableRequestsSize) {
        // Committed notes initialization
        committedNodes = new boolean[n];
        for (int i = 0; i < n; i++) {
            committedNodes[i] = false;
        }
        // Ant population initialization
        ants = new Ant[numAnts];
        for (int i = 0; i < numAnts; i++) {
            ants[i] = createDefaultAnt(n, availableRequestsSize);
        }
        // Initialize best so far ant
        bestSoFarAnt = createDefaultAnt(n, availableRequestsSize);
        bestSoFarAnt.setLongestTourLength(Double.MAX_VALUE);
        // Initialize restart best ant
        restartBestAnt = createDefaultAnt(n, availableRequestsSize);
        restartBestAnt.setLongestTourLength(Double.MAX_VALUE);
    }

    // Copy solution from ant a1 into ant a2
    public void copyFromTo(Ant a1, Ant a2, VRPTW instance) {
        a2.emptyMemory(instance);
        a2.setTotalTourLength(a1.getTotalTourLength());
        a2.setToVisit(a1.getToVisit());
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            a2.getCostObjectives()[indexObj] = a1.getCostObjectives()[indexObj];
        }
        // Create the remaining positions
        if (a2.getUsedVehicles() < a1.getUsedVehicles()) {
            for (int index = a2.getUsedVehicles(); index < a1.getUsedVehicles(); index++) {
                a2.getTourLengths().add(index, 0.0);
                a2.getTours().add(index, new ArrayList<>());
                a2.getCurrentQuantity().add(index, 0.0);
                a2.getCurrentTime().add(index, 0.0);
            }
        }
        // Copy routes
        for (int i = 0; i < a1.getUsedVehicles(); i++) {
            a2.getTourLengths().set(i, a1.getTourLengths().get(i));
            a2.getCurrentQuantity().set(i, a1.getCurrentQuantity().get(i));
            a2.getCurrentTime().set(i, a1.getCurrentTime().get(i));
            int size = a1.getTours().get(i).size();
            for (int j = 0; j < size; j++) {
                int elem = a1.getTours().get(i).get(j);
                a2.getTours().get(i).add(elem);
            }
        }
        for (int i = 0; i < instance.getN(); i++) {
            a2.getVisited()[i] = a1.getVisited()[i];
        }
        a2.setUsedVehicles(a1.getUsedVehicles());
        for (int i = 0; i < (instance.getN() + 1); i++) {
            a2.getBeginService()[i] = a1.getBeginService()[i];
        }
    }

    public Ant createDefaultAnt(int n, int availableRequestsSize) {
        Ant ant = new Ant();
        ant.setBeginService(new double[n + 1]);
        ant.setCurrentTime(new ArrayList<>());
        ant.setCurrentQuantity(new ArrayList<>());
        ant.setAddedEmptyTour(false);
        ant.setUsedVehicles(1);
        ant.setTours(new ArrayList<>());
        ant.setTourLengths(new ArrayList<>());
        ant.getTours().add(new ArrayList<>());
        ant.getTourLengths().add(0.0);
        ant.setVisited(new boolean[n]);
        ant.setToVisit(availableRequestsSize);
        ant.setCostObjectives(new double[2]);
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            ant.getCostObjectives()[indexObj] = 0;
        }
        ant.setEarliestTime(new ArrayList<>(ant.getUsedVehicles()));
        ant.setLatestTime(new ArrayList<>(ant.getUsedVehicles()));
        return ant;
    }

    public boolean[] getCommittedNodes() {
        return committedNodes;
    }

    public void setCommittedNodes(boolean[] committedNodes) {
        this.committedNodes = committedNodes;
    }

    public Ant[] getAnts() {
        return ants;
    }

    public void setAnts(Ant[] ants) {
        this.ants = ants;
    }

    public Ant getBestSoFarAnt() {
        return bestSoFarAnt;
    }

    public void setBestSoFarAnt(Ant bestSoFarAnt) {
        this.bestSoFarAnt = bestSoFarAnt;
    }

    public Ant getRestartBestAnt() {
        return restartBestAnt;
    }

    public void setRestartBestAnt(Ant restartBestAnt) {
        this.restartBestAnt = restartBestAnt;
    }

    public double[][] getPheromone() {
        return pheromone;
    }

    public void setPheromone(double[][] pheromone) {
        this.pheromone = pheromone;
    }

    public int getNumAnts() {
        return numAnts;
    }

    public void setNumAnts(int numAnts) {
        this.numAnts = numAnts;
    }

    public int getNumNearestAnts() {
        return numNearestAnts;
    }

    public void setNumNearestAnts(int numNearestAnts) {
        this.numNearestAnts = numNearestAnts;
    }

    public double getRho() {
        return rho;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public double getLocalRho() {
        return localRho;
    }

    public void setLocalRho(double localRho) {
        this.localRho = localRho;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getQ0() {
        return q0;
    }

    public void setQ0(double q0) {
        this.q0 = q0;
    }

    public int getuGb() {
        return uGb;
    }

    public void setuGb(int uGb) {
        this.uGb = uGb;
    }

    public double getTrail0() {
        return trail0;
    }

    public void setTrail0(double trail0) {
        this.trail0 = trail0;
    }
}
