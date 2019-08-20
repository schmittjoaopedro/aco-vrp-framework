package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.removal;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.RemovalOperator;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomRemoval extends RemovalOperator {

    private Instance instance;

    private Random random;

    public RandomRemoval(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    @Override
    public void remove(Solution solution, int q) {
        List<Integer> requestIds = new ArrayList<>();
        for (Task pickupTask : instance.pickupTasks) {
            if (solution.visited[pickupTask.nodeId]) {
                requestIds.add(pickupTask.requestId);
            }
        }
        Collections.shuffle(requestIds, random);
        if (requestIds.size() > q) requestIds = requestIds.subList(0, q);
        solution.remove(requestIds, instance);
    }
}
