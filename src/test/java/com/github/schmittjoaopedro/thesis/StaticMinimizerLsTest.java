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

public class StaticMinimizerLsTest {

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
    public void minimizeVehiclesAndCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 3249.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 3154.51] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 2947.56] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2773.87] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2772.18] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3631.51] at iteration 124");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3454.76] at iteration 127");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3363.07] at iteration 136");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3362.20] at iteration 140");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3356.42] at iteration 157");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3178.25] at iteration 347");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3175.58] at iteration 354");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3167.36] at iteration 356");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3144.53] at iteration 373");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3139.13] at iteration 374");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3136.95] at iteration 375");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3135.93] at iteration 14308");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3134.19] at iteration 16963");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3134.08] at iteration 23969");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.0774195748927);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
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

    @Test
    public void minimizeVehiclesAndCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 3719.92]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3539.94] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4482.48] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4086.64] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 4528.32] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3871.03] at iteration 11");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3802.49] at iteration 21");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 4513.55] at iteration 77");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 4169.48] at iteration 79");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 4098.99] at iteration 88");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3941.42] at iteration 102");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3899.63] at iteration 195");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3844.40] at iteration 305");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3751.91] at iteration 306");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3730.09] at iteration 307");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3713.24] at iteration 309");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3690.32] at iteration 1090");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3674.82] at iteration 1535");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3668.89] at iteration 1536");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3664.51] at iteration 1537");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3648.15] at iteration 1538");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3643.83] at iteration 1543");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3643.74] at iteration 1546");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3616.30] at iteration 1549");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3612.51] at iteration 3440");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3603.60] at iteration 3478");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3601.89] at iteration 4262");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3599.66] at iteration 4264");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3594.84] at iteration 5106");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3586.06] at iteration 6014");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3584.30] at iteration 6846");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3582.46] at iteration 6865");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3576.66] at iteration 7786");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3571.75] at iteration 9284");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3948.77] at iteration 9867");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3922.69] at iteration 9893");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3910.01] at iteration 9919");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3900.18] at iteration 9925");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3869.89] at iteration 10394");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3810.08] at iteration 10926");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3808.03] at iteration 10963");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3808.02] at iteration 10974");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3778.20] at iteration 10977");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3766.07] at iteration 11067");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3765.49] at iteration 11072");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3762.55] at iteration 11154");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3758.12] at iteration 11183");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3755.48] at iteration 19385");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3748.93] at iteration 19944");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3743.32] at iteration 20382");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3743.316494717629);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
    }
}
