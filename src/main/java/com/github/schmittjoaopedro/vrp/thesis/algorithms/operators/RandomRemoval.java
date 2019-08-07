package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators;

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
    public void remove(Solution s, int q) {
        List<Integer> removeList = new ArrayList<>();
        for (Task pickupTask : instance.pickupTasks) {
            if (s.visited[pickupTask.nodeId]) removeList.add(pickupTask.requestId);
        }
        Collections.shuffle(removeList, random);
        if (removeList.size() > q) removeList = removeList.subList(0, q);
        s.remove(removeList, instance);
    }
}
