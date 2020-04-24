package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.Overall;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.utils.InstanceUtils;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

public class TabuEmbeddedSaTest {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;

    static {
        pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
    }

    //@Test
    public void allTest() throws Exception {
        for (String instance : InstanceUtils.instances_100) {
            singleExec(instance, 10);
        }
    }

    //@Test
    public void singleTest() throws Exception {
        singleExec("lc204", 1);
    }

    private void singleExec(String problem, int numTrials) throws Exception {
        Solution bestSoFar = null;
        long computationTime = 0;
        System.out.println("=======================================");
        System.out.println("Starting processing of " + problem);
        for (int i = 0; i < numTrials; i++) {
            Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, problem + ".txt").toFile());
            Overall solver = new Overall(instance, new Random());
            solver.setPrint(false);
            solver.run();
            Solution solutionBest = solver.getSolutionBest();
            System.out.println("Trial " + i + " = " + solutionBest.toString());
            if (bestSoFar == null || SolutionUtils.getBest(bestSoFar, solutionBest) == solutionBest) {
                bestSoFar = solutionBest;
                computationTime = solver.getComputationTimeInSeconds();
            }
        }
        System.out.println("Finishing processing of " + problem);
        System.out.println("NV\tTC\tSD\tWT\tCT");
        System.out.println(bestSoFar.tours.size() + "\t" +
                bestSoFar.totalCost + "\t" +
                bestSoFar.totalScheduleDuration + "\t" +
                bestSoFar.totalWaitingTime + "\t" +
                computationTime);
    }
}
