package com.github.schmittjoaopedro.vrp.dvrptwacs;


import java.util.ArrayList;
import java.util.List;

public class Ants {

    private LoggerOutput loggerOutput;

    private InsertionHeuristic insertionHeuristic;

    private Utilities utilities;

    private Controller controller;

    public final double weight1 = 0.4;
    public final double weight2 = 0.4;
    public final double weight3 = 0.2;

    public Ant ants[];
    public Ant bestSoFarAnt;
    public Ant restartBestAnt;

    public double pheromone[][];

    public int nAnts; /* number of ants */

    public int nnAnts; /* length of nearest neighbor lists for the ants' solution construction */

    public double rho; /* parameter for evaporation used in global pheromne update*/
    public double local_rho;  /* parameter for evaporation used in local pheromone update*/
    public double pheromonePreservation;
    public double alpha; /* importance of trail */
    public double beta; /* importance of heuristic evaluate */
    public double q0; /* probability of best choice in tour construction */

    public boolean asFlag; /* ant system */
    public boolean acsFlag; /* ant colony system (ACS) */

    public int uGb; /* every uGb iterations update with best-so-far ant */

    public double trail0; /* initial pheromone level in ACS */

    public Ants(Controller controller, Utilities utilities, InsertionHeuristic insertionHeuristic, LoggerOutput loggerOutput) {
        this.controller = controller;
        this.utilities = utilities;
        this.insertionHeuristic = insertionHeuristic;
        this.loggerOutput = loggerOutput;
    }

    //allocate the memory for the ant colony, the best-so-far ant
    public void allocateAnts(VRPTW vrptw) {
        ants = new Ant[nAnts];
        controller.committedNodes = new boolean[vrptw.n];
        for (int i = 0; i < vrptw.n; i++) {
            controller.committedNodes[i] = false;
        }
        for (int i = 0; i < nAnts; i++) {
            ants[i] = createNewAnt(vrptw);
        }
        bestSoFarAnt = createNewAnt(vrptw);
        bestSoFarAnt.longestTourLength = Double.MAX_VALUE;
        restartBestAnt = createNewAnt(vrptw);
        restartBestAnt.longestTourLength = Double.MAX_VALUE;
    }

    public Ant createNewAnt(VRPTW vrptw) {
        Ant ant = new Ant();
        ant.tours = new ArrayList<>();
        ant.tourLengths = new ArrayList<>();
        ant.beginService = new double[vrptw.n + 1];
        ant.currentTime = new ArrayList<>();
        ant.currentQuantity = new ArrayList<>();
        ant.usedVehicles = 1;
        ant.addedEmptyTour = false;
        for (int j = 0; j < ant.usedVehicles; j++) {
            ant.tours.add(j, new ArrayList<>());
            ant.tourLengths.add(j, 0.0);
        }
        ant.visited = new boolean[vrptw.n];
        ant.costObjectives = new double[]{0.0, 0.0};
        ant.earliestTime = new ArrayList(ant.usedVehicles);
        ant.latestTime = new ArrayList(ant.usedVehicles);
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        ant.toVisit = vrptw.getIdAvailableRequests().size();
        return ant;
    }

    //find the best ant of the current iteration (the one with the lowest number of used vehicles and
    //with smaller total traveled distance)
    public int findBest() {
        int idxBest;
        double min1;
        double min2;
        //first detect the ant which uses the minimum number of vehicles
        min1 = ants[0].usedVehicles;
        for (int i = 1; i < nAnts; i++) {
            if (ants[i].usedVehicles < min1) {
                min1 = ants[i].usedVehicles;
            }
        }
        //among the vehicles which use the minimum number of vehicles, select the best ant as the one with the minimum total distance for its traveled tours
        min2 = Double.MAX_VALUE;
        idxBest = 0;
        for (int i = 0; i < nAnts; i++) {
            if (ants[i].usedVehicles == min1 && ants[i].totalTourLength < min2) {
                min2 = ants[i].totalTourLength;
                idxBest = i;
            }
        }
        return idxBest;
    }

    //initialize pheromone trails
    public void initPheromoneTrails(double initialTrail) {
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j <= i; j++) {
                pheromone[i][j] = initialTrail;
                pheromone[j][i] = initialTrail;
            }
        }
    }

    //preserve some of the pheromone level on the edges between available nodes
    public void preservePheromones(List<Integer> idAvailableRequests) {
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (idAvailableRequests.contains(i - 1) && idAvailableRequests.contains(j - 1)
                        || ((i == 0) && idAvailableRequests.contains(j - 1))
                        || ((j == 0) && idAvailableRequests.contains(i - 1))) {
                    pheromone[i][j] = pheromone[i][j] * (1.0 - pheromonePreservation) + pheromonePreservation * trail0;
                    pheromone[j][i] = pheromone[i][j];
                } else {
                    pheromone[i][j] = trail0;
                    pheromone[j][i] = pheromone[i][j];
                }
            }
        }
    }

    //implements the pheromone trail evaporation
    public void evaporation() {
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j <= i; j++) {
                pheromone[i][j] = (1.0 - rho) * pheromone[i][j];
                pheromone[j][i] = pheromone[i][j];
            }
        }
    }

    //reinforces edges used in ant k's solution
    public void globalUpdatePheromone(Ant ant) {
        int size;
        int curr;
        int next;
        double dTau = 1.0 / ant.totalTourLength;
        for (int i = 0; i < ant.usedVehicles; i++) {
            size = ant.tours.get(i).size();
            for (int k = 0; k < size - 1; k++) {
                curr = ant.tours.get(i).get(k);
                next = ant.tours.get(i).get(k + 1);
                curr++;
                next++;
                pheromone[curr][next] += dTau;
                pheromone[next][curr] = pheromone[curr][next];
            }
        }
    }

    //empty the ants's memory regarding visited cities
    public void antEmptyMemory(Ant ant, List<Integer> idAvailableRequests) {
        ant.totalTourLength = 0;
        ant.longestTourLength = Integer.MAX_VALUE;
        ant.indexLongestTour = 0;
        ant.addedEmptyTour = false;
        ant.costObjectives = new double[]{0.0, 0.0};
        ant.tourLengths.clear();
        ant.currentQuantity.clear();
        ant.currentTime.clear();
        //clear all the elements (cities) from the tours of an ant
        for (int i = 0; i < ant.usedVehicles; i++) {
            ant.tours.get(i).clear();
        }
        ant.tours.clear();
        if (ant.earliestTime != null) {
            ant.earliestTime.clear();
        }
        if (ant.latestTime != null) {
            ant.latestTime.clear();
        }
        ant.usedVehicles = 1;
        for (int i = 0; i < ant.usedVehicles; i++) {
            ant.tourLengths.add(i, 0.0);
            ant.currentQuantity.add(i, 0.0);
            ant.currentTime.add(i, 0.0);
            ant.tours.add(i, new ArrayList<>());
        }
        for (int j = 0; j < ant.visited.length; j++) {
            ant.visited[j] = false;
        }
        for (int j = 0; j < ant.beginService.length; j++) {
            ant.beginService[j] = 0;
        }
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        ant.toVisit = idAvailableRequests.size();
    }

    //get the list with the unvisited customers
    public ArrayList<Integer> getNonRoutedCustomers(Ant ant, List<Integer> idKnownRequests) {
        int count = 0;
        ArrayList<Integer> nonRoutedCustomers = new ArrayList<>(ant.toVisit);
        //collect nodes missing from the ant's solution; depot is considered to be visisted by default
        for (int city : idKnownRequests) {
            if (ant.visited[city] == false) {
                nonRoutedCustomers.add(city);
                count++;
                if (count == ant.toVisit) {
                    break;
                }
            }
        }
        return nonRoutedCustomers;
    }

    //choose for an ant as the next city the one with maximal value of heuristic information times pheromone
    public int[] chooseBestNext(Ant ant, VRPTW vrptw, VRPTW_ACS vrptwAcs) {
        int vehicle = 0;
        int lastPos;
        int currentCity;
        int nextCity = vrptw.n;
        int helpCity;
        int startIndex;
        int startIndexTour;
        double valueBest = -1.0;  /* values in total matrix are always >= 0.0 */
        double help[];
        int[] values = new int[2];
        double bestBeginService = 0;
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        ArrayList<Integer> lastCommittedIndexes;
        if (ant.addedEmptyTour) {
            startIndex = ant.usedVehicles - 1;
        } else {
            startIndex = 0;
        }
        for (int vehicleIdx = startIndex; vehicleIdx < ant.usedVehicles; vehicleIdx++) {
            lastPos = ant.tours.get(vehicleIdx).size() - 1;
            currentCity = ant.tours.get(vehicleIdx).get(lastPos);
            currentCity++;
            for (int city : idKnownRequests) {
                if (ant.visited[city] == false) {
                    helpCity = city + 1;
                    help = vrptwAcs.HEURISTIC(this, ant, vrptw, vehicleIdx, currentCity, helpCity, true);
                    if (help[0] == 1.0 && help[1] > valueBest) { // Is feasible
                        nextCity = helpCity - 1;
                        valueBest = help[1]; // Cost
                        vehicle = vehicleIdx;
                        bestBeginService = help[2]; // Begin service
                    }
                }
            }
        }
        //it means that not all the cities are covered and some customers has not yet been serviced
        //by using an insertion heuristic try to insert in the infeasible solution the customers not visited yet
        //when no more customer with feasible insertions can be found, start a new route/tour and add
        //one more vehicle for constructing a feasible solution
        if (nextCity == vrptw.n) {
            if (ant.toVisit > 0 && ant.toVisit <= 10) {
                //determine nodes that are not visited yet in the current ant's solution
                ArrayList<Integer> nonRoutedCustomers = getNonRoutedCustomers(ant, vrptw.getIdAvailableRequests());
                startIndexTour = 0;
                lastCommittedIndexes = new ArrayList<>();
                for (int index = 0; index < bestSoFarAnt.usedVehicles; index++) {
                    lastCommittedIndexes.add(controller.getLastCommittedPos(bestSoFarAnt, index));
                }
                //skip over committed (defined) nodes when performing insertion heuristic
                insertionHeuristic.insertUnroutedCustomers(ant, vrptw, nonRoutedCustomers, startIndexTour, lastCommittedIndexes);
            }
            //if no more unrouted customers can be feasible inserted in the solution and there are still
            //remaining unrouted customers, add a new tour
            if (ant.toVisit > 0) {
                ant.usedVehicles++;
                int indexTour = ant.usedVehicles - 1;
                ant.tours.add(indexTour, new ArrayList<>());
                ant.tours.get(indexTour).add(-1);
                ant.tourLengths.add(indexTour, 0.0);
                ant.currentQuantity.add(indexTour, 0.0);
                ant.currentTime.add(indexTour, 0.0);
                ant.addedEmptyTour = true;
                values[0] = -1;
                values[1] = indexTour;
            }
        } else {
            ant.tours.get(vehicle).add(nextCity);
            ant.visited[nextCity] = true;
            ant.toVisit--;
            ant.currentTime.set(vehicle, bestBeginService);
            ant.beginService[nextCity + 1] = bestBeginService;
            double newQuantity = ant.currentQuantity.get(vehicle) + reqList.get(nextCity + 1).getDemand();
            ant.currentQuantity.set(vehicle, newQuantity);
            values[0] = nextCity;
            values[1] = vehicle;
        }
        return values;
    }

    //chooses for an ant as the next city the one with maximal value of heuristic information times pheromone
    public int[] neighbourChooseBestNext(Ant ant, VRPTW vrptw, VRPTW_ACS vrptwAcs) {
        int vehicle = 0;
        int lastPos;
        int currentCity;
        int nextCity = vrptw.n;
        int helpCity;
        int startPos;
        double valueBest = -1.0;  //values in total matrix are always >= 0.0
        double help[];
        double bestBeginService = 0;
        int[] values = new int[2];
        int[][] nnList = vrptw.instance.nnList;
        ArrayList<Request> reqList = vrptw.getRequests();
        if (ant.addedEmptyTour) {
            startPos = ant.usedVehicles - 1;
        } else {
            startPos = 0;
        }
        for (int vehicleIdx = startPos; vehicleIdx < ant.usedVehicles; vehicleIdx++) {
            lastPos = ant.tours.get(vehicleIdx).size() - 1;
            currentCity = ant.tours.get(vehicleIdx).get(lastPos);
            currentCity++;
            for (int i = 0; i < nnAnts; i++) {
                helpCity = nnList[currentCity][i];
                if ((vrptw.getIdAvailableRequests().contains(helpCity - 1)) && (!ant.visited[helpCity - 1])) {
                    help = vrptwAcs.HEURISTIC(this, ant, vrptw, vehicleIdx, currentCity, helpCity, true);
                    if (help[0] == 1.0 && help[1] > valueBest) { // Feasible
                        nextCity = helpCity - 1;
                        valueBest = help[1]; // Cost
                        vehicle = vehicleIdx;
                        bestBeginService = help[2]; // Begin service
                    }
                }
            }
        }
        if (nextCity == vrptw.n) {
            // all cities in nearest neighbor list were already visited
            values = chooseBestNext(ant, vrptw, vrptwAcs);
        } else {
            ant.tours.get(vehicle).add(nextCity);
            ant.visited[nextCity] = true;
            ant.toVisit--;
            ant.currentTime.set(vehicle, bestBeginService);
            ant.beginService[nextCity + 1] = bestBeginService;
            double newQuantity = ant.currentQuantity.get(vehicle) + reqList.get(nextCity + 1).getDemand();
            ant.currentQuantity.set(vehicle, newQuantity);
            values[0] = nextCity;
            values[1] = vehicle;
        }
        return values;
    }

    public void chooseClosestNn(Ant ant, int idxVehicle, VRPTW vrptw, VRPTW_ACS vrptwAcs) {
        int currentCity;
        int nextCity;
        int helpCity;
        double bestBeginService = 0;
        double minValue;
        double help[];
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        while (ant.toVisit > 0) {
            nextCity = vrptw.n;
            int lastPos = ant.tours.get(idxVehicle).size() - 1;
            currentCity = ant.tours.get(idxVehicle).get(lastPos);
            currentCity++;
            minValue = Integer.MAX_VALUE;
            for (int city : idKnownRequests) {
                if (ant.visited[city] == false) {
                    helpCity = city + 1;
                    help = vrptwAcs.HEURISTIC(this, ant, vrptw, idxVehicle, currentCity, helpCity, false);
                    if (help[0] == 1.0 && help[1] < minValue) {
                        nextCity = helpCity - 1;
                        minValue = help[1];
                        bestBeginService = help[2];
                    }
                }
            }
            //no more nodes can be feasible added in the tour
            if (nextCity == vrptw.n) {
                break;
            } else {
                ant.tours.get(idxVehicle).add(nextCity);
                ant.visited[nextCity] = true;
                ant.toVisit--;
                ant.currentTime.set(idxVehicle, bestBeginService);
                ant.beginService[nextCity + 1] = bestBeginService;
                ant.currentQuantity.set(idxVehicle, ant.currentQuantity.get(idxVehicle) + reqList.get(nextCity + 1).getDemand());
            }
        }
    }

    public void chooseClosestNext(Ant ant, int idxVehicle, VRPTW vrptw, VRPTW_ACS vrptwAcs) {
        int currentCity;
        int nextCity = vrptw.n;
        int indexTour;
        int helpCity;
        double bestBeginService = 0;
        double minValue;
        double help[];
        ArrayList<Request> reqList = vrptw.getRequests();
        ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
        int lastPos = ant.tours.get(idxVehicle).size() - 1;
        currentCity = ant.tours.get(idxVehicle).get(lastPos);
        currentCity++;
        minValue = Integer.MAX_VALUE;
        for (int city : idKnownRequests) {
            if (ant.visited[city] == false) {
                helpCity = city + 1;
                help = vrptwAcs.HEURISTIC(this, ant, vrptw, idxVehicle, currentCity, helpCity, false);
                if (help[0] == 1.0 && help[1] < minValue) {
                    nextCity = helpCity - 1;
                    minValue = help[1];
                    bestBeginService = help[2];
                }
            }
        }
        //it means that not all the cities are covered and some customers has not yet been serviced
        //by using insertion heuristic try to insert in the current tour of the solution under construction
        //the customers not visited yet
        //when no more customer with feasible insertions can be found, start a new route/tour and add
        //one more vehicle for constructing a feasible solution
        if (nextCity == vrptw.n) {
            //if no more unrouted customers can be feasible inserted in the solution and there are still
            //remaining unrouted customers, add a new tour
            if (ant.toVisit > 0) {
                ant.usedVehicles++;
                indexTour = ant.usedVehicles - 1;
                ant.tours.add(indexTour, new ArrayList<>());
                ant.tours.get(indexTour).add(-1);
                ant.tourLengths.add(indexTour, 0.0);
                ant.currentQuantity.add(indexTour, 0.0);
                ant.currentTime.add(indexTour, 0.0);
            }
        } else {
            ant.tours.get(idxVehicle).add(nextCity);
            ant.visited[nextCity] = true;
            ant.toVisit--;
            ant.currentTime.set(idxVehicle, bestBeginService);
            ant.beginService[nextCity + 1] = bestBeginService;
            ant.currentQuantity.set(idxVehicle, ant.currentQuantity.get(idxVehicle) + reqList.get(nextCity + 1).getDemand());
        }
    }

    //Choose for an ant probabilistically a next city among all unvisited cities in the current city's candidate list
    public int[] neighbourChooseAndMoveToNext(Ant ant, VRPTW vrptw, VRPTW_ACS vrptwAcs) {
        int help;
        int city;
        int vehicle = 0;
        int currentCity;
        double sumProb = 0.0;
        double probPtr[][];
        double helpPtr[];
        int[] tempCities = new int[ant.usedVehicles];
        double distance;
        double arrivalTime;
        double beginService;
        ArrayList<Request> reqList = vrptw.getRequests();
        if ((q0 > 0.0) && (utilities.random01() < q0)) {
            /*
             * With a probability q0 make the best possible choice according to pheromone trails and heuristic
             * information, this corresponds to exploitation.
             * We first check whether q0 > 0.0, to avoid the very common case of q0 = 0.0 to have to compute
             * a random number, which is expensive computationally.
             */
            return neighbourChooseBestNext(ant, vrptw, vrptwAcs);
        }
        probPtr = new double[ant.usedVehicles][nnAnts + 1];
        for (int j = 0; j < ant.usedVehicles; j++) {
            for (int i = 0; i < nnAnts; i++) {
                probPtr[j][i] = Double.POSITIVE_INFINITY;
            }
        }
        for (int j = 0; j < (ant.usedVehicles - 1); j++) {
            probPtr[j][nnAnts] = 0;
        }
        probPtr[ant.usedVehicles - 1][nnAnts] = Double.POSITIVE_INFINITY;
        for (int vehicleIdx = 0; vehicleIdx < ant.usedVehicles; vehicleIdx++) {
            /* current_city city of ant k */
            int lastPos = ant.tours.get(vehicleIdx).size() - 1;
            currentCity = ant.tours.get(vehicleIdx).get(lastPos);
            currentCity++;
            for (int i = 0; i < nnAnts; i++) {
                city = vrptw.instance.nnList[currentCity][i];
                helpPtr = vrptwAcs.HEURISTIC(this, ant, vrptw, vehicleIdx, currentCity, city, true);
                // Check if route is feasible
                if (!vrptw.getIdAvailableRequests().contains(city - 1) || ant.visited[city - 1] || helpPtr[0] == 0.0) {
                    probPtr[vehicleIdx][i] = 0.0; /* city already visited */
                } else if (helpPtr[0] == 1.0 && vrptw.getIdAvailableRequests().contains(city - 1) && !ant.visited[city - 1]) {
                    probPtr[vehicleIdx][i] = helpPtr[1];
                    tempCities[vehicleIdx] = currentCity;
                    sumProb += probPtr[vehicleIdx][i];
                }
            }
        }
        if (sumProb <= 0.0) {
            /* All cities from the candidate  are tabu (are already visited) */
            return chooseBestNext(ant, vrptw, vrptwAcs);
        } else {
            // at least one neighbor is eligible, choose one according to the selection probabilities
            int idx = 0;
            double rnd = utilities.random01() * sumProb;
            boolean done = false;
            boolean forcedEnd = false;
            double partialSum = 0;
            for (int vehicleIdx = 0; vehicleIdx < ant.usedVehicles && !done; vehicleIdx++) {
                idx = 0;
                partialSum += probPtr[vehicleIdx][idx];
                /* This loop always stops because prob_ptr[nnAnts] == HUGE_VAL */
                while (partialSum <= rnd) {
                    idx++;
                    if (idx < 0) {
                        loggerOutput.log("Test: indexSalesman = " + vehicleIdx + " i= " + idx);
                        partialSum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                        break;
                    }
                    if (idx < probPtr[vehicleIdx].length) {
                        partialSum += probPtr[vehicleIdx][idx];
                        vehicle = vehicleIdx;
                    } else if (idx >= probPtr[vehicleIdx].length) {
                        break;
                    }
                    //add a big value to the partial_sum to be sure that the while loop ends
                    else if (vehicleIdx == (ant.usedVehicles - 1) && idx >= probPtr[vehicleIdx].length && partialSum <= rnd) {
                        partialSum += Double.POSITIVE_INFINITY;
                        forcedEnd = true;
                    }
                }
                if (partialSum > rnd) {
                    done = true;
                    if (!forcedEnd) {
                        vehicle = vehicleIdx;
                    } else { //choose randomly a salesman to whom add the city
                        vehicle = (int) (utilities.random01() * ant.usedVehicles);
                    }
                }
            }
            // This may very rarely happen because of rounding if rnd is close to 1.
            if (idx == nnAnts) {
                return neighbourChooseBestNext(ant, vrptw, vrptwAcs);
            }
            currentCity = tempCities[vehicle];
            int maxIndex = 0;
            double maxValue;
            if (idx < 0) {
                maxValue = probPtr[vehicle][0];
                for (int j = 1; j < nnAnts; j++) {
                    if (probPtr[vehicle][j] > maxValue) {
                        maxValue = probPtr[vehicle][j];
                        maxIndex = j;
                    }
                }
                idx = maxIndex;
            }
            help = vrptw.instance.nnList[currentCity][idx];
            distance = vrptw.instance.distance[currentCity][help];
            arrivalTime = ant.currentTime.get(vehicle) + reqList.get(currentCity).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(help).getStartWindow());
            ant.tours.get(vehicle).add(help - 1);
            ant.visited[help - 1] = true;
            ant.toVisit--;
            ant.currentTime.set(vehicle, beginService);
            ant.beginService[help] = beginService;
            double newQuantity = ant.currentQuantity.get(vehicle) + reqList.get(help).getDemand();
            ant.currentQuantity.set(vehicle, newQuantity);
            return new int[]{help - 1, vehicle};
        }
    }

    //reinforces the edges used in ant's solution as in ACS
    public void globalAcsPheromoneUpdate(Ant ant) {
        int size;
        int curr;
        int next;
        double dTau = 1.0 / ant.totalTourLength;
        for (int i = 0; i < ant.usedVehicles; i++) {
            size = ant.tours.get(i).size();
            for (int k = 0; k < size - 1; k++) {
                curr = ant.tours.get(i).get(k);
                next = ant.tours.get(i).get(k + 1);
                curr++;
                next++;
                pheromone[curr][next] = (1. - rho) * pheromone[curr][next] + rho * dTau;
                pheromone[next][curr] = pheromone[curr][next];
            }
        }

    }

    //removes some pheromone on edge just passed by the ant
    public void localAcsPheromoneUpdate(Ant ant, int vehicleIdx) {
        int lastPos = ant.tours.get(vehicleIdx).size() - 1;
        int curr = ant.tours.get(vehicleIdx).get(lastPos);
        int next = ant.tours.get(vehicleIdx).get(lastPos - 1);
        curr++;
        next++;
        /* still additional parameter has to be introduced */
        pheromone[next][curr] = (1. - local_rho) * pheromone[next][curr] + local_rho * trail0;
        pheromone[curr][next] = pheromone[next][curr];
    }

    //copy solution from ant a1 into ant a2
    public void copyFromTo(Ant from, Ant to, VRPTW vrptw) {
        antEmptyMemory(to, vrptw.getIdAvailableRequests());
        to.totalTourLength = from.totalTourLength;
        to.toVisit = from.toVisit;
        for (int i = 0; i < 2; i++) {
            to.costObjectives[i] = from.costObjectives[i];
        }
        if (to.usedVehicles < from.usedVehicles) {
            for (int index = to.usedVehicles; index < from.usedVehicles; index++) {
                to.tourLengths.add(index, 0.0);
                to.tours.add(index, new ArrayList<>());
                to.currentQuantity.add(index, 0.0);
                to.currentTime.add(index, 0.0);
            }
        }
        for (int i = 0; i < from.usedVehicles; i++) {
            to.tourLengths.set(i, from.tourLengths.get(i));
            to.currentQuantity.set(i, from.currentQuantity.get(i));
            to.currentTime.set(i, from.currentTime.get(i));
            int size = from.tours.get(i).size();
            for (int j = 0; j < size; j++) {
                int elem = from.tours.get(i).get(j);
                to.tours.get(i).add(elem);
            }
        }
        for (int i = 0; i < vrptw.n; i++) {
            to.visited[i] = from.visited[i];
        }
        to.usedVehicles = from.usedVehicles;
        for (int i = 0; i < (vrptw.n + 1); i++) {
            to.beginService[i] = from.beginService[i];
        }
    }

    public double computeToursAmplitude(Ant ant) {
        double min = ant.tourLengths.get(0);
        double max = ant.tourLengths.get(0);
        for (int i = 1; i < ant.tours.size(); i++) {
            if (ant.tourLengths.get(i) < min) {
                min = ant.tourLengths.get(i);
            }
            if (ant.tourLengths.get(i) > max) {
                max = ant.tourLengths.get(i);
            }
        }
        return max - min;
    }

}
