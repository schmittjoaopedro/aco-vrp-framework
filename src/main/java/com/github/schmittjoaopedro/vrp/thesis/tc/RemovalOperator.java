package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.*;
import java.util.stream.Collectors;

public class RemovalOperator {

    private Instance instance;

    private Random random;

    private static double D = 6;

    public RemovalOperator(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
    }

    public List<Request> removeRandomRequest(Solution solution, int q) {
        List<Request> removedRequests = new ArrayList<>();
        ArrayList<Req> assignedRequests = getAllRequests(solution.requestIds);
        while (removedRequests.size() < q) {
            int r = (int) (random.nextDouble() * assignedRequests.size());
            Req req = assignedRequests.get(r);
            Request request = instance.requests[req.requestId];
            removedRequests.add(request);
            assignedRequests.remove(request);
            solution.removeRequest(req.vehicleId, request);
        }
        return removedRequests;
    }

    public List<Request> removeShawRequests(Solution solution, int q) {
        List<Req> R = new ArrayList<>();
        Req r = selectRandomRequestFromSolution(solution.requestIds);
        Set<Integer> removedReqs = new HashSet<>();
        R.add(r);
        removedReqs.add(r.requestId);
        solution.removeRequest(r.vehicleId, instance.requests[r.requestId]);
        while (R.size() < q) {
            r = R.get((int) (random.nextDouble() * R.size()));
            ArrayList<Req> assignedRequests = getMostRelatedRequests(instance.requests[r.requestId], solution, removedReqs);
            assignedRequests.sort(Comparator.comparing(Req::getCost));
            double y = random.nextDouble();
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            r = assignedRequests.get(requestD);
            solution.removeRequest(r.vehicleId, instance.requests[r.requestId]);
            R.add(r);
            removedReqs.add(r.requestId);
        }
        return R.stream().map(rq -> instance.requests[rq.requestId]).collect(Collectors.toList());
    }

    public List<Request> removeExpensiveRequests(Solution solution, int q) {
        return removeRequestBasedOnCriteria(solution, q, ExpensiveCriteria.Request);
    }

    public List<Request> removeExpensiveNodes(Solution solution, int q) {
        return removeRequestBasedOnCriteria(solution, q, ExpensiveCriteria.Node);
    }

    private List<Request> removeRequestBasedOnCriteria(Solution solution, int noReqToRemove, ExpensiveCriteria removalMethod) {
        ArrayList<Req> R = new ArrayList<>();
        ArrayList<Req> assignedRequests = getRequestsCosts(solution, removalMethod);
        assignedRequests.sort(Comparator.comparing(Req::getCost));
        while (R.size() < noReqToRemove) {
            double y = random.nextDouble();
            int requestD = (int) (Math.pow(y, D) * assignedRequests.size());
            Req r = assignedRequests.get(requestD);
            R.add(r);
            assignedRequests.remove(r);
            solution.removeRequest(r.vehicleId, instance.requests[r.requestId]);
            updateChangedVehicle(solution, r, assignedRequests, removalMethod); // Update vehicle that had their route changed
        }
        return R.stream().map(rq -> instance.requests[rq.requestId]).collect(Collectors.toList());
    }

    private void updateChangedVehicle(Solution solution, Req r, ArrayList<Req> assignedRequests, ExpensiveCriteria removalMethod) {
        int i = 0;
        Map<Integer, RemovalRequest> requestMap = getRequestsCostsByCriteria(solution, r.vehicleId, removalMethod);
        while (i < assignedRequests.size()) {
            if (assignedRequests.get(i).vehicleId == r.vehicleId) {
                RemovalRequest removalRequest = requestMap.get(assignedRequests.get(i).requestId);
                Req req = new Req(r.vehicleId, removalRequest.requestId, removalRequest.cost);
                assignedRequests.set(i, req);
            }
            i++;
        }
        assignedRequests.sort(Comparator.comparing(Req::getCost));
    }

    private ArrayList<Req> getRequestsCosts(Solution solution, ExpensiveCriteria removalMethod) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < solution.tours.size(); k++) { // for each vehicle k in vehicles from solution
            Map<Integer, RemovalRequest> requestMap = getRequestsCostsByCriteria(solution, k, removalMethod);
            for (RemovalRequest removalRequest : requestMap.values()) {
                if (solution.visitedRequests[removalRequest.requestId]) {
                    assignedRequests.add(new Req(k, removalRequest.requestId, removalRequest.cost));
                }
            }
        }
        return assignedRequests;
    }

    private Map<Integer, RemovalRequest> getRequestsCostsByCriteria(Solution solution, int vehicle, ExpensiveCriteria removalMethod) {
        Map<Integer, RemovalRequest> requestsMap = new HashMap<>();
        double gain;
        double routeCost = instance.costEvaluation(solution.tours.get(vehicle));
        for (int requestId : solution.requestIds.get(vehicle)) {
            Request request = instance.requests[requestId];
            switch (removalMethod) {
                case Node:
                    double deliveryCost = instance.costEvaluation(solution.tours.get(vehicle), requestId, request.deliveryTask.nodeId);
                    double pickupCost = instance.costEvaluation(solution.tours.get(vehicle), requestId, request.pickupTask.nodeId);
                    gain = routeCost - Math.min(deliveryCost, pickupCost);
                    requestsMap.put(requestId, new RemovalRequest(requestId, gain));
                    break;
                case Request:
                    gain = routeCost - instance.costEvaluation(solution.tours.get(vehicle), requestId);
                    requestsMap.put(requestId, new RemovalRequest(requestId, gain));
                    break;
                default:
                    throw new RuntimeException("Error on process request cost criteria");
            }
        }
        for (RemovalRequest removalRequest : requestsMap.values()) {
            removalRequest.cost = removalRequest.cost * -1.0; // Multiply by negative to put best gains in front of the list
        }
        return requestsMap;
    }

    private ArrayList<Req> getMostRelatedRequests(Request request, Solution solution, Set<Integer> removedReqs) {
        Task reqI = request.deliveryTask;
        ArrayList<Req> assignedRequests = new ArrayList<>();
        for (int k = 0; k < solution.requestIds.size(); k++) {
            for (int r = 0; r < solution.requestIds.get(k).size(); r++) {
                Task reqJ = instance.requests[solution.requestIds.get(k).get(r)].deliveryTask;
                if (reqI.requestId != reqJ.requestId && !removedReqs.contains(reqJ.requestId)) {
                    double relate = instance.dist(reqI.nodeId, reqJ.nodeId);
                    assignedRequests.add(new Req(k, reqJ.requestId, relate));
                }
            }
        }
        return assignedRequests;
    }

    private Req selectRandomRequestFromSolution(ArrayList<ArrayList<Integer>> requests) {
        int rndVehicle = (int) (random.nextDouble() * requests.size());
        int rndRequest = (int) (random.nextDouble() * requests.get(rndVehicle).size());
        return new Req(rndVehicle, requests.get(rndVehicle).get(rndRequest));
    }

    private ArrayList<Req> getAllRequests(ArrayList<ArrayList<Integer>> requests) {
        ArrayList<Req> assignedRequests = new ArrayList<>();
        int rId;
        for (int k = 0; k < requests.size(); k++) {
            for (int r = 0; r < requests.get(k).size(); r++) {
                rId = requests.get(k).get(r);
                assignedRequests.add(new Req(k, rId, 0.0));
            }
        }
        return assignedRequests;
    }

    private class RemovalRequest {

        private int requestId;

        private double cost;

        public RemovalRequest(int requestId, double cost) {
            this.requestId = requestId;
            this.cost = cost;
        }

        public int getRequestId() {
            return requestId;
        }

        public double getCost() {
            return cost;
        }
    }

    private enum ExpensiveCriteria {
        Request, Node
    }

}
