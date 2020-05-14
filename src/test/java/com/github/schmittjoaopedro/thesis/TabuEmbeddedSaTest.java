package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.Overall;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.utils.InstanceUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

public class TabuEmbeddedSaTest {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;

    static {
        pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
    }

    @Test
    public void allTest() throws Exception {
        for (String instance : InstanceUtils.instances_200) {
            singleExec(instance, 30, false);
        }
    }

    @Test
    public void singleTest() throws Exception {
        singleExec("lr202", 1, true);
    }

    private void singleExec(String problem, int numTrials, boolean print) throws Exception {
        Solution bestSoFar = null;
        long computationTime = 0;
        SummaryStatistics stats[] = new SummaryStatistics[5];
        for (int i = 0; i < 5; i++) stats[i] = new SummaryStatistics();
        System.out.println("=======================================");
        System.out.println("Starting processing of " + problem);
        for (int i = 0; i < numTrials; i++) {
            Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, problem + ".txt").toFile());
            Overall solver = new Overall(instance, new Random());
            solver.setPrint(print);
            solver.run();
            Solution solutionBest = solver.getSolutionBest();
            System.out.println("Trial " + i + " = " + getFormatted(solutionBest, solver.getComputationTimeInSeconds()));
            stats[0].addValue(solutionBest.tours.size());
            stats[1].addValue(solutionBest.totalCost);
            stats[2].addValue(solutionBest.totalScheduleDuration);
            stats[3].addValue(solutionBest.totalWaitingTime);
            stats[4].addValue(solver.getComputationTimeInSeconds());
            if (bestSoFar == null || SolutionUtils.getBest(bestSoFar, solutionBest) == solutionBest) {
                bestSoFar = solutionBest;
                computationTime = solver.getComputationTimeInSeconds();
            }
        }
        System.out.println("Finishing processing of " + problem);
        System.out.println("Best so far");
        System.out.println("NV\tTC\tSD\tWT\tCT");
        System.out.println(getFormatted(bestSoFar, computationTime));
        System.out.println("Best average");
        System.out.println("NV\tTC\tSD\tWT\tCT");
        System.out.println(getFormatted(stats));
    }

    public static String getFormatted(Solution solution, long computationTime) {
        return solution.tours.size() + "\t" +
                solution.totalCost + "\t" +
                solution.totalScheduleDuration + "\t" +
                solution.totalWaitingTime + "\t" +
                computationTime;
    }

    public static String getFormatted(SummaryStatistics[] statistics) {
        StringBuilder resultStr = new StringBuilder();
        for (int i = 0; i < statistics.length; i++) {
            resultStr.append(MathUtils.round(statistics[i].getMean(), 2) + "±" +
                    MathUtils.round(statistics[i].getStandardDeviation(), 2) + "\t");
        }
        return resultStr.toString();
    }
}
