package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MPDPTW_MMAS {

    private static final String rootDirectory;

    private static int statisticInterval = 10;

    private static int maxIterations = 100;

    private static int seed = 1;

    static {
        rootDirectory = Paths.get(MPDPTW_MMAS.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws IOException {

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

    private static void executeTest(String problem) throws IOException {
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
}
