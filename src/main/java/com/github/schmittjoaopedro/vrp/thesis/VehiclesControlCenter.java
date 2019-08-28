package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.InsertionService;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;

public class VehiclesControlCenter {

    private Instance instance;

    private double startTime;

    private double endTime;

    private InsertionService insertionService;

    // Fix what clients were committed/transition by vehicles during the working day
    private ArrayList<ArrayList<Integer>> visitedNodes = new ArrayList<>();

    public VehiclesControlCenter(Instance instance, double startTime, double endTime) {
        this.instance = instance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.insertionService = new InsertionService(instance);
    }

    public boolean moveVehicle(Solution solution, double currentTime) {
        Task task;
        ArrayList<Integer> tour;
        RouteTimes routeTimes;
        boolean moved = false;
        double time = getScaledAlgorithmTime(currentTime);
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            routeTimes = new RouteTimes(tour.size());
            insertionService.calculateRouteTimes(tour, routeTimes);
            for (int i = 1; i < tour.size() - 1; i++) { // Ignore depots at start and end
                task = instance.getTask(tour.get(i));
                if (!task.isCommitted()) {
                    if (time >= getScaledProblemTime(routeTimes.startTime[i]) && !task.isCommitted()) {
                        // If the vehicle arrived at the client, the request is committed.
                        task.status = Task.Status.Committed;
                        moved = true;
                        fixCommittedRequest(task, k);
                    } else if (time >= getScaledProblemTime(routeTimes.departureTime[i - 1]) && !task.isTransition()) {
                        // If the vehicle departed from previous client, the current request is in transition.
                        task.status = Task.Status.Transition;
                        moved = true;
                        fixTransitionRequest(task, k);
                    }
                }
            }
        }
        checkConsistencyWithVisitedNodes(solution);
        return moved;
    }

    private void fixTransitionRequest(Task task, int k) {
        if (visitedNodes.size() <= k) {
            visitedNodes.add(new ArrayList<>());
        }
        visitedNodes.get(k).add(task.nodeId);
    }

    private void fixCommittedRequest(Task task, int k) {
        if (visitedNodes.size() <= k) {
            visitedNodes.add(new ArrayList<>());
        }
        if (!visitedNodes.get(k).contains(task.nodeId)) {
            visitedNodes.get(k).add(task.nodeId);
        }
    }

    private void checkConsistencyWithVisitedNodes(Solution solution) {
        for (int i = 0; i < visitedNodes.size(); i++) {
            for (int j = 0; j < visitedNodes.get(i).size(); j++) {
                if (solution.tours.get(i).get(j + 1).intValue() != visitedNodes.get(i).get(j).intValue()) { // Using j+1 to ignore depot node on solution
                    throw new RuntimeException("Moving vehicles routes are different from best solution");
                }
            }
        }
    }

    private void printPartialRoutes(Solution solution) {
        System.out.println(">cls");
        ArrayList<Integer> tour;
        Task task;
        for (int k = 0; k < solution.tours.size(); k++) {
            tour = solution.tours.get(k);
            String route = "Vehicle " + k + ": ";
            for (int i = 1; i < tour.size() - 1; i++) {
                task = instance.getTask(tour.get(i));
                if (!task.isIdle()) {
                    route += "[" + task.requestId + "|" + tour.get(i);
                    if (task.isTransition()) {
                        route += "*] ";
                    } else {
                        route += "] ";
                    }
                } else {
                    route += task.requestId + "|" + tour.get(i) + " ";
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
        return (currentTime - instance.depot.twStart) / (instance.depot.twEnd - instance.depot.twStart);
    }
}
