package com.github.schmittjoaopedro.tsp.tools;

import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.*;

public class GlobalStatistics {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private Long currentTime;

    private List<Vertex> bestRoute = new ArrayList<>();

    private double bestSoFar;

    public void startTimer() {
        currentTime = System.currentTimeMillis();
    }

    public void endTimer(String eventName) {
        timeStatistics.put(eventName, System.currentTimeMillis() - currentTime);
    }

    public double getBestSoFar() {
        return bestSoFar;
    }

    public void setBestSoFar(double bestSoFar) {
        this.bestSoFar = bestSoFar;
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
