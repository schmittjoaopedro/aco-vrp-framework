package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

public class MPDPTW_ALNS_TEST {

    private static final String rootDirectory;

    int maxIterations = 100000;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        ALNS alns = new ALNS(rootDirectory, "n_8_25_3.txt", maxIterations, new Random(1));
        alns.execute();
    }

    @Test
    public void mpdptw_test() {
        //for (String noVert : new String[]{"400", "100", "50", "25"}) {
        for (String noVert : new String[]{"25", "50", "100", "400"}) {
            for (String typ : new String[]{"l", "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        String file = typ + "_" + reqSize + "_" + noVert + "_" + id;
                        ALNS alns = new ALNS(rootDirectory, file + ".txt", maxIterations, new Random(1));
                        alns.execute();
                    }
                }
            }
        }
    }

}
