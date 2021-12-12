package com.github.schmittjoaopedro.vrp.dpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MDPDPTW_ALNS_TEST {

    private static String dpdptw100Directory;

    static {
        try {
            dpdptw100Directory = Paths.get(DPDPTW_ALNS_TEST.class.getClassLoader().getResource("dpdptw_100").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dpdptw_lc101_a_0_1_test() throws IOException {
        String problem = "lc101_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17 Sol = [F = true, NV = 1, TC = 68.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1, 2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 25 Sol = [F = true, NV = 3, TC = 170.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 41 Sol = [F = true, NV = 5, TC = 277.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 49 Sol = [F = true, NV = 9, TC = 486.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 57 Sol = [F = true, NV = 10, TC = 603.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 Sol = [F = true, NV = 10, TC = 745.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 Sol = [F = true, NV = 11, TC = 702.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 Sol = [F = true, NV = 10, TC = 658.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17, 18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 Sol = [F = true, NV = 10, TC = 667.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 170 Sol = [F = true, NV = 10, TC = 713.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23, 24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 235 Sol = [F = true, NV = 10, TC = 733.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26, 27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 243 Sol = [F = true, NV = 10, TC = 750.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29, 30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 Sol = [F = true, NV = 10, TC = 773.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 Sol = [F = true, NV = 10, TC = 779.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 Sol = [F = true, NV = 10, TC = 884.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 Sol = [F = true, NV = 10, TC = 793.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 397 Sol = [F = true, NV = 10, TC = 794.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39, 40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 Sol = [F = true, NV = 10, TC = 801.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 543 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 10, TC = 816.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 Sol = [F = true, NV = 10, TC = 822.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50, 51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 688 Sol = [F = true, NV = 10, TC = 827.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "Requests\n" +
                "6 8 7 9\n" +
                "10 12 13 14 15 11\n" +
                "31 29 28 30\n" +
                "1 0 3 5 4 2\n" +
                "51 49 48 50 52\n" +
                "16 17 19 20 18\n" +
                "22 21 23 24 27 26 25\n" +
                "37 35 33 34 32 36\n" +
                "47 46 45 43 44\n" +
                "42 40 38 39 41\n" +
                "Cost = 828.936866942834\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_a_0_5_test() throws IOException {
        String problem = "lc101_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 Sol = [F = true, NV = 1, TC = 68.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 122 Sol = [F = true, NV = 2, TC = 140.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 154 Sol = [F = true, NV = 3, TC = 170.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 203 Sol = [F = true, NV = 4, TC = 203.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 211 Sol = [F = true, NV = 5, TC = 277.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 Sol = [F = true, NV = 7, TC = 371.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 259 Sol = [F = true, NV = 8, TC = 416.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 276 Sol = [F = true, NV = 9, TC = 486.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 Sol = [F = true, NV = 10, TC = 603.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 Sol = [F = true, NV = 10, TC = 608.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 Sol = [F = true, NV = 11, TC = 681.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 Sol = [F = true, NV = 11, TC = 699.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 Sol = [F = true, NV = 11, TC = 710.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 Sol = [F = true, NV = 11, TC = 711.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 11, TC = 712.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 Sol = [F = true, NV = 11, TC = 718.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 Sol = [F = true, NV = 11, TC = 721.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 Sol = [F = true, NV = 11, TC = 745.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 Sol = [F = true, NV = 11, TC = 767.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 Sol = [F = true, NV = 11, TC = 770.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 Sol = [F = true, NV = 11, TC = 787.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 Sol = [F = true, NV = 11, TC = 792.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 Sol = [F = true, NV = 11, TC = 796.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 Sol = [F = true, NV = 11, TC = 804.0]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 Sol = [F = true, NV = 11, TC = 812.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 Sol = [F = true, NV = 11, TC = 827.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 Sol = [F = true, NV = 11, TC = 834.0]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 Sol = [F = true, NV = 11, TC = 835.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 Sol = [F = true, NV = 11, TC = 838.84]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 Sol = [F = true, NV = 11, TC = 844.84]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 Sol = [F = true, NV = 11, TC = 847.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 Sol = [F = true, NV = 11, TC = 848.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 Sol = [F = true, NV = 11, TC = 849.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 Sol = [F = true, NV = 11, TC = 855.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 Sol = [F = true, NV = 11, TC = 858.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 Sol = [F = true, NV = 11, TC = 858.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 Sol = [F = true, NV = 11, TC = 858.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 Sol = [F = true, NV = 11, TC = 864.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 Sol = [F = true, NV = 11, TC = 864.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 Sol = [F = true, NV = 11, TC = 867.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 Sol = [F = true, NV = 11, TC = 870.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 Sol = [F = true, NV = 11, TC = 875.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 Sol = [F = true, NV = 11, TC = 876.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 Sol = [F = true, NV = 11, TC = 878.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 Sol = [F = true, NV = 11, TC = 881.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 Sol = [F = true, NV = 11, TC = 882.99]");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 90 88 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 87 86 83 82 84 85 89 91 0\n" +
                "Requests\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 13 15 14 11\n" +
                "1 0 3 4 5 2\n" +
                "51 49 48 50 52\n" +
                "22 21 23 26 24 27 25\n" +
                "47\n" +
                "37 35 32 33 34 36\n" +
                "16 17 18 19 20\n" +
                "42 40 39 38 41\n" +
                "46 45 44 43\n" +
                "Cost = 882.9855940407241\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lc101_a_0_9_test() throws IOException {
        String problem = "lc101_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 Sol = [F = true, NV = 1, TC = 68.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 219 Sol = [F = true, NV = 2, TC = 140.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 284 Sol = [F = true, NV = 3, TC = 170.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 373 Sol = [F = true, NV = 4, TC = 203.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 381 Sol = [F = true, NV = 5, TC = 277.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 Sol = [F = true, NV = 7, TC = 371.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 Sol = [F = true, NV = 8, TC = 416.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 494 Sol = [F = true, NV = 9, TC = 486.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 10, TC = 603.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 Sol = [F = true, NV = 10, TC = 654.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 858 Sol = [F = true, NV = 10, TC = 667.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 866 Sol = [F = true, NV = 10, TC = 676.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 907 Sol = [F = true, NV = 10, TC = 693.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 12, TC = 771.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1481 Sol = [F = true, NV = 13, TC = 811.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1513 Sol = [F = true, NV = 13, TC = 818.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 Sol = [F = true, NV = 13, TC = 818.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 Sol = [F = true, NV = 13, TC = 842.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 Sol = [F = true, NV = 13, TC = 864.61]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2145 Sol = [F = true, NV = 13, TC = 867.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2169 Sol = [F = true, NV = 13, TC = 874.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2177 Sol = [F = true, NV = 14, TC = 920.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2193 Sol = [F = true, NV = 14, TC = 926.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2201 Sol = [F = true, NV = 14, TC = 929.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2225 Sol = [F = true, NV = 14, TC = 937.25]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 Sol = [F = true, NV = 14, TC = 945.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2800 Sol = [F = true, NV = 14, TC = 960.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 Sol = [F = true, NV = 14, TC = 966.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3496 Sol = [F = true, NV = 14, TC = 968.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 Sol = [F = true, NV = 14, TC = 971.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3528 Sol = [F = true, NV = 14, TC = 986.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3544 Sol = [F = true, NV = 14, TC = 989.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 Sol = [F = true, NV = 14, TC = 990.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4183 Sol = [F = true, NV = 14, TC = 991.29]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 Sol = [F = true, NV = 14, TC = 998.02]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4232 Sol = [F = true, NV = 14, TC = 1000.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 Sol = [F = true, NV = 14, TC = 1000.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4887 Sol = [F = true, NV = 14, TC = 1002.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 Sol = [F = true, NV = 14, TC = 1007.95]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4952 Sol = [F = true, NV = 14, TC = 1007.95]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4968 Sol = [F = true, NV = 14, TC = 1010.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 14, TC = 1013.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5534 Sol = [F = true, NV = 14, TC = 1018.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5559 Sol = [F = true, NV = 14, TC = 1020.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6206 Sol = [F = true, NV = 14, TC = 1021.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6246 Sol = [F = true, NV = 14, TC = 1023.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7395 Sol = [F = true, NV = 14, TC = 1025.01]");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 5 7 8 10 11 9 6 4 2 1 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 43 65 41 72 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 67 61 64 102 66 69 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 3 75 0\n" +
                "0 42 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 63 74 0\n" +
                "0 62 68 0\n" +
                "Requests\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 14 15 13 11\n" +
                "1 3 5 4 2\n" +
                "51 49 48 50 52\n" +
                "22 35\n" +
                "47 46 45 43 44\n" +
                "37 34 36\n" +
                "16 17 18 20 19\n" +
                "42 40 39 38 41\n" +
                "0\n" +
                "21 23 24 27 26 25\n" +
                "33\n" +
                "32\n" +
                "Cost = 1025.0141560857844\n" +
                "Num. vehicles = 14");
    }

    @Test
    public void dpdptw_lc101_q_0_0_1_test() throws IOException {
        String problem = "lc101_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 2, TC = 182.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 187 Sol = [F = true, NV = 3, TC = 245.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 Sol = [F = true, NV = 4, TC = 306.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 Sol = [F = true, NV = 5, TC = 336.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 413 Sol = [F = true, NV = 6, TC = 363.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 429 Sol = [F = true, NV = 7, TC = 408.64]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 Sol = [F = true, NV = 8, TC = 453.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 Sol = [F = true, NV = 9, TC = 487.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 10, TC = 558.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 Sol = [F = true, NV = 11, TC = 675.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 12, TC = 723.1]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 Sol = [F = true, NV = 13, TC = 787.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 Sol = [F = true, NV = 13, TC = 796.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 Sol = [F = true, NV = 14, TC = 875.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 Sol = [F = true, NV = 16, TC = 953.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 Sol = [F = true, NV = 17, TC = 992.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 Sol = [F = true, NV = 17, TC = 993.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 Sol = [F = true, NV = 17, TC = 999.67]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 Sol = [F = true, NV = 18, TC = 1056.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 Sol = [F = true, NV = 18, TC = 1080.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 Sol = [F = true, NV = 18, TC = 1102.06]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 Sol = [F = true, NV = 18, TC = 1104.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 Sol = [F = true, NV = 18, TC = 1112.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 Sol = [F = true, NV = 18, TC = 1117.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 Sol = [F = true, NV = 18, TC = 1121.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 18, TC = 1144.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 Sol = [F = true, NV = 18, TC = 1152.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 Sol = [F = true, NV = 18, TC = 1161.52]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 Sol = [F = true, NV = 18, TC = 1167.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 Sol = [F = true, NV = 18, TC = 1167.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 Sol = [F = true, NV = 18, TC = 1172.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 Sol = [F = true, NV = 18, TC = 1194.55]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 Sol = [F = true, NV = 18, TC = 1197.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 Sol = [F = true, NV = 18, TC = 1200.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 Sol = [F = true, NV = 18, TC = 1221.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 Sol = [F = true, NV = 18, TC = 1233.52]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 Sol = [F = true, NV = 18, TC = 1235.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 Sol = [F = true, NV = 18, TC = 1276.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 Sol = [F = true, NV = 18, TC = 1279.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 Sol = [F = true, NV = 18, TC = 1285.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 18, TC = 1285.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 Sol = [F = true, NV = 19, TC = 1329.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 Sol = [F = true, NV = 19, TC = 1333.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 Sol = [F = true, NV = 19, TC = 1337.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 19, TC = 1338.03]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 Sol = [F = true, NV = 19, TC = 1339.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 Sol = [F = true, NV = 19, TC = 1341.02]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 Sol = [F = true, NV = 19, TC = 1342.83]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 41 45 48 51 101 50 52 49 47 0\n" +
                "0 11 1 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 26 23 103 0\n" +
                "0 5 7 8 10 30 21 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 82 85 88 0\n" +
                "0 67 62 61 68 0\n" +
                "0 32 31 35 39 36 105 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 65 72 64 102 66 69 0\n" +
                "0 87 83 84 89 0\n" +
                "0 33 37 38 34 0\n" +
                "0 3 75 0\n" +
                "0 42 40 44 46 28 22 0\n" +
                "0 63 74 9 4 0\n" +
                "0 86 91 0\n" +
                "0 6 2 0\n" +
                "Requests\n" +
                "22 24 27 26 25\n" +
                "5\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 14 11\n" +
                "1 3 15\n" +
                "51 49 48 50 52\n" +
                "47 43\n" +
                "37 32\n" +
                "16 18 19\n" +
                "42 40 39 38 41\n" +
                "35 34 36\n" +
                "46 44\n" +
                "17 20\n" +
                "0\n" +
                "21 23 13\n" +
                "33 4\n" +
                "45\n" +
                "2\n" +
                "Cost = 1342.8306403354761\n" +
                "Num. vehicles = 19");
    }

    @Test
    public void dpdptw_lc101_q_0_0_5_test() throws IOException {
        String problem = "lc101_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 9, TC = 1273.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 9, TC = 1200.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4 Sol = [F = true, NV = 9, TC = 1142.03]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 9, TC = 1120.0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9 Sol = [F = true, NV = 9, TC = 1114.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16 Sol = [F = true, NV = 9, TC = 1105.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 Sol = [F = true, NV = 10, TC = 1177.46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 252 Sol = [F = true, NV = 10, TC = 927.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 255 Sol = [F = true, NV = 10, TC = 914.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 257 Sol = [F = true, NV = 10, TC = 880.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 261 Sol = [F = true, NV = 10, TC = 876.03]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 275 Sol = [F = true, NV = 10, TC = 861.44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 281 Sol = [F = true, NV = 10, TC = 854.77]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 282 Sol = [F = true, NV = 10, TC = 850.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 Sol = [F = true, NV = 11, TC = 828.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 Sol = [F = true, NV = 12, TC = 867.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 13, TC = 937.7]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 Sol = [F = true, NV = 14, TC = 1040.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 621 Sol = [F = true, NV = 14, TC = 1035.55]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 15, TC = 1082.58]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 Sol = [F = true, NV = 16, TC = 1147.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 Sol = [F = true, NV = 16, TC = 1155.46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 994 Sol = [F = true, NV = 16, TC = 1152.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 Sol = [F = true, NV = 17, TC = 1192.33]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 Sol = [F = true, NV = 17, TC = 1196.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 17, TC = 1258.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 17, TC = 1255.55]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 Sol = [F = true, NV = 17, TC = 1263.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 Sol = [F = true, NV = 17, TC = 1270.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 Sol = [F = true, NV = 17, TC = 1302.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 Sol = [F = true, NV = 17, TC = 1307.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 Sol = [F = true, NV = 17, TC = 1354.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 Sol = [F = true, NV = 17, TC = 1361.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 Sol = [F = true, NV = 17, TC = 1367.13]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 17, TC = 1431.58]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 Sol = [F = true, NV = 17, TC = 1442.6]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 Sol = [F = true, NV = 18, TC = 1519.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 18, TC = 1519.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 Sol = [F = true, NV = 18, TC = 1523.29]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 9 4 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 33 37 30 21 0\n" +
                "0 42 40 44 46 45 48 50 52 0\n" +
                "0 67 62 61 64 102 68 0\n" +
                "0 90 86 88 91 0\n" +
                "0 78 104 51 101 49 47 0\n" +
                "0 98 96 95 94 92 93 36 105 0\n" +
                "0 97 106 6 2 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 28 22 0\n" +
                "0 43 41 35 38 39 34 0\n" +
                "0 32 31 29 26 23 103 0\n" +
                "0 81 76 71 70 73 77 79 80 0\n" +
                "0 65 72 66 69 0\n" +
                "0 87 83 82 84 85 89 0\n" +
                "0 63 74 11 1 0\n" +
                "0 100 99 0\n" +
                "Requests\n" +
                "1 0 3 4\n" +
                "6 8 9 7\n" +
                "17 15\n" +
                "21 23 24 26\n" +
                "37 32 34\n" +
                "47 45\n" +
                "40 27 25\n" +
                "51 49 48 19\n" +
                "50 2\n" +
                "31 29 28 30\n" +
                "10 12 13\n" +
                "22 18 20\n" +
                "16 14 11\n" +
                "42 39 38 41\n" +
                "35 36\n" +
                "46 43 44\n" +
                "33 5\n" +
                "52\n" +
                "Cost = 1523.2880216280541\n" +
                "Num. vehicles = 18");
    }

    @Test
    public void dpdptw_lc101_q_0_0_9_test() throws IOException {
        String problem = "lc101_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 10, TC = 824.25]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 Sol = [F = true, NV = 10, TC = 827.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 10, TC = 827.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 11, TC = 853.18]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 29 30 28 26 22 21 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 23 103 0\n" +
                "Requests\n" +
                "0 1 2 3 4 5\n" +
                "6 7 8 9\n" +
                "10 12 13 14 15\n" +
                "16 17 18 20 19\n" +
                "21 22 23 24 27 25 26\n" +
                "28 29 30 31\n" +
                "32 33 34 35 37 36\n" +
                "39 40 41 42 38\n" +
                "43 44 45 46 47\n" +
                "48 49 50 51 52\n" +
                "11\n" +
                "Cost = 853.1795076299532\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_a_0_1_test() throws IOException {
        String problem = "lrc107_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 334 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2, 3, 4, 5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 Sol = [F = true, NV = 5, TC = 588.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 Sol = [F = true, NV = 9, TC = 1116.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 585 Sol = [F = true, NV = 9, TC = 1100.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 586 Sol = [F = true, NV = 9, TC = 1075.35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 589 Sol = [F = true, NV = 9, TC = 1033.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 591 Sol = [F = true, NV = 9, TC = 975.25]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 Sol = [F = true, NV = 11, TC = 1052.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 725 Sol = [F = true, NV = 11, TC = 1013.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 Sol = [F = true, NV = 12, TC = 1259.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 800 Sol = [F = true, NV = 12, TC = 1256.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 814 Sol = [F = true, NV = 12, TC = 1224.3]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 14, TC = 1451.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 837 Sol = [F = true, NV = 14, TC = 1374.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 Sol = [F = true, NV = 14, TC = 1549.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 922 Sol = [F = true, NV = 14, TC = 1515.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 Sol = [F = true, NV = 14, TC = 1573.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1040 Sol = [F = true, NV = 14, TC = 1528.6]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 Sol = [F = true, NV = 14, TC = 1704.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1169 Sol = [F = true, NV = 14, TC = 1680.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1176 Sol = [F = true, NV = 14, TC = 1519.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 Sol = [F = true, NV = 14, TC = 1637.0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1300 Sol = [F = true, NV = 14, TC = 1625.38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1304 Sol = [F = true, NV = 14, TC = 1604.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1307 Sol = [F = true, NV = 14, TC = 1550.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 Sol = [F = true, NV = 14, TC = 1642.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 Sol = [F = true, NV = 14, TC = 1624.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1612 Sol = [F = true, NV = 14, TC = 1537.43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1686 Sol = [F = true, NV = 14, TC = 1519.79]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 Sol = [F = true, NV = 14, TC = 1515.41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5800 Sol = [F = true, NV = 14, TC = 1515.4]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 75 87 59 97 58 77 74 105 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 83 86 22 66 0\n" +
                "0 95 92 62 84 63 85 76 89 91 102 0\n" +
                "0 98 11 15 73 78 60 70 68 0\n" +
                "0 52 64 51 18 48 25 24 80 0\n" +
                "0 82 99 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 61 81 106 90 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 42 39 38 41 44 43 40 37 35 36 0\n" +
                "0 65 23 21 19 49 20 57 56 0\n" +
                "0 69 88 79 53 0\n" +
                "Requests\n" +
                "18 15 19 17 16\n" +
                "41 51 40 45\n" +
                "1 4 0 3 5 2 29\n" +
                "44 13\n" +
                "50 32 48 47 33\n" +
                "52 6 39 30\n" +
                "28 34 27 10\n" +
                "43\n" +
                "7 8 9 25\n" +
                "31 42\n" +
                "38 37 26 49\n" +
                "24 22 21 23 20\n" +
                "35 14 12 11\n" +
                "36 46\n" +
                "Cost = 1515.4033356960215\n" +
                "Num. vehicles = 14");
    }

    @Test
    public void dpdptw_lrc107_a_0_5_test() throws IOException {
        String problem = "lrc107_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2000 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 Sol = [F = true, NV = 3, TC = 303.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2584 Sol = [F = true, NV = 4, TC = 359.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2667 Sol = [F = true, NV = 4, TC = 368.42]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2750 Sol = [F = true, NV = 6, TC = 557.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2834 Sol = [F = true, NV = 7, TC = 647.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2917 Sol = [F = true, NV = 7, TC = 652.03]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13, 14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 Sol = [F = true, NV = 8, TC = 892.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 Sol = [F = true, NV = 8, TC = 876.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3001 Sol = [F = true, NV = 8, TC = 861.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3005 Sol = [F = true, NV = 8, TC = 860.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3018 Sol = [F = true, NV = 8, TC = 850.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3084 Sol = [F = true, NV = 8, TC = 863.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3166 Sol = [F = true, NV = 8, TC = 862.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 Sol = [F = true, NV = 9, TC = 928.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 Sol = [F = true, NV = 10, TC = 1024.61]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 Sol = [F = true, NV = 12, TC = 1162.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 Sol = [F = true, NV = 12, TC = 1173.29]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 13, TC = 1242.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 Sol = [F = true, NV = 13, TC = 1316.19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3863 Sol = [F = true, NV = 13, TC = 1304.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 Sol = [F = true, NV = 13, TC = 1307.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 Sol = [F = true, NV = 13, TC = 1355.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 13, TC = 1435.43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4100 Sol = [F = true, NV = 13, TC = 1428.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 Sol = [F = true, NV = 13, TC = 1520.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4233 Sol = [F = true, NV = 13, TC = 1485.13]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 Sol = [F = true, NV = 13, TC = 1536.28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 Sol = [F = true, NV = 13, TC = 1471.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 Sol = [F = true, NV = 14, TC = 1586.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 Sol = [F = true, NV = 14, TC = 1567.31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4471 Sol = [F = true, NV = 14, TC = 1539.55]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 Sol = [F = true, NV = 14, TC = 1566.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4501 Sol = [F = true, NV = 14, TC = 1544.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 14, TC = 1585.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 14, TC = 1558.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 Sol = [F = true, NV = 14, TC = 1567.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 Sol = [F = true, NV = 14, TC = 1560.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 Sol = [F = true, NV = 15, TC = 1616.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 Sol = [F = true, NV = 16, TC = 1687.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4862 Sol = [F = true, NV = 16, TC = 1671.81]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 16, TC = 1709.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4974 Sol = [F = true, NV = 16, TC = 1691.66]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 16, TC = 1663.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 16, TC = 1727.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 16, TC = 1756.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5252 Sol = [F = true, NV = 16, TC = 1729.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 16, TC = 1774.35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5491 Sol = [F = true, NV = 16, TC = 1757.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5505 Sol = [F = true, NV = 16, TC = 1741.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6021 Sol = [F = true, NV = 16, TC = 1740.06]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6058 Sol = [F = true, NV = 16, TC = 1737.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 16, TC = 1741.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6285 Sol = [F = true, NV = 16, TC = 1737.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 Sol = [F = true, NV = 16, TC = 1802.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6425 Sol = [F = true, NV = 16, TC = 1784.58]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6431 Sol = [F = true, NV = 16, TC = 1744.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 16, TC = 1747.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7183 Sol = [F = true, NV = 16, TC = 1746.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 Sol = [F = true, NV = 16, TC = 1752.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 Sol = [F = true, NV = 16, TC = 1749.87]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 75 87 59 97 58 77 74 105 0\n" +
                "0 14 12 47 17 16 13 9 11 15 10 0\n" +
                "0 95 84 50 96 0\n" +
                "0 2 6 42 39 40 37 35 36 0\n" +
                "0 83 86 52 64 24 25 0\n" +
                "0 21 23 19 18 48 49 20 56 0\n" +
                "0 72 82 65 57 99 93 0\n" +
                "0 98 78 88 53 0\n" +
                "0 92 85 63 89 22 66 0\n" +
                "0 61 81 106 90 0\n" +
                "0 71 67 62 51 76 91 102 80 0\n" +
                "0 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 69 73 79 60 70 68 0\n" +
                "0 94 54 0\n" +
                "0 41 38 43 44 0\n" +
                "Requests\n" +
                "18 16 17 15 19\n" +
                "41 45 51 40\n" +
                "8 7 25 9 6\n" +
                "50 26\n" +
                "1 24 22 20\n" +
                "44 28 34\n" +
                "12 14 11 10\n" +
                "38 43 35\n" +
                "52 46\n" +
                "48 13 33\n" +
                "31 42\n" +
                "37 32 27 47\n" +
                "4 2 5 3 0 29\n" +
                "36 39 30\n" +
                "49\n" +
                "23 21\n" +
                "Cost = 1749.872056122007\n" +
                "Num. vehicles = 16");
    }

    @Test
    public void dpdptw_lrc107_a_0_9_test() throws IOException {
        String problem = "lrc107_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 3, TC = 303.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 Sol = [F = true, NV = 4, TC = 368.42]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 5, TC = 488.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 6, TC = 557.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5167 Sol = [F = true, NV = 7, TC = 647.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 7, TC = 657.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 7, TC = 687.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 Sol = [F = true, NV = 8, TC = 762.67]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 9, TC = 824.38]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 9, TC = 861.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 9, TC = 870.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 Sol = [F = true, NV = 9, TC = 917.74]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 9, TC = 958.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 Sol = [F = true, NV = 9, TC = 1007.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 10, TC = 1054.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 11, TC = 1116.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 11, TC = 1122.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 11, TC = 1129.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 11, TC = 1146.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 11, TC = 1148.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 Sol = [F = true, NV = 12, TC = 1228.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 Sol = [F = true, NV = 12, TC = 1249.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 Sol = [F = true, NV = 12, TC = 1288.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 12, TC = 1369.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 Sol = [F = true, NV = 12, TC = 1409.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 Sol = [F = true, NV = 13, TC = 1524.39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7593 Sol = [F = true, NV = 13, TC = 1493.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 13, TC = 1565.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 14, TC = 1589.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 14, TC = 1595.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 15, TC = 1698.74]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 Sol = [F = true, NV = 15, TC = 1750.67]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 16, TC = 1801.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8676 Sol = [F = true, NV = 16, TC = 1797.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 Sol = [F = true, NV = 17, TC = 1918.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 Sol = [F = true, NV = 18, TC = 2006.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 Sol = [F = true, NV = 18, TC = 2008.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 Sol = [F = true, NV = 18, TC = 2014.13]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 18, TC = 2022.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 Sol = [F = true, NV = 18, TC = 2023.21]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 Sol = [F = true, NV = 18, TC = 2031.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 Sol = [F = true, NV = 18, TC = 2043.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 Sol = [F = true, NV = 18, TC = 2045.18]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 28 26 30 34 0\n" +
                "0 75 58 87 59 97 77 0\n" +
                "0 14 16 47 12 11 15 13 9 0\n" +
                "0 95 84 19 20 22 66 0\n" +
                "0 2 6 5 8 7 103 45 3 55 104 0\n" +
                "0 83 86 52 23 49 24 0\n" +
                "0 21 63 89 56 0\n" +
                "0 82 99 98 78 73 70 0\n" +
                "0 72 93 71 50 67 96 0\n" +
                "0 92 62 85 76 51 80 0\n" +
                "0 61 90 69 79 60 68 0\n" +
                "0 38 44 42 40 1 46 4 100 0\n" +
                "0 65 57 17 10 0\n" +
                "0 81 106 39 37 35 36 0\n" +
                "0 27 32 33 101 91 102 0\n" +
                "0 94 54 0\n" +
                "0 88 53 41 43 0\n" +
                "0 64 18 48 25 74 105 0\n" +
                "Requests\n" +
                "18 17 15\n" +
                "41 45 51\n" +
                "8 25 7 6\n" +
                "50 11 13\n" +
                "1 3 5 4 29\n" +
                "44 28 14\n" +
                "33 12\n" +
                "43 52 39\n" +
                "38 37 26\n" +
                "32 48 27\n" +
                "31 36 30\n" +
                "21 24 0 2\n" +
                "35 9\n" +
                "42 22 20\n" +
                "16 19 47\n" +
                "49\n" +
                "23 46\n" +
                "34 10 40\n" +
                "Cost = 2045.177717984539\n" +
                "Num. vehicles = 18");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_1_test() throws IOException {
        String problem = "lrc107_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 2, TC = 215.61]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 3, TC = 320.1]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 4, TC = 360.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 5, TC = 444.02]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 6, TC = 499.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 6, TC = 511.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5340 Sol = [F = true, NV = 6, TC = 508.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 6, TC = 569.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 7, TC = 638.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 8, TC = 729.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5671 Sol = [F = true, NV = 8, TC = 728.58]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 8, TC = 739.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 9, TC = 860.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 10, TC = 923.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 10, TC = 956.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6291 Sol = [F = true, NV = 10, TC = 955.02]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 Sol = [F = true, NV = 11, TC = 1021.28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6472 Sol = [F = true, NV = 11, TC = 1020.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 11, TC = 1065.95]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 11, TC = 1114.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 12, TC = 1210.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 14, TC = 1313.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 14, TC = 1321.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 15, TC = 1394.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 Sol = [F = true, NV = 15, TC = 1396.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 15, TC = 1451.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 15, TC = 1492.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 Sol = [F = true, NV = 15, TC = 1530.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8100 Sol = [F = true, NV = 15, TC = 1525.0]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 16, TC = 1692.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8254 Sol = [F = true, NV = 16, TC = 1687.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 16, TC = 1700.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8393 Sol = [F = true, NV = 16, TC = 1694.67]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 16, TC = 1774.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8462 Sol = [F = true, NV = 16, TC = 1768.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 17, TC = 1851.44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8695 Sol = [F = true, NV = 17, TC = 1845.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 17, TC = 1869.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9038 Sol = [F = true, NV = 17, TC = 1863.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 Sol = [F = true, NV = 17, TC = 1880.68]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 Sol = [F = true, NV = 17, TC = 1939.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 18, TC = 1995.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 19, TC = 2032.6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9694 Sol = [F = true, NV = 19, TC = 2026.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 19, TC = 2040.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 Sol = [F = true, NV = 20, TC = 2133.47]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 Sol = [F = true, NV = 20, TC = 2134.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 Sol = [F = true, NV = 20, TC = 2139.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 20, TC = 2158.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 Sol = [F = true, NV = 20, TC = 2159.86]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 20, TC = 2186.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 20, TC = 2188.19]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 12 13 87 59 74 105 0\n" +
                "0 65 57 23 49 22 66 0\n" +
                "0 31 29 28 26 30 34 0\n" +
                "0 7 103 2 6 88 53 60 68 0\n" +
                "0 14 16 47 9 11 15 17 10 0\n" +
                "0 95 84 19 18 48 20 0\n" +
                "0 83 86 75 97 58 77 0\n" +
                "0 21 63 89 56 0\n" +
                "0 82 99 52 24 0\n" +
                "0 72 93 92 85 91 102 0\n" +
                "0 98 78 73 70 0\n" +
                "0 62 76 51 80 0\n" +
                "0 61 90 50 96 0\n" +
                "0 71 67 38 44 0\n" +
                "0 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 42 40 27 32 33 101 0\n" +
                "0 69 79 81 106 0\n" +
                "0 94 54 0\n" +
                "0 41 39 37 43 35 36 0\n" +
                "0 64 25 0\n" +
                "Requests\n" +
                "7 45 40\n" +
                "35 14 13\n" +
                "18 17 15\n" +
                "4 1 46 30\n" +
                "8 25 6 9\n" +
                "50 11 10\n" +
                "44 41 51\n" +
                "33 12\n" +
                "28 43\n" +
                "38 48 47\n" +
                "52 39\n" +
                "32 27\n" +
                "31 26\n" +
                "37 21\n" +
                "5 3 0 2 29\n" +
                "24 16 19\n" +
                "36 42\n" +
                "49\n" +
                "23 22 20\n" +
                "34\n" +
                "Cost = 2188.193321250225\n" +
                "Num. vehicles = 20");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_5_test() throws IOException {
        String problem = "lrc107_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 8, TC = 802.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 84 Sol = [F = true, NV = 8, TC = 801.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 117 Sol = [F = true, NV = 8, TC = 798.26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 134 Sol = [F = true, NV = 8, TC = 796.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 8, TC = 805.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 9, TC = 921.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4927 Sol = [F = true, NV = 9, TC = 913.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 10, TC = 997.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 11, TC = 1069.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5263 Sol = [F = true, NV = 11, TC = 1060.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5266 Sol = [F = true, NV = 11, TC = 1053.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 12, TC = 1145.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5511 Sol = [F = true, NV = 12, TC = 1129.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 12, TC = 1157.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 12, TC = 1210.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 12, TC = 1221.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 13, TC = 1282.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 13, TC = 1315.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6216 Sol = [F = true, NV = 13, TC = 1313.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 13, TC = 1323.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6281 Sol = [F = true, NV = 13, TC = 1321.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 13, TC = 1342.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 13, TC = 1390.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 14, TC = 1468.7]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 15, TC = 1508.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 Sol = [F = true, NV = 15, TC = 1550.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 16, TC = 1596.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 16, TC = 1603.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 17, TC = 1765.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 17, TC = 1804.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 17, TC = 1829.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 17, TC = 1852.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 17, TC = 1908.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 17, TC = 1929.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 17, TC = 1951.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 17, TC = 1972.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 Sol = [F = true, NV = 17, TC = 1976.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 17, TC = 1976.44]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 1 45 46 4 100 0\n" +
                "0 64 51 19 21 25 20 56 80 0\n" +
                "0 33 101 31 29 26 28 30 34 0\n" +
                "0 39 38 37 44 35 36 0\n" +
                "0 65 57 0\n" +
                "0 82 99 52 83 86 24 22 66 0\n" +
                "0 71 67 94 50 96 54 0\n" +
                "0 69 98 11 15 73 78 79 70 0\n" +
                "0 75 87 59 97 58 77 74 105 0\n" +
                "0 14 16 47 12 13 9 17 10 0\n" +
                "0 95 84 63 89 0\n" +
                "0 2 6 55 104 88 53 0\n" +
                "0 72 93 92 85 91 102 0\n" +
                "0 62 76 18 48 23 49 0\n" +
                "0 61 90 81 106 41 43 0\n" +
                "0 7 103 8 3 60 68 0\n" +
                "0 42 40 27 32 0\n" +
                "Requests\n" +
                "3 0 2\n" +
                "34 27 11 12\n" +
                "19 18 15 17\n" +
                "22 21 20\n" +
                "35\n" +
                "43 28 44 13\n" +
                "37 49 26\n" +
                "36 6 52 39\n" +
                "41 45 51 40\n" +
                "8 25 7 9\n" +
                "50 33\n" +
                "1 29 46\n" +
                "38 48 47\n" +
                "32 10 14\n" +
                "31 42 23\n" +
                "5 4 30\n" +
                "24 16\n" +
                "Cost = 1976.4356278741984\n" +
                "Num. vehicles = 17");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_9_test() throws IOException {
        String problem = "lrc107_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 13, TC = 1337.81]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 807 Sol = [F = true, NV = 13, TC = 1322.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1243 Sol = [F = true, NV = 13, TC = 1302.03]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2100 Sol = [F = true, NV = 13, TC = 1298.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2151 Sol = [F = true, NV = 13, TC = 1295.69]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4426 Sol = [F = true, NV = 13, TC = 1293.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 13, TC = 1353.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5645 Sol = [F = true, NV = 13, TC = 1348.25]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 14, TC = 1433.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6098 Sol = [F = true, NV = 14, TC = 1414.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 15, TC = 1595.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 16, TC = 1672.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 15, TC = 1667.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 15, TC = 1709.09]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 7 103 8 5 3 1 45 46 4 100 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 0\n" +
                "0 65 52 74 105 57 24 22 66 0\n" +
                "0 33 101 31 29 26 27 30 32 0\n" +
                "0 41 39 38 35 36 37 44 43 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 62 85 63 51 76 89 91 102 80 0\n" +
                "0 82 99 2 6 55 104 0\n" +
                "0 95 84 28 34 0\n" +
                "0 98 11 15 73 78 60 70 68 0\n" +
                "0 83 86 97 77 0\n" +
                "0 61 81 106 90 88 53 0\n" +
                "0 87 59 75 58 0\n" +
                "0 42 40 69 79 0\n" +
                "Requests\n" +
                "4 3 5 0 2\n" +
                "7 8 25 9\n" +
                "34 11 14 10 12\n" +
                "35 28 40 13\n" +
                "19 18 15 16\n" +
                "23 22 20 21\n" +
                "38 37 49 26\n" +
                "48 32 33 27 47\n" +
                "43 1 29\n" +
                "50 17\n" +
                "52 6 39 30\n" +
                "44 51\n" +
                "31 42 46\n" +
                "45 41\n" +
                "24 36\n" +
                "Cost = 1709.0931975796304\n" +
                "Num. vehicles = 15");
    }
}
