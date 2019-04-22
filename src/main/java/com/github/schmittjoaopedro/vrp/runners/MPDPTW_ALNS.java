package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;

import java.nio.file.Paths;
import java.util.Random;

public class MPDPTW_ALNS {

    private static final String rootDirectory;

    private static int maxIterations = 100000;

    static {
        rootDirectory = Paths.get(MPDPTW_ALNS.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws Exception {
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_400_1.txt").toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
    }

    private void mpdptw_test() throws Exception {
        //for (String noVert : new String[]{"400", "100", "50", "25"}) {
        for (String noVert : new String[]{"25", "50", "100", "400"}) {
            for (String typ : new String[]{"l", "n", "w"}) {
                for (String reqSize : new String[]{"4", "8"}) {
                    for (String id : new String[]{"1", "2", "3", "4", "5"}) {
                        String file = typ + "_" + reqSize + "_" + noVert + "_" + id;
                        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, file + ".txt").toFile());
                        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
                        alns.setGenerateFile(true);
                        alns.execute();
                    }
                }
            }
        }
    }

}
