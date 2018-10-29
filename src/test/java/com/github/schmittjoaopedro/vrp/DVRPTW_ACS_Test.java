package com.github.schmittjoaopedro.vrp;

import com.github.schmittjoaopedro.vrp.dvrptwacs.Program;
import org.junit.Test;

import java.nio.file.Paths;

public class DVRPTW_ACS_Test {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dvrptw").getFile().substring(1)).toString();
    }

    @Test
    public void dynamic_vrp_time_window_acs_c101_0_5_test() {
        Program dvrptwSolver = new Program(rootDirectory, "r103", 0.1, 1);
        dvrptwSolver.execute();
    }

}
