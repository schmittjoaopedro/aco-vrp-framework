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

public class StaticNvTcMinimizerTest {

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
    public void minimizeNvAndTc_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3134.077419574893);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   4385.17]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   3583.05] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  20, TC =   3547.92] at iteration 3");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4704.05] at iteration 4");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4700.50] at iteration 6");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3893.97] at iteration 7");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3742.10] at iteration 8");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3649.07] at iteration 10");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3584.79] at iteration 11");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3537.75] at iteration 13");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3537.21] at iteration 14");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3446.48] at iteration 15");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3366.87] at iteration 19");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3261.95] at iteration 21");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3218.62] at iteration 29");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3183.34] at iteration 34");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3181.61] at iteration 36");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3154.26] at iteration 38");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3055.40] at iteration 41");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3037.98] at iteration 42");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3033.81] at iteration 66");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3014.42] at iteration 70");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   3006.15] at iteration 72");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2965.49] at iteration 73");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2928.74] at iteration 96");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2919.95] at iteration 98");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2806.03] at iteration 101");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2796.80] at iteration 226");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2792.87] at iteration 227");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2789.56] at iteration 228");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   2779.00] at iteration 229");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3513.18] at iteration 307");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3484.50] at iteration 311");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3380.28] at iteration 317");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3380.10] at iteration 324");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3379.14] at iteration 329");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3311.32] at iteration 342");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3306.88] at iteration 343");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3296.96] at iteration 365");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3292.49] at iteration 368");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3242.44] at iteration 371");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3240.55] at iteration 384");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3238.34] at iteration 385");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3237.36] at iteration 386");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3233.54] at iteration 395");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3206.73] at iteration 409");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3204.98] at iteration 411");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3194.46] at iteration 413");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3192.65] at iteration 470");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3192.17] at iteration 539");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3188.85] at iteration 575");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3186.86] at iteration 579");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3184.38] at iteration 593");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3181.71] at iteration 600");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3180.16] at iteration 602");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3168.21] at iteration 609");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3167.75] at iteration 616");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3164.51] at iteration 617");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3155.25] at iteration 627");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3155.18] at iteration 629");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3153.44] at iteration 697");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3153.01] at iteration 2143");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3148.45] at iteration 3871");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3140.67] at iteration 8620");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3139.60] at iteration 8621");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3139.01] at iteration 11786");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3135.93] at iteration 12108");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.19] at iteration 17274");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3134.08] at iteration 19435");
    }

    @Test
    public void minimizeNvAndTc_lr1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3612.643593084884);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.requestIds.size()).isEqualTo(15);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  19, TC =   5436.06]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   4846.85] at iteration 1");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   4787.19] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   4389.13] at iteration 3");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4523.87] at iteration 4");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4451.17] at iteration 5");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4347.85] at iteration 10");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4321.10] at iteration 11");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4634.58] at iteration 12");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4560.83] at iteration 14");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4500.19] at iteration 17");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4496.77] at iteration 18");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4434.74] at iteration 19");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4389.28] at iteration 20");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4331.93] at iteration 22");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4256.17] at iteration 23");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4222.24] at iteration 24");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4207.75] at iteration 27");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4174.17] at iteration 31");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4146.79] at iteration 55");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4143.63] at iteration 57");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4123.01] at iteration 75");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4117.98] at iteration 129");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4117.39] at iteration 130");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4116.99] at iteration 131");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4097.64] at iteration 135");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4087.18] at iteration 137");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4044.28] at iteration 257");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4028.85] at iteration 258");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4027.24] at iteration 259");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4021.11] at iteration 260");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3974.56] at iteration 261");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3972.80] at iteration 262");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3970.45] at iteration 604");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3965.51] at iteration 605");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3876.66] at iteration 633");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3869.62] at iteration 634");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3868.74] at iteration 657");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3850.97] at iteration 765");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3809.02] at iteration 772");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3798.72] at iteration 773");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3792.99] at iteration 775");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3787.84] at iteration 863");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3787.67] at iteration 864");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3763.97] at iteration 927");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3751.24] at iteration 1572");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3750.84] at iteration 1583");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3734.08] at iteration 1584");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3687.13] at iteration 1602");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3662.01] at iteration 1603");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3661.41] at iteration 14283");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3655.53] at iteration 14410");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3652.26] at iteration 14412");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3652.15] at iteration 14416");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3649.42] at iteration 14419");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3649.18] at iteration 14433");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3647.09] at iteration 14475");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3644.87] at iteration 14483");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3623.28] at iteration 19615");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3621.93] at iteration 19616");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3620.95] at iteration 19624");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3618.64] at iteration 19627");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3616.76] at iteration 19635");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3616.75] at iteration 19673");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   3612.64] at iteration 20771");
    }

    @Test
    public void minimizeNvAndTc_lrc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lrc1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3224.801134731682);
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  17, TC =   4663.77]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   4392.60] at iteration 1");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  16, TC =   4402.06] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4396.71] at iteration 3");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4395.59] at iteration 4");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4387.28] at iteration 5");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4180.92] at iteration 7");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3938.14] at iteration 10");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3926.36] at iteration 12");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3788.14] at iteration 13");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3699.85] at iteration 15");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3693.09] at iteration 17");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3640.88] at iteration 18");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3535.54] at iteration 19");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3532.52] at iteration 20");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3484.21] at iteration 22");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3448.97] at iteration 23");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3448.93] at iteration 24");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3436.92] at iteration 27");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3432.70] at iteration 29");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   3431.33] at iteration 31");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3667.01] at iteration 39");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3663.35] at iteration 41");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3590.54] at iteration 42");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3585.37] at iteration 54");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3581.41] at iteration 66");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3574.82] at iteration 68");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3571.23] at iteration 74");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3552.36] at iteration 78");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3551.74] at iteration 79");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3475.68] at iteration 151");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3443.82] at iteration 153");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3435.51] at iteration 157");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3435.44] at iteration 158");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3434.68] at iteration 202");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3424.65] at iteration 212");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3397.36] at iteration 214");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3395.15] at iteration 465");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3392.88] at iteration 700");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3386.85] at iteration 701");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3372.04] at iteration 915");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3369.87] at iteration 935");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3347.34] at iteration 936");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3346.05] at iteration 937");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3335.46] at iteration 1132");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3326.90] at iteration 1133");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3314.01] at iteration 1145");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3304.21] at iteration 2724");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3304.01] at iteration 2725");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3303.67] at iteration 2726");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3239.01] at iteration 2727");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3238.02] at iteration 2732");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3237.96] at iteration 2777");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3235.96] at iteration 2780");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3235.17] at iteration 2782");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3231.43] at iteration 2783");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3227.34] at iteration 2784");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3226.51] at iteration 2858");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3226.50] at iteration 16755");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3226.28] at iteration 16760");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3226.09] at iteration 19630");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3225.33] at iteration 19742");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   3224.80] at iteration 19788");
    }
}
