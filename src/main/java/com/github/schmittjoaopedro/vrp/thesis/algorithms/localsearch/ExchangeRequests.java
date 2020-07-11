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
        return exchange(solution, false);
    }

    public Solution exchange(Solution solution, boolean stopDLS) {
        Solution tempSol = SolutionUtils.copy(solution);
        Request r1, r2;
        int v1, v2;
        ArrayList<Integer> tr1, tr2;
        RouteTimes tm1, tm2;
        double c1, c2;
        for (int k = 0; k < tempSol.tours.size(); k++) {
            tempSol.indexVehicle(k);
        }
        Set<Integer> solutionHashes = new HashSet<>();
        boolean improvement = true;
        long time = System.currentTimeMillis();

        // Data structure to improve processing times
        ArrayList<Integer>[][] VRTours = new ArrayList[instance.numVehicles][instance.numRequests];
        RouteTimes[][] VRTimes = new RouteTimes[instance.numVehicles][instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            r1 = instance.requests[i];
            if (r1.isVehicleRelocatable()) {
                v1 = tempSol.getVehicle(r1.pickupTask.nodeId);
                VRTours[v1][r1.requestId] = createTourWithoutRequest(tempSol.tours.get(v1), r1);
                VRTimes[v1][r1.requestId] = createRouteTimes(VRTours[v1][r1.requestId]);
            }
        }

        while (improvement) {
            improvement = false;
            for (int i = 0; i < instance.numRequests - 1; i++) { // Process [0, 1, ..., n-1]
                r1 = instance.requests[i];
                if (r1.isVehicleRelocatable()) {
                    v1 = tempSol.getVehicle(r1.pickupTask.nodeId);
                    tr1 = VRTours[v1][r1.requestId];
                    tm1 = VRTimes[v1][r1.requestId];
                    for (int j = i + 1; j < instance.numRequests; j++) { // Process [1, 2, ..., n]
                        r2 = instance.requests[j];
                        if (r2.isVehicleRelocatable()) {
                            v2 = tempSol.getVehicle(r2.pickupTask.nodeId);
                            if (v1 != v2) {
                                InsertPosition r2Pos = insertionService.calculateBestPosition(tr1, r2, tm1);
                                if (r2Pos.cost < Double.MAX_VALUE) {
                                    tr2 = VRTours[v2][r2.requestId];
                                    tm2 = VRTimes[v2][r2.requestId];
                                    InsertPosition r1Pos = insertionService.calculateBestPosition(tr2, r1, tm2);
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
                                                // Update exchanged requests and vehicles
                                                VRTours[v1][r1.requestId] = null;
                                                VRTimes[v1][r1.requestId] = null;
                                                VRTours[v2][r2.requestId] = null;
                                                VRTimes[v2][r2.requestId] = null;
                                                VRTours[v1][r2.requestId] = createTourWithoutRequest(tempSol.tours.get(v1), r2);
                                                VRTimes[v1][r2.requestId] = createRouteTimes(VRTours[v1][r2.requestId]);
                                                VRTours[v2][r1.requestId] = createTourWithoutRequest(tempSol.tours.get(v2), r1);
                                                VRTimes[v2][r1.requestId] = createRouteTimes(VRTours[v2][r1.requestId]);
                                                for (int r = 0; r < instance.numRequests; r++) {
                                                    int rId = instance.requests[r].requestId;
                                                    if (VRTours[v1][rId] != null && rId != r2.requestId) {
                                                        VRTours[v1][rId] = createTourWithoutRequest(tempSol.tours.get(v1), instance.requests[r]);
                                                        VRTimes[v1][rId] = createRouteTimes(VRTours[v1][rId]);
                                                    }
                                                    if (VRTours[v2][rId] != null && rId != r1.requestId) {
                                                        VRTours[v2][rId] = createTourWithoutRequest(tempSol.tours.get(v2), instance.requests[r]);
                                                        VRTimes[v2][rId] = createRouteTimes(VRTours[v2][rId]);
                                                    }
                                                }
                                                // Update current pointers
                                                v1 = v2;
                                                tr1 = VRTours[v1][r1.requestId];
                                                tm1 = VRTimes[v1][r1.requestId];
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
            if (stopDLS) {
                break;
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
