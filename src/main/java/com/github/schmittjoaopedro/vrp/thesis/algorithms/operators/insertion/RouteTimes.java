package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;

import java.util.ArrayList;

public class RouteTimes {

    public double[] arrivalTime;

    public double[] startTime;

    public double[] waitingTime;

    public double[] slackTime;

    public double[] departureTime;

    public RouteTimes(ArrayList<Integer> route, Instance instance) {
        int size = route.size();
        arrivalTime = new double[size];
        startTime = new double[size];
        waitingTime = new double[size];
        slackTime = new double[size];
        departureTime = new double[size];
        calculateRouteTimes(route, instance);
    }

    public RouteTimes(ArrayList<Integer> route, Instance instance, boolean[] ignoreRequests) {
        int size = route.size();
        arrivalTime = new double[size];
        startTime = new double[size];
        waitingTime = new double[size];
        slackTime = new double[size];
        departureTime = new double[size];
        calculateRouteTimes(route, instance, ignoreRequests);
    }

    private void calculateRouteTimes(ArrayList<Integer> route, Instance instance) {
        // Node times
        double visitedTime;
        double time = instance.lastIdleTime(route.get(1));
        // Depot times
        arrivalTime[0] = time;
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
            arrivalTime[j] = time;
            time = Math.max(time, instance.twStart(curr));
            startTime[j] = time;
            departureTime[j] = time + instance.serviceTime(curr);
            waitingTime[j] = Math.max(0., time - visitedTime);
        }
        // Slack times
        slackTime[route.size() - 1] = instance.twEnd(route.get(route.size() - 1)) - startTime[route.size() - 1];
        for (int i = route.size() - 2; i > 0; --i) {
            slackTime[i] = Math.min(slackTime[i + 1] + waitingTime[i + 1], instance.twEnd(route.get(i)) - startTime[i]);
        }
    }

    private void calculateRouteTimes(ArrayList<Integer> route, Instance instance, boolean[] ignoreRequests) {
        // Node times
        double visitedTime;
        double time = instance.lastIdleTime(route.get(1));
        // Depot times
        arrivalTime[0] = time;
        startTime[0] = time;
        departureTime[0] = time;
        waitingTime[0] = 0.0;
        slackTime[0] = 0.0;
        int prevNode = route.get(0);
        int currNode;
        for (int j = 1; j < route.size(); ++j) {
            currNode = route.get(j);
            while (!instance.isDepot(currNode) && ignoreRequests[instance.getTask(currNode).requestId]) {
                ++j;
                currNode = route.get(j);
            }
            time += instance.dist(prevNode, currNode) / instance.vehicleSpeed + instance.serviceTime(prevNode);
            visitedTime = time;
            arrivalTime[j] = time;
            time = Math.max(time, instance.twStart(currNode));
            startTime[j] = time;
            departureTime[j] = time + instance.serviceTime(currNode);
            waitingTime[j] = Math.max(0., time - visitedTime);
            prevNode = currNode;
        }
        // Slack times
        int currIdx = route.size() - 1;
        slackTime[currIdx] = instance.twEnd(route.get(currIdx)) - startTime[currIdx];
        int prevIdx = currIdx;
        while (currIdx > 0) {
            currIdx--;
            while (!instance.isDepot(route.get(currIdx)) && ignoreRequests[instance.getTask(route.get(currIdx)).requestId]) {
                currIdx--;
            }
            if (currIdx > 0) {
                slackTime[currIdx] = Math.min(slackTime[prevIdx] + waitingTime[prevIdx], instance.twEnd(route.get(currIdx)) - startTime[currIdx]);
                prevIdx = currIdx;
            }
        }
    }
}
