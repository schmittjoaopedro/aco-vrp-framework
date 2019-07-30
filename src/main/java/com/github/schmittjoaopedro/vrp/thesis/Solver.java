package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.nv.VehicleMinimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Solver {

    private Instance instance;

    private Random random;

    private int maxIterations;

    private VehicleMinimizer vehicleMinimizer;

    private Solution solutionBest;

    private LinkedList<String> logs = new LinkedList<>();

    public Solver(Instance instance, Random random, int maxIterations) {
        this.instance = instance;
        this.random = random;
        this.maxIterations = maxIterations;
    }

    public void init() {
        vehicleMinimizer = new VehicleMinimizer(instance, random);
        vehicleMinimizer.init();
        solutionBest = vehicleMinimizer.getFeasibleSolutionBest();
        log("Initial solution = " + solutionBest);
    }

    public void run() {
        int iteration = 1;
        while (iteration < maxIterations) {
            vehicleMinimizer.optimize(iteration);
            updateBest(vehicleMinimizer.getFeasibleSolutionBest());
            iteration++;
        }
        printSolutionBest();
    }

    public void updateBest(Solution solution) {
        boolean isNVMinimized = solution.tours.size() < solutionBest.tours.size();
        boolean isTCMinimized = solution.tours.size() == solutionBest.tours.size() && MathUtils.round(solution.totalCost) < MathUtils.round(solutionBest.totalCost);
        if (solution.feasible && (isNVMinimized || isTCMinimized)) {
            solutionBest = SolutionUtils.copy(solution);
            log("New best = " + solutionBest);
        }
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
