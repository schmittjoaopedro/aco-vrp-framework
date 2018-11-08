package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.Collections;

/**
 * the Solver is the central part of the algorithm, which reads the benchmark data,
 * initializes the data structures, builds an initial solution using nearest neighbor heuristic
 * and starts the ant colony, once the working day has started.
 */
public class Solver {

    //file name to be used for input data set
    private String vrpInstance;

    private DynamicController dynamicController;

    private String antSystem;

    private String rootDirectory;

    private Utilities utilities;

    private Timer timer;

    private Statistics statistics;

    private Ants ants;

    private VRPTW_ACS vrptwAcs;

    private VRPTW vrptw;

    public DistanceType distanceType;

    private InsertionHeuristic insertionHeuristic;

    private LoggerOutput loggerOutput;

    public Solver(String antSystem, String rootDirectory, String instanceName, double dynamicLevel, int seed, boolean isDiscreteTime, boolean printLogger) {
        this.vrpInstance = instanceName;
        this.rootDirectory = rootDirectory;
        this.antSystem = antSystem;
        this.loggerOutput = new LoggerOutput();
        this.loggerOutput.setPrint(printLogger);
        this.dynamicController = new DynamicController(this.loggerOutput);
        this.dynamicController.dynamicLevel = dynamicLevel;
        this.utilities = new Utilities();
        this.utilities.setSeed(seed);
        this.statistics = new Statistics(isDiscreteTime);
        this.insertionHeuristic = new InsertionHeuristic(this.loggerOutput);
        this.ants = new Ants(this.dynamicController, this.utilities, this.insertionHeuristic, this.loggerOutput);
        this.timer = new Timer(this.statistics);
    }

    public void execute() {
        long startTime, endTime;
        double currentTime;
        boolean threadStopped = false, isNewNodesAvailable, isNewNodesCommitted;
        //keeps the last index/position in the array of dynamic requests sorted ascending by available time
        //of the recent request which became available in the last time slice
        int lastPos;
        ArrayList<Integer> newAvailableIdNodes;
        ArrayList<Integer> idKnownRequests;
        ArrayList<Integer> lastCommittedIndexes;
        double sum;
        //reads benchmark data; read the data from the input file
        String dvrptwInstance = vrpInstance + "-" + dynamicController.dynamicLevel;
        String fileName = dvrptwInstance + ".txt";
        DataReader reader = new DataReader(this.rootDirectory, fileName);
        //read the data from the file
        vrptw = reader.read(utilities);
        loggerOutput.log("DVRPTW_ACS MinSum >> Solving dynamic VRPTW instance: " + dvrptwInstance);
        //include in the counting also the depot, which is assumed to be a-priori known
        int countAPriori = vrptw.getIdAvailableRequests().size();
        loggerOutput.log("No. of customers' requests (except the depot): " + vrptw.n + ", among which " + countAPriori +
                " are a-priori known (available nodes excluding the depot) and " + vrptw.getDynamicRequests().size() +
                " are dynamic requests");
        //calculate scale value
        dynamicController.calculateScaleValue(vrptw);
        initProgram();
        //Calculate scale request values
        dynamicController.scaleRequestTimes(vrptw);
        //sorting dynamic requests in ascending order by their available time
        ArrayList<Request> dynamicRequests = vrptw.getDynamicRequests();
        Collections.sort(dynamicRequests);
        vrptw.setDynamicRequests(dynamicRequests);
        //compute nearest neighborhood lists
        int[][][] result = vrptw.computeNnLists(ants);
        vrptw.instance.nnList = result[0];
        vrptw.instance.nnListAll = result[1];
        this.vrptwAcs = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        vrptwAcs.initTry(vrptw);
        utilities.plotResult(vrptw, ants.bestSoFarAnt, 1800, 900);
        //counter which stores the number of the current time slice that we are during the execution of the algorithm,
        //which simulates a working day
        int currentTimeSlice = 1;
        dynamicController.idLastAvailableNode = 0;
        statistics.noEvaluations = 0;
        statistics.noSolutions = 0;
        double lengthTimeSlice = (double) dynamicController.workingDay / (double) dynamicController.noTimeSlices;
        startTime = timer.getCurrentTime();
        //start the ant colony
        VRPTW_ACS worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        Thread thread = null;
        if (!statistics.isDiscreteTime) {
            thread = new Thread(worker);
            thread.start();
        }
        //check periodically if the problem has changed and new nodes (customer requests) became available
        //or there are nodes from the best so far solution that must be marked as committed
        do {
            //compute current time up to this point
            statistics.currentTimeSlice = currentTimeSlice;
            endTime = timer.getCurrentTime();
            currentTime = (endTime - startTime) / 1000.0;
            //did a new time slice started?
            if (currentTime > currentTimeSlice * lengthTimeSlice) {
                //advance to next time slice
                loggerOutput.log("Trial 1; Current time (seconds): " + currentTime + "; new time slice started at " + currentTimeSlice * lengthTimeSlice);
                //check if there are new nodes that became available in the last time slice
                newAvailableIdNodes = dynamicController.countNoAvailableNodes(dynamicRequests, currentTime);
                //mark the fact that new nodes (from the list of dynamic customer requests) are available
                int countNodes = newAvailableIdNodes.size();
                if (countNodes > 0) {
                    isNewNodesAvailable = true;
                } else {
                    isNewNodesAvailable = false;
                }
                //check if there are nodes that must be marked as committed in the tours of the best so far solution
                isNewNodesCommitted = dynamicController.checkNewCommittedNodes(ants.bestSoFarAnt, currentTimeSlice, lengthTimeSlice);
                //check if new nodes are available (known) or there are parts (nodes) that must be committed from the tours of the best so far solution
                if (isNewNodesAvailable || isNewNodesCommitted) {
                    //stop the execution of the ant colony thread
                    worker.terminate();
                    if (thread != null) {
                        //wait for the thread to stop
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    threadStopped = true;
                    //if there are nodes to be committed
                    if (isNewNodesCommitted) {
                        //commit necessary nodes after the ant colony execution is stopped
                        dynamicController.commitNodes(ants.bestSoFarAnt, currentTimeSlice, lengthTimeSlice);
                    }
                }
                //if there are new available nodes, update the list of available/known nodes (customer requests)
                if (isNewNodesAvailable) {
                    String knowNodesMsg = countNodes + " new nodes became available (known): ";
                    idKnownRequests = vrptw.getIdAvailableRequests();
                    for (int id : newAvailableIdNodes) {
                        idKnownRequests.add(id);
                        knowNodesMsg += (id + 1) + " ";
                    }
                    loggerOutput.log(knowNodesMsg);
                    vrptw.setIdAvailableRequests(idKnownRequests);
                    loggerOutput.log("Number of total available (known) nodes (excluding the depot): " + idKnownRequests.size());
                    //insert new available nodes in the best so far solution
                    ants.bestSoFarAnt.toVisit = countNodes;
                    //determine nodes that are not visited yet in the current ant's solution
                    ArrayList<Integer> unroutedList = ants.getNonRoutedCustomers(ants.bestSoFarAnt, vrptw.getIdAvailableRequests());
                    //skip over committed (defined) nodes when performing insertion heuristic
                    lastCommittedIndexes = new ArrayList<>();
                    for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
                        lastPos = dynamicController.getLastCommittedPos(ants.bestSoFarAnt, index);
                        lastCommittedIndexes.add(lastPos);
                    }
                    insertionHeuristic.insertUnroutedCustomers(ants.bestSoFarAnt, vrptw, unroutedList, 0, lastCommittedIndexes);
                    //if there are still remaining unvisited cities from the ones that are available
                    //insert an empty tour and add cities in it following nearest-neighbour heuristic
                    int indexTour;
                    while (ants.bestSoFarAnt.toVisit > 0) {
                        ants.bestSoFarAnt.usedVehicles++;
                        indexTour = ants.bestSoFarAnt.usedVehicles - 1;
                        ants.bestSoFarAnt.tours.add(indexTour, new ArrayList<Integer>());
                        ants.bestSoFarAnt.tours.get(indexTour).add(-1);
                        ants.bestSoFarAnt.tourLengths.add(indexTour, 0.0);
                        ants.bestSoFarAnt.currentQuantity.add(indexTour, 0.0);
                        ants.bestSoFarAnt.currentTime.add(indexTour, 0.0);
                        //try to add as many unvisited cities/nodes as possible in this newly created tour
                        //following the nearest neighbour heuristic
                        ants.chooseClosestNn(ants.bestSoFarAnt, indexTour, vrptw, vrptwAcs);
                        //try to insert remaining cities using insertion heuristic
                        if (ants.bestSoFarAnt.toVisit > 0) {
                            //determine nodes that are not visited yet in the current ant's solution
                            unroutedList = ants.getNonRoutedCustomers(ants.bestSoFarAnt, vrptw.getIdAvailableRequests());
                            //skip over committed (defined) nodes when performing insertion heuristic
                            lastCommittedIndexes = new ArrayList<Integer>();
                            for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
                                lastPos = dynamicController.getLastCommittedPos(ants.bestSoFarAnt, index);
                                lastCommittedIndexes.add(lastPos);
                            }
                            insertionHeuristic.insertUnroutedCustomers(ants.bestSoFarAnt, vrptw, unroutedList, indexTour, lastCommittedIndexes);
                        }
                        //add the depot again to end this tour
                        ants.bestSoFarAnt.tours.get(indexTour).add(-1);
                    }
                    sum = 0.0;
                    for (int i = 0; i < ants.bestSoFarAnt.usedVehicles; i++) {
                        ants.bestSoFarAnt.tourLengths.set(i, vrptw.computeTourLengthWithDepot(ants.bestSoFarAnt.tours.get(i)));
                        sum += ants.bestSoFarAnt.tourLengths.get(i);
                    }
                    ants.bestSoFarAnt.totalTourLength = sum;
                    double scalledValue = 0.0;
                    if (dynamicController.scalingValue != 0) {
                        scalledValue = ants.bestSoFarAnt.totalTourLength / dynamicController.scalingValue;
                    }
                    loggerOutput.log("Best ant after inserting the new available nodes>> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
                }
                currentTimeSlice++;
            }
            //restart the colony thread
            if (threadStopped) {
                //restart the ant colony thread
                worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
                if (!statistics.isDiscreteTime) {
                    thread = new Thread(worker);
                    thread.start();
                }
                threadStopped = false;
            }
            if (statistics.isDiscreteTime) {
                worker.run();
            }
            if (currentTime >= dynamicController.workingDay) {
                break;
            }
        } while (true);
        //working day is over
        //stop the worker thread
        if (thread != null) {
            worker.terminate();
            //wait for the thread to stop
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //end of the working day; try final improvements of the best so far solution
        //by applying iterated relocate multiple route and exchange multiple route local search operators
        double scalledValue = 0.0;
        if (dynamicController.scalingValue != 0) {
            scalledValue = ants.bestSoFarAnt.totalTourLength / dynamicController.scalingValue;
        }
        loggerOutput.log("Final best solution >> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
        String finalRouteStr = "";
        for (int i = 0; i < ants.bestSoFarAnt.usedVehicles; i++) {
            int tourLength = ants.bestSoFarAnt.tours.get(i).size();
            finalRouteStr = "";
            for (int j = 0; j < tourLength; j++) {
                int city = ants.bestSoFarAnt.tours.get(i).get(j);
                city = city + 1;  //so as to correspond to the city indexes from the VRPTW input file
                finalRouteStr += (city + " ");
            }
            loggerOutput.log(finalRouteStr);
        }
        loggerOutput.log("Total number of evaluations: " + statistics.noEvaluations);
        loggerOutput.log("Total number of feasible solutions: " + statistics.noSolutions);
        boolean isValid = vrptwAcs.checkFeasibility(ants.bestSoFarAnt, vrptw, true);
        if (isValid) {
            loggerOutput.log("The final solution is valid (feasible)..");
            utilities.plotResult(vrptw, ants.bestSoFarAnt, 1800, 900);
        } else {
            loggerOutput.log("The final solution is not valid (feasible)..");
        }
    }

    //initialize the program
    public void initProgram() {
        setDefaultParameters();
        parseCommandline();
        //compute distance matrix between cities and allocate ants
        if (ants.nAnts < 0)
            ants.nAnts = vrptw.n;
        // Adjust distances between nodes (cities) according to this scale value
        vrptw.instance.distance = vrptw.computeDistances(dynamicController.scalingValue, distanceType);
        ants.allocateAnts(vrptw);
        ants.pheromone = new double[vrptw.n + 1][vrptw.n + 1];
        dynamicController.allocateStructures(vrptw);
    }

    public void parseCommandline() {
        // Choice of ONE algorithm
        int algorithmCount = 0;
        if (antSystem.equals("u")) {
            algorithmCount++;
        }
        if (antSystem.equals("z")) {
            algorithmCount++;
        }
        if (algorithmCount > 1) {
            System.err.println("Error: More than one ACO algorithm enabled in the command line.");
            System.exit(1);
        } else if (algorithmCount == 1) {
            ants.asFlag = false;
            ants.acsFlag = false;
        }
        if (antSystem.equals("u")) {
            ants.asFlag = true;
            setDefaultAsParameters(ants);
            loggerOutput.log("\nRun basic Ant System #1");
        }
        if (antSystem.equals("z")) {
            ants.acsFlag = true;
            setDefaultAcsParameters(ants);
            loggerOutput.log("\nRun Ant Colony System #1");
        }
    }

    //set default parameter settings
    public void setDefaultParameters() {
        /* number of ants and number of nearest neighbors in tour construction */
        ants.nAnts = 25;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
        ants.pheromonePreservation = 0.3;
        statistics.max_tries = 10;
        statistics.max_tours = 10 * 20;
        //10 seconds allowed for running time; it is used in the termination condition of ACO
        statistics.max_time = 100;
        //maximum number of allowed iterations until the ACO algorithm stops
        statistics.max_iterations = 3000;
        statistics.optimal = 1;
        statistics.branch_fac = 1.00001;
        ants.uGb = Integer.MAX_VALUE;
        ants.acsFlag = false;
        ants.asFlag = false;
        distanceType = DistanceType.EUC_2D;
    }

    public void setDefaultAsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = -1;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
    }

    public void setDefaultAcsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = 10;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 1.0;
        ants.rho = 0.9;
        //used in local pheromone update
        ants.local_rho = 0.9;
        ants.q0 = 0.9;
    }

    public LoggerOutput getLoggerOutput() {
        return loggerOutput;
    }
}
