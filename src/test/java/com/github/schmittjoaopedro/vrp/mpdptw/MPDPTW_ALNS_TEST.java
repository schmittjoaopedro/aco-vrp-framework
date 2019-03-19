package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

public class MPDPTW_ALNS_TEST {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        int maxIterations = 25000;
        ALNS alns = new ALNS(rootDirectory, "n_8_100_3.txt", maxIterations, new Random(1));
        alns.execute();
    }

}
