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

public class StaticNvTcMinimizerTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeNvAndTc_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   4385.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   4377.03] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   4224.21] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   4119.91] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   4116.68] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   3926.65] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4542.68] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4529.39] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3987.54] at iteration 11");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3975.39] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3899.21] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3558.97] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3476.54] at iteration 20");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3404.95] at iteration 21");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3404.52] at iteration 22");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3398.36] at iteration 24");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3348.31] at iteration 26");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3338.44] at iteration 28");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   3034.98] at iteration 29");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2965.43] at iteration 47");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2952.61] at iteration 48");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2939.04] at iteration 49");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2920.55] at iteration 60");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2909.18] at iteration 73");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3410.51] at iteration 97");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3408.34] at iteration 117");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3373.68] at iteration 119");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3371.56] at iteration 121");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3367.45] at iteration 127");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3365.26] at iteration 129");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3269.79] at iteration 201");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3269.18] at iteration 205");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3212.10] at iteration 211");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3208.86] at iteration 219");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3205.58] at iteration 272");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3155.82] at iteration 1049");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3155.38] at iteration 2845");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3153.23] at iteration 2847");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3150.65] at iteration 2850");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3145.40] at iteration 2994");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3145.32] at iteration 2995");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3140.57] at iteration 7796");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3138.39] at iteration 7797");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3137.64] at iteration 14769");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3137.40] at iteration 15373");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.74] at iteration 17963");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.69] at iteration 17980");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.19] at iteration 21572");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.08] at iteration 21887");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.0774195748927);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
    }

    @Test
    public void minimizeNvAndTc_lr1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  19, TC =   5436.06]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   5079.29] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4618.28] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4410.36] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4407.26] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4400.49] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4397.14] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4239.85] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4219.58] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4471.39] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4442.41] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4429.92] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4347.55] at iteration 14");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4296.67] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3977.38] at iteration 18");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3964.86] at iteration 19");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3933.30] at iteration 32");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3932.98] at iteration 33");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3805.65] at iteration 40");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4545.40] at iteration 68");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4534.31] at iteration 74");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4481.37] at iteration 76");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4444.06] at iteration 82");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4434.88] at iteration 84");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4302.34] at iteration 88");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4294.04] at iteration 104");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4277.58] at iteration 106");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4194.70] at iteration 118");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4193.05] at iteration 126");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4158.58] at iteration 129");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4035.21] at iteration 131");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3997.77] at iteration 164");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3986.94] at iteration 174");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3967.12] at iteration 178");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3943.77] at iteration 187");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3921.04] at iteration 190");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3914.31] at iteration 193");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3886.52] at iteration 199");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3879.37] at iteration 466");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3877.48] at iteration 467");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3867.18] at iteration 468");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3793.18] at iteration 511");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3767.28] at iteration 517");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3746.20] at iteration 524");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3741.97] at iteration 1197");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3699.90] at iteration 1211");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3696.28] at iteration 1212");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3694.09] at iteration 1216");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3693.49] at iteration 1220");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3687.57] at iteration 1232");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3685.37] at iteration 1265");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3680.95] at iteration 1268");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3674.13] at iteration 1273");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3673.96] at iteration 1275");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3665.81] at iteration 4662");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3654.97] at iteration 6896");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3631.86] at iteration 6906");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3622.49] at iteration 7162");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3621.43] at iteration 7164");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3618.10] at iteration 7925");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3617.68] at iteration 8249");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3617.44] at iteration 8255");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3617.10] at iteration 9345");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3616.87] at iteration 9346");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3616.45] at iteration 10704");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3613.55] at iteration 12971");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3612.64] at iteration 16547");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3612.6435930848843);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.requestIds.size()).isEqualTo(15);
    }

    @Test
    public void minimizeNvAndTc_lrc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lrc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  17, TC =   4663.77]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4481.25] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4478.94] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3932.82] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3853.80] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3699.03] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3696.69] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   3685.68] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3656.25] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3650.90] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3637.89] at iteration 14");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3572.76] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3559.21] at iteration 22");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3553.88] at iteration 23");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3547.89] at iteration 31");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3544.41] at iteration 33");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3540.49] at iteration 34");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3494.47] at iteration 40");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3485.42] at iteration 42");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3478.86] at iteration 43");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3460.78] at iteration 48");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3460.70] at iteration 50");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3434.31] at iteration 51");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3879.76] at iteration 52");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3834.13] at iteration 58");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3800.93] at iteration 59");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3788.62] at iteration 62");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3677.56] at iteration 65");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3556.67] at iteration 66");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3549.07] at iteration 68");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3541.60] at iteration 69");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3433.90] at iteration 87");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3435.67] at iteration 146");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3411.83] at iteration 149");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3409.69] at iteration 172");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3389.14] at iteration 175");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3351.77] at iteration 177");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3338.77] at iteration 733");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3324.08] at iteration 1921");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3320.59] at iteration 1938");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3314.58] at iteration 2959");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3305.42] at iteration 2960");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3291.68] at iteration 2962");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3279.07] at iteration 2963");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3276.52] at iteration 3078");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3263.27] at iteration 3169");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3258.23] at iteration 3176");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3256.77] at iteration 3195");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3254.64] at iteration 3219");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3254.02] at iteration 3225");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3253.22] at iteration 3864");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3253.18] at iteration 13498");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3250.00] at iteration 13508");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3245.74] at iteration 13563");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3245.66] at iteration 13564");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3240.68] at iteration 13683");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3240.30] at iteration 13781");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3237.06] at iteration 13784");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3233.93] at iteration 13790");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3232.26] at iteration 13802");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3231.82] at iteration 13824");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3231.66] at iteration 15828");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3221.52] at iteration 17085");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3220.62] at iteration 17115");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3220.615999486282);
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
    }
}
