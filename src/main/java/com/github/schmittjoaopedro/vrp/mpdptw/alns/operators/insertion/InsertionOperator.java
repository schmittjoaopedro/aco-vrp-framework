package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;

import java.util.*;

public class InsertionOperator {

    protected ProblemInstance instance;

    protected InsertionMethod insertionMethod;

    protected Random random;

    protected double useNoiseAtHeuristic = 0.0;

    public InsertionOperator(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionMethod = new InsertionMethod(instance, random);
    }

    public void insertRequests(Solution solution, List<Req> requestsToInsert, InsertionMethod.PickupMethod pickupMethod, int useNoise) {
        throw new RuntimeException("Not implemented yet");
    }

    protected double generateRandomNoise() {
        return (2 * random.nextDouble() * instance.getMaxDistance()) - instance.getMaxDistance();
    }

    public double getUseNoiseAtHeuristic() {
        return useNoiseAtHeuristic;
    }

    public void setUseNoiseAtHeuristic(double useNoiseAtHeuristic) {
        this.useNoiseAtHeuristic = useNoiseAtHeuristic;
    }

    protected class RouteCache {

        // Cache routes based on a given key. In this case the composition of vehicle and request.
        private Map<String, InsertRequest> cache = new HashMap<>();

        // Used o test if a vehicle with for a given request is infeasible
        protected boolean isNull(int k, int r) {
            return cache.get(k + "-" + r) == null;
        }

        protected ArrayList<Integer> getCacheRoute(int k, int r) {
            return cache.get(k + "-" + r).route;
        }

        protected Double getCacheCost(int k, int r) {
            return cache.get(k + "-" + r).cost;
        }

        protected void setCacheCost(int k, int r, ArrayList<Integer> route) {
            String key = k + "-" + r;
            if (!cache.containsKey(key)) {
                if (route != null) {
                    cache.put(key, new InsertRequest(instance.costEvaluation(route), k, r, route));
                } else {
                    cache.put(key, null);
                }
            }
        }

        protected boolean hasCacheCost(int k, int r) {
            return cache.containsKey(k + "-" + r);
        }

        // Remove all cache entries associated with the given vehicle
        protected void removeVehicleFromCache(int k) {
            String key = k + "-";
            Set<String> keySet = new HashSet<>(cache.keySet());
            for (String hashKey : keySet) {
                if (hashKey.startsWith(key)) {
                    cache.remove(hashKey);
                }
            }
        }
    }

    public class InsertRequest {
        public double cost;
        public int vehicle;
        public int reqId;
        public ArrayList<Integer> route;
        public int numRoutes;

        public InsertRequest(double cost, int vehicle, int reqId, ArrayList<Integer> route) {
            this.cost = cost;
            this.vehicle = vehicle;
            this.reqId = reqId;
            this.route = route;
        }

        public InsertRequest(double cost, int vehicle, int reqId, ArrayList<Integer> route, int numRoutes) {
            this.cost = cost;
            this.vehicle = vehicle;
            this.reqId = reqId;
            this.route = route;
            this.numRoutes = numRoutes;
        }

        public double getCost() {
            return cost;
        }

        public int getNumRoutes() {
            return numRoutes;
        }

        @Override
        public String toString() {
            return "(" + vehicle + "," + reqId + ") = " + cost;
        }
    }

}
