package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 1;

    private static int maxIterations = 1000;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "l_4_25_2.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_4_25_4_test() {
        Solver solver = new Solver(rootDirectory, "l_8_50_3.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "n_4_25_1.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "l_8_100_5.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_without_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "w_8_100_5.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "n_8_100_5.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_400_1_test() {
        Solver solver = new Solver(rootDirectory, "w_8_400_1.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_test() {
        for (String noVert : new String[]{"25", "50", "100", "400"}) {
            for (String typ : new String[]{"l", "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        execute(typ + "_" + reqSize + "_" + noVert + "_" + id);
                    }
                }
            }
        }
    }

    private void execute(String file) {
        Solver solver = new Solver(rootDirectory, file + ".txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

}
