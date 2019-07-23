package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRemoval extends RemovalOperator {

    public RandomRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
    }

    /*
     * Random removal: here we randomly remove q requests from a solution. The motivation behind this operator is to
     * increase the diversity. At the beginning, the set L contains all the assigned request in solution S. At each
     * iteration, a request r is randomly selected in the list. All nodes are removed from solution S and r is removed
     * from L too.
     */
    @Override
    public List<Req> removeRequests(Solution solution, int noReqToRemove) {
        ArrayList<Req> removedRequests = new ArrayList<>();
        ArrayList<Req> assignedRequests = getAllRequests(solution.requests); // L a set of assigned requests in S
        while (removedRequests.size() < noReqToRemove) { // For i = 1 to q do
            int r = (int) (random.nextDouble() * assignedRequests.size()); // a randomly chosen request in L
            Req request = assignedRequests.get(r);
            removedRequests.add(request);  // L <- L\{r}
            assignedRequests.remove(request);
            removeRequest(solution.tours, solution.requests, request); // Un-assign all the nodes of request r in solution S
        }
        return removedRequests;
    }
}
