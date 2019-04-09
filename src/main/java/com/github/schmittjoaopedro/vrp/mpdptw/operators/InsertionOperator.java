package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.*;

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

    public void insertRequestsSequentially(Solution solution, List<Req> requestsToInsert) {
        insertGreedyRequests(solution, requestsToInsert, PickupMethod.Random);
    }

    public void insertRequests(Solution solution, List<Req> requestsToInsert, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        switch (insertionMethod) {
            case Greedy:
                insertGreedyRequests(solution, requestsToInsert, pickupMethod);
                break;
            case Regret3:
            case Regret3Noise:
                insertRegretRequests(solution, requestsToInsert, 3, insertionMethod, pickupMethod);
                break;
            case RegretM:
            case RegretMNoise:
                insertRegretRequests(solution, requestsToInsert, solution.tours.size(), insertionMethod, pickupMethod);
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
    public void insertRegretRequests(Solution solution, List<Req> requestsToInsert, int regretLevel, InsertionMethod insertionMethod, PickupMethod pickupMethod) {
        // Cache already processed routes to evict over processing
        RouteCache originalRoutesCache = new RouteCache();
        RouteCache newRoutesCache = new RouteCache();
        // Compute the gain (time difference) for each request insertion
        while (!requestsToInsert.isEmpty()) {
            List<InsertRequest> requestsRegret = new ArrayList<>();
            for (int r = 0; r < requestsToInsert.size(); r++) { // for each request r in requests to insert
                int requestId = requestsToInsert.get(r).requestId;
                List<InsertRequest> feasibleRoutes = new ArrayList<>();
                // Create a new empty vehicle, if there is not one available, as a possibility to insert the request
                int lastVehicle = solution.tours.size() - 1;
                if (solution.tours.get(lastVehicle).size() > 2) {
                    SolutionUtils.addEmptyVehicle(solution);
                    lastVehicle++;
                }
                for (int k = 0; k < solution.tours.size(); k++) {
                    if (!originalRoutesCache.hasCacheCost(k, requestId)) {
                        originalRoutesCache.setCacheCost(k, requestId, solution.tours.get(k));
                    }
                    double originalCost = originalRoutesCache.getCacheCost(k, requestId);
                    if (!newRoutesCache.hasCacheCost(k, requestId)) {
                        ArrayList<Integer> originalRoute = new ArrayList<>(solution.tours.get(k));
                        if (insertRequestOnVehicle(solution, k, requestId, pickupMethod, insertionMethod)) {
                            // Calculate the lost in cost to be inserting request r in vehicle k
                            newRoutesCache.setCacheCost(k, requestId, solution.tours.get(k));
                            double newCost = newRoutesCache.getCacheCost(k, requestId);
                            double costDiff = newCost - originalCost;
                            feasibleRoutes.add(new InsertRequest(costDiff, k, requestId, solution.tours.get(k)));
                            solution.tours.set(k, originalRoute);
                            instance.solutionEvaluation(solution, k);
                        } else {
                            newRoutesCache.setCacheCost(k, requestId, null);
                        }
                    } else if (!newRoutesCache.isNull(k, requestId)) {
                        double costDiff = newRoutesCache.getCacheCost(k, requestId) - originalCost;
                        feasibleRoutes.add(new InsertRequest(costDiff, k, requestId, newRoutesCache.getCacheRoute(k, requestId)));
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
            solution.tours.set(reqToInsert.vehicle, reqToInsert.route);
            solution.requests.get(reqToInsert.vehicle).add(reqToInsert.reqId);
            originalRoutesCache.removeVehicleFromCache(reqToInsert.vehicle);
            newRoutesCache.removeVehicleFromCache(reqToInsert.vehicle);
            // Remove the inserted request from the requests to insert list
            for (int i = 0; i < requestsToInsert.size(); i++) {
                if (reqToInsert.reqId == requestsToInsert.get(i).requestId) {
                    requestsToInsert.remove(i);
                    break;
                }
            }
        }
        removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
    }

    /*
     * Greedy insertion: select a remaining request and inserted it based on a greedy criterion, namely in the position
     * yielding the lowest increase in the objective function.
     */
    public void insertGreedyRequests(Solution solution, List<Req> requestsToInsert, PickupMethod pickupMethod) {
        for (int r = 0; r < requestsToInsert.size(); r++) { // For each request r in requests to insert
            Req currReq = requestsToInsert.get(r);
            InsertRequest insertRequest = null;
            int lastVehicle = solution.tours.size() - 1;
            if (solution.tours.get(lastVehicle).size() > 2) {
                // Create a new vehicle to let available to the greedy operator
                lastVehicle++;
                SolutionUtils.addEmptyVehicle(solution);
            }
            for (int k = 0; k < solution.tours.size(); k++) { // For each vehicle from solution
                double prevCost = solution.tourCosts.get(k); // Evaluate the current vehicle route cost
                ArrayList<Integer> originalRoute = new ArrayList<>(solution.tours.get(k)); // Clone the route from vehicle k to evict update the original one
                if (insertRequestOnVehicle(solution, k, currReq.requestId, pickupMethod, InsertionMethod.Greedy)) { // If the request insertion is feasible
                    double costIncrease = (solution.tourCosts.get(k) - prevCost); // Calculate the lost of insert request r in vehicle k
                    // If a new best insertion was found, hold this reference (request yielding the lowest increase in the objective function)
                    if (insertRequest == null || costIncrease < insertRequest.cost) {
                        insertRequest = new InsertRequest(costIncrease, k, currReq.requestId, solution.tours.get(k));
                    }
                    solution.tours.set(k, originalRoute);
                    instance.solutionEvaluation(solution, k);
                }
            }
            if (insertRequest == null) {
                // Use the optimal solver to build a feasible request
                OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(currReq.requestId, instance);
                optimalRequestSolver.optimize();
                solution.tours.get(lastVehicle).clear();
                for (int i : optimalRequestSolver.getBestRoute()) {
                    solution.tours.get(lastVehicle).add(i);
                }
                solution.requests.get(lastVehicle).add(currReq.requestId);
                instance.solutionEvaluation(solution, lastVehicle);
            } else {
                // Add the inserted request on the vehicle
                solution.tours.set(insertRequest.vehicle, insertRequest.route);
                solution.requests.get(insertRequest.vehicle).add(currReq.requestId);
                instance.solutionEvaluation(solution, insertRequest.vehicle);
            }
        }
        removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
    }

    /*
     * While there are nodes in the pickup set, the algorithm selects a pickup node pi \in Pr using one of th pickups
     * selection methods described in Section 3.3.1. Then the best position of pi in k is found (BestPosition(pi, l))
     * i found by applying Algorithm 5, and the it is removed from the pickups set. Once all pickups are inserted, the
     * delivery node dr is inserted at its best position in route k. If the insertion of node i \in {Pr U {Dr}} is not
     * possible, the algorithm is interrupted and BestPosition(i, k) returns null.
     */
    public boolean insertRequestOnVehicle(Solution solution, int vehicle, int requestToInsert, PickupMethod pickupMethod, InsertionMethod insertionMethod) {
        BestPosition bestPosition = null;
        instance.solutionEvaluation(solution, vehicle);
        ArrayList<Integer> originalRoute = new ArrayList(solution.tours.get(vehicle));
        List<Request> pickups = new ArrayList<>(instance.getPickups(requestToInsert)); // pickups <- Pr
        Request delivery = instance.getDelivery(requestToInsert);
        boolean feasible = true;
        while (feasible && !pickups.isEmpty()) { // while pickups <> null do
            BestPickup pickup = selectAPickup(solution, vehicle, pickups, pickupMethod, insertionMethod); // pi <- selectAPickup(pickups, method)
            if (pickup == null) {
                feasible = false;
                break;
            }
            pickups.remove(pickup.pickupNode); // pickups <- pickups\{p}
            switch (pickupMethod) {
                case Simple:
                case Random:
                    // BestPosition(pi, k) <- Insert pi at its best insertion position in k
                    bestPosition = insertAtBestPosition(solution, vehicle, pickup.pickupNode.nodeId, insertionMethod, 0);
                    break;
                case Expensive:
                case Cheapest:
                    bestPosition = pickup.bestPosition;
                    break;
            }
            if (bestPosition == null) { // If BestPosition(pi, k) = null then
                feasible = false; // Return request insertion infeasible
            } else {
                solution.tours.get(vehicle).add(bestPosition.position, pickup.pickupNode.nodeId);
                instance.solutionEvaluation(solution, vehicle);
            }
        }
        if (feasible) {
            // BestPosition(dr, k) <- Insert dr at its best insertion position in k
            bestPosition = insertAtBestPosition(solution, vehicle, delivery.nodeId, insertionMethod, getLastPickupIndex(solution.tours.get(vehicle), requestToInsert));
            if (bestPosition == null) { // If BestPosition(dr, k) = null then
                feasible = false; // Return request insertion infeasible
            } else {
                solution.tours.get(vehicle).add(bestPosition.position, delivery.nodeId);
                instance.solutionEvaluation(solution, vehicle);
            }
        }
        if (!feasible) {
            solution.tours.set(vehicle, originalRoute);
            instance.solutionEvaluation(solution, vehicle);
        }
        return feasible; // Request insertion is feasible
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

    /*
     * One of the challenges of the MPDPTW is that a single request contains many pickup nodes that ca be performed in
     * any order. Hence, when inserting a request on a route, we have the choice to change the order in which pickup
     * nodes are inserted, as long as the delivery node is present after all pickup nodes along the route. Hence, for
     * each of the tour insertion operators described in Section 3.3, we must determine the order at wich differente nodes
     * will be inserted.
     *
     * Insertion methods:
     * 1. Simple insertion order: here, the method selects the pickup nodes according to the order in which they are
     * described in the instance.
     * 2. Random insertion order: in the random method, the index of a pickup node from pickups is randomly generated
     * in [1...|pickups|]
     * 3. Cheapest insertion first: this method inserts first the node with the cheapest insertion cost.
     * 4. Most expensive first: likewise, this method works similarly to the cheapest one, but inserts first the most
     * expensive nodes.
     */
    private BestPickup selectAPickup(Solution solution, int vehicle, List<Request> pickups, PickupMethod method, InsertionMethod insertionMethod) {
        BestPickup bestPickup = null;
        switch (method) {
            case Simple:
                bestPickup = new BestPickup(pickups.get(0), null);
                break;
            case Random:
                int pos = (int) (random.nextDouble() * pickups.size());
                bestPickup = new BestPickup(pickups.get(pos), null);
                break;
            case Cheapest:
            case Expensive:
                BestPosition bestPos;
                Map<Request, BestPosition> costs = new HashMap<>();
                for (Request pickup : pickups) {
                    bestPos = insertAtBestPosition(solution, vehicle, pickup.nodeId, insertionMethod, 0);
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
                randomNoise = (2 * random.nextDouble() * instance.getMaxDistance()) - instance.getMaxDistance();
                break;
        }
        return randomNoise;
    }

    private int getLastPickupIndex(ArrayList<Integer> route, int requestId) {
        int pos = 0;
        Request req;
        for (int i = 1; i < route.size() - 1; i++) {
            req = instance.getRequest(route.get(i));
            if (req.isPickup && req.requestId == requestId) {
                pos = i;
            }
        }
        return pos;
    }

    /*
     * In order to determine the best insertion position of node i in route k, Algorithm 5 iterates through all the nodes
     * already inserted in the route starting from depot node 0 until its last customer location. At each step, the algorithm
     * temporarily inserts i after a node j from k, computing the increase in routing costs \Delta_{i}^{k}.
     *
     * At the first iteration of the algorithm, node i is inserted between the depot node 0 and the first customer of route k.
     * It then computes the arrival time of k at i, verifying that the vehicle would visit i before bi given the current
     * insertion. Next, tnext is set to the vehicle arrival time at next assuming that k does not include i. Then, the vehicle
     * arrival time at next, namely t'next is computed, assuming that i is inserted in k. The added duration is used to verify
     * that if node i is visited, the vehicle would visit next before the end of it TW bnext. Also, the added duration is
     * compared with teh slack at next, that indicates to which extent the vehicle's visit to next would be postponed. If one
     * of these conditions is violated, the algorithm moves to the next iteration, to test the next insertion position.
     *
     * If all the conditions are verified, the insert of i in k at the position under evaluation (NewCost) is computed,
     * possibly adding a random noise. A temporary NewCost is compared against \Delta_{i}^{k*} to determine whether the
     * insertion of i in k improves the solution. If the condition is verified, the feasibility of the new solution is tested
     * by first inserting i in k to obtain S'. If S' generates a finite cost, the solution is feasible and the best known
     * insertion position of i in k is set to prev. Finally, \Delta_{i}^{k*} is set to the value of NewCost in line 23.
     */
    private BestPosition insertAtBestPosition(Solution solution, int vehicle, Integer node, InsertionMethod insertionMethod, int prevPos) {
        double deltaBestCost = Double.MAX_VALUE; // \Delta_{i}^{k*} <- Infinity
        BestPosition bestPosition = null; // BestPosition(i, k) <- null
        ArrayList<Integer> route = solution.tours.get(vehicle);
        int prev = route.get(prevPos); // prev <- the depot node 0
        prevPos++;
        int next = route.get(prevPos); // next <- first customer of route k
        int currIdx = prevPos;
        Request reqI = instance.getRequest(node);
        double t, tNext, tNewNext, addedDuration, newCost;
        boolean feasible;
        while (currIdx < route.size()) { // while prev <> p + n + 1
            feasible = true;
            t = solution.departureTime.get(vehicle).get(currIdx - 1) + instance.dist(prev, node); // t <- vehicleArrivalTimeAt(k, i)
            if (t > reqI.twEnd) { // if t > bi then
                break; // Exit algorithm 5
            }
            tNext = solution.departureTime.get(vehicle).get(currIdx - 1) + instance.dist(prev, next); // Set t_next the actual arrival time at next
            tNewNext = Math.max(t, reqI.twStart) + reqI.serviceTime + instance.dist(node, next); // t'_next <- arrival time at next if i is inserted before
            addedDuration = tNewNext - tNext; // addedDuration = t'_next - t_next
            if (tNext > twEnd(next) || addedDuration > solution.arrivalSlackTimes.get(vehicle).get(currIdx)) { // t_next > b_next  OR addedDuration > slack_next
                feasible = false;
            }
            if (feasible) {
                // NewCost <- C_prev,i + C_i,next - C_prev,next + generateRandomNoise()
                newCost = instance.dist(prev, node) + instance.dist(node, next) - instance.dist(prev, next) + generateRandomNoise(insertionMethod);
                if (newCost < deltaBestCost) {
                    route.add(currIdx, node); // Insert i after prev in the current solution S'
                    if (instance.restrictionsEvaluation(route).feasible) { // If S' is feasible then
                        deltaBestCost = newCost; // \Delta_{i}^{k*} <- NewCost
                        bestPosition = new BestPosition(currIdx, deltaBestCost); // BestPosition(i, k) <- prev
                    }
                    route.remove(currIdx);
                }
            }
            prev = next;
            if (prev == instance.getDepot().nodeId) {
                break;
            }
            currIdx++;
            next = route.get(currIdx);
        }
        return bestPosition;
    }

    private double twEnd(int next) {
        if (next == instance.getDepot().nodeId) {
            return instance.getDepot().twEnd;
        } else {
            return instance.getRequest(next).twEnd;
        }
    }

    // Remove empty vehicles
    private void removeEmptyVehicles(Solution solution) {
        int position = 0;
        while (solution.tours.size() > position) {
            if (solution.requests.get(position).isEmpty()) {
                solution.tours.remove(position);
                solution.requests.remove(position);
            } else {
                position++;
            }
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

    public class RouteCache {

        // Cache routes based on a given key. In this case the composition of vehicle and request.
        private Map<String, InsertRequest> cache = new HashMap<>();

        // Used o test if a vehicle with for a given request is infeasible
        private boolean isNull(int k, int r) {
            return cache.get(k + "-" + r) == null;
        }

        private ArrayList<Integer> getCacheRoute(int k, int r) {
            return cache.get(k + "-" + r).route;
        }

        private Double getCacheCost(int k, int r) {
            return cache.get(k + "-" + r).cost;
        }

        private void setCacheCost(int k, int r, ArrayList<Integer> route) {
            String key = k + "-" + r;
            if (!cache.containsKey(key)) {
                if (route != null) {
                    cache.put(key, new InsertRequest(instance.costEvaluation(route), k, r, route));
                } else {
                    cache.put(key, null);
                }
            }
        }

        private boolean hasCacheCost(int k, int r) {
            return cache.containsKey(k + "-" + r);
        }

        // Remove all cache entries associated with the given vehicle
        private void removeVehicleFromCache(int k) {
            String key = k + "-";
            Set<String> keySet = new HashSet<>(cache.keySet());
            for (String hashKey : keySet) {
                if (hashKey.startsWith(key)) {
                    cache.remove(hashKey);
                }
            }
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
