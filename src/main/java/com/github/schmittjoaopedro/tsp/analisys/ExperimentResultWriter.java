package com.github.schmittjoaopedro.tsp.analisys;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import org.apache.commons.io.FileUtils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExperimentResultWriter {

    private Map<String, List<String>> algorithmsResult;

    private double numIterations;

    private String[] headerNames;

    private List<String> keys = new ArrayList<>();

    public ExperimentResultWriter() {
        super();
        algorithmsResult = new HashMap<>();
    }

    public void initializeACO(int numIterations) {
        this.headerNames = new String[]{"ALG", "INSTANCE", "FREQ", "MAG", "RHO", "ALPHA", "BETA", "MEAN", "SD_MEAN"};
        initialize(numIterations);
    }

    public void initializeALNS(int numIterations) {
        this.headerNames = new String[]{"ALG", "INSTANCE", "NV", "TC", "MEAN", "MEAN_SD"};
        initialize(numIterations);
    }

    private void initialize(int numIterations) {
        this.numIterations = numIterations;
        for (String header : headerNames) {
            addLine("header", header);
        }
        for (int i = 1; i <= numIterations; i++) {
            addLine("header", String.valueOf(i));
        }
        keys.add("header");
    }

    public void computeResultsACO(String resultsPath, String algName, String testInstance, double mag, int freq, double rho, double alpha, double beta, List<IterationStatistic> result) throws Exception {
        String fileName = testInstance +
                "_freq_" + freq +
                "_mag_" + mag +
                "_rho_" + rho +
                "_alpha_" + alpha +
                "_beta_" + beta +
                "_" + algName +
                ".txt";
        keys.add(fileName);
        StringBuilder finalResult = new StringBuilder();
        double mean = 0.0;
        double sdMean = 0.0;
        for (IterationStatistic iter : result) {
            finalResult.append(iter).append('\n');
            mean += iter.getBestSoFar();
            sdMean += iter.getBestSoFarSd();
        }
        mean /= numIterations;
        sdMean /= numIterations;
        FileUtils.writeStringToFile(Paths.get(resultsPath, fileName).toFile(), finalResult.toString(), "UTF-8");
        addLine(fileName, algName);
        addLine(fileName, testInstance);
        addLine(fileName, String.valueOf(freq));
        addLine(fileName, String.valueOf(mag));
        addLine(fileName, String.valueOf(rho));
        addLine(fileName, String.valueOf(alpha));
        addLine(fileName, String.valueOf(beta));
        addLine(fileName, String.valueOf(mean));
        addLine(fileName, String.valueOf(sdMean));
        for (IterationStatistic iter : result) {
            addLine(fileName, String.valueOf(iter.getBestSoFar()));
        }
    }

    public void computeResultsALNS(String resultsPath, String algName, String testInstance, List<IterationStatistic> result) throws Exception {
        String fileName = testInstance + "_" + algName + ".txt";
        keys.add(fileName);
        StringBuilder finalResult = new StringBuilder();
        double mean = 0.0;
        double sdMean = 0.0;
        double nv = 0.0;
        double tc = 0.0;
        for (IterationStatistic iter : result) {
            finalResult.append(iter).append('\n');
            mean += iter.getBestSoFar();
            sdMean += iter.getBestSoFarSd();
            nv = iter.getBestSoFarNV();
            tc = iter.getBestSoFar();
        }
        mean /= numIterations;
        sdMean /= numIterations;
        FileUtils.writeStringToFile(Paths.get(resultsPath, fileName).toFile(), finalResult.toString(), "UTF-8");
        addLine(fileName, algName);
        addLine(fileName, testInstance);
        addLine(fileName, String.valueOf(nv));
        addLine(fileName, String.valueOf(tc));
        addLine(fileName, String.valueOf(mean));
        addLine(fileName, String.valueOf(sdMean));
        for (IterationStatistic iter : result) {
            addLine(fileName, String.valueOf(iter.getBestSoFar()));
        }
    }

    public void addLine(String key, String data) {
        if (!algorithmsResult.containsKey(key)) {
            algorithmsResult.put(key, new ArrayList<>());
        }
        algorithmsResult.get(key).add(data);
    }

    public void compileResults(String resultsPath, String fileName) throws Exception {
        StringBuilder finalResult = new StringBuilder();
        int count = 0;
        int maxSize = algorithmsResult.get("header").size();
        while (count < maxSize) {
            for (int i = 0; i < keys.size(); i++) {
                finalResult.append(algorithmsResult.get(keys.get(i)).get(count));
                if (i < keys.size() - 1) {
                    finalResult.append(",");
                }
            }
            finalResult.append("\n");
            count++;
        }
        FileUtils.writeStringToFile(Paths.get(resultsPath, fileName).toFile(), finalResult.toString(), "UTF-8");
    }

}
