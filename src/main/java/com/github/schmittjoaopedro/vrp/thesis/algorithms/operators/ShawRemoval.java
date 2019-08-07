package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class ShawRemoval {

    private Instance instance;

    private Random random;

    private double shawRandomDegree = 6;

    public ShawRemoval(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    // Remove Customer base on relatedness
    public void remove(Solution solution, int q) {
        ArrayList<Integer> allRequest = new ArrayList<>();
        ArrayList<Integer> removeList = new ArrayList<>();
        for (int i = 0; i < solution.tours.size(); ++i) {
            solution.findVisitedTime(instance, i);
        }
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
                        relate[i].set(j, solution.relatedness(instance, reqA, reqB));
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
}
