package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.graph.Vertex;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_TSP_Test {

    private String kroA100;

    private String kroA200;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
    }

    @Test
    public void test_MMAS_tsp_kroA100_with_seed_1() {
        MMAS_TSP mmas_tsp = new MMAS_TSP(kroA100, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFar()).isEqualTo(21644);
        assertThat(getTourString(mmas_tsp)).isEqualTo("[86, 50, 60, 57, 66, 27, 92, 46, 0, 62, 5, 48, 89, 9, 83, 78, 52, 18, 74, 91, 7, 41, 88, 30, 79, 55, 96, 3, 64, 25, 65, 69, 21, 15, 87, 93, 17, 23, 37, 98, 35, 71, 20, 73, 58, 16, 14, 10, 31, 44, 90, 97, 22, 76, 59, 61, 34, 85, 26, 11, 19, 56, 8, 6, 54, 82, 33, 28, 45, 42, 2, 13, 70, 40, 99, 47, 29, 38, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 1, 53, 39, 63, 43, 49, 84, 67, 72, 68, 80, 24, 86]");

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

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(10000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_1() {
        MMAS_TSP mmas_tsp = new MMAS_TSP(kroA200, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFar()).isEqualTo(30074);
        assertThat(getTourString(mmas_tsp)).isEqualTo("[15, 117, 178, 65, 152, 118, 91, 9, 174, 98, 18, 189, 48, 17, 109, 28, 183, 36, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 110, 116, 114, 52, 0, 131, 84, 144, 14, 122, 197, 26, 190, 39, 146, 11, 92, 148, 105, 162, 3, 100, 59, 192, 127, 166, 157, 76, 79, 64, 30, 66, 176, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 156, 106, 108, 74, 154, 182, 21, 133, 7, 16, 24, 142, 89, 33, 57, 140, 170, 199, 147, 87, 97, 113, 102, 145, 128, 82, 61, 71, 129, 38, 27, 37, 70, 195, 177, 55, 151, 4, 104, 42, 136, 132, 175, 112, 194, 181, 75, 69, 143, 101, 153, 20, 139, 163, 22, 172, 149, 90, 93, 94, 85, 138, 49, 184, 167, 53, 5, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 191, 13, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 193, 50, 15]");

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

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(40000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_2() {
        MMAS_TSP mmas_tsp = new MMAS_TSP(kroA200, 0.02, 1500, 2);
        mmas_tsp.setStatisticInterval(100);
        mmas_tsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFar()).isEqualTo(29772);
        assertThat(getTourString(mmas_tsp)).isEqualTo("[44, 196, 35, 56, 73, 99, 32, 155, 179, 130, 141, 68, 188, 72, 2, 58, 40, 166, 127, 192, 157, 76, 160, 124, 180, 1, 34, 168, 67, 29, 88, 153, 20, 139, 163, 101, 75, 69, 143, 22, 172, 149, 90, 94, 93, 181, 194, 112, 175, 132, 136, 42, 104, 4, 177, 151, 55, 195, 85, 138, 49, 167, 184, 61, 82, 71, 129, 38, 70, 37, 27, 147, 87, 113, 97, 199, 170, 140, 57, 33, 89, 24, 16, 142, 102, 145, 128, 133, 21, 7, 154, 182, 74, 53, 5, 108, 106, 156, 186, 150, 79, 64, 12, 176, 66, 30, 46, 119, 111, 126, 185, 134, 41, 54, 19, 63, 161, 159, 78, 14, 122, 197, 26, 190, 144, 84, 131, 0, 52, 114, 116, 110, 39, 146, 11, 45, 171, 120, 173, 158, 23, 31, 135, 60, 25, 198, 6, 81, 77, 8, 137, 123, 117, 15, 50, 193, 121, 115, 187, 43, 62, 178, 36, 183, 28, 109, 17, 48, 189, 148, 105, 92, 162, 3, 100, 59, 107, 191, 13, 174, 9, 91, 98, 18, 118, 65, 152, 47, 169, 83, 10, 51, 86, 125, 95, 165, 164, 103, 96, 80, 44]");

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

        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(40000);
        assertThat(mmas_tsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    private String getTourString(MMAS_TSP mmas_tsp) {
        StringBuilder tour = new StringBuilder("[");
        for (Vertex vertex : mmas_tsp.getGlobalStatistics().getBestRoute()) {
            tour.append(vertex.getId()).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

}
