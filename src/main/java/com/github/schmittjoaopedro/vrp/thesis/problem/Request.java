package com.github.schmittjoaopedro.vrp.thesis.problem;

public class Request {

    public int requestId;

    public Task pickupTask;

    public Task deliveryTask;

    public double announceTime;

    public int getRequestId() {
        return requestId;
    }

    public double getAnnounceTime() {
        return announceTime;
    }

}
