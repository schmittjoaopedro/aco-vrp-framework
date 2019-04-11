package com.github.schmittjoaopedro.vrp.mpdptw.aco;

import com.github.schmittjoaopedro.vrp.mpdptw.MMAS;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;

import java.util.Random;

public interface SolutionBuilder {

    void init(ProblemInstance instance, Random random, MMAS mmas);

    void constructSolutions();

    void onSearchControlExecute();

}
