package com.github.schmittjoaopedro.vrp.mpdptw.coelho;

import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InsertionOperator {

    private ProblemInstance instance;

    private Random random;

    public InsertionOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void insertRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert) {
        Req currReq;
        for (int r = 0; r < requestsToInsert.size(); r++) {
            boolean foundVehicle = false;
            currReq = requestsToInsert.get(r);
            for (int k = 0; k < solution.size(); k++) {
                if (insertRequestOnVehicle(currReq, solution.get(k))) {
                    requests.get(k).add(currReq.requestId);
                    foundVehicle = true;
                    break;
                }
            }
            if (!foundVehicle) {
                requests.add(new ArrayList<>());
                requests.get(requests.size() - 1).add(currReq.requestId);
                solution.add(new ArrayList<>());
                solution.get(requests.size() - 1).add(0);
                solution.get(requests.size() - 1).add(0);
                if (!insertRequestOnVehicle(currReq, solution.get(solution.size() - 1))) {
                    OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(currReq.requestId, instance);
                    optimalRequestSolver.optimize();
                    ArrayList<Integer> newTour = new ArrayList<>();
                    for (int i : optimalRequestSolver.getBestRoute()) {
                        newTour.add(i);
                    }
                    solution.get(requests.size() - 1).clear();
                    solution.get(requests.size() - 1).addAll(newTour);
                }
            }
        }
    }

    public boolean insertRequestOnVehicle(Req request, ArrayList<Integer> kRoute) {
        ArrayList<Integer> route = new ArrayList();
        route.addAll(kRoute);
        List<Request> pickups = getPickups(request.requestId);
        Request delivery = instance.delivery.get(request.requestId);
        Request pickup;
        Integer bestPosition;
        while (!pickups.isEmpty()) {
            pickup = pickups.get(0);
            bestPosition = insertAtBestPosition(pickup.nodeId, route, 0);
            pickups.remove(pickup);
            if (bestPosition == null) {
                return false;
            } else {
                route.add(bestPosition, pickup.nodeId);
            }
        }
        bestPosition = insertAtBestPosition(delivery.nodeId, route, getLastPickupIndex(route, request.requestId));
        if (bestPosition == null) {
            return false;
        } else {
            route.add(bestPosition, delivery.nodeId);
        }
        kRoute.clear();
        kRoute.addAll(route);
        return true;
    }

    private int getLastPickupIndex(ArrayList<Integer> route, int requestId) {
        int pos = 0;
        Request req;
        for (int i = 1; i < route.size() - 1; i++) {
            req = instance.requests[route.get(i) - 1];
            if (req.isPickup && req.requestId == requestId) {
                pos = i;
            }
        }
        return pos;
    }

    private List<Request> getPickups(int requestId) {
        List<Request> pickups = new ArrayList<>();
        pickups.addAll(instance.pickups.get(requestId));
        Collections.shuffle(pickups, random);
        return pickups;
    }

    private Integer insertAtBestPosition(Integer i, ArrayList<Integer> route, int prevPos) {
        double bestCost = Double.MAX_VALUE;
        Integer bestPosition = null;
        int prev = route.get(prevPos);
        prevPos++;
        int next = route.get(prevPos);
        int currIdx = prevPos;
        Request reqI = instance.requests[i - 1];
        double[][] dists = instance.distances;
        double t, tNext, tNewNext, twEndNext, addedDuration, slackNext, newCost;
        double travelTime = 0.0;
        boolean infeasible;
        int count = 1;
        while (count < currIdx) {
            travelTime = travelTime + dists[route.get(count - 1)][route.get(count)];
            travelTime = Math.max(travelTime, instance.requests[route.get(count) - 1].twStart);
            travelTime += instance.requests[route.get(count) - 1].serviceTime;
            count++;
        }
        while (currIdx < route.size()) {
            infeasible = false;
            t = travelTime + dists[prev][i];
            if (t > reqI.twEnd) {
                break;
            }
            tNext = travelTime + dists[prev][next];
            tNewNext = Math.max(t, reqI.twStart) + reqI.serviceTime + dists[i][next];
            addedDuration = tNewNext - tNext;
            twEndNext = twEnd(next);
            slackNext = slackNext(next, tNext);
            if (tNext > twEndNext || addedDuration > slackNext) {
                infeasible = true;
            }
            if (!infeasible) {
                newCost = dists[prev][i] + dists[i][next] - dists[prev][next];
                if (newCost < bestCost) {
                    route.add(currIdx, i);
                    if (instance.restrictionsEvaluation(route).feasible) {
                        bestPosition = currIdx;
                        bestCost = newCost;
                    }
                    route.remove(currIdx);
                }
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

    private double twEnd(int next) {
        if (next == instance.depot.nodeId) {
            return instance.depot.twEnd;
        } else {
            return instance.requests[next - 1].twEnd;
        }
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

    private double slackNext(int next, double tNext) {
        if (next == instance.depot.nodeId) {
            return instance.depot.twEnd - tNext;
        } else {
            return instance.requests[next - 1].twEnd - instance.requests[next - 1].serviceTime - tNext;
        }
    }

}
