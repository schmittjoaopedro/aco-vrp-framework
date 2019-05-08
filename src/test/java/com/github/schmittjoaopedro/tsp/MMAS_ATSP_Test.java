package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_ATSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_ATSP_Test {

    private String kroA100;

    private String kroA200;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
    }

    @Test
    public void test_MMAS_ATSP_kroA100_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_ATSP mmas_atsp = new MMAS_ATSP(graph, 0.02, 1500, 1);
        mmas_atsp.setStatisticInterval(100);
        mmas_atsp.run();
        assertThat(mmas_atsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(21793);
        assertThat(TestUtils.getTourString(mmas_atsp.getGlobalStatistics())).isEqualTo("[65, 69, 93, 21, 87, 15, 52, 78, 17, 23, 37, 98, 35, 83, 9, 89, 48, 5, 62, 0, 27, 92, 66, 57, 60, 50, 86, 24, 80, 84, 67, 72, 68, 63, 39, 53, 1, 43, 49, 81, 94, 12, 75, 32, 36, 4, 77, 51, 95, 38, 29, 47, 99, 70, 40, 13, 2, 42, 45, 28, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 97, 90, 44, 31, 10, 14, 16, 58, 73, 20, 71, 46, 91, 7, 41, 88, 30, 79, 55, 96, 74, 18, 3, 64, 25, 65]");

        // Iteration 100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIteration())).isEqualTo(100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationMean())).isEqualTo(36566.59);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationSd())).isEqualTo(2373.11);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBranchFactor())).isEqualTo(2.17);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getDiversity())).isEqualTo(65.31);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBestSoFar())).isEqualTo(30137);

        // Iteration 200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIteration())).isEqualTo(200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationMean())).isEqualTo(31941.97);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationSd())).isEqualTo(1942.77);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBranchFactor())).isEqualTo(2.13);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getDiversity())).isEqualTo(47.98);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBestSoFar())).isEqualTo(26240);

        // Iteration 300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIteration())).isEqualTo(300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationMean())).isEqualTo(28664.19);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationSd())).isEqualTo(1959.48);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBranchFactor())).isEqualTo(1.31);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getDiversity())).isEqualTo(26.19);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBestSoFar())).isEqualTo(23994);

        // Iteration 400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIteration())).isEqualTo(400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationMean())).isEqualTo(26542.16);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationSd())).isEqualTo(2504.01);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBranchFactor())).isEqualTo(0.84);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getDiversity())).isEqualTo(13.61);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBestSoFar())).isEqualTo(22600);

        // Iteration 500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIteration())).isEqualTo(500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationMean())).isEqualTo(26619.46);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationSd())).isEqualTo(2655.63);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBranchFactor())).isEqualTo(0.67);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getDiversity())).isEqualTo(10.33);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBestSoFar())).isEqualTo(22410);

        // Iteration 600
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIteration())).isEqualTo(600);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationMean())).isEqualTo(26573.48);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationSd())).isEqualTo(2379.63);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getDiversity())).isEqualTo(10.19);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBestSoFar())).isEqualTo(22410);

        // Iteration 700
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIteration())).isEqualTo(700);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationMean())).isEqualTo(26753.01);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationSd())).isEqualTo(2462.80);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBranchFactor())).isEqualTo(0.51);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getDiversity())).isEqualTo(12.10);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBestSoFar())).isEqualTo(22410);

        // Iteration 800
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIteration())).isEqualTo(800);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationMean())).isEqualTo(37594.92);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationSd())).isEqualTo(2010.58);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBranchFactor())).isEqualTo(2.63);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getDiversity())).isEqualTo(68.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBestSoFar())).isEqualTo(22410);

        // Iteration 900
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIteration())).isEqualTo(900);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationMean())).isEqualTo(33185.41);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationSd())).isEqualTo(2153.37);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBranchFactor())).isEqualTo(1.71);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getDiversity())).isEqualTo(51.13);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBestSoFar())).isEqualTo(22410);

        // Iteration 1000
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIteration())).isEqualTo(1000);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationMean())).isEqualTo(29591.47);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationSd())).isEqualTo(2351.35);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBranchFactor())).isEqualTo(1.32);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getDiversity())).isEqualTo(25.54);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBestSoFar())).isEqualTo(22410);

        // Iteration 1100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIteration())).isEqualTo(1100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationMean())).isEqualTo(26871.16);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationSd())).isEqualTo(2444.69);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBranchFactor())).isEqualTo(0.82);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getDiversity())).isEqualTo(12.19);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBestSoFar())).isEqualTo(22410);

        // Iteration 1200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIteration())).isEqualTo(1200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationMean())).isEqualTo(26607.89);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationSd())).isEqualTo(2306.22);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBranchFactor())).isEqualTo(0.67);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getDiversity())).isEqualTo(9.53);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBestSoFar())).isEqualTo(22410);

        // Iteration 1300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIteration())).isEqualTo(1300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationMean())).isEqualTo(25546.57);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationSd())).isEqualTo(2411.00);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBranchFactor())).isEqualTo(0.62);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getDiversity())).isEqualTo(8.28);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBestSoFar())).isEqualTo(22410);

        // Iteration 1400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIteration())).isEqualTo(1400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationMean())).isEqualTo(25122.11);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationSd())).isEqualTo(2551.38);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBranchFactor())).isEqualTo(0.63);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getDiversity())).isEqualTo(9.16);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBestSoFar())).isEqualTo(22093);

        // Iteration 1500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIteration())).isEqualTo(1500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationMean())).isEqualTo(26145.84);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationSd())).isEqualTo(2691.91);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBranchFactor())).isEqualTo(0.60);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getDiversity())).isEqualTo(10.44);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBestSoFar())).isEqualTo(21793);

        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(15000);
        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_atsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_ATSP_kroA200_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ATSP mmas_atsp = new MMAS_ATSP(graph, 0.02, 1500, 1);
        mmas_atsp.setStatisticInterval(100);
        mmas_atsp.run();
        assertThat(mmas_atsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(30670);
        assertThat(TestUtils.getTourString(mmas_atsp.getGlobalStatistics())).isEqualTo("[153, 139, 20, 163, 22, 172, 167, 184, 61, 82, 71, 129, 70, 195, 4, 104, 42, 136, 132, 175, 112, 194, 181, 75, 69, 101, 143, 149, 90, 93, 94, 49, 138, 85, 177, 151, 55, 37, 38, 27, 147, 87, 199, 140, 57, 170, 97, 113, 128, 145, 102, 33, 89, 142, 24, 16, 7, 21, 133, 74, 182, 154, 111, 126, 185, 134, 41, 54, 19, 63, 161, 66, 30, 46, 119, 156, 108, 106, 5, 53, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 166, 127, 192, 157, 76, 79, 64, 176, 12, 78, 159, 14, 122, 197, 26, 190, 144, 84, 131, 0, 52, 114, 116, 110, 39, 146, 11, 105, 148, 18, 98, 174, 9, 91, 118, 103, 164, 165, 95, 125, 86, 51, 10, 83, 169, 47, 152, 65, 178, 50, 193, 121, 115, 187, 43, 62, 15, 117, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 45, 171, 28, 183, 36, 109, 17, 48, 189, 92, 162, 3, 100, 59, 107, 191, 13, 35, 56, 73, 155, 99, 32, 44, 196, 80, 96, 179, 130, 141, 68, 188, 72, 2, 58, 40, 88, 153]");

        // Iteration 100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIteration())).isEqualTo(100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationMean())).isEqualTo(50697.22);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationSd())).isEqualTo(2174.36);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBranchFactor())).isEqualTo(2.58);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getDiversity())).isEqualTo(133.43);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBestSoFar())).isEqualTo(43764);

        // Iteration 200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIteration())).isEqualTo(200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationMean())).isEqualTo(45498.00);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationSd())).isEqualTo(2115.46);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBranchFactor())).isEqualTo(1.98);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getDiversity())).isEqualTo(102.43);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBestSoFar())).isEqualTo(39457);

        // Iteration 300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIteration())).isEqualTo(300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationMean())).isEqualTo(41152.72);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationSd())).isEqualTo(1900.58);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBranchFactor())).isEqualTo(1.68);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getDiversity())).isEqualTo(65.54);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBestSoFar())).isEqualTo(35311);

        // Iteration 400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIteration())).isEqualTo(400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationMean())).isEqualTo(38695.49);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationSd())).isEqualTo(1963.72);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBranchFactor())).isEqualTo(1.04);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getDiversity())).isEqualTo(35.16);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBestSoFar())).isEqualTo(33568);

        // Iteration 500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIteration())).isEqualTo(500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationMean())).isEqualTo(36553.57);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationSd())).isEqualTo(2515.79);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBranchFactor())).isEqualTo(0.72);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getDiversity())).isEqualTo(22.88);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBestSoFar())).isEqualTo(32479);

        // Iteration 600
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIteration())).isEqualTo(600);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationMean())).isEqualTo(36065.93);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationSd())).isEqualTo(2548.04);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBranchFactor())).isEqualTo(0.61);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getDiversity())).isEqualTo(16.32);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBestSoFar())).isEqualTo(32178);

        // Iteration 700
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIteration())).isEqualTo(700);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationMean())).isEqualTo(35565.70);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationSd())).isEqualTo(2688.84);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBranchFactor())).isEqualTo(0.58);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getDiversity())).isEqualTo(11.98);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBestSoFar())).isEqualTo(31881);

        // Iteration 800
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIteration())).isEqualTo(800);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationMean())).isEqualTo(34766.35);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationSd())).isEqualTo(2898.10);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getDiversity())).isEqualTo(9.95);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBestSoFar())).isEqualTo(31597);

        // Iteration 900
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIteration())).isEqualTo(900);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationMean())).isEqualTo(35386.61);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationSd())).isEqualTo(2809.48);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBranchFactor())).isEqualTo(0.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getDiversity())).isEqualTo(10.31);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBestSoFar())).isEqualTo(31460);

        // Iteration 1000
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIteration())).isEqualTo(1000);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationMean())).isEqualTo(34074.23);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationSd())).isEqualTo(2586.69);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBranchFactor())).isEqualTo(0.54);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getDiversity())).isEqualTo(9.12);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBestSoFar())).isEqualTo(31394);

        // Iteration 1100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIteration())).isEqualTo(1100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationMean())).isEqualTo(35041.52);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationSd())).isEqualTo(2418.62);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getDiversity())).isEqualTo(11.77);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBestSoFar())).isEqualTo(31024);

        // Iteration 1200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIteration())).isEqualTo(1200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationMean())).isEqualTo(33883.20);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationSd())).isEqualTo(2566.51);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBranchFactor())).isEqualTo(0.53);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getDiversity())).isEqualTo(8.92);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBestSoFar())).isEqualTo(31019);

        // Iteration 1300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIteration())).isEqualTo(1300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationMean())).isEqualTo(34086.57);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationSd())).isEqualTo(2886.98);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBranchFactor())).isEqualTo(0.54);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getDiversity())).isEqualTo(9.41);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBestSoFar())).isEqualTo(30997);

        // Iteration 1400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIteration())).isEqualTo(1400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationMean())).isEqualTo(33934.28);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationSd())).isEqualTo(2664.33);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBranchFactor())).isEqualTo(0.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getDiversity())).isEqualTo(10.04);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBestSoFar())).isEqualTo(30733);

        // Iteration 1500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIteration())).isEqualTo(1500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationMean())).isEqualTo(33907.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationSd())).isEqualTo(2678.39);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBranchFactor())).isEqualTo(0.53);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getDiversity())).isEqualTo(9.88);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBestSoFar())).isEqualTo(30670);

        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(70000);
        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_atsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_ATSP_kroA200_with_seed_2() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ATSP mmas_atsp = new MMAS_ATSP(graph, 0.02, 1500, 2);
        mmas_atsp.setStatisticInterval(100);
        mmas_atsp.run();
        assertThat(mmas_atsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(30958.0);
        assertThat(TestUtils.getTourString(mmas_atsp.getGlobalStatistics())).isEqualTo("[44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 178, 36, 183, 28, 109, 17, 48, 189, 148, 105, 92, 162, 3, 100, 59, 192, 127, 166, 40, 58, 2, 72, 88, 163, 139, 20, 153, 101, 75, 69, 143, 22, 172, 149, 90, 85, 138, 71, 129, 128, 145, 16, 24, 142, 89, 33, 102, 97, 113, 57, 140, 170, 199, 87, 147, 27, 38, 37, 70, 55, 151, 177, 195, 4, 104, 42, 136, 132, 175, 112, 194, 181, 93, 94, 49, 184, 167, 61, 82, 133, 21, 7, 154, 182, 74, 53, 5, 108, 106, 156, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 157, 76, 79, 64, 78, 159, 161, 19, 63, 54, 185, 134, 41, 126, 111, 119, 46, 30, 66, 176, 12, 14, 122, 197, 26, 190, 144, 84, 131, 110, 116, 114, 52, 0, 39, 146, 11, 45, 171, 120, 173, 158, 23, 31, 135, 60, 25, 198, 6, 81, 77, 8, 137, 123, 117, 15, 50, 193, 152, 65, 118, 91, 18, 98, 9, 174, 13, 191, 107, 68, 141, 188, 130, 179, 73, 56, 35, 155, 99, 32, 44]");

        // Iteration 100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIteration())).isEqualTo(100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationMean())).isEqualTo(50289.73);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getIterationSd())).isEqualTo(2240.07);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBranchFactor())).isEqualTo(2.44);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getDiversity())).isEqualTo(132.24);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(0).getBestSoFar())).isEqualTo(43322.00);

        // Iteration 200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIteration())).isEqualTo(200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationMean())).isEqualTo(44674.45);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getIterationSd())).isEqualTo(1860.33);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBranchFactor())).isEqualTo(2.26);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getDiversity())).isEqualTo(101.68);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(1).getBestSoFar())).isEqualTo(38131);

        // Iteration 300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIteration())).isEqualTo(300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationMean())).isEqualTo(42017.40);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getIterationSd())).isEqualTo(1946.96);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBranchFactor())).isEqualTo(1.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getDiversity())).isEqualTo(61.04);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(2).getBestSoFar())).isEqualTo(36442);

        // Iteration 400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIteration())).isEqualTo(400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationMean())).isEqualTo(39469.53);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getIterationSd())).isEqualTo(1840.06);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBranchFactor())).isEqualTo(0.95);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getDiversity())).isEqualTo(30.04);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(3).getBestSoFar())).isEqualTo(34474);

        // Iteration 500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIteration())).isEqualTo(500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationMean())).isEqualTo(37924.21);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getIterationSd())).isEqualTo(2342.01);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBranchFactor())).isEqualTo(0.66);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getDiversity())).isEqualTo(17.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(4).getBestSoFar())).isEqualTo(33566);

        // Iteration 600
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIteration())).isEqualTo(600);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationMean())).isEqualTo(37152.87);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getIterationSd())).isEqualTo(2329.11);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBranchFactor())).isEqualTo(0.59);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getDiversity())).isEqualTo(14.18);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(5).getBestSoFar())).isEqualTo(33152);

        // Iteration 700
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIteration())).isEqualTo(700);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationMean())).isEqualTo(36230.41);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getIterationSd())).isEqualTo(2439.35);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getDiversity())).isEqualTo(13.43);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(6).getBestSoFar())).isEqualTo(32748);

        // Iteration 800
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIteration())).isEqualTo(800);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationMean())).isEqualTo(36793.63);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getIterationSd())).isEqualTo(2250.51);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBranchFactor())).isEqualTo(0.58);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getDiversity())).isEqualTo(20.28);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(7).getBestSoFar())).isEqualTo(32291);

        // Iteration 900
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIteration())).isEqualTo(900);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationMean())).isEqualTo(35987.97);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getIterationSd())).isEqualTo(2411.73);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getDiversity())).isEqualTo(13.06);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(8).getBestSoFar())).isEqualTo(31698);

        // Iteration 1000
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIteration())).isEqualTo(1000);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationMean())).isEqualTo(35805.74);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getIterationSd())).isEqualTo(2630.17);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getDiversity())).isEqualTo(14.14);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(9).getBestSoFar())).isEqualTo(31506);

        // Iteration 1100
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIteration())).isEqualTo(1100);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationMean())).isEqualTo(34361.80);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getIterationSd())).isEqualTo(2696.75);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBranchFactor())).isEqualTo(0.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getDiversity())).isEqualTo(9.20);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(10).getBestSoFar())).isEqualTo(31392);

        // Iteration 1200
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIteration())).isEqualTo(1200);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationMean())).isEqualTo(34748.94);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getIterationSd())).isEqualTo(2477.74);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBranchFactor())).isEqualTo(0.56);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getDiversity())).isEqualTo(11.80);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(11).getBestSoFar())).isEqualTo(31273);

        // Iteration 1300
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIteration())).isEqualTo(1300);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationMean())).isEqualTo(33657.35);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getIterationSd())).isEqualTo(2466.91);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBranchFactor())).isEqualTo(0.54);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getDiversity())).isEqualTo(8.32);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(12).getBestSoFar())).isEqualTo(31154);

        // Iteration 1400
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIteration())).isEqualTo(1400);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationMean())).isEqualTo(34612.47);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getIterationSd())).isEqualTo(2087.44);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBranchFactor())).isEqualTo(0.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getDiversity())).isEqualTo(12.00);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(13).getBestSoFar())).isEqualTo(31012);

        // Iteration 1500
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIteration())).isEqualTo(1500);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationMean())).isEqualTo(34747.95);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getIterationSd())).isEqualTo(2362.90);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBranchFactor())).isEqualTo(0.55);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getDiversity())).isEqualTo(11.11);
        assertThat(TestUtils.round(mmas_atsp.getIterationStatistics().get(14).getBestSoFar())).isEqualTo(30958);

        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(70000);
        assertThat(mmas_atsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_atsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

}
