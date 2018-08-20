package com.github.schmittjoaopedro.aco.ls.us;

import com.github.schmittjoaopedro.graph.Graph;

public class CoordinatesGenius {

    public static int MAX_N = 500;

    public int task;

    public Coordinate g[] = new Coordinate[MAX_N + 1];

    public double d[][] = new double[MAX_N + 1][MAX_N + 1];

    public void setXYValues(int n, int cities, double x, double y) {
        task = n;
        g[cities + 1] = new Coordinate();
        g[cities + 1].x = x;
        g[cities + 1].y = y;
    }

    public void distances(Graph graph) {
        for (int i = 1; i <= (task); i++) {
            for (int j = 1; j <= (task); j++) {
                if (i != j) {
                    d[i][j] = graph.getEdge(i - 1, j - 1).getCost();
                }
            }
        }
    }
}
