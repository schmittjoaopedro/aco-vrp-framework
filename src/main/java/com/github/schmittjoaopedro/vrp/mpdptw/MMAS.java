package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class MMAS {

    private ProblemInstance instance;

    private double upperBound;

    private double alpha;

    private double beta;

    private double rho;

    private double trailMax;

    private double trailMin;

    private double lambda;

    private int restartFoundBest;

    private int restartIteration;

    private double calculatedBranchFact;

    private int nAnts;

    private int foundBest;

    private int depth;

    private int currentIteration;

    private int uGb = Integer.MAX_VALUE;

    private List<Ant> antPopulation;

    private Ant bestSoFar;

    private Ant restartBest;

    private double[][] pheromoneNodes;

    private int[][] nnList;

    private Random random;

    private double branchFac = 1.00001;

    private Map<String, ArrayList<Integer>> feasibleRoutes = new HashMap<>();

    public final double weight1 = 0.3;

    public final double weight2 = 0.3;

    public final double weight3 = 0.3;

    public final double weight4 = 0.1;

    public MMAS(ProblemInstance instance) {
        this.instance = instance;
    }

    public void allocateAnts() {
        antPopulation = new ArrayList<>();
        for (int i = 0; i < getnAnts(); i++) {
            antPopulation.add(i, AntUtils.createEmptyAnt(instance));
        }
        bestSoFar = AntUtils.createEmptyAnt(instance);
        restartBest = AntUtils.createEmptyAnt(instance);
    }

    public void allocateStructures() {
        pheromoneNodes = new double[instance.noNodes][instance.noNodes];
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                pheromoneNodes[i][j] = 0.0;
            }
        }
        nnList = new int[instance.noNodes][depth];
    }

    public void computeNNList() {
        double[] distanceVector = new double[instance.noNodes];
        int[] helpVector = new int[instance.noNodes];
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (i != j && instance.isFeasible(i, j)) {
                    distanceVector[j] = instance.distances[i][j];
                } else {
                    distanceVector[j] = Double.MAX_VALUE;
                }
                if (i != j) {
                    helpVector[j] = j;
                }
            }
            sort(distanceVector, helpVector, 0, instance.noNodes - 1);
            for (int j = 0; j < depth; j++) {
                nnList[i][j] = helpVector[j];
            }
        }
    }

    private void swap(double v[], int v2[], int i, int j) {
        double tmpCost = v[i];
        int tempVertex = v2[i];
        v[i] = v[j];
        v2[i] = v2[j];
        v[j] = tmpCost;
        v2[j] = tempVertex;
    }

    private void sort(double v[], int v2[], int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap(v, v2, ++last, k);
        swap(v, v2, left, last);
        sort(v, v2, left, last);
        sort(v, v2, last + 1, right);
    }

    public void initTry() {
        lambda = 0.05;
        restartIteration = 1;
        for (int i = 0; i < bestSoFar.tourLengths.size(); i++) {
            bestSoFar.tourLengths.set(i, Double.MAX_VALUE);
        }
        foundBest = 0;
        trailMax = 1.0 / ((rho) * nnTour());
        trailMin = trailMax / (2.0 * instance.noNodes);
        initPheromoneTrails(trailMax);
    }

    private void initPheromoneTrails(double initialTrail) {
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (i != j) {
                    pheromoneNodes[i][j] = initialTrail;
                }
            }
        }
    }

    public Ant findBest() {
        Ant ant = antPopulation.get(0);
        double min = antPopulation.get(0).totalCost;
        for (int k = 1; k < nAnts; k++) {
            if (ant.feasible) {
                if (antPopulation.get(k).feasible && antPopulation.get(k).totalCost < min) {
                    min = antPopulation.get(k).totalCost;
                    ant = antPopulation.get(k);
                }
            } else {
                if (antPopulation.get(k).totalCost < min || antPopulation.get(k).feasible) {
                    min = antPopulation.get(k).totalCost;
                    ant = antPopulation.get(k);
                }
            }
        }
        return ant;
    }

    public boolean updateBestSoFar() {
        Ant iterationBest = findBest();
        boolean found = false;
        boolean isBetterCost = iterationBest.totalCost < bestSoFar.totalCost;
        boolean isCurrentFeasible = bestSoFar.feasible;
        boolean isNewFeasible = iterationBest.feasible;
        if ((!isCurrentFeasible && isBetterCost) || (isNewFeasible && isBetterCost) || (isNewFeasible && !isCurrentFeasible)) {
            found = true;
            AntUtils.copyFromTo(iterationBest, bestSoFar);
            AntUtils.copyFromTo(iterationBest, restartBest);
            foundBest = getCurrentIteration();
            restartFoundBest = getCurrentIteration();
            calculatedBranchFact = nodeBranching(lambda); // TODO: Rever
        }
        return found;
    }

    public void setPheromoneBounds() {
        double p_x = Math.exp(Math.log(0.05) / instance.noNodes);
        trailMin = 1.0 * (1.0 - p_x) / (p_x * ((depth + 1) / 2));
        trailMax = 1.0 / (rho * bestSoFar.totalCost);
        trailMin = trailMax * trailMin;
    }

    public void setPheromoneBoundsForLS() {
        trailMax = 1.0 / (rho * bestSoFar.totalCost);
        trailMin = trailMax / (2.0 * instance.noNodes);
    }

    public void updateRestartBest() {
        Ant iterationBest = findBest();
        if (iterationBest.totalCost < restartBest.totalCost) {
            AntUtils.copyFromTo(iterationBest, restartBest);
            restartFoundBest = getCurrentIteration();
        }
    }

    public void evaporation() {
        for (int i = 0; i < pheromoneNodes.length; i++) {
            for (int j = 0; j < pheromoneNodes[i].length; j++) {
                if (i != j) {
                    pheromoneNodes[i][j] = (1.0 - rho) * pheromoneNodes[i][j];
                }
            }
        }
    }

    public void pheromoneUpdate() {
        Ant iterationBest;
        if (getCurrentIteration() % uGb == 0) {
            iterationBest = findBest();
            globalPheromoneDeposit(iterationBest);
        } else {
            if (uGb == 1 && (getCurrentIteration() - restartFoundBest > 50)) {
                globalPheromoneDeposit(bestSoFar);
            } else {
                globalPheromoneDeposit(restartBest);
            }
        }
        uGb = 25;
    }

    public void globalPheromoneDeposit(Ant ant) {
        int from, to;
        double dTau = 1.0 / ant.totalCost;
        for (int i = 0; i < ant.tours.size(); i++) {
            for (int j = 0; j < ant.tours.get(i).size() - 1; j++) {
                from = ant.tours.get(i).get(j);
                to = ant.tours.get(i).get(j + 1);
                pheromoneNodes[from][to] = pheromoneNodes[from][to] + dTau;
                //pheromoneNodes[to][from] = pheromoneNodes[from][to]; // We can't consider this problem asymmetric due to time windows
            }
        }
    }

    public void checkPheromoneTrailLimits() {
        for (int i = 0; i < pheromoneNodes.length; i++) {
            for (int j = 0; j < pheromoneNodes.length; j++) {
                if (i != j) {
                    if (pheromoneNodes[i][j] < trailMin) {
                        pheromoneNodes[i][j] = trailMin;
                    } else if (pheromoneNodes[i][j] > trailMax) {
                        pheromoneNodes[i][j] = trailMax;
                    }
                }
            }
        }
    }

    public void searchControl() {
        if (getCurrentIteration() % 100 == 0) {
            calculatedBranchFact = nodeBranching(lambda);
            if (calculatedBranchFact < branchFac && getCurrentIteration() - restartFoundBest > 250) {
                System.out.println("Restart trails!!!");
                restartBest.totalCost = Double.MAX_VALUE;
                initPheromoneTrails(trailMax);
                restartIteration = getCurrentIteration();
                feasibleRoutes.clear();
            }
        }
    }

    public void updateUGB() {
        if ((getCurrentIteration() - restartIteration) < 25)
            uGb = 25;
        else if ((getCurrentIteration() - restartIteration) < 75)
            uGb = 5;
        else if ((getCurrentIteration() - restartIteration) < 125)
            uGb = 3;
        else if ((getCurrentIteration() - restartIteration) < 250)
            uGb = 2;
        else
            uGb = 1;
    }

    private double nnTour() {
        OptimalRequestSolver optimalRequestSolver;
        upperBound = 0.0;
        for (int r = 0; r < instance.noReq; r++) {
            optimalRequestSolver = new OptimalRequestSolver(r, instance);
            optimalRequestSolver.optimize();
            upperBound += optimalRequestSolver.getBestCost();
        }
        return upperBound;
    }

    public void constructSolutions() {
        for (Ant ant : antPopulation) {// For each ant
            AntUtils.antEmptyMemory(ant, instance);
            constructAntSolution(ant);
            instance.restrictionsEvaluation(ant);
            if (ant.capacityPenalty > 0) {
                throw new RuntimeException("Invalid capacity penaly!!");
            }
        }
    }

    public void constructAntSolution(Ant ant) {
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
                            feasibleRoutes.put(key, null);
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

    public Ant findWorst() {
        Ant ant = antPopulation.get(0);
        double max = antPopulation.get(0).totalCost;
        for (int k = 1; k < nAnts; k++) {
            if (antPopulation.get(k).totalCost > max) {
                max = antPopulation.get(k).totalCost;
                ant = antPopulation.get(k);
            }
        }
        return ant;
    }

    public double getPenaltyRate() {
        double count = 0;
        for (Ant ant : antPopulation) {
            if (!ant.feasible) {
                count++;
            }
        }
        return count / (double) antPopulation.size();
    }

    public double calculateDiversity() {
        double avgDistance = 0.0;
        for (int k = 0; k < antPopulation.size() - 1; k++) {
            for (int j = k + 1; j < antPopulation.size(); j++) {
                avgDistance += (double) distanceBetweenAnts(antPopulation.get(k), antPopulation.get(j));
            }
        }
        double difference = avgDistance / ((double) nAnts * (double) (nAnts - 1) / 2.0);
        return difference / instance.noNodes;
    }

    private int distanceBetweenAnts(Ant a1, Ant a2) {
        int curr, next, distance = 0;
        Set<String> edgesA1 = new HashSet<>();

        for (int k = 0; k < a1.tours.size(); k++) {
            for (int i = 0; i < a1.tours.get(k).size() - 1; i++) {
                curr = a1.tours.get(k).get(i);
                next = a1.tours.get(k).get(i + 1);
                edgesA1.add(curr + "-" + next);
            }
        }

        for (int k = 0; k < a2.tours.size(); k++) {
            for (int i = 0; i < a2.tours.get(k).size() - 1; i++) {
                curr = a2.tours.get(k).get(i);
                next = a2.tours.get(k).get(i + 1);
                if (!edgesA1.contains(curr + "-" + next)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public double nodeBranching() {
        return nodeBranching(lambda);
    }

    private double nodeBranching(double l) {
        double min;
        double max;
        double cutoff;
        double avg = 0.0;
        double numBranches[] = new double[instance.noNodes];
        for (int m = 1; m < instance.noNodes; m++) {
            min = Double.MAX_VALUE;
            max = Double.MIN_VALUE;
            for (int i = 0; i < instance.noNodes; i++) {
                if (m != i) {
                    max = Math.max(pheromoneNodes[m][i], max);
                    min = Math.min(pheromoneNodes[m][i], min);
                }
            }
            cutoff = min + l * (max - min);
            for (int i = 0; i < depth; i++) {
                if (m != i && pheromoneNodes[m][i] > cutoff)
                    numBranches[m] += 1.0;
            }
        }
        for (int m = 0; m < instance.noNodes; m++) {
            avg += numBranches[m];
        }
        return (avg / (double) instance.noNodes);
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public void setnAnts(int nAnts) {
        this.nAnts = nAnts;
    }

    public int getnAnts() {
        return nAnts;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public Ant getBestSoFar() {
        return bestSoFar;
    }

    public void setBestSoFar(Ant bestSoFar) {
        this.bestSoFar = bestSoFar;
    }

    public double getCalculatedBranchFact() {
        return calculatedBranchFact;
    }

    public int getFoundBest() {
        return foundBest;
    }

    public List<Ant> getAntPopulation() {
        return antPopulation;
    }

    private class NextClient {
        int nextClient = 0;
        double heuristic = 0.0;
        double departureTime = 0.0;
        double demand = 0.0;
        boolean feasible = false;
        double cumulativeCost = 0.0;
    }

}
