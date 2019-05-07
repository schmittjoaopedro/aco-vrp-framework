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

public class MDPDPTW_ALNS_TEST {

    private static final String dpdptw100Directory;

    static {
        dpdptw100Directory = Paths.get(DPDPTW_ALNS_TEST.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
    }

    @Test
    public void dpdptw_lc101_a_0_1_test() throws IOException {
        String problem = "lc101_a_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 89 BFS = 673.4931990075114, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 98 BFS = 702.0678656902725, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17, 18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 162 BFS = 710.4945165662037, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 170 BFS = 756.6708765196997, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23, 24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 235 BFS = 774.6301369545977, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26, 27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 243 BFS = 791.4280144767266, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29, 30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 814.8080756984575, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 820.832712081229, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 BFS = 867.6042873158133, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 397 BFS = 869.0953194685724, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39, 40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 BFS = 914.7332615155135, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 466 BFS = 876.2395555582536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 879.1237559585803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 543 BFS = 884.9870714706498, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 892.0224641893022, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 585 BFS = 890.8223536671179, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 935.7496113578774, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 897.2559054006174, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50, 51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 688 BFS = 901.8354820890619, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 711 BFS = 900.6353715668776, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 902.449924508601, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 5 7 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 67 62 61 64 102 68 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 65 63 74 72 66 69 0\n" +
                "0 3 8 10 11 9 6 4 2 1 75 0\n" +
                "Requests\n" +
                "6 8 7 9\n" +
                "10 11 12 13 15 14\n" +
                "31 30 28 29\n" +
                "1\n" +
                "51 50 52 49 48\n" +
                "16 17 20 18 19\n" +
                "22 21 23 27 24 26 25\n" +
                "37 34 32\n" +
                "47 46 44 45 43\n" +
                "42 40 39 38 41\n" +
                "35 36 33\n" +
                "0 2 4 5 3\n" +
                "Cost = 902.449924508601\n" +
                "Num. vehicles = 12");
    }

    @Test
    public void dpdptw_lc101_a_0_5_test() throws IOException {
        String problem = "lc101_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 608.4463579270783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 BFS = 681.7450327341721, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 478 BFS = 630.392923139982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 BFS = 647.9648148254532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 658.9675898227431, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 660.6182018300894, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 661.2769979300507, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 667.3942406986745, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 BFS = 667.8290964379997, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 BFS = 691.6512511098133, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 BFS = 713.5706006521704, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 BFS = 716.4995328403049, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 BFS = 733.3082638062882, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 BFS = 739.0911033463802, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 BFS = 742.3064824257783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 BFS = 750.1061413284172, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 758.1154379306004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 BFS = 773.4862025501482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 779.5108389329196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 BFS = 781.2026548656956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 BFS = 784.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 BFS = 790.3491896750191, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 BFS = 793.2781218631536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 BFS = 794.7691540159127, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 BFS = 795.1808428081866, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 BFS = 801.9133901055939, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 BFS = 804.2130525118735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 BFS = 804.7975905059205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 BFS = 810.66090601799, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 BFS = 813.266457293454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 816.4961882144581, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 BFS = 821.1723806350769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 BFS = 822.9297399479576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 BFS = 824.5412890212222, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 BFS = 827.1223140011103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 BFS = 828.936866942834, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 67 65 63 62 74 72 61 64 102 68 66 69 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "Requests\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 14 15 11 13\n" +
                "1 0 3 5 4 2\n" +
                "51 49 48 50 52\n" +
                "22 21 23 24 26 27 25\n" +
                "47 46 45 43 44\n" +
                "37 35 33 32 34 36\n" +
                "16 17 18 20 19\n" +
                "42 40 39 38 41\n" +
                "Cost = 828.936866942834\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void dpdptw_lc101_a_0_9_test() throws IOException {
        String problem = "lc101_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 494 BFS = 486.5683202048671, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 603.7063245484899, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 651.5466337946076, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 858 BFS = 665.0778912497848, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 866 BFS = 673.4931990075114, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 907 BFS = 691.0650906929826, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 768.9204353192524, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1481 BFS = 808.7429560791079, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1513 BFS = 815.5189949476929, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 815.9538506870182, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 839.7760053588318, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1586 BFS = 861.6953549011888, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2145 BFS = 864.6242870893234, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2169 BFS = 871.8213201037738, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2177 BFS = 896.1234328922366, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2193 BFS = 901.9062724323284, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2201 BFS = 905.1216515117264, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2225 BFS = 912.9213104143653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 920.9306070165485, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2800 BFS = 936.3013716360963, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2840 BFS = 942.3260080188679, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3496 BFS = 944.017823951644, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3520 BFS = 947.1643587609674, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3528 BFS = 962.1327214601174, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3544 BFS = 965.0616536482521, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3577 BFS = 966.5526858010112, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4183 BFS = 966.9643745932851, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 BFS = 973.6969218906922, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4232 BFS = 975.9965842969718, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4256 BFS = 976.5811222910188, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4887 BFS = 977.761101744559, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 BFS = 983.6244172566285, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4952 BFS = 983.6244172566285, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4968 BFS = 986.2299685320925, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 989.4596994530964, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5534 BFS = 994.1358918737152, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5559 BFS = 995.8932511865961, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6206 BFS = 997.5048002598608, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6246 BFS = 998.8745488457573, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7395 BFS = 1000.6891017874808, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 5 7 8 10 11 9 6 4 2 1 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 43 41 62 68 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 67 61 64 102 66 69 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 65 72 0\n" +
                "0 3 75 0\n" +
                "0 42 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 63 74 0\n" +
                "Requests\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 14 15 13 11\n" +
                "1 3 5 4 2\n" +
                "51 49 48 50 52\n" +
                "22 32\n" +
                "47 46 45 43 44\n" +
                "37 34 36\n" +
                "16 17 18 20 19\n" +
                "42 40 39 38 41\n" +
                "35\n" +
                "0\n" +
                "21 23 24 27 26 25\n" +
                "33\n" +
                "Cost = 1000.6891017874808\n" +
                "Num. vehicles = 14");
    }

    @Test
    public void dpdptw_lc101_q_0_0_1_test() throws IOException {
        String problem = "lc101_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 185.21025295222879, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 182.86944611643477, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 187 BFS = 245.06771073655594, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 306.992853116585, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 336.992853116585, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 413 BFS = 363.94423806873226, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 429 BFS = 408.6433388591863, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 453.4766983965759, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 487.9595751526676, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 558.1233714168482, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 675.261375760471, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 723.1016850065887, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 787.985052055956, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 796.4003598136826, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 BFS = 875.22023445094, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 BFS = 953.0755790772098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 992.8980998370653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 BFS = 993.5568959370266, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 BFS = 999.6741387056502, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 BFS = 1056.321472746302, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 BFS = 1080.1436274181156, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 BFS = 1102.0629769604727, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 1104.9919091486072, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 BFS = 1112.1889421630576, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 BFS = 1117.9717817031496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 1121.1871607825476, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 1144.1377697490425, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 1152.1470663512257, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 1161.5178309707735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 BFS = 1167.3219902494054, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 1167.542467353545, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 BFS = 1172.0709525582279, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 1194.547318717822, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 BFS = 1197.4762509059567, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 BFS = 1200.0451240237892, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 BFS = 1221.4919712575995, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 1233.5194019518456, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 BFS = 1235.8190643581252, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 BFS = 1276.63140287744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 BFS = 1279.3183412222247, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 1285.1816567342944, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 1285.225707052129, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 1329.9402546938102, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 1333.169985614814, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 BFS = 1337.846178035433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 1338.0347897345912, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 1339.6463388078557, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 BFS = 1341.0160873937523, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 BFS = 1342.8306403354761, feasible = true");
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
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 837.0302395002793, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 806.7757065972233, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 878.8328028831395, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 955.5820222264131, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 331 BFS = 908.8328028831395, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 1010.7485663364864, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 511 BFS = 1003.8694131017335, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 524 BFS = 947.6728412081197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 1017.8366374723004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 1120.7290143227012, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 621 BFS = 1113.8498610879483, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 1160.8770624471733, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 1225.7604294965406, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 957 BFS = 1222.8076029741046, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 1227.8409819069811, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 1268.2849729601965, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1647 BFS = 1267.6635026668366, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 1272.1046330726974, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 1352.7463213185627, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 1357.5209206991815, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 1370.4033768118886, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 1390.7994548662598, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 1395.5747840212987, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 BFS = 1442.6246596424196, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 1449.3572069398272, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 1455.2205224518964, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 1495.298935820537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 1537.5645996673416, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 1540.7943305883455, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 1540.9829422875036, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 1544.8630429286331, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 9 4 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 42 40 44 46 45 48 50 52 0\n" +
                "0 54 53 58 60 36 105 0\n" +
                "0 67 62 61 64 102 68 0\n" +
                "0 90 88 0\n" +
                "0 78 104 35 38 39 34 0\n" +
                "0 86 91 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 25 27 28 22 0\n" +
                "0 33 37 30 21 0\n" +
                "0 57 55 56 59 0\n" +
                "0 20 24 29 26 23 103 0\n" +
                "0 43 41 11 1 0\n" +
                "0 32 31 51 101 49 47 0\n" +
                "0 81 76 71 70 73 77 79 80 0\n" +
                "0 65 72 66 69 0\n" +
                "0 87 83 82 84 85 89 0\n" +
                "0 63 74 6 2 0\n" +
                "Requests\n" +
                "1 0 3 4\n" +
                "6 8 9 7\n" +
                "21 23 24 26\n" +
                "29 28 19\n" +
                "37 32 34\n" +
                "47\n" +
                "40 18 20\n" +
                "45\n" +
                "51 49 48 50 52\n" +
                "12 13\n" +
                "17 15\n" +
                "31 30\n" +
                "10 14 11\n" +
                "22 5\n" +
                "16 27 25\n" +
                "42 39 38 41\n" +
                "35 36\n" +
                "46 43 44\n" +
                "33 2\n" +
                "Cost = 1544.8630429286331\n" +
                "Num. vehicles = 19");
    }

    @Test
    public void dpdptw_lc101_q_0_0_9_test() throws IOException {
        String problem = "lc101_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 10000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 824.2505754418187, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 827.1795076299532, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 853.1795076299532, feasible = true");
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
                "0 1 3 5 4 2\n" +
                "6 8 7 9\n" +
                "10 12 13 15 14\n" +
                "16 17 20 18 19\n" +
                "21 22 24 23 27 25 26\n" +
                "29 31 30 28\n" +
                "32 33 35 37 34 36\n" +
                "39 40 42 38 41\n" +
                "45 46 47 44 43\n" +
                "49 51 50 48 52\n" +
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
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 334 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2, 3, 4, 5, 6, 7, 8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 500 BFS = 588.2019280872435, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 584 BFS = 954.8455736295007, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 600 BFS = 911.0785253461142, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 608 BFS = 900.1230504261996, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 BFS = 1019.3625372379969, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 BFS = 1230.8779133554785, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 764 BFS = 1196.6847697616035, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 1358.7695023907484, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 849 BFS = 1329.3615138203918, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 900 BFS = 1300.4238838707965, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 BFS = 1489.6396164611137, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 965 BFS = 1444.7698320322813, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 BFS = 1569.183098322436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1050 BFS = 1567.593035705305, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1139 BFS = 1541.6659482507587, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 BFS = 1598.929369001609, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 BFS = 1677.6328834813169, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1258 BFS = 1671.1677296541754, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1259 BFS = 1656.6383698963612, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1300 BFS = 1647.5220996651165, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 1713.2983917716667, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 1686.2514121747442, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 1639.1065115089325, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1808 BFS = 1632.649744368765, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2556 BFS = 1632.183274958909, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3029 BFS = 1622.684379489631, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3137 BFS = 1609.0699787290469, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4618 BFS = 1609.0586869467024, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 27 28 26 30 32 34 33 101 0\n" +
                "0 75 97 58 77 74 105 0\n" +
                "0 2 6 7 103 8 5 3 1 45 46 4 100 55 104 0\n" +
                "0 83 86 87 59 11 15 0\n" +
                "0 95 84 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 82 99 0\n" +
                "0 98 69 78 73 79 60 70 68 0\n" +
                "0 72 93 81 106 0\n" +
                "0 61 90 0\n" +
                "0 19 23 21 18 48 49 20 56 0\n" +
                "0 42 41 38 39 44 43 40 37 35 36 0\n" +
                "0 92 85 50 96 0\n" +
                "0 88 53 0\n" +
                "0 71 67 94 54 0\n" +
                "0 64 65 52 57 25 24 22 66 0\n" +
                "0 62 63 51 76 89 91 102 80 0\n" +
                "Requests\n" +
                "18 17 15 19 16\n" +
                "41 40 51\n" +
                "1 4 0 29 2 5 3\n" +
                "44 45 6\n" +
                "50\n" +
                "7 9 25 8\n" +
                "43\n" +
                "52 36 39 30\n" +
                "38 42\n" +
                "31\n" +
                "11 14 12 10\n" +
                "24 23 20 22 21\n" +
                "48 26\n" +
                "46\n" +
                "37 49\n" +
                "34 35 28 13\n" +
                "32 33 47 27\n" +
                "Cost = 1609.0586869467024\n" +
                "Num. vehicles = 17");
    }

    @Test
    public void dpdptw_lrc107_a_0_5_test() throws IOException {
        String problem = "lrc107_a_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2000 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2500 BFS = 303.50717631738166, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2584 BFS = 359.2034743659811, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2667 BFS = 368.4235967981312, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6, 7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2750 BFS = 557.4761127912735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2834 BFS = 647.2303507458782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2917 BFS = 652.0347510349663, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10, 11, 12, 13, 14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3000 BFS = 817.5269012029345, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3084 BFS = 830.2084638498011, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 BFS = 967.016940684748, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3168 BFS = 877.6869094279031, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 BFS = 967.3532279971264, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 BFS = 1040.8687997064474, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 BFS = 1051.1055447167432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 1068.5156341239258, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 BFS = 1204.6799862854323, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 BFS = 1165.9136819077105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3906 BFS = 1149.8332189836365, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 BFS = 1153.776884593648, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 BFS = 1203.3716551159034, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4058 BFS = 1202.5250936459556, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 1264.8766918114802, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1397.7665203092404, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1388.5807105703695, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4169 BFS = 1361.7839411518712, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4173 BFS = 1286.570154924123, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4200 BFS = 1271.7917833530148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 BFS = 1308.4783877056082, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 BFS = 1388.4578004176396, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4446 BFS = 1374.0944789928258, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1386.2816494693664, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4519 BFS = 1377.0958397304955, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4541 BFS = 1366.7623182227971, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1406.8011394358823, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4600 BFS = 1382.9316966248982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 BFS = 1394.7415764028144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 1440.578311216278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4800 BFS = 1433.594144180764, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 BFS = 1570.4096972475716, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4864 BFS = 1517.4979783641372, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4873 BFS = 1511.8086631104015, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1578.4171456288989, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1550.8387349922493, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4973 BFS = 1545.0606443945996, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 1564.4851716939338, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1628.938145580344, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1730.098984042904, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1685.636706090464, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5336 BFS = 1678.2263845608927, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5370 BFS = 1663.5119334370477, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1698.996092756021, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1675.2382783956689, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6344 BFS = 1671.9839846489244, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6358 BFS = 1666.1424706547928, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 BFS = 1686.713099007336, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6419 BFS = 1672.3658711244227, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6435 BFS = 1668.7210200863012, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1675.7040643822954, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1647.2193485989712, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6607 BFS = 1645.5940678748166, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 BFS = 1651.6361306294625, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 28 27 26 34 30 32 33 101 0\n" +
                "0 75 97 58 77 74 105 0\n" +
                "0 14 12 47 17 16 13 9 11 15 10 0\n" +
                "0 95 84 64 18 48 25 0\n" +
                "0 2 5 8 7 103 45 3 1 46 6 4 100 0\n" +
                "0 83 86 87 59 0\n" +
                "0 21 23 19 49 20 56 0\n" +
                "0 82 99 65 52 57 24 22 66 0\n" +
                "0 72 93 94 54 0\n" +
                "0 61 81 106 90 0\n" +
                "0 92 62 85 63 89 76 91 102 0\n" +
                "0 98 78 73 60 55 104 70 68 0\n" +
                "0 42 40 39 41 38 44 43 37 35 36 0\n" +
                "0 69 79 88 53 0\n" +
                "0 51 80 0\n" +
                "0 71 67 50 96 0\n" +
                "Requests\n" +
                "18 17 16 15 19\n" +
                "41 51 40\n" +
                "8 7 25 9 6\n" +
                "50 34 10\n" +
                "1 3 5 4 0 2\n" +
                "44 45\n" +
                "12 14 11\n" +
                "43 35 28 13\n" +
                "38 49\n" +
                "31 42\n" +
                "32 48 33 47\n" +
                "52 39 30 29\n" +
                "24 22 23 21 20\n" +
                "36 46\n" +
                "27\n" +
                "37 26\n" +
                "Cost = 1651.6361306294625\n" +
                "Num. vehicles = 16");
    }

    @Test
    public void dpdptw_lrc107_a_0_9_test() throws IOException {
        String problem = "lrc107_a_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 104.49069638727337, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [1]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4417 BFS = 219.84971319001224, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [2]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 303.50717631738166, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [3, 4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 368.4235967981312, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5, 6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 488.57399515682, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 557.4761127912735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5167 BFS = 647.2303507458782, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 657.7317274341985, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 687.6468751361128, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11, 12, 13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5417 BFS = 762.6710106472323, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 824.3780406304192, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 861.6240616179431, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5620 BFS = 849.3495336961442, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 857.983591730189, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5750 BFS = 923.1224574607052, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 964.0132290839308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5917 BFS = 1009.3839121672136, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 1062.2492895567634, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1165.8327578080332, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1148.0608887215103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1154.2826285196538, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1161.5112551474565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1175.151539232749, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1181.0863335541308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 1260.9097880130323, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 BFS = 1293.0415400946058, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7236 BFS = 1291.3436850897456, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 BFS = 1329.4292664111092, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1410.4650570416625, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1431.8334581736199, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 BFS = 1543.0185249160304, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7606 BFS = 1523.9975383696742, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1595.717559641116, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1619.8007487987006, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1626.1913941846356, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1684.7395931739693, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 BFS = 1714.5755432269402, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1753.3112963311205, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 BFS = 1864.1427917985193, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 BFS = 1912.6529316102258, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 BFS = 1956.655208597087, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 BFS = 1970.6991943994963, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1978.7084910016795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 BFS = 1979.7711109538006, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 BFS = 1988.3419374092502, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 BFS = 1991.680130667123, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 BFS = 1994.0334523896943, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 28 26 30 34 33 101 91 102 0\n" +
                "0 75 58 97 77 74 105 0\n" +
                "0 14 16 47 12 11 15 13 9 0\n" +
                "0 95 84 62 51 76 80 0\n" +
                "0 2 6 5 45 8 3 1 46 4 100 55 104 0\n" +
                "0 83 86 87 59 17 10 0\n" +
                "0 21 63 89 56 0\n" +
                "0 82 99 52 19 20 24 0\n" +
                "0 72 93 71 50 67 96 0\n" +
                "0 98 78 73 7 103 88 53 70 0\n" +
                "0 61 90 69 79 60 68 0\n" +
                "0 92 85 18 48 0\n" +
                "0 38 44 42 40 27 32 0\n" +
                "0 65 57 94 54 0\n" +
                "0 81 106 39 37 35 36 0\n" +
                "0 41 43 0\n" +
                "0 64 22 23 25 49 66 0\n" +
                "Requests\n" +
                "18 17 15 19 47\n" +
                "41 51 40\n" +
                "8 25 7 6\n" +
                "50 32 27\n" +
                "1 3 5 0 2 29\n" +
                "44 45 9\n" +
                "33 12\n" +
                "43 28 11\n" +
                "38 37 26\n" +
                "52 39 4 46\n" +
                "31 36 30\n" +
                "48 10\n" +
                "21 24 16\n" +
                "35 49\n" +
                "42 22 20\n" +
                "23\n" +
                "34 14 13\n" +
                "Cost = 1994.0334523896943\n" +
                "Num. vehicles = 17");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_1_test() throws IOException {
        String problem = "lrc107_q_0_0.1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 215.6114377533283, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 320.10213414060166, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 360.3636952498931, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 444.02115837726257, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 499.717456425862, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 511.1407271904824, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5354 BFS = 508.9375788580121, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 572.1299428883451, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5509 BFS = 569.9267945558748, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 638.8289121903284, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 728.5831501449331, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 739.8003926831122, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5843 BFS = 739.0845268332537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 860.9692535561766, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 923.7983940322098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6109 BFS = 922.6762835393635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 955.0169285019059, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 BFS = 1021.2779047252682, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6412 BFS = 1020.1557942324221, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1065.9519418806292, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1115.849599319473, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6595 BFS = 1114.7274888266268, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1210.3612739999007, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1313.9447422511705, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1321.1733688789734, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1394.1590119575965, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 BFS = 1396.4272007690065, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1451.8028786519653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1492.5325667740103, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 BFS = 1530.6181480953737, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8124 BFS = 1524.995497258094, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1692.7753659369496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8200 BFS = 1687.1527150996699, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1700.2954196283272, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8394 BFS = 1694.6727687910475, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1774.0720512054318, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8443 BFS = 1768.449400368152, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1851.4440079453789, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8700 BFS = 1845.8213571080992, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1869.1623145101169, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9039 BFS = 1863.5396636728372, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 BFS = 1886.3002250635316, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9255 BFS = 1880.6775742262519, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 BFS = 1944.8484240528653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9356 BFS = 1939.2257732155856, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 2001.0534641469458, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9600 BFS = 1995.430813309666, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 2032.60061041161, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9727 BFS = 2026.9779595743303, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 2134.1023758558517, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 BFS = 2226.9206537102996, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 BFS = 2227.5347301608335, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 BFS = 2233.3196745164146, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 2252.252435322308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 BFS = 2253.315055274429, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 2279.4953951619277, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 2281.647360855121, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 12 13 87 59 74 105 0\n" +
                "0 65 57 23 49 22 66 0\n" +
                "0 31 29 28 26 30 34 0\n" +
                "0 7 103 2 6 88 53 60 68 0\n" +
                "0 14 16 47 9 11 15 17 10 0\n" +
                "0 95 84 19 18 48 20 0\n" +
                "0 83 86 75 58 0\n" +
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
                "0 97 77 0\n" +
                "0 64 25 0\n" +
                "Requests\n" +
                "7 45 40\n" +
                "35 14 13\n" +
                "18 17 15\n" +
                "4 1 46 30\n" +
                "8 25 6 9\n" +
                "50 11 10\n" +
                "44 41\n" +
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
                "51\n" +
                "34\n" +
                "Cost = 2281.647360855121\n" +
                "Num. vehicles = 21");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_5_test() throws IOException {
        String problem = "lrc107_q_0_0.5.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 802.3312991376806, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10 BFS = 801.6660244412656, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 83 BFS = 798.2611440541702, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 189 BFS = 796.943614757329, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 805.274649190606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 920.633665993345, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5000 BFS = 913.6603171319288, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 977.8837176182369, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5131 BFS = 976.5661883213957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1032.262486369995, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1143.655909874669, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5570 BFS = 1123.3674878445834, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 1157.935441460973, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 1210.5698368715819, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 1232.9897645430144, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1294.6967945262013, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1381.2142366077321, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6176 BFS = 1373.1390556192523, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1389.848294641777, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1410.1647405402096, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1482.7585390773772, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1525.9347941350848, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6668 BFS = 1516.062492655447, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1565.6865387266382, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6753 BFS = 1555.8142372470004, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1608.1129455978312, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6874 BFS = 1598.2406441181934, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1654.001540361317, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1675.831404184733, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1834.1852651969007, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1873.3642709455476, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1874.2493362570735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1897.6535687074902, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 1953.3665302285995, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1974.4932218352046, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 1996.532970953126, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10755 BFS = 1993.425982641098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 2015.0301553042837, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 BFS = 2018.3683485621564, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 2019.077457310223, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 1 45 46 4 100 0\n" +
                "0 64 51 21 18 48 25 56 80 0\n" +
                "0 33 101 31 29 26 28 30 34 0\n" +
                "0 39 38 37 44 0\n" +
                "0 65 57 87 59 0\n" +
                "0 82 99 52 83 86 24 0\n" +
                "0 71 67 94 50 96 54 0\n" +
                "0 69 98 11 15 73 78 79 70 0\n" +
                "0 75 97 58 77 74 105 0\n" +
                "0 95 84 63 89 91 102 0\n" +
                "0 2 6 55 104 88 53 0\n" +
                "0 14 16 47 12 13 17 9 10 0\n" +
                "0 72 93 27 32 0\n" +
                "0 22 19 20 23 49 66 0\n" +
                "0 92 85 62 76 0\n" +
                "0 61 90 0\n" +
                "0 7 103 8 3 60 68 0\n" +
                "0 42 40 81 106 41 43 35 36 0\n" +
                "Requests\n" +
                "3 0 2\n" +
                "34 27 12 10\n" +
                "19 18 15 17\n" +
                "22 21\n" +
                "35 45\n" +
                "43 28 44\n" +
                "37 49 26\n" +
                "36 6 52 39\n" +
                "41 51 40\n" +
                "50 33 47\n" +
                "1 29 46\n" +
                "8 25 7 9\n" +
                "38 16\n" +
                "11 13 14\n" +
                "48 32\n" +
                "31\n" +
                "5 4 30\n" +
                "24 42 23 20\n" +
                "Cost = 2019.077457310223\n" +
                "Num. vehicles = 18");
    }

    @Test
    public void dpdptw_lrc107_q_0_0_9_test() throws IOException {
        String problem = "lrc107_q_0_0.9.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, 20000, new Random(1));
        alns.enableMovingVehicle();
        alns.execute();
        List<String> logs = alns.getLog();
        int i = 0;
        assertThat(logs.get(i++)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 1337.8058416891342, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12 BFS = 1306.7601453694454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 405 BFS = 1302.1775457312463, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 406 BFS = 1295.6928095338744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3142 BFS = 1293.1937640441606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1353.228659414796, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5742 BFS = 1350.7477164517793, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5747 BFS = 1348.2486709620653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 1433.3571601373433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6119 BFS = 1414.105141246453, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1597.1213145055538, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1566.008381214716, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1637.7812700288553, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1668.6551538943972, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 7 103 8 5 3 1 45 46 4 100 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 64 19 25 23 21 18 48 49 20 56 0\n" +
                "0 65 52 74 105 57 24 22 66 0\n" +
                "0 33 101 31 29 26 28 30 34 0\n" +
                "0 41 39 38 35 36 37 44 43 0\n" +
                "0 72 71 93 94 67 50 96 54 0\n" +
                "0 92 62 85 63 89 76 91 102 0\n" +
                "0 82 99 2 6 55 104 0\n" +
                "0 95 84 51 27 32 80 0\n" +
                "0 98 11 15 73 78 60 70 68 0\n" +
                "0 83 86 88 53 0\n" +
                "0 61 81 106 90 0\n" +
                "0 87 59 75 97 58 77 0\n" +
                "0 42 40 69 79 0\n" +
                "Requests\n" +
                "4 5 3 0 2\n" +
                "7 8 25 9\n" +
                "34 11 14 12 10\n" +
                "35 28 40 13\n" +
                "19 18 15 17\n" +
                "23 22 21 20\n" +
                "38 37 49 26\n" +
                "48 32 33 47\n" +
                "43 1 29\n" +
                "50 27 16\n" +
                "52 6 39 30\n" +
                "44 46\n" +
                "31 42\n" +
                "45 41 51\n" +
                "24 36\n" +
                "Cost = 1668.6551538943972\n" +
                "Num. vehicles = 15");
    }
}
