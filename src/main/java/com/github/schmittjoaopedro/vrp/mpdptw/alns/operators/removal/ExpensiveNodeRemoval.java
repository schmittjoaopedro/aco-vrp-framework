package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.*;

public class ExpensiveNodeRemoval extends ExpensiveRemoval {

    public ExpensiveNodeRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
    }

    /*
     * Most expensive nodes: here we identify the nodes that lead to the biggest savings when removed from the solution, in
     * the hope that a less expensive insertion position can be found for them later. This operator can be seen as a variant
     * of the shaw removal where the relatedness between to requests g(r_1, r_2, S) is the cost of removing the most expensive
     * arcs connecting a request of r_1. The final value is multiplied by -1 to have the most expensive requests in the first
     * position of the array.
     * Accordingly email from Jean-François Côté I can ignore the step 5 from algorithm 3.
     */
    @Override
    public List<Req> removeRequests(Solution solution, int noReqToRemove) {
        return super.removeRequests(solution, noReqToRemove);
    }

    @Override
    protected Map<Integer, RemovalRequest> getRequestsCostsByCriteria(ArrayList<Integer> route, ArrayList<Integer> requests) {
        Map<Integer, RemovalRequest> requestsMap = new HashMap<>();
        double gain;
        double routeCost = instance.costEvaluation(route); // Calculate the cost of the original route
        for (int requestId : requests) {
            double cost = instance.costEvaluation(route, requestId, instance.getDelivery(requestId).nodeId); // Route cost without delivery node
            for (Request req : instance.getPickups(requestId)) {
                double newCost = instance.costEvaluation(route, requestId, req.nodeId); // Route cost without pickup node
                if (newCost < cost) { // Store the lowest cost based on node removal
                    cost = newCost;
                }
            }
            gain = routeCost - cost; // Calculate the gain on remove this request based on node
            requestsMap.put(requestId, new RemovalRequest(requestId, gain));
        }
        for (RemovalRequest removalRequest : requestsMap.values()) {
            removalRequest.cost = removalRequest.cost * -1.0; // Multiply by negative to put best gains in front of the list
        }
        return requestsMap;
    }
}
