package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.GlobalStatistics;
import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solver implements Runnable {

    private String rootDirectory;

    private String fileName;

    private ProblemInstance problemInstance;

    private List<IterationStatistic> iterationStatistics;

    private MMAS mmas;

    private GlobalStatistics globalStatistics = new GlobalStatistics();

    private int maxIterations;

    private int seed;

    private double rho;

    private String filePath;

    public Solver(String rootDirectory, String fileName, int maxIterations, int seed, double rho) {
        this.fileName = fileName;
        this.rootDirectory = rootDirectory;
        this.maxIterations = maxIterations;
        this.seed = seed;
        this.rho = rho;
    }

    @Override
    public void run() {
        // Initialization of MMAS
        globalStatistics.startTimer();
        initProblemInstance();
        mmas.setRho(rho);
        mmas.setAlpha(1.0);
        mmas.setBeta(2.0);
        mmas.setQ_0(0.0);
        mmas.setnAnts(problemInstance.noNodes);
        mmas.setDepth(20);
        mmas.allocateAnts();
        mmas.allocateStructures();
        mmas.setRandom(new Random(seed));
        mmas.setSymmetric(false);
        mmas.computeNNList();
        mmas.initHeuristicInfo();
        mmas.initTry();
        globalStatistics.endTimer("MMAS Initialization");
    }

    private void initProblemInstance() {
        try {
            filePath = Paths.get(rootDirectory, fileName).toString();
            problemInstance = DataReader.getProblemInstance(new File(filePath));
            mmas = new MMAS(problemInstance);
            iterationStatistics = new ArrayList<>(maxIterations);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
