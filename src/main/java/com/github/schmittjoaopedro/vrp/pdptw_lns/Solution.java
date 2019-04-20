package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

public class Solution {

    ArrayList<Integer>[] vehicleRoute;
    boolean visited[];

    public Solution(Instance instance) {
        visited = new boolean[instance.requestNumber];
        vehicleRoute = new ArrayList[instance.vehicleNumber];
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
}
