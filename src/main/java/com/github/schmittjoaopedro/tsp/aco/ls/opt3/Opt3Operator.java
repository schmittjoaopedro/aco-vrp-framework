package com.github.schmittjoaopedro.tsp.aco.ls.opt3;

import com.github.schmittjoaopedro.tsp.aco.ls.LocalSearchUtils;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.asymmetric.Asymmetric3Opt;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.symmetric.Symmetric3Opt;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Opt3Operator {

    private int[] tour;

    private int[] optimizedTour;

    private int distances[][];

    private int n;

    private int nn_ls = 20;

    private int nn_list[][];

    boolean symmetric = true;

    private Graph graph;

    private int subTourMap[];

    private int phase;

    private int max = 0;

    private int min = Integer.MAX_VALUE;

    private boolean subTourOptimization = false;

    private boolean keepOriginalDepotPosition = false;

    private Random random;

    public boolean init(Graph graph, Random random, int[] tour, int phase) {
        int subGraphLength = graph.getVertexCount() - phase;
        if (phase < 0) {
            return init(graph, random, tour);
        } else if (hasSufficientVertices(subGraphLength)) { // number of minimum vertices for US of sub-tour
            this.phase = phase;
            subTourOptimization = true;
            subTourMap = LocalSearchUtils.createSubTourMap(tour, phase, subGraphLength);
            Graph newGraph = LocalSearchUtils.createSubGraph(subTourMap, graph, tour[0], subGraphLength);
            return init(newGraph, random, tour);
        } else {
            return false;
        }
    }

    public boolean init(Graph graph, Random random, int[] tour) {
        if (hasSufficientVertices(graph.getVertexCount())) { // number of minimum vertices for US
            this.graph = graph;
            this.random = random;
            this.tour = tour;
            defineStructures(graph, tour.length);
            // Init from git
            return true;
        } else {
            return false;
        }
    }

    public boolean initStaticVersion(Graph graph, Random random, int[] tour) {
        if (hasSufficientVertices(graph.getVertexCount())) { // number of minimum vertices for US
            this.graph = graph;
            this.random = random;
            this.tour = tour;
            if (distances == null) {
                defineStructures(graph, tour.length);
            }
            // Init from git
            return true;
        } else {
            return false;
        }
    }

    public void optimize() {
        if (subTourOptimization) {
            optimizedTour = LocalSearchUtils.createSubTour(subTourMap);
        } else {
            optimizedTour = tour.clone();
        }
        if (symmetric) {
            Symmetric3Opt.three_opt_first(random, optimizedTour, n, distances, nn_list, nn_ls);
        } else {
            Asymmetric3Opt.threeOpt(optimizedTour, distances); // It can enter in eternal loop
        }
    }

    public int[] getResult() {
        if (isKeepOriginalDepotPosition()) {
            // Differently of US, the 3-opt operator rotate the depot along the route.
            // Therefore, in sub-tour optimization we fix the first node as depot
            optimizedTour = LocalSearchUtils.getRotatedRouteToFirstNode(optimizedTour, 0);
        }
        if (subTourOptimization) {
            return LocalSearchUtils.getResult(subTourOptimization, subTourMap, optimizedTour, tour, phase);
        } else {
            return optimizedTour;
        }
    }

    public boolean isKeepOriginalDepotPosition() {
        return keepOriginalDepotPosition;
    }

    public void setKeepOriginalDepotPosition(boolean keepOriginalDepotPosition) {
        this.keepOriginalDepotPosition = keepOriginalDepotPosition;
    }

    private void defineStructures(Graph graph, int tourLength) {
        distances = new int[tourLength - 1][tourLength - 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                if (i != j) {
                    distances[i][j] = (int) graph.getEdge(i, j).getCost();
                    min = Math.min(min, distances[i][j]);
                    max = Math.max(max, distances[i][j]);
                }
            }
        }
        n = graph.getVertexCount();
        symmetric = LocalSearchUtils.isSymmetric(distances);
        if (symmetric) {
            nn_list = LocalSearchUtils.createNNList(n, nn_ls, distances);
        } else {
            distances = LocalSearchUtils.asymmetricToSymmetric(distances, min - 1, max + 1);
        }
    }

    private boolean hasSufficientVertices(int i) {
        return i > 9; // Num of min edges necessary to US, to make the operators fair we assume the same value for 3-opt
    }
}
