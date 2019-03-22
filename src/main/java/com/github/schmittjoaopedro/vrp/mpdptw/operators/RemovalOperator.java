package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.*;

/**
 * Removal operators allow a solution to be changed by partially destroying it, removing some requests from the routes
 * of some vehicles. We define a set RO of removal operators containing four different heuristics to remove nodes from
 * a solution. The number "q" of requests to remove is drawn between limits established in Section 4, while we set
 * alpha as the number of requests already assigned to a route.
 */
public class RemovalOperator {

    private ProblemInstance instance;

    private Random random;

    private static double D = 6; // According Coelho paper

    public RemovalOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    /*
     * Random removal: here we randomly remove q requests from a solution. The motivation behind this operator is to
     * increase the diversity. At the beginning, the set L contains all the assigned request in solution S. At each
     * iteration, a request r is randomly selected in the list. All nodes are removed from solution S and r is removed
     * from L too.
     */
    public List<Req> removeRandomRequest(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> removedRequests = new ArrayList<>();
        ArrayList<Req> assignedRequests = getAllRequests(requests); // L a set of assigned requests in S
        while (removedRequests.size() < noReqToRemove) { // For i = 1 to q do
            int r = (int) (random.nextDouble() * assignedRequests.size()); // a randomly chosen request in L
            Req request = assignedRequests.get(r);
            removedRequests.add(request);  // L <- L\{r}
            assignedRequests.remove(request);
            removeRequest(solution, requests, request); // Un-assign all the nodes of request r in solution S
        }
        removeEmptyVehicles(solution, requests);
        return removedRequests;
    }

    /*
     * Shaw removal: this operator removes clusters of requests that are related one to each other. A seed request is
     * randomly selected, ant the its q-1 most related neighbors are removed from the solution S. The idea o shaw removal
     * is to remove requests that are similar to each other, in the hope that they can be all inserted elsewhere in a
     * more profitable position. First, a function g(r_1, r_2, S) that measures the relatedness between two requests
     * by means of distance between the delivery nodes is defined. Second, an assigned request r is randomly selected
     * from the solution. The nodes of r are removed from the solution and the request r is stored inside the set of
     * removed requests R. Finally, the removal loop occurs. A request r is randomly selected in R. Assigned requests
     * from S are put inside the set o assigned requests S and sorted accordingly g(). A random number between 0 and 1
     * is chosen. The request located in position y^D is removed from the solution and added to R. D is a parameter set to 6.
     */
    public List<Req> removeShawRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> R = new ArrayList<>(); // R <- {r} : set of removed requests
        Req r = selectRandomRequestFromSolution(requests); // r <- a randomly chosen request in S
        R.add(r);
        removeRequest(solution, requests, r); // Un-assign all the nodes of request r in solution S
        while (R.size() < noReqToRemove) {
            r = R.get((int) (random.nextDouble() * R.size())); // r <- a randomly chosen request in R
            ArrayList<Req> assignedRequests = getMostRelatedRequests(r, requests); // an array of assigned requests in S
            assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
            double y = random.nextDouble(); // y <- a randomly number between 0 and 1
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            r = assignedRequests.get(requestD);
            removeRequest(solution, requests, r); // Un-assign all the nodes of request L[y^D|L|] in solution S
            R.add(r); // R <- R U {L[y^D|L|]}
        }
        removeEmptyVehicles(solution, requests);
        return R;
    }

    /*
     * Most expensive nodes: here we identify the nodes that lead to the biggest savings when removed from the solution, in
     * the hope that a less expensive insertion position can be found for them later. This operator can be seen as a variant
     * of the shaw removal where the relatedness between to requests g(r_1, r_2, S) is the cost of removing the most expensive
     * arcs connecting a request of r_1. The final value is multiplied by -1 to have the most expensive requests in the first
     * position of the array.
     * Accordingly email from Jean-François Côté I can ignore the step 5 from algorithm 3.
     */
    public List<Req> removeMostExpensiveNodes(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> R = new ArrayList<>(); // R <- {r} : set of removed requests
        Req r = selectRandomRequestFromSolution(requests); // r <- a randomly chosen request in S
        R.add(r);
        removeRequest(solution, requests, r); // Un-assign all the nodes of request r in solution S
        // array of requests with the cost of the most expensive nodes removal (gain of removal)
        ArrayList<Req> assignedRequests = getMostExpensiveNodesRequests(solution);
        assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
        while (R.size() < noReqToRemove) {
            double y = random.nextDouble(); // y <- a randomly number between 0 and 1
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            r = assignedRequests.get(requestD);
            R.add(r); // R <- R U {L[y^D|L|]}
            assignedRequests.remove(r); // L <- L \{r}
            removeRequest(solution, requests, r); // Un-assign all the nodes of request L[y^D|L|] in solution S
            updateChangedVehicles(solution, r, assignedRequests); // Update vehicle that had their route changed
        }
        removeEmptyVehicles(solution, requests); // Remove empty vehicles at the end, to evict update indices during algorithm execution
        return R;
    }

    private void updateChangedVehicles(ArrayList<ArrayList<Integer>> solution, Req r, ArrayList<Req> assignedRequests) {
        int i = 0;
        Map<Integer, RemovalRequest> requestMap = getRequestsCostsByExpensiveNode(solution.get(r.vehicleId)); // Recalculate the requests costs for the current vehicle
        while (i < assignedRequests.size()) {
            if (assignedRequests.get(i).vehicleId == r.vehicleId) { // If still exists requests associated with the given vehicle
                RemovalRequest removalRequest = requestMap.get(assignedRequests.get(i).requestId);
                Req req = new Req(r.vehicleId, removalRequest.requestId, removalRequest.cost); // Set the updated cost for the given request
                assignedRequests.set(i, req);
            }
            i++;
        }
        assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
    }

    /*
     * Most expensive requests: this operator is similar to the most expensive nodes, but act on full requests instead of
     * individual nodes. This operator can be seen as a variant of shaw removal where the relatedness of two requests g(r1, r2, S)
     * is the cost of removing request r1 from S. The final value is multiplied by -1 to have the most expensive requests in the
     * first positions of the array.
     * Accordingly email from Jean-François Côté I can ignore the step 5 from algorithm 3.
     */
    public List<Req> removeExpensiveRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> R = new ArrayList<>();  // R <- {r} : set of removed requests
        Req r = selectRandomRequestFromSolution(requests); // r <- a randomly chosen request in S
        R.add(r);
        removeRequest(solution, requests, r); // Un-assign all the nodes of request r in solution S
        while (R.size() < noReqToRemove) {
            // Array of requests with the cost of the most expensive requests (removal gain)
            ArrayList<Req> assignedRequests = getMostExpensiveRequests(solution, requests);
            assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
            double y = random.nextDouble(); // y <- a randomly number between 0 and 1
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            r = assignedRequests.get(requestD);
            removeRequest(solution, requests, r); // Un-assign all the nodes of request L[y^D|L|] in solution S
            R.add(r); // R <- R U {L[y^D|L|]}
        }
        removeEmptyVehicles(solution, requests);
        return R;
    }

    // Remove vehicles with empty request list
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

    /*
     * Return an array of request ordered by the relatedness (distance between delivery points)
     * with the request parameter.
     */
    private ArrayList<Req> getMostRelatedRequests(Req request, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < requests.size(); k++) { // For each vehicle k in vehicles
            for (int r = 0; r < requests.get(k).size(); r++) { // For each request r in vehicle k
                int reqId = requests.get(k).get(r);
                if (reqId != request.requestId) {
                    // Get the distance between the delivery points between the request parameter and the current request r of vehicle k
                    double relatedCost = instance.distances[instance.delivery.get(request.requestId).nodeId][instance.delivery.get(reqId).nodeId];
                    assignedRequests.add(new Req(k, reqId, relatedCost)); // Add the request with the related cost to the list of assigned requests
                }
            }
        }
        return assignedRequests;
    }

    /*
     * Return an array of requests ordered by the relatedness of the most expensive requests (cost gain in removing a request from the route).
     */
    private ArrayList<Req> getMostExpensiveRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < requests.size(); k++) { // For each vehicle k of vehicles from S
            double originalRoutCost = instance.costEvaluation(solution.get(k)); // Calculate the current vehicle k route cost
            for (int r = 0; r < requests.get(k).size(); r++) { // For each request of vehicle k
                int requestId = requests.get(k).get(r); // obtain the request id
                double newRouteCost = instance.costEvaluation(solution.get(k), requestId); // calculate the route cost ignoring the current request r
                double gain = (originalRoutCost - newRouteCost) * -1.0; // calculate the gain of removing request r
                assignedRequests.add(new Req(k, requestId, gain));
            }
        }
        return assignedRequests;
    }

    /*
     * Return an array of requests ordered by the relatedness of the most expensive requests by their nodes (cost gain in
     * removing the request with the most expensive node from the route).
     */
    private ArrayList<Req> getMostExpensiveNodesRequests(ArrayList<ArrayList<Integer>> solution) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < solution.size(); k++) { // for each vehicle k in vehicles from solution
            Map<Integer, RemovalRequest> requestMap = getRequestsCostsByExpensiveNode(solution.get(k));
            for (RemovalRequest removalRequest : requestMap.values()) {
                assignedRequests.add(new Req(k, removalRequest.requestId, removalRequest.cost));
            }
        }
        return assignedRequests;
    }

    private Map<Integer, RemovalRequest> getRequestsCostsByExpensiveNode(ArrayList<Integer> route) {
        Map<Integer, RemovalRequest> requestsMap = new HashMap<>();
        for (int i = 1; i < route.size() - 1; i++) { // for each node i from route of vehicle k (ignoring depots)
            int prev = route.get(i - 1); // Get previous node
            int curr = route.get(i); // Get current node
            int next = route.get(i + 1); // get next node
            int requestId = instance.requests[curr - 1].requestId;
            double cost = instance.distances[prev][curr] + instance.distances[curr][next]; // Calculate the cost of (prev->curr->next)
            double deltaCost = cost - instance.distances[prev][next]; // Calculate the gain in remove the current node (prev->next)
            if (!requestsMap.containsKey(requestId) || deltaCost > requestsMap.get(requestId).cost) {
                requestsMap.put(requestId, new RemovalRequest(requestId, deltaCost));
            }
        }
        for (RemovalRequest removalRequest : requestsMap.values()) {
            removalRequest.cost = removalRequest.cost * -1.0;
        }
        return requestsMap;
    }

    private ArrayList<Req> getAllRequests(ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int rId;
        for (int k = 0; k < requests.size(); k++) {
            for (int r = 0; r < requests.get(k).size(); r++) {
                rId = requests.get(k).get(r);
                assignedRequests.add(new Req(k, rId, 0.0));
            }
        }
        return assignedRequests;
    }

    private Req selectRandomRequestFromSolution(ArrayList<ArrayList<Integer>> requests) {
        int rndVehicle = (int) (random.nextDouble() * requests.size()); // Select a random vehicle
        int rndRequest = (int) (random.nextDouble() * requests.get(rndVehicle).size()); // Select a random request from the vehicle
        return new Req(rndVehicle, requests.get(rndVehicle).get(rndRequest));
    }

    private void removeRequest(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, Req request) {
        // Remove all pickups node from solution
        for (Request req : instance.pickups.get(request.requestId)) {
            removeItem(solution.get(request.vehicleId), req.nodeId);
        }
        // Remove the delivery node from solution
        removeItem(solution.get(request.vehicleId), instance.delivery.get(request.requestId).nodeId);
        // Remove request id from solution
        removeItem(requests.get(request.vehicleId), request.requestId);
    }

    private void removeItem(List<Integer> array, int item) {
        int position = -1;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == item) {
                position = i;
                break;
            }
        }
        array.remove(position);
    }

    public class RemovalRequest {

        private int requestId;

        private double cost;

        public RemovalRequest(int requestId, double cost) {
            this.requestId = requestId;
            this.cost = cost;
        }

        public int getRequestId() {
            return requestId;
        }

        public double getCost() {
            return cost;
        }
    }

}
