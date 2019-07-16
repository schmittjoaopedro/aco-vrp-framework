package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MPDPTW_ALNS_TEST {

    private static final String rootDirectory;

    int maxIterations = 100000;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 4, TC = 5521.75]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 4, TC = 4933.04]");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 10 Sol = [F = true, NV = 3, TC = 4585.85]");
        assertThat(log.get(4)).contains("Instance = l_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 7 5 3 1 13 14 8 4 6 2 0\n" +
                "0 23 11 22 24 25 10 9 12 0\n" +
                "0 15 18 16 19 20 21 17 0\n" +
                "Requests\n" +
                "0 1 4 2\n" +
                "3 7\n" +
                "5 6\n" +
                "Cost = 4585.850993238566\n" +
                "Num. vehicles = 3");
    }

    @Test
    public void mpdptw_normal_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 5, TC = 5456.05]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 7 Sol = [F = true, NV = 4, TC = 5108.86]");
        assertThat(log.get(3)).contains("Instance = n_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 13 14 4 6 0\n" +
                "0 23 11 22 24 25 10 9 12 0\n" +
                "0 7 15 8 1 16 2 17 0\n" +
                "0 18 19 20 21 0\n" +
                "Requests\n" +
                "1 4\n" +
                "7 3\n" +
                "5 0 2\n" +
                "6\n" +
                "Cost = 5108.860823037317\n" +
                "Num. vehicles = 4");
    }

    @Test
    public void mpdptw_without_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 4, TC = 4726.73]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 3 Sol = [F = true, NV = 3, TC = 4080.78]");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 109 Sol = [F = true, NV = 3, TC = 3982.77]");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 500 Sol = [F = true, NV = 3, TC = 3979.5]");
        assertThat(log.get(5)).contains("Instance = w_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 23 1 22 24 15 25 16 2 17 0\n" +
                "0 9 11 10 12 0\n" +
                "0 18 7 5 19 3 20 21 13 14 8 4 6 0\n" +
                "Requests\n" +
                "0 5 7\n" +
                "3\n" +
                "4 6 2 1\n" +
                "Cost = 3979.501754513747\n" +
                "Num. vehicles = 3");
    }

    @Test
    public void mpdptw_large_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 6, TC = 6657.02]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 3 Sol = [F = true, NV = 5, TC = 6221.15]");
        assertThat(log.get(3)).contains("Instance = l_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 8 6 3 1 9 4 2 7 10 0\n" +
                "0 42 43 13 15 49 47 44 46 45 50 48 14 16 0\n" +
                "0 22 11 19 18 12 17 20 21 23 0\n" +
                "0 29 25 33 31 26 32 28 24 30 34 27 0\n" +
                "0 36 37 35 39 38 40 41 0\n" +
                "Requests\n" +
                "0 1\n" +
                "3 9 8\n" +
                "2 4\n" +
                "5 6\n" +
                "7\n" +
                "Cost = 6221.1468619143625\n" +
                "Num. vehicles = 5");
    }

    @Test
    public void mpdptw_normal_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 7, TC = 7689.0]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 33 Sol = [F = true, NV = 7, TC = 7631.97]");
        assertThat(log.get(3)).contains("Instance = n_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 8 6 3 4 1 9 2 7 10 0\n" +
                "0 15 13 14 16 0\n" +
                "0 22 11 19 18 12 17 20 21 23 0\n" +
                "0 29 33 31 32 28 30 34 0\n" +
                "0 36 35 37 39 38 40 41 0\n" +
                "0 25 26 24 27 0\n" +
                "0 42 43 49 47 45 44 46 50 48 0\n" +
                "Requests\n" +
                "1 0\n" +
                "3\n" +
                "4 2\n" +
                "6\n" +
                "7\n" +
                "5\n" +
                "9 8\n" +
                "Cost = 7631.966003915071\n" +
                "Num. vehicles = 7");
    }

    @Test
    public void mpdptw_without_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 4, TC = 4996.07]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 16 Sol = [F = true, NV = 3, TC = 4727.31]");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 67 Sol = [F = true, NV = 3, TC = 4574.28]");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 102 Sol = [F = true, NV = 3, TC = 4569.14]");
        assertThat(log.get(5)).contains("Instance = w_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 42 45 46 47 49 44 15 13 43 11 12 24 14 16 26 25 48 50 27 0\n" +
                "0 20 29 1 31 33 17 32 28 2 19 18 30 34 21 22 23 0\n" +
                "0 5 36 40 38 39 4 9 35 37 3 6 8 7 10 41 0\n" +
                "Requests\n" +
                "2 3 9 8 5\n" +
                "0 6 4\n" +
                "1 7\n" +
                "Cost = 4569.1382309839555\n" +
                "Num. vehicles = 3");
    }

    @Test
    public void mpdptw_without_8_25_3_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_25_3.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.setShowLog(true);
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 3, TC = 3589.7]");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 2, TC = 3030.28]");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 63 Sol = [F = true, NV = 2, TC = 2950.77]");
        assertThat(log.get(4)).contains("Instance = w_8_25_3.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 9 6 8 1 15 3 16 14 11 13 5 2 7 4 17 18 10 12 0\n" +
                "0 21 25 23 24 19 26 27 20 22 28 0\n" +
                "Requests\n" +
                "3 1 2 0\n" +
                "4 5 6\n" +
                "Cost = 2950.7741171841853\n" +
                "Num. vehicles = 2");
    }
}
