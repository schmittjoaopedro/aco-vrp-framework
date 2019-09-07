package com.github.schmittjoaopedro.vrp.thesis.problem;

public class Request {

    public int requestId;

    public Task pickupTask;

    public Task deliveryTask;

    public double announceTime;

    public double lastIdleTime; // Time when vehicle starts to go visit this request

    public int getRequestId() {
        return requestId;
    }

    public double getAnnounceTime() {
        return announceTime;
    }

    public boolean isVehicleRelocatable() {
        return pickupTask.isIdle() && deliveryTask.isIdle();
    }

    public boolean isFullyVisited() {
        return !pickupTask.isIdle() && !deliveryTask.isIdle();
    }

}
