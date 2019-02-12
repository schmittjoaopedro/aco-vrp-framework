package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "l_4_25_1.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_4_25_4_test() {
        Solver solver = new Solver(rootDirectory, "l_4_25_4.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "n_4_25_1.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "l_8_100_5.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_without_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "w_8_100_5.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_100_5_test() {
        Solver solver = new Solver(rootDirectory, "n_8_100_5.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

    @Test
    public void mpdptw_normal_8_400_1_test() {
        Solver solver = new Solver(rootDirectory, "n_8_400_1.txt", 1000, 1, 0.02, 1, true);
        solver.run();
    }

}
