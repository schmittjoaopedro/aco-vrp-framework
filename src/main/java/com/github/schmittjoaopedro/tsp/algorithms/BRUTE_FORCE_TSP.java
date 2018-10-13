package com.github.schmittjoaopedro.tsp.algorithms;

import com.github.schmittjoaopedro.tsp.graph.Graph;

public class BRUTE_FORCE_TSP {

    private Graph graph;

    private int[] bestSoFar;

    private double cost;

    public BRUTE_FORCE_TSP(Graph graph) {
        this.graph = graph;
        bestSoFar = new int[graph.getVertexCount() + 1];
    }

    public void run() {
        int tour[] = new int[graph.getVertexCount() + 1];
        boolean visited[] = new boolean[graph.getVertexCount()];
        cost = Double.MAX_VALUE;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            tour[0] = i;
            visited[i] = true;
            setNextStep(tour, visited, 1);
            visited[i] = false;
        }
    }

    private void setNextStep(int tour[], boolean visited[], int step) {
        if (step == visited.length) {
            tour[step] = tour[0];
            double cost = calculateCost(tour);
            if (cost < this.cost) {
                this.cost = cost;
                this.bestSoFar = tour.clone();
                System.out.println(getTourString(tour) + " = " + cost);
            }
        } else {
            for (int i = 0; i < graph.getVertexCount(); i++) {
                if (!visited[i]) {
                    tour[step] = i;
                    visited[i] = true;
                    setNextStep(tour, visited, step + 1);
                    visited[i] = false;
                }
            }
        }
    }

    private double calculateCost(int tour[]) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(tour[i], tour[i + 1]).getCost();
        }
        return cost;
    }

    public int[] getBestSoFar() {
        return bestSoFar;
    }

    public void setBestSoFar(int[] bestSoFar) {
        this.bestSoFar = bestSoFar;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTourString() {
        return getTourString(bestSoFar);
    }

    public String getTourString(int tour[]) {
        StringBuilder tourString = new StringBuilder("[");
        for (int city : tour) {
            tourString.append(city).append(", ");
        }
        tourString.delete(tourString.length() - 2, tourString.length()).append("]");
        return tourString.toString();
    }
}
