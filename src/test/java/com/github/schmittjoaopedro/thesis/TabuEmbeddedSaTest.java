package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.TABU_SA_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.statistic.ExperimentStatistics;
import com.github.schmittjoaopedro.vrp.thesis.utils.InstanceUtils;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class TabuEmbeddedSaTest {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;

    static {
        pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
    }

    @Test
    public void lc101_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc101.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.setPrint(true);
        solver.run();
        ExperimentStatistics experimentStatistics = solver.getExperimentStatistics();
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(17202);
        Solution bsf = experimentStatistics.solutionBest;
        assertThat(bsf.tours.size()).isEqualTo(10);
        assertThat(bsf.totalCost).isEqualTo(828.9368669428338);
        assertThat(bsf.totalScheduleDuration).isEqualTo(9828.936866942833);
        assertThat(bsf.totalWaitingTime).isEqualTo(0.0);
        assertThat(bsf.getTourStr(0)).isEqualTo("0 20 24 25 27 29 30 28 26 23 103 22 21 0");
        assertThat(bsf.getTourStr(1)).isEqualTo("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(bsf.getTourStr(2)).isEqualTo("0 67 65 63 62 74 72 61 64 102 68 66 69 0");
        assertThat(bsf.getTourStr(3)).isEqualTo("0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0");
        assertThat(bsf.getTourStr(4)).isEqualTo("0 90 87 86 83 82 84 85 88 89 91 0");
        assertThat(bsf.getTourStr(5)).isEqualTo("0 13 17 18 19 15 16 14 12 0");
        assertThat(bsf.getTourStr(6)).isEqualTo("0 32 33 31 35 37 38 39 36 105 34 0");
        assertThat(bsf.getTourStr(7)).isEqualTo("0 57 55 54 53 56 58 60 59 0");
        assertThat(bsf.getTourStr(8)).isEqualTo("0 98 96 95 94 92 93 97 106 100 99 0");
        assertThat(bsf.getTourStr(9)).isEqualTo("0 81 78 104 76 71 70 73 77 79 80 0");
    }

    @Test
    public void lr203_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr203.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(4));
        solver.setPrint(true);
        solver.run();
        ExperimentStatistics experimentStatistics = solver.getExperimentStatistics();
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(278134);
        Solution bsf = experimentStatistics.solutionBest;
        assertThat(bsf.tours.size()).isEqualTo(3);
        assertThat(bsf.totalCost).isEqualTo(1272.2431997546068);
        assertThat(bsf.totalScheduleDuration).isEqualTo(2799.6837829859764);
        assertThat(bsf.totalWaitingTime).isEqualTo(527.4405832313696);
        assertThat(bsf.getTourStr(0)).isEqualTo("0 94 95 2 73 39 75 57 42 92 102 96 85 16 44 43 38 61 5 99 22 79 78 9 101 3 68 54 55 25 24 80 77 28 58 97 100 0");
        assertThat(bsf.getTourStr(1)).isEqualTo("0 52 18 60 83 45 46 36 64 11 62 88 31 30 1 76 26 40 6 87 41 56 12 35 34 29 4 74 13 0");
        assertThat(bsf.getTourStr(2)).isEqualTo("0 50 33 81 65 71 51 69 27 89 86 14 15 23 67 72 21 53 84 8 48 47 49 19 10 63 90 32 66 20 70 7 82 17 91 37 98 93 59 0");
    }

    //@Test
    public void allTest() throws Exception {
        for (String instance : InstanceUtils.instances_200) {
            singleExec(instance, 30, false);
        }
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
            TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random());
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
