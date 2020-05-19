package com.github.schmittjoaopedro.vrp.thesis.algorithms.stop;

public interface StopCriteria {

    void reset();

    void update();

    boolean isContinue();

    double getScaledTime();

    StopCriteria copy();

}
