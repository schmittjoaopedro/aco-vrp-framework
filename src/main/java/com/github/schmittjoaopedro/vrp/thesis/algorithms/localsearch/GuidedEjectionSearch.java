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

    public Solution deleteRoute(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        long startTime = System.currentTimeMillis();
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
        double iter = 0;
        double pertCt = 0;
        double pertEf = 0;
        // while EP != NULL or termination condition is not met do
        while (!ejectionPool.isEmpty() || hasTimePassedOver(startTime)) {
            String log = "Iteration " + iter++;
            // Select and remove request h_in from EP with the LIFO strategy
            Request request = instance.requests[ejectionPool.pop()];
            // if N_insert != NULL then
            List<InsertPosition> neighborhood = findNeighborhoodInsert(request, solution);
            if (!neighborhood.isEmpty()) {
                // Select σ' ∈ N_insert(h_in, σ) randomly; Update σ = σ'
                InsertPosition insertPosition = neighborhood.get(random.nextInt(neighborhood.size()));
                solution.insert(instance, request.requestId, insertPosition.vehicle, insertPosition.pickupPos, insertPosition.deliveryPos);
                solution.indexVehicle(insertPosition.vehicle);
            // end if
            }
            // if h_in cannot be inserted in σ then
            EjectedSolution bestEjectedSolution = null;
            if (neighborhood.isEmpty()) {
                // Set p[h_in] := p[h_in] + 1
                penaltyCounters[request.requestId] = penaltyCounters[request.requestId] + 1;
                // Select σ' ∈ N_ej(h_in, σ) such that P_sum = p[h_out_1 + ... + h_out_k] is minimized
                List<EjectedSolution> ejectionNeighborhood = findNeighborhoodWithEjection(request, solution, penaltyCounters);
                Collections.shuffle(ejectionNeighborhood, random);
                for (EjectedSolution currentEjectedSolution : ejectionNeighborhood) {
                    if (bestEjectedSolution == null || currentEjectedSolution.penaltySum < bestEjectedSolution.penaltySum) {
                        bestEjectedSolution = currentEjectedSolution;
                    }
                }
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
                log += ", pool size: " + ejectionPool.size();
                // σ := Perturb(σ)
                double costBefore = solution.totalCost;
                solution = perturb(solution);
                pertCt++;
                if (Math.abs(costBefore - solution.totalCost) > 0.01) pertEf++;
                log += ", perturb: (" + String.format("%.2f", (pertEf / pertCt) * 100.0) + ") " + String.format("%d %.2f", solution.tours.size(), (costBefore - solution.totalCost));
                if (bestEjectedSolution != null) {
                    log += ", best eject: " + bestEjectedSolution.penaltySum;
                }
                if (iter % 1000 == 0) {
                    System.out.println(log);
                }
            // end if
            }
        // end while
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

    private boolean hasTimePassedOver(long startTime) {
        if (maxTimeInSec > 0) {
            long currentTime = System.currentTimeMillis();
            return (currentTime - startTime) > maxTimeInSec * 1000;
        } else {
            return false;
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

    private List<EjectedSolution> findNeighborhoodWithEjection(Request request, Solution solution, int[] penaltyCounters) {
        List<EjectedSolution> ejectedSolutions = new ArrayList<>();
        boolean[] ignoreRequests = new boolean[instance.numRequests];
        for (int v = 0; v < solution.tours.size(); v++) {
            lexicographicSearch(ejectedSolutions, solution, request, v, penaltyCounters, ignoreRequests, 0, Math.min(kMax, solution.requestIds.get(v).size()));
        }
        // Consider the eject of request itself as part of neighborhood
        EjectedSolution ejectedSolution = new EjectedSolution();
        ejectedSolution.insertPosition = null;
        ejectedSolution.penaltySum = penaltyCounters[request.requestId];
        ejectedSolutions.add(ejectedSolution);
        return ejectedSolutions;
    }

    private void lexicographicSearch(List<EjectedSolution> ejectedSolutions, Solution solution, Request request, int vehicle,
                                     int[] penaltyCounters, boolean[] ignoreRequests, int reqIdx, int maxIdx) {
        ArrayList<Integer> route = solution.tours.get(vehicle);
        ArrayList<Integer> requestIds = solution.requestIds.get(vehicle);
        ignoreRequests[requestIds.get(reqIdx)] = true;
        RouteTimes routeTime = new RouteTimes(route, instance, ignoreRequests);
        List<InsertPosition> insertPositions = neighborhoodService.searchFeasibleNeighborhood(route, request, routeTime, ignoreRequests);
        for (InsertPosition insertPosition : insertPositions) {
            insertPosition.vehicle = vehicle;
            EjectedSolution ejectedSolution = new EjectedSolution();
            ejectedSolution.insertPosition = insertPosition;
            for (Integer reqId : requestIds) {
                if (ignoreRequests[reqId]) {
                    ejectedSolution.ejectedRequests.add(reqId);
                    ejectedSolution.penaltySum += penaltyCounters[reqId];
                }
            }
            ejectedSolutions.add(ejectedSolution);
        }
        for (int r = reqIdx + 1; r < maxIdx; r++) {
            lexicographicSearch(ejectedSolutions, solution, request, vehicle, penaltyCounters, ignoreRequests, r, maxIdx);
        }
        ignoreRequests[requestIds.get(reqIdx)] = false;
    }

    private Solution perturb(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        int numVehicles = solution.tours.size();
        for (int i = 0; i < iRand; i++) {
            int vehicleIdx = random.nextInt(numVehicles);
            int requestIdx = random.nextInt(solution.requestIds.get(vehicleIdx).size());
            Request selectedRequest = instance.requests[solution.requestIds.get(vehicleIdx).get(requestIdx)];
            if (random.nextDouble() < pEx) {
                List<Pair<InsertPosition, InsertPosition>> neighborhood = neighborhoodService.searchExchangeNeighborhood(solution, selectedRequest.requestId, vehicleIdx);
                if (neighborhood.size() > 0) {
                    Pair<InsertPosition, InsertPosition> exchange = neighborhood.get(random.nextInt(neighborhood.size()));
                    InsertPosition r1Insert = exchange.getLeft();
                    InsertPosition r2Insert = exchange.getRight();
                    solution.remove(Arrays.asList(r1Insert.requestId, r2Insert.requestId), instance);
                    solution.insert(instance, r1Insert.requestId, r1Insert.vehicle, r1Insert.pickupPos, r1Insert.deliveryPos);
                    solution.insert(instance, r2Insert.requestId, r2Insert.vehicle, r2Insert.pickupPos, r2Insert.deliveryPos);
                } else {
                    break;
                }
            } else {
                List<InsertPosition> neighborhood = neighborhoodService.searchRelocateNeighborhood(solution, selectedRequest.requestId, vehicleIdx);
                if (neighborhood.size() > 0) {
                    InsertPosition selectedPosition = neighborhood.get(random.nextInt(neighborhood.size()));
                    solution.remove(Collections.singletonList(selectedRequest.requestId), instance);
                    solution.insert(instance, selectedRequest.requestId, selectedPosition.vehicle, selectedPosition.pickupPos, selectedPosition.deliveryPos);
                } else {
                    break;
                }
            }
        }
        instance.solutionEvaluation(solution);
        return solution;
    }

    private class EjectedSolution {
        InsertPosition insertPosition;
        List<Integer> ejectedRequests = new ArrayList<>(kMax);
        int penaltySum;
    }

}
