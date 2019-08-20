package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

public class RouteTimes {

    public double[] startTime;

    public double[] waitingTime;

    public double[] slackTime;

    public RouteTimes(int size) {
        startTime = new double[size];
        waitingTime = new double[size];
        slackTime = new double[size];
    }

}
