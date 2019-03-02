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

    private int segment = 100;

    private double theta1 = 33;

    private double theta2 = 9;

    private double theta3 = 13;

    private InsertionOperator insertionOperator;

    private RemovalOperator removalOperator;

    private double[] roScores;

    private double[] riScores;

    private double[] roWeights;

    private double[] riWeights;

    private int[] roUsages;

    private int[] riUsages;

    private Random random;

    private double eta = 0.1; //η

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    public ALNS(String rootDirectory, String fileName, double initialT, double minT, double c, Solution initialSolution, int maxIterations, Random random) {
        this.rootDirectory = rootDirectory;
        this.fileName = fileName;
        this.initialT = initialT; // Temperature
        this.solution = initialSolution.copy();
        this.solutionBest = initialSolution.copy();
        this.maxIterations = maxIterations;
        this.c = c; // Colling rate
        this.minT = minT;
        this.random = random;
    }

    public void execute() {
        this.initProblemInstance();

        this.insertionOperator = new InsertionOperator(instance, random);
        this.removalOperator = new RemovalOperator(instance, random);
        this.relocateRequestOperator = new RelocateRequestOperator(instance, random);
        this.exchangeRequestOperator = new ExchangeRequestOperator(instance);

        roUsages = new int[RemovalMethod.values().length];
        roScores = new double[RemovalMethod.values().length];
        roWeights = new double[RemovalMethod.values().length];
        riScores = new double[InsertionMethod.values().length];
        riWeights = new double[InsertionMethod.values().length];
        riUsages = new int[InsertionMethod.values().length];

        T = initialT;

        while (!stopCriteriaMeet()) {
            Solution solutionNew = solution.copy();
            int q = generateRandomQ();
            int ro = selectRemovalOperator();
            int ri = selectInsertionOperator();
            List<Req> removedRequests = removeRequests(solutionNew, ro, q);
            insertRequests(solutionNew, ri, removedRequests);
            instance.restrictionsEvaluation(solutionNew);
            increaseUsages(ro, ri);
            if (solutionNew.totalCost < solutionBest.totalCost) {
                solutionBest = solution = applyImprovement(solutionNew);
                increaseScores(ro, ri, theta1);
            } else if (solutionNew.totalCost < solution.totalCost) {
                solution = solutionNew;
                increaseScores(ro, ri, theta2);
            } else if (accept(solutionNew, solution)) {
                solution = solutionNew;
                increaseScores(ro, ri, theta3);
            }
            T = T * c;
            if (T < minT) {
                T = minT;
            }
            if (endOfSegment()) {
                updateWeights();
                resetOperatorsScores();
                applyImprovement(solution);
                System.out.println("Iter " + iteration + " BFS = " + solution.totalCost + ", feasible = " + solution.feasible);
            }
            iteration++;
        }
        printFinalRoute();
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
            if (riUsages[ri] != 0) {
                riWeights[ri] = (1.0 - eta) * riWeights[ri] + eta * (riScores[ri] / riUsages[ri]);
            }
        }
        for (int ro = 0; ro < roWeights.length; ro++) {
            if (roUsages[ro] != 0) {
                roWeights[ro] = (1.0 - eta) * roWeights[ro] + eta * (roScores[ro] / riUsages[ro]);
            }
        }
    }

    private boolean endOfSegment() {
        return iteration % segment == 0;
    }

    private boolean accept(Solution newSolution, Solution solution) {
        return random.nextDouble() < Math.pow(Math.E, -(newSolution.totalCost - solution.totalCost) / T);
    }

    private void increaseScores(int ro, int ri, double theta) {
        roWeights[ro] = roWeights[ro] + theta;
        riWeights[ri] = riWeights[ri] + theta;
    }

    private void increaseUsages(int ro, int ri) {
        roUsages[ro] = roUsages[ro] + 1;
        riUsages[ri] = riUsages[ri] + 1;
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
        return getNextRouletteWheelOperator(roWeights);
    }

    private int selectInsertionOperator() {
        return getNextRouletteWheelOperator(riWeights);
    }

    private int getNextRouletteWheelOperator(double[] rewards) {
        double sum = 0.0;
        double probs[] = new double[rewards.length];
        for (int i = 0; i < rewards.length; i++) {
            probs[i] = sum + rewards[i];
            sum = probs[i];
        }
        if (sum == 0.0) {
            return (int) (random.nextDouble() * rewards.length);
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
