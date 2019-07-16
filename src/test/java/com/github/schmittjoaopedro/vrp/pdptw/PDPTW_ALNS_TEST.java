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
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 11, TC = 1570.13]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 11, TC = 1480.68]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 2 Sol = [F = true, NV = 11, TC = 1300.8]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 7 Sol = [F = true, NV = 11, TC = 1112.64]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 13 Sol = [F = true, NV = 11, TC = 866.22]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 30 Sol = [F = true, NV = 10, TC = 827.86]");
        assertThat(logs.get(7)).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 5 3 7 8 10 11 9 6 4 2 1 75 0\n" +
                "0 13 17 18 19 15 16 14 12 0\n" +
                "0 20 24 25 27 30 26 103 23 22 21 0\n" +
                "0 32 33 31 35 104 37 38 39 36 34 29 28 0\n" +
                "0 81 78 76 71 70 73 77 79 80 63 0\n" +
                "0 57 55 54 53 56 58 60 59 0\n" +
                "0 43 42 41 40 44 46 101 45 48 51 50 52 49 47 0\n" +
                "0 67 65 62 74 72 61 64 68 66 69 0\n" +
                "0 98 96 95 94 92 93 102 97 100 99 0\n" +
                "0 90 87 86 83 82 84 85 88 89 91 0\n" +
                "Requests\n" +
                "2 5 4 0 1 3\n" +
                "9 7 8 6\n" +
                "11 10 12 13 14\n" +
                "19 17 15 20 16 18\n" +
                "39 41 40 36 37\n" +
                "30 29 31 28\n" +
                "24 23 25 21 27 22 26\n" +
                "38 32 34 35 33\n" +
                "48 51 49 50 47\n" +
                "43 46 45 44 42\n" +
                "Cost = 827.8646991698506\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 20, TC = 1881.87]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 2 Sol = [F = true, NV = 18, TC = 1798.26]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 8 Sol = [F = true, NV = 16, TC = 1756.71]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 52 Sol = [F = true, NV = 14, TC = 1698.67]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 85 Sol = [F = true, NV = 14, TC = 1670.01]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 96 Sol = [F = true, NV = 13, TC = 1602.17]");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 97 Sol = [F = true, NV = 13, TC = 1599.78]");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 506 Sol = [F = true, NV = 12, TC = 1576.92]");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 584 Sol = [F = true, NV = 12, TC = 1558.07]");
        assertThat(logs.get(10)).contains("Instance = lrc102.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 85 63 76 51 22 49 20 24 0\n" +
                "0 65 83 19 23 21 18 48 25 77 105 0\n" +
                "0 39 71 67 84 56 66 0\n" +
                "0 92 95 62 29 30 32 103 89 0\n" +
                "0 2 45 1 3 5 8 101 6 46 4 60 98 0\n" +
                "0 42 61 81 90 68 54 96 106 0\n" +
                "0 82 11 73 79 7 55 100 70 0\n" +
                "0 33 28 27 26 31 34 50 93 94 80 0\n" +
                "0 91 64 99 86 57 74 59 52 0\n" +
                "0 36 44 40 38 41 43 35 37 72 104 0\n" +
                "0 14 47 15 16 9 87 97 75 102 58 0\n" +
                "0 69 88 53 78 10 13 17 12 0\n" +
                "Requests\n" +
                "10 26 37 44\n" +
                "8 32 43 38 9\n" +
                "34 19 27\n" +
                "13 14 30 48\n" +
                "1 24 23 2 3 0\n" +
                "41 22 29 50\n" +
                "40 42 52 4\n" +
                "12 11 16 15 49\n" +
                "31 47 28 51\n" +
                "18 17 21 20 35\n" +
                "7 45 36 25 6\n" +
                "33 39 5 46\n" +
                "Cost = 1558.0693464846543\n" +
                "Num. vehicles = 12");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 34, TC = 7956.73]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 34, TC = 7210.36]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 2 Sol = [F = true, NV = 34, TC = 6955.07]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 3 Sol = [F = true, NV = 34, TC = 6459.0]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 4 Sol = [F = true, NV = 34, TC = 4068.51]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 30, TC = 3824.12]");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 6 Sol = [F = true, NV = 26, TC = 3544.03]");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 8 Sol = [F = true, NV = 24, TC = 3278.02]");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 10 Sol = [F = true, NV = 23, TC = 3012.51]");
        assertThat(logs.get(10)).isEqualTo("NEW BEST = Iter 11 Sol = [F = true, NV = 22, TC = 2918.08]");
        assertThat(logs.get(11)).isEqualTo("NEW BEST = Iter 15 Sol = [F = true, NV = 21, TC = 2776.62]");
        assertThat(logs.get(12)).isEqualTo("NEW BEST = Iter 18 Sol = [F = true, NV = 20, TC = 2704.57]");
        assertThat(logs.get(13)).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 161 104 18 54 185 132 7 181 117 49 0\n" +
                "0 190 5 10 193 46 128 106 167 207 34 95 158 0\n" +
                "0 21 23 182 75 163 194 145 195 52 92 0\n" +
                "0 20 41 85 80 31 25 172 77 110 162 0\n" +
                "0 101 144 119 166 35 126 71 9 1 99 53 201 0\n" +
                "0 57 118 83 143 176 36 206 33 121 165 188 108 0\n" +
                "0 133 48 26 152 40 153 169 89 105 15 59 198 0\n" +
                "0 32 171 65 86 115 94 51 174 136 189 0\n" +
                "0 113 155 78 175 13 43 2 90 67 39 107 212 0\n" +
                "0 164 210 66 147 160 47 91 70 0\n" +
                "0 60 211 82 180 84 191 125 4 72 17 0\n" +
                "0 62 131 44 102 146 208 68 76 0\n" +
                "0 114 159 38 150 22 151 16 140 204 187 142 111 63 56 0\n" +
                "0 148 103 197 203 124 141 69 200 0\n" +
                "0 170 134 50 156 112 168 79 205 29 87 42 123 0\n" +
                "0 73 116 12 129 11 6 122 139 0\n" +
                "0 177 3 88 8 186 127 98 157 137 183 0\n" +
                "0 93 55 135 58 202 184 199 37 81 138 0\n" +
                "0 30 120 19 192 196 97 14 96 130 28 74 149 0\n" +
                "0 45 178 27 173 154 209 24 61 100 64 179 109 0\n" +
                "Requests\n" +
                "69 87 12 59 97\n" +
                "2 99 6 90 22 102\n" +
                "44 103 14 16 98\n" +
                "51 13 26 17 47\n" +
                "57 77 5 89 42 33\n" +
                "35 76 23 66 49 21\n" +
                "70 25 82 54 31 10\n" +
                "63 93 20 40 52\n" +
                "61 84 27 8 60 45\n" +
                "88 30 41 79\n" +
                "37 48 100 1 50\n" +
                "28 58 78 38\n" +
                "62 11 73 75 86 81 15\n" +
                "74 104 80 67\n" +
                "85 91 92 46 32 71\n" +
                "43 64 7 3\n" +
                "0 95 53 72 4\n" +
                "34 55 105 36 24\n" +
                "101 9 65 68 56 19\n" +
                "94 18 29 96 39 83\n" +
                "Cost = 2704.5677663304737\n" +
                "Num. vehicles = 20");
    }

    @Test
    public void pdptw_lrc207_test() throws IOException {
        String problem = "lrc207.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 5, TC = 1413.79]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 4, TC = 1319.58]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 8 Sol = [F = true, NV = 4, TC = 1215.48]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 24 Sol = [F = true, NV = 4, TC = 1176.88]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 34 Sol = [F = true, NV = 4, TC = 1109.97]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 55 Sol = [F = true, NV = 3, TC = 1083.98]");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 67 Sol = [F = true, NV = 3, TC = 1064.4]");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 2380 Sol = [F = true, NV = 3, TC = 1062.05]");
        assertThat(logs.get(9)).contains("Instance = lrc207.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 2 5 45 6 7 73 12 14 47 17 16 15 11 9 87 86 74 57 22 20 49 77 58 97 13 10 60 55 100 70 68 0\n" +
                "0 65 83 64 95 63 33 30 28 29 31 67 71 72 41 39 38 40 44 42 61 81 90 53 78 79 8 46 4 3 1 43 36 35 37 101 54 0\n" +
                "0 82 99 52 59 75 23 21 18 19 76 51 85 84 56 92 94 96 93 62 50 34 27 26 32 89 48 25 102 24 66 91 80 0\n" +
                "Requests\n" +
                "2 5 43 6 11 50 28 44 4 48 24 36 1 9 3 26 0\n" +
                "41 25 34 15 21 14 17 30 32 33 35 19 16 18 23 37 22 20\n" +
                "40 8 38 47 45 31 39 42 46 49 27 12 10 13 29 7\n" +
                "Cost = 1062.0483117841093\n" +
                "Num. vehicles = 3");
    }
}
