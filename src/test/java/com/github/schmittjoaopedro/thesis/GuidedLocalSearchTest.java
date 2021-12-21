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

    static {
        try {
            pdptw100Directory = Paths.get(StaticNvMinimizerTest.class.getClassLoader().getResource("pdp_100").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lc1Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lc103.txt").toFile());
        Solution initialSolution = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2);

        assertThat(initialSolution.feasible).isTrue();
        assertThat(initialSolution.totalCost).isEqualTo(1125.81, Offset.offset(0.001));
        assertThat(initialSolution.tours.size()).isEqualTo(10);
        assertThat(initialSolution.requestIds.size()).isEqualTo(10);
        assertThat(initialSolution.getTourStr(0)).isEqualTo("0 20 24 25 27 29 30 28 26 103 23 22 21 0");
        assertThat(initialSolution.getTourStr(1)).isEqualTo("0 67 65 62 74 72 61 64 68 66 69 46 101 0");
        assertThat(initialSolution.getTourStr(2)).isEqualTo("0 5 3 7 8 10 11 9 6 4 2 1 75 0");
        assertThat(initialSolution.getTourStr(3)).isEqualTo("0 43 42 41 40 44 45 48 51 50 52 49 47 0");
        assertThat(initialSolution.getTourStr(4)).isEqualTo("0 90 87 86 83 82 84 85 88 89 91 96 95 0");
        assertThat(initialSolution.getTourStr(5)).isEqualTo("0 32 33 31 35 104 37 38 39 36 34 18 16 0");
        assertThat(initialSolution.getTourStr(6)).isEqualTo("0 57 55 54 53 56 58 60 59 71 73 0");
        assertThat(initialSolution.getTourStr(7)).isEqualTo("0 13 17 19 15 14 12 94 99 0");
        assertThat(initialSolution.getTourStr(8)).isEqualTo("0 81 78 98 92 93 102 97 100 0");
        assertThat(initialSolution.getTourStr(9)).isEqualTo("0 76 70 77 79 80 63 0");
        assertThat(initialSolution.getReqStr(0)).isEqualTo("11 10 13 12 14 15");
        assertThat(initialSolution.getReqStr(1)).isEqualTo("35 33 32 34 38 26");
        assertThat(initialSolution.getReqStr(2)).isEqualTo("0 2 1 4 3 5");
        assertThat(initialSolution.getReqStr(3)).isEqualTo("22 23 25 21 24 27");
        assertThat(initialSolution.getReqStr(4)).isEqualTo("44 45 43 46 42 49");
        assertThat(initialSolution.getReqStr(5)).isEqualTo("17 19 18 20 16 9");
        assertThat(initialSolution.getReqStr(6)).isEqualTo("29 28 31 30 37");
        assertThat(initialSolution.getReqStr(7)).isEqualTo("8 7 6 48");
        assertThat(initialSolution.getReqStr(8)).isEqualTo("50 47 51 41");
        assertThat(initialSolution.getReqStr(9)).isEqualTo("40 39 36");

        Solution solutionBest = guidedEjectionSearch.deleteRoute(initialSolution);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1464.89, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(9);
        assertThat(solutionBest.requestIds.size()).isEqualTo(9);
    }

    @Test
    public void lr1Test() throws Exception {
        Random random = new Random(1);
        Instance instance = Reader.getInstance(Paths.get(pdptw100Directory, "lr103.txt").toFile());
        Solution initialSolution = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2);

        assertThat(initialSolution.feasible).isTrue();
        assertThat(initialSolution.totalCost).isEqualTo(1854.07, Offset.offset(0.01));
        assertThat(initialSolution.tours.size()).isEqualTo(17);
        assertThat(initialSolution.requestIds.size()).isEqualTo(17);

        Solution solutionBest = guidedEjectionSearch.deleteRoute(initialSolution);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1828.02, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(16);
        assertThat(solutionBest.requestIds.size()).isEqualTo(16);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1914.86, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(15);
        assertThat(solutionBest.requestIds.size()).isEqualTo(15);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1871.09, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(14);
        assertThat(solutionBest.requestIds.size()).isEqualTo(14);

        solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
        assertThat(solutionBest.feasible).isTrue();
        assertThat(solutionBest.totalCost).isEqualTo(1649.86, Offset.offset(0.01));
        assertThat(solutionBest.tours.size()).isEqualTo(13);
        assertThat(solutionBest.requestIds.size()).isEqualTo(13);
    }

    private Solution createInitialSolution(Instance instance, Random random) {
        Solution solution = SolutionUtils.createSolution(instance);
        new RegretInsertion(random, instance, (sol, inst) -> 1).insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        return solution;
    }

}
