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
        for (int i = 0; i < from.tours.size(); i++) {
            to.tours.add(i, (ArrayList<Integer>) from.tours.get(i).clone());
        }
        to.tourLengths = new ArrayList<>();
        for (int i = 0; i < from.tourLengths.size(); i++) {
            to.tourLengths.add(i, from.tourLengths.get(i));
        }
    }

    public static void antEmptyMemory(Ant ant, ProblemInstance instance) {
        ant.feasible = true;
        ant.tours = new ArrayList<>();
        ant.requests = new ArrayList<>();
        ant.visited = new boolean[instance.noNodes];
        ant.demands = new double[instance.noNodes];
        ant.tourLengths = new ArrayList<>();
        ant.totalCost = Double.MAX_VALUE;
        ant.departureTime = new double[instance.noNodes];
        ant.visitedRequests = new boolean[instance.noReq];
        ant.capacityPenalty = 0.0;
        ant.timeWindowPenalty = 0.0;
        ant.toVisit = instance.noNodes;
    }

    public static Ant createEmptyAnt(ProblemInstance instance) {
        Ant ant = new Ant();
        AntUtils.antEmptyMemory(ant, instance);
        return ant;
    }

}
