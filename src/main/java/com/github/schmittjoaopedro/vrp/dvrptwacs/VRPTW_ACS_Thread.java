package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.sql.Time;

public class VRPTW_ACS_Thread extends Thread {

    private VRPTW_ACS vrpAcs;

    private VRPTW vrpInstance;

    private Controller controller;

    private Utilities utilities;

    private Ants ants;

    private InOut inOut;

    private Timer timer;

    private boolean isRunning = true;

    private boolean threadRestarted;

    public VRPTW_ACS_Thread(boolean threadStopped, VRPTW_ACS vrpAcs, VRPTW vrpInstance, Ants ants, InOut inOut, Controller controller, Utilities utilities, Timer timer) {
        super();
        setVrpAcs(vrpAcs);
        setVrpInstance(vrpInstance);
        setAnts(ants);
        setThreadRestarted(threadStopped);
        setInOut(inOut);
        setController(controller);
        setUtilities(utilities);
        setTimer(timer);
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
                ants.preservePheromones(vrpInstance, inOut.getPheromonePreservation());
            }
            // Do the optimization task (work)
            vrpAcs.constructSolutions(vrpInstance, ants, inOut, utilities);
            // Increase evaluations counter
            inOut.setNoEvaluation(inOut.getNoEvaluation() + 1);
            counter++;
            vrpAcs.updateStatistics(vrpInstance, ants, inOut, timer);
            vrpAcs.pheromoneTrailUpdate(ants);
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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Utilities getUtilities() {
        return utilities;
    }

    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
