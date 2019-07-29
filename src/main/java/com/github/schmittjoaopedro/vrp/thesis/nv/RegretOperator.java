package com.github.schmittjoaopedro.vrp.thesis.nv;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class RegretOperator {

    private double ep = 0.00000001;

    double noiseControl = 0.025;

    private Random random;

    private Instance instance;

    public RegretOperator(Random random, Instance instance) {
        this.random = random;
        this.instance = instance;
    }

    public void regretInsert(Solution solution, int k, int useNoise) {
        ArrayList<Double> C[] = new ArrayList[instance.numNodes];
        ArrayList<Boolean> calculated[] = new ArrayList[instance.numNodes];
        ArrayList<Pair<Integer, Integer>> pos[] = new ArrayList[instance.numNodes];
        ArrayList<Double> startTime = new ArrayList<>();
        ArrayList<Double> waitingTime = new ArrayList<>();
        ArrayList<Double> maxDelay = new ArrayList<>();
        // Initialize request/vehicle structures
        for (int i = 1; i < instance.numNodes; ++i) {
            if (!solution.visited[i] && (instance.demand(i) > 0)) {
                C[i] = new ArrayList<>();
                calculated[i] = new ArrayList<>(solution.tours.size());
                pos[i] = new ArrayList<>(solution.tours.size());
                for (int j = 0; j < solution.tours.size(); j++) {
                    C[i].add(0.0);
                    calculated[i].add(false);
                    pos[i].add(Pair.of(0, 0));
                }
            }
        }
        for (int j = 0; j < solution.tours.size(); ++j) {
            calcMaxDelay(solution.tours.get(j), startTime, waitingTime, maxDelay);
            for (int i = 1; i < instance.numTasks; ++i) {
                if (!solution.visited[i] && instance.demand(i) > 0) {
                    Pair<Double, Pair<Integer, Integer>> ret = insertingCost(solution.tours.get(j), i, pos[i].get(j), startTime, waitingTime, maxDelay);
                    C[i].set(j, ret.getLeft()); // Calculate the cost to insert request i on vehicle j
                    pos[i].set(j, ret.getRight()); // Hold the insertion position of request i on vehicle j
                }
            }
        }
        while (true) {
            // Choose Node and Route to insert
            int minPossible = k;
            double maxRegret = 0;
            int insertRequest = 0;
            int insertRoute = 0;
            double insertCost = Double.MAX_VALUE;
            Pair<Integer, Integer> insertPos = null;
            for (int i = 1; i < instance.numNodes; ++i) {
                if (!solution.visited[i] && instance.demand(i) > 0) { // For each pickup not visited
                    Queue<Pair<Double, Integer>> heap = new PriorityQueue<>(); // Create a heap to hold the best vehicle to insert the request
                    for (int j = 0; j < solution.tours.size(); ++j) {
                        double cost = C[i].get(j);
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
                    int kRoute;
                    for (int z = 1; z < k; ++z) // Obtain the k-position to calculate regret cost
                        if (!heap.isEmpty()) {
                            kCost = heap.peek().getLeft();
                            kRoute = heap.peek().getRight();
                            if (kCost < Double.MAX_VALUE) ++possibleRoute;
                            heap.poll();
                        }
                    double regretCost = kCost - minCost;
                    if (possibleRoute < minPossible // Prioritize minimum vehicles number
                            || (possibleRoute == minPossible && (regretCost > maxRegret // If the number of vehicles is the same and the regret cost is greater
                            || (regretCost == maxRegret && minCost < insertCost)))) { // If the greedy strategy (same regret costs, but is a better insertion cost)

                        minPossible = possibleRoute;
                        maxRegret = regretCost;
                        insertRequest = i;
                        insertRoute = minRoute;
                        insertPos = pos[i].get(minRoute);
                        insertCost = minCost;
                    }
                }
            }
            if (insertCost == Double.MAX_VALUE) return;
            // Insert node and recalculate Insert Cost

            insert(solution, instance, insertRequest, insertRoute, insertPos);
            calcMaxDelay(solution.tours.get(insertRoute), startTime, waitingTime, maxDelay); // Update inserted vehicle
            for (int i = 1; i < instance.numNodes; ++i)
                if (!solution.visited[i] && (instance.demand(i) > 0) && C[i].get(insertRoute) < Double.MAX_VALUE) {
                    Pair<Double, Pair<Integer, Integer>> ret = insertingCost(solution.tours.get(insertRoute), i, pos[i].get(insertRoute), startTime, waitingTime, maxDelay);
                    C[i].set(insertRoute, ret.getLeft());
                    pos[i].set(insertRoute, ret.getRight());
                }
        }
    }

    // Add noise factor
    double generateNoise() {
        return (random.nextDouble() - 0.5) * (noiseControl * instance.maxDistance) * 2;
    }

    // Insert 2 nodes to route at pos
    void insert(Solution solution, Instance instance, int node, int route, Pair<Integer, Integer> pos) {
        solution.tours.get(route).add(solution.tours.get(route).get(0) + pos.getLeft(), node);
        int deliveryNode = instance.deliveryTasks[instance.getTask(node).requestId].nodeId;
        solution.tours.get(route).add(solution.tours.get(route).get(0) + pos.getRight(), deliveryNode);
        solution.visited[node] = true;
        solution.visited[deliveryNode] = true;
    }

    // Calculate max delay for 1 route
    void calcMaxDelay(ArrayList<Integer> route, ArrayList<Double> startTime, ArrayList<Double> waitingTime, ArrayList<Double> maxDelay) {
        startTime.clear();
        waitingTime.clear();
        maxDelay.clear();
        for (int i = 0; i < route.size(); i++) {
            startTime.add(0.0);
            waitingTime.add(0.0);
            maxDelay.add(0.0);
        }
        double time = 0;
        for (int j = 1; j < route.size(); ++j) {
            time += instance.dist(route.get(j), route.get(j - 1)) / instance.vehicleSpeed + instance.serviceTime(route.get(j - 1));
            double visitedTime = time;
            time = Math.max(time, instance.twStart(route.get(j)));
            startTime.set(j, time);
            waitingTime.set(j, Math.max(0., time - visitedTime));
            maxDelay.set(j, 0.0);
        }
        int p = route.get(route.size() - 1);
        maxDelay.set(route.size() - 1, instance.twEnd(p) - startTime.get(route.size() - 1));
        for (int i = route.size() - 2; i > 0; --i) {
            p = route.get(i);
            maxDelay.set(i, Math.min(maxDelay.get(i + 1) + waitingTime.get(i + 1),
                    instance.twEnd(p) - startTime.get(i)));
        }
    }

    // Calculate min cost to insert a pickup node to route
    Pair<Double, Pair<Integer, Integer>> insertingCost(ArrayList<Integer> route, int node, Pair<Integer, Integer> pos, ArrayList<Double> startTime, ArrayList<Double> waitingTime, ArrayList<Double> maxDelay) {
        double minRouteCost = Double.MAX_VALUE;
        double totalAmount = 0;
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        ArrayList<Double> newStartTime = new ArrayList<>();
        ArrayList<Double> newWaitingTime = new ArrayList<>();
        ArrayList<Double> newMaxDelay = new ArrayList<>();
        newRoute.add(newRoute.get(0), node);
        for (int i = 1; i < route.size(); ++i) {
            swap(newRoute, i, i - 1);
            double cost = instance.dist(node, route.get(i - 1)) + instance.dist(node,route.get(i)) - instance.dist(route.get(i), route.get(i - 1));
            double arrivalTime = startTime.get(i - 1) + instance.dist(node,route.get(i - 1)) + instance.serviceTime(route.get(i - 1));
            double waitTime = Math.max(0.0, instance.twStart(node) - arrivalTime);
            double delay = cost + waitTime + instance.serviceTime(node);
            if (cost <= minRouteCost) {
                if (totalAmount + instance.demand(node) <= instance.vehiclesCapacity) {
                    if (arrivalTime <= instance.twEnd(node)) {
                        if (delay <= waitingTime.get(i) + maxDelay.get(i) + ep) {
                            Pair<Integer, Integer> tmpPos = Pair.of(0, 0);
                            calcMaxDelay(newRoute, newStartTime, newWaitingTime, newMaxDelay);
                            int deliveryNode = instance.deliveryTasks[instance.getTask(node).requestId].nodeId;
                            Pair<Double, Pair<Integer, Integer>> ret = insertingCost(newRoute, deliveryNode, tmpPos, i + 1, totalAmount + instance.demand(node), newStartTime, newWaitingTime, newMaxDelay);
                            tmpPos = ret.getRight();
                            if (cost + ret.getLeft() < minRouteCost) {
                                minRouteCost = cost + ret.getLeft();
                                pos = Pair.of(i, tmpPos.getLeft());
                            }
                        }
                    } else break;
                }
            }
            totalAmount += instance.demand(route.get(i));
            if (totalAmount > instance.vehiclesCapacity) break;
        }
        return Pair.of(minRouteCost, pos);
    }

    // Calculate min cost to insert a delivery node to route
    Pair<Double, Pair<Integer, Integer>> insertingCost(ArrayList<Integer> route, int node, Pair<Integer, Integer> pos, int startPos, double totalAmount, ArrayList<Double> startTime, ArrayList<Double> waitingTime, ArrayList<Double> maxDelay) {
        double minRouteCost = Double.MAX_VALUE;
        for (int i = startPos; i < route.size(); ++i) {
            double cost = instance.dist(node,route.get(i - 1)) + instance.dist(node,route.get(i)) - instance.dist(route.get(i),route.get(i - 1));
            double arrivalTime = startTime.get(i - 1) + instance.dist(node,route.get(i - 1)) + instance.serviceTime(route.get(i - 1));
            double waitTime = Math.max(0., instance.twStart(node) - arrivalTime);
            double delay = cost + waitTime + instance.serviceTime(node);
            if (cost <= minRouteCost) {
                if (totalAmount + instance.demand(node) <= instance.vehiclesCapacity) {
                    if (arrivalTime <= instance.twEnd(node)) {
                        if (delay <= waitingTime.get(i) + maxDelay.get(i) + ep) {
                            if (cost < minRouteCost) {
                                minRouteCost = cost;
                                pos = Pair.of(i, 0);
                            }
                        }
                    } else break;
                }
            }
            totalAmount += instance.demand(route.get(i));
            if (totalAmount > instance.vehiclesCapacity) break;
        }
        return Pair.of(minRouteCost, pos);
    }

    private void swap(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }
}
