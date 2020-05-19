package com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.InsertionOperator;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.StopCriteria;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class MetaHeuristic {

    private double initialTemperature;

    private double temperature;

    private double coolingRatio;

    private int MSNI;

    private Instance instance;

    private TABU tabu;

    private DescentLocalSearch descentLocalSearch;

    private Random random;

    private InsertionOperator insertionOperator;

    private StopCriteria stopCriteria;

    public MetaHeuristic(double initialTemperature, double coolingRatio, int MSNI, Instance instance, Random random, StopCriteria stopCriteria) {
        this.initialTemperature = initialTemperature;
        this.coolingRatio = coolingRatio;
        this.MSNI = MSNI;
        this.instance = instance;
        this.tabu = new TABU();
        this.descentLocalSearch = new DescentLocalSearch(instance, stopCriteria);
        this.random = random;
        this.insertionOperator = new RegretInsertion(random, instance, (sol, inst) -> 1);
        this.stopCriteria = stopCriteria;
    }

    public Solution tabuEmbeddedSa(Solution solutionBase) {
        Solution solutionBest = SolutionUtils.copy(solutionBase);
        int noImprovements = 0;
        temperature = initialTemperature;
        tabu.init(instance);
        solutionBest = descentLocalSearch.findBestByShiftAndExchange(solutionBest);
        solutionBest = descentLocalSearch.executeReArrange(solutionBest);
        Solution solution = SolutionUtils.copy(solutionBest);
        while (noImprovements < MSNI && stopCriteria.isContinue()) {
            solution = metropolisProcedure(solution);
            solution = reduceVehiclesNumber(solution);
            solution = descentLocalSearch.findBestByShiftAndExchange(solution);
            solution = descentLocalSearch.executeReArrange(solution);
            if (SolutionUtils.getBest(solution, solutionBest) == solution) {
                solutionBest = solution;
                solution = SolutionUtils.copy(solutionBest);
                noImprovements = 0;
            } else {
                noImprovements++;
            }
        }
        return solutionBest;
    }

    public Solution metropolisProcedure(Solution solutionBase) {
        Solution solution = SolutionUtils.copy(solutionBase);
        double delta = 0;
        double probability = 0;
        LinkedList<Solution> neighborhood = descentLocalSearch.findNeighborByShiftAndExchange(solutionBase);
        while (true && stopCriteria.isContinue()) {
            // S' <- get a random neighbor of S which is not in tabu set, within nPDS(S') U nPDE(S')
            Collections.shuffle(neighborhood, random);
            while (!neighborhood.isEmpty()) {
                solution = neighborhood.pollLast();
                if (!tabu.exist(getEigenValue(solution))) {
                    break;
                }
            }
            if (neighborhood.isEmpty()) {
                solution = solutionBase;
                break;
            }
            // \delta <- SACost(S') - SACost(S)
            delta = saCost(solution) - saCost(solutionBase);
            if (delta <= 0) {
                probability = 1.0;
            } else {
                probability = Math.pow(Math.E, -(delta / temperature));
            }
            if (random.nextDouble() <= probability) {
                // Update global annealing temperature: T <- collingRation * temperature
                temperature = coolingRatio * temperature;
                if (temperature == 0) {
                    temperature = 1.0;
                }
                // Record S' into TABU set
                tabu.offerAndPoll(getEigenValue(solution));
                break;
            }
        }
        return solution;
    }

    private double saCost(Solution solution) {
        //return solution.totalCost + 0.01 * solution.totalCost * solution.totalWaitingTime;
        return solution.totalCost;
    }

    private EigenValue getEigenValue(Solution solution) {
        return new EigenValue(
                solution.tours.size(),
                (int) solution.totalCost,
                (int) solution.totalScheduleDuration,
                (int) solution.totalWaitingTime
        );
    }

    public Solution reduceVehiclesNumber(Solution solutionBase) {
        Solution solution = solutionBase;
        Solution solutionNew = SolutionUtils.copy(solutionBase);
        for (int k = 0; k < solutionBase.tours.size(); k++) {
            solutionNew.remove(solutionBase.requestIds.get(k), instance);
            SolutionUtils.removeEmptyVehicles(solutionNew);
            instance.solutionEvaluation(solutionNew);
            insertionOperator.insert(solutionNew, 0);
            if (SolutionUtils.allRequestsAreAttended(solutionNew)) {
                solution = solutionNew;
                break;
            } else {
                solutionNew = SolutionUtils.copy(solutionBase);
            }
        }
        return solution;
    }
}
