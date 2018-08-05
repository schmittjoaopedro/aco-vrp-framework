package com.github.schmittjoaopedro.tools;

import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.utils.Maths;

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
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            this.originalCosts.put(edge, edge.getCost());
            this.trafficFactors.put(edge, 1.0);
        }
        addRandomChange();
        computeDistances();
    }

    private void addRandomChange() {
        for (Edge edge : trafficFactors.keySet()) {
            if (random.nextDouble() <= magnitude) {
                trafficFactors.put(edge, getRandomBound());
            } else {
                trafficFactors.put(edge, 1.0);
            }
        }
    }

    private void computeDistances() {
        for (Edge edge : trafficFactors.keySet()) {
            edge.setCost(Maths.getTSPEuclideanDistance(edge.getFrom(), edge.getTo()));
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
