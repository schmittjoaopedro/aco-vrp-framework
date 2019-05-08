package com.github.schmittjoaopedro.statistic;

import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.*;

public class GlobalStatistics {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private Long currentTime;

    private List<Vertex> bestRoute = new ArrayList<>();

    private double bestSoFarTC;

    private double bestSoFarNV;

    public void startTimer() {
        currentTime = System.currentTimeMillis();
    }

    public void endTimer(String eventName) {
        timeStatistics.put(eventName, System.currentTimeMillis() - currentTime);
    }

    public double getBestSoFarTC() {
        return bestSoFarTC;
    }

    public void setBestSoFarTC(double bestSoFarTC) {
        this.bestSoFarTC = bestSoFarTC;
    }

    public double getBestSoFarNV() {
        return bestSoFarNV;
    }

    public void setBestSoFarNV(double bestSoFarNV) {
        this.bestSoFarNV = bestSoFarNV;
    }

    public List<Vertex> getBestRoute() {
        return bestRoute;
    }

    public void setBestRoute(List<Vertex> bestRoute) {
        Set<Integer> bestRouteValidator = new HashSet<>();
        for (Vertex vertex : bestRoute) {
            bestRouteValidator.add(vertex.getId());
        }
        if (bestRouteValidator.size() != bestRoute.size() - 1) {
            throw new RuntimeException("Invalid best route");
        }
        this.bestRoute = bestRoute;
    }

    public Map<String, Long> getTimeStatistics() {
        return timeStatistics;
    }
}
