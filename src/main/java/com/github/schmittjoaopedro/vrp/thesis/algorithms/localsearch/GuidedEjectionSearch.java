package com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertPosition;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.NeighborhoodService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class GuidedEjectionSearch {

    private Instance instance;

    private Random random;

    private long maxTimeInSec;

    private int kMax;

    private NeighborhoodService neighborhoodService;

    private int iRand = 100;

    private double pEx = 0.5;

    public GuidedEjectionSearch(Instance instance, Random random, int kMax) {
        this.instance = instance;
        this.random = random;
        this.neighborhoodService = new NeighborhoodService(instance);
        this.maxTimeInSec = 0;
        this.kMax = kMax;
    }

    public GuidedEjectionSearch(Instance instance, Random random, int kMax, int maxTimeInSec) {
        this.instance = instance;
        this.random = random;
        this.neighborhoodService = new NeighborhoodService(instance);
        this.maxTimeInSec = maxTimeInSec;
        this.kMax = kMax;
    }

    public Solution deleteRoute(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        long startTime = System.currentTimeMillis();
        double iter = 0, ejecCt = 0, penaSm = 0, poolSz = 0;
        // Select and remove a route randomly from σ
        List<Integer> randomRoute = removeRandomRoute(solution);
        // Initialize EP (ejection pool) with the requests in the removed route
        Stack<Integer> ejectionPool = new Stack<>();
        ejectionPool.addAll(randomRoute);
        // Initialize all penalty counters p[h] := 1 (h = 1,...,N/2)
        int[] penaltyCounters = new int[instance.numRequests];
        for (int i = 0; i < instance.numRequests; i++) {
            penaltyCounters[i] = 1;
        }
        // while EP != NULL or termination condition is not met do
        while (!ejectionPool.isEmpty() && hasTime(startTime)) {
            // Select and remove request h_in from EP with the LIFO strategy
            Request request = instance.requests[ejectionPool.pop()];
            // if N_insert != NULL then
            List<InsertPosition> neighborhood = findNeighborhoodInsert(request, solution);
            if (!neighborhood.isEmpty()) {
                // Select σ' ∈ N_insert(h_in, σ) randomly; Update σ = σ'
                InsertPosition insertPosition = neighborhood.get(random.nextInt(neighborhood.size()));
                solution.insert(instance, request.requestId, insertPosition.vehicle, insertPosition.pickupPos, insertPosition.deliveryPos);
                solution.indexVehicle(insertPosition.vehicle);
            }
            // if h_in cannot be inserted in σ then
            EjectedSolution bestEjectedSolution = null;
            if (neighborhood.isEmpty()) {
                // Set p[h_in] := p[h_in] + 1
                penaltyCounters[request.requestId] = penaltyCounters[request.requestId] + 1;
                // Select σ' ∈ N_ej(h_in, σ) such that P_sum = p[h_out_1 + ... + h_out_k] is minimized
                bestEjectedSolution = findBestInsertWithEjection(request, solution, penaltyCounters);
                if (bestEjectedSolution.insertPosition != null) {
                    // Update σ := σ'
                    InsertPosition insertPosition = bestEjectedSolution.insertPosition;
                    solution.insert(instance, request.requestId, insertPosition.vehicle, insertPosition.pickupPos, insertPosition.deliveryPos);
                    solution.remove(bestEjectedSolution.ejectedRequests, instance);
                    solution.indexVehicle(insertPosition.vehicle);
                    instance.solutionEvaluation(solution);
                    // Add the ejected requests {h_out_1, ..., h_out_k} to EP
                    for (Integer ejectedRequest : bestEjectedSolution.ejectedRequests) {
                        ejectionPool.push(ejectedRequest);
                    }
                } else {
                    ejectionPool.push(request.requestId);
                }
                // σ := Perturb(σ)
                solution = perturb(solution);

                ejecCt++;
                poolSz += ejectionPool.size();
                if (bestEjectedSolution != null) {
                    penaSm += bestEjectedSolution.penaltySum;
                }
            }
            iter++;
            if (iter % 100 == 0) {
                String log = "It " + StringUtils.leftPad("" + iter, 10, ' ');
                log += ", avg eject: " + StringUtils.leftPad(String.format("%.2f", (ejecCt / 100.0)), 6, ' ');
                log += ", avg pool: " + StringUtils.leftPad(String.format("%.2f", (poolSz / ejecCt)), 6, ' ');
                log += ", avg penalty: " + StringUtils.leftPad(String.format("%.2f", (penaSm / ejecCt)), 6, ' ');
                System.out.println(log);
                ejecCt = poolSz = penaSm = 0;
            }
        }
        // if EP != NULL then Restore σ to the input state
        if (!ejectionPool.isEmpty()) {
            solution = solutionBase;
        }
        instance.solutionEvaluation(solution);
        return solution;
    }

    private List<Integer> removeRandomRoute(Solution solution) {
        List<Integer> removedRequests = Collections.emptyList();
        if (solution.tours.size() > 1) {
            int selectedRoute = random.nextInt(solution.tours.size());
            solution.tours.remove(selectedRoute);
            removedRequests = solution.requestIds.remove(selectedRoute);
            instance.solutionEvaluation(solution);
        }
        return removedRequests;
    }

    private boolean hasTime(long startTime) {
        if (maxTimeInSec > 0) {
            long currentTime = System.currentTimeMillis();
            return (currentTime - startTime) < maxTimeInSec * 1000;
        } else {
            return true;
        }
    }

    private List<InsertPosition> findNeighborhoodInsert(Request request, Solution solution) {
        RouteTimes routeTime;
        ArrayList<Integer> route;
        List<InsertPosition> neighborhood = new ArrayList<>();
        for (int i = 0; i < solution.tours.size(); i++) {
            route = solution.tours.get(i);
            routeTime = new RouteTimes(route, instance);
            List<InsertPosition> insertPositions = neighborhoodService.searchFeasibleNeighborhood(route, request, routeTime, new boolean[instance.numRequests]);
            for (InsertPosition insertPosition : insertPositions) {
                insertPosition.vehicle = i;
                neighborhood.add(insertPosition);
            }
        }
        return neighborhood;
    }

    private EjectedSolution findBestInsertWithEjection(Request request, Solution solution, int[] penaltyCounters) {
        List<EjectedSolution> ejectedSolutions = new LinkedList<>();
        boolean[] ignoreRequests = new boolean[instance.numRequests];
        // Consider the eject of request itself as part of neighborhood
        EjectedSolution ejectedSolution = new EjectedSolution();
        ejectedSolution.insertPosition = null;
        ejectedSolution.penaltySum = penaltyCounters[request.requestId];
        ejectedSolutions.add(ejectedSolution);

        BestEjectedSolution bestEjectedSolution = new BestEjectedSolution();
        bestEjectedSolution.ejectedSolution = ejectedSolution;
        for (int v = 0; v < solution.tours.size(); v++) {
            lexicographicSearch(bestEjectedSolution, solution, request, v, penaltyCounters, ignoreRequests, 0, 0, Math.min(kMax, solution.requestIds.get(v).size()));
        }
        return bestEjectedSolution.ejectedSolution;
    }

    private void lexicographicSearch(BestEjectedSolution bestEjectedSolutions, Solution solution, Request request, int vehicle,
                                     int[] penaltyCounters, boolean[] ignoreRequests, int startIdx, int kCurr, int kMax) {
        if (kCurr < kMax) {
            ArrayList<Integer> route = solution.tours.get(vehicle);
            ArrayList<Integer> requestIds = solution.requestIds.get(vehicle);
            for (int r = startIdx; r < requestIds.size(); r++) {
                ignoreRequests[requestIds.get(r)] = true;
                RouteTimes routeTime = new RouteTimes(route, instance, ignoreRequests);
                List<InsertPosition> insertPositions = neighborhoodService.searchFeasibleNeighborhood(route, request, routeTime, ignoreRequests);
                boolean foundBest = false;
                if (!insertPositions.isEmpty()) {
                    InsertPosition insertPosition = insertPositions.get(0);
                    insertPosition.vehicle = vehicle;
                    EjectedSolution ejectedSolution = new EjectedSolution();
                    ejectedSolution.insertPosition = insertPosition;
                    for (Integer reqId : requestIds) {
                        if (ignoreRequests[reqId]) {
                            ejectedSolution.ejectedRequests.add(reqId);
                            ejectedSolution.penaltySum += penaltyCounters[reqId];
                        }
                    }
                    if (bestEjectedSolutions.ejectedSolution == null ||
                            ejectedSolution.penaltySum < bestEjectedSolutions.ejectedSolution.penaltySum) {
                        bestEjectedSolutions.ejectedSolution = ejectedSolution;
                        foundBest = true;
                    }
                }
                if (!foundBest) {
                    lexicographicSearch(bestEjectedSolutions, solution, request, vehicle, penaltyCounters, ignoreRequests, r + 1, kCurr + 1, kMax);
                }
                ignoreRequests[requestIds.get(r)] = false;
            }
        }
    }

    private Solution perturb(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        int numVehicles = solution.tours.size();
        boolean runEx = true, runRl = true;
        for (int i = 0; i < iRand; i++) {
            int vehicleIdx = random.nextInt(numVehicles);
            if (solution.requestIds.get(vehicleIdx).size() > 0) {
                int requestIdx = random.nextInt(solution.requestIds.get(vehicleIdx).size());
                Request selectedRequest = instance.requests[solution.requestIds.get(vehicleIdx).get(requestIdx)];
                if (random.nextDouble() < pEx && runEx) {
                    List<Pair<InsertPosition, InsertPosition>> neighborhood = searchExchangeNeighborhood(solution, selectedRequest.requestId, vehicleIdx);
                    if (neighborhood.size() > 0) {
                        Pair<InsertPosition, InsertPosition> exchange = neighborhood.get(random.nextInt(neighborhood.size()));
                        InsertPosition r1Insert = exchange.getLeft();
                        InsertPosition r2Insert = exchange.getRight();
                        solution.remove(Arrays.asList(r1Insert.requestId, r2Insert.requestId), instance);
                        solution.insert(instance, r1Insert.requestId, r1Insert.vehicle, r1Insert.pickupPos, r1Insert.deliveryPos);
                        solution.insert(instance, r2Insert.requestId, r2Insert.vehicle, r2Insert.pickupPos, r2Insert.deliveryPos);
                        runEx = runRl = true;
                    } else {
                        runEx = false;
                    }
                } else if (runRl) {
                    List<InsertPosition> neighborhood = searchRelocateNeighborhood(solution, selectedRequest.requestId, vehicleIdx);
                    if (neighborhood.size() > 0) {
                        InsertPosition selectedPosition = neighborhood.get(random.nextInt(neighborhood.size()));
                        solution.remove(Collections.singletonList(selectedRequest.requestId), instance);
                        solution.insert(instance, selectedRequest.requestId, selectedPosition.vehicle, selectedPosition.pickupPos, selectedPosition.deliveryPos);
                        runEx = runRl = true;
                    } else {
                        runRl = false;
                    }
                }
                if (!runEx && !runRl) {
                    break;
                }
            }
        }
        instance.solutionEvaluation(solution);
        return solution;
    }

    public List<InsertPosition> searchRelocateNeighborhood(Solution solution, int requestId, int vehicleIdx) {
        int numVehicles = solution.tours.size();
        Request request = instance.requests[requestId];
        List<InsertPosition> neighborhood = new ArrayList<>();
        ArrayList<Integer> tour = new ArrayList<>(solution.tours.get(vehicleIdx));
        solution.tours.get(vehicleIdx).remove(Integer.valueOf(request.pickupTask.nodeId));
        solution.tours.get(vehicleIdx).remove(Integer.valueOf(request.deliveryTask.nodeId));
        for (int v = 0; v < numVehicles; v++) {
            List<InsertPosition> insertPositions = neighborhoodService.searchFeasibleNeighborhood(solution.tours.get(v), request, new RouteTimes(solution.tours.get(v), instance));
            for (InsertPosition neighbor : insertPositions) neighbor.vehicle = v;
            neighborhood.addAll(insertPositions);
        }
        solution.tours.set(vehicleIdx, tour);
        return neighborhood;
    }

    public List<Pair<InsertPosition, InsertPosition>> searchExchangeNeighborhood(Solution solution, int requestId, int vehicleIdx) {
        int numVehicles = solution.tours.size();
        Request req1 = instance.requests[requestId];
        List<Pair<InsertPosition, InsertPosition>> neighborhood = new ArrayList<>();
        ArrayList<Integer> tour1 = new ArrayList<>(solution.tours.get(vehicleIdx));
        tour1.remove(Integer.valueOf(req1.pickupTask.nodeId));
        tour1.remove(Integer.valueOf(req1.deliveryTask.nodeId));
        RouteTimes routeTimes1 = new RouteTimes(tour1, instance);
        for (int v = 0; v < numVehicles; v++) {
            if (vehicleIdx != v) {
                for (int r = 0; r < solution.requestIds.get(v).size(); r++) {
                    Request req2 = instance.requests[solution.requestIds.get(v).get(r)];
                    List<InsertPosition> r2Neighborhood = neighborhoodService.searchFeasibleNeighborhood(tour1, req2, routeTimes1);
                    if (!r2Neighborhood.isEmpty()) {
                        ArrayList<Integer> tour2 = new ArrayList<>(solution.tours.get(v));
                        tour2.remove(Integer.valueOf(req2.pickupTask.nodeId));
                        tour2.remove(Integer.valueOf(req2.deliveryTask.nodeId));
                        List<InsertPosition> r1Neighborhood = neighborhoodService.searchFeasibleNeighborhood(tour2, req1, new RouteTimes(tour2, instance));
                        if (!r1Neighborhood.isEmpty()) {
                            for (InsertPosition r1Neighbor : r1Neighborhood) {
                                for (InsertPosition r2Neighbor : r2Neighborhood) {
                                    r1Neighbor.vehicle = v;
                                    r1Neighbor.requestId = req1.requestId;
                                    r2Neighbor.vehicle = vehicleIdx;
                                    r2Neighbor.requestId = req2.requestId;
                                    neighborhood.add(Pair.of(r1Neighbor, r2Neighbor));
                                }
                            }
                        }
                    }
                }
            }
        }
        return neighborhood;
    }

    private class BestEjectedSolution {
        EjectedSolution ejectedSolution;
    }

    private class EjectedSolution {
        InsertPosition insertPosition;
        List<Integer> ejectedRequests = new ArrayList<>(kMax);
        int penaltySum;
    }

}
