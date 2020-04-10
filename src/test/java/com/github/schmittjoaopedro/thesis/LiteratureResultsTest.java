package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.vrp.thesis.Solver;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.LNSOptimizer;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LiteratureResultsTest {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;

    static {
        pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
    }

    @Test
    public void lc1Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc103.txt").toFile());
        Solver solver = new Solver(instance, new Random(5), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.totalCost).isEqualTo(1035.3499342457624);
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 81 78 76 71 70 73 77 79 80 83 82 63 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 67 65 62 74 72 61 64 68 66 69 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 43 42 41 40 44 46 101 45 48 51 50 52 49 47 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 20 25 27 15 30 24 23 18 16 14 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 90 87 98 92 84 85 88 86 89 91 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 32 33 31 35 104 37 38 39 36 34 29 28 26 103 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 57 55 54 53 56 58 60 59 22 21 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 13 17 19 94 93 102 97 100 99 96 95 12 0");
    }

    @Test
    public void lc2Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc205.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(588.8759626534237);
        assertThat(solutionBest.tours.size()).isEqualTo(3);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 67 63 62 74 72 61 64 66 69 68 65 49 55 54 53 56 58 60 59 57 40 44 46 45 51 50 52 47 43 42 41 48 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 20 22 24 27 30 29 6 32 33 31 35 37 38 39 36 34 28 26 23 18 19 16 101 14 12 15 17 13 25 9 11 10 8 21 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 93 5 75 2 1 99 100 97 92 94 95 98 7 3 4 89 91 88 86 84 83 82 85 76 71 70 73 80 79 81 102 78 77 96 87 90 0");
    }

    @Test
    public void lr1Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr112.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1003.766997800698);
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 92 98 85 93 87 57 15 43 42 97 13 58 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 27 31 88 7 82 8 46 36 49 47 48 104 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 28 76 79 78 34 35 71 65 66 20 105 1 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 2 41 22 72 74 101 75 56 23 67 39 4 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 95 59 99 94 6 18 83 84 17 45 60 89 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 5 61 16 86 38 14 44 91 100 102 37 96 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 21 73 40 53 26 12 80 103 24 25 55 54 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 69 30 51 9 81 106 33 50 29 3 77 68 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 52 62 19 11 64 63 90 32 10 70 0");
    }

    @Test
    public void lr2Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr209.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(930.5857489156436);
        assertThat(solutionBest.tours.size()).isEqualTo(3);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 52 82 83 5 99 59 95 92 98 44 14 38 86 16 61 85 18 7 8 84 17 45 46 36 49 32 10 48 60 89 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 28 76 29 12 40 2 73 21 72 39 67 23 75 22 41 15 57 87 94 6 101 53 26 13 97 42 43 91 100 37 93 96 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 27 69 31 88 62 47 19 11 64 63 90 30 51 71 9 81 33 3 79 78 34 35 65 66 20 70 1 50 77 68 80 24 54 55 25 4 56 74 58 102 0");
    }

    @Test
    public void lrc1Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lrc105.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1637.6243913700941);
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 63 62 67 84 51 85 89 102 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 42 61 8 6 46 4 107 3 1 100 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 90 53 66 56 91 106 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 33 76 18 48 21 25 24 105 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 98 47 16 103 15 87 59 97 75 58 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 72 71 81 41 54 96 94 93 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 69 88 78 108 73 60 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 2 45 5 7 79 55 101 68 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 82 12 14 11 9 10 13 17 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 92 95 64 99 52 86 57 74 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 65 83 19 23 22 20 49 77 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 39 36 44 38 40 37 35 104 43 70 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 29 27 26 30 28 32 31 34 50 80 0");
    }

    @Test
    public void lrc2Test() throws Exception {
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lrc203.txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true, LNSOptimizer.Type.ALNS);
        solver.init();
        solver.run();
        Solution solutionBest = solver.getSolutionBest();
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1089.0676879447258);
        assertThat(solutionBest.tours.size()).isEqualTo(3);
        int idx = 0;
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 90 65 91 92 102 95 50 33 32 34 31 29 27 26 28 30 62 67 85 63 89 76 19 49 22 20 51 84 56 66 82 98 55 68 70 100 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 81 54 39 36 42 45 7 60 15 11 88 61 44 40 38 41 2 6 8 46 4 101 5 3 1 43 35 37 72 71 93 94 96 80 0");
        assertThat(StringUtils.join(solutionBest.tours.get(idx++), ' ')).isEqualTo("0 69 64 18 48 21 23 57 99 52 86 87 9 53 78 79 73 14 12 10 16 47 17 13 74 59 97 75 58 77 25 24 83 0");
    }
}
