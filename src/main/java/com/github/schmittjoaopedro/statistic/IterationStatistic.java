package com.github.schmittjoaopedro.statistic;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class IterationStatistic {

    private Map<String, Long> timeStatistics = new HashMap<>();

    private double bestSoFarNV;

    private double iterationBestNV;

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

    private double feasible;

    public void startTimer() {
        currentTime = System.currentTimeMillis();
    }

    public void endTimer(String eventName) {
        timeStatistics.put(eventName, System.currentTimeMillis() - currentTime);
    }

    public double getBestSoFarNV() {
        return bestSoFarNV;
    }

    public void setBestSoFarNV(double bestSoFarNV) {
        this.bestSoFarNV = bestSoFarNV;
    }

    public double getIterationBestNV() {
        return iterationBestNV;
    }

    public void setIterationBestNV(double iterationBestNV) {
        this.iterationBestNV = iterationBestNV;
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

    public double getFeasible() {
        return feasible;
    }

    public void setFeasible(double feasible) {
        this.feasible = feasible;
    }

    @Override
    public String toString() {
        StringBuilder log = new StringBuilder();
        if (this.getIteration() > 0)
            log.append("IT. " + StringUtils.rightPad(String.format("%d", (int) this.getIteration()), 8));
        if (this.getBestSoFar() > 0)
            log.append("BSF: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getBestSoFar()), 11));
        if (this.getBestSoFarNV() > 0)
            log.append("BSF. NV: " + StringUtils.rightPad(String.format(Locale.US, "%.1f", this.getBestSoFarNV()), 5));
        if (this.getBestSoFarSd() > 0)
            log.append("BSF. SD: " + StringUtils.rightPad(String.format(Locale.US, "%.1f", this.getBestSoFarSd()), 8));
        if (this.getIterationWorst() > 0)
            log.append("IT. WORST: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationWorst()), 12));
        if (this.getIterationBest() > 0)
            log.append("IT. BEST: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationBest()), 12));
        if (this.getIterationBestNV() > 0)
            log.append("IT. BEST NV: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationBestNV()), 8));
        if (this.getIterationMean() > 0)
            log.append("IT. MEAN: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationMean()), 12));
        if (this.getIterationSd() > 0)
            log.append("IT. SD: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getIterationSd()), 10));
        if (this.getBranchFactor() > 0)
            log.append("BRANCH FACTOR: " + StringUtils.rightPad(String.format(Locale.US, "%.3f", this.getBranchFactor()), 10));
        if (this.getDiversity() > 0)
            log.append("DIV: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getDiversity()), 10));
        if (getPenaltyRate() > 0)
            log.append("PEN. RATE: " + StringUtils.rightPad(String.format(Locale.US, "%.2f", this.getPenaltyRate()), 8));
        if (getFeasible() > 0) log.append("FEASIBLE: " + getFeasible());
        return log.toString();
    }

    public String toStringCsv() {
        StringBuilder log = new StringBuilder();
        log.append(String.format("%d", (int) this.getIteration())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getBestSoFar())).append(",");
        log.append(String.format(Locale.US, "%.1f", this.getBestSoFarNV())).append(",");
        log.append(String.format(Locale.US, "%.1f", this.getBestSoFarSd())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getIterationWorst())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getIterationBest())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getIterationBestNV())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getIterationMean())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getIterationSd())).append(",");
        log.append(String.format(Locale.US, "%.2f", this.getPenaltyRate())).append(",");
        log.append(getFeasible());
        return log.toString();
    }

    public String toStringCsvHeader() {
        StringBuilder log = new StringBuilder();
        log.append("iteration").append(",");
        log.append("bestSoFar").append(",");
        log.append("bestSoFarNv").append(",");
        log.append("bestSoFarSd").append(",");
        log.append("worst").append(",");
        log.append("best").append(",");
        log.append("bestNv").append(",");
        log.append("mean").append(",");
        log.append("sd").append(",");
        log.append("penaltyRate");
        return log.toString();
    }
}
