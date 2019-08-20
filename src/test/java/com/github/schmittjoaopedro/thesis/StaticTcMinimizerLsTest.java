package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StaticTcMinimizerLsTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeCost_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.enableLocalSearch();
        solver.init();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3719.917829850539);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
    }

    @Test
    public void minimizeCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 3249.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3015.33] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2777.47] at iteration 35");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2775.74] at iteration 39");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2773.53] at iteration 40");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2773.39] at iteration 120");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2772.18] at iteration 431");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.1821743258974);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        assertThat(solutionBest.requestIds.size()).isEqualTo(18);
    }

    @Test
    public void minimizeCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 3719.92]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3709.53] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3664.01] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3643.02] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3640.00] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3515.19] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3510.52] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3499.15] at iteration 17");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3493.10] at iteration 18");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3462.78] at iteration 219");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3428.72] at iteration 229");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3418.09] at iteration 3078");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3417.15] at iteration 3083");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3413.37] at iteration 3800");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3409.81] at iteration 5801");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3409.20] at iteration 5806");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3406.44] at iteration 5819");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3398.68] at iteration 5841");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3397.51] at iteration 11626");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3393.39] at iteration 11629");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3392.97] at iteration 11646");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3389.18] at iteration 13457");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3389.1780885580893);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
    }
}
