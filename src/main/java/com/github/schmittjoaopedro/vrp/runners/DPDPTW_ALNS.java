package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class DPDPTW_ALNS {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    private static final String pdptw400Directory;

    private static final String pdptw600Directory;

    private static final String pdptw800Directory;

    private static final String pdptw1000Directory;

    static {
        pdptw100Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/100-tasks").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/200-tasks").getFile().substring(1)).toString();
        pdptw400Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/400-tasks").getFile().substring(1)).toString();
        pdptw600Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/600-tasks").getFile().substring(1)).toString();
        pdptw800Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/800-tasks").getFile().substring(1)).toString();
        pdptw1000Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/1000-tasks").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws Exception {
        executeProblemSolver(pdptw600Directory, "LC1_6_1_a_0.5");
    }

    private static void executeProblemSolver(String root, String problem) throws IOException {
        String fileName = problem + ".txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(root, fileName).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.setShowLog(true);
        alns.setGenerateFile(false);
        alns.run();
    }
}
