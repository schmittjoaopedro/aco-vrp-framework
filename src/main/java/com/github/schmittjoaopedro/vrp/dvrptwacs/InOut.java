package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class InOut {

    // Maximum number of independent tries.
    private int maxTries;

    // Maximum number of tour constructions in one try.
    private int maxTours;

    // Maximal allowed run time of a try. It is used in the termination condition of ACO.
    private double maxTime;

    // Maximum number of allowed iterations until the ACO algorithm stops.
    private int maxIterations;

    // Time used until some given event.
    private double timeUsed;

    // Time passed until some moment.
    private double timePassed;

    // Optimal solution or bound to find.
    private int optimal;

    // If branching factor < branchingFactor => update trails.
    private double branchingFactor;

    // Parameter to determine branching factor.
    private double lambda;

    // Counter of number constructed tours.
    private int numTours;

    // Iteration counter
    private int iteration;

    // Iteration in which best solution is found.
    private int foundBest;

    private int noEvaluation;

    // Counter for the total number of feasible solutions.
    private int noSolutions;

    private DistanceType distanceType;

    private double pheromonePreservation;

    public void initProgram(int runNumber, VRPTW instance, double scalingValue, Ants ants, Utilities utilities) {
        setDefaultParameters(ants, utilities);
        setDefaultACSParameters(ants);
        if (ants.getNumAnts() < 0) {
            ants.setNumAnts(instance.getN());
        }
        // Compute distance matrix between cities and allocate ants
        instance.computeDistances(distanceType, scalingValue);
        ants.allocateAnts(instance.getN(), instance.getIdAvailableRequests().size());
        System.out.println("\nRun Ant Colony System #" + (runNumber + 1));
    }

    private void setDefaultParameters(Ants ants, Utilities utilities) {
        // Ants parameters.
        ants.setNumAnts(25);
        ants.setNnAnts(20);
        ants.setAlpha(1.0);
        ants.setBeta(2.0);
        ants.setRho(0.5);
        ants.setQ0(0.0);
        ants.setuGb(Integer.MAX_VALUE);
        // Execution parameters.
        setMaxTries(10);
        setMaxTours(10 * 20);
        setMaxTime(100);
        setMaxIterations(3000);
        setOptimal(1);
        setBranchingFactor(1.00001);
        setDistanceType(DistanceType.EUC_2D);
        setPheromonePreservation(0.3);
        // Utilities parameters.
        utilities.setSeed((int) System.currentTimeMillis());
    }

    private void setDefaultACSParameters(Ants ants) {
        // Number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction.
        ants.setNumAnts(10);
        ants.setNnAnts(20);
        ants.setAlpha(1.0);
        ants.setBeta(1.0);
        ants.setRho(0.9);
        ants.setLocalRho(0.9);
        ants.setQ0(0.9);
    }

    public int getMaxTries() {
        return maxTries;
    }

    public void setMaxTries(int maxTries) {
        this.maxTries = maxTries;
    }

    public int getMaxTours() {
        return maxTours;
    }

    public void setMaxTours(int maxTours) {
        this.maxTours = maxTours;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public int getOptimal() {
        return optimal;
    }

    public void setOptimal(int optimal) {
        this.optimal = optimal;
    }

    public double getBranchingFactor() {
        return branchingFactor;
    }

    public void setBranchingFactor(double branchingFactor) {
        this.branchingFactor = branchingFactor;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public DistanceType getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(DistanceType distanceType) {
        this.distanceType = distanceType;
    }

    public double getPheromonePreservation() {
        return pheromonePreservation;
    }

    public void setPheromonePreservation(double pheromonePreservation) {
        this.pheromonePreservation = pheromonePreservation;
    }

    public double getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(double timeUsed) {
        this.timeUsed = timeUsed;
    }

    public double getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(double timePassed) {
        this.timePassed = timePassed;
    }

    public int getNumTours() {
        return numTours;
    }

    public void setNumTours(int numTours) {
        this.numTours = numTours;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public int getFoundBest() {
        return foundBest;
    }

    public void setFoundBest(int foundBest) {
        this.foundBest = foundBest;
    }

    public int getNoEvaluation() {
        return noEvaluation;
    }

    public void setNoEvaluation(int noEvaluation) {
        this.noEvaluation = noEvaluation;
    }

    public int getNoSolutions() {
        return noSolutions;
    }

    public void setNoSolutions(int noSolutions) {
        this.noSolutions = noSolutions;
    }
}
