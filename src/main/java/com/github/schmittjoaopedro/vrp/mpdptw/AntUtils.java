package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class AntUtils {

    public static void copyFromTo(Ant from, Ant to) {
        to.tours = new ArrayList<>();
        to.requests = new ArrayList<>();
        to.tourCosts = new ArrayList<>();
        to.departureTime = new ArrayList<>();
        to.arrivalTime = new ArrayList<>();
        to.departureSlackTimes = new ArrayList<>();
        to.waitingTimes = new ArrayList<>();
        to.arrivalSlackTimes = new ArrayList<>();
        to.delays = new ArrayList<>();
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
            to.departureTime.add(i, (ArrayList<Double>) from.departureTime.get(i).clone());
            to.arrivalTime.add(i, (ArrayList<Double>) from.arrivalTime.get(i).clone());
            to.departureSlackTimes.add(i, (ArrayList<Double>) from.departureSlackTimes.get(i).clone());
            to.waitingTimes.add(i, (ArrayList<Double>) from.waitingTimes.get(i).clone());
            to.arrivalSlackTimes.add(i, (ArrayList<Double>) from.arrivalSlackTimes.get(i).clone());
            to.delays.add(i, (ArrayList<Double>) from.delays.get(i).clone());
        }
        for (int i = 0; i < from.requests.size(); i++) {
            to.requests.add(i, (ArrayList<Integer>) from.requests.get(i).clone());
        }
        for (int i = 0; i < from.tourCosts.size(); i++) {
            to.tourCosts.add(i, from.tourCosts.get(i));
        }
        to.visited = from.visited.clone();
        to.capacity = from.capacity.clone();
        to.visitedRequests = from.visitedRequests.clone();
        to.toVisit = from.toVisit;
        to.totalCost = from.totalCost;
        to.feasible = from.feasible;
        to.timeWindowPenalty = from.timeWindowPenalty;
        to.capacityPenalty = from.capacityPenalty;
    }

    public static void antEmptyMemory(Ant ant, ProblemInstance instance) {
        ant.tours = new ArrayList<>();
        ant.requests = new ArrayList<>();
        ant.tourCosts = new ArrayList<>();
        ant.departureTime = new ArrayList<>();
        ant.arrivalTime = new ArrayList<>();
        ant.departureSlackTimes = new ArrayList<>();
        ant.waitingTimes = new ArrayList<>();
        ant.arrivalSlackTimes = new ArrayList<>();
        ant.delays = new ArrayList<>();
        ant.visited = new boolean[instance.getNumNodes()];
        ant.capacity = new double[instance.getNumNodes()];
        ant.visitedRequests = new boolean[instance.getNumReq()];
        ant.toVisit = instance.getNumNodes();
        ant.totalCost = Double.MAX_VALUE;
        ant.feasible = true;
        ant.timeWindowPenalty = 0.0;
        ant.capacityPenalty = 0.0;
    }

    public static Ant createEmptyAnt(ProblemInstance instance) {
        Ant ant = new Ant();
        AntUtils.antEmptyMemory(ant, instance);
        return ant;
    }

    public static void addEmptyVehicle(Ant ant) {
        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(0);
        tour.add(0);
        ant.tours.add(tour);
        ant.requests.add(new ArrayList<>());
        ant.tourCosts.add(0.0);
    }

    public static void removeEmptyVehicles(Ant ant) {
        int position = 0;
        while (ant.tours.size() > position) {
            if (ant.requests.get(position).isEmpty()) {
                ant.tours.remove(position);
                ant.requests.remove(position);
                ant.tourCosts.remove(position);
            } else {
                position++;
            }
        }
    }

    public static void removeRequest(ProblemInstance instance, Ant ant, int vehicle, Integer requestId) {
        int nodeIdx = 0;
        int node;
        while (nodeIdx < ant.tours.get(vehicle).size()) {
            node = ant.tours.get(vehicle).get(nodeIdx);
            if (node != instance.getDepot().nodeId && instance.getRequestId(node) == requestId) {
                ant.tours.get(vehicle).remove(nodeIdx);
            } else {
                nodeIdx++;
            }
        }
        ant.requests.get(vehicle).remove(requestId);
    }

    public static int findRequestVehicleOwner(Ant ant, Integer requestId) {
        int vehicle = 0;
        for (int k = 0; k < ant.requests.size(); k++) {
            if (ant.requests.get(k).contains(requestId)) {
                vehicle = k;
                break;
            }
        }
        return vehicle;
    }

    public static Ant getBetterAnt(Ant oldAnt, Ant newAnt) {
        double oldCost = oldAnt.totalCost + oldAnt.timeWindowPenalty;
        double newCost = newAnt.totalCost + newAnt.timeWindowPenalty;
        if (oldAnt.feasible) {
            return newAnt.feasible && newCost < oldCost ? newAnt : oldAnt;
        } else {
            return newCost < oldCost ? newAnt : oldAnt;
        }
    }

}
