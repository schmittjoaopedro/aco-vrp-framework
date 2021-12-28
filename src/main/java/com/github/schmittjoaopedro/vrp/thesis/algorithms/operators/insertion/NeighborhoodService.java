package com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class NeighborhoodService {

    private double ep = 0.00000001;

    private Instance instance;

    public NeighborhoodService(Instance instance) {
        this.instance = instance;
    }

    public List<InsertPosition> searchRelocateNeighborhood(Solution solution, int requestId, int vehicleIdx) {
        int numVehicles = solution.tours.size();
        Request request = instance.requests[requestId];
        List<InsertPosition> neighborhood = new ArrayList<>();
        ArrayList<Integer> tour = new ArrayList<>(solution.tours.get(vehicleIdx));
        solution.tours.get(vehicleIdx).remove(Integer.valueOf(request.pickupTask.nodeId));
        solution.tours.get(vehicleIdx).remove(Integer.valueOf(request.deliveryTask.nodeId));
        for (int v = 0; v < numVehicles; v++) {
            List<InsertPosition> vehicleNeighborhood = searchFeasibleNeighborhood(solution.tours.get(v), request, new RouteTimes(solution.tours.get(v), instance));
            for (InsertPosition neighbor : vehicleNeighborhood) neighbor.vehicle = v;
            neighborhood.addAll(vehicleNeighborhood);
        }
        solution.tours.set(vehicleIdx, tour);
        return neighborhood;
    }

    public List<Pair<InsertPosition, InsertPosition>> searchExchangeNeighborhood(Solution solution, int requestId, int vehicleIdx) {
        int numVehicles = solution.tours.size();
        Request req1 = instance.requests[requestId];
        List<Pair<InsertPosition, InsertPosition>> neighborhood = new ArrayList<>();
        ArrayList<Integer> tour1 = new ArrayList<>(solution.tours.get(vehicleIdx));
        tour1.remove(Integer.valueOf(req1.pickupTask.nodeId));
        tour1.remove(Integer.valueOf(req1.deliveryTask.nodeId));
        RouteTimes routeTimes1 = new RouteTimes(tour1, instance);
        for (int v = 0; v < numVehicles; v++) {
            if (vehicleIdx != v) {
                for (int r = 0; r < solution.requestIds.get(v).size(); r++) {
                    Request req2 = instance.requests[solution.requestIds.get(v).get(r)];
                    List<InsertPosition> r2Neighborhood = searchFeasibleNeighborhood(tour1, req2, routeTimes1);
                    if (!r2Neighborhood.isEmpty()) {
                        ArrayList<Integer> tour2 = new ArrayList<>(solution.tours.get(v));
                        tour2.remove(Integer.valueOf(req2.pickupTask.nodeId));
                        tour2.remove(Integer.valueOf(req2.deliveryTask.nodeId));
                        List<InsertPosition> r1Neighborhood = searchFeasibleNeighborhood(tour2, req1, new RouteTimes(tour2, instance));
                        if (!r1Neighborhood.isEmpty()) {
                            for (InsertPosition r1Neighbor : r1Neighborhood) {
                                for (InsertPosition r2Neighbor : r2Neighborhood) {
                                    r1Neighbor.vehicle = v;
                                    r1Neighbor.requestId = req1.requestId;
                                    r2Neighbor.vehicle = vehicleIdx;
                                    r2Neighbor.requestId = req2.requestId;
                                    neighborhood.add(Pair.of(r1Neighbor, r2Neighbor));
                                }
                            }
                        }
                    }
                }
            }
        }
        return neighborhood;
    }

    public List<InsertPosition> searchFeasibleNeighborhood(ArrayList<Integer> route, Request request, RouteTimes routeTimes) {
        return searchFeasibleNeighborhood(route, request, routeTimes, null);
    }

    public List<InsertPosition> searchFeasibleNeighborhood(ArrayList<Integer> route, Request request, RouteTimes routeTimes, boolean[] ignoreRequests) {
        List<InsertPosition> insertPositions = new LinkedList<>();
        if (request.pickupTask.isIdle()) {
            double cost, arrivalTime, waitTime, delay, totalAmount = 0;
            int prevNode = route.get(0), currNode, pickupNode = request.pickupTask.nodeId, prevIdx = 0;
            boolean isValidCapacity, isValidTimeWindow, isValidSlackTime;
            ArrayList<Integer> newRoute = new ArrayList<>(route);
            newRoute.add(newRoute.get(0), pickupNode);
            for (int i = 1; i < route.size(); ++i) { // Ignore depot
                swapPositions(newRoute, i, i - 1); // Advance current pickup one position
                currNode = route.get(i);
                if (instance.isDepot(currNode) || ignoreRequests == null || !ignoreRequests[instance.getTask(currNode).requestId]) {
                    boolean isInTimeToVisit = instance.lastIdleTime(pickupNode) <= routeTimes.departureTime[prevIdx]; // Already know request when we departure from previous node
                    boolean isIdleNode = instance.isDepot(currNode) || instance.getTask(currNode).isIdle();
                    if (isIdleNode && isInTimeToVisit) {
                        cost = instance.dist(prevNode, pickupNode) + instance.dist(pickupNode, currNode) - instance.dist(prevNode, currNode);
                        arrivalTime = routeTimes.startTime[prevIdx] + instance.dist(prevNode, pickupNode) + instance.serviceTime(prevNode);
                        waitTime = Math.max(0.0, instance.twStart(pickupNode) - arrivalTime);
                        delay = cost + waitTime + instance.serviceTime(pickupNode);
                        isValidCapacity = totalAmount + instance.demand(pickupNode) <= instance.vehiclesCapacity;
                        if (isValidCapacity) {
                            isValidTimeWindow = arrivalTime <= instance.twEnd(pickupNode);
                            if (!isValidTimeWindow) {
                                break;
                            }
                            isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                            if (isValidSlackTime) {
                                RouteTimes newRouteTimes = ignoreRequests == null ? new RouteTimes(newRoute, instance) : new RouteTimes(newRoute, instance, ignoreRequests);
                                List<InsertPosition> deliveryPositions = calculateBestDeliveryPosition(newRoute, request.deliveryTask.nodeId, i + 1, totalAmount + instance.demand(pickupNode), newRouteTimes, ignoreRequests);
                                for (InsertPosition deliveryPosition : deliveryPositions) {
                                    InsertPosition insertPosition = new InsertPosition();
                                    insertPosition.cost = cost + deliveryPosition.cost;
                                    insertPosition.pickupPos = i;
                                    insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                                    insertPositions.add(insertPosition);
                                }
                            }
                        }
                    }
                    totalAmount += instance.demand(route.get(i));
                    if (totalAmount > instance.vehiclesCapacity) {
                        break;
                    }
                    prevIdx = i;
                    prevNode = currNode;
                }
            }
        } /*else {
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
                newRoute.remove(deliveryIdx);
                RouteTimes newRouteTimes = new RouteTimes(newRoute, instance);
                List<InsertPosition> deliveryPositions = calculateBestDeliveryPosition(newRoute, request.deliveryTask.nodeId, startNode, totalAmount, newRouteTimes);
                for (InsertPosition deliveryPosition : deliveryPositions) {
                    InsertPosition insertPosition = new InsertPosition();
                    insertPosition.cost = deliveryPosition.cost;
                    insertPosition.pickupPos = pickupIdx;
                    insertPosition.deliveryPos = deliveryPosition.deliveryPos;
                    insertPositions.add(insertPosition);
                }
            }
        }*/
        return insertPositions;
    }

    private List<InsertPosition> calculateBestDeliveryPosition(ArrayList<Integer> newRoute, int node, int startPos, double totalAmount, RouteTimes routeTimes, boolean[] ignoreRequests) {
        double cost, arrivalTime, waitTime, delay;
        int prevNode = newRoute.get(startPos - 1), currNode, prevIdx = startPos - 1;
        boolean isValidCapacity, isValidTimeWindow, isValidSlackTime;
        List<InsertPosition> insertPositions = new LinkedList<>();
        for (int i = startPos; i < newRoute.size(); ++i) {
            currNode = newRoute.get(i);
            if (instance.isDepot(currNode) || ignoreRequests == null || !ignoreRequests[instance.getTask(currNode).requestId]) {
                cost = instance.dist(prevNode, node) + instance.dist(node, currNode) - instance.dist(prevNode, currNode);
                arrivalTime = routeTimes.startTime[prevIdx] + instance.serviceTime(prevNode) + instance.dist(prevNode, node);
                waitTime = Math.max(0., instance.twStart(node) - arrivalTime);
                delay = cost + waitTime + instance.serviceTime(node);
                isValidCapacity = totalAmount + instance.demand(node) <= instance.vehiclesCapacity;
                if (isValidCapacity) {
                    isValidTimeWindow = arrivalTime <= instance.twEnd(node);
                    if (!isValidTimeWindow) {
                        break;
                    }
                    isValidSlackTime = delay <= routeTimes.waitingTime[i] + routeTimes.slackTime[i] + ep;
                    if (isValidSlackTime) {
                        InsertPosition insertPosition = new InsertPosition();
                        insertPosition.cost = cost;
                        insertPosition.deliveryPos = i;
                        insertPositions.add(insertPosition);
                    }
                }
                totalAmount += instance.demand(newRoute.get(i));
                if (totalAmount > instance.vehiclesCapacity) {
                    break;
                }
                prevIdx = i;
                prevNode = currNode;
            }
        }
        return insertPositions;
    }

    private void swapPositions(ArrayList<Integer> route, int i1, int i2) {
        Integer aux = route.get(i1);
        route.set(i1, route.get(i2));
        route.set(i2, aux);
    }

}
