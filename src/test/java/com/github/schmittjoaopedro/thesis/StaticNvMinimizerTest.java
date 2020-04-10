package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
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
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
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
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3352.0332862894074);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   4385.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4326.63] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   4785.37] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3352.03] at iteration 141");
    }

    @Test
    public void minimizeVehicles_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(4253.694858533941);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   5070.74]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4538.34] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4477.21] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   4559.66] at iteration 21");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4271.70] at iteration 93");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4253.69] at iteration 6366");
    }

}
