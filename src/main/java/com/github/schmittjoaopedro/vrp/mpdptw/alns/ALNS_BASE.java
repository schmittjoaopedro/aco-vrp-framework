package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.*;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.RemovalOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public abstract class ALNS_BASE {

    protected double initialT;

    protected double minT;

    protected Solution solution;

    protected Solution solutionNew;

    protected Solution solutionBest;

    protected double T;

    protected ProblemInstance instance;

    protected static final int DEFAULT_MAX_ITERATIONS = 100000;

    protected double sigma1;

    protected double sigma2;

    protected double sigma3;

    protected double rho;

    protected String acceptMethod;

    protected double minWeight;

    protected double noiseControl;

    protected int segment;

    protected double initialCost;

    protected double coolingRate;

    protected InsertionOperator[] insertionOperators;

    protected RemovalOperator[] removalOperators;

    // Probabilities calculated for ro's and ri's
    protected double[] roProbs;
    protected double[] riProbs;
    protected double[] noiseProbs;
    protected double roProbsSum;
    protected double riProbsSum;
    protected double noiseProbsSum;

    // Indicates how well an operator has performed in the past
    protected double[] roWeights;
    protected double[] riWeights;
    protected double[] noiseWeights;

    // These are the accumulated scores (PI_i) of each operator during the executiong of segment (delta).
    protected double[] roScores;
    protected double[] riScores;
    protected double[] noiseScores;

    // Number of times that each operator has been used in the last segment j.
    protected double[] roUsages;
    protected double[] riUsages;
    protected double[] noiseUsages;

    protected Random random;

    protected Function<Integer, Integer> dMin;

    protected Function<Integer, Integer> dMax;

    protected Set<Integer> hashNumber = new HashSet<>();

    protected Function<Solution, Solution> applyImprovement;

    protected DetailedStatistics detailedStatistics = new DetailedStatistics();

    public ALNS_BASE(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionOperators = new InsertionOperator[5];
        noiseScores = new double[2];
        noiseWeights = new double[2];
        noiseUsages = new double[2];
        noiseProbs = new double[2];
    }

    public Solution getSolutionBest() {
        return solutionBest;
    }

    public Solution getSolutionNew() {
        return solutionNew;
    }

    public void setGlobalSolution(Solution solution) {
        this.solutionBest = this.solution = solution;
    }

    /*
     * Based on experiments evaluated by Naccache (2018) (Table 3).
     */
    protected int generateRandomQ() {
        int min = dMin.apply(instance.getNumReq());
        int max = dMax.apply(instance.getNumReq());
        return min + (int) (random.nextDouble() * (max - min));
    }

    /*
     * Given h operators with weights w_i, operator j will be selected with probability w_j / sum_{i=1}_{h} w_i
     */
    protected int getNextRouletteWheelOperator(double sum, double[] probs) {
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

    public void setInsertionOperators(InsertionOperator[] insertionOperators) {
        this.insertionOperators = insertionOperators;
        for (InsertionOperator insertionOperator : insertionOperators) {
            insertionOperator.setUseNoiseAtHeuristic(noiseControl);
        }
        riScores = new double[insertionOperators.length];
        riWeights = new double[insertionOperators.length];
        riUsages = new double[insertionOperators.length];
        riProbs = new double[insertionOperators.length];
    }

    public void setRemovalOperators(RemovalOperator[] removalOperators) {
        this.removalOperators = removalOperators;
        roScores = new double[removalOperators.length];
        roWeights = new double[removalOperators.length];
        roUsages = new double[removalOperators.length];
        roProbs = new double[removalOperators.length];
    }

    public void resetWeights() {
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

    private double updateWeightsProbabilities(double[] weights, double[] probs) {
        double sum = 0.0;
        for (int i = 0; i < weights.length; i++) {
            probs[i] = sum + weights[i];
            sum = probs[i];
        }
        return sum;
    }

    protected void updateScores(int ro, int ri, int useNoise, double sigma) {
        roScores[ro] = roScores[ro] + sigma;
        riScores[ri] = riScores[ri] + sigma;
        noiseScores[useNoise] = noiseScores[useNoise] + sigma;
    }

    protected void updateWeights() {
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

    protected void removeRequests(Solution solution, int ro, int q) {
        List<Req> removedRequests = removalOperators[ro].removeRequests(solution, q);
        for (Req req : removedRequests) {
            solution.visitedRequests[req.requestId] = false;
            for (Request pickup : instance.getPickups(req.requestId)) {
                solution.visited[pickup.nodeId] = false;
            }
            solution.visited[instance.getDelivery(req.requestId).nodeId] = false;
        }
    }

    protected void insertRequests(Solution solution, int ri, int useNoise) {
        ArrayList<Req> removedRequests = new ArrayList<>();
        for (int i = 0; i < solution.visitedRequests.length; i++) {
            if (!solution.visitedRequests[i]) {
                removedRequests.add(new Req(findVehicle(i, solution), i));
            }
        }
        // Use random pickup method
        insertionOperators[ri].insertRequests(solution, removedRequests, InsertionMethod.PickupMethod.Random, useNoise);
    }

    protected int findVehicle(Integer reqId, Solution solution) {
        for (int k = 0; k < solution.requests.size(); k++) {
            if (solution.requests.get(k).contains(reqId)) {
                return k;
            }
        }
        return -1;
    }

    protected void updateBest(Solution solution) {
        updateBest(solution, false);
    }

    protected void updateBest(Solution solution, boolean force) {
        if (force || (solution.feasible && solution.totalCost < solutionBest.totalCost)) {
            solutionBest = SolutionUtils.copy(solution);
        }
    }

    protected boolean endOfSegment(int iteration) {
        return iteration % segment == 0;
    }


    public void logDetailedStatistics(int iteration) {
        detailedStatistics.costEvaluationCount = instance.getCostEvaluationCount();
        detailedStatistics.partialEvaluationCount = instance.getPartialEvaluationCount();
        detailedStatistics.fullEvaluationCount = instance.getFullEvaluationCount();
        detailedStatistics.hash_solution_count = hashNumber.size();
        detailedStatistics.noise_false = noiseWeights[0];
        detailedStatistics.noise_true = noiseWeights[1];
        detailedStatistics.insertMethodNames = new String[insertionOperators.length];
        detailedStatistics.insertMethodWeights = new double[insertionOperators.length];
        for (int i = 0; i < insertionOperators.length; i++) {
            detailedStatistics.insertMethodNames[i] = insertionOperators[i].toString();
            detailedStatistics.insertMethodWeights[i] = riWeights[i];
        }
        detailedStatistics.removalMethodNames = new String[removalOperators.length];
        detailedStatistics.removalMethodWeights = new double[removalOperators.length];
        for (int i = 0; i < removalOperators.length; i++) {
            detailedStatistics.removalMethodNames[i] = removalOperators[i].toString();
            detailedStatistics.removalMethodWeights[i] = roWeights[i];
        }
        detailedStatistics.temperature = T;
        try {
            StringBuilder text = new StringBuilder();
            Field[] fields = DetailedStatistics.class.getFields();
            // Log file header
            if (iteration == 1) {
                String[] fieldNames = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fieldNames[i] = fields[i].getName();
                }
                text.append(StringUtils.join(fieldNames, ';')).append('\n');
            }
            String[] values = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                values[i] = String.valueOf(fields[i].get(detailedStatistics)).replaceAll(",", ".");
            }
            text.append(StringUtils.join(values, ';')).append('\n');
            FileUtils.writeStringToFile(new File("C:\\Temp\\detailed-" + instance.getFileName().replaceAll(".txt", ".csv")), text.toString(), "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setT(double t) {
        T = t;
    }

    public void setMinT(double minT) {
        this.minT = minT;
    }

    public void setSigma1(double sigma1) {
        this.sigma1 = sigma1;
    }

    public void setSigma2(double sigma2) {
        this.sigma2 = sigma2;
    }

    public void setSigma3(double sigma3) {
        this.sigma3 = sigma3;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public void setNoiseControl(double noiseControl) {
        this.noiseControl = noiseControl;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public void setInitialCost(double initialCost) {
        this.initialCost = initialCost;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public void setdMin(Function<Integer, Integer> dMin) {
        this.dMin = dMin;
    }

    public void setdMax(Function<Integer, Integer> dMax) {
        this.dMax = dMax;
    }

}
