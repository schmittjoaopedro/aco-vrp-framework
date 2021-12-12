package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Random;

public class DPDPTW_ALNS {

    private static int maxIterations = 25000;

    private static String pdptw100Directory;

    private static String pdptw200Directory;

    private static String pdptw400Directory;

    private static String pdptw600Directory;

    private static String pdptw800Directory;

    private static String pdptw1000Directory;

    static {
        try {
            pdptw100Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/100-tasks").toURI()).toString();
            pdptw200Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/200-tasks").toURI()).toString();
            pdptw400Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/400-tasks").toURI()).toString();
            pdptw600Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/600-tasks").toURI()).toString();
            pdptw800Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/800-tasks").toURI()).toString();
            pdptw1000Directory = Paths.get(PDPTW_ALNS.class.getClassLoader().getResource("dpdptw/1000-tasks").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        executeProblemSolver(pdptw400Directory, "LR1_4_8_q_0_0.1");
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
