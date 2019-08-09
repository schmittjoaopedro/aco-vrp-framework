package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RegretInsertion extends InsertionOperator {

    private double ep = 0.00000001;

    double noiseControl = 0.025;

    private Random random;

    private Instance instance;

    private RegretValue regretValue;

    public RegretInsertion(Random random, Instance instance, RegretValue regretValue) {
        this.random = random;
        this.instance = instance;
        this.regretValue = regretValue;
    }

    @Override
    public void insert(Solution solution, int useNoise) {
        Task pickupTask;
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
            calculateRouteTimes(solution.tours.get(k), routeTimes);
            for (int i = 0; i < insertionCosts.length; i++) {
                pickupTask = instance.pickupTasks[i];
                if (solution.visited[pickupTask.nodeId] == false) {
                    insertionCosts[i][k] = insertingPickupCost(solution.tours.get(k), pickupTask, routeTimes);
                }
            }
        }

        // Choose Node and Route to insert
        while (true) {
            int minPossible = regretK;
            double maxRegret = 0;
            int insertRequest = 0;
            int insertVehicle = 0;
            double insertCost = Double.MAX_VALUE;
            InsertPosition insertPos = null;
            for (int i = 0; i < insertionCosts.length; i++) {
                pickupTask = instance.pickupTasks[i];
                if (solution.visited[pickupTask.nodeId] == false) {
                    Queue<Pair<Double, Integer>> heap = new PriorityQueue<>(); // Create a heap to hold the best vehicle to insert the request
                    for (int j = 0; j < insertionCosts[i].length; j++) {
                        double cost = insertionCosts[i][j].cost;
                        if (cost < Double.MAX_VALUE) {
                            cost += useNoise * generateNoise(); // Generate noise to increase diversity
                        }
                        heap.add(Pair.of(cost, j));
                    }
                    double minCost = heap.peek().getLeft();
                    if (minCost == Double.MAX_VALUE) continue;
                    int possibleRoute = 0;
                    if (minCost < Double.MAX_VALUE) ++possibleRoute;
                    int minRoute = heap.peek().getRight();
                    heap.poll();
                    double kCost = minCost;
                    for (int z = 1; z < regretK; ++z) { // Obtain the k-position to calculate regret cost
                        if (!heap.isEmpty()) {
                            kCost = heap.peek().getLeft();
                            if (kCost < Double.MAX_VALUE) ++possibleRoute;
                            heap.poll();
                        }
                    }
                    double regretCost = kCost - minCost;
                    if (possibleRoute < minPossible // Prioritize minimum vehicles number
                            || (possibleRoute == minPossible && (regretCost > maxRegret // If the number of vehicles is the same and the regret cost is greater
                            || (regretCost == maxRegret && minCost < insertCost)))) { // If the greedy strategy (same regret costs, but is a better insertion cost)
                        minPossible = possibleRoute;
                        maxRegret = regretCost;
                        insertRequest = pickupTask.requestId;
                        insertVehicle = minRoute;
                        insertPos = insertionCosts[i][minRoute];
                        insertCost = minCost;
                    }
                }
            }
            if (insertCost == Double.MAX_VALUE) return;
            // Insert node and recalculate Insert Cost
            solution.insert(instance, insertRequest, insertVehicle, insertPos.pickupPos, insertPos.deliveryPos);
            routeTimes = new RouteTimes(solution.tours.get(insertVehicle).size());
            calculateRouteTimes(solution.tours.get(insertVehicle), routeTimes); // Update inserted vehicle
            for (int i = 0; i < insertionCosts.length; i++) {
                pickupTask = instance.pickupTasks[i];
                if (solution.visited[pickupTask.nodeId] == false && insertionCosts[i][insertVehicle].cost < Double.MAX_VALUE) {
                    insertionCosts[i][insertVehicle] = insertingPickupCost(solution.tours.get(insertVehicle), pickupTask, routeTimes);
                }
            }
        }
    }

    private double generateNoise() {
        return (random.nextDouble() - 0.5) * noiseControl * instance.maxDistance * 2;
    }

    private void calculateRouteTimes(ArrayList<Integer> route, RouteTimes routeTimes) {
        // Depot times
        routeTimes.startTime[0] = 0.0;
        routeTimes.waitingTime[0] = 0.0;
        routeTimes.slackTime[0] = 0.0;
        // Node times
        double time = 0;
        for (int j = 1; j < route.size(); ++j) {
            int prev = route.get(j - 1);
            int curr = route.get(j);
            time += instance.dist(prev, curr) / instance.vehicleSpeed + instance.serviceTime(prev);
            double visitedTime = time;
            time = Math.max(time, instance.twStart(curr));
            routeTimes.startTime[j] = time;
            routeTimes.waitingTime[j] = Math.max(0., time - visitedTime);
            routeTimes.slackTime[0] = 0.0;
        }
        int p = route.get(route.size() - 1);
        routeTimes.slackTime[route.size() - 1] = instance.twEnd(p) - routeTimes.startTime[route.size() - 1];
        for (int i = route.size() - 2; i > 0; --i) {
            p = route.get(i);
            routeTimes.slackTime[i] = Math.min(routeTimes.slackTime[i + 1] + routeTimes.waitingTime[i + 1], instance.twEnd(p) - routeTimes.startTime[i]);
        }
    }

    // Calculate min cost to insert a pickup node to route
    protected InsertPosition insertingPickupCost(ArrayList<Integer> route, Task pickupTask, RouteTimes routeTimes) {
        InsertPosition insertPosition = new InsertPosition();
        double minRouteCost = Double.MAX_VALUE;
        insertPosition.cost = minRouteCost;
        double totalAmount = 0;
        int pickupNode = pickupTask.nodeId;
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        newRoute.add(newRoute.get(0), pickupNode);
        RouteTimes newRouteTimes = new RouteTimes(newRoute.size());
        for (int i = 1; i < route.size(); ++i) { // Ignore depot
            swapPositions(newRoute, i, i - 1); // Advance current pickup one position
            int prevNode = route.get(i - 1);
            int currNode = route.get(i);
            double cost = instance.dist(prevNode, pickupNode) + instance.dist(pickupNode, currNode) - instance.dist(prevNode, currNode);
            double arrivalTime = routeTimes.startTime[i - 1] + instance.dist(prevNode, pickupNode) + instance.serviceTime(prevNode);
            double waitTime = Math.max(0.0, instance.twStart(pickupNode) - arrivalTime);
            double delay = cost + waitTime + instance.serviceTime(pickupNode);
            if (cost <= minRouteCost) {
                boolean isValidCapacity = totalAmount + instance.demand(pickupNode) <= instance.vehiclesCapacity;
                if (isValidCapacity) {
                    boolean isValidTimeWindow = arrivalTime <= instance.twEnd(pickupNode);
                    if (!isValidTimeWindow) {
                        break;
                    }
                    boolean isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                    if (isValidSlackTime) {
                        calculateRouteTimes(newRoute, newRouteTimes);
                        int deliveryNode = instance.deliveryTasks[instance.getTask(pickupNode).requestId].nodeId;
                        InsertPosition deliveryPosition = insertingDeliveryCost(newRoute, deliveryNode, i + 1, totalAmount + instance.demand(pickupNode), newRouteTimes);
                        if (cost + deliveryPosition.cost < minRouteCost) {
                            minRouteCost = cost + deliveryPosition.cost;
                            insertPosition.cost = minRouteCost;
                            insertPosition.pickupPos = i;
                            insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                        }
                    }
                }
            }
            totalAmount += instance.demand(route.get(i));
            if (totalAmount > instance.vehiclesCapacity) {
                break;
            }
        }
        return insertPosition;
    }

    // Calculate min cost to insert a delivery node to route
    private InsertPosition insertingDeliveryCost(ArrayList<Integer> route, int node, int startPos, double totalAmount, RouteTimes routeTimes) {
        InsertPosition insertPosition = new InsertPosition();
        double minRouteCost = Double.MAX_VALUE;
        insertPosition.cost = minRouteCost;
        for (int i = startPos; i < route.size(); ++i) {
            double cost = instance.dist(node, route.get(i - 1)) + instance.dist(node, route.get(i)) - instance.dist(route.get(i), route.get(i - 1));
            double arrivalTime = routeTimes.startTime[i - 1] + instance.dist(node, route.get(i - 1)) + instance.serviceTime(route.get(i - 1));
            double waitTime = Math.max(0., instance.twStart(node) - arrivalTime);
            double delay = cost + waitTime + instance.serviceTime(node);
            if (cost <= minRouteCost) {
                boolean isValidCapacity = totalAmount + instance.demand(node) <= instance.vehiclesCapacity;
                if (isValidCapacity) {
                    boolean isValidTimeWindow = arrivalTime <= instance.twEnd(node);
                    if (!isValidTimeWindow) {
                        break;
                    }
                    boolean isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                    if (isValidSlackTime && cost < minRouteCost) {
                        minRouteCost = cost;
                        insertPosition.cost = minRouteCost;
                        insertPosition.deliveryPos = i;
                    }
                }
            }
            totalAmount += instance.demand(route.get(i));
            if (totalAmount > instance.vehiclesCapacity) break;
        }
        return insertPosition;
    }

    private void swapPositions(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }

    @FunctionalInterface
    public interface RegretValue {

        int getK(Solution solution, Instance instance);

    }

    protected class InsertPosition {

        protected double cost = Double.MAX_VALUE;

        protected int pickupPos = 0;

        protected int deliveryPos = 0;

    }

    protected class RouteTimes {

        public RouteTimes(int size) {
            startTime = new double[size];
            waitingTime = new double[size];
            slackTime = new double[size];
        }

        protected double[] startTime;

        protected double[] waitingTime;

        protected double[] slackTime;

    }

}
