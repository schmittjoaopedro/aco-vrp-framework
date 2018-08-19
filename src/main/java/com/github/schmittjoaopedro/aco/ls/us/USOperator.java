package com.github.schmittjoaopedro.aco.ls.us;


import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapted from C++ version proposed in:
 *
 * M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for dynamic traveling
 * salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7, pp. 1743-1756, 2017.
 */
public class USOperator {

    private int problemSize;
    private int tourLength;
    private CoordinatesGenius tspFile;
    private int[] tour;
    private double cost;
    private Graph graph;

    public void init(Graph graph, List<Vertex> route, double cost) {
        this.graph = graph;
        problemSize = graph.getVertexCount();
        tourLength = route.size();
        // Init structure
        tspFile = new CoordinatesGenius();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            tspFile.setXYValues(graph.getVertexCount(), i, graph.getVertex(i).getX(), graph.getVertex(i).getY());
        }
        this.cost = cost;
        tour = new int[tourLength];
        for (int i = 0; i < tourLength; i++) {
            tour[i] = route.get(i).getId();
        }
        tspFile.distances(graph);
    }

    public void optimize() {
        // Init genius
        TurnsElem element;
        RouteGenius genius = new RouteGenius();
        genius.initialize();
        genius.initNeighborhood(tspFile.task);
        // Take the best ant for the considered iteration;
        genius.littleTurns(tour[0], tspFile.g); //initial
        genius.addNextNode(tour[0], tspFile.task, tspFile.d);
        for (int i = 1; i < problemSize; i++) {
            genius.addOneTurns(tour[i], tspFile.g);
            genius.addNextNode(tour[i], tspFile.task, tspFile.d);
        } //copy the tour and calculates the nearest neighbours of the nodes.

        if (!genius.numberedTurns()) {
            //do nothing
        } else {
            genius.routeCopy(tspFile.task, tspFile.g, tspFile.d, cost);
            //COPY THE RESULTING TOUR
            element = genius.t.ptr;
            for (int i = 0; i < problemSize; i++) {
                tour[i] = element.node - 1;
                element = element.next;
            }
            tour[problemSize] = tour[0];
        }
    }

    public List<Vertex> getResult() {
        List<Vertex> optimized = new ArrayList<>();
        for(int i = 0; i < tour.length; i++) {
            optimized.add(graph.getVertex(tour[i]));
        }
        return optimized;
    }
}
