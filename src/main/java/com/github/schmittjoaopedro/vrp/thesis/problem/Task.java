package com.github.schmittjoaopedro.vrp.thesis.problem;

public class Task {

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

    public Status status;

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
        Idle, Transition, Committed;
    }
}
