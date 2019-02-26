package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
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
        mmas.setBeta(2.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(50);
        mmas.setDepth(problemInstance.noNodes);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.computeNNList();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");

        // Init local search
        this.localSearch = new LocalSearch(problemInstance, new Random(seed));

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
        globalStatistics.endTimer("Algorithm");
        printFinalRoute();
    }

    private void printFinalRoute() {
        Ant ant = mmas.getBestSoFar();
        String msg = "";
        problemInstance.restrictionsEvaluation(ant);
        boolean feasible = true;
        double cost;
        for (ArrayList route : ant.tours) {
            feasible &= mmas.isRouteFeasible(route);
        }
        ant.feasible &= feasible;
        ant.totalCost = 0.0;
        for (int i = 0; i < ant.tours.size(); i++) {
            cost = problemInstance.costEvaluation(ant.tours.get(i));
            ant.tourLengths.set(i, cost);
            ant.totalCost += cost;
        }
        msg += "\nInstance = " + fileName;
        msg += "\nBest solution feasibility = " + ant.feasible + "\nRoutes";
        for (ArrayList route : ant.tours) {
            msg += "\n" + StringUtils.join(route, "-");
        }
        msg += "\nRequests";
        for (ArrayList requests : ant.requests) {
            msg += "\n" + StringUtils.join(requests, "-");
        }
        msg += "\nCost = " + ant.totalCost;
        msg += "\nPenalty = " + ant.timeWindowPenalty;
        msg += "\nTotal time (ms) = " + globalStatistics.getTimeStatistics().get("Algorithm");
        System.out.println(msg);
        logInFile(msg);
        Set<Integer> processedNodes = new HashSet<>();
        for (int k = 0; k < mmas.getBestSoFar().tours.size(); k++) {
            for (int i = 1; i < mmas.getBestSoFar().tours.get(k).size() - 1; i++) {
                if (processedNodes.contains(mmas.getBestSoFar().tours.get(k).get(i))) {
                    throw new RuntimeException("Invalid route, duplicated nodes");
                } else {
                    processedNodes.add(mmas.getBestSoFar().tours.get(k).get(i));
                }
            }
        }
        MapPrinter.printResult(ant, problemInstance, 1200, 1000);
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
        //for (Ant ant : mmas.getAntPopulation()) {
        //    executeLocalSearch(ant);
        //}
        executeLocalSearch(mmas.findBest());
    }

    private void executeLocalSearch(Ant ant) {
        problemInstance.restrictionsEvaluation(ant);
        Ant improvedAnt = localSearch.optimize(ant);
        if (ant.feasible) {
            if (improvedAnt.feasible) {
                updateAnt(improvedAnt, ant);
            }
        } else if (!mmas.getBestSoFar().feasible) {
            FeasibilitySearch feasibilitySearch = new FeasibilitySearch(problemInstance);
            Ant feasibleAnt = feasibilitySearch.feasibility(improvedAnt);
            improvedAnt = localSearch.optimize(feasibleAnt);
            if (improvedAnt.feasible && improvedAnt.totalCost < feasibleAnt.totalCost) {
                feasibleAnt = improvedAnt;
            }
            if (feasibleAnt.feasible) {
                updateAnt(feasibleAnt, ant);
            }
        } else {
            updateAnt(improvedAnt, ant);
        }
    }

    private void updateAnt(Ant improvedAnt, Ant bestAnt) {
        if (improvedAnt.totalCost < bestAnt.totalCost ||
                (improvedAnt.totalCost == bestAnt.totalCost && improvedAnt.timeWindowPenalty < bestAnt.timeWindowPenalty) ||
                (improvedAnt.feasible && !bestAnt.feasible)) {
            int antIndex = mmas.getAntPopulation().indexOf(bestAnt);
            mmas.getAntPopulation().set(antIndex, improvedAnt);
            mmas.penalizeAnt(improvedAnt);
        } else {
            mmas.penalizeAnt(bestAnt);
        }
    }

    private void logInFile(String text) {
        try {
            FileUtils.writeStringToFile(new File("C:\\Temp\\result-" + fileName), text + "\n", "UTF-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
