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

public class StaticTcMinimizerLsTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeCost_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.enableLocalSearch();
        solver.init();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3758.460096900253);
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        // Check tours
        assertThat(StringUtils.join(solutionBest.tours.get(0), " ")).isEqualTo("0 196 152 30 34 88 99 138 90 204 39 208 37 65 29 96 108 97 45 0");
        assertThat(StringUtils.join(solutionBest.tours.get(1), " ")).isEqualTo("0 149 81 113 69 134 203 165 31 185 124 183 118 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(2), " ")).isEqualTo("0 7 55 41 157 89 106 125 151 56 141 191 43 129 70 164 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(3), " ")).isEqualTo("0 35 145 112 121 181 146 169 103 101 154 105 24 0");
        assertThat(StringUtils.join(solutionBest.tours.get(4), " ")).isEqualTo("0 67 150 137 36 44 68 116 25 201 180 27 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(5), " ")).isEqualTo("0 60 32 38 84 104 207 100 159 62 194 142 128 52 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(6), " ")).isEqualTo("0 155 14 63 3 5 117 80 182 175 9 0");
        assertThat(StringUtils.join(solutionBest.tours.get(7), " ")).isEqualTo("0 140 46 94 57 173 114 12 19 178 23 127 58 47 82 22 174 0");
        assertThat(StringUtils.join(solutionBest.tours.get(8), " ")).isEqualTo("0 4 54 85 144 186 179 202 190 0");
        assertThat(StringUtils.join(solutionBest.tours.get(9), " ")).isEqualTo("0 92 168 139 187 162 171 16 115 170 122 53 42 78 148 0");
        assertThat(StringUtils.join(solutionBest.tours.get(10), " ")).isEqualTo("0 1 143 26 176 51 123 21 126 156 172 197 119 0");
        assertThat(StringUtils.join(solutionBest.tours.get(11), " ")).isEqualTo("0 72 147 135 93 199 109 133 64 195 49 206 61 28 75 0");
        assertThat(StringUtils.join(solutionBest.tours.get(12), " ")).isEqualTo("0 188 15 184 166 193 71 153 167 33 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(13), " ")).isEqualTo("0 73 13 20 98 163 136 95 177 6 189 102 10 83 77 0");
        assertThat(StringUtils.join(solutionBest.tours.get(14), " ")).isEqualTo("0 132 18 120 158 50 66 131 86 160 130 11 79 0");
        assertThat(StringUtils.join(solutionBest.tours.get(15), " ")).isEqualTo("0 87 48 161 8 76 205 2 17 40 200 192 111 0");
        // Check requests
        assertThat(StringUtils.join(solutionBest.requestIds.get(0), " ")).isEqualTo("52 20 66 44 76 101 18 34 14");
        assertThat(StringUtils.join(solutionBest.requestIds.get(1), " ")).isEqualTo("64 37 83 55 94 73 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(2), " ")).isEqualTo("43 61 62 23 75 29 22 4");
        assertThat(StringUtils.join(solutionBest.requestIds.get(3), " ")).isEqualTo("59 54 71 50 92 16");
        assertThat(StringUtils.join(solutionBest.requestIds.get(4), " ")).isEqualTo("74 17 36 57 35 12");
        assertThat(StringUtils.join(solutionBest.requestIds.get(5), " ")).isEqualTo("99 15 19 41 32 51 31");
        assertThat(StringUtils.join(solutionBest.requestIds.get(6), " ")).isEqualTo("93 2 33 58 77");
        assertThat(StringUtils.join(solutionBest.requestIds.get(7), " ")).isEqualTo("11 30 68 89 56 6 24 46");
        assertThat(StringUtils.join(solutionBest.requestIds.get(8), " ")).isEqualTo("28 1 91 70");
        assertThat(StringUtils.join(solutionBest.requestIds.get(9), " ")).isEqualTo("87 86 96 45 81 67 85");
        assertThat(StringUtils.join(solutionBest.requestIds.get(10), " ")).isEqualTo("69 90 0 13 88 60");
        assertThat(StringUtils.join(solutionBest.requestIds.get(11), " ")).isEqualTo("38 103 65 100 26 53 72");
        assertThat(StringUtils.join(solutionBest.requestIds.get(12), " ")).isEqualTo("7 84 95 97 98");
        assertThat(StringUtils.join(solutionBest.requestIds.get(13), " ")).isEqualTo("10 49 82 48 3 39 47");
        assertThat(StringUtils.join(solutionBest.requestIds.get(14), " ")).isEqualTo("78 5 9 27 79 63");
        assertThat(StringUtils.join(solutionBest.requestIds.get(15), " ")).isEqualTo("8 21 25 40 80 42");
    }

    @Test
    public void minimizeCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 19, TC = 3299.6]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3220.52]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3216.6]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3206.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3138.62]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 2950.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2830.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2815.8]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2815.26]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2782.7]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2777.57]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2775.34]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2774.58]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2772.18]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.1821743258984);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 101 144 119 166 35 126 71 9 1 99 53 17 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 170 134 50 156 112 168 79 29 87 42 123 133 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 60 82 180 84 191 125 4 72 193 46 128 90 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 116 164 66 160 47 205 91 70 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 21 23 182 75 163 194 145 195 52 92 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 10 5 106 167 43 13 2 175 203 155 39 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 177 3 88 8 186 127 98 157 199 183 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 20 41 85 80 31 25 172 77 110 179 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 113 73 129 11 6 122 139 34 95 158 190 67 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 62 120 44 192 196 97 14 96 130 28 19 30 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 32 171 65 86 115 94 51 174 136 189 147 12 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 93 55 135 58 184 37 138 146 102 131 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 45 178 173 154 24 61 100 64 27 149 68 76 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 48 26 152 40 153 169 89 105 15 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 114 159 38 204 150 22 151 16 140 187 142 111 63 56 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 161 104 18 54 185 132 7 181 117 49 81 137 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 57 118 83 143 176 36 33 121 165 188 202 108 78 201 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 148 103 206 197 124 141 69 200 162 109 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("49 60 73 48 18 0");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("27 40 54 23 86 67");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("98 63 41 2 93 31");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("25 35 82 58");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("74 12 94 100 37 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("53 7 3 20 89 85");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("64 91 6 1 96");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("16 22 11 43 38");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("8 4 46 80 55 36");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("47 99 61 9 65 33");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("44 28 34 17 87 57");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("68 45 69 30 75");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("24 14 79 32 88 92");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("15 78 21 26 52");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("13 77 70 56 19 72 81");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("66 83 10 95 5 51");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("84 59 90 42 39 29 97");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("50 101 62 71 76");
    }

    @Test
    public void minimizeCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 3758.46]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3716.34]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3660.65]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3637.22]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3612.33]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3582.54]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3576.72]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3575.19]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3568.04]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3567.48]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3554.86]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3547.72]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3544.11]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3543.34]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3539.33]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3534.05]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3516.86]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3514.74]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3495.03]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3489.34]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3487.28]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3480.86]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3457.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3457.4]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3454.07]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3450.68]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3448.75]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3447.35]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3445.71]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3445.2]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3437.59]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3436.81]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3434.76]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3434.54]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3433.52]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 13, TC = 3433.01]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3433.0148897439076);
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 196 60 99 152 32 38 84 104 207 100 159 194 142 128 88 40 200 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 92 123 168 139 187 162 115 16 171 170 122 76 205 17 192 53 42 78 148 119 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 132 18 120 158 50 66 131 86 160 130 11 79 172 197 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 46 54 85 94 12 37 22 58 47 82 96 65 29 174 108 97 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 87 145 35 112 48 161 8 2 146 169 181 121 183 118 111 101 105 24 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 155 14 63 5 3 117 25 201 80 182 175 9 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 149 81 1 143 26 176 51 21 113 165 69 134 203 103 124 185 31 126 156 154 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 4 188 15 184 166 153 71 193 33 144 186 179 202 190 167 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 140 57 173 95 178 19 114 39 208 138 90 204 23 127 77 198 74 45 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 67 150 137 36 44 68 116 180 195 27 49 206 61 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 7 55 41 157 89 106 125 151 56 141 191 43 129 70 164 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 73 13 20 30 34 62 98 163 136 177 6 189 102 10 83 52 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 72 147 135 93 199 109 133 64 28 75 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("76 31 19 41 99 51 15 101 21");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("67 87 45 85 60 86 81 40 8 96");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("63 88 78 5 79 9 27");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("11 24 46 18 6 28 34 52");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("16 71 92 42 54 25 59 94 80");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("12 93 33 2 77 58");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("50 83 90 64 0 37 13 69 55 73");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("70 91 95 98 1 7 84 97");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("66 44 89 102 20 30 68 47 56");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("36 26 35 17 100 74 57");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("61 62 23 4 29 75 22 43");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("82 10 3 14 39 48 49 32");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("38 53 65 103 72");
    }
}
