package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 10;

    private static int maxIterations = 1000;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() {
        Solver solver = new Solver(rootDirectory, "l_4_25_1.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_4_25_4_test() {
        Solver solver = new Solver(rootDirectory, "l_4_25_4.txt", maxIterations, 1, 0.02, statisticInterval, true);
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
        Solver solver = new Solver(rootDirectory, "l_4_400_3.txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

    @Test
    public void mpdptw_large_4_400_1_test() {
        execute("l_4_400_1");
        execute("l_4_400_2");
        execute("l_4_400_3");
        execute("l_4_400_4");
        execute("l_4_400_5");

        execute("l_8_400_1");
        execute("l_8_400_2");
        execute("l_8_400_3");
        execute("l_8_400_4");
        execute("l_8_400_5");

        execute("n_4_400_1");
        execute("n_4_400_2");
        execute("n_4_400_3");
        execute("n_4_400_4");
        execute("n_4_400_5");

        execute("n_8_400_1");
        execute("n_8_400_2");
        execute("n_8_400_3");
        execute("n_8_400_4");
        execute("n_8_400_5");

        execute("w_4_400_1");
        execute("w_4_400_2");
        execute("w_4_400_3");
        execute("w_4_400_4");
        execute("w_4_400_5");

        execute("w_8_400_1");
        execute("w_8_400_2");
        execute("w_8_400_3");
        execute("w_8_400_4");
        execute("w_8_400_5");
    }

    private void execute(String file) {
        Solver solver = new Solver(rootDirectory, file + ".txt", maxIterations, 1, 0.02, statisticInterval, true);
        solver.run();
    }

}
