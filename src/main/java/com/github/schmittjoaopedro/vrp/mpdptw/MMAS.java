package com.github.schmittjoaopedro.vrp.mpdptw;

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

    private double[][] pheromoneReqs;

    private int[][] nnList;

    private Random random;

    private double branchFac = 1.00001;

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
        pheromoneReqs = new double[instance.noReq + 1][instance.noReq + 1];
        for (int i = 0; i < pheromoneReqs.length; i++) {
            for (int j = 0; j < pheromoneReqs.length; j++) {
                pheromoneReqs[i][j] = 0.0;
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
        for (int i = 0; i < pheromoneReqs.length; i++) {
            for (int j = 0; j < pheromoneReqs.length; j++) {
                if (i != j) {
                    pheromoneReqs[i][j] = initialTrail;
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
        for (int i = 0; i < pheromoneReqs.length; i++) {
            for (int j = 0; j < pheromoneReqs[i].length; j++) {
                if (i != j) {
                    pheromoneReqs[i][j] = (1.0 - rho) * pheromoneReqs[i][j];
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
        for (int i = 0; i < ant.requests.size(); i++) {
            for (int j = 0; j < ant.requests.get(i).size() - 1; j++) {
                from = ant.requests.get(i).get(j);
                to = ant.requests.get(i).get(j + 1);
                pheromoneReqs[from][to] = pheromoneReqs[from][to] + dTau;
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
        for (int i = 0; i < pheromoneReqs.length; i++) {
            for (int j = 0; j < pheromoneReqs.length; j++) {
                if (i != j) {
                    if (pheromoneReqs[i][j] < trailMin) {
                        pheromoneReqs[i][j] = trailMin;
                    } else if (pheromoneReqs[i][j] > trailMax) {
                        pheromoneReqs[i][j] = trailMax;
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
        for (Ant ant : antPopulation) {
            AntUtils.antEmptyMemory(ant, instance);
        }
        for (Ant ant : antPopulation) {
            int[] requestsIds = getRequestsHeuristically();
            requestFor:
            for (int r = 1; r < requestsIds.length; r++) { // Starts in 1 to ignore depot node
                for (int k = 0; k < ant.tours.size(); k++) {
                    if (insertRequestOnTour(ant.tours.get(k), requestsIds[r])) {
                        ant.requests.get(k).add(requestsIds[r]);
                        ant.tourLengths.set(k, instance.costEvaluation(ant.tours.get(k)));
                        continue requestFor;
                    }
                }
                AntUtils.addEmptyVehicle(ant);
                int k = ant.tours.size() - 1;
                if (!insertRequestOnTour(ant.tours.get(k), requestsIds[r])) {
                    OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requestsIds[r], instance);
                    optimalRequestSolver.optimize();
                    ArrayList<Integer> tour = optimalRequestSolver.getTour();
                    ant.tours.set(k, tour);
                }
                ant.requests.get(k).add(requestsIds[r]);
                ant.tourLengths.set(k, instance.costEvaluation(ant.tours.get(k)));
            }
        }
        for (Ant ant : antPopulation) {
            instance.restrictionsEvaluation(ant);
            if (ant.capacityPenalty > 0) {
                throw new RuntimeException("Invalid capacity penaly!!");
            }
        }
    }

    private boolean insertRequestOnTour(ArrayList<Integer> originalRoute, int requestId) {
        boolean success = true;
        ArrayList<Integer> route = new ArrayList<>(originalRoute);
        Integer[] pickupNodes = instance.getPickupNodes(requestId);
        Integer deliveryNode = instance.getDelivery(requestId);
        int pos, lastPos = 0;
        for (int i = 0; i < pickupNodes.length; i++) {
            int pNode = pickupNodes[i];
            pos = insertNode(route, pNode, 0);
            if (pos == -1) {
                success = false;
                break;
            } else {
                if (pos <= lastPos) {
                    lastPos++;
                } else {
                    lastPos = pos;
                }
            }
        }
        if (success) {
            pos = insertNode(route, deliveryNode, lastPos);
            if (pos == -1) {
                success = false;
            }
        }
        if (success) {
            originalRoute.clear();
            originalRoute.addAll(route);
        }
        return success;
    }

    private int insertNode(ArrayList<Integer> route, int pNode, int startNode) {
        int n = route.size() - 1; // Ignore last depot, because we already have the first one
        double[] probs = new double[n];
        double sum = 0.0;
        ProblemInstance.FitnessResult insertCost;
        double routeCost = instance.costEvaluation(route);
        boolean hasProb = false;
        for (int j = startNode; j < n; j++) {
            route.add(j + 1, pNode);
            insertCost = instance.restrictionsEvaluation(route);
            if (insertCost.feasible) {
                double newCost = instance.costEvaluation(route);
                double dist = 1.0 / (newCost - routeCost + 0.1);
                sum += Math.pow(pheromoneNodes[route.get(j)][pNode], alpha) + Math.pow(dist, beta);
                probs[j] = sum;
                hasProb = true;
            } else {
                probs[j] = sum;
            }
            route.remove(j + 1);
        }
        if (hasProb) {
            int selectedIdx = getNextRouletteSelection(probs, sum) + 1; // Insert after the selected node
            route.add(selectedIdx, pNode);
            return selectedIdx;
        } else {
            return -1;
        }
    }

    private int[] getRequestsHeuristically() {
        int noReq = pheromoneReqs.length; // Depot + requests positions
        int[] requests = new int[noReq];
        boolean[] visited = new boolean[noReq];
        requests[0] = 0;
        visited[0] = true;
        int step = 1, current;
        double sum;
        double[] probs = new double[noReq];
        while (step < noReq) {
            sum = 0.0;
            current = requests[step - 1];
            for (int next = 0; next < noReq; next++) {
                probs[next] = sum;
                if (current != next && !visited[next]) {
                    sum += Math.pow(pheromoneReqs[current][next], alpha); // TODO: Heuristic value could be the mean between the values
                    probs[next] = sum;
                }
            }
            // Due to pheromoneReqs as index, that has one more request to simulate a depot, we have to subtract to ignore the depot
            requests[step] = getNextRouletteSelection(probs, sum);
            visited[requests[step]] = true;
            step++;
        }
        for (int i = 0; i < requests.length; i++) {
            requests[i]--;
        }
        return requests;
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

    public double calculateFutureCost(Ant ant, int vehicle, int currentCity, int nextCity) {
        double cost = 0.0;
        for (int reqId : ant.requests.get(vehicle)) {
            for (Request req : instance.pickups.get(reqId)) {
                if (!ant.visited[req.nodeId]) {
                    cost += instance.distances[currentCity][req.nodeId];
                }
            }
            if (!ant.visited[instance.delivery.get(reqId).nodeId]) {
                cost += instance.distances[currentCity][instance.delivery.get(reqId).nodeId];
            }
        }
        int reqId = instance.requests[nextCity - 1].requestId;
        if (!ant.visitedRequests[reqId]) {
            for (Request req : instance.pickups.get(reqId)) {
                cost += instance.distances[currentCity][req.nodeId];
            }
            cost += instance.distances[currentCity][instance.delivery.get(reqId).nodeId];
        }
        return cost;
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
