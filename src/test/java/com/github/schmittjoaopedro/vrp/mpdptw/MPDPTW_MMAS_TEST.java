package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 10;

    private static int maxIterations = 100;

    private static int seed = 1;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_main_problems_test() {
        executeTest("l_4_25_1.txt");
        executeTest("l_8_25_1.txt");
        executeTest("n_4_25_1.txt");
        executeTest("n_8_25_1.txt");
        executeTest("w_4_25_1.txt");
        executeTest("w_8_25_1.txt");
        executeTest("l_4_50_1.txt");
        executeTest("l_8_50_1.txt");
        executeTest("n_4_50_1.txt");
        executeTest("n_8_50_1.txt");
        executeTest("w_4_50_1.txt");
        executeTest("w_8_50_1.txt");
        executeTest("l_4_100_1.txt");
        executeTest("l_8_100_1.txt");
        executeTest("n_4_100_1.txt");
        executeTest("n_8_100_1.txt");
        executeTest("w_4_100_1.txt");
        executeTest("w_8_100_1.txt");
        executeTest("l_4_400_1.txt");
        executeTest("l_8_400_1.txt");
        executeTest("n_4_400_1.txt");
        executeTest("n_8_400_1.txt");
        executeTest("w_4_400_1.txt");
        executeTest("w_8_400_1.txt");

    }

    private void executeTest(String problem) {
        ArrayList<Double> costs = new ArrayList<>();
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> penalty = new ArrayList<>();
        int sampleSize = 1, feasible = 0;
        for (int i = 0; i < sampleSize; i++) {
            Solver solver = new Solver(rootDirectory, problem, maxIterations, i, 0.02, statisticInterval, true);
            solver.run();
            costs.add(solver.getBestSolution().totalCost);
            penalty.add(solver.getBestSolution().timeWindowPenalty);
            time.add(solver.getGlobalStatistics().getTimeStatistics().get("Algorithm").doubleValue());
            if (solver.getBestSolution().feasible) {
                feasible++;
            }
        }
        double meanCosts = Maths.getMean(costs);
        double meanTime = Maths.getMean(time);
        double meanPenalty = Maths.getMean(penalty);
        System.out.println("---------------------------------------");
        System.out.println("Instance = " + problem);
        System.out.println("Mean costs = " + meanCosts);
        System.out.println("Mean penalty = " + meanPenalty);
        System.out.println("Mean time = " + meanTime);
        System.out.println("Feasible = " + feasible + " of " + sampleSize);
        System.out.println("---------------------------------------");
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "w_4_400_1.txt", maxIterations, seed, 0.8, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_4_25_4_test() {
        Solver solver = new Solver(rootDirectory, "n_8_25_3.txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "w_8_400_1.txt", maxIterations, seed, 0.8, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "w_8_100_1.txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_without_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "n_8_100_5.txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "n_8_100_5.txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_400_1_test() {
        Solver solver = new Solver(rootDirectory, "w_8_400_1.txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_test() {
        //for (String noVert : new String[]{"400", "100", "50", "25"}) {
        for (String noVert : new String[]{"25", "50", "100", "400"}) {
            for (String typ : new String[]{"l", "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        execute(typ + "_" + reqSize + "_" + noVert + "_" + id);
                    }
                }
            }
        }
    }

    private void execute(String file) {
        Solver solver = new Solver(rootDirectory, file + ".txt", maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

}
