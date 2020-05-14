package com.github.schmittjoaopedro.vrp.thesis.algorithms.stop;

public class TimeCriteria implements StopCriteria {

    private long startTime;

    private long maxTime;

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isContinue() {
        return System.currentTimeMillis() - startTime < maxTime;
    }

    @Override
    public void update() {
    }

    @Override
    public double getScaledTime() {
        double curr = System.currentTimeMillis() - startTime;
        double max = maxTime;
        return curr / max;
    }

    @Override
    public StopCriteria copy() {
        return of(maxTime);
    }

    public static TimeCriteria of(long maxTime) {
        TimeCriteria timeCriteria = new TimeCriteria();
        timeCriteria.maxTime = maxTime;
        return timeCriteria;
    }
}
