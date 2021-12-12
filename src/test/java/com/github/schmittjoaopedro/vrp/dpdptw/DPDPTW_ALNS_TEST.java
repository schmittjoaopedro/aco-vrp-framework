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

public class DPDPTW_ALNS_TEST {

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
        alns.setShowLog(true);
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
        alns.setShowLog(true);
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
        alns.setShowLog(true);
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 604 Sol = [F = true, NV = 7, TC = 806.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 Sol = [F = true, NV = 10, TC = 976.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 700 Sol = [F = true, NV = 10, TC = 960.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 724 Sol = [F = true, NV = 9, TC = 946.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 Sol = [F = true, NV = 11, TC = 1264.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 765 Sol = [F = true, NV = 11, TC = 1191.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 786 Sol = [F = true, NV = 11, TC = 1129.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 791 Sol = [F = true, NV = 10, TC = 1113.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 801 Sol = [F = true, NV = 10, TC = 1104.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 12, TC = 1244.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 Sol = [F = true, NV = 12, TC = 1207.85]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 840 Sol = [F = true, NV = 11, TC = 1134.25]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 Sol = [F = true, NV = 14, TC = 1374.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 922 Sol = [F = true, NV = 13, TC = 1338.42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 951 Sol = [F = true, NV = 12, TC = 1329.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 Sol = [F = true, NV = 12, TC = 1425.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1067 Sol = [F = true, NV = 12, TC = 1405.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1119 Sol = [F = true, NV = 12, TC = 1326.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 Sol = [F = true, NV = 12, TC = 1481.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1170 Sol = [F = true, NV = 12, TC = 1454.25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1176 Sol = [F = true, NV = 11, TC = 1364.67]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 Sol = [F = true, NV = 12, TC = 1366.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1253 Sol = [F = true, NV = 12, TC = 1359.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1273 Sol = [F = true, NV = 12, TC = 1264.19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1394 Sol = [F = true, NV = 11, TC = 1245.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1438 Sol = [F = true, NV = 11, TC = 1235.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 Sol = [F = true, NV = 11, TC = 1291.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1700 Sol = [F = true, NV = 11, TC = 1248.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2200 Sol = [F = true, NV = 11, TC = 1232.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3148 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 61 81 106 90 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "Requests\n" +
                "10 34 12 14 11 47\n" +
                "4 0 29 5 2 1 3\n" +
                "28 35 13 40 44\n" +
                "51 45 41 43\n" +
                "52 36 46 39 30\n" +
                "42 31\n" +
                "17 15 18 16 19\n" +
                "6 8 9 25 7\n" +
                "27 33 32 48 50\n" +
                "38 26 49 37\n" +
                "23 21 24 20 22\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_a_0_5_test() throws IOException {
        String problem = "lrc107_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.setShowLog(true);
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 Sol = [F = true, NV = 6, TC = 746.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 Sol = [F = true, NV = 8, TC = 814.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3261 Sol = [F = true, NV = 7, TC = 799.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 Sol = [F = true, NV = 9, TC = 920.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3338 Sol = [F = true, NV = 9, TC = 917.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 Sol = [F = true, NV = 10, TC = 998.38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3422 Sol = [F = true, NV = 10, TC = 989.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3454 Sol = [F = true, NV = 9, TC = 942.78]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 Sol = [F = true, NV = 10, TC = 987.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3671 Sol = [F = true, NV = 10, TC = 973.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3675 Sol = [F = true, NV = 10, TC = 943.76]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 Sol = [F = true, NV = 11, TC = 1058.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3843 Sol = [F = true, NV = 10, TC = 1028.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3900 Sol = [F = true, NV = 10, TC = 1006.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 Sol = [F = true, NV = 10, TC = 1097.22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3922 Sol = [F = true, NV = 10, TC = 1002.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3947 Sol = [F = true, NV = 9, TC = 991.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 Sol = [F = true, NV = 10, TC = 1032.03]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 Sol = [F = true, NV = 10, TC = 1139.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4091 Sol = [F = true, NV = 10, TC = 1117.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4100 Sol = [F = true, NV = 10, TC = 1113.69]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 Sol = [F = true, NV = 10, TC = 1109.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 Sol = [F = true, NV = 11, TC = 1128.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 Sol = [F = true, NV = 11, TC = 1160.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4325 Sol = [F = true, NV = 11, TC = 1151.08]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4326 Sol = [F = true, NV = 11, TC = 1149.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 Sol = [F = true, NV = 11, TC = 1153.39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4347 Sol = [F = true, NV = 11, TC = 1139.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 Sol = [F = true, NV = 11, TC = 1159.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 Sol = [F = true, NV = 11, TC = 1172.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 Sol = [F = true, NV = 11, TC = 1244.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4700 Sol = [F = true, NV = 11, TC = 1218.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4729 Sol = [F = true, NV = 11, TC = 1217.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 Sol = [F = true, NV = 12, TC = 1266.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4753 Sol = [F = true, NV = 12, TC = 1265.52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4785 Sol = [F = true, NV = 11, TC = 1240.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 Sol = [F = true, NV = 12, TC = 1333.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4851 Sol = [F = true, NV = 12, TC = 1259.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4861 Sol = [F = true, NV = 11, TC = 1188.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 Sol = [F = true, NV = 11, TC = 1295.44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4919 Sol = [F = true, NV = 11, TC = 1282.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4941 Sol = [F = true, NV = 11, TC = 1218.14]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 12, TC = 1275.69]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 13, TC = 1315.57]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 12, TC = 1290.36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5755 Sol = [F = true, NV = 11, TC = 1214.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 11, TC = 1330.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 Sol = [F = true, NV = 11, TC = 1316.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6200 Sol = [F = true, NV = 11, TC = 1316.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 Sol = [F = true, NV = 11, TC = 1329.92]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6420 Sol = [F = true, NV = 11, TC = 1318.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6460 Sol = [F = true, NV = 11, TC = 1296.1]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 11, TC = 1302.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6577 Sol = [F = true, NV = 11, TC = 1233.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6578 Sol = [F = true, NV = 11, TC = 1227.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 Sol = [F = true, NV = 11, TC = 1332.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8268 Sol = [F = true, NV = 11, TC = 1318.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8508 Sol = [F = true, NV = 11, TC = 1236.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9145 Sol = [F = true, NV = 11, TC = 1232.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9200 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 61 81 106 90 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "Requests\n" +
                "1 3 0 5 4 2 29\n" +
                "8 6 9 7 25\n" +
                "35 28 13 40 44\n" +
                "48 32 50 27 33\n" +
                "36 30 46 39 52\n" +
                "31 42\n" +
                "34 12 10 47 14 11\n" +
                "17 19 15 18 16\n" +
                "24 23 22 20 21\n" +
                "37 49 38 26\n" +
                "43 51 41 45\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_a_0_9_test() throws IOException {
        String problem = "lrc107_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.setShowLog(true);
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5243 Sol = [F = true, NV = 4, TC = 530.22]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 5, TC = 541.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 5, TC = 555.79]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 Sol = [F = true, NV = 6, TC = 641.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 7, TC = 712.92]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5501 Sol = [F = true, NV = 6, TC = 675.68]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 6, TC = 670.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 7, TC = 712.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 Sol = [F = true, NV = 6, TC = 746.32]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 8, TC = 831.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 Sol = [F = true, NV = 8, TC = 814.64]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5969 Sol = [F = true, NV = 7, TC = 799.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 8, TC = 878.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6003 Sol = [F = true, NV = 8, TC = 856.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6009 Sol = [F = true, NV = 7, TC = 850.63]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6033 Sol = [F = true, NV = 7, TC = 830.28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6052 Sol = [F = true, NV = 7, TC = 815.42]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 9, TC = 936.06]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6103 Sol = [F = true, NV = 9, TC = 931.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6133 Sol = [F = true, NV = 9, TC = 929.29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6156 Sol = [F = true, NV = 9, TC = 926.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 10, TC = 963.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6200 Sol = [F = true, NV = 10, TC = 957.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6238 Sol = [F = true, NV = 9, TC = 935.81]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 9, TC = 964.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6300 Sol = [F = true, NV = 9, TC = 964.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6322 Sol = [F = true, NV = 9, TC = 964.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6400 Sol = [F = true, NV = 9, TC = 952.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 9, TC = 940.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 10, TC = 973.88]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 9, TC = 973.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6682 Sol = [F = true, NV = 9, TC = 952.42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 Sol = [F = true, NV = 9, TC = 950.17]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 10, TC = 1017.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6918 Sol = [F = true, NV = 9, TC = 967.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 Sol = [F = true, NV = 9, TC = 1049.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7083 Sol = [F = true, NV = 9, TC = 993.86]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 Sol = [F = true, NV = 9, TC = 1026.95]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7100 Sol = [F = true, NV = 9, TC = 1017.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7167 Sol = [F = true, NV = 9, TC = 991.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 Sol = [F = true, NV = 10, TC = 1121.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7261 Sol = [F = true, NV = 10, TC = 1044.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7300 Sol = [F = true, NV = 10, TC = 1031.05]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 11, TC = 1170.97]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7338 Sol = [F = true, NV = 11, TC = 1110.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7370 Sol = [F = true, NV = 10, TC = 1106.31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7424 Sol = [F = true, NV = 10, TC = 1100.35]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 Sol = [F = true, NV = 10, TC = 1113.83]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 Sol = [F = true, NV = 10, TC = 1109.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 Sol = [F = true, NV = 11, TC = 1182.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7594 Sol = [F = true, NV = 11, TC = 1130.5]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 11, TC = 1235.39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7759 Sol = [F = true, NV = 11, TC = 1191.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7767 Sol = [F = true, NV = 11, TC = 1153.93]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 12, TC = 1196.03]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 11, TC = 1188.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8187 Sol = [F = true, NV = 11, TC = 1187.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 11, TC = 1278.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8338 Sol = [F = true, NV = 11, TC = 1261.83]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8340 Sol = [F = true, NV = 11, TC = 1146.96]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 11, TC = 1285.66]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8420 Sol = [F = true, NV = 11, TC = 1158.77]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 Sol = [F = true, NV = 11, TC = 1334.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8587 Sol = [F = true, NV = 11, TC = 1235.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 12, TC = 1266.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8668 Sol = [F = true, NV = 11, TC = 1218.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8676 Sol = [F = true, NV = 11, TC = 1201.19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8708 Sol = [F = true, NV = 11, TC = 1196.92]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 Sol = [F = true, NV = 11, TC = 1189.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 Sol = [F = true, NV = 11, TC = 1249.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8977 Sol = [F = true, NV = 11, TC = 1194.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 Sol = [F = true, NV = 12, TC = 1237.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9300 Sol = [F = true, NV = 12, TC = 1219.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9319 Sol = [F = true, NV = 11, TC = 1210.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9400 Sol = [F = true, NV = 11, TC = 1205.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 Sol = [F = true, NV = 12, TC = 1342.42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9500 Sol = [F = true, NV = 12, TC = 1298.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9507 Sol = [F = true, NV = 12, TC = 1292.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9520 Sol = [F = true, NV = 12, TC = 1281.55]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9521 Sol = [F = true, NV = 11, TC = 1231.2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9525 Sol = [F = true, NV = 11, TC = 1218.43]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 12, TC = 1254.0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9669 Sol = [F = true, NV = 11, TC = 1234.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 Sol = [F = true, NV = 11, TC = 1232.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10200 Sol = [F = true, NV = 11, TC = 1227.84]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 Sol = [F = true, NV = 11, TC = 1253.91]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11006 Sol = [F = true, NV = 11, TC = 1234.88]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11200 Sol = [F = true, NV = 11, TC = 1215.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 Sol = [F = true, NV = 11, TC = 1271.99]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11678 Sol = [F = true, NV = 11, TC = 1220.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 Sol = [F = true, NV = 11, TC = 1290.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12039 Sol = [F = true, NV = 11, TC = 1228.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12600 Sol = [F = true, NV = 11, TC = 1227.08]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 Sol = [F = true, NV = 11, TC = 1315.84]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14946 Sol = [F = true, NV = 11, TC = 1309.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15138 Sol = [F = true, NV = 11, TC = 1248.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15174 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 61 81 106 90 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "Requests\n" +
                "37 49 26 38\n" +
                "51 41 43 45\n" +
                "31 42\n" +
                "7 6 9 8 25\n" +
                "15 16 18 17 19\n" +
                "0 4 2 5 1 29 3\n" +
                "23 24 21 20 22\n" +
                "35 44 13 40 28\n" +
                "10 14 11 34 12 47\n" +
                "48 27 32 50 33\n" +
                "39 52 36 46 30\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_1_test() throws IOException {
        String problem = "lrc107_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.setShowLog(true);
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 4, TC = 380.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 3, TC = 439.24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5252 Sol = [F = true, NV = 3, TC = 438.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5253 Sol = [F = true, NV = 3, TC = 398.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 3, TC = 447.62]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 Sol = [F = true, NV = 3, TC = 407.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 5, TC = 492.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5508 Sol = [F = true, NV = 4, TC = 461.04]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 5, TC = 509.9]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 6, TC = 607.29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5679 Sol = [F = true, NV = 5, TC = 568.87]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 5, TC = 583.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 6, TC = 663.44]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 7, TC = 761.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 Sol = [F = true, NV = 7, TC = 736.43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6101 Sol = [F = true, NV = 7, TC = 717.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6107 Sol = [F = true, NV = 6, TC = 712.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 7, TC = 719.02]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 Sol = [F = true, NV = 6, TC = 755.73]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 8, TC = 902.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 8, TC = 872.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6519 Sol = [F = true, NV = 7, TC = 851.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 8, TC = 877.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6590 Sol = [F = true, NV = 8, TC = 821.82]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 9, TC = 880.75]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6675 Sol = [F = true, NV = 9, TC = 878.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6684 Sol = [F = true, NV = 8, TC = 836.72]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 9, TC = 937.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6761 Sol = [F = true, NV = 9, TC = 921.31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 Sol = [F = true, NV = 9, TC = 920.26]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 Sol = [F = true, NV = 9, TC = 1011.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6985 Sol = [F = true, NV = 9, TC = 927.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7100 Sol = [F = true, NV = 9, TC = 927.23]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 10, TC = 945.8]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 Sol = [F = true, NV = 10, TC = 1071.17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7668 Sol = [F = true, NV = 10, TC = 968.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7700 Sol = [F = true, NV = 10, TC = 968.68]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 Sol = [F = true, NV = 10, TC = 993.27]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 10, TC = 1006.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7900 Sol = [F = true, NV = 10, TC = 1006.01]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 Sol = [F = true, NV = 10, TC = 1132.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8090 Sol = [F = true, NV = 10, TC = 1110.28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8095 Sol = [F = true, NV = 10, TC = 1052.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8125 Sol = [F = true, NV = 10, TC = 1041.09]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 10, TC = 1103.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 Sol = [F = true, NV = 11, TC = 1122.15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8344 Sol = [F = true, NV = 10, TC = 1119.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8400 Sol = [F = true, NV = 10, TC = 1106.53]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 11, TC = 1136.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 11, TC = 1261.84]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8669 Sol = [F = true, NV = 11, TC = 1152.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1280.35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1225.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9005 Sol = [F = true, NV = 11, TC = 1163.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9016 Sol = [F = true, NV = 11, TC = 1153.91]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 Sol = [F = true, NV = 11, TC = 1274.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9300 Sol = [F = true, NV = 11, TC = 1267.11]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 Sol = [F = true, NV = 12, TC = 1307.13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9338 Sol = [F = true, NV = 11, TC = 1220.92]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9400 Sol = [F = true, NV = 11, TC = 1173.34]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 12, TC = 1230.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9586 Sol = [F = true, NV = 12, TC = 1225.4]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 12, TC = 1259.51]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 12, TC = 1324.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9776 Sol = [F = true, NV = 12, TC = 1258.59]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 Sol = [F = true, NV = 13, TC = 1409.66]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9837 Sol = [F = true, NV = 12, TC = 1378.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9913 Sol = [F = true, NV = 12, TC = 1346.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9916 Sol = [F = true, NV = 12, TC = 1339.98]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10000 Sol = [F = true, NV = 12, TC = 1294.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10017 Sol = [F = true, NV = 11, TC = 1276.21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10047 Sol = [F = true, NV = 11, TC = 1201.0]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 Sol = [F = true, NV = 12, TC = 1354.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10251 Sol = [F = true, NV = 12, TC = 1290.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10300 Sol = [F = true, NV = 12, TC = 1265.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 Sol = [F = true, NV = 12, TC = 1502.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10516 Sol = [F = true, NV = 12, TC = 1399.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10629 Sol = [F = true, NV = 12, TC = 1371.33]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 13, TC = 1423.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10755 Sol = [F = true, NV = 12, TC = 1423.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10791 Sol = [F = true, NV = 12, TC = 1400.82]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 Sol = [F = true, NV = 12, TC = 1398.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10942 Sol = [F = true, NV = 12, TC = 1288.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11300 Sol = [F = true, NV = 12, TC = 1285.86]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 Sol = [F = true, NV = 12, TC = 1292.18]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 12, TC = 1390.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12926 Sol = [F = true, NV = 12, TC = 1374.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13550 Sol = [F = true, NV = 12, TC = 1305.79]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13631 Sol = [F = true, NV = 11, TC = 1251.86]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14200 Sol = [F = true, NV = 11, TC = 1245.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16509 Sol = [F = true, NV = 11, TC = 1241.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 11, TC = 1242.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16700 Sol = [F = true, NV = 11, TC = 1232.54]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17000 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 61 81 106 90 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "Requests\n" +
                "30 46 52 39 36\n" +
                "38 49 26 37\n" +
                "32 48 50 33 27\n" +
                "42 31\n" +
                "24 22 23 20 21\n" +
                "17 15 19 18 16\n" +
                "1 0 4 5 2 29 3\n" +
                "44 28 13 40 35\n" +
                "47 12 10 14 34 11\n" +
                "51 41 43 45\n" +
                "6 8 7 9 25\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_5_test() throws IOException {
        String problem = "lrc107_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.setShowLog(true);
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4934 Sol = [F = true, NV = 7, TC = 816.85]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 Sol = [F = true, NV = 8, TC = 855.36]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 Sol = [F = true, NV = 8, TC = 866.3]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 9, TC = 1012.5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 Sol = [F = true, NV = 9, TC = 983.3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5509 Sol = [F = true, NV = 8, TC = 967.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5553 Sol = [F = true, NV = 8, TC = 955.66]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 Sol = [F = true, NV = 9, TC = 994.6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5641 Sol = [F = true, NV = 8, TC = 978.07]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 Sol = [F = true, NV = 9, TC = 1046.83]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 Sol = [F = true, NV = 9, TC = 1044.24]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 Sol = [F = true, NV = 9, TC = 1039.76]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5857 Sol = [F = true, NV = 9, TC = 1036.75]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 Sol = [F = true, NV = 9, TC = 1053.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6101 Sol = [F = true, NV = 9, TC = 1051.31]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 Sol = [F = true, NV = 9, TC = 1054.13]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 Sol = [F = true, NV = 10, TC = 1160.87]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6300 Sol = [F = true, NV = 10, TC = 1107.6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6400 Sol = [F = true, NV = 10, TC = 1101.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6415 Sol = [F = true, NV = 10, TC = 1084.98]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 11, TC = 1162.26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6504 Sol = [F = true, NV = 11, TC = 1128.49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6544 Sol = [F = true, NV = 10, TC = 1081.0]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 Sol = [F = true, NV = 11, TC = 1177.19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6591 Sol = [F = true, NV = 10, TC = 1103.7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6644 Sol = [F = true, NV = 10, TC = 1089.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 Sol = [F = true, NV = 11, TC = 1187.69]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6668 Sol = [F = true, NV = 10, TC = 1136.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6700 Sol = [F = true, NV = 10, TC = 1097.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 Sol = [F = true, NV = 11, TC = 1164.1]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 Sol = [F = true, NV = 11, TC = 1238.47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6839 Sol = [F = true, NV = 11, TC = 1238.22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6848 Sol = [F = true, NV = 11, TC = 1230.05]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6878 Sol = [F = true, NV = 11, TC = 1144.27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7200 Sol = [F = true, NV = 11, TC = 1140.65]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 Sol = [F = true, NV = 11, TC = 1230.33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7446 Sol = [F = true, NV = 10, TC = 1227.05]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7687 Sol = [F = true, NV = 10, TC = 1226.06]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7774 Sol = [F = true, NV = 10, TC = 1224.9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7778 Sol = [F = true, NV = 10, TC = 1223.32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7781 Sol = [F = true, NV = 10, TC = 1220.48]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 Sol = [F = true, NV = 11, TC = 1149.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7900 Sol = [F = true, NV = 11, TC = 1141.06]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 11, TC = 1309.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8200 Sol = [F = true, NV = 11, TC = 1271.31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8223 Sol = [F = true, NV = 11, TC = 1251.74]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8300 Sol = [F = true, NV = 11, TC = 1240.6]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 Sol = [F = true, NV = 12, TC = 1269.41]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 Sol = [F = true, NV = 12, TC = 1273.6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9012 Sol = [F = true, NV = 11, TC = 1254.68]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9162 Sol = [F = true, NV = 11, TC = 1195.12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9300 Sol = [F = true, NV = 11, TC = 1190.83]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 Sol = [F = true, NV = 11, TC = 1301.71]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 Sol = [F = true, NV = 11, TC = 1311.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 Sol = [F = true, NV = 11, TC = 1309.88]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 12, TC = 1364.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9800 Sol = [F = true, NV = 12, TC = 1306.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9801 Sol = [F = true, NV = 12, TC = 1301.63]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 Sol = [F = true, NV = 12, TC = 1294.66]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9926 Sol = [F = true, NV = 11, TC = 1291.67]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9948 Sol = [F = true, NV = 11, TC = 1284.48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9984 Sol = [F = true, NV = 11, TC = 1274.89]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 Sol = [F = true, NV = 12, TC = 1259.34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 Sol = [F = true, NV = 12, TC = 1257.28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11394 Sol = [F = true, NV = 11, TC = 1236.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11700 Sol = [F = true, NV = 11, TC = 1230.77]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12442 Sol = [F = true, NV = 11, TC = 1225.56]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 Sol = [F = true, NV = 11, TC = 1260.63]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 Sol = [F = true, NV = 12, TC = 1381.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13016 Sol = [F = true, NV = 12, TC = 1320.14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13600 Sol = [F = true, NV = 12, TC = 1306.4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13629 Sol = [F = true, NV = 11, TC = 1235.7]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 Sol = [F = true, NV = 11, TC = 1257.16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17070 Sol = [F = true, NV = 11, TC = 1245.69]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 18718 Sol = [F = true, NV = 11, TC = 1232.54]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 31 29 28 27 26 34 30 32 33 101 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 61 81 106 90 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "Requests\n" +
                "45 51 41 43\n" +
                "23 21 20 24 22\n" +
                "28 35 40 44 13\n" +
                "49 26 37 38\n" +
                "33 48 32 50 27\n" +
                "16 15 18 17 19\n" +
                "10 34 11 14 12 47\n" +
                "42 31\n" +
                "39 30 36 46 52\n" +
                "6 25 7 9 8\n" +
                "0 4 5 1 2 3 29\n" +
                "Cost = 1232.541301266746\n" +
                "Num. vehicles = 11");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_9_test() throws IOException {
        String problem = "lrc107_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 13, TC = 1337.81]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 34 Sol = [F = true, NV = 12, TC = 1333.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 38 Sol = [F = true, NV = 12, TC = 1303.94]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 Sol = [F = true, NV = 12, TC = 1280.11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 Sol = [F = true, NV = 11, TC = 1237.63]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 375 Sol = [F = true, NV = 11, TC = 1206.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1351 Sol = [F = true, NV = 11, TC = 1202.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3048 Sol = [F = true, NV = 11, TC = 1185.37]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 Sol = [F = true, NV = 11, TC = 1198.39]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 Sol = [F = true, NV = 12, TC = 1364.07]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6218 Sol = [F = true, NV = 12, TC = 1288.51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 Sol = [F = true, NV = 12, TC = 1287.89]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6621 Sol = [F = true, NV = 12, TC = 1252.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6821 Sol = [F = true, NV = 11, TC = 1198.65]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 Sol = [F = true, NV = 11, TC = 1194.79]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 Sol = [F = true, NV = 12, TC = 1342.93]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8400 Sol = [F = true, NV = 12, TC = 1334.16]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 Sol = [F = true, NV = 12, TC = 1413.56]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8757 Sol = [F = true, NV = 12, TC = 1392.59]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8786 Sol = [F = true, NV = 12, TC = 1383.35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9100 Sol = [F = true, NV = 12, TC = 1329.22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9146 Sol = [F = true, NV = 12, TC = 1326.02]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9157 Sol = [F = true, NV = 12, TC = 1301.37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9300 Sol = [F = true, NV = 12, TC = 1289.54]");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 Sol = [F = true, NV = 13, TC = 1435.01]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10159 Sol = [F = true, NV = 12, TC = 1421.78]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10200 Sol = [F = true, NV = 12, TC = 1349.73]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 Sol = [F = true, NV = 12, TC = 1341.72]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11200 Sol = [F = true, NV = 12, TC = 1318.1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11800 Sol = [F = true, NV = 12, TC = 1298.04]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11927 Sol = [F = true, NV = 11, TC = 1256.39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12012 Sol = [F = true, NV = 11, TC = 1248.96]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12114 Sol = [F = true, NV = 11, TC = 1238.39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12200 Sol = [F = true, NV = 11, TC = 1236.23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12500 Sol = [F = true, NV = 11, TC = 1230.14]");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 61 81 106 90 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "Requests\n" +
                "0 3 5 1 2 29 4\n" +
                "49 26 38 37\n" +
                "24 22 23 20 21\n" +
                "50 33 32 48 27\n" +
                "28 44 13 35 40\n" +
                "8 25 6 9 7\n" +
                "15 17 18 16 19\n" +
                "46 52 30 36 39\n" +
                "42 31\n" +
                "45 41 51 43\n" +
                "47 12 11 14 34 10\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

}
