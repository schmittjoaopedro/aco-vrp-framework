package com.github.schmittjoaopedro.tsp.tools;

import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Dynamic Benchmark Generator Problem used in:
 *
 * - M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for
 *   dynamic traveling salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7,
 *   pp. 1743-1756, 2017.
 */
public class DBGP {

    private double magnitude;

    private double frequency;

    private double lowerBound;

    private double upperBound;

    private Random random;

    private Graph graph;

    private Map<Edge, Double> originalCosts = new HashMap<>();

    private Map<Edge, Double> trafficFactors = new HashMap<>();

    public DBGP(Graph graph) {
        super();
        this.graph = graph;
    }

    public void initializeEnvironment() {
        initializeTrafficFactors();
        addRandomChange();
        computeDistances();
    }

    public void initializeTrafficFactors() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            this.originalCosts.put(edge, edge.getCost());
            this.trafficFactors.put(edge, 1.0);
        }
    }

    public boolean applyNewChanges(int iteration) {
        if (iteration % frequency == 0) {
            addRandomChange();
            computeDistances();
            return true;
        }
        return false;
    }

    public boolean applyCurrentChanges(int iteration) {
        if (iteration % frequency == 0) {
            computeDistances();
            return true;
        }
        return false;
    }

    public void addRandomChange() {
        Edge edge;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < graph.getVertexCount(); j++) {
                if (i != j) {
                    edge = graph.getEdge(i, j);
                    if (random.nextDouble() <= magnitude) {
                        trafficFactors.put(edge, 1.0 + getRandomBound());
                    } else {
                        trafficFactors.put(edge, 1.0);
                    }
                }
            }
        }
    }

    private void computeDistances() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            edge.setCost(originalCosts.get(edge) * trafficFactors.get(edge));
        }
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    private double getRandomBound() {
        return lowerBound + (upperBound - lowerBound) * random.nextDouble();
    }
}
