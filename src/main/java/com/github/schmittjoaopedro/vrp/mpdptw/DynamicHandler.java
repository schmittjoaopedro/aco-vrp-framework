package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.Req;

import java.util.*;

public class DynamicHandler {

    private ProblemInstance instance;

    private double startAlgorithmTime;

    private double endAlgorithmTime;

    private ArrayList<RequestWrapper> dynamicRequests;

    public DynamicHandler(ProblemInstance instance, double startAlgorithmTime, double endAlgorithmTime) {
        this.instance = instance;
        this.startAlgorithmTime = startAlgorithmTime;
        this.endAlgorithmTime = endAlgorithmTime;
    }

    public void prepareInstance() {
        Map<Integer, RequestWrapper> requestsMap = new HashMap<>();
        for (Request request : instance.getRequests()) {
            if (!requestsMap.containsKey(request.requestId)) {
                requestsMap.put(request.requestId, new RequestWrapper());
                requestsMap.get(request.requestId).announceTime = request.announceTime;
                requestsMap.get(request.requestId).requestId = request.requestId;
            }
            requestsMap.get(request.requestId).requests.add(request);
        }
        dynamicRequests = new ArrayList<>(requestsMap.values());
        Collections.sort(dynamicRequests, Comparator.comparing(RequestWrapper::getAnnounceTime).thenComparing(RequestWrapper::getRequestId));
        int count = 0;
        for (RequestWrapper requestWrapper : dynamicRequests) {
            for (Request request : requestWrapper.requests) {
                request.requestId = count;
            }
            count++;
        }
        instance.setRequests(new Request[0]);
        instance.setPickups(new ArrayList[0]);
        instance.setDelivery(new Request[0]);
        instance.setNumReq(0);
        instance.setNumNodes(1);
        instance.setDistances(new double[][]{{0.0}});
    }

    public List<Integer> processDynamism(double time) {
        List<Integer> newRequestIds = new ArrayList<>();
        List<Request> newRequests = new ArrayList<>();
        List<RequestWrapper> toRemove = new ArrayList<>();
        int numNewReqs = 0;
        for (RequestWrapper requestWrapper : dynamicRequests) {
            if (getAlgScaledTime(time) >= getProbScaledTime(requestWrapper.announceTime)) {
                newRequests.addAll(requestWrapper.requests);
                toRemove.add(requestWrapper);
                newRequestIds.add(requestWrapper.requestId);
                numNewReqs++;
            }
        }
        if (numNewReqs > 0) {
            dynamicRequests.removeAll(toRemove);
            // Re-size structures
            int maxNodeId = instance.getNumNodes();
            for (Request request : newRequests) {
                maxNodeId = Math.max(maxNodeId, request.nodeId + 1);
            }
            Request[] requestNodes = new Request[maxNodeId]; // Ignore depot
            Map<Integer, List<Request>> pickups = new HashMap<>();
            Map<Integer, Request> delivery = new HashMap<>();

            // Add existent request nodes
            for (Request request : instance.getRequests()) {
                if (request != null) {
                    requestNodes[request.nodeId - 1] = request;
                }
                if (request.isPickup) {
                    if (!pickups.containsKey(request.requestId)) {
                        pickups.put(request.requestId, new ArrayList<>());
                    }
                    pickups.get(request.requestId).add(request);
                } else {
                    if (!delivery.containsKey(request.requestId)) {
                        delivery.put(request.requestId, request);
                    }
                }
            }
            // Add new request nodes
            for (Request request : newRequests) {
                requestNodes[request.nodeId - 1] = request;
                if (request.isPickup) {
                    if (!pickups.containsKey(request.requestId)) {
                        pickups.put(request.requestId, new ArrayList<>());
                    }
                    pickups.get(request.requestId).add(request);
                } else {
                    if (!delivery.containsKey(request.requestId)) {
                        delivery.put(request.requestId, request);
                    }
                }
            }

            // Load requests information
            int numReq = instance.getNumReq() + numNewReqs;
            instance.setNumReq(numReq);
            List<Request>[] pickupsArray = new ArrayList[instance.getNumReq()];
            Request[] deliveriesArray = new Request[instance.getNumReq()];
            instance.setPickups(pickupsArray);
            instance.setDelivery(deliveriesArray);
            for (int i = 0; i < instance.getNumReq(); i++) {
                pickupsArray[i] = pickups.get(i);
                deliveriesArray[i] = delivery.get(i);
            }
            instance.setRequests(requestNodes);

            // Calculate distances
            instance.setNumNodes(maxNodeId);
            instance.setDistances(new double[maxNodeId][maxNodeId]);
            instance.calculateDistances();
            instance.calculateMaxDistance();

        }
        return newRequestIds;
    }

    private double getAlgScaledTime(double currentTime) {
        return (currentTime - startAlgorithmTime) / (endAlgorithmTime - startAlgorithmTime);
    }

    private double getProbScaledTime(double currentTime) {
        return (currentTime - instance.getDepot().twStart) / (instance.getDepot().twEnd - instance.getDepot().twStart);
    }

    private class RequestWrapper {
        List<Request> requests = new ArrayList<>();
        Double announceTime;
        Integer requestId;

        public Double getAnnounceTime() {
            return announceTime;
        }

        public Integer getRequestId() {
            return requestId;
        }
    }
}
