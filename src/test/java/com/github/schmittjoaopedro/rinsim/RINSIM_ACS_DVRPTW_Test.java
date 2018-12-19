package com.github.schmittjoaopedro.rinsim;

import com.github.schmittjoaopedro.rinsim.dvrptwacs.RINSIM_ACS_DVRPTW;
import com.github.schmittjoaopedro.vrp.DVRPTW_ACS_Test;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class RINSIM_ACS_DVRPTW_Test {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dvrptw").getFile().substring(1)).toString();
    }

    @Test
    public void rinsim_dynamic_vrp_time_window_acs_r103_0_1_test() throws Exception {
        File file = Paths.get(rootDirectory, "r103-0.1.txt").toFile();
        RINSIM_ACS_DVRPTW rinsimAcsDvrptw = new RINSIM_ACS_DVRPTW(file, true);
        rinsimAcsDvrptw.run();
    }

}
