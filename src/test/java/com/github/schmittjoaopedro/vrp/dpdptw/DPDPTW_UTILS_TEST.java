package com.github.schmittjoaopedro.vrp.dpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import org.junit.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DPDPTW_UTILS_TEST {

    private static final String dpdptw400Directory;

    static {
        dpdptw400Directory = Paths.get(DPDPTW_ALNS_TEST.class.getClassLoader().getResource("dpdptw_400").getFile().substring(1)).toString();
    }

    @Test
    public void strictRequestOptimalSolverTest() throws Exception {
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw400Directory, "LR1_4_8_q_0_0.1.txt").toFile());
        OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(88, instance);
        optimalRequestSolver.optimize();
        assertThat(optimalRequestSolver.getBestRoute()).containsExactly(0, 184, 4, 0);
        assertThat(optimalRequestSolver.getBestCost()).isEqualTo(285.60750571225356);
    }
}
