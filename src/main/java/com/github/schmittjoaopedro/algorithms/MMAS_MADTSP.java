package com.github.schmittjoaopedro.algorithms;

import com.github.schmittjoaopedro.aco.MMAS;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.graph.Vertex;
import com.github.schmittjoaopedro.tools.DBGP;
import com.github.schmittjoaopedro.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tools.IterationStatistic;
import com.github.schmittjoaopedro.tools.MVBS;
import com.github.schmittjoaopedro.utils.Maths;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MMAS_MADTSP implements Runnable {

    private MVBS mvbs;

    private DBGP dbgp;

    private Graph graph;

    private MMAS mmas;

    private int maxIterations;

    private int statisticInterval = 1;

    private int mmasSeed;

    private int dbgpSeed;

    private double rho;

    private double magnitude;

    private int frequency;

    private boolean showLog = true;

    private boolean useLocalSearch = false;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    public MMAS_MADTSP(String problemInstance, double rho, int maxIterations, double magnitude, int frequency) {
        this.maxIterations = maxIterations;
        this.rho = rho;
        this.magnitude = magnitude;
        this.frequency = frequency;
        graph = GraphFactory.createGraphFromTSP(new File(problemInstance));
        mmas = new MMAS(graph);
        dbgp = new DBGP(graph);
        iterationStatistics = new ArrayList<>(maxIterations);
    }

    @Override
    public void run() {
        // Initialization DBGP
        dbgp.setLowerBound(0.0);
        dbgp.setUpperBound(2.0);
        dbgp.setRandom(new Random(getDbgpSeed()));
        dbgp.setMagnitude(magnitude);
        dbgp.setFrequency(frequency);
        dbgp.initializeEnvironment();
        // Initialization MMAS
        globalStatistics.startTimer();
        mmas.setRho(rho);
        mmas.setAlpha(1.0);
        mmas.setBeta(5.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(50);
        mmas.setDepth(20);
        mmas.setEPSILON(0.000000000000000000000001);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(getMmasSeed()));
        mmas.computeNNList();
        mmas.initHeuristicInfo();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");
        // Initialization moving vehicle benchmark simulator
        globalStatistics.startTimer();
        mvbs = new MVBS(graph, mmas);
        mvbs.setMaxIterations(maxIterations);
        mvbs.initialize();
        globalStatistics.endTimer("MVBS Initialization");
        // Execute MMAS
        globalStatistics.startTimer();
        for (int i = 1; i <= maxIterations; i++) {
            IterationStatistic iterationStatistic = new IterationStatistic();
            mmas.setCurrentIteration(i);
            // Construction
            iterationStatistic.startTimer();
            mmas.computeNNList();
            mmas.initHeuristicInfo();
            mmas.computeTotalInfo();
            mvbs.constructSolutions();
            iterationStatistic.endTimer("Construction");
            // Daemon
            iterationStatistic.startTimer();
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
            mmas.evaporation();
            mmas.pheromoneUpdate();
            if (useLocalSearch) {
                mmas.updateUGB();
            }
            mmas.checkPheromoneTrailLimits();
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
                iterationStatistic.setTour(mmas.getBestSoFar().getTour().clone());
                iterationStatistics.add(iterationStatistic);
                if (showLog) {
                    System.out.println(iterationStatistic);
                }
            }
            mvbs.moveNext(i, mmas.getBestSoFar());
            if (i < maxIterations && dbgp.applyChanges(i)) {
                mmas.getBestSoFar().setCost(Double.MAX_VALUE);
            }
        }
        globalStatistics.endTimer("MMAS Execution");
        globalStatistics.setBestSoFar(mmas.getBestSoFar().getCost());
        List<Vertex> tour = new ArrayList<>(graph.getVertexCount() + 1);
        for (int vertexId : mmas.getBestSoFar().getTour()) {
            tour.add(graph.getVertex(vertexId));
        }
        globalStatistics.setBestRoute(tour);
    }

    public List<IterationStatistic> getIterationStatistics() {
        return iterationStatistics;
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

    public int getMmasSeed() {
        return mmasSeed;
    }

    public void setMmasSeed(int mmasSeed) {
        this.mmasSeed = mmasSeed;
    }

    public int getDbgpSeed() {
        return dbgpSeed;
    }

    public void setDbgpSeed(int dbgpSeed) {
        this.dbgpSeed = dbgpSeed;
    }
}
