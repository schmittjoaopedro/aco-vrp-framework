package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.vrp.pdptw_lns.Solver;

import java.io.IOException;
import java.nio.file.Paths;

public class PDPTW_LNS {

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    private static final String pdptw400Directory;

    private static final String pdptw600Directory;

    private static final String pdptw800Directory;

    static {
        pdptw100Directory = Paths.get(PDPTW_LNS.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(PDPTW_LNS.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
        pdptw400Directory = Paths.get(PDPTW_LNS.class.getClassLoader().getResource("pdp_400").getFile().substring(1)).toString();
        pdptw600Directory = Paths.get(PDPTW_LNS.class.getClassLoader().getResource("pdp_600").getFile().substring(1)).toString();
        pdptw800Directory = Paths.get(PDPTW_LNS.class.getClassLoader().getResource("pdp_800").getFile().substring(1)).toString();
    }

    public void pdptw_100_tasks_test() throws IOException {
        executeProblemSolver(pdptw100Directory, "lc101");
        executeProblemSolver(pdptw100Directory, "lc102");
        executeProblemSolver(pdptw100Directory, "lc103");
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

    public void pdptw_400_tasks_test() {
        executeProblemSolver(pdptw400Directory, "LC1_4_1");
        executeProblemSolver(pdptw400Directory, "LC1_4_2");
        executeProblemSolver(pdptw400Directory, "LC1_4_3");
        executeProblemSolver(pdptw400Directory, "LC1_4_4");
        executeProblemSolver(pdptw400Directory, "LC1_4_5");
        executeProblemSolver(pdptw400Directory, "LC1_4_6");
        executeProblemSolver(pdptw400Directory, "LC1_4_7");
        executeProblemSolver(pdptw400Directory, "LC1_4_8");
        executeProblemSolver(pdptw400Directory, "LC1_4_9");
        executeProblemSolver(pdptw400Directory, "LC1_4_10");
        executeProblemSolver(pdptw400Directory, "LC2_4_1");
        executeProblemSolver(pdptw400Directory, "LC2_4_2");
        executeProblemSolver(pdptw400Directory, "LC2_4_3");
        executeProblemSolver(pdptw400Directory, "LC2_4_4");
        executeProblemSolver(pdptw400Directory, "LC2_4_5");
        executeProblemSolver(pdptw400Directory, "LC2_4_6");
        executeProblemSolver(pdptw400Directory, "LC2_4_7");
        executeProblemSolver(pdptw400Directory, "LC2_4_8");
        executeProblemSolver(pdptw400Directory, "LC2_4_9");
        executeProblemSolver(pdptw400Directory, "LC2_4_10");
        executeProblemSolver(pdptw400Directory, "LR1_4_1");
        executeProblemSolver(pdptw400Directory, "LR1_4_2");
        executeProblemSolver(pdptw400Directory, "LR1_4_3");
        executeProblemSolver(pdptw400Directory, "LR1_4_4");
        executeProblemSolver(pdptw400Directory, "LR1_4_5");
        executeProblemSolver(pdptw400Directory, "LR1_4_6");
        executeProblemSolver(pdptw400Directory, "LR1_4_7");
        executeProblemSolver(pdptw400Directory, "LR1_4_8");
        executeProblemSolver(pdptw400Directory, "LR1_4_9");
        executeProblemSolver(pdptw400Directory, "LR1_4_10");
        executeProblemSolver(pdptw400Directory, "LR2_4_1");
        executeProblemSolver(pdptw400Directory, "LR2_4_2");
        executeProblemSolver(pdptw400Directory, "LR2_4_3");
        executeProblemSolver(pdptw400Directory, "LR2_4_4");
        executeProblemSolver(pdptw400Directory, "LR2_4_5");
        executeProblemSolver(pdptw400Directory, "LR2_4_6");
        executeProblemSolver(pdptw400Directory, "LR2_4_7");
        executeProblemSolver(pdptw400Directory, "LR2_4_8");
        executeProblemSolver(pdptw400Directory, "LR2_4_9");
        executeProblemSolver(pdptw400Directory, "LR2_4_10");
        executeProblemSolver(pdptw400Directory, "LRC1_4_1");
        executeProblemSolver(pdptw400Directory, "LRC1_4_2");
        executeProblemSolver(pdptw400Directory, "LRC1_4_3");
        executeProblemSolver(pdptw400Directory, "LRC1_4_4");
        executeProblemSolver(pdptw400Directory, "LRC1_4_5");
        executeProblemSolver(pdptw400Directory, "LRC1_4_6");
        executeProblemSolver(pdptw400Directory, "LRC1_4_7");
        executeProblemSolver(pdptw400Directory, "LRC1_4_8");
        executeProblemSolver(pdptw400Directory, "LRC1_4_9");
        executeProblemSolver(pdptw400Directory, "LRC1_4_10");
        executeProblemSolver(pdptw400Directory, "LRC2_4_1");
        executeProblemSolver(pdptw400Directory, "LRC2_4_2");
        executeProblemSolver(pdptw400Directory, "LRC2_4_3");
        executeProblemSolver(pdptw400Directory, "LRC2_4_4");
        executeProblemSolver(pdptw400Directory, "LRC2_4_5");
        executeProblemSolver(pdptw400Directory, "LRC2_4_6");
        executeProblemSolver(pdptw400Directory, "LRC2_4_7");
        executeProblemSolver(pdptw400Directory, "LRC2_4_8");
        executeProblemSolver(pdptw400Directory, "LRC2_4_9");
        executeProblemSolver(pdptw400Directory, "LRC2_4_10");
    }

    private void executeProblemSolver(String directory, String problem) {
        Solver solver = new Solver(directory, problem + ".txt", 1);
        solver.run();
    }

    public static void main(String[] args) throws Exception {
        //new PDPTW_LNS().pdptw_100_tasks_test();
        Solver solver = new Solver(pdptw200Directory, "lc1_2_8.txt", 1);
        solver.run();
    }

}
