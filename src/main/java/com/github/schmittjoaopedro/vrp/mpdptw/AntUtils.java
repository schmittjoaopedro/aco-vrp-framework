package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class AntUtils {

    public static void copyFromTo(Ant from, Ant to) {
        to.totalCost = from.totalCost;
        to.capacityPenalty = from.capacityPenalty;
        to.feasible = from.feasible;
        to.visited = from.visited.clone();
        to.timeWindowPenalty = from.timeWindowPenalty;
        to.departureTime = from.departureTime;
        to.visitedRequests = from.visitedRequests;
        to.tours = new ArrayList<>();
        to.requests = new ArrayList<>();
        to.capacity = new ArrayList<>();
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
        }
        to.tourLengths = new ArrayList<>();
        for (int i = 0; i < from.tourLengths.size(); i++) {
            to.tourLengths.add(i, from.tourLengths.get(i));
        }
        for (int i = 0; i < from.requests.size(); i++) {
            to.requests.add(i, (ArrayList<Integer>) from.requests.get(i).clone());
        }
        for (int i = 0; i < from.capacity.size(); i++) {
            to.capacity.add(i, from.capacity.get(i));
        }
    }

    public static void antEmptyMemory(Ant ant, ProblemInstance instance) {
        ant.feasible = true;
        ant.tours = new ArrayList<>();
        ant.requests = new ArrayList<>();
        ant.capacity = new ArrayList<>();
        ant.visited = new boolean[instance.getNumNodes()];
        ant.demands = new double[instance.getNumNodes()];
        ant.tourLengths = new ArrayList<>();
        ant.totalCost = Double.MAX_VALUE;
        ant.departureTime = new double[instance.getNumNodes()];
        ant.visitedRequests = new boolean[instance.getNumReq()];
        ant.capacityPenalty = 0.0;
        ant.timeWindowPenalty = 0.0;
        ant.toVisit = instance.getNumNodes();
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
        ant.tourLengths.add(0.0);
    }

    public static void removeEmptyVehicles(Ant ant) {
        int position = 0;
        while (ant.tours.size() > position) {
            if (ant.requests.get(position).isEmpty()) {
                ant.tours.remove(position);
                ant.requests.remove(position);
                ant.tourLengths.remove(position);
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
