package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

import com.github.schmittjoaopedro.vrp.thesis.CallCenter;
import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.VehiclesControlCenter;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.IterationCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.StopCriteria;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.statistic.ExperimentStatistics;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

// Overall algorithm
public class TABU_SA_DPDP {

    private StopCriteria stopCriteria;

    private Instance instance;

    private MetaHeuristic metaHeuristic;

    private InsertionOperator insertionHeuristic;

    private Solution solutionBest;

    private long computationTime;

    private boolean print = true;

    private String finalResult = "";

    private VehiclesControlCenter vehiclesControlCenter;

    private CallCenter callCenter;

    private ExperimentStatistics experimentStatistics;

    private LinkedList<String> logs = new LinkedList<>();

    public TABU_SA_DPDP(Instance instance, Random random) {
        this(instance, random, IterationCriteria.of(5000));
    }

    public TABU_SA_DPDP(Instance instance, Random random, StopCriteria stopCriteria) {
        this(instance, random, stopCriteria, 90);
    }

    public TABU_SA_DPDP(Instance instance, Random random, StopCriteria stopCriteria, int setupTime) {
        this.instance = instance;
        this.insertionHeuristic = new RegretInsertion(random, instance, (sol, inst) -> 1);
        this.stopCriteria = stopCriteria;
        this.metaHeuristic = new MetaHeuristic(50.0, 0.95, 4, instance, random);
        experimentStatistics = new ExperimentStatistics();
        callCenter = new CallCenter(instance, setupTime);
    }

    public void run() {
        computationTime = System.currentTimeMillis();
        stopCriteria.reset();
        // Create initial solution
        Optional.ofNullable(callCenter).ifPresent(c -> c.loadStaticRequests());
        solutionBest = SolutionUtils.createSolution(instance);
        insertNewRequests(solutionBest);

        // Heuristic main method
        while (stopCriteria.isContinue()) { // Execute overall until the time period
            attendNewRequests();
            if (instance.numRequests > 0) {
                // Execute TABU Embedded SA heuristic until no more improvements or no new requests were announced
                metaHeuristic.init(solutionBest);
                // Before start the meta-heuristic we tracked the vehicles to guarantee that everything is updated
                trackOperatingVehicles();
                while (metaHeuristic.next() && stopCriteria.isContinue() && !attendNewRequests()) {
                    Solution solutionNew = metaHeuristic.getSolutionBest();
                    if (SolutionUtils.getBest(solutionNew, solutionBest) == solutionNew) {
                        solutionBest = solutionNew;
                        log("New best = " + solutionBest + " at scaled time " + stopCriteria.getScaledTime());
                    }
                    stopCriteria.update();
                    // Because tracking is calculated using the best solution this operation is executed after update the best solution
                    trackOperatingVehicles();
                }
            }
            stopCriteria.update();
            //log("Scaled time " + stopCriteria.getScaledTime());
        }
        computationTime = System.currentTimeMillis() - computationTime;
        experimentStatistics.totalTime = computationTime;
        experimentStatistics.numSolutionsEvaluation = instance.numEvaluatedFunction;
        instance.solutionEvaluation(solutionBest);
        experimentStatistics.solutionBest = solutionBest;
        compileFinalResult();
        log(finalResult);
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }

    public long getComputationTimeInSeconds() {
        return computationTime / 1000;
    }

    /**
     * Update algorithm time relative to the current iteration
     */
    private void updateAlgorithmTime() {
        double scaledTime = stopCriteria.getScaledTime();
        instance.currentTime = (int) (instance.depot.twEnd - instance.depot.twStart) * scaledTime;
    }

    /**
     * Check if new requests should be attended in the current time
     */
    private boolean attendNewRequests() {
        boolean newReq = false;
        updateAlgorithmTime();
        if (Optional.ofNullable(callCenter).isPresent()) {
            List<Integer> requests = callCenter.loadNewRequests();
            if (!requests.isEmpty()) {
                log("New requests = " + StringUtils.join(requests));
                insertNewRequests(solutionBest);
                newReq = true;
            }
        }
        return newReq;
    }

    /**
     * Track vehicles operation
     */
    private boolean trackOperatingVehicles() {
        boolean wereVehiclesMoved = false;
        if (instance.movingVehicle) {
            // Track moving vehicles
            wereVehiclesMoved = vehiclesControlCenter.moveVehicle(solutionBest);
        }
        return wereVehiclesMoved;
    }

    public void enableVehicleControlCenter() {
        vehiclesControlCenter = new VehiclesControlCenter(instance);
        instance.movingVehicle = true;
    }

    protected void insertNewRequests(Solution solutionBase) {
        Solution solution = copyBaseSolution(solutionBase);
        instance.solutionEvaluation(solution);
        while (solution.toVisit > 0) {
            insertionHeuristic.insert(solution, 0);
            SolutionUtils.removeEmptyVehicles(solution);
            instance.solutionEvaluation(solution);
            // If the fleet is insufficient to attend new requests. Add a new vehicle to this operation.
            if (solution.toVisit > 0) {
                instance.extraVehicles++;
                solution = copyBaseSolution(solutionBase);
            }
        }
        log("Insertion heuristic = " + solution);
        solutionBest = solution;
    }

    protected Solution copyBaseSolution(Solution solutionBase) {
        Solution newSolution = SolutionUtils.createSolution(instance);
        if (solutionBase != null) {
            for (int k = 0; k < solutionBase.tours.size(); k++) {
                newSolution.tours.set(k, new ArrayList<>(solutionBase.tours.get(k)));
                newSolution.requestIds.set(k, new ArrayList<>(solutionBase.requestIds.get(k)));
            }
        }
        instance.solutionEvaluation(newSolution);
        return newSolution;
    }

    public void compileFinalResult() {
        double[] startTimes = new double[solutionBest.tours.size()];
        for (int i = 0; i < startTimes.length; i++) {
            startTimes[i] = instance.lastIdleTime(solutionBest.tours.get(i).get(1));
        }
        Optional.ofNullable(callCenter).ifPresent(c -> c.rollbackOriginalInformation(solutionBest));
        String msg = "\nInstance = " + instance.name;
        msg += "\nBest solution feasibility = " + solutionBest.feasible + "\nRoutes";
        for (int i = 0; i < solutionBest.tours.size(); i++) {
            ArrayList<Integer> route = solutionBest.tours.get(i);
            double startTime = startTimes[i];
            if (instance.movingVehicle) {
                msg += "\nStart time " + MathUtils.round(startTime, 2) + " Route = " + StringUtils.join(route, " ");
            } else {
                msg += "\n" + StringUtils.join(route, " ");
            }
        }
        msg += "\nRequests";
        for (ArrayList requests : solutionBest.requestIds) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nNum. vehicles = " + solutionBest.tours.size();
        msg += "\nTotal cost = " + solutionBest.totalCost;
        finalResult = msg;
    }

    public String getFinalResult() {
        return finalResult;
    }

    private void log(String log) {
        logs.add(log);
        if (print) {
            System.out.println(log);
        }
    }

    public List<String> getLogs() {
        return logs;
    }

    public ExperimentStatistics getExperimentStatistics() {
        return experimentStatistics;
    }
}
