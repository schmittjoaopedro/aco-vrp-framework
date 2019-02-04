package com.github.schmittjoaopedro.tsp.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IterationStatistic {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private double iteration;

    private double iterationBest;

    private double iterationWorst;

    private double iterationMean;

    private double iterationSd;

    private double diversity;

    private double branchFactor;

    private double bestSoFar;

    private double bestSoFarSd;

    private int[] tour;

    private int[] mvsbTour;

    private Long currentTime;

    private double penaltyRate;

    private boolean feasible;

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

    public double getIterationSd() {
        return iterationSd;
    }

    public void setIterationSd(double iterationSd) {
        this.iterationSd = iterationSd;
    }

    public int[] getTour() {
        return tour;
    }

    public void setTour(int[] tour) {
        this.tour = tour;
    }

    public int[] getMvsbTour() {
        return mvsbTour;
    }

    public void setMvsbTour(int[] mvsbTour) {
        this.mvsbTour = mvsbTour;
    }

    public double getPenaltyRate() {
        return penaltyRate;
    }

    public double getBestSoFarSd() {
        return bestSoFarSd;
    }

    public void setBestSoFarSd(double bestSoFarSd) {
        this.bestSoFarSd = bestSoFarSd;
    }

    public void setPenaltyRate(double penaltyRate) {
        this.penaltyRate = penaltyRate;
    }

    public boolean isFeasible() {
        return feasible;
    }

    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    @Override
    public String toString() {
        StringBuilder log = new StringBuilder();
        log.append("IT. " + StringUtils.rightPad(String.format("%d", (int) this.getIteration()), 8));
        log.append("BSF: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getBestSoFar()), 15));
        log.append("BSF. SD: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getBestSoFarSd()), 15));
        log.append("IT. WORST: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationWorst()), 15));
        log.append("IT. BEST: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationBest()), 15));
        log.append("IT. MEAN: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationMean()), 15));
        log.append("IT. SD: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationSd()), 15));
        log.append("BRANCH FACTOR: " + StringUtils.rightPad(String.format(Locale.US, "%.3f", this.getBranchFactor()), 10));
        log.append("DIV: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getDiversity()), 10));
        if (getPenaltyRate() > 0) {
            log.append("PEN. RATE: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getPenaltyRate()), 8));
            log.append("FEASIBLE: " + (feasible ? "T" : "F"));
        }
        return log.toString();
    }
}
