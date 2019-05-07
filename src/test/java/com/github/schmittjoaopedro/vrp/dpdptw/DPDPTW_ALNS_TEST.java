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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 57 BFS = 603.70632454849, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 730.5492654458798, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 630.3929231399821, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 BFS = 658.9675898227432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17, 18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 BFS = 705.5661494511836, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 BFS = 667.3942406986745, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 170 BFS = 713.5706006521705, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23, 24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 235 BFS = 733.3082638062884, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26, 27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 243 BFS = 750.1061413284173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29, 30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 773.486202550148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 BFS = 826.2824141675038, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 390 BFS = 793.2781218631536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 397 BFS = 794.7691540159126, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39, 40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 BFS = 801.9133901055937, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 543 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 816.4961882144582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50, 51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 688 BFS = 827.1223140011102, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "Requests\n" +
                "21 26 22 27 23 24 25\n" +
                "15 12 10 14 11 13\n" +
                "32 34 33 37 35 36\n" +
                "2 3 1 4 0 5\n" +
                "46 44 47 45 43\n" +
                "52 48 50 49 51\n" +
                "29 31 30 28\n" +
                "16 20 19 17 18\n" +
                "6 8 9 7\n" +
                "38 40 41 39 42\n" +
                "Cost = 828.9368669428338\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 300 BFS = 486.56832020486706, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 651.5466337946077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 608.4463579270782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 BFS = 730.5492654458798, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 482 BFS = 630.3929231399821, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 BFS = 647.9648148254535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 658.9675898227432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 BFS = 658.9675898227431, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 660.6182018300894, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 661.2769979300508, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 667.3942406986745, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 BFS = 667.8290964379999, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 BFS = 691.6512511098133, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 BFS = 716.4995328403049, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 BFS = 733.3082638062883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 BFS = 739.0911033463802, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 BFS = 742.3064824257782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 BFS = 750.1061413284173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1400 BFS = 750.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 758.1154379306004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 BFS = 773.486202550148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 779.5108389329197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 BFS = 781.2026548656958, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 BFS = 784.3491896750193, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 BFS = 793.2781218631536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 BFS = 794.7691540159126, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 BFS = 795.1808428081868, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 BFS = 801.9133901055939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 BFS = 804.2130525118736, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 BFS = 804.7975905059207, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2400 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2700 BFS = 804.7975905059204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 BFS = 813.2664572934541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 BFS = 857.2110180969122, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 BFS = 821.172380635077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 BFS = 822.9297399479577, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3200 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 BFS = 824.5412890212224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "2 5 3 4 0 1\n" +
                "42 40 39 38 41\n" +
                "51 52 50 48 49\n" +
                "13 11 15 10 14 12\n" +
                "31 30 29 28\n" +
                "37 33 35 34 36 32\n" +
                "23 22 24 26 21 27 25\n" +
                "47 45 43 46 44\n" +
                "20 19 17 18 16\n" +
                "8 7 9 6\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 416.4045239406865, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 494 BFS = 486.56832020486706, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 603.70632454849, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 608.4463579270783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 858 BFS = 621.9776153822555, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 866 BFS = 630.392923139982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 907 BFS = 647.9648148254535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 658.9675898227433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1100 BFS = 658.9675898227432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1200 BFS = 658.9675898227431, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1481 BFS = 660.6182018300897, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1500 BFS = 660.6182018300896, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1513 BFS = 667.3942406986744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 667.8290964379999, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 691.6512511098133, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 BFS = 713.5706006521705, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2145 BFS = 716.4995328403049, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2169 BFS = 727.8843673480433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2177 BFS = 733.3082638062882, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2193 BFS = 739.0911033463802, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2201 BFS = 742.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2225 BFS = 750.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 758.1154379306005, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2800 BFS = 773.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 BFS = 804.7468790603042, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 BFS = 779.5108389329197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2900 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3496 BFS = 781.2026548656958, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 BFS = 784.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3528 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3544 BFS = 793.2781218631537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 BFS = 794.7691540159128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3600 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4183 BFS = 795.1808428081868, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 BFS = 801.9133901055939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4232 BFS = 804.2130525118735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 BFS = 804.7975905059207, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4400 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4887 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4952 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4968 BFS = 813.2664572934541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 816.4961882144582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5200 BFS = 816.496188214458, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5534 BFS = 821.172380635077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5559 BFS = 822.9297399479577, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6206 BFS = 824.5412890212222, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6246 BFS = 827.1223140011105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6300 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7395 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "Requests\n" +
                "37 36 33 35 32 34\n" +
                "2 3 1 0 4 5\n" +
                "23 24 27 26 21 22 25\n" +
                "51 50 49 48 52\n" +
                "46 47 45 43 44\n" +
                "8 9 7 6\n" +
                "17 19 16 20 18\n" +
                "39 40 42 38 41\n" +
                "10 12 15 11 14 13\n" +
                "31 30 29 28\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 182.86944611643477, feasible = true");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 418.55592045881394, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 453.0387972149056, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 523.2025934790862, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 640.3405978227091, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 700 BFS = 640.340597822709, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 644.7257626298435, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 644.7257626298435, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 653.1410703875702, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 BFS = 670.7129620730414, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 BFS = 681.7157370703312, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 685.4996203683515, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 BFS = 686.1584164683127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 BFS = 692.2756592369365, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1700 BFS = 692.2756592369362, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 BFS = 692.7105149762616, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 BFS = 716.5326696480752, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 BFS = 738.7763051127889, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1900 BFS = 738.7763051127888, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 781.995693159952, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 741.7052373009234, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 BFS = 745.3082638062883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 BFS = 791.3815592054088, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2437 BFS = 751.0911033463804, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 754.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 762.1061413284173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 BFS = 762.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 770.1154379306005, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3100 BFS = 770.1154379306004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 779.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 BFS = 785.29036182878, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 785.5108389329197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3300 BFS = 785.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 BFS = 787.2026548656955, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 BFS = 793.2781218631537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 BFS = 794.7691540159128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4100 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4600 BFS = 794.7691540159126, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 BFS = 795.1808428081869, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 801.913390105594, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 BFS = 804.2130525118736, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 804.7975905059204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 810.6609060179901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 813.266457293454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 816.496188214458, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 BFS = 821.1723806350769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 BFS = 822.9297399479575, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 824.5412890212224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 BFS = 828.9368669428337, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "Requests\n" +
                "24 22 27 23 21 26 25\n" +
                "39 38 40 42 41\n" +
                "33 37 35 32 34 36\n" +
                "10 13 15 14 12 11\n" +
                "47 45 44 43 46\n" +
                "51 49 50 52 48\n" +
                "28 30 31 29\n" +
                "20 19 17 18 16\n" +
                "0 4 5 2 1 3\n" +
                "9 8 7 6\n" +
                "Cost = 828.9368669428337\n" +
                "Num. vehicles = 10");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 806.7757065972233, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2 BFS = 752.7456724775496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 754.695703688548, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 756.5629577381264, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 757.9742680547992, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 798.407615087962, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 766.8900813671936, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 769.2041305321534, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 700 BFS = 769.2041305321533, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 773.5892953392879, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 773.5892953392879, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 778.6226742721642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 782.4065575701845, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1800 BFS = 782.4065575701844, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2300 BFS = 782.4065575701843, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 786.2262176826855, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2400 BFS = 786.2262176826854, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 791.1892073134176, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2900 BFS = 791.1892073134175, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 795.9638066940363, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3100 BFS = 795.9638066940362, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 802.8119986566195, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 843.6062099492419, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 806.1774819409344, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3400 BFS = 806.1774819409343, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 808.3790440126782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 BFS = 811.2986809857272, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 812.9135161785927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 818.7768316906622, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 818.7768316906622, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 821.3823829661262, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 824.6121138871304, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 BFS = 824.6121138871302, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 826.3694732000109, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "Requests\n" +
                "15 14 10 13 12 11\n" +
                "34 37 35 33 32 36\n" +
                "0 1 4 5 2 3\n" +
                "42 39 38 40 41\n" +
                "27 22 24 21 25 23 26\n" +
                "20 16 18 19 17\n" +
                "8 9 7 6\n" +
                "30 28 29 31\n" +
                "52 49 48 51 50\n" +
                "46 45 44 47 43\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 200 BFS = 824.2505754418186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 828.9368669428338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "Requests\n" +
                "32 35 36 34 37 33\n" +
                "5 4 0 2 1 3\n" +
                "6 7 8 9\n" +
                "22 25 24 27 21 23 26\n" +
                "12 10 15 13 14 11\n" +
                "28 30 29 31\n" +
                "19 17 16 20 18\n" +
                "47 45 46 44 43\n" +
                "42 38 40 39 41\n" +
                "50 51 49 52 48\n" +
                "Cost = 828.9368669428338\n" +
                "Num. vehicles = 10");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 506 BFS = 540.5488027573015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 BFS = 820.332092407436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 597 BFS = 814.6418365955808, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 623 BFS = 799.7094772159529, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 BFS = 964.4866170070205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 687 BFS = 949.4469864423157, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 BFS = 1186.389790553674, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 BFS = 1105.4662659682226, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 762 BFS = 1102.467234002377, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 1158.414535770055, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 846 BFS = 1135.770380850824, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 BFS = 1382.5727671034713, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 953 BFS = 1321.0244650491563, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 954 BFS = 1260.4656370017283, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 BFS = 1457.3849895146304, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1101 BFS = 1435.9061834702845, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 BFS = 1404.0559880340945, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 BFS = 1390.0764513541822, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 BFS = 1391.7963457900805, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 1557.4759531994544, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 BFS = 1492.772468587028, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 1415.165538656299, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1943 BFS = 1339.8197458482628, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4100 BFS = 1318.2647053457283, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4871 BFS = 1309.3386545045962, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8643 BFS = 1296.9181094591308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13313 BFS = 1249.5846710940457, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13358 BFS = 1230.1448446772672, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 61 81 106 90 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "Requests\n" +
                "11 34 12 10 47 14\n" +
                "4 0 1 29 2 5 3\n" +
                "33 27 48 32 50\n" +
                "17 15 19 18 16\n" +
                "23 24 21 20 22\n" +
                "6 9 7 25 8\n" +
                "13 35 28 44 40\n" +
                "42 31\n" +
                "52 30 39 36 46\n" +
                "51 41 43 45\n" +
                "49 26 37 38\n" +
                "Cost = 1230.1448446772672\n" +
                "Num. vehicles = 11");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2667 BFS = 260.4516030630957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2750 BFS = 449.8809258945144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2834 BFS = 547.2743034415381, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2849 BFS = 536.9625218187405, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2852 BFS = 530.2153431106785, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2917 BFS = 586.8544273910579, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2923 BFS = 541.7669221078286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13, 14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 BFS = 733.519288219841, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3006 BFS = 713.9603244811535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3012 BFS = 669.2286668012526, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3084 BFS = 712.6559667286147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 BFS = 777.7948324591308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3170 BFS = 738.0321951078446, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 BFS = 857.0559810595541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3260 BFS = 814.6418365955806, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3300 BFS = 799.7094772159528, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 BFS = 935.0069849064628, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 BFS = 918.9128663774748, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 BFS = 999.4205043188515, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3466 BFS = 968.0359209472439, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3486 BFS = 961.5314166245848, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3657 BFS = 931.843833637659, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 1004.650455248709, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 995.2394146903608, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3677 BFS = 960.5644719795685, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3684 BFS = 943.7567306304604, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 BFS = 1052.1609404550675, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3875 BFS = 996.9145000503452, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 BFS = 1046.1634375528135, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3918 BFS = 1002.1514772512328, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 BFS = 1044.3606591530004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4026 BFS = 1031.0512488337256, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 1102.4599030341424, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4091 BFS = 1098.6184401455591, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1175.55998154706, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1140.9983934193137, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 BFS = 1126.7069425303375, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 BFS = 1240.2537911871827, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4251 BFS = 1237.9948533253612, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 BFS = 1305.0854277834358, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4338 BFS = 1274.5571365608928, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4341 BFS = 1228.2063639201724, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4356 BFS = 1208.6078527531542, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1241.5170284462522, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4504 BFS = 1218.4544182005359, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4509 BFS = 1191.7641992034403, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4530 BFS = 1137.770380850824, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1278.3205939332156, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1230.1868795898463, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4588 BFS = 1146.9553498025762, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 BFS = 1299.2989047106066, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4693 BFS = 1210.0870352519046, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 1330.5922150276072, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4752 BFS = 1219.3771916757355, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4764 BFS = 1170.0027744681063, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 BFS = 1422.0936274555888, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4861 BFS = 1389.1548649025522, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4873 BFS = 1274.575642222569, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1500.4820426358128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4920 BFS = 1476.72687674278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4921 BFS = 1364.032619055516, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4933 BFS = 1315.8929879585583, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 1550.6425842040217, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5087 BFS = 1384.6884106189689, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 1350.708412275289, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5200 BFS = 1321.5123129644292, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1426.4063521952567, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5300 BFS = 1396.4062646111468, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1500.8397237796348, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1480.7659350931356, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5347 BFS = 1431.036813074384, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5349 BFS = 1427.538142674588, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5354 BFS = 1411.7765841714788, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5357 BFS = 1382.890878748565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 1369.0799546811825, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5707 BFS = 1345.4043641024352, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1422.7509759200686, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 BFS = 1381.4736368355093, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 BFS = 1607.2208938592535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6455 BFS = 1512.603420811247, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1445.7118441525656, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6648 BFS = 1359.70558155061, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7429 BFS = 1314.6045033069536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8183 BFS = 1292.8788529727535, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8200 BFS = 1289.0177810680118, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 BFS = 1306.3525242126914, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11400 BFS = 1301.3631846120245, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11415 BFS = 1285.9271514296008, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11600 BFS = 1279.4424152322288, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11625 BFS = 1253.143127821514, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11680 BFS = 1230.1448446772672, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17800 BFS = 1230.144844677267, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 61 81 106 90 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "Requests\n" +
                "48 50 27 33 32\n" +
                "6 7 25 9 8\n" +
                "34 10 12 47 11 14\n" +
                "23 20 21 24 22\n" +
                "30 39 46 36 52\n" +
                "17 15 18 16 19\n" +
                "5 2 4 29 3 1 0\n" +
                "13 35 40 44 28\n" +
                "42 31\n" +
                "51 45 43 41\n" +
                "38 49 26 37\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5167 BFS = 547.2743034415382, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5223 BFS = 536.9625218187405, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5228 BFS = 530.2153431106785, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 541.7669221078286, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 603.0675210118643, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5384 BFS = 555.7883179679795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 BFS = 641.9198348513438, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5437 BFS = 640.1496952728554, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 666.302518820863, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 710.528885502492, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5585 BFS = 670.1096204874016, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 727.8513280278689, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 BFS = 712.6559667286147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 BFS = 738.0321951078445, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 803.5396240649227, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 BFS = 811.9355392579052, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 871.7146280839455, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6029 BFS = 830.3549715842555, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 944.0694247967086, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6100 BFS = 928.1078399415212, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 950.099175182921, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6210 BFS = 932.4771853230092, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 983.4792202387722, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6269 BFS = 939.4462029198261, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 996.2831978677917, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6585 BFS = 971.5322001470746, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6625 BFS = 966.7056347776099, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 BFS = 949.4469864423158, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 992.8570318185642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6949 BFS = 955.4127500811686, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 1048.96971812375, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7010 BFS = 1040.670316277949, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7018 BFS = 1023.862574928841, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7021 BFS = 991.2242442384897, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 BFS = 1097.2245184229241, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7086 BFS = 1002.151477251233, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 BFS = 1117.0859813339007, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7252 BFS = 1088.48961402047, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7300 BFS = 1088.1991342713122, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7314 BFS = 1044.549694468208, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1201.4009253848963, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7343 BFS = 1171.1979916610294, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7365 BFS = 1122.3553053048727, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7387 BFS = 1117.3489423707924, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7400 BFS = 1092.8102512564778, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1157.8582861407754, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1109.9872876937545, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 BFS = 1258.2041504238737, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7586 BFS = 1234.1888461501921, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7587 BFS = 1196.788355508577, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7600 BFS = 1182.462738190655, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7615 BFS = 1131.4797329344913, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1245.2727804203494, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7757 BFS = 1204.283916719328, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7776 BFS = 1189.8137239511761, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7779 BFS = 1134.3313466124703, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1187.7354266766895, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8195 BFS = 1156.3509325875439, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8292 BFS = 1141.2858357877174, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8297 BFS = 1135.770380850824, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1142.3111435486132, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1279.7168470687495, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8418 BFS = 1233.326447024609, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8470 BFS = 1211.6526654832178, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 BFS = 1432.5722450182188, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8589 BFS = 1334.1792820766625, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8621 BFS = 1289.1566570435905, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1412.4290181647032, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1364.558019717682, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8685 BFS = 1327.6732437284734, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 BFS = 1307.428657735352, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8819 BFS = 1267.5343083018267, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 BFS = 1325.3407230747018, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 BFS = 1306.885892109441, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9173 BFS = 1284.6666440400745, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 BFS = 1473.8078273054973, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9446 BFS = 1402.008940059353, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9500 BFS = 1376.697708761766, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9513 BFS = 1320.4485831545744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1380.1093169367766, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9894 BFS = 1325.4063499761469, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10000 BFS = 1319.8751565822204, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10001 BFS = 1247.9530057750198, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 BFS = 1478.487878445128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11008 BFS = 1364.8733233653702, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11100 BFS = 1347.6331403491185, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11467 BFS = 1312.405779832338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 BFS = 1506.3833573605718, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11597 BFS = 1473.0464402622888, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 BFS = 1423.2163161336669, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11767 BFS = 1375.0131750597172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12966 BFS = 1289.0064892856674, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12970 BFS = 1227.0821192900041, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 BFS = 1373.7198772610093, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14936 BFS = 1323.628485239006, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17415 BFS = 1309.2063939995965, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19100 BFS = 1305.8827380898526, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19121 BFS = 1299.7874485573318, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 95 84 51 91 102 80 0\n" +
                "0 31 29 27 26 28 30 34 32 0\n" +
                "0 92 62 85 63 76 89 33 101 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 61 81 106 90 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "Requests\n" +
                "3 4 5 2 0 1 29\n" +
                "52 30 36 39 46\n" +
                "7 6 9 25 8\n" +
                "44 28 13 40 35\n" +
                "11 14 12 10 34\n" +
                "21 24 23 20 22\n" +
                "27 50 47\n" +
                "15 18 17 16\n" +
                "19 33 32 48\n" +
                "49 37 38 26\n" +
                "42 31\n" +
                "43 45 51 41\n" +
                "Cost = 1299.7874485573318\n" +
                "Num. vehicles = 12");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 22 BFS = 179.1097523093241, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 283.6004486965975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 360.3636952498931, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 400.2595223085897, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5104 BFS = 380.5555510125147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 436.2518490611141, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5251 BFS = 391.3023536718018, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 400.3844662100348, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 492.93707291890144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5501 BFS = 461.0434340785012, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 481.94979886629153, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 607.2901965583432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5700 BFS = 600.7600686898187, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5714 BFS = 582.0525742557472, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5725 BFS = 579.1998894960411, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5732 BFS = 568.8664298494184, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 583.8288253841935, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 664.2530602859628, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 722.0197667206096, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6116 BFS = 715.2122081036717, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 719.0193097702104, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 BFS = 746.4277369873901, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 872.0674092582763, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 820.1250377162407, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 864.2335726119536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6585 BFS = 821.8194281479803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6592 BFS = 821.0051796371765, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 881.5655804556009, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6674 BFS = 836.7183146258511, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1027.3844215918823, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6751 BFS = 936.8847579825544, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6800 BFS = 934.5183129868427, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 974.4024041401603, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6920 BFS = 947.360787998033, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 942.3015790944637, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1070.1302774531737, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 945.7978582942941, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7400 BFS = 945.797858294294, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 BFS = 968.683158540042, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7700 BFS = 957.4538777450024, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1095.5618023841982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7751 BFS = 1043.6194308421627, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7833 BFS = 1032.7622472134537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1045.5050493786712, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7853 BFS = 1006.008174067541, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8000 BFS = 1006.0081740675408, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 BFS = 1041.090059043157, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1159.4473580314411, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8173 BFS = 1099.007598577326, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1220.4278574627383, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8388 BFS = 1171.818584019882, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1241.1102006375452, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1197.272499095727, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8520 BFS = 1150.1621505136636, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1311.237402592633, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8698 BFS = 1214.9333322718112, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8700 BFS = 1204.349863701184, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8732 BFS = 1191.3519212537742, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8867 BFS = 1140.5794069874496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1155.434838999698, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 BFS = 1280.6550602675377, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 BFS = 1210.0497026405678, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9300 BFS = 1172.1352628883433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 BFS = 1283.8141078634546, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9356 BFS = 1228.1184719727908, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9443 BFS = 1207.590506624064, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1299.0694462884635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9590 BFS = 1270.8568771641947, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 BFS = 1270.1960363157525, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1321.6103975320436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9677 BFS = 1287.9675368560038, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1315.7667850586663, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9800 BFS = 1314.0048410664683, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9802 BFS = 1244.7717596042376, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 BFS = 1378.4989691120793, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9835 BFS = 1330.627970665058, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9960 BFS = 1243.3727240516919, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 BFS = 1423.1842664456508, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10258 BFS = 1352.2599060409154, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10395 BFS = 1250.7164509721763, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 BFS = 1443.4375939013048, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10565 BFS = 1361.865528715061, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 1397.3125053595838, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10847 BFS = 1358.5067609759765, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 BFS = 1469.2269151263308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 BFS = 1452.5573794017967, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12205 BFS = 1441.9985950464238, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12554 BFS = 1379.9550473182283, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 1509.515126341609, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12936 BFS = 1477.7631633512885, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12947 BFS = 1433.3655835141753, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12998 BFS = 1399.1329521243981, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13205 BFS = 1391.801562864834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13600 BFS = 1378.8304788790927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13800 BFS = 1350.989088524584, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15300 BFS = 1339.9870599972287, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15375 BFS = 1302.2146989307616, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16100 BFS = 1302.2146989307614, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 1326.8808647957592, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19564 BFS = 1313.6268123106781, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19600 BFS = 1305.2774243180245, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 19994 BFS = 1303.404123818299, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 41 39 38 35 36 37 44 43 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 94 50 96 54 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 42 40 72 71 93 67 0\n" +
                "0 61 81 106 90 0\n" +
                "Requests\n" +
                "0 5 3 1 2 29 4\n" +
                "36 46 39 30 52\n" +
                "6 8 25 7 9\n" +
                "10 34 14 11 12 47\n" +
                "45 43 51 41\n" +
                "23 22 20 21\n" +
                "17 15 19 16 18\n" +
                "49 26\n" +
                "13 44 35 28 40\n" +
                "48 27 50 33 32\n" +
                "24 38 37\n" +
                "42 31\n" +
                "Cost = 1303.404123818299\n" +
                "Num. vehicles = 12");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14 BFS = 761.3417108485291, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 79 BFS = 756.1437358918253, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1352 BFS = 755.4784611954102, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 770.2191115124219, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4095 BFS = 768.9015822155807, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4106 BFS = 764.4747703251023, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4700 BFS = 764.4747703251022, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 856.9112778478504, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4950 BFS = 845.4337830991435, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5035 BFS = 816.8522294332016, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 939.1421220384134, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5087 BFS = 883.391011311313, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 855.3155895419393, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 979.420378968266, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5255 BFS = 969.0327771186613, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5270 BFS = 922.8268075431855, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5281 BFS = 899.5690827035787, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5284 BFS = 892.7129707860493, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5300 BFS = 884.0838748095714, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5494 BFS = 871.5358018945212, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1006.302347914042, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5540 BFS = 987.1162342489316, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 1020.1408912271287, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5628 BFS = 1014.3471642739198, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 1044.2588138196647, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 1154.0868215442322, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5835 BFS = 1118.9698762869098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5838 BFS = 1095.787368094655, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5906 BFS = 1050.4199558003047, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1068.1669705724903, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1130.972271318823, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6196 BFS = 1067.432435146116, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1198.1118502603565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6261 BFS = 1113.590172353235, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6270 BFS = 1072.9905557496415, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6300 BFS = 1065.8078483693778, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1081.6645538964526, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1137.7721090241237, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6591 BFS = 1089.9380364162791, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1153.9282401864762, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1335.8732859307468, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6753 BFS = 1180.9415336950333, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1227.4981808726805, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1194.3022612800025, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6900 BFS = 1191.0032008158125, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7177 BFS = 1144.9375974959887, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1201.7157177245708, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7400 BFS = 1197.1888124278664, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7459 BFS = 1172.726912962849, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7614 BFS = 1146.0135843882515, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7800 BFS = 1141.7263392665568, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1205.3376881498507, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8002 BFS = 1145.3483096918367, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1378.1980949154777, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8173 BFS = 1312.612656116184, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8199 BFS = 1234.3727600753155, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8267 BFS = 1211.1104094491, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1392.6707595392422, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8423 BFS = 1361.66307918549, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8503 BFS = 1254.2882995916784, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1405.9075747429642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9015 BFS = 1306.373920936028, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9418 BFS = 1275.2526000401203, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1486.5212093165007, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 BFS = 1278.7148433533168, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1470.5236786900982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9679 BFS = 1377.4695453170186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9700 BFS = 1369.8219064805428, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1568.5839644005844, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9783 BFS = 1453.5242278682094, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9787 BFS = 1350.5000664737333, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10264 BFS = 1219.2090555113527, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 1502.8996435384336, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10755 BFS = 1423.4617525922404, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10893 BFS = 1416.0225130268054, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10895 BFS = 1371.437898809454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10900 BFS = 1341.2471397171864, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12698 BFS = 1299.6193879932307, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 1527.5941872094218, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12924 BFS = 1441.7508946648818, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 BFS = 1443.3410522914774, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13002 BFS = 1423.9012258746986, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13600 BFS = 1404.703147389023, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13617 BFS = 1372.1300552257237, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14604 BFS = 1360.1540985683248, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15022 BFS = 1318.24549060046, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15061 BFS = 1247.052284893565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 15990 BFS = 1229.0599386639294, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 1424.0831539708265, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16586 BFS = 1409.8268696707937, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16590 BFS = 1353.0343956876136, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17742 BFS = 1346.1847388239855, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 17997 BFS = 1321.0228965371198, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 18130 BFS = 1311.882252326759, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 18158 BFS = 1299.240025452931, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 61 90 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 72 71 67 27 26 30 32 93 0\n" +
                "0 94 81 106 54 0\n" +
                "0 33 101 28 31 29 34 50 96 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "Requests\n" +
                "31\n" +
                "45 51 43 41\n" +
                "16 37 15 38\n" +
                "49 42\n" +
                "26 17 18 19\n" +
                "0 1 29 5 4 2 3\n" +
                "8 6 9 25 7\n" +
                "50 27 33 48 32\n" +
                "23 21 20 24 22\n" +
                "40 28 13 35 44\n" +
                "34 11 12 47 10 14\n" +
                "36 39 52 30 46\n" +
                "Cost = 1299.240025452931\n" +
                "Num. vehicles = 12");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 100 BFS = 1266.4764495396876, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 200 BFS = 1213.2266709807475, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1338 BFS = 1200.1918740697672, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5183 BFS = 1195.936375652854, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1198.3868801950252, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 1357.6118015779894, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6008 BFS = 1313.0119678499975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6041 BFS = 1273.9842879803048, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6208 BFS = 1216.6438962873528, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1420.331306814355, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8172 BFS = 1314.5620130596392, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1362.3003046990932, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1483.2853968843183, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9800 BFS = 1400.1015597919861, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11673 BFS = 1388.7508825860932, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11680 BFS = 1369.9327082584878, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12307 BFS = 1364.6320303865837, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13325 BFS = 1363.2262941248846, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13500 BFS = 1363.2262941248844, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13600 BFS = 1301.0535749397143, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13900 BFS = 1299.7874485573318, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14029 BFS = 1276.9433697425147, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14100 BFS = 1230.1448446772672, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14500 BFS = 1230.144844677267, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 64 19 25 23 21 18 48 49 20 56 91 102 0\n" +
                "0 69 98 88 53 78 73 79 60 70 68 0\n" +
                "0 65 83 52 86 74 105 57 24 22 66 0\n" +
                "0 82 99 87 59 75 97 58 77 0\n" +
                "0 41 38 39 42 44 43 40 37 35 36 0\n" +
                "0 11 12 14 47 17 16 15 13 9 10 0\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 92 95 62 84 85 63 51 76 89 80 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 61 81 106 90 0\n" +
                "Requests\n" +
                "12 14 47 10 11 34\n" +
                "36 30 39 52 46\n" +
                "13 44 28 40 35\n" +
                "43 51 45 41\n" +
                "23 20 22 24 21\n" +
                "6 9 25 7 8\n" +
                "17 15 18 19 16\n" +
                "4 3 1 5 2 0 29\n" +
                "27 48 32 50 33\n" +
                "37 26 49 38\n" +
                "42 31\n" +
                "Cost = 1230.144844677267\n" +
                "Num. vehicles = 11");
    }

}
