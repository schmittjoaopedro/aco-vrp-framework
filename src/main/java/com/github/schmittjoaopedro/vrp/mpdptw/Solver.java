package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    private LocalSearch localSearch;

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
        mmas.setnAnts(50);
        mmas.setDepth(problemInstance.noReq);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.computeNNList();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");

        // Init local search
        this.localSearch = new LocalSearch(problemInstance);

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
            executeLocalSearch();
            boolean hasBest = mmas.updateBestSoFar();
            if (hasBest) {
                mmas.setPheromoneBoundsForLS();
            }
            mmas.updateRestartBest();
            iterationStatistic.endTimer("Daemon");
            // Pheromone
            iterationStatistic.startTimer();
            mmas.evaporation();
            mmas.pheromoneUpdate();
            mmas.checkPheromoneTrailLimits();
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
                iterationStatistic.setIterationMean(Maths.getMean(mmas.getAntPopulation().stream().map(Ant::getCost).collect(Collectors.toList())));
                iterationStatistic.setIterationSd(Maths.getStd(mmas.getAntPopulation().stream().map(Ant::getCost).collect(Collectors.toList())));
                iterationStatistic.setPenaltyRate(mmas.getPenaltyRate());
                iterationStatistics.add(iterationStatistic);
                if (showLog) {
                    System.out.println(iterationStatistic);
                    logInFile(iterationStatistic.toString());
                }
            }
        }
        printFinalRoute();
    }

    private void printFinalRoute() {
        boolean feasible = true;
        for (ArrayList route : mmas.getBestSoFar().tours) {
            feasible &= mmas.isRouteFeasible(route);
        }
        mmas.getBestSoFar().feasible = feasible;
        problemInstance.fitnessEvaluation(mmas.getBestSoFar());
        System.out.println("Best solution feasibility = " + mmas.getBestSoFar().feasible);
        logInFile("Best solution feasibility = " + mmas.getBestSoFar().feasible);
        for (ArrayList route : mmas.getBestSoFar().tours) {
            System.out.println(StringUtils.join(route, "-"));
            logInFile(StringUtils.join(route, "-"));
        }
        System.out.println("Cost = " + mmas.getBestSoFar().totalCost);
        System.out.println("Penalty = " + mmas.getBestSoFar().timeWindowPenalty);
        logInFile("Cost = " + mmas.getBestSoFar().totalCost);
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

    private void executeLocalSearch() {
        Ant bestAnt = mmas.findBest();
        problemInstance.fitnessEvaluation(bestAnt);
        Ant improvedAnt = localSearch.relocate(bestAnt);
        if (bestAnt.feasible) {
            if (improvedAnt.feasible) {
                updateAnt(improvedAnt, bestAnt);
            }
        } else {
            updateAnt(improvedAnt, bestAnt);
        }
    }

    private void updateAnt(Ant improvedAnt, Ant bestAnt) {
        if (improvedAnt.totalCost < bestAnt.totalCost ||
                (improvedAnt.totalCost == bestAnt.totalCost && improvedAnt.timeWindowPenalty < bestAnt.timeWindowPenalty)) {
            int antIndex = mmas.getAntPopulation().indexOf(bestAnt);
            mmas.getAntPopulation().set(antIndex, improvedAnt);
            mmas.penalizeAnt(improvedAnt);
        } else {
            mmas.penalizeAnt(bestAnt);
        }
    }

    private void logInFile(String text) {
        /*try {
            FileUtils.writeStringToFile(new File("C:\\Temp\\result-" + fileName), text + "\n", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
