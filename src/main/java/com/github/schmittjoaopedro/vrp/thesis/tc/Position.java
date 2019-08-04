package com.github.schmittjoaopedro.vrp.thesis.tc;

public class Position {
    public double cost = Double.MAX_VALUE;
    public int pickupPos;
    public int deliveryPos;
    public int requestId;
    public int vehicle;

    public double getCost() {
        return cost;
    }
}
