package com.github.schmittjoaopedro.vrp.dvrptwacs;

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

    private Controller controller;

    public VRPTW_ACS(Controller controller, boolean localSearch) {
        super();
        setLsSearch(localSearch);
        setController(controller);
        if (localSearch) {
            setLocalSearchOperator(new LocalSearchOperator(controller));
        }
    }

    public void chooseClosestNN(Ant ant, int indexSalesman, VRPTW vrp) {
        int currentCity, nextCity;
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDifference, deliveryUrgency, bestBeginService = 0, minValue, metricValue;
        double distances[][] = vrp.getProblem().getDistance();
        ArrayList<Request> reqList = vrp.getRequests();
        ArrayList<Integer> idKnownRequests = vrp.getIdAvailableRequests();
        while (ant.getToVisit() > 0) {
            nextCity = vrp.getN();
            int lastPos = ant.getTours().get(indexSalesman).size() - 1;
            currentCity = ant.getTours().get(indexSalesman).get(lastPos);
            currentCity++;
            minValue = Integer.MAX_VALUE;
            for (int city : idKnownRequests) {
                if (!ant.getVisited()[city]) { // City already not visited
                    distance = distances[currentCity][city + 1];
                    arrivalTime = ant.getCurrentTime().get(indexSalesman) + reqList.get(currentCity).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    distanceDepot = distances[city + 1][0];
                    arrivalTimeDepot = beginService + reqList.get(city + 1).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                    if (isFeasible(vrp, ant, city, beginService, beginServiceDepot, indexSalesman)) {
                        // Compute the value of the "closeness" metric; this metric tries to account
                        // for both geographical and temporal closeness of customers
                        timeDifference = beginService - ant.getBeginService()[currentCity] - reqList.get(currentCity).getServiceTime();
                        deliveryUrgency = reqList.get(city + 1).getEndWindow() - (ant.getBeginService()[currentCity] + reqList.get(currentCity).getServiceTime() + distance);
                        metricValue = getWeight1() * distance + getWeight2() * timeDifference + getWeight3() * deliveryUrgency;
                        if (metricValue < minValue) {
                            nextCity = city;
                            minValue = metricValue;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
            // No more nodes can be feasible added in the tour
            if (nextCity == vrp.getN()) {
                break;
            } else {
                ant.getTours().get(indexSalesman).add(nextCity);
                ant.getVisited()[nextCity] = true;
                ant.setToVisit(ant.getToVisit() - 1);
                ant.getCurrentTime().set(indexSalesman, bestBeginService);
                ant.getBeginService()[nextCity + 1] = bestBeginService;
                ant.getCurrentTime().set(indexSalesman, ant.getCurrentQuantity().get(indexSalesman) + reqList.get(nextCity + 1).getDemand());
            }
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


    /**
     * Manage the solution construction phase (construct a set of complete and closed tours, one
     * for each vehicle)
     */
    public void constructSolutions(VRPTW instance, Ants ants, InOut inOut, Utilities utilities) {
        int k; // counter variable
        int step = 0; // counter of the number of construction steps
        int values[];

        /* Mark all cities as unvisited */
        for (k = 0; k < ants.getNumAnts(); k++) {
            ants.getAnts()[k].emptyMemory(instance);
        }

        /* Place the ants on same initial city, which is the depot city */
        for (k = 0; k < ants.getNumAnts(); k++) {
            for (int i = 0; i < ants.getAnts()[k].getUsedVehicles(); i++) {
                // Place each ant on the depot city, which is the start city of each tour -1 is a special marker
                // for the deport city, so that it's not be confused with the rest of the cities all the rest of
                // the cities are represented by integer values > 0
                ants.getAnts()[k].getTours().get(i).add(-1);
            }
        }

        // Initialize ant solution with the committed nodes (if any) from each tour of the best so far solution
        if (controller.checkCommittedTours(ants)) {
            for (k = 0; k < ants.getNumAnts(); k++) {
                controller.addCommittedNodes(ants, ants.getAnts()[k], instance);
            }
        }

        while (!isDone(ants)) {
            for (k = 0; k < ants.getNumAnts(); k++) {
                if (ants.getAnts()[k].getToVisit() > 0) {
                    // Choose for each ant in a probabilistic way by some type of roulette wheel selection which
                    // salesman to consider next, that will visit a city salesman = (int)(Math.random() * MTsp.m)
                    values = neighbourChooseAndMoveToNext(ants.getAnts()[k], instance, ants, utilities);
                    if (values[0] != -1) {
                        ants.localAcsPheromoneUpdate(ants.getAnts()[k], values[1]);
                    }
                }
            }
        }
        double longestTourLength;
        int idLongestTour = 0;
        for (k = 0; k < ants.getNumAnts(); k++) {
            Ant ant = ants.getAnts()[k];
            inOut.setNoSolutions(inOut.getNoSolutions() + 1);
            longestTourLength = Double.MIN_VALUE;
            for (int i = 0; i < ant.getUsedVehicles(); i++) {
                step = ant.getTours().get(i).size();
                ant.getTours().get(i).add(step, -1);
                ant.getTourLengths().set(i, instance.computeTourLengthDepotOnlyInFirstPosition(ant.getTours().get(i)));
                ant.setTotalTourLength(ant.getTotalTourLength() + ant.getTourLengths().get(i));
                if (longestTourLength < ant.getTourLengths().get(i)) {
                    longestTourLength = ant.getTourLengths().get(i);
                    idLongestTour = i;
                }
                ants.localAcsPheromoneUpdate(ant, i);
            }
            ant.setLongestTourLength(longestTourLength);
            ant.setIndexLongestTour(idLongestTour);
            ant.getCostObjectives()[0] = ant.getTotalTourLength();
            ant.getCostObjectives()[1] = ant.computeToursAmplitude();
        }
        inOut.setNumTours(ants.getNumAnts() * ants.getAnts()[0].getUsedVehicles()); // Each ant constructs a complete and closed tour
    }

    // Check if there is still an ant with left cities to visit
    private boolean isDone(Ants ants) {
        for (int k = 0; k < ants.getNumAnts(); k++) {
            if (ants.getAnts()[k].getToVisit() > 0) {
                return false;
            }
        }
        return true;
    }

    // Choose for an ant probabilistically a next city among all unvisited cities in the current city's candidate list
    private int[] neighbourChooseAndMoveToNext(Ant a, VRPTW vrp, Ants ants, Utilities utilities) {
        int i, j, help, city, salesman = 0;
        int currentCity;
        double rnd, partialSum, sumProb = 0.0;
        double probPtr[][];
        double help1;
        int[] values = new int[2];
        int[] tempCities = new int[a.getUsedVehicles()];
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double deliveryUrgency, timeDifference;
        ArrayList<Request> reqList = vrp.getRequests();


        if ((ants.getQ0() > 0.0) && (utilities.random01() < ants.getQ0())) {
            /*
             * With a probability q_0 make the best possible choice according to pheromone trails
             * and heuristic information, this corresponds to exploitation.
             *
             * We first check whether q_0 > 0.0, to avoid the very common case of q_0 = 0.0
             * to have to compute a random number, which is expensive computationally.
             */
            values = neighbourChooseBestNext(ants, a, vrp);
            return values;
        }

        probPtr = new double[a.getUsedVehicles()][vrp.getProblem().getNumNear() + 1];
        for (j = 0; j < a.getUsedVehicles(); j++) {
            for (i = 0; i < vrp.getProblem().getNumNear(); i++) {
                probPtr[j][i] = Double.POSITIVE_INFINITY;
            }
        }
        for (j = 0; j < (a.getUsedVehicles() - 1); j++) {
            probPtr[j][vrp.getProblem().getNumNear()] = 0;
        }
        probPtr[a.getUsedVehicles() - 1][vrp.getProblem().getNumNear()] = Double.POSITIVE_INFINITY;

        for (int indexSalesman = 0; indexSalesman < a.getUsedVehicles(); indexSalesman++) {
            // Current_city city of ant k
            int lastPos = a.getTours().get(indexSalesman).size() - 1;
            currentCity = a.getTours().get(indexSalesman).get(lastPos);
            currentCity++;
            for (i = 0; i < vrp.getProblem().getNumNear(); i++) {
                city = vrp.getProblem().getNnList()[currentCity][i];
                distance = vrp.getProblem().getDistance()[currentCity][city];
                arrivalTime = a.getCurrentTime().get(indexSalesman) + reqList.get(currentCity).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(city).getStartWindow());
                distanceDepot = vrp.getProblem().getDistance()[city][0];
                arrivalTimeDepot = beginService + reqList.get(city).getServiceTime() + distanceDepot;
                beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                if (!(vrp.getIdAvailableRequests().contains(city - 1)) || (a.getVisited()[city - 1]) || (!isFeasible(vrp, a, city - 1, beginService, beginServiceDepot, indexSalesman)))
                    probPtr[indexSalesman][i] = 0.0; // City already visited
                else if ((isFeasible(vrp, a, city - 1, beginService, beginServiceDepot, indexSalesman)) && (vrp.getIdAvailableRequests().contains(city - 1)) && !(a.getVisited()[city - 1])) {
                    deliveryUrgency = reqList.get(city).getEndWindow() - (a.getBeginService()[currentCity] + reqList.get(currentCity).getServiceTime() + distance);
                    timeDifference = beginService - a.getBeginService()[currentCity] - reqList.get(currentCity).getServiceTime();
                    help1 = 1.0 / (weight1 * distance + weight2 * timeDifference + weight3 * deliveryUrgency);
                    help1 = Math.pow(help1, ants.getBeta());
                    help1 = help1 * Math.pow(ants.getPheromone()[currentCity][city], ants.getAlpha());
                    probPtr[indexSalesman][i] = help1;
                    tempCities[indexSalesman] = currentCity;
                    sumProb += probPtr[indexSalesman][i];
                }
            }
        }

        if (sumProb <= 0.0) {
            // All cities from the candidate  are tabu (are already visited)
            values = chooseBestNext(ants, a, vrp);
            return values;
        } else {
            // At least one neighbor is eligible, choose one according to the selection probabilities
            rnd = utilities.random01();
            rnd *= sumProb;
            i = 0;
            boolean done = false, forcedEnd = false;
            partialSum = 0;
            for (int indexSalesman = 0; indexSalesman < a.getUsedVehicles() && !done; indexSalesman++) {
                i = 0;
                partialSum += probPtr[indexSalesman][i];
                // This loop always stops because prob_ptr[nn_ants] == HUGE_VAL
                while (partialSum <= rnd) {
                    i++;
                    if (i < 0) {
                        partialSum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                        break;
                    }
                    if (i < probPtr[indexSalesman].length) {
                        partialSum += probPtr[indexSalesman][i];
                        salesman = indexSalesman;
                    } else if (i >= probPtr[indexSalesman].length) {
                        break;
                    }
                    // Add a big value to the partial_sum to be sure that the while loop ends
                    else if (indexSalesman == (a.getUsedVehicles() - 1) && i >= probPtr[indexSalesman].length && partialSum <= rnd) {
                        partialSum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                    }
                }
                if (partialSum > rnd) {
                    done = true;
                    if (!forcedEnd) {
                        salesman = indexSalesman;
                    } else { // Choose randomly a salesman to whom add the city
                        salesman = (int) (Math.random() * a.getUsedVehicles());
                    }
                }
            }
            // This may very rarely happen because of rounding if rnd is close to 1.
            if (i == vrp.getProblem().getNumNear()) {
                values = neighbourChooseBestNext(ants, a, vrp);
                return values;
            }
            currentCity = tempCities[salesman];
            int maxIndex = 0;
            double maxValue;
            if (i < 0) {
                maxValue = probPtr[salesman][0];
                for (j = 1; j < vrp.getProblem().getNumNear(); j++) {
                    if (probPtr[salesman][j] > maxValue) {
                        maxValue = probPtr[salesman][j];
                        maxIndex = j;
                    }
                }
                i = maxIndex;
            }
            help = vrp.getProblem().getNnList()[currentCity][i];
            distance = vrp.getProblem().getDistance()[currentCity][help];
            arrivalTime = a.getCurrentTime().get(salesman) + reqList.get(currentCity).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(help).getStartWindow());
            a.getTours().get(salesman).add(help - 1);
            a.getVisited()[help - 1] = true;
            a.setToVisit(a.getToVisit() - 1);
            a.getCurrentTime().set(salesman, beginService);
            a.getBeginService()[help] = beginService;
            double newQuantity = a.getCurrentQuantity().get(salesman) + reqList.get(help).getDemand();
            a.getCurrentQuantity().set(salesman, newQuantity);
            values[0] = help - 1;
            values[1] = salesman;
            return values;
        }
    }

    // Chooses for an ant as the next city the one with maximal value of heuristic information times pheromone
    private int[] neighbourChooseBestNext(Ants ants, Ant a, VRPTW vrp) {
        int i, currentCity, nextCity, helpCity, salesman = 0, startPos;
        double valueBest = -1; // Values in total matrix are always >= 0.0
        double help;
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDifference, deliveryUrgency, bestBeginService = 0;
        int[] values = new int[2];
        ArrayList<Request> reqList = vrp.getRequests();
        nextCity = vrp.getN();
        if (a.isAddedEmptyTour()) {
            startPos = a.getUsedVehicles() - 1;
        } else {
            startPos = 0;
        }
        for (int indexSalesman = startPos; indexSalesman < a.getUsedVehicles(); indexSalesman++) {
            int lastPos = a.getTours().get(indexSalesman).size() - 1;
            currentCity = a.getTours().get(indexSalesman).get(lastPos);
            currentCity++;
            for (i = 0; i < vrp.getProblem().getNumNear(); i++) {
                helpCity = vrp.getProblem().getNnList()[currentCity][i];
                if ((vrp.getIdAvailableRequests().contains(helpCity - 1)) && (!a.getVisited()[helpCity - 1])) {
                    distance = vrp.getProblem().getDistance()[currentCity][helpCity];
                    arrivalTime = a.getCurrentTime().get(indexSalesman) + reqList.get(currentCity).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(helpCity).getStartWindow());
                    distanceDepot = vrp.getProblem().getDistance()[helpCity][0];
                    arrivalTimeDepot = beginService + reqList.get(helpCity).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                    if (isFeasible(vrp, a, helpCity - 1, beginService, beginServiceDepot, indexSalesman)) {
                        deliveryUrgency = reqList.get(helpCity).getEndWindow() - (a.getBeginService()[currentCity] + reqList.get(currentCity).getServiceTime() + distance);
                        timeDifference = beginService - a.getBeginService()[currentCity] - reqList.get(currentCity).getServiceTime();
                        help = 1.0 / (weight1 * distance + weight2 * timeDifference + weight3 * deliveryUrgency);
                        help = Math.pow(help, ants.getBeta());
                        help = help * Math.pow(ants.getPheromone()[currentCity][helpCity], ants.getAlpha());
                        if (help > valueBest) {
                            valueBest = help;
                            nextCity = helpCity - 1;
                            salesman = indexSalesman;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
        }
        if (nextCity == vrp.getN()) {
            // All cities in nearest neighbor list were already visited
            values = chooseBestNext(ants, a, vrp);
            return values;
        } else {
            a.getTours().get(salesman).add(nextCity);
            a.getVisited()[nextCity] = true;
            a.setToVisit(a.getToVisit() - 1);
            a.getCurrentTime().set(salesman, bestBeginService);
            a.getBeginService()[nextCity + 1] = bestBeginService;
            double newQuantity = a.getCurrentQuantity().get(salesman) + reqList.get(nextCity + 1).getDemand();
            a.getCurrentQuantity().set(salesman, newQuantity);
            values[0] = nextCity;
            values[1] = salesman;
            return values;
        }
    }

    // Choose for an ant as the next city the one with maximal value of heuristic information times pheromone
    private int[] chooseBestNext(Ants ants, Ant a, VRPTW vrp) {
        int currentCity, nextCity, salesman = 0, indexTour, startIndex, startIndexTour = 0;
        double valueBest = -1.;  // Values in total matrix are always >= 0.0
        double help;
        int[] values = new int[2];
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDifference, deliveryUrgency, bestBeginService = 0;
        ArrayList<Request> reqList = vrp.getRequests();
        ArrayList<Integer> idKnownRequests = vrp.getIdAvailableRequests();
        ArrayList<Integer> lastCommittedIndexes;
        int pos;
        boolean appliedInsertion = false;
        nextCity = vrp.getN();
        if (a.isAddedEmptyTour()) {
            startIndex = a.getUsedVehicles() - 1;
        } else {
            startIndex = 0;
        }
        for (int indexSalesman = startIndex; indexSalesman < a.getUsedVehicles(); indexSalesman++) {
            int lastPos = a.getTours().get(indexSalesman).size() - 1;
            currentCity = a.getTours().get(indexSalesman).get(lastPos);
            currentCity++;
            for (int city : idKnownRequests) {
                if (!a.getVisited()[city]) {
                    distance = vrp.getProblem().getDistance()[currentCity][city + 1];
                    arrivalTime = a.getCurrentTime().get(indexSalesman) + reqList.get(currentCity).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    distanceDepot = vrp.getProblem().getDistance()[city + 1][0];
                    arrivalTimeDepot = beginService + reqList.get(city + 1).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                    if (isFeasible(vrp, a, city, beginService, beginServiceDepot, indexSalesman)) {
                        deliveryUrgency = reqList.get(city + 1).getEndWindow() - (a.getBeginService()[currentCity] + reqList.get(currentCity).getServiceTime() + distance);
                        timeDifference = beginService - a.getBeginService()[currentCity] - reqList.get(currentCity).getServiceTime();
                        help = 1.0 / (weight1 * distance + weight2 * timeDifference + weight3 * deliveryUrgency);
                        help = Math.pow(help, ants.getBeta());
                        help = help * Math.pow(ants.getPheromone()[currentCity][city + 1], ants.getAlpha());
                        if (help > valueBest) {
                            nextCity = city;
                            valueBest = help;
                            salesman = indexSalesman;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
        }
        /**
         * It means that not all the cities are covered and some customers has not yet been serviced
         * by using an insertion heuristic try to insert in the infeasible solution the customers not visited yet
         * when no more customer with feasible insertions can be found, start a new route/tour and add
         * one more vehicle for constructing a feasible solution
         */
        if (nextCity == vrp.getN()) {
            if ((a.getToVisit() > 0) && (a.getToVisit() <= 10)) {
                // Determine nodes that are not visited yet in the current ant's solution
                ArrayList<Integer> unroutedList = controller.unRoutedCustomers(a, vrp);
                if (appliedInsertion) {
                    startIndexTour = a.getUsedVehicles() - 1;
                } else {
                    startIndexTour = 0;
                }
                lastCommittedIndexes = new ArrayList<>();
                for (int index = 0; index < ants.getBestSoFarAnt().getUsedVehicles(); index++) {
                    pos = controller.getLastCommittedPos(index, ants);
                    lastCommittedIndexes.add(pos);
                }
                // Skip over committed (defined) nodes when performing insertion heuristic
                InsertionHeuristic.insertUnRoutedCustomers(a, vrp, unroutedList, startIndexTour, lastCommittedIndexes);
            }
            // If no more un-routed customers can be feasible inserted in the solution and there are still
            // remaining un-routed customers, add a new tour
            if (a.getToVisit() > 0) {
                a.setUsedVehicles(a.getUsedVehicles() + 1);
                indexTour = a.getUsedVehicles() - 1;
                a.getTours().add(indexTour, new ArrayList<>());
                a.getTours().get(indexTour).add(-1);
                a.getTourLengths().add(indexTour, 0.0);
                a.getCurrentQuantity().add(indexTour, 0.0);
                a.getCurrentTime().add(indexTour, 0.0);
                a.setAddedEmptyTour(true);
                values[0] = -1;
                values[1] = indexTour;
            }
        } else {
            a.getTours().get(salesman).add(nextCity);
            a.getVisited()[nextCity] = true;
            a.setToVisit(a.getToVisit() - 1);
            a.getCurrentTime().set(salesman, bestBeginService);
            a.getBeginService()[nextCity + 1] = bestBeginService;
            double newQuantity = a.getCurrentQuantity().get(salesman) + reqList.get(nextCity + 1).getDemand();
            a.getCurrentQuantity().set(salesman, newQuantity);
            values[0] = nextCity;
            values[1] = salesman;
        }
        return values;
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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
