package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.*;

public class DynamicHandler {

    private ProblemInstance instance;

    private double startTime;

    private double endTime;

    private ArrayList<RequestWrapper> dynamicRequests;

    private Map<Integer, Integer> requestOriginals = new HashMap<>();

    private Map<Integer, Integer> nodeOriginals = new HashMap<>();

    public DynamicHandler(ProblemInstance instance, double startTime, double endTime) {
        this.instance = instance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /*
     * Takes the problem instance, and adapt it to the algorithm based on announce time (dynamic instances).
     * To adapt the problem instance, we order the request based on the announce time and re-assign the requestIds
     * and nodeIds in incremental order. The idea is to keep the problem restrictions but increasing the instance
     * requests accordingly the announce time.
     */
    public void adaptDynamicVersion() {
        // Group request nodes, based on request id, in a request wrapper.
        Map<Integer, RequestWrapper> requestsGroups = new HashMap<>();
        for (Request request : instance.getRequests()) {
            if (!requestsGroups.containsKey(request.requestId)) {
                requestsGroups.put(request.requestId, new RequestWrapper(request.announceTime, request.requestId));
            }
            requestsGroups.get(request.requestId).requests.add(request);
        }

        // Sort the requests based on the announce time.
        dynamicRequests = new ArrayList<>(requestsGroups.values());
        Collections.sort(dynamicRequests, Comparator.comparing(RequestWrapper::getAnnounceTime).thenComparing(RequestWrapper::getRequestId));

        // Re-assign the request and node IDs in incremental order. It will simplify further updates of the problem instance
        // we new requests are added to the problem.
        int count = 0;
        int node = 1;
        for (RequestWrapper requestWrapper : dynamicRequests) {
            for (Request request : requestWrapper.requests) {
                // Keeps the mapping of original ID's to revert the problem back to the original instance at the end of execution
                requestOriginals.put(count, request.requestId);
                nodeOriginals.put(node, request.nodeId);
                request.requestId = count;
                request.nodeId = node;
                node++;
            }
            requestWrapper.requestId = count;
            count++;
        }

        // Reset all instance structures. At first time all requests are considered unknown
        instance.setRequests(new Request[0]);
        instance.setPickups(new ArrayList[0]);
        instance.setDelivery(new Request[0]);
        instance.setNumReq(0);
        instance.setNumNodes(1);
        instance.setDistances(new double[][]{{0.0}}); // We only have depot information
        instance.getIdleRequests().clear();
    }

    /*
     * Add new requests to the problem based on the announce time compared with the current execution time.
     */
    public List<Integer> processDynamism(double currentTime) {
        // Check for new available requests based on the passing time.
        List<Integer> newRequestIds = new ArrayList<>();
        List<Request> newRequestNodes = new ArrayList<>();
        List<RequestWrapper> reqWrappersToRemove = new ArrayList<>();
        for (RequestWrapper requestWrapper : dynamicRequests) {
            if (getAlgorithmTime(currentTime) >= getProblemTime(requestWrapper.announceTime)) {
                newRequestIds.add(requestWrapper.requestId);
                newRequestNodes.addAll(requestWrapper.requests);
                reqWrappersToRemove.add(requestWrapper);
            }
        }
        // If new requests gets available
        if (newRequestIds.size() > 0) {
            // Remove the new requests from the dynamic request list
            dynamicRequests.removeAll(reqWrappersToRemove);
            // Re-size structures
            int numNodes = instance.getNumNodes() + newRequestNodes.size(); // Update number of nodes
            Request[] requestNodes = new Request[numNodes - 1]; // Ignore depot (-1)

            // Add existent and new request nodes
            for (Request request : instance.getRequests()) {
                requestNodes[request.nodeId - 1] = request;
            }
            for (Request request : newRequestNodes) {
                requestNodes[request.nodeId - 1] = request;
                instance.getIdleRequests().add(request.requestId);
            }

            // Load requests information
            int numReq = instance.getNumReq() + newRequestIds.size();
            instance.setNumReq(numReq);
            instance.setRequests(requestNodes);
            instance.updateRequestStructures();

            // Update distances matrix
            instance.setNumNodes(numNodes);
            instance.setDistances(new double[numNodes][numNodes]);
            instance.calculateDistances();
            instance.calculateMaxValues();

        }
        return newRequestIds;
    }

    /*
     * Rollback original ID's to compare the solution with the literature results.
     */
    public void rollbackOriginalInformation(Solution solution) {
        // Rollback original request ID's
        for (Request request : instance.getRequests()) {
            request.requestId = requestOriginals.get(request.requestId);
            request.nodeId = nodeOriginals.get(request.nodeId);
        }
        // Convert solution ID's for the original prolem ID's
        ArrayList<Integer> tour, requests;
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            requests = solution.requests.get(k);
            for (int i = 1; i < tour.size() - 1; i++) {
                tour.set(i, nodeOriginals.get(tour.get(i)));
            }
            for (int i = 0; i < requests.size(); i++) {
                requests.set(i, requestOriginals.get(requests.get(i)));
            }
        }
        // Update request structures
        Arrays.sort(instance.getRequests(), Comparator.comparing(Request::getNodeId));
        instance.updateRequestStructures();
        // Update distance structures
        instance.setDistances(new double[instance.getNumNodes()][instance.getNumNodes()]);
        instance.calculateDistances();
        instance.calculateMaxValues();
        // Calculate solution again
        instance.solutionEvaluation(solution);
    }

    private double getAlgorithmTime(double currentTime) {
        return (currentTime - startTime) / (endTime - startTime);
    }

    private double getProblemTime(double currentTime) {
        return (currentTime - instance.getDepot().twStart) / (instance.getDepot().twEnd - instance.getDepot().twStart);
    }

    private class RequestWrapper {
        List<Request> requests = new ArrayList<>();
        Double announceTime;
        Integer requestId;

        public RequestWrapper(Double announceTime, Integer requestId) {
            this.announceTime = announceTime;
            this.requestId = requestId;
        }

        public Double getAnnounceTime() {
            return announceTime;
        }

        public Integer getRequestId() {
            return requestId;
        }
    }
}
