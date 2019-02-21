package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.coelho.InsertionOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.coelho.RemovalOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.coelho.Req;

import java.util.List;
import java.util.Random;

public class LocalSearchALNS {

    private ProblemInstance instance;

    private Random random;

    private RemovalOperator removalOperator;

    private InsertionOperator insertionOperator;

    private LocalSearchRelocate localSearchRelocate;

    public LocalSearchALNS(ProblemInstance instance, Random random) {
        this.instance = instance;
        this.random = random;
        this.removalOperator = new RemovalOperator(instance, random);
        this.insertionOperator = new InsertionOperator(instance, random);
        this.localSearchRelocate = new LocalSearchRelocate(instance);
    }

    public Ant optimize(Ant ant) {
        Ant tempAnt = AntUtils.createEmptyAnt(instance);
        Ant improvedAnt = AntUtils.createEmptyAnt(instance);
        AntUtils.copyFromTo(ant, tempAnt);
        AntUtils.copyFromTo(ant, improvedAnt);
        instance.restrictionsEvaluation(tempAnt);
        instance.restrictionsEvaluation(improvedAnt);
        boolean improvement = true;
        double oldCost = improvedAnt.totalCost;
        while (improvement) {
            List<Req> removedRequests = removalOperator.removeRequests(tempAnt.tours, tempAnt.requests, generateNoRemovalRequests());
            insertionOperator.insertRequests(tempAnt.tours, tempAnt.requests, removedRequests);
            Ant tempAnt2 = localSearchRelocate.relocate(tempAnt);
            instance.restrictionsEvaluation(tempAnt);
            if (tempAnt2.totalCost < tempAnt.totalCost) {
                tempAnt = tempAnt2;
            }
            improvement = tempAnt.totalCost < improvedAnt.totalCost;
            if (improvement) {
                AntUtils.copyFromTo(tempAnt, improvedAnt);
            }
        }
        if (improvedAnt.totalCost < oldCost) {
            return improvedAnt;
        } else {
            return ant;
        }
    }

    private int generateNoRemovalRequests() {
        int min = (int) Math.min(6, 0.15 * instance.noReq);
        int max = (int) Math.min(18, 0.4 * instance.noReq) + 1;
        return min + (int) (random.nextDouble() * (max - min));
    }
}
