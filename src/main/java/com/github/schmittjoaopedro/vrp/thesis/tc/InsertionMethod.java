package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;

public class InsertionMethod {

    private Instance instance;

    private boolean useNoise;

    public InsertionMethod(Instance instance) {
        this.instance = instance;
        this.useNoise = false;
    }

    public InsertionMethod(Instance instance, boolean useNoise) {
        this.instance = instance;
        this.useNoise = useNoise;
    }

    public Position calculateInsertion(Request request, Solution solution, int vehicle) {
        Position position = new Position();
        ArrayList<Integer> originalTour = new ArrayList<>(solution.tours.get(vehicle));
        BestPosition pickupPos = calculateBestPosition(solution, vehicle, request.pickupTask, 0);
        if (pickupPos != null) {
            solution.tours.get(vehicle).add(pickupPos.position, request.pickupTask.nodeId);
            instance.solutionEvaluation(solution, vehicle); // TODO: Optimize performance
            BestPosition deliveryPos = calculateBestPosition(solution, vehicle, request.deliveryTask, pickupPos.position);
            if (deliveryPos != null) {
                position.cost = solution.tourCosts.get(vehicle) + pickupPos.cost + deliveryPos.cost;
                position.pickupPos = pickupPos.position;
                position.deliveryPos = deliveryPos.position;
            }
        }
        solution.tours.set(vehicle, originalTour);
        instance.solutionEvaluation(solution, vehicle);
        return position;
    }

    public BestPosition calculateBestPosition(Solution solution, int vehicle, Task task, int prevPos) {
        double deltaBestCost = Double.MAX_VALUE;
        BestPosition bestPosition = null;
        ArrayList<Integer> route = solution.tours.get(vehicle);
        int prev = route.get(prevPos);
        int next = route.get(++prevPos);
        int currIdx = prevPos;
        double t, tNext, tNewNext, addedDuration, newCost;
        boolean feasible;
        while (currIdx < route.size()) {
            feasible = true;
            t = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, task.nodeId);
            if (t > task.twEnd) {
                break;
            }
            tNext = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, next);
            tNewNext = Math.max(t, task.twStart) + task.serviceTime + instance.dist(task.nodeId, next);
            addedDuration = tNewNext - tNext;
            if (tNext > instance.twEnd(next) || addedDuration > solution.arrivalSlackTimes.get(vehicle)[currIdx]) {
                feasible = false;
            }
            if (feasible) {
                newCost = instance.dist(prev, task.nodeId) + instance.dist(task.nodeId, next) - instance.dist(prev, next) + generateRandomNoise();
                if (newCost < deltaBestCost) {
                    route.add(currIdx, task.nodeId);
                    if (instance.isFeasibleTour(route)) {
                        deltaBestCost = newCost;
                        bestPosition = new BestPosition(currIdx, deltaBestCost);
                    }
                    route.remove(currIdx);
                }
            }
            prev = next;
            if (prev == instance.depot.nodeId) {
                break;
            }
            currIdx++;
            next = route.get(currIdx);
        }
        return bestPosition;
    }

    private double generateRandomNoise() {
        if (useNoise) {
            return (2.0 * Math.random() * instance.maxDistance) - instance.maxDistance;
        } else {
            return 0.0;
        }
    }

    public class BestPosition {
        double cost;
        int position;

        public BestPosition(int position, double cost) {
            this.cost = cost;
            this.position = position;
        }
    }
}
