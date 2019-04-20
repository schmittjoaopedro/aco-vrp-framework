package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Solver {

    Random random;

    Instance instance;

    Long start;

    double tempControl;

    double coolingRate;

    long stage1Iteration;

    double ep = 0.00000001;

    double noiseControl = 0.025;

    double[] removalWeight;
    double[] insertingWeight;
    double[] noiseWeight;
    double[] removalScore;
    double[] insertingScore;
    double[] noiseScore;
    int[] removalCount;
    int[] insertingCount;
    int[] noiseCount;
    Set<Long> visitedList = new HashSet<>();

    public Solver(String directory, String file, int seed) {
        random = new Random(seed);
        instance = DataReader.getInput(directory, file);
    }

    public void run() {
        start = System.currentTimeMillis();
        Map<Integer, Double> minSol = new HashMap<>();
        minSol.put(1000, Double.MAX_VALUE);
        Map<Integer, Double> sumSol = new HashMap<>();
        sumSol.put(0, 0.0);
        for (int i = 0; i < 10; i++) {
            System.out.println("Test run #" + i);
            System.out.println("Stage 1 : Minimize number of vehicles used");
            tempControl = 0.35;
            coolingRate = 0.9999;
            long _start = System.currentTimeMillis();
            stage1Iteration = 0;
            instance.vehicleNumber = instance.initialVehicleNumber;
            resetAdaptiveWeight();
            visitedList.clear();

            Solution s = new Solution(instance);
            int minVehicle;
            regretInsert(s, 1, 0);

            instance.vehicleNumber = s.usedVehicle(instance);
            s.resize(instance.vehicleNumber);
            Solution sTemp = s.copy(instance);
            while (s.requestBankSize(instance) == 0) {
                minVehicle = s.usedVehicle(instance);
                System.out.println("Min Vehicle User : " + minVehicle + " Best Solution: " + s.objFunction(instance));
            }
        }
    }

    void regretInsert(Solution s, int k, int useNoise) {
        ArrayList<Double> C[] = new ArrayList[instance.requestNumber];
        ArrayList<Boolean> calculated[] = new ArrayList[instance.requestNumber];
        ArrayList<Pair<Integer, Integer>> pos[] = new ArrayList[instance.requestNumber];
        ArrayList<Double> startTime = new ArrayList<>();
        ArrayList<Double> waitingTime = new ArrayList<>();
        ArrayList<Double> maxDelay = new ArrayList<>();
        for (int i = 1; i < instance.requestNumber; ++i) {
            if (!s.visited[i] && (instance.requestList[i].amount > 0)) {
                C[i] = new ArrayList<>();
                calculated[i] = new ArrayList<>(instance.vehicleNumber);
                pos[i] = new ArrayList<>(instance.vehicleNumber);
                for (int j = 0; j < instance.vehicleNumber; j++) {
                    C[i].add(0.0);
                    calculated[i].add(false);
                    pos[i].add(Pair.of(0, 0));
                }
            }
        }
        for (int j = 0; j < instance.vehicleNumber; ++j) {
            calcMaxDelay(s.vehicleRoute[j], startTime, waitingTime, maxDelay);
            for (int i = 1; i < instance.requestNumber; ++i) {
                if (!s.visited[i] && instance.requestList[i].amount > 0) {
                    Pair<Double, Pair<Integer, Integer>> ret = insertingCost(s.vehicleRoute[j], i, pos[i].get(j), startTime, waitingTime, maxDelay);
                    C[i].set(j, ret.getLeft());
                    pos[i].set(j, ret.getRight());
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
            for (int i = 1; i < instance.requestNumber; ++i) {
                if (!s.visited[i] && instance.requestList[i].amount > 0) {
                    Queue<Pair<Double, Integer>> heap = new PriorityQueue<>();
                    for (int j = 0; j < instance.vehicleNumber; ++j) {
                        double cost = C[i].get(j);
                        if (cost < Double.MAX_VALUE) {
                            cost += useNoise * generateNoise();
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
                    for (int z = 1; z < k; ++z)
                        if (!heap.isEmpty()) {
                            kCost = heap.peek().getLeft();
                            kRoute = heap.peek().getRight();
                            if (kCost < Double.MAX_VALUE) ++possibleRoute;
                            heap.poll();
                        }
                    double regretCost = kCost - minCost;
                    if (possibleRoute < minPossible
                            || (possibleRoute == minPossible && (regretCost > maxRegret
                            || (regretCost == maxRegret && minCost < insertCost)))) {

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

            s.insert(instance, insertRequest, insertRoute, insertPos);
            calcMaxDelay(s.vehicleRoute[insertRoute], startTime, waitingTime, maxDelay);
            for (int i = 1; i < instance.requestNumber; ++i)
                if (!s.visited[i] && (instance.requestList[i].amount > 0) && C[i].get(insertRoute) < Double.MAX_VALUE) {
                    Pair<Double, Pair<Integer, Integer>> ret = insertingCost(s.vehicleRoute[insertRoute], i, pos[i].get(insertRoute), startTime, waitingTime, maxDelay);
                    C[i].set(insertRoute, ret.getLeft());
                    pos[i].set(insertRoute, ret.getRight());
                }
        }
    }

    // Add noise factor
    double generateNoise() {
        return (Math.random() - 0.5) * (noiseControl * instance.maxDist) * 2;
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
            time += instance.distances[route.get(j)][route.get(j - 1)] / instance.vehicleSpeed + instance.requestList[route.get(j - 1)].serviceTime;
            double visitedTime = time;
            time = Math.max(time, instance.requestList[route.get(j)].lowerTime);
            startTime.set(j, time);
            waitingTime.set(j, Math.max(0., time - visitedTime));
            maxDelay.set(j, 0.0);
        }
        int p = route.get(route.size() - 1);
        maxDelay.set(route.size() - 1, instance.requestList[p].upperTime - startTime.get(route.size() - 1));
        for (int i = route.size() - 2; i > 0; --i) {
            p = route.get(i);
            maxDelay.set(i, Math.min(maxDelay.get(i + 1) + waitingTime.get(i + 1),
                    instance.requestList[p].upperTime - startTime.get(i)));
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
            double cost = instance.distances[node][route.get(i - 1)] + instance.distances[node][route.get(i)] - instance.distances[route.get(i)][route.get(i - 1)];
            double arrivalTime = startTime.get(i - 1) + instance.distances[node][route.get(i - 1)] + instance.requestList[route.get(i - 1)].serviceTime;
            double waitTime = Math.max(0.0, instance.requestList[node].lowerTime - arrivalTime);
            double delay = cost + waitTime + instance.requestList[node].serviceTime;
            if (cost <= minRouteCost) {
                if (totalAmount + instance.requestList[node].amount <= instance.vehicleCapacity) {
                    if (arrivalTime <= instance.requestList[node].upperTime) {
                        if (delay <= waitingTime.get(i) + maxDelay.get(i) + ep) {
                            Pair<Integer, Integer> tmpPos = Pair.of(0, 0);
                            calcMaxDelay(newRoute, newStartTime, newWaitingTime, newMaxDelay);
                            Pair<Double, Pair<Integer, Integer>> ret = insertingCost(newRoute, instance.requestList[node].delivery, tmpPos, i + 1, totalAmount + instance.requestList[node].amount, newStartTime, newWaitingTime, newMaxDelay);
                            tmpPos = ret.getRight();
                            if (cost + ret.getLeft() < minRouteCost) {
                                minRouteCost = cost + ret.getLeft();
                                pos = Pair.of(i, tmpPos.getLeft());
                            }
                        }
                    } else break;
                }
            }
            totalAmount += instance.requestList[route.get(i)].amount;
            if (totalAmount > instance.vehicleCapacity) break;
        }
        return Pair.of(minRouteCost, pos);
    }

    // Calculate min cost to insert a delivery node to route
    Pair<Double, Pair<Integer, Integer>> insertingCost(ArrayList<Integer> route, int node, Pair<Integer, Integer> pos, int startPos, double totalAmount, ArrayList<Double> startTime, ArrayList<Double> waitingTime, ArrayList<Double> maxDelay) {
        double minRouteCost = Double.MAX_VALUE;
        for (int i = startPos; i < route.size(); ++i) {
            double cost = instance.distances[node][route.get(i - 1)] + instance.distances[node][route.get(i)] - instance.distances[route.get(i)][route.get(i - 1)];
            double arrivalTime = startTime.get(i - 1) + instance.distances[node][route.get(i - 1)] + instance.requestList[route.get(i - 1)].serviceTime;
            double waitTime = Math.max(0., instance.requestList[node].lowerTime - arrivalTime);
            double delay = cost + waitTime + instance.requestList[node].serviceTime;
            if (cost <= minRouteCost) {
                if (totalAmount + instance.requestList[node].amount <= instance.vehicleCapacity) {
                    if (arrivalTime <= instance.requestList[node].upperTime) {
                        if (delay <= waitingTime.get(i) + maxDelay.get(i) + ep) {
                            if (cost < minRouteCost) {
                                minRouteCost = cost;
                                pos = Pair.of(i, 0);
                            }
                        }
                    } else break;
                }
            }
            totalAmount += instance.requestList[route.get(i)].amount;
            if (totalAmount > instance.vehicleCapacity) break;
        }
        return Pair.of(minRouteCost, pos);
    }

    private void swap(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }

    void resetAdaptiveWeight() {
        removalWeight = new double[]{1, 1, 1};
        insertingWeight = new double[]{1, 1, 1, 1, 1};
        noiseWeight = new double[]{1, 1};
        removalScore = new double[]{0, 0, 0};
        insertingScore = new double[]{0, 0, 0, 0, 0};
        noiseScore = new double[]{0, 0};
        removalCount = new int[]{0, 0, 0};
        insertingCount = new int[]{0, 0, 0, 0, 0};
        noiseCount = new int[]{0, 0};

    }

}
