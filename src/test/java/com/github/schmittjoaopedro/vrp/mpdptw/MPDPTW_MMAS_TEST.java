package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SequentialInfeasible;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 10;

    private static int maxIterations = 100;

    private static int seed = 1;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_main_problems_test() throws IOException {

        executeTest("n_4_25_1.txt");
        executeTest("n_4_50_1.txt");
        executeTest("n_4_100_1.txt");
        executeTest("n_4_400_1.txt");

        executeTest("n_8_25_1.txt");
        executeTest("n_8_50_1.txt");
        executeTest("n_8_100_1.txt");
        executeTest("n_8_400_1.txt");

        executeTest("l_4_25_1.txt");
        executeTest("l_4_50_1.txt");
        executeTest("l_4_100_1.txt");
        executeTest("l_4_400_1.txt");

        executeTest("l_8_25_1.txt");
        executeTest("l_8_50_1.txt");
        executeTest("l_8_100_1.txt");
        executeTest("l_8_400_1.txt");

        executeTest("w_4_25_1.txt");
        executeTest("w_4_50_1.txt");
        executeTest("w_4_100_1.txt");
        executeTest("w_4_400_1.txt");

        executeTest("w_8_25_1.txt");
        executeTest("w_8_50_1.txt");
        executeTest("w_8_100_1.txt");
        executeTest("w_8_400_1.txt");

    }

    private void executeTest(String problem) throws IOException {
        ArrayList<Double> costs = new ArrayList<>();
        ArrayList<Double> time = new ArrayList<>();
        ArrayList<Double> penalty = new ArrayList<>();
        int sampleSize = 5, feasible = 0;
        for (int i = 0; i < sampleSize; i++) {
            ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
            Solver solver = new Solver(problem, instance, maxIterations, i, 0.2, statisticInterval, true);
            solver.setParallel(true);
            solver.setLsActive(true);
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
        StringBuilder resume = new StringBuilder();
        resume.append("\n---------------------------------------");
        resume.append("\nInstance = " + problem);
        resume.append("\nMean costs = " + meanCosts);
        resume.append("\nMean penalty = " + meanPenalty);
        resume.append("\nMean time = " + meanTime);
        resume.append("\nFeasible = " + feasible + " of " + sampleSize);
        resume.append("\n---------------------------------------");
        FileUtils.writeStringToFile(new File("C:\\Temp\\mpdptw\\_resume.txt"), resume.toString(), "UTF-8", true);
    }

    @Test
    public void mpdptw_sequential_feasible_test() throws IOException {
        String problem = "w_4_100_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 10, seed, 0.8, 1, true);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 17412.37   BSF. SD: 0.00    IT. WORST: 21685.26    IT. BEST: 17412.37    IT. MEAN: 19670.08    IT. SD: 1043.76   BRANCH FACTOR: 0.990     DIV: 0.98      ");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 17058.92   BSF. SD: 0.00    IT. WORST: 22574.03    IT. BEST: 17058.92    IT. MEAN: 19985.31    IT. SD: 1128.29   BRANCH FACTOR: 1.827     DIV: 0.96      ");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 17058.92   BSF. SD: 0.00    IT. WORST: 21291.34    IT. BEST: 17267.36    IT. MEAN: 19465.14    IT. SD: 970.42    BRANCH FACTOR: 0.990     DIV: 0.92      ");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 15633.10   BSF. SD: 0.00    IT. WORST: 21219.29    IT. BEST: 15633.10    IT. MEAN: 18601.03    IT. SD: 1083.70   BRANCH FACTOR: 1.721     DIV: 0.77      ");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 15633.10   BSF. SD: 0.00    IT. WORST: 19715.72    IT. BEST: 15681.73    IT. MEAN: 17630.24    IT. SD: 1081.70   BRANCH FACTOR: 0.990     DIV: 0.64      ");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 15303.60   BSF. SD: 0.00    IT. WORST: 18539.47    IT. BEST: 15303.60    IT. MEAN: 16323.91    IT. SD: 803.66    BRANCH FACTOR: 1.048     DIV: 0.26      ");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 15303.60   BSF. SD: 0.00    IT. WORST: 17611.99    IT. BEST: 15303.60    IT. MEAN: 16060.29    IT. SD: 624.92    BRANCH FACTOR: 0.990     DIV: 0.26      ");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 17434.81    IT. BEST: 15202.22    IT. MEAN: 15729.94    IT. SD: 477.22    BRANCH FACTOR: 1.038     DIV: 0.16      ");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 17562.19    IT. BEST: 15202.22    IT. MEAN: 15787.15    IT. SD: 450.24    BRANCH FACTOR: 0.990     DIV: 0.21      ");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 16774.05    IT. BEST: 15202.22    IT. MEAN: 15738.64    IT. SD: 431.42    BRANCH FACTOR: 0.990     DIV: 0.18      ");
    }

    @Test
    public void mpdptw_sequential_infeasible_test() throws IOException {
        String problem = "w_4_100_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 10, seed, 0.8, 1, true);
        solver.setSolutionBuilderClass(SequentialInfeasible.class);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 22062.96   BSF. SD: 0.00    IT. WORST: 26443.83    IT. BEST: 22062.96    IT. MEAN: 24131.55    IT. SD: 1011.14   BRANCH FACTOR: 0.990     DIV: 1.00      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 20657.80   BSF. SD: 0.00    IT. WORST: 26862.76    IT. BEST: 20657.80    IT. MEAN: 23953.56    IT. SD: 1110.20   BRANCH FACTOR: 1.952     DIV: 0.99      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 20657.80   BSF. SD: 0.00    IT. WORST: 26222.27    IT. BEST: 21375.75    IT. MEAN: 23350.67    IT. SD: 1075.56   BRANCH FACTOR: 0.990     DIV: 0.95      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 20115.41   BSF. SD: 0.00    IT. WORST: 25204.62    IT. BEST: 20115.41    IT. MEAN: 22693.02    IT. SD: 1049.13   BRANCH FACTOR: 1.452     DIV: 0.86      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 20115.41   BSF. SD: 0.00    IT. WORST: 23581.92    IT. BEST: 19739.14    IT. MEAN: 21374.74    IT. SD: 1002.66   BRANCH FACTOR: 0.990     DIV: 0.63      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 19090.57   BSF. SD: 0.00    IT. WORST: 22568.50    IT. BEST: 19090.57    IT. MEAN: 20913.70    IT. SD: 840.14    BRANCH FACTOR: 1.317     DIV: 0.39      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 18922.97   BSF. SD: 0.00    IT. WORST: 22477.40    IT. BEST: 18922.97    IT. MEAN: 19748.68    IT. SD: 800.59    BRANCH FACTOR: 1.221     DIV: 0.34      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 18533.95   BSF. SD: 0.00    IT. WORST: 20698.53    IT. BEST: 18533.95    IT. MEAN: 19145.43    IT. SD: 480.64    BRANCH FACTOR: 1.125     DIV: 0.14      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 18533.95   BSF. SD: 0.00    IT. WORST: 21083.77    IT. BEST: 18398.31    IT. MEAN: 18805.72    IT. SD: 561.75    BRANCH FACTOR: 0.990     DIV: 0.11      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 17887.38   BSF. SD: 0.00    IT. WORST: 18881.26    IT. BEST: 17887.38    IT. MEAN: 18527.96    IT. SD: 104.68    BRANCH FACTOR: 1.202     DIV: 0.02      PEN. RATE: 1.00    FEASIBLE: F");
    }

    @Test
    public void mpdptw_large_4_25_4_test() throws IOException {
        String problem = "n_8_25_3.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_4_25_1_test() throws IOException {
        String problem = "w_8_400_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.8, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_8_100_5_test() throws IOException {
        String problem = "w_8_100_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_without_8_100_5_test() throws IOException {
        String problem = "n_4_400_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 1000, seed, 0.2, 1, true);
        solver.setLsActive(true);
        solver.setParallel(true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_100_5_test() throws IOException {
        String problem = "n_8_100_5.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_400_1_test() throws IOException {
        String problem = "w_8_400_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_test() throws IOException {
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

    private void execute(String file) throws IOException {
        String problem = file + ".txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.run();
    }

}
