package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

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

    private double c;

    private double segment = 100.0;

    private double sigma1 = 33;

    private double sigma2 = 20;

    private double sigma3 = 12;

    private double d;

    private double w;

    private double minWeight = 1.0;

    private double maxWeight = 20.0;

    private InsertionOperator insertionOperator;

    private RemovalOperator removalOperator;

    private double[] roScores;

    private double[] riScores;

    private double[] roWeights;

    private double[] riWeights;

    private int[] roUsages;

    private int[] riUsages;

    private Random random;

    private double rho = 0.1; //η

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    public ALNS(String rootDirectory, String fileName, Solution initialSolution, int maxIterations, Random random) {
        this.rootDirectory = rootDirectory;
        this.fileName = fileName;
        this.maxIterations = maxIterations;
        this.random = random;

        this.solution = initialSolution.copy();
        this.solutionBest = initialSolution.copy();

        d = solution.totalCost;
        w = 0.05;
        c = 0.9995;
        initialT = (1.0 + w) * d;
        T = initialT;
        minT = (1.0 + w) * d * Math.pow(c, 30000);
    }

    public void execute() {
        this.initProblemInstance();

        this.insertionOperator = new InsertionOperator(instance, random);
        this.removalOperator = new RemovalOperator(instance, random);
        this.relocateRequestOperator = new RelocateRequestOperator(instance, random);
        this.exchangeRequestOperator = new ExchangeRequestOperator(instance);

        roScores = new double[RemovalMethod.values().length];
        roWeights = new double[RemovalMethod.values().length];
        roUsages = new int[RemovalMethod.values().length];

        riScores = new double[InsertionMethod.values().length];
        riWeights = new double[InsertionMethod.values().length];
        riUsages = new int[InsertionMethod.values().length];

        for (int i = 0; i < roWeights.length; i++) {
            roWeights[i] = 1.0;
        }
        for (int i = 0; i < riWeights.length; i++) {
            riWeights[i] = 1.0;
        }

        while (!stopCriteriaMeet()) {
            Solution solutionNew = solution.copy();
            int q = generateRandomQ();
            int ro = selectRemovalOperator();
            int ri = selectInsertionOperator();
            List<Req> removedRequests = removeRequests(solutionNew, ro, q);
            insertRequests(solutionNew, ri, removedRequests);
            instance.restrictionsEvaluation(solutionNew);
            if (solutionNew.totalCost < solutionBest.totalCost) {
                solution = applyImprovement(solutionNew);
                updateBest(solution);
                updateScores(ro, ri, sigma1);
            } else if (solutionNew.totalCost < solution.totalCost) {
                solution = solutionNew;
                updateScores(ro, ri, sigma2);
            } else if (accept(solutionNew, solution)) {
                solution = solutionNew;
                updateScores(ro, ri, sigma3);
            }
            T = T * c;
            if (T < minT) {
                T = initialT;
            }
            if (endOfSegment()) {
                updateWeights();
                resetOperatorsScores();
                solution = applyImprovement(solution);
                updateBest(solution);
                System.out.println("Iter " + iteration + " BFS = " + solution.totalCost + ", feasible = " + solution.feasible);
            }
            iteration++;
        }
        printFinalRoute();
    }

    private void updateBest(Solution solution) {
        if (solution.feasible && solution.totalCost < solutionBest.totalCost) {
            solutionBest = solution.copy();
            System.out.println("NEW BEST = Iter " + iteration + " BFS = " + solutionBest.totalCost + ", feasible = " + solutionBest.feasible);
        }
    }

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
            riWeights[ri] = (1.0 - rho) * riWeights[ri] + rho * (riUsages[ri] / segment) * riScores[ri];
            if (riWeights[ri] < minWeight) {
                riWeights[ri] = minWeight;
            }
            if (riWeights[ri] > maxWeight) {
                riWeights[ri] = maxWeight;
            }
        }
        for (int ro = 0; ro < roWeights.length; ro++) {
            roWeights[ro] = (1.0 - rho) * roWeights[ro] + rho * (roUsages[ro] / segment) * roScores[ro];
            if (roWeights[ro] < minWeight) {
                roWeights[ro] = minWeight;
            }
            if (roWeights[ro] > maxWeight) {
                roWeights[ro] = maxWeight;
            }
        }
    }

    private boolean endOfSegment() {
        return iteration % segment == 0;
    }

    private boolean accept(Solution newSolution, Solution solution) {
        return random.nextDouble() < Math.pow(Math.E, -(newSolution.totalCost - solution.totalCost) / T);
    }

    private void updateScores(int ro, int ri, double sigma) {
        // The score must be divivid accordingly Ropke (pg 8)
        roScores[ro] = roScores[ro] + (sigma / 2.0);
        riScores[ri] = riScores[ri] + (sigma / 2.0);
    }

    private Solution applyImprovement(Solution solution) {
        boolean improvement = true;
        Solution improved = solution.copy();
        while (improvement) {
            improvement = false;
            improved = relocateRequestOperator.relocate(improved);
            improved = exchangeRequestOperator.exchange(improved);
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

    private int generateRandomQ() {
        int min = (int) Math.min(6, 0.15 * instance.noReq);
        int max = (int) Math.min(18, 0.4 * instance.noReq) + 1;
        return min + (int) (random.nextDouble() * (max - min));
    }

    private int selectRemovalOperator() {
        int ro = getNextRouletteWheelOperator(roWeights);
        roUsages[ro] = roUsages[ro] + 1;
        return ro;
    }

    private int selectInsertionOperator() {
        int ri = getNextRouletteWheelOperator(riWeights);
        riUsages[ri] = riUsages[ri] + 1;
        return ri;
    }

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
            double rand = random.nextDouble() * sum;
            while (rand > probs[count]) {
                count++;
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
