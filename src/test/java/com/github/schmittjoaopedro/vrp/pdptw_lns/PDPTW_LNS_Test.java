package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.junit.Test;

import java.nio.file.Paths;

public class PDPTW_LNS_Test {

    int maxIterations = 100000;

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    private static final String pdptw400Directory;

    private static final String pdptw600Directory;

    static {
        pdptw100Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
        pdptw400Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_400").getFile().substring(1)).toString();
        pdptw600Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_600").getFile().substring(1)).toString();
    }


    @Test
    public void pdptw_lr104_test() {
        String problem = "lr1_2_6.txt";
        Solver solver = new Solver(pdptw200Directory, problem, 1);
        solver.run();
    }
}
