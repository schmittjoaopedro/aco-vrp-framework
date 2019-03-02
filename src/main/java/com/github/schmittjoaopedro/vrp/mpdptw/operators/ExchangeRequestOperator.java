package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.Ant;
import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.Solution;

import java.util.ArrayList;

public class ExchangeRequestOperator {

    private ProblemInstance instance;

    public ExchangeRequestOperator(ProblemInstance instance) {
        this.instance = instance;
    }

    public Ant exchange(Ant ant) {
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, improvedAnt);
        AntUtils.copyFromTo(improvedAnt, tempAnt);
        boolean improvement = true;
        int v1, v2;
        double improvedCost = improvedAnt.totalCost + improvedAnt.timeWindowPenalty;
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (int r1 = 0; r1 < instance.noReq; r1++) {
                v1 = AntUtils.findRequestVehicleOwner(tempAnt, r1);
                v1RouteOrigin = new ArrayList<>(tempAnt.tours.get(v1));
                AntUtils.removeRequest(instance, tempAnt, v1, r1);
                for (int r2 = 0; r2 < instance.noReq; r2++) {
                    if (r1 != r2) {
                        v2 = AntUtils.findRequestVehicleOwner(tempAnt, r2);
                        v2RouteOrigin = new ArrayList<>(tempAnt.tours.get(v2));
                        AntUtils.removeRequest(instance, tempAnt, v2, r2);
                        if (insertRequest(tempAnt.tours, v1, r2)) {
                            tempAnt.requests.get(v1).add(r2);
                            if (insertRequest(tempAnt.tours, v2, r1)) {
                                tempAnt.requests.get(v2).add(r1);
                                instance.restrictionsEvaluation(tempAnt);
                                tempCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                                if (tempCost < improvedCost) {
                                    AntUtils.copyFromTo(tempAnt, improvedAnt);
                                    improvedCost = tempCost;
                                    improvement = true;
                                }
                                AntUtils.removeRequest(instance, tempAnt, v2, r1);
                            }
                            AntUtils.removeRequest(instance, tempAnt, v1, r2);
                        }
                        tempAnt.tours.set(v2, v2RouteOrigin);
                        tempAnt.requests.get(v2).add(r2);
                    }
                }
                tempAnt.tours.set(v1, v1RouteOrigin);
                tempAnt.requests.get(v1).add(r1);
            }
        }
        instance.restrictionsEvaluation(improvedAnt);
        return AntUtils.getBetterAnt(ant, improvedAnt);
    }

    public Solution exchange(Solution solution) {
        Solution improvedSol = solution.copy();
        Solution tempSol = improvedSol.copy();
        boolean improvement = true;
        int v1, v2;
        double improvedCost = improvedSol.totalCost;
        double tempCost;
        ArrayList<Integer> v1RouteOrigin, v2RouteOrigin;
        while (improvement) {
            improvement = false;
            for (int r1 = 0; r1 < instance.noReq; r1++) {
                v1 = tempSol.findRequestVehicleOwner(r1);
                v1RouteOrigin = new ArrayList<>(tempSol.tours.get(v1));
                tempSol.removeRequest(instance, v1, r1);
                for (int r2 = 0; r2 < instance.noReq; r2++) {
                    if (r1 != r2) {
                        v2 = tempSol.findRequestVehicleOwner(r2);
                        v2RouteOrigin = new ArrayList<>(tempSol.tours.get(v2));
                        tempSol.removeRequest(instance, v2, r2);
                        if (insertRequest(tempSol.tours, v1, r2)) {
                            tempSol.requests.get(v1).add(r2);
                            if (insertRequest(tempSol.tours, v2, r1)) {
                                tempSol.requests.get(v2).add(r1);
                                instance.restrictionsEvaluation(tempSol);
                                tempCost = tempSol.totalCost;
                                if (tempCost < improvedCost) {
                                    improvedSol = tempSol.copy();
                                    improvedCost = tempCost;
                                    improvement = true;
                                }
                                tempSol.removeRequest(instance, v2, r1);
                            }
                            tempSol.removeRequest(instance, v1, r2);
                        }
                        tempSol.tours.set(v2, v2RouteOrigin);
                        tempSol.requests.get(v2).add(r2);
                    }
                }
                tempSol.tours.set(v1, v1RouteOrigin);
                tempSol.requests.get(v1).add(r1);
            }
        }
        instance.restrictionsEvaluation(improvedSol);
        return solution.getBestSolution(improvedSol);
    }

    private boolean insertRequest(ArrayList<ArrayList<Integer>> tours, int vehicle, int reqId) {
        int lastPickup = 0;
        for (Request pickup : instance.pickups.get(reqId)) {
            int bestPosition = getBestPosition(pickup.nodeId, tours.get(vehicle), 0);
            tours.get(vehicle).add(bestPosition, pickup.nodeId);
            if (lastPickup >= bestPosition) {
                lastPickup++;
            } else {
                lastPickup = bestPosition;
            }
        }
        int deliveryId = instance.delivery.get(reqId).nodeId;
        int bestPosition = getBestPosition(deliveryId, tours.get(vehicle), lastPickup);
        tours.get(vehicle).add(bestPosition, deliveryId);
        return true;
    }

    private int getBestPosition(Integer i, ArrayList<Integer> route, int prevPos) {
        double bestCost = Double.MAX_VALUE;
        int bestPosition = 0;
        int prev = route.get(prevPos);
        prevPos++;
        int next = route.get(prevPos);
        int currIdx = prevPos;
        double[][] dists = instance.distances;
        double newCost;
        double travelTime = 0.0;
        int count = 1;
        while (count < currIdx) {
            travelTime = travelTime + dists[route.get(count - 1)][route.get(count)];
            travelTime = Math.max(travelTime, instance.requests[route.get(count) - 1].twStart);
            travelTime += instance.requests[route.get(count) - 1].serviceTime;
            count++;
        }
        while (currIdx < route.size()) {
            newCost = dists[prev][i] + dists[i][next] - dists[prev][next];
            if (newCost < bestCost) {
                bestCost = newCost;
                bestPosition = currIdx;
            }
            travelTime = travelTime + dists[prev][next];
            travelTime = Math.max(travelTime, twStart(next));
            travelTime += serviceTime(next);
            prev = next;
            if (prev == instance.depot.nodeId) {
                break;
            }
            currIdx++;
            next = route.get(currIdx);
        }
        return bestPosition;
    }

    private double twStart(int next) {
        if (next == instance.depot.nodeId) {
            return instance.depot.twStart;
        } else {
            return instance.requests[next - 1].twStart;
        }
    }

    private double serviceTime(int next) {
        if (next == instance.depot.nodeId) {
            return 0.0;
        } else {
            return instance.requests[next - 1].serviceTime;
        }
    }
}
