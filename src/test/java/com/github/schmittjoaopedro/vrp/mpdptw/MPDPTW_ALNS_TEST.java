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
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 5265.6562907478765, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 4 BFS = 4876.311938952875, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 36 BFS = 4585.850993238566, feasible = true");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 900 BFS = 4585.850993238565, feasible = true");
        assertThat(log.get(5)).contains("Instance = l_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 15 18 16 19 20 21 17 0\n" +
                "0 7 5 3 1 13 14 8 4 6 2 0\n" +
                "0 23 11 22 24 25 10 9 12 0\n" +
                "Requests\n" +
                "5 6\n" +
                "4 2 0 1\n" +
                "3 7\n" +
                "Cost = 4585.850993238565\n" +
                "Num. vehicles = 3");
    }

    @Test
    public void mpdptw_normal_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 5456.053007895955, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 3 BFS = 5108.860823037317, feasible = true");
        assertThat(log.get(3)).contains("Instance = n_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 13 14 4 6 0\n" +
                "0 23 11 22 24 25 10 9 12 0\n" +
                "0 7 15 8 1 16 2 17 0\n" +
                "0 18 19 20 21 0\n" +
                "Requests\n" +
                "4 1\n" +
                "3 7\n" +
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
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 4726.727878734164, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 2 BFS = 3982.772943037291, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 594 BFS = 3979.501754513747, feasible = true");
        assertThat(log.get(4)).contains("Instance = w_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 23 1 22 24 15 25 16 2 17 0\n" +
                "0 18 7 5 19 3 20 21 13 14 8 4 6 0\n" +
                "0 9 11 10 12 0\n" +
                "Requests\n" +
                "7 5 0\n" +
                "2 1 4 6\n" +
                "3\n" +
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
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 6657.017613416864, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 68 BFS = 6221.1468619143625, feasible = true");
        assertThat(log.get(3)).contains("Instance = l_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 29 25 33 31 26 32 28 24 30 34 27 0\n" +
                "0 42 43 13 15 49 47 44 46 45 50 48 14 16 0\n" +
                "0 5 8 6 3 1 9 4 2 7 10 0\n" +
                "0 22 11 19 18 12 17 20 21 23 0\n" +
                "0 36 37 35 39 38 40 41 0\n" +
                "Requests\n" +
                "6 5\n" +
                "3 9 8\n" +
                "1 0\n" +
                "4 2\n" +
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
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 7688.999168652441, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 10 BFS = 7631.966003915071, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 8400 BFS = 7631.96600391507, feasible = true");
        assertThat(log.get(4)).contains("Instance = n_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 22 11 19 18 12 17 20 21 23 0\n" +
                "0 5 8 6 3 4 1 9 2 7 10 0\n" +
                "0 36 35 37 39 38 40 41 0\n" +
                "0 25 26 24 27 0\n" +
                "0 42 43 49 47 45 44 46 50 48 0\n" +
                "0 29 33 31 32 28 30 34 0\n" +
                "0 15 13 14 16 0\n" +
                "Requests\n" +
                "4 2\n" +
                "1 0\n" +
                "7\n" +
                "5\n" +
                "8 9\n" +
                "6\n" +
                "3\n" +
                "Cost = 7631.96600391507\n" +
                "Num. vehicles = 7");
    }

    @Test
    public void mpdptw_without_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 4996.067384581166, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 36 BFS = 4793.62827780313, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 38 BFS = 4662.937035954059, feasible = true");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 138 BFS = 4574.279678062881, feasible = true");
        assertThat(log.get(5)).isEqualTo("NEW BEST = Iter 1252 BFS = 4571.69645898469, feasible = true");
        assertThat(log.get(6)).isEqualTo("NEW BEST = Iter 1500 BFS = 4569.1382309839555, feasible = true");
        assertThat(log.get(7)).contains("Instance = w_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 42 45 46 47 49 44 15 13 43 11 12 24 14 16 26 25 48 50 27 0\n" +
                "0 20 29 1 31 33 17 32 28 2 19 18 30 34 21 22 23 0\n" +
                "0 5 36 40 38 39 4 9 35 37 3 6 8 7 10 41 0\n" +
                "Requests\n" +
                "8 2 3 9 5\n" +
                "6 0 4\n" +
                "7 1\n" +
                "Cost = 4569.1382309839555\n" +
                "Num. vehicles = 3");
    }

    @Test
    public void mpdptw_without_8_25_3_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_25_3.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 3589.7020398715913, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 1 BFS = 3030.2826703620926, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 1264 BFS = 2950.7741171841853, feasible = true");
        assertThat(log.get(4)).contains("Instance = w_8_25_3.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 9 6 8 1 15 3 16 14 11 13 5 2 7 4 17 18 10 12 0\n" +
                "0 21 25 23 24 19 26 27 20 22 28 0\n" +
                "Requests\n" +
                "1 3 2 0\n" +
                "4 5 6\n" +
                "Cost = 2950.7741171841853\n" +
                "Num. vehicles = 2");
    }
}
