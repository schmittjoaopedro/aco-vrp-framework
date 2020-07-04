package com.github.schmittjoaopedro.thesis.tssa;

import com.github.schmittjoaopedro.thesis.alns.StaticNvMinimizerTest;
import com.github.schmittjoaopedro.vrp.thesis.MathUtils;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.IterationCriteria;
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
import java.util.ArrayList;
import java.util.List;
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
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(103003L);
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
        int i = 0;
        List logs = new ArrayList(solver.getLogs()); // Convert linked list to array list
        assertThat(logs.get(i++)).isEqualTo("Insertion heuristic = [F = true, NV =  10, TC =    828.94]");
    }

    @Test
    public void lc204_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc204.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(2), IterationCriteria.of(1000));
        solver.setPrint(true);
        solver.run();
        ExperimentStatistics experimentStatistics = solver.getExperimentStatistics();
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(191641L);
        Solution bsf = experimentStatistics.solutionBest;
        assertThat(bsf.tours.size()).isEqualTo(3);
        assertThat(bsf.totalCost).isEqualTo(590.5987456679449);
        assertThat(bsf.totalScheduleDuration).isEqualTo(9590.598745667945);
        assertThat(bsf.totalWaitingTime).isEqualTo(0.0);
        assertThat(bsf.getTourStr(0)).isEqualTo("0 20 22 24 27 30 29 6 32 33 31 35 37 38 39 36 34 28 26 101 23 18 19 16 14 12 15 17 13 25 9 11 10 8 21 0");
        assertThat(bsf.getTourStr(1)).isEqualTo("0 67 63 62 74 72 61 64 66 69 68 65 49 55 54 53 56 58 60 59 57 40 44 46 41 42 45 51 50 52 47 43 48 0");
        assertThat(bsf.getTourStr(2)).isEqualTo("0 93 5 75 2 1 99 100 97 92 94 95 98 7 3 4 89 91 88 84 86 83 82 102 85 76 71 70 73 80 79 81 78 77 96 87 90 0");
        int i = 0;
        List logs = new ArrayList(solver.getLogs()); // Convert linked list to array list
        assertThat(logs.get(i++)).isEqualTo("Insertion heuristic = [F = true, NV =   4, TC =    880.87]");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =    662.23] at scaled time 0.001");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =    647.41] at scaled time 0.002");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =    599.83] at scaled time 0.336");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =    590.60] at scaled time 0.395");
    }

    @Test
    public void lr203_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr203.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(4));
        solver.setPrint(true);
        solver.run();
        ExperimentStatistics experimentStatistics = solver.getExperimentStatistics();
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(825089);
        Solution bsf = experimentStatistics.solutionBest;
        assertThat(bsf.tours.size()).isEqualTo(3);
        assertThat(bsf.totalCost).isEqualTo(1072.3595348703668);
        assertThat(bsf.totalScheduleDuration).isEqualTo(2767.4970997972564);
        assertThat(bsf.totalWaitingTime).isEqualTo(695.1375649268896);
        assertThat(bsf.getTourStr(0)).isEqualTo("0 94 95 92 102 42 57 15 39 67 23 75 72 73 21 53 79 78 9 101 3 68 54 55 25 24 80 77 28 0");
        assertThat(bsf.getTourStr(1)).isEqualTo("0 52 18 60 83 45 46 36 64 11 62 88 31 30 76 12 26 40 87 43 41 22 56 29 34 35 4 74 2 13 58 0");
        assertThat(bsf.getTourStr(2)).isEqualTo("0 50 33 81 65 71 51 1 69 27 89 96 59 85 86 38 14 44 16 61 5 6 99 84 8 48 47 49 19 10 63 90 32 66 20 70 7 82 17 93 91 100 98 37 97 0");
        int i = 0;
        List logs = new ArrayList(solver.getLogs()); // Convert linked list to array list
        assertThat(logs.get(i++)).isEqualTo("Insertion heuristic = [F = true, NV =   4, TC =   1595.50]");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1252.29] at scaled time 2.0E-4");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1251.14] at scaled time 8.0E-4");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1245.73] at scaled time 0.001");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1242.96] at scaled time 0.0012");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1231.66] at scaled time 0.0014");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1223.00] at scaled time 0.003");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   4, TC =   1222.09] at scaled time 0.0146");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1388.05] at scaled time 0.0252");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1375.00] at scaled time 0.0258");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1370.85] at scaled time 0.0356");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1347.47] at scaled time 0.071");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1344.88] at scaled time 0.0712");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1307.96] at scaled time 0.0718");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1307.36] at scaled time 0.072");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1305.73] at scaled time 0.0722");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1303.76] at scaled time 0.0768");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1283.95] at scaled time 0.077");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1282.67] at scaled time 0.0958");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1282.58] at scaled time 0.1594");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1272.24] at scaled time 0.1678");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1267.25] at scaled time 0.6494");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1211.43] at scaled time 0.653");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1197.18] at scaled time 0.6532");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1078.23] at scaled time 0.6558");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1078.18] at scaled time 0.6674");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =   3, TC =   1072.36] at scaled time 0.7102");
    }

    @Test
    public void lrc106_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lrc106.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.setPrint(true);
        solver.run();
        ExperimentStatistics experimentStatistics = solver.getExperimentStatistics();
        assertThat(experimentStatistics.numSolutionsEvaluation).isEqualTo(98940L);
        Solution bsf = experimentStatistics.solutionBest;
        assertThat(bsf.tours.size()).isEqualTo(11);
        assertThat(bsf.totalCost).isEqualTo(1425.5285985764344);
        assertThat(bsf.totalScheduleDuration).isEqualTo(2475.0100222934866);
        assertThat(bsf.totalWaitingTime).isEqualTo(49.48142371705241);
        assertThat(bsf.getTourStr(0)).isEqualTo("0 72 71 67 30 32 34 50 93 80 105 0");
        assertThat(bsf.getTourStr(1)).isEqualTo("0 69 98 88 53 12 103 10 9 13 17 0");
        assertThat(bsf.getTourStr(2)).isEqualTo("0 95 62 63 85 76 51 102 84 56 66 0");
        assertThat(bsf.getTourStr(3)).isEqualTo("0 42 44 39 38 36 40 41 43 37 35 0");
        assertThat(bsf.getTourStr(4)).isEqualTo("0 2 45 5 8 7 6 46 4 3 1 104 100 0");
        assertThat(bsf.getTourStr(5)).isEqualTo("0 65 83 64 19 23 21 18 48 25 77 0");
        assertThat(bsf.getTourStr(6)).isEqualTo("0 15 16 47 78 73 79 60 55 106 70 0");
        assertThat(bsf.getTourStr(7)).isEqualTo("0 82 52 99 86 57 22 49 20 24 91 0");
        assertThat(bsf.getTourStr(8)).isEqualTo("0 14 11 87 59 75 97 58 74 0");
        assertThat(bsf.getTourStr(9)).isEqualTo("0 92 61 81 90 94 96 54 68 0");
        assertThat(bsf.getTourStr(10)).isEqualTo("0 33 31 29 27 28 26 101 89 0");
        int i = 0;
        List logs = new ArrayList(solver.getLogs()); // Convert linked list to array list
        assertThat(logs.get(i++)).isEqualTo("Insertion heuristic = [F = true, NV =  17, TC =   1968.06]");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  14, TC =   1571.75] at scaled time 2.0E-4");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  13, TC =   1561.86] at scaled time 0.0228");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  13, TC =   1557.37] at scaled time 0.0234");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  13, TC =   1482.41] at scaled time 0.025");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  12, TC =   1527.34] at scaled time 0.0252");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  12, TC =   1441.24] at scaled time 0.0264");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  12, TC =   1437.14] at scaled time 0.0522");
        assertThat(logs.get(i++)).isEqualTo("New best = [F = true, NV =  11, TC =   1425.53] at scaled time 0.1478");
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
