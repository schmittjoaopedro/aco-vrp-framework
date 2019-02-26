package com.github.schmittjoaopedro.vrp.mpdptw.coelho;

import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.*;

public class InsertionOperator {

    private ProblemInstance instance;

    private Random random;

    public InsertionOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void insertRequestsSequentially(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert) {
        insertRequests(solution, requests, requestsToInsert, PickupMethod.Random, InsertionMethod.Greedy);
    }

    public void insertRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        Req currReq;
        ArrayList<Integer> newRoute;
        BestRequest bestRequest;
        for (int r = 0; r < requestsToInsert.size(); r++) {
            currReq = requestsToInsert.get(r);
            bestRequest = null;
            for (int k = 0; k < solution.size(); k++) {
                newRoute = new ArrayList<>(solution.get(k));
                if (insertRequestOnVehicle(currReq, newRoute, pickupMethod, insertionMethod)) {
                    double cost = instance.costEvaluation(newRoute);
                    if (bestRequest == null || cost < bestRequest.cost) {
                        bestRequest = new BestRequest(cost, k, newRoute);
                    }
                }
            }
            if (bestRequest == null) {
                requests.add(new ArrayList<>());
                requests.get(requests.size() - 1).add(currReq.requestId);
                solution.add(new ArrayList<>());
                solution.get(requests.size() - 1).add(0);
                solution.get(requests.size() - 1).add(0);
                if (!insertRequestOnVehicle(currReq, solution.get(solution.size() - 1), PickupMethod.Random, insertionMethod)) {
                    OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(currReq.requestId, instance);
                    optimalRequestSolver.optimize();
                    ArrayList<Integer> newTour = new ArrayList<>();
                    for (int i : optimalRequestSolver.getBestRoute()) {
                        newTour.add(i);
                    }
                    solution.get(requests.size() - 1).clear();
                    solution.get(requests.size() - 1).addAll(newTour);
                }
            } else {
                switch (insertionMethod) {
                    case Greedy:
                        solution.set(bestRequest.vehicle, bestRequest.route);
                        requests.get(bestRequest.vehicle).add(currReq.requestId);
                        break;
                }
            }
        }
    }

    public boolean insertRequestOnVehicle(Req request, ArrayList<Integer> kRoute, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        ArrayList<Integer> route = new ArrayList();
        route.addAll(kRoute);
        List<Request> pickups = getPickups(request.requestId, pickupMethod);
        Request delivery = instance.delivery.get(request.requestId);
        BestPickup pickup;
        BestPosition bestPosition = null;
        while (!pickups.isEmpty()) {
            pickup = selectAPickup(kRoute, pickups, pickupMethod, insertionMethod);
            if (pickup == null) return false;
            pickups.remove(pickup.pickupNode);
            switch (pickupMethod) {
                case Simple:
                case Random:
                    bestPosition = insertAtBestPosition(pickup.pickupNode.nodeId, route, insertionMethod, 0);
                    break;
                case Expensive:
                case Cheapest:
                    bestPosition = pickup.bestPosition;
                    break;
            }
            if (bestPosition == null) {
                return false;
            } else {
                route.add(bestPosition.position, pickup.pickupNode.nodeId);
            }
        }
        bestPosition = insertAtBestPosition(delivery.nodeId, route, insertionMethod, getLastPickupIndex(route, request.requestId));
        if (bestPosition == null) {
            return false;
        } else {
            route.add(bestPosition.position, delivery.nodeId);
        }
        kRoute.clear();
        kRoute.addAll(route);
        return true;
    }

    private BestPickup selectAPickup(ArrayList<Integer> kRoute, List<Request> pickups, PickupMethod method, InsertionMethod insertionMethod) {
        BestPickup bestPickup = null;
        switch (method) {
            case Simple:
            case Random:
                bestPickup = new BestPickup(pickups.get(0), null);
                break;
            case Cheapest:
            case Expensive:
                BestPosition bestPos;
                Map<Request, BestPosition> costs = new HashMap<>();
                for (Request pickup : pickups) {
                    bestPos = insertAtBestPosition(pickup.nodeId, kRoute, insertionMethod, 0);
                    if (bestPos != null) {
                        costs.put(pickup, bestPos);
                    }
                }
                for (Map.Entry<Request, BestPosition> entry : costs.entrySet()) {
                    if (bestPickup == null) {
                        bestPickup = new BestPickup(entry.getKey(), entry.getValue());
                    } else if (PickupMethod.Cheapest.equals(method) && entry.getValue().cost < bestPickup.bestPosition.cost) {
                        bestPickup.pickupNode = entry.getKey();
                        bestPickup.bestPosition = entry.getValue();
                    } else if (PickupMethod.Expensive.equals(method) && entry.getValue().cost > bestPickup.bestPosition.cost) {
                        bestPickup.pickupNode = entry.getKey();
                        bestPickup.bestPosition = entry.getValue();
                    }
                }
                break;
        }
        return bestPickup;
    }

    private double generateRandomNoise(InsertionMethod insertionMethod) {
        double randomNoise = 0.0;
        switch (insertionMethod) {
            case Regret3:
            case RegretM:
                randomNoise = (0.5 - Math.random()) * instance.maxDistance;
                break;
        }
        return randomNoise;
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

    private List<Request> getPickups(int requestId, PickupMethod method) {
        List<Request> pickups = new ArrayList<>();
        pickups.addAll(instance.pickups.get(requestId));
        if (PickupMethod.Random.equals(method)) {
            Collections.shuffle(pickups, random);
        }
        return pickups;
    }

    private BestPosition insertAtBestPosition(Integer i, ArrayList<Integer> route, InsertionMethod insertionMethod, int prevPos) {
        double bestCost = Double.MAX_VALUE;
        BestPosition bestPosition = null;
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
            tNewNext = Math.max(t, reqI.twStart) + reqI.serviceTime + dists[i][next] + generateRandomNoise(insertionMethod);
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
                        bestCost = newCost;
                        bestPosition = new BestPosition(currIdx, bestCost);
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

    public enum PickupMethod {
        Simple, Random, Cheapest, Expensive
    }

    public enum InsertionMethod {
        Greedy, Regret3, RegretM, Regret3Noise, RegretMNoise
    }

    public class BestPickup {
        public Request pickupNode;
        public BestPosition bestPosition;

        public BestPickup(Request pickupNode, BestPosition bestPosition) {
            this.pickupNode = pickupNode;
            this.bestPosition = bestPosition;
        }
    }

    public class BestPosition {
        public int position;
        public double cost;

        public BestPosition(int position, double cost) {
            this.position = position;
            this.cost = cost;
        }
    }

    public class BestRequest {
        public double cost;
        public int vehicle;
        public ArrayList<Integer> route;

        public BestRequest(double cost, int vehicle, ArrayList<Integer> route) {
            this.cost = cost;
            this.vehicle = vehicle;
            this.route = route;
        }
    }

}
