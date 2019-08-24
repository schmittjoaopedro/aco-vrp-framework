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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 4385.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 4377.03] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 4224.21] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 4119.91] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 3624.44] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 3408.08] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 4054.02] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3933.10] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3893.80] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3600.28] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3598.64] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3513.42] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3503.45] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3431.51] at iteration 19");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3255.51] at iteration 20");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3101.98] at iteration 29");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3099.43] at iteration 30");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3081.00] at iteration 40");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2971.74] at iteration 59");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2969.54] at iteration 61");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2903.86] at iteration 62");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2810.44] at iteration 78");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2803.77] at iteration 79");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2781.31] at iteration 86");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2780.10] at iteration 87");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3557.25] at iteration 155");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3555.44] at iteration 194");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3526.97] at iteration 196");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3424.35] at iteration 209");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3421.73] at iteration 237");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3418.48] at iteration 239");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3331.26] at iteration 265");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3271.54] at iteration 273");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3271.50] at iteration 274");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3262.85] at iteration 600");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3191.23] at iteration 625");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3167.29] at iteration 627");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3159.60] at iteration 630");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3158.46] at iteration 634");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3142.01] at iteration 637");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3140.89] at iteration 13705");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3140.15] at iteration 14054");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3139.51] at iteration 15048");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3136.95] at iteration 15265");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3135.93] at iteration 15894");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3135.56] at iteration 16014");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3135.21] at iteration 17817");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 3134.19] at iteration 18208");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.1891730841385);
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 19, TC = 5436.06]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 5079.29] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4618.28] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4410.36] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4407.26] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4400.49] at iteration 5");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4397.14] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4239.85] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4219.58] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4471.39] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4442.41] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4414.64] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3949.40] at iteration 15");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3942.90] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 5029.22] at iteration 34");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4903.48] at iteration 38");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4800.68] at iteration 39");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4769.99] at iteration 40");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4750.37] at iteration 41");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4604.47] at iteration 42");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4575.27] at iteration 43");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4568.73] at iteration 46");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4549.83] at iteration 47");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4510.16] at iteration 49");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4269.84] at iteration 56");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4268.71] at iteration 57");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4235.34] at iteration 62");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4222.92] at iteration 63");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4222.56] at iteration 65");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4217.15] at iteration 66");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4127.11] at iteration 78");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4110.29] at iteration 79");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4105.68] at iteration 85");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4102.87] at iteration 88");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4094.33] at iteration 99");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4091.50] at iteration 102");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4039.50] at iteration 111");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4016.62] at iteration 112");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3988.63] at iteration 120");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3935.01] at iteration 242");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3933.14] at iteration 243");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3932.69] at iteration 246");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3928.45] at iteration 309");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3885.09] at iteration 461");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3884.32] at iteration 463");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3880.74] at iteration 468");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3866.29] at iteration 471");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3833.17] at iteration 757");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3830.49] at iteration 758");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3805.54] at iteration 817");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3805.34] at iteration 818");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3798.77] at iteration 820");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3741.21] at iteration 1040");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3738.96] at iteration 4333");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3726.44] at iteration 4335");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3723.20] at iteration 7577");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3693.15] at iteration 7605");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3692.31] at iteration 7609");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3686.64] at iteration 7610");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3684.83] at iteration 7614");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3683.84] at iteration 7620");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3655.43] at iteration 7628");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3649.38] at iteration 7725");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3645.86] at iteration 7779");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3645.8619061410736);
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 17, TC = 4663.77]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4481.25] at iteration 1");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4198.95] at iteration 2");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 17, TC = 4193.58] at iteration 3");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4156.59] at iteration 4");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4113.47] at iteration 6");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3959.17] at iteration 7");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3828.06] at iteration 8");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3824.39] at iteration 9");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3767.89] at iteration 10");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3764.13] at iteration 11");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3735.24] at iteration 12");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3721.13] at iteration 13");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3717.13] at iteration 14");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 4126.83] at iteration 16");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3731.77] at iteration 25");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3700.96] at iteration 27");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3665.21] at iteration 33");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3663.45] at iteration 34");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3632.42] at iteration 39");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3536.50] at iteration 43");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3507.11] at iteration 45");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3502.27] at iteration 120");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3491.02] at iteration 163");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3490.07] at iteration 164");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3472.39] at iteration 187");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3320.50] at iteration 223");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3287.52] at iteration 230");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3696.78] at iteration 243");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3435.87] at iteration 244");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3422.96] at iteration 245");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3413.87] at iteration 399");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3381.30] at iteration 477");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3352.48] at iteration 917");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3350.15] at iteration 2946");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3339.05] at iteration 2948");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3320.83] at iteration 2950");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3319.94] at iteration 2951");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3319.44] at iteration 4441");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3312.99] at iteration 4453");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3309.33] at iteration 4599");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3304.21] at iteration 4602");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3282.15] at iteration 4620");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3281.41] at iteration 4623");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3278.60] at iteration 4625");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3272.70] at iteration 4696");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3272.59] at iteration 8656");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3272.25] at iteration 8662");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3269.14] at iteration 8667");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3268.56] at iteration 8855");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3266.01] at iteration 9636");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3261.03] at iteration 9642");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3259.00] at iteration 9644");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3258.99] at iteration 9646");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3251.52] at iteration 9668");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3246.99] at iteration 9669");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3246.92] at iteration 13751");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3216.04] at iteration 13839");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3215.56] at iteration 13912");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3191.52] at iteration 22551");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3188.59] at iteration 22554");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3188.31] at iteration 22586");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3187.94] at iteration 22660");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3187.74] at iteration 23840");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3187.7410538080317);
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
    }
}
