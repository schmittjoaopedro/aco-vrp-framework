package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
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
    double worstRandomDegree = 3;
    double ShawRandomDegree = 6;
    double removeControl = 0.4;
    int maxIteration = 25000;
    int improvementLimit = 2000;
    double[] scoreWeight = {33, 9, 13};
    double[] relateWeight = {9, 3, 2, 5};
    double reactionFactor = 0.1;

    double[] removalWeight;
    double[] insertingWeight;
    double[] noiseWeight;
    double[] removalScore;
    double[] insertingScore;
    double[] noiseScore;
    int[] removalCount;
    int[] insertingCount;
    int[] noiseCount;
    Set<BigInteger> visitedList = new HashSet<>();

    String problem;
    List<String> logs = new ArrayList<>();

    public Solver(String directory, String file, int seed) {
        problem = file;
        random = new Random(seed);
        instance = DataReader.getInput(directory, file);
    }

    public void run() {
        start = System.currentTimeMillis();
        Pair<Integer, Double> minSol = Pair.of(1000, Double.MAX_VALUE);
        Pair<Integer, Double> sumSol = Pair.of(0, 0.0);
        log("Executing " + problem);
        for (int i = 0; i < 1; i++) {
            log("Test run #" + i);
            log("Stage 1 : Minimize number of vehicles used");
            tempControl = 0.35;
            coolingRate = 0.9999;
            long _start = System.currentTimeMillis();
            stage1Iteration = 0;
            instance.vehicleNumber = instance.initialVehicleNumber;
            resetAdaptiveWeight();
            visitedList.clear();

            Solution s = new Solution(instance);
            int minVehicle = 0;
            regretInsert(s, 1, 0);

            instance.vehicleNumber = s.usedVehicle(instance);
            s.resize(instance.vehicleNumber);
            Solution sTemp = s.copy(instance);
            while (s.requestBankSize(instance) == 0) {
                minVehicle = s.usedVehicle(instance);
                log("Min Vehicle User : " + minVehicle + " Best Solution: " + s.objFunction(instance));
                instance.vehicleNumber = minVehicle - 1;
                s.resize(instance.vehicleNumber);
                s = LNS(s, 1);
                if (s.requestBankSize(instance) == 0) sTemp = s.copy(instance);
            }
            instance.vehicleNumber = minVehicle;
            log("Stage 2 : Minimize the cost");
            tempControl = 0.05;
            coolingRate = 0.99975;
            resetAdaptiveWeight();
            s = LNS(sTemp, 2);
            minVehicle = s.usedVehicle(instance);
            double bestSol = s.objFunction(instance);
            log("Min Vehicle User : " + minVehicle + " Best Solution: " + bestSol);
            log("Time " + ((System.currentTimeMillis() - _start) / 1000.0));
            if (minSol.compareTo(Pair.of(minVehicle, bestSol)) > 0) {
                minSol = Pair.of(minVehicle, bestSol);
            }
            sumSol = Pair.of(sumSol.getLeft() + minVehicle, sumSol.getRight() + bestSol);
            int v = 1;
            String finalRoute = "";
            for (ArrayList<Integer> route : s.vehicleRoute) {
                finalRoute += "Vehicle " + v++ + ": ";
                for (int node : route) {
                    finalRoute += node + " ";
                }
                finalRoute += "\n";
            }
            log(finalRoute);
        }
        System.out.println("--------------------\n\n");
    }

    private void log(String msg) {
        System.out.println(msg);
        logs.add(msg);
    }

    // Large neighborhood search with initial solution s;
    Solution LNS(Solution s, int stage) {
        Solution sBest = s.copy(instance);
        double temp = calcTemp(s.tempObjFunction(instance));
        long nbIteration = 0;
        if (stage == 1) nbIteration = stage1Iteration;
        int lastImprovement = 0;
        do {
            if (stage == 1) ++stage1Iteration;
            int q;
            do {
                q = (int) (random.nextDouble() * Math.min(100, (int) (removeControl * (instance.requestNumber / 2))));
            } while (q < 4);
            ++nbIteration;
            ++lastImprovement;
            Solution sNew = s.copy(instance);
            if (sNew.requestBankSize(instance) < sBest.requestBankSize(instance)) lastImprovement = 0;
            int useNoise = rouletteSelection(noiseWeight);

            // Remove and insert
            int removeHeuristic = requestRemove(sNew, q, removalWeight, worstRandomDegree, ShawRandomDegree);
            int insertHeuristic = requestInsert(sNew, q, insertingWeight, useNoise);

            // Calculate Score for each heuristic
            ++removalCount[removeHeuristic];
            ++insertingCount[insertHeuristic];
            ++noiseCount[useNoise];
            if (accept(sNew, s, temp)) {
                BigInteger hashNb = sNew.getHash(instance);
                if (!visitedList.contains(hashNb)) {
                    visitedList.add(hashNb);
                    if (sNew.objFunction(instance) < sBest.objFunction(instance)) {
                        sBest = sNew;
                        removalScore[removeHeuristic] += scoreWeight[0];
                        insertingScore[insertHeuristic] += scoreWeight[0];
                        noiseScore[useNoise] += scoreWeight[0];
                    } else if (sNew.objFunction(instance) < s.objFunction(instance)) {
                        removalScore[removeHeuristic] += scoreWeight[1];
                        insertingScore[insertHeuristic] += scoreWeight[1];
                        noiseScore[useNoise] += scoreWeight[1];
                    } else {
                        removalScore[removeHeuristic] += scoreWeight[2];
                        insertingScore[insertHeuristic] += scoreWeight[2];
                        noiseScore[useNoise] += scoreWeight[2];
                    }
                }
                s = sNew;
            }
            temp = temp * coolingRate;
            if (nbIteration % 100 == 0) {
                //printWeights(temp, sBest);
                updateAdaptiveWeight(removalWeight, removalScore, removalCount, reactionFactor);
                updateAdaptiveWeight(insertingWeight, insertingScore, insertingCount, reactionFactor);
                updateAdaptiveWeight(noiseWeight, noiseScore, noiseCount, reactionFactor);
            }


            // Stop Stage 1 if no improvement
            if (stage == 1) {
                if (sBest.requestBankSize(instance) == 0) return sBest;
                if (sBest.requestBankSize(instance) >= 10 && lastImprovement > improvementLimit) return sBest;
            }
        } while (nbIteration < maxIteration);
        return sBest;
    }

    void printWeights(double temp, Solution sBest) {
        System.out.println("Removal = [" + StringUtils.join(getArray(removalWeight),',') +
                "] Repair = [" + StringUtils.join(getArray(insertingWeight),',') + "]" +
                " T = " + temp +
                " Hash = " + visitedList.size() +
                " NV = " + sBest.usedVehicle(instance) + " TC = " + sBest.objFunction(instance));
    }

    private String[] getArray(double[] array) {
        String data[] = new String[array.length];
        double sum = 0.0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        for (int i = 0; i < array.length; i++) {
            data[i] = String.valueOf((int) (100 * array[i] / sum));
        }
        return data;
    }

    // Remove requests from solution and return used heuristic
    int requestRemove(Solution s, int q, double[] removalWeight, double worstRandomDegree, double ShawRandomDegree) {
        int removeHeuristic = rouletteSelection(removalWeight);
        if (removeHeuristic == 0) randomRemoval(s, q);
        else if (removeHeuristic == 1) worstRemoval(s, q, worstRandomDegree);
        else ShawRemoval(s, q, ShawRandomDegree);
        return removeHeuristic;
    }

    // Remove a number of random Customer
    void randomRemoval(Solution s, int q) {
        List<Integer> removeList = new ArrayList<>();
        for (int i = 1; i < instance.requestNumber; ++i) {
            if (s.visited[i] && instance.requestList[i].amount > 0) removeList.add(i);
        }
        Collections.shuffle(removeList, random);
        if (removeList.size() > q) removeList = removeList.subList(0, q);
        s.remove(removeList, instance);
    }

    // Remove Customer base on Cost
    void worstRemoval(Solution s, int q, double worstRandomDegree) {
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            s.findRoute(i);
        }
        // Calculates the cost difference for each route for each request removed
        double[] C = new double[instance.requestNumber];
        for (int i = 1; i < instance.requestNumber; ++i) {
            if (s.visited[i] && instance.requestList[i].amount > 0) {
                int removeRoute = s.route[i].getLeft();
                Pair<Integer, Integer> pos = Pair.of(s.route[i].getRight(), s.route[instance.requestList[i].delivery].getRight());
                C[i] = removeCost(pos, s.vehicleRoute[removeRoute]);
            }
        }
        while (q > 0) { // While there are requests to remove
            // Create a heap to hold requests with expensive costs in front
            Queue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 1; i < instance.requestNumber; ++i) {
                if (s.visited[i] && instance.requestList[i].amount > 0) {
                    heap.add(Pair.of(-C[i], -i));
                }
            }
            if (heap.isEmpty()) break;
            double y = random.nextDouble();
            y = Math.pow(y, worstRandomDegree);
            int toRemove = (int) (y * (double) heap.size());
            int removeRequest = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removeRequest = -heap.peek().getRight();
                heap.poll();
            }
            int removeRoute = s.route[removeRequest].getLeft();
            int pickupNode = s.route[removeRequest].getRight();
            int deliveryNode = s.route[instance.requestList[removeRequest].delivery].getRight();
            s.visited[removeRequest] = false;
            s.visited[instance.requestList[removeRequest].delivery] = false;
            s.vehicleRoute[removeRoute].remove(deliveryNode);
            s.vehicleRoute[removeRoute].remove(pickupNode);
            --q;
            s.findRoute(removeRoute);
            for (int i = 1; i < s.vehicleRoute[removeRoute].size() - 1; ++i) {
                int p = s.vehicleRoute[removeRoute].get(i);
                if (instance.requestList[p].amount > 0) {
                    Pair<Integer, Integer> pos = Pair.of(s.route[p].getRight(), s.route[instance.requestList[p].delivery].getRight());
                    C[i] = removeCost(pos, s.vehicleRoute[removeRoute]);
                }
            }
        }
    }

    // Remove Customer base on relateness
    void ShawRemoval(Solution s, int q, double ShawRandomDegree) {
        ArrayList<Integer> allRequest = new ArrayList<>();
        ArrayList<Integer> removeList = new ArrayList<>();
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            s.findVisitedTime(instance, i);
        }
        ArrayList<Double>[] relate = new ArrayList[instance.requestNumber];
        for (int i = 0; i < instance.requestNumber; ++i) {
            if (s.visited[i] && (instance.requestList[i].amount > 0)) {
                allRequest.add(i);
                relate[i] = new ArrayList<>(instance.requestNumber);
                for (int j = 0; j < instance.requestNumber; ++j) {
                    relate[i].add(0.0);
                    if (s.visited[j] && (instance.requestList[j].amount > 0)) {
                        relate[i].set(j, s.relatedness(instance, i, j));
                    }
                }
            }
        }
        int r = (int) (random.nextDouble() * (double) allRequest.size());

        removeList.add(allRequest.get(r));
        allRequest.remove(r);
        while (removeList.size() < q && allRequest.size() > 0) {
            r = (int) (random.nextDouble() % (double) removeList.size());
            PriorityQueue<Pair<Double, Integer>> heap = new PriorityQueue<>();
            for (int i = 0; i < allRequest.size(); ++i) {
                heap.add(Pair.of(relate[allRequest.get(i)].get(removeList.get(r)), -i));
            }
            double y = random.nextDouble();
            y = Math.pow(y, ShawRandomDegree);
            int toRemove = (int) (y * (double) allRequest.size());
            int removePos = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePos = -heap.peek().getRight();
                heap.poll();
            }
            removeList.add(allRequest.get(removePos));
            allRequest.remove(removePos);
        }
        s.remove(removeList, instance);
    }

    // Calculate cost to remove customer from route
    double removeCost(Pair<Integer, Integer> pos, ArrayList<Integer> route) {
        double oldCost = instance.calcRouteCost(route);
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        int delPos = pos.getRight();
        newRoute.remove(delPos);
        delPos = pos.getLeft();
        newRoute.remove(delPos);
        double cost = instance.calcRouteCost(newRoute);
        return oldCost - cost;
    }

    // Insert unvisited requests from solution and return used heuristic
    int requestInsert(Solution s, int q, double[] insertingWeight, int useNoise) {
        int insertHeuristic = rouletteSelection(insertingWeight);
        if (insertHeuristic == 0) regretInsert(s, 1, useNoise); // Basic Greedy insert
        else if (insertHeuristic == 1) regretInsert(s, 2, useNoise);
        else if (insertHeuristic == 2) regretInsert(s, 3, useNoise);
        else if (insertHeuristic == 3) regretInsert(s, 4, useNoise);
        else regretInsert(s, s.requestBankSize(instance) / 2, useNoise); // Regret-m insert
        return insertHeuristic;
    }

    // Check if the new Solution is Accepted
    boolean accept(Solution sNew, Solution s, double temp) {
        double obj = s.objFunction(instance);
        double objNew = sNew.objFunction(instance);
        if (objNew <= obj) return true;
        double probability = Math.exp((obj - objNew) / temp);
        return (random.nextDouble() <= probability);
    }

    // Update Weights of each heuristic after a segment
    void updateAdaptiveWeight(double[] weight, double[] score, int[] nb, double reactionFactor) {
        for (int i = 0; i < weight.length; ++i) {
            weight[i] = weight[i] * (1 - reactionFactor) + reactionFactor * (score[i] / Math.max(nb[i], 1));
            score[i] = 0;
            nb[i] = 0;
        }
    }

    // Select a number using Roulette Wheel Selection
    int rouletteSelection(double[] weight) {
        double[] sumWeight = new double[weight.length];
        double randomNumber = random.nextDouble();
        sumWeight[0] = weight[0];
        for (int i = 1; i < weight.length; ++i) {
            sumWeight[i] = sumWeight[i - 1] + weight[i];
        }
        randomNumber = randomNumber * sumWeight[weight.length - 1];
        for (int i = 0; i < weight.length; ++i) {
            if (randomNumber < sumWeight[i]) return i;
        }
        return weight.length - 1;
    }

    // Calculate Starting Temperature
    double calcTemp(double objValue) {
        return objValue * tempControl / Math.log(2);
    }

    void regretInsert(Solution s, int k, int useNoise) {
        ArrayList<Double> C[] = new ArrayList[instance.requestNumber];
        ArrayList<Boolean> calculated[] = new ArrayList[instance.requestNumber];
        ArrayList<Pair<Integer, Integer>> pos[] = new ArrayList[instance.requestNumber];
        ArrayList<Double> startTime = new ArrayList<>();
        ArrayList<Double> waitingTime = new ArrayList<>();
        ArrayList<Double> maxDelay = new ArrayList<>();
        // Initialize request/vehicle structures
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
            for (int i = 1; i < instance.requestNumber; ++i) {
                if (!s.visited[i] && instance.requestList[i].amount > 0) { // For each pickup not visited
                    Queue<Pair<Double, Integer>> heap = new PriorityQueue<>(); // Create a heap to hold the best vehicle to insert the request
                    for (int j = 0; j < instance.vehicleNumber; ++j) {
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

            s.insert(instance, insertRequest, insertRoute, insertPos);
            calcMaxDelay(s.vehicleRoute[insertRoute], startTime, waitingTime, maxDelay); // Update inserted vehicle
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
        return (random.nextDouble() - 0.5) * (noiseControl * instance.maxDist) * 2;
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

    public List<String> getLogs() {
        return logs;
    }
}
