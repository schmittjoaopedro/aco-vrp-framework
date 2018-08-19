package com.github.schmittjoaopedro.aco.ls.us;


import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapted from C++ version proposed in:
 *
 * M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for dynamic traveling
 * salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7, pp. 1743-1756, 2017.
 */
public class USOperator {

    private int problemSize;
    private int tourLength;
    private coordG tspFile;
    private int[] tour;
    private double cost;
    private Graph graph;

    public void init(Graph graph, List<Vertex> route, double cost) {
        this.graph = graph;
        problemSize = graph.getVertexCount();
        tourLength = route.size();
        // Init structure
        tspFile = new coordG();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            tspFile.xyvalues(graph.getVertexCount(), i, graph.getVertex(i).getX(), graph.getVertex(i).getY());
        }
        this.cost = cost;
        tour = new int[tourLength];
        for (int i = 0; i < tourLength; i++) {
            tour[i] = route.get(i).getId();
        }
        tspFile.distances();
    }

    public void optimize() {
        // Init genius
        tourneelem element;
        routeG genius = new routeG();
        genius.initialize();
        genius.initnneighbour(tspFile.task);
        // Take the best ant for the considered iteration;
        genius.petittourne(tour[0], tspFile.g); //initial
        genius.ajoutenoeudprox(tour[0], tspFile.task, tspFile.d);
        for (int i = 1; i < problemSize; i++) {
            genius.ajoute_a_tourne(tour[i], tspFile.g);
            genius.ajoutenoeudprox(tour[i], tspFile.task, tspFile.d);
        } //copy the tour and calculates the nearest neighbours of the nodes.

        if (!genius.numerote_tourne()) {
            //do nothing
        } else {
            genius.route_copy(tspFile.task, tspFile.g, tspFile.d, cost);
            //COPY THE RESULTING TOUR
            element = genius.t.ptr;
            for (int i = 0; i < problemSize; i++) {
                tour[i] = element.noeud - 1;
                element = element.prochain;
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
