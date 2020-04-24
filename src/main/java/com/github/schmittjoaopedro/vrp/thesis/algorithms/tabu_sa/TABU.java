package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;

public class TABU {

    private int currentPosition;

    private EigenValue[] queue;

    public void init(Instance instance) {
        queue = new EigenValue[Math.max(30, instance.numRequests)];
        currentPosition = 0;
    }

    public boolean exist(EigenValue eigenValue) {
        for (int i = 0; i < currentPosition; i++) {
            if (eigenValue.isSame(queue[i])) {
                return true;
            }
        }
        return false;
    }

    public void offerAndPoll(EigenValue eigenValue) {
        if (exist(eigenValue)) {
            return;
        }
        if (currentPosition < queue.length) {
            queue[currentPosition] = eigenValue;
            currentPosition++;
        } else {
            for (int i = queue.length - 1; i > 0; i--) {
                queue[i - 1] = queue[i];
            }
            queue[queue.length - 1] = eigenValue;
        }
    }

}
