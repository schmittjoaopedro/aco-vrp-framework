package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SolverTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(SolverTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void initialSolutionTest() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations);
        solver.init();
        solver.printSolutionBest();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.totalCost).isEqualTo(5070.736543598931);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        // Check tours
        assertThat(StringUtils.join(solutionBest.tours.get(0), " ")).isEqualTo("0 149 81 35 196 60 152 104 207 62 88 99 138 90 204 39 208 37 96 108 97 45 52 59 105 0");
        assertThat(StringUtils.join(solutionBest.tours.get(1), " ")).isEqualTo("0 132 155 67 7 137 14 120 113 69 134 203 165 31 185 124 103 181 154 24 198 74 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(2), " ")).isEqualTo("0 55 41 157 188 4 89 106 125 151 56 33 141 191 43 129 70 164 190 0");
        assertThat(StringUtils.join(solutionBest.tours.get(3), " ")).isEqualTo("0 73 145 112 30 13 34 121 76 205 146 169 101 172 49 206 197 0");
        assertThat(StringUtils.join(solutionBest.tours.get(4), " ")).isEqualTo("0 63 150 36 44 68 3 117 25 201 116 80 109 180 27 75 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(5), " ")).isEqualTo("0 87 92 16 2 32 38 84 100 159 194 142 183 118 128 40 200 0");
        assertThat(StringUtils.join(solutionBest.tours.get(6), " ")).isEqualTo("0 18 50 66 131 5 182 175 9 65 29 179 202 0");
        assertThat(StringUtils.join(solutionBest.tours.get(7), " ")).isEqualTo("0 140 46 94 57 173 114 12 19 178 23 127 58 47 82 22 174 0");
        assertThat(StringUtils.join(solutionBest.tours.get(8), " ")).isEqualTo("0 147 184 54 85 193 144 186 167 28 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(9), " ")).isEqualTo("0 123 48 161 187 171 8 170 122 53 42 17 192 111 119 0");
        assertThat(StringUtils.join(solutionBest.tours.get(10), " ")).isEqualTo("0 1 143 26 176 51 168 139 21 126 156 78 148 0");
        assertThat(StringUtils.join(solutionBest.tours.get(11), " ")).isEqualTo("0 72 135 93 199 133 64 195 160 130 11 79 61 0");
        assertThat(StringUtils.join(solutionBest.tours.get(12), " ")).isEqualTo("0 15 166 71 153 95 6 77 83 0");
        assertThat(StringUtils.join(solutionBest.tours.get(13), " ")).isEqualTo("0 20 98 163 136 177 189 102 10 0");
        assertThat(StringUtils.join(solutionBest.tours.get(14), " ")).isEqualTo("0 158 86 0");
        assertThat(StringUtils.join(solutionBest.tours.get(15), " ")).isEqualTo("0 162 115 0");
        // Check requests
        assertThat(StringUtils.join(solutionBest.requestIds.get(0), " ")).isEqualTo("73 52 20 66 44 16 31 76 101 32 51 18");
        assertThat(StringUtils.join(solutionBest.requestIds.get(1), " ")).isEqualTo("64 50 37 83 77 63 55 35 92 4 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(2), " ")).isEqualTo("43 61 62 23 75 29 22 97 1");
        assertThat(StringUtils.join(solutionBest.requestIds.get(3), " ")).isEqualTo("59 54 71 40 14 39 88 26");
        assertThat(StringUtils.join(solutionBest.requestIds.get(4), " ")).isEqualTo("74 12 17 33 58 53 36 57");
        assertThat(StringUtils.join(solutionBest.requestIds.get(5), " ")).isEqualTo("94 99 15 19 41 21 42 45");
        assertThat(StringUtils.join(solutionBest.requestIds.get(6), " ")).isEqualTo("93 2 9 27 34 91");
        assertThat(StringUtils.join(solutionBest.requestIds.get(7), " ")).isEqualTo("11 30 68 89 56 6 24 46");
        assertThat(StringUtils.join(solutionBest.requestIds.get(8), " ")).isEqualTo("28 98 70 95 72");
        assertThat(StringUtils.join(solutionBest.requestIds.get(9), " ")).isEqualTo("80 25 8 87 86 60 96");
        assertThat(StringUtils.join(solutionBest.requestIds.get(10), " ")).isEqualTo("69 90 0 13 85 67");
        assertThat(StringUtils.join(solutionBest.requestIds.get(11), " ")).isEqualTo("38 103 65 100 79 5");
        assertThat(StringUtils.join(solutionBest.requestIds.get(12), " ")).isEqualTo("7 84 47 3");
        assertThat(StringUtils.join(solutionBest.requestIds.get(13), " ")).isEqualTo("10 49 82 48");
        assertThat(StringUtils.join(solutionBest.requestIds.get(14), " ")).isEqualTo("78");
        assertThat(StringUtils.join(solutionBest.requestIds.get(15), " ")).isEqualTo("81");
    }

}
