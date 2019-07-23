package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.*;

public class ExpensiveRequestRemoval extends ExpensiveRemoval {

    public ExpensiveRequestRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
    }

    /*
     * Most expensive requests: this operator is similar to the most expensive nodes, but act on full requests instead of
     * individual nodes. This operator can be seen as a variant of shaw removal where the relatedness of two requests g(r1, r2, S)
     * is the cost of removing request r1 from S. The final value is multiplied by -1 to have the most expensive requests in the
     * first positions of the array.
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
            gain = routeCost - instance.costEvaluation(route, requestId); // Calculate the gain on remove fully the current request
            requestsMap.put(requestId, new RemovalRequest(requestId, gain));
        }
        for (RemovalRequest removalRequest : requestsMap.values()) {
            removalRequest.cost = removalRequest.cost * -1.0; // Multiply by negative to put best gains in front of the list
        }
        return requestsMap;
    }
}
