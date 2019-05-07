package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_ALNS_TEST {

    int maxIterations = 10000;

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    static {
        pdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_lc103_test() throws IOException {
        String problem = "lc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 972.1290684955856, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1 BFS = 850.0302242304471, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 22 BFS = 827.8646991698506, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 100 BFS = 827.8646991698505, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 700 BFS = 827.8646991698504, feasible = true");
        assertThat(logs.get(6)).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 81 78 76 71 70 73 77 79 80 63 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "0 43 42 41 40 44 46 101 45 48 51 50 52 49 47 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 20 24 25 27 30 26 103 23 22 21 0\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 98 96 95 94 92 93 102 97 100 99 0\n" +
                "0 32 33 31 35 104 37 38 39 36 34 29 28 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 67 65 62 74 72 61 64 68 66 69 0\n" +
                "Requests\n" +
                "40 39 41 36 37\n" +
                "44 43 45 46 42\n" +
                "25 22 27 23 21 24 26\n" +
                "28 29 30 31\n" +
                "10 12 11 14 13\n" +
                "0 2 3 1 5 4\n" +
                "48 47 49 51 50\n" +
                "18 19 17 15 16 20\n" +
                "6 9 8 7\n" +
                "35 33 38 32 34\n" +
                "Cost = 827.8646991698504\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 1881.869285285301, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 2 BFS = 1794.2922150748643, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 11 BFS = 1703.2728255829325, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 100 BFS = 1678.8990495061576, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 118 BFS = 1638.9900063024345, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 249 BFS = 1579.876013258397, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 1399 BFS = 1574.9812503454202, feasible = true");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 2206 BFS = 1574.5150862397754, feasible = true");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 2572 BFS = 1558.0693464846545, feasible = true");
        assertThat(logs.get(10)).contains("Instance = lrc102.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 14 47 15 16 9 87 97 75 102 58 0\n" +
                "0 82 11 73 79 7 55 100 70 0\n" +
                "0 65 83 19 23 18 48 21 25 77 105 0\n" +
                "0 33 28 27 26 31 34 50 93 94 80 0\n" +
                "0 85 63 76 51 22 49 20 24 0\n" +
                "0 69 88 53 78 10 13 17 12 0\n" +
                "0 36 44 40 38 41 43 35 37 72 104 0\n" +
                "0 92 95 62 29 30 32 103 89 0\n" +
                "0 2 45 1 3 5 8 101 6 46 4 60 98 0\n" +
                "0 91 64 99 86 57 74 59 52 0\n" +
                "0 39 71 67 84 56 66 0\n" +
                "0 42 61 81 90 68 54 96 106 0\n" +
                "Requests\n" +
                "7 25 36 6 45\n" +
                "52 40 4 42\n" +
                "9 38 32 43 8\n" +
                "15 11 16 12 49\n" +
                "44 37 10 26\n" +
                "5 46 33 39\n" +
                "18 35 20 17 21\n" +
                "30 14 48 13\n" +
                "3 0 24 23 1 2\n" +
                "31 47 51 28\n" +
                "34 27 19\n" +
                "41 22 29 50\n" +
                "Cost = 1558.0693464846545\n" +
                "Num. vehicles = 12");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 4374.8388767824335, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1 BFS = 3708.5361392022273, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 2 BFS = 3346.1280630842816, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 3 BFS = 3224.5504974090472, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 4 BFS = 3143.983951142811, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 7 BFS = 3118.7362024878266, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 8 BFS = 3022.2733880309706, feasible = true");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 18 BFS = 2863.288361750899, feasible = true");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 24 BFS = 2798.993366090696, feasible = true");
        assertThat(logs.get(10)).isEqualTo("NEW BEST = Iter 29 BFS = 2704.567766330474, feasible = true");
        assertThat(logs.get(11)).isEqualTo("NEW BEST = Iter 100 BFS = 2704.5677663304737, feasible = true");
        assertThat(logs.get(12)).isEqualTo("NEW BEST = Iter 200 BFS = 2704.567766330473, feasible = true");
        assertThat(logs.get(13)).isEqualTo("NEW BEST = Iter 800 BFS = 2704.5677663304728, feasible = true");
        assertThat(logs.get(14)).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 113 155 78 175 13 43 2 90 67 39 107 212 0\n" +
                "0 101 144 119 166 35 126 71 9 1 99 53 201 0\n" +
                "0 133 48 26 152 40 153 169 89 105 15 59 198 0\n" +
                "0 60 211 82 180 84 191 125 4 72 17 0\n" +
                "0 190 5 10 193 46 128 106 167 207 34 95 158 0\n" +
                "0 57 118 83 143 176 36 206 33 121 165 188 108 0\n" +
                "0 20 41 85 80 31 25 172 77 110 162 0\n" +
                "0 114 159 38 150 22 151 16 140 204 187 142 111 63 56 0\n" +
                "0 45 178 27 173 154 209 24 61 100 64 179 109 0\n" +
                "0 170 134 50 156 112 168 79 205 29 87 42 123 0\n" +
                "0 177 3 88 8 186 127 98 157 137 183 0\n" +
                "0 93 55 135 58 202 184 199 37 81 138 0\n" +
                "0 73 116 12 129 11 6 122 139 0\n" +
                "0 32 171 65 86 115 94 51 174 136 189 0\n" +
                "0 164 210 66 147 160 47 91 70 0\n" +
                "0 161 104 18 54 185 132 7 181 117 49 0\n" +
                "0 21 23 182 75 163 194 145 195 52 92 0\n" +
                "0 148 103 197 203 124 141 69 200 0\n" +
                "0 62 131 44 102 146 208 68 76 0\n" +
                "0 30 120 19 192 196 97 14 96 130 28 74 149 0\n" +
                "Requests\n" +
                "27 84 8 45 61 60\n" +
                "42 33 5 77 57 89\n" +
                "25 82 54 31 70 10\n" +
                "37 48 100 50 1\n" +
                "102 22 90 6 99 2\n" +
                "21 35 23 66 49 76\n" +
                "51 17 47 26 13\n" +
                "75 73 86 62 11 15 81\n" +
                "83 94 29 39 18 96\n" +
                "32 92 85 91 46 71\n" +
                "53 95 0 4 72\n" +
                "105 24 55 36 34\n" +
                "43 7 3 64\n" +
                "52 40 20 63 93\n" +
                "79 88 41 30\n" +
                "97 12 87 59 69\n" +
                "16 103 14 44 98\n" +
                "67 74 104 80\n" +
                "58 78 38 28\n" +
                "56 101 65 9 19 68\n" +
                "Cost = 2704.5677663304728\n" +
                "Num. vehicles = 20");
    }

    @Test
    public void pdptw_lrc207_test() throws IOException {
        String problem = "lrc207.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 BFS = 1413.787015477243, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 10 BFS = 1330.355183746783, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 31 BFS = 1233.7561169091346, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 46 BFS = 1181.850739177697, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 207 BFS = 1109.9669600550933, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 210 BFS = 1083.9846208486392, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 515 BFS = 1065.03163760252, feasible = true");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 1018 BFS = 1062.0483117841093, feasible = true");
        assertThat(logs.get(9)).contains("Instance = lrc207.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 2 5 45 6 7 73 12 14 47 17 16 15 11 9 87 86 74 57 22 20 49 77 58 97 13 10 60 55 100 70 68 0\n" +
                "0 65 83 64 95 63 33 30 28 29 31 67 71 72 41 39 38 40 44 42 61 81 90 53 78 79 8 46 4 3 1 43 36 35 37 101 54 0\n" +
                "0 82 99 52 59 75 23 21 18 19 76 51 85 84 56 92 94 96 93 62 50 34 27 26 32 89 48 25 102 24 66 91 80 0\n" +
                "Requests\n" +
                "11 50 5 43 1 6 36 3 26 44 48 24 28 4 2 0 9\n" +
                "19 17 30 16 32 18 41 23 22 21 20 37 14 34 35 25 15 33\n" +
                "46 12 47 7 10 39 27 31 49 42 40 13 38 29 8 45\n" +
                "Cost = 1062.0483117841093\n" +
                "Num. vehicles = 3");
    }
}
