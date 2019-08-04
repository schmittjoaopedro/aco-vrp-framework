package com.github.schmittjoaopedro.vrp.thesis.tc;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CostMinimizer {

    private Instance instance;

    private Random random;

    private Solution feasibleSolutionBest;

    private Solution solutionBest;

    private Solution solution;

    private InsertionHeuristic insertionHeuristic;

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

    private Set<Integer> visitedList = new HashSet<>();

    private double T;

    private double initialCost;

    private double initialT;

    private double minT;

    private double tolerance = 0.05; // (w)

    private double coolingRate = 0.99975; // reduction factor of acceptance methods (c)

    private double sigma1 = 33; // reward for finding a new global best solution

    private double sigma2 = 9; // reward for finding an improving, not global best, solution

    private double sigma3 = 13; // reward for finding an accepted non-improving solution

    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)

    private double segment = 100.0;

    private RemovalOperator removalOperator;

    private InsertionOperator insertionOperator;

    private LocalSearch localSearch;

    public CostMinimizer(Instance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionHeuristic = new InsertionHeuristic(instance);
        this.removalOperator = new RemovalOperator(instance, random);
        this.insertionOperator = new InsertionOperator(instance, random);
        this.localSearch = new LocalSearch(instance);
    }

    public void init() {
        solution = insertionHeuristic.createInitialSolution();
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        feasibleSolutionBest = SolutionUtils.copy(solution);
        resetAdaptiveWeight();
        visitedList.clear();
        calcTemp(solution.totalCost);
        solutionBest = SolutionUtils.copy(solution);
    }

    public void optimize(int iteration) {
        Solution solutionNew = SolutionUtils.copy(solution);
        int q = generateRandomQ();

        int ro = getNextRouletteWheelOperator(roProbsSum, roProbs);
        int ri = getNextRouletteWheelOperator(riProbsSum, riProbs);
        int useNoise = getNextRouletteWheelOperator(noiseProbsSum, noiseProbs);

        roUsages[ro] = roUsages[ro] + 1;
        riUsages[ri] = riUsages[ri] + 1;
        noiseUsages[useNoise] = noiseUsages[useNoise] + 1;

        removeRequests(solutionNew, ro, q);
        insertRequests(solutionNew, ri, useNoise);
        if (solutionNew.feasible) {
            SolutionUtils.removeEmptyVehicles(solutionNew);
        }
        instance.solutionEvaluation(solutionNew);

        int solutionHash = solutionNew.getHash();
        if (!visitedList.contains(solutionHash)) {
            visitedList.add(solutionHash);
            if (accept(solutionNew, solution)) {
                if (SolutionUtils.getBest(solutionBest, solutionNew) == solutionNew) {
                    solutionNew = applyImprovement(solutionNew);
                    solutionBest = SolutionUtils.copy(solutionNew);
                    updateScores(ro, ri, useNoise, sigma1);
                } else if (SolutionUtils.getBest(solution, solutionNew) == solutionNew) {
                    updateScores(ro, ri, useNoise, sigma2);
                } else {
                    updateScores(ro, ri, useNoise, sigma3);
                }
                solution = solutionNew;
            }
        }
        if (solutionBest.totalCost < feasibleSolutionBest.totalCost) {
            feasibleSolutionBest = SolutionUtils.copy(solutionBest);
        }
        T = T * coolingRate;
        if (T < minT) {
            resetAdaptiveWeight();
            T = initialT;
        }
        if (iteration % segment == 0) {
            updateWeights();
            //solution = applyImprovement(solution);
            if (SolutionUtils.getBest(solution, solutionBest) == solution) {
                solutionBest = SolutionUtils.copy(solution);
            }
        }
    }

    public void setNewBaseSolution(Solution solution) {
        this.feasibleSolutionBest = SolutionUtils.copy(solution);
        this.solutionBest = SolutionUtils.copy(solution);
        this.solution = SolutionUtils.copy(solution);
        calcTemp(solution.totalCost);
    }

    private Solution applyImprovement(Solution solution) {
        boolean improvement = true;
        Solution improved = SolutionUtils.copy(solution);
        Solution tempSolution = improved;
        while (improvement) {
            improvement = false;
            tempSolution = localSearch.exchange(tempSolution);
            tempSolution = localSearch.relocate(tempSolution);
            if (SolutionUtils.getBest(improved, tempSolution) == tempSolution) {
                improved = SolutionUtils.copy(tempSolution);
                improvement = true;
            }
        }
        instance.solutionEvaluation(improved);
        return improved;
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

    private double updateWeightsProbabilities(double[] weights, double[] probs) {
        double sum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            probs[i] = sum + weights[i];
            sum = probs[i];
        }
        return sum;
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

    private void updateScores(int ro, int ri, int useNoise, double sigma) {
        roScores[ro] = roScores[ro] + sigma;
        riScores[ri] = riScores[ri] + sigma;
        noiseScores[useNoise] = noiseScores[useNoise] + sigma;
    }

    private boolean accept(Solution newSolution, Solution oldSolution) {
        double obj = oldSolution.totalCost;
        double objNew = newSolution.totalCost;
        if (objNew <= obj) return true;
        double probability = Math.exp((obj - objNew) / T);
        return (random.nextDouble() <= probability);
    }

    private void insertRequests(Solution solution, int ri, int useNoise) {
        List<Request> removedRequests = getDetachedRequests(solution);
        // TODO: Verificar uso de ruído
        switch (ri) {
            case 0:
                insertionOperator.insertGreedyRequests(solution, removedRequests, useNoise);
                break;
            case 1:
                insertionOperator.insertRegretRequests(solution, removedRequests, 2, useNoise);
                break;
            case 2:
                insertionOperator.insertRegretRequests(solution, removedRequests, 3, useNoise);
                break;
            case 3:
                insertionOperator.insertRegretRequests(solution, removedRequests, 4, useNoise);
                break;
            case 4:
                insertionOperator.insertRegretRequests(solution, removedRequests, solution.tours.size(), useNoise);
                break;
        }
    }

    @NotNull
    private List<Request> getDetachedRequests(Solution solution) {
        List<Request> removedRequests = new ArrayList<>();
        for (Request request : instance.requests) {
            if (!solution.visitedRequests[request.requestId]) {
                removedRequests.add(request); // TODO: Considerar o status da requisição para identificação dos veículos
            }
        }
        return removedRequests;
    }

    private void removeRequests(Solution solution, int ro, int q) {
        List<Request> removedRequests = null;
        switch (ro) {
            case 0:
                removedRequests = removalOperator.removeRandomRequest(solution, q);
                break;
            case 1:
                removedRequests = removalOperator.removeShawRequests(solution, q);
                break;
            case 2:
                removedRequests = removalOperator.removeExpensiveRequests(solution, q);
                break;
            case 3:
                removedRequests = removalOperator.removeExpensiveNodes(solution, q);
                break;
        }
        for (Request req : removedRequests) {
            solution.visitedRequests[req.requestId] = false;
            solution.visited[req.pickupTask.nodeId] = false; // TODO: Considerar o status da requisição para identificação dos veículos
            solution.visited[req.deliveryTask.nodeId] = false;
        }
    }

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

    private int generateRandomQ() {
        int min = dMin(instance.numRequests);
        int max = dMax(instance.numRequests);
        return min + (int) (random.nextDouble() * (max - min));
    }

    private int dMin(double n) {
        return (int) Math.max(1, Math.min(6, 0.15 * n));
    }

    private int dMax(double n) {
        return (int) Math.min(18, 0.4 * n);
    }

    private void calcTemp(double totalCost) {
        initialCost = totalCost;
        initialT = getInitialT();
        T = initialT;
        minT = initialT * Math.pow(coolingRate, 30000);
    }

    private double getInitialT() {
        return (initialCost * tolerance) / Math.log(2);
    }

    private void resetAdaptiveWeight() {
        roWeights = new double[]{1, 1, 1, 1};
        roScores = new double[]{0, 0, 0, 0};
        roUsages = new double[]{0, 0, 0, 0};
        roProbs = new double[]{0, 0, 0, 0};
        roProbsSum = updateWeightsProbabilities(roWeights, roProbs);

        riWeights = new double[]{1, 1, 1, 1, 1};
        riScores = new double[]{0, 0, 0, 0, 0};
        riUsages = new double[]{0, 0, 0, 0, 0};
        riProbs = new double[]{0, 0, 0, 0, 0};
        riProbsSum = updateWeightsProbabilities(riWeights, riProbs);

        noiseWeights = new double[]{1, 1};
        noiseScores = new double[]{0, 0};
        noiseUsages = new double[]{0, 0};
        noiseProbs = new double[]{0, 0};
        noiseProbsSum = updateWeightsProbabilities(noiseWeights, noiseProbs);
    }

    public Solution getFeasibleSolutionBest() {
        return feasibleSolutionBest;
    }
}
