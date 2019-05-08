package com.github.schmittjoaopedro.tsp.algorithms;

import com.github.schmittjoaopedro.tsp.aco.MMAS;
import com.github.schmittjoaopedro.tsp.aco.MMAS_MEM_Memory;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.tools.DBGP;
import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Max-Min Ant System with Memory for the Cycled Asymmetric and Dynamic Travelling Salesman Problem
 */
public class MMAS_MEM_CADTSP implements Runnable {

    private DBGP dbgp[];

    private Graph graph;

    private MMAS mmas;

    private MMAS_MEM_Memory memory;

    private int maxIterations;

    private int statisticInterval = 1;

    private int mmasSeed;

    private int dbgpSeed;

    private double rho;

    private double magnitude;

    private int frequency;

    private int cycleSize;

    private boolean showLog = true;

    private boolean useLocalSearch = false;

    private boolean changed = false;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    public MMAS_MEM_CADTSP(Graph graph, double rho, int maxIterations, double magnitude, int frequency, int cycleSize) {
        this.maxIterations = maxIterations;
        this.rho = rho;
        this.magnitude = magnitude;
        this.cycleSize = cycleSize;
        this.frequency = frequency;
        this.graph = graph;
        mmas = new MMAS(graph);
        memory = new MMAS_MEM_Memory(graph, mmas);
        dbgp = new DBGP[cycleSize];
        iterationStatistics = new ArrayList<>(maxIterations);
    }

    @Override
    public void run() {
        // Initialization DBGP
        globalStatistics.startTimer();
        Random dbgpRandom = new Random(getDbgpSeed());
        int cycleTurn = cycleSize - 1;
        for (; cycleTurn >= 0; cycleTurn--) { // Reverse order, for that the first position starts adding random change
            dbgp[cycleTurn] = new DBGP(graph);
            dbgp[cycleTurn].setLowerBound(0.0);
            dbgp[cycleTurn].setUpperBound(2.0);
            dbgp[cycleTurn].setRandom(dbgpRandom);
            dbgp[cycleTurn].setMagnitude(magnitude);
            dbgp[cycleTurn].setFrequency(frequency);
            dbgp[cycleTurn].initializeTrafficFactors(); // We can't change the graph while starting DBGP
            dbgp[cycleTurn].addRandomChange();
        }
        dbgp[0].applyCurrentChanges(0); // First environment
        cycleTurn = ++cycleTurn % cycleSize; // Next environment
        globalStatistics.endTimer("DBGP Initialization");
        // Initialization MMAS
        Random random = new Random(getMmasSeed());
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
        mmas.setRandom(random);
        mmas.computeNNList();
        mmas.initHeuristicInfo();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");
        //Initialization memory
        globalStatistics.startTimer();
        memory.setShortMemorySize(4);
        memory.setMutationProbability(0.01);
        memory.setImmigrantRate(0.4);
        memory.setRandom(random);
        memory.initialize();
        globalStatistics.endTimer("MEMORY Initialization");
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
            mmas.constructSolutions();
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
            if (changed) {
                mmas.setRho(0.02);
                memory.updateShortTermMemory();
                memory.pheromoneUpdate(i);
            } else {
                mmas.pheromoneUpdate();
            }
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
            if (i < maxIterations && dbgp[cycleTurn].applyCurrentChanges(i)) {
                cycleTurn = ++cycleTurn % cycleSize;  // Next environment
            }
            repairSolution();
        }
        globalStatistics.endTimer("MMAS Execution");
        globalStatistics.setBestSoFarTC(mmas.getBestSoFar().getCost());
        List<Vertex> tour = new ArrayList<>(graph.getVertexCount() + 1);
        for (int vertexId : mmas.getBestSoFar().getTour()) {
            tour.add(graph.getVertex(vertexId));
        }
        globalStatistics.setBestRoute(tour);
    }

    private void repairSolution() {
        double originalCost = mmas.getBestSoFar().getCost();
        mmas.getBestSoFar().setCost(mmas.fitnessEvaluation(mmas.getBestSoFar().getTour()));
        if (originalCost != mmas.getBestSoFar().getCost()) {
            changed = true;
        }
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
