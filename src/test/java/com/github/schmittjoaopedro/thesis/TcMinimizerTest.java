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

public class TcMinimizerTest {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void minimizeCost_initial_solution() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.init();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
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

    @Test
    public void minimizeCost_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 4385.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 4043.29]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 4030.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3883.66]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3881.23]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3781.05]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3529.07]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3326.46]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3314.6]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3313.77]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3310.86]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3107.31]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3088.66]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3085.21]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3084.6]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3077.42]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 3069.79]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2945.37]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2937.98]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2873.06]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2869.57]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2775.21]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2774.0]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2772.18]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.182174325898);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 93 55 135 58 184 37 138 146 102 131 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 62 120 44 192 196 97 14 96 130 28 19 30 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 10 5 106 167 43 13 2 175 203 155 39 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 20 41 85 80 31 25 172 77 110 179 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 170 134 50 156 112 168 79 29 87 42 123 133 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 114 159 38 204 150 22 151 16 140 187 142 111 63 56 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 113 73 129 11 6 122 139 34 95 158 190 67 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 177 3 88 8 186 127 98 157 199 183 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 57 118 83 143 176 36 33 121 165 188 202 108 78 201 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 21 23 182 75 163 194 145 195 52 92 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 48 26 152 40 153 169 89 105 15 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 116 164 66 160 47 205 91 70 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 45 178 173 154 24 61 100 64 27 149 68 76 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 161 104 18 54 185 132 7 181 117 49 81 137 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 60 82 180 84 191 125 4 72 193 46 128 90 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 32 171 65 86 115 94 51 174 136 189 147 12 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 148 103 206 197 124 141 69 200 162 109 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 101 144 119 166 35 126 71 9 1 99 53 17 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("45 30 68 69 75");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("9 99 61 47 65 33");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("89 53 3 85 20 7");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("16 11 38 43 22");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("40 54 23 86 27 67");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("19 77 70 81 13 56 72");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("80 36 8 4 55 46");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("64 96 91 1 6");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("97 84 90 29 42 59 39");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("94 100 12 37 74 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("52 78 15 21 26");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("82 35 25 58");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("14 79 92 32 88 24");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("10 83 51 5 95 66");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("41 93 98 63 31 2");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("17 87 57 44 28 34");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("50 71 62 76 101");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("73 60 48 0 49 18");
    }

    @Test
    public void minimizeCost_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, false, true);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        int counter = 0;
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 5070.74]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 4652.02]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4503.62]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4485.29]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4469.69]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4386.81]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4305.2]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4050.56]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 4012.92]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3922.08]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3912.83]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3902.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3872.21]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3869.53]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3831.41]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3808.61]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3753.26]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3743.25]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3732.83]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3726.99]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3707.85]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3684.33]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3669.9]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3665.44]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3645.03]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3628.72]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3621.21]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3609.51]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3588.02]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3581.87]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3573.11]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3557.45]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3540.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3522.62]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3520.47]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3506.51]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3500.32]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3492.43]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3487.26]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3463.95]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3463.65]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3455.99]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3439.78]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3438.59]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3428.15]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3426.35]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3425.04]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3420.17]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3417.33]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3416.46]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3414.65]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3408.54]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 14, TC = 3407.48]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3407.477246629216);
        assertThat(solutionBest.tours.size()).isEqualTo(14);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 67 63 137 36 44 5 3 117 80 182 175 9 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 35 145 112 121 181 146 169 103 154 101 105 24 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 140 57 173 95 178 23 19 114 138 32 90 204 159 127 77 198 74 45 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 4 188 15 184 166 193 153 71 144 186 179 202 33 190 167 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 149 81 1 143 26 176 51 21 113 124 134 203 69 165 31 185 126 156 172 197 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 89 106 125 151 129 191 43 141 70 164 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 46 54 85 94 12 82 47 58 22 174 108 97 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 73 13 20 30 34 62 98 163 136 177 6 189 102 10 83 52 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 72 147 135 93 199 109 64 133 195 25 201 116 61 49 206 27 28 75 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 18 150 68 158 50 66 131 86 180 160 130 11 79 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 196 60 99 152 38 84 104 207 100 194 183 118 142 128 88 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 7 55 41 157 56 39 208 37 65 96 29 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 87 161 92 48 8 162 115 16 2 76 205 17 40 200 192 111 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 132 155 14 120 123 168 139 187 170 171 122 42 53 78 148 119 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("58 2 33 17 35 93");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("71 54 16 59 50 92");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("68 66 44 89 30 102 15 47 56");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("1 91 84 98 95 97 70 7");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("64 55 13 83 69 0 90 37 88 73");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("75 62 23 43 61");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("46 6 28 11 52 24");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("3 49 32 48 39 82 10 14");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("65 100 57 53 72 38 26 103 12");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("5 9 78 27 74 36 79");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("51 99 101 19 76 41 94 31");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("18 34 22 4 29 20");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("45 40 81 80 21 42 25 8");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("67 60 85 86 96 87 77 63");
    }
}
