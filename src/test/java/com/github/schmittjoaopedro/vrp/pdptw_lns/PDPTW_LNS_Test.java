package com.github.schmittjoaopedro.vrp.pdptw_lns;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class PDPTW_LNS_Test {

    int maxIterations = 100000;

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    private static final String pdptw400Directory;

    private static final String pdptw600Directory;

    static {
        pdptw100Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
        pdptw400Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_400").getFile().substring(1)).toString();
        pdptw600Directory = Paths.get(PDPTW_LNS_Test.class.getClassLoader().getResource("pdp_600").getFile().substring(1)).toString();
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


    private  void executeProblemSolver(String directory, String problem) {
        Solver solver = new Solver(directory, problem + ".txt", 1);
        solver.run();
    }

    @Test
    public void pdptw_lr104_test() {
        String problem = "lc204.txt";
        Solver solver = new Solver(pdptw100Directory, problem, 1);
        solver.run();
    }
}
