package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.Solution;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

public class MPDPTW_ALNS_TEST {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        String problemFile = "l_4_25_1.txt";
        Solver solver = new Solver(rootDirectory, problemFile, 1, 1, 0.02, 1, true);
        solver.run();
        Ant bestSolution = solver.getBestSolution();

        Solution solution = new Solution();
        for (int i = 0; i < bestSolution.tours.size(); i++) {
            solution.tours.add(i, bestSolution.tours.get(i));
            solution.requests.add(i, bestSolution.requests.get(i));
            solution.tourLengths.add(i, bestSolution.tourLengths.get(i));
        }
        solution.totalCost = bestSolution.totalCost;
        solution.feasible = bestSolution.feasible;

        double d = solution.totalCost;
        double w = 0.05;
        double initialT = (1.0 + w) * d;
        double c = 0.9995;
        double minT = (1.0 + w) * d * Math.pow(c, 30000);
        int maxIterations = 100000;
        ALNS alns = new ALNS(rootDirectory, problemFile, initialT, minT, c, solution, maxIterations, new Random(1));
        alns.execute();
    }

}
