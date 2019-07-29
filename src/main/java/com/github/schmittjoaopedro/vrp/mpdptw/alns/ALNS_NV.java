package com.github.schmittjoaopedro.vrp.mpdptw.alns;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.SolutionUtils;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.GreedyInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Random;

public class ALNS_NV extends ALNS_BASE {

    double removeControl = 0.4;

    public ALNS_NV(ProblemInstance instance, Random random) {
        super(instance, random);
    }

    public void init() {
        /*this.setInsertionOperators(new InsertionOperator[]{
                new GreedyInsertion(instance, random, true),
                new RegretInsertion(instance, random, "2", false),
                new RegretInsertion(instance, random, "3", false),
                new RegretInsertion(instance, random, "4", false),
                new RegretInsertion(instance, random, "k", false)
        });*/
        this.setInsertionOperators(new InsertionOperator[]{
                new GreedyInsertion(instance, random, true),
                new RegretInsertion(instance, random, "2", false),
                new RegretInsertion(instance, random, "3", false),
                new RegretInsertion(instance, random, "k", false),
                new RegretInsertion(instance, random, "2", true),
                new RegretInsertion(instance, random, "3", true),
                new RegretInsertion(instance, random, "k", true)
        });
        this.setRemovalOperators(new RemovalOperator[]{
                new RandomRemoval(random, instance),
                new ShawRemoval(random, instance),
                new ExpensiveNodeRemoval(random, instance),
                new ExpensiveRequestRemoval(random, instance),
                new RandomVehicleRemoval(random, instance),
                new ExpensiveVehicleRemoval(random, instance)
        });
        noiseScores = new double[2];
        noiseWeights = new double[2];
        noiseUsages = new double[2];
        noiseProbs = new double[2];

        parameters = new Parameters();
        parameters.noiseControl = 0.025;
        parameters.rho = 0.1;
        parameters.coolingRate = 0.9999;
        parameters.segment = 100;
        parameters.sigma1 = 33;
        parameters.sigma2 = 20;
        parameters.sigma3 = 13;
        parameters.minWeight = 1.0;
        parameters.acceptMethod = "SA";
        parameters.tolerance = 0.35;

        resetWeights();
    }

    public void setTemperature(Solution initialSolution) {
        parameters.initialCost = initialSolution.totalCost;
        parameters.initialT = (parameters.initialCost * parameters.tolerance) / Math.log(2);
        setT(parameters.initialT);
        parameters.minT = parameters.initialT * Math.pow(parameters.coolingRate, 30000);
    }

    protected int generateRandomQ() {
        return (int) (random.nextDouble() * Math.min(100, (int) (removeControl * (instance.getNumReq() / 2))));
    }

    public void performIteration(int iteration) {
        solutionNew = SolutionUtils.copy(solution); // S' <- S
        int q = 0;
        while (q < 4) {
            q = generateRandomQ(); // q <- Generate a Random number of requests to remove
        }

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
        instance.solutionEvaluation(solutionNew);
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
                } else if (instance.getBestNv(solution, solutionNew) == solutionNew) { // Else if f(S') < f(S) then
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

            //System.out.println("q = " + q + ", ro = " + ro + ", ri = " + ri + ", ns = " + useNoise);
            ArrayList<Integer> removedRequests = new ArrayList<>();
            for (int i = 0; i < solution.visitedRequests.length; i++) {
                if (!solution.visitedRequests[i]) {
                    removedRequests.add(i);
                }
            }
            System.out.println(iteration + " |sigma| = " + solution.tours.size() + ", |r|^2 = " + instance.getTourSquare(solution) + ", t(r) = " + Maths.round(solution.totalCost, 2) + ", BSF " + solution + " T = " + Maths.round(T, 1) + "->" + Maths.round(parameters.minT, 1) + " Reqs -> " + StringUtils.join(removedRequests));

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
            //printIterationStatus();
            updateBest(solution);
        }

        if (solution.feasible) {
            solutionBest = SolutionUtils.copy(solution);
            int vehicle = solution.requests.size() - 1;
            solution.requests.get(vehicle).clear();
            SolutionUtils.removeEmptyVehicles(solution);
            instance.solutionEvaluation(solution);
            setTemperature(solution);
        }
    }


    protected boolean accept(Solution newSolution, Solution oldSolution) {
        if (instance.getBestNv(oldSolution, newSolution) == newSolution) {
            return true;
        } else if ("SA".equals(parameters.acceptMethod)) {
            // The acceptance criterion is such as that a candidate solution S' is accepted given the current solution S
            // with a probability e^-(f(S')-f(S))/T.
            return random.nextDouble() <= Math.exp(instance.getNvDiff(oldSolution, newSolution) / T);
        } else if ("TA".equals(parameters.acceptMethod)) {
            return instance.getNvDiff(oldSolution, newSolution) < T;
        } else {
            return false;
        }
    }

}
