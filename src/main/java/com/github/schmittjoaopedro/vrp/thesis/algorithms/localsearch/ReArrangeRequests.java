package com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertPosition;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertionService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReArrangeRequests {

    private Instance instance;

    private InsertionService insertionService;

    public ReArrangeRequests(Instance instance) {
        this.instance = instance;
        this.insertionService = new InsertionService(instance);
    }

    public Solution reArrange(Solution solution) {
        Request request;
        InsertPosition insertPosition;
        Set<Integer> solutionHashes = new HashSet<>();
        Solution tempSol = SolutionUtils.copy(solution);
        RouteTimes routeTime;
        for (int k = 0; k < tempSol.tours.size(); k++) {
            tempSol.indexVehicle(k);
        }
        ArrayList<Integer> newTour;
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (Integer requestId = 0; requestId < instance.numRequests; requestId++) {
                request = instance.requests[requestId];
                if (request.isVehicleRelocatable()) {
                    int vehicle = tempSol.getVehicle(request.pickupTask.nodeId);
                    newTour = createTourWithoutRequest(tempSol.tours.get(vehicle), request);
                    routeTime = new RouteTimes(newTour, instance);
                    insertPosition = insertionService.calculateBestPosition(newTour, request, routeTime);
                    if (insertPosition.cost < Double.MAX_VALUE) {
                        int hash = getHashCost(insertPosition, vehicle);
                        if (!solutionHashes.contains(hash)) {
                            solutionHashes.add(hash);
                            double removalGain = tempSol.calculateRequestRemovalGain(instance, request);
                            if (insertPosition.cost < removalGain) {
                                // Update request in vehicle
                                tempSol.remove(Arrays.asList(requestId), instance);
                                tempSol.insert(instance, requestId, vehicle, insertPosition.pickupPos, insertPosition.deliveryPos);
                                tempSol.indexVehicle(vehicle);
                                improvement = true;
                            }
                        }
                    }
                }
            }
        }
        instance.solutionEvaluation(tempSol);
        return tempSol;
    }

    public ArrayList<Integer> createTourWithoutRequest(ArrayList<Integer> tour, Request request) {
        ArrayList<Integer> newTour = new ArrayList<>(tour.size() - 2);
        for (int i = 0; i < tour.size(); i++) {
            if (tour.get(i) != request.pickupTask.nodeId && tour.get(i) != request.deliveryTask.nodeId) {
                newTour.add(tour.get(i));
            }
        }
        return newTour;
    }

    public int getHashCost(InsertPosition insertPosition, Integer vehicle) {
        return ("-" + vehicle +
                "-" + MathUtils.round(insertPosition.cost) +
                "-" + insertPosition.deliveryPos +
                "-" + insertPosition.pickupPos).hashCode();
    }

}
