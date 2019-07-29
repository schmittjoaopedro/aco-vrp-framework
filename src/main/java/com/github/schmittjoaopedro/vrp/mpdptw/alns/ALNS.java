package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.ExchangeRequestOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateRequestOperator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * ALNS applied for the MPDPTW proposed in:
 * Naccache, S., Côté, J., & Coelho, L. C. (2018). The multi-pickup and delivery problem with time windows.
 * European Journal of Operational Research, 269(1), 353–362. https://doi.org/10.1016/j.ejor.2018.01.035
 */
public class ALNS implements Runnable {

    private Solution solutionBest;

    private int iteration = 1;

    private ProblemInstance instance;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    private DetailedStatistics detailedStatistics = new DetailedStatistics();

    private int numIterations;

    private Random random;

    private Long statisticInterval = 1L;

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    private boolean generateFile = Boolean.FALSE;

    private boolean generateDetailedStatistics = Boolean.FALSE;

    private InsertionHeuristic insertionHeuristic;

    private DynamicHandler dynamicHandler;

    private List<String> log = new ArrayList<>();

    private MovingVehicle movingVehicle;

    private boolean showLog = false;

    //private ALNS_TC alns_tc;

    private ALNS_NV alns_nv;

    public ALNS(ProblemInstance instance, int numIterations, Random random) {
        this.instance = instance;
        this.random = random;
        this.numIterations = numIterations;
        iterationStatistics = new ArrayList<>(numIterations);

        dynamicHandler = new DynamicHandler(instance, 0.0, numIterations);
        dynamicHandler.adaptDynamicVersion();

        //alns_tc = new ALNS_TC(instance, random);
        //alns_tc.setLocalSearch(this::applyLocalSearch);
        //alns_tc.init();

        if (instance.isMinimizeVehicles()) {
            alns_nv = new ALNS_NV(instance, random);
            //alns_nv.setLocalSearch(this::applyLocalSearch);
            alns_nv.init();
        }

        relocateRequestOperator = new RelocateRequestOperator(instance, random);
        exchangeRequestOperator = new ExchangeRequestOperator(instance, random);
        insertionHeuristic = new InsertionHeuristic(instance, random);
    }

    @Override
    public void run() {

        globalStatistics.startTimer();

        /*
         * Create initial solution.
         *
         * An initial solution S is generated through a construction heuristic in which requests are progressively
         * inserted within an available vehicle, at its minimum insertion cost position. After all requests have been
         * inserted, solution S is improved by our local search operator.
         */
        Solution initialSolution = insertionHeuristic.createInitialSolution();
        initialSolution = applyLocalSearch(initialSolution);
        solutionBest = SolutionUtils.copy(initialSolution);

        // Update algorithms temperature
        updateTemperature(initialSolution);

        globalStatistics.endTimer("Initialization");

        globalStatistics.startTimer();
        while (!stopCriteriaMeet()) {
            Long iterationTime = System.currentTimeMillis();

            // Process new requests
            List<Integer> newRequestIds = dynamicHandler.getNewDynamicRequests(iteration);
            if (!newRequestIds.isEmpty()) {
                log("New requests add: " + StringUtils.join(newRequestIds));
                Solution solution = SolutionUtils.copy(solutionBest);
                for (int req : newRequestIds) {
                    insertionHeuristic.addRequest(solution, req);
                }
                solution = applyLocalSearch(solution);
                updateBest(solution, true);
                updateTemperature(solutionBest);
            }

            if (instance.getNumReq() > 0) {

                // Minimize vehicles
                if (instance.isMinimizeVehicles()) {
                    alns_nv.performIteration(iteration);
                    Solution nvSolution = alns_nv.getGlobalSolution();
                    if (nvSolution.feasible && nvSolution.tours.size() < solutionBest.tours.size()) {
                        updateBest(nvSolution, false);
                        //alns_tc.setTemperature(nvSolution);
                        //alns_tc.setGlobalSolution(nvSolution);
                    }
                }

                // Minimize costs
                //alns_tc.performIteration(iteration);
                //updateBest(alns_tc.getGlobalSolution(), false);

                detailedStatistics.s_best_TC = solutionBest.totalCost;
                detailedStatistics.s_best_NV = solutionBest.tours.size();
            }

            // Check moving vehicle
            if (movingVehicle != null) {
                if (movingVehicle.moveVehicle(solutionBest, iteration)) {
                    initialSolution = SolutionUtils.copy(solutionBest);
                    //alns_tc.setGlobalSolution(solutionBest);
                    if (instance.isMinimizeVehicles()) {
                        alns_nv.setGlobalSolution(solutionBest);
                    }
                }
            }

            if (iteration % statisticInterval == 0) {
                IterationStatistic iterationStatistic = new IterationStatistic();
                iterationStatistic.setIteration(iteration);
                iterationStatistic.setBestSoFar(solutionBest.totalCost);
                iterationStatistic.setBestSoFarNV(solutionBest.tours.size());
                iterationStatistic.setIterationBest(initialSolution.totalCost);
                iterationStatistic.setIterationBestNV(initialSolution.tours.size());
                iterationStatistic.setFeasible(initialSolution.feasible ? 1.0 : 0.0);
                iterationStatistics.add(iterationStatistic);
                logInFile(iterationStatistic.toStringCsv());
            }

            if (generateDetailedStatistics) {
                detailedStatistics.iterationTime = System.currentTimeMillis() - iterationTime;
                //alns_tc.logDetailedStatistics(iteration);
                alns_nv.logDetailedStatistics(iteration);
            }

            if (iteration % 1000 == 0) {
                String msg = iteration + " -> Sol = " + solutionBest.toString();
                System.out.println(msg);
            }

            iteration++;
        }
        globalStatistics.endTimer("Algorithm");

        dynamicHandler.rollbackOriginalInformation(solutionBest);
        printFinalRoute();
    }

    private void updateTemperature(Solution initialSolution) {
        //alns_tc.setTemperature(initialSolution);
        if (instance.isMinimizeVehicles()) {
            alns_nv.setGlobalSolution(initialSolution);
            alns_nv.setTemperature(initialSolution);
        }
    }

    private void log(String msg) {
        log.add(msg);
        if (showLog) {
            //System.out.println(Thread.currentThread().getId() + " -> " + msg);
            System.out.println(msg);
        }
    }

    private void logInFile(String text) {
        if (generateFile) {
            try {
                FileUtils.writeStringToFile(new File("C:\\Temp\\result-" + instance.getFileName()), text + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void updateBest(Solution solution, boolean force) {
        if (force || (solution.feasible && instance.getBest(solutionBest, solution) == solution)) {
            solutionBest = SolutionUtils.copy(solution);
            String msg = "NEW BEST = Iter " + iteration + " Sol = " + solution.toString();
            log(msg);
        }
    }

    private Solution applyLocalSearch(Solution solution) {
        boolean improvement = true;
        boolean foundImprovement = false;
        Solution improved = SolutionUtils.copy(solution);
        Solution tempSolution = improved;
        while (improvement) {
            improvement = false;
            tempSolution = relocateRequestOperator.relocate(tempSolution);
            tempSolution = exchangeRequestOperator.exchange(tempSolution);
            if (instance.getBest(improved, tempSolution) == tempSolution) {
                improved = SolutionUtils.copy(tempSolution);
                improvement = true;
                foundImprovement = true;
            }
        }
        if (foundImprovement) {
            detailedStatistics.localSearchImproved++;
        }
        detailedStatistics.localSearchUsed++;
        return improved;
    }

    private boolean stopCriteriaMeet() {
        return iteration > numIterations;
    }

    private void printFinalRoute() {
        Solution solution = solutionBest;
        String msg = "";
        instance.solutionEvaluation(solution);
        solution.totalCost = 0.0;
        double cost = 0.0;
        for (int i = 0; i < solution.tours.size(); i++) {
            cost = instance.costEvaluation(solution.tours.get(i));
            solution.tourCosts.set(i, cost);
            solution.totalCost += cost;
        }
        msg += "\nInstance = " + instance.getFileName();
        msg += "\nBest solution feasibility = " + solution.feasible + "\nRoutes";
        for (ArrayList route : solution.tours) {
            msg += "\n" + StringUtils.join(route, " ");
        }
        msg += "\nRequests";
        for (ArrayList requests : solution.requests) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nCost = " + Maths.round(solution.totalCost, 2);
        msg += "\nNum. vehicles = " + solution.tours.size();
        Set<Integer> processedNodes = new HashSet<>();
        for (int k = 0; k < solutionBest.tours.size(); k++) {
            for (int i = 1; i < solutionBest.tours.get(k).size() - 1; i++) {
                if (processedNodes.contains(solutionBest.tours.get(k).get(i))) {
                    throw new RuntimeException("Invalid route, duplicated nodes");
                } else {
                    processedNodes.add(solutionBest.tours.get(k).get(i));
                }
            }
        }
        msg += "\nInitialization time(s) = " + ((globalStatistics.getTimeStatistics().get("Initialization")) / 1000.0);
        msg += "\nAlgorithm time(s) = " + ((globalStatistics.getTimeStatistics().get("Algorithm")) / 1000.0);
        log(msg);
        logInFile(msg);
        //MapPrinter.printResult(solutionBest, instance, 800, 600, instance.getFileName());
    }

    public void enableMovingVehicle() {
        movingVehicle = new MovingVehicle(instance, 0, numIterations);
    }

    public void setGenerateDetailedStatistics(boolean generateDetailedStatistics) {
        this.generateDetailedStatistics = generateDetailedStatistics;
    }

    public void setGenerateFile(boolean generateFile) {
        this.generateFile = generateFile;
    }

    public List<String> getLog() {
        return log;
    }

    public Long getStatisticInterval() {
        return statisticInterval;
    }

    public void setStatisticInterval(Long statisticInterval) {
        this.statisticInterval = statisticInterval;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public List<IterationStatistic> getIterationStatistics() {
        return iterationStatistics;
    }
}
