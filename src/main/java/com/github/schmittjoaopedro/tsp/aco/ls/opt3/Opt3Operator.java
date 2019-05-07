package com.github.schmittjoaopedro.tsp.aco.ls.opt3;

import com.github.schmittjoaopedro.tsp.aco.ls.LocalSearchUtils;
import com.github.schmittjoaopedro.tsp.graph.Graph;

import java.util.Random;

public class Opt3Operator {

    private int[] tour;

    private int[] optimizedTour;

    private int distances[][];

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

    /**
    Sub-tour optimization is used to optimize a route were the vehicle have already visited clients.
    The phase indicates the current vehicle position, or the last visited client of the route.
    To optimize in this cases, we create a sub-graph with the not already visited clients, and adapt the
    tour to a sub-tour that is equivalent to this sub-graph.
     */
    public boolean init(Graph graph, Random random, int[] tour, int phase) {
        int subGraphLength = graph.getVertexCount() - phase;
        if (phase < 0) {
            return init(graph, random, tour);
        } else if (hasSufficientVertices(subGraphLength)) { // number of minimum vertices for US of sub-tour
            this.phase = phase;
            subTourOptimization = true;
            subTourMap = LocalSearchUtils.createSubTourMap(tour, phase, subGraphLength);
            return init(
                    LocalSearchUtils.createSubGraph(subTourMap, graph, tour[0], subGraphLength),
                    random,
                    tour);
        } else {
            return false;
        }
    }

    public boolean init(Graph graph, Random random, int[] tour) {
        if (hasSufficientVertices(graph.getVertexCount())) { // number of minimum vertices for US
            this.graph = graph;
            this.random = random;
            this.tour = tour;
            defineStructures(this.graph);
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
                defineStructures(graph);
            }
            // Init from git
            return true;
        } else {
            return false;
        }
    }

    public void optimize() {
        if (subTourOptimization) {
            /*
            We create a new tour to run optimization based on the sub-tour map, as the sub-tour map
            was created considering the order of the original tour, the result will be a tour with the
            nodes in sequence, e.g.: [0,1,2,3,...,n,0]
             */
            optimizedTour = LocalSearchUtils.createSubTour(subTourMap);
        } else {
            // If we are not executing sub-optimization then we use a clone of the original tour
            optimizedTour = tour.clone();
        }
        if (!symmetric) {
            // If the matrix is asymmetric we need to insert the ghost nodes to make a valid route to optimize
            optimizedTour = LocalSearchUtils.convertToAssymetricTour(optimizedTour);
        }
        LocalSearch3Opt.three_opt_first(random, optimizedTour, distances.length, distances, nn_list, nn_ls);
    }

    public int[] getResult() {
        int[] tourResult = convertResult(optimizedTour);
        return LocalSearchUtils.getResult(subTourOptimization, subTourMap, tourResult, tour, phase);
    }

    public int[] convertResult(int[] tourToConvert) {
        int count = 0;
        // We use the graph number of vertex because in asymmetric problems the tourToConvert will have twice the size
        int[] tourCopy = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < tourToConvert.length; i++) {
            // In case of asymmetric matrix we need to ignore the ghost instances.
            if (graph.getVertex(tourToConvert[i]) != null) {
                tourCopy[count] = graph.getVertex(tourToConvert[i]).getId();
                count++;
            }
        }
        if (isKeepOriginalDepotPosition()) {
            // Differently of US, the 3-opt operator rotate the depot along the route.
            // Therefore, in sub-tour optimization we fix the first node as depot and
            // rotate this to the first position to make it valid
            tourCopy = LocalSearchUtils.getRotatedRouteToFirstNode(tourCopy, 0);
        }
        return tourCopy;
    }

    public boolean isKeepOriginalDepotPosition() {
        return keepOriginalDepotPosition;
    }

    public void setKeepOriginalDepotPosition(boolean keepOriginalDepotPosition) {
        this.keepOriginalDepotPosition = keepOriginalDepotPosition;
    }

    private void defineStructures(Graph graph) {
        distances = new int[graph.getVertexCount()][graph.getVertexCount()];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                if (i != j) {
                    distances[i][j] = (int) graph.getEdge(i, j).getCost();
                    min = Math.min(min, distances[i][j]);
                    max = Math.max(max, distances[i][j]);
                }
            }
        }
        symmetric = LocalSearchUtils.isSymmetric(distances);
        if (!symmetric) {
            distances = LocalSearchUtils.asymmetricToSymmetric(distances, min - 1, max + 1);
        }
        nn_list = LocalSearchUtils.createNNList(distances.length, nn_ls, distances);
    }

    private boolean hasSufficientVertices(int i) {
        return i > 9; // Num of min edges necessary to US, to make the operators fair we assume the same value for 3-opt
    }
}
