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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 243 BFS = 818.5225561349674, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29, 30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 308 BFS = 841.9026173566982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 847.9272537394698, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 389 BFS = 914.3394625122459, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 391 BFS = 869.4763446720772, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 397 BFS = 870.9673768248363, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39, 40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 462 BFS = 878.1116129145175, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 470 BFS = 880.9958133148442, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 543 BFS = 888.0456835236903, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 550 BFS = 886.8591288269137, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 892.6944110233818, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48, 49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 899.1279627568813, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50, 51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 688 BFS = 903.6939836199181, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 692 BFS = 902.5074289231414, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 904.3219818648649, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 29 30 28 26 23 103 22 21 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 5 3 7 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 32 33 31 35 37 38 39 36 105 34 0\n" +
                "0 43 42 41 40 44 46 45 48 51 101 50 52 49 47 0\n" +
                "0 67 62 61 64 102 68 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 81 78 104 76 71 70 73 77 79 80 0\n" +
                "0 65 63 74 72 66 69 0\n" +
                "0 8 10 0\n" +
                "Requests\n" +
                "6 9 7 8\n" +
                "10 15 13 11 14 12\n" +
                "31 30 29 28\n" +
                "1 2 4 0 5\n" +
                "51 52 50 48 49\n" +
                "16 19 18 17 20\n" +
                "22 21 23 24 26 27 25\n" +
                "37 34 32\n" +
                "47 43 46 45 44\n" +
                "42 40 38 39 41\n" +
                "35 33 36\n" +
                "3\n" +
                "Cost = 904.3219818648649\n" +
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
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 502 BFS = 699.3169244196433, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 710.3196994169332, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 818 BFS = 711.9703114242795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 712.6291075242407, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 842 BFS = 718.7463502928645, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 850 BFS = 721.7187605864591, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 874 BFS = 745.5409152582725, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 882 BFS = 767.4602648006297, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1190 BFS = 770.3891969887642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1206 BFS = 787.1979279547476, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1214 BFS = 792.9807674948394, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1222 BFS = 796.1961465742374, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1230 BFS = 803.9958054768765, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 874.53383262077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1538 BFS = 812.0051020790597, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30, 31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1554 BFS = 827.97819196565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32, 33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1578 BFS = 834.0028283484216, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1942 BFS = 835.6946442811975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1950 BFS = 838.8411790905211, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1958 BFS = 844.8411790905211, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1967 BFS = 847.7701112786556, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1983 BFS = 848.817881113803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2323 BFS = 849.2295699060769, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2331 BFS = 855.962117203484, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2347 BFS = 858.2617796097637, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2363 BFS = 858.8463176038108, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2711 BFS = 858.8463176038108, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2719 BFS = 864.7096331158803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2751 BFS = 864.7096331158803, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2759 BFS = 867.3151843913442, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2776 BFS = 870.5449153123483, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3075 BFS = 875.2211077329671, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3091 BFS = 876.9784670458478, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3447 BFS = 878.5900161191124, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3471 BFS = 881.1710410990006, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4102 BFS = 882.9855940407241, feasible = true");
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
                "10 12 14 15 11 13\n" +
                "1 0 3 5 2 4\n" +
                "51 49 48 50 52\n" +
                "22 21 23 26 27 24 25\n" +
                "47\n" +
                "37 35 33 32 34 36\n" +
                "16 17 18 20 19\n" +
                "42 40 39 38 41\n" +
                "46 45 43 44\n" +
                "Cost = 882.9855940407241\n" +
                "Num. vehicles = 11");
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
        assertThat(logs.get(i++)).isEqualTo("New requests add: [4]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 187 BFS = 253.31774816442095, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [5]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 315.24289054445, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [6]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 345.24289054445, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [7]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 413 BFS = 378.61170447237134, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [8]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 429 BFS = 453.02282278677944, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 507.6352427624057, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 518 BFS = 542.1181195184975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 612.2819157826781, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 729.4199201263009, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 777.2602293724186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 842.1435964217858, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [15, 16]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 850.5589041795124, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1004 BFS = 929.3787788167698, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1044 BFS = 1007.2341234430396, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 1047.056644202895, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1675 BFS = 1047.7154403028565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1683 BFS = 1053.83268307148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1708 BFS = 1110.480017112132, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1748 BFS = 1134.3021717839454, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1764 BFS = 1156.2215213263025, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 1159.150453514437, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2412 BFS = 1166.3474865288874, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2436 BFS = 1172.1303260689795, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2444 BFS = 1175.3457051483774, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 1198.2963141148723, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 1206.3056107170555, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 1215.6763753366033, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3156 BFS = 1221.4805346152352, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 1221.7010117193747, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3884 BFS = 1226.2294969240575, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 1248.7058630836518, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3941 BFS = 1251.6347952717863, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3973 BFS = 1254.2036683896188, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4653 BFS = 1275.650515623429, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 1287.6779463176754, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4701 BFS = 1289.977608723955, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4725 BFS = 1330.7899472432696, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5429 BFS = 1333.4768855880548, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 1339.340201100124, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 1339.3842514179587, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 1364.7775439047082, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 1368.007274825712, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6157 BFS = 1372.683467246331, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 1372.8720789454892, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 1374.4836280187537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6942 BFS = 1375.8533766046507, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8212 BFS = 1377.6679295463741, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 43 41 45 48 51 101 50 52 49 47 0\n" +
                "0 82 85 6 2 0\n" +
                "0 11 1 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 29 26 23 103 0\n" +
                "0 5 7 8 10 30 21 0\n" +
                "0 98 96 95 94 92 93 97 106 100 99 0\n" +
                "0 90 88 0\n" +
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
                "Requests\n" +
                "22 24 27 26 25\n" +
                "43 2\n" +
                "5\n" +
                "6 8 9 7\n" +
                "31 29 28 30\n" +
                "10 12 14 11\n" +
                "1 3 15\n" +
                "51 49 48 50 52\n" +
                "47\n" +
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
                "Cost = 1377.6679295463741\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1 BFS = 807.61921691635, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7 BFS = 752.7456724775496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 251 BFS = 824.8027687634658, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 316 BFS = 854.8027687634658, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 510 BFS = 893.642807088446, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 551 BFS = 963.8066033526267, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 615 BFS = 1066.6989802030275, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 624 BFS = 1061.6653344883491, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 939 BFS = 1108.692535847574, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 955 BFS = 1173.5759028969412, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 963 BFS = 1178.6092818298177, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 968 BFS = 1175.6564553073817, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1643 BFS = 1215.4789760672372, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2379 BFS = 1219.2986361797387, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2468 BFS = 1300.5617947189633, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3083 BFS = 1305.336394099582, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3115 BFS = 1318.2188502122892, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3164 BFS = 1338.6149282666604, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3908 BFS = 1343.3902574216997, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3924 BFS = 1390.4401330428202, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4669 BFS = 1397.1726803402278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5445 BFS = 1403.035995852297, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5502 BFS = 1443.1144092209372, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5526 BFS = 1485.3800730677417, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5551 BFS = 1488.609803988746, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6182 BFS = 1488.7984156879043, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6902 BFS = 1492.6785163290338, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lc101_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 9 4 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 42 40 44 46 45 48 50 52 0\n" +
                "0 54 53 58 60 36 105 0\n" +
                "0 67 62 61 64 102 68 0\n" +
                "0 90 86 88 91 0\n" +
                "0 78 104 35 38 39 34 0\n" +
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
                "47 45\n" +
                "40 18 20\n" +
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
                "Cost = 1492.6785163290338\n" +
                "Num. vehicles = 18");
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
                "0 1 3 2 5 4\n" +
                "6 8 9 7\n" +
                "10 12 15 13 14\n" +
                "16 17 18 20 19\n" +
                "21 22 24 23 27 26 25\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 625 BFS = 900.1230504261996, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23, 24, 25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 BFS = 1065.4410347056657, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 667 BFS = 1018.2979083156977, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28, 29, 30, 31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 750 BFS = 1190.1419565322565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 800 BFS = 1178.9169905905183, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34, 35, 36, 37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 834 BFS = 1317.1003046535948, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 835 BFS = 1309.814483683691, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38, 39, 40, 41, 42, 43, 44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 917 BFS = 1606.4601702903308, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 949 BFS = 1525.4344551219017, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45, 46, 47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1000 BFS = 1668.5337417507903, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1167 BFS = 1694.8309873015055, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1250 BFS = 1728.883955149616, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1500 BFS = 1725.2644486297677, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1502 BFS = 1722.8867968321483, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1584 BFS = 1744.7912520625387, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 1600 BFS = 1713.854083972073, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2200 BFS = 1694.1249393250089, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2669 BFS = 1691.625893835295, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3700 BFS = 1689.0044438344385, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1688.0224032647366, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4900 BFS = 1685.1876206465936, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 28 34 50 96 0\n" +
                "0 75 58 74 105 0\n" +
                "0 2 6 55 104 0\n" +
                "0 83 64 86 87 59 97 77 25 0\n" +
                "0 95 84 0\n" +
                "0 12 14 47 17 16 13 9 10 0\n" +
                "0 82 99 0\n" +
                "0 98 88 78 53 0\n" +
                "0 72 93 81 106 0\n" +
                "0 61 90 0\n" +
                "0 92 62 85 63 51 76 89 91 102 80 0\n" +
                "0 42 41 38 39 44 43 40 37 35 36 0\n" +
                "0 19 23 21 18 48 49 20 56 0\n" +
                "0 71 67 94 54 0\n" +
                "0 7 103 8 5 3 1 45 46 4 100 0\n" +
                "0 27 26 30 32 33 101 0\n" +
                "0 69 11 15 73 79 60 70 68 0\n" +
                "0 65 52 57 24 22 66 0\n" +
                "Requests\n" +
                "18 17 26\n" +
                "41 40\n" +
                "1 29\n" +
                "44 34 45 51\n" +
                "50\n" +
                "7 9 25 8\n" +
                "43\n" +
                "52 46\n" +
                "38 42\n" +
                "31\n" +
                "48 32 27 33 47\n" +
                "24 23 21 20 22\n" +
                "11 12 10 14\n" +
                "37 49\n" +
                "4 0 5 3 2\n" +
                "16 15 19\n" +
                "36 6 30 39\n" +
                "35 28 13\n" +
                "Cost = 1685.1876206465936\n" +
                "Num. vehicles = 18");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3167 BFS = 895.3473295803173, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18, 19, 20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3250 BFS = 985.0136481495406, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22, 23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3334 BFS = 1057.7101088384657, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24, 25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3417 BFS = 1093.8906442418513, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3438 BFS = 1068.7611023595653, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3500 BFS = 1067.9468538487615, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3667 BFS = 1085.3569432559443, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3744 BFS = 1083.4866623544353, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3834 BFS = 1221.7571060223408, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3862 BFS = 1206.3218475713738, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 3917 BFS = 1214.9784308969531, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4000 BFS = 1247.8384874465075, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4040 BFS = 1243.4851460521802, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 1307.9055726490385, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4090 BFS = 1302.216257395303, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4141 BFS = 1298.7298902934908, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1363.230391812177, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4167 BFS = 1331.7839637060667, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4250 BFS = 1434.976841424137, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4251 BFS = 1399.84817776553, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4317 BFS = 1364.398094312237, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4334 BFS = 1482.2102777388145, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4338 BFS = 1461.1986953189876, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4376 BFS = 1433.1173771312867, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1454.251050051326, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4500 BFS = 1451.7679895585077, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1519.199008413307, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4584 BFS = 1482.2013507824615, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4667 BFS = 1557.860331619121, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4677 BFS = 1537.6997333981863, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40, 41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4750 BFS = 1589.6286579172183, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4802 BFS = 1579.1985392784886, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4834 BFS = 1624.4189838304642, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4851 BFS = 1608.2066686936264, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4902 BFS = 1591.9129165910354, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4903 BFS = 1583.1743966546276, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 1719.344000599523, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 1791.295719799512, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 1783.4094661705021, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5195 BFS = 1764.337424726396, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1811.5085472357887, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5312 BFS = 1785.3253011902984, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1897.5961939360798, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 1861.1281338131719, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5345 BFS = 1825.9570525905565, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1882.3345936068772, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6121 BFS = 1863.2230885359218, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6123 BFS = 1827.6675330104636, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6417 BFS = 1918.996491399871, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6439 BFS = 1910.9385084712699, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1909.63249547518, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6571 BFS = 1878.7211462036848, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6792 BFS = 1874.0769399497221, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8250 BFS = 1874.4276484195047, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 27 28 26 30 32 34 0\n" +
                "0 75 87 59 97 58 77 74 105 0\n" +
                "0 14 12 17 16 11 15 13 10 0\n" +
                "0 95 84 33 101 0\n" +
                "0 2 5 8 7 103 6 4 45 3 100 0\n" +
                "0 83 86 0\n" +
                "0 21 23 49 51 56 91 102 80 0\n" +
                "0 82 99 69 73 79 60 70 68 0\n" +
                "0 72 93 81 106 0\n" +
                "0 98 78 88 53 0\n" +
                "0 92 62 85 63 89 76 0\n" +
                "0 52 19 20 22 24 66 0\n" +
                "0 38 44 42 39 41 43 40 37 35 36 0\n" +
                "0 61 90 0\n" +
                "0 65 47 9 57 0\n" +
                "0 94 54 0\n" +
                "0 1 46 55 104 0\n" +
                "0 71 50 67 96 0\n" +
                "0 64 25 18 48 0\n" +
                "Requests\n" +
                "18 16 17 15\n" +
                "41 45 40 51\n" +
                "8 7 9 6\n" +
                "50 19\n" +
                "1 3 5 4 2\n" +
                "44\n" +
                "12 14 27 47\n" +
                "43 36 39 30\n" +
                "38 42\n" +
                "52 46\n" +
                "32 48 33\n" +
                "28 11 13\n" +
                "21 24 22 23 20\n" +
                "31\n" +
                "35 25\n" +
                "49\n" +
                "0 29\n" +
                "37 26\n" +
                "34 10\n" +
                "Cost = 1874.4276484195047\n" +
                "Num. vehicles = 19");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5600 BFS = 849.3495336961442, feasible = true");
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
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1172.0544976061767, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1179.2831242339794, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1192.9234083192719, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1198.8582026406536, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7000 BFS = 1254.2338805236125, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7084 BFS = 1286.365632605186, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7250 BFS = 1331.7269279659101, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31, 32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1564.9461767535781, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7500 BFS = 1576.6649776647382, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34, 35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7584 BFS = 1687.8500444071487, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1759.5700656785907, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1783.6532548361754, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1790.0439002221099, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1848.5920992114436, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8584 BFS = 1882.304175569663, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1929.614699776203, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8750 BFS = 2040.4461952436018, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8834 BFS = 2133.2644730980496, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9167 BFS = 2133.449384933713, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9417 BFS = 2139.2343292892942, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 2147.243625891477, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11000 BFS = 2148.3062458435984, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11584 BFS = 2156.877072299048, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 11750 BFS = 2160.215265556921, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14917 BFS = 2162.367231250114, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_a_0.9.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 31 29 28 26 30 34 0\n" +
                "0 75 58 17 10 0\n" +
                "0 14 16 47 12 11 15 13 9 0\n" +
                "0 95 84 62 51 76 80 0\n" +
                "0 2 6 5 45 8 3 1 46 4 100 55 104 0\n" +
                "0 83 86 23 49 0\n" +
                "0 21 63 89 56 0\n" +
                "0 82 99 52 19 18 48 20 22 24 66 0\n" +
                "0 72 93 92 85 91 102 0\n" +
                "0 98 78 73 7 103 88 53 70 0\n" +
                "0 61 90 69 79 60 68 0\n" +
                "0 71 67 38 44 0\n" +
                "0 87 59 97 77 74 105 0\n" +
                "0 42 40 27 32 33 101 0\n" +
                "0 65 57 50 96 0\n" +
                "0 81 106 39 37 35 36 0\n" +
                "0 94 54 0\n" +
                "0 41 43 0\n" +
                "0 64 25 0\n" +
                "Requests\n" +
                "18 17 15\n" +
                "41 9\n" +
                "8 25 7 6\n" +
                "50 32 27\n" +
                "1 3 5 0 2 29\n" +
                "44 14\n" +
                "33 12\n" +
                "43 28 11 10 13\n" +
                "38 48 47\n" +
                "52 39 4 46\n" +
                "31 36 30\n" +
                "37 21\n" +
                "45 51 40\n" +
                "24 16 19\n" +
                "35 26\n" +
                "42 22 20\n" +
                "49\n" +
                "23\n" +
                "34\n" +
                "Cost = 2162.367231250114\n" +
                "Num. vehicles = 19");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5334 BFS = 508.9375788580121, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [9, 10]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 569.9267945558748, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [11]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 638.8289121903284, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [12]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 729.2990159947917, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5704 BFS = 728.5831501449331, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [13]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 739.0845268332537, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [14, 15, 16, 17]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 860.9692535561766, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [18]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 923.7983940322098, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [19]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 956.139038994752, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6212 BFS = 955.0169285019059, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [20]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6334 BFS = 1021.2779047252682, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6345 BFS = 1020.1557942324221, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [21, 22]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1065.9519418806292, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1115.849599319473, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1211.483384492747, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25, 26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1315.0668527440168, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6917 BFS = 1322.2954793718197, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1395.2811224504428, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7667 BFS = 1397.5493112618528, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7750 BFS = 1452.9249891448117, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1493.6546772668567, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8084 BFS = 1531.74025858822, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33, 34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1688.2748255925162, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8334 BFS = 1695.7948792838938, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1775.194161698278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8428 BFS = 1769.5715108609984, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1852.5661184382252, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8668 BFS = 1846.9434676009455, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1870.2844250029632, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9032 BFS = 1864.6617741656835, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9250 BFS = 1881.7996847190982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9334 BFS = 1945.9705345457116, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9370 BFS = 1940.347883708432, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41, 42]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 2002.175574639792, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9596 BFS = 1996.5529238025124, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 2117.990950770481, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 2131.661327447107, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9834 BFS = 2224.479605301555, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10250 BFS = 2225.0936817520883, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10500 BFS = 2230.8786261076693, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 2249.811386913563, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12167 BFS = 2250.874006865684, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 2277.054346753183, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51, 52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 2279.206312446376, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 12 13 75 58 74 105 0\n" +
                "0 65 57 23 49 22 66 0\n" +
                "0 31 29 28 26 30 34 0\n" +
                "0 7 103 2 6 88 53 60 68 0\n" +
                "0 14 16 47 9 11 15 17 10 0\n" +
                "0 95 84 19 18 48 20 0\n" +
                "0 83 86 87 59 0\n" +
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
                "0 97 77 0\n" +
                "0 41 39 37 43 35 36 0\n" +
                "0 64 25 0\n" +
                "Requests\n" +
                "7 41 40\n" +
                "35 14 13\n" +
                "18 17 15\n" +
                "4 1 46 30\n" +
                "8 25 6 9\n" +
                "50 11 10\n" +
                "44 45\n" +
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
                "51\n" +
                "23 22 20\n" +
                "34\n" +
                "Cost = 2279.206312446376\n" +
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 14 BFS = 796.943614757329, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [23]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4084 BFS = 806.5921784874473, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4184 BFS = 805.274649190606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [24]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4917 BFS = 920.633665993345, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4921 BFS = 918.8582920886327, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [25]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5084 BFS = 1004.2911291207145, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5100 BFS = 1002.5157552160022, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5131 BFS = 997.3177802592982, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [26]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5250 BFS = 1069.8606314205367, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5271 BFS = 1053.0140783078975, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [27, 28]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1145.8901999778545, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5505 BFS = 1129.0436468652154, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [29]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5584 BFS = 1211.1034265979783, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5662 BFS = 1197.945764499669, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [30]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5667 BFS = 1250.580159910278, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [31]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5834 BFS = 1266.770851852334, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [32]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6084 BFS = 1416.291382783115, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [33]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6167 BFS = 1418.028817461451, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [34]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6250 BFS = 1426.6628754954957, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [35]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6500 BFS = 1446.979321393928, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [36]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6584 BFS = 1495.7548683399257, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [37]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6667 BFS = 1577.2026601621696, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6670 BFS = 1562.2547415106735, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [38]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1616.954404753723, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6750 BFS = 1602.006486102227, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [39]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6834 BFS = 1659.380811624916, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6838 BFS = 1644.43289297342, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [40]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7334 BFS = 1690.3214877369057, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [41]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 7834 BFS = 1706.76314467967, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [42, 43]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1868.9203625212458, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [44]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8417 BFS = 1926.1878770396434, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [45]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9000 BFS = 1950.2656515274161, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [46]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9584 BFS = 1973.6698839778328, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9667 BFS = 2029.382845498942, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 2050.509537105547, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 10750 BFS = 2072.549286223469, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 12917 BFS = 2094.153458886655, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 13000 BFS = 2097.4916521445275, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 16584 BFS = 2097.665798463795, feasible = true");
        assertThat(logs.get(i++)).contains("Instance = lrc107_q_0_0.5.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 1 45 46 4 100 0\n" +
                "0 64 51 19 23 25 49 20 80 0\n" +
                "0 33 101 31 29 26 28 30 34 0\n" +
                "0 39 38 37 44 35 36 0\n" +
                "0 65 57 50 96 0\n" +
                "0 82 99 52 24 22 66 0\n" +
                "0 71 67 62 76 0\n" +
                "0 69 98 11 15 73 78 79 70 0\n" +
                "0 75 97 58 77 74 105 0\n" +
                "0 14 16 47 12 13 17 9 10 0\n" +
                "0 95 84 63 89 0\n" +
                "0 2 6 55 104 88 53 0\n" +
                "0 83 86 87 59 0\n" +
                "0 21 18 48 56 0\n" +
                "0 72 93 92 85 91 102 0\n" +
                "0 94 54 0\n" +
                "0 61 90 81 106 41 43 0\n" +
                "0 7 103 8 3 60 68 0\n" +
                "0 42 40 27 32 0\n" +
                "Requests\n" +
                "3 0 2\n" +
                "34 27 11 14\n" +
                "19 18 15 17\n" +
                "22 21 20\n" +
                "35 26\n" +
                "43 28 13\n" +
                "37 32\n" +
                "36 52 6 39\n" +
                "41 51 40\n" +
                "8 25 7 9\n" +
                "50 33\n" +
                "1 29 46\n" +
                "44 45\n" +
                "12 10\n" +
                "38 48 47\n" +
                "49\n" +
                "31 42 23\n" +
                "5 4 30\n" +
                "24 16\n" +
                "Cost = 2097.665798463795\n" +
                "Num. vehicles = 19");
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
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 238 BFS = 1302.1775457312463, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 2290 BFS = 1295.6928095338744, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 4866 BFS = 1293.1937640441606, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [47]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1426.5826498195906, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 5500 BFS = 1387.5757361623316, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [48]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6000 BFS = 1411.2717946123148, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 6762 BFS = 1406.5337370557254, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [49, 50]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8167 BFS = 1558.4369770239884, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [51]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 8667 BFS = 1620.5095114312105, feasible = true");
        assertThat(logs.get(i++)).isEqualTo("New requests add: [52]");
        assertThat(logs.get(i++)).isEqualTo("NEW BEST = Iter 9750 BFS = 1654.1424945997169, feasible = true");
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
                "0 82 99 75 97 58 77 0\n" +
                "0 95 84 51 27 32 80 0\n" +
                "0 98 11 15 73 78 60 70 68 0\n" +
                "0 83 86 87 59 0\n" +
                "0 61 81 106 90 0\n" +
                "0 2 6 55 104 69 79 0\n" +
                "0 42 40 88 53 0\n" +
                "Requests\n" +
                "4 5 3 2 0\n" +
                "7 8 25 9\n" +
                "34 11 14 12 10\n" +
                "35 28 40 13\n" +
                "19 18 15 17\n" +
                "23 22 21 20\n" +
                "38 37 49 26\n" +
                "48 32 33 47\n" +
                "43 41 51\n" +
                "50 27 16\n" +
                "52 6 39 30\n" +
                "44 45\n" +
                "31 42\n" +
                "1 29 36\n" +
                "24 46\n" +
                "Cost = 1654.1424945997169\n" +
                "Num. vehicles = 15");
    }
}
