package com.github.schmittjoaopedro.vrp.mpdptw;

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

    public double announceTime;

    public Status status = Status.Idle;

    public int getNodeId() {
        return nodeId;
    }

    public boolean isCommitted() {
        return Status.Committed.equals(status);
    }

    public boolean isIdle() {
        return Status.Idle.equals(status);
    }

    public boolean isTransition() {
        return Status.Transition.equals(status);
    }

    public enum Status {
        Idle, Transition, Committed
    }
}
