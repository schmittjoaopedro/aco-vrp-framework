package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

/* contains the main entry point for the implementation of ACO for solving the VRPTW problem on instances
 * belonging to Solomon benchmark
 */
public class VRPTW_ACS implements Runnable {

    private LoggerOutput loggerOutput;

    private InOut inOut;

    private Controller controller;

    private Ants ants;

    private Timer timer;

    private boolean isRunning = true;

    private int counter = 0;

    /**
     * indicates whether local search is used in the ACO algorithm
     **/
    public boolean ls_flag = true;

    //indicates whether the thread was restarted from outside
    private boolean threadRestarted;

    private VRPTW vrptw;

    public VRPTW_ACS(boolean threadStopped, VRPTW vrptw, Controller controller, Ants ants, InOut inOut, Timer timer, LoggerOutput loggerOutput) {
        this.threadRestarted = threadStopped;
        this.vrptw = vrptw;
        this.controller = controller;
        this.ants = ants;
        this.inOut = inOut;
        this.timer = timer;
        this.loggerOutput = loggerOutput;
    }

    public boolean isFeasible(VRPTW vrp, Ant a, int city, double beginService, double beginServiceDepot, int indexSalesman) {
        boolean ok = false;
        double currentQuantity;
        ArrayList<Request> reqList = vrp.getRequests();
        //check upper bound of the time window of the next customer to be visited &&
        //current capacity of the car && vehicle arrival time at the depot (the maximum total route time -> upper bound of the time window of the depot)
        currentQuantity = a.currentQuantity.get(indexSalesman) + reqList.get(city + 1).getDemand();
        if (beginService <= reqList.get(city + 1).getEndWindow() && currentQuantity <= vrp.getCapacity()
                && beginServiceDepot <= reqList.get(0).getEndWindow()) {
            ok = true;
        }
        return ok;
    }

    //check if there is still an ant with left cities to visit
    public boolean isDone() {
        for (int k = 0; k < ants.n_ants; k++) {
            if (ants.ants[k].toVisit > 0) {
                return false;
            }
        }
        return true;
    }

    //add to the ant's solution the committed nodes from each tour of the best so far solution
    public void addCommitedNodes(Ant a, VRPTW vrptw) {
        int index, city, current_city;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = vrptw.getRequests();
        int startIndex = 1, pos;
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int indexTour = 0; indexTour < ants.best_so_far_ant.usedVehicles; indexTour++) {
            pos = controller.getLastCommitedPos(indexTour);
            lastCommitedIndexes.add(pos);
        }
        for (int i = 0; i < lastCommitedIndexes.size(); i++) {
            //we have at least one committed node in the i-th tour (i.e. tour with index i)
            index = lastCommitedIndexes.get(i);
            if (index > 0) {
                //if the number of vehicles (tours) from the ant solution is less than the index of the
                //tour from the best so far solution, add new (empty) tours in the ant's solution
                if (a.usedVehicles < (i + 1)) {
                    a.usedVehicles = i + 1;
                    for (int l = startIndex; l < a.usedVehicles; l++) {
                        a.tours.add(l, new ArrayList<Integer>());
                        a.tours.get(l).add(-1);
                        a.tour_lengths.add(l, 0.0);
                        a.currentQuantity.add(l, 0.0);
                        a.currentTime.add(l, 0.0);
                    }
                    startIndex = i + 1;
                }
                int lastPos = a.tours.get(i).size() - 1;
                current_city = a.tours.get(i).get(lastPos);
                current_city++;
                //add in the ant's i-th tour all the committed nodes from the i-th tour of the best so far solution
                for (int j = 1; j <= index; j++) {
                    city = ants.best_so_far_ant.tours.get(i).get(j);
                    distance = vrptw.instance.distance[current_city][city + 1];
                    arrivalTime = a.currentTime.get(i) + reqList.get(current_city).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    if (beginService > reqList.get(city + 1).getEndWindow()) {
                        loggerOutput.log("Method addCommitedNodes: solution infeasible..");
                    }
                    //add committed node to the ant's tour
                    a.tours.get(i).add(j, city);
                    a.visited[city] = true;
                    a.toVisit--;
                    a.currentTime.set(i, beginService);
                    a.beginService[city + 1] = beginService;
                    a.currentQuantity.set(i, a.currentQuantity.get(i) + reqList.get(city + 1).getDemand());
                    current_city = city + 1;
                }
            }
        }
    }

    //check if there are any committed tours (i.e. tours that contain at least one committed node that
    //should be included in the ant's solution)
    public boolean checkCommitedTours() {
        int lastPos;
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        for (int index : lastCommitedIndexes) {
            if (index > 0) {
                return true;
            }
        }
        return false;
    }

    //manage the solution construction phase (construct a set of complete and closed tours, one
    //for each vehicle)
    public void construct_solutions(VRPTW vrptw) {
        int k; /* counter variable */
        int step; /* counter of the number of construction steps */
        int values[];
        /* Mark all cities as unvisited */
        for (k = 0; k < ants.n_ants; k++) {
            ants.ant_empty_memory(ants.ants[k], vrptw);
        }
        /* Place the ants on same initial city, which is the depot city */
        for (k = 0; k < ants.n_ants; k++) {
            for (int i = 0; i < ants.ants[k].usedVehicles; i++) {
                //place each ant on the depot city, which is the start city of each tour
                // -1 is a special marker for the deport city, so that it's not be confused with the rest of the cities
                // all the rest of the cities are represented by integer values > 0
                ants.ants[k].tours.get(i).add(-1);
            }
        }
        //initialize ant solution with the committed nodes (if any) from each tour of the best so far solution
        if (checkCommitedTours()) {
            for (k = 0; k < ants.n_ants; k++) {
                addCommitedNodes(ants.ants[k], vrptw);
            }
        }
        while (!isDone()) {
            for (k = 0; k < ants.n_ants; k++) {
                if (ants.ants[k].toVisit > 0) {
                    //choose for each ant in a probabilistic way by some type of roullette wheel selection
                    //which salesman to consider next, that will visit a city
                    values = ants.neighbour_choose_and_move_to_next(ants.ants[k], vrptw, this);
                    if (values[0] != -1 && ants.acs_flag) {
                        ants.local_acs_pheromone_update(ants.ants[k], values[1]);
                    }

                }
            }
        }
        double longestTourLength;
        int idLongestTour = 0;
        for (k = 0; k < ants.n_ants; k++) {
            inOut.noSolutions++;
            longestTourLength = Double.MIN_VALUE;
            for (int i = 0; i < ants.ants[k].usedVehicles; i++) {
                step = ants.ants[k].tours.get(i).size();
                ants.ants[k].tours.get(i).add(step, -1);
                ants.ants[k].tour_lengths.set(i, vrptw.compute_tour_length_(ants.ants[k].tours.get(i)));
                ants.ants[k].total_tour_length += ants.ants[k].tour_lengths.get(i);
                if (longestTourLength < ants.ants[k].tour_lengths.get(i)) {
                    longestTourLength = ants.ants[k].tour_lengths.get(i);
                    idLongestTour = i;
                }
                if (ants.acs_flag)
                    ants.local_acs_pheromone_update(ants.ants[k], i);
            }
            ants.ants[k].longest_tour_length = longestTourLength;
            ants.ants[k].indexLongestTour = idLongestTour;
            ants.ants[k].costObjectives[0] = ants.ants[k].total_tour_length;
            ants.ants[k].costObjectives[1] = ants.computeToursAmplitude(ants.ants[k]);
        }
        inOut.n_tours += (ants.n_ants * ants.ants[0].usedVehicles); //each ant constructs a complete and closed tour
    }

    //initialize variables appropriately when starting a trial
    public void init_try(VRPTW vrptw) {
        timer.start_timers();
        inOut.time_used = timer.elapsed_time();
        inOut.time_passed = inOut.time_used;
        int noAvailableNodes;
        /* Initialize variables concerning statistics etc. */
        inOut.n_tours = 1;
        inOut.iteration = 1;
        ants.best_so_far_ant.total_tour_length = Double.MAX_VALUE;
        ants.restart_best_ant.total_tour_length = Double.MAX_VALUE;
        inOut.found_best = 0;
        inOut.lambda = 0.05;
        /*
         * Initialize the Pheromone trails, only if ACS is used, Ants.pheromones
         * have to be initialized differently
         */
        if (!(ants.acs_flag)) {
            ants.trail_0 = 1. / ((ants.rho) * nn_tour(vrptw));
            /*
             * in the original papers on Ant System it is not exactly defined what the
             * initial value of the Ants.pheromones is. Here we set it to some
             * small constant, analogously as done in MAX-MIN Ant System.
             */
            ants.init_pheromone_trails(vrptw, ants.trail_0);
        }
        if (ants.acs_flag) {
            noAvailableNodes = vrptw.getIdAvailableRequests().size();
            //if no cities are available except the depot, set the pheromone levels to a static value
            if (noAvailableNodes == 0) {
                ants.trail_0 = 1.0;
            } else {
                ants.trail_0 = 1. / ((double) (noAvailableNodes + 1) * (double) nn_tour(vrptw));
            }
            ants.init_pheromone_trails(vrptw, ants.trail_0);
        }
    }

    //skip committed (defined) nodes when applying local search operators
    public Ant local_search(Ant a, VRPTW instance) {
        //apply relocate multiple route local search operator
        a = relocateMultipleRoute(a, instance);
        //apply exchange multiple route local search operator
        a = exchangeMultipleRoute(a, instance);
        return a;
    }

    public boolean checkFeasibleTourRelocationMultiple(Ant a, VRPTW vrptw, int indexTourSource, int indexTourDestination, int i, int j) {
        int city, previousCity, prevCity, nextCity, currentCity;
        double currentQuantity, arrivalTime, currentTime = 0.0, beginService, distance;
        ArrayList<Request> reqList = vrptw.getRequests();
        city = a.tours.get(indexTourSource).get(i);
        currentQuantity = a.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
        if (currentQuantity > vrptw.getCapacity()) {
            return false;
        }
        //check time window constraints in source tour
        for (int pos = i + 1; pos < a.tours.get(indexTourSource).size(); pos++) {
            if (pos == (i + 1)) {
                prevCity = a.tours.get(indexTourSource).get(pos - 2);
                currentCity = a.tours.get(indexTourSource).get(pos);
                currentTime = a.beginService[prevCity + 1];
            } else {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(pos);
            }
            distance = vrptw.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        previousCity = a.tours.get(indexTourDestination).get(j - 1);
        nextCity = a.tours.get(indexTourDestination).get(j);
        arrivalTime = a.beginService[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + vrptw.instance.distance[previousCity + 1][city + 1];
        beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
        if (beginService > reqList.get(city + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;
        arrivalTime = currentTime + reqList.get(city + 1).getServiceTime() + vrptw.instance.distance[city + 1][nextCity + 1];
        beginService = Math.max(arrivalTime, reqList.get(nextCity + 1).getStartWindow());
        if (beginService > reqList.get(nextCity + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;
        //check time window constraints in destination tour
        for (int pos = j + 1; pos < a.tours.get(indexTourDestination).size(); pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            distance = vrptw.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        return true;
    }

    public void updateBeginServiceRelocationMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        ArrayList<Request> reqList = vrp.getRequests();
        //update of begin service times for the source tour
        for (int pos = i; pos < a.tours.get(indexTourSource).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourSource).get(pos - 1);
            currentCity = a.tours.get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Relocation Multiple indexTourSource: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourSource, beginService);
        //update of begin service times for the destination tour
        for (int pos = j; pos < a.tours.get(indexTourDestination).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Relocation Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourDestination, beginService);
    }

    //skip committed (defined) nodes
    //relocateMultipleRoute is performed only once and the best improvement (which minimizes most the
    //total traveled distance) is applied
    public Ant relocateMultipleRoute(Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        int lastPos;
        Ant bestImprovedAnt = new Ant();
        bestImprovedAnt.tours = new ArrayList();
        bestImprovedAnt.tour_lengths = new ArrayList<>();
        bestImprovedAnt.beginService = new double[vrptw.n + 1];
        bestImprovedAnt.currentTime = new ArrayList<>();
        bestImprovedAnt.currentQuantity = new ArrayList<>();
        bestImprovedAnt.usedVehicles = 1;
        for (int j = 0; j < bestImprovedAnt.usedVehicles; j++) {
            bestImprovedAnt.tours.add(j, new ArrayList<>());
            bestImprovedAnt.tour_lengths.add(j, 0.0);
        }
        bestImprovedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        bestImprovedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        bestImprovedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            bestImprovedAnt.costObjectives[indexObj] = 0;
        }
        bestImprovedAnt.earliestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        bestImprovedAnt.latestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tour_lengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tour_lengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);

        ants.copy_from_to(a, bestImprovedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);

        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        for (int indexTourSource = 0; indexTourSource < a.usedVehicles; indexTourSource++) {
            for (int indexTourDestination = 0; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved/relocated
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the relocation's destination
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size(); j++) {
                            //check if results a feasible solution (i.e. no time window constraint is violated)
                            feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                            if (feasible) {
                                //obtain the neighbour solution corresponding to the relocation operator
                                city = temp.tours.get(indexTourSource).get(i);
                                temp.tours.get(indexTourSource).remove(i);
                                temp.tours.get(indexTourDestination).add(j, city);
                                newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourSource, newQuantity1);
                                newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                //also update the current time of the source and destination tours
                                updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                //update total traveled distance and lengths of source and destination tours
                                sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                newDistance1 = temp.tour_lengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                // new distance 2
                                destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                newDistance2 = temp.tour_lengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                // total distance
                                newTotalDistance = temp.total_tour_length - temp.tour_lengths.get(indexTourSource) - temp.tour_lengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                temp.total_tour_length = newTotalDistance;
                                temp.tour_lengths.set(indexTourSource, newDistance1);
                                temp.tour_lengths.set(indexTourDestination, newDistance2);
                                //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                    temp.tours.remove(indexTourSource);
                                    temp.usedVehicles--;
                                }
                                //if some improvement is obtained in the total traveled distance
                                if ((temp.total_tour_length < bestImprovedAnt.total_tour_length)
                                        || (temp.usedVehicles < bestImprovedAnt.usedVehicles)) {
                                    ants.copy_from_to(temp, bestImprovedAnt, vrptw);
                                }
                                //restore previous solution constructed by ant
                                ants.copy_from_to(a, temp, vrptw);
                            }
                        }
                    }
                }
            }
        }
        return bestImprovedAnt;
    }

    //find the index of the tour having the smallest number of visited nodes (cities/customers)
    public int findShortestTour(Ant a) {
        int indexTour = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.usedVehicles; i++) {
            if (min > a.tours.get(i).size()) {
                min = a.tours.get(i).size();
                indexTour = i;
            }
        }
        return indexTour;
    }

    //skip committed (defined) nodes
    //relocateMultipleRouteIterated is performed multiple times until no further improvement (which minimizes most the
    //total traveled distance or reduces the number of used vehicles) is possible
    public Ant relocateMultipleRouteIterated(Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tour_lengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tour_lengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tour_lengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tour_lengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                loggerOutput.log("Inside relocateMultipleRouteIterated; count=" + count);
            }
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(improvedAnt, temp, vrptw);
            for (int indexTourSource = 0; indexTourSource < temp.usedVehicles; indexTourSource++) {
                for (int indexTourDestination = 0; indexTourDestination < temp.usedVehicles; indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        //index of the element to be moved/relocated
                        if (indexTourSource > lastCommitedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                        }
                        //index of the relocation's destination
                        if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.tours.get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.tours.get(indexTourDestination).size(); j++) {
                                //check if results a feasible solution (i.e. no time window constraint is violated)
                                feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                if (feasible) {
                                    //obtain the neighbour solution corresponding to the relocation operator
                                    city = temp.tours.get(indexTourSource).get(i);
                                    temp.tours.get(indexTourSource).remove(i);
                                    temp.tours.get(indexTourDestination).add(j, city);
                                    newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                    temp.currentQuantity.set(indexTourSource, newQuantity1);
                                    newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                    temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                    //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                    //also update the current time of the source and destination tours
                                    updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    //update total traveled distance and lengths of source and destination tours
                                    sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                    sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                    newDistance1 = temp.tour_lengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                    // new distance 2
                                    destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                    destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                    newDistance2 = temp.tour_lengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                    // new total distance
                                    newTotalDistance = temp.total_tour_length - temp.tour_lengths.get(indexTourSource) - temp.tour_lengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                    temp.total_tour_length = newTotalDistance;
                                    temp.tour_lengths.set(indexTourSource, newDistance1);
                                    temp.tour_lengths.set(indexTourDestination, newDistance2);
                                    //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                    if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                        temp.tours.remove(indexTourSource);
                                        temp.tour_lengths.remove(indexTourSource);
                                        temp.currentQuantity.remove(indexTourSource);
                                        temp.currentTime.remove(indexTourSource);
                                        temp.usedVehicles--;
                                    }
                                    //performing the rounding of the two numbers up to 10 decimals so that in the
                                    //comparison of the 2 double values to consider only the first 10 most significant decimals
                                    round1 = Math.round(temp.total_tour_length * tempNo) / tempNo;
                                    round2 = Math.round(improvedAnt.total_tour_length * tempNo) / tempNo;
                                    //if some improvement is obtained in the total traveled distance
                                    if (((round1 < round2) && (temp.usedVehicles == improvedAnt.usedVehicles))
                                            || (temp.usedVehicles < improvedAnt.usedVehicles)) {
                                        ants.copy_from_to(temp, improvedAnt, vrptw);
                                        foundImprovement = true;
                                    }
                                    //restore previous solution constructed by ant
                                    ants.copy_from_to(a, temp, vrptw);
                                }
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }

    //skip committed (defined) nodes
    //relocateMultipleRouteIterated is performed multiple times until no further improvement (which minimizes most the
    //total traveled distance or reduces the number of used vehicles) is possible
    //try to relocate nodes from the tour having the minimum number of visited nodes, in an attempt
    //to reduce (minimize) the number of tours (i.e. used vehicles)
    public Ant relocateMultipleRouteIteratedShortest(Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int indexTourSource, lastPos;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tour_lengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tour_lengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tour_lengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<Integer>());
            temp.tour_lengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<Integer>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        while (foundImprovement) {
            foundImprovement = false;
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(a, temp, vrptw);
            indexTourSource = findShortestTour(a);
            for (int indexTourDestination = 0; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved/relocated
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the relocation's destination
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size(); j++) {
                            //check if results a feasible solution (i.e. no time window constraint is violated)
                            feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                            if (feasible) {
                                //obtain the neighbour solution corresponding to the relocation operator
                                city = temp.tours.get(indexTourSource).get(i);
                                temp.tours.get(indexTourSource).remove(i);
                                temp.tours.get(indexTourDestination).add(j, city);
                                newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourSource, newQuantity1);
                                newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                //also update the current time of the source and destination tours
                                updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                //update total traveled distance and lengths of source and destination tours
                                sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                newDistance1 = temp.tour_lengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                // new distance 2
                                destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                newDistance2 = temp.tour_lengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                // new total distance
                                newTotalDistance = temp.total_tour_length - temp.tour_lengths.get(indexTourSource) - temp.tour_lengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                temp.total_tour_length = newTotalDistance;
                                temp.tour_lengths.set(indexTourSource, newDistance1);
                                temp.tour_lengths.set(indexTourDestination, newDistance2);
                                //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                    temp.tours.remove(indexTourSource);
                                    temp.usedVehicles--;
                                }
                                //if some improvement is obtained in the total traveled distance
                                if (((temp.total_tour_length < improvedAnt.total_tour_length) && (temp.usedVehicles == improvedAnt.usedVehicles))
                                        || (temp.usedVehicles < improvedAnt.usedVehicles)) {
                                    ants.copy_from_to(temp, improvedAnt, vrptw);
                                    foundImprovement = true;
                                }
                                //restore previous solution constructed by ant
                                ants.copy_from_to(a, temp, vrptw);
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }

    public boolean checkFeasibleTourExchangeMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity, city1, city2;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService, currentQuantity;
        ArrayList<Request> reqList = vrp.getRequests();
        //check vehicle capacity tour constraints for source and destination tours
        city1 = a.tours.get(indexTourSource).get(i);
        city2 = a.tours.get(indexTourDestination).get(j);
        currentQuantity = a.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
        if (currentQuantity > vrp.getCapacity()) {
            return false;
        }
        currentQuantity = a.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
        if (currentQuantity > vrp.getCapacity()) {
            return false;
        }
        //check feasibility for source tour regarding time windows constraints
        for (int pos = i; pos < a.tours.get(indexTourSource).size(); pos++) {
            if (pos == i) {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourDestination).get(j);
                currentTime = a.beginService[prevCity + 1];
            } else if (pos == (i + 1)) {
                prevCity = a.tours.get(indexTourDestination).get(j);
                currentCity = a.tours.get(indexTourSource).get(pos);
            } else {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(pos);
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        //check feasibility for destination tour regarding time windows constraints
        for (int pos = j; pos < a.tours.get(indexTourDestination).size(); pos++) {
            if (pos == j) {
                prevCity = a.tours.get(indexTourDestination).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(i);
                currentTime = a.beginService[prevCity + 1];
            } else if (pos == (j + 1)) {
                prevCity = a.tours.get(indexTourSource).get(i);
                currentCity = a.tours.get(indexTourDestination).get(pos);
            } else {
                prevCity = a.tours.get(indexTourDestination).get(pos - 1);
                currentCity = a.tours.get(indexTourDestination).get(pos);
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        return true;
    }

    public void updateBeginServiceExchangeMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        ArrayList<Request> reqList = vrp.getRequests();
        for (int pos = i; pos < a.tours.get(indexTourSource).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourSource).get(pos - 1);
            currentCity = a.tours.get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Exchange Multiple indexTourSource: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourSource, beginService);
        for (int pos = j; pos < a.tours.get(indexTourDestination).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Exchange Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourDestination, beginService);
    }

    public Ant exchangeMultipleRoute(Ant a, VRPTW vrptw) {
        boolean feasible;
        int city1, city2, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        int lastPos;
        Ant bestImprovedAnt = new Ant();
        bestImprovedAnt.tours = new ArrayList();
        bestImprovedAnt.tour_lengths = new ArrayList<>();
        bestImprovedAnt.beginService = new double[vrptw.n + 1];
        bestImprovedAnt.currentTime = new ArrayList<>();
        bestImprovedAnt.currentQuantity = new ArrayList<>();
        bestImprovedAnt.usedVehicles = 1;
        for (int j = 0; j < bestImprovedAnt.usedVehicles; j++) {
            bestImprovedAnt.tours.add(j, new ArrayList<>());
            bestImprovedAnt.tour_lengths.add(j, 0.0);
        }
        bestImprovedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        bestImprovedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        bestImprovedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            bestImprovedAnt.costObjectives[indexObj] = 0;
        }
        bestImprovedAnt.earliestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        bestImprovedAnt.latestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tour_lengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tour_lengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, bestImprovedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        for (int indexTourSource = 0; indexTourSource < (a.usedVehicles - 1); indexTourSource++) {
            for (int indexTourDestination = indexTourSource + 1; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved from the source tour
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the element to be moved from the destination tour
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size() - 1; j++) {
                            if (indexTourSource <= indexTourDestination) {
                                //check if results a feasible solution (i.e. no time window constraint is violated)
                                feasible = checkFeasibleTourExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                if (feasible) {
                                    //obtain the neighbour solution corresponding to the relocation operator
                                    city1 = temp.tours.get(indexTourSource).get(i);
                                    city2 = temp.tours.get(indexTourDestination).get(j);
                                    temp.tours.get(indexTourSource).set(i, city2);
                                    temp.tours.get(indexTourDestination).set(j, city1);
                                    newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
                                    temp.currentQuantity.set(indexTourSource, newQuantity1);
                                    newQuantity2 = temp.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
                                    temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                    //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                    //also update the current time of the source and destination tours
                                    updateBeginServiceExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    //update total traveled distance and lengths of source and destination tours
                                    sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                    sourceNextCity = temp.tours.get(indexTourSource).get(i + 1);
                                    newDistance1 = temp.tour_lengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city1 + 1] - vrptw.instance.distance[city1 + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][city2 + 1] + vrptw.instance.distance[city2 + 1][sourceNextCity + 1];
                                    // new distance 2
                                    destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                    destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                    newDistance2 = temp.tour_lengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][city2 + 1] - vrptw.instance.distance[city2 + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city1 + 1] + vrptw.instance.distance[city1 + 1][destinationNextCity + 1];
                                    // new total distance
                                    newTotalDistance = temp.total_tour_length - temp.tour_lengths.get(indexTourSource) - temp.tour_lengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                    temp.total_tour_length = newTotalDistance;
                                    temp.tour_lengths.set(indexTourSource, newDistance1);
                                    temp.tour_lengths.set(indexTourDestination, newDistance2);
                                    //if some improvement is obtained in the total traveled distance
                                    if (temp.total_tour_length < bestImprovedAnt.total_tour_length) {
                                        ants.copy_from_to(temp, bestImprovedAnt, vrptw);
                                    }
                                    //restore previous solution constructed by ant
                                    ants.copy_from_to(a, temp, vrptw);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestImprovedAnt;
    }

    public Ant exchangeMultipleRouteIterated(Ant a, VRPTW vrptw) {
        boolean feasible;
        int city1, city2, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tour_lengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tour_lengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tour_lengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tour_lengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<Integer>();
        for (int index = 0; index < ants.best_so_far_ant.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                loggerOutput.log("Inside exchangeMultipleRouteIterated; count=" + count);
            }
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(improvedAnt, temp, vrptw);
            for (int indexTourSource = 0; indexTourSource < (temp.usedVehicles - 1); indexTourSource++) {
                for (int indexTourDestination = indexTourSource + 1; indexTourDestination < temp.usedVehicles; indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        //index of the element to be moved from the source tour
                        if (indexTourSource > lastCommitedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                        }
                        //index of the element to be moved from the destination tour
                        if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.tours.get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.tours.get(indexTourDestination).size() - 1; j++) {
                                if (indexTourSource <= indexTourDestination) {
                                    //check if results a feasible solution (i.e. no time window constraint is violated)
                                    feasible = checkFeasibleTourExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    if (feasible) {
                                        //obtain the neighbour solution corresponding to the relocation operator
                                        city1 = temp.tours.get(indexTourSource).get(i);
                                        city2 = temp.tours.get(indexTourDestination).get(j);
                                        temp.tours.get(indexTourSource).set(i, city2);
                                        temp.tours.get(indexTourDestination).set(j, city1);
                                        newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
                                        temp.currentQuantity.set(indexTourSource, newQuantity1);
                                        newQuantity2 = temp.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
                                        temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                        //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                        //also update the current time of the source and destination tours
                                        updateBeginServiceExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                        //update total traveled distance and lengths of source and destination tours
                                        sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                        sourceNextCity = temp.tours.get(indexTourSource).get(i + 1);
                                        newDistance1 = temp.tour_lengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city1 + 1] - vrptw.instance.distance[city1 + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][city2 + 1] + vrptw.instance.distance[city2 + 1][sourceNextCity + 1];
                                        // new distance 2
                                        destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                        destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                        newDistance2 = temp.tour_lengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][city2 + 1] - vrptw.instance.distance[city2 + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city1 + 1] + vrptw.instance.distance[city1 + 1][destinationNextCity + 1];
                                        // new total distance
                                        newTotalDistance = temp.total_tour_length - temp.tour_lengths.get(indexTourSource) - temp.tour_lengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                        temp.total_tour_length = newTotalDistance;
                                        temp.tour_lengths.set(indexTourSource, newDistance1);
                                        temp.tour_lengths.set(indexTourDestination, newDistance2);
                                        //performing the rounding of the two numbers up to 10 decimals so that in the
                                        //comparison of the 2 double values to consider only the first 10 most significant decimals
                                        round1 = Math.round(temp.total_tour_length * tempNo) / tempNo;
                                        round2 = Math.round(improvedAnt.total_tour_length * tempNo) / tempNo;
                                        //if some improvement is obtained in the total traveled distance
                                        if (round1 < round2) {
                                            ants.copy_from_to(temp, improvedAnt, vrptw);
                                            foundImprovement = true;
                                        }
                                        //restore previous solution constructed by ant
                                        ants.copy_from_to(a, temp, vrptw);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }

    //manage some statistical information about the trial, especially if a new best solution
    //(best-so-far) is found and adjust some parameters if a new best solution is found
    public void update_statistics(VRPTW vrptw) {
        int iteration_best_ant;
        double scalingValue, scalledValue = 0.0;
        Object obj = new Object();
        double round1, round2;
        double tempNo = Math.pow(10, 10);
        iteration_best_ant = ants.find_best(); /* iteration_best_ant is a global variable */
        Ant a = ants.ants[iteration_best_ant];
        if (ls_flag) {
            //apply multiple route relocation and exchange iterated local search operators for the iteration best ant
            a = relocateMultipleRouteIterated(a, vrptw);
            a = exchangeMultipleRouteIterated(a, vrptw);
        }
        synchronized (obj) {
            round1 = Math.round(a.total_tour_length * tempNo) / tempNo;
            round2 = Math.round(ants.best_so_far_ant.total_tour_length * tempNo) / tempNo;
            if ((a.usedVehicles < ants.best_so_far_ant.usedVehicles) || ((a.usedVehicles == ants.best_so_far_ant.usedVehicles) && (round1 < round2))
                    || ((round1 < round2) && (ants.best_so_far_ant.total_tour_length == Double.MAX_VALUE))) {
                inOut.time_used = timer.elapsed_time();  //best solution found after time_used
                ants.copy_from_to(a, ants.best_so_far_ant, vrptw);
                scalingValue = controller.getScalingValue();
                if (scalingValue != 0) {
                    scalledValue = ants.best_so_far_ant.total_tour_length / scalingValue;
                }
                loggerOutput.log("Updated Best so far ant >> No. of used vehicles=" + ants.best_so_far_ant.usedVehicles + " total tours length=" + ants.best_so_far_ant.total_tour_length + " (scalled value = " + scalledValue + ")");
            }
        }

    }

    //manage global Ants.pheromone deposit for Ant System
    public void as_update() {
        for (int k = 0; k < ants.n_ants; k++)
            ants.global_update_pheromone(ants.ants[k]);
    }

    //manage global Ants.pheromone deposit for Ant Colony System
    public void acs_global_update() {
        ants.global_acs_pheromone_update(ants.best_so_far_ant);
    }

    //manage global Ants.pheromone trail update for the ACO algorithms
    public void pheromone_trail_update(VRPTW vrptw) {
        //Simulate the Ants.pheromone evaporation of all Ants.pheromones; this is not necessary for ACS
        if (ants.as_flag) {
            /* evaporate all Ants.pheromone trails */
            ants.evaporation(vrptw);
        }
        /* Next, apply the Ants.pheromone deposit for the various ACO algorithms */
        if (ants.as_flag)
            as_update();
        else if (ants.acs_flag)
            acs_global_update();
    }

    //do the work of running the ant colony which tries to solve the DVRPTW instance using the available
    //(known so far) customer requests
    public void run() {
        while (isRunning) {
            //the thread was restarted (i.e. it was previously stopped and started again)
            if (threadRestarted) {
                //compute the new value for the initial pheromone trail based on the current best solution so far
                int noAvailableNodes = vrptw.getIdAvailableRequests().size();
                ants.trail_0 = 1. / ((double) (noAvailableNodes + 1) * (double) ants.best_so_far_ant.total_tour_length);
                //preserve a certain amount of the pheromones from the previous run of the ant colony
                ants.preservePheromones(vrptw);
            }
            //do the optimization task (work)
            construct_solutions(vrptw);
            //increase evaluations counter
            inOut.noEvaluations++;
            counter++;
            update_statistics(vrptw);
            pheromone_trail_update(vrptw);
            //force the ant colony thread to stop its execution
            if (counter > 300) {
                isRunning = false;
            }
            if (inOut.isDiscreteTime) {
                counter = 0;
                break;
            }
        }
    }

    //generate a nearest neighbor tour and compute tour length using only the available nodes (nodes known so far)
    public double nn_tour(VRPTW vrptw) {
        int step, salesman;
        double sum = 0, sum1 = 0, scalledValue = 0;
        ants.ant_empty_memory(ants.ants[0], vrptw);
        for (int i = 0; i < ants.ants[0].usedVehicles; i++) {
            //place the ant on the depot city, which is the start city of each tour
            // -1 is a special marker for the deport city, so that it's not be confused with the rest of the cities
            // all the rest of the cities are represented by integer values > 0
            ants.ants[0].tours.get(i).add(-1);
        }
        //there are still left available (known) cities to be visited
        while (ants.ants[0].toVisit > 0) {
            salesman = ants.ants[0].usedVehicles - 1;
            ants.choose_closest_next(ants.ants[0], salesman, vrptw, this);
        }
        int nrTours = ants.ants[0].usedVehicles;
        for (int i = 0; i < nrTours; i++) {
            step = ants.ants[0].tours.get(i).size();
            ants.ants[0].tours.get(i).add(step, -1);
            ants.ants[0].tour_lengths.set(i, vrptw.compute_tour_length(ants.ants[0].tours.get(i)));
            sum1 += ants.ants[0].tour_lengths.get(i);
        }
        ants.ants[0].total_tour_length = sum1;
        if (ls_flag) {
            ants.ants[0] = relocateMultipleRouteIterated(ants.ants[0], vrptw);
            ants.ants[0] = exchangeMultipleRouteIterated(ants.ants[0], vrptw);
            //compute new distances and update longest tour
            for (int l = 0; l < ants.ants[0].usedVehicles; l++) {
                ants.ants[0].tour_lengths.set(l, vrptw.compute_tour_length(ants.ants[0].tours.get(l)));
                sum += ants.ants[0].tour_lengths.get(l);
            }
            ants.ants[0].total_tour_length = sum;
        }
        double scalingValue = controller.getScalingValue();
        if (scalingValue != 0) {
            scalledValue = ants.ants[0].total_tour_length / scalingValue;
        }
        loggerOutput.log("\nInitial (nearest neighbour tour) total tour length: " + ants.ants[0].total_tour_length + " (scalled value = " + scalledValue + "); Number of vehicles used: " + ants.ants[0].usedVehicles);
        sum1 = ants.ants[0].total_tour_length;
        //initialize best solution so far with this solution constructed by the nearest neighbour heuristic
        ants.copy_from_to(ants.ants[0], ants.best_so_far_ant, vrptw);
        ants.ant_empty_memory(ants.ants[0], vrptw);
        return sum1;
    }

    public void terminate() {
        isRunning = false;
    }

}
