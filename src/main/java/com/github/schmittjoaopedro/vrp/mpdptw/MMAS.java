package com.github.schmittjoaopedro.vrp.mpdptw;

import java.lang.reflect.Array;
import java.util.*;

public class MMAS {

    private ProblemInstance instance;

    private double upperBound;

    private double EPSILON = 0.1;

    private double alpha;

    private double beta;

    private double q_0;

    private double rho;

    private double trailMax;

    private double trailMin;

    private double lambda;

    private int foundBest;

    private int restartFoundBest;

    private int restartIteration;

    private double calculatedBranchFact;

    private int nAnts;

    private int depth;

    private int currentIteration;

    private int uGb = Integer.MAX_VALUE;

    private List<Ant> antPopulation;

    private Ant bestSoFar;

    private Ant restartBest;

    private Ant previousBestSoFar;

    private double[][] pheromoneNodes;

    private double[][] pheromoneRequests;

    private int[][] nnList;

    private Random random;

    private double branchFac = 1.00001;

    private boolean symmetric = false;

    private double probabilities[];

    public MMAS(ProblemInstance instance) {
        this.instance = instance;
    }

    public void allocateAnts() {
        antPopulation = new ArrayList<>();
        for (int i = 0; i < getnAnts(); i++) {
            antPopulation.add(i, createEmptyAnt());
        }
        bestSoFar = createEmptyAnt();
        restartBest = createEmptyAnt();
        previousBestSoFar = createEmptyAnt();
    }

    public Ant createEmptyAnt() {
        Ant ant = new Ant();
        antEmptyMemory(ant);
        return ant;
    }

    public void allocateStructures() {
        pheromoneNodes = new double[instance.noNodes][instance.noNodes];
        pheromoneRequests = new double[instance.noReq + 1][instance.noReq + 1];
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                pheromoneNodes[i][j] = 0.0;
            }
        }
        for (int i = 0; i < pheromoneRequests.length; i++) {
            for (int j = 0; j < pheromoneRequests.length; j++) {
                pheromoneRequests[i][j] = 0.0;
            }
        }
        nnList = new int[instance.noNodes][depth];
        probabilities = new double[depth + 1];
    }

    public void computeNNList() {
        double[] distanceVector = new double[instance.noNodes];
        int[] helpVector = new int[instance.noNodes];
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (i != j && isTimeFeasible(i, j)) {
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

    // Check if the end time window of node j is achievable departing from node i
    public boolean isTimeFeasible(int i, int j) {
        boolean feasible = true;
        // requests does not index the node depot, therefore we have to adjust the indexes to ignore the depot
        if (i == 0 || j == 0) {
            feasible = true;
        } else {
            i--;
            j--;
            feasible = instance.requests[i].twStart + instance.requests[i].serviceTime + instance.distances[i][j] < instance.requests[j].twEnd;
        }
        return feasible;
    }

    // TODO: Service time must be accomplished before end time window?
    public boolean isRouteFeasible(ArrayList<Integer> tour) {
        double currentTime, capacity, demand, twStart, twEnd, serviceTime;
        boolean feasible = true;
        int current, next;
        currentTime = 0.0;
        capacity = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            current = tour.get(i);
            next = tour.get(i + 1);
            if (next == instance.depot.nodeId) {
                twStart = instance.depot.twStart;
                twEnd = instance.depot.twEnd;
                serviceTime = 0.0;
                demand = 0.0;
            } else {
                twStart = instance.requests[next - 1].twStart;
                twEnd = instance.requests[next - 1].twEnd;
                serviceTime = instance.requests[next - 1].serviceTime;
                demand = instance.requests[next - 1].demand;
            }
            currentTime += instance.distances[current][next];
            currentTime = Math.max(currentTime, twStart);
            currentTime += serviceTime;
            capacity += demand;
            if (currentTime > twEnd || capacity > instance.vehicleCapacity) {
                feasible = false;
                break;
            }
        }
        return feasible;
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
                if (i != j && isTimeFeasible(i, j)) {
                    pheromoneNodes[i][j] = initialTrail;
                }
            }
        }
        for (int i = 0; i < pheromoneRequests.length; i++) {
            for (int j = 0; j < pheromoneRequests.length; j++) {
                if (i != j) {
                    pheromoneRequests[i][j] = initialTrail;
                }
            }
        }
    }

    public Ant findBest() {
        Ant ant = antPopulation.get(0);
        double min = antPopulation.get(0).totalCost;
        for (int k = 1; k < nAnts; k++) {
            if (antPopulation.get(k).totalCost < min) {
                min = antPopulation.get(k).totalCost;
                ant = antPopulation.get(k);
            }
        }
        return ant;
    }

    public boolean updateBestSoFar() {
        Ant iterationBest = findBest();
        boolean found = false;
        if (iterationBest.totalCost < bestSoFar.totalCost) {
            found = true;
            copyFromTo(iterationBest, bestSoFar);
            copyFromTo(iterationBest, restartBest);
            foundBest = getCurrentIteration();
            restartFoundBest = getCurrentIteration();
            // calculatedBranchFact = nodeBranching(lambda); // TODO: Rever
        }
        return found;
    }

    public void setPheromoneBounds() {
        double p_x = Math.exp(Math.log(0.05) / instance.noNodes);
        trailMin = 1.0 * (1.0 - p_x) / (p_x * ((depth + 1) / 2));
        trailMax = 1.0 / (rho * instance.noNodes);
        trailMin = trailMax * trailMin;
    }

    public void updateRestartBest() {
        Ant iterationBest = findBest();
        if (iterationBest.totalCost < restartBest.totalCost) {
            copyFromTo(iterationBest, restartBest);
            restartFoundBest = getCurrentIteration();
        }
    }

    public void evaporation() {
        for (int i = 0; i < pheromoneRequests.length; i++) {
            for (int j = 0; j < pheromoneRequests[i].length; j++) {
                if (i != j) {
                    pheromoneRequests[i][j] = (1.0 - rho) * pheromoneRequests[i][j];
                }
            }
        }
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
            }
        }
        for (int i = 0; i < ant.requests.size(); i++) {
            for (int j = 0; j < ant.requests.get(i).size() - 1; j++) {
                from = ant.requests.get(i).get(j);
                to = ant.requests.get(i).get(j + 1);
                pheromoneRequests[from][to] = pheromoneRequests[from][to] + dTau;
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
        for (int i = 0; i < pheromoneRequests.length; i++) {
            for (int j = 0; j < pheromoneRequests.length; j++) {
                if (i != j) {
                    if (pheromoneRequests[i][j] < trailMin) {
                        pheromoneRequests[i][j] = trailMin;
                    } else if (pheromoneRequests[i][j] > trailMax) {
                        pheromoneRequests[i][j] = trailMax;
                    }
                }
            }
        }
    }

    public void searchControl() {
        if (getCurrentIteration() % 100 == 0) {
            calculatedBranchFact = nodeBranching(lambda);
            if (calculatedBranchFact < branchFac && getCurrentIteration() - restartFoundBest > 250) {
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

    public void copyFromTo(Ant from, Ant to) {
        to.totalCost = from.totalCost;
        to.capacityPenalty = from.capacityPenalty;
        to.feasible = from.feasible;
        to.visited = from.visited.clone();
        to.twPenalty = from.twPenalty;
        to.departureTime = from.departureTime;
        to.visitedRequests = from.visitedRequests;
        to.tours = new ArrayList<>();
        for (int i = 0; i < to.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) to.tours.get(0).clone());
        }
        to.tourLengths = new ArrayList<>();
        for (int i = 0; i < to.tourLengths.size(); i++) {
            to.tourLengths.add(i, to.tourLengths.get(0));
        }
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

    // TODO: Cost function must to consider the service time?
    public void fitnessEvaluation(Ant ant) {
        double currentTime, capacity, demand, twStart, twEnd, serviceTime;
        ant.totalCost = 0.0;
        int curr, next;
        for (int k = 0; k < ant.tours.size(); k++) {
            double tourCost = 0.0;
            ArrayList<Integer> tour = ant.tours.get(k);
            currentTime = 0.0;
            capacity = 0.0;
            for (int i = 0; i < ant.tours.get(k).size() - 1; i++) {
                curr = ant.tours.get(k).get(i);
                next = ant.tours.get(k).get(i + 1);
                if (next == instance.depot.nodeId) {
                    twStart = instance.depot.twStart;
                    twEnd = instance.depot.twEnd;
                    demand = 0.0;
                    serviceTime = 0.0;
                } else {
                    twStart = instance.requests[next - 1].twStart;
                    twEnd = instance.requests[next - 1].twEnd;
                    demand = instance.requests[next - 1].demand;
                    serviceTime = instance.requests[next - 1].serviceTime;
                }
                currentTime += instance.distances[curr][next];
                currentTime = Math.max(currentTime, twStart);
                currentTime += serviceTime;
                capacity += demand;
                if (currentTime > twEnd) {
                    ant.twPenalty += currentTime - twEnd;
                }
                if (capacity > instance.vehicleCapacity) {
                    ant.capacityPenalty += capacity - instance.vehicleCapacity;
                }
            }
            tourCost = currentTime;
            ant.tourLengths.add(k, tourCost);
            ant.totalCost += tourCost;
        }
        ant.totalCost += ant.twPenalty + ant.capacityPenalty;
    }

    public void antEmptyMemory(Ant ant) {
        ant.feasible = false;
        ant.tours = new ArrayList<>();
        ant.requests = new ArrayList<>();
        ant.visited = new boolean[instance.noNodes];
        ant.tourLengths = new ArrayList<>();
        ant.totalCost = Double.MAX_VALUE;
        ant.departureTime = new double[instance.noNodes];
        ant.visitedRequests = new boolean[instance.noReq];
        ant.capacityPenalty = 0.0;
        ant.twPenalty = 0.0;
    }

    public void constructSolutions() {
        boolean isFeasible;
        int reqStep, vehicle, lastPickupPos, pickupStep, currentReq;
        int pickupInsertion[], deliveryInsertion[]; // Return position to insert request , the request id to insert
        Ant currentAnt;
        Set<Integer> insertedPositions;
        for (Ant ant : antPopulation) {
            antEmptyMemory(ant);
        }
        for (int k = 0; k < antPopulation.size(); k++) {
            reqStep = 0;
            vehicle = 0;
            currentReq = instance.noReq; // It means that ant must to start a new vehicle
            currentAnt = antPopulation.get(k);
            currentAnt.feasible = true;
            currentAnt.tours.add(vehicle, new ArrayList<>());
            currentAnt.tours.get(vehicle).add(instance.depot.nodeId);
            currentAnt.tours.get(vehicle).add(instance.depot.nodeId);
            currentAnt.requests.add(vehicle, new ArrayList<>());
            currentAnt.requests.get(vehicle).add(instance.noReq);
            currentAnt.requests.get(vehicle).add(instance.noReq);

            while (reqStep < instance.noReq) {
                int reqId = neighbourNextRequest(currentAnt, currentReq);
                // If reqId == noReq, it means that the vehicle must be closed and a
                // new one started (because request indexes go from 0 to noReq - 1).
                isFeasible = isRouteFeasible(currentAnt.tours.get(vehicle));
                if (reqId == instance.noReq || !isFeasible) {
                    vehicle++;
                    currentReq = instance.noReq;
                    currentAnt.feasible &= isFeasible;
                    currentAnt.tours.add(vehicle, new ArrayList<>());
                    currentAnt.requests.add(vehicle, new ArrayList<>());
                    currentAnt.tours.get(vehicle).add(instance.depot.nodeId);
                    currentAnt.tours.get(vehicle).add(instance.depot.nodeId);
                    currentAnt.requests.get(vehicle).add(instance.noReq);
                    currentAnt.requests.get(vehicle).add(instance.noReq);
                } else {
                    currentAnt.requests.get(vehicle).add(currentAnt.requests.get(vehicle).size() - 1, reqId);
                    lastPickupPos = 0;
                    pickupStep = 0;
                    currentAnt.visitedRequests[reqId] = true;
                    insertedPositions = new HashSet<>();
                    // Selects the best position of each request pickup based on pheromone
                    while (pickupStep < instance.pickups.get(reqId).size()) {
                        pickupInsertion = neighbourNextNode(currentAnt, vehicle, 0, insertedPositions, instance.pickups.get(reqId));
                        currentAnt.tours.get(vehicle).add(pickupInsertion[0], pickupInsertion[1]);
                        currentAnt.visited[pickupInsertion[1]] = true;
                        // Update the last pickup position for that pickups takes precedence over the delivery
                        if (pickupInsertion[0] <= lastPickupPos) {
                            lastPickupPos++;
                        } else {
                            lastPickupPos = pickupInsertion[0];
                        }
                        pickupStep++;
                    }
                    // Selects the best delivery position of request based on pheromone
                    deliveryInsertion = neighbourNextNode(currentAnt, vehicle, lastPickupPos, insertedPositions, Arrays.asList(instance.delivery.get(reqId)));
                    currentAnt.tours.get(vehicle).add(deliveryInsertion[0], deliveryInsertion[1]);
                    currentAnt.visited[deliveryInsertion[1]] = true;
                    reqStep++;
                }
            }
        }
        for (Ant ant : antPopulation) {
            fitnessEvaluation(ant);
        }
    }

    private int[] neighbourNextNode(Ant ant, int vehicle, int startAt, Set<Integer> insertedPositions, List<Request> requestParts) {
        double sum = 0.0;
        int tourNode, pickupNode;
        Double[] temp;

        ArrayList<Integer> tour = ant.tours.get(vehicle);
        int tourNoLastDepot = tour.size() - 1;
        List<Double[]> probabilities = new ArrayList<>();

        for (int i = startAt; i < tourNoLastDepot; i++) {
            tourNode = tour.get(i);
            for (int j = 0; j < requestParts.size(); j++) {
                pickupNode = requestParts.get(j).nodeId;
                if (!ant.visited[pickupNode] && isTimeFeasible(tourNode, pickupNode) && !insertedPositions.contains(tourNode)) {
                    double heuristic = 1.0 / (
                            (1.0 / (pickupNode < instance.requests.length ? instance.requests[pickupNode].twEnd : instance.depot.twEnd)) +
                                    (1.0 - (1.0 / (pickupNode < instance.requests.length ? instance.requests[pickupNode].twStart : instance.depot.twStart))) +
                                    (1.0 / instance.distances[tourNode][pickupNode])
                    );
//                    double heuristic = 1.0 / instance.distances[tourNode][pickupNode];
                    sum += Math.pow(pheromoneNodes[tourNode][pickupNode], alpha) * Math.pow(heuristic, beta);
                    temp = new Double[]{
                            (double) i, // Insertion position
                            (double) pickupNode, // Node to insert
                            sum // Selection probability
                    };
                    probabilities.add(temp);
                }
            }
        }

        double rand = random.nextDouble() * sum;
        int selected = 0;
        if (probabilities.isEmpty()) {
            for (int i = 0; i < requestParts.size(); i++) {
                if (!ant.visited[requestParts.get(i).nodeId]) {
                    selected = requestParts.get(i).nodeId;
                    break;
                }
            }
            return new int[]{tourNoLastDepot, selected};
        } else {
            while (selected != probabilities.size() - 1) {
                if (rand < probabilities.get(selected)[2]) {
                    break;
                }
                selected++;
            }
            insertedPositions.add(tour.get(probabilities.get(selected)[0].intValue()));
            probabilities.get(selected)[0]++;
            return new int[]{probabilities.get(selected)[0].intValue(), probabilities.get(selected)[1].intValue()};
        }
    }

    private int neighbourNextRequest(Ant ant, int current) {
        int noReqWithCloseRoute = instance.noReq + 1;
        double[] probabilities = new double[noReqWithCloseRoute];
        double sum = 0.0;
        for (int r = 0; r < noReqWithCloseRoute; r++) {
            if (r == current || (r != instance.noReq && ant.visitedRequests[r])) {
                probabilities[r] = sum;
            } else {
                probabilities[r] = sum + Math.pow(pheromoneRequests[current][r], alpha);
                sum = probabilities[r];
            }
        }
        double rand = random.nextDouble() * sum;
        int selected = 0;
        while (selected != probabilities.length - 1) {
            if (rand < probabilities[selected]) {
                break;
            }
            selected++;
        }
        return selected;
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
        return avgDistance / ((double) nAnts * (double) (nAnts - 1) / 2.0);
    }

    private int distanceBetweenAnts(Ant a1, Ant a2) {
        int curr, next;
        Set<String> edgesA1 = new HashSet<>();
        Set<String> edgesA2 = new HashSet<>();

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
                edgesA2.add(curr + "-" + next);
            }
        }

        for (String edge : edgesA1) {
            edgesA2.remove(edge);
        }
        for (String edge : edgesA2) {
            edgesA1.remove(edge);
        }

        return edgesA2.size() + edgesA1.size();
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
        for (int m = 0; m < instance.noNodes; m++) {
            min = pheromoneNodes[m][0];
            max = min;
            for (int i = 1; i < instance.noNodes; i++) {
                max = Math.max(pheromoneNodes[m][i], max);
                min = Math.min(pheromoneNodes[m][i], min);
            }
            cutoff = min + l * (max - min);
            for (int i = 0; i < depth; i++) {
                if (pheromoneNodes[m][i] > cutoff)
                    numBranches[m] += 1.0;
            }
        }
        for (int m = 0; m < instance.noNodes; m++) {
            avg += numBranches[m];
        }
        return (avg / (double) (instance.noNodes * 2));
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setQ_0(double q_0) {
        this.q_0 = q_0;
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

    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
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
}
