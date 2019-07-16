package com.github.schmittjoaopedro.vrp.dpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DPDPTW_ALNS_TEST {

    private static final String dpdptw100Directory;

    static {
        dpdptw100Directory = Paths.get(DPDPTW_ALNS_TEST.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
    }

    @Test
    public void dpdptw_lc101_a_0_1_test() throws IOException {
        String problem = "lc101_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 Sol = [F = true, NV = 11, TC = 759.12]");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 Sol = [F = true, NV = 11, TC = 828.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 391 Sol = [F = true, NV = 10, TC = 793.28]");
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
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "Requests\n" +
                "16 17 18 20 19\n" +
                "30 31 28 29\n" +
                "1 5 0 3 4 2\n" +
                "9 8 6 7\n" +
                "51 50 52 49 48\n" +
                "37 35 33 32 34 36\n" +
                "42 39 38 40 41\n" +
                "47 45 46 43 44\n" +
                "22 21 23 24 27 26 25\n" +
                "13 12 10 11 14 15\n" +
                "Cost = 828.936866942834\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_a_0_5_test() throws IOException {
        String problem = "lc101_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 Sol = [F = true, NV = 10, TC = 654.46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 471 Sol = [F = true, NV = 10, TC = 608.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 Sol = [F = true, NV = 11, TC = 730.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 482 Sol = [F = true, NV = 10, TC = 630.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 Sol = [F = true, NV = 10, TC = 647.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 Sol = [F = true, NV = 10, TC = 658.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 Sol = [F = true, NV = 10, TC = 660.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 10, TC = 661.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 Sol = [F = true, NV = 10, TC = 667.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 Sol = [F = true, NV = 10, TC = 667.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 Sol = [F = true, NV = 10, TC = 691.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 Sol = [F = true, NV = 10, TC = 713.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 Sol = [F = true, NV = 10, TC = 716.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 Sol = [F = true, NV = 10, TC = 733.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 Sol = [F = true, NV = 10, TC = 739.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 Sol = [F = true, NV = 10, TC = 742.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 Sol = [F = true, NV = 10, TC = 750.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 Sol = [F = true, NV = 10, TC = 758.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 Sol = [F = true, NV = 10, TC = 773.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 Sol = [F = true, NV = 10, TC = 779.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 Sol = [F = true, NV = 10, TC = 781.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 Sol = [F = true, NV = 10, TC = 784.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 Sol = [F = true, NV = 10, TC = 790.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 Sol = [F = true, NV = 10, TC = 793.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 Sol = [F = true, NV = 10, TC = 794.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 Sol = [F = true, NV = 10, TC = 795.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 Sol = [F = true, NV = 10, TC = 801.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 Sol = [F = true, NV = 10, TC = 804.21]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 Sol = [F = true, NV = 10, TC = 813.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 Sol = [F = true, NV = 10, TC = 816.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 Sol = [F = true, NV = 10, TC = 821.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 Sol = [F = true, NV = 10, TC = 822.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 Sol = [F = true, NV = 10, TC = 824.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 Sol = [F = true, NV = 10, TC = 827.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "Requests\n" +
                "33 37 35 32 34 36\n" +
                "52 51 49 50 48\n" +
                "31 29 28 30\n" +
                "19 20 16 18 17\n" +
                "1 3 5 4 2 0\n" +
                "21 23 27 26 22 24 25\n" +
                "8 6 9 7\n" +
                "42 39 38 40 41\n" +
                "12 10 15 11 14 13\n" +
                "47 45 44 46 43\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_a_0_9_test() throws IOException {
        String problem = "lc101_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 Sol = [F = true, NV = 10, TC = 608.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 858 Sol = [F = true, NV = 10, TC = 621.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 866 Sol = [F = true, NV = 10, TC = 630.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 907 Sol = [F = true, NV = 10, TC = 647.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 10, TC = 658.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1481 Sol = [F = true, NV = 10, TC = 660.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1513 Sol = [F = true, NV = 10, TC = 667.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 Sol = [F = true, NV = 10, TC = 667.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 Sol = [F = true, NV = 10, TC = 691.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 Sol = [F = true, NV = 10, TC = 713.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2145 Sol = [F = true, NV = 10, TC = 716.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2169 Sol = [F = true, NV = 10, TC = 727.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2177 Sol = [F = true, NV = 10, TC = 733.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2193 Sol = [F = true, NV = 10, TC = 739.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2201 Sol = [F = true, NV = 10, TC = 742.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2225 Sol = [F = true, NV = 10, TC = 750.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 Sol = [F = true, NV = 10, TC = 758.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2800 Sol = [F = true, NV = 10, TC = 773.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 Sol = [F = true, NV = 10, TC = 779.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3496 Sol = [F = true, NV = 10, TC = 781.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 Sol = [F = true, NV = 10, TC = 784.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3528 Sol = [F = true, NV = 10, TC = 790.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3544 Sol = [F = true, NV = 10, TC = 793.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 Sol = [F = true, NV = 10, TC = 794.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4183 Sol = [F = true, NV = 10, TC = 795.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 Sol = [F = true, NV = 10, TC = 801.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4232 Sol = [F = true, NV = 10, TC = 804.21]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4887 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4952 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4968 Sol = [F = true, NV = 10, TC = 813.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 10, TC = 816.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5534 Sol = [F = true, NV = 10, TC = 821.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5559 Sol = [F = true, NV = 10, TC = 822.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6206 Sol = [F = true, NV = 10, TC = 824.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6246 Sol = [F = true, NV = 10, TC = 827.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7395 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "Requests\n" +
                "29 28 30 31\n" +
                "8 6 7 9\n" +
                "42 40 39 38 41\n" +
                "0 2 3 4 1 5\n" +
                "51 49 48 50 52\n" +
                "17 20 19 18 16\n" +
                "45 46 43 47 44\n" +
                "33 34 35 37 32 36\n" +
                "24 27 22 21 23 26 25\n" +
                "15 13 14 11 10 12\n" +
                "Cost = 828.9368669428341\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_q_0_0_1_test() throws IOException {
        String problem = "lc101_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 2, TC = 182.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 187 Sol = [F = true, NV = 2, TC = 216.06]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 Sol = [F = true, NV = 3, TC = 271.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 Sol = [F = true, NV = 4, TC = 301.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 413 Sol = [F = true, NV = 5, TC = 335.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 429 Sol = [F = true, NV = 6, TC = 373.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 Sol = [F = true, NV = 7, TC = 418.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 Sol = [F = true, NV = 8, TC = 453.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 9, TC = 523.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 Sol = [F = true, NV = 10, TC = 640.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 800 Sol = [F = true, NV = 10, TC = 640.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 10, TC = 644.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 Sol = [F = true, NV = 10, TC = 644.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 Sol = [F = true, NV = 10, TC = 653.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 Sol = [F = true, NV = 10, TC = 670.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 Sol = [F = true, NV = 10, TC = 681.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 Sol = [F = true, NV = 10, TC = 685.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 Sol = [F = true, NV = 10, TC = 686.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 Sol = [F = true, NV = 10, TC = 692.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 Sol = [F = true, NV = 10, TC = 692.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 Sol = [F = true, NV = 11, TC = 786.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1753 Sol = [F = true, NV = 10, TC = 716.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 Sol = [F = true, NV = 10, TC = 738.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1900 Sol = [F = true, NV = 10, TC = 738.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 Sol = [F = true, NV = 10, TC = 741.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 Sol = [F = true, NV = 10, TC = 745.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 Sol = [F = true, NV = 10, TC = 751.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 Sol = [F = true, NV = 10, TC = 754.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 10, TC = 762.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 Sol = [F = true, NV = 10, TC = 770.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 Sol = [F = true, NV = 10, TC = 779.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 Sol = [F = true, NV = 10, TC = 785.29]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 Sol = [F = true, NV = 10, TC = 785.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 Sol = [F = true, NV = 10, TC = 787.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 Sol = [F = true, NV = 10, TC = 790.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 Sol = [F = true, NV = 10, TC = 793.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 Sol = [F = true, NV = 10, TC = 794.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 Sol = [F = true, NV = 10, TC = 795.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 Sol = [F = true, NV = 10, TC = 801.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 Sol = [F = true, NV = 10, TC = 804.21]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 Sol = [F = true, NV = 10, TC = 804.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 10, TC = 810.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 Sol = [F = true, NV = 10, TC = 813.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 Sol = [F = true, NV = 10, TC = 816.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 Sol = [F = true, NV = 10, TC = 821.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 10, TC = 822.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 Sol = [F = true, NV = 10, TC = 824.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 Sol = [F = true, NV = 10, TC = 827.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "Requests\n" +
                "19 17 18 16 20\n" +
                "4 2 5 1 0 3\n" +
                "12 14 10 13 15 11\n" +
                "9 6 8 7\n" +
                "32 37 35 33 34 36\n" +
                "39 38 40 41 42\n" +
                "22 21 23 24 27 26 25\n" +
                "43 47 46 45 44\n" +
                "52 48 51 49 50\n" +
                "29 28 31 30\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_q_0_0_5_test() throws IOException {
        String problem = "lc101_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 9, TC = 1273.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 9, TC = 1200.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4 Sol = [F = true, NV = 9, TC = 1158.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8 Sol = [F = true, NV = 9, TC = 1151.25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9 Sol = [F = true, NV = 9, TC = 1120.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10 Sol = [F = true, NV = 9, TC = 1099.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11 Sol = [F = true, NV = 9, TC = 1070.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19 Sol = [F = true, NV = 9, TC = 873.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 22 Sol = [F = true, NV = 9, TC = 871.38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 23 Sol = [F = true, NV = 9, TC = 844.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 220 Sol = [F = true, NV = 9, TC = 777.06]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 Sol = [F = true, NV = 9, TC = 779.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 Sol = [F = true, NV = 9, TC = 825.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 Sol = [F = true, NV = 9, TC = 861.6]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 10, TC = 964.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 Sol = [F = true, NV = 10, TC = 766.89]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 Sol = [F = true, NV = 10, TC = 813.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 622 Sol = [F = true, NV = 10, TC = 769.2]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 Sol = [F = true, NV = 10, TC = 773.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 Sol = [F = true, NV = 10, TC = 773.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 Sol = [F = true, NV = 10, TC = 778.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 Sol = [F = true, NV = 10, TC = 782.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 Sol = [F = true, NV = 10, TC = 786.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 10, TC = 881.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 Sol = [F = true, NV = 10, TC = 791.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 Sol = [F = true, NV = 10, TC = 795.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 Sol = [F = true, NV = 10, TC = 802.81]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 Sol = [F = true, NV = 11, TC = 843.61]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 Sol = [F = true, NV = 10, TC = 806.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 Sol = [F = true, NV = 10, TC = 808.38]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 Sol = [F = true, NV = 10, TC = 811.3]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 Sol = [F = true, NV = 10, TC = 812.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 Sol = [F = true, NV = 10, TC = 818.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 10, TC = 818.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 Sol = [F = true, NV = 10, TC = 821.38]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 Sol = [F = true, NV = 10, TC = 824.61]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 10, TC = 826.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "Requests\n" +
                "39 40 42 38 41\n" +
                "14 15 10 11 13 12\n" +
                "23 21 27 22 24 25 26\n" +
                "7 9 8 6\n" +
                "29 28 30 31\n" +
                "51 49 50 48 52\n" +
                "47 45 46 43 44\n" +
                "20 17 16 18 19\n" +
                "1 4 2 0 5 3\n" +
                "33 37 35 34 32 36\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_q_0_0_9_test() throws IOException {
        String problem = "lc101_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 Sol = [F = true, NV = 10, TC = 828.94]");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "Requests\n" +
                "2 4 1 0 3 5\n" +
                "8 7 9 6\n" +
                "10 12 13 15 14 11\n" +
                "20 17 19 16 18\n" +
                "32 33 34 35 37 36\n" +
                "31 29 30 28\n" +
                "22 24 27 25 21 23 26\n" +
                "42 39 40 38 41\n" +
                "47 45 43 44 46\n" +
                "48 49 50 51 52\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lrc107_a_0_1_test() throws IOException {
        String problem = "lrc107_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 334 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2, 3, 4, 5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 Sol = [F = true, NV = 5, TC = 547.3]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 Sol = [F = true, NV = 9, TC = 893.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 Sol = [F = true, NV = 8, TC = 820.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 Sol = [F = true, NV = 8, TC = 814.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 606 Sol = [F = true, NV = 7, TC = 806.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 Sol = [F = true, NV = 10, TC = 964.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 700 Sol = [F = true, NV = 10, TC = 943.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 Sol = [F = true, NV = 11, TC = 1139.69]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 753 Sol = [F = true, NV = 11, TC = 1109.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 814 Sol = [F = true, NV = 10, TC = 1102.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 821 Sol = [F = true, NV = 10, TC = 1102.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 11, TC = 1214.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 836 Sol = [F = true, NV = 11, TC = 1213.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 841 Sol = [F = true, NV = 11, TC = 1155.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 Sol = [F = true, NV = 13, TC = 1341.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 975 Sol = [F = true, NV = 12, TC = 1293.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 Sol = [F = true, NV = 13, TC = 1359.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 Sol = [F = true, NV = 12, TC = 1460.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1169 Sol = [F = true, NV = 12, TC = 1444.65]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1180 Sol = [F = true, NV = 12, TC = 1397.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1198 Sol = [F = true, NV = 12, TC = 1394.29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1200 Sol = [F = true, NV = 12, TC = 1376.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1204 Sol = [F = true, NV = 11, TC = 1325.86]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 Sol = [F = true, NV = 11, TC = 1337.71]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1300 Sol = [F = true, NV = 11, TC = 1335.71]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1410 Sol = [F = true, NV = 11, TC = 1245.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 61 81 106 90 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "Requests\n" +
                "41 51 43 45\n" +
                "8 6 9 7 25\n" +
                "17 18 15 19 16\n" +
                "10 11 14 34 47 12\n" +
                "39 30 52 36 46\n" +
                "27 50 48 32 33\n" +
                "5 1 0 4 3 2 29\n" +
                "31 42\n" +
                "21 22 23 20 24\n" +
                "13 40 44 28 35\n" +
                "26 49 38 37\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_a_0_5_test() throws IOException {
        String problem = "lrc107_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2000 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 Sol = [F = true, NV = 2, TC = 240.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2584 Sol = [F = true, NV = 2, TC = 251.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2667 Sol = [F = true, NV = 2, TC = 260.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2750 Sol = [F = true, NV = 4, TC = 449.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2834 Sol = [F = true, NV = 5, TC = 547.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2838 Sol = [F = true, NV = 5, TC = 536.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2848 Sol = [F = true, NV = 4, TC = 530.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2917 Sol = [F = true, NV = 5, TC = 541.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13, 14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 Sol = [F = true, NV = 6, TC = 741.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3002 Sol = [F = true, NV = 6, TC = 725.06]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3003 Sol = [F = true, NV = 6, TC = 719.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3028 Sol = [F = true, NV = 6, TC = 717.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3069 Sol = [F = true, NV = 6, TC = 669.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3084 Sol = [F = true, NV = 7, TC = 728.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3100 Sol = [F = true, NV = 7, TC = 712.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 Sol = [F = true, NV = 7, TC = 776.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3200 Sol = [F = true, NV = 7, TC = 754.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3222 Sol = [F = true, NV = 7, TC = 738.03]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 Sol = [F = true, NV = 8, TC = 831.45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3298 Sol = [F = true, NV = 7, TC = 805.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3300 Sol = [F = true, NV = 7, TC = 799.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 Sol = [F = true, NV = 9, TC = 942.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3363 Sol = [F = true, NV = 9, TC = 919.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 Sol = [F = true, NV = 10, TC = 960.18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3500 Sol = [F = true, NV = 9, TC = 942.46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3600 Sol = [F = true, NV = 9, TC = 930.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 10, TC = 981.99]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 10, TC = 976.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3778 Sol = [F = true, NV = 9, TC = 957.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 Sol = [F = true, NV = 10, TC = 1055.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3851 Sol = [F = true, NV = 9, TC = 1002.86]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 Sol = [F = true, NV = 9, TC = 1086.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 Sol = [F = true, NV = 10, TC = 1154.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 Sol = [F = true, NV = 10, TC = 1055.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4010 Sol = [F = true, NV = 10, TC = 1042.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 11, TC = 1155.83]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4096 Sol = [F = true, NV = 11, TC = 1105.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4097 Sol = [F = true, NV = 10, TC = 1098.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4118 Sol = [F = true, NV = 10, TC = 1098.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 Sol = [F = true, NV = 11, TC = 1140.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 Sol = [F = true, NV = 11, TC = 1126.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 Sol = [F = true, NV = 12, TC = 1288.85]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4259 Sol = [F = true, NV = 12, TC = 1182.46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4270 Sol = [F = true, NV = 11, TC = 1171.71]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4322 Sol = [F = true, NV = 11, TC = 1158.52]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 Sol = [F = true, NV = 11, TC = 1281.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 Sol = [F = true, NV = 11, TC = 1198.92]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4425 Sol = [F = true, NV = 11, TC = 1134.33]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 Sol = [F = true, NV = 12, TC = 1203.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 Sol = [F = true, NV = 11, TC = 1155.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 11, TC = 1176.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4600 Sol = [F = true, NV = 11, TC = 1145.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 Sol = [F = true, NV = 11, TC = 1210.41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4674 Sol = [F = true, NV = 11, TC = 1201.68]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 Sol = [F = true, NV = 13, TC = 1378.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4769 Sol = [F = true, NV = 13, TC = 1317.53]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4770 Sol = [F = true, NV = 12, TC = 1313.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4800 Sol = [F = true, NV = 12, TC = 1303.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4830 Sol = [F = true, NV = 12, TC = 1245.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 Sol = [F = true, NV = 12, TC = 1257.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 13, TC = 1339.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 12, TC = 1284.41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5048 Sol = [F = true, NV = 12, TC = 1251.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 12, TC = 1292.29]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 12, TC = 1285.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 12, TC = 1416.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5335 Sol = [F = true, NV = 12, TC = 1315.25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5400 Sol = [F = true, NV = 11, TC = 1289.38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 11, TC = 1271.18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 Sol = [F = true, NV = 11, TC = 1271.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5799 Sol = [F = true, NV = 11, TC = 1214.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 11, TC = 1312.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6143 Sol = [F = true, NV = 11, TC = 1305.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6349 Sol = [F = true, NV = 11, TC = 1215.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 Sol = [F = true, NV = 11, TC = 1296.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 11, TC = 1227.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 Sol = [F = true, NV = 11, TC = 1279.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8259 Sol = [F = true, NV = 11, TC = 1236.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9100 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 61 81 106 90 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "Requests\n" +
                "12 10 14 34 11 47\n" +
                "42 31\n" +
                "25 6 8 9 7\n" +
                "46 39 30 52 36\n" +
                "18 15 17 16 19\n" +
                "37 49 26 38\n" +
                "50 32 48 27 33\n" +
                "4 0 29 1 3 5 2\n" +
                "45 43 41 51\n" +
                "44 28 13 35 40\n" +
                "22 23 20 24 21\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_a_0_9_test() throws IOException {
        String problem = "lrc107_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 1, TC = 104.49]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4417 Sol = [F = true, NV = 2, TC = 219.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 2, TC = 240.62]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 Sol = [F = true, NV = 2, TC = 260.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 4, TC = 423.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4936 Sol = [F = true, NV = 3, TC = 421.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 Sol = [F = true, NV = 4, TC = 483.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5018 Sol = [F = true, NV = 4, TC = 449.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5167 Sol = [F = true, NV = 5, TC = 547.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5198 Sol = [F = true, NV = 5, TC = 536.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 5, TC = 552.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5268 Sol = [F = true, NV = 5, TC = 541.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 5, TC = 555.79]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 Sol = [F = true, NV = 6, TC = 650.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5466 Sol = [F = true, NV = 6, TC = 641.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 7, TC = 703.63]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5529 Sol = [F = true, NV = 6, TC = 669.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 7, TC = 710.53]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5586 Sol = [F = true, NV = 6, TC = 670.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 6, TC = 689.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 Sol = [F = true, NV = 6, TC = 714.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 8, TC = 862.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5840 Sol = [F = true, NV = 7, TC = 805.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5900 Sol = [F = true, NV = 7, TC = 804.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5902 Sol = [F = true, NV = 7, TC = 798.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 Sol = [F = true, NV = 7, TC = 799.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 8, TC = 893.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6001 Sol = [F = true, NV = 8, TC = 881.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6014 Sol = [F = true, NV = 7, TC = 833.68]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 9, TC = 920.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6090 Sol = [F = true, NV = 9, TC = 919.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 10, TC = 932.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 9, TC = 942.09]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6400 Sol = [F = true, NV = 9, TC = 938.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 9, TC = 931.84]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 10, TC = 982.65]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6600 Sol = [F = true, NV = 10, TC = 943.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 9, TC = 1089.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 9, TC = 1059.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6922 Sol = [F = true, NV = 9, TC = 1055.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6924 Sol = [F = true, NV = 9, TC = 1050.79]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6951 Sol = [F = true, NV = 9, TC = 967.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 Sol = [F = true, NV = 10, TC = 1193.65]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7001 Sol = [F = true, NV = 10, TC = 991.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 Sol = [F = true, NV = 11, TC = 1110.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7094 Sol = [F = true, NV = 10, TC = 1034.79]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7107 Sol = [F = true, NV = 10, TC = 1002.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 Sol = [F = true, NV = 10, TC = 1191.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7251 Sol = [F = true, NV = 10, TC = 1081.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7263 Sol = [F = true, NV = 10, TC = 1045.53]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7300 Sol = [F = true, NV = 10, TC = 1031.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 10, TC = 1106.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7339 Sol = [F = true, NV = 10, TC = 1092.81]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 Sol = [F = true, NV = 10, TC = 1106.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 Sol = [F = true, NV = 11, TC = 1185.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7600 Sol = [F = true, NV = 11, TC = 1169.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7602 Sol = [F = true, NV = 11, TC = 1150.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7693 Sol = [F = true, NV = 11, TC = 1130.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 11, TC = 1153.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7800 Sol = [F = true, NV = 11, TC = 1134.33]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 11, TC = 1242.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8173 Sol = [F = true, NV = 11, TC = 1241.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8198 Sol = [F = true, NV = 11, TC = 1186.92]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8215 Sol = [F = true, NV = 11, TC = 1155.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 11, TC = 1190.05]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8336 Sol = [F = true, NV = 11, TC = 1184.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 12, TC = 1257.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8463 Sol = [F = true, NV = 11, TC = 1226.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8568 Sol = [F = true, NV = 11, TC = 1159.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 Sol = [F = true, NV = 11, TC = 1235.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8585 Sol = [F = true, NV = 11, TC = 1213.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 12, TC = 1240.28]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 Sol = [F = true, NV = 13, TC = 1423.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8758 Sol = [F = true, NV = 13, TC = 1352.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 Sol = [F = true, NV = 13, TC = 1355.6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8839 Sol = [F = true, NV = 12, TC = 1332.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8924 Sol = [F = true, NV = 12, TC = 1323.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8963 Sol = [F = true, NV = 11, TC = 1194.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 Sol = [F = true, NV = 12, TC = 1276.35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9200 Sol = [F = true, NV = 11, TC = 1271.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9230 Sol = [F = true, NV = 11, TC = 1208.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 Sol = [F = true, NV = 12, TC = 1321.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 12, TC = 1427.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9733 Sol = [F = true, NV = 12, TC = 1407.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9741 Sol = [F = true, NV = 12, TC = 1287.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10300 Sol = [F = true, NV = 11, TC = 1286.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10400 Sol = [F = true, NV = 11, TC = 1284.0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 Sol = [F = true, NV = 11, TC = 1281.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 Sol = [F = true, NV = 11, TC = 1275.77]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10905 Sol = [F = true, NV = 11, TC = 1275.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 Sol = [F = true, NV = 11, TC = 1217.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 Sol = [F = true, NV = 11, TC = 1310.29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11700 Sol = [F = true, NV = 11, TC = 1298.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 Sol = [F = true, NV = 11, TC = 1234.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11800 Sol = [F = true, NV = 11, TC = 1227.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 Sol = [F = true, NV = 11, TC = 1320.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15206 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16800 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 61 81 106 90 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "Requests\n" +
                "51 41 43 45\n" +
                "23 21 24 20 22\n" +
                "10 11 14 12 34 47\n" +
                "5 4 0 1 29 2 3\n" +
                "38 37 26 49\n" +
                "39 46 30 36 52\n" +
                "9 7 8 6 25\n" +
                "35 44 28 13 40\n" +
                "42 31\n" +
                "48 32 27 50 33\n" +
                "15 18 17 19 16\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_1_test() throws IOException {
        String problem = "lrc107_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 2, TC = 215.61]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17 Sol = [F = true, NV = 2, TC = 179.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 3, TC = 283.6]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 4, TC = 360.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 4, TC = 405.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5098 Sol = [F = true, NV = 3, TC = 387.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 3, TC = 439.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5255 Sol = [F = true, NV = 3, TC = 417.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5265 Sol = [F = true, NV = 3, TC = 398.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 3, TC = 447.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 3, TC = 407.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 5, TC = 492.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5505 Sol = [F = true, NV = 4, TC = 461.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 5, TC = 509.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5607 Sol = [F = true, NV = 4, TC = 481.95]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 5, TC = 579.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5681 Sol = [F = true, NV = 5, TC = 574.95]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 6, TC = 611.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5837 Sol = [F = true, NV = 5, TC = 583.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 6, TC = 663.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 7, TC = 722.83]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6111 Sol = [F = true, NV = 6, TC = 712.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 6, TC = 728.33]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 Sol = [F = true, NV = 6, TC = 755.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 8, TC = 862.15]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 8, TC = 821.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 7, TC = 871.97]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 9, TC = 970.82]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6862 Sol = [F = true, NV = 9, TC = 936.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 9, TC = 1037.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 9, TC = 1028.31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7068 Sol = [F = true, NV = 9, TC = 1010.18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7095 Sol = [F = true, NV = 9, TC = 965.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 10, TC = 978.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 Sol = [F = true, NV = 10, TC = 1068.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7672 Sol = [F = true, NV = 9, TC = 1018.58]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7700 Sol = [F = true, NV = 9, TC = 1014.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 9, TC = 1050.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 10, TC = 1006.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 Sol = [F = true, NV = 10, TC = 1041.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 11, TC = 1105.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8200 Sol = [F = true, NV = 11, TC = 1104.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 10, TC = 1123.79]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8338 Sol = [F = true, NV = 10, TC = 1119.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 11, TC = 1158.94]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 11, TC = 1229.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8685 Sol = [F = true, NV = 11, TC = 1192.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8700 Sol = [F = true, NV = 11, TC = 1191.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 11, TC = 1199.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9068 Sol = [F = true, NV = 11, TC = 1160.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9100 Sol = [F = true, NV = 11, TC = 1155.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 Sol = [F = true, NV = 11, TC = 1277.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9295 Sol = [F = true, NV = 11, TC = 1265.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9298 Sol = [F = true, NV = 11, TC = 1265.12]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 Sol = [F = true, NV = 12, TC = 1310.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9336 Sol = [F = true, NV = 12, TC = 1251.09]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9372 Sol = [F = true, NV = 11, TC = 1196.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9392 Sol = [F = true, NV = 11, TC = 1192.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9500 Sol = [F = true, NV = 11, TC = 1178.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 12, TC = 1206.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 12, TC = 1349.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9681 Sol = [F = true, NV = 12, TC = 1327.53]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9682 Sol = [F = true, NV = 11, TC = 1277.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 Sol = [F = true, NV = 11, TC = 1242.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 12, TC = 1301.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 Sol = [F = true, NV = 12, TC = 1263.18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9922 Sol = [F = true, NV = 11, TC = 1216.42]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 Sol = [F = true, NV = 12, TC = 1350.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10271 Sol = [F = true, NV = 12, TC = 1260.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10300 Sol = [F = true, NV = 11, TC = 1209.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10499 Sol = [F = true, NV = 11, TC = 1198.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 Sol = [F = true, NV = 11, TC = 1212.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 11, TC = 1291.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10800 Sol = [F = true, NV = 11, TC = 1288.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 Sol = [F = true, NV = 11, TC = 1284.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11579 Sol = [F = true, NV = 11, TC = 1283.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11586 Sol = [F = true, NV = 11, TC = 1220.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 Sol = [F = true, NV = 11, TC = 1221.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 11, TC = 1323.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 Sol = [F = true, NV = 11, TC = 1319.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13009 Sol = [F = true, NV = 11, TC = 1317.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13129 Sol = [F = true, NV = 11, TC = 1227.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 11, TC = 1309.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16800 Sol = [F = true, NV = 11, TC = 1302.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17682 Sol = [F = true, NV = 11, TC = 1246.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17800 Sol = [F = true, NV = 11, TC = 1240.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 18300 Sol = [F = true, NV = 11, TC = 1238.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 18812 Sol = [F = true, NV = 11, TC = 1232.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19200 Sol = [F = true, NV = 11, TC = 1230.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19800 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 61 81 106 90 0\n" +
                "Requests\n" +
                "29 1 0 3 4 5 2\n" +
                "51 45 41 43\n" +
                "15 18 17 19 16\n" +
                "26 37 38 49\n" +
                "48 50 33 32 27\n" +
                "12 14 34 10 11 47\n" +
                "44 28 13 40 35\n" +
                "30 46 52 39 36\n" +
                "23 21 20 24 22\n" +
                "6 7 9 8 25\n" +
                "42 31\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_5_test() throws IOException {
        String problem = "lrc107_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 8, TC = 802.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 7, TC = 760.57]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 100 Sol = [F = true, NV = 7, TC = 756.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 800 Sol = [F = true, NV = 7, TC = 756.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 827 Sol = [F = true, NV = 7, TC = 755.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 7, TC = 768.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 Sol = [F = true, NV = 7, TC = 764.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4717 Sol = [F = true, NV = 7, TC = 763.81]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 8, TC = 840.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4932 Sol = [F = true, NV = 7, TC = 813.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 8, TC = 874.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5114 Sol = [F = true, NV = 7, TC = 871.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5200 Sol = [F = true, NV = 7, TC = 870.61]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 7, TC = 887.45]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 9, TC = 1012.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5503 Sol = [F = true, NV = 8, TC = 967.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5509 Sol = [F = true, NV = 8, TC = 955.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 9, TC = 1009.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 9, TC = 1004.47]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 10, TC = 1057.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 10, TC = 1157.06]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5847 Sol = [F = true, NV = 9, TC = 1076.61]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5848 Sol = [F = true, NV = 9, TC = 1037.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5900 Sol = [F = true, NV = 9, TC = 1036.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 9, TC = 1094.09]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 Sol = [F = true, NV = 9, TC = 1091.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6115 Sol = [F = true, NV = 9, TC = 1051.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 9, TC = 1054.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6200 Sol = [F = true, NV = 9, TC = 1047.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 10, TC = 1073.66]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6253 Sol = [F = true, NV = 10, TC = 1072.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6300 Sol = [F = true, NV = 10, TC = 1067.46]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 10, TC = 1086.42]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 11, TC = 1140.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6604 Sol = [F = true, NV = 10, TC = 1090.64]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 11, TC = 1152.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6672 Sol = [F = true, NV = 10, TC = 1102.58]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 11, TC = 1156.42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 Sol = [F = true, NV = 11, TC = 1144.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6801 Sol = [F = true, NV = 11, TC = 1136.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 Sol = [F = true, NV = 11, TC = 1229.91]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6843 Sol = [F = true, NV = 11, TC = 1163.58]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7100 Sol = [F = true, NV = 11, TC = 1140.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 10, TC = 1234.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7358 Sol = [F = true, NV = 10, TC = 1226.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7595 Sol = [F = true, NV = 10, TC = 1225.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7672 Sol = [F = true, NV = 10, TC = 1224.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7680 Sol = [F = true, NV = 10, TC = 1223.25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7827 Sol = [F = true, NV = 10, TC = 1222.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 11, TC = 1145.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 11, TC = 1236.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8363 Sol = [F = true, NV = 11, TC = 1161.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 11, TC = 1201.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8500 Sol = [F = true, NV = 11, TC = 1190.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8515 Sol = [F = true, NV = 11, TC = 1188.85]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8800 Sol = [F = true, NV = 11, TC = 1184.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1273.82]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1225.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9400 Sol = [F = true, NV = 11, TC = 1195.99]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 12, TC = 1327.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 Sol = [F = true, NV = 12, TC = 1244.13]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 13, TC = 1381.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9742 Sol = [F = true, NV = 12, TC = 1353.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 12, TC = 1383.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9860 Sol = [F = true, NV = 12, TC = 1369.19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9898 Sol = [F = true, NV = 12, TC = 1322.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 Sol = [F = true, NV = 12, TC = 1291.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10505 Sol = [F = true, NV = 12, TC = 1246.58]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 12, TC = 1363.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 Sol = [F = true, NV = 12, TC = 1361.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 Sol = [F = true, NV = 12, TC = 1344.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11300 Sol = [F = true, NV = 12, TC = 1308.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11796 Sol = [F = true, NV = 11, TC = 1217.21]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 12, TC = 1334.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 Sol = [F = true, NV = 12, TC = 1461.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13067 Sol = [F = true, NV = 12, TC = 1397.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13084 Sol = [F = true, NV = 12, TC = 1368.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13089 Sol = [F = true, NV = 12, TC = 1365.57]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13294 Sol = [F = true, NV = 12, TC = 1340.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14000 Sol = [F = true, NV = 12, TC = 1324.29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14867 Sol = [F = true, NV = 11, TC = 1261.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14900 Sol = [F = true, NV = 11, TC = 1244.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14961 Sol = [F = true, NV = 11, TC = 1239.64]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 11, TC = 1260.77]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16602 Sol = [F = true, NV = 11, TC = 1252.42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17300 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 61 81 106 90 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "Requests\n" +
                "23 24 21 20 22\n" +
                "29 0 1 5 4 2 3\n" +
                "32 50 33 27 48\n" +
                "10 11 34 12 47 14\n" +
                "42 31\n" +
                "49 26 37 38\n" +
                "51 45 41 43\n" +
                "13 28 35 44 40\n" +
                "17 19 15 18 16\n" +
                "52 39 36 46 30\n" +
                "8 7 6 25 9\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_9_test() throws IOException {
        String problem = "lrc107_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 13, TC = 1337.81]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 100 Sol = [F = true, NV = 12, TC = 1287.26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 110 Sol = [F = true, NV = 12, TC = 1265.45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 200 Sol = [F = true, NV = 12, TC = 1260.0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 356 Sol = [F = true, NV = 12, TC = 1219.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 362 Sol = [F = true, NV = 12, TC = 1216.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 366 Sol = [F = true, NV = 11, TC = 1185.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 11, TC = 1205.82]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5889 Sol = [F = true, NV = 11, TC = 1187.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 12, TC = 1333.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6261 Sol = [F = true, NV = 11, TC = 1255.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6359 Sol = [F = true, NV = 11, TC = 1213.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6496 Sol = [F = true, NV = 11, TC = 1206.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7700 Sol = [F = true, NV = 11, TC = 1203.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 12, TC = 1374.19]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 13, TC = 1486.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8670 Sol = [F = true, NV = 13, TC = 1463.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8685 Sol = [F = true, NV = 13, TC = 1321.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8749 Sol = [F = true, NV = 12, TC = 1289.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1276.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 12, TC = 1383.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9753 Sol = [F = true, NV = 12, TC = 1378.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9970 Sol = [F = true, NV = 12, TC = 1311.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11900 Sol = [F = true, NV = 11, TC = 1309.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12500 Sol = [F = true, NV = 11, TC = 1291.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12797 Sol = [F = true, NV = 11, TC = 1230.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12811 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 61 81 106 90 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "Requests\n" +
                "39 52 30 36 46\n" +
                "34 12 10 14 11 47\n" +
                "23 24 20 22 21\n" +
                "15 17 18 16 19\n" +
                "6 8 7 25 9\n" +
                "41 51 45 43\n" +
                "0 4 1 3 29 5 2\n" +
                "28 35 44 13 40\n" +
                "37 49 38 26\n" +
                "42 31\n" +
                "48 32 33 50 27\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

}
