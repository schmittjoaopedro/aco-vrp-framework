package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.thesis.alns.StaticNvMinimizerTest;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch.GuidedEjectionSearch;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import com.github.schmittjoaopedro.vrp.thesis.utils.InstanceUtils;
import org.apache.commons.io.FileUtils;
import org.assertj.core.data.Offset;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GuidedLocalSearchTest {

    private static String pdptw100Directory;

    private static String pdptw1000Directory;

    static {
        try {
            pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").toURI()).toString();
            pdptw1000Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_1000").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lc1Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc103.txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1583.85, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.requestIds.size()).isEqualTo(9);
    }

    @Test
    public void lr1Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr103.txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2);

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1854.07, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(17);
        assertThat(solutionBest.requestIds.size()).isEqualTo(17);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2036.94, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1943.36, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.requestIds.size()).isEqualTo(15);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1771.31, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(14);
        assertThat(solutionBest.requestIds.size()).isEqualTo(14);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1634.23, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
    }

    //@Test
    public void lc1_10_2_Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, "LC1_10_2.txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 3);

        while (solutionBest.tours.size() > 94) {
            long time = System.currentTimeMillis();
            solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
            System.out.println(solutionBest);
            System.out.println("Total time (m): " + (System.currentTimeMillis() - time) / 1000.0 / 60.0);
        }
    }

    //@Test
    public void lc1_10_3_Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, "LC1_10_3.txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 3);

        while (solutionBest.tours.size() > 80) {
            long time = System.currentTimeMillis();
            solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
            System.out.println(solutionBest);
            System.out.println("Total time (m): " + (System.currentTimeMillis() - time) / 1000.0 / 60.0);
        }
    }

    @Test
    public void runAllTest() throws Exception {
        //String instanceName = "LC1_10_2";
        for (String instanceName : InstanceUtils.instances_1000) {
            Random random = new Random(1);
            File logFile = Paths.get("results", "GES", instanceName + ".csv").toFile();
            System.out.println("Running " + instanceName);
            FileUtils.write(logFile, "feasible,number of vehicles,total cost,time millis\n", "UTF-8", false);
            Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, instanceName + ".txt").toFile());
            long time = System.currentTimeMillis();
            Solution solutionBest = createInitialSolution(instance, random);
            FileUtils.write(logFile, solutionBest.feasible + "," + solutionBest.tours.size() + "," + solutionBest.totalCost + "," + (System.currentTimeMillis() - time) + "\n", "UTF-8", true);
            GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2, 300);
            int currNV = Integer.MAX_VALUE;
            while (solutionBest.feasible && solutionBest.tours.size() < currNV) {
                currNV = solutionBest.tours.size();
                solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
                FileUtils.write(logFile, solutionBest.feasible + "," + solutionBest.tours.size() + "," + solutionBest.totalCost + "," + (System.currentTimeMillis() - time) + "\n", "UTF-8", true);
            }
        }

        /*
         Running LRC2_10_4
         java.lang.IllegalArgumentException: bound must be positive
         */
    }

    private Solution createInitialSolution(Instance instance, Random random) {
        Solution solution = SolutionUtils.createSolution(instance);
        new RegretInsertion(random, instance, (sol, inst) -> 1).insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        return solution;
    }

}
