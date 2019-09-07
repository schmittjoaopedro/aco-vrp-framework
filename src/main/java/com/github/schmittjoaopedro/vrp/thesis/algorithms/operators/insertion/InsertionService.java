package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;

public class InsertionService {

    private double ep = 0.00000001;

    private Instance instance;

    public InsertionService(Instance instance) {
        this.instance = instance;
    }

    public InsertPosition calculateBestPosition(ArrayList<Integer> route, Request request, RouteTimes routeTimes) {
        InsertPosition insertPosition = new InsertPosition();
        if (request.pickupTask.isIdle()) {
            double cost, arrivalTime, waitTime, delay, totalAmount = 0;
            int prevNode, currNode, pickupNode = request.pickupTask.nodeId;
            boolean isValidCapacity, isValidTimeWindow, isValidSlackTime;
            ArrayList<Integer> newRoute = new ArrayList<>(route);
            newRoute.add(newRoute.get(0), pickupNode);
            for (int i = 1; i < route.size(); ++i) { // Ignore depot
                swapPositions(newRoute, i, i - 1); // Advance current pickup one position
                prevNode = route.get(i - 1);
                currNode = route.get(i);
                boolean isInTimeToVisit = instance.lastIdleTime(pickupNode) <= routeTimes.departureTime[i - 1]; // Already know request when we departure from previous node
                boolean isIdleNode = instance.isDepot(currNode) || instance.getTask(currNode).isIdle();
                if (isIdleNode && isInTimeToVisit) {
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
                                RouteTimes newRouteTimes = new RouteTimes(newRoute, instance);
                                InsertPosition deliveryPosition = calculateBestDeliveryPosition(newRoute, request.deliveryTask.nodeId, i + 1, totalAmount + instance.demand(pickupNode), newRouteTimes);
                                if (cost + deliveryPosition.cost < insertPosition.cost) {
                                    insertPosition.cost = cost + deliveryPosition.cost;
                                    insertPosition.pickupPos = i;
                                    insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                                }
                            }
                        }
                    }
                }
                totalAmount += instance.demand(route.get(i));
                if (totalAmount > instance.vehiclesCapacity) {
                    break;
                }
            }
        } else {
            int deliveryIdx = route.indexOf(request.deliveryTask.nodeId);
            if (deliveryIdx > 0 && request.deliveryTask.isIdle()) {
                Task task;
                int startNode = 1;
                int pickupIdx = route.indexOf(request.pickupTask.nodeId);
                ArrayList<Integer> newRoute = new ArrayList<>(route);
                double totalAmount = 0.0;
                for (int i = 1; i <= pickupIdx; i++) {
                    task = instance.getTask(route.get(i));
                    totalAmount += task.demand;
                }
                for (int i = pickupIdx + 1; i < route.size() - 1; i++) {
                    task = instance.getTask(route.get(i));
                    if (task.isIdle()) {
                        startNode = i;
                        break;
                    } else {
                        totalAmount += task.demand;
                    }
                }
                double cost = instance.dist(route.get(deliveryIdx - 1), route.get(deliveryIdx)) +
                        instance.dist(route.get(deliveryIdx), route.get(deliveryIdx + 1)) -
                        instance.dist(route.get(deliveryIdx - 1), route.get(deliveryIdx + 1));
                newRoute.remove(deliveryIdx);
                RouteTimes newRouteTimes = new RouteTimes(newRoute, instance);
                InsertPosition deliveryPosition = calculateBestDeliveryPosition(newRoute, request.deliveryTask.nodeId, startNode, totalAmount, newRouteTimes);
                if (deliveryPosition.cost < cost) {
                    insertPosition.cost = deliveryPosition.cost;
                    insertPosition.pickupPos = pickupIdx;
                    insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                }
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

    private void swapPositions(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }

}
