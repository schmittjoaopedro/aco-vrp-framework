package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GreedyInsertion extends InsertionOperator {

    public GreedyInsertion(ProblemInstance instance, Random random) {
        super(instance, random, false);
    }

    public GreedyInsertion(ProblemInstance instance, Random random, boolean insertionNoise) {
        super(instance, random, insertionNoise);
    }

    /*
     * Greedy insertion: select a remaining request and inserted it based on a greedy criterion, namely in the position
     * yielding the lowest increase in the objective function.
     */
    @Override
    public void insertRequests(Solution solution, List<Req> requestsToInsert, PickupMethod pickupMethod, double heuristicNoiseControl) {
        while (!requestsToInsert.isEmpty()) {
            InsertionOperator.InsertRequest bestRequest = null;
            for (int r = 0; r < requestsToInsert.size(); r++) { // For each request r in requests to insert
                Req currReq = requestsToInsert.get(r);
                InsertionOperator.InsertRequest insertRequest = null;
                if (instance.isFullyIdle(currReq.requestId)) { // If all nodes were not visited by vehicle yet
                    if (instance.allowAddVehicles() && solution.tours.get(solution.tours.size() - 1).size() > 2) {
                        // Create a new vehicle to let available to the greedy operator
                        SolutionUtils.addEmptyVehicle(solution);
                    }
                    for (int k = 0; k < solution.tours.size(); k++) { // For each vehicle from solution
                        double prevCost = solution.tourCosts.get(k); // Evaluate the current vehicle route cost
                        ArrayList<Integer> originalRoute = new ArrayList<>(solution.tours.get(k)); // Clone the route from vehicle k to evict update the original one
                        if (insertionMethod.insertRequestOnVehicle(solution, k, currReq.requestId, pickupMethod)) { // If the request insertion is feasible
                            double costIncrease = (solution.tourCosts.get(k) - prevCost); // Calculate the lost of insert request r in vehicle k
                            costIncrease += generateRandomNoise(heuristicNoiseControl);
                            // If a new best insertion was found, hold this reference (request yielding the lowest increase in the objective function)
                            if (insertRequest == null || costIncrease < insertRequest.cost) {
                                insertRequest = new InsertionOperator.InsertRequest(costIncrease, k, currReq.requestId, solution.tours.get(k));
                            }
                            solution.tours.set(k, originalRoute);
                            instance.solutionEvaluation(solution, k);
                        }
                    }
                }
                // There are nodes that were visited by the current vehicle. In this case we must to keep these request in the same vehicle.
                // If the delivery is already not visited, it also indicates that can exist pickups not visited. In the other case, the request
                // was fully visited.
                else if (instance.getDelivery(currReq.requestId).isIdle()) {
                    // Optimize only the vehicle that holds the request
                    instance.solutionEvaluation(solution, currReq.vehicleId);
                    double prevCost = solution.tourCosts.get(currReq.vehicleId);
                    ArrayList<Integer> originalRoute = new ArrayList<>(solution.tours.get(currReq.vehicleId));
                    if (insertionMethod.improveRequestOnVehicle(solution, currReq.vehicleId, currReq.requestId, pickupMethod)) { // If the request insertion is feasible
                        double costIncrease = (solution.tourCosts.get(currReq.vehicleId) - prevCost); // Calculate the lost of insert request r in vehicle k
                        costIncrease += generateRandomNoise(heuristicNoiseControl);
                        // If a new best insertion was found, hold this reference (request yielding the lowest increase in the objective function)
                        if (insertRequest == null || costIncrease < insertRequest.cost) {
                            insertRequest = new InsertionOperator.InsertRequest(costIncrease, currReq.vehicleId, currReq.requestId, solution.tours.get(currReq.vehicleId));
                        }
                        solution.tours.set(currReq.vehicleId, originalRoute);
                        instance.solutionEvaluation(solution, currReq.vehicleId);
                    }
                }
                // Greedy criterion, select the request with the minimum increasing cost
                if (insertRequest != null && (bestRequest == null || insertRequest.cost < bestRequest.cost)) {
                    bestRequest = insertRequest;
                }
            }
            if (bestRequest == null) { // No found position for the remaining requests
                break;
            } else {
                // Add the inserted request on the vehicle
                solution.tours.set(bestRequest.vehicle, bestRequest.route);
                SolutionUtils.addRequest(bestRequest.reqId, bestRequest.vehicle, solution);
                instance.solutionEvaluation(solution, bestRequest.vehicle);
                for (Req r : requestsToInsert) {
                    if (r.requestId == bestRequest.reqId) {
                        requestsToInsert.remove(r);
                        break;
                    }
                }
            }
        }
        instance.solutionEvaluation(solution);
    }

}
