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
        //optimize("LC1_10_2", 94);
        /*
        Running LC1_10_2
        [F = true, NV = 104, TC =  67410.64]
        Total time (m): 0.018733333333333334
        [F = true, NV = 103, TC =  64818.38]
        Total time (m): 0.0052
        [F = true, NV = 102, TC =  66028.30]
        Total time (m): 0.15751666666666667
        [F = true, NV = 101, TC =  67370.31]
        Total time (m): 0.21256666666666665
        [F = true, NV = 100, TC =  65039.99]
        Total time (m): 0.2833333333333333
        [F = true, NV =  99, TC =  64287.22]
        Total time (m): 0.34473333333333334
        [F = true, NV =  98, TC =  62656.22]
        Total time (m): 0.34773333333333334
        [F = true, NV =  97, TC =  63341.35]
        Total time (m): 0.6886833333333333
        [F = true, NV =  96, TC =  60446.50]
        Total time (m): 1.6785999999999999
        [F = true, NV =  95, TC =  60202.97]
        Total time (m): 2.25425
        [F = true, NV =  94, TC =  60054.30]
        Total time (m): 5.977016666666667
         */

        //optimize("LC1_10_3", 80);
        /*
        Running LC1_10_3
        [F = true, NV =  96, TC =  65341.27]
        Total time (m): 0.0014
        [F = true, NV =  95, TC =  68517.72]
        Total time (m): 0.009433333333333332
        [F = true, NV =  94, TC =  67266.52]
        Total time (m): 0.05755
        [F = true, NV =  93, TC =  67024.69]
        Total time (m): 0.013433333333333334
        [F = true, NV =  92, TC =  65372.61]
        Total time (m): 0.016933333333333335
        [F = true, NV =  91, TC =  63814.18]
        Total time (m): 0.0351
        [F = true, NV =  90, TC =  63247.85]
        Total time (m): 0.015183333333333333
        [F = true, NV =  89, TC =  62048.02]
        Total time (m): 0.18845
        [F = true, NV =  88, TC =  61830.92]
        Total time (m): 0.33785000000000004
        [F = true, NV =  87, TC =  59608.41]
        Total time (m): 0.26799999999999996
        [F = true, NV =  86, TC =  57882.09]
        Total time (m): 0.33081666666666665
        [F = true, NV =  85, TC =  57102.37]
        Total time (m): 0.4374
        [F = true, NV =  84, TC =  55315.03]
        Total time (m): 0.6763666666666667
        [F = true, NV =  83, TC =  54910.54]
        Total time (m): 1.2423333333333335
        [F = true, NV =  82, TC =  54307.66]
        Total time (m): 1.1329166666666666
        [F = true, NV =  81, TC =  53069.42]
        Total time (m): 4.841433333333333
        [F = true, NV =  80, TC =  50876.02]
        Total time (m): 7.53885
         */

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

        optimize("LRC1_10_3", 53);
        /*
        Running LRC1_10_3
        [F = true, NV =  67, TC =  64525.32]
        Total time (m): 0.0017833333333333334
        [F = true, NV =  66, TC =  84854.69]
        Total time (m): 0.031083333333333334
        [F = true, NV =  65, TC =  84322.39]
        Total time (m): 0.00515
        [F = true, NV =  64, TC =  82334.43]
        Total time (m): 0.00175
        [F = true, NV =  63, TC =  81326.55]
        Total time (m): 0.014583333333333334
        [F = true, NV =  62, TC =  79576.54]
        Total time (m): 0.05201666666666667
        [F = true, NV =  61, TC =  79196.87]
        Total time (m): 0.011899999999999999
        [F = true, NV =  60, TC =  77684.97]
        Total time (m): 0.093
        [F = true, NV =  59, TC =  74849.49]
        Total time (m): 0.09476666666666667
        [F = true, NV =  58, TC =  73984.62]
        Total time (m): 0.12794999999999998
        [F = true, NV =  57, TC =  75919.32]
        Total time (m): 0.3419
        [F = true, NV =  56, TC =  73995.90]
        Total time (m): 0.48511666666666664
        [F = true, NV =  55, TC =  73009.86]
        Total time (m): 1.3462
        [F = true, NV =  54, TC =  70445.30]
        Total time (m): 3.3638666666666666
        [F = true, NV =  53, TC =  71405.93]
        Total time (m): 6.0562000000000005
         */

        optimize("LRC1_10_5", 72);
        /*
        Running LRC1_10_5
        [F = true, NV =  91, TC =  84165.38]
        Total time (m): 0.025500000000000002
        [F = true, NV =  90, TC =  79323.86]
        Total time (m): 0.019316666666666666
        [F = true, NV =  89, TC =  84219.77]
        Total time (m): 0.021616666666666666
        [F = true, NV =  88, TC =  80757.74]
        Total time (m): 0.04916666666666667
        [F = true, NV =  87, TC =  79115.37]
        Total time (m): 0.00475
        [F = true, NV =  86, TC =  80482.05]
        Total time (m): 0.04235
        [F = true, NV =  85, TC =  79853.59]
        Total time (m): 0.009266666666666668
        [F = true, NV =  84, TC =  78953.50]
        Total time (m): 0.0017333333333333333
        [F = true, NV =  83, TC =  76402.70]
        Total time (m): 0.09851666666666666
        [F = true, NV =  82, TC =  78364.01]
        Total time (m): 0.17225000000000001
        [F = true, NV =  81, TC =  77422.43]
        Total time (m): 0.26408333333333334
        [F = true, NV =  80, TC =  77714.72]
        Total time (m): 0.26315
        [F = true, NV =  79, TC =  74119.03]
        Total time (m): 0.29975
        [F = true, NV =  78, TC =  75335.63]
        Total time (m): 0.9656833333333333
        [F = true, NV =  77, TC =  72753.69]
        Total time (m): 0.9186333333333334
        [F = true, NV =  76, TC =  73444.86]
        Total time (m): 1.7442333333333333
        [F = true, NV =  75, TC =  70817.67]
        Total time (m): 2.1599999999999997
        [F = true, NV =  74, TC =  71954.12]
        Total time (m): 2.7966333333333333
        [F = true, NV =  73, TC =  71735.99]
        Total time (m): 8.011466666666667
        [F = true, NV =  72, TC =  70486.59]
        Total time (m): 19.290166666666668
         */

        optimize("LRC1_10_6", 68);
        /*
        Running LRC1_10_6
        [F = true, NV =  89, TC =  69392.10]
        Total time (m): 5.666666666666667E-4
        [F = true, NV =  88, TC =  75889.81]
        Total time (m): 0.020450000000000003
        [F = true, NV =  87, TC =  75025.01]
        Total time (m): 0.0036333333333333335
        [F = true, NV =  86, TC =  75344.75]
        Total time (m): 0.0144
        [F = true, NV =  85, TC =  74623.24]
        Total time (m): 0.03055
        [F = true, NV =  84, TC =  76871.16]
        Total time (m): 0.012750000000000001
        [F = true, NV =  83, TC =  74345.43]
        Total time (m): 0.04775
        [F = true, NV =  82, TC =  75776.07]
        Total time (m): 0.057300000000000004
        [F = true, NV =  81, TC =  73054.33]
        Total time (m): 0.05401666666666667
        [F = true, NV =  80, TC =  71693.57]
        Total time (m): 0.009583333333333333
        [F = true, NV =  79, TC =  69290.46]
        Total time (m): 0.17446666666666666
        [F = true, NV =  78, TC =  69977.66]
        Total time (m): 0.15088333333333334
        [F = true, NV =  77, TC =  70007.22]
        Total time (m): 0.2542666666666667
        [F = true, NV =  76, TC =  67433.70]
        Total time (m): 0.36795000000000005
        [F = true, NV =  75, TC =  71862.93]
        Total time (m): 0.55125
        [F = true, NV =  74, TC =  67143.52]
        Total time (m): 0.4250666666666667
        [F = true, NV =  73, TC =  67998.06]
        Total time (m): 0.5305333333333333
        [F = true, NV =  72, TC =  65123.48]
        Total time (m): 0.9589166666666666
        [F = true, NV =  71, TC =  64136.23]
        Total time (m): 1.1084666666666665
        [F = true, NV =  70, TC =  63521.88]
        Total time (m): 2.0874333333333333
        [F = true, NV =  69, TC =  66878.25]
        Total time (m): 2.5836166666666665
        [F = true, NV =  68, TC =  63547.91]
        Total time (m): 7.00185
         */

        optimize("LRC1_10_7", 61);
        /*
        Running LRC1_10_7
        [F = true, NV =  82, TC =  68089.18]
        Total time (m): 7.0E-4
        [F = true, NV =  81, TC =  69557.27]
        Total time (m): 7.5E-4
        [F = true, NV =  80, TC =  71950.10]
        Total time (m): 0.007166666666666667
        [F = true, NV =  79, TC =  71987.66]
        Total time (m): 0.0013
        [F = true, NV =  78, TC =  70816.28]
        Total time (m): 0.009433333333333332
        [F = true, NV =  77, TC =  69446.27]
        Total time (m): 0.0024333333333333334
        [F = true, NV =  76, TC =  71450.43]
        Total time (m): 0.013683333333333332
        [F = true, NV =  75, TC =  69731.96]
        Total time (m): 0.019016666666666668
        [F = true, NV =  74, TC =  72845.73]
        Total time (m): 0.010816666666666667
        [F = true, NV =  73, TC =  70069.41]
        Total time (m): 0.10258333333333333
        [F = true, NV =  72, TC =  69093.35]
        Total time (m): 0.18985
        [F = true, NV =  71, TC =  68709.50]
        Total time (m): 0.11405
        [F = true, NV =  70, TC =  66597.57]
        Total time (m): 0.2075
        [F = true, NV =  69, TC =  65260.82]
        Total time (m): 0.2524
        [F = true, NV =  68, TC =  65478.68]
        Total time (m): 0.20978333333333332
        [F = true, NV =  67, TC =  63345.52]
        Total time (m): 0.2695
        [F = true, NV =  66, TC =  63239.05]
        Total time (m): 0.48506666666666665
        [F = true, NV =  65, TC =  60499.10]
        Total time (m): 0.5758666666666666
        [F = true, NV =  64, TC =  62088.62]
        Total time (m): 0.6351333333333333
        [F = true, NV =  63, TC =  61531.87]
        Total time (m): 2.686783333333333
        [F = true, NV =  62, TC =  61227.59]
        Total time (m): 3.0987
        [F = true, NV =  61, TC =  59057.19]
        Total time (m): 7.121283333333333
         */

        optimize("LRC1_10_8", 56);
        /*
        Running LRC1_10_8
        [F = true, NV =  72, TC =  71162.24]
        Total time (m): 0.02115
        [F = true, NV =  71, TC =  70050.57]
        Total time (m): 0.020783333333333334
        [F = true, NV =  70, TC =  68562.11]
        Total time (m): 0.016116666666666665
        [F = true, NV =  69, TC =  71612.25]
        Total time (m): 0.06898333333333334
        [F = true, NV =  68, TC =  69346.24]
        Total time (m): 0.0025666666666666667
        [F = true, NV =  67, TC =  67506.71]
        Total time (m): 0.0848
        [F = true, NV =  66, TC =  68346.10]
        Total time (m): 0.1921
        [F = true, NV =  65, TC =  64482.53]
        Total time (m): 0.13788333333333333
        [F = true, NV =  64, TC =  66267.37]
        Total time (m): 0.2207
        [F = true, NV =  63, TC =  64944.86]
        Total time (m): 0.32883333333333337
        [F = true, NV =  62, TC =  64443.27]
        Total time (m): 0.40414999999999995
        [F = true, NV =  61, TC =  63338.67]
        Total time (m): 0.39644999999999997
        [F = true, NV =  60, TC =  63168.35]
        Total time (m): 1.0502833333333335
        [F = true, NV =  59, TC =  62395.44]
        Total time (m): 2.7298666666666667
        [F = true, NV =  58, TC =  62758.84]
        Total time (m): 5.408133333333334
        [F = true, NV =  57, TC =  59339.05]
        Total time (m): 6.88345
        [F = true, NV =  56, TC =  58865.16]
        Total time (m): 14.1037
         */

        optimize("LRC1_10_10", 48);
        /*
        Running LRC1_10_10
        [F = true, NV =  64, TC =  65861.90]
        Total time (m): 0.025183333333333332
        [F = true, NV =  63, TC =  68908.19]
        Total time (m): 0.032766666666666666
        [F = true, NV =  62, TC =  67489.13]
        Total time (m): 0.017133333333333334
        [F = true, NV =  61, TC =  63330.98]
        Total time (m): 0.04386666666666667
        [F = true, NV =  60, TC =  63317.03]
        Total time (m): 0.08091666666666668
        [F = true, NV =  59, TC =  63524.67]
        Total time (m): 0.07836666666666667
        [F = true, NV =  58, TC =  61574.01]
        Total time (m): 0.10626666666666668
        [F = true, NV =  57, TC =  63389.15]
        Total time (m): 0.21139999999999998
        [F = true, NV =  56, TC =  59864.82]
        Total time (m): 0.29359999999999997
        [F = true, NV =  55, TC =  58962.84]
        Total time (m): 0.4216333333333333
        [F = true, NV =  54, TC =  56731.13]
        Total time (m): 0.5187833333333333
        [F = true, NV =  53, TC =  57015.86]
        Total time (m): 0.7313
        [F = true, NV =  52, TC =  59389.06]
        Total time (m): 1.6686333333333332
        [F = true, NV =  51, TC =  56415.76]
        Total time (m): 2.850016666666667
        [F = true, NV =  50, TC =  55337.15]
        Total time (m): 6.502816666666666
        [F = true, NV =  49, TC =  53013.43]
        Total time (m): 12.268416666666667
        [F = true, NV =  48, TC =  53665.57]
        Total time (m): 22.623566666666665
         */

        optimize("LRC2_10_5", 17);
        /*
        Running LRC2_10_5
        [F = true, NV =  26, TC =  92296.58]
        Total time (m): 0.0394
        [F = true, NV =  25, TC =  87195.21]
        Total time (m): 0.04013333333333333
        [F = true, NV =  24, TC =  86224.84]
        Total time (m): 0.03766666666666666
        [F = true, NV =  23, TC =  86816.76]
        Total time (m): 0.03953333333333333
        [F = true, NV =  22, TC =  78256.01]
        Total time (m): 0.10644999999999999
        [F = true, NV =  21, TC =  74139.42]
        Total time (m): 0.10665
        [F = true, NV =  20, TC =  74672.49]
        Total time (m): 0.5382666666666667
        [F = true, NV =  19, TC =  73681.07]
        Total time (m): 1.03805
        [F = true, NV =  18, TC =  71438.27]
        Total time (m): 3.7258
        [F = true, NV =  17, TC =  66809.22]
        Total time (m): 53.406333333333336
         */

        //optimize("LRC1_10_1", 82);
        /* IllegalArgumentException: bound must be positive */

        //optimize("LRC1_10_9", 53);
        /* java.lang.IllegalArgumentException: bound must be positive */

        //optimize("LRC2_10_4", 11);
        /*
        Running LRC2_10_4
        [F = true, NV =  17, TC =  58994.51]
        Total time (m): 0.0060999999999999995
        [F = true, NV =  16, TC =  79838.30]
        Total time (m): 0.06381666666666667
        [F = true, NV =  15, TC =  91997.40]
        Total time (m): 4.523516666666667
        [F = true, NV =  14, TC =  86520.39]
        Total time (m): 3.9230666666666663
        [F = true, NV =  13, TC =  79557.58]
        Total time (m): 10.522416666666667
        [F = true, NV =  12, TC =  73645.22]
        Total time (m): 62.740916666666664
         */

    }

    public void optimize(String fileName, int numMin) throws Exception {
        Random random = new Random(1);
        System.out.println("Running " + fileName);
        Instance instance = Reader.getInstance(Paths.get(pdptw1000Directory, fileName + ".txt").toFile());
        Solution solutionBest = createInitialSolution(instance, random);
        GuidedEjectionSearch guidedEjectionSearch = new GuidedEjectionSearch(instance, random, 2, 1800);

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
