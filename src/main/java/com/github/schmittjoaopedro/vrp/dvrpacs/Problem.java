package com.github.schmittjoaopedro.vrp.dvrpacs;

public class Problem {

    // Instance name.
    private String name;

    // Number of nearest neighbors.
    private int numNear;

    // Array of classes containing coordinates of nodes.
    private Point[] nodes;

    // Distance matrix: distance[i][j] gives distance.
    private double[][] distance;

    // Nearest neighbor list; contains for each node i a sorted list of num_near nearest neighbors.
    int[][] nnList;

    // Nearest neighbor list; contains for each node i a sorted list of num_near nearest neighbors including the depot
    int[][] nnListAll;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumNear() {
        return numNear;
    }

    public void setNumNear(int numNear) {
        this.numNear = numNear;
    }

    public Point[] getNodes() {
        return nodes;
    }

    public void setNodes(Point[] nodes) {
        this.nodes = nodes;
    }

    public double[][] getDistance() {
        return distance;
    }

    public void setDistance(double[][] distance) {
        this.distance = distance;
    }

    public int[][] getNnList() {
        return nnList;
    }

    public void setNnList(int[][] nnList) {
        this.nnList = nnList;
    }

    public int[][] getNnListAll() {
        return nnListAll;
    }

    public void setNnListAll(int[][] nnListAll) {
        this.nnListAll = nnListAll;
    }
}
