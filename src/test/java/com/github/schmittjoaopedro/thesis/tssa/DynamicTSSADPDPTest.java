package com.github.schmittjoaopedro.thesis.tssa;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.stop.IterationCriteria;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.tabu_sa.TABU_SA_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicTSSADPDPTest {

    private static int maxIterations = 25000;

    private static final String dpdptw100Directory;

    static {
        dpdptw100Directory = Paths.get(DynamicTSSADPDPTest.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
    }

    @Test
    public void dpdptw_lc103_a_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1), IterationCriteria.of(5000));
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1054.2291016902493);
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10355.071860859929);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(300.8427591696797);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("0 81 78 76 71 70 73 77 79 80 83 82 63 0");
        assertThat(summaryResults).contains("0 67 65 98 94 92 93 102 97 100 99 96 95 0");
        assertThat(summaryResults).contains("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("0 42 41 40 44 48 72 64 68 69 49 46 101 0");
        assertThat(summaryResults).contains("0 57 55 54 53 56 58 60 59 43 51 52 47 0");
        assertThat(summaryResults).contains("0 13 17 18 19 15 16 14 12 85 86 89 91 0");
        assertThat(summaryResults).contains("0 32 33 31 35 104 37 38 39 36 34 29 28 26 103 0");
        assertThat(summaryResults).contains("0 90 87 62 74 84 88 61 66 0");
        assertThat(summaryResults).contains("0 20 24 25 27 30 45 50 23 22 21 0");
    }

    @Test
    public void dpdptw_lc103_a_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_a_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.totalCost).isEqualTo(1038.3507683136875);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10145.64237054699);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(107.2916022333039);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("0 81 78 76 71 70 73 77 79 80 83 82 63 0");
        assertThat(summaryResults).contains("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("0 20 24 25 27 29 30 28 26 103 23 22 21 0");
        assertThat(summaryResults).contains("0 57 55 54 53 56 58 72 68 59 60 46 101 0");
        assertThat(summaryResults).contains("0 43 42 41 40 44 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("0 32 33 31 35 104 37 38 39 36 34 18 16 0");
        assertThat(summaryResults).contains("0 13 17 19 12 15 14 64 61 66 69 0");
        assertThat(summaryResults).contains("0 90 87 62 74 84 85 88 86 89 91 0");
        assertThat(summaryResults).contains("0 67 65 98 94 92 93 102 97 100 99 96 95 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_1_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.1.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.totalCost).isEqualTo(1038.3507683136875);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10145.642370546992);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(107.2916022333039);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("0 20 24 25 27 29 30 28 26 103 23 22 21 0");
        assertThat(summaryResults).contains("0 81 78 76 71 70 73 77 79 80 83 82 63 0");
        assertThat(summaryResults).contains("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("0 57 55 54 53 56 58 72 68 59 60 46 101 0");
        assertThat(summaryResults).contains("0 43 42 41 40 44 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("0 32 33 31 35 104 37 38 39 36 34 18 16 0");
        assertThat(summaryResults).contains("0 67 65 98 94 92 93 102 97 100 99 96 95 0");
        assertThat(summaryResults).contains("0 13 17 19 12 15 14 64 61 66 69 0");
        assertThat(summaryResults).contains("0 90 87 62 74 84 85 88 86 89 91 0");
    }

    @Test
    public void dpdptw_lc103_q_0_0_5_test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(dpdptw100Directory, "lc103_q_0_0.5.txt").toFile());
        TABU_SA_DPDP solver = new TABU_SA_DPDP(instance, new Random(1));
        solver.setPrint(true);
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.tours.size()).isEqualTo(10);
        assertThat(solutionBest.totalCost).isEqualTo(827.8646991698506);
        assertThat(solutionBest.totalScheduleDuration).isEqualTo(10058.030338059863);
        assertThat(solutionBest.totalWaitingTime).isEqualTo(230.16563889001253);
        List<String> summaryResults = Arrays.asList(solver.getFinalResult().split("\n"));
        assertThat(summaryResults).contains("0 20 24 25 27 30 26 103 23 22 21 0");
        assertThat(summaryResults).contains("0 57 55 54 53 56 58 60 59 0");
        assertThat(summaryResults).contains("0 81 78 76 71 70 73 77 79 80 63 0");
        assertThat(summaryResults).contains("0 43 42 41 40 44 46 101 45 48 51 50 52 49 47 0");
        assertThat(summaryResults).contains("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(summaryResults).contains("0 13 17 18 19 15 16 14 12 0");
        assertThat(summaryResults).contains("0 90 87 86 83 82 84 85 88 89 91 0");
        assertThat(summaryResults).contains("0 98 96 95 94 92 93 102 97 100 99 0");
        assertThat(summaryResults).contains("0 32 33 31 35 104 37 38 39 36 34 29 28 0");
        assertThat(summaryResults).contains("0 67 65 62 74 72 61 64 68 66 69 0");
    }

}
