package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExpensiveVehicleRemoval extends RemovalOperator {

    public ExpensiveVehicleRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
    }

    /**
     * Takes a random vehicles and try to remove all requests from it. The objective is to minimize the
     * vehicles number.
     */
    @Override
    public List<Req> removeRequests(Solution solution, int noReq) {
        ArrayList<Req> removedRequests = new ArrayList<>();
        ArrayList<Req> assignedRequests = getAllRequests(solution.requests); // L a set of assigned requests in S
        int v = 0;
        int s = 0;
        double c = 0.0;
        for (int i = 0; i < solution.tours.size(); i++) {
            if (solution.tours.get(i).size() > s) {
                v = i;
                c = solution.tourCosts.get(i);
                s = solution.tours.size();
            } else if (solution.tours.get(i).size() == s && solution.tourCosts.get(i) > c) {
                v = i;
                c = solution.tourCosts.get(i);
                s = solution.tours.size();
            }
        }
        for (Req request : assignedRequests) { // For i = 1 to q do
            if (solution.requests.get(v).contains(request.requestId)) {
                removedRequests.add(request);  // L <- L\{r}
                removeRequest(solution.tours, solution.requests, request); // Un-assign all the nodes of request r in solution S
            }
        }
        return removedRequests;
    }
}