package com.github.schmittjoaopedro.tools;

import com.github.schmittjoaopedro.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.bestRoute = bestRoute;
    }
}
