package com.github.schmittjoaopedro.vrp.dvrptwacs;

import com.github.schmittjoaopedro.rinsim.dvrptwacs.BSFListener;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/* contains the main entry point for the implementation of ACO for solving the VRPTW problem on instances
 * belonging to Solomon benchmark
 */
public class VRPTW_ACS implements Runnable {

    private LoggerOutput loggerOutput;

    private Statistics statistics;

    private DynamicController dynamicController;

    private Ants ants;

    private Timer timer;

    private boolean isRunning = true;

    private int counter = 0;

    private BSFListener bsfListener;

    private Semaphore semaphore;

    /**
     * indicates whether local search is used in the ACO algorithm
     **/
    public boolean lsFlag = true;

    private LocalSearch localSearch;

    //indicates whether the thread was restarted from outside
    private boolean threadRestarted;

    private VRPTW vrptw;

    public VRPTW_ACS(boolean threadStopped, VRPTW vrptw, DynamicController dynamicController, Ants ants, Statistics statistics, Timer timer, LoggerOutput loggerOutput) {
        this.threadRestarted = threadStopped;
        this.vrptw = vrptw;
        this.dynamicController = dynamicController;
        this.ants = ants;
        this.statistics = statistics;
        this.timer = timer;
        this.loggerOutput = loggerOutput;
        if (lsFlag) {
            localSearch = new LocalSearch(loggerOutput);
        }
    }

    public double[] HEURISTIC(Ants ants, Ant ant, VRPTW vrptw, int vehicleIdx, int currentCity, int nextCity, boolean inverse) {
        double cost = 0.0;
        double feasible = 0.0;
        double distances[][] = vrptw.instance.distance;
        ArrayList<Request> reqList = vrptw.getRequests();
        // Calculate distance from current city to next city
        double distance = distances[currentCity][nextCity];
        double arrivalTime = ant.currentTime.get(vehicleIdx) + reqList.get(currentCity).getServiceTime() + distance;
        double beginService = Math.max(arrivalTime, reqList.get(nextCity).getStartWindow());
        // Calculate distance from next city to depot
        double distanceDepot = distances[nextCity][0];
        double arrivalTimeDepot = beginService + reqList.get(nextCity).getServiceTime() + distanceDepot;
        double beginServiceDepot = Math.max(arrivalTimeDepot, reqList.get(0).getStartWindow());
        // Check if route is feasible
        if (isFeasibleMove(vrptw, ant, nextCity - 1, beginService, beginServiceDepot, vehicleIdx)) {
            double deliveryUrgency = reqList.get(nextCity).getEndWindow() - (ant.beginService[currentCity] + reqList.get(currentCity).getServiceTime() + distance);
            double timeDifference = beginService - ant.beginService[currentCity] - reqList.get(currentCity).getServiceTime();
            if (inverse) {
                cost = 1.0 / (ants.weight1 * distance + ants.weight2 * timeDifference + ants.weight3 * deliveryUrgency);
                cost = Math.pow(cost, ants.beta);
                cost = cost * Math.pow(ants.pheromone[currentCity][nextCity], ants.alpha);
            } else {
                cost = ants.weight1 * distance + ants.weight2 * timeDifference + ants.weight3 * deliveryUrgency;
            }
            feasible = 1.0;
        }
        return new double[]{feasible, cost, beginService};
    }

    public boolean isFeasibleMove(VRPTW vrptw, Ant ant, int city, double beginService, double beginServiceDepot, int indexSalesman) {
        boolean ok = false;
        double currentQuantity;
        ArrayList<Request> reqList = vrptw.getRequests();
        //check upper bound of the time window of the next customer to be visited &&
        //current capacity of the car && vehicle arrival time at the depot (the maximum total route time -> upper bound of the time window of the depot)
        currentQuantity = ant.currentQuantity.get(indexSalesman) + reqList.get(city + 1).getDemand();
        if (beginService <= reqList.get(city + 1).getEndWindow() && currentQuantity <= vrptw.getCapacity() && beginServiceDepot <= reqList.get(0).getEndWindow()) {
            ok = true;
        }
        return ok;
    }


    public boolean checkFeasibility(Ant a, VRPTW vrp, boolean printNoNodes) {
        int currentCity, prevCity, addedNodes = 0;
        double currentQuantity, currentTime;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = vrp.getRequests();
        for (int indexTour = 0; indexTour < a.usedVehicles; indexTour++) {
            currentQuantity = reqList.get(0).getDemand();
            currentTime = 0.0;
            for (int currentPos = 1; currentPos < a.tours.get(indexTour).size(); currentPos++) {
                if (currentPos < a.tours.get(indexTour).size() - 1) {
                    addedNodes++;
                }
                prevCity = a.tours.get(indexTour).get(currentPos - 1);
                currentCity = a.tours.get(indexTour).get(currentPos);
                currentQuantity += reqList.get(currentCity + 1).getDemand();
                distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
                arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
                if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                    loggerOutput.log("Time window constraint violated");
                    return false;
                }
                currentTime = beginService;
            }
            if (currentQuantity > vrp.getCapacity()) {
                loggerOutput.log("Capacity constraint violated");
                return false;
            }
        }
        if (printNoNodes) {
            loggerOutput.log("Added nodes=" + addedNodes);
        }
        return true;
    }

    //check if there is still an ant with left cities to visit
    public boolean isDone() {
        for (int k = 0; k < ants.nAnts; k++) {
            if (ants.ants[k].toVisit > 0) {
                return false;
            }
        }
        return true;
    }


    //manage the solution construction phase (construct a set of complete and closed tours, one
    //for each vehicle)
    public void constructSolutions(VRPTW vrptw) {
        int k; /* counter variable */
        int step; /* counter of the number of construction steps */
        int values[];
        /* Mark all cities as unvisited */
        for (k = 0; k < ants.nAnts; k++) {
            ants.antEmptyMemory(ants.ants[k], vrptw.getIdAvailableRequests());
        }
        /* Place the ants on same initial city, which is the depot city */
        for (k = 0; k < ants.nAnts; k++) {
            for (int i = 0; i < ants.ants[k].usedVehicles; i++) {
                //place each ant on the depot city, which is the start city of each tour
                // -1 is a special marker for the deport city, so that it's not be confused with the rest of the cities
                // all the rest of the cities are represented by integer values > 0
                ants.ants[k].tours.get(i).add(-1);
            }
        }
        //initialize ant solution with the committed nodes (if any) from each tour of the best so far solution
        if (dynamicController.checkCommittedTours(ants.bestSoFarAnt)) {
            for (k = 0; k < ants.nAnts; k++) {
                dynamicController.addCommittedNodes(ants.bestSoFarAnt, ants.ants[k], vrptw);
            }
        }
        while (!isDone()) {
            for (k = 0; k < ants.nAnts; k++) {
                if (ants.ants[k].toVisit > 0) {
                    //choose for each ant in a probabilistic way by some type of roullette wheel selection
                    //which salesman to consider next, that will visit a city
                    values = ants.neighbourChooseAndMoveToNext(ants.ants[k], vrptw, this);
                    if (values[0] != -1 && ants.acsFlag) {
                        ants.localAcsPheromoneUpdate(ants.ants[k], values[1]);
                    }

                }
            }
        }
        double longestTourLength;
        int idLongestTour = 0;
        for (k = 0; k < ants.nAnts; k++) {
            statistics.noSolutions++;
            longestTourLength = Double.MIN_VALUE;
            for (int i = 0; i < ants.ants[k].usedVehicles; i++) {
                step = ants.ants[k].tours.get(i).size();
                ants.ants[k].tours.get(i).add(step, -1);
                ants.ants[k].tourLengths.set(i, vrptw.computeTourLengthWithDepot(ants.ants[k].tours.get(i)));
                ants.ants[k].totalTourLength += ants.ants[k].tourLengths.get(i);
                if (longestTourLength < ants.ants[k].tourLengths.get(i)) {
                    longestTourLength = ants.ants[k].tourLengths.get(i);
                    idLongestTour = i;
                }
                if (ants.acsFlag)
                    ants.localAcsPheromoneUpdate(ants.ants[k], i);
            }
            ants.ants[k].longestTourLength = longestTourLength;
            ants.ants[k].indexLongestTour = idLongestTour;
            ants.ants[k].costObjectives[0] = ants.ants[k].totalTourLength;
            ants.ants[k].costObjectives[1] = ants.computeToursAmplitude(ants.ants[k]);
        }
        statistics.nTours += (ants.nAnts * ants.ants[0].usedVehicles); //each ant constructs a complete and closed tour
    }

    private void acquireLock() {
        if (semaphore != null) {
            try {
                semaphore.acquire();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void releaseLock() {
        if (semaphore != null) {
            semaphore.release();
        }
    }

    //initialize variables appropriately when starting a trial
    public void initTry(VRPTW vrptw) {
        timer.startTimers();
        statistics.timeUsed = timer.elapsedTime();
        statistics.timePassed = statistics.timeUsed;
        int noAvailableNodes;
        /* Initialize variables concerning statistics etc. */
        statistics.nTours = 1;
        statistics.iteration = 1;
        ants.bestSoFarAnt.totalTourLength = Double.MAX_VALUE;
        ants.restartBestAnt.totalTourLength = Double.MAX_VALUE;
        statistics.foundBest = 0;
        statistics.lambda = 0.05;
        // Initialize the Pheromone trails, only if ACS is used, Ants.pheromones have to be initialized differently
        if (!(ants.acsFlag)) {
            ants.trail0 = 1. / ((ants.rho) * nnTour(vrptw));
            /*
             * in the original papers on Ant System it is not exactly defined what the
             * initial value of the Ants.pheromones is. Here we set it to some
             * small constant, analogously as done in MAX-MIN Ant System.
             */
            ants.initPheromoneTrails(ants.trail0);
        }
        if (ants.acsFlag) {
            noAvailableNodes = vrptw.getIdAvailableRequests().size();
            //if no cities are available except the depot, set the pheromone levels to a static value
            if (noAvailableNodes == 0) {
                ants.trail0 = 1.0;
            } else {
                ants.trail0 = 1. / ((double) (noAvailableNodes + 1) * nnTour(vrptw));
            }
            ants.initPheromoneTrails(ants.trail0);
        }
    }

    //manage some statistical information about the trial, especially if a new best solution
    //(best-so-far) is found and adjust some parameters if a new best solution is found
    public void updateStatistics(VRPTW vrptw) {
        int iterationBestAnt;
        double scalingValue, scaledValue = 0.0;
        Object obj = new Object();
        double round1, round2;
        double tempNo = Math.pow(10, 10);
        iterationBestAnt = ants.findBest(); /* iteration_best_ant is a global variable */
        Ant a = ants.ants[iterationBestAnt];
        if (lsFlag) {
            //apply multiple route relocation and exchange iterated local search operators for the iteration best ant
            a = localSearch.relocateMultipleRouteIterated(ants, dynamicController, a, vrptw);
            a = localSearch.exchangeMultipleRouteIterated(ants, dynamicController, a, vrptw);
        }
        synchronized (obj) {
            round1 = Math.round(a.totalTourLength * tempNo) / tempNo;
            round2 = Math.round(ants.bestSoFarAnt.totalTourLength * tempNo) / tempNo;
            if ((a.usedVehicles < ants.bestSoFarAnt.usedVehicles) || ((a.usedVehicles == ants.bestSoFarAnt.usedVehicles) && (round1 < round2))
                    || ((round1 < round2) && (ants.bestSoFarAnt.totalTourLength == Double.MAX_VALUE))) {
                statistics.timeUsed = timer.elapsedTime();  //best solution found after timeUsed
                ants.copyFromTo(a, ants.bestSoFarAnt, vrptw);
                if (bsfListener != null) {
                    bsfListener.onBSFFound(ants.bestSoFarAnt);
                }
                scalingValue = dynamicController.getScalingValue();
                if (scalingValue != 0) {
                    scaledValue = ants.bestSoFarAnt.totalTourLength / scalingValue;
                }
                loggerOutput.log("Updated Best so far ant >> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scaledValue + ")");
            }
        }

    }

    //manage global Ants.pheromone deposit for Ant System
    public void asUpdate() {
        for (int k = 0; k < ants.nAnts; k++)
            ants.globalUpdatePheromone(ants.ants[k]);
    }

    //manage global Ants.pheromone deposit for Ant Colony System
    public void acsGlobalUpdate() {
        ants.globalAcsPheromoneUpdate(ants.bestSoFarAnt);
    }

    //manage global Ants.pheromone trail update for the ACO algorithms
    public void pheromoneTrailUpdate() {
        //Simulate the Ants.pheromone evaporation of all Ants.pheromones; this is not necessary for ACS
        if (ants.asFlag) {
            /* evaporate all Ants.pheromone trails */
            ants.evaporation();
        }
        /* Next, apply the Ants.pheromone deposit for the various ACO algorithms */
        if (ants.asFlag)
            asUpdate();
        else if (ants.acsFlag)
            acsGlobalUpdate();
    }

    //do the work of running the ant colony which tries to solve the DVRPTW instance using the available
    //(known so far) customer requests
    public void run() {
        while (isRunning) {
            //the thread was restarted (i.e. it was previously stopped and started again)
            if (threadRestarted) {
                //compute the new value for the initial pheromone trail based on the current best solution so far
                int noAvailableNodes = vrptw.getIdAvailableRequests().size();
                ants.trail0 = 1.0 / ((double) (noAvailableNodes + 1) * (double) ants.bestSoFarAnt.totalTourLength);
                //preserve a certain amount of the pheromones from the previous run of the ant colony
                ants.preservePheromones(vrptw.getIdAvailableRequests());
            }
            //do the optimization task (work)
            try {
                acquireLock();
                constructSolutions(vrptw);
                //increase evaluations counter
                statistics.noEvaluations++;
                counter++;
                updateStatistics(vrptw);
            } finally {
                releaseLock();
            }
            pheromoneTrailUpdate();
            //force the ant colony thread to stop its execution
            if (counter > 300) {
                isRunning = false;
            }
            if (statistics.isDiscreteTime) {
                counter = 0;
                break;
            }
        }
    }

    // generate a nearest neighbor tour and compute tour length using only the available nodes (nodes known so far)
    public double nnTour(VRPTW vrptw) {
        Ant ant = ants.createNewAnt(vrptw);
        int step, vehicle;
        double sum = 0, sum1 = 0, scaledValue = 0;
        ants.antEmptyMemory(ant, vrptw.getIdAvailableRequests());
        for (int i = 0; i < ant.usedVehicles; i++) {
            //place the ant on the depot city, which is the start city of each tour
            // -1 is a special marker for the deport city, so that it's not be confused with the rest of the cities
            // all the rest of the cities are represented by integer values > 0
            ant.tours.get(i).add(-1);
        }
        //there are still left available (known) cities to be visited
        while (ant.toVisit > 0) {
            vehicle = ant.usedVehicles - 1;
            ants.chooseClosestNext(ant, vehicle, vrptw, this);
        }
        int nrTours = ant.usedVehicles;
        for (int i = 0; i < nrTours; i++) {
            step = ant.tours.get(i).size();
            ant.tours.get(i).add(step, -1);
            ant.tourLengths.set(i, vrptw.computeTourLength(ant.tours.get(i)));
            sum1 += ant.tourLengths.get(i);
        }
        ant.totalTourLength = sum1;
        if (lsFlag) {
            ant = localSearch.relocateMultipleRouteIterated(ants, dynamicController, ant, vrptw);
            ant = localSearch.exchangeMultipleRouteIterated(ants, dynamicController, ant, vrptw);
            //compute new distances and update longest tour
            for (int l = 0; l < ant.usedVehicles; l++) {
                ant.tourLengths.set(l, vrptw.computeTourLength(ant.tours.get(l)));
                sum += ant.tourLengths.get(l);
            }
            ant.totalTourLength = sum;
        }
        double scalingValue = dynamicController.getScalingValue();
        if (scalingValue != 0) {
            scaledValue = ant.totalTourLength / scalingValue;
        }
        loggerOutput.log("\nInitial (nearest neighbour tour) total tour length: " + ant.totalTourLength + " (scalled value = " + scaledValue + "); Number of vehicles used: " + ant.usedVehicles);
        sum1 = ant.totalTourLength;
        //initialize best solution so far with this solution constructed by the nearest neighbour heuristic
        ants.copyFromTo(ant, ants.bestSoFarAnt, vrptw);
        ants.antEmptyMemory(ant, vrptw.getIdAvailableRequests());
        return sum1;
    }

    public void terminate() {
        isRunning = false;
    }

    public BSFListener getBsfListener() {
        return bsfListener;
    }

    public void setBsfListener(BSFListener bsfListener) {
        this.bsfListener = bsfListener;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }
}
