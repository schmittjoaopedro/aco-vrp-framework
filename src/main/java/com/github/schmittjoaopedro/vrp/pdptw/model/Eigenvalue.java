package com.github.schmittjoaopedro.vrp.pdptw.model;

public class Eigenvalue {

    public int numberOfVehicles;
    public int totalTravelCost;
    public int scheduleDuration;
    public int totalWaitingTime;

    public Eigenvalue(int NV, int TC, int SD, int WT) {
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

    public boolean isSame(Eigenvalue e) {
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
