package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.VehicleMinimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.CostMinimizer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Solver {

    private Instance instance;

    private int maxIterations;

    private VehicleMinimizer vehicleMinimizer;

    private CostMinimizer costMinimizer;

    private Solution solutionBest;

    private LinkedList<String> logs = new LinkedList<>();

    public Solver(Instance instance, Random random, int maxIterations, boolean minimizeNv, boolean minimizeTc) {
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
    }

    public void init() {
        // Init both algorithms
        Optional.ofNullable(vehicleMinimizer).ifPresent(VehicleMinimizer::init);
        Optional.ofNullable(costMinimizer).ifPresent(CostMinimizer::init);
        // Create initial solution for both
        Solution initNv = Optional.ofNullable(vehicleMinimizer).map(VehicleMinimizer::getFeasibleSolutionBest).orElse(null);
        Solution initTc = Optional.ofNullable(costMinimizer).map(CostMinimizer::getFeasibleSolutionBest).orElse(null);
        // Select best initial solution
        solutionBest = getBestSolution(initNv, initTc);
        instance.solutionEvaluation(solutionBest);
        log("Initial solution = " + solutionBest);
    }

    public void run() {
        int iteration = 1;
        while (iteration < maxIterations) {
            final int i = iteration;
            // Minimize NV
            Optional.ofNullable(vehicleMinimizer).ifPresent(nv -> nv.optimize(i));
            Solution feasibleNV = Optional.ofNullable(vehicleMinimizer).map(VehicleMinimizer::getFeasibleSolutionBest).orElse(null);
            // Minimize TC
            Optional.ofNullable(costMinimizer).ifPresent(tc -> tc.optimize(i));
            Solution feasibleTC = Optional.ofNullable(costMinimizer).map(CostMinimizer::getFeasibleSolutionBest).orElse(null);
            // Use best solution from both NV and TC
            Optional.of(getBestSolution(feasibleNV, feasibleTC)).ifPresent(this::updateBest);
            // Synchronize algorithm objectives
            if (feasibleNV != null && feasibleTC != null && SolutionUtils.getBest(feasibleNV, feasibleTC) == feasibleNV) {
                costMinimizer.resetToInitialSolution(feasibleNV);
            }
            iteration++;
        }
        instance.solutionEvaluation(solutionBest);
        printSolutionBest();
    }

    public void updateBest(Solution solution) {
        Solution bestSol = SolutionUtils.getBest(solutionBest, solution);
        if (bestSol != null && bestSol != solutionBest) {
            solutionBest = SolutionUtils.copy(bestSol);
            log("New best = " + solutionBest);
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
        String msg = "\nInstance = " + instance.name;
        msg += "\nBest solution feasibility = " + solutionBest.feasible + "\nRoutes";
        for (ArrayList route : solutionBest.tours) {
            msg += "\n" + StringUtils.join(route, " ");
        }
        msg += "\nRequests";
        for (ArrayList requests : solutionBest.requestIds) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nNum. vehicles = " + solutionBest.tours.size();
        msg += "\nTotal cost = " + solutionBest.totalCost;
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

    public void log(String msg) {
        System.out.println(msg);
        logs.add(msg);
    }

    public LinkedList<String> getLogs() {
        return logs;
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }
}
