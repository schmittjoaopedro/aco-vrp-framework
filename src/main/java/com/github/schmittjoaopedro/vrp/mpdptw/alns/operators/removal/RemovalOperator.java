package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Removal operators allow a solution to be changed by partially destroying it, removing some requests from the routes
 * of some vehicles. We define a set RO of removal operators containing four different heuristics to remove nodes from
 * a solution. The number "q" of requests to remove is drawn between limits established in Section 4, while we set
 * alpha as the number of requests already assigned to a route.
 */
public class RemovalOperator {

    protected Random random;

    protected ProblemInstance instance;

    public RemovalOperator(Random random, ProblemInstance instance) {
        this.random = random;
        this.instance = instance;
    }

    public List<Req> removeRequests(Solution solution, int noReq) {
        throw new RuntimeException("Not implemented yet");
    }

    protected ArrayList<Req> getAllRequests(ArrayList<ArrayList<Integer>> requests) {
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

    protected void removeRequest(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, Req request) {
        if (instance.isFullyIdle(request.requestId)) {
            // Remove all pickups node from solution
            for (Request req : instance.getPickups(request.requestId)) {
                removeItem(solution.get(request.vehicleId), req.nodeId);
            }
            // Remove the delivery node from solution
            removeItem(solution.get(request.vehicleId), instance.getDelivery(request.requestId).nodeId);
            // Remove request id from solution
            removeItem(requests.get(request.vehicleId), request.requestId);
        }
    }

    // Using node as Integer object type allows to remove from array using object reference
    protected void removeItem(List<Integer> array, Integer node) {
        array.remove(node);
    }
}
