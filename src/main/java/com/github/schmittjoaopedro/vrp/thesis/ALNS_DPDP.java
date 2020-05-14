package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.alns.CostMinimizerALNS;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.alns.VehicleMinimizerALNS;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.lns.CostMinimizerLNS;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.lns.VehicleMinimizerLNS;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.IterationCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.StopCriteria;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.statistic.ExperimentStatistics;
import com.github.schmittjoaopedro.vrp.thesis.statistic.Statistic;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ALNS_DPDP {

    public static final Logger LOGGER;

    static {
        System.setProperty("logFilename", "run_" + System.currentTimeMillis() + ".log");
        LOGGER = LogManager.getLogger(ALNS_DPDP.class);
    }

    private boolean printConsole = true;

    private boolean parallelExecution = false;

    private Instance instance;

    private StopCriteria stopCriteria;

    private LNSOptimizer vehicleMinimizer;

    private LNSOptimizer costMinimizer;

    private Solution solutionBest;

    private LinkedList<String> logs = new LinkedList<>();

    private CallCenter callCenter;

    private VehiclesControlCenter vehiclesControlCenter;

    private ExperimentStatistics experimentStatistics;

    private RoutePrinter routePrinter;

    private boolean collectIterationStatistics = false;

    private ThreadPoolExecutor threadPoolExecutor;

    private boolean logThreadInfo;

    private int iteration = 1;

    public ALNS_DPDP(Instance instance, Random random, int maxIterations, boolean minimizeNv, boolean minimizeTc, LNSOptimizer.Type type) {
        this(instance, random, maxIterations, 90, minimizeNv, minimizeTc, type);
    }

    public ALNS_DPDP(Instance instance, Random random, StopCriteria stopCriteria, boolean minimizeNv, boolean minimizeTc, LNSOptimizer.Type type) {
        this(instance, random, stopCriteria, 90, minimizeNv, minimizeTc, type);
    }

    public ALNS_DPDP(Instance instance, Random random, int maxIterations, double setupTime, boolean minimizeNv, boolean minimizeTc, LNSOptimizer.Type type) {
        this(instance, random, IterationCriteria.of(maxIterations), setupTime, minimizeNv, minimizeTc, type);
    }

    public ALNS_DPDP(Instance instance, Random random, StopCriteria stopCriteria, double setupTime, boolean minimizeNv, boolean minimizeTc, LNSOptimizer.Type type) {
        this.instance = instance;
        this.stopCriteria = stopCriteria;
        createVehicleMinimizer(instance, random, minimizeNv, type);
        createCostMinimizer(instance, random, minimizeTc, type);
        callCenter = new CallCenter(instance, setupTime);
        experimentStatistics = new ExperimentStatistics();
    }

    private void createCostMinimizer(Instance instance, Random random, boolean minimizeTc, LNSOptimizer.Type type) {
        if (minimizeTc == true) {
            switch (type) {
                case ALNS:
                    costMinimizer = new CostMinimizerALNS(instance, random);
                    break;
                case LNS:
                    costMinimizer = new CostMinimizerLNS(instance, random);
                    break;
                default:
                    throw new RuntimeException("Invalid algorithm");
            }
        }
    }

    private void createVehicleMinimizer(Instance instance, Random random, boolean minimizeNv, LNSOptimizer.Type type) {
        if (minimizeNv == true) {
            switch (type) {
                case ALNS:
                    vehicleMinimizer = new VehicleMinimizerALNS(instance, random);
                    break;
                case LNS:
                    vehicleMinimizer = new VehicleMinimizerLNS(instance, random);
                    break;
                default:
                    throw new RuntimeException("Invalid algorithm");
            }
        }
    }

    public void init() {
        // Load static requests
        Optional.ofNullable(callCenter).ifPresent(c -> c.loadStaticRequests());
        solutionBest = SolutionUtils.createSolution(instance);
        resetAlgorithms();
    }

    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            stopCriteria.start();
            while (stopCriteria.isContinue()) {
                // Update algorithm time relative to the current iteration
                updateAlgorithmTime();
                if (instance.numRequests > 0) {
                    /*
                     * Monitors vehicles visiting clients.
                     * This step must precede the new requests attendance because the local search executed by
                     * resetAlgorithms (in attendNewRequests) can relocate from one route to another a request
                     * that must be visited in the current time by the current route.
                     */
                    trackOperatingVehicles();
                    printVehiclesOperation();
                }
                // Check if new requests should be attended in the current time
                attendNewRequests();
                if (instance.numRequests > 0) {
                    // Optimize solutions
                    executeOptimization();
                    Solution feasibleNV = Optional.ofNullable(vehicleMinimizer).map(LNSOptimizer::getFeasibleSolutionBest).orElse(null);
                    Solution feasibleTC = Optional.ofNullable(costMinimizer).map(LNSOptimizer::getFeasibleSolutionBest).orElse(null);
                    // Use best solution from both NV and TC
                    Optional.of(getBestSolution(feasibleNV, feasibleTC)).ifPresent(best -> updateBest(best, iteration));
                    // Synchronize algorithm objectives
                    if (feasibleNV != null && feasibleTC != null && SolutionUtils.getBest(feasibleNV, feasibleTC) == feasibleNV) {
                        costMinimizer.resetToInitialSolution(feasibleNV);
                    }
                    collectIterationStatistics();
                }
                iteration++;
                stopCriteria.update();
            }
            experimentStatistics.totalTime = System.currentTimeMillis() - startTime;
            experimentStatistics.totalIterations = iteration;
            experimentStatistics.numSolutionsEvaluation = instance.numEvaluatedFunction;
            printVehiclesOperation();
            instance.solutionEvaluation(solutionBest);
            experimentStatistics.solutionBest = solutionBest;
            printSolutionBest();
        } finally {
            finishThreads();
        }
    }

    public void finishThreads() {
        if (parallelExecution) {
            threadPoolExecutor.shutdown();
        }
    }

    private void executeOptimization() {
        if (parallelExecution) {
            CountDownLatch latch = new CountDownLatch(2);
            threadPoolExecutor.submit(() -> {
                Optional.ofNullable(vehicleMinimizer).ifPresent(nv -> nv.optimize(iteration));
                latch.countDown();
            });
            threadPoolExecutor.submit(() -> {
                Optional.ofNullable(costMinimizer).ifPresent(tc -> tc.optimize(iteration));
                latch.countDown();
            });
            try {
                if (iteration % 5000 == 0 && logThreadInfo) {
                    LOGGER.info("Iteration {}. Active threads {}", iteration, Thread.activeCount());
                }
                latch.await();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            Optional.ofNullable(vehicleMinimizer).ifPresent(nv -> nv.optimize(iteration));
            Optional.ofNullable(costMinimizer).ifPresent(tc -> tc.optimize(iteration));
        }
    }

    private void trackOperatingVehicles() {
        if (instance.movingVehicle) {
            // Track moving vehicles
            if (vehiclesControlCenter.moveVehicle(solutionBest)) {
                Optional.ofNullable(vehicleMinimizer).ifPresent(vm -> vm.setBaseSolution(solutionBest));
                Optional.ofNullable(costMinimizer).ifPresent(cm -> cm.setBaseSolution(solutionBest));
            }
        }
    }

    private void collectIterationStatistics() {
        if (collectIterationStatistics) {
            experimentStatistics.add(
                    stopCriteria.getScaledTime(),
                    instance.numEvaluatedFunction,
                    solutionBest,
                    vehicleMinimizer.getSolutionBest(),
                    vehicleMinimizer.getSolutionLocal(),
                    vehicleMinimizer.getTemperature(),
                    costMinimizer.getSolutionBest(),
                    costMinimizer.getSolutionLocal(),
                    costMinimizer.getTemperature()
            );
        }
    }

    private void updateAlgorithmTime() {
        double scaledTime = stopCriteria.getScaledTime();
        instance.currentTime = (int) (instance.depot.twEnd - instance.depot.twStart) * scaledTime;
    }

    private void attendNewRequests() {
        if (Optional.ofNullable(callCenter).isPresent()) {
            List<Integer> requests = callCenter.loadNewRequests();
            if (!requests.isEmpty()) {
                log("New requests = " + StringUtils.join(requests));
                resetAlgorithms();
            }
        }
    }

    private void resetAlgorithms() {
        if (instance.numRequests > 0) {
            // Init both algorithms
            Optional.ofNullable(vehicleMinimizer).ifPresent(vm -> vm.init(solutionBest));
            Optional.ofNullable(costMinimizer).ifPresent(cm -> cm.init(solutionBest));
            // Create initial solution for both
            Solution initNv = Optional.ofNullable(vehicleMinimizer).map(LNSOptimizer::getFeasibleSolutionBest).orElse(null);
            Solution initTc = Optional.ofNullable(costMinimizer).map(LNSOptimizer::getFeasibleSolutionBest).orElse(null);
            // Select best initial solution
            solutionBest = getBestSolution(initNv, initTc);
            instance.solutionEvaluation(solutionBest);
            log("Insertion heuristic = " + solutionBest);
        }
    }

    private void updateBest(Solution solution, int iteration) {
        Solution bestSol = SolutionUtils.getBest(solutionBest, solution);
        if (bestSol != null && bestSol != solutionBest) {
            solutionBest = SolutionUtils.copy(bestSol);
            log("New best = " + solutionBest + " at iteration " + iteration);
        }
    }

    @NotNull
    private Solution getBestSolution(Solution initNv, Solution initTc) {
        return SolutionUtils.copy(Optional.ofNullable(initNv)
                .map(nv -> Optional.ofNullable(initTc)
                        .map(tc -> SolutionUtils.getBest(nv, tc))
                        .orElse(nv))
                .orElse(initTc));
    }

    public void printSolutionBest() {
        String msg = getSummaryResults();
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
        log(msg);
    }

    public void printSolutionBestRoute(String path) {
        RoutePrinter finalPrinter = new RoutePrinter(instance, path, 1024, 768);
        finalPrinter.staticPrintRoute(instance, solutionBest);
    }

    private String getSummaryResults() {
        double[] startTimes = new double[solutionBest.tours.size()];
        for (int i = 0; i < startTimes.length; i++) {
            startTimes[i] = instance.lastIdleTime(solutionBest.tours.get(i).get(1));
        }
        Optional.ofNullable(callCenter).ifPresent(c -> c.rollbackOriginalInformation(solutionBest));
        String msg = "\nInstance = " + instance.name;
        msg += "\nBest solution feasibility = " + solutionBest.feasible + "\nRoutes";
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            ArrayList<Integer> route = solutionBest.tours.get(i);
            double startTime = startTimes[i];
            if (instance.movingVehicle) {
                msg += "\nStart time " + MathUtils.round(startTime, 2) + " Route = " + StringUtils.join(route, " ");
            } else {
                msg += "\n" + StringUtils.join(route, " ");
            }
        }
        msg += "\nRequests";
        for (ArrayList requests : solutionBest.requestIds) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nNum. vehicles = " + solutionBest.tours.size();
        msg += "\nTotal cost = " + solutionBest.totalCost;
        return msg;
    }

    public void log(String msg) {
        if (printConsole) {
            System.out.println(msg);
        }
        logs.add(msg);
    }

    public LinkedList<String> getLogs() {
        return logs;
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }

    public void setPrintConsole(boolean printConsole) {
        this.printConsole = printConsole;
    }

    public void enableLocalSearch() {
        Optional.ofNullable(costMinimizer).ifPresent(tc -> tc.setUseLocalSearch(true));
    }

    public void enableVehicleControlCenter() {
        vehiclesControlCenter = new VehiclesControlCenter(instance);
        instance.movingVehicle = true;
    }

    public void saveStatistics(File file) {
        try {
            FileUtils.writeStringToFile(file, "iteration,nv,tc\n", "UTF-8", false);
            FileUtils.writeStringToFile(file, getSummaryResults(), "UTF-8", true);
            StringBuilder iterationStatistics = new StringBuilder();
            for (Statistic statistic : experimentStatistics.getStatistics()) {
                iterationStatistics
                        .append(statistic.processCompletion).append(',')
                        .append(statistic.solver_best_nv).append(',')
                        .append(statistic.solver_best_tc).append('\n');
            }
            FileUtils.writeStringToFile(file, iterationStatistics.toString(), "UTF-8", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printVehiclesOperation() {
        if (routePrinter != null && iteration % 5 == 0) {
            routePrinter.printRoute(instance, solutionBest, iteration);
        }
    }

    public void enablePrintOperation(String folderPath) {
        routePrinter = new RoutePrinter(instance, folderPath, 1024, 768);
    }

    public void enableIterationStatistics() {
        collectIterationStatistics = true;
    }

    public ExperimentStatistics getExperimentStatistics() {
        return experimentStatistics;
    }

    public void enableParallelExecution() {
        this.parallelExecution = true;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    }

    public void disableCallCenter() {
        callCenter = null;
    }

    public void enableThreadInfo() {
        logThreadInfo = true;
    }
}
