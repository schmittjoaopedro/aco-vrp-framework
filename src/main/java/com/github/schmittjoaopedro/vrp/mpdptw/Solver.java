package com.github.schmittjoaopedro.vrp.mpdptw;

import java.io.File;
import java.nio.file.Paths;

public class Solver implements Runnable {

    private String rootDirectory;

    private String fileName;

    private ProblemInstance problemInstance;

    private String filePath;

    public Solver(String rootDirectory, String fileName) {
        this.fileName = fileName;
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void run() {
        initProblemInstace();
    }

    private void initProblemInstace() {
        try {
            filePath = Paths.get(rootDirectory, fileName).toString();
            problemInstance = DataReader.getProblemInstance(new File(filePath));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
