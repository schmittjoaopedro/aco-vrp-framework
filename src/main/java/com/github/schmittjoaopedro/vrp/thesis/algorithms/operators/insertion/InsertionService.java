package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;

import java.util.ArrayList;

public class InsertionService {

    private double ep = 0.00000001;

    private Instance instance;

    public InsertionService(Instance instance) {
        this.instance = instance;
    }

    public InsertPosition calculateBestPosition(ArrayList<Integer> route, Request request, RouteTimes routeTimes) {
        InsertPosition insertPosition = new InsertPosition();
        double cost, arrivalTime, waitTime, delay, totalAmount = 0;
        int prevNode, currNode, pickupNode = request.pickupTask.nodeId;
        boolean isValidCapacity, isValidTimeWindow, isValidSlackTime;
        ArrayList<Integer> newRoute = new ArrayList<>(route);
        newRoute.add(newRoute.get(0), pickupNode);
        RouteTimes newRouteTimes = new RouteTimes(newRoute.size());
        for (int i = 1; i < route.size(); ++i) { // Ignore depot
            swapPositions(newRoute, i, i - 1); // Advance current pickup one position
            prevNode = route.get(i - 1);
            currNode = route.get(i);
            cost = instance.dist(prevNode, pickupNode) + instance.dist(pickupNode, currNode) - instance.dist(prevNode, currNode);
            arrivalTime = routeTimes.startTime[i - 1] + instance.dist(prevNode, pickupNode) + instance.serviceTime(prevNode);
            waitTime = Math.max(0.0, instance.twStart(pickupNode) - arrivalTime);
            delay = cost + waitTime + instance.serviceTime(pickupNode);
            if (cost <= insertPosition.cost) {
                isValidCapacity = totalAmount + instance.demand(pickupNode) <= instance.vehiclesCapacity;
                if (isValidCapacity) {
                    isValidTimeWindow = arrivalTime <= instance.twEnd(pickupNode);
                    if (!isValidTimeWindow) {
                        break;
                    }
                    isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                    if (isValidSlackTime) {
                        calculateRouteTimes(newRoute, newRouteTimes);
                        InsertPosition deliveryPosition = calculateBestDeliveryPosition(newRoute, request.deliveryTask.nodeId, i + 1, totalAmount + instance.demand(pickupNode), newRouteTimes);
                        if (cost + deliveryPosition.cost < insertPosition.cost) {
                            insertPosition.cost = cost + deliveryPosition.cost;
                            insertPosition.pickupPos = i;
                            insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                        }
                    }
                }
            }
            totalAmount += instance.demand(route.get(i));
            if (totalAmount > instance.vehiclesCapacity) {
                break;
            }
        }
        return insertPosition;
    }

    private InsertPosition calculateBestDeliveryPosition(ArrayList<Integer> newRoute, int node, int startPos, double totalAmount, RouteTimes routeTimes) {
        double cost, arrivalTime, waitTime, delay;
        int prevNode, currNode;
        boolean isValidCapacity, isValidTimeWindow, isValidSlackTime;
        InsertPosition insertPosition = new InsertPosition();
        for (int i = startPos; i < newRoute.size(); ++i) {
            prevNode = newRoute.get(i - 1);
            currNode = newRoute.get(i);
            cost = instance.dist(prevNode, node) + instance.dist(node, currNode) - instance.dist(prevNode, currNode);
            arrivalTime = routeTimes.startTime[i - 1] + instance.serviceTime(prevNode) + instance.dist(prevNode, node);
            waitTime = Math.max(0., instance.twStart(node) - arrivalTime);
            delay = cost + waitTime + instance.serviceTime(node);
            if (cost <= insertPosition.cost) {
                isValidCapacity = totalAmount + instance.demand(node) <= instance.vehiclesCapacity;
                if (isValidCapacity) {
                    isValidTimeWindow = arrivalTime <= instance.twEnd(node);
                    if (!isValidTimeWindow) {
                        break;
                    }
                    isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                    if (isValidSlackTime && cost < insertPosition.cost) {
                        insertPosition.cost = cost;
                        insertPosition.deliveryPos = i;
                    }
                }
            }
            totalAmount += instance.demand(newRoute.get(i));
            if (totalAmount > instance.vehiclesCapacity) {
                break;
            }
        }
        return insertPosition;
    }

    public void calculateRouteTimes(ArrayList<Integer> route, RouteTimes routeTimes) {
        // Depot times
        routeTimes.startTime[0] = 0.0;
        routeTimes.waitingTime[0] = 0.0;
        routeTimes.slackTime[0] = 0.0;
        // Node times
        double visitedTime, time = 0;
        int prev, curr;
        for (int j = 1; j < route.size(); ++j) {
            prev = route.get(j - 1);
            curr = route.get(j);
            time += instance.dist(prev, curr) / instance.vehicleSpeed + instance.serviceTime(prev);
            visitedTime = time;
            time = Math.max(time, instance.twStart(curr));
            routeTimes.startTime[j] = time;
            routeTimes.waitingTime[j] = Math.max(0., time - visitedTime);
            routeTimes.slackTime[0] = 0.0;
        }
        // Slack times
        routeTimes.slackTime[route.size() - 1] = instance.twEnd(route.get(route.size() - 1)) - routeTimes.startTime[route.size() - 1];
        for (int i = route.size() - 2; i > 0; --i) {
            routeTimes.slackTime[i] = Math.min(routeTimes.slackTime[i + 1] + routeTimes.waitingTime[i + 1], instance.twEnd(route.get(i)) - routeTimes.startTime[i]);
        }
    }

    private void swapPositions(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }

}
