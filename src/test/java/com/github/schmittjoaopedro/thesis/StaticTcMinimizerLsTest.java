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
        assertThat(StringUtils.join(solutionBest.requestIds.get(1), " ")).isEqualTo("64 37 83 55 94 102 73");
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 3299.6]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 20, TC = 3081.66]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 19, TC = 3080.79]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2827.76]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2826.99]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2825.1]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2790.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2784.13]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2776.66]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2773.05]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 18, TC = 2772.18]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2772.182174325898);
        assertThat(solutionBest.tours.size()).isEqualTo(18);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 57 118 83 143 176 36 33 121 165 188 202 108 78 201 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 101 144 119 166 35 126 71 9 1 99 53 17 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 148 103 206 197 124 141 69 200 162 109 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 161 104 18 54 185 132 7 181 117 49 81 137 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 32 171 65 86 115 94 51 174 136 189 147 12 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 45 178 173 154 24 61 100 64 27 149 68 76 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 93 55 135 58 184 37 138 146 102 131 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 21 23 182 75 163 194 145 195 52 92 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 113 73 129 11 6 122 139 34 95 158 190 67 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 20 41 85 80 31 25 172 77 110 179 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 116 164 66 160 47 205 91 70 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 48 26 152 40 153 169 89 105 15 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 62 120 44 192 196 97 14 96 130 28 19 30 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 60 82 180 84 191 125 4 72 193 46 128 90 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 177 3 88 8 186 127 98 157 199 183 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 170 134 50 156 112 168 79 29 87 42 123 133 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 114 159 38 204 150 22 151 16 140 187 142 111 63 56 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 10 5 106 167 43 13 2 175 203 155 39 107 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("59 39 42 97 90 29 84");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("49 18 73 48 0 60");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("50 71 62 101 76");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("5 83 51 10 95 66");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("44 17 57 28 87 34");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("32 92 14 88 79 24");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("30 68 75 69 45");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("100 74 12 94 102 37");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("46 80 36 8 55 4");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("22 43 16 11 38");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("25 35 58 82");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("21 78 52 15 26");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("65 47 33 61 9 99");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("41 98 93 63 31 2");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("6 91 64 96 1");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("67 86 40 27 23 54");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("70 72 19 81 56 13 77");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("3 53 89 7 20 85");
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
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3624.56]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3602.73]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 16, TC = 3598.5]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3584.37]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3559.52]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3552.09]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3529.43]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3494.96]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3463.69]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3442.44]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3441.86]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3424.01]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3413.52]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3410.65]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3407.57]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3402.79]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3396.97]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3393.18]");
        assertThat(solver.getLogs().get(counter++)).isEqualTo("New best = [F = true, NV = 15, TC = 3390.11]");

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(3390.111999238638);
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        // Check tours
        counter = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 18 150 68 158 50 66 131 86 180 160 130 11 79 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 73 13 20 30 34 62 98 163 136 177 6 189 102 10 83 52 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 89 106 125 151 141 191 43 129 70 164 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 72 147 135 93 199 109 64 133 195 25 201 116 61 49 206 27 28 75 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 87 48 161 8 76 205 2 17 40 200 192 111 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 4 188 15 184 166 153 71 193 33 144 186 179 202 190 167 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 140 57 173 95 178 19 114 23 127 77 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 149 81 1 143 26 176 51 123 21 126 156 172 197 119 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 46 54 85 94 12 82 47 58 22 174 108 97 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 132 155 14 120 69 165 31 113 185 124 183 118 90 204 138 45 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 35 145 112 121 181 146 169 103 134 203 154 101 105 24 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 92 168 139 187 162 115 16 171 170 122 42 53 78 148 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 7 55 41 157 56 39 208 37 65 96 29 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 67 63 137 36 44 5 3 117 80 182 175 9 0");
        assertThat(StringUtils.join(solutionBest.tours.get(counter++), " ")).isEqualTo("0 196 60 99 152 32 38 84 104 207 100 159 194 142 128 88 59 0");
        // Check requests
        counter = 0;
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("5 27 74 36 78 79 9");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("82 48 39 3 49 14 10 32");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("23 75 43 62 61");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("26 100 53 57 12 65 103 72 38");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("40 21 8 25 80 42");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("91 97 1 98 70 84 95 7");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("68 47 30 56 89 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("60 69 88 0 13 90 73");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("11 6 46 52 24 28");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("44 55 94 37 77 83 63 66");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("64 71 54 16 50 59 92");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("45 87 67 85 96 81 86");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("22 29 34 18 4 20");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("35 2 58 17 33 93");
        assertThat(StringUtils.join(solutionBest.requestIds.get(counter++), " ")).isEqualTo("15 101 76 51 31 41 19 99");
    }
}
