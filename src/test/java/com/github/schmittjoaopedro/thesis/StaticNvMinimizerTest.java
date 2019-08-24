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

public class StaticNvMinimizerTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeVehicles_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false);
        solver.init();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(5070.736543598931);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);
    }

    @Test
    public void minimizeVehicles_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solver.getLogs().get(0)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 4385.17]");
        assertThat(solver.getLogs().get(1)).isEqualTo("New best = [F = true, NV = 19, TC = 4097.69] at iteration 6");
        assertThat(solver.getLogs().get(2)).isEqualTo("New best = [F = true, NV = 18, TC = 4133.07] at iteration 45");
        assertThat(solver.getLogs().get(3)).isEqualTo("New best = [F = true, NV = 17, TC = 3640.98] at iteration 67");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3640.9778334906696);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
    }

    @Test
    public void minimizeVehicles_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solver.getLogs().get(0)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 5070.74]");
        assertThat(solver.getLogs().get(1)).isEqualTo("New best = [F = true, NV = 15, TC = 4538.34] at iteration 1");
        assertThat(solver.getLogs().get(2)).isEqualTo("New best = [F = true, NV = 14, TC = 4287.88] at iteration 4");
        assertThat(solver.getLogs().get(3)).isEqualTo("New best = [F = true, NV = 13, TC = 4278.02] at iteration 7");
        assertThat(solver.getLogs().get(4)).isEqualTo("New best = [F = true, NV = 12, TC = 4091.96] at iteration 81");
        assertThat(solver.getLogs().get(5)).isEqualTo("New best = [F = true, NV = 11, TC = 3924.43] at iteration 5415");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3924.4295378737456);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
    }

}
