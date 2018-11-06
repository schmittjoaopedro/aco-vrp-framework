package com.github.schmittjoaopedro.vrp.dvrptwacs;


import java.util.ArrayList;

public class Ants {

    private LoggerOutput loggerOutput;

    private InsertionHeuristic insertionHeuristic;

    private Utilities utilities;

    private Controller controller;

    private InOut inOut;

    public final double weight1 = 0.4;
    public final double weight2 = 0.4;
    public final double weight3 = 0.2;

    //weight used in the nearest neighbour heuristic, when computing an initial solution to calculate the
    //value of the initial pheromone trail
    public double initialWeight1;
    public double initialWeight2;
    public double initialWeight3;

    //it indicates that node at position/index i is committed if the value at position i is true; the
    //depot node is considered to be committed by default and it's not included in this array
    public boolean[] committedNodes;

    public Ant ants[];
    public Ant best_so_far_ant;
    public Ant restart_best_ant;

    public double pheromone[][];

    public int n_ants; /* number of ants */

    public int nn_ants; /* length of nearest neighbor lists for the ants' solution construction */

    public double rho; /* parameter for evaporation used in global pheromne update*/
    public double local_rho;  /* parameter for evaporation used in local pheromone update*/
    public double alpha; /* importance of trail */
    public double beta; /* importance of heuristic evaluate */
    public double q_0; /* probability of best choice in tour construction */

    public boolean as_flag; /* ant system */
    public boolean acs_flag; /* ant colony system (ACS) */

    public int u_gb; /* every u_gb iterations update with best-so-far ant */

    public double trail_0; /* initial pheromone level in ACS */

    public Ants(Controller controller, Utilities utilities, InOut inOut, InsertionHeuristic insertionHeuristic, LoggerOutput loggerOutput) {
        this.controller = controller;
        this.utilities = utilities;
        this.inOut = inOut;
        this.insertionHeuristic = insertionHeuristic;
        this.loggerOutput = loggerOutput;
    }

    //allocate the memory for the ant colony, the best-so-far ant
    public void allocate_ants(VRPTW vrptw) {
        int i, j;
        ants = new Ant[n_ants];
        committedNodes = new boolean[vrptw.n];
        for (i = 0; i < vrptw.n; i++) {
            committedNodes[i] = false;
        }
        for (i = 0; i < n_ants; i++) {
            ants[i] = new Ant();
            ants[i].tours = new ArrayList();
            ants[i].tour_lengths = new ArrayList<>();
            ants[i].beginService = new double[vrptw.n + 1];
            ants[i].currentTime = new ArrayList<>();
            ants[i].currentQuantity = new ArrayList<>();
            ants[i].usedVehicles = 1;
            ants[i].addedEmptyTour = false;
            for (j = 0; j < ants[i].usedVehicles; j++) {
                ants[i].tours.add(j, new ArrayList<>());
                ants[i].tour_lengths.add(j, 0.0);
            }
            ants[i].visited = new boolean[vrptw.n];
            //the another node is the depot, which is by default visited by each salesman and added in its tour
            ants[i].toVisit = vrptw.getIdAvailableRequests().size();
            ants[i].costObjectives = new double[2];
            for (int indexObj = 0; indexObj < 2; indexObj++) {
                ants[i].costObjectives[indexObj] = 0;
            }
            ants[i].earliestTime = new ArrayList(ants[i].usedVehicles);
            ants[i].latestTime = new ArrayList(ants[i].usedVehicles);
        }
        best_so_far_ant = new Ant();
        best_so_far_ant.tours = new ArrayList();
        best_so_far_ant.tour_lengths = new ArrayList<Double>();
        best_so_far_ant.beginService = new double[vrptw.n + 1];
        best_so_far_ant.currentTime = new ArrayList<Double>();
        best_so_far_ant.currentQuantity = new ArrayList<Double>();
        best_so_far_ant.usedVehicles = 1;
        best_so_far_ant.addedEmptyTour = false;
        for (j = 0; j < best_so_far_ant.usedVehicles; j++) {
            best_so_far_ant.tours.add(j, new ArrayList<Integer>());
            best_so_far_ant.tour_lengths.add(j, 0.0);
        }
        best_so_far_ant.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        best_so_far_ant.toVisit = vrptw.getIdAvailableRequests().size();
        best_so_far_ant.longest_tour_length = Double.MAX_VALUE;
        best_so_far_ant.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            best_so_far_ant.costObjectives[indexObj] = 0;
        }
        best_so_far_ant.earliestTime = new ArrayList(best_so_far_ant.usedVehicles);
        best_so_far_ant.latestTime = new ArrayList(best_so_far_ant.usedVehicles);
        restart_best_ant = new Ant();
        restart_best_ant.tours = new ArrayList();
        restart_best_ant.tour_lengths = new ArrayList<Double>();
        restart_best_ant.beginService = new double[vrptw.n + 1];
        restart_best_ant.currentTime = new ArrayList<Double>();
        restart_best_ant.currentQuantity = new ArrayList<Double>();
        restart_best_ant.usedVehicles = 1;
        restart_best_ant.addedEmptyTour = false;
        for (j = 0; j < restart_best_ant.usedVehicles; j++) {
            restart_best_ant.tours.add(j, new ArrayList<Integer>());
            restart_best_ant.tour_lengths.add(j, 0.0);
        }
        restart_best_ant.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        restart_best_ant.toVisit = vrptw.getIdAvailableRequests().size();
        restart_best_ant.longest_tour_length = Double.MAX_VALUE;
        restart_best_ant.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            restart_best_ant.costObjectives[indexObj] = 0;
        }
        restart_best_ant.earliestTime = new ArrayList(restart_best_ant.usedVehicles);
        restart_best_ant.latestTime = new ArrayList(restart_best_ant.usedVehicles);
    }

    //find the best ant of the current iteration (the one with the lowest number of used vehicles and
    //with smaller total traveled distance)
    public int find_best() {
        double min1, min2;
        int k1, k2, k2_min;
        //first detect the ant which uses the minimum number of vehicles
        min1 = ants[0].usedVehicles;
        for (k1 = 1; k1 < n_ants; k1++) {
            if (ants[k1].usedVehicles < min1) {
                min1 = ants[k1].usedVehicles;
            }
        }
        //among the vehicles which use the minimum number of vehicles, select the best ant as the one with the minimum total distance for its traveled tours
        min2 = Double.MAX_VALUE;
        k2_min = 0;
        for (k2 = 0; k2 < n_ants; k2++) {
            if (ants[k2].usedVehicles == min1) {
                if (ants[k2].total_tour_length < min2) {
                    min2 = ants[k2].total_tour_length;
                    k2_min = k2;
                }
            }
        }
        return k2_min;
    }

    //initialize pheromone trails
    //matricea cu urmele de feromoni trebuie sa se faca relativ la toate cele n orase
    public void init_pheromone_trails(VRPTW vrptw, double initial_trail) {
        int i, j;
        /* Initialize pheromone trails */
        for (i = 0; i < (vrptw.n + 1); i++) {
            for (j = 0; j <= i; j++) {
                pheromone[i][j] = initial_trail;
                pheromone[j][i] = initial_trail;
            }
        }
    }

    //preserve some of the pheromone level on the edges between available nodes
    public void preservePheromones(VRPTW vrptw) {
        for (int i = 0; i < (vrptw.n + 1); i++) {
            for (int j = 0; j <= i; j++) {
                if (vrptw.getIdAvailableRequests().contains(i - 1) && vrptw.getIdAvailableRequests().contains(j - 1)
                        || ((i == 0) && vrptw.getIdAvailableRequests().contains(j - 1))
                        || ((j == 0) && vrptw.getIdAvailableRequests().contains(i - 1))) {
                    pheromone[i][j] = pheromone[i][j] * (1 - inOut.pheromonePreservation) + inOut.pheromonePreservation * trail_0;
                    pheromone[j][i] = pheromone[i][j];
                } else {
                    pheromone[i][j] = trail_0;
                    pheromone[j][i] = pheromone[i][j];
                }
            }
        }
    }

    //implements the pheromone trail evaporation
    public void evaporation(VRPTW vrptw) {
        int i, j;
        for (i = 0; i < vrptw.n + 1; i++) {
            for (j = 0; j <= i; j++) {
                pheromone[i][j] = (1 - rho) * pheromone[i][j];
                pheromone[j][i] = pheromone[i][j];
            }
        }
    }

    //reinforces edges used in ant k's solution
    public void global_update_pheromone(Ant a) {
        int i, j, h, k, size;
        double d_tau;
        d_tau = 1.0 / a.total_tour_length;
        for (i = 0; i < a.usedVehicles; i++) {
            size = a.tours.get(i).size();
            for (k = 0; k < size - 1; k++) {
                j = a.tours.get(i).get(k);
                h = a.tours.get(i).get(k + 1);
                j++;
                h++;
                pheromone[j][h] += d_tau;
                pheromone[h][j] = pheromone[j][h];
            }
        }
    }

    //empty the ants's memory regarding visited cities
    public void ant_empty_memory(Ant a, VRPTW vrptw) {
        int i, j;
        a.total_tour_length = 0;
        a.longest_tour_length = Integer.MAX_VALUE;
        a.indexLongestTour = 0;
        a.addedEmptyTour = false;
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            a.costObjectives[indexObj] = 0;
        }
        a.tour_lengths.clear();
        a.currentQuantity.clear();
        a.currentTime.clear();
        //clear all the elements (cities) from the tours of an ant
        for (i = 0; i < a.usedVehicles; i++) {
            a.tours.get(i).clear();
        }
        a.tours.clear();
        if (a.earliestTime != null) {
            a.earliestTime.clear();
        }
        if (a.latestTime != null) {
            a.latestTime.clear();
        }
        a.usedVehicles = 1;
        for (i = 0; i < a.usedVehicles; i++) {
            a.tour_lengths.add(i, 0.0);
            a.currentQuantity.add(i, 0.0);
            a.currentTime.add(i, 0.0);
            a.tours.add(i, new ArrayList<>());
        }
        for (j = 0; j < vrptw.n; j++) {
            a.visited[j] = false;
        }
        for (j = 0; j < (vrptw.n + 1); j++) {
            a.beginService[j] = 0;
        }
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        a.toVisit = vrptw.getIdAvailableRequests().size();
    }

    //get the list with the unvisited customers
    public ArrayList unroutedCustomers(Ant a, VRPTW vrptw) {
        ArrayList<Integer> l = new ArrayList<>(a.toVisit);
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        int count = 0;
        //collect nodes missing from the ant's solution; depot is considered to be visisted by default
        for (int city : idKnownRequests) {
            if (a.visited[city] == false) {
                l.add(city);
                count++;
                if (count == a.toVisit) {
                    break;
                }
            }
        }
        return l;
    }

    //choose for an ant as the next city the one with maximal value of heuristic information times pheromone
    public int[] choose_best_next(Ant a, VRPTW vrptw, VRPTW_ACS vrptw_acs) {
        int current_city, next_city, salesman = 0, indexTour, startIndex, startIndexTour;
        double value_best = -1.;  /* values in total matrix are always >= 0.0 */
        double help;
        int[] values = new int[2];
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDiference, deliveryUrgency, bestBeginService = 0;
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        ArrayList<Integer> lastCommitedIndexes;
        int pos;
        boolean appliedInsertion = false;
        next_city = vrptw.n;
        if (a.addedEmptyTour) {
            startIndex = a.usedVehicles - 1;
        } else {
            startIndex = 0;
        }
        for (int indexSalesman = startIndex; indexSalesman < a.usedVehicles; indexSalesman++) {
            int lastPos = a.tours.get(indexSalesman).size() - 1;
            current_city = a.tours.get(indexSalesman).get(lastPos);
            current_city++;
            for (int city : idKnownRequests) {
                if (a.visited[city])
                    ; /* city already visited, do nothing */
                else {
                    distance = vrptw.instance.distance[current_city][city + 1];
                    arrivalTime = a.currentTime.get(indexSalesman) + reqList.get(current_city).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());

                    distanceDepot = vrptw.instance.distance[city + 1][0];
                    arrivalTimeDepot = beginService + reqList.get(city + 1).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());

                    if (vrptw_acs.isFeasible(vrptw, a, city, beginService, beginServiceDepot, indexSalesman)) {
                        deliveryUrgency = reqList.get(city + 1).getEndWindow() - (a.beginService[current_city] + reqList.get(current_city).getServiceTime() + distance);
                        timeDiference = beginService - a.beginService[current_city] - reqList.get(current_city).getServiceTime();
                        help = 1.0 / (weight1 * distance + weight2 * timeDiference + weight3 * deliveryUrgency);
                        help = Math.pow(help, beta);
                        help = help * Math.pow(pheromone[current_city][city + 1], alpha);
                        if (help > value_best) {
                            next_city = city;
                            value_best = help;
                            salesman = indexSalesman;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
        }
        //it means that not all the cities are covered and some customers has not yet been serviced
        //by using an insertion heuristic try to insert in the infeasible solution the customers not visited yet
        //when no more customer with feasible insertions can be found, start a new route/tour and add
        //one more vehicle for constructing a feasible solution
        if (next_city == vrptw.n) {
            if ((a.toVisit > 0) && (a.toVisit <= 10)) {
                //determine nodes that are not visited yet in the current ant's solution
                ArrayList<Integer> unroutedList = unroutedCustomers(a, vrptw);
                if (appliedInsertion) {
                    startIndexTour = a.usedVehicles - 1;
                } else {
                    startIndexTour = 0;
                }
                lastCommitedIndexes = new ArrayList<>();
                for (int index = 0; index < best_so_far_ant.usedVehicles; index++) {
                    pos = controller.getLastCommitedPos(index);
                    lastCommitedIndexes.add(pos);
                }
                //skip over committed (defined) nodes when performing insertion heuristic
                insertionHeuristic.insertUnroutedCustomers(a, vrptw, unroutedList, startIndexTour, lastCommitedIndexes);
            }
            //if no more unrouted customers can be feasible inserted in the solution and there are still
            //remaining unrouted customers, add a new tour
            if (a.toVisit > 0) {
                a.usedVehicles++;
                indexTour = a.usedVehicles - 1;
                a.tours.add(indexTour, new ArrayList<Integer>());
                a.tours.get(indexTour).add(-1);
                a.tour_lengths.add(indexTour, 0.0);
                a.currentQuantity.add(indexTour, 0.0);
                a.currentTime.add(indexTour, 0.0);
                a.addedEmptyTour = true;
                values[0] = -1;
                values[1] = indexTour;
            }
        } else {
            a.tours.get(salesman).add(next_city);
            a.visited[next_city] = true;
            a.toVisit--;
            a.currentTime.set(salesman, bestBeginService);
            a.beginService[next_city + 1] = bestBeginService;
            double newQuantity = a.currentQuantity.get(salesman) + reqList.get(next_city + 1).getDemand();
            a.currentQuantity.set(salesman, newQuantity);
            values[0] = next_city;
            values[1] = salesman;
        }
        return values;
    }

    //chooses for an ant as the next city the one with maximal value of heuristic information times pheromone
    public int[] neighbour_choose_best_next(Ant a, VRPTW vrptw, VRPTW_ACS vrptw_acs) {
        int i, current_city, next_city, help_city, salesman = 0, startPos;
        double value_best = -1;  //values in total matrix are always >= 0.0
        double help;
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double currentTime, timeDiference, waiting, deliveryUrgency, bestBeginService = 0;
        int[] values = new int[2];
        ArrayList<Request> reqList = vrptw.getRequests();
        next_city = vrptw.n;
        if (a.addedEmptyTour) {
            startPos = a.usedVehicles - 1;
        } else {
            startPos = 0;
        }
        for (int indexSalesman = startPos; indexSalesman < a.usedVehicles; indexSalesman++) {
            int lastPos = a.tours.get(indexSalesman).size() - 1;
            current_city = a.tours.get(indexSalesman).get(lastPos);
            current_city++;
            for (i = 0; i < nn_ants; i++) {
                help_city = vrptw.instance.nn_list[current_city][i];
                if ((vrptw.getIdAvailableRequests().contains(help_city - 1)) && (!a.visited[help_city - 1])) {
                    distance = vrptw.instance.distance[current_city][help_city];
                    arrivalTime = a.currentTime.get(indexSalesman) + reqList.get(current_city).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(help_city).getStartWindow());
                    distanceDepot = vrptw.instance.distance[help_city][0];
                    arrivalTimeDepot = beginService + reqList.get(help_city).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                    if (vrptw_acs.isFeasible(vrptw, a, help_city - 1, beginService, beginServiceDepot, indexSalesman)) {
                        deliveryUrgency = reqList.get(help_city).getEndWindow() - (a.beginService[current_city] + reqList.get(current_city).getServiceTime() + distance);
                        timeDiference = beginService - a.beginService[current_city] - reqList.get(current_city).getServiceTime();
                        help = 1.0 / (weight1 * distance + weight2 * timeDiference + weight3 * deliveryUrgency);
                        help = Math.pow(help, beta);
                        help = help * Math.pow(pheromone[current_city][help_city], alpha);
                        if (help > value_best) {
                            value_best = help;
                            next_city = help_city - 1;
                            salesman = indexSalesman;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
        }
        if (next_city == vrptw.n) {
            // all cities in nearest neighbor list were already visited
            values = choose_best_next(a, vrptw, vrptw_acs);
            return values;
        } else {
            a.tours.get(salesman).add(next_city);
            a.visited[next_city] = true;
            a.toVisit--;
            a.currentTime.set(salesman, bestBeginService);
            a.beginService[next_city + 1] = bestBeginService;
            double newQuantity = a.currentQuantity.get(salesman) + reqList.get(next_city + 1).getDemand();
            a.currentQuantity.set(salesman, newQuantity);
            values[0] = next_city;
            values[1] = salesman;
            return values;
        }
    }

    public void choose_closest_nn(Ant a, int indexSalesman, VRPTW vrptw, VRPTW_ACS vrptw_acs) {
        int current_city, next_city;
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDiference, deliveryUrgency, bestBeginService = 0, minValue, metricValue;
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        while (a.toVisit > 0) {
            next_city = vrptw.n;
            int lastPos = a.tours.get(indexSalesman).size() - 1;
            current_city = a.tours.get(indexSalesman).get(lastPos);
            current_city++;
            minValue = Integer.MAX_VALUE;
            for (int city : idKnownRequests) {
                if (a.visited[city])
                    ;  //city already visited
                else {
                    distance = vrptw.instance.distance[current_city][city + 1];
                    arrivalTime = a.currentTime.get(indexSalesman) + reqList.get(current_city).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    distanceDepot = vrptw.instance.distance[city + 1][0];
                    arrivalTimeDepot = beginService + reqList.get(city + 1).getServiceTime() + distanceDepot;
                    beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                    if (vrptw_acs.isFeasible(vrptw, a, city, beginService, beginServiceDepot, indexSalesman)) {
                        //compute the value of the "closeness" metric; this metric tries to account
                        //for both geographical and temporal closeness of customers
                        timeDiference = beginService - a.beginService[current_city] - reqList.get(current_city).getServiceTime();
                        deliveryUrgency = reqList.get(city + 1).getEndWindow() - (a.beginService[current_city] + reqList.get(current_city).getServiceTime() + distance);
                        metricValue = weight1 * distance + weight2 * timeDiference + weight3 * deliveryUrgency;
                        if (metricValue < minValue) {
                            next_city = city;
                            minValue = metricValue;
                            bestBeginService = beginService;
                        }
                    }
                }
            }
            //no more nodes can be feasible added in the tour
            if (next_city == vrptw.n) {
                break;
            } else {
                a.tours.get(indexSalesman).add(next_city);
                a.visited[next_city] = true;
                a.toVisit--;
                a.currentTime.set(indexSalesman, bestBeginService);
                a.beginService[next_city + 1] = bestBeginService;
                a.currentQuantity.set(indexSalesman, a.currentQuantity.get(indexSalesman) + reqList.get(next_city + 1).getDemand());
            }
        }
    }

    public void choose_closest_next(Ant a, int indexSalesman, VRPTW vrptw, VRPTW_ACS vrptw_acs) {
        int current_city, next_city, indexTour;
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double timeDiference, deliveryUrgency, bestBeginService = 0, minValue, metricValue;
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        next_city = vrptw.n;
        int lastPos = a.tours.get(indexSalesman).size() - 1;
        current_city = a.tours.get(indexSalesman).get(lastPos);
        current_city++;
        minValue = Integer.MAX_VALUE;
        for (int city : idKnownRequests) {
            if (a.visited[city])
                ;  //city already visited
            else {
                distance = vrptw.instance.distance[current_city][city + 1];
                arrivalTime = a.currentTime.get(indexSalesman) + reqList.get(current_city).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                distanceDepot = vrptw.instance.distance[city + 1][0];
                arrivalTimeDepot = beginService + reqList.get(city + 1).getServiceTime() + distanceDepot;
                beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
                if (vrptw_acs.isFeasible(vrptw, a, city, beginService, beginServiceDepot, indexSalesman)) {
                    //compute the value of the "closeness" metric; this metric tries to account
                    //for both geographical and temporal closeness of customers
                    timeDiference = beginService - a.beginService[current_city] - reqList.get(current_city).getServiceTime();
                    deliveryUrgency = reqList.get(city + 1).getEndWindow() - (a.beginService[current_city] + reqList.get(current_city).getServiceTime() + distance);
                    metricValue = weight1 * distance + weight2 * timeDiference + weight3 * deliveryUrgency;
                    if (metricValue < minValue) {
                        next_city = city;
                        minValue = metricValue;
                        bestBeginService = beginService;
                    }
                }
            }
        }
        //it means that not all the cities are covered and some customers has not yet been serviced
        //by using insertion heuristic try to insert in the current tour of the solution under construction
        //the customers not visited yet
        //when no more customer with feasible insertions can be found, start a new route/tour and add
        //one more vehicle for constructing a feasible solution
        if (next_city == vrptw.n) {
            //if no more unrouted customers can be feasible inserted in the solution and there are still
            //remaining unrouted customers, add a new tour
            if (a.toVisit > 0) {
                a.usedVehicles++;
                indexTour = a.usedVehicles - 1;
                a.tours.add(indexTour, new ArrayList<Integer>());
                a.tours.get(indexTour).add(-1);
                a.tour_lengths.add(indexTour, 0.0);
                a.currentQuantity.add(indexTour, 0.0);
                a.currentTime.add(indexTour, 0.0);
            }
        } else {
            a.tours.get(indexSalesman).add(next_city);
            a.visited[next_city] = true;
            a.toVisit--;
            a.currentTime.set(indexSalesman, bestBeginService);
            a.beginService[next_city + 1] = bestBeginService;
            a.currentQuantity.set(indexSalesman, a.currentQuantity.get(indexSalesman) + reqList.get(next_city + 1).getDemand());
        }
    }

    //Choose for an ant probabilistically a next city among all unvisited cities in the current city's candidate list
    public int[] neighbour_choose_and_move_to_next(Ant a, VRPTW vrptw, VRPTW_ACS vrptw_acs) {
        int i, j, help, city, salesman = 0;
        int current_city;
        double rnd, partial_sum, sum_prob = 0.0;
        double prob_ptr[][];
        double help1;
        int[] values = new int[2];
        int[] tempCities = new int[a.usedVehicles];
        double distance, distanceDepot, arrivalTime, arrivalTimeDepot, beginService, beginServiceDepot;
        double deliveryUrgency, timeDiference;
        ArrayList<Request> reqList = vrptw.getRequests();
        if ((q_0 > 0.0) && (utilities.random01() < q_0)) {
            /*
             * With a probability q_0 make the best possible choice according to pheromone trails and heuristic
             * information, this corresponds to exploitation.
             * We first check whether q_0 > 0.0, to avoid the very common case of q_0 = 0.0 to have to compute
             * a random number, which is expensive computationally.
             */
            values = neighbour_choose_best_next(a, vrptw, vrptw_acs);
            return values;
        }
        prob_ptr = new double[a.usedVehicles][nn_ants + 1];
        for (j = 0; j < a.usedVehicles; j++) {
            for (i = 0; i < nn_ants; i++) {
                prob_ptr[j][i] = Double.POSITIVE_INFINITY;
            }
        }
        for (j = 0; j < (a.usedVehicles - 1); j++) {
            prob_ptr[j][nn_ants] = 0;
        }
        prob_ptr[a.usedVehicles - 1][nn_ants] = Double.POSITIVE_INFINITY;
        for (int indexSalesman = 0; indexSalesman < a.usedVehicles; indexSalesman++) {
            /* current_city city of ant k */
            int lastPos = a.tours.get(indexSalesman).size() - 1;
            current_city = a.tours.get(indexSalesman).get(lastPos);
            current_city++;
            for (i = 0; i < nn_ants; i++) {
                city = vrptw.instance.nn_list[current_city][i];
                distance = vrptw.instance.distance[current_city][city];
                arrivalTime = a.currentTime.get(indexSalesman) + reqList.get(current_city).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(city).getStartWindow());
                distanceDepot = vrptw.instance.distance[city][0];
                arrivalTimeDepot = beginService + reqList.get(city).getServiceTime() + distanceDepot;
                beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());

                if (!(vrptw.getIdAvailableRequests().contains(city - 1)) || (a.visited[city - 1]) || (!vrptw_acs.isFeasible(vrptw, a, city - 1, beginService, beginServiceDepot, indexSalesman)))
                    prob_ptr[indexSalesman][i] = 0.0; /* city already visited */
                else if ((vrptw_acs.isFeasible(vrptw, a, city - 1, beginService, beginServiceDepot, indexSalesman)) && (vrptw.getIdAvailableRequests().contains(city - 1)) && !(a.visited[city - 1])) {
                    deliveryUrgency = reqList.get(city).getEndWindow() - (a.beginService[current_city] + reqList.get(current_city).getServiceTime() + distance);
                    timeDiference = beginService - a.beginService[current_city] - reqList.get(current_city).getServiceTime();
                    help1 = 1.0 / (weight1 * distance + weight2 * timeDiference + weight3 * deliveryUrgency);
                    help1 = Math.pow(help1, beta);
                    help1 = help1 * Math.pow(pheromone[current_city][city], alpha);
                    prob_ptr[indexSalesman][i] = help1;
                    tempCities[indexSalesman] = current_city;
                    sum_prob += prob_ptr[indexSalesman][i];
                }
            }
        }
        if (sum_prob <= 0.0) {
            /* All cities from the candidate  are tabu (are already visited) */
            values = choose_best_next(a, vrptw, vrptw_acs);
            return values;
        } else {
            // at least one neighbor is eligible, choose one according to the selection probabilities
            rnd = utilities.random01();
            rnd *= sum_prob;
            i = 0;
            boolean done = false, forcedEnd = false;
            partial_sum = 0;
            for (int indexSalesman = 0; indexSalesman < a.usedVehicles && !done; indexSalesman++) {
                i = 0;
                partial_sum += prob_ptr[indexSalesman][i];
                /* This loop always stops because prob_ptr[nn_ants] == HUGE_VAL */
                while (partial_sum <= rnd) {
                    i++;
                    if (i < 0) {
                        loggerOutput.log("Iter=" + inOut.iteration + " Test: indexSalesman= " + indexSalesman + " i= " + i);
                        partial_sum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                        break;
                    }
                    if (i < prob_ptr[indexSalesman].length) {
                        partial_sum += prob_ptr[indexSalesman][i];
                        salesman = indexSalesman;
                    } else if (i >= prob_ptr[indexSalesman].length) {
                        break;
                    }
                    //add a big value to the partial_sum to be sure that the while loop ends
                    else if (indexSalesman == (a.usedVehicles - 1) && i >= prob_ptr[indexSalesman].length && partial_sum <= rnd) {
                        partial_sum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                    }
                }
                if (partial_sum > rnd) {
                    done = true;
                    if (!forcedEnd) {
                        salesman = indexSalesman;
                    } else { //choose randomly a salesman to whom add the city
                        salesman = (int) (utilities.random01() * a.usedVehicles);
                    }
                }
            }
            // This may very rarely happen because of rounding if rnd is close to 1.
            if (i == nn_ants) {
                values = neighbour_choose_best_next(a, vrptw, vrptw_acs);
                return values;
            }
            current_city = tempCities[salesman];
            int maxIndex = 0;
            double maxValue;
            if (i < 0) {
                maxValue = prob_ptr[salesman][0];
                for (j = 1; j < nn_ants; j++) {
                    if (prob_ptr[salesman][j] > maxValue) {
                        maxValue = prob_ptr[salesman][j];
                        maxIndex = j;
                    }
                }
                i = maxIndex;
            }
            help = vrptw.instance.nn_list[current_city][i];
            distance = vrptw.instance.distance[current_city][help];
            arrivalTime = a.currentTime.get(salesman) + reqList.get(current_city).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(help).getStartWindow());
            a.tours.get(salesman).add(help - 1);
            a.visited[help - 1] = true;
            a.toVisit--;
            a.currentTime.set(salesman, beginService);
            a.beginService[help] = beginService;
            double newQuantity = a.currentQuantity.get(salesman) + reqList.get(help).getDemand();
            a.currentQuantity.set(salesman, newQuantity);
            values[0] = help - 1;
            values[1] = salesman;
            return values;
        }

    }

    //reinforces the edges used in ant's solution as in ACS
    public void global_acs_pheromone_update(Ant a) {
        int i, j, h, k, size;
        double d_tau;
        d_tau = 1.0 / (double) a.total_tour_length;
        for (i = 0; i < a.usedVehicles; i++) {
            size = a.tours.get(i).size();
            for (k = 0; k < size - 1; k++) {
                j = a.tours.get(i).get(k);
                h = a.tours.get(i).get(k + 1);
                j++;
                h++;
                pheromone[j][h] = (1. - rho) * pheromone[j][h] + rho * d_tau;
                pheromone[h][j] = pheromone[j][h];
            }
        }

    }

    //removes some pheromone on edge just passed by the ant
    public void local_acs_pheromone_update(Ant a, int indexSalesman) {
        int h, j;
        int lastPos = a.tours.get(indexSalesman).size() - 1;
        j = a.tours.get(indexSalesman).get(lastPos);
        h = a.tours.get(indexSalesman).get(lastPos - 1);
        j++;
        h++;
        /* still additional parameter has to be introduced */
        pheromone[h][j] = (1. - local_rho) * pheromone[h][j] + local_rho * trail_0;
        pheromone[j][h] = pheromone[h][j];
    }

    //copy solution from ant a1 into ant a2
    public void copy_from_to(Ant a1, Ant a2, VRPTW vrptw) {
        int i, j;
        ant_empty_memory(a2, vrptw);
        a2.total_tour_length = a1.total_tour_length;
        a2.toVisit = a1.toVisit;
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            a2.costObjectives[indexObj] = a1.costObjectives[indexObj];
        }
        if (a2.usedVehicles < a1.usedVehicles) {
            for (int index = a2.usedVehicles; index < a1.usedVehicles; index++) {
                a2.tour_lengths.add(index, 0.0);
                a2.tours.add(index, new ArrayList<Integer>());
                a2.currentQuantity.add(index, 0.0);
                a2.currentTime.add(index, 0.0);
            }
        }
        for (i = 0; i < a1.usedVehicles; i++) {
            a2.tour_lengths.set(i, a1.tour_lengths.get(i));
            a2.currentQuantity.set(i, a1.currentQuantity.get(i));
            a2.currentTime.set(i, a1.currentTime.get(i));
            int size = a1.tours.get(i).size();
            for (j = 0; j < size; j++) {
                int elem = a1.tours.get(i).get(j);
                a2.tours.get(i).add(elem);
            }
        }
        for (i = 0; i < vrptw.n; i++) {
            a2.visited[i] = a1.visited[i];
        }
        a2.usedVehicles = a1.usedVehicles;
        for (i = 0; i < (vrptw.n + 1); i++) {
            a2.beginService[i] = a1.beginService[i];
        }
    }

    public double computeToursAmplitude(Ant a) {
        double min, max;
        int i;
        min = a.tour_lengths.get(0);
        max = a.tour_lengths.get(0);
        for (i = 1; i < a.tours.size(); i++) {
            if (a.tour_lengths.get(i) < min) {
                min = a.tour_lengths.get(i);
            }
            if (a.tour_lengths.get(i) > max) {
                max = a.tour_lengths.get(i);
            }
        }
        return (max - min);
    }

}
