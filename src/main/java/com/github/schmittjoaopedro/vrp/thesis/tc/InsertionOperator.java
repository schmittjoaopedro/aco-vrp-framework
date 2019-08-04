package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class InsertionOperator {

    private Instance instance;

    private Random random;

    private InsertionMethod insertionMethod;

    private double noiseControl = 0.025;

    public InsertionOperator(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionMethod = new InsertionMethod(instance, false);
    }

    public InsertionOperator(Instance instance, Random random, boolean useNoise) {
        this.instance = instance;
        this.random = random;
        this.insertionMethod = new InsertionMethod(instance, useNoise);
    }

    public void insertGreedyRequests(Solution solution, List<Request> requestsToInsert, int useNoise) {
        while (!requestsToInsert.isEmpty()) {
            Position bestPosition = null;
            for (int r = 0; r < requestsToInsert.size(); r++) {
                Request currReq = requestsToInsert.get(r);
                for (int k = 0; k < solution.tours.size(); k++) {
                    Position insertPosition = insertionMethod.calculateInsertion(currReq, solution, k);
                    if (insertPosition.cost < Double.MAX_VALUE) {
                        insertPosition.cost += useNoise * generateNoise();
                        if (bestPosition == null || insertPosition.cost < bestPosition.cost) {
                            bestPosition = insertPosition;
                            bestPosition.requestId = currReq.requestId;
                            bestPosition.vehicle = k;
                        }
                    }
                }
            }
            if (bestPosition == null) {
                break;
            } else {
                solution.insertRequest(instance, bestPosition.requestId, bestPosition.vehicle, bestPosition.pickupPos, bestPosition.deliveryPos);
                instance.solutionEvaluation(solution, bestPosition.vehicle);
                for (Request request : requestsToInsert) {
                    if (request.requestId == bestPosition.requestId) {
                        requestsToInsert.remove(request);
                        break;
                    }
                }
            }
        }
        instance.solutionEvaluation(solution);
    }

    public void insertRegretRequests(Solution solution, List<Request> requestsToInsert, int regret, int useNoise) {
        while (!requestsToInsert.isEmpty()) {
            List<Position> requestsRegret = new ArrayList<>();
            for (int r = 0; r < requestsToInsert.size(); r++) {
                Request request = requestsToInsert.get(r);
                List<Position> feasibleRoutes = new ArrayList<>();
                for (int k = 0; k < solution.tours.size(); k++) {
                    Position insertPosition = insertionMethod.calculateInsertion(request, solution, k);
                    if (insertPosition.cost < Double.MAX_VALUE) {
                        insertPosition.cost += useNoise * generateNoise();
                        insertPosition.vehicle = k;
                        insertPosition.requestId = request.requestId;
                        feasibleRoutes.add(insertPosition);
                    }
                }
                if (!feasibleRoutes.isEmpty()) {
                    feasibleRoutes.sort(Comparator.comparing(Position::getCost));
                    requestsRegret.add(getRegretRequestValue(feasibleRoutes, regret));
                }
            }
            if (requestsRegret.isEmpty()) {
                break;
            } else {
                requestsRegret.sort(Comparator.comparing(Position::getCost).reversed());
                Position reqToInsert = requestsRegret.get(0);
                solution.insertRequest(instance, reqToInsert.requestId, reqToInsert.vehicle, reqToInsert.pickupPos, reqToInsert.deliveryPos);
                instance.solutionEvaluation(solution, reqToInsert.vehicle);
                for (int i = 0; i < requestsToInsert.size(); i++) {
                    if (reqToInsert.requestId == requestsToInsert.get(i).requestId) {
                        requestsToInsert.remove(i);
                        break;
                    }
                }
            }
        }
        instance.solutionEvaluation(solution);
    }

    private Position getRegretRequestValue(List<Position> requests, int level) {
        Position r1 = requests.get(0);
        int k = Math.min(level + 1, requests.size());
        double regretValue = 0.0;
        for (int h = 1; h < k; h++) {
            regretValue += requests.get(h).cost - r1.cost;
        }
        r1.cost = regretValue;
        return r1;
    }

    private double generateNoise() {
        return (random.nextDouble() - 0.5) * (noiseControl * instance.maxDistance) * 2.0;
    }
}
