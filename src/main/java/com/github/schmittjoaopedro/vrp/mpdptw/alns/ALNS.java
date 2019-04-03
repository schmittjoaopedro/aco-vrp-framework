package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
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

    private int maxIterations;

    private String rootDirectory;

    private String fileName;

    private ProblemInstance instance;

    private double c; // cooling rate

    private double segment = 100.0;

    // Define this values as (sigma1 > sigma2 > sigma3)
    private double sigma1 = 33;
    private double sigma2 = 20;
    private double sigma3 = 12;

    private double d; // initial cost

    private double w; // temp control

    private InsertionOperator insertionOperator;

    private RemovalOperator removalOperator;

    // Indicates how well an operator has performed in the past
    private double[] roWeights;
    private double[] riWeights;

    // These are the accumulated scores (PI_i) of each operator during the executiong of segment (delta).
    private double[] roScores;
    private double[] riScores;

    // Number of times that each operator has been used in the last segment j.
    private int[] roUsages;
    private int[] riUsages;

    private Random random;

    // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance.
    private double rho = 0.1; //η

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    public ALNS(String rootDirectory, String fileName, int maxIterations, Random random) {
        this.rootDirectory = rootDirectory;
        this.fileName = fileName;
        this.maxIterations = maxIterations;
        this.random = random;

        initProblemInstance();
        insertionOperator = new InsertionOperator(instance, random);
        removalOperator = new RemovalOperator(instance, random);
        relocateRequestOperator = new RelocateRequestOperator(instance, random);
        exchangeRequestOperator = new ExchangeRequestOperator(instance, random);

        /*
         * An initial solution S is generated through a construction heuristic in which requests are progressively
         * inserted within an available vehicle, at its minimum insertion cost position. After all requests have been
         * inserted, solution S is improved by our local search operator.
         */
        InsertionHeuristic insertionHeuristic = new InsertionHeuristic(instance, random);
        solution = insertionHeuristic.createInitialSolution();
        solution = applyImprovement(solution);
        solutionBest = solution.copy();

        d = solution.totalCost;
        w = 0.05;
        c = 0.9995;
        initialT = (1.0 + w) * d;
        //initialT = d * w / Math.log(2);
        T = initialT;
        minT = initialT * Math.pow(c, 30000);
    }

    public void execute() {

        roScores = new double[RemovalMethod.values().length];
        roWeights = new double[RemovalMethod.values().length];
        roUsages = new int[RemovalMethod.values().length];

        riScores = new double[InsertionMethod.values().length];
        riWeights = new double[InsertionMethod.values().length];
        riUsages = new int[InsertionMethod.values().length];

        /*
         * The score of the operators are initially set to 0.
         */
        for (int i = 0; i < roWeights.length; i++) {
            roWeights[i] = 1.0;
        }
        for (int i = 0; i < riWeights.length; i++) {
            riWeights[i] = 1.0;
        }

        while (!stopCriteriaMeet()) {
            Solution solutionNew = solution.copy(); // S' <- S
            int q = generateRandomQ(); // q <- Generate a Random number of requests to remove
            /*
             * Request removal and insertion operators ro and io are randomly inserted from set RO and IO using independent
             * roulette wheels based on the score of each operator.
             */
            int ro = selectRemovalOperator(); // ro <- Select and operator from RO (Section 3.2) using a roulette wheel based on the weight of the operators.
            int ri = selectInsertionOperator(); // ri <- Select and operator from IO (Section 3.3) using a roulette wheel based on the weight of the operators.
            List<Req> removedRequests = removeRequests(solutionNew, ro, q); // Remove q requests from S' using ro
            insertRequests(solutionNew, ri, removedRequests); // Insert removed requests into S' by applying io using a random pickup insertion method (Section 3.3.1)
            instance.restrictionsEvaluation(solutionNew); // Update the solution cost
            if (solutionNew.totalCost < solutionBest.totalCost) { // If f(S') < f(S_best) then
                solution = applyImprovement(solutionNew); // Apply improvement (Section 3.4) to S'
                updateBest(solution); // S_best <- S <- S'
                // Increase the scores of io and ro by sigma1
                updateScores(ro, ri, sigma1); // Increment by sigma1 if the new solution is a new best one
            } else if (solutionNew.totalCost < solution.totalCost) { // Else if f(S') < f(S) then
                solution = solutionNew; // S <- S'
                // Increase the scores of the ro and io by sigma2
                updateScores(ro, ri, sigma2);  // Increment by sigma2 if the new solution is better than the previous one
            } else if (accept(solutionNew, solution)) { // else if accept(S', S) then
                solution = solutionNew; // S <- S'
                // Increase the scores of the ro and io by sigma3
                updateScores(ro, ri, sigma3); // Increment by sigma3 if the new solution is not better but still accepted
            }

            /*
             * T is the temperature that decreases at each iteration according to a standard exponential cooling rate.
             * When the temperature reaches a minimum threshold, it is set to it's initial value (reheating). This process
             * allow worse solutions to be more easily accepted and increase the diversity.
             */
            T = T * c;
            if (T < minT) {
                T = initialT;
            }
            if (endOfSegment()) { // if end of a segment of 𝛿 iterations then
                updateWeights(); // Update weights and reset scores of operators
                resetOperatorsScores();
                solution = applyImprovement(solution); // Apply improvement to S
                updateBest(solution);
                System.out.println("Iter " + iteration +
                        " Best = " + format(solution.totalCost) + ", feasible = " + solution.feasible +
                        " BSF = " + format(solutionBest.totalCost) + ", feasible = " + solutionBest.feasible +
                        ", T = " + format(T) + ", minT = " + format(minT) +
                        ", ro = " + StringUtils.join(getArray(roWeights), ',') +
                        ", ri = " + StringUtils.join(getArray(riWeights), ','));
            }
            iteration++;
        }
        printFinalRoute();
    }

    private String[] getArray(double[] array) {
        String data[] = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            data[i] = format(array[i]);
        }
        return data;
    }

    private String format(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    private void updateBest(Solution solution) {
        if (solution.feasible && solution.totalCost < solutionBest.totalCost) {
            solutionBest = solution.copy();
            System.out.println("NEW BEST = Iter " + iteration + " BFS = " + solutionBest.totalCost + ", feasible = " + solutionBest.feasible);
        }
    }

    /*
     * The scores are updated to zero at the end of each segment.
     */
    private void resetOperatorsScores() {
        for (int ri = 0; ri < riWeights.length; ri++) {
            riScores[ri] = 0;
            riUsages[ri] = 0;
        }
        for (int ro = 0; ro < roWeights.length; ro++) {
            roScores[ro] = 0;
            roUsages[ro] = 0;
        }
    }

    private void updateWeights() {
        for (int ri = 0; ri < riWeights.length; ri++) {
            if (riUsages[ri] != 0) {
                riWeights[ri] = (1.0 - rho) * riWeights[ri] + rho * riScores[ri] / riUsages[ri];
            }
        }
        for (int ro = 0; ro < roWeights.length; ro++) {
            if (roUsages[ro] != 0) {
                roWeights[ro] = (1.0 - rho) * roWeights[ro] + rho * roScores[ro] / roUsages[ro];
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
        return random.nextDouble() < Math.pow(Math.E, -(newSolution.totalCost - solution.totalCost) / T);
    }

    private void updateScores(int ro, int ri, double sigma) {
        roScores[ro] = roScores[ro] + sigma;
        riScores[ri] = riScores[ri] + sigma;
    }

    private Solution applyImprovement(Solution solution) {
        boolean improvement = true;
        Solution improved = solution.copy();
        Solution tempSolution = improved;
        while (improvement) {
            improvement = false;
            tempSolution = relocateRequestOperator.relocate(tempSolution);
            tempSolution = exchangeRequestOperator.exchange(tempSolution);
            if (tempSolution.totalCost < improved.totalCost) {
                improved = tempSolution;
                improvement = true;
            }
        }
        return improved;
    }

    private boolean stopCriteriaMeet() {
        return iteration > maxIterations;
    }

    private void initProblemInstance() {
        try {
            String filePath = Paths.get(rootDirectory, fileName).toString();
            instance = DataReader.getProblemInstance(new File(filePath));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void insertRequests(Solution solution, int ri, List<Req> removedRequests) {
        InsertionMethod insertionMethod = InsertionMethod.values()[ri];
        // Use random pickup method
        switch (insertionMethod) {
            case Greedy:
                insertionOperator.insertGreedyRequests(solution.tours, solution.requests, removedRequests, PickupMethod.Random);
                break;
            case Regret3Noise:
            case Regret3:
                insertionOperator.insertRegretRequests(solution.tours, solution.requests, removedRequests, 3, insertionMethod, PickupMethod.Random);
                break;
            case RegretMNoise:
            case RegretM:
                insertionOperator.insertRegretRequests(solution.tours, solution.requests, removedRequests, solution.tours.size(), insertionMethod, PickupMethod.Random);
                break;
        }
    }

    private List<Req> removeRequests(Solution solution, int ro, int q) {
        List<Req> removedRequests = null;
        RemovalMethod removalMethod = RemovalMethod.values()[ro];
        switch (removalMethod) {
            case Random:
                removedRequests = removalOperator.removeRandomRequest(solution.tours, solution.requests, q);
                break;
            case Shaw:
                removedRequests = removalOperator.removeShawRequests(solution.tours, solution.requests, q);
                break;
            case ExpensiveRequest:
                removedRequests = removalOperator.removeExpensiveRequests(solution.tours, solution.requests, q);
                break;
            case ExpensiveNode:
                removedRequests = removalOperator.removeMostExpensiveNodes(solution.tours, solution.requests, q);
                break;
        }
        return removedRequests;
    }

    /*
     * Based on experiments evaluated by Naccache (2018) (Table 3).
     */
    private int generateRandomQ() {
        int min = (int) Math.min(6, 0.15 * instance.getNumReq());
        int max = (int) Math.min(18, 0.4 * instance.getNumReq());
        return min + (int) (random.nextDouble() * (max - min));
    }

    private int selectRemovalOperator() {
        int ro = getNextRouletteWheelOperator(roWeights);
        roUsages[ro] = roUsages[ro] + 1;
        return ro;
    }

    private int selectInsertionOperator() {
        // Ignore K-regret
        riWeights[InsertionMethod.RegretM.ordinal()] = 0.0; // Accordingly coelho, k-regret is deteriorating the results.
        int ri = getNextRouletteWheelOperator(riWeights);
        riUsages[ri] = riUsages[ri] + 1;
        return ri;
    }

    /*
     * Given h operators with weights w_i, operator j will be selected with probability w_j / sum_{i=1}_{h} w_i
     */
    private int getNextRouletteWheelOperator(double[] weights) {
        double sum = 0.0;
        double probs[] = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            probs[i] = sum + weights[i];
            sum = probs[i];
        }
        if (sum == 0.0) {
            return (int) (random.nextDouble() * weights.length);
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

    private void printFinalRoute() {
        Solution solution = solutionBest;
        String msg = "";
        instance.restrictionsEvaluation(solution);
        solution.totalCost = 0.0;
        double cost = 0.0;
        for (int i = 0; i < solution.tours.size(); i++) {
            cost = instance.costEvaluation(solution.tours.get(i));
            solution.tourLengths.set(i, cost);
            solution.totalCost += cost;
        }
        msg += "\nInstance = " + fileName;
        msg += "\nBest solution feasibility = " + solution.feasible + "\nRoutes";
        for (ArrayList route : solution.tours) {
            msg += "\n" + StringUtils.join(route, "-");
        }
        msg += "\nRequests";
        for (ArrayList requests : solution.requests) {
            msg += "\n" + StringUtils.join(requests, "-");
        }
        msg += "\nCost = " + solution.totalCost;
        System.out.println(msg);
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
    }
}
