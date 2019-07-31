package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
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

    @Test
    public void minimizeVehicles_lc1_2_3_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lc1_2_3.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solver.getLogs().get(0)).isEqualTo("Initial solution = [F = true, NV = 20, TC = 4385.17]");
        assertThat(solver.getLogs().get(1)).isEqualTo("New best = [F = true, NV = 19, TC = 4097.69]");
        assertThat(solver.getLogs().get(2)).isEqualTo("New best = [F = true, NV = 18, TC = 4133.07]");
        assertThat(solver.getLogs().get(3)).isEqualTo("New best = [F = true, NV = 17, TC = 3640.98]");

        assertThat(solutionBest.totalCost).isEqualTo(3640.9778334906696);
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        // Check tours
        assertThat(StringUtils.join(solutionBest.tours.get(0), " ")).isEqualTo("0 21 23 173 154 24 61 100 64 149 146 131 76 0");
        assertThat(StringUtils.join(solutionBest.tours.get(1), " ")).isEqualTo("0 167 5 191 72 43 155 1 17 39 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(2), " ")).isEqualTo("0 62 120 44 192 196 97 14 96 105 15 28 30 0");
        assertThat(StringUtils.join(solutionBest.tours.get(3), " ")).isEqualTo("0 113 73 116 129 11 6 122 139 164 158 34 67 0");
        assertThat(StringUtils.join(solutionBest.tours.get(4), " ")).isEqualTo("0 48 182 75 163 194 145 195 52 92 42 169 133 0");
        assertThat(StringUtils.join(solutionBest.tours.get(5), " ")).isEqualTo("0 45 178 26 152 40 153 89 59 130 19 68 27 0");
        assertThat(StringUtils.join(solutionBest.tours.get(6), " ")).isEqualTo("0 148 103 206 197 69 141 65 51 174 162 12 109 0");
        assertThat(StringUtils.join(solutionBest.tours.get(7), " ")).isEqualTo("0 4 82 180 84 125 46 128 106 10 2 90 13 0");
        assertThat(StringUtils.join(solutionBest.tours.get(8), " ")).isEqualTo("0 177 3 88 8 186 127 98 157 99 183 199 53 0");
        assertThat(StringUtils.join(solutionBest.tours.get(9), " ")).isEqualTo("0 78 201 101 144 119 35 71 9 126 166 60 193 188 202 0");
        assertThat(StringUtils.join(solutionBest.tours.get(10), " ")).isEqualTo("0 93 55 135 58 184 37 138 102 124 200 0");
        assertThat(StringUtils.join(solutionBest.tours.get(11), " ")).isEqualTo("0 170 134 50 156 112 168 79 29 87 123 198 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(12), " ")).isEqualTo("0 20 41 85 80 31 25 172 77 110 171 94 179 0");
        assertThat(StringUtils.join(solutionBest.tours.get(13), " ")).isEqualTo("0 114 159 38 204 150 22 151 16 140 187 142 111 63 56 0");
        assertThat(StringUtils.join(solutionBest.tours.get(14), " ")).isEqualTo("0 57 118 83 143 176 36 33 121 165 95 108 190 175 203 0");
        assertThat(StringUtils.join(solutionBest.tours.get(15), " ")).isEqualTo("0 32 115 160 47 205 66 70 86 136 189 147 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(16), " ")).isEqualTo("0 161 104 18 54 185 132 7 181 117 49 81 137 0");
        // Check requests
        assertThat(StringUtils.join(solutionBest.requestIds.get(0), " ")).isEqualTo("14 79 32 88 75 12");
        assertThat(StringUtils.join(solutionBest.requestIds.get(1), " ")).isEqualTo("20 3 85 98 0");
        assertThat(StringUtils.join(solutionBest.requestIds.get(2), " ")).isEqualTo("33 9 61 47 99 52");
        assertThat(StringUtils.join(solutionBest.requestIds.get(3), " ")).isEqualTo("4 80 36 58 8 55");
        assertThat(StringUtils.join(solutionBest.requestIds.get(4), " ")).isEqualTo("94 74 26 23 100 37");
        assertThat(StringUtils.join(solutionBest.requestIds.get(5), " ")).isEqualTo("78 21 65 92 15 24");
        assertThat(StringUtils.join(solutionBest.requestIds.get(6), " ")).isEqualTo("71 28 101 50 34 76");
        assertThat(StringUtils.join(solutionBest.requestIds.get(7), " ")).isEqualTo("93 2 7 63 53 41");
        assertThat(StringUtils.join(solutionBest.requestIds.get(8), " ")).isEqualTo("1 64 96 91 48 6");
        assertThat(StringUtils.join(solutionBest.requestIds.get(9), " ")).isEqualTo("49 18 60 97 39 73 31");
        assertThat(StringUtils.join(solutionBest.requestIds.get(10), " ")).isEqualTo("45 30 68 69 62");
        assertThat(StringUtils.join(solutionBest.requestIds.get(11), " ")).isEqualTo("40 54 27 86 67 102");
        assertThat(StringUtils.join(solutionBest.requestIds.get(12), " ")).isEqualTo("11 22 16 43 38 87");
        assertThat(StringUtils.join(solutionBest.requestIds.get(13), " ")).isEqualTo("19 13 56 70 77 81 72");
        assertThat(StringUtils.join(solutionBest.requestIds.get(14), " ")).isEqualTo("84 90 59 46 42 29 89");
        assertThat(StringUtils.join(solutionBest.requestIds.get(15), " ")).isEqualTo("44 82 25 35 17 57");
        assertThat(StringUtils.join(solutionBest.requestIds.get(16), " ")).isEqualTo("5 10 66 51 95 83");
    }

    @Test
    public void minimizeVehicles_lr1_2_10_Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw200Directory, "lr1_2_10.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solver.getLogs().get(0)).isEqualTo("Initial solution = [F = true, NV = 16, TC = 5070.74]");
        assertThat(solver.getLogs().get(1)).isEqualTo("New best = [F = true, NV = 15, TC = 4538.34]");
        assertThat(solver.getLogs().get(2)).isEqualTo("New best = [F = true, NV = 14, TC = 4287.88]");
        assertThat(solver.getLogs().get(3)).isEqualTo("New best = [F = true, NV = 13, TC = 4278.02]");
        assertThat(solver.getLogs().get(4)).isEqualTo("New best = [F = true, NV = 12, TC = 4091.96]");
        assertThat(solver.getLogs().get(5)).isEqualTo("New best = [F = true, NV = 11, TC = 3924.43]");

        assertThat(solutionBest.totalCost).isEqualTo(3924.4295378737456);
        assertThat(solutionBest.tours.size()).isEqualTo(11);
        // Check tours
        assertThat(StringUtils.join(solutionBest.tours.get(0), " ")).isEqualTo("0 196 73 13 20 30 34 99 98 163 136 6 189 177 102 10 83 108 97 0");
        assertThat(StringUtils.join(solutionBest.tours.get(1), " ")).isEqualTo("0 57 173 152 89 106 23 19 39 208 84 90 204 138 88 128 45 0");
        assertThat(StringUtils.join(solutionBest.tours.get(2), " ")).isEqualTo("0 55 41 157 15 166 153 71 193 151 125 56 129 191 43 141 164 70 107 0");
        assertThat(StringUtils.join(solutionBest.tours.get(3), " ")).isEqualTo("0 87 35 145 112 48 161 8 2 121 181 146 169 103 154 101 111 40 200 105 24 0");
        assertThat(StringUtils.join(solutionBest.tours.get(4), " ")).isEqualTo("0 18 150 68 158 50 66 131 86 195 180 160 130 11 79 116 61 49 206 27 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(5), " ")).isEqualTo("0 149 155 14 81 60 32 38 104 207 100 62 194 142 183 118 159 198 74 52 59 0");
        assertThat(StringUtils.join(solutionBest.tours.get(6), " ")).isEqualTo("0 7 67 63 137 36 44 5 3 117 25 201 80 182 175 9 37 65 96 29 110 0");
        assertThat(StringUtils.join(solutionBest.tours.get(7), " ")).isEqualTo("0 140 46 54 85 94 12 95 22 114 178 127 58 47 82 174 77 0");
        assertThat(StringUtils.join(solutionBest.tours.get(8), " ")).isEqualTo("0 4 188 184 72 147 93 199 135 133 64 109 167 33 144 186 179 202 190 28 75 0");
        assertThat(StringUtils.join(solutionBest.tours.get(9), " ")).isEqualTo("0 92 123 168 139 187 162 115 16 171 170 122 76 205 17 42 53 192 78 148 119 0");
        assertThat(StringUtils.join(solutionBest.tours.get(10), " ")).isEqualTo("0 132 1 143 120 26 176 51 21 113 165 69 134 203 124 185 31 126 156 172 197 0");
        // Check requests
        assertThat(StringUtils.join(solutionBest.requestIds.get(0), " ")).isEqualTo("14 82 49 10 52 48 3 39 101");
        assertThat(StringUtils.join(solutionBest.requestIds.get(1), " ")).isEqualTo("89 43 44 76 30 41 66 20");
        assertThat(StringUtils.join(solutionBest.requestIds.get(2), " ")).isEqualTo("62 61 23 98 84 75 29 7 22");
        assertThat(StringUtils.join(solutionBest.requestIds.get(3), " ")).isEqualTo("80 54 21 71 16 25 59 92 50 42");
        assertThat(StringUtils.join(solutionBest.requestIds.get(4), " ")).isEqualTo("79 57 100 27 26 78 5 36 74 9");
        assertThat(StringUtils.join(solutionBest.requestIds.get(5), " ")).isEqualTo("19 51 32 15 31 94 99 102 77 73");
        assertThat(StringUtils.join(solutionBest.requestIds.get(6), " ")).isEqualTo("58 2 18 34 12 93 33 35 17 4");
        assertThat(StringUtils.join(solutionBest.requestIds.get(7), " ")).isEqualTo("68 56 11 24 28 46 47 6");
        assertThat(StringUtils.join(solutionBest.requestIds.get(8), " ")).isEqualTo("97 72 53 103 91 70 1 38 95 65");
        assertThat(StringUtils.join(solutionBest.requestIds.get(9), " ")).isEqualTo("81 86 96 45 67 60 87 85 8 40");
        assertThat(StringUtils.join(solutionBest.requestIds.get(10), " ")).isEqualTo("83 0 69 13 88 55 37 90 64 63");
    }

}
