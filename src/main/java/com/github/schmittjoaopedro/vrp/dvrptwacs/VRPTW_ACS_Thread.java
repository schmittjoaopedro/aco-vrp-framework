package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class VRPTW_ACS_Thread extends Thread {

    private VRPTW_ACS vrpAcs;

    private VRPTW vrpInstance;

    private Ants ants;

    private InOut inOut;

    private boolean isRunning = true;

    private boolean threadRestarted;

    public VRPTW_ACS_Thread(boolean threadStopped, VRPTW_ACS vrpAcs, VRPTW vrpInstance, Ants ants, InOut inOut) {
        super();
        setVrpAcs(vrpAcs);
        setVrpInstance(vrpInstance);
        setAnts(ants);
        setThreadRestarted(threadStopped);
        setInOut(inOut);
    }

    /**
     * Do the work of running the ant colony which tries to solve the DVRPTW instance using the available
     * (known so far) customer requests
     */
    @Override
    public void run() {
        int counter = 0;
        isRunning = true;
        while (isRunning) {
            // The thread was restarted (i.e. it was previously stopped and started again)
            if (threadRestarted) {
                // Compute the new value for the initial pheromone trail based on the current best solution so far
                int noAvailableNodes = vrpInstance.getIdAvailableRequests().size();
                ants.setTrail0(1.0 / ((double) (noAvailableNodes + 1) * ants.getBestSoFarAnt().getTotalTourLength()));
                // Preserve a certain amount of the pheromones from the previous run of the ant colony
                ants.preservePheromones(vrpInstance);
            }
            // Do the optimization task (work)
            constructSolutions(vrpInstance);
            // Increase evaluations counter
            inOut.setNoEvaluation(inOut.getNoEvaluation() + 1);
            counter++;
            updateStatistics(vrpInstance);
            pheromoneTrailUpdate();
            searchControlAndStatistics();
            // Force the ant colony thread to stop its execution
            if (counter > 300) {
                isRunning = false;
            }
        }
    }

    public void terminate() {
        isRunning = false;
    }

    public VRPTW_ACS getVrpAcs() {
        return vrpAcs;
    }

    public void setVrpAcs(VRPTW_ACS vrpAcs) {
        this.vrpAcs = vrpAcs;
    }

    public VRPTW getVrpInstance() {
        return vrpInstance;
    }

    public void setVrpInstance(VRPTW vrpInstance) {
        this.vrpInstance = vrpInstance;
    }

    public Ants getAnts() {
        return ants;
    }

    public void setAnts(Ants ants) {
        this.ants = ants;
    }

    public boolean isThreadRestarted() {
        return threadRestarted;
    }

    public void setThreadRestarted(boolean threadRestarted) {
        this.threadRestarted = threadRestarted;
    }

    public InOut getInOut() {
        return inOut;
    }

    public void setInOut(InOut inOut) {
        this.inOut = inOut;
    }
}
