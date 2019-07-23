package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;

import java.util.*;

public class ShawRemoval extends RemovalOperator {

    private static double D = 6; // According Coelho paper

    private RelateMethod relateMethod = RelateMethod.Coelho;

    public ShawRemoval(Random random, ProblemInstance instance) {
        super(random, instance);
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
    @Override
    public List<Req> removeRequests(Solution solution, int noReqToRemove) {
        ArrayList<Req> R = new ArrayList<>(); // R <- {r} : set of removed requests
        Req r = selectRandomRequestFromSolution(solution.requests); // r <- a randomly chosen request in S
        Set<Integer> removedReqs = new HashSet<>();
        R.add(r);
        removedReqs.add(r.requestId);
        removeRequest(solution.tours, solution.requests, r); // Un-assign all the nodes of request r in solution S
        while (R.size() < noReqToRemove) {
            r = R.get((int) (random.nextDouble() * R.size())); // r <- a randomly chosen request in R
            ArrayList<Req> assignedRequests = getMostRelatedRequests(r, solution, removedReqs); // an array of assigned requests in S
            assignedRequests.sort(Comparator.comparing(Req::getCost)); // Sort L such that for i < j => G(r_i, r, S) < g(r_j, r, S)
            double y = random.nextDouble(); // y <- a randomly number between 0 and 1
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            r = assignedRequests.get(requestD);
            removeRequest(solution.tours, solution.requests, r); // Un-assign all the nodes of request L[y^D|L|] in solution S
            R.add(r); // R <- R U {L[y^D|L|]}
            removedReqs.add(r.requestId);
        }
        return R;
    }

    /*
     * Return an array of request ordered by the relatedness (distance between delivery points)
     * with the request parameter.
     */
    private ArrayList<Req> getMostRelatedRequests(Req request, Solution solution, Set<Integer> removedReqs) {
        Request reqI = instance.getDelivery(request.requestId);
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < solution.requests.size(); k++) { // For each vehicle k in vehicles
            for (int r = 0; r < solution.requests.get(k).size(); r++) { // For each request r in vehicle k
                Request reqJ = instance.getDelivery(solution.requests.get(k).get(r));
                if (reqI.requestId != reqJ.requestId && !removedReqs.contains(reqJ.requestId)) {
                    double relate = 0.0;
                    if (RelateMethod.Coelho.equals(relateMethod)) {
                        // Get the distance between the delivery points between the request parameter and the current request r of vehicle k (coelho)
                        relate = instance.dist(reqI.nodeId, reqJ.nodeId);
                    } else if (RelateMethod.RopkeCoelho.equals(relateMethod)) {
                        // Relate distance based on Ropke and Coelho
                        double relateDeliveryDist = instance.dist(reqI.nodeId, reqJ.nodeId) / instance.getMaxDistance();
                        double relateTw = Math.abs(reqI.twStart - reqJ.twStart) / solution.maxTime;
                        double relateDemand = Math.abs(reqI.demand - reqJ.demand) / instance.getMaxDemand();
                        relate = 9.0 * relateDeliveryDist + 3.0 * relateTw + 2.0 * relateDemand;
                    }
                    assignedRequests.add(new Req(k, reqJ.requestId, relate)); // Add the request with the related cost to the list of assigned requests
                }
            }
        }
        return assignedRequests;
    }

    private Req selectRandomRequestFromSolution(ArrayList<ArrayList<Integer>> requests) {
        int rndVehicle = (int) (random.nextDouble() * requests.size()); // Select a random vehicle
        int rndRequest = (int) (random.nextDouble() * requests.get(rndVehicle).size()); // Select a random request from the vehicle
        return new Req(rndVehicle, requests.get(rndVehicle).get(rndRequest));
    }

    public enum RelateMethod {
        Coelho, RopkeCoelho
    }

    public void setRelateMethod(RelateMethod relateMethod) {
        this.relateMethod = relateMethod;
    }
}
