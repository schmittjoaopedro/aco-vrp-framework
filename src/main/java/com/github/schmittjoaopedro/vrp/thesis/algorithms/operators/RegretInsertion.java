package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

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
            int minPossibleVehicles = regretK;
            double maxRegretCost = 0;
            int insertRequestId = 0;
            int insertRoute = 0;
            double insertCost = Double.MAX_VALUE;
            InsertPosition insertPos = null;
            for (int r = 0; r < insertionCosts.length; r++) {
                pickupTask = instance.pickupTasks[r];
                if (solution.visited[pickupTask.nodeId] == false) {
                    Queue<InsertVehicle> heap = new PriorityQueue<>(); // Create a heap to hold the best vehicle to insert the request
                    for (int k = 0; k < insertionCosts[r].length; k++) {
                        double cost = insertionCosts[r][k].cost;
                        if (cost < Double.MAX_VALUE) {
                            cost += useNoise * generateNoise(); // Generate noise to increase diversity
                        }
                        heap.add(new InsertVehicle(cost, k));
                    }
                    InsertVehicle insertionVehicle = heap.peek();
                    if (insertionVehicle.cost == Double.MAX_VALUE) continue;
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
            calculateRouteTimes(solution.tours.get(insertRoute), routeTimes); // Update the vehicle costs
            for (int i = 0; i < insertionCosts.length; i++) {
                pickupTask = instance.pickupTasks[i];
                if (solution.visited[pickupTask.nodeId] == false && insertionCosts[i][insertRoute].cost < Double.MAX_VALUE) {
                    insertionCosts[i][insertRoute] = insertingPickupCost(solution.tours.get(insertRoute), pickupTask, routeTimes);
                }
            }
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
        // Slack times
        int p = route.get(route.size() - 1);
        routeTimes.slackTime[route.size() - 1] = instance.twEnd(p) - routeTimes.startTime[route.size() - 1];
        for (int i = route.size() - 2; i > 0; --i) {
            p = route.get(i);
            routeTimes.slackTime[i] = Math.min(routeTimes.slackTime[i + 1] + routeTimes.waitingTime[i + 1], instance.twEnd(p) - routeTimes.startTime[i]);
        }
    }

    private double generateNoise() {
        return (random.nextDouble() - 0.5) * noiseControl * instance.maxDistance * 2;
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

    protected class RouteTimes {

        protected double[] startTime;

        protected double[] waitingTime;

        protected double[] slackTime;

        public RouteTimes(int size) {
            startTime = new double[size];
            waitingTime = new double[size];
            slackTime = new double[size];
        }

    }

}
