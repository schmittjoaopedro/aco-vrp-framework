package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertionService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VehiclesControlCenter {

    private Instance instance;

    private InsertionService insertionService;

    // Fix what clients were committed/transition by vehicles during the working day
    private Map<Integer, ArrayList<Integer>> visitedNodes = new HashMap<>();

    public VehiclesControlCenter(Instance instance) {
        this.instance = instance;
        this.insertionService = new InsertionService(instance);
    }

    public boolean moveVehicle(Solution solution) {
        Task task;
        ArrayList<Integer> tour;
        RouteTimes routeTimes;
        double currentTime = instance.currentTime;
        boolean moved = false;
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            routeTimes = new RouteTimes(tour, instance);
            for (int i = 1; i < tour.size() - 1; i++) { // Ignore depots at start and end
                task = instance.getTask(tour.get(i));
                if (!task.isCommitted()) {
                    if (currentTime >= routeTimes.departureTime[i - 1] && !task.isTransition()) {
                        // If the vehicle departed from previous client, the current request is in transition.
                        task.status = Task.Status.Transition;
                        moved = true;
                        fixTransitionRequest(task, tour.get(1));
                    }
                    if (currentTime >= routeTimes.departureTime[i] && !task.isCommitted()) {
                        // If the vehicle arrived at the client, the request is committed.
                        task.status = Task.Status.Committed;
                        moved = true;
                        fixCommittedRequest(task, tour.get(1));
                    }
                }
            }
        }
        for (Request req : instance.requests) {
            if (req.pickupTask.isIdle()) {
                // When it let to be idle, then the startVisitTime will be the time that
                // vehicle starts to move to this request
                req.lastIdleTime = instance.currentTime;
            }
        }
        checkConsistencyWithVisitedNodes(solution);
        return moved;
    }

    private void fixTransitionRequest(Task task, Integer firstClientNode) {
        if (!instance.isDepot(firstClientNode)) {
            visitedNodes.computeIfAbsent(firstClientNode, (key) -> new ArrayList<>()).add(task.nodeId);
        }
    }

    private void fixCommittedRequest(Task task, Integer firstClientNode) {
        if (!instance.isDepot(firstClientNode)) {
            ArrayList<Integer> tour = visitedNodes.computeIfAbsent(firstClientNode, (key) -> new ArrayList<>());
            if (!tour.contains(task.nodeId)) {
                tour.add(task.nodeId);
            }
        }
    }

    private void checkConsistencyWithVisitedNodes(Solution solution) {
        ArrayList<Integer> solutionTour;
        ArrayList<Integer> vehicleTour;
        for (int i = 0; i < solution.tours.size(); i++) {
            solutionTour = solution.tours.get(i);
            if (visitedNodes.containsKey(solutionTour.get(1))) {
                vehicleTour = visitedNodes.get(solutionTour.get(1));
                for (int j = 0; j < vehicleTour.size(); j++) {
                    if (solutionTour.get(j + 1).intValue() != vehicleTour.get(j).intValue()) { // Using j+1 to ignore depot node on solution
                        throw new RuntimeException("Moving vehicles routes are different from best solution");
                    }
                }
            }
        }
    }
}
