package com.github.schmittjoaopedro.vrp.mpdptw.operators;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.*;

public class RemovalOperator {

    private ProblemInstance instance;

    private Random random;

    private static double D = 6; // According Coelho paper

    public RemovalOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    // Random removal
    public List<Req> removeRandomRequest(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> assignedRequests;
        Req request = selectRandomRequestFromSolution(requests);
        ArrayList<Req> removedRequests = new ArrayList<>();
        removedRequests.add(request);
        removeRequest(solution, requests, request);
        int requestD;
        while (removedRequests.size() < noReqToRemove) {
            assignedRequests = getAllRequests(requests);
            requestD = (int) (random.nextDouble() * assignedRequests.size());
            request = assignedRequests.get(requestD);
            removeRequest(solution, requests, request);
            removedRequests.add(request);
        }
        removeEmptyVehicles(solution, requests);
        return removedRequests;
    }

    // Shaw removal
    public List<Req> removeShawRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> assignedRequests;
        Req request = selectRandomRequestFromSolution(requests);
        ArrayList<Req> removedRequests = new ArrayList<>();
        removedRequests.add(request);
        removeRequest(solution, requests, request);
        double y;
        int requestD;
        while (removedRequests.size() < noReqToRemove) {
            request = removedRequests.get((int) (random.nextDouble() * removedRequests.size()));
            assignedRequests = getMostRelatedRequests(request, requests);
            assignedRequests.sort(Comparator.comparing(Req::getCost));
            y = random.nextDouble();
            requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            request = assignedRequests.get(requestD);
            removeRequest(solution, requests, request);
            removedRequests.add(request);
        }
        removeEmptyVehicles(solution, requests);
        return removedRequests;
    }

    public List<Req> removeMostExpensiveNodes(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> assignedRequests;
        Req request = selectRandomRequestFromSolution(requests);
        ArrayList<Req> removedRequests = new ArrayList<>();
        removedRequests.add(request);
        removeRequest(solution, requests, request);
        double y;
        int requestD;
        while (removedRequests.size() < noReqToRemove) {
            assignedRequests = getMostExpensiveNodesRequests(solution, requests);
            assignedRequests.sort(Comparator.comparing(Req::getCost));
            y = random.nextDouble();
            requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            request = assignedRequests.get(requestD);
            removeRequest(solution, requests, request);
            removedRequests.add(request);
        }
        removeEmptyVehicles(solution, requests);
        return removedRequests;
    }

    public List<Req> removeExpensiveRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> assignedRequests;
        Req request = selectRandomRequestFromSolution(requests);
        ArrayList<Req> removedRequests = new ArrayList<>();
        removedRequests.add(request);
        removeRequest(solution, requests, request);
        double y;
        int requestD;
        while (removedRequests.size() < noReqToRemove) {
            assignedRequests = getMostExpensiveRequests(solution, requests);
            assignedRequests.sort(Comparator.comparing(Req::getCost));
            y = random.nextDouble();
            requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            request = assignedRequests.get(requestD);
            removeRequest(solution, requests, request);
            removedRequests.add(request);
        }
        removeEmptyVehicles(solution, requests);
        return removedRequests;
    }

    private void removeEmptyVehicles(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        int position = 0;
        while (solution.size() > position) {
            if (requests.get(position).isEmpty()) {
                solution.remove(position);
                requests.remove(position);
            } else {
                position++;
            }
        }
    }

    private ArrayList<Req> getMostRelatedRequests(Req request, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int requestId;
        double relatedCost;
        for (int vehicleId = 0; vehicleId < requests.size(); vehicleId++) {
            for (int requestIdx = 0; requestIdx < requests.get(vehicleId).size(); requestIdx++) {
                requestId = requests.get(vehicleId).get(requestIdx);
                if (requestId != request.requestId) {
                    relatedCost = instance.distances[instance.delivery.get(request.requestId).nodeId][instance.delivery.get(requestId).nodeId];
                    assignedRequests.add(new Req(vehicleId, requestId, relatedCost));
                }
            }
        }
        return assignedRequests;
    }

    private ArrayList<Req> getMostExpensiveRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int requestId;
        double originalRoutCost, newRouteCost;
        for (int vehicleId = 0; vehicleId < requests.size(); vehicleId++) {
            originalRoutCost = instance.costEvaluation(solution.get(vehicleId));
            for (int requestIdx = 0; requestIdx < requests.get(vehicleId).size(); requestIdx++) {
                requestId = requests.get(vehicleId).get(requestIdx);
                newRouteCost = instance.costEvaluation(solution.get(vehicleId), requestId);
                newRouteCost = (originalRoutCost - newRouteCost) * -1.0;
                assignedRequests.add(new Req(vehicleId, requestId, newRouteCost));
            }
        }
        return assignedRequests;
    }

    private ArrayList<Req> getMostExpensiveNodesRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int requestId, prev, curr, next;
        double cost, newCost;
        double[] expensiveRequests = new double[instance.noReq];
        int[] vehicleRequests = new int[instance.noReq];
        for (int vehicleId = 0; vehicleId < requests.size(); vehicleId++) {
            for (int i = 1; i < solution.get(vehicleId).size() - 1; i++) {
                prev = solution.get(vehicleId).get(i - 1);
                curr = solution.get(vehicleId).get(i);
                next = solution.get(vehicleId).get(i + 1);
                requestId = instance.requests[curr - 1].requestId;
                cost = instance.distances[prev][curr] + instance.distances[curr][next];
                newCost = instance.distances[prev][next];
                if (expensiveRequests[requestId] < (cost - newCost)) {
                    expensiveRequests[requestId] = cost - newCost;
                }
                vehicleRequests[requestId] = vehicleId;
            }
        }
        for (int i = 0; i < expensiveRequests.length; i++) {
            if (expensiveRequests[i] != 0) {
                assignedRequests.add(new Req(vehicleRequests[i], i, expensiveRequests[i] * -1.0));
            }
        }
        return assignedRequests;
    }

    private ArrayList<Req> getAllRequests(ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int requestId;
        for (int vehicleId = 0; vehicleId < requests.size(); vehicleId++) {
            for (int requestIdx = 0; requestIdx < requests.get(vehicleId).size(); requestIdx++) {
                requestId = requests.get(vehicleId).get(requestIdx);
                assignedRequests.add(new Req(vehicleId, requestId, 0.0));
            }
        }
        return assignedRequests;
    }

    private Req selectRandomRequestFromSolution(ArrayList<ArrayList<Integer>> requests) {
        double noVehicles = requests.size();
        int rndVehicle = (int) (random.nextDouble() * noVehicles);
        ArrayList<Integer> vehicleRequests = requests.get(rndVehicle);
        double noRequests = vehicleRequests.size();
        int rndRequest = (int) (random.nextDouble() * noRequests);
        int removedRequest = requests.get(rndVehicle).get(rndRequest);
        return new Req(rndVehicle, removedRequest);
    }

    private void removeRequest(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, Req request) {
        for (Request req : instance.pickups.get(request.requestId)) {
            removeItem(solution.get(request.vehicleId), req.nodeId);
        }
        removeItem(solution.get(request.vehicleId), instance.delivery.get(request.requestId).nodeId);
        removeItem(requests.get(request.vehicleId), request.requestId);
    }

    private void removeItem(List<Integer> array, int item) {
        int position = -1;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == item) {
                position = i;
                break;
            }
        }
        array.remove(position);
    }

}
