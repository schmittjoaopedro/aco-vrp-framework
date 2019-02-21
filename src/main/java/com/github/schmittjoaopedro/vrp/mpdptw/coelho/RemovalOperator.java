package com.github.schmittjoaopedro.vrp.mpdptw.coelho;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RemovalOperator {

    private ProblemInstance intance;

    private Random random;

    private static double D = 6; // According Coelho paper

    public RemovalOperator(ProblemInstance instance, Random random) {
        this.intance = instance;
        this.random = random;
    }

    public List<Req> removeRequests(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, int noReqToRemove) {
        ArrayList<Req> assignedRequests;
        Req request = selectRandomRequestFromSolution(requests);
        ArrayList<Req> removedRequests = new ArrayList<>();
        removedRequests.add(request);
        removeAllNodes(solution, requests, request);
        double rnd;
        int requestD, assignedReqSize;
        while (removedRequests.size() < noReqToRemove) {
            assignedRequests = getAllRequestsFromSolution(solution, requests);
            assignedRequests.sort(Comparator.comparing(Req::getCost));
            rnd = random.nextDouble();
            assignedReqSize = (int) Math.min(D, assignedRequests.size() - 1);
            requestD = (int) (rnd * assignedReqSize);
            request = assignedRequests.get(requestD);
            removeAllNodes(solution, requests, request);
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

    private ArrayList<Req> getAllRequestsFromSolution(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int requestId;
        double originalRoutCost, newRouteCost;
        for (int vehicleId = 0; vehicleId < requests.size(); vehicleId++) {
            originalRoutCost = intance.costEvaluation(solution.get(vehicleId));
            for (int requestIdx = 0; requestIdx < requests.get(vehicleId).size(); requestIdx++) {
                requestId = requests.get(vehicleId).get(requestIdx);
                newRouteCost = intance.costEvaluation(solution.get(vehicleId), requestId);
                newRouteCost = (originalRoutCost - newRouteCost) * -1.0;
                assignedRequests.add(new Req(vehicleId, requestId, newRouteCost));
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

    private void removeAllNodes(ArrayList<ArrayList<Integer>> solution, ArrayList<ArrayList<Integer>> requests, Req request) {
        for (Request req : intance.pickups.get(request.requestId)) {
            removeItem(solution.get(request.vehicleId), req.nodeId);
        }
        removeItem(solution.get(request.vehicleId), intance.delivery.get(request.requestId).nodeId);
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
