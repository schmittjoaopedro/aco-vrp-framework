package com.github.schmittjoaopedro.vrp.dvrpacs;

import java.util.ArrayList;

/**
 * The controller is the central part of the algorithm, which reads the benchmark data,
 * initializes the data structures, builds an initial solution using nearest neighbor heuristic
 * and starts the ant colony, once the working day has started.
 * <p>
 * Original version: https://github.com/ralucanecula/DVRPTW_ACS
 */
public class VRPTW_ACS {

    private final double weight1 = 0.4;  //0.3   //0.4
    private final double weight2 = 0.4;  //0.5   //0.4
    private final double weight3 = 0.2;  //0.2   //0.2

    /**
     * Weight used in the nearest neighbour heuristic, when computing an initial solution to calculate the
     * value of the initial pheromone trail.
     */
    private double initialWeight1;
    private double initialWeight2;
    private double initialWeight3;

    private boolean lsSearch;

    private LocalSearchOperator localSearchOperator;

    public VRPTW_ACS(boolean localSearch) {
        super();
        setLsSearch(localSearch);
        if (localSearch) {
            setLocalSearchOperator(new LocalSearchOperator());
        }
    }

    // Generate a nearest neighbor tour and compute tour length using only the available nodes (nodes known so far)
    public double getNNTourCost(Ants ants, VRPTW instance, double scalingValue) {
        int step;
        double sum = 0, sum1 = 0, scaledValue = 0;
        Ant ant = ants.getAnts()[0];

        ant.emptyMemory(instance);

        for (int i = 0; i < ant.getUsedVehicles(); i++) {
            // Place the ant on the depot city, which is the start city of each tour -1 is a special marker
            // for the deport city, so that it's not be confused with the rest of the cities all the rest of
            // the cities are represented by integer values > 0
            ant.getTours().get(i).add(-1);
        }

        // There are still left available (known) cities to be visited
        while (ant.getToVisit() > 0) {
            int salesman = ant.getUsedVehicles() - 1;
            chooseClosestNext(ant, salesman, instance);
        }

        int nrTours = ant.getUsedVehicles();
        for (int i = 0; i < nrTours; i++) {
            step = ant.getTours().get(i).size();
            ant.getTours().get(i).add(step, -1);
            ant.getTourLengths().set(i, instance.computeTourLength(ant.getTours().get(i)));
            sum1 += ant.getTourLengths().get(i);
        }
        ant.setTotalTourLength(sum1);

        // Execute local search phase
        if (isLsSearch()) {
            ant = localSearchOperator.relocateMultipleRouteIterated(ants, ant, instance);
            ant = localSearchOperator.exchangeMultipleRouteIterated(ants, ant, instance);
            // Compute new distances and update longest tour
            for (int l = 0; l < ant.getUsedVehicles(); l++) {
                ant.getTourLengths().set(l, instance.computeTourLength(ant.getTours().get(l)));
                sum += ant.getTourLengths().get(l);
            }
            ant.setTotalTourLength(sum);
        }

        if (scalingValue != 0) {
            scaledValue = ant.getTotalTourLength() / scalingValue;
        }
        System.out.println("\nInitial (nearest neighbour tour) total tour length: " + ant.getTotalTourLength() +
                " (scalled value = " + scaledValue + "); Number of vehicles used: " + ant.getUsedVehicles());
        sum1 = ant.getTotalTourLength();

        // Initialize best solution so far with this solution constructed by the nearest neighbour heuristic
        ants.copyFromTo(ant, ants.getBestSoFarAnt(), instance);
        ant.emptyMemory(instance);
        return sum1;
    }

    public void chooseClosestNext(Ant ant, int indexSalesman, VRPTW instance) {
        double bestBeginService = 0;
        double distances[][] = instance.getProblem().getDistance();
        ArrayList<Request> requestList = instance.getRequests();
        ArrayList<Integer> idKnownRequests = instance.getIdAvailableRequests();
        int nextCity = instance.getN();
        int lastPos = ant.getTours().get(indexSalesman).size() - 1;
        int currentCity = ant.getTours().get(indexSalesman).get(lastPos);
        currentCity++;
        double minValue = Integer.MAX_VALUE;
        for (int city : idKnownRequests) {
            if (!ant.getVisited()[city]) { // City already not visited
                // Next city metrics
                double distance = distances[currentCity][city + 1];
                double arrivalTime = ant.getCurrentTime().get(indexSalesman) + requestList.get(currentCity).getServiceTime() + distance;
                double beginService = Math.max(arrivalTime, requestList.get(city + 1).getStartWindow());
                // Depot metrics
                double distanceDepot = distances[city + 1][0];
                double arrivalTimeDepot = beginService + requestList.get(city + 1).getServiceTime() + distanceDepot;
                double beginServiceDepot = Math.max(arrivalTimeDepot, requestList.get(0).getStartWindow());

                if (isFeasible(instance, ant, city, beginService, beginServiceDepot, indexSalesman)) {
                    // Compute the value of the "closeness" metric; this metric tries to account
                    // for both geographical and temporal closeness of customers
                    double timeDifference = beginService - ant.getBeginService()[currentCity] - requestList.get(currentCity).getServiceTime();
                    double deliveryUrgency = requestList.get(city + 1).getEndWindow() - (ant.getBeginService()[currentCity] + requestList.get(currentCity).getServiceTime() + distance);
                    double metricValue = getWeight1() * distance + getWeight2() * timeDifference + getWeight3() * deliveryUrgency;

                    if (metricValue < minValue) {
                        nextCity = city;
                        minValue = metricValue;
                        bestBeginService = beginService;
                    }
                }
            }
        }
        /*
         * It means that not all the cities are covered and some customers has not yet been serviced
         * by using insertion heuristic try to insert in the current tour of the solution under construction
         * the customers not visited yet when no more customer with feasible insertions can be found,
         * start a new route/tour and add one more vehicle for constructing a feasible solution
         */
        if (nextCity == instance.getN()) {
            // If no more un-routed customers can be feasible inserted in the solution and there are still
            // remaining un-routed customers, add a new tour
            if (ant.getToVisit() > 0) {
                ant.setUsedVehicles(ant.getUsedVehicles() + 1);
                int indexTour = ant.getUsedVehicles() - 1;
                ant.getTours().add(indexTour, new ArrayList<>());
                ant.getTours().get(indexTour).add(-1);
                ant.getTourLengths().add(indexTour, 0.0);
                ant.getCurrentQuantity().add(indexTour, 0.0);
                ant.getCurrentTime().add(indexTour, 0.0);
            }

        } else {
            ant.getTours().get(indexSalesman).add(nextCity);
            ant.getVisited()[nextCity] = true;
            ant.setToVisit(ant.getToVisit() - 1);
            ant.getCurrentTime().set(indexSalesman, bestBeginService);
            ant.getBeginService()[nextCity + 1] = bestBeginService;
            ant.getCurrentQuantity().set(indexSalesman, ant.getCurrentQuantity().get(indexSalesman) + requestList.get(nextCity + 1).getDemand());
        }

    }

    /**
     * Check:
     * - upper bound of the time window of the next customer to be visited &&
     * - current capacity of the car &&
     * - vehicle arrival time at the depot (the maximum total route time -> upper bound of the time window of the depot)
     */
    private boolean isFeasible(VRPTW instance, Ant ant, int city, double beginService, double beginServiceDepot, int indexSalesman) {
        ArrayList<Request> requestList = instance.getRequests();
        double currentQuantity = ant.getCurrentQuantity().get(indexSalesman) + requestList.get(city + 1).getDemand();
        return beginService <= requestList.get(city + 1).getEndWindow() &&
                currentQuantity <= instance.getCapacity() &&
                beginServiceDepot <= requestList.get(0).getEndWindow();
    }

    public boolean isLsSearch() {
        return lsSearch;
    }

    public void setLsSearch(boolean lsSearch) {
        this.lsSearch = lsSearch;
    }

    public double getWeight1() {
        return weight1;
    }

    public double getWeight2() {
        return weight2;
    }

    public double getWeight3() {
        return weight3;
    }

    public double getInitialWeight1() {
        return initialWeight1;
    }

    public void setInitialWeight1(double initialWeight1) {
        this.initialWeight1 = initialWeight1;
    }

    public double getInitialWeight2() {
        return initialWeight2;
    }

    public void setInitialWeight2(double initialWeight2) {
        this.initialWeight2 = initialWeight2;
    }

    public double getInitialWeight3() {
        return initialWeight3;
    }

    public void setInitialWeight3(double initialWeight3) {
        this.initialWeight3 = initialWeight3;
    }

    public LocalSearchOperator getLocalSearchOperator() {
        return localSearchOperator;
    }

    public void setLocalSearchOperator(LocalSearchOperator localSearchOperator) {
        this.localSearchOperator = localSearchOperator;
    }
}
