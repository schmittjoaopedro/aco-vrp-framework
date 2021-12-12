package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_ALNS_TEST {

    int maxIterations = 10000;

    private static String pdptw100Directory;

    private static String pdptw200Directory;

    static {
        try {
            pdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").toURI()).toString();
            pdptw200Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_200").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pdptw_lc103_test() throws IOException {
        String problem = "lc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.setShowLog(true);
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
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 20, TC = 1881.87]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 19, TC = 1846.04]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 3 Sol = [F = true, NV = 16, TC = 1767.19]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 5 Sol = [F = true, NV = 15, TC = 1739.09]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 6 Sol = [F = true, NV = 15, TC = 1729.8]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 12 Sol = [F = true, NV = 15, TC = 1662.58]");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 21 Sol = [F = true, NV = 14, TC = 1658.47]");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 61 Sol = [F = true, NV = 13, TC = 1646.77]");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 73 Sol = [F = true, NV = 13, TC = 1644.3]");
        assertThat(logs.get(10)).isEqualTo("NEW BEST = Iter 119 Sol = [F = true, NV = 13, TC = 1631.76]");
        assertThat(logs.get(11)).isEqualTo("NEW BEST = Iter 203 Sol = [F = true, NV = 13, TC = 1585.64]");
        assertThat(logs.get(12)).isEqualTo("NEW BEST = Iter 204 Sol = [F = true, NV = 12, TC = 1576.92]");
        assertThat(logs.get(13)).isEqualTo("NEW BEST = Iter 507 Sol = [F = true, NV = 12, TC = 1558.07]");
        assertThat(logs.get(14)).contains("Instance = lrc102.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 2 45 1 3 5 8 101 6 46 4 60 98 0\n" +
                "0 42 61 81 90 68 54 96 106 0\n" +
                "0 65 83 19 23 48 18 21 25 77 105 0\n" +
                "0 91 64 99 86 57 74 59 52 0\n" +
                "0 39 71 67 84 56 66 0\n" +
                "0 36 44 40 38 41 43 35 37 72 104 0\n" +
                "0 33 28 27 26 31 34 50 93 94 80 0\n" +
                "0 14 47 15 16 9 87 97 75 102 58 0\n" +
                "0 92 95 62 29 30 32 103 89 0\n" +
                "0 82 11 73 79 7 55 100 70 0\n" +
                "0 69 88 53 78 10 13 17 12 0\n" +
                "0 85 63 76 51 22 49 20 24 0\n" +
                "Requests\n" +
                "0 3 24 23 1 2\n" +
                "41 50 22 29\n" +
                "9 43 38 8 32\n" +
                "31 47 28 51\n" +
                "34 19 27\n" +
                "20 18 21 17 35\n" +
                "15 16 11 12 49\n" +
                "7 25 45 36 6\n" +
                "30 48 14 13\n" +
                "4 40 42 52\n" +
                "46 39 33 5\n" +
                "10 44 26 37\n" +
                "Cost = 1558.0693464846543\n" +
                "Num. vehicles = 12");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.setShowLog(true);
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
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 7 Sol = [F = true, NV = 25, TC = 3357.96]");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 8 Sol = [F = true, NV = 24, TC = 3239.42]");
        assertThat(logs.get(10)).isEqualTo("NEW BEST = Iter 9 Sol = [F = true, NV = 22, TC = 3031.46]");
        assertThat(logs.get(11)).isEqualTo("NEW BEST = Iter 18 Sol = [F = true, NV = 21, TC = 2938.74]");
        assertThat(logs.get(12)).isEqualTo("NEW BEST = Iter 23 Sol = [F = true, NV = 21, TC = 2863.29]");
        assertThat(logs.get(13)).isEqualTo("NEW BEST = Iter 26 Sol = [F = true, NV = 21, TC = 2798.99]");
        assertThat(logs.get(14)).isEqualTo("NEW BEST = Iter 27 Sol = [F = true, NV = 20, TC = 2704.57]");
        assertThat(logs.get(15)).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 93 55 135 58 202 184 199 37 81 138 0\n" +
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
                "0 161 104 18 54 185 132 7 181 117 49 0\n" +
                "0 62 131 44 102 146 208 68 76 0\n" +
                "0 114 159 38 150 22 151 16 140 204 187 142 111 63 56 0\n" +
                "0 148 103 197 203 124 141 69 200 0\n" +
                "0 170 134 50 156 112 168 79 205 29 87 42 123 0\n" +
                "0 177 3 88 8 186 127 98 157 137 183 0\n" +
                "0 73 116 12 129 11 6 122 139 0\n" +
                "0 30 120 19 192 196 97 14 96 130 28 74 149 0\n" +
                "0 45 178 27 173 154 209 24 61 100 64 179 109 0\n" +
                "Requests\n" +
                "55 105 34 24 36\n" +
                "2 6 99 90 102 22\n" +
                "14 44 98 103 16\n" +
                "17 26 13 51 47\n" +
                "42 57 77 33 5 89\n" +
                "35 49 23 66 76 21\n" +
                "25 31 70 54 82 10\n" +
                "20 52 63 40 93\n" +
                "8 27 45 84 61 60\n" +
                "41 79 88 30\n" +
                "48 37 100 1 50\n" +
                "97 69 12 87 59\n" +
                "28 38 78 58\n" +
                "86 73 62 75 11 15 81\n" +
                "67 74 80 104\n" +
                "46 85 92 71 91 32\n" +
                "53 95 4 0 72\n" +
                "7 3 43 64\n" +
                "56 65 19 101 68 9\n" +
                "29 94 96 83 39 18\n" +
                "Cost = 2704.5677663304737\n" +
                "Num. vehicles = 20");
    }

    @Test
    public void pdptw_lrc207_test() throws IOException {
        String problem = "lrc207.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.setShowLog(true);
        alns.run();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("New requests add: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50]");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 1 Sol = [F = true, NV = 5, TC = 1413.79]");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 2 Sol = [F = true, NV = 4, TC = 1308.69]");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 38 Sol = [F = true, NV = 4, TC = 1124.66]");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 42 Sol = [F = true, NV = 3, TC = 1092.15]");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 78 Sol = [F = true, NV = 3, TC = 1082.68]");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 100 Sol = [F = true, NV = 3, TC = 1065.08]");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 153 Sol = [F = true, NV = 3, TC = 1064.4]");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 2067 Sol = [F = true, NV = 3, TC = 1062.05]");
        assertThat(logs.get(9)).contains("Instance = lrc207.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0 69 98 88 2 45 5 6 7 73 12 14 47 17 16 15 11 9 87 86 74 57 22 20 49 77 58 97 13 10 60 55 100 70 68 0\n" +
                "0 65 83 64 95 63 33 30 28 29 31 67 71 72 41 39 38 40 44 42 61 81 90 53 78 79 8 46 4 3 1 43 36 35 37 101 54 0\n" +
                "0 82 99 52 59 75 23 21 18 19 76 51 85 84 56 92 94 96 93 62 50 34 27 26 32 89 48 25 102 24 66 91 80 0\n" +
                "Requests\n" +
                "26 5 3 44 6 50 4 43 11 0 24 2 48 1 28 36 9\n" +
                "37 41 16 17 22 23 20 33 32 18 34 35 21 25 19 15 14 30\n" +
                "10 40 12 31 27 13 38 39 42 47 49 46 45 7 8 29\n" +
                "Cost = 1062.0483117841093\n" +
                "Num. vehicles = 3");
    }
}
