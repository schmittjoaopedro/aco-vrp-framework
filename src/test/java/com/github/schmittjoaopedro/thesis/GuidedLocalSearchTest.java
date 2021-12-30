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
        optimize("LR1_10_5", 59);
        /*
        Running LR1_10_5
        [F = true, NV =  79, TC =  78310.47]
        Total time (m): 0.0028333333333333335
        [F = true, NV =  78, TC =  81628.63]
        Total time (m): 0.0091
        [F = true, NV =  77, TC =  82938.98]
        Total time (m): 0.002316666666666667
        [F = true, NV =  76, TC =  81628.72]
        Total time (m): 0.007816666666666666
        [F = true, NV =  75, TC =  80796.74]
        Total time (m): 0.0028333333333333335
        [F = true, NV =  74, TC =  82533.20]
        Total time (m): 0.041183333333333336
        [F = true, NV =  73, TC =  79834.73]
        Total time (m): 0.0013833333333333334
        [F = true, NV =  72, TC =  80243.85]
        Total time (m): 0.11511666666666667
        [F = true, NV =  71, TC =  80367.17]
        Total time (m): 0.13856666666666667
        [F = true, NV =  70, TC =  75925.78]
        Total time (m): 0.09313333333333333
        [F = true, NV =  69, TC =  77205.91]
        Total time (m): 0.32939999999999997
        [F = true, NV =  68, TC =  77177.09]
        Total time (m): 0.33595
        [F = true, NV =  67, TC =  76414.79]
        Total time (m): 0.3979666666666667
        [F = true, NV =  66, TC =  72867.76]
        Total time (m): 0.7232833333333333
        [F = true, NV =  65, TC =  72101.70]
        Total time (m): 1.4478833333333334
        [F = true, NV =  64, TC =  72032.39]
        Total time (m): 2.0154666666666667
        [F = true, NV =  63, TC =  72409.85]
        Total time (m): 1.9614333333333334
        [F = true, NV =  62, TC =  70339.37]
        Total time (m): 3.1269333333333336
        [F = true, NV =  61, TC =  71052.90]
        Total time (m): 9.328766666666667
        [F = true, NV =  60, TC =  70471.05]
        Total time (m): 5.39725
        [F = true, NV =  59, TC =  69195.02]
        Total time (m): 19.176483333333334
         */

        optimize("LR1_10_6", 48);
        /*
        Running LR1_10_6
        [F = true, NV =  68, TC =  74591.74]
        Total time (m): 4.333333333333333E-4
        [F = true, NV =  67, TC =  75795.66]
        Total time (m): 0.0015
        [F = true, NV =  66, TC =  81394.85]
        Total time (m): 0.014666666666666666
        [F = true, NV =  65, TC =  82095.63]
        Total time (m): 0.034083333333333334
        [F = true, NV =  64, TC =  80716.01]
        Total time (m): 0.032766666666666666
        [F = true, NV =  63, TC =  76762.32]
        Total time (m): 0.020433333333333335
        [F = true, NV =  62, TC =  78399.10]
        Total time (m): 0.025733333333333334
        [F = true, NV =  61, TC =  77275.24]
        Total time (m): 0.13116666666666668
        [F = true, NV =  60, TC =  76942.26]
        Total time (m): 0.05475
        [F = true, NV =  59, TC =  73675.15]
        Total time (m): 0.23818333333333333
        [F = true, NV =  58, TC =  74863.26]
        Total time (m): 0.41378333333333334
        [F = true, NV =  57, TC =  71661.22]
        Total time (m): 0.5763166666666667
        [F = true, NV =  56, TC =  72115.79]
        Total time (m): 0.4589666666666667
        [F = true, NV =  55, TC =  69344.44]
        Total time (m): 0.5400666666666667
        [F = true, NV =  54, TC =  70672.15]
        Total time (m): 0.9592833333333334
        [F = true, NV =  53, TC =  68457.98]
        Total time (m): 0.8204833333333333
        [F = true, NV =  52, TC =  67044.38]
        Total time (m): 2.3108333333333335
        [F = true, NV =  51, TC =  67198.12]
        Total time (m): 2.8631499999999996
        [F = true, NV =  50, TC =  63958.34]
        Total time (m): 4.281616666666666
        [F = true, NV =  49, TC =  66445.85]
        Total time (m): 7.806066666666666
        [F = true, NV =  48, TC =  64433.60]
        Total time (m): 16.941366666666667
         */

        optimize("LR1_10_9", 49);
        /*
        Running LR1_10_9
        [F = true, NV =  66, TC =  72762.50]
        Total time (m): 0.0027333333333333333
        [F = true, NV =  65, TC =  73299.61]
        Total time (m): 0.0016333333333333334
        [F = true, NV =  64, TC =  73645.67]
        Total time (m): 0.012316666666666667
        [F = true, NV =  63, TC =  73092.67]
        Total time (m): 0.028833333333333332
        [F = true, NV =  62, TC =  72278.06]
        Total time (m): 0.013666666666666666
        [F = true, NV =  61, TC =  72152.48]
        Total time (m): 0.07513333333333333
        [F = true, NV =  60, TC =  69848.43]
        Total time (m): 0.015600000000000001
        [F = true, NV =  59, TC =  70366.04]
        Total time (m): 0.29481666666666667
        [F = true, NV =  58, TC =  69870.73]
        Total time (m): 0.19558333333333333
        [F = true, NV =  57, TC =  67473.23]
        Total time (m): 0.3312333333333333
        [F = true, NV =  56, TC =  69143.48]
        Total time (m): 0.44811666666666666
        [F = true, NV =  55, TC =  66737.82]
        Total time (m): 0.5574666666666667
        [F = true, NV =  54, TC =  65109.26]
        Total time (m): 1.0768666666666666
        [F = true, NV =  53, TC =  64615.46]
        Total time (m): 1.4915999999999998
        [F = true, NV =  52, TC =  62664.07]
        Total time (m): 2.2258166666666668
        [F = true, NV =  51, TC =  63081.64]
        Total time (m): 3.7365
        [F = true, NV =  50, TC =  60328.19]
        Total time (m): 5.3918333333333335
        [F = true, NV =  49, TC =  62846.27]
        Total time (m): 10.975566666666667
         */

        optimize("LR1_10_10", 39);
        /*
        Running LR1_10_10
        [F = true, NV =  53, TC =  68985.21]
        Total time (m): 0.0146
        [F = true, NV =  52, TC =  67981.31]
        Total time (m): 0.014283333333333334
        [F = true, NV =  51, TC =  67551.73]
        Total time (m): 0.05171666666666667
        [F = true, NV =  50, TC =  66318.39]
        Total time (m): 0.010333333333333333
        [F = true, NV =  49, TC =  64654.23]
        Total time (m): 0.0663
        [F = true, NV =  48, TC =  64402.31]
        Total time (m): 0.12243333333333334
        [F = true, NV =  47, TC =  60426.38]
        Total time (m): 0.18878333333333333
        [F = true, NV =  46, TC =  60106.99]
        Total time (m): 0.28040000000000004
        [F = true, NV =  45, TC =  60255.81]
        Total time (m): 0.20608333333333334
        [F = true, NV =  44, TC =  58908.37]
        Total time (m): 0.7494666666666667
        [F = true, NV =  43, TC =  58368.50]
        Total time (m): 0.94815
        [F = true, NV =  42, TC =  56478.38]
        Total time (m): 1.1605166666666666
        [F = true, NV =  41, TC =  55481.41]
        Total time (m): 3.6104666666666665
        [F = true, NV =  40, TC =  55465.40]
        Total time (m): 5.8717500000000005
        [F = true, NV =  39, TC =  53413.64]
        Total time (m): 15.1201
         */

        optimize("LR2_10_2", 14);
        /*
        Running LR2_10_2
        [F = true, NV =  22, TC =  93422.10]
        Total time (m): 0.005333333333333333
        [F = true, NV =  21, TC = 108904.16]
        Total time (m): 0.03476666666666666
        [F = true, NV =  20, TC = 108938.22]
        Total time (m): 0.17776666666666668
        [F = true, NV =  19, TC = 102089.57]
        Total time (m): 0.4562
        [F = true, NV =  18, TC =  97081.26]
        Total time (m): 0.2605
        [F = true, NV =  17, TC =  92129.29]
        Total time (m): 1.2153833333333333
        [F = true, NV =  16, TC =  87978.45]
        Total time (m): 2.0521666666666665
        [F = true, NV =  15, TC =  83693.96]
        Total time (m): 4.807733333333333
        [F = true, NV =  14, TC =  83051.95]
        Total time (m): 139.90756666666667
         */

        optimize("LR2_10_6", 11);
        /*
        Running LR2_10_6
        [F = true, NV =  19, TC =  73675.02]
        Total time (m): 5.0E-5
        [F = true, NV =  18, TC = 103758.18]
        Total time (m): 0.21280000000000002
        [F = true, NV =  17, TC =  98571.32]
        Total time (m): 0.12066666666666667
        [F = true, NV =  16, TC =  93811.11]
        Total time (m): 0.13663333333333333
        [F = true, NV =  15, TC =  89849.51]
        Total time (m): 0.4183
        [F = true, NV =  14, TC =  86273.00]
        Total time (m): 0.62905
        [F = true, NV =  13, TC =  79948.12]
        Total time (m): 3.0257
        [F = true, NV =  12, TC =  74741.00]
        Total time (m): 11.1536
        [F = true, NV =  11, TC =  69596.15]
        Total time (m): 113.23575000000001
         */

        //optimize("LRC1_10_1", 82);
        /* IllegalArgumentException: bound must be positive */

        optimize("LRC1_10_3", 53);
        optimize("LRC1_10_5", 72);
        optimize("LRC1_10_6", 68);
        optimize("LRC1_10_7", 61);
        optimize("LRC1_10_8", 56);
        optimize("LRC1_10_9", 53);
        optimize("LRC1_10_10", 48);
        optimize("LRC2_10_4", 11);
        optimize("LRC2_10_5", 17);
    }

    public void optimize(String fileName, int numMin) throws Exception {
        Random random = new Random(1);
        System.out.println("Running " + fileName);
        Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, fileName + ".txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 3);

        while (solutionBest.tours.size() > numMin) {
            long time = System.currentTimeMillis();
            solutionBest = guidedEjectionSearch.deleteRoute(solutionBest);
            System.out.println(solutionBest);
            System.out.println("Total time (m): " + (System.currentTimeMillis() - time) / 1000.0 / 60.0);
        }
    }

    private Solution createInitialSolution(Instance instance, Random random) {
        Solution solution = SolutionUtils.createSolution(instance);
        new RegretInsertion(random, instance, (sol, inst) -> 1).insert(solution, 0);
        SolutionUtils.removeEmptyVehicles(solution);
        instance.solutionEvaluation(solution);
        return solution;
    }

}
