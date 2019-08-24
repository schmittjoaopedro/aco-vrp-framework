package com.github.schmittjoaopedro.vrp.thesis.algorithms;

public class Statistics {

    public int[] nv;

    public double[] tc;

    public Statistics(int maxIterations) {
        nv = new int[maxIterations];
        tc = new double[maxIterations];
    }
}
