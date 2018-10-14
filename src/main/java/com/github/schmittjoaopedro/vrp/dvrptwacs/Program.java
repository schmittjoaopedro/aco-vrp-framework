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

    private int idLastAvailableNode;

    private int addedNodes;

    private VRPTW vrpInstance;

    // Optimization algorithm
    private VRPTW_ACS vrptwAcs;

    private InOut inOut;

    private Ants ants;

    private Utilities utilities;

    private Timer timer;

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
        ArrayList<Integer> newAvailableIdNodes = new ArrayList<>();
        ArrayList<Integer> idKnownRequests = new ArrayList<>();
        ArrayList<Integer> lastCommittedIndexes;

        // Counter which stores the number of the current time slice that we are during the execution of the
        // algorithm, which simulates a working day.
        int currentTimeSlice = 1;

        // Keeps the last index/position in the array of dynamic requests sorted ascending by available time
        // of the recent request which became available in the last time slice.
        int countApriori, lastPos;

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
        }

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

    public int getIdLastAvailableNode() {
        return idLastAvailableNode;
    }

    public void setIdLastAvailableNode(int idLastAvailableNode) {
        this.idLastAvailableNode = idLastAvailableNode;
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
