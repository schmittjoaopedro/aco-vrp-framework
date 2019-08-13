package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class ShawRemoval extends RemovalOperator {

    private Instance instance;

    private Random random;

    private double shawRandomDegree = 6;

    private double[] relateWeight = {9, 3, 2, 5};

    public ShawRemoval(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    @Override
    public void remove(Solution solution, int q) {
        ArrayList<Integer> allRequest = new ArrayList<>();
        ArrayList<Integer> removeList = new ArrayList<>();
        RouteTimes routeTimes = calculateRouteTimes(solution);
        ArrayList<Double>[] relate = new ArrayList[instance.numRequests];
        for (int i = 0; i < instance.numRequests; ++i) {
            Request reqA = instance.requests[i];
            if (solution.visited[reqA.pickupTask.nodeId]) {
                allRequest.add(i);
                relate[i] = new ArrayList<>(instance.numRequests);
                for (int j = 0; j < instance.numRequests; ++j) {
                    relate[i].add(0.0);
                    Request reqB = instance.requests[j];
                    if (solution.visited[instance.requests[j].pickupTask.nodeId]) {
                        relate[i].set(j, relatedness(solution, routeTimes, reqA, reqB));
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
            y = Math.pow(y, shawRandomDegree);
            int toRemove = (int) (y * (double) allRequest.size());
            int removePos = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePos = -heap.peek().getRight();
                heap.poll();
            }
            removeList.add(allRequest.get(removePos));
            allRequest.remove(removePos);
        }
        solution.remove(removeList, instance);
    }

    protected RouteTimes calculateRouteTimes(Solution solution) {
        RouteTimes routeTimes = new RouteTimes();
        routeTimes.visitedTime = new double[instance.numNodes];
        routeTimes.startTime = new double[instance.numNodes];
        routeTimes.waitTime = new double[instance.numNodes];
        routeTimes.visitedTime[0] = 0;
        for (int r = 0; r < solution.tours.size(); r++) {
            double time = 0;
            int prev, curr;
            for (int j = 1; j < solution.tours.get(r).size() - 1; ++j) {
                prev = solution.tours.get(r).get(j - 1);
                curr = solution.tours.get(r).get(j);
                time += instance.dist(prev, curr) / instance.vehicleSpeed + instance.serviceTime(prev);
                routeTimes.visitedTime[curr] = time;
                routeTimes.startTime[curr] = Math.max(time, instance.twStart(curr));
                time = routeTimes.startTime[curr];
                routeTimes.waitTime[curr] = Math.max(0.0, time - routeTimes.visitedTime[curr]);
                solution.maxTime = Math.max(solution.maxTime, time);
            }
        }
        return routeTimes;
    }

    protected double relatedness(Solution solution, RouteTimes routeTimes, Request reqA, Request reqB) {
        int rAP = reqA.pickupTask.nodeId;
        int rAD = reqA.deliveryTask.nodeId;
        int rBP = reqB.pickupTask.nodeId;
        int rBD = reqB.deliveryTask.nodeId;
        double ret = relateWeight[0] * (instance.dist(rAP, rBP) + instance.dist(rAD, rBD)) / instance.maxDistance;
        ret += relateWeight[1] * (Math.abs(routeTimes.visitedTime[rAP] - routeTimes.visitedTime[rBP]) + Math.abs(routeTimes.visitedTime[rAD] - routeTimes.visitedTime[rBD])) / solution.maxTime;
        ret += relateWeight[2] * Math.abs(reqA.pickupTask.demand - reqB.pickupTask.demand) / instance.maxDemand;
        return ret;
    }

    protected class RouteTimes {

        protected double[] visitedTime;

        protected double[] startTime;

        protected double[] waitTime;

    }
}
