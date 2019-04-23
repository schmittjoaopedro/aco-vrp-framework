package com.github.schmittjoaopedro.vrp.pdptw_lns;

import java.util.ArrayList;

public class Instance {

    int initialVehicleNumber;
    double vehicleCapacity;
    double vehicleSpeed;
    double maxAmount;
    double maxDist;
    int vehicleNumber;
    int requestNumber;
    Request[] requestList;
    double[][] distances;
    Double[] objWeight = {1.0, 0.0, 100000.0};
    double[] relateWeight = {9, 3, 2, 5};

    // Calculate cost of 1 route
    double calcRouteCost(ArrayList<Integer> route) {
        double sumDist = 0, sumTime = 0;
        double currentCapacity = 0;
        for (int i = 1; i < route.size(); ++i) {
            currentCapacity += requestList[route.get(i)].amount;
            if (currentCapacity > vehicleCapacity) return Double.MAX_VALUE;
            sumDist += distances[route.get(i)][route.get(i - 1)];
            sumTime = sumTime + distances[route.get(i)][route.get(i - 1)] / vehicleSpeed;
            sumTime = Math.max(sumTime, requestList[route.get(i)].lowerTime);
            if (sumTime > requestList[route.get(i)].upperTime) {
                return Double.MAX_VALUE;
            }
            sumTime += requestList[route.get(i)].serviceTime;
        }
        return objWeight[0] * sumDist + objWeight[1] * sumTime;
    }
}
