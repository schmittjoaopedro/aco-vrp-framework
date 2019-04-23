package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
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
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 3 BFS = 4998.065349148712, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 14 BFS = 4941.334110004384, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 101 BFS = 4876.311938952875, feasible = true");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 158 BFS = 4585.850993238566, feasible = true");
        assertThat(log.get(5)).isEqualTo("NEW BEST = Iter 3200 BFS = 4585.850993238565, feasible = true");
        assertThat(log.get(6)).contains("Instance = l_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-7-5-3-1-13-14-8-4-6-2-0\n" +
                "0-15-18-16-19-20-21-17-0\n" +
                "0-23-11-22-24-25-10-9-12-0\n" +
                "Requests\n" +
                "4-2-1-0\n" +
                "6-5\n" +
                "3-7\n" +
                "Cost = 4585.850993238565\n" +
                "Num. vehicles = 3\n" +
                "Total time (s) = ");
    }

    @Test
    public void mpdptw_normal_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 2 BFS = 5108.860823037317, feasible = true");
        assertThat(log.get(2)).contains("Instance = n_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-5-3-13-14-4-6-0\n" +
                "0-23-11-22-24-25-10-9-12-0\n" +
                "0-7-15-8-1-16-2-17-0\n" +
                "0-18-19-20-21-0\n" +
                "Requests\n" +
                "1-4\n" +
                "7-3\n" +
                "5-0-2\n" +
                "6\n" +
                "Cost = 5108.860823037317\n" +
                "Num. vehicles = 4\n" +
                "Total time (s) = ");
    }

    @Test
    public void mpdptw_without_4_25_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_4_25_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 13 BFS = 4563.334730520919, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 16 BFS = 3982.7729430372915, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 500 BFS = 3982.772943037291, feasible = true");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 2046 BFS = 3979.501754513747, feasible = true");
        assertThat(log.get(5)).contains("Instance = w_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-23-1-22-24-15-25-16-2-17-0\n" +
                "0-18-7-5-19-3-20-21-13-14-8-4-6-0\n" +
                "0-9-11-10-12-0\n" +
                "Requests\n" +
                "7-5-0\n" +
                "2-1-4-6\n" +
                "3\n" +
                "Cost = 3979.501754513747\n" +
                "Num. vehicles = 3\n" +
                "Total time (s) = ");
    }

    @Test
    public void mpdptw_large_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 29 BFS = 6221.1468619143625, feasible = true");
        assertThat(log.get(2)).contains("Instance = l_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-29-25-33-31-26-32-28-24-30-34-27-0\n" +
                "0-42-43-13-15-49-47-44-46-45-50-48-14-16-0\n" +
                "0-22-11-19-18-12-17-20-21-23-0\n" +
                "0-5-8-6-3-1-9-4-2-7-10-0\n" +
                "0-36-37-35-39-38-40-41-0\n" +
                "Requests\n" +
                "5-6\n" +
                "9-3-8\n" +
                "2-4\n" +
                "0-1\n" +
                "7\n" +
                "Cost = 6221.1468619143625\n" +
                "Num. vehicles = 5\n" +
                "Total time (s) = ");
    }

    @Test
    public void mpdptw_normal_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 2 BFS = 7631.966003915071, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 1700 BFS = 7631.96600391507, feasible = true");
        assertThat(log.get(3)).contains("Instance = n_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-5-8-6-3-4-1-9-2-7-10-0\n" +
                "0-15-13-14-16-0\n" +
                "0-25-26-24-27-0\n" +
                "0-42-43-49-47-45-44-46-50-48-0\n" +
                "0-29-33-31-32-28-30-34-0\n" +
                "0-36-35-37-39-38-40-41-0\n" +
                "0-22-11-19-18-12-17-20-21-23-0\n" +
                "Requests\n" +
                "1-0\n" +
                "3\n" +
                "5\n" +
                "9-8\n" +
                "6\n" +
                "7\n" +
                "4-2\n" +
                "Cost = 7631.96600391507\n" +
                "Num. vehicles = 7\n" +
                "Total time (s) = ");
    }

    @Test
    public void mpdptw_without_8_50_1_test() throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_50_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> log = alns.getLog();
        assertThat(log.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]");
        assertThat(log.get(1)).isEqualTo("NEW BEST = Iter 200 BFS = 5181.850556942561, feasible = true");
        assertThat(log.get(2)).isEqualTo("NEW BEST = Iter 300 BFS = 5150.445499311903, feasible = true");
        assertThat(log.get(3)).isEqualTo("NEW BEST = Iter 302 BFS = 5016.105785800849, feasible = true");
        assertThat(log.get(4)).isEqualTo("NEW BEST = Iter 1689 BFS = 4833.043967519014, feasible = true");
        assertThat(log.get(5)).isEqualTo("NEW BEST = Iter 4507 BFS = 4777.88851276985, feasible = true");
        assertThat(log.get(6)).isEqualTo("NEW BEST = Iter 6400 BFS = 4759.719115331629, feasible = true");
        assertThat(log.get(7)).isEqualTo("NEW BEST = Iter 6857 BFS = 4735.153130378827, feasible = true");
        assertThat(log.get(8)).isEqualTo("NEW BEST = Iter 7293 BFS = 4725.42301107687, feasible = true");
        assertThat(log.get(9)).contains("Instance = w_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-29-25-33-31-26-32-28-24-30-34-43-44-49-47-46-45-42-27-50-48-0\n" +
                "0-40-38-39-4-9-1-37-3-6-8-7-2-5-10-35-36-41-0\n" +
                "0-22-21-15-13-11-19-18-12-14-16-17-20-23-0\n" +
                "Requests\n" +
                "6-9-8-5\n" +
                "1-0-7\n" +
                "4-2-3\n" +
                "Cost = 4725.42301107687\n" +
                "Num. vehicles = 3\n" +
                "Total time (s) = ");
    }
}
