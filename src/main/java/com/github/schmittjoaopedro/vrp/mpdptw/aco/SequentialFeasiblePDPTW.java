package com.github.schmittjoaopedro.vrp.mpdptw.aco;

import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SequentialFeasiblePDPTW implements SolutionBuilder {

    private ProblemInstance instance;

    private List<Solution> antPopulation;

    private double[][] pheromoneNodes;

    private double alpha;

    private double beta;

    private Random random;

    private MMAS mmas;

    private boolean parallel;

    private InsertionMethod insertionMethod;

    @Override
    public void init(ProblemInstance instance, Random random, MMAS mmas) {
        this.instance = instance;
        this.mmas = mmas;
        this.insertionMethod = new InsertionMethod(instance, random);
        updateParameters();
    }

    @Override
    public void onSearchControlExecute() {
    }

    @Override
    public void constructSolutions() {
        if (parallel) {
            Thread[] antBuilders = new Thread[antPopulation.size()];
            for (int i = 0; i < antPopulation.size(); i++) {
                Solution ant = antPopulation.get(i);
                antBuilders[i] = new Thread(() -> {
                    SolutionUtils.clearSolution(ant, instance);
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
                SolutionUtils.clearSolution(ant, instance);
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
        double routeCost = 0.0;
        while (ant.toVisit > 0) {
            int nextNode = addNextNode(ant, vehicle, currIdx, routeCost);
            if (nextNode == -1) {
                ant.toVisit -= ant.tours.get(vehicle).size() - 2; // Ignore depot at start and end
                if (ant.toVisit == 0) {
                    break;
                }
                SolutionUtils.addEmptyVehicle(ant);
                vehicle++;
                currIdx = 0;
                routeCost = 0.0;
            } else {
                routeCost += instance.dist(ant.tours.get(vehicle).get(currIdx), nextNode);
                routeCost = Math.max(instance.twStart(nextNode), routeCost);
                routeCost += instance.serviceTime(nextNode);
                ant.visited[nextNode] = true;
                int reqId = instance.getRequestId(nextNode);
                if (!ant.visitedRequests[reqId]) {
                    ant.requests.get(vehicle).add(reqId);
                    ant.visitedRequests[reqId] = true;
                }
                currIdx++;
            }
        }
    }

    public int addNextNode(Solution ant, int vehicle, int currIdx, double routeCost) {
        Map<Integer, ArrayList<Integer>> feasibleTours = new HashMap<>();
        Map<Integer, Double> feasibleCosts = new HashMap<>();
        ArrayList<Integer> originalRoute = ant.tours.get(vehicle);
        int curr = originalRoute.get(currIdx);
        boolean hasProb = false;
        for (Integer next = 0; next < instance.getNumNodes(); next++) {
            if (!ant.visited[next] && isPrecedenceValid(ant, next)) {
                Request req = instance.getRequest(next);
                double newCost = Math.max(routeCost + instance.dist(curr, next), req.twStart);
                if (newCost <= req.twEnd) {
                    ArrayList<Integer> tempTour = new ArrayList<>(originalRoute);
                    ant.tours.set(vehicle, tempTour);
                    boolean feasible = false;
                    // This is a new node. First, try to add the delivery node in the best position
                    if (req.isPickup) {
                        tempTour.add(currIdx + 1, next); // insert new node after next
                        instance.solutionEvaluation(ant, vehicle);
                        int deliveryNode = instance.getDelivery(req.requestId).nodeId;
                        InsertionMethod.BestPosition bestPosition = insertionMethod.insertAtBestPosition(ant, vehicle, deliveryNode, currIdx + 1);
                        if (bestPosition != null) { // If a best position was found
                            tempTour.add(bestPosition.position, deliveryNode);
                            feasible = true;
                        } else {
                            tempTour.add(currIdx + 2, deliveryNode);
                        }
                    } else {
                        tempTour.remove(next);
                        tempTour.add(currIdx + 1, next);
                    }
                    if (!feasible) {
                        feasible = optimize(tempTour, currIdx + 2).feasible;
                    }
                    if (feasible) {
                        double tau = pheromoneNodes[curr][next];
                        //double eta = 1.0 / (instance.dist(curr, next) + 0.00000001);
                        double cost = Math.pow(tau, alpha);// + Math.pow(eta, beta);
                        feasibleCosts.put(next, cost);
                        feasibleTours.put(next, tempTour); // Ignore current and next nodes
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
        }
        if (next != -1) {
            ant.tours.set(vehicle, feasibleTours.get(next));
        } else {
            ant.tours.set(vehicle, originalRoute);
        }
        return next;
    }

    // If next node is delivery, then pickup must be already visited. If next node is pickup, then precedence is valid.
    private boolean isPrecedenceValid(Solution ant, int next) {
        Request req = instance.getRequest(next);
        if (req.isDeliver) {
            return ant.visited[instance.getPickups(req.requestId).get(0).nodeId];
        } else {
            return true;
        }
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
