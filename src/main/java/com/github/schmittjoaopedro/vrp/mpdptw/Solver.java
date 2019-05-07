package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SequentialFeasiblePDPTW;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SolutionBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Solver implements Runnable {

    private String problemName;

    private ProblemInstance instance;

    private List<IterationStatistic> iterationStatistics;

    private MMAS mmas;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    private int maxIterations;

    private int seed;

    private double rho;

    private int statisticInterval;

    private boolean showLog;

    private LocalSearch localSearch;

    private boolean lsActive;

    private Class<? extends SolutionBuilder> solutionBuilderClass = SequentialFeasiblePDPTW.class;

    private boolean parallel;

    private String finalSolution;

    private boolean generateFile = Boolean.FALSE;

    public Solver(String problemName, ProblemInstance instance, int maxIterations, int seed, double rho, int statisticInterval, boolean showLog) {
        this.problemName = problemName;
        this.instance = instance;
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
        mmas.setnAnts(50);
        mmas.setDepth(instance.getNumNodes());
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.setParallel(parallel);
        mmas.setSolutionBuilder(solutionBuilderClass);
        mmas.computeNNList();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");

        // Init local search
        this.localSearch = new LocalSearch(instance, new Random(seed));

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
            if (lsActive) {
                executeLocalSearch();
            }
            boolean hasBest = mmas.updateBestSoFar();
            if (hasBest) {
                if (lsActive) {
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
            //mmas.checkPheromoneTrailLimits();
            mmas.searchControl();
            iterationStatistic.endTimer("Pheromone");
            // Statistics
            if (i % statisticInterval == 0) {
                iterationStatistic.setIteration(i);
                iterationStatistic.setBestSoFar(mmas.getBestSoFar().totalCost);
                iterationStatistic.setDiversity(mmas.calculateDiversity());
                iterationStatistic.setBranchFactor(mmas.nodeBranching());
                iterationStatistic.setIterationBest(mmas.findBest().totalCost);
                iterationStatistic.setIterationWorst(mmas.findWorst().totalCost);
                iterationStatistic.setFeasible(mmas.getBestSoFar().feasible ? 1.0 : 0.0);
                iterationStatistic.setIterationMean(Maths.getMean(mmas.getAntPopulation().stream().map(Solution::getCost).collect(Collectors.toList())));
                iterationStatistic.setIterationSd(Maths.getStd(mmas.getAntPopulation().stream().map(Solution::getCost).collect(Collectors.toList())));
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
        Solution ant = mmas.getBestSoFar();
        String msg = "";
        instance.restrictionsEvaluation(ant);
        boolean feasible = true;
        double cost;
        for (ArrayList route : ant.tours) {
            feasible &= instance.restrictionsEvaluation(route).feasible;
        }
        ant.feasible &= feasible;
        ant.totalCost = 0.0;
        for (int i = 0; i < ant.tours.size(); i++) {
            cost = instance.costEvaluation(ant.tours.get(i));
            ant.tourCosts.set(i, cost);
            ant.totalCost += cost;
        }
        msg += "\nInstance = " + problemName;
        msg += "\nBest solution feasibility = " + ant.feasible + "\nRoutes";
        for (ArrayList route : ant.tours) {
            msg += "\n" + StringUtils.join(route, "-");
        }
        msg += "\nRequests";
        for (ArrayList requests : ant.requests) {
            msg += "\n" + StringUtils.join(requests, "-");
        }
        msg += "\nNum. Vehicles = " + ant.tours.size();
        msg += "\nCost = " + ant.totalCost;
        msg += "\nPenalty = " + ant.timeWindowPenalty;
        msg += "\nTotal time (ms) = " + globalStatistics.getTimeStatistics().get("Algorithm");
        finalSolution = msg;
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
        MapPrinter.printResult(ant, instance, 1200, 1000, problemName);
    }

    private void initProblemInstance() {
        try {
            mmas = new MMAS(instance);
            iterationStatistics = new ArrayList<>(maxIterations);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void executeLocalSearch() {
        Solution bestAnt = mmas.findBest();
        instance.solutionEvaluation(bestAnt);
        Solution improvedAnt = localSearch.optimize(bestAnt);
        if (SolutionUtils.getBest(bestAnt, improvedAnt) != bestAnt) {
            int antIndex = mmas.getAntPopulation().indexOf(bestAnt);
            mmas.getAntPopulation().set(antIndex, improvedAnt);
        }
    }

    private void logInFile(String text) {
        if (generateFile) {
            try {
                FileUtils.writeStringToFile(new File("C:\\Temp\\mpdptw\\result-" + problemName), text + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSolutionBuilderClass(Class<? extends SolutionBuilder> solutionBuilderClass) {
        this.solutionBuilderClass = solutionBuilderClass;
    }

    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
    }

    public List<IterationStatistic> getIterationStatistics() {
        return iterationStatistics;
    }

    public Solution getBestSolution() {
        return mmas.getBestSoFar();
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public void setLsActive(boolean lsActive) {
        this.lsActive = lsActive;
    }

    public String getFinalSolution() {
        return finalSolution;
    }

    public void setGenerateFile(boolean generateFile) {
        this.generateFile = generateFile;
    }
}
