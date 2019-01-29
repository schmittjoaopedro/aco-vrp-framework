package com.github.schmittjoaopedro.vrp.mpdptw;

public class ProblemInstance {

    public int noNodes;

    public int noReq;

    public int noMaxVehicles;

    public double vehicleCapacity;

    public Depot depot;

    public double[][] distances;

    public Request[] pickUpRequests;

    public Request[] deliveryRequests;
}
