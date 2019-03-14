package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.preprocessing.InfeasibleRequestsPairs;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_PRE_PROCESSING_TEST {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void preProcessingLocalSearchTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_400_1.txt").toFile());
        InfeasibleRequestsPairs infeasibleRequestsPairs = new InfeasibleRequestsPairs(problemInstance, new Random(1));
        boolean[][] feasibilityPairs = infeasibleRequestsPairs.calculateFeasibilityPairs();
        assertThat(feasibilityPairs).isNotNull();
    }

}
