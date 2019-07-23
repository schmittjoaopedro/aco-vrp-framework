package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.*;

public class ExpensiveRemoval extends RemovalOperator {

    private static double D = 6; // According Coelho paper

    public ExpensiveRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
    }

    @Override
    public List<Req> removeRequests(Solution solution, int noReqToRemove) {
        ArrayList<Req> R = new ArrayList<>(); // R <- {r} : set of removed requests
        // array of requests with the cost of the most expensive nodes removal (gain of removal)
        ArrayList<Req> assignedRequests = getRequestsCosts(solution.tours, solution.requests);
        assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
        while (R.size() < noReqToRemove) {
            double y = random.nextDouble(); // y <- a randomly number between 0 and 1
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            Req r = assignedRequests.get(requestD);
            R.add(r); // R <- R U {L[y^D|L|]}
            assignedRequests.remove(r); // L <- L \{r}
            removeRequest(solution.tours, solution.requests, r); // Un-assign all the nodes of request L[y^D|L|] in solution S
            updateChangedVehicle(solution.tours, solution.requests, r, assignedRequests); // Update vehicle that had their route changed
        }
        return R;
    }

    // Update cost of the changed vehicle
    private void updateChangedVehicle(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, Req r, ArrayList<Req> assignedRequests) {
        int i = 0;
        Map<Integer, RemovalRequest> requestMap = getRequestsCostsByCriteria(solution.get(r.vehicleId), requests.get(r.vehicleId)); // Recalculate the requests costs for the current vehicle
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
     * Return an array of requests ordered by the relatedness of the most expensive requests by their nodes (cost gain in
     * removing the request with the most expensive node from the route).
     */
    private ArrayList<Req> getRequestsCosts(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < solution.size(); k++) { // for each vehicle k in vehicles from solution
            Map<Integer, RemovalRequest> requestMap = getRequestsCostsByCriteria(solution.get(k), requests.get(k));
            for (RemovalRequest removalRequest : requestMap.values()) {
                assignedRequests.add(new Req(k, removalRequest.requestId, removalRequest.cost));
            }
        }
        return assignedRequests;
    }

    protected Map<Integer, RemovalRequest> getRequestsCostsByCriteria(ArrayList<Integer> route, ArrayList<Integer> requests) {
        throw new RuntimeException("Not implemented yet");
    }

    protected class RemovalRequest {

        protected int requestId;

        protected double cost;

        protected RemovalRequest(int requestId, double cost) {
            this.requestId = requestId;
            this.cost = cost;
        }

        protected int getRequestId() {
            return requestId;
        }

        protected double getCost() {
            return cost;
        }
    }
}
