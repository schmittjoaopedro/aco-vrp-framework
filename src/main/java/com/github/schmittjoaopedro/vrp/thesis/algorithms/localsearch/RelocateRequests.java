package com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertPosition;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertionService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Arrays;

public class RelocateRequests {

    private Instance instance;

    private InsertionService insertionService;

    public RelocateRequests(Instance instance) {
        this.instance = instance;
        this.insertionService = new InsertionService(instance);
    }

    public Solution relocate(Solution solution) {
        int bestVehicle = -1;
        Request request;
        InsertPosition bestInsertion, insertPosition;
        Solution tempSol = SolutionUtils.createSolution(instance);
        SolutionUtils.copyFromTo(solution, tempSol);
        RouteTimes[] routeTimes = new RouteTimes[tempSol.tours.size()];
        for (int k = 0; k < tempSol.tours.size(); k++) {
            routeTimes[k] = new RouteTimes(tempSol.tours.get(k).size());
            insertionService.calculateRouteTimes(tempSol.tours.get(k), routeTimes[k]);
            tempSol.indexVehicle(k);
        }
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (Integer requestId = 0; requestId < instance.numRequests; requestId++) {
                request = instance.requests[requestId];
                int vehicle = tempSol.getVehicle(request.pickupTask.nodeId);
                bestInsertion = new InsertPosition();
                for (int k = 0; k < tempSol.tours.size(); k++) {
                    if (k != vehicle) {
                        insertPosition = insertionService.calculateBestPosition(tempSol.tours.get(k), request, routeTimes[k]);
                        if (insertPosition.cost < bestInsertion.cost) {
                            bestInsertion = insertPosition;
                            bestVehicle = k;
                        }
                    }
                }
                if (bestInsertion.cost < Double.MAX_VALUE) {
                    double removalGain = tempSol.calculateRequestRemovalGain(instance, request);
                    if (bestInsertion.cost < removalGain) {
                        // Remove request from old vehicle
                        tempSol.remove(Arrays.asList(requestId), instance);
                        routeTimes[vehicle] = new RouteTimes(tempSol.tours.get(vehicle).size());
                        insertionService.calculateRouteTimes(tempSol.tours.get(vehicle), routeTimes[vehicle]);
                        tempSol.indexVehicle(vehicle);
                        // Insert request on new vehicle
                        tempSol.insert(instance, requestId, bestVehicle, bestInsertion.pickupPos, bestInsertion.deliveryPos);
                        routeTimes[bestVehicle] = new RouteTimes(tempSol.tours.get(bestVehicle).size());
                        insertionService.calculateRouteTimes(tempSol.tours.get(bestVehicle), routeTimes[bestVehicle]);
                        tempSol.indexVehicle(bestVehicle);
                        improvement = true;
                    }
                }
            }
        }
        instance.solutionEvaluation(tempSol);
        return tempSol;
    }
}
