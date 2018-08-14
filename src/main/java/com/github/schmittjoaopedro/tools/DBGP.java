package com.github.schmittjoaopedro.tools;

import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

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

    public boolean applyChanges(int iteration) {
        if (iteration % frequency == 0) {
            addRandomChange();
            computeDistances();
            return true;
        }
        return false;
    }

    private void addRandomChange() {
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
