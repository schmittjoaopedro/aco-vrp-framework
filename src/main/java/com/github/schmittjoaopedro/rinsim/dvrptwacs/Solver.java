package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.core.model.ModelProvider;
import com.github.rinde.rinsim.core.model.ModelReceiver;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TickListener;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.schmittjoaopedro.rinsim.IdPoint;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.vrp.dvrptwacs.*;
import com.github.schmittjoaopedro.vrp.dvrptwacs.Timer;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solver implements ModelReceiver, TickListener, BSFListener {

    /* RimSim attributes */

    protected PDPModel pdpModel;

    protected RoadModel roadModel;


    /* VRP Solver attributes */

    private DynamicController dynamicController;

    private String antSystem;

    private Utilities utilities;

    private Timer timer;

    private Statistics statistics;

    private Ants ants;

    private VRPTW_ACS vrptwAcs;

    private VRPTW vrptw;

    public DistanceType distanceType;

    private InsertionHeuristic insertionHeuristic;

    private LoggerOutput loggerOutput;

    private File problemFile;

    private VRPTW_ACS worker;

    //private Thread thread = null;

    private Semaphore semaphore;

    boolean threadStopped = false;

    /* Integration attributes */

    private Set<Parcel> knowParcels;

    private Set<Parcel> deliveredParcels = new HashSet<>();

    private int noIterationsByTimeSlice = 10;

    public Solver(File problemFile, int seed, int timeSlices, int workingDay) {
        this.problemFile = problemFile;
        this.antSystem = "z"; // Use ACS (Ant Colony System)
        this.loggerOutput = new LoggerOutput();
        this.loggerOutput.setPrint(false);
        this.dynamicController = new DynamicController(this.loggerOutput, workingDay, timeSlices);
        this.utilities = new Utilities();
        this.utilities.setSeed(seed);
        this.statistics = new Statistics();
        this.insertionHeuristic = new InsertionHeuristic(this.loggerOutput);
        this.ants = new Ants(this.dynamicController, this.utilities, this.insertionHeuristic, this.loggerOutput);
        this.timer = new Timer(this.statistics);
        this.semaphore = new Semaphore(1, true);
    }

    @Override
    public void registerModelProvider(ModelProvider mp) {
        pdpModel = mp.getModel(PDPModel.class);
        roadModel = mp.getModel(RoadModel.class);
        roadModel.getObjectsOfType(Salesman.class).forEach(salesman -> salesman.setSolver(this));
        initSolver();
    }

    @Override
    public void tick(TimeLapse timeLapse) {
        knowParcels = getKnownParcels();
        boolean hasWorkToDo = knowParcels.size() != deliveredParcels.size();
        try {
            acquireLock();
            //waitSolverTimeSliceFinish();
            if (timeLapse.hasTimeLeft() && hasWorkToDo) {
                Set<Parcel> tickDeliveredParcels = getPickedParcels();
                tickDeliveredParcels.removeAll(deliveredParcels);
                deliveredParcels.addAll(tickDeliveredParcels);
                int countNodes = knowParcels.size() - vrptw.getIdAvailableRequests().size();
                boolean isNewNodesAvailable = countNodes > 0;
                //stop solver
                if (isNewNodesAvailable || !worker.isRunning()) {
                    stopSolver();
                }
                //if there are new available nodes, update the list of available/known nodes (customer requests)
                if (isNewNodesAvailable) {
                    String knowNodesMsg = countNodes + " new nodes became available (known): ";
                    ArrayList<Integer> idKnownRequests = vrptw.getIdAvailableRequests();
                    ArrayList<Integer> newAvailableIdNodes = new ArrayList<>(knowParcels.stream()
                            .filter(parcel -> !idKnownRequests.contains(((IdPoint) parcel.getDeliveryLocation()).id - 1))
                            .map(parcel -> ((IdPoint) parcel.getDeliveryLocation()).id - 1)
                            .collect(Collectors.toList()));
                    newAvailableIdNodes.sort(Comparator.naturalOrder());
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
                    ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
                    int lastPos;
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
                            lastCommittedIndexes = new ArrayList<>();
                            for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
                                lastPos = dynamicController.getLastCommittedPos(ants.bestSoFarAnt, index);
                                lastCommittedIndexes.add(lastPos);
                            }
                            insertionHeuristic.insertUnroutedCustomers(ants.bestSoFarAnt, vrptw, unroutedList, indexTour, lastCommittedIndexes);
                        }
                        //add the depot again to end this tour
                        ants.bestSoFarAnt.tours.get(indexTour).add(-1);
                    }
                    double sum = 0.0;
                    for (int i = 0; i < ants.bestSoFarAnt.usedVehicles; i++) {
                        ants.bestSoFarAnt.tourLengths.set(i, vrptw.computeTourLengthWithDepot(ants.bestSoFarAnt.tours.get(i)));
                        sum += ants.bestSoFarAnt.tourLengths.get(i);
                    }
                    ants.bestSoFarAnt.totalTourLength = sum;
                    double scalledValue = 0.0;
                    if (dynamicController.scalingValue != 0) {
                        scalledValue = ants.bestSoFarAnt.totalTourLength / dynamicController.scalingValue;
                    }
                    onBSFFound(ants.bestSoFarAnt);
                    loggerOutput.log("Best ant after inserting the new available nodes>> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
                }
            }
            //restart the colony thread
            if (threadStopped || !worker.isRunning()) {
                //restart the ant colony thread
                worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
                //worker.setSemaphore(semaphore);
                worker.setBsfListener(this);
                worker.setNoMaxIterations(noIterationsByTimeSlice);
                worker.run();
                threadStopped = false;
            } else {
                stopSolver();
            }
            //System.out.println(timeLapse);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            releaseLock();
        }
        // Validate routes constructed
        DebuggerUtils.compareRoutes(roadModel, ants);
        DebuggerUtils.validateAllRoutesAndDeliveries(roadModel, pdpModel, knowParcels, false);
    }

    @Override
    public void afterTick(TimeLapse timeLapse) {

    }

    private void stopSolver() {
        //stop the execution of the ant colony thread
        worker.terminate();
//        if (thread != null) {
        //wait for the thread to stop
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        threadStopped = true;
    }

    private void waitSolverTimeSliceFinish() {
        while (worker.isRunning()) {
            try {
                Thread.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void commitParcel(Parcel parcel) {
        int parcelId = ((IdPoint) parcel.getDeliveryLocation()).id;
        dynamicController.committedNodes[parcelId - 1] = true;
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

    private void initSolver() {
        //keeps the last index/position in the array of dynamic requests sorted ascending by available time
        //of the recent request which became available in the last time slice
        //reads benchmark data; read the data from the input file
        DataReader reader = new DataReader(this.problemFile.getParentFile().getPath(), this.problemFile.getName());
        //read the data from the file
        vrptw = reader.read(utilities);
        loggerOutput.log("DVRPTW_ACS MinSum >> Solving dynamic VRPTW instance: " + this.problemFile.getName().replace(".txt", StringUtils.EMPTY));
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
        //compute nearest neighborhood lists
        int[][][] result = vrptw.computeNnLists(ants);
        vrptw.instance.nnList = result[0];
        vrptw.instance.nnListAll = result[1];
        vrptwAcs = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        //vrptwAcs.setSemaphore(semaphore);
        vrptwAcs.initTry(vrptw);
        knowParcels = getKnownParcels();
        onBSFFound(ants.bestSoFarAnt);
        DebuggerUtils.compareRoutes(roadModel, ants);
        //counter which stores the number of the current time slice that we are during the execution of the algorithm,
        //which simulates a working day
        dynamicController.idLastAvailableNode = 0;
        statistics.noEvaluations = 0;
        statistics.noSolutions = 0;
        //start the ant colony
        worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        //worker.setSemaphore(semaphore);
        worker.setBsfListener(this);
        worker.setNoMaxIterations(noIterationsByTimeSlice);
        worker.run();
    }

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

    public Set<Parcel> getKnownParcels() {
        Set<Parcel> parcels = new HashSet<>();
        parcels.addAll(pdpModel.getParcels(PDPModel.ParcelState.values()));
        return parcels;
    }


    private Set<Parcel> getPickedParcels() {
        return knowParcels.stream().filter(parcel -> pdpModel.getParcelState(parcel).isPickedUp()).collect(Collectors.toSet());
    }

    @Override
    public void onBSFFound(Ant bestSoFar) {
        Map<Integer, Parcel> parcelMap = getKnownParcels().stream().collect(Collectors.toMap(parcel -> ((IdPoint) parcel.getDeliveryLocation()).id, Function.identity()));
        List<Salesman> salesmen = roadModel.getObjectsOfType(Salesman.class).stream().collect(Collectors.toList());
        salesmen.sort(Comparator.comparing(Salesman::getId));
        for (Salesman salesman : salesmen) {
            salesman.getRoute().clear();
            salesman.setBeginService(bestSoFar.beginService);
            salesman.setSemaphore(semaphore);
            salesman.setActivated(false);
        }
        for (int i = 0; i < bestSoFar.tours.size(); i++) {
            List<Integer> tour = bestSoFar.tours.get(i);
            salesmen.get(i).setActivated(true);
            for (int j = 1; j < tour.size() - 1; j++) {
                salesmen.get(i).getRoute().add(parcelMap.get(tour.get(j) + 1));
            }
        }
    }

    public void finishStatistics() {
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
            //utilities.plotResult(vrptw, ants.bestSoFarAnt, 1800, 900);
        } else {
            loggerOutput.log("The final solution is not valid (feasible)..");
        }
    }

    public LoggerOutput getLoggerOutput() {
        return loggerOutput;
    }
}
