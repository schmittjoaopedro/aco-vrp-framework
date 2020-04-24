package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

public class EigenValue {

    private int numberOfVehicles;

    private int totalTravelCost;

    private int scheduleDuration;

    private int totalWaitingTime;

    public EigenValue(int NV, int TC, int SD, int WT) {
        this.numberOfVehicles = NV;
        this.totalTravelCost = TC;
        this.scheduleDuration = SD;
        this.totalWaitingTime = WT;
    }

    public int getNV() {
        return this.numberOfVehicles;
    }

    public int getTC() {
        return this.totalTravelCost;
    }

    public int getSD() {
        return this.scheduleDuration;
    }

    public int getWT() {
        return this.totalWaitingTime;
    }

    public boolean isSame(EigenValue e) {
        if (this.numberOfVehicles == e.numberOfVehicles &&
                this.totalTravelCost == e.totalTravelCost &&
                this.scheduleDuration == e.scheduleDuration &&
                this.totalWaitingTime == e.totalWaitingTime) {
            return true;
        } else {
            return false;
        }
    }
}
