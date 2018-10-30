package com.github.schmittjoaopedro.vrp;

import com.github.schmittjoaopedro.vrp.dvrptwacs.Controller;
import com.github.schmittjoaopedro.vrp.dvrptwacs.InOut;
import com.github.schmittjoaopedro.vrp.dvrptwacs.LoggerOutput;
import org.junit.Test;

import java.nio.file.Paths;

public class DVRPTW_ACS_Test {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dvrptw").getFile().substring(1)).toString();
    }

    @Test
    public void dynamic_vrp_time_window_acs_r103_0_1_test() {
        LoggerOutput.reset();
        InOut.isDiscreteTime = true;
        Controller.execute("z", rootDirectory, "r103", 0.1, 1);
    }

}
