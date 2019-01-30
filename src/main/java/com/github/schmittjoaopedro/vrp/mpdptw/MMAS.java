package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private double[][] pheromone;

    private double[][] heuristic;

    private double[][] total;

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
        pheromone = new double[instance.noNodes][instance.noNodes];
        heuristic = new double[instance.noNodes][instance.noNodes];
        total = new double[instance.noNodes][instance.noNodes];
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                pheromone[i][j] = 0.0;
                heuristic[i][j] = 0.0;
                total[i][j] = 0.0;
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

    public void initHeuristicInfo() {
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (isTimeFeasible(i, j)) {
                    heuristic[i][j] = 1.0 / (instance.distances[i][j] + EPSILON);
                } else {
                    heuristic[i][j] = Double.MAX_VALUE;
                }
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
            if (instance.requests[i].isPickup && instance.requests[j].isDeliver) {
                feasible = instance.requests[i].twStart + instance.requests[i].serviceTime + instance.distances[i][j] < instance.requests[j].twEnd;
            }
        }
        return feasible;
    }

    // TODO: Service time must be accomplished before end time window?
    public boolean isRouteFeasible(List<Integer> tour) {
        double currentTime = 0.0;
        double capacity = 0.0;
        double demand;
        boolean feasible = true;
        int current, next;
        double twStart, twEnd, serviceTime;
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
        computeTotalInfo();
    }

    private void initPheromoneTrails(double initialTrail) {
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (i != j && isTimeFeasible(i, j)) {
                    pheromone[i][j] = initialTrail;
                }
            }
        }
    }

    public void computeTotalInfo() {
        for (int i = 0; i < instance.noNodes; i++) {
            for (int j = 0; j < instance.noNodes; j++) {
                if (i != j && isTimeFeasible(i, j)) {
                    total[i][j] = Math.pow(pheromone[i][j], alpha) * Math.pow(heuristic[i][j], beta);
                }
            }
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

    private int insertRequests(Ant ant, int vehicle) {
        int insertedRequests = 0;
        ant.tours.get(vehicle).add(ant.tours.get(vehicle).get(0));
        for (int r = 0; r < instance.noReq; r++) {
            if (!ant.visitedRequests[r] && insertRequest(ant, vehicle, r)) {
                ant.visitedRequests[r] = true;
                insertedRequests++;
            }
        }
        return insertedRequests;
    }

    private boolean insertRequest(Ant ant, int vehicle, int request) {
        ArrayList<Integer> newRoute = (ArrayList<Integer>) ant.tours.get(vehicle).clone();
        int lastReqIdx = 1, bestPosition;
        // First insert all pickup points for the current request
        for (Request req : instance.pickUpRequests.get(request)) {
            bestPosition = 0;
            for (int i = 0; i < newRoute.size() - 1; i++) {
                if (req.twEnd > instance.requests[newRoute.get(i) - 1].twEnd) {
                    bestPosition = i + 1;
                }
            }
            newRoute.add(bestPosition, req.nodeId);
            if (bestPosition <= lastReqIdx) {
                lastReqIdx++; // Deslocate the lastReq to right when a request is inserted before him
            } else {
                lastReqIdx = Math.max(lastReqIdx, bestPosition);
            }
        }
        // Lastly, insert the delivery point after all took requests
        bestPosition = lastReqIdx;
        for (int i = bestPosition; i < newRoute.size() - 1; i++) {
            if (instance.deliveryRequests.get(request).twEnd < instance.requests[newRoute.get(i) - 1].twEnd) {
                bestPosition = i + 1;
            }
        }
        newRoute.add(bestPosition, instance.deliveryRequests.get(request).nodeId);
        // If the request fit in the route and is feasible
        boolean inserted = newRoute.size() != ant.tours.get(vehicle).size() && isRouteFeasible(newRoute);
        if (inserted) {
            ant.tours.set(vehicle, newRoute);
        }
        return inserted;
    }

    // TODO: Cost function must to consider the service time?
    public void fitnessEvaluation(Ant ant) {
        double totalCost = 0.0;
        for (int k = 0; k < ant.tours.size(); k++) {
            double tourCost = 0.0;
            ArrayList<Integer> tour = ant.tours.get(k);
            for (int i = 0; i < ant.tours.get(k).size() - 1; i++) {
                tourCost += instance.distances[tour.get(i)][tour.get(i + 1)];
            }
            ant.tourLengths.add(k, tourCost);
            totalCost += tourCost;
        }
        ant.totalCost = totalCost;
    }

    public boolean isClosedRoute(Ant ant, int vehicle) {
        // Return true when a route have more than one node, and the first and last nodes are the depot.
        return ant.tours.get(vehicle).size() > 1 &&
                ant.tours.get(vehicle).get(0) == ant.tours.get(vehicle).get(ant.tours.get(vehicle).size() - 1);
    }

    public void placeAnt(Ant ant, int vehicle) {
        // Create a new route and put ant in depot
        ant.visited[instance.depot.nodeId] = true;
        ant.tours.add(new ArrayList<>());
        ant.tours.get(vehicle).add(instance.depot.nodeId);
    }

    public void antEmptyMemory(Ant ant) {
        ant.feasible = false;
        ant.tours = new ArrayList<>();
        ant.visited = new boolean[instance.noNodes];
        ant.tourLengths = new ArrayList<>();
        ant.totalCost = Double.MAX_VALUE;
        ant.departureTime = new double[instance.noNodes];
        ant.visitedRequests = new boolean[instance.noReq];
    }

    public Ant copy(Ant ant) {
        Ant newAnt = createEmptyAnt();
        for (int i = 0; i < ant.tours.size(); i++) {
            newAnt.tours.add(i, (ArrayList<Integer>) ant.tours.get(i).clone());
            newAnt.tourLengths.add(i, ant.tourLengths.get(i));
        }
        newAnt.totalCost = ant.totalCost;
        newAnt.visitedRequests = ant.visitedRequests.clone();
        newAnt.visited = ant.visited.clone();
        newAnt.departureTime = ant.departureTime.clone();
        return newAnt;
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
}
