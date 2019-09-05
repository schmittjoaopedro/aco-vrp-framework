package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;

import java.util.ArrayList;

public class RouteTimes {

    public double[] startTime;

    public double[] waitingTime;

    public double[] slackTime;

    public double[] departureTime;

    public RouteTimes(ArrayList<Integer> route, Instance instance) {
        int size = route.size();
        startTime = new double[size];
        waitingTime = new double[size];
        slackTime = new double[size];
        departureTime = new double[size];
        calculateRouteTimes(route, instance);
    }

    public void calculateRouteTimes(ArrayList<Integer> route, Instance instance) {
        // Node times
        double visitedTime;
        double time = instance.startVisitTime(route.get(1));
        // Depot times
        startTime[0] = time;
        departureTime[0] = time;
        waitingTime[0] = 0.0;
        slackTime[0] = 0.0;
        int prev, curr;
        for (int j = 1; j < route.size(); ++j) {
            prev = route.get(j - 1);
            curr = route.get(j);
            time += instance.dist(prev, curr) / instance.vehicleSpeed + instance.serviceTime(prev);
            visitedTime = time;
            time = Math.max(time, instance.twStart(curr));
            startTime[j] = time;
            departureTime[j] = time + instance.serviceTime(curr);
            waitingTime[j] = Math.max(0., time - visitedTime);
            slackTime[0] = 0.0;
        }
        // Slack times
        slackTime[route.size() - 1] = instance.twEnd(route.get(route.size() - 1)) - startTime[route.size() - 1];
        for (int i = route.size() - 2; i > 0; --i) {
            slackTime[i] = Math.min(slackTime[i + 1] + waitingTime[i + 1], instance.twEnd(route.get(i)) - startTime[i]);
        }
    }
}
