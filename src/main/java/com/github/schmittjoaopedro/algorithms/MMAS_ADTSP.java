package com.github.schmittjoaopedro.algorithms;

import com.github.schmittjoaopedro.aco.MMAS;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.tools.DBGP;
import com.github.schmittjoaopedro.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tools.IterationStatistic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MMAS_ADTSP {

    private String problemInstance;

    private Graph graph;

    private DBGP dbgp;

    private MMAS mmas;

    private int maxIterations;

    private int seed;

    private double rho;

    private double magnitude;

    private double frequency;

    private boolean useLocalSearch = false;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    public MMAS_ADTSP(String problemInstance, double magnitude, double frequency, double rho, int maxIterations, int seed) {
        this.problemInstance = problemInstance;
        this.magnitude = magnitude;
        this.frequency = frequency;
        this.maxIterations = maxIterations;
        this.rho = rho;
        this.seed = seed;
        graph = GraphFactory.createGraphFromTSP(new File(problemInstance));
        dbgp = new DBGP(graph);
        mmas = new MMAS(graph);
        iterationStatistics = new ArrayList<>(maxIterations);
    }

    public void run() {
        // Initialization DBGP
        globalStatistics.startTimer();
        dbgp.setFrequency(frequency);
        dbgp.setMagnitude(magnitude);
        dbgp.setUpperBound(2.0);
        dbgp.setLowerBound(0.0);
        dbgp.setRandom(new Random(seed));
        dbgp.initializeEnvironment();
        globalStatistics.endTimer("DBGP Initialization");

        // Initialization MMAS
        globalStatistics.startTimer();
        mmas.setRho(rho);
        mmas.setAlpha(1.0);
        mmas.setBeta(2.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(graph.getVertexCount());
        mmas.setDepth(20);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.setSymmetric(true);
        mmas.computeNNList();
        mmas.initHeuristicInfo();
        mmas.initTry();
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
            mmas.computeTotalInfo();
            mmas.searchControl();
            iterationStatistic.endTimer("Pheromone");
            // Statistics
            iterationStatistic.setIteration(i);
            iterationStatistic.setBestSoFar(mmas.getBestSoFar().getCost());
            iterationStatistic.setDiversity(mmas.calculateDiversity());
            iterationStatistic.setBranchFactor(mmas.getCalculatedBranchFact());
            iterationStatistic.setIterationBest(mmas.findBest().getCost());
            iterationStatistic.setIterationWorst(mmas.findWorst().getCost());
            iterationStatistic.setIterationMean(mmas.getMean());
            iterationStatistics.add(iterationStatistic);

            if (hasBest) {
                System.out.println(iterationStatistic);
            }
        }
        globalStatistics.endTimer("MMAS Execution");

    }

}
