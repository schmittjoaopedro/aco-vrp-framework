package com.github.schmittjoaopedro.tools;

import java.util.HashMap;
import java.util.Map;

public class GlobalStatistics {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private Long currentTime;

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
}
