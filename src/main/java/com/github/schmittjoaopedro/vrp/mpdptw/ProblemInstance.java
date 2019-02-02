package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.List;
import java.util.Map;

public class ProblemInstance {

    public int noNodes;

    public int noReq;

    public int noMaxVehicles;

    public double vehicleCapacity;

    public Depot depot;

    public double[][] distances;

    public Request[] requests;

    public Map<Integer, List<Request>> pickups;

    public Map<Integer, Request> delivery;
}
