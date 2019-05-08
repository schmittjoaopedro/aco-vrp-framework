package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_TSP_Test {

    private String kroA100;

    private String kroA200;

    private String joinville46;

    private String joinville78;

    private String joinville125;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
        joinville46 = getClass().getClassLoader().getResource("tsp/joinville46.vrp").getFile();
        joinville78 = getClass().getClassLoader().getResource("tsp/joinville78.vrp").getFile();
        joinville125 = getClass().getClassLoader().getResource("tsp/joinville125.vrp").getFile();
    }

    @Test
    public void test_MMAS_tsp_kroA100_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(21644);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[86, 50, 60, 57, 66, 27, 92, 46, 0, 62, 5, 48, 89, 9, 83, 78, 52, 18, 74, 91, 7, 41, 88, 30, 79, 55, 96, 3, 64, 25, 65, 69, 21, 15, 87, 93, 17, 23, 37, 98, 35, 71, 20, 73, 58, 16, 14, 10, 31, 44, 90, 97, 22, 76, 59, 61, 34, 85, 26, 11, 19, 56, 8, 6, 54, 82, 33, 28, 45, 42, 2, 13, 70, 40, 99, 47, 29, 38, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 1, 53, 39, 63, 43, 49, 84, 67, 72, 68, 80, 24, 86]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(34912.34);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(2058.611546502627);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(3.46);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(59.78606060606061);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(27872);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(30490.12);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(2101.704067050008);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(2.04);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(39.24666666666667);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(25151);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(26992.86);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(2447.54197875701);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.405);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(20.26888888888889);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(22794);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(25998.3);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(2155.9319412619534);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.255);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(13.89151515151515);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(22607);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(25494.62);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(2626.871434027385);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.14);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(11.222424242424243);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(22397);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(24716.14);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(2211.3661256829873);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.02);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(8.012929292929293);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(22397);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(25051.74);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(2301.8046965392396);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.085);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(9.402626262626262);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(22224);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(24825.97);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(2447.290455758876);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.09);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(8.634343434343434);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(21987);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(24664.48);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(1896.7872171307747);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.09);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(10.067272727272726);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(21720);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(23744.51);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(2120.6563711133003);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.08);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(7.837979797979798);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(21644);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(23074.72);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(2153.7295117300523);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(1.03);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(4.929292929292929);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(21644);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(23204.91);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(1992.8477370158755);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(4.603838383838384);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(21644);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(23016.89);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(1970.5706238897837);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(4.0636363636363635);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(21644);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(35924.48);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(2293.5091083095767);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(3.44);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(63.3379797979798);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(21644);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(30778.97);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(2114.526230839687);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(2.465);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(43.66646464646465);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(21644);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(20000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(30074);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[15, 117, 178, 65, 152, 118, 91, 9, 174, 98, 18, 189, 48, 17, 109, 28, 183, 36, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 110, 116, 114, 52, 0, 131, 84, 144, 14, 122, 197, 26, 190, 39, 146, 11, 92, 148, 105, 162, 3, 100, 59, 192, 127, 166, 157, 76, 79, 64, 30, 66, 176, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 156, 106, 108, 74, 154, 182, 21, 133, 7, 16, 24, 142, 89, 33, 57, 140, 170, 199, 147, 87, 97, 113, 102, 145, 128, 82, 61, 71, 129, 38, 27, 37, 70, 195, 177, 55, 151, 4, 104, 42, 136, 132, 175, 112, 194, 181, 75, 69, 143, 101, 153, 20, 139, 163, 22, 172, 149, 90, 93, 94, 85, 138, 49, 184, 167, 53, 5, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 191, 13, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 193, 50, 15]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(48486.125);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(2214.8093011764895);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(3.2025);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(123.32412060301507);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(42069);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(42776.595);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(1802.599061872695);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(2.6075);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(87.58949748743719);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(36890);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(40208.375);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(2009.9066415917364);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.6025);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(59.64623115577889);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(34536);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(38147.37);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(2227.852104178025);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.35);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(35.89150753768844);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(32683);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(35967.375);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(2334.36235240733);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.13);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(21.369547738693466);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(31480);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(35382.735);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(2310.9974428108717);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0975);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(18.336281407035177);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(31262);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(34593.725);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(2231.00834389467);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.075);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(16.09105527638191);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(30982);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(34315.43);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(2536.726322898557);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.08);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(15.122060301507538);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(30775);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(33252.42);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(2326.9329207539354);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.0675);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(11.049447236180905);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(30529);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(33344.2);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(2441.5733723694066);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.0475);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(11.152763819095478);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(30288);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(32994.205);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(2376.5682836834394);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(1.0525);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(12.276984924623116);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(30231);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(33466.76);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(2177.278025517644);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.0525);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(12.834371859296482);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(30219);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(32617.69);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(2191.4068572425904);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.0075);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(8.615175879396984);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(30219);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(32144.79);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(2076.256867247876);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(0.9975);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(6.738944723618091);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(30219);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(33475.3);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(2504.4795184411983);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.0175);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(9.6635175879397);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(30074);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(100000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_2() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 2);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(29772);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[44, 196, 35, 56, 73, 99, 32, 155, 179, 130, 141, 68, 188, 72, 2, 58, 40, 166, 127, 192, 157, 76, 160, 124, 180, 1, 34, 168, 67, 29, 88, 153, 20, 139, 163, 101, 75, 69, 143, 22, 172, 149, 90, 94, 93, 181, 194, 112, 175, 132, 136, 42, 104, 4, 177, 151, 55, 195, 85, 138, 49, 167, 184, 61, 82, 71, 129, 38, 70, 37, 27, 147, 87, 113, 97, 199, 170, 140, 57, 33, 89, 24, 16, 142, 102, 145, 128, 133, 21, 7, 154, 182, 74, 53, 5, 108, 106, 156, 186, 150, 79, 64, 12, 176, 66, 30, 46, 119, 111, 126, 185, 134, 41, 54, 19, 63, 161, 159, 78, 14, 122, 197, 26, 190, 144, 84, 131, 0, 52, 114, 116, 110, 39, 146, 11, 45, 171, 120, 173, 158, 23, 31, 135, 60, 25, 198, 6, 81, 77, 8, 137, 123, 117, 15, 50, 193, 121, 115, 187, 43, 62, 178, 36, 183, 28, 109, 17, 48, 189, 148, 105, 92, 162, 3, 100, 59, 107, 191, 13, 174, 9, 91, 98, 18, 118, 65, 152, 47, 169, 83, 10, 51, 86, 125, 95, 165, 164, 103, 96, 80, 44]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(48085.43);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(1969.569640286857);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(3.435);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(122.0045728643216);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(42094);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(42543.735);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(2005.0004611022166);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(2.955);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(91.95592964824121);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(36709);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(39674.695);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(1800.9291158668304);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.8425);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(63.57638190954774);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(34156);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(38089.945);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(2029.7923407847761);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.3725);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(38.76497487437186);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(32091);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(36026.795);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(2309.4173737124747);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.2175);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(23.57788944723618);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(30830);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(33972.395);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(2728.608675772785);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.105);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(14.431005025125629);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(30385);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(33668.005);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(2552.630611775992);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.075);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(12.908492462311559);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(30099);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(32104.2);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(2466.7261460074933);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.06);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(8.567185929648241);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(29939);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(31781.335);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(2289.403734616368);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.04);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(7.514572864321608);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(29822);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(32218.495);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(2434.139914960316);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.0325);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(8.349547738693467);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(29772);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(31622.43);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(2354.004526686025);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(1.02);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(5.7196482412060305);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(29772);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(31264.825);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(2186.9881366675554);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(4.386934673366834);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(29772);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(30931.08);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(1858.1195615798338);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(4.012311557788944);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(29772);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(49095.915);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(2036.7721995202835);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(3.6825);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(127.30030150753768);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(29772);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(43660.67);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(2047.998582045481);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(3.01);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(95.09130653266331);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(29772);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(100000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville46_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville46));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.setShowLog(false);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(43128.98399999998);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[41, 13, 0, 24, 5, 31, 17, 25, 42, 4, 36, 21, 3, 29, 14, 6, 9, 12, 30, 11, 46, 44, 20, 19, 32, 2, 45, 35, 37, 8, 16, 15, 18, 1, 10, 39, 40, 23, 43, 27, 34, 28, 33, 7, 22, 26, 38, 41]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(67657.65721276596);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(4684.700083102606);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(2.5425531914893615);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(29.159111933395003);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(53308.636000000006);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(59559.94257446809);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(3163.075461229114);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(1.6702127659574468);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(19.609620721554116);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(47096.032);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(55574.73291489362);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(5458.074871287906);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.2340425531914894);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(11.999074930619797);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(45334.376999999986);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(56532.904106382986);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(4691.394014092897);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.148936170212766);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(13.19981498612396);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(44196.42499999999);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(55377.89821276597);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(7566.825886301677);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.0851063829787233);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(10.122109158186865);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(43827.30399999999);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(50475.78755319149);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(5849.172287326224);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0425531914893618);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(6.642923219241443);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(43827.30399999998);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(52886.30978723404);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(6817.3828681431705);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.0425531914893618);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(9.495837187789084);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(43448.40099999998);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(52101.78474468087);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(6357.415015409504);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.0851063829787233);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(8.703977798334876);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(43370.01199999998);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(52521.420319148936);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(6673.886576795708);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.0638297872340425);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(8.386679000925069);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(51285.837574468074);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(6795.2637446148665);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(6.691026827012026);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(51484.81527659574);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(6269.721402734385);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.9787234042553191);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(8.017576318223867);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(51122.94021276596);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(5726.949303219115);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(0.9787234042553191);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(7.23404255319149);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(70391.3184680851);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(4793.659710530272);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(2.1914893617021276);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(31.853839037927845);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(64471.635170212765);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(4410.035120219301);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(2.0106382978723403);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(23.178538390379277);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(43128.98399999998);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(57072.32708510639);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(4667.244602311279);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.4680851063829787);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(14.842738205365402);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(43128.98399999998);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(5000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville78_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville78));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.setShowLog(false);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(54915.86299999997);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[18, 76, 50, 63, 49, 60, 30, 78, 12, 9, 14, 6, 29, 66, 3, 21, 36, 42, 4, 17, 56, 19, 45, 57, 2, 32, 22, 51, 26, 38, 64, 13, 71, 72, 0, 74, 41, 24, 5, 31, 25, 11, 46, 44, 58, 20, 77, 35, 37, 8, 48, 16, 67, 68, 15, 52, 70, 23, 27, 53, 62, 34, 28, 33, 59, 69, 7, 54, 65, 43, 73, 40, 47, 75, 39, 55, 61, 1, 10, 18]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(85350.62431645568);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(4940.29204487323);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(2.5063291139240507);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(47.60434923726063);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(67795.32999999999);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(76947.5685949367);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(5159.879477709407);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(1.6075949367088607);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(30.37909769555339);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(61428.682);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(69041.24251898735);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(5629.9013469224155);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.240506329113924);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(17.26809477442389);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(57532.07899999996);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(66502.88230379745);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(5256.3647656999265);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.1772151898734178);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(14.236611489776047);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(56062.64099999999);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(64589.27443037973);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(6678.730991398491);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.1012658227848102);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(10.930217461863032);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(55699.58099999998);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(63215.75630379744);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(6921.0977641923155);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0886075949367089);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(10.12203829925349);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(55237.742999999966);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(65702.3188734177);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(7391.728084399606);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.0506329113924051);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(10.93313859136644);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(55183.33099999997);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(65755.894278481);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(7121.621988893747);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.0253164556962024);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(8.253489126906848);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(63607.99893670884);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(7359.259202165535);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(0.9936708860759493);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(7.5806556312885425);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(64019.425405063266);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(7115.686610090302);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(0.9936708860759493);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(7.681596884128529);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(86955.10156962027);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(5685.638695496553);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(2.7848101265822787);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(50.82570593962999);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(77225.79862025315);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(4404.689791329349);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(2.107594936708861);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(35.570269393054204);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(70784.48050632908);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(4686.684280984018);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.3227848101265822);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(20.323271665043816);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(69073.10296202534);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(5925.0961589288945);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(1.2025316455696202);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(15.19409282700422);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(54915.86299999997);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(67207.90113924051);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(5464.411439882232);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.1962025316455696);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(15.609866926322622);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(54915.86299999997);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(10000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville125_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville125));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.setShowLog(false);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(70165.09999999999);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[88, 56, 17, 87, 106, 42, 4, 36, 115, 3, 21, 11, 46, 37, 35, 8, 48, 16, 67, 120, 68, 82, 15, 81, 111, 85, 114, 57, 2, 117, 123, 113, 22, 79, 92, 32, 95, 45, 77, 84, 58, 44, 20, 25, 86, 124, 29, 66, 49, 14, 6, 9, 12, 78, 30, 60, 63, 50, 80, 107, 76, 89, 18, 122, 1, 10, 121, 112, 61, 101, 118, 52, 103, 23, 70, 105, 109, 55, 39, 75, 47, 40, 119, 73, 108, 43, 53, 62, 54, 7, 69, 99, 59, 100, 33, 104, 28, 102, 34, 27, 97, 65, 96, 116, 90, 51, 26, 38, 93, 64, 13, 94, 71, 72, 0, 91, 83, 74, 41, 24, 5, 31, 98, 19, 110, 88]");

        // Iteration 100
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationMean()).isEqualTo(108532.249496);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getIterationSd()).isEqualTo(4976.961313182312);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(3.016);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getDiversity()).isEqualTo(79.35948387096774);
        assertThat(mmas_tsp.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(92973.96);

        // Iteration 200
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationMean()).isEqualTo(98003.21864);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getIterationSd()).isEqualTo(5354.646731892274);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(1.92);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getDiversity()).isEqualTo(52.343870967741935);
        assertThat(mmas_tsp.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(83356.056);

        // Iteration 300
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationMean()).isEqualTo(95917.58785600006);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getIterationSd()).isEqualTo(5193.596108913341);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.352);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getDiversity()).isEqualTo(37.05509677419355);
        assertThat(mmas_tsp.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(78712.40899999997);

        // Iteration 400
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationMean()).isEqualTo(92451.79386399998);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getIterationSd()).isEqualTo(8066.601839894845);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.088);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getDiversity()).isEqualTo(22.68967741935484);
        assertThat(mmas_tsp.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(76100.18100000003);

        // Iteration 500
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationMean()).isEqualTo(90974.25883199999);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getIterationSd()).isEqualTo(7964.58556070573);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.068);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getDiversity()).isEqualTo(20.631225806451614);
        assertThat(mmas_tsp.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(73219.27700000002);

        // Iteration 600
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationMean()).isEqualTo(90830.56023999999);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getIterationSd()).isEqualTo(9106.665037738252);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getDiversity()).isEqualTo(16.293032258064517);
        assertThat(mmas_tsp.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(73219.27700000002);

        // Iteration 700
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationMean()).isEqualTo(89377.33668799998);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getIterationSd()).isEqualTo(8146.604611412347);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.044);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getDiversity()).isEqualTo(19.137290322580647);
        assertThat(mmas_tsp.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(72941.13799999999);

        // Iteration 800
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationMean()).isEqualTo(88714.741088);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getIterationSd()).isEqualTo(8092.14973516183);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.016);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getDiversity()).isEqualTo(17.905806451612904);
        assertThat(mmas_tsp.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(72110.27900000001);

        // Iteration 900
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationMean()).isEqualTo(88584.51141600001);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getIterationSd()).isEqualTo(8712.928512369455);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.016);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getDiversity()).isEqualTo(16.68541935483871);
        assertThat(mmas_tsp.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(71702.153);

        // Iteration 1000
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationMean()).isEqualTo(87852.08492000001);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getIterationSd()).isEqualTo(7731.33386684917);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(0.988);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getDiversity()).isEqualTo(17.94374193548387);
        assertThat(mmas_tsp.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(71266.072);

        // Iteration 1100
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationMean()).isEqualTo(90616.01916799998);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getIterationSd()).isEqualTo(9441.246364618604);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.992);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getDiversity()).isEqualTo(17.979225806451613);
        assertThat(mmas_tsp.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(70929.036);

        // Iteration 1200
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationMean()).isEqualTo(89064.05275199999);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getIterationSd()).isEqualTo(8804.023320925711);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.016);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getDiversity()).isEqualTo(18.046064516129032);
        assertThat(mmas_tsp.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(70831.957);

        // Iteration 1300
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationMean()).isEqualTo(85273.46488799999);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getIterationSd()).isEqualTo(7849.953727879989);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.024);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getDiversity()).isEqualTo(15.479870967741935);
        assertThat(mmas_tsp.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(70824.447);

        // Iteration 1400
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationMean()).isEqualTo(85993.96110399997);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getIterationSd()).isEqualTo(7465.063524185982);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(1.036);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getDiversity()).isEqualTo(17.800387096774195);
        assertThat(mmas_tsp.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(70517.03299999995);

        // Iteration 1500
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationMean()).isEqualTo(85247.49925600004);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getIterationSd()).isEqualTo(8026.2488501207245);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.064);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getDiversity()).isEqualTo(17.383612903225806);
        assertThat(mmas_tsp.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(70165.09999999999);

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(50000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

}
