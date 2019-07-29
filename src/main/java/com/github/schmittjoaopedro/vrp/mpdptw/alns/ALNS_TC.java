package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.GreedyInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.*;

import java.util.Random;

public class ALNS_TC extends ALNS_BASE {

    double removeControl = 0.4;

    public ALNS_TC(ProblemInstance instance, Random random) {
        super(instance, random);
    }

    public void init() {
        this.setInsertionOperators(new InsertionOperator[]{
                new GreedyInsertion(instance, random, true),
                new RegretInsertion(instance, random, "3", false),
                new RegretInsertion(instance, random, "k", false),
                new RegretInsertion(instance, random, "3", true),
                new RegretInsertion(instance, random, "k", true)
        });
        this.setRemovalOperators(new RemovalOperator[]{
                new RandomRemoval(random, instance),
                new ShawRemoval(random, instance),
                new ExpensiveNodeRemoval(random, instance),
                new ExpensiveRequestRemoval(random, instance)
        });
        noiseScores = new double[2];
        noiseWeights = new double[2];
        noiseUsages = new double[2];
        noiseProbs = new double[2];

        parameters = new Parameters();
        parameters.noiseControl = 0.025;
        parameters.rho = 0.1;
        parameters.coolingRate = 0.9995;
        parameters.segment = 100;
        parameters.sigma1 = 33;
        parameters.sigma2 = 20;
        parameters.sigma3 = 13;
        parameters.minWeight = 1.0;
        parameters.acceptMethod = "SA";
        parameters.tolerance = 0.05;

        resetWeights();
    }

    public void setTemperature(Solution solutionBest) {
        parameters.initialCost = solutionBest.totalCost;
        parameters.initialT = (parameters.initialCost * parameters.tolerance) / Math.log(2);
        setT(parameters.initialT);
        parameters.minT = parameters.initialT * Math.pow(parameters.coolingRate, 30000);
        setGlobalSolution(solutionBest);
    }

    protected int generateRandomQ() {
        // Based on experiments evaluated by Naccache (2018) (Table 3).
        int min = dMin(instance.getNumReq());
        int max = dMax(instance.getNumReq());
        return min + (int) (random.nextDouble() * (max - min));
    }

    protected int dMin(double n) {
        return (int) Math.max(1, Math.min(6, 0.15 * n));
    }

    protected int dMax(double n) {
        return (int) Math.min(18, 0.4 * n);
    }

    public void performIteration(int iteration) {
        solutionNew = SolutionUtils.copy(solution); // S' <- S
        int q = generateRandomQ(); // q <- Generate a Random number of requests to remove

        /*
         * Request removal and insertion operators ro and io are randomly inserted from set RO and IO using independent
         * roulette wheels based on the score of each operator.
         */
        int useNoise = getNextRouletteWheelOperator(noiseProbsSum, noiseProbs);
        int ro = getNextRouletteWheelOperator(roProbsSum, roProbs); // ro <- Select and operator from RO (Section 3.2) using a roulette wheel based on the weight of the operators.
        int ri = getNextRouletteWheelOperator(riProbsSum, riProbs); // ri <- Select and operator from IO (Section 3.3) using a roulette wheel based on the weight of the operators.

        roUsages[ro] = roUsages[ro] + 1;
        riUsages[ri] = riUsages[ri] + 1;
        noiseUsages[useNoise] = noiseUsages[useNoise] + 1;

        removeRequests(solutionNew, ro, q); // Remove q requests from S' using ro
        insertRequests(solutionNew, ri, useNoise); // Insert removed requests into S' by applying io using a random pickup insertion method (Section 3.3.1)
        SolutionUtils.removeEmptyVehicles(solutionNew);
        instance.solutionEvaluation(solutionNew); // Update the solution cost

        detailedStatistics.s_best_TC = solutionBest.totalCost;
        detailedStatistics.s_best_NV = solutionBest.tours.size();
        detailedStatistics.s_current_TC = solution.totalCost;
        detailedStatistics.s_current_NV = solution.tours.size();
        detailedStatistics.s_new_TC = solutionNew.totalCost;
        detailedStatistics.s_new_NV = solutionNew.tours.size();

        int solutionHash = SolutionUtils.getHash(solutionNew);
        if (!hashNumber.contains(solutionHash)) { // If not know solution on TABU-list
            hashNumber.add(solutionHash);
            if (accept(solutionNew, solution)) { // if accept(S', S) then
                if (instance.getBest(solutionBest, solutionNew) == solutionNew) { // If f(S') < f(S_best) then
                    if (localSearch != null) {
                        solutionNew = localSearch.apply(solutionNew); // Apply improvement (Section 3.4) to S'
                    }
                    updateBest(solutionNew); // S_best <- S <- S'
                    // Increase the scores of io and ro by sigma1
                    updateScores(ro, ri, useNoise, parameters.sigma1); // Increment by sigma1 if the new solution is a new best one
                    detailedStatistics.bsfAcceptCount++;
                } else if (instance.getBest(solution, solutionNew) == solutionNew) { // Else if f(S') < f(S) then
                    // Increase the scores of the ro and io by sigma2
                    updateScores(ro, ri, useNoise, parameters.sigma2);  // Increment by sigma2 if the new solution is better than the previous one
                    detailedStatistics.currentAcceptCount++;
                } else {
                    // Increase the scores of the ro and io by sigma3
                    updateScores(ro, ri, useNoise, parameters.sigma3); // Increment by sigma3 if the new solution is not better but still accepted
                    detailedStatistics.worstAcceptCount++;
                }
                solution = solutionNew; // S <- S'
            }
        }

        /*
         * T is the temperature that decreases at each iteration according to a standard exponential cooling rate.
         * When the temperature reaches a minimum threshold, it is set to it's initial value (reheating). This process
         * allow worse solutions to be more easily accepted and increase the diversity.
         */
        T = T * parameters.coolingRate;
        if (T < parameters.minT) {
            resetWeights();
            T = parameters.initialT;
        }
        if (endOfSegment(iteration)) { // if end of a segment of 𝛿 iterations then
            updateWeights(); // Update weights and reset scores of operators
            if (localSearch != null) {
                solution = localSearch.apply(solution); // Apply improvement to S
            }
            updateBest(solution);
        }
    }

}
