package com.github.schmittjoaopedro.vrp.mpdptw;

import java.util.ArrayList;

public class MovingVehicle {

    private ProblemInstance instance;

    private double startTime;

    private double endTime;

    // Fix what clients were committed/transition by vehicles during the working day
    private ArrayList<ArrayList<Integer>> visitedNodes = new ArrayList<>();

    public MovingVehicle(ProblemInstance instance, double startTime, double endTime) {
        this.instance = instance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean moveVehicle(Solution solution, double currentTime) {
        Request req;
        ArrayList<Integer> tour;
        boolean moved = false;
        double time = getScaledAlgorithmTime(currentTime);
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            for (int i = 1; i < tour.size() - 1; i++) { // Ignore depots at start and end
                req = instance.getRequest(tour.get(i));
                if (!req.isCommitted()) {
                    if (time >= getScaledProblemTime(Math.max(instance.twStart(tour.get(i)), solution.arrivalTime.get(k)[i])) && !req.isCommitted()) {
                        // If the vehicle arrived at the client, the request is committed.
                        req.status = Request.Status.Committed;
                        moved = true;
                        instance.getIdleRequests().remove(req.requestId);
                        fixCommittedRequest(req, k);
                    } else if (time >= getScaledProblemTime(solution.departureTime.get(k)[i - 1]) && !req.isTransition()) {
                        // If the vehicle departed from previous client, the current request is in transition.
                        req.status = Request.Status.Transition;
                        moved = true;
                        instance.getIdleRequests().remove(req.requestId);
                        fixTransitionRequest(req, k);
                    }
                }
            }
        }
        checkConsistencyWithVisitedNodes(solution);
        return moved;
    }

    private void fixTransitionRequest(Request req, int k) {
        if (visitedNodes.size() <= k) {
            visitedNodes.add(new ArrayList<>());
        }
        visitedNodes.get(k).add(req.nodeId);
    }

    private void fixCommittedRequest(Request req, int k) {
        if (visitedNodes.size() <= k) {
            visitedNodes.add(new ArrayList<>());
        }
        if (!visitedNodes.get(k).contains(req.nodeId)) {
            visitedNodes.get(k).add(req.nodeId);
        }
    }

    private void checkConsistencyWithVisitedNodes(Solution solution) {
        for (int i = 0; i < visitedNodes.size(); i++) {
            for (int j = 0; j < visitedNodes.get(i).size(); j++) {
                if (solution.tours.get(i).get(j + 1) != visitedNodes.get(i).get(j)) { // Using j+1 to ignore depot node on solution
                    throw new RuntimeException("Moving vehicles routes are different from best solution");
                }
            }
        }
    }

    private void printPartialRoutes(Solution solution) {
        System.out.println(">cls");
        ArrayList<Integer> tour;
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            String route = "Vehicle " + k + ": ";
            for (int i = 1; i < tour.size() - 1; i++) {
                if (!instance.isIdle(tour.get(i))) {
                    route += "[" + instance.getRequestId(tour.get(i)) + "|" + tour.get(i);
                    if (instance.getRequest(tour.get(i)).isTransition()) {
                        route += "*] ";
                    } else {
                        route += "] ";
                    }
                } else {
                    route += instance.getRequestId(tour.get(i)) + "|" + tour.get(i) + " ";
                }
            }
            System.out.println(route);
        }
        try {
            // Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getScaledAlgorithmTime(double currentTime) {
        return (currentTime - startTime) / (endTime - startTime);
    }

    private double getScaledProblemTime(double currentTime) {
        return (currentTime - instance.getDepot().twStart) / (instance.getDepot().twEnd - instance.getDepot().twStart);
    }

}
