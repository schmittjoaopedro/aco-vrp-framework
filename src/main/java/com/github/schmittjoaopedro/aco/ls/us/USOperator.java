package com.github.schmittjoaopedro.aco.ls.us;


import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.Vertex;

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

    private int[] tour;

    private double cost;

    private Graph graph;

    private int subTourMap[];

    private int[] route;

    private int phase;

    private boolean stopEternalLoops = false;

    private boolean subTourOptimization = false;

    public boolean init(Graph graph, int[] tour) {
        if (hasSuficientVertices(graph.getVertexCount())) { // number of minimum vertices for US
            this.tour = tour;
            this.graph = graph;
            this.cost = fitnessEvaluation(tour);
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

    public boolean init(Graph graph, int[] route, int phase) {
        int subGraphLength = graph.getVertexCount() - phase;
        if (phase < 0) {
            return init(graph, route);
        } else if (hasSuficientVertices(subGraphLength)) { // number of minimum vertices for US of sub-tour
            this.phase = phase;
            this.route = route;
            subTourOptimization = true;
            createSubTourMap(route, phase, subGraphLength);
            Graph newGraph = createSubGraph(graph, route[0], subGraphLength);
            return init(newGraph, createSubTour());
        } else {
            return false;
        }
    }

    public int[] createSubTour() {
        int[] vertexTour = new int[subTourMap.length + 1];
        for (int i = 0; i < subTourMap.length; i++) {
            vertexTour[i] = i;
        }
        vertexTour[subTourMap.length] = vertexTour[0];
        return vertexTour;
    }

    public Graph createSubGraph(Graph graph, int toId, int subTourLength) {
        Graph newGraph = new Graph();
        for (int i = 0; i < subTourLength; i++) {
            Vertex vertex = new Vertex(i);
            vertex.setX(graph.getVertex(subTourMap[i]).getX());
            vertex.setY(graph.getVertex(subTourMap[i]).getY());
            newGraph.addVertex(vertex);
        }
        int edgeId = 0;
        for (int i = 0; i < subTourLength; i++) {
            for (int j = 0; j < subTourLength; j++) {
                if (i != j) {
                    Edge edge = new Edge(edgeId++);
                    edge.setFrom(newGraph.getVertex(i));
                    edge.setTo(newGraph.getVertex(j));
                    edge.setCost(graph.getEdge(subTourMap[i], subTourMap[j]).getCost());
                    edge.getFrom().getAdj().put(edge.getToId(), edge);
                    newGraph.addEdge(edge);
                }
            }
        }
        for (int i = 1; i < subTourLength; i++) {
            newGraph.getEdge(i, 0).setCost(graph.getEdge(subTourMap[i], toId).getCost());
        }
        return newGraph;
    }

    public void createSubTourMap(int[] route, int phase, int subTourLength) {
        subTourMap = new int[subTourLength];
        subTourMap[0] = route[phase];
        for (int i = 1; i < subTourLength; i++) {
            subTourMap[i] = route[phase + i];
        }
    }

    public boolean hasSuficientVertices(int i) {
        return i > 9;
    }

    public void optimize() {
        // Init genius
        TourElem element;
        genius.initialize();
        genius.initNeighborhood(tspFile.task);
        genius.littleTurns(tour[0], tspFile.g);
        genius.addNextNode(tour[0], tspFile.task, tspFile.d);
        for (int i = 1; i < problemSize; i++) {
            genius.addOneTurns(tour[i], tspFile.g);
            genius.addNextNode(tour[i], tspFile.task, tspFile.d);
        } //copy the tour and calculates the nearest neighbours of the nodes.
        if (genius.numberedTurns()) {
            genius.routeCopy(tspFile.task, tspFile.g, tspFile.d, cost);
            element = genius.t.ptr;
            for (int i = 0; i < problemSize; i++) {
                tour[i] = element.node - 1;
                element = element.next;
            }
            tour[problemSize] = tour[0];
        }
    }

    public int[] getResult() {
        if (subTourOptimization) {
            for (int i = 1; i < subTourMap.length; i++) {
                route[phase + i] = subTourMap[tour[i]];
            }
            return route;
        } else {
            return tour;
        }
    }

    private double fitnessEvaluation(int[] tour) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(tour[i], tour[i + 1]).getCost();
        }
        return cost;
    }

    public void setStopEternalLoops(boolean active) {
        if (genius != null) {
            genius.findEternalLoops = active;
        } else {
            stopEternalLoops = active;
        }
    }
}
