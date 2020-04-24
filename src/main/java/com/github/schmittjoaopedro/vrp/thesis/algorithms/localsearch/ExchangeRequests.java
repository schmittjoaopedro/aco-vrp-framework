package com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertPosition;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertionService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.*;

public class ExchangeRequests {

    private Instance instance;

    private InsertionService insertionService;

    public ExchangeRequests(Instance instance) {
        this.instance = instance;
        this.insertionService = new InsertionService(instance);
    }

    public Solution exchange(Solution solution) {
        Solution tempSol = SolutionUtils.copy(solution);
        Request r1, r2;
        int v1, v2;
        ArrayList<Integer> t1, t2;
        RouteTimes rt1, rt2;
        double c1, c2;
        for (int k = 0; k < tempSol.tours.size(); k++) {
            tempSol.indexVehicle(k);
        }
        Set<Integer> solutionHashes = new HashSet<>();
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i < instance.numRequests - 1; i++) { // Process [0, 1, ..., n-1]
                r1 = instance.requests[i];
                if (r1.isVehicleRelocatable()) {
                    v1 = tempSol.getVehicle(r1.pickupTask.nodeId);
                    t1 = createTourWithoutRequest(tempSol.tours.get(v1), r1);
                    rt1 = createRouteTimes(t1);
                    for (int j = i + 1; j < instance.numRequests; j++) { // Process [1, 2, ..., n]
                        r2 = instance.requests[j];
                        if (r2.isVehicleRelocatable()) {
                            v2 = tempSol.getVehicle(r2.pickupTask.nodeId);
                            if (v1 != v2) {
                                InsertPosition r2Pos = insertionService.calculateBestPosition(t1, r2, rt1);
                                if (r2Pos.cost < Double.MAX_VALUE) {
                                    t2 = createTourWithoutRequest(tempSol.tours.get(v2), r2);
                                    rt2 = createRouteTimes(t2);
                                    InsertPosition r1Pos = insertionService.calculateBestPosition(t2, r1, rt2);
                                    if (r1Pos.cost < Double.MAX_VALUE) {
                                        int hash = getHash(v1, r1Pos, v2, r2Pos);
                                        if (!solutionHashes.contains(hash)) {
                                            solutionHashes.add(hash);
                                            c1 = tempSol.calculateRequestRemovalGain(instance, r1);
                                            c2 = tempSol.calculateRequestRemovalGain(instance, r2);
                                            if (r1Pos.cost + r2Pos.cost < c1 + c2) {
                                                tempSol.remove(Arrays.asList(r1.requestId, r2.requestId), instance);
                                                tempSol.insert(instance, r2.requestId, v1, r2Pos.pickupPos, r2Pos.deliveryPos);
                                                tempSol.insert(instance, r1.requestId, v2, r1Pos.pickupPos, r1Pos.deliveryPos);
                                                tempSol.indexVehicle(v1);
                                                tempSol.indexVehicle(v2);
                                                v1 = tempSol.getVehicle(r1.pickupTask.nodeId);
                                                t1 = createTourWithoutRequest(tempSol.tours.get(v1), r1);
                                                rt1 = createRouteTimes(t1);
                                                improvement = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        instance.solutionEvaluation(tempSol);
        return tempSol;
    }

    public void exchange(Solution solution, LinkedList<Solution> neighborhood) {
        Solution tempSol = SolutionUtils.copy(solution);
        Request r1, r2;
        int v1, v2;
        ArrayList<Integer> t1, t2;
        RouteTimes rt1, rt2;
        for (int k = 0; k < tempSol.tours.size(); k++) {
            tempSol.indexVehicle(k);
        }
        for (int i = 0; i < instance.numRequests - 1; i++) { // Process [0, 1, ..., n-1]
            r1 = instance.requests[i];
            if (r1.isVehicleRelocatable()) {
                v1 = tempSol.getVehicle(r1.pickupTask.nodeId);
                t1 = createTourWithoutRequest(tempSol.tours.get(v1), r1);
                rt1 = createRouteTimes(t1);
                for (int j = i + 1; j < instance.numRequests; j++) { // Process [1, 2, ..., n]
                    r2 = instance.requests[j];
                    if (r2.isVehicleRelocatable()) {
                        v2 = tempSol.getVehicle(r2.pickupTask.nodeId);
                        if (v1 != v2) {
                            InsertPosition r2Pos = insertionService.calculateBestPosition(t1, r2, rt1);
                            if (r2Pos.cost < Double.MAX_VALUE) {
                                t2 = createTourWithoutRequest(tempSol.tours.get(v2), r2);
                                rt2 = createRouteTimes(t2);
                                InsertPosition r1Pos = insertionService.calculateBestPosition(t2, r1, rt2);
                                if (r1Pos.cost < Double.MAX_VALUE) {
                                    Solution neighbor = SolutionUtils.copy(tempSol);
                                    neighbor.remove(Arrays.asList(r1.requestId, r2.requestId), instance);
                                    neighbor.insert(instance, r2.requestId, v1, r2Pos.pickupPos, r2Pos.deliveryPos);
                                    neighbor.insert(instance, r1.requestId, v2, r1Pos.pickupPos, r1Pos.deliveryPos);
                                    instance.solutionEvaluation(neighbor);
                                    neighborhood.add(neighbor);
                                }
                            }
                        }
                    }
                }
            }
        }
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

    public RouteTimes createRouteTimes(ArrayList<Integer> tour) {
        return new RouteTimes(tour, instance);
    }

    public int getHash(int v1, InsertPosition pos1, int v2, InsertPosition pos2) {
        return ("-" + v1 +
                "-" + v2 +
                "-" + MathUtils.round(pos1.cost) +
                "-" + MathUtils.round(pos2.cost) +
                "-" + pos1.pickupPos +
                "-" + pos2.pickupPos +
                "-" + pos1.deliveryPos +
                "-" + pos2.deliveryPos).hashCode();
    }

}
