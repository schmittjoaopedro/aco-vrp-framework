package com.github.schmittjoaopedro.tsp.aco.ls.us;

import com.github.schmittjoaopedro.tsp.graph.Graph;

public class CoordinatesGenius {

    public int MAX_N;

    public int task;

    public Coordinate g[];

    public float d[][];

    public CoordinatesGenius() {
        MAX_N = 500;
        g = new Coordinate[MAX_N + 1];
        d = new float[MAX_N + 1][MAX_N + 1];
    }

    public CoordinatesGenius(int MAX_N) {
        this.MAX_N = MAX_N;
        g = new Coordinate[MAX_N + 1];
        d = new float[MAX_N + 1][MAX_N + 1];
    }

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
                    d[i][j] = (float) graph.getEdge(i - 1, j - 1).getCost();
                }
            }
        }
    }
}
