package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution {

    ArrayList<Integer>[] vehicleRoute;
    Pair<Integer, Integer>[] route;
    boolean visited[];
    double[] visitedTime, startTime, waitingTime, routeAmount;
    double maxTime = 0.0;
    BigInteger hash;

    public Solution(Instance instance) {
        vehicleRoute = new ArrayList[instance.vehicleNumber];
        visited = new boolean[instance.requestNumber];
        visitedTime = new double[instance.requestNumber];
        startTime = new double[instance.requestNumber];
        waitingTime = new double[instance.requestNumber];
        route = new Pair[instance.requestNumber];
        routeAmount = new double[instance.vehicleNumber];
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            vehicleRoute[i] = new ArrayList<>();
            vehicleRoute[i].add(0);
            vehicleRoute[i].add(0);

        }
        visited[0] = true;
    }

    Solution copy(Instance instance) {
        Solution solution = new Solution(instance);
        solution.visited = visited.clone();
        solution.vehicleRoute = new ArrayList[vehicleRoute.length];
        solution.route = route.clone();
        solution.visitedTime = visitedTime.clone();
        solution.startTime = startTime.clone();
        solution.waitingTime = waitingTime.clone();
        solution.routeAmount = routeAmount.clone();
        solution.maxTime = maxTime;
        for (int i = 0; i < vehicleRoute.length; i++) {
            solution.vehicleRoute[i] = new ArrayList<>(vehicleRoute[i]);
        }
        return solution;
    }

    // Insert 2 nodes to route at pos
    void insert(Instance instance, int node, int route, Pair<Integer, Integer> pos) {
        vehicleRoute[route].add(vehicleRoute[route].get(0) + pos.getLeft(), node);
        vehicleRoute[route].add(vehicleRoute[route].get(0) + pos.getRight(), instance.requestList[node].delivery);
        visited[node] = true;
        visited[instance.requestList[node].delivery] = true;
    }

    // Number of used vehicle
    int usedVehicle(Instance instance) {
        int ret = 0;
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            if (vehicleRoute[i].size() > 2) ++ret;
        }
        return ret;
    }

    // Remove vehicles from solution
    void resize(int k) {
        for (int i = k; i < vehicleRoute.length; ++i) {
            for (int j = 1; j < vehicleRoute[i].size() - 1; ++j) {
                visited[vehicleRoute[i].get(j)] = false;
            }
        }
        ArrayList<Integer>[] newRoute = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            newRoute[i] = vehicleRoute[i];
        }
        vehicleRoute = newRoute;
    }

    // Number of unvisited customers
    int requestBankSize(Instance instance) {
        int ret = 0;
        for (int i = 0; i < instance.requestNumber; ++i)
            if (!visited[i]) ++ret;
        return ret;
    }

    // Total Objective value of the solution
    double objFunction(Instance instance) {
        double ret = 0;
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            ret += instance.calcRouteCost(vehicleRoute[i]);
        }
        for (int i = 1; i < instance.requestNumber; ++i) {
            if (!visited[i]) ret += instance.objWeight[2];
        }
        return ret;
    }

    // Calculate Obj Value to calculate starting temperature
    double tempObjFunction(Instance instance) {
        double ret = 0;
        ArrayList<Double> finishTime = new ArrayList<>();
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            ret += instance.calcRouteCost(vehicleRoute[i]);
        }
        return ret;
    }

    int getHash() {
        ArrayList<ArrayList<Integer>> clonedRoutes = new ArrayList();
        for (int i = 0; i < vehicleRoute.length; i++) {
            clonedRoutes.add(new ArrayList<>(this.vehicleRoute[i]));
        }
        clonedRoutes.sort(Comparator.<ArrayList<Integer>, Integer>comparing(a -> a.size()).thenComparing(b -> b.get(1)));
        return StringUtils.join(clonedRoutes).hashCode();
    }

    // Remove all node from removeList to request bank
    void remove(List<Integer> removeList, Instance instance) {
        for (int i = 0; i < removeList.size(); ++i) {
            int pickupNode = removeList.get(i);
            int deliveryNode = instance.requestList[removeList.get(i)].delivery;
            visited[pickupNode] = false;
            visited[deliveryNode] = false;
        }
        for (int i = 0; i < instance.vehicleNumber; ++i) {
            ArrayList<Integer> newRoute = new ArrayList<>();
            for (int j = 0; j < vehicleRoute[i].size(); ++j) {
                if (visited[vehicleRoute[i].get(j)]) {
                    newRoute.add(vehicleRoute[i].get(j));
                }
            }
            vehicleRoute[i] = newRoute;
        }
    }

    // Find position of node i in solution
    void findRoute(int i) {
        for (int j = 1; j < vehicleRoute[i].size() - 1; ++j) {
            route[vehicleRoute[i].get(j)] = Pair.of(i, j);
        }
    }

    // Calculate relateness of 2 nodes in the solution
    double relatedness(Instance instance, int a, int b) {
        int u = instance.requestList[a].delivery;
        int v = instance.requestList[b].delivery;
        double ret = instance.relateWeight[0] * (instance.distances[a][b] + instance.distances[u][v]) / instance.maxDist;
        ret += instance.relateWeight[1] * (Math.abs(visitedTime[a] - visitedTime[b]) + Math.abs(visitedTime[u] - visitedTime[v])) / maxTime;
        ret += instance.relateWeight[2] * Math.abs(instance.requestList[a].amount - instance.requestList[b].amount) / instance.maxAmount;
        return ret;
    }

    // Calculate time visiting each node
    void findVisitedTime(Instance instance, int r) {
        double time = 0;
        int startPos = 1;
        visitedTime[0] = 0;
        routeAmount[r] = 0;
        for (int j = startPos; j < vehicleRoute[r].size() - 1; ++j) {
            time += instance.distances[vehicleRoute[r].get(j)][vehicleRoute[r].get(j - 1)] / instance.vehicleSpeed
                    + instance.requestList[vehicleRoute[r].get(j - 1)].serviceTime;
            visitedTime[vehicleRoute[r].get(j)] = time;
            startTime[vehicleRoute[r].get(j)] = Math.max(time, instance.requestList[vehicleRoute[r].get(j)].lowerTime);
            time = startTime[vehicleRoute[r].get(j)];
            waitingTime[vehicleRoute[r].get(j)] = Math.max(0., time - visitedTime[vehicleRoute[r].get(j)]);
            maxTime = Math.max(maxTime, time);
        }
    }
}
