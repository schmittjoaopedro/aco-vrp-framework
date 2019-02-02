package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solver implements Runnable {

    private String rootDirectory;

    private String fileName;

    private ProblemInstance problemInstance;

    private List<IterationStatistic> iterationStatistics;

    private MMAS mmas;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    private int maxIterations;

    private int seed;

    private double rho;

    private String filePath;

    private int statisticInterval;

    private boolean showLog;

    public Solver(String rootDirectory, String fileName, int maxIterations, int seed, double rho, int statisticInterval, boolean showLog) {
        this.fileName = fileName;
        this.rootDirectory = rootDirectory;
        this.maxIterations = maxIterations;
        this.seed = seed;
        this.rho = rho;
        this.statisticInterval = statisticInterval;
        this.showLog = showLog;
    }

    @Override
    public void run() {
        // Initialization of MMAS
        globalStatistics.startTimer();
        initProblemInstance();
        mmas.setRho(rho);
        mmas.setAlpha(1.0);
        mmas.setBeta(5.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(problemInstance.noNodes);
        mmas.setDepth(20);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.setSymmetric(false);
        mmas.computeNNList();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");

        // Execute MMAS
        globalStatistics.startTimer();
        mmas.getBestSoFar().feasible = false;
        for (int i = 1; i <= maxIterations; i++) {
            IterationStatistic iterationStatistic = new IterationStatistic();
            mmas.setCurrentIteration(i);
            // Construction
            iterationStatistic.startTimer();
            mmas.constructSolutions();
            iterationStatistic.endTimer("Construction");
            // Daemon
            boolean hasBest = mmas.updateBestSoFar();
            if (hasBest) {
                mmas.setPheromoneBounds();
            }
            mmas.updateRestartBest();
            iterationStatistic.endTimer("Daemon");
            // Pheromone
            iterationStatistic.startTimer();
            mmas.evaporation();
            mmas.pheromoneUpdate();
//            mmas.checkPheromoneTrailLimits();
            mmas.searchControl(); // TODO: Rever
            iterationStatistic.endTimer("Pheromone");
            // Statistics
            if (i % statisticInterval == 0) {
                iterationStatistic.setIteration(i);
                iterationStatistic.setBestSoFar(mmas.getBestSoFar().totalCost);
                iterationStatistic.setDiversity(mmas.calculateDiversity());
                iterationStatistic.setBranchFactor(mmas.nodeBranching());
                iterationStatistic.setIterationBest(mmas.findBest().totalCost);
                iterationStatistic.setIterationWorst(mmas.findWorst().totalCost);
                iterationStatistic.setFeasible(mmas.getBestSoFar().feasible);
//                iterationStatistic.setIterationMean(Maths.getPopMean(mmas.getAntPopulation()));
//                iterationStatistic.setIterationSd(Maths.getPopultionStd(mmas.getAntPopulation()));
                iterationStatistic.setPenaltyRate(mmas.getPenaltyRate());
                iterationStatistics.add(iterationStatistic);
                if (showLog) {
                    System.out.println(iterationStatistic);
                }
            }
        }
        boolean feasible = true;
        for (ArrayList route : mmas.getBestSoFar().tours) {
            feasible &= mmas.isRouteFeasible(route);
        }
        mmas.getBestSoFar().feasible = feasible;
        mmas.fitnessEvaluation(mmas.getBestSoFar());
        System.out.println("Best solution feasibility = " + mmas.getBestSoFar().feasible);
        for (ArrayList route : mmas.getBestSoFar().tours) {
            System.out.println(StringUtils.join(route, "-"));
        }
        System.out.println("Cost = " + mmas.getBestSoFar().totalCost);
    }

    private void initProblemInstance() {
        try {
            filePath = Paths.get(rootDirectory, fileName).toString();
            problemInstance = DataReader.getProblemInstance(new File(filePath));
            mmas = new MMAS(problemInstance);
            iterationStatistics = new ArrayList<>(maxIterations);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
