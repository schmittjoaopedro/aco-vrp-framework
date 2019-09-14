package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StaticTcMinimizerTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeCost_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.init();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(5070.736543598931);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);
    }

    @Test
    public void minimizeCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.182174325898);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        assertThat(solutionBest.requestIds.size()).isEqualTo(18);
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   4385.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4043.29] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4030.97] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3883.66] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3881.23] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3781.05] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3529.07] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3326.46] at iteration 11");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3314.60] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3199.93] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3197.35] at iteration 19");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3194.35] at iteration 21");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3141.66] at iteration 23");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3135.03] at iteration 24");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3060.53] at iteration 25");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3027.32] at iteration 57");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3024.92] at iteration 61");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2922.40] at iteration 62");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2895.58] at iteration 139");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2894.71] at iteration 140");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2890.32] at iteration 141");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2889.79] at iteration 142");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2784.30] at iteration 159");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2782.87] at iteration 178");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2778.18] at iteration 180");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2776.26] at iteration 182");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2772.18] at iteration 870");
    }

    @Test
    public void minimizeCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3421.964026239856);
        assertThat(solutionBest.tours.size()).isEqualTo(14);
        assertThat(solutionBest.requestIds.size()).isEqualTo(14);
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   5070.74]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4652.02] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4503.62] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4494.17] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4396.59] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4322.17] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4240.01] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4058.14] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4048.68] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4037.90] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4013.67] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4010.63] at iteration 17");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3994.36] at iteration 18");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3954.88] at iteration 21");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3949.41] at iteration 23");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3941.93] at iteration 25");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3813.71] at iteration 31");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3788.88] at iteration 32");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3731.32] at iteration 33");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3676.43] at iteration 35");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3629.50] at iteration 74");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3614.85] at iteration 172");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3609.96] at iteration 173");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3605.45] at iteration 174");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3597.10] at iteration 175");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3596.78] at iteration 176");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3590.01] at iteration 180");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3571.91] at iteration 184");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3554.47] at iteration 185");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3536.50] at iteration 269");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3534.21] at iteration 271");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3502.73] at iteration 735");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3496.51] at iteration 738");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3490.88] at iteration 744");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3490.27] at iteration 759");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3485.35] at iteration 761");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3478.43] at iteration 762");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3472.43] at iteration 4931");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3467.66] at iteration 4933");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3467.39] at iteration 4940");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3456.83] at iteration 4945");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3455.91] at iteration 5781");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3454.26] at iteration 5782");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3427.33] at iteration 7843");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3426.93] at iteration 7845");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3422.01] at iteration 7850");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3421.96] at iteration 17849");
    }
}
