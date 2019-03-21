package com.github.schmittjoaopedro.vrp.mpdptw.operators;

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

    /*
     * Regret-3, no noise: the selection of the next request to be inserted in the solution is based on a regret criterion.
     * This means that one does not want to have a costly insertion at a later point in time if the current request is not
     * selected now. Here, we set the regret level to 3, and no noise is applied in it computation.
     * Regret-m, no noise: similar to the previous one, the regret level is equals to the number of vehicles m, and no
     * insertion noise is applied.
     * Regret-3, with noise: here, we make us of an insertion noise to allow some extra diversity in the search of the
     * regret-3 computation.
     * Regret-m, with noise: similarly, we use a regret-m criterion to which insertion noise is added.
     */
    public void insertRegretRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, int regretLevel, InsertionMethod insertionMethod, PickupMethod pickupMethod) {
        // Compute the gain (time difference) for each request insertion
        while (!requestsToInsert.isEmpty()) {
            List<InsertRequest> requestsRegret = new ArrayList<>();
            for (int r = 0; r < requestsToInsert.size(); r++) { // for each request r in requests to insert
                int requestId = requestsToInsert.get(r).requestId;
                List<InsertRequest> feasibleRoutes = new ArrayList<>();
                // Create a new empty vehicle, if there is not one available, as a possibility to insert the request
                int lastVehicle = requests.size() - 1;
                if (solution.get(lastVehicle).size() > 2) {
                    requests.add(new ArrayList<>());
                    solution.add(new ArrayList<>());
                    lastVehicle++;
                    solution.get(lastVehicle).add(0);
                    solution.get(lastVehicle).add(0);
                }
                for (int k = 0; k < solution.size(); k++) {
                    ArrayList<Integer> newRoute = new ArrayList<>(solution.get(k));
                    if (insertRequestOnVehicle(requestId, newRoute, pickupMethod, insertionMethod)) {
                        // Calculate the lost in cost to be inserting request r in vehicle k
                        double costDiff = instance.costEvaluation(newRoute) - instance.costEvaluation(solution.get(k));
                        feasibleRoutes.add(new InsertRequest(costDiff, k, requestId, newRoute));
                    }
                }
                // Use the optimal solver to execute the insertion heuristic
                if (feasibleRoutes.isEmpty()) {
                    OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(requestId, instance);
                    optimalRequestSolver.optimize();
                    ArrayList<Integer> newRoute = new ArrayList<>();
                    for (int i : optimalRequestSolver.getBestRoute()) {
                        newRoute.add(i);
                    }
                    feasibleRoutes.add(new InsertRequest(optimalRequestSolver.getBestCost(), lastVehicle, requestId, newRoute));
                }
                // Sort the vector in ascending order, from the best to worst
                feasibleRoutes.sort(Comparator.comparing(InsertRequest::getCost));
                // Get the best request based on regret criterion
                requestsRegret.add(getRegretRequestValue(feasibleRoutes, regretLevel));
            }
            // Sort in descending order, to select the most expensive request based on the regret criterion
            requestsRegret.sort(Comparator.comparing(InsertRequest::getCost).reversed());
            // Insert the costly insertion on the solution
            InsertRequest reqToInsert = requestsRegret.get(0);
            solution.set(reqToInsert.vehicle, reqToInsert.route);
            requests.get(reqToInsert.vehicle).add(reqToInsert.reqId);
            // Remove the inserted request from the requests to insert list
            for (int i = 0; i < requestsToInsert.size(); i++) {
                if (reqToInsert.reqId == requestsToInsert.get(i).requestId) {
                    requestsToInsert.remove(i);
                    break;
                }
            }
        }
        removeEmptyVehicles(solution, requests);
    }

    /*
     * Greedy insertion: select a remaining request and inserted it based on a greedy criterion, namely in the position
     * yielding the lowest increase in the objective function.
     */
    public void insertGreedyRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, List<Req> requestsToInsert, PickupMethod pickupMethod) {
        for (int r = 0; r < requestsToInsert.size(); r++) { // For each request r in requests to insert
            Req currReq = requestsToInsert.get(r);
            InsertRequest insertRequest = null;
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
                    if (insertRequest == null || lost < insertRequest.cost) {
                        insertRequest = new InsertRequest(lost, k, currReq.requestId, newRoute);
                    }
                }
            }
            if (insertRequest == null) {
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
                solution.set(insertRequest.vehicle, insertRequest.route);
                requests.get(insertRequest.vehicle).add(currReq.requestId);
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

    /*
     * Accordingly: Hemmelmayr, V. C., Cordeau, J.-F., & Crainic, T. G. (2012). An adaptive large neighborhood search heuristic
     * for Two-Echelon Vehicle Routing Problems arising in city logistics. Computers & Operations Research, 39(12), 3215–3228.
     *
     * Regret insertion in the regret heuristic, customers are treated in the order of their regret value. The regret value
     * is the cost difference between the best insertion position and the second best. Thus, customers with a high regret value
     * should be inserted first. More precisely, a regret-k heuristic chooses to insert customer i among the set U of untreated
     * customers according to i = \arg max_{i \in U} (\sum_{h=2}^{k} \Delta f_{i}^{h} - \Delta f_{i}^{1}), where \Delta f_{i}^{h}
     * is the cost of insert customer h at the hth cheapest position. This heuristic uses look-ahead information and can prevent
     * situations where we have to insert customers on poor positions because the better positions are no longer available. Once
     * a customer has been inserted, the insertion positions of the remaining unplaced customers have to be recomputed by
     * considering the change caused by inserting this customer at a position.
     */
    private InsertRequest getRegretRequestValue(List<InsertRequest> requests, int level) {
        InsertRequest r1 = requests.get(0); // Obtain the first request
        int k = Math.min(level + 1, requests.size()); // Calculate the k-value, in case the list of requests is smaller that the number of requests
        double regretValue = 0.0;
        // Find worst regret based on cost difference of the k cheapest request related to the current request
        for (int h = 1; h < k; h++) { // Start at the second request
            regretValue += requests.get(h).cost - r1.cost; // Sum the regret cost
        }
        return new InsertRequest(regretValue, r1.vehicle, r1.reqId, r1.route);
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

    public class InsertRequest {
        public double cost;
        public int vehicle;
        public int reqId;
        public ArrayList<Integer> route;

        public InsertRequest(double cost, int vehicle, int reqId, ArrayList<Integer> route) {
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
