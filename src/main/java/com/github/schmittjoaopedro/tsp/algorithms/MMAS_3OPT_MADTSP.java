package com.github.schmittjoaopedro.tsp.algorithms;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.aco.MMAS;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.Opt3Operator;
import com.github.schmittjoaopedro.tsp.aco.ls.us.USOperator;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.tools.DBGP;
import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.tools.MVBS;
import com.github.schmittjoaopedro.tsp.utils.DebugLogger;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MMAS_3OPT_MADTSP implements Runnable {

    private MVBS mvbs;

    private Opt3Operator opt3Operator;

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

    private boolean useLocalSearch = true;

    private double alpha;

    private double beta;

    private Random random;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    public MMAS_3OPT_MADTSP(Graph graph, double rho, int maxIterations, double magnitude, int frequency, double alpha, double beta) {
        this.maxIterations = maxIterations;
        this.rho = rho;
        this.alpha = alpha;
        this.beta = beta;
        this.magnitude = magnitude;
        this.frequency = frequency;
        this.graph = graph;
        mmas = new MMAS(graph);
        dbgp = new DBGP(graph);
        iterationStatistics = new ArrayList<>(maxIterations);
    }

    @Override
    public void run() {
        // Initialization DBGP
        globalStatistics.startTimer();
        dbgp.setLowerBound(0.0);
        dbgp.setUpperBound(2.0);
        dbgp.setRandom(new Random(getDbgpSeed()));
        dbgp.setMagnitude(magnitude);
        dbgp.setFrequency(frequency);
        dbgp.initializeEnvironment();
        globalStatistics.endTimer("DBGP Initialization");
        // Initialization MMAS
        globalStatistics.startTimer();
        random = new Random(getMmasSeed());
        mmas.setRho(rho);
        mmas.setAlpha(alpha);
        mmas.setBeta(beta);
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
        opt3Operator = new Opt3Operator();
        opt3Operator.setKeepOriginalDepotPosition(true);
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
                    executeLocalSearch();
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
            mvbs.moveNext(i, mmas.getBestSoFar());
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
                iterationStatistic.setMvsbTour(mvbs.getTour().clone());
                iterationStatistics.add(iterationStatistic);
                if (showLog) {
                    System.out.println(iterationStatistic);
                }
            }
            if (dbgp.applyNewChanges(i) && i < maxIterations) {
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

    public void executeLocalSearch() {
        Ant iterationBest = mmas.findBest();
        if (opt3Operator.init(graph, random, iterationBest.getTour().clone(), mvbs.getPhase())) {
            opt3Operator.optimize();
            int result[] = opt3Operator.getResult();
            double newCost = mmas.fitnessEvaluation(result);
            if (newCost < iterationBest.getCost()) {
                DebugLogger.validIntegrityLocalSearch(result, newCost, iterationBest.getCost());
                iterationBest.setCost(newCost);
                iterationBest.setTour(result);
                mmas.copyFromTo(iterationBest, mmas.getBestSoFar());
                mmas.copyFromTo(iterationBest, mmas.getRestartBest());
            }
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

    public MVBS getMvbs() {
        return mvbs;
    }

    public void setMvbs(MVBS mvbs) {
        this.mvbs = mvbs;
    }

}
