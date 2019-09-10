package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.CostMinimizer;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.Statistic;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.VehicleMinimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class Solver {

    private boolean printConsole = true;

    private Instance instance;

    private int maxIterations;

    private VehicleMinimizer vehicleMinimizer;

    private CostMinimizer costMinimizer;

    private Solution solutionBest;

    private LinkedList<String> logs = new LinkedList<>();

    private CallCenter callCenter;

    private VehiclesControlCenter vehiclesControlCenter;

    private int iteration = 1;

    private Statistic statistic;

    private RoutePrinter routePrinter;

    private boolean collectStatistics;

    public Solver(Instance instance, Random random, int maxIterations, boolean minimizeNv, boolean minimizeTc) {
        this(instance, random, maxIterations, 90, minimizeNv, minimizeTc);
    }

    public Solver(Instance instance, Random random, int maxIterations, double setupTime, boolean minimizeNv, boolean minimizeTc) {
        this.instance = instance;
        this.maxIterations = maxIterations;
        // Create vehicle minimizer
        if (minimizeNv == true) {
            vehicleMinimizer = new VehicleMinimizer(instance, random);
        }
        // Create cost minimizer
        if (minimizeTc == true) {
            costMinimizer = new CostMinimizer(instance, random);
        }
        callCenter = new CallCenter(instance, setupTime);
        statistic = new Statistic(maxIterations, instance);
    }

    public void init() {
        // Load static requests
        callCenter.loadStaticRequests();
        solutionBest = SolutionUtils.createSolution(instance);
        resetAlgorithms();
    }

    public void run() {
        while (iteration < maxIterations) {
            final int i = iteration;
            // Update algorithm time relative to the current iteration
            updateAlgorithmTime(i);
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
                // Minimize NV
                Optional.ofNullable(vehicleMinimizer).ifPresent(nv -> nv.optimize(i));
                Solution feasibleNV = Optional.ofNullable(vehicleMinimizer).map(VehicleMinimizer::getFeasibleSolutionBest).orElse(null);
                // Minimize TC
                Optional.ofNullable(costMinimizer).ifPresent(tc -> tc.optimize(i));
                Solution feasibleTC = Optional.ofNullable(costMinimizer).map(CostMinimizer::getFeasibleSolutionBest).orElse(null);
                // Use best solution from both NV and TC
                Optional.of(getBestSolution(feasibleNV, feasibleTC)).ifPresent(best -> updateBest(best, i));
                // Synchronize algorithm objectives
                if (feasibleNV != null && feasibleTC != null && SolutionUtils.getBest(feasibleNV, feasibleTC) == feasibleNV) {
                    costMinimizer.resetToInitialSolution(feasibleNV);
                }
                collectStatistics();
            }
            iteration++;
        }
        printVehiclesOperation();
        instance.solutionEvaluation(solutionBest);
        statistic.solutionBest = solutionBest;
        printSolutionBest();
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

    private void collectStatistics() {
        if (collectStatistics) {
            statistic.global_nv[iteration] = solutionBest.tours.size();
            statistic.global_tc[iteration] = solutionBest.totalCost;
            statistic.global_fc[iteration] = solutionBest.feasible ? 1 : 0;
            if (vehicleMinimizer != null) {
                statistic.vehicle_minimizer_best_nv[iteration] = vehicleMinimizer.getSolutionBest().tours.size();
                statistic.vehicle_minimizer_best_tc[iteration] = vehicleMinimizer.getSolutionBest().totalCost;
                statistic.vehicle_minimizer_local_nv[iteration] = vehicleMinimizer.getSolutionLocal().tours.size();
                statistic.vehicle_minimizer_local_tc[iteration] = vehicleMinimizer.getSolutionLocal().totalCost;
                statistic.vehicle_minimizer_temperature[iteration] = vehicleMinimizer.getTemperature();
            }
            if (costMinimizer != null) {
                statistic.cost_minimizer_best_nv[iteration] = costMinimizer.getSolutionBest().tours.size();
                statistic.cost_minimizer_best_tc[iteration] = costMinimizer.getSolutionBest().totalCost;
                statistic.cost_minimizer_local_nv[iteration] = costMinimizer.getSolutionLocal().tours.size();
                statistic.cost_minimizer_local_tc[iteration] = costMinimizer.getSolutionLocal().totalCost;
                statistic.cost_minimizer_temperature[iteration] = costMinimizer.getTemperature();
            }
        }
    }

    private void updateAlgorithmTime(double currentTime) {
        double scaledTime = currentTime / maxIterations;
        instance.currentTime = (int) (instance.depot.twEnd - instance.depot.twStart) * scaledTime;
    }

    private void attendNewRequests() {
        List<Integer> requests = callCenter.loadNewRequests();
        if (!requests.isEmpty()) {
            log("New requests = " + StringUtils.join(requests));
            resetAlgorithms();
        }
    }

    private void resetAlgorithms() {
        if (instance.numRequests > 0) {
            // Init both algorithms
            Optional.ofNullable(vehicleMinimizer).ifPresent(vm -> vm.init(solutionBest));
            Optional.ofNullable(costMinimizer).ifPresent(cm -> cm.init(solutionBest));
            // Create initial solution for both
            Solution initNv = Optional.ofNullable(vehicleMinimizer).map(VehicleMinimizer::getFeasibleSolutionBest).orElse(null);
            Solution initTc = Optional.ofNullable(costMinimizer).map(CostMinimizer::getFeasibleSolutionBest).orElse(null);
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

    private String getSummaryResults() {
        double[] startTimes = new double[solutionBest.tours.size()];
        for (int i = 0; i < startTimes.length; i++) {
            startTimes[i] = instance.lastIdleTime(solutionBest.tours.get(i).get(1));
        }
        callCenter.rollbackOriginalInformation(solutionBest);
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
            StringBuilder iterationStatistics = new StringBuilder();
            for (int i = 1; i < maxIterations; i++) {
                iterationStatistics.append(i).append(',').append(statistic.global_nv[i]).append(',').append(statistic.global_tc[i]).append('\n');
            }
            FileUtils.writeStringToFile(file, iterationStatistics.toString(), "UTF-8", true);
            FileUtils.writeStringToFile(file, getSummaryResults(), "UTF-8", true);
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

    public void enableStatisticsCollector() {
        collectStatistics = true;
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
