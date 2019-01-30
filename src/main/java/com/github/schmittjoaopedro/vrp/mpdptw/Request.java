package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;
import java.util.List;

public class Request {

    public int nodeId;

    public int requestId;

    public double x;

    public double y;

    public boolean isPickup;

    public boolean isDeliver;

    public double demand;

    public double twStart;

    public double twEnd;

    public double serviceTime;

    public List<Request> feasibleRequests = new ArrayList<>();

}
