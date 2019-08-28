package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RegretInsertion extends InsertionOperator {

    private Instance instance;

    double noiseControl = 0.025;

    private InsertionService insertionService;

    private RegretValue regretValue;

    private Random random;

    public RegretInsertion(Random random, Instance instance, RegretValue regretValue) {
        this.random = random;
        this.instance = instance;
        this.regretValue = regretValue;
        this.insertionService = new InsertionService(instance);
    }

    @Override
    public void insert(Solution solution, int useNoise) {
        Task pickupTask;
        Request request;
        RouteTimes routeTimes;
        int regretK = regretValue.getK(solution, instance);
        InsertPosition[][] insertionCosts = new InsertPosition[instance.numRequests][solution.tours.size()]; // Insertion of Request x Vehicles costs
        // Initialize request/vehicle structure
        for (int r = 0; r < insertionCosts.length; r++) { // requests
            for (int k = 0; k < insertionCosts[r].length; k++) { // vehicles
                insertionCosts[r][k] = new InsertPosition();
            }
        }
        // Calculate insertion cost of each request x vehicle
        for (int k = 0; k < solution.tours.size(); ++k) {
            routeTimes = new RouteTimes(solution.tours.get(k).size());
            insertionService.calculateRouteTimes(solution.tours.get(k), routeTimes);
            for (int r = 0; r < insertionCosts.length; r++) {
                request = instance.requests[r];
                if (solution.removedRequests[request.requestId]) {
                    insertionCosts[r][k] = insertionService.calculateBestPosition(solution.tours.get(k), request, routeTimes);
                }
            }
        }
        // Choose node and route to insert
        while (true) {
            int minPossibleVehicles = regretK;
            double maxRegretCost = 0;
            int insertRequestId = 0;
            int insertRoute = 0;
            double insertCost = Double.MAX_VALUE;
            InsertPosition insertPos = null;
            for (int r = 0; r < insertionCosts.length; r++) {
                pickupTask = instance.pickupTasks[r];
                if (solution.removedRequests[r]) {
                    Queue<InsertVehicle> heap = new PriorityQueue<>(); // Create a heap to hold the best vehicle to insert the request
                    for (int k = 0; k < insertionCosts[r].length; k++) {
                        double cost = insertionCosts[r][k].cost;
                        if (cost < Double.MAX_VALUE) {
                            cost += useNoise * generateNoise(); // Generate noise to increase diversity
                        }
                        heap.add(new InsertVehicle(cost, k));
                    }
                    InsertVehicle insertionVehicle = heap.peek();
                    if (insertionVehicle == null || insertionVehicle.cost == Double.MAX_VALUE) continue;
                    int curPossibleVehicles = 0;
                    if (insertionVehicle.cost < Double.MAX_VALUE) ++curPossibleVehicles;
                    int minRoute = insertionVehicle.routeIndex;
                    heap.poll();
                    double kCost = insertionVehicle.cost;
                    for (int z = 1; z < regretK; ++z) { // Obtain the k-position to calculate regret cost
                        if (!heap.isEmpty()) {
                            kCost = heap.peek().cost;
                            if (kCost < Double.MAX_VALUE) ++curPossibleVehicles;
                            heap.poll();
                        }
                    }
                    double regretCost = kCost - insertionVehicle.cost;
                    boolean isLessVehiclesNumber = curPossibleVehicles < minPossibleVehicles;
                    boolean isSameVehiclesNumber = curPossibleVehicles == minPossibleVehicles;
                    boolean isGreaterRegret = regretCost > maxRegretCost;
                    boolean isGreedyInsertion = regretCost == maxRegretCost && insertionVehicle.cost < insertCost;
                    if (isLessVehiclesNumber || (isSameVehiclesNumber && (isGreaterRegret || isGreedyInsertion))) {
                        minPossibleVehicles = curPossibleVehicles;
                        maxRegretCost = regretCost;
                        insertRequestId = pickupTask.requestId;
                        insertRoute = minRoute;
                        insertPos = insertionCosts[r][minRoute];
                        insertCost = insertionVehicle.cost;
                    }
                }
            }
            if (insertCost == Double.MAX_VALUE) return;
            // Insert node and recalculate Insert Cost
            solution.insert(instance, insertRequestId, insertRoute, insertPos.pickupPos, insertPos.deliveryPos);
            routeTimes = new RouteTimes(solution.tours.get(insertRoute).size());
            insertionService.calculateRouteTimes(solution.tours.get(insertRoute), routeTimes); // Update the vehicle costs
            for (int r = 0; r < insertionCosts.length; r++) {
                request = instance.requests[r];
                if (solution.removedRequests[r] && insertionCosts[r][insertRoute].cost < Double.MAX_VALUE) {
                    insertionCosts[r][insertRoute] = insertionService.calculateBestPosition(solution.tours.get(insertRoute), request, routeTimes);
                }
            }
        }
    }

    private double generateNoise() {
        return (random.nextDouble() - 0.5) * noiseControl * instance.maxDistance * 2;
    }

    @FunctionalInterface
    public interface RegretValue {

        int getK(Solution solution, Instance instance);

    }

    protected class InsertVehicle implements Comparable<InsertVehicle> {

        protected double cost;

        protected int routeIndex;

        public InsertVehicle(double cost, int routeIndex) {
            this.cost = cost;
            this.routeIndex = routeIndex;
        }

        @Override
        public int compareTo(InsertVehicle other) {
            return Double.compare(cost, other.cost);
        }
    }

}
