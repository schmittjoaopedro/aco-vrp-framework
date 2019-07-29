package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.nv.VehicleMinimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Solver {

    private Instance instance;

    private Random random;

    private int maxIterations;

    private VehicleMinimizer vehicleMinimizer;

    private Solution solutionBest;

    public Solver(Instance instance, Random random, int maxIterations) {
        this.instance = instance;
        this.random = random;
        this.maxIterations = maxIterations;
    }

    public void init() {
        vehicleMinimizer = new VehicleMinimizer(instance, random);
        vehicleMinimizer.init();
        solutionBest = vehicleMinimizer.getSolutionBest();
    }

    public void run() {
        int iteration = 1;
        while (iteration < maxIterations) {
            vehicleMinimizer.optimize(iteration);
            iteration++;
        }
        printSolutionBest();
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
        msg += "\nCost = " + solutionBest.totalCost;
        msg += "\nNum. vehicles = " + solutionBest.tours.size();
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
        System.out.println(msg);
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }
}
