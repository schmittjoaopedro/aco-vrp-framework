package com.github.schmittjoaopedro.vrp.thesis.algorithms.stop;

public class IterationCriteria implements StopCriteria {

    private int currentIteration;

    public long maxIterations;

    @Override
    public void reset() {
        currentIteration = 1;
    }

    @Override
    public boolean isContinue() {
        return currentIteration < maxIterations;
    }

    @Override
    public void update() {
        currentIteration++;
    }

    @Override
    public double getScaledTime() {
        double curr = currentIteration;
        double max = maxIterations;
        return curr / max;
    }

    @Override
    public StopCriteria copy() {
        return of(maxIterations);
    }

    public static IterationCriteria of(long maxIterations) {
        IterationCriteria iterationCriteria = new IterationCriteria();
        iterationCriteria.maxIterations = maxIterations;
        return iterationCriteria;
    }
}
