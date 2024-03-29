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

public class StaticNvMinimizerTest {

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
    public void minimizeVehicles_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(5070.736543598931);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);
    }

    @Test
    public void minimizeVehicles_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3352.0332862894074);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  20, TC =   4385.17]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  19, TC =   4326.63] at iteration 4");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  18, TC =   4785.37] at iteration 6");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  17, TC =   3352.03] at iteration 141");
    }

    @Test
    public void minimizeVehicles_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        ALNS_DPDP ALNSDPDP = new ALNS_DPDP(instance, new Random(1), maxIterations, true, false, LNSOptimizer.Type.ALNS);
        ALNSDPDP.init();
        ALNSDPDP.run();
        Solution solutionBest = ALNSDPDP.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(4253.694858533941);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        assertThat(solutionBest.requestIds.size()).isEqualTo(11);
        int counter = 0;
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("Insertion heuristic = [F = true, NV =  16, TC =   5070.74]");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  15, TC =   4538.34] at iteration 1");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  14, TC =   4477.21] at iteration 2");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  13, TC =   4559.66] at iteration 21");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  12, TC =   4271.70] at iteration 93");
        assertThat(ALNSDPDP.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV =  11, TC =   4253.69] at iteration 6366");
    }

}
