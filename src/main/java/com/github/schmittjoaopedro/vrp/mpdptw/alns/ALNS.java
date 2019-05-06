package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.*;
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
public class ALNS {

    private double initialT;

    private double minT;

    private Solution solution;

    private Solution solutionBest;

    private double T;

    private int iteration = 1;

    private ProblemInstance instance;

    /*
     * Parameters section.
     *
     * (sigma1 > sigma2 > sigma3)
     */

    // Ropke and Pisinger
    /*private int numIterations = 25000;
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
    /*private int numIterations = 10000;
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
    private int numIterations = 100000;
    private double tolerance = 0.05; // (w)
    private double coolingRate = 0.9995; // reduction factor of acceptance methods (c)
    private double sigma1 = 33; // reward for finding a new global best solution
    private double sigma2 = 20; // reward for finding an improving, not global best, solution
    private double sigma3 = 13; // reward for finding an accepted non-improving solution
    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private String acceptMethod = "SA";
    private double minWeight = 1.0;
    private double noiseControl = 0.025;
    private int dMin(double n) { return (int) Math.max(1, Math.min(6, 0.15 * n)); }
    private int dMax(double n) { return (int) Math.min(18, 0.4 * n); }
    //private double getInitialT() { return (1.0 + tolerance) * initialCost; }
    private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }


    private double segment = 100.0;

    private double initialCost; // initial cost

    private InsertionOperator insertionOperator;

    private RemovalOperator removalOperator;

    // Probabilities calculated for ro's and ri's
    private double[] roProbs;
    private double[] riProbs;
    private double[] noiseProbs;
    private double roProbsSum;
    private double riProbsSum;
    private double noiseProbsSum;

    // Indicates how well an operator has performed in the past
    private double[] roWeights;
    private double[] riWeights;
    private double[] noiseWeights;

    // These are the accumulated scores (PI_i) of each operator during the executiong of segment (delta).
    private double[] roScores;
    private double[] riScores;
    private double[] noiseScores;

    // Number of times that each operator has been used in the last segment j.
    private double[] roUsages;
    private double[] riUsages;
    private double[] noiseUsages;

    private Random random;

    private Long startTime;

    private Long endTime;

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    private boolean generateFile = Boolean.FALSE;

    private InsertionHeuristic insertionHeuristic;

    private DynamicHandler dynamicHandler;

    private List<String> log = new ArrayList<>();

    private Set<Integer> hashNumber = new HashSet<>();

    private MovingVehicle movingVehicle;

    public ALNS(ProblemInstance instance, int numIterations, Random random) {
        this(instance, random);
        this.numIterations = numIterations;
    }

    public ALNS(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;

        dynamicHandler = new DynamicHandler(instance, 0.0, numIterations);
        dynamicHandler.adaptDynamicVersion();

        insertionOperator = new InsertionOperator(instance, random);
        insertionOperator.setNoiseControl(noiseControl);
        removalOperator = new RemovalOperator(instance, random);
        relocateRequestOperator = new RelocateRequestOperator(instance, random);
        exchangeRequestOperator = new ExchangeRequestOperator(instance, random);
    }

    public void execute() {

        /*
         * An initial solution S is generated through a construction heuristic in which requests are progressively
         * inserted within an available vehicle, at its minimum insertion cost position. After all requests have been
         * inserted, solution S is improved by our local search operator.
         */
        insertionHeuristic = new InsertionHeuristic(instance, random);
        solution = insertionHeuristic.createInitialSolution();
        instance.solutionEvaluation(solution);
        solution = applyImprovement(solution);
        instance.solutionEvaluation(solution);
        solutionBest = SolutionUtils.copy(solution);
        updateParameters();

        startTime = System.currentTimeMillis();

        roScores = new double[RemovalMethod.values().length];
        roWeights = new double[RemovalMethod.values().length];
        roUsages = new double[RemovalMethod.values().length];
        roProbs = new double[RemovalMethod.values().length];

        riScores = new double[InsertionMethod.values().length];
        riWeights = new double[InsertionMethod.values().length];
        riUsages = new double[InsertionMethod.values().length];
        riProbs = new double[InsertionMethod.values().length];

        noiseScores = new double[2];
        noiseWeights = new double[2];
        noiseUsages = new double[2];
        noiseProbs = new double[2];

        resetWeights();

        while (!stopCriteriaMeet()) {

            // Process new requests
            List<Integer> newRequestIds = dynamicHandler.processDynamism(iteration);
            if (!newRequestIds.isEmpty()) {
                System.out.println("New requests add: " + StringUtils.join(newRequestIds));
                log.add("New requests add: " + StringUtils.join(newRequestIds));
                for (int req : newRequestIds) {
                    instance.solutionEvaluation(solution);
                    insertionHeuristic.addRequests(solution, req);
                }
                instance.solutionEvaluation(solution);
                solution = applyImprovement(solution);
                instance.solutionEvaluation(solution);
                updateBest(solution, true);
                updateParameters();
            }

            if (instance.getNumReq() > 0) {
                Solution solutionNew = SolutionUtils.copy(solution); // S' <- S
                int q = generateRandomQ(); // q <- Generate a Random number of requests to remove
                /*
                 * Request removal and insertion operators ro and io are randomly inserted from set RO and IO using independent
                 * roulette wheels based on the score of each operator.
                 */
                int ro = getNextRouletteWheelOperator(roProbsSum, roProbs); // ro <- Select and operator from RO (Section 3.2) using a roulette wheel based on the weight of the operators.
                int ri = getNextRouletteWheelOperator(riProbsSum, riProbs); // ri <- Select and operator from IO (Section 3.3) using a roulette wheel based on the weight of the operators.
                int useNoise = getNextRouletteWheelOperator(noiseProbsSum, noiseProbs);

                roUsages[ro] = roUsages[ro] + 1;
                riUsages[ri] = riUsages[ri] + 1;
                noiseUsages[useNoise] = noiseUsages[useNoise] + 1;

                removeRequests(solutionNew, ro, q); // Remove q requests from S' using ro
                insertRequests(solutionNew, ri, useNoise); // Insert removed requests into S' by applying io using a random pickup insertion method (Section 3.3.1)
                SolutionUtils.removeEmptyVehicles(solutionNew);
                instance.solutionEvaluation(solutionNew); // Update the solution cost

                if (accept(solutionNew, solution)) {
                    int solutionHash = SolutionUtils.getHash(solutionNew);
                    if (!hashNumber.contains(solutionHash)) {
                        hashNumber.add(solutionHash);
                        if (SolutionUtils.getBest(solutionBest, solutionNew) == solutionNew) { // If f(S') < f(S_best) then
                            solution = applyImprovement(solutionNew); // Apply improvement (Section 3.4) to S'
                            updateBest(solution); // S_best <- S <- S'
                            // Increase the scores of io and ro by sigma1
                            updateScores(ro, ri, useNoise, sigma1); // Increment by sigma1 if the new solution is a new best one
                        } else if (SolutionUtils.getBest(solution, solutionNew) == solutionNew) { // Else if f(S') < f(S) then
                            // Increase the scores of the ro and io by sigma2
                            updateScores(ro, ri, useNoise, sigma2);  // Increment by sigma2 if the new solution is better than the previous one
                        } else if (accept(solutionNew, solution)) { // else if accept(S', S) then
                            // Increase the scores of the ro and io by sigma3
                            updateScores(ro, ri, useNoise, sigma3); // Increment by sigma3 if the new solution is not better but still accepted
                        }
                    }
                    solution = solutionNew; // S <- S'
                }

                /*
                 * T is the temperature that decreases at each iteration according to a standard exponential cooling rate.
                 * When the temperature reaches a minimum threshold, it is set to it's initial value (reheating). This process
                 * allow worse solutions to be more easily accepted and increase the diversity.
                 */
                T = T * coolingRate;
                if (T < minT) {
                    resetWeights();
                    T = initialT;
                }
                if (endOfSegment()) { // if end of a segment of 𝛿 iterations then
                    updateWeights(); // Update weights and reset scores of operators
                    solution = applyImprovement(solution); // Apply improvement to S
                    //printIterationStatus();
                    updateBest(solution);
                }
            }

            // Check moving vehicle
            if (movingVehicle != null) {
                if (movingVehicle.moveVehicle(solutionBest, iteration)) {
                    solution = SolutionUtils.copy(solutionBest);
                }
            }
            iteration++;
        }

        endTime = System.currentTimeMillis();
        dynamicHandler.rollbackOriginalInformation(solutionBest);
        printFinalRoute();
    }

    private void resetWeights() {
        /*
         * The score of the operators are initially set to 0.
         */
        // Init for removal
        for (int i = 0; i < roWeights.length; i++) {
            roWeights[i] = minWeight;
        }
        roProbsSum = updateWeightsProbabilities(roWeights, roProbs);
        // Init for insertion
        for (int i = 0; i < riWeights.length; i++) {
            riWeights[i] = minWeight;
        }
        riProbsSum = updateWeightsProbabilities(riWeights, riProbs);
        // Init noise
        for (int i = 0; i < noiseWeights.length; i++) {
            noiseWeights[i] = minWeight;
        }
        noiseProbsSum = updateWeightsProbabilities(noiseWeights, noiseProbs);
    }

    private void log(String msg) {
        log.add(msg);
        System.out.println(msg);
        if (generateFile) {
            try {
                FileUtils.writeStringToFile(new File("C:\\Temp\\mpdptw\\result-" + instance.getFileName()), msg + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printIterationStatus() {
        String msg = "Iter " + iteration +
                " Best = " + solution.toString() +
                " BSF = " + solutionBest.toString() +
                ", T = " + format(T) + ", minT = " + format(minT) +
                ", ro = [" + StringUtils.join(getArray(roWeights), ',') + "]" +
                ", ri = [" + StringUtils.join(getArray(riWeights), ',') + "]" +
                ", noise = [" + StringUtils.join(getArray(noiseWeights), ',') + "]" +
                ", Hash = " + hashNumber.size();
        System.out.println(msg);
    }

    private void updateParameters() {
        initialCost = solution.totalCost;
        initialT = getInitialT();
        T = initialT;
        minT = initialT * Math.pow(coolingRate, 30000);
    }

    private String[] getArray(double[] array) {
        String data[] = new String[array.length];
        double sum = 0.0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        for (int i = 0; i < array.length; i++) {
            data[i] = String.valueOf((int) (100 * array[i] / sum));
        }
        return data;
    }

    private String format(double value) {
        return String.format(Locale.US, "%.3f", value);
    }

    private void updateBest(Solution solution) {
        updateBest(solution, false);
    }

    private void updateBest(Solution solution, boolean force) {
        if (force || (solution.feasible && solution.totalCost < solutionBest.totalCost)) {
            solutionBest = SolutionUtils.copy(solution);
            String msg = "NEW BEST = Iter " + iteration + " BFS = " + solutionBest.totalCost + ", feasible = " + solutionBest.feasible;
            log(msg);
        }
    }

    private void updateWeights() {
        // Update for removal operators
        updateWeight(roWeights, roUsages, roScores);
        roProbsSum = updateWeightsProbabilities(roWeights, roProbs);
        // Update for insertion operators
        updateWeight(riWeights, riUsages, riScores);
        riProbsSum = updateWeightsProbabilities(riWeights, riProbs);
        // Update for noise
        updateWeight(noiseWeights, noiseUsages, noiseScores);
        noiseProbsSum = updateWeightsProbabilities(noiseWeights, noiseProbs);
    }

    private void updateWeight(double weight[], double usage[], double scores[]) {
        double maxWeight = 0.0;
        for (int r = 0; r < weight.length; r++) {
            weight[r] = (1.0 - rho) * weight[r] + rho * (scores[r] / Math.max(usage[r], 1.0));
            maxWeight = Math.max(weight[r], maxWeight);
            scores[r] = 0.0;
            usage[r] = 0.0;
        }
        double minWeight = maxWeight * 0.05;
        for (int r = 0; r < weight.length; r++) {
            if (weight[r] < minWeight) {
                weight[r] += minWeight;
            }
        }
    }

    private boolean endOfSegment() {
        return iteration % segment == 0;
    }

    /*
     * The acceptance criterion is such as that a candidate solution S' is accepted given the current solution S
     * with a probability e^-(f(S')-f(S))/T.
     */
    private boolean accept(Solution newSolution, Solution solution) {
        if (SolutionUtils.getBest(solution, newSolution) == newSolution) {
            return true;
        } else if ("SA".equals(acceptMethod)) {
            double difference = newSolution.totalCost - solution.totalCost;
            return random.nextDouble() < Math.exp(-1.0 * difference / T);
        } else if ("TA".equals(acceptMethod)) {
            double difference = newSolution.totalCost - solution.totalCost;
            return difference < T;
        } else {
            return false;
        }
    }

    private void updateScores(int ro, int ri, int useNoise, double sigma) {
        roScores[ro] = roScores[ro] + sigma;
        riScores[ri] = riScores[ri] + sigma;
        noiseScores[useNoise] = noiseScores[useNoise] + sigma;
    }

    private Solution applyImprovement(Solution solution) {
        boolean improvement = true;
        Solution improved = SolutionUtils.copy(solution);
        Solution tempSolution = improved;
        while (improvement) {
            improvement = false;
            tempSolution = relocateRequestOperator.relocate(tempSolution);
            tempSolution = exchangeRequestOperator.exchange(tempSolution);
            if (SolutionUtils.getBest(improved, tempSolution) == tempSolution) {
                improved = SolutionUtils.copy(tempSolution);
                improvement = true;
            }
        }
        return improved;
    }

    private boolean stopCriteriaMeet() {
        return iteration > numIterations;
    }

    private void insertRequests(Solution solution, int ri, int useNoise) {
        ArrayList<Req> removedRequests = new ArrayList<>();
        for (int i = 0; i < solution.visitedRequests.length; i++) {
            if (!solution.visitedRequests[i]) {
                removedRequests.add(new Req(-1, i));
            }
        }
        InsertionMethod insertionMethod = InsertionMethod.values()[ri];
        // Use random pickup method
        switch (insertionMethod) {
            case Greedy:
                insertionOperator.insertGreedyRequests(solution, removedRequests, PickupMethod.Random, useNoise);
                break;
            case Regret3Noise:
            case Regret3:
                insertionOperator.insertRegretRequests(solution, removedRequests, 3, insertionMethod, PickupMethod.Random, useNoise);
                break;
            case RegretMNoise:
            case RegretM:
                insertionOperator.insertRegretRequests(solution, removedRequests, solution.tours.size(), insertionMethod, PickupMethod.Random, useNoise);
                break;
        }
    }

    private void removeRequests(Solution solution, int ro, int q) {
        List<Req> removedRequests = null;
        RemovalMethod removalMethod = RemovalMethod.values()[ro];
        switch (removalMethod) {
            case Random:
                removedRequests = removalOperator.removeRandomRequest(solution.tours, solution.requests, q);
                break;
            case Shaw:
                removedRequests = removalOperator.removeShawRequests(solution, q);
                break;
            case ExpensiveRequest:
                removedRequests = removalOperator.removeExpensiveRequests(solution.tours, solution.requests, q);
                break;
            case ExpensiveNode:
                removedRequests = removalOperator.removeMostExpensiveNodes(solution.tours, solution.requests, q);
                break;
        }
        for (Req req : removedRequests) {
            solution.visitedRequests[req.requestId] = false;
            for (Request pickup : instance.getPickups(req.requestId)) {
                solution.visited[pickup.nodeId] = false;
            }
            solution.visited[instance.getDelivery(req.requestId).nodeId] = false;
        }
    }

    /*
     * Based on experiments evaluated by Naccache (2018) (Table 3).
     */
    private int generateRandomQ() {
        int min = dMin(instance.getNumReq());
        int max = dMax(instance.getNumReq());
        return min + (int) (random.nextDouble() * (max - min));
    }

    /*
     * Given h operators with weights w_i, operator j will be selected with probability w_j / sum_{i=1}_{h} w_i
     */
    private int getNextRouletteWheelOperator(double sum, double[] probs) {
        if (sum == 0.0) {
            return (int) (random.nextDouble() * probs.length);
        } else {
            int count = 0;
            double partialSum = probs[count];
            double rand = random.nextDouble() * sum;
            while (partialSum <= rand) {
                count++;
                partialSum = probs[count];
            }
            return count;
        }
    }

    private double updateWeightsProbabilities(double[] weights, double[] probs) {
        double sum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            probs[i] = sum + weights[i];
            sum = probs[i];
        }
        return sum;
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
        msg += "\nTotal time (s) = " + ((endTime - startTime) / 1000.0);
        log(msg);
    }

    public void enableMovingVehicle() {
        movingVehicle = new MovingVehicle(instance, 0, numIterations);
    }

    public void setGenerateFile(boolean generateFile) {
        this.generateFile = generateFile;
    }

    public List<String> getLog() {
        return log;
    }
}
