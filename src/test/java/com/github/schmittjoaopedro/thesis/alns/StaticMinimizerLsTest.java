package com.github.schmittjoaopedro.thesis.alns;

import com.github.schmittjoaopedro.vrp.thesis.ALNS_DPDP;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class StaticMinimizerLsTest {

    private static int maxIterations = 25000;

    private static String pdptw200Directory;

    static {
        try {
            pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void minimizeCost_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.enableLocalSearch();
        ALNSDPDP.init();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3719.917829850539);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
    }

    @Test
    public void minimizeCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, false, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.enableLocalSearch();
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.182174325898);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        assertThat(solutionBest.requestIds.size()).isEqualTo(18);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  19, TC =   3249.13]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2805.31] at iteration 8");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2799.80] at iteration 12");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2798.59] at iteration 15");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2794.45] at iteration 18");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2784.58] at iteration 21");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2775.26] at iteration 38");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2774.00] at iteration 67");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2773.39] at iteration 618");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2772.66] at iteration 826");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2772.18] at iteration 2641");
    }

    @Test
    public void minimizeVehiclesAndCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.enableLocalSearch();
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.1891730841385);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  19, TC =   3249.13]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3247.95] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   2969.99] at iteration 4");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2853.45] at iteration 5");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2852.84] at iteration 7");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2777.34] at iteration 25");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2773.87] at iteration 40");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3560.98] at iteration 192");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3505.56] at iteration 193");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3467.56] at iteration 213");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3463.57] at iteration 228");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3450.55] at iteration 231");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3450.33] at iteration 248");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3449.47] at iteration 267");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3433.28] at iteration 304");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3423.50] at iteration 316");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3233.82] at iteration 396");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3227.05] at iteration 407");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3224.87] at iteration 409");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3145.33] at iteration 469");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3145.13] at iteration 476");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3143.84] at iteration 484");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3143.75] at iteration 5367");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3140.28] at iteration 5387");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3138.02] at iteration 5388");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3137.43] at iteration 13211");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3136.95] at iteration 13213");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3136.41] at iteration 14669");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.93] at iteration 14980");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.21] at iteration 16532");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.67] at iteration 19089");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.19] at iteration 19121");
    }

    @Test
    public void minimizeCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, false, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.enableLocalSearch();
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3391.901654388153);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   3719.92]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3522.50] at iteration 10");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3477.23] at iteration 262");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3450.58] at iteration 486");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3445.14] at iteration 488");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3434.74] at iteration 490");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3432.96] at iteration 496");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3432.85] at iteration 1994");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3420.58] at iteration 5326");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3419.18] at iteration 5327");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3415.36] at iteration 5329");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3408.48] at iteration 5333");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3407.92] at iteration 5338");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3407.24] at iteration 5341");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3405.07] at iteration 5346");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3403.65] at iteration 5353");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3401.44] at iteration 15251");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3400.87] at iteration 15257");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3399.75] at iteration 15266");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3397.96] at iteration 15306");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3394.16] at iteration 15310");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3392.95] at iteration 15350");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3392.38] at iteration 16798");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3391.90] at iteration 17794");
    }

    @Test
    public void minimizeVehiclesAndCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(3), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.enableLocalSearch();
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3844.0799274247756);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   3719.92]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3715.42] at iteration 1");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3704.37] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4656.73] at iteration 5");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4300.25] at iteration 7");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4032.58] at iteration 8");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3969.14] at iteration 15");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3968.12] at iteration 16");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   4339.49] at iteration 19");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3874.12] at iteration 26");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3873.32] at iteration 28");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4118.82] at iteration 38");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3946.61] at iteration 59");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3801.68] at iteration 108");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3730.56] at iteration 746");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3729.98] at iteration 752");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3691.50] at iteration 1232");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3679.87] at iteration 1234");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3676.48] at iteration 1236");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3666.68] at iteration 1239");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3661.16] at iteration 1240");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3646.71] at iteration 1265");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3594.48] at iteration 1378");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3588.65] at iteration 1383");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3585.74] at iteration 1771");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3580.02] at iteration 3248");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   3566.25] at iteration 5592");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4046.36] at iteration 8191");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4031.76] at iteration 8192");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4027.94] at iteration 8200");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4025.33] at iteration 8201");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4024.75] at iteration 8213");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4022.60] at iteration 8219");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3986.11] at iteration 8262");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3936.44] at iteration 8476");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3856.59] at iteration 8987");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3844.87] at iteration 9898");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   3844.08] at iteration 9917");
    }
}
