package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.ExchangeRequestOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateRequestOperator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * ALNS applied for the MPDPTW proposed in:
 * Naccache, S., Côté, J., & Coelho, L. C. (2018). The multi-pickup and delivery problem with time windows.
 * European Journal of Operational Research, 269(1), 353–362. https://doi.org/10.1016/j.ejor.2018.01.035
 */
public class ALNS implements Runnable {

    private Solution solutionBest;

    private int iteration = 1;

    private ProblemInstance instance;

    private List<IterationStatistic> iterationStatistics;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    private DetailedStatistics detailedStatistics = new DetailedStatistics();

    /*
     * Parameters section.
     *
     * (sigma1 > sigma2 > sigma3)
     */

    // Ropke and Pisinger
    /*private static final int DEFAULT_MAX_ITERATIONS = 25000;
    private double tolerance = 0.05; // (w)
    private double coolingRate = 0.99975; // reduction factor of acceptance methods (c)
    private double sigma1 = 33; // reward for finding a new global best solution
    private double sigma2 = 9; // reward for finding an improving, not global best, solution
    private double sigma3 = 13; // reward for finding an accepted non-improving solution
    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private int dMin(double n) { return (int) Math.min(30.0, 0.1 * n); }
    private int dMax(double n) { return (int) Math.min(60.0, 0.4 * n); }
    private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }
    private String acceptMethod = "SA";
    private double minWeight = 1.0;
    private double noiseControl = 0.025;*/

    // Lutz
    /*private static final int DEFAULT_MAX_ITERATIONS = 10000;
    private double tolerance = 0.01; // (w)
    private double coolingRate = 0.9997; // reduction factor of acceptance methods (c)
    private double sigma1 = 135; // reward for finding a new global best solution
    private double sigma2 = 70; // reward for finding an improving, not global best, solution
    private double sigma3 = 25; // reward for finding an accepted non-improving solution
    private double rho = 0.35; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private int dMin(double n) { return (int) (0.075 * n); }
    private int dMax(double n) { return (int) (0.275 * n); }
    private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }
    private String acceptMethod = "TA";
    private double minWeight = 1.0;
    private double noiseControl = 0.43;*/

    // Coelho
    private static final int DEFAULT_MAX_ITERATIONS = 100000;
    private double tolerance = 0.05; // (w)
    private double coolingRate = 0.9995; // reduction factor of acceptance methods (c)
    private double sigma1 = 33; // reward for finding a new global best solution
    private double sigma2 = 20; // reward for finding an improving, not global best, solution
    private double sigma3 = 13; // reward for finding an accepted non-improving solution
    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private String acceptMethod = "SA";
    private double minWeight = 1.0;
    private double noiseControl = 0.025;

    private int dMin(double n) {
        return (int) Math.max(1, Math.min(6, 0.15 * n));
    }

    private int dMax(double n) {
        return (int) Math.min(18, 0.4 * n);
    }

    //private double getInitialT() { return (1.0 + tolerance) * initialCost; }
    private double getInitialT() {
        return (initialCost * tolerance) / Math.log(2);
    }

    private int numIterations;

    private int segment = 100;

    private double initialCost; // initial cost

    private Random random;

    private Long statisticInterval = 1L;

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    private boolean generateFile = Boolean.FALSE;

    private boolean generateDetailedStatistics = Boolean.FALSE;

    private InsertionHeuristic insertionHeuristic;

    private DynamicHandler dynamicHandler;

    private List<String> log = new ArrayList<>();

    private MovingVehicle movingVehicle;

    private boolean showLog = false;

    private ALNS_TC alns_tc;

    public ALNS(ProblemInstance instance, int numIterations, Random random) {
        this.instance = instance;
        this.random = random;
        this.numIterations = numIterations;

        dynamicHandler = new DynamicHandler(instance, 0.0, numIterations);
        dynamicHandler.adaptDynamicVersion();

        ALNS_BASE.Parameters parameters = createParameters();
        this.alns_tc = new ALNS_TC(instance, random, parameters);
        this.alns_tc.init();

        relocateRequestOperator = new RelocateRequestOperator(instance, random);
        exchangeRequestOperator = new ExchangeRequestOperator(instance, random);
        iterationStatistics = new ArrayList<>(numIterations);
    }

    @Override
    public void run() {

        /*
         * An initial solution S is generated through a construction heuristic in which requests are progressively
         * inserted within an available vehicle, at its minimum insertion cost position. After all requests have been
         * inserted, solution S is improved by our local search operator.
         */
        globalStatistics.startTimer();
        insertionHeuristic = new InsertionHeuristic(instance, random);
        Solution initialSolution = insertionHeuristic.createInitialSolution();
        instance.solutionEvaluation(initialSolution);
        initialSolution = applyImprovement(initialSolution);
        instance.solutionEvaluation(initialSolution);
        solutionBest = SolutionUtils.copy(initialSolution);
        setAlnsTcParameters();
        globalStatistics.endTimer("Initialization");

        globalStatistics.startTimer();
        while (!stopCriteriaMeet()) {
            Long iterationTime = System.currentTimeMillis();

            // Process new requests
            List<Integer> newRequestIds = dynamicHandler.processDynamism(iteration);
            if (!newRequestIds.isEmpty()) {
                log("New requests add: " + StringUtils.join(newRequestIds));
                Solution solution = SolutionUtils.copy(alns_tc.getSolution());
                for (int req : newRequestIds) {
                    instance.solutionEvaluation(solution);
                    insertionHeuristic.addRequest(solution, req);
                }
                instance.solutionEvaluation(solution);
                solution = applyImprovement(solution);
                instance.solutionEvaluation(solution);
                updateBest(solution, true);
                setAlnsTcParameters();
            }

            if (instance.getNumReq() > 0) {
                alns_tc.performIteration(iteration);
                detailedStatistics.s_best_TC = solutionBest.totalCost;
                detailedStatistics.s_best_NV = solutionBest.tours.size();
                updateBest(alns_tc.getGlobalSolution(), false);
            }

            // Check moving vehicle
            if (movingVehicle != null) {
                if (movingVehicle.moveVehicle(solutionBest, iteration)) {
                    initialSolution = SolutionUtils.copy(solutionBest);
                    alns_tc.setGlobalSolution(solutionBest);
                }
            }

            if (iteration % statisticInterval == 0) {
                IterationStatistic iterationStatistic = new IterationStatistic();
                iterationStatistic.setIteration(iteration);
                iterationStatistic.setBestSoFar(solutionBest.totalCost);
                iterationStatistic.setBestSoFarNV(solutionBest.tours.size());
                iterationStatistic.setIterationBest(initialSolution.totalCost);
                iterationStatistic.setIterationBestNV(initialSolution.tours.size());
                iterationStatistic.setFeasible(initialSolution.feasible ? 1.0 : 0.0);
                iterationStatistics.add(iterationStatistic);
                logInFile(iterationStatistic.toStringCsv());
            }
            if (generateDetailedStatistics) {
                detailedStatistics.iterationTime = System.currentTimeMillis() - iterationTime;
                alns_tc.logDetailedStatistics(iteration);
            }
            iteration++;
        }
        globalStatistics.endTimer("Algorithm");

        dynamicHandler.rollbackOriginalInformation(solutionBest);
        printFinalRoute();
    }

    private void log(String msg) {
        log.add(msg);
        if (showLog) {
            //System.out.println(Thread.currentThread().getId() + " -> " + msg);
            System.out.println(msg);
        }
    }

    private void logInFile(String text) {
        if (generateFile) {
            try {
                FileUtils.writeStringToFile(new File("C:\\Temp\\result-" + instance.getFileName()), text + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void updateBest(Solution solution, boolean force) {
        if (force || (solution.feasible && instance.getBest(solutionBest, solution) == solution)) {
            solutionBest = SolutionUtils.copy(solution);
            String msg = "NEW BEST = Iter " + iteration + " Sol = " + solution.toString();
            log(msg);
        }
    }

    private void setAlnsTcParameters() {
        initialCost = solutionBest.totalCost;
        double initialT = getInitialT();
        double T = initialT;
        double minT = initialT * Math.pow(coolingRate, 30000);

        alns_tc.setT(T);
        alns_tc.parameters.initialCost = initialCost;
        alns_tc.parameters.initialT = initialT;
        alns_tc.parameters.minT = minT;
        alns_tc.setGlobalSolution(solutionBest);
    }

    private ALNS_BASE.Parameters createParameters() {
        ALNS_BASE.Parameters parameters = new ALNS_BASE.Parameters();
        parameters.noiseControl = noiseControl;
        parameters.rho = rho;
        parameters.coolingRate = coolingRate;
        parameters.dMax = this::dMax;
        parameters.dMin = this::dMin;
        parameters.applyImprovement = this::applyImprovement;
        parameters.segment = segment;
        parameters.sigma1 = sigma1;
        parameters.sigma2 = sigma2;
        parameters.sigma3 = sigma3;
        parameters.minWeight = minWeight;
        parameters.acceptMethod = acceptMethod;
        return parameters;
    }

    private Solution applyImprovement(Solution solution) {
        boolean improvement = true, foundImprovement = false;
        Solution improved = SolutionUtils.copy(solution);
        Solution tempSolution = improved;
        while (improvement) {
            improvement = false;
            tempSolution = relocateRequestOperator.relocate(tempSolution);
            tempSolution = exchangeRequestOperator.exchange(tempSolution);
            if (instance.getBest(improved, tempSolution) == tempSolution) {
                improved = SolutionUtils.copy(tempSolution);
                improvement = true;
                foundImprovement = true;
            }
        }
        if (foundImprovement) {
            detailedStatistics.localSearchImproved++;
        }
        detailedStatistics.localSearchUsed++;
        return improved;
    }

    private boolean stopCriteriaMeet() {
        return iteration > numIterations;
    }

    private void printFinalRoute() {
        Solution solution = solutionBest;
        String msg = "";
        instance.solutionEvaluation(solution);
        solution.totalCost = 0.0;
        double cost = 0.0;
        for (int i = 0; i < solution.tours.size(); i++) {
            cost = instance.costEvaluation(solution.tours.get(i));
            solution.tourCosts.set(i, cost);
            solution.totalCost += cost;
        }
        msg += "\nInstance = " + instance.getFileName();
        msg += "\nBest solution feasibility = " + solution.feasible + "\nRoutes";
        for (ArrayList route : solution.tours) {
            msg += "\n" + StringUtils.join(route, " ");
        }
        msg += "\nRequests";
        for (ArrayList requests : solution.requests) {
            msg += "\n" + StringUtils.join(requests, " ");
        }
        msg += "\nCost = " + solution.totalCost;
        msg += "\nNum. vehicles = " + solution.tours.size();
        Set<Integer> processedNodes = new HashSet<>();
        for (int k = 0; k < solutionBest.tours.size(); k++) {
            for (int i = 1; i < solutionBest.tours.get(k).size() - 1; i++) {
                if (processedNodes.contains(solutionBest.tours.get(k).get(i))) {
                    throw new RuntimeException("Invalid route, duplicated nodes");
                } else {
                    processedNodes.add(solutionBest.tours.get(k).get(i));
                }
            }
        }
        msg += "\nInitialization time(s) = " + ((globalStatistics.getTimeStatistics().get("Initialization")) / 1000.0);
        msg += "\nAlgorithm time(s) = " + ((globalStatistics.getTimeStatistics().get("Algorithm")) / 1000.0);
        log(msg);
        logInFile(msg);
        //MapPrinter.printResult(solutionBest, instance, 800, 600, instance.getFileName());
    }

    public void enableMovingVehicle() {
        movingVehicle = new MovingVehicle(instance, 0, numIterations);
    }

    public void setGenerateDetailedStatistics(boolean generateDetailedStatistics) {
        this.generateDetailedStatistics = generateDetailedStatistics;
    }

    public void setGenerateFile(boolean generateFile) {
        this.generateFile = generateFile;
    }

    public List<String> getLog() {
        return log;
    }

    public Long getStatisticInterval() {
        return statisticInterval;
    }

    public void setStatisticInterval(Long statisticInterval) {
        this.statisticInterval = statisticInterval;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public List<IterationStatistic> getIterationStatistics() {
        return iterationStatistics;
    }
}
