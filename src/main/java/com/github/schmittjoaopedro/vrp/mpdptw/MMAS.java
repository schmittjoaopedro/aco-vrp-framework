package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionOperator;

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

    private InsertionOperator insertionOperator;

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
        if (this.insertionOperator == null) {
            this.insertionOperator = new InsertionOperator(instance, random);
        }
        for (Ant ant : antPopulation) {
            AntUtils.antEmptyMemory(ant, instance);
            constructSolution(ant);
            instance.restrictionsEvaluation(ant);
            if (ant.capacityPenalty > 0) {
                throw new RuntimeException("Invalid capacity penaly!!");
            }
        }
    }

    public void constructSolution(Ant ant) {
        int phase = 0;
        int curr = 0;
        int reqVehicles[] = new int[instance.noReq];
        for (int i = 0; i < reqVehicles.length; i++) {
            reqVehicles[i] = -1;
        }
        while (phase < instance.noNodes - 1) { // Ignore depot node
            int nextNode = chooseNextNode(ant, curr);
            insertNextNode(ant, reqVehicles, nextNode);
            ant.visited[nextNode] = true;
            curr = nextNode;
            phase++;
        }
        AntUtils.removeEmptyVehicles(ant);
    }

    private int chooseNextNode(Ant ant, int curr) {
        double probabilities[] = new double[instance.noNodes];
        double sum = 0.0;
        for (int next = 1; next < instance.noNodes; next++) { // Start with the first client
            Request req = instance.requests[next - 1];
            boolean isPickup = req.isPickup;
            if (!ant.visited[next] && (isPickup || isDeliveryAvailable(ant, req.requestId))) {
                sum += pheromoneNodes[curr][next];
                probabilities[next] = sum;
            }
        }
        int count = 0;
        double partialSum = probabilities[count];
        double rand = random.nextDouble() * sum;
        while (partialSum <= rand) {
            count++;
            partialSum = probabilities[count];
        }
        return count;
    }

    private boolean isDeliveryAvailable(Ant ant, int reqId) {
        boolean available = true;
        for (Request pickup : instance.pickups.get(reqId)) {
            available &= ant.visited[pickup.nodeId];
        }
        return available;
    }

    private void insertNextNode(Ant ant, int reqVehicles[], int nextNode) {
        ArrayList<Integer> tour;
        InsertionOperator.BestPosition bestPosition;
        Request nodeReq = instance.requests[nextNode - 1];
        int vehicle = reqVehicles[nodeReq.requestId];
        ArrayList<Integer> nodesToInsert = new ArrayList<>();

        if (ant.tours.isEmpty() || ant.tours.get(ant.tours.size() - 1).size() > 2) { // It the last route is not empty (does not contains only depot as origin and destination)
            AntUtils.addEmptyVehicle(ant); // Add a new vehicle that can be used as option to insert the request.
        }

        if (vehicle != -1) { // If the current request is already associated with a given vehicle
            // For the current node, if it is the delivery node, we need to find the last pickup point to manage
            // the precedence constraint. In case of pickup it is the depot and in case of delivery it is the last pickup point.
            tour = ant.tours.get(vehicle);
            int prevPosition = 0;
            if (nodeReq.isDeliver) {
                prevPosition = instance.getLastPickupPosition(tour, nodeReq.requestId);
            }
            // Try to insert the current node in the associated vehicle
            bestPosition = insertionOperator.insertAtBestPosition(nextNode, tour, InsertionMethod.Greedy, prevPosition);
            if (bestPosition != null) {
                // A feasible insertion was found, return from this method to continue the construction process
                reqVehicles[nodeReq.requestId] = vehicle;
                tour.add(bestPosition.position, nodeReq.nodeId);
                return;
            } else {
                // When the current route can't be used, we need to remove all nodes of the current request of the current
                // vehicle and try the insertion of these nodes in different vehicles
                int i = 0;
                while (i < tour.size()) {
                    int node = tour.get(i);
                    if (node != instance.depot.nodeId && instance.requests[tour.get(i) - 1].requestId == nodeReq.requestId) {
                        nodesToInsert.add(tour.get(i));
                        tour.remove(i);
                    } else {
                        i++;
                    }
                }
                AntUtils.removeRequest(instance, ant, vehicle, nodeReq.requestId);
            }
        }
        // Here a new vehicle is choose to receive the current node and the already received nodes associated to the same request.
        // When we try to re-insert nodes for the current request we keep the order from the previous vehicle. Therefore,
        // the new node to be inserted will hold the last list position.
        nodesToInsert.add(nodeReq.nodeId);
        Double bestCost = Double.MAX_VALUE;
        ArrayList<Integer> route = null;
        Request delivery = null;
        int node;
        int selectedVehicle = -1;

        vehicleFor:
        for (int k = 0; k < ant.tours.size(); k++) { // For each vehicle
            if (k != vehicle) { // We ignore the previous vehicle as it was not possible to add the node before.
                tour = new ArrayList<>(ant.tours.get(k)); // Copy the route to not modify the original
                for (int i = 0; i < nodesToInsert.size(); i++) {
                    node = nodesToInsert.get(i);
                    // If the current node is pickup tyr to insert it on the route
                    if (instance.requests[node - 1].isPickup) {
                        bestPosition = insertionOperator.insertAtBestPosition(node, tour, InsertionMethod.Greedy, 0);
                        if (bestPosition == null) {
                            continue vehicleFor; // Go to next vehicle
                        } else {
                            tour.add(bestPosition.position, node); // Add the node on the route
                        }
                    } else {
                        delivery = instance.requests[node - 1];
                    }
                }
                if (delivery != null) { // Delivery nodes are precessed in last
                    bestPosition = insertionOperator.insertAtBestPosition(delivery.nodeId, tour, InsertionMethod.Greedy, instance.getLastPickupPosition(tour, nodeReq.requestId));
                    if (bestPosition == null) {
                        continue vehicleFor;
                    } else {
                        tour.add(bestPosition.position, delivery.nodeId);
                    }
                }
                // Update with the best route
                double currentCost = instance.costEvaluation(ant.tours.get(k));
                double newCost = instance.costEvaluation(tour);
                if (newCost - currentCost < bestCost) {
                    bestCost = newCost - currentCost;
                    selectedVehicle = k;
                    route = tour;
                }
            }
        }
        // In last case, if the heuristic could not create a new route, then use the exact method for this request
        if (route == null) {
            OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(nodeReq.requestId, instance);
            optimalRequestSolver.optimize();
            selectedVehicle = ant.tours.size() - 1;
            route = ant.tours.get(selectedVehicle);
            route.clear();
            for (int i = 0; i < optimalRequestSolver.getBestRoute().length; i++) {
                // The nodes of the optimal solution to be added must consider the nodesToInsert. Remember that the pheromone
                // will be used to selected the next available nodes in future iterations.
                node = optimalRequestSolver.getBestRoute()[i];
                if (node == instance.depot.nodeId || nodesToInsert.contains(node)) {
                    route.add(node);
                }
            }
        }

        ant.tours.set(selectedVehicle, route);
        ant.requests.get(selectedVehicle).add(nodeReq.requestId);
        reqVehicles[nodeReq.requestId] = selectedVehicle;
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
