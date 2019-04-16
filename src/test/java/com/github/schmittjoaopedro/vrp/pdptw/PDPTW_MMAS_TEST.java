package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solver;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_MMAS_TEST {

    private static final String pdptw100Directory;

    private static final String dpdptw100Directory;

    private static final String pdptw200Directory;

    private static int statisticInterval = 1;

    private static int maxIterations = 1000;

    private static int seed = 1;

    static {
        dpdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
        pdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_100_tasks_test() throws IOException {
        executeProblemSolver(pdptw100Directory, "lc101");
        executeProblemSolver(pdptw100Directory, "lc102");
        executeProblemSolver(pdptw100Directory, "lc103");
        executeProblemSolver(pdptw100Directory, "lc101");
        executeProblemSolver(pdptw100Directory, "lc104");
        executeProblemSolver(pdptw100Directory, "lc105");
        executeProblemSolver(pdptw100Directory, "lc106");
        executeProblemSolver(pdptw100Directory, "lc107");
        executeProblemSolver(pdptw100Directory, "lc108");
        executeProblemSolver(pdptw100Directory, "lc109");
        executeProblemSolver(pdptw100Directory, "lc201");
        executeProblemSolver(pdptw100Directory, "lc202");
        executeProblemSolver(pdptw100Directory, "lc203");
        executeProblemSolver(pdptw100Directory, "lc204");
        executeProblemSolver(pdptw100Directory, "lc205");
        executeProblemSolver(pdptw100Directory, "lc206");
        executeProblemSolver(pdptw100Directory, "lc207");
        executeProblemSolver(pdptw100Directory, "lc208");
        executeProblemSolver(pdptw100Directory, "lr101");
        executeProblemSolver(pdptw100Directory, "lr102");
        executeProblemSolver(pdptw100Directory, "lr103");
        executeProblemSolver(pdptw100Directory, "lr104");
        executeProblemSolver(pdptw100Directory, "lr105");
        executeProblemSolver(pdptw100Directory, "lr106");
        executeProblemSolver(pdptw100Directory, "lr107");
        executeProblemSolver(pdptw100Directory, "lr108");
        executeProblemSolver(pdptw100Directory, "lr109");
        executeProblemSolver(pdptw100Directory, "lr110");
        executeProblemSolver(pdptw100Directory, "lr111");
        executeProblemSolver(pdptw100Directory, "lr112");
        executeProblemSolver(pdptw100Directory, "lr201");
        executeProblemSolver(pdptw100Directory, "lr202");
        executeProblemSolver(pdptw100Directory, "lr203");
        executeProblemSolver(pdptw100Directory, "lr204");
        executeProblemSolver(pdptw100Directory, "lr205");
        executeProblemSolver(pdptw100Directory, "lr206");
        executeProblemSolver(pdptw100Directory, "lr207");
        executeProblemSolver(pdptw100Directory, "lr208");
        executeProblemSolver(pdptw100Directory, "lr209");
        executeProblemSolver(pdptw100Directory, "lr210");
        executeProblemSolver(pdptw100Directory, "lr211");
        executeProblemSolver(pdptw100Directory, "lrc101");
        executeProblemSolver(pdptw100Directory, "lrc102");
        executeProblemSolver(pdptw100Directory, "lrc103");
        executeProblemSolver(pdptw100Directory, "lrc104");
        executeProblemSolver(pdptw100Directory, "lrc105");
        executeProblemSolver(pdptw100Directory, "lrc106");
        executeProblemSolver(pdptw100Directory, "lrc107");
        executeProblemSolver(pdptw100Directory, "lrc108");
        executeProblemSolver(pdptw100Directory, "lrc201");
        executeProblemSolver(pdptw100Directory, "lrc202");
        executeProblemSolver(pdptw100Directory, "lrc203");
        executeProblemSolver(pdptw100Directory, "lrc204");
        executeProblemSolver(pdptw100Directory, "lrc205");
        executeProblemSolver(pdptw100Directory, "lrc206");
        executeProblemSolver(pdptw100Directory, "lrc207");
        executeProblemSolver(pdptw100Directory, "lrc208");
    }

    @Test
    public void pdptw_200_tasks_test() throws Exception {
        executeProblemSolver(pdptw200Directory, "LC1_2_1");
        executeProblemSolver(pdptw200Directory, "LC1_2_2");
        executeProblemSolver(pdptw200Directory, "LC1_2_3");
        executeProblemSolver(pdptw200Directory, "LC1_2_4");
        executeProblemSolver(pdptw200Directory, "LC1_2_5");
        executeProblemSolver(pdptw200Directory, "LC1_2_6");
        executeProblemSolver(pdptw200Directory, "LC1_2_7");
        executeProblemSolver(pdptw200Directory, "LC1_2_8");
        executeProblemSolver(pdptw200Directory, "LC1_2_9");
        executeProblemSolver(pdptw200Directory, "LC1_2_10");
        executeProblemSolver(pdptw200Directory, "LC2_2_1");
        executeProblemSolver(pdptw200Directory, "LC2_2_2");
        executeProblemSolver(pdptw200Directory, "LC2_2_3");
        executeProblemSolver(pdptw200Directory, "LC2_2_4");
        executeProblemSolver(pdptw200Directory, "LC2_2_5");
        executeProblemSolver(pdptw200Directory, "LC2_2_6");
        executeProblemSolver(pdptw200Directory, "LC2_2_7");
        executeProblemSolver(pdptw200Directory, "LC2_2_8");
        executeProblemSolver(pdptw200Directory, "LC2_2_9");
        executeProblemSolver(pdptw200Directory, "LC2_2_10");
        executeProblemSolver(pdptw200Directory, "LR1_2_1");
        executeProblemSolver(pdptw200Directory, "LR1_2_2");
        executeProblemSolver(pdptw200Directory, "LR1_2_3");
        executeProblemSolver(pdptw200Directory, "LR1_2_4");
        executeProblemSolver(pdptw200Directory, "LR1_2_5");
        executeProblemSolver(pdptw200Directory, "LR1_2_6");
        executeProblemSolver(pdptw200Directory, "LR1_2_7");
        executeProblemSolver(pdptw200Directory, "LR1_2_8");
        executeProblemSolver(pdptw200Directory, "LR1_2_9");
        executeProblemSolver(pdptw200Directory, "LR1_2_10");
        executeProblemSolver(pdptw200Directory, "LR2_2_1");
        executeProblemSolver(pdptw200Directory, "LR2_2_2");
        executeProblemSolver(pdptw200Directory, "LR2_2_3");
        executeProblemSolver(pdptw200Directory, "LR2_2_4");
        executeProblemSolver(pdptw200Directory, "LR2_2_5");
        executeProblemSolver(pdptw200Directory, "LR2_2_6");
        executeProblemSolver(pdptw200Directory, "LR2_2_7");
        executeProblemSolver(pdptw200Directory, "LR2_2_8");
        executeProblemSolver(pdptw200Directory, "LR2_2_9");
        executeProblemSolver(pdptw200Directory, "LR2_2_10");
        executeProblemSolver(pdptw200Directory, "LRC1_2_1");
        executeProblemSolver(pdptw200Directory, "LRC1_2_2");
        executeProblemSolver(pdptw200Directory, "LRC1_2_3");
        executeProblemSolver(pdptw200Directory, "LRC1_2_4");
        executeProblemSolver(pdptw200Directory, "LRC1_2_5");
        executeProblemSolver(pdptw200Directory, "LRC1_2_6");
        executeProblemSolver(pdptw200Directory, "LRC1_2_7");
        executeProblemSolver(pdptw200Directory, "LRC1_2_8");
        executeProblemSolver(pdptw200Directory, "LRC1_2_9");
        executeProblemSolver(pdptw200Directory, "LRC1_2_10");
        executeProblemSolver(pdptw200Directory, "LRC2_2_1");
        executeProblemSolver(pdptw200Directory, "LRC2_2_2");
        executeProblemSolver(pdptw200Directory, "LRC2_2_3");
        executeProblemSolver(pdptw200Directory, "LRC2_2_4");
        executeProblemSolver(pdptw200Directory, "LRC2_2_5");
        executeProblemSolver(pdptw200Directory, "LRC2_2_6");
        executeProblemSolver(pdptw200Directory, "LRC2_2_7");
        executeProblemSolver(pdptw200Directory, "LRC2_2_8");
        executeProblemSolver(pdptw200Directory, "LRC2_2_9");
        executeProblemSolver(pdptw200Directory, "LRC2_2_10");
    }

    private void executeProblemSolver(String root, String problem) throws IOException {
        String fileName = problem + ".txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(root, fileName).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.setLsActive(true);
        solver.setParallel(true);
        solver.run();
    }

    // TODO: Evaluate this route because one different vehicle route was found with the same cost
    @Test
    public void pdptw_lr104_test() throws IOException {
        String problem = "lc101.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.2, statisticInterval, true);
        solver.setLsActive(true);
        solver.run();
        assertThat(solver.getBestSolution().totalCost).isEqualTo(828.9368669428337);
    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.2, statisticInterval, true);
        solver.setLsActive(true);
        solver.setParallel(false);
        solver.run();
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.2, statisticInterval, true);
        solver.setLsActive(true);
        solver.setParallel(false);
        solver.run();
    }
}
