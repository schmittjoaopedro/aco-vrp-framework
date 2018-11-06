package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Timer {

    private long startTime;

    private InOut inOut;

    public Timer(InOut inOut) {
        this.inOut = inOut;
    }

    //virtual and real time of day are computed and stored to allow at later time the computation of the elapsed time
    public void start_timers() {
        startTime = getCurrentTime();
    }

    //return the time used in seconds (virtual or real, depending on type)
    public double elapsed_time() {
        return (getCurrentTime() - startTime) / 1000.0;
    }

    public long getCurrentTime() {
        if (inOut.isDiscreteTime) {
            double x = inOut.currentTimeSlice;
            double noSolCur = inOut.noSolutions;
            double noSolEst = getNoSolEstimated(x);
            long time = (long) ((((x - 1) + (noSolCur / noSolEst)) / 50.0) * 100.0 * 1000.0);
            return Math.max(time, 0);
        } else {
            return System.currentTimeMillis();
        }
    }

    public double getNoSolEstimated(double x) {
        // Calculated with Excel
        return 0.0195 * Math.pow(x, 4.0) - 0.6707 * Math.pow(x, 3.0) + 18.528 * Math.pow(x, 2) + 20.967 * x + 12.558;
    }
}
