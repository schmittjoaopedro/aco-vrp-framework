package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.util.ArrayList;

public class InsertionMethod {

    private Instance instance;

    public InsertionMethod(Instance instance) {
        this.instance = instance;
    }

    public Position calculateInsertion(Request request, Solution solution, int vehicle) {
        Position position = new Position();
        ArrayList<Integer> originalTour = new ArrayList<>(solution.tours.get(vehicle));
        BestPosition pickupPos = calculateBestPosition(solution, vehicle, request.pickupTask, 0);
        if (pickupPos != null) {
            solution.tours.get(vehicle).add(pickupPos.position, request.pickupTask.nodeId);
            instance.solutionEvaluation(solution); // TODO: Optimize performance
            BestPosition deliveryPos = calculateBestPosition(solution, vehicle, request.deliveryTask, pickupPos.position);
            if (deliveryPos != null) {
                position.cost = solution.tourCosts.get(vehicle) + pickupPos.cost + deliveryPos.cost;
                position.pickupPos = pickupPos.position;
                position.deliveryPos = deliveryPos.position;
            }
        }
        solution.tours.set(vehicle, originalTour);
        instance.solutionEvaluation(solution);
        return position;
    }

    public BestPosition calculateBestPosition(Solution solution, int vehicle, Task task, int prevPos) {
        double deltaBestCost = Double.MAX_VALUE; // \Delta_{i}^{k*} <- Infinity
        BestPosition bestPosition = null; // BestPosition(i, k) <- null
        ArrayList<Integer> route = solution.tours.get(vehicle);
        int prev = route.get(prevPos); // prev <- the depot node 0
        int next = route.get(++prevPos); // next <- first customer of route k
        int currIdx = prevPos;
        double t, tNext, tNewNext, addedDuration, newCost;
        boolean feasible;
        while (currIdx < route.size()) { // while prev <> p + n + 1
            feasible = true;
            t = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, task.nodeId); // t <- vehicleArrivalTimeAt(k, i)
            if (t > task.twEnd) { // if t > bi then
                break; // Exit algorithm 5
            }
            tNext = solution.departureTime.get(vehicle)[currIdx - 1] + instance.dist(prev, next); // Set t_next the actual arrival time at next
            tNewNext = Math.max(t, task.twStart) + task.serviceTime + instance.dist(task.nodeId, next); // t'_next <- arrival time at next if i is inserted before
            addedDuration = tNewNext - tNext; // addedDuration = t'_next - t_next
            if (tNext > instance.twEnd(next) || addedDuration > solution.arrivalSlackTimes.get(vehicle)[currIdx]) { // t_next > b_next  OR addedDuration > slack_next
                feasible = false;
            }
            if (feasible) {
                // NewCost <- C_prev,i + C_i,next - C_prev,next + generateRandomNoise()
                newCost = instance.dist(prev, task.nodeId) + instance.dist(task.nodeId, next) - instance.dist(prev, next);
                if (newCost < deltaBestCost) {
                    route.add(currIdx, task.nodeId); // Insert i after prev in the current solution S'
                    if (instance.isFeasibleTour(route)) { // If S' is feasible then
                        deltaBestCost = newCost; // \Delta_{i}^{k*} <- NewCost
                        bestPosition = new BestPosition(currIdx, deltaBestCost); // BestPosition(i, k) <- prev
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

    public class BestPosition {
        double cost;
        int position;

        public BestPosition(int position, double cost) {
            this.cost = cost;
            this.position = position;
        }
    }
}
