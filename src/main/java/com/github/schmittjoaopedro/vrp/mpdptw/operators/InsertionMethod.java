package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;

import java.util.*;

public class InsertionMethod {

    protected ProblemInstance instance;

    protected Random random;

    protected double useNoiseAtBestPosition = 0.0;

    public InsertionMethod(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    /*
     * This method is similar to the insertRequestOnVehicle, but it tries to improve the idle nodes from the current vehicle and request.
     * Expect that the request nodes were not removed from the solution, as this method will take care of this internally.
     * If a feasible solution is found it is kept, in the other case the original route is restored.
     */
    public boolean improveRequestOnVehicle(Solution solution, int vehicle, int requestToImprove, PickupMethod pickupMethod) {
        if (!instance.getDelivery(requestToImprove).isIdle()) {
            throw new RuntimeException("There is no idle request to insert on vehicle");
        }
        BestPosition bestPosition = null;
        Request delivery = instance.getDelivery(requestToImprove);
        ArrayList<Integer> originalRoute = new ArrayList(solution.tours.get(vehicle));
        List<Request> pickups = new ArrayList<>(); // pickups <- Pr // Ignore visited pickups for dynamic problems
        // Remove all idle nodes of the request from the vehicle
        for (Request pickup : instance.getPickups(requestToImprove)) {
            if (pickup.isIdle()) {
                SolutionUtils.removeNode(pickup.nodeId, solution.tours.get(vehicle));
                pickups.add(pickup);
            }
        }
        SolutionUtils.removeNode(delivery.nodeId, solution.tours.get(vehicle));
        // Update the solution cost without the idle nodes
        instance.solutionEvaluation(solution, vehicle);
        boolean feasible = true;
        while (feasible && !pickups.isEmpty()) { // while pickups <> null do
            BestPickup pickup = selectAPickup(solution, vehicle, pickups, pickupMethod); // pi <- selectAPickup(pickups, method)
            if (pickup == null) {
                feasible = false;
                break;
            }
            pickups.remove(pickup.pickupNode); // pickups <- pickups\{p}
            switch (pickupMethod) {
                case Simple:
                case Random:
                    // BestPosition(pi, k) <- Insert pi at its best insertion position in k
                    bestPosition = insertAtBestPosition(solution, vehicle, pickup.pickupNode.nodeId, 0);
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
            bestPosition = insertAtBestPosition(solution, vehicle, delivery.nodeId, getLastPickupIndex(solution.tours.get(vehicle), requestToImprove));
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
     * While there are nodes in the pickup set, the algorithm selects a pickup node pi \in Pr using one of th pickups
     * selection methods described in Section 3.3.1. Then the best position of pi in k is found (BestPosition(pi, l))
     * i found by applying Algorithm 5, and the it is removed from the pickups set. Once all pickups are inserted, the
     * delivery node dr is inserted at its best position in route k. If the insertion of node i \in {Pr U {Dr}} is not
     * possible, the algorithm is interrupted and BestPosition(i, k) returns null.
     */
    public boolean insertRequestOnVehicle(Solution solution, int vehicle, int requestToInsert, PickupMethod pickupMethod) {
        if (!instance.getDelivery(requestToInsert).isIdle()) {
            throw new RuntimeException("There is no idle request to insert on vehicle");
        }
        BestPosition bestPosition = null;
        instance.solutionEvaluation(solution, vehicle);
        ArrayList<Integer> originalRoute = new ArrayList(solution.tours.get(vehicle));
        List<Request> pickups = new ArrayList<>(instance.getPickups(requestToInsert)); // pickups <- Pr
        Request delivery = instance.getDelivery(requestToInsert);
        boolean feasible = true;
        while (feasible && !pickups.isEmpty()) { // while pickups <> null do
            BestPickup pickup = selectAPickup(solution, vehicle, pickups, pickupMethod); // pi <- selectAPickup(pickups, method)
            if (pickup == null) {
                feasible = false;
                break;
            }
            if (pickup.pickupNode.isIdle()) { // Considers pickups not yet visited
                pickups.remove(pickup.pickupNode); // pickups <- pickups\{p}
                switch (pickupMethod) {
                    case Simple:
                    case Random:
                        // BestPosition(pi, k) <- Insert pi at its best insertion position in k
                        bestPosition = insertAtBestPosition(solution, vehicle, pickup.pickupNode.nodeId, 0);
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
        }
        if (feasible) {
            // BestPosition(dr, k) <- Insert dr at its best insertion position in k
            bestPosition = insertAtBestPosition(solution, vehicle, delivery.nodeId, getLastPickupIndex(solution.tours.get(vehicle), requestToInsert));
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
    private BestPickup selectAPickup(Solution solution, int vehicle, List<Request> pickups, PickupMethod method) {
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
                    bestPos = insertAtBestPosition(solution, vehicle, pickup.nodeId, 0);
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
    public BestPosition insertAtBestPosition(Solution solution, int vehicle, Integer node, int prevPos) {
        double deltaBestCost = Double.MAX_VALUE; // \Delta_{i}^{k*} <- Infinity
        BestPosition bestPosition = null; // BestPosition(i, k) <- null
        ArrayList<Integer> route = solution.tours.get(vehicle);
        int prev = route.get(prevPos); // prev <- the depot node 0
        int next = route.get(++prevPos); // next <- first customer of route k
        int currIdx = prevPos;
        Request reqI = instance.getRequest(node);
        double t, tNext, tNewNext, addedDuration, newCost;
        boolean feasible;
        while (currIdx < route.size()) { // while prev <> p + n + 1
            feasible = true;
            t = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, node); // t <- vehicleArrivalTimeAt(k, i)
            if (t > reqI.twEnd) { // if t > bi then
                break; // Exit algorithm 5
            }
            tNext = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, next); // Set t_next the actual arrival time at next
            tNewNext = Math.max(t, reqI.twStart) + reqI.serviceTime + instance.dist(node, next); // t'_next <- arrival time at next if i is inserted before
            addedDuration = tNewNext - tNext; // addedDuration = t'_next - t_next
            if (tNext > twEnd(next) || addedDuration > solution.arrivalSlackTimes.get(vehicle)[currIdx] || !instance.isIdle(next)) { // t_next > b_next  OR addedDuration > slack_next
                feasible = false;
            }
            if (feasible) {
                // NewCost <- C_prev,i + C_i,next - C_prev,next + generateRandomNoise()
                newCost = instance.dist(prev, node) + instance.dist(node, next) - instance.dist(prev, next) + (generateRandomNoise() * useNoiseAtBestPosition);
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

    private double twEnd(int next) {
        if (next == instance.getDepot().nodeId) {
            return instance.getDepot().twEnd;
        } else {
            return instance.getRequest(next).twEnd;
        }
    }

    protected double generateRandomNoise() {
        return (2 * random.nextDouble() * instance.getMaxDistance()) - instance.getMaxDistance();
    }

    public enum PickupMethod {
        Simple, Random, Cheapest, Expensive
    }

}
