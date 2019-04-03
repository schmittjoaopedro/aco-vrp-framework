package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 1;

    private static int maxIterations = 10;

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
    public void mpdptw_w_4_100_1_test() {
        Solver solver = new Solver(rootDirectory, "w_4_100_1.txt", maxIterations, seed, 0.8, statisticInterval, true);
        solver.run();
        solver.setParallel(false);
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 16740.24   BSF. SD: 0.00    IT. WORST: 21024.66    IT. BEST: 16740.24    IT. MEAN: 19119.01    IT. SD: 945.86    BRANCH FACTOR: 0.990     DIV: 0.96       NO.PATHS: 20818");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 15683.98   BSF. SD: 0.00    IT. WORST: 21053.15    IT. BEST: 15683.98    IT. MEAN: 18646.75    IT. SD: 1142.90   BRANCH FACTOR: 1.740     DIV: 0.95       NO.PATHS: 37689");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 15284.18   BSF. SD: 0.00    IT. WORST: 20168.05    IT. BEST: 15284.18    IT. MEAN: 18050.46    IT. SD: 1086.96   BRANCH FACTOR: 1.865     DIV: 0.93       NO.PATHS: 54310");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 15284.18   BSF. SD: 0.00    IT. WORST: 20018.04    IT. BEST: 15579.82    IT. MEAN: 17838.58    IT. SD: 988.59    BRANCH FACTOR: 0.990     DIV: 0.91       NO.PATHS: 70232");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 15284.18   BSF. SD: 0.00    IT. WORST: 20553.56    IT. BEST: 15585.43    IT. MEAN: 17738.11    IT. SD: 1082.74   BRANCH FACTOR: 0.990     DIV: 0.90       NO.PATHS: 84899");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 14562.20   BSF. SD: 0.00    IT. WORST: 19501.69    IT. BEST: 14562.20    IT. MEAN: 17564.61    IT. SD: 1114.22   BRANCH FACTOR: 1.721     DIV: 0.90       NO.PATHS: 98282");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 14562.20   BSF. SD: 0.00    IT. WORST: 20102.82    IT. BEST: 15061.82    IT. MEAN: 17668.02    IT. SD: 1062.58   BRANCH FACTOR: 0.990     DIV: 0.91       NO.PATHS: 112866");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 14562.20   BSF. SD: 0.00    IT. WORST: 19298.51    IT. BEST: 15273.61    IT. MEAN: 17480.74    IT. SD: 1044.38   BRANCH FACTOR: 0.990     DIV: 0.90       NO.PATHS: 126570");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 14562.20   BSF. SD: 0.00    IT. WORST: 20845.94    IT. BEST: 15145.02    IT. MEAN: 18015.22    IT. SD: 1063.92   BRANCH FACTOR: 0.990     DIV: 0.91       NO.PATHS: 139516");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 14562.20   BSF. SD: 0.00    IT. WORST: 19624.36    IT. BEST: 15865.56    IT. MEAN: 17712.94    IT. SD: 972.92    BRANCH FACTOR: 0.990     DIV: 0.90       NO.PATHS: 152487");
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
