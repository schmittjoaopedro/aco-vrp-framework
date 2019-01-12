package com.github.schmittjoaopedro.tsp.tools;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.aco.MMAS;
import com.github.schmittjoaopedro.tsp.graph.Graph;

public class MVBS {

    private Graph graph;

    private double intervalTime;

    private int maxIterations;

    private int[] tour;

    private boolean[] visited;

    private int phase = -1;

    private double accumulativeCost;

    private MMAS mmas;

    public MVBS(Graph graph, MMAS mmas) {
        this.graph = graph;
        this.mmas = mmas;
    }

    public void initialize() {
        intervalTime = (double) maxIterations / graph.getVertexCount();
        tour = new int[graph.getVertexCount() + 1];
        visited = new boolean[graph.getVertexCount()];
    }

    public void moveNext(int iteration, Ant ant) {
        if (iteration >= (phase + 1) * intervalTime) {
            phase++;
            tour[phase] = ant.getTour()[phase];
            visited[tour[phase]] = true;
            if (phase > 0) {
                graph.getEdge(tour[phase - 1], tour[phase]).setEditable(false);
                accumulativeCost += graph.getEdge(tour[phase - 1], tour[phase]).getCost();
            }
        }
    }

    public boolean isValid(Ant ant) {
        for (int i = 0; i <= phase; i++) {
            if (ant.getTour()[i] != tour[i]) {
                return false;
            }
        }
        return true;
    }

    public void constructSolutions() {
        for (Ant ant : mmas.getAntPopulation()) {
            mmas.antEmptyMemory(ant);
        }
        int step = phase;
        if (step < 0) {
            step++;
            for (Ant ant : mmas.getAntPopulation()) {
                mmas.placeAnt(ant, step);
            }
        } else {
            for (Ant ant : mmas.getAntPopulation()) {
                for (int i = 0; i <= phase; i++) {
                    ant.getTour()[i] = tour[i];
                    ant.getVisited()[tour[i]] = true;
                }
            }
        }
        while (step < graph.getVertexCount() - 1) {
            step++;
            for (int k = 0; k < mmas.getAntPopulation().size(); k++) {
                mmas.neighbourChooseAndMoveToNext(mmas.getAntPopulation().get(k), step);
            }
        }
        for (Ant ant : mmas.getAntPopulation()) {
            ant.getTour()[graph.getVertexCount()] = ant.getTour()[0];
            ant.setCost(mmas.fitnessEvaluation(ant.getTour()));
        }
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int[] getTour() {
        return tour;
    }

    public void setTour(int[] tour) {
        this.tour = tour;
    }

    public double getAccumulativeCost() {
        return accumulativeCost;
    }

    public void setAccumulativeCost(double accumulativeCost) {
        this.accumulativeCost = accumulativeCost;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public void setVisited(boolean[] visited) {
        this.visited = visited;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
