package com.github.schmittjoaopedro.thesis;

import com.github.schmittjoaopedro.thesis.alns.StaticNvMinimizerTest;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.localsearch.GuidedEjectionSearch;
import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.SolutionUtils;
import org.assertj.core.data.Offset;
import org.junit.Test;

import java.net.URISyntaxException;
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
        assertThat(solutionBest.totalCost).isEqualTo(1744.09, Offset.offset(0.01));
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
        assertThat(solutionBest.totalCost).isEqualTo(1819.87, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(2008.01, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.requestIds.size()).isEqualTo(15);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1829.63, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(14);
        assertThat(solutionBest.requestIds.size()).isEqualTo(14);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1640.93, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
    }

    @Test
    public void lc1_10_2_Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, "LC1_10_2.txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2);

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(62628.75, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(105);
        assertThat(solutionBest.requestIds.size()).isEqualTo(105);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(65827.90, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(104);
        assertThat(solutionBest.requestIds.size()).isEqualTo(104);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(67822.40, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(103);
        assertThat(solutionBest.requestIds.size()).isEqualTo(103);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(66230.02, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(102);
        assertThat(solutionBest.requestIds.size()).isEqualTo(102);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(65110.98, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(101);
        assertThat(solutionBest.requestIds.size()).isEqualTo(101);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(64819.58, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(100);
        assertThat(solutionBest.requestIds.size()).isEqualTo(100);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(64517.70, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(99);
        assertThat(solutionBest.requestIds.size()).isEqualTo(99);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(63608.22, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(98);
        assertThat(solutionBest.requestIds.size()).isEqualTo(98);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        System.out.println("97 - " + solutionBest.totalCost);
        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        System.out.println("96 - " + solutionBest.totalCost);
        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        System.out.println("95 - " + solutionBest.totalCost);
        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        System.out.println("94 - " + solutionBest.totalCost);

        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(64517.70, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(100);
        assertThat(solutionBest.requestIds.size()).isEqualTo(100);
    }

    private Solution createInitialSolution(Instance instance, Random random) {
        Solution solution = SolutionUtils.createSolution(instance);
        new RegretInsertion(random, instance, (sol, inst) -> 1).insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        return solution;
    }

}
