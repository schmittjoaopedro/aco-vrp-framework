package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RegretInsertion extends InsertionOperator {

    private Supplier<Integer> regretLevel;

    public RegretInsertion(ProblemInstance instance, Random random, Supplier<Integer> regretLevel, double useNoiseAtHeuristic) {
        super(instance, random);
        this.regretLevel = regretLevel;
        this.useNoiseAtHeuristic = useNoiseAtHeuristic;
    }

    /*
     * Regret-3, no noise: the selection of the next request to be inserted in the solution is based on a regret criterion.
     * This means that one does not want to have a costly insertion at a later point in time if the current request is not
     * selected now. Here, we set the regret level to 3, and no noise is applied in it computation.
     * Regret-m, no noise: similar to the previous one, the regret level is equals to the number of vehicles m, and no
     * insertion noise is applied.
     * Regret-3, with noise: here, we make us of an insertion noise to allow some extra diversity in the search of the
     * regret-3 computation.
     * Regret-m, with noise: similarly, we use a regret-m criterion to which insertion noise is added.
     */
    public void insertRequests(Solution solution, List<Req> requestsToInsert, PickupMethod pickupMethod, int useNoise) {
        int q = requestsToInsert.size();
        // Cache already processed routes to evict over processing
        RouteCache originalRoutesCache = new RouteCache();
        RouteCache newRoutesCache = new RouteCache();
        // Compute the gain (time difference) for each request insertion
        while (!requestsToInsert.isEmpty()) {
            boolean isLessAvailableVehicles = false; // Indicates when the number of available routes for one request is less than the number of requests to insert
            List<InsertRequest> requestsRegret = new ArrayList<>();
            for (int r = 0; r < requestsToInsert.size(); r++) { // for each request r in requests to insert
                int requestId = requestsToInsert.get(r).requestId;
                List<InsertRequest> feasibleRoutes = new ArrayList<>();
                if (instance.isFullyIdle(requestId)) {
                    if (instance.allowAddVehicles() && solution.tours.get(solution.tours.size() - 1).size() > 2) {
                        // Create a new empty vehicle, if there is not one available, as a possibility to insert the request
                        SolutionUtils.addEmptyVehicle(solution);
                    }
                    for (int k = 0; k < solution.tours.size(); k++) {
                        if (!originalRoutesCache.hasCacheCost(k, requestId)) {
                            originalRoutesCache.setCacheCost(k, requestId, solution.tours.get(k));
                        }
                        double originalCost = originalRoutesCache.getCacheCost(k, requestId);
                        if (!newRoutesCache.hasCacheCost(k, requestId)) {
                            ArrayList<Integer> originalRoute = new ArrayList<>(solution.tours.get(k));
                            if (insertionMethod.insertRequestOnVehicle(solution, k, requestId, pickupMethod)) {
                                // Calculate the lost in cost to be inserting request r in vehicle k
                                newRoutesCache.setCacheCost(k, requestId, solution.tours.get(k));
                                double newCost = newRoutesCache.getCacheCost(k, requestId);
                                double costIncrease = newCost - originalCost;
                                costIncrease += useNoise + (generateRandomNoise() * useNoiseAtHeuristic);
                                feasibleRoutes.add(new InsertRequest(costIncrease, k, requestId, solution.tours.get(k)));
                                solution.tours.set(k, originalRoute);
                                instance.solutionEvaluation(solution, k);
                            } else {
                                newRoutesCache.setCacheCost(k, requestId, null);
                            }
                        } else if (!newRoutesCache.isNull(k, requestId)) {
                            double costDiff = newRoutesCache.getCacheCost(k, requestId) - originalCost;
                            feasibleRoutes.add(new InsertRequest(costDiff, k, requestId, newRoutesCache.getCacheRoute(k, requestId)));
                        }
                    }
                    if (!feasibleRoutes.isEmpty()) {
                        // Sort the vector in ascending order, from the best to worst
                        feasibleRoutes.sort(Comparator.comparing(InsertRequest::getCost));
                        // Get the best request based on regret criterion
                        requestsRegret.add(getRegretRequestValue(feasibleRoutes, regretLevel.get()));
                        if (feasibleRoutes.size() < regretLevel.get()) {
                            isLessAvailableVehicles = true;
                        }
                    }
                } else {
                    int k = requestsToInsert.get(r).vehicleId;
                    instance.solutionEvaluation(solution, k);
                    feasibleRoutes.add(new InsertRequest(solution.tourCosts.get(k), k, requestId, new ArrayList<>(solution.tours.get(k))));
                    requestsRegret.add(getRegretRequestValue(feasibleRoutes, regretLevel.get()));
                }
            }
            if (requestsRegret.isEmpty()) { // No found position for the remaining requests
                break;
            } else {
                if (isLessAvailableVehicles) {
                    // When is not possible to calculate regret value because the regret level is greater than the number of feasible position
                    // the vehicle with small available positions should be taken in account.
                    requestsRegret.sort(Comparator.comparing(InsertRequest::getNumRoutes).thenComparing(InsertRequest::getCost));
                } else {
                    // Sort in descending order, to select the most expensive request based on the regret criterion
                    requestsRegret.sort(Comparator.comparing(InsertRequest::getCost).reversed());
                }
                // Insert the costly insertion on the solution
                InsertRequest reqToInsert = requestsRegret.get(0);
                solution.tours.set(reqToInsert.vehicle, reqToInsert.route);
                SolutionUtils.addRequest(reqToInsert.reqId, reqToInsert.vehicle, solution);
                originalRoutesCache.removeVehicleFromCache(reqToInsert.vehicle);
                newRoutesCache.removeVehicleFromCache(reqToInsert.vehicle);
                // Remove the inserted request from the requests to insert list
                for (int i = 0; i < requestsToInsert.size(); i++) {
                    if (reqToInsert.reqId == requestsToInsert.get(i).requestId) {
                        requestsToInsert.remove(i);
                        break;
                    }
                }
            }
        }
        instance.solutionEvaluation(solution);
    }

    /*
     * Accordingly: Hemmelmayr, V. C., Cordeau, J.-F., & Crainic, T. G. (2012). An adaptive large neighborhood search heuristic
     * for Two-Echelon Vehicle Routing Problems arising in city logistics. Computers & Operations Research, 39(12), 3215–3228.
     *
     * Regret insertion in the regret heuristic, customers are treated in the order of their regret value. The regret value
     * is the cost difference between the best insertion position and the second best. Thus, customers with a high regret value
     * should be inserted first. More precisely, a regret-k heuristic chooses to insert customer i among the set U of untreated
     * customers according to i = \arg max_{i \in U} (\sum_{h=2}^{k} \Delta f_{i}^{h} - \Delta f_{i}^{1}), where \Delta f_{i}^{h}
     * is the cost of insert customer h at the hth cheapest position. This heuristic uses look-ahead information and can prevent
     * situations where we have to insert customers on poor positions because the better positions are no longer available. Once
     * a customer has been inserted, the insertion positions of the remaining unplaced customers have to be recomputed by
     * considering the change caused by inserting this customer at a position.
     */
    private InsertRequest getRegretRequestValue(List<InsertRequest> requests, int level) {
        InsertRequest r1 = requests.get(0); // Obtain the first request
        int k = Math.min(level + 1, requests.size()); // Calculate the k-value, in case the list of requests is smaller that the number of requests
        double regretValue = 0.0;
        // Find worst regret based on cost difference of the k cheapest request related to the current request
        for (int h = 1; h < k; h++) { // Start at the second request
            regretValue += requests.get(h).cost - r1.cost; // Sum the regret cost
        }
        return new InsertRequest(regretValue, r1.vehicle, r1.reqId, r1.route, Math.min(level, requests.size()));
    }

}
