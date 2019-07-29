package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;

import java.nio.file.Paths;
import java.util.Random;

public class Runner {

    private static int maxIterations = 25000;

    private static final String pdptw200Directory;

    static {
        pdptw200Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws Exception {
        executeProblemSolver(pdptw200Directory, "lr1_2_10");
    }

    private static void executeProblemSolver(String directory, String problem) throws Exception {
        Instance instance = Reader.getInstance(Paths.get(directory, problem + ".txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations);
        solver.init();
        solver.run();
    }
}
