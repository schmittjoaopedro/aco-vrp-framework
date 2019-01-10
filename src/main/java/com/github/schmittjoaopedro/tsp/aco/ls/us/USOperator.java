package com.github.schmittjoaopedro.tsp.aco.ls.us;


import com.github.schmittjoaopedro.tsp.aco.ls.LocalSearchUtils;
import com.github.schmittjoaopedro.tsp.graph.Graph;

/**
 * Adapted from C++ version proposed in:
 * <p>
 * M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for dynamic traveling
 * salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7, pp. 1743-1756, 2017.
 * -> https://mavrovouniotis.github.io/Codes/MMAS_US.zip
 * <p>
 * To compare these tests with the C program, remember that the random number generator can generate 0. Tho evict
 * this problem, run the algorithm with magnitude = -0.1
 */
public class USOperator {

    private int problemSize;

    private RouteGenius genius;

    private CoordinatesGenius tspFile;

    private int[] originalTour;

    private int[] optimizedTour;

    private double cost;

    private int subTourMap[];

    private int phase;

    private boolean stopEternalLoops = false;

    private boolean subTourOptimization = false;

    public boolean init(Graph graph, int[] tour) {
        if (hasSufficientVertices(graph.getVertexCount())) { // number of minimum vertices for US
            this.optimizedTour = tour;
            this.cost = LocalSearchUtils.fitnessEvaluation(graph, tour);
            problemSize = graph.getVertexCount();
            tspFile = new CoordinatesGenius(graph.getVertexCount());
            for (int i = 0; i < graph.getVertexCount(); i++) {
                tspFile.setXYValues(graph.getVertexCount(), i, graph.getVertex(i).getX(), graph.getVertex(i).getY());
            }
            tspFile.distances(graph);
            genius = new RouteGenius(tspFile, 3, Math.min(15, problemSize - 1));
            genius.findEternalLoops = stopEternalLoops;
            return true;
        } else {
            return false;
        }
    }

    public boolean init(Graph graph, int[] tour, int phase) {
        int subGraphLength = graph.getVertexCount() - phase;
        if (phase < 0) {
            return init(graph, tour);
        } else if (hasSufficientVertices(subGraphLength)) { // number of minimum vertices for US of sub-tour
            this.phase = phase;
            this.originalTour = tour;
            subTourOptimization = true;
            subTourMap = LocalSearchUtils.createSubTourMap(tour, phase, subGraphLength);
            return init(
                    LocalSearchUtils.createSubGraph(subTourMap, graph, tour[0], subGraphLength),
                    LocalSearchUtils.createSubTour(subTourMap));
        } else {
            return false;
        }
    }

    public void optimize() {
        // Init genius
        TourElem element;
        genius.initialize();
        genius.initNeighborhood(tspFile.task);
        genius.littleTurns(optimizedTour[0], tspFile.g);
        genius.addNextNode(optimizedTour[0], tspFile.task, tspFile.d);
        for (int i = 1; i < problemSize; i++) {
            genius.addOneTurns(optimizedTour[i], tspFile.g);
            genius.addNextNode(optimizedTour[i], tspFile.task, tspFile.d);
        } //copy the tour and calculates the nearest neighbours of the nodes.
        if (genius.numberedTurns()) {
            genius.routeCopy(tspFile.task, tspFile.g, tspFile.d, cost);
            element = genius.t.ptr;
            for (int i = 0; i < problemSize; i++) {
                optimizedTour[i] = element.node - 1;
                element = element.next;
            }
            optimizedTour[problemSize] = optimizedTour[0];
        }
    }

    public int[] getResult() {
        return LocalSearchUtils.getResult(subTourOptimization, subTourMap, optimizedTour, originalTour, phase);
    }

    public void setStopEternalLoops(boolean active) {
        if (genius != null) {
            genius.findEternalLoops = active;
        } else {
            stopEternalLoops = active;
        }
    }

    private boolean hasSufficientVertices(int i) {
        return i > 9; // Num of min edges necessary to US
    }
}
