package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

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
        ArrayList<Integer> attendedRequests = new ArrayList<>(instance.numRequests);
        ArrayList<Integer> removeRequests = new ArrayList<>(q);
        RouteTimes routeTimes = calculateRouteTimes(solution);
        double[][] requestsRelatedness = new double[instance.numRequests][instance.numRequests]; // R x R relatedness matrix
        for (int i = 0; i < instance.numRequests; ++i) {
            Request reqA = instance.requests[i];
            if (solution.visited[reqA.pickupTask.nodeId]) {
                attendedRequests.add(i);
                for (int j = 0; j < instance.numRequests; ++j) {
                    requestsRelatedness[i][j] = 0.0;
                    Request reqB = instance.requests[j];
                    if (solution.visited[instance.requests[j].pickupTask.nodeId]) {
                        requestsRelatedness[i][j] = relatedness(solution, routeTimes, reqA, reqB);
                    }
                }
            }
        }
        int r = (int) (random.nextDouble() * (double) attendedRequests.size());
        removeRequests.add(attendedRequests.get(r));
        attendedRequests.remove(r); // Remove at position r
        while (removeRequests.size() < q && attendedRequests.size() > 0) {
            r = (int) (random.nextDouble() % (double) removeRequests.size());
            PriorityQueue<RemovalRequest> heap = new PriorityQueue<>();
            for (int i = 0; i < attendedRequests.size(); ++i) {
                heap.add(new RemovalRequest(requestsRelatedness[attendedRequests.get(i)][removeRequests.get(r)], -i));
            }
            double y = random.nextDouble();
            y = Math.pow(y, shawRandomDegree);
            int toRemove = (int) (y * (double) attendedRequests.size());
            int removePos = -1;
            for (int i = 0; i <= toRemove; ++i) {
                removePos = -heap.peek().position;
                heap.poll();
            }
            removeRequests.add(attendedRequests.get(removePos));
            attendedRequests.remove(removePos);
        }
        solution.remove(removeRequests, instance);
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
                time = Math.max(time, instance.twStart(curr));
                routeTimes.startTime[curr] = time;
                routeTimes.waitTime[curr] = Math.max(0.0, time - routeTimes.visitedTime[curr]);
                solution.maxTime = Math.max(solution.maxTime, time);
            }
        }
        return routeTimes;
    }

    protected double relatedness(Solution solution, RouteTimes routeTimes, Request reqA, Request reqB) {
        return relateWeight[0] * (instance.dist(reqA.pickupTask.nodeId, reqB.pickupTask.nodeId) + instance.dist(reqA.deliveryTask.nodeId, reqB.deliveryTask.nodeId)) / instance.maxDistance
                + relateWeight[1] * (Math.abs(routeTimes.visitedTime[reqA.pickupTask.nodeId] - routeTimes.visitedTime[reqB.pickupTask.nodeId]) + Math.abs(routeTimes.visitedTime[reqA.deliveryTask.nodeId] - routeTimes.visitedTime[reqB.deliveryTask.nodeId])) / solution.maxTime
                + relateWeight[2] * Math.abs(reqA.pickupTask.demand - reqB.pickupTask.demand) / instance.maxDemand;
    }

    protected class RouteTimes {

        protected double[] visitedTime;

        protected double[] startTime;

        protected double[] waitTime;

    }

    protected class RemovalRequest implements Comparable<RemovalRequest> {

        protected double cost;

        private int position;

        public RemovalRequest(double cost, int position) {
            this.cost = cost;
            this.position = position;
        }

        @Override
        public int compareTo(RemovalRequest request) {
            int compare = Double.compare(cost, request.cost);
            if (compare == 0) {
                compare = Integer.compare(position, request.position);
            }
            return compare;
        }
    }
}
