package com.github.schmittjoaopedro.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class IterationStatistic {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private double iteration;

    private double iterationBest;

    private double iterationWorst;

    private double iterationMean;

    private double diversity;

    private double branchFactor;

    private double bestSoFar;

    private Long currentTime;

    public void startTimer() {
        currentTime = System.currentTimeMillis();
    }

    public void endTimer(String eventName) {
        timeStatistics.put(eventName, System.currentTimeMillis() - currentTime);
    }

    public double getIteration() {
        return iteration;
    }

    public void setIteration(double iteration) {
        this.iteration = iteration;
    }

    public double getIterationBest() {
        return iterationBest;
    }

    public void setIterationBest(double iterationBest) {
        this.iterationBest = iterationBest;
    }

    public double getIterationWorst() {
        return iterationWorst;
    }

    public void setIterationWorst(double iterationWorst) {
        this.iterationWorst = iterationWorst;
    }

    public double getIterationMean() {
        return iterationMean;
    }

    public void setIterationMean(double iterationMean) {
        this.iterationMean = iterationMean;
    }

    public double getDiversity() {
        return diversity;
    }

    public void setDiversity(double diversity) {
        this.diversity = diversity;
    }

    public double getBranchFactor() {
        return branchFactor;
    }

    public void setBranchFactor(double branchFactor) {
        this.branchFactor = branchFactor;
    }

    public double getBestSoFar() {
        return bestSoFar;
    }

    public void setBestSoFar(double bestSoFar) {
        this.bestSoFar = bestSoFar;
    }

    @Override
    public String toString() {
        StringBuilder log = new StringBuilder();
        log.append("IT. " + StringUtils.rightPad(String.format("%d", (int) this.getIteration()), 8));
        log.append("BSF: " + StringUtils.rightPad(String.format("%.2f", this.getBestSoFar()), 15));
        log.append("IT. WORST: " + StringUtils.rightPad(String.format("%.2f", this.getIterationWorst()), 15));
        log.append("IT. BEST: " + StringUtils.rightPad(String.format("%.2f", this.getIterationBest()), 15));
        log.append("IT. MEAN: " + StringUtils.rightPad(String.format("%.2f", this.getIterationMean()), 15));
        log.append("BRANCH FACTOR: " + StringUtils.rightPad(String.format("%.2f", this.getBranchFactor()), 15));
        log.append("Div: " + StringUtils.rightPad(String.format("%.2f", this.getDiversity()), 15));
        return log.toString();
    }
}
