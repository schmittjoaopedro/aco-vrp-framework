package com.github.schmittjoaopedro.vrp.mpdptw.operators;


public class Req {

    public int vehicleId;
    public int requestId;
    public double cost;

    public Req(int vehicleId, int requestId) {
        this.vehicleId = vehicleId;
        this.requestId = requestId;
    }

    public Req(int vehicleId, int requestId, double cost) {
        this.vehicleId = vehicleId;
        this.requestId = requestId;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}