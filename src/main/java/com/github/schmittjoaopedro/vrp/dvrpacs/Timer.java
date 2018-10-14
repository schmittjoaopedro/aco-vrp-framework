package com.github.schmittjoaopedro.vrp.dvrpacs;

public class Timer {

    private long startTime;

    // Virtual and real time of day are computed and stored to allow at later time the computation of the elapsed time
    public void startTimers() {
        startTime = System.currentTimeMillis();
    }

    // Return the time used in seconds (virtual or real, depending on type)
    public double elapsedTime() {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

}
