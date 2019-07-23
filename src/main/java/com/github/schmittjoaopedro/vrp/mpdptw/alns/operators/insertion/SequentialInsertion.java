package com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion;

import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.Req;

import java.util.List;
import java.util.Random;

public class SequentialInsertion extends GreedyInsertion {

    public SequentialInsertion(ProblemInstance instance, Random random) {
        super(instance, random);
    }

    public void insertRequests(Solution solution, List<Req> requestsToInsert) {
        super.insertRequests(solution, requestsToInsert, PickupMethod.Random, 0);
    }
}
