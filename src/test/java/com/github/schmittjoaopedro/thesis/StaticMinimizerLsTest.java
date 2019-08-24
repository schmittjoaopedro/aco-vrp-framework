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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   3249.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3132.77] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   2999.66] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2788.31] at iteration 30");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2782.75] at iteration 289");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2772.18] at iteration 292");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.182174325898);
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   3249.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   3154.51] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   2947.56] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2773.87] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2772.18] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3413.76] at iteration 448");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3373.67] at iteration 452");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3368.08] at iteration 464");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3286.14] at iteration 486");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3282.44] at iteration 489");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3281.37] at iteration 492");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3277.15] at iteration 509");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3270.97] at iteration 510");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3270.16] at iteration 517");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3267.96] at iteration 521");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3245.89] at iteration 615");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3245.00] at iteration 621");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3202.11] at iteration 1389");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3187.52] at iteration 1397");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3187.25] at iteration 8372");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3186.72] at iteration 8463");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3167.42] at iteration 8950");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3162.58] at iteration 8955");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3151.50] at iteration 8963");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3147.62] at iteration 8965");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3147.37] at iteration 8966");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3142.15] at iteration 8971");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3140.92] at iteration 9426");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3139.82] at iteration 9438");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3138.14] at iteration 13903");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.93] at iteration 13915");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.21] at iteration 17276");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.72] at iteration 17405");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.19] at iteration 20325");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.1891730841385);
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   3719.92]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3709.53] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3697.70] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3627.58] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3561.35] at iteration 20");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3485.28] at iteration 30");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3459.43] at iteration 141");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3437.01] at iteration 826");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3436.43] at iteration 827");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3429.70] at iteration 4054");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3426.32] at iteration 4055");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3414.04] at iteration 5785");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3413.46] at iteration 5786");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3413.00] at iteration 7699");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3405.90] at iteration 8314");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3405.32] at iteration 8315");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3403.05] at iteration 8428");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3398.99] at iteration 8441");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3394.85] at iteration 10699");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3394.37] at iteration 10705");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3392.97] at iteration 10733");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3389.18] at iteration 10765");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3389.177392261772);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
    }

    @Test
    public void minimizeVehiclesAndCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(3), maxIterations, true, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   3719.92]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3715.42] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3704.37] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4297.89] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3721.12] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3710.30] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3707.26] at iteration 14");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4358.72] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3940.36] at iteration 18");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   4224.75] at iteration 25");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3861.62] at iteration 29");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3762.99] at iteration 30");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3762.95] at iteration 32");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3595.72] at iteration 55");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3580.25] at iteration 56");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4469.12] at iteration 157");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4183.29] at iteration 197");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4174.37] at iteration 198");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4155.68] at iteration 200");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3981.99] at iteration 201");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3980.19] at iteration 204");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3978.57] at iteration 208");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3920.32] at iteration 224");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3908.35] at iteration 228");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3820.42] at iteration 375");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3814.48] at iteration 376");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3763.16] at iteration 472");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3758.83] at iteration 474");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3711.11] at iteration 1633");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3682.68] at iteration 1710");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3635.21] at iteration 1719");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3608.72] at iteration 1723");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3608.14] at iteration 1729");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3606.37] at iteration 1818");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3595.79] at iteration 8600");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3588.13] at iteration 8676");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3587.85] at iteration 8679");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3586.00] at iteration 8791");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3584.04] at iteration 8869");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3578.12] at iteration 8872");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3577.06] at iteration 8873");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3927.18] at iteration 11075");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3887.12] at iteration 11077");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3884.51] at iteration 11087");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3873.49] at iteration 11140");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3873.23] at iteration 11151");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3861.36] at iteration 11232");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3859.21] at iteration 11233");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3836.21] at iteration 13209");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3792.76] at iteration 13405");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3781.68] at iteration 13406");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3780.70] at iteration 13408");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3758.50] at iteration 13594");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3748.09] at iteration 13605");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3748.0920761787306);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
    }
}
