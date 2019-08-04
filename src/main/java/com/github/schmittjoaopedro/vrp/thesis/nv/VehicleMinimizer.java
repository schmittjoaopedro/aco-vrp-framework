package com.github.schmittjoaopedro.vrp.thesis.nv;

import com.github.schmittjoaopedro.vrp.thesis.problem.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class VehicleMinimizer {

    private Instance instance;

    private Random random;

    private double tolerance = 0.35;

    private double coolingRate = 0.9999;

    private double worstRandomDegree = 3;

    private double ShawRandomDegree = 6;

    private double removeControl = 0.4;

    private Set<Integer> visitedList = new HashSet<>();

    private Solution feasibleSolutionBest;

    private Solution solutionBest;

    private Solution solution;

    private RegretOperator regretOperator;

    private double[] scoreWeight = {33, 9, 13};

    private double reactionFactor = 0.1;

    private double[] removalWeight;
    private double[] insertingWeight;
    private double[] noiseWeight;
    private double[] removalScore;
    private double[] insertingScore;
    private double[] noiseScore;
    private int[] removalCount;
    private int[] insertingCount;
    private int[] noiseCount;

    private double T;

    public VehicleMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void init() {
        regretOperator = new RegretOperator(random, instance);
        solution = SolutionUtils.createSolution(instance);
        regretOperator.regretInsert(solution, 1, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);

        resetAdaptiveWeight();
        visitedList.clear();
        decreaseVehicleNumber(solution);
        instance.solutionEvaluation(solution);
        T = calcTemp(solution.totalCost);
        solutionBest = SolutionUtils.copy(solution);
    }

    public void optimize(int iteration) {
        // Copy current solution
        Solution sNew = SolutionUtils.copy(solution);

        // Generate random Q
        int q = 0;
        while (q < 4) {
            q = (int) (random.nextDouble() * Math.min(100, (int) (removeControl * instance.numRequests)));
        }

        // Noise, remove and insert
        int useNoise = rouletteSelection(noiseWeight);
        int removeHeuristic = requestRemove(sNew, q, removalWeight, worstRandomDegree, ShawRandomDegree);
        int insertHeuristic = requestInsert(sNew, q, insertingWeight, useNoise);
        instance.solutionEvaluation(sNew);

        // Calculate Score for each heuristic
        ++removalCount[removeHeuristic];
        ++insertingCount[insertHeuristic];
        ++noiseCount[useNoise];
        if (accept(sNew, solution, T)) {
            Integer hashNb = sNew.getHash();
            if (!visitedList.contains(hashNb)) {
                visitedList.add(hashNb);
                if (sNew.objFunction(instance) < solutionBest.objFunction(instance)) {
                    solutionBest = SolutionUtils.copy(sNew);
                    removalScore[removeHeuristic] += scoreWeight[0];
                    insertingScore[insertHeuristic] += scoreWeight[0];
                    noiseScore[useNoise] += scoreWeight[0];
                } else if (sNew.objFunction(instance) < solution.objFunction(instance)) {
                    removalScore[removeHeuristic] += scoreWeight[1];
                    insertingScore[insertHeuristic] += scoreWeight[1];
                    noiseScore[useNoise] += scoreWeight[1];
                } else {
                    removalScore[removeHeuristic] += scoreWeight[2];
                    insertingScore[insertHeuristic] += scoreWeight[2];
                    noiseScore[useNoise] += scoreWeight[2];
                }
            }
            solution = sNew;
        }
        T = T * coolingRate;
        if (iteration % 100 == 0) {
            //printWeights(temp, sBest);
            updateAdaptiveWeight(removalWeight, removalScore, removalCount, reactionFactor);
            updateAdaptiveWeight(insertingWeight, insertingScore, insertingCount, reactionFactor);
            updateAdaptiveWeight(noiseWeight, noiseScore, noiseCount, reactionFactor);
        }

        if (solution.feasible) {
            feasibleSolutionBest = SolutionUtils.copy(solution);
            decreaseVehicleNumber(solution);
            instance.solutionEvaluation(solution);
            solutionBest = SolutionUtils.copy(solution);
            T = calcTemp(solution.totalCost);
        }

    }

    // Update Weights of each heuristic after a segment
    private void updateAdaptiveWeight(double[] weight, double[] score, int[] nb, double reactionFactor) {
        for (int i = 0; i < weight.length; ++i) {
            weight[i] = weight[i] * (1 - reactionFactor) + reactionFactor * (score[i] / Math.max(nb[i], 1));
            score[i] = 0;
            nb[i] = 0;
        }
    }

    // Check if the new Solution is Accepted
    private boolean accept(Solution sNew, Solution s, double temp) {
        double obj = s.objFunction(instance);
        double objNew = sNew.objFunction(instance);
        if (objNew <= obj) return true;
        double probability = Math.exp((obj - objNew) / temp);
        return (random.nextDouble() <= probability);
    }

    public void decreaseVehicleNumber(Solution solution) {
        int vehicle = solution.tours.size() - 1;
        solution.tours.remove(vehicle);
        solution.requestIds.remove(vehicle);
        instance.solutionEvaluation(solution);
    }

    private void resetAdaptiveWeight() {
        removalWeight = new double[]{1, 1, 1};
        insertingWeight = new double[]{1, 1, 1, 1, 1};
        noiseWeight = new double[]{1, 1};
        removalScore = new double[]{0, 0, 0};
        insertingScore = new double[]{0, 0, 0, 0, 0};
        noiseScore = new double[]{0, 0};
        removalCount = new int[]{0, 0, 0};
        insertingCount = new int[]{0, 0, 0, 0, 0};
        noiseCount = new int[]{0, 0};
    }

    // Calculate Starting Temperature
    double calcTemp(double objValue) {
        return objValue * tolerance / Math.log(2);
    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }

    // Select a number using Roulette Wheel Selection
    private int rouletteSelection(double[] weight) {
        double[] sumWeight = new double[weight.length];
        double randomNumber = random.nextDouble();
        sumWeight[0] = weight[0];
        for (int i = 1; i < weight.length; ++i) {
            sumWeight[i] = sumWeight[i - 1] + weight[i];
        }
        randomNumber = randomNumber * sumWeight[weight.length - 1];
        for (int i = 0; i < weight.length; ++i) {
            if (randomNumber < sumWeight[i]) return i;
        }
        return weight.length - 1;
    }

    // Insert unvisited requests from solution and return used heuristic
    int requestInsert(Solution s, int q, double[] insertingWeight, int useNoise) {
        int insertHeuristic = rouletteSelection(insertingWeight);
        if (insertHeuristic == 0) regretOperator.regretInsert(s, 1, useNoise); // Basic Greedy insert
        else if (insertHeuristic == 1) regretOperator.regretInsert(s, 2, useNoise);
        else if (insertHeuristic == 2) regretOperator.regretInsert(s, 3, useNoise);
        else if (insertHeuristic == 3) regretOperator.regretInsert(s, 4, useNoise);
        else regretOperator.regretInsert(s, s.requestBankSize(instance), useNoise); // Regret-m insert
        return insertHeuristic;
    }

    // Remove requests from solution and return used heuristic
    private int requestRemove(Solution solution, int q, double[] removalWeight, double worstRandomDegree, double ShawRandomDegree) {
        int removeHeuristic = rouletteSelection(removalWeight);
        if (removeHeuristic == 0) randomRemoval(solution, q);
        else if (removeHeuristic == 1) worstRemoval(solution, q, worstRandomDegree);
        else ShawRemoval(solution, q, ShawRandomDegree);
        return removeHeuristic;
    }

    // Remove a number of random Customer
    void randomRemoval(Solution s, int q) {
        List<Integer> removeList = new ArrayList<>();
        for (Task pickupTask : instance.pickupTasks) {
            if (s.visited[pickupTask.nodeId]) removeList.add(pickupTask.requestId);
        }
        Collections.shuffle(removeList, random);
        if (removeList.size() > q) removeList = removeList.subList(0, q);
        s.remove(removeList, instance);
    }

    // Remove Customer base on Cost
    private void worstRemoval(Solution solution, int q, double worstRandomDegree) {
        for (int i = 0; i < solution.tours.size(); ++i) {
            solution.findRoute(i);
        }
        // Calculates the cost difference for each route for each request removed
        double[] costs = new double[instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            Request request = instance.requests[i];
            if (solution.visited[request.pickupTask.nodeId]) {
                int removeRoute = solution.nodeVehicle[request.pickupTask.nodeId].getLeft();
                Pair<Integer, Integer> pos = Pair.of(solution.nodeVehicle[request.pickupTask.nodeId].getRight(), solution.nodeVehicle[request.deliveryTask.nodeId].getRight());
                costs[request.requestId] = removeCost(pos, solution.tours.get(removeRoute));
            }
        }
        while (q > 0) { // While there are requests to remove
            // Create a heap to hold requests with expensive costs in front
            Queue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 0; i < instance.numRequests; ++i) {
                Request request = instance.requests[i];
                if (solution.visited[request.pickupTask.nodeId]) {
                    heap.add(Pair.of(-costs[request.requestId], -request.pickupTask.nodeId));
                }
            }
            if (heap.isEmpty()) break;
            double y = random.nextDouble();
            y = Math.pow(y, worstRandomDegree);
            int toRemove = (int) (y * (double) heap.size());
            int removePickupNode = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePickupNode = -heap.peek().getRight();
                heap.poll();
            }
            Integer requestId = instance.getTask(removePickupNode).requestId;
            int removeDeliveryNode = instance.requests[requestId].deliveryTask.nodeId;
            int removeRoute = solution.nodeVehicle[removePickupNode].getLeft();
            int pickupIdx = solution.nodeVehicle[removePickupNode].getRight();
            int deliveryIdx = solution.nodeVehicle[removeDeliveryNode].getRight();
            solution.visited[removePickupNode] = false;
            solution.visited[removeDeliveryNode] = false;
            solution.tours.get(removeRoute).remove(deliveryIdx);
            solution.tours.get(removeRoute).remove(pickupIdx);
            solution.requestIds.get(removeRoute).remove(requestId);
            --q;
            solution.findRoute(removeRoute);
            for (int i = 1; i < solution.tours.get(removeRoute).size() - 1; ++i) {
                int p = solution.tours.get(removeRoute).get(i);
                if (instance.getTask(p).isPickup) {
                    Request request = instance.requests[instance.getTask(p).requestId];
                    removeDeliveryNode = request.deliveryTask.nodeId;
                    Pair<Integer, Integer> pos = Pair.of(solution.nodeVehicle[p].getRight(), solution.nodeVehicle[removeDeliveryNode].getRight());
                    costs[request.requestId] = removeCost(pos, solution.tours.get(removeRoute));
                }
            }
        }
    }

    // Remove Customer base on relatedness
    void ShawRemoval(Solution solution, int q, double ShawRandomDegree) {
        ArrayList<Integer> allRequest = new ArrayList<>();
        ArrayList<Integer> removeList = new ArrayList<>();
        for (int i = 0; i < solution.tours.size(); ++i) {
            solution.findVisitedTime(instance, i);
        }
        ArrayList<Double>[] relate = new ArrayList[instance.numRequests];
        for (int i = 0; i < instance.numRequests; ++i) {
            Request reqA = instance.requests[i];
            if (solution.visited[reqA.pickupTask.nodeId]) {
                allRequest.add(i);
                relate[i] = new ArrayList<>(instance.numRequests);
                for (int j = 0; j < instance.numRequests; ++j) {
                    relate[i].add(0.0);
                    Request reqB = instance.requests[j];
                    if (solution.visited[instance.requests[j].pickupTask.nodeId]) {
                        relate[i].set(j, solution.relatedness(instance, reqA, reqB));
                    }
                }
            }
        }
        int r = (int) (random.nextDouble() * (double) allRequest.size());

        removeList.add(allRequest.get(r));
        allRequest.remove(r);
        while (removeList.size() < q && allRequest.size() > 0) {
            r = (int) (random.nextDouble() % (double) removeList.size());
            PriorityQueue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 0; i < allRequest.size(); ++i) {
                heap.add(Pair.of(relate[allRequest.get(i)].get(removeList.get(r)), -i));
            }
            double y = random.nextDouble();
            y = Math.pow(y, ShawRandomDegree);
            int toRemove = (int) (y * (double) allRequest.size());
            int removePos = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePos = -heap.peek().getRight();
                heap.poll();
            }
            removeList.add(allRequest.get(removePos));
            allRequest.remove(removePos);
        }
        solution.remove(removeList, instance);
    }

    // Calculate cost to remove customer from route
    private double removeCost(Pair<Integer, Integer> pos, ArrayList<Integer> route) {
        double oldCost = instance.calcRouteCost(route);
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        int delPos = pos.getRight();
        newRoute.remove(delPos);
        delPos = pos.getLeft();
        newRoute.remove(delPos);
        double cost = instance.calcRouteCost(newRoute);
        return oldCost - cost;
    }

}
