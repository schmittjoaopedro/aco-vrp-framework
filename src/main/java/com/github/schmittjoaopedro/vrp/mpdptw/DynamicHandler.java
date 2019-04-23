package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.operators.Req;

import java.util.*;

public class DynamicHandler {

    private ProblemInstance instance;

    private double startAlgorithmTime;

    private double endAlgorithmTime;

    private ArrayList<RequestWrapper> dynamicRequests;

    private Map<Integer, Integer> requestOriginals = new HashMap<>();

    private Map<Integer, Integer> nodeOriginals = new HashMap<>();

    public DynamicHandler(ProblemInstance instance, double startAlgorithmTime, double endAlgorithmTime) {
        this.instance = instance;
        this.startAlgorithmTime = startAlgorithmTime;
        this.endAlgorithmTime = endAlgorithmTime;
    }

    public void adaptDynamicVersion() {
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
        int node = 1;
        for (RequestWrapper requestWrapper : dynamicRequests) {
            for (Request request : requestWrapper.requests) {
                requestOriginals.put(count, request.requestId);
                nodeOriginals.put(node, request.nodeId);
                request.requestId = count;
                request.nodeId = node;
                node++;
            }
            requestWrapper.requestId = count;
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
            Request[] requestNodes = new Request[maxNodeId - 1]; // Ignore depot

            // Add existent request nodes
            for (Request request : instance.getRequests()) {
                if (request != null) {
                    requestNodes[request.nodeId - 1] = request;
                }
            }
            // Add new request nodes
            for (Request request : newRequests) {
                requestNodes[request.nodeId - 1] = request;
            }

            // Load requests information
            int numReq = instance.getNumReq() + numNewReqs;
            instance.setNumReq(numReq);
            instance.setRequests(requestNodes);
            instance.updateRequestStructures();

            // Calculate distances
            instance.setNumNodes(maxNodeId);
            instance.setDistances(new double[maxNodeId][maxNodeId]);
            instance.calculateDistances();
            instance.calculateMaxDistance();

        }
        return newRequestIds;
    }

    public void reloadOriginalInformation(Solution solution) {
        for (Request request : instance.getRequests()) {
            request.requestId = requestOriginals.get(request.requestId);
            request.nodeId = nodeOriginals.get(request.nodeId);
        }
        Arrays.sort(instance.getRequests(), Comparator.comparing(Request::getNodeId));
        for (int k = 0; k < solution.tours.size(); k++) {
            for (int i = 1; i < solution.tours.get(k).size() - 1; i++) {
                solution.tours.get(k).set(i, nodeOriginals.get(solution.tours.get(k).get(i)));
            }
            for (int i = 0; i < solution.requests.get(k).size(); i++) {
                solution.requests.get(k).set(i, requestOriginals.get(solution.requests.get(k).get(i)));
            }
        }
        instance.updateRequestStructures();
        instance.setDistances(new double[instance.getNumNodes()][instance.getNumNodes()]);
        instance.calculateDistances();
        instance.calculateMaxDistance();
        instance.solutionEvaluation(solution);
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
