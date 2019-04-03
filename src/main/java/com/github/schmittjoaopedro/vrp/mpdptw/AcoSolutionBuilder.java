package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcoSolutionBuilder {

    private ProblemInstance instance;

    private List<Ant> antPopulation;

    private Map<String, ArrayList<Integer>> feasibleRoutes = new HashMap<>();

    private double[][] pheromoneNodes;

    private double alpha;

    private double beta;

    private Random random;

    private boolean parallel;

    public AcoSolutionBuilder(ProblemInstance instance, List<Ant> antPopulation, Random random, double[][] pheromones, double alpha, double beta, boolean parallel) {
        this.instance = instance;
        this.antPopulation = antPopulation;
        this.pheromoneNodes = pheromones;
        this.alpha = alpha;
        this.beta = beta;
        this.random = random;
        this.parallel = parallel;
    }

    public void constructSolutions() {
        if (parallel) {
            Thread[] antBuilders = new Thread[antPopulation.size()];
            for (int i = 0; i < antPopulation.size(); i++) {
                Ant ant = antPopulation.get(i);
                antBuilders[i] = new Thread(() -> {
                    AntUtils.antEmptyMemory(ant, instance);
                    constructAntSolution(instance, ant);
                    instance.restrictionsEvaluation(ant);
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
            for (Ant ant : antPopulation) {// For each ant
                AntUtils.antEmptyMemory(ant, instance);
                constructAntSolution(instance, ant);
                instance.restrictionsEvaluation(ant);
                if (ant.capacityPenalty > 0) {
                    throw new RuntimeException("Invalid capacity penaly!!");
                }
            }
        }
    }

    public void constructAntSolution(ProblemInstance instance, Ant ant) {
        AntUtils.addEmptyVehicle(ant);
        int vehicle = 0;
        int currIdx = 0;
        ant.visited[0] = true;
        ant.toVisit--; // Remove depot
        ArrayList<Integer> remainingTour = new ArrayList<>();
        String hashKey = "0";
        while (ant.toVisit > 0) {
            int nextNode = selectNextNode(ant, vehicle, currIdx, remainingTour, hashKey);
            if (nextNode == -1) {
                addRemainingTour(ant, vehicle, remainingTour);
                remainingTour = new ArrayList<>();
                AntUtils.addEmptyVehicle(ant);
                vehicle++;
                currIdx = 0;
                hashKey = "0";
            } else {
                ant.tours.get(vehicle).add(ant.tours.get(vehicle).size() - 1, nextNode);
                ant.visited[nextNode] = true;
                int reqId = instance.requests[nextNode - 1].requestId;
                boolean containsNodeReq = ant.requests.get(vehicle).contains(reqId);
                if (!containsNodeReq) {
                    ant.requests.get(vehicle).add(reqId);
                }
                ant.toVisit--;
                ArrayList<Integer> route = new ArrayList<>(ant.tours.get(vehicle));
                route.addAll(route.size() - 1, remainingTour);
                instance.restrictionsEvaluation(route);
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

    private void addRemainingTour(Ant ant, int vehicle, ArrayList<Integer> remainingTour) {
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

    public int selectNextNode(Ant ant, int vehicle, int currIdx, ArrayList<Integer> remainingTour, String hashKey) {
        Map<Integer, ArrayList<Integer>> feasibleRemainingRoutes = new HashMap<>();
        Map<Integer, Double> feasibleCosts = new HashMap<>();
        int curr = ant.tours.get(vehicle).get(currIdx);
        boolean hasProb = false;
        double routeCost = instance.restrictionsEvaluation(ant.tours.get(vehicle)).cost;
        for (int i = 0; i < instance.noNodes; i++) {
            if (!ant.visited[i]) {
                Request req = instance.requests[i - 1];
                double newCost = routeCost - instance.distances[curr][0] + instance.distances[curr][i];
                newCost = Math.max(newCost, req.twStart);
                if (newCost < req.twEnd) {
                    newCost += req.serviceTime;
                    ArrayList<Integer> tempTour;
                    String key = hashKey + "," + i;
                    boolean feasibleChoice = feasibleRoutes.containsKey(key);
                    if (feasibleChoice) {
                        tempTour = feasibleRoutes.get(key);
                        feasibleChoice = tempTour != null;
                    } else {
                        ArrayList<Integer> tempRemainingTour = new ArrayList<>(remainingTour);
                        tempTour = new ArrayList<>(ant.tours.get(vehicle));
                        tempTour.add(tempTour.size() - 1, i); // insert before depot
                        addNextNodesRequests(ant.requests.get(vehicle), tempRemainingTour, i);
                        removeNode(tempRemainingTour, i);
                        addRemainingTour(tempTour, tempRemainingTour);
                        if (!isPrecedenceViolated(tempRemainingTour, i) && optimize(tempTour, currIdx + 2)) {
                            feasibleRoutes.put(key, tempTour);
                            feasibleChoice = true;
                        } else {
                            feasibleChoice = false;
                        }
                    }
                    if (feasibleChoice) { // Start after the next position
                        double tau = pheromoneNodes[curr][i];
                        double eta = 1.0 / newCost;
                        double cost = Math.pow(tau, alpha) + Math.pow(eta, beta);
                        feasibleCosts.put(i, cost);
                        feasibleRemainingRoutes.put(i, new ArrayList<>(tempTour.subList(currIdx + 2, tempTour.size() - 1))); // Ignore current and next nodes
                        hasProb = true;
                    }
                }
            }
        }
        int next = -1;
        if (hasProb) {
            double[] probs = new double[instance.noNodes];
            for (Map.Entry<Integer, Double> costs : feasibleCosts.entrySet()) {
                probs[costs.getKey()] = costs.getValue();
            }
            double sum = 0.0;
            for (int i = 0; i < probs.length; i++) {
                sum += probs[i];
                probs[i] = sum;
            }
            next = getNextRouletteSelection(probs, sum);
            remainingTour.clear();
            remainingTour.addAll(feasibleRemainingRoutes.get(next));
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
        Request req = instance.requests[node - 1];
        if (req.isDeliver) {
            for (int i = 0; i < tempRemainingTour.size(); i++) {
                Request req2 = instance.requests[tempRemainingTour.get(i) - 1];
                if (req.requestId == req2.requestId && req2.isPickup) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean optimize(ArrayList<Integer> tour, int startAt) {
        RelocateNodeOperator relocateNodeOperator = new RelocateNodeOperator(instance);
        ArrayList<Integer> improved = relocateNodeOperator.relocate(tour, startAt, true);
        ProblemInstance.FitnessResult result = instance.restrictionsEvaluation(improved);
        if (result.feasible) {
            tour.clear();
            tour.addAll(improved);
        }
        return result.feasible;
    }

    private void addNextNodesRequests(ArrayList<Integer> requests, ArrayList<Integer> tempRemainingTour, int nextNode) {
        int reqId = instance.requests[nextNode - 1].requestId;
        if (!requests.contains(reqId)) {
            for (Request pickup : instance.pickups.get(reqId)) {
                tempRemainingTour.add(pickup.nodeId);
            }
            tempRemainingTour.add(instance.delivery.get(reqId).nodeId);
        }
    }

    public void clearFeasibleRoutes() {
        feasibleRoutes.clear();
    }

    public Map<String, ArrayList<Integer>> getFeasibleRoutes() {
        return feasibleRoutes;
    }
}
