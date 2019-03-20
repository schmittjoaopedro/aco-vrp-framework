package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.AntUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.*;

/**
 * Insertion operators aim at building a feasible solution after some requests have been removed from it. We developed
 * a set IO of insertion operators containing five heuristics. Each of these five insertion operators require
 * evaluating the best position to insert a node. Algorithm 4 presents the general heuristic.
 */
public class InsertionOperator {

    private ProblemInstance instance;

    private Random random;

    public InsertionOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public void insertRequestsSequentially(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert) {
        insertGreedyRequests(solution, requests, requestsToInsert, PickupMethod.Random);
    }

    public void insertRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        switch (insertionMethod) {
            case Greedy:
                insertGreedyRequests(solution, requests, requestsToInsert, pickupMethod);
                break;
            case Regret3:
            case Regret3Noise:
                insertRegretRequests(solution, requests, requestsToInsert, 3, insertionMethod, pickupMethod);
                break;
            case RegretM:
            case RegretMNoise:
                insertRegretRequests(solution, requests, requestsToInsert, solution.size(), insertionMethod, pickupMethod);
                break;
        }
    }

    public void insertRegretRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, int regretLevel, InsertionMethod insertionMethod, PickupMethod pickupMethod) {
        Req currReq;
        double vehicleCost, costDiff, cost;
        ArrayList<Integer> newRoute;
        double[] vehicleCosts = new double[solution.size()];
        List<BestRequest> bestRequestPositions;
        List<BestRequest> requestsCosts = new ArrayList<>();
        // Compute current routes costs to calculate the request insertion cost differences
        for (int k = 0; k < solution.size(); k++) {
            vehicleCosts[k] = instance.costEvaluation(solution.get(k));
        }
        // Compute the gain (time difference) for each request insertion
        for (int r = 0; r < requestsToInsert.size(); r++) {
            currReq = requestsToInsert.get(r);
            bestRequestPositions = new ArrayList<>();
            for (int k = 0; k < solution.size(); k++) {
                vehicleCost = vehicleCosts[k];
                newRoute = new ArrayList<>(solution.get(k));
                if (insertRequestOnVehicle(currReq.requestId, newRoute, pickupMethod, insertionMethod)) {
                    cost = instance.costEvaluation(newRoute);
                    costDiff = cost - vehicleCost; // Smaller differences means greater reduction in the solution cost
                    bestRequestPositions.add(new BestRequest(costDiff, k, currReq.requestId, newRoute));
                }
            }
            // Compute cost of a new route insertion
            newRoute = new ArrayList<>();
            newRoute.add(0);
            newRoute.add(0);
            // Creates a new vehicle and try the insertion using the heuristic method
            if (insertRequestOnVehicle(currReq.requestId, newRoute, pickupMethod, insertionMethod)) {
                cost = instance.costEvaluation(newRoute);
                bestRequestPositions.add(new BestRequest(cost, solution.size(), currReq.requestId, newRoute));
            }
            // Use the optimal solver to execute the insertion heuristic
            else {
                OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(currReq.requestId, instance);
                optimalRequestSolver.optimize();
                ArrayList<Integer> newTour = new ArrayList<>();
                for (int i : optimalRequestSolver.getBestRoute()) {
                    newTour.add(i);
                }
                cost = instance.costEvaluation(newRoute);
                bestRequestPositions.add(new BestRequest(cost, solution.size(), currReq.requestId, newRoute));
            }
            // Get the best request based on regret value
            requestsCosts.add(getRegretRequestValue(bestRequestPositions, regretLevel));
        }
        requestsCosts.sort(Comparator.comparing(BestRequest::getCost).reversed());
        requestsToInsert.clear();
        for (BestRequest reqRegret : requestsCosts) {
            requestsToInsert.add(new Req(reqRegret.vehicle, reqRegret.reqId, 0.0));
        }
        insertGreedyRequests(solution, requests, requestsToInsert, pickupMethod);
    }

    private BestRequest getRegretRequestValue(List<BestRequest> requestPositions, int regretLevel) {
        BestRequest temp, worstRegret = null;
        double costDiff;
        // Sort the vector to
        requestPositions.sort(Comparator.comparing(BestRequest::getCost));
        int regretIdx;
        BestRequest req = requestPositions.get(0);
        regretIdx = regretLevel + 1;
        regretIdx = Math.min(regretIdx, requestPositions.size());
        // Find worst regret based on cost difference of the k cheapest request related to the current request
        for (int i = 1; i < regretIdx; i++) {
            temp = requestPositions.get(i);
            if (temp != req) {
                costDiff = temp.cost - req.cost;
                if (worstRegret == null || costDiff > worstRegret.cost) {
                    worstRegret = new BestRequest(costDiff, temp.vehicle, temp.reqId, temp.route);
                }
            }
        }
        if (worstRegret == null) {
            worstRegret = new BestRequest(req.cost, req.vehicle, req.reqId, req.route);
        }
        return worstRegret;
    }

    /*
     * Greedy insertion: select a remaining request and inserted it based on a greedy criterion, namely in the position
     * yielding the lowest increase in the objective function.
     */
    public void insertGreedyRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, PickupMethod pickupMethod) {
        for (int r = 0; r < requestsToInsert.size(); r++) { // For each request r in requests to insert
            Req currReq = requestsToInsert.get(r);
            BestRequest bestRequest = null;
            int lastVehicle = requests.size() - 1;
            if (solution.get(lastVehicle).size() > 2) {
                // Create a new vehicle to let available to the greedy operator
                requests.add(new ArrayList<>());
                solution.add(new ArrayList<>());
                lastVehicle++;
                solution.get(lastVehicle).add(0);
                solution.get(lastVehicle).add(0);
            }
            for (int k = 0; k < solution.size(); k++) { // For each vehicle from solution
                double vehicleCost = instance.costEvaluation(solution.get(k)); // Evaluate the vehicle route cost
                ArrayList<Integer> newRoute = new ArrayList<>(solution.get(k)); // Clone the route from vehicle k to evict update the original one
                if (insertRequestOnVehicle(currReq.requestId, newRoute, pickupMethod, InsertionMethod.Greedy)) { // If the request insertion is feasible
                    double lost = (instance.costEvaluation(newRoute) - vehicleCost); // Calculate the lost of insert request r in vehicle k
                    // If a new best insertion was found, hold this reference (request yielding the lowest increase in the objective function)
                    if (bestRequest == null || lost < bestRequest.cost) {
                        bestRequest = new BestRequest(lost, k, currReq.requestId, newRoute);
                    }
                }
            }
            if (bestRequest == null) {
                // Use the optimal solver to build a feasible request
                OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(currReq.requestId, instance);
                optimalRequestSolver.optimize();
                solution.get(lastVehicle).clear();
                for (int i : optimalRequestSolver.getBestRoute()) {
                    solution.get(lastVehicle).add(i);
                }
                requests.get(lastVehicle).add(currReq.requestId);
            } else {
                // Add the inserted request on the vehicle
                solution.set(bestRequest.vehicle, bestRequest.route);
                requests.get(bestRequest.vehicle).add(currReq.requestId);
            }
        }
        removeEmptyVehicles(solution, requests);
    }

    public boolean insertRequestOnVehicle(int requestId, ArrayList<Integer> kRoute, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        BestPosition bestPosition = null;
        ArrayList<Integer> route = new ArrayList(kRoute);
        List<Request> pickups = getPickups(requestId, pickupMethod);
        Request delivery = instance.delivery.get(requestId);
        while (!pickups.isEmpty()) {
            BestPickup pickup = selectAPickup(kRoute, pickups, pickupMethod, insertionMethod);
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
        bestPosition = insertAtBestPosition(delivery.nodeId, route, insertionMethod, getLastPickupIndex(route, requestId));
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
            case Regret3Noise:
            case RegretMNoise:
                randomNoise = (0.5 - random.nextDouble()) * instance.maxDistance;
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
        double deltaBestCost = Double.MAX_VALUE;
        double slackTimes[] = instance.slackTimesSavelsbergh(route, true);
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
            t = travelTime + dists[prev][i]; // Vehicle arrival time at(k, i)
            if (t > reqI.twEnd) { // t > bi
                break; // Exit algorithm
            }
            tNext = travelTime + dists[prev][next]; // Set t_next the actual arrival time at next
            tNewNext = Math.max(t, reqI.twStart) + reqI.serviceTime + dists[i][next]; // t'_next <- arrival time at next if i is inserted before
            addedDuration = tNewNext - tNext; // addedDuration = t'_next - t_next
            twEndNext = twEnd(next);
            slackNext = slackTimes[currIdx];//slackNext(next, tNext); // TODO: Should be the savelsbergh calculation
            if (tNext > twEndNext || addedDuration > slackNext) { // t_next > b_next  OR addedDuration > slack_next
                infeasible = true;
            }
            if (!infeasible) {
                newCost = dists[prev][i] + dists[i][next] - dists[prev][next] + generateRandomNoise(insertionMethod);
                if (newCost < deltaBestCost) {
                    route.add(currIdx, i);
                    if (instance.restrictionsEvaluation(route).feasible) {
                        deltaBestCost = newCost;
                        bestPosition = new BestPosition(currIdx, deltaBestCost);
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

    // Remove empty vehicles
    private void removeEmptyVehicles(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        int position = 0;
        while (solution.size() > position) {
            if (requests.get(position).isEmpty()) {
                solution.remove(position);
                requests.remove(position);
            } else {
                position++;
            }
        }
    }

    private double slackNext(int next, double tNext) {
        if (next == instance.depot.nodeId) {
            return instance.depot.twEnd - tNext;
        } else {
            return instance.requests[next - 1].twEnd - tNext;
        }
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
        public int reqId;
        public ArrayList<Integer> route;

        public BestRequest(double cost, int vehicle, int reqId, ArrayList<Integer> route) {
            this.cost = cost;
            this.vehicle = vehicle;
            this.reqId = reqId;
            this.route = route;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "(" + vehicle + "," + reqId + ") = " + cost;
        }
    }

}
