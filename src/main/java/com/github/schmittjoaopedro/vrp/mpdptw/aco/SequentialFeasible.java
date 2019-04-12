package com.github.schmittjoaopedro.vrp.mpdptw.aco;

import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SequentialFeasible implements SolutionBuilder {

    private ProblemInstance instance;

    private List<Solution> antPopulation;

    private Map<String, ArrayList<Integer>> routesCache = new HashMap<>();

    private Map<String, Double> routesCostCache = new HashMap<>();

    private double[][] pheromoneNodes;

    private double alpha;

    private double beta;

    private Random random;

    private MMAS mmas;

    private boolean parallel;

    @Override
    public void init(ProblemInstance instance, Random random, MMAS mmas) {
        this.instance = instance;
        this.mmas = mmas;
        updateParameters();
    }

    @Override
    public void onSearchControlExecute() {
        routesCache.clear();
        routesCostCache.clear();
    }

    @Override
    public void constructSolutions() {
        if (parallel) {
            Thread[] antBuilders = new Thread[antPopulation.size()];
            for (int i = 0; i < antPopulation.size(); i++) {
                Solution ant = antPopulation.get(i);
                antBuilders[i] = new Thread(() -> {
                    SolutionUtils.antEmptyMemory(ant, instance);
                    constructAntSolution(instance, ant);
                    instance.solutionEvaluation(ant);
                    if (ant.capacityPenalty > 0) {
                        throw new RuntimeException("Invalid capacity penaly!!");
                    }
                });
            }
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            for (Thread t : antBuilders) {
                executorService.submit(t);
            }
            executorService.shutdown();
            while (!executorService.isTerminated()) ;
        } else {
            for (Solution ant : antPopulation) {// For each ant
                SolutionUtils.antEmptyMemory(ant, instance);
                constructAntSolution(instance, ant);
                instance.solutionEvaluation(ant);
                if (ant.capacityPenalty > 0) {
                    throw new RuntimeException("Invalid capacity penaly!!");
                }
            }
        }
    }

    public void constructAntSolution(ProblemInstance instance, Solution ant) {
        SolutionUtils.addEmptyVehicle(ant);
        int vehicle = 0;
        int currIdx = 0;
        ant.visited[0] = true;
        ant.toVisit--; // Remove depot
        ArrayList<Integer> remainingTour = new ArrayList<>();
        String hashKey = "0";
        double routeCost = 0.0;
        while (ant.toVisit > 0) {
            int nextNode = selectNextNode(ant, vehicle, currIdx, routeCost, remainingTour, hashKey);
            if (nextNode == -1) {
                addRemainingTour(ant, vehicle, remainingTour);
                remainingTour = new ArrayList<>();
                SolutionUtils.addEmptyVehicle(ant);
                vehicle++;
                currIdx = 0;
                hashKey = "0";
                routeCost = 0.0;
            } else {
                routeCost += instance.dist(ant.tours.get(vehicle).get(currIdx), nextNode);
                routeCost = Math.max(instance.twStart(nextNode), routeCost);
                routeCost += instance.serviceTime(nextNode);
                ant.tours.get(vehicle).add(ant.tours.get(vehicle).size() - 1, nextNode);
                ant.visited[nextNode] = true;
                int reqId = instance.getRequestId(nextNode);
                boolean containsNodeReq = ant.requests.get(vehicle).contains(reqId);
                if (!containsNodeReq) {
                    ant.requests.get(vehicle).add(reqId);
                }
                ant.toVisit--;
                currIdx++;
                hashKey += "," + nextNode;
            }
        }
    }

    private void removeNode(ArrayList<Integer> route, int node) {
        for (int i = 0; i < route.size(); i++) {
            if (route.get(i) == node) {
                route.remove(i);
                break;
            }
        }
    }

    private void addRemainingTour(Solution ant, int vehicle, ArrayList<Integer> remainingTour) {
        for (int i = 0; i < remainingTour.size(); i++) {
            int tourLength = ant.tours.get(vehicle).size();
            ant.tours.get(vehicle).add(tourLength - 2, remainingTour.get(i));
            ant.visited[remainingTour.get(i)] = true;
        }
        ant.toVisit = ant.toVisit - remainingTour.size();
    }

    private void addRemainingTour(ArrayList<Integer> tour, ArrayList<Integer> remainingTour) {
        for (int i = 0; i < remainingTour.size(); i++) {
            tour.add(tour.size() - 1, remainingTour.get(i)); // Add before depot
        }
    }

    public int selectNextNode(Solution ant, int vehicle, int currIdx, double routeCost, ArrayList<Integer> pendingTour, String currKey) {
        Map<Integer, ArrayList<Integer>> pendingTours = new HashMap<>();
        Map<Integer, Double> feasibleCosts = new HashMap<>();
        int curr = ant.tours.get(vehicle).get(currIdx);
        boolean hasProb = false;
        for (int next = 0; next < instance.getNumNodes(); next++) {
            if (!ant.visited[next]) {
                Request req = instance.getRequest(next);
                double newCost = Math.max(routeCost + instance.dist(curr, next), req.twStart);
                if (newCost < req.twEnd) {
                    ArrayList<Integer> tempTour;
                    String nextKey = currKey + "," + next;
                    boolean feasibleChoice = routesCache.containsKey(nextKey);
                    if (feasibleChoice) {
                        tempTour = routesCache.get(nextKey);
                        feasibleChoice = tempTour != null;
                    } else {
                        ArrayList<Integer> tempPendingTour = new ArrayList<>(pendingTour);
                        tempTour = new ArrayList<>(ant.tours.get(vehicle));
                        tempTour.add(tempTour.size() - 1, next); // insert before depot
                        addNextNodesRequests(ant.requests.get(vehicle), tempPendingTour, next);
                        removeNode(tempPendingTour, next);
                        addRemainingTour(tempTour, tempPendingTour);
                        if (!isPrecedenceViolated(tempPendingTour, next)) {
                            ProblemInstance.FitnessResult result = optimize(tempTour, currIdx + 2);
                            if (result.feasible) {
                                routesCache.put(nextKey, tempTour);
                                routesCostCache.put(nextKey, result.cost);
                                feasibleChoice = true;
                            } else {
                                feasibleChoice = false;
                            }
                        } else {
                            feasibleChoice = false;
                        }
                    }
                    if (feasibleChoice) { // Start after the next position
                        double tau = pheromoneNodes[curr][next];
                        //double eta = 1.0 / (instance.dist(curr, next) + 0.00000001);
                        double cost = Math.pow(tau, alpha);// + Math.pow(eta, beta);
                        feasibleCosts.put(next, cost);
                        pendingTours.put(next, new ArrayList<>(tempTour.subList(currIdx + 2, tempTour.size() - 1))); // Ignore current and next nodes
                        hasProb = true;
                    }
                }
            }
        }
        int next = -1;
        if (hasProb) {
            double[] probs = new double[instance.getNumNodes()];
            for (Map.Entry<Integer, Double> costs : feasibleCosts.entrySet()) {
                probs[costs.getKey()] = costs.getValue();
            }
            double sum = 0.0;
            for (int i = 0; i < probs.length; i++) {
                sum += probs[i];
                probs[i] = sum;
            }
            next = getNextRouletteSelection(probs, sum);
            pendingTour.clear();
            pendingTour.addAll(pendingTours.get(next));
        }
        return next;
    }

    private int getNextRouletteSelection(double[] probs, double sum) {
        int count = 0;
        double partialSum = probs[count];
        double rand = random.nextDouble() * sum;
        while (partialSum <= rand) {
            count++;
            partialSum = probs[count];
        }
        return count;
    }

    private boolean isPrecedenceViolated(ArrayList<Integer> tempRemainingTour, int node) {
        Request req = instance.getRequest(node);
        if (req.isDeliver) {
            for (int i = 0; i < tempRemainingTour.size(); i++) {
                Request req2 = instance.getRequest(tempRemainingTour.get(i));
                if (req.requestId == req2.requestId && req2.isPickup) {
                    return true;
                }
            }
        }
        return false;
    }

    private ProblemInstance.FitnessResult optimize(ArrayList<Integer> tour, int startAt) {
        RelocateNodeOperator relocateNodeOperator = new RelocateNodeOperator(instance);
        ArrayList<Integer> improved = relocateNodeOperator.relocate(tour, startAt, true);
        ProblemInstance.FitnessResult result = instance.restrictionsEvaluation(improved);
        if (result.feasible) {
            tour.clear();
            tour.addAll(improved);
        }
        return result;
    }

    private void addNextNodesRequests(ArrayList<Integer> requests, ArrayList<Integer> tempRemainingTour, int nextNode) {
        int reqId = instance.getRequestId(nextNode);
        if (!requests.contains(reqId)) {
            for (Request pickup : instance.getPickups(reqId)) {
                tempRemainingTour.add(pickup.nodeId);
            }
            tempRemainingTour.add(instance.getDelivery(reqId).nodeId);
        }
    }

    private void updateParameters() {
        this.antPopulation = mmas.getAntPopulation();
        this.pheromoneNodes = mmas.getPheromoneNodes();
        this.alpha = mmas.getAlpha();
        this.beta = mmas.getBeta();
        this.random = mmas.getRandom();
        this.parallel = mmas.isParallel();
    }
}
