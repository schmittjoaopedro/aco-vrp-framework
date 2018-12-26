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

public class Solver implements ModelReceiver, TickListener, BestSoFarListener, DeliveryListener {

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

    public DistanceType distanceType = DistanceType.EUC_2D;

    private InsertionHeuristic insertionHeuristic;

    private LoggerOutput loggerOutput;

    private File problemFile;

    private VRPTW_ACS worker;

    private Semaphore semaphore;

    boolean threadStopped = false;

    /* Integration attributes */

    private Set<Parcel> knowParcels;

    private Set<Parcel> deliveredParcels = new HashSet<>();

    private int noIterationsByTimeSlice = 10;

    public Solver(Semaphore semaphore, File problemFile, int seed, int timeSlices, int workingDay) {
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
        this.semaphore = semaphore;
    }

    @Override
    public void registerModelProvider(ModelProvider mp) {
        pdpModel = mp.getModel(PDPModel.class);
        roadModel = mp.getModel(RoadModel.class);
        initSolver();
    }

    @Override
    public void tick(TimeLapse timeLapse) {
        knowParcels = getKnownParcels();
        boolean hasWorkToDo = knowParcels.size() != deliveredParcels.size();
        try {
            acquireLock();
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
                    onBestSoFarFound(ants.bestSoFarAnt);
                    loggerOutput.log("Best ant after inserting the new available nodes>> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
                }
            }
            if (threadStopped || !worker.isRunning()) {
                //restart the ant colony thread
                worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
                worker.setBestSoFarListener(this);
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
        // Do nothing
    }

    private void stopSolver() {
        //stop the execution of the ant colony thread
        worker.terminate();
        threadStopped = true;
    }

    @Override
    public void onParcelDelivered(Parcel parcel) {
        int parcelId = ((IdPoint) parcel.getDeliveryLocation()).id;
        dynamicController.committedNodes[parcelId - 1] = true;
    }

    private void acquireLock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void releaseLock() {
        semaphore.release();
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
        Defaults.initProgram(vrptw, dynamicController, distanceType, ants, statistics, loggerOutput, antSystem);
        // Init try
        vrptwAcs = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        vrptwAcs.initTry(vrptw);
        knowParcels = getKnownParcels();
        onBestSoFarFound(ants.bestSoFarAnt);
        DebuggerUtils.compareRoutes(roadModel, ants);
        //counter which stores the number of the current time slice that we are during the execution of the algorithm,
        //which simulates a working day
        dynamicController.idLastAvailableNode = 0;
        statistics.noEvaluations = 0;
        statistics.noSolutions = 0;
        //start the ant colony
        worker = new VRPTW_ACS(threadStopped, vrptw, dynamicController, ants, statistics, timer, loggerOutput);
        worker.setBestSoFarListener(this);
        worker.setNoMaxIterations(noIterationsByTimeSlice);
        worker.run();
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
    public void onBestSoFarFound(Ant bestSoFar) {
        Map<Integer, Parcel> parcelMap = getKnownParcels().stream().collect(Collectors.toMap(parcel -> ((IdPoint) parcel.getDeliveryLocation()).id, Function.identity()));
        List<Salesman> salesmen = roadModel.getObjectsOfType(Salesman.class).stream().collect(Collectors.toList());
        salesmen.sort(Comparator.comparing(Salesman::getId));
        for (Salesman salesman : salesmen) {
            salesman.getRoute().clear();
            salesman.setBeginService(bestSoFar.beginService);
            salesman.setActive(false);
        }
        for (int i = 0; i < bestSoFar.tours.size(); i++) {
            List<Integer> tour = bestSoFar.tours.get(i);
            salesmen.get(i).setActive(true);
            for (int j = 1; j < tour.size() - 1; j++) {
                salesmen.get(i).getRoute().add(parcelMap.get(tour.get(j) + 1));
            }
        }
    }

    public void finishStatistics() {
        Defaults.finishLogs(dynamicController, loggerOutput, ants, vrptwAcs, statistics, vrptw);
    }

    public LoggerOutput getLoggerOutput() {
        return loggerOutput;
    }
}
