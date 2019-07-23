package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.RandomRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.ExchangeRequestOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateRequestOperator;

import java.util.List;
import java.util.Random;

public class LocalSearch {

    private ProblemInstance instance;

    private Random random;

    private InsertionOperator insertionOperator;

    private RelocateNodeOperator relocateNodeOperator;

    private RelocateRequestOperator relocateRequestOperator;

    private ExchangeRequestOperator exchangeRequestOperator;

    public LocalSearch(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.insertionOperator = new InsertionOperator(instance, random);
        this.relocateRequestOperator = new RelocateRequestOperator(instance, random);
        this.relocateNodeOperator = new RelocateNodeOperator(instance);
        this.exchangeRequestOperator = new ExchangeRequestOperator(instance, random);
    }

    public Solution optimize(Solution ant) {
        Solution tempAnt = ant;
        boolean improvement = true;
        double oldCost = ant.totalCost + ant.timeWindowPenalty;
        double newCost;
        while (improvement) {
            improvement = false;
            tempAnt = relocateNodeOperator.relocate(tempAnt);
            tempAnt = optimizeInternal(tempAnt);
            tempAnt = relocateRequestOperator.relocate(tempAnt);
            tempAnt = exchangeRequestOperator.exchange(tempAnt);
            newCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
            if (newCost < oldCost) {
                oldCost = tempAnt.totalCost + tempAnt.timeWindowPenalty;
                improvement = true;
            }
        }
        return tempAnt;
    }

    public Solution optimizeInternal(Solution ant) {
        Solution tempAnt = SolutionUtils.createNewSolution(instance);
        Solution improvedAnt = SolutionUtils.createNewSolution(instance);
        SolutionUtils.copyFromTo(ant, tempAnt);
        SolutionUtils.copyFromTo(ant, improvedAnt);
        boolean improvement = true;
        boolean improved = false;
        while (improvement) {
            List<Req> removedRequests = new RandomRemoval(random, instance).removeRequests(tempAnt, generateNoRemovalRequests());
            insertionOperator.insertRequests(tempAnt, removedRequests, InsertionMethod.PickupMethod.Random, 0);
            instance.solutionEvaluation(tempAnt);
            SolutionUtils.removeEmptyVehicles(tempAnt);
            improvement = instance.getBest(improvedAnt, tempAnt) == tempAnt;
            if (improvement) {
                SolutionUtils.copyFromTo(tempAnt, improvedAnt);
                improved = true;
            }
        }
        if (improved) {
            return improvedAnt;
        } else {
            return ant;
        }
    }

    private int generateNoRemovalRequests() {
        int min = (int) Math.min(6, 0.15 * instance.getNumReq());
        int max = (int) Math.min(18, 0.4 * instance.getNumReq()) + 1;
        return min + (int) (random.nextDouble() * (max - min));
    }
}
