package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class PDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int statisticInterval = 1;

    private static int maxIterations = 1000;

    private static int seed = 1;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_large_lc101_test() throws IOException {
        /*executeProblemSolver("lc101");
        executeProblemSolver("lc102");
        executeProblemSolver("lc103");
        executeProblemSolver("lc104");
        executeProblemSolver("lc105");
        executeProblemSolver("lc106");
        executeProblemSolver("lc107");
        executeProblemSolver("lc108");
        executeProblemSolver("lc109");
        executeProblemSolver("lc201");
        executeProblemSolver("lc202");
        executeProblemSolver("lc203");
        executeProblemSolver("lc204");
        executeProblemSolver("lc205");
        executeProblemSolver("lc206");
        executeProblemSolver("lc207");
        executeProblemSolver("lc208");
        executeProblemSolver("lr101");
        executeProblemSolver("lr102");
        executeProblemSolver("lr103");
        executeProblemSolver("lr104");
        executeProblemSolver("lr105");
        executeProblemSolver("lr106");
        executeProblemSolver("lr107");
        executeProblemSolver("lr108");
        executeProblemSolver("lr109");
        executeProblemSolver("lr110");
        executeProblemSolver("lr111");
        executeProblemSolver("lr112");
        executeProblemSolver("lr201");
        executeProblemSolver("lr202");
        executeProblemSolver("lr203");
        executeProblemSolver("lr204");
        executeProblemSolver("lr205");
        executeProblemSolver("lr206");
        executeProblemSolver("lr207");
        executeProblemSolver("lr208");
        executeProblemSolver("lr209");
        executeProblemSolver("lr210");
        executeProblemSolver("lr211");
        executeProblemSolver("lrc101");*/
        executeProblemSolver("lrc102");
        executeProblemSolver("lrc103");
        executeProblemSolver("lrc104");
        executeProblemSolver("lrc105");
        executeProblemSolver("lrc106");
        executeProblemSolver("lrc107");
        executeProblemSolver("lrc108");
        executeProblemSolver("lrc201");
        executeProblemSolver("lrc202");
        executeProblemSolver("lrc203");
        executeProblemSolver("lrc204");
        executeProblemSolver("lrc205");
        executeProblemSolver("lrc206");
        executeProblemSolver("lrc207");
        executeProblemSolver("lrc208");
    }

    private void executeProblemSolver(String problem) throws IOException {
        String fileName = problem + ".txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(rootDirectory, fileName).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.02, statisticInterval, true);
        solver.setLsActive(true);
        solver.setParallel(true);
        solver.run();
    }

    // TODO: Evaluate this route because one different vehicle route was found with the same cost
    @Test
    public void pdptw_large_lr104_test() throws IOException {
        String problem = "lr104.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.2, statisticInterval, true);
        solver.setLsActive(true);
        solver.run();
    }

    @Test
    public void pdptw_large_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, maxIterations, seed, 0.2, statisticInterval, true);
        solver.setLsActive(true);
        solver.setParallel(false);
        solver.run();
    }
}
