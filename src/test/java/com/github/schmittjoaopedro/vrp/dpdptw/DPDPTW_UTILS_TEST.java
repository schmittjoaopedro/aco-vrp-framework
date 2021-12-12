package com.github.schmittjoaopedro.vrp.dpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.OptimalRequestSolver;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DPDPTW_UTILS_TEST {

    private static String dpdptw400Directory;

    static {
        try {
            dpdptw400Directory = Paths.get(DPDPTW_ALNS_TEST.class.getClassLoader().getResource("dpdptw_400").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void strictRequestOptimalSolverTest() throws Exception {
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dpdptw400Directory, "LR1_4_8_q_0_0.1.txt").toFile());
        OptimalRequestSolver optimalRequestSolver = new OptimalRequestSolver(88, instance);
        optimalRequestSolver.optimize();
        assertThat(optimalRequestSolver.getBestRoute()).containsExactly(0, 184, 4, 0);
        assertThat(optimalRequestSolver.getBestCost()).isEqualTo(285.60750571225356);
    }

    @Test
    @Ignore
    public void resultPrinter() throws Exception {
        String folder = "C:\\Temp\\pdptw\\pdp_800\\";
        Collection<File> files = FileUtils.listFiles(new File(folder), new String[]{"csv"}, true);
        for (File file : files) {
            if (file.getName().contains("RESULT")) {
                List<String> content = FileUtils.readLines(file, "UTF-8");
                String fileName = content.get(1).split(",")[1];
                double bsfNv = Double.parseDouble(content.get(2).split(",")[1]);
                double bsfTc = Double.parseDouble(content.get(3).split(",")[1]);
                double bsfNvMean = Double.parseDouble(content.get(4).split(",")[1]);
                double bsfTcMean = Double.parseDouble(content.get(5).split(",")[1]);
                double pOff = Double.parseDouble(content.get(6).split(",")[1]);
                double pOffSd = Double.parseDouble(content.get(7).split(",")[1]);
                System.out.printf(Locale.US, "%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n", fileName, bsfNv, bsfTc, bsfNvMean, bsfTcMean, pOff, pOffSd);
            }
        }
    }

    @Test
    @Ignore
    public void resultDynamicsPrinter() throws Exception {
        String folder = "C:\\Temp\\dpdptw\\dpdp_200_RESULTS";
        String instanceName = "LRC2_2_";
        String prefix = "RESULTS-";
        //String[] dynamics = {"_a_0.1.csv", "_a_0.25.csv", "_a_0.5.csv", "_a_0.75.csv", "_a_1.0.csv"};
        String[] dynamics = {"_q_0_0.1.csv", "_q_0_0.25.csv", "_q_0_0.5.csv", "_q_0_0.75.csv", "_q_0_1.0.csv"};
        String[] sizes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        for (String idx : sizes) {
            System.out.printf(Locale.US, "%s\t", instanceName + idx);
            for (String dynamic : dynamics) {
                List<String> content = FileUtils.readLines(Paths.get(folder, prefix + instanceName + idx + dynamic).toFile(), "UTF-8");
                double bsfNv = Double.parseDouble(content.get(2).split(",")[1]);
                double bsfTc = Double.parseDouble(content.get(3).split(",")[1]);
                double bsfNvMean = Double.parseDouble(content.get(4).split(",")[1]);
                double bsfTcMean = Double.parseDouble(content.get(5).split(",")[1]);
                double pOff = Double.parseDouble(content.get(6).split(",")[1]);
                double pOffSd = Double.parseDouble(content.get(7).split(",")[1]);
                System.out.printf(Locale.US, "%.2f\t%.2f\t%.2f\t%.2f\t\t\t", bsfNvMean, bsfTcMean, pOff, pOffSd);
            }
            System.out.println("");
        }
    }
}
