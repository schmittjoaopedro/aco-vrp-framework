package com.github.schmittjoaopedro.tsp.algorithms;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.aco.MMAS;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.Opt3Operator;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Max-Min Ant System for the Travelling Salesman Problem
 */
public class MMAS_3OPT_TSP implements Runnable {

    private Opt3Operator opt3Operator;

    private Graph graph;

    private MMAS mmas;

    private int maxIterations;

    private int statisticInterval = 1;

    private int seed;

    private double rho;

    private boolean showLog = true;

    private boolean useLocalSearch = true;

    private Random random;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    public MMAS_3OPT_TSP(Graph graph, int maxIterations, int seed) {
        this.maxIterations = maxIterations;
        this.rho = 0.2; // With Local Search the Rho is defined as 0.2
        this.seed = seed;
        this.graph = graph;
        mmas = new MMAS(graph);
        iterationStatistics = new ArrayList<>(maxIterations);
        random = new Random(seed);
    }

    @Override
    public void run() {
        // Initialization MMAS
        globalStatistics.startTimer();
        mmas.setRho(rho);
        mmas.setAlpha(1.0);
        mmas.setBeta(2.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(25);
        mmas.setDepth(20);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(random);
        mmas.setSymmetric(true);
        mmas.computeNNList();
        mmas.initHeuristicInfo();
        mmas.initTry(useLocalSearch);
        opt3Operator = new Opt3Operator();
        globalStatistics.endTimer("MMAS Initialization");

        // Execute MMAS
        globalStatistics.startTimer();
        for (int i = 1; i <= maxIterations; i++) {
            IterationStatistic iterationStatistic = new IterationStatistic();
            mmas.setCurrentIteration(i);
            // Construction
            iterationStatistic.startTimer();
            mmas.constructSolutions();
            iterationStatistic.endTimer("Construction");
            // Daemon
            iterationStatistic.startTimer();
            executeLocalSearch();
            boolean hasBest = mmas.updateBestSoFar();
            if (hasBest) {
                if (useLocalSearch) {
                    mmas.setPheromoneBoundsForLS();
                } else {
                    mmas.setPheromoneBounds();
                }
            }
            mmas.updateRestartBest();
            iterationStatistic.endTimer("Daemon");
            // Pheromone
            iterationStatistic.startTimer();
            mmas.evaporationLocalSearch();
            mmas.pheromoneUpdate();
            if (useLocalSearch) {
                mmas.updateUGB();
            }
            mmas.computeNnListTotalInfo();
            mmas.searchControl();
            iterationStatistic.endTimer("Pheromone");
            // Statistics
            if (i % statisticInterval == 0) {
                iterationStatistic.setIteration(i);
                iterationStatistic.setBestSoFar(mmas.getBestSoFar().getCost());
                iterationStatistic.setDiversity(mmas.calculateDiversity());
                iterationStatistic.setBranchFactor(mmas.getCalculatedBranchFact());
                iterationStatistic.setIterationBest(mmas.findBest().getCost());
                iterationStatistic.setIterationWorst(mmas.findWorst().getCost());
                iterationStatistic.setIterationMean(Maths.getPopMean(mmas.getAntPopulation()));
                iterationStatistic.setIterationSd(Maths.getPopultionStd(mmas.getAntPopulation()));
                iterationStatistics.add(iterationStatistic);
                if (showLog) {
                    System.out.println(iterationStatistic);
                }
            }
        }
        globalStatistics.endTimer("MMAS Execution");
        globalStatistics.setBestSoFarTC(mmas.getBestSoFar().getCost());
        List<Vertex> tour = new ArrayList<>(graph.getVertexCount() + 1);
        for (int vertexId : mmas.getBestSoFar().getTour()) {
            tour.add(graph.getVertex(vertexId));
        }
        globalStatistics.setBestRoute(tour);
    }

    public void executeLocalSearch() {
        int result[];
        double newCost;
        for (Ant ant : mmas.getAntPopulation()) {
            opt3Operator.initStaticVersion(graph, random, ant.getTour().clone());
            opt3Operator.optimize();
            result = opt3Operator.getResult();
            newCost = mmas.fitnessEvaluation(result);
            if (newCost < ant.getCost()) {
                ant.setTour(result);
                ant.setCost(newCost);
            }
        }
    }

    public List<IterationStatistic> getIterationStatistics() {
        return iterationStatistics;
    }

    public void roundStatistics() {
        for (IterationStatistic iterationStatistic : getIterationStatistics()) {
            iterationStatistic.setIterationMean(Maths.round(iterationStatistic.getIterationMean(), 2));
            iterationStatistic.setIterationSd(Maths.round(iterationStatistic.getIterationSd(), 2));
            iterationStatistic.setBranchFactor(Maths.round(iterationStatistic.getBranchFactor(), 3));
            iterationStatistic.setDiversity(Maths.round(iterationStatistic.getDiversity(), 2));
            iterationStatistic.setBestSoFar(Maths.round(iterationStatistic.getBestSoFar(), 2));
        }
    }



    public void setIterationStatistics(List<IterationStatistic> iterationStatistics) {
        this.iterationStatistics = iterationStatistics;
    }

    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
    }

    public void setGlobalStatistics(GlobalStatistics globalStatistics) {
        this.globalStatistics = globalStatistics;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public int getStatisticInterval() {
        return statisticInterval;
    }

    public void setStatisticInterval(int statisticInterval) {
        this.statisticInterval = statisticInterval;
    }
}
