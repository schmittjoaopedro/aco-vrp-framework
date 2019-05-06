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
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17 BFS = 68.10749521219215, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1, 2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 25 BFS = 170.16459149810834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 41 BFS = 277.94452374043783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 49 BFS = 486.5683202048672, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 57 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 730.5492654458797, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 630.392923139982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 BFS = 759.1239321286407, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 BFS = 658.9675898227433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17, 18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 BFS = 705.5661494511835, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 163 BFS = 667.3942406986744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 170 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23, 24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 235 BFS = 733.3082638062883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26, 27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 243 BFS = 750.1061413284173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29, 30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 773.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 BFS = 858.7648678412171, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 390 BFS = 793.2781218631537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 397 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39, 40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 BFS = 867.426306084282, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 468 BFS = 801.9133901055941, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 804.7975905059207, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 543 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 853.9249162227657, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 553 BFS = 816.4961882144582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 860.3584679562651, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 616 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50, 51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 688 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 BFS = 828.9368669428337, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "Requests\n" +
                "36 37 34 32 35 33\n" +
                "10 15 13 11 14 12\n" +
                "25 23 21 22 26 27 24\n" +
                "38 40 42 39 41\n" +
                "44 47 46 45 43\n" +
                "50 51 52 48 49\n" +
                "6 7 8 9\n" +
                "4 5 2 3 0 1\n" +
                "19 17 16 20 18\n" +
                "30 28 29 31\n" +
                "Cost = 828.9368669428337\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_a_0_5_test() throws IOException {
        String problem = "lc101_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 68.10749521219215, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 122 BFS = 140.16459149810834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 154 BFS = 170.16459149810834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 203 BFS = 203.53340542602967, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 211 BFS = 277.94452374043783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 371.39698204104434, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 259 BFS = 416.4045239406865, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 276 BFS = 486.5683202048671, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 603.70632454849, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 400 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 608.4463579270782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 BFS = 630.3929231399821, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 BFS = 630.392923139982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 BFS = 647.9648148254535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 733.0228260148286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 658.9675898227432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 660.6182018300894, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 661.2769979300508, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 667.3942406986744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 BFS = 667.8290964379999, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 BFS = 691.6512511098133, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 BFS = 713.5706006521705, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 900 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 BFS = 716.4995328403049, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 BFS = 733.3082638062883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 BFS = 739.0911033463801, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 BFS = 742.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 BFS = 750.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 758.1154379306005, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 BFS = 773.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 847.9340407423697, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1581 BFS = 804.7468790603041, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1700 BFS = 779.5108389329195, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 BFS = 842.847092309277, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 BFS = 781.2026548656956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 BFS = 784.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 BFS = 793.2781218631536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 BFS = 868.8243902079979, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1984 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 BFS = 795.1808428081865, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 BFS = 867.5703807756147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 BFS = 837.9520275674291, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2332 BFS = 801.9133901055939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 BFS = 837.2173448162239, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2348 BFS = 804.2130525118737, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 BFS = 842.226318514228, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2364 BFS = 804.7975905059204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 BFS = 804.7975905059204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 BFS = 813.266457293454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 816.4961882144582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 BFS = 821.1723806350768, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 BFS = 824.5412890212223, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 BFS = 827.1223140011105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3500 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "Requests\n" +
                "22 24 26 27 23 21 25\n" +
                "36 32 35 37 34 33\n" +
                "4 1 5 3 2 0\n" +
                "50 51 52 48 49\n" +
                "7 9 6 8\n" +
                "20 17 18 16 19\n" +
                "12 15 13 11 14 10\n" +
                "40 38 42 39 41\n" +
                "46 44 45 47 43\n" +
                "30 28 29 31\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lc101_a_0_9_test() throws IOException {
        String problem = "lc101_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 BFS = 68.10749521219215, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 219 BFS = 140.16459149810834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 284 BFS = 170.16459149810834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 373 BFS = 203.53340542602967, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 381 BFS = 277.94452374043783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 BFS = 371.39698204104434, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 416.40452394068654, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 494 BFS = 486.5683202048671, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 BFS = 486.56832020486706, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 603.70632454849, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 608.4463579270782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 858 BFS = 621.9776153822554, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 866 BFS = 630.3929231399821, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 907 BFS = 647.9648148254535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 658.9675898227431, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1481 BFS = 660.6182018300897, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1513 BFS = 667.3942406986746, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 667.8290964379999, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 691.6512511098134, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2145 BFS = 817.0778605257461, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2149 BFS = 777.7475157920911, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2151 BFS = 716.4995328403049, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2169 BFS = 727.8843673480433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2177 BFS = 733.3082638062882, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2193 BFS = 739.0911033463801, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2201 BFS = 804.9712505614705, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2205 BFS = 742.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2225 BFS = 803.9958054768765, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2231 BFS = 750.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 758.1154379306005, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2800 BFS = 798.7222426775327, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2801 BFS = 773.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3496 BFS = 781.2026548656956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 BFS = 784.349189675019, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3528 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3544 BFS = 793.2781218631537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 BFS = 829.6455036765268, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4183 BFS = 795.1808428081868, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 BFS = 801.913390105594, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4232 BFS = 804.2130525118736, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 BFS = 837.8018828102707, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4257 BFS = 804.7975905059207, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4887 BFS = 870.4545811759414, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4895 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4952 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4968 BFS = 813.2664572934541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 816.4961882144582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5534 BFS = 821.1723806350769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5559 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6206 BFS = 824.5412890212223, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6246 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7395 BFS = 929.5151946282751, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7400 BFS = 889.8457755063667, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7401 BFS = 828.936866942834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "34 36 37 33 35 32\n" +
                "12 10 11 13 15 14\n" +
                "23 26 24 22 25 27 21\n" +
                "19 17 18 20 16\n" +
                "1 0 2 5 4 3\n" +
                "39 40 38 42 41\n" +
                "44 47 46 45 43\n" +
                "50 51 52 48 49\n" +
                "31 29 30 28\n" +
                "6 8 7 9\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lc101_q_0_0_1_test() throws IOException {
        String problem = "lc101_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 185.21025295222879, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17 BFS = 182.86944611643477, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 187 BFS = 216.05616339086492, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 271.89719981718827, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 301.89719981718827, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 413 BFS = 335.2660137451096, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 429 BFS = 373.7225609214243, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 418.5559204588139, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 453.0387972149056, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 523.2025934790862, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 640.3405978227091, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 900 BFS = 640.340597822709, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 687.149489779546, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 644.7257626298435, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 644.7257626298436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 653.1410703875702, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 BFS = 670.7129620730414, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 BFS = 681.7157370703312, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 721.5382578301867, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1647 BFS = 685.4996203683515, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 BFS = 686.1584164683127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 BFS = 820.9867328727993, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 BFS = 692.2756592369365, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1700 BFS = 692.2756592369362, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 BFS = 692.7105149762616, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 BFS = 716.5326696480753, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 BFS = 738.7763051127889, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1900 BFS = 738.7763051127888, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 811.8123023758411, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2380 BFS = 741.7052373009235, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 BFS = 810.965254476309, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2413 BFS = 745.3082638062883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 BFS = 751.0911033463801, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 754.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 762.106141328417, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 770.1154379306004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 779.486202550148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 BFS = 785.29036182878, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 785.5108389329198, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3300 BFS = 785.5108389329197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3600 BFS = 785.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 BFS = 787.2026548656956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 864.4044258671045, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3909 BFS = 790.3491896750193, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 BFS = 867.3333580552389, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3942 BFS = 793.2781218631537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 BFS = 828.9735568424472, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3975 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 BFS = 795.1808428081866, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 801.913390105594, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4700 BFS = 801.9133901055939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 BFS = 804.2130525118735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 BFS = 858.8463176038107, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 BFS = 870.4545811759414, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 864.7096331158803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5503 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 920.8022503766792, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 813.2664572934541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 BFS = 821.1723806350768, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 847.1723806350769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6185 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 824.5412890212223, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 BFS = 864.551042009418, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 BFS = 866.3594430907289, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "10 12 14 15 13 11\n" +
                "42 40 38 39 41\n" +
                "17 20 19 18 16\n" +
                "51 49 48 50 52\n" +
                "35 33 34 36 37 32\n" +
                "47 46 45 44 43\n" +
                "31 29 28 30\n" +
                "22 24 27 23 26 21 25\n" +
                "5 1 0 3 4 2\n" +
                "7 8 9 6\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lc101_q_0_0_5_test() throws IOException {
        String problem = "lc101_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 837.0302395002793, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 807.61921691635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3 BFS = 784.2632061983181, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12 BFS = 752.7456724775495, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 754.6957036885481, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 788.0804914588949, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 319 BFS = 756.5629577381264, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 757.9742680547992, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 795.0228273176152, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 561 BFS = 766.8900813671936, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 BFS = 766.8900813671935, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 769.2041305321533, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 773.5892953392878, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 773.5892953392878, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 778.6226742721642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 778.6226742721641, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 813.9240912909529, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 782.4065575701845, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2000 BFS = 782.4065575701844, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 786.2262176826855, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 791.1892073134176, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 827.4813404148047, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 795.9638066940363, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 802.8119986566196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 843.6062099492418, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 806.1774819409343, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 866.102673208286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 834.5851394875176, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3913 BFS = 808.3790440126783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 BFS = 811.2986809857272, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 850.3422441869002, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 812.9135161785927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 818.7768316906622, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 818.7768316906622, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 890.455548418015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 821.3823829661262, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 824.6121138871304, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 BFS = 824.6121138871301, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 826.369473200011, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6400 BFS = 826.3694732000109, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 828.936866942834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "25 26 24 27 21 22 23\n" +
                "12 13 14 15 11 10\n" +
                "50 48 52 49 51\n" +
                "45 44 43 47 46\n" +
                "31 28 29 30\n" +
                "20 19 16 18 17\n" +
                "0 4 5 3 1 2\n" +
                "34 32 36 37 33 35\n" +
                "41 38 40 39 42\n" +
                "9 7 6 8\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lc101_q_0_0_9_test() throws IOException {
        String problem = "lc101_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 824.2505754418187, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 900 BFS = 824.2505754418186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3300 BFS = 827.179507629953, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 827.1795076299533, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 BFS = 828.9368669428337, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "26 22 23 21 27 24 25\n" +
                "33 35 34 36 37 32\n" +
                "40 42 41 39 38\n" +
                "13 11 12 14 15 10\n" +
                "30 31 28 29\n" +
                "50 51 48 49 52\n" +
                "47 44 43 46 45\n" +
                "2 3 0 5 4 1\n" +
                "17 19 20 16 18\n" +
                "7 9 8 6\n" +
                "Cost = 828.9368669428337\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lrc107_a_0_1_test() throws IOException {
        String problem = "lrc107_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 334 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2, 3, 4, 5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 BFS = 547.2959814653632, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 540.5488027573015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 515 BFS = 530.2153431106785, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 BFS = 894.5428177321809, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 BFS = 820.3320924074361, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 658 BFS = 812.8418866177635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 BFS = 987.7897479494064, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 700 BFS = 960.6762672373555, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 BFS = 1146.1706731303977, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 800 BFS = 1107.4662659682228, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 1210.3795815959204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 836 BFS = 1155.3688920178422, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 BFS = 1437.4504887007815, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 BFS = 1390.8331806068395, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 930 BFS = 1311.721222880248, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 BFS = 1518.2510663258631, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 BFS = 1512.6728047780084, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1200 BFS = 1467.261899642952, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1203 BFS = 1409.0238986820211, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 BFS = 1529.6767784419744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1500 BFS = 1507.760968305685, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1506 BFS = 1483.866855320279, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 1487.443504321323, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1900 BFS = 1429.6510815060406, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2600 BFS = 1427.1827993802601, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6855 BFS = 1370.5425691250032, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9500 BFS = 1355.813944523359, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10170 BFS = 1291.7830011866301, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10200 BFS = 1291.78300118663, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10203 BFS = 1279.4424152322285, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 BFS = 1271.0843868866875, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11598 BFS = 1267.5259301592196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11604 BFS = 1248.0861037424409, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11800 BFS = 1244.7199190902465, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12759 BFS = 1239.1807807734995, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12788 BFS = 1232.541301266746, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13321 BFS = 1230.1448446772672, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 61 81 106 90 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "Requests\n" +
                "23 20 22 24 21\n" +
                "5 0 3 4 1 2 29\n" +
                "52 46 36 30 39\n" +
                "28 40 13 44 35\n" +
                "10 34 14 11 12 47\n" +
                "6 25 9 8 7\n" +
                "15 18 19 17 16\n" +
                "50 27 48 32 33\n" +
                "43 45 51 41\n" +
                "42 31\n" +
                "37 38 26 49\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11\n" +
                "Total time (s) = ");
    }

    @Test
    public void dpdptw_lrc107_a_0_5_test() throws IOException {
        String problem = "lrc107_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2000 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 BFS = 240.62268786557559, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2584 BFS = 251.36949052486273, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2667 BFS = 305.53910834632507, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2668 BFS = 260.4516030630957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2750 BFS = 483.068734988581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2761 BFS = 449.8809258945144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2834 BFS = 547.2743034415381, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2861 BFS = 536.9625218187404, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2865 BFS = 530.2153431106786, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2917 BFS = 541.7669221078286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13, 14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 BFS = 733.519288219841, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3009 BFS = 725.8591893924741, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3010 BFS = 703.6268648345308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3070 BFS = 675.67942538487, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3079 BFS = 669.2286668012526, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3084 BFS = 721.2711106473465, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3151 BFS = 712.6559667286147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 BFS = 738.0321951078446, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 BFS = 814.6418365955808, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 BFS = 928.1549699452471, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 BFS = 1037.721589502282, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 BFS = 968.035920947244, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 BFS = 945.1364587316817, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 1026.2214626495893, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 971.7937527746082, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 BFS = 1041.5783031783292, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 BFS = 1082.0830992812546, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3922 BFS = 999.9324594806436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 BFS = 1145.004145445678, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4007 BFS = 1081.405307773565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4029 BFS = 1042.2805296287652, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 1189.8375648255956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1303.7076312952788, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4179 BFS = 1230.5788700437258, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 BFS = 1220.3091971578792, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 BFS = 1332.6285800700566, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 BFS = 1195.128686694532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4401 BFS = 1184.6854055523097, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1331.868796280572, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1288.3453650674444, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4556 BFS = 1196.5288755877514, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1249.95859616286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4602 BFS = 1215.8755097902329, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 BFS = 1355.3379212614118, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4668 BFS = 1304.9251003275745, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 1349.439882723617, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 BFS = 1427.58484727083, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1424.8859779986108, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1408.0897594647975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 1335.2934024083827, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 1409.895335272812, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 1408.2610443874412, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5119 BFS = 1271.4354244216927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1410.4242423341657, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1514.333146698537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5660 BFS = 1450.4369172902923, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5662 BFS = 1428.9791714511969, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1543.8074063011136, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6087 BFS = 1469.0699022848585, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 BFS = 1439.372631493957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 BFS = 1647.9762420019501, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6445 BFS = 1523.5144687335903, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6457 BFS = 1320.343135714617, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1471.8227211102223, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7813 BFS = 1401.5766733564442, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 BFS = 1542.4825155189212, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 BFS = 1500.030138775635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8635 BFS = 1390.5237022849838, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11495 BFS = 1292.0805064552749, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17241 BFS = 1261.5347728576623, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17269 BFS = 1230.144844677267, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 61 81 106 90 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "Requests\n" +
                "10 11 12 47 14 34\n" +
                "30 36 46 52 39\n" +
                "5 4 1 0 29 3 2\n" +
                "6 9 7 25 8\n" +
                "28 13 40 44 35\n" +
                "18 15 17 16 19\n" +
                "23 20 21 22 24\n" +
                "27 50 48 32 33\n" +
                "42 31\n" +
                "51 41 45 43\n" +
                "38 26 49 37\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11\n" +
                "Total time (s) =");
    }

    @Test
    public void dpdptw_lrc107_a_0_9_test() throws IOException {
        String problem = "lrc107_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 240.62268786557559, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 260.4516030630957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 423.2096312542916, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 449.8809258945144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5167 BFS = 536.9625218187405, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5169 BFS = 530.2153431106786, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5200 BFS = 530.2153431106785, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 594.3199199682073, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5278 BFS = 552.1003817544514, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5283 BFS = 541.7669221078286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 602.6704203867139, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5397 BFS = 566.1217776146023, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 BFS = 650.4831549194781, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5473 BFS = 640.1496952728553, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 666.302518820863, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 730.5940318659294, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5585 BFS = 711.7323083821722, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 670.1096204874016, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 712.6559667286147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 BFS = 712.6559667286145, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 BFS = 794.6025738082387, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5757 BFS = 754.3034486054441, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5768 BFS = 738.0321951078446, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 880.6708358385093, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5845 BFS = 813.7616946746449, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5876 BFS = 805.4873985268528, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 BFS = 829.5018034827588, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5937 BFS = 817.966615247128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 871.7146280839456, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 961.2440173188588, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6109 BFS = 930.3462023200864, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1000.4338707457866, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6177 BFS = 945.0953865135212, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1022.3764809992117, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6258 BFS = 971.9849401607173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6405 BFS = 938.318179807634, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1020.5387733785939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6603 BFS = 943.7567306304605, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1084.4531304650156, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6919 BFS = 1011.4570648328636, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6935 BFS = 954.9170275857606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 1041.5783031783292, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7002 BFS = 991.2242442384896, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 BFS = 1050.0224756982539, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7100 BFS = 1031.729040341415, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 BFS = 1224.157285591721, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7253 BFS = 1204.6611640663994, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7263 BFS = 1042.4293868943516, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1202.4062394337568, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7483 BFS = 1179.7126460102083, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1221.9571869457977, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1159.1017512938083, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 BFS = 1202.5758493160877, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7604 BFS = 1171.7121994409208, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1313.8144104338492, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7755 BFS = 1232.4655718115357, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8003 BFS = 1188.6998405660866, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1323.1407335264294, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1245.3147282348573, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1347.8247849463492, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1213.0581796075087, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1282.6891336136034, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8501 BFS = 1172.2925301514213, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 BFS = 1285.2744611354055, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1290.3958430106643, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 BFS = 1513.6460587536512, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 BFS = 1473.5864686479908, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8776 BFS = 1346.1910554490496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 BFS = 1448.0876290172287, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 BFS = 1344.244321898795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 BFS = 1376.2633625224632, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 BFS = 1471.4033781007545, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9500 BFS = 1393.0894330926808, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1458.9123365235291, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10300 BFS = 1436.2497176020909, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 BFS = 1495.7684158313252, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 BFS = 1441.8708298741342, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 BFS = 1659.3552121524606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11585 BFS = 1499.9635595121606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11586 BFS = 1480.5237330953819, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 BFS = 1659.1387551159164, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 BFS = 1593.843389239244, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11751 BFS = 1380.4665552688723, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 BFS = 1553.2412627502147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14953 BFS = 1484.2594476105314, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15052 BFS = 1370.390659037114, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 31 29 27 26 28 30 34 32 0\n" +
                "0 61 81 106 90 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 69 98 78 73 79 60 70 68 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 92 62 85 63 76 89 33 101 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 95 84 51 91 102 80 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 88 53 11 15 0\n" +
                "Requests\n" +
                "1 3 5 0 2 29 4\n" +
                "15 16 17 18\n" +
                "42 31\n" +
                "10 14 34 12 11\n" +
                "21 23 20 22 24\n" +
                "52 30 36 39\n" +
                "8 25 7 9\n" +
                "35 40 13 44 28\n" +
                "48 32 19 33\n" +
                "37 49 38 26\n" +
                "50 27 47\n" +
                "45 43 51 41\n" +
                "46 6\n" +
                "Cost = 1370.390659037114\n" +
                "Num. vehicles = 13");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_1_test() throws IOException {
        String problem = "lrc107_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 215.6114377533283, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6 BFS = 179.1097523093241, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 283.6004486965975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 364.73963712780426, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4949 BFS = 360.3636952498931, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 380.5555510125147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 416.2604312503653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5266 BFS = 411.0063249678769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 425.34254378859833, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5342 BFS = 400.3844662100348, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 492.93707291890144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 461.0434340785012, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 509.8968190113195, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 611.7235477858698, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 607.2901965583432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5677 BFS = 582.0374964675191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5744 BFS = 579.1998894960412, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 647.6238251014944, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 602.536319818265, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5885 BFS = 583.8288253841935, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 694.2568440682537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6008 BFS = 670.6461963840455, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 784.2051731619, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6089 BFS = 717.974171770252, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 719.0193097702105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 BFS = 784.9724240115302, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6367 BFS = 769.2050096264329, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 893.6133086996523, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6525 BFS = 847.4763417903042, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 886.0152931256679, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6585 BFS = 821.8194281479801, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 942.6123166584019, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 900.1981721944284, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 999.8188494109677, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6770 BFS = 934.518312986843, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1049.000205471183, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6919 BFS = 974.026067824551, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6962 BFS = 941.4873305836599, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7100 BFS = 941.4873305836597, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1011.6740782271212, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7420 BFS = 945.7978582942941, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 BFS = 1125.0503378087556, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 BFS = 994.3883391945457, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 993.2653719023233, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1051.1815621934113, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7901 BFS = 1017.2374548625805, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 BFS = 1178.8129903374295, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8112 BFS = 1103.655439347738, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1228.7553405232372, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8200 BFS = 1208.0325526894565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8202 BFS = 1167.1428206209057, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1213.4314872161788, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1169.5937856743606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1235.37643471703, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1363.2890591175474, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8679 BFS = 1244.4462399886393, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1398.6516689533562, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1289.372730186015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9100 BFS = 1273.3933889947234, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9102 BFS = 1228.4284442167443, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 BFS = 1296.2159576922027, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9264 BFS = 1245.0554316282648, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 BFS = 1345.0957085988198, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 BFS = 1279.4387179287992, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9509 BFS = 1254.0230090554824, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9512 BFS = 1230.813391948655, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1317.3933499546542, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1454.9677768833492, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9698 BFS = 1385.2983400897579, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 BFS = 1376.5509165185551, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9716 BFS = 1338.6227775349114, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1380.8109621399242, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9751 BFS = 1334.4850810055957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 BFS = 1432.6329456892954, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9900 BFS = 1375.8142567264447, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9901 BFS = 1319.7877607604175, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 BFS = 1464.3861769932835, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10251 BFS = 1347.6036243977321, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 BFS = 1515.4128594883412, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10531 BFS = 1372.4858317242831, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 1585.7746476791947, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10786 BFS = 1473.0949343787536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10787 BFS = 1397.3780773999968, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 BFS = 1584.7913221578144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12174 BFS = 1454.1120864533298, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12300 BFS = 1419.5479481929228, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12301 BFS = 1400.1081217761441, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 1534.715338813592, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13181 BFS = 1434.349473910636, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14100 BFS = 1405.1409965554171, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 1547.2371494259937, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16591 BFS = 1486.6186633781815, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17816 BFS = 1417.3757106915311, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 62 33 101 63 89 76 91 102 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 92 95 84 51 85 50 96 80 0\n" +
                "0 65 83 52 86 57 24 22 66 0\n" +
                "0 31 29 27 26 28 30 34 32 0\n" +
                "0 81 106 0\n" +
                "0 82 99 87 59 75 58 74 105 0\n" +
                "0 69 98 78 73 79 60 70 68 0\n" +
                "0 88 53 11 15 97 77 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 72 71 93 67 94 54 0\n" +
                "0 61 90 0\n" +
                "Requests\n" +
                "23 20 22 24 21\n" +
                "32 19 33 47\n" +
                "4 2 3 5 1 0 29\n" +
                "48 27 50 26\n" +
                "13 28 35 44\n" +
                "18 16 17 15\n" +
                "42\n" +
                "45 40 41 43\n" +
                "39 36 52 30\n" +
                "46 6 51\n" +
                "10 12 14 11 34\n" +
                "25 7 9 8\n" +
                "38 37 49\n" +
                "31\n" +
                "Cost = 1417.3757106915311\n" +
                "Num. vehicles = 14");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_5_test() throws IOException {
        String problem = "lrc107_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 802.3312991376806, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15 BFS = 760.5705477823036, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 202 BFS = 756.1437358918253, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3700 BFS = 756.1437358918251, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 839.23545743777, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4098 BFS = 807.2574531838624, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4204 BFS = 802.0611868719297, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4212 BFS = 788.0825459312609, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4253 BFS = 768.9015822155807, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4393 BFS = 764.4747703251023, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 856.9112778478504, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 849.0294711815963, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 968.7211711306793, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 925.0543915158822, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 901.5439382826202, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5101 BFS = 884.6939640871733, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5105 BFS = 874.2805678386256, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1004.6508495981282, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5251 BFS = 967.2856400768278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5281 BFS = 913.2965908070549, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5351 BFS = 866.2956859775011, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1056.152273233463, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 1035.9428498964917, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5503 BFS = 1011.154925633782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 1069.1729071883203, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 1032.0455881233338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 1123.315888588271, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5676 BFS = 1063.282723317899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 1082.260844306171, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5838 BFS = 1037.169770864165, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1159.3149272396613, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6091 BFS = 1061.7317464564105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1127.4218136041302, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6200 BFS = 1125.658935904936, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6218 BFS = 1064.1243059399553, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1151.3517726613286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1072.9905557496418, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1237.7773535131805, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1180.338886084965, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6505 BFS = 1082.6539490522782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1203.9138554564313, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1151.326161111532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1261.881786150852, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1161.244825654316, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6674 BFS = 1101.2554471963015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1321.4622156296227, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6762 BFS = 1237.467227552721, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1272.768946095574, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1208.3241608125807, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7101 BFS = 1192.8085959430098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1338.2515440186905, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7359 BFS = 1239.3008170862845, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7502 BFS = 1195.7023686316759, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7661 BFS = 1141.7263392665568, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1308.4753096005181, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1193.8845828352723, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7838 BFS = 1145.3483096918364, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1329.5094722073732, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1402.2217965114717, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8703 BFS = 1349.1885820614434, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8901 BFS = 1293.9037187610131, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1463.4670917354188, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1441.2910563771675, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9001 BFS = 1436.475115384116, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9049 BFS = 1343.6414557872954, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1496.2753003114226, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9585 BFS = 1396.402660360608, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9630 BFS = 1352.0217502211422, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1461.1861601835458, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 BFS = 1425.243810697475, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9701 BFS = 1367.2021632114927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1589.5286908442008, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1515.4466396078471, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9800 BFS = 1467.6839870901579, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9869 BFS = 1342.8082841628345, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9871 BFS = 1332.0388327736, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 1479.0802771108188, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10802 BFS = 1428.4689074058792, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11400 BFS = 1387.3004065112461, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 1586.7382913680979, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12930 BFS = 1471.2056398260008, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 BFS = 1524.7635349503796, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 BFS = 1487.3450942358274, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13624 BFS = 1415.3481201012714, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 1483.9951927628572, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16900 BFS = 1440.2542464878234, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19400 BFS = 1427.1561491304979, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 64 19 21 23 25 49 20 56 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 82 99 0\n" +
                "0 72 71 93 67 94 54 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 61 81 106 90 0\n" +
                "0 92 62 85 63 89 76 91 102 0\n" +
                "0 65 52 57 18 48 22 24 66 0\n" +
                "0 95 84 51 50 96 80 0\n" +
                "0 83 86 87 59 0\n" +
                "0 11 15 75 97 58 77 74 105 0\n" +
                "Requests\n" +
                "36 39 52 30 46\n" +
                "14 11 12 34\n" +
                "20 23 22 21 24\n" +
                "43\n" +
                "38 49 37\n" +
                "3 5 0 29 1 2 4\n" +
                "19 15 17 18 16\n" +
                "7 25 9 8\n" +
                "42 31\n" +
                "47 33 32 48\n" +
                "10 28 35 13\n" +
                "26 27 50\n" +
                "44 45\n" +
                "51 6 41 40\n" +
                "Cost = 1427.1561491304979\n" +
                "Num. vehicles = 14");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_9_test() throws IOException {
        String problem = "lrc107_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 1337.8058416891342, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 BFS = 1283.2114879016572, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4381 BFS = 1249.6214064602323, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4600 BFS = 1242.7214628423098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1419.081247547186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 1396.4464593809262, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5601 BFS = 1275.7835172392597, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 1380.349654780539, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6085 BFS = 1330.02912519045, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7602 BFS = 1274.8691452627804, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1607.6786012841728, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8176 BFS = 1481.8943469514365, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8402 BFS = 1465.2912454493594, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1631.3300074999302, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8669 BFS = 1543.8770843531845, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8673 BFS = 1448.1033876615102, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1589.6596293828727, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9752 BFS = 1468.5278297194893, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9758 BFS = 1428.8380614180512, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15700 BFS = 1423.5294521406101, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15783 BFS = 1360.5774305062077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16493 BFS = 1305.8827380898529, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17600 BFS = 1305.3669778463795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17610 BFS = 1285.9271514296008, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19000 BFS = 1285.9271514296006, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19032 BFS = 1230.1448446772672, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 61 81 106 90 0\n" +
                "Requests\n" +
                "14 34 12 11 10 47\n" +
                "3 0 2 29 4 5 1\n" +
                "17 15 19 16 18\n" +
                "13 28 35 40 44\n" +
                "46 30 36 52 39\n" +
                "23 21 24 20 22\n" +
                "6 7 25 9 8\n" +
                "49 38 37 26\n" +
                "45 43 51 41\n" +
                "27 50 32 33 48\n" +
                "42 31\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
    }

}
