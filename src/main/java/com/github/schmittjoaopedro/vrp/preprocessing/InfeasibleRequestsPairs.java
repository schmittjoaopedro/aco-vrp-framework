package com.github.schmittjoaopedro.vrp.preprocessing;

import com.github.schmittjoaopedro.vrp.mpdptw.Ant;
import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfeasibleRequestsPairs {

    private ProblemInstance instance;

    private RemovalOperator removalOperator;

    private InsertionOperator insertionOperator;

    private RelocateNodeOperator relocateNodeOperator;

    public InfeasibleRequestsPairs(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.removalOperator = new RemovalOperator(instance, random);
        this.insertionOperator = new InsertionOperator(instance, random);
        this.relocateNodeOperator = new RelocateNodeOperator(instance);
    }

    public boolean[][] calculateFeasibilityPairs() {
        boolean[][] requestsPairs = new boolean[instance.getNumReq()][instance.getNumReq()];
        for (int i = 0; i < instance.getNumReq(); i++) {
            int noF = 0;
            for (int j = i + 1; j < instance.getNumReq(); j++) {
                requestsPairs[i][j] = isFeasible(i, j);
                requestsPairs[j][i] = requestsPairs[i][j];
                if (!requestsPairs[i][j]) {
                    noF++;
                }
            }
            System.out.println(noF);
        }
        return requestsPairs;
    }

    private boolean isFeasible(int req1, int req2) {
        // Simulate vehicle requests
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(req1);
        requests.add(req2);
        // Route
        ArrayList<Integer> route1 = getOptimalRoute(req1);
        ArrayList<Integer> route2 = getOptimalRoute(req2);
        ArrayList<Integer> routeMerged = new ArrayList<>();
        routeMerged.add(0);
        routeMerged.addAll(route1.subList(1, route1.size() - 1));
        routeMerged.addAll(route2.subList(1, route2.size() - 1));
        routeMerged.add(0);
        // Temporary ant
        Ant ant = AntUtils.createEmptyAnt(instance);
        ant.tours.add(routeMerged);
        ant.requests.add(requests);
        instance.restrictionsEvaluation(ant);
        // Try to find feasible route
        ant = optimize(ant);
        instance.restrictionsEvaluation(ant, false);
        return ant.feasible;
    }

    public Ant optimize(Ant ant) {
        Ant tempAnt = ant;
        boolean improvement = true;
        double oldCost = ant.totalCost + ant.timeWindowPenalty;
        double newCost;
        while (improvement) {
            improvement = false;
            tempAnt = relocateNodeOperator.relocate(tempAnt);
            tempAnt = optimize(tempAnt, InsertionMethod.Greedy);
            newCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
            if (tempAnt.feasible) break;
            if (newCost < oldCost) {
                oldCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                improvement = true;
            }
        }
        return tempAnt;
    }

    public Ant optimize(Ant ant, InsertionMethod insertionMethod) {
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, tempAnt);
        AntUtils.copyFromTo(ant, improvedAnt);
        boolean improvement = true;
        boolean improved = false;
        while (improvement) {
            List<Req> removedRequests = removalOperator.removeRandomRequest(tempAnt.tours, tempAnt.requests, 1);
            insertionOperator.insertRequests(tempAnt.tours, tempAnt.requests, removedRequests, PickupMethod.Random, insertionMethod);
            instance.restrictionsEvaluation(tempAnt);
            improvement = AntUtils.getBetterAnt(improvedAnt, tempAnt) == tempAnt;
            if (improvement) {
                AntUtils.copyFromTo(tempAnt, improvedAnt);
                improved = true;
                if (tempAnt.feasible) break;
            }
        }
        if (improved) {
            return improvedAnt;
        } else {
            return ant;
        }
    }

    private ArrayList<Integer> getOptimalRoute(int requestId) {
        OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requestId, instance);
        optimalRequestSolver.optimize();
        ArrayList<Integer> newTour = new ArrayList<>();
        for (int i : optimalRequestSolver.getBestRoute()) {
            newTour.add(i);
        }
        return newTour;
    }

}
