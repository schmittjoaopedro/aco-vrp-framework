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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3134.19] at iteration 14455");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3134.08] at iteration 20629");

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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3445.86] at iteration 1228");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3437.92] at iteration 2581");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3434.56] at iteration 4105");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3419.80] at iteration 4608");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3417.30] at iteration 4613");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3400.47] at iteration 5140");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3397.66] at iteration 5617");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3397.09] at iteration 5622");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3393.54] at iteration 8609");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3393.544588254913);
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3696.16] at iteration 2300");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3632.29] at iteration 2301");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3603.55] at iteration 3316");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3595.96] at iteration 3465");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3595.39] at iteration 3467");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3588.32] at iteration 4685");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3583.10] at iteration 7660");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 12, TC = 3560.40] at iteration 8946");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4174.56] at iteration 11027");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4150.72] at iteration 11039");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4147.59] at iteration 11048");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4147.19] at iteration 11064");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4143.77] at iteration 11084");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4141.43] at iteration 11136");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4138.99] at iteration 11139");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4138.91] at iteration 11178");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4101.14] at iteration 11237");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4092.66] at iteration 11239");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4080.90] at iteration 13417");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4074.79] at iteration 13422");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4036.24] at iteration 14704");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4034.98] at iteration 14711");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 4019.74] at iteration 15477");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3986.50] at iteration 15492");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3985.35] at iteration 15526");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3984.56] at iteration 15535");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3981.02] at iteration 15545");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3879.12] at iteration 19818");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3879.04] at iteration 19851");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3861.58] at iteration 19953");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3801.69] at iteration 19974");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3795.93] at iteration 19983");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3763.60] at iteration 22520");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3761.51] at iteration 22531");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3757.20] at iteration 22546");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3749.85] at iteration 22552");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3748.86] at iteration 22752");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3742.26] at iteration 22778");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3733.59] at iteration 23423");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3732.81] at iteration 23642");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3728.48] at iteration 23657");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 11, TC = 3727.90] at iteration 23658");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3727.900902106139);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
    }
}
