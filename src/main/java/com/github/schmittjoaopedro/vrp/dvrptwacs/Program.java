package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The controller is the central part of the algorithm, which reads the benchmark data,
 * initializes the data structures, builds an initial solution using nearest neighbor heuristic
 * and starts the ant colony, once the working day has started.
 * <p>
 * Original version: https://github.com/ralucanecula/DVRPTW_ACS
 */
public class Program {

    // Length of a working day in seconds.
    private int workingDay = 100;

    // Number of time slices.
    private int numTimeSlices = 50;

    // File name to be used for input data set.
    private String vrpInstanceName = "r103"; // r104

    private String rootDirectory;

    // Dynamic level, which gives the proportion of the dynamic requests (available time > 0) from the DVRPTW instance.
    private double dynamicLevel = 0.1; //0.0  //0.1  //0.5  //1.0

    // The scaling value is used to scale all time-related values.
    private double scalingValue;

    private int addedNodes;

    private VRPTW vrpInstance;

    // Optimization algorithm
    private VRPTW_ACS vrptwAcs;

    private InOut inOut;

    private Ants ants;

    private Utilities utilities;

    private Timer timer;

    private Controller controller;

    public Program(String rootDirectory, String vrpInstanceName, double dynamicLevel) {
        super();
        setRootDirectory(rootDirectory);
        setVrpInstanceName(vrpInstanceName);
        setDynamicLevel(dynamicLevel);
        setInOut(new InOut());
        setAnts(new Ants());
        setUtilities(new Utilities());
        setTimer(new Timer());
        setVrptwAcs(new VRPTW_ACS(true));
    }

    public void execute() {
        int numTrials = 1;
        long startTime, endTime;
        double sum, currentTime, newStartWindow, newEndWindow, newServiceTime, newAvailableTime;
        boolean threadStopped = false, isNewNodesAvailable, isNewNodesCommitted;
        ArrayList<Integer> newAvailableIdNodes;
        ArrayList<Integer> idKnownRequests;

        // Counter which stores the number of the current time slice that we are during the execution of the
        // algorithm, which simulates a working day.
        int currentTimeSlice = 1;

        // Keeps the last index/position in the array of dynamic requests sorted ascending by available time
        // of the recent request which became available in the last time slice.
        int countApriori;

        for (int trial = 0; trial < numTrials; trial++) {
            // Reads benchmark data; read the data from the input file.
            String dvrptwInstance = vrpInstanceName + "-" + dynamicLevel;
            String fileName = dvrptwInstance + ".txt";
            DataReader reader = new DataReader(rootDirectory, fileName);
            // Read the data from the file
            vrpInstance = reader.read();

            System.out.println("DVRPTW_ACS MinSum >> Solving dynamic VRPTW instance: " + dvrptwInstance);
            // Include in the counting also the depot, which is assumed to be apriori known.
            countApriori = vrpInstance.getIdAvailableRequests().size();
            System.out.println("No. of customers' requests (except the depot): " + vrpInstance.getN() +
                    ", among which " + countApriori + " are apriori known (available nodes excluding the depot) and " +
                    vrpInstance.getDynamicRequests().size() + " are dynamic requests");

            // Compute the scaling value with which we can scale all time-related values.
            // First record is assumed to defined the depot demand.
            Request depotReq = vrpInstance.getRequests().get(0);
            scalingValue = (double) workingDay / (depotReq.getEndWindow() - depotReq.getStartWindow());

            // Adjust distances between nodes (cities) according to this scale value
            inOut.initProgram(trial, vrpInstance, scalingValue, ants, utilities);

            // Adjust for each request, all the time related values according to the length of the working day we are simulating
            if (scalingValue != 0) {
                System.out.println("Scaling value = " + scalingValue);
                for (Request request : vrpInstance.getRequests()) {
                    newStartWindow = request.getStartWindow() * scalingValue;
                    request.setStartWindow(newStartWindow);
                    newEndWindow = request.getEndWindow() * scalingValue;
                    request.setEndWindow(newEndWindow);
                    newServiceTime = request.getServiceTime() * scalingValue;
                    request.setServiceTime(newServiceTime);
                    newAvailableTime = request.getAvailableTime() * scalingValue;
                    request.setAvailableTime(newAvailableTime);
                }
            }

            // Sorting dynamic requests in ascending order by their available time
            ArrayList<Request> dynamicRequests = vrpInstance.getDynamicRequests();
            Collections.sort(dynamicRequests);
            vrpInstance.setDynamicRequests(dynamicRequests);

            vrpInstance.computeNNLists(ants, utilities);
            setOptimizationAlgorithmParameters();

            controller.setIdLastAvailableNode(0);
            addedNodes = 0;
            inOut.setNoEvaluation(0);
            inOut.setNoSolutions(0);
            double lengthTimeSlice = (double) workingDay / (double) numTimeSlices;
            startTime = System.currentTimeMillis();

            // Start the ant colony optimization in another thread
            VRPTW_ACS_Thread worker = new VRPTW_ACS_Thread(threadStopped, vrptwAcs, vrpInstance, ants);
            worker.start();
            // Check periodically if the problem has changed and new nodes (customer requests) became available
            // or there are nodes from the best so far solution that must be marked as committed
            do {
                // Compute current time up to this point
                endTime = System.currentTimeMillis();
                currentTime = (endTime - startTime) / 1000.0;
                // Did a new time slice started?
                if (currentTime > currentTimeSlice * lengthTimeSlice) {
                    // Advance to next time slice
                    System.out.println("Trial " + (trial + 1) + "; Current time (seconds): " + currentTime + "; new time slice started at " + currentTimeSlice * lengthTimeSlice);
                    // Check if there are new nodes that became available in the last time slice
                    newAvailableIdNodes = controller.countNoAvailableNodes(dynamicRequests, currentTime);
                    // Mark the fact that new nodes (from the list of dynamic customer requests) are available
                    int countNodes = newAvailableIdNodes.size();
                    isNewNodesAvailable = countNodes > 0;
                    // Check if there are nodes that must be marked as committed in the tours of the best so far solution
                    isNewNodesCommitted = controller.checkNewCommittedNodes(ants, currentTimeSlice, lengthTimeSlice);
                    // Check if new nodes are available (known) or there are parts (nodes) that must be committed from the tours of the best so far solution
                    if (isNewNodesAvailable || isNewNodesCommitted) {
                        // Stop the execution of the ant colony thread
                        if (worker != null) {
                            worker.terminate();
                            // Wait for the thread to stop
                            try {
                                worker.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        threadStopped = true;
                        // If there are nodes to be committed
                        if (isNewNodesCommitted) {
                            // Commit necessary nodes after the ant colony execution is stopped
                            controller.commitNodes(ants, currentTimeSlice, lengthTimeSlice);
                        }
                    }
                    // If there are new available nodes, update the list of available/known nodes (customer requests)
                    if (isNewNodesAvailable) {
                        System.out.print(countNodes + " new nodes became available (known): ");
                        idKnownRequests = vrpInstance.getIdAvailableRequests();
                        for (int id : newAvailableIdNodes) {
                            idKnownRequests.add(id);
                            System.out.print((id + 1) + " ");
                        }
                        vrpInstance.setIdAvailableRequests(idKnownRequests);
                        System.out.println("\nNumber of total available (known) nodes (excluding the depot): " + idKnownRequests.size());
                        // Insert new available nodes in the best so far solution
                        ants.getBestSoFarAnt().setToVisit(countNodes);
                        addUnRoutedCustomers(0);
                        // If there are still remaining unvisited cities from the ones that are available
                        // insert an empty tour and add cities in it following nearest-neighbour heuristic
                        while (ants.getBestSoFarAnt().getToVisit() > 0) {
                            ants.getBestSoFarAnt().setUsedVehicles(ants.getBestSoFarAnt().getUsedVehicles() + 1);
                            int indexTour = ants.getBestSoFarAnt().getUsedVehicles() - 1;
                            ants.getBestSoFarAnt().getTours().add(indexTour, new ArrayList<Integer>());
                            ants.getBestSoFarAnt().getTours().get(indexTour).add(-1);
                            ants.getBestSoFarAnt().getTourLengths().add(indexTour, 0.0);
                            ants.getBestSoFarAnt().getCurrentQuantity().add(indexTour, 0.0);
                            ants.getBestSoFarAnt().getCurrentTime().add(indexTour, 0.0);
                            // Try to add as many unvisited cities/nodes as possible in this newly created tour following the nearest neighbour heuristic
                            vrptwAcs.chooseClosestNN(ants.getBestSoFarAnt(), indexTour, vrpInstance);
                            // Try to insert remaining cities using insertion heuristic
                            if (ants.getBestSoFarAnt().getToVisit() > 0) {
                                addUnRoutedCustomers(indexTour);
                            }
                            // Add the depot again to end this tour
                            ants.getBestSoFarAnt().getTours().get(indexTour).add(-1);
                        }
                        sum = 0.0;
                        for (int i = 0; i < ants.getBestSoFarAnt().getUsedVehicles(); i++) {
                            ants.getBestSoFarAnt().getTourLengths().set(i, vrpInstance.computeTourLengthDepotOnlyInFirstPosition(ants.getBestSoFarAnt().getTours().get(i)));
                            sum += ants.getBestSoFarAnt().getTourLengths().get(i);
                        }
                        ants.getBestSoFarAnt().setTotalTourLength(sum);
                        double scaledValue = 0.0;
                        if (scalingValue != 0) {
                            scaledValue = ants.getBestSoFarAnt().getTotalTourLength() / scalingValue;
                        }
                        System.out.println("Best ant after inserting the new available nodes>> No. of used vehicles=" + ants.getBestSoFarAnt().getUsedVehicles() +
                                " total tours length=" + ants.getBestSoFarAnt().getTotalTourLength() + " (scalled value = " + scaledValue + ")");
                    }
                    currentTimeSlice++;
                }

                // Restart the colony thread
                if (threadStopped) {
                    // Restart the ant colony thread
                    worker = new VRPTW_ACS_Thread(threadStopped, vrptwAcs, vrpInstance, ants);
                    worker.start();
                    threadStopped = false;
                }
                if (currentTime >= workingDay) {
                    break;
                }
            } while (true);
            // Working day is over, stop the worker thread
            if (worker != null) {
                worker.terminate();
                // Wait for the thread to stop
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            double scaledValue = 0.0;
            if (scalingValue != 0) {
                scaledValue = ants.getBestSoFarAnt().getTotalTourLength() / scalingValue;
            }
            System.out.println("Final best solution >> No. of used vehicles=" + ants.getBestSoFarAnt().getUsedVehicles() +
                    " total tours length=" + ants.getBestSoFarAnt().getTotalTourLength() + " (scalled value = " + scaledValue + ")");
            for (int i = 0; i < ants.getBestSoFarAnt().getUsedVehicles(); i++) {
                int tourLength = ants.getBestSoFarAnt().getTours().get(i).size();
                for (int j = 0; j < tourLength; j++) {
                    int city = ants.getBestSoFarAnt().getTours().get(i).get(j);
                    city = city + 1;  // So as to correspond to the city indexes from the VRPTW input file
                    System.out.print(city + " ");
                }
                System.out.println();
            }
            System.out.println("Total number of evaluations: " + inOut.getNoEvaluation());
            System.out.println("Total number of feasible solutions: " + inOut.getNoSolutions());
            boolean isValid = utilities.checkFeasibility(ants.getBestSoFarAnt(), vrpInstance, controller, true);
            if (isValid) {
                System.out.println("The final solution is valid (feasible)..");
            } else {
                System.out.println("The final solution is not valid (feasible)..");
            }
        }
    }

    private void addUnRoutedCustomers(int indexTour) {
        // Determine nodes that are not visited yet in the current ant's solution
        ArrayList<Integer> unRoutedList = controller.unRoutedCustomers(ants.getBestSoFarAnt(), vrpInstance);
        // Skip over committed (defined) nodes when performing insertion heuristic
        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.getBestSoFarAnt().getUsedVehicles(); index++) {
            int lastPos = Controller.getLastCommittedPos(index, ants);
            lastCommittedIndexes.add(lastPos);
        }
        InsertionHeuristic.insertUnRoutedCustomers(ants.getBestSoFarAnt(), vrpInstance, unRoutedList, indexTour, lastCommittedIndexes);
    }

    public void setOptimizationAlgorithmParameters() {
        // Init timer
        timer.startTimers();
        inOut.setTimeUsed(timer.elapsedTime());
        inOut.setTimePassed(inOut.getTimeUsed());

        // Initialize variables concerning statistics etc.
        inOut.setNumTours(1);
        inOut.setIteration(1);
        ants.getBestSoFarAnt().setTotalTourLength(Double.MAX_VALUE);
        ants.getRestartBestAnt().setTotalTourLength(Double.MAX_VALUE);
        inOut.setFoundBest(0);
        inOut.setLambda(0.05);

        // Initialize the Pheromone trails
        ants.setPheromone(new double[vrpInstance.getN() + 1][vrpInstance.getN() + 1]);
        int numAvailableNodes = vrpInstance.getIdAvailableRequests().size();
        // If no cities are available except the depot, set the pheromone levels to a static value
        if (numAvailableNodes == 0) {
            ants.setTrail0(1.0);
        } else {
            ants.setTrail0(1.0 / ((double) (numAvailableNodes + 1) * vrptwAcs.getNNTourCost(ants, vrpInstance, scalingValue)));
        }
        ants.initPheromoneTrails(ants.getTrail0());

    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public double getScalingValue() {
        return scalingValue;
    }

    public void setScalingValue(double scalingValue) {
        this.scalingValue = scalingValue;
    }

    public int getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(int workingDay) {
        this.workingDay = workingDay;
    }

    public int getNumTimeSlices() {
        return numTimeSlices;
    }

    public void setNumTimeSlices(int numTimeSlices) {
        this.numTimeSlices = numTimeSlices;
    }

    public String getVrpInstanceName() {
        return vrpInstanceName;
    }

    public void setVrpInstanceName(String vrpInstanceName) {
        this.vrpInstanceName = vrpInstanceName;
    }

    public double getDynamicLevel() {
        return dynamicLevel;
    }

    public void setDynamicLevel(double dynamicLevel) {
        this.dynamicLevel = dynamicLevel;
    }

    public int getAddedNodes() {
        return addedNodes;
    }

    public void setAddedNodes(int addedNodes) {
        this.addedNodes = addedNodes;
    }

    public InOut getInOut() {
        return inOut;
    }

    public void setInOut(InOut inOut) {
        this.inOut = inOut;
    }

    public Ants getAnts() {
        return ants;
    }

    public void setAnts(Ants ants) {
        this.ants = ants;
    }

    public Utilities getUtilities() {
        return utilities;
    }

    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    public VRPTW getVrpInstance() {
        return vrpInstance;
    }

    public void setVrpInstance(VRPTW vrpInstance) {
        this.vrpInstance = vrpInstance;
    }

    public VRPTW_ACS getVrptwAcs() {
        return vrptwAcs;
    }

    public void setVrptwAcs(VRPTW_ACS vrptwAcs) {
        this.vrptwAcs = vrptwAcs;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
