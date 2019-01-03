package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP_3OPT;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_TSP_3OPT_Test {

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
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 1);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.run();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(21282);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[84, 81, 94, 12, 75, 32, 36, 4, 51, 77, 95, 38, 29, 47, 99, 40, 70, 13, 2, 42, 45, 28, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 97, 90, 44, 31, 10, 14, 16, 58, 73, 20, 71, 9, 83, 35, 98, 37, 23, 17, 78, 52, 87, 15, 93, 21, 69, 65, 25, 64, 3, 96, 55, 79, 30, 88, 41, 7, 91, 74, 18, 89, 48, 5, 62, 0, 46, 92, 27, 66, 57, 60, 50, 86, 24, 80, 68, 63, 39, 53, 1, 43, 49, 72, 67, 84]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(21357.04);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(188.95909610283383);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(4.59);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(21282);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(21397.12);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(290.1948081318249);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(5.4);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(21282);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(21377.68);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(263.9445522579821);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(4.8966666666666665);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(21282);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(21344.56);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(118.0985040831029);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(3.8033333333333332);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(21282);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(21382.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(208.03759275669387);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(6.906666666666666);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(21282);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(21415.2);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(235.12124531824003);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(8.613333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(21282);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(21355.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(152.04986024327678);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(4.513333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(21282);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(21354.68);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(186.38774995512262);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(4.55);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(21282);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(21351.8);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(123.87661872470798);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(7.55);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(21282);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(21408.56);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(213.08215942839198);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(6.713333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(21282);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(21355.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(178.72323109582965);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(4.403333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(21282);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(21337.28);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(140.55648923712724);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(3.15);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(21282);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(21382.64);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(192.15073943825112);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(7.493333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(21282);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(21331.44);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(77.51563283192195);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(4.773333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(21282);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(21377.28);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(183.11665680652865);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(0.995);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(6.056666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(21282);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(20000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 1);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.run();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(29368);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[39, 131, 110, 116, 114, 52, 0, 84, 144, 190, 26, 197, 122, 14, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 30, 66, 176, 64, 79, 76, 157, 192, 127, 166, 29, 67, 168, 34, 1, 180, 124, 160, 150, 186, 156, 106, 108, 5, 53, 74, 182, 154, 7, 21, 133, 128, 145, 102, 142, 16, 24, 89, 33, 57, 140, 170, 199, 97, 113, 87, 147, 27, 38, 37, 70, 129, 71, 82, 61, 184, 167, 49, 138, 85, 195, 55, 151, 177, 4, 104, 42, 136, 132, 175, 112, 194, 181, 93, 94, 90, 149, 172, 22, 143, 69, 75, 101, 163, 139, 20, 153, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 13, 191, 59, 100, 3, 162, 92, 105, 148, 189, 48, 17, 109, 28, 183, 36, 178, 152, 65, 118, 18, 98, 91, 9, 174, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 193, 50, 15, 117, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 11, 146, 39]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(29396.4);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(141.99999999999994);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(1.12);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(29368);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(29459.28);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(182.69521978785687);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(7.43);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(29368);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(29495.56);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(244.24425615900705);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(12.753333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(29368);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(29432.44);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(180.35106505553736);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(6.886666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(29368);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(29428.32);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(164.19315860696912);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(6.746666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(29368);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(29588.4);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(401.5161889637826);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(11.853333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(29368);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(29441.6);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(146.777325678503);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(8.333333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(29368);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(29447.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(200.8954786283986);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(6.2);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(29368);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(29451.24);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(208.03030227990024);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(7.253333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(29368);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(29482.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(195.37009324186064);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(9.61);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(29368);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(29424.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(116.7789650008368);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(5.076666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(29368);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(29477.6);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(204.37934500987782);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(7.526666666666666);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(29368);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(29422.92);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(152.9010900331758);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(7.023333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(29368);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(29455.04);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(185.9837537707707);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(7.676666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(29368);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(29385.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(64.5148045025326);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(3.3233333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(29368);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(100000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_2() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 2);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.run();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(29368);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[139, 20, 153, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 13, 191, 59, 100, 3, 162, 92, 105, 148, 189, 48, 17, 109, 28, 183, 36, 178, 152, 65, 118, 18, 98, 91, 9, 174, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 193, 50, 15, 117, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 11, 146, 39, 131, 110, 116, 114, 52, 0, 84, 144, 190, 26, 197, 122, 14, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 30, 66, 176, 64, 79, 76, 157, 192, 127, 166, 29, 67, 168, 34, 1, 180, 124, 160, 150, 186, 156, 106, 108, 5, 53, 74, 182, 154, 7, 21, 133, 128, 145, 102, 142, 16, 24, 89, 33, 57, 140, 170, 199, 97, 113, 87, 147, 27, 38, 37, 70, 129, 71, 82, 61, 184, 167, 49, 138, 85, 195, 55, 151, 177, 4, 104, 42, 136, 132, 175, 112, 194, 181, 93, 94, 90, 149, 172, 22, 143, 69, 75, 101, 163, 139]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(29453.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(194.56997027633358);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(9.36);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(29368);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(29382.8);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(35.16745654721138);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(3.203333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(29368);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(29467.12);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(257.56573918128163);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(6.683333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(29368);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(29470.88);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(171.39606179839723);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(8.093333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(29368);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(29454.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(255.3929456086574);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(6.99);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(29368);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(29561.04);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(306.5911338139662);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(17.253333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(29368);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(29468.2);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(246.2969075458858);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(6.876666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(29368);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(29458.48);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(181.52183523385457);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(8.716666666666667);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(29368);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(29502.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(251.7653801988404);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(10.663333333333334);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(29368);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(29490.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(181.52592101405244);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(11.773333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(29368);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(29428.84);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(153.87405022723402);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(4.793333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(29368);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(29435.8);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(131.95485339059465);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(8.11);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(29368);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(29487.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(170.9207126126029);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(10.213333333333333);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(29368);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(29404.36);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(106.65086341266384);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(5.4366666666666665);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(29368);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(29482.36);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(234.69978696198257);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(1.0);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(12.6);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(29368);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(100000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville46_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville46));
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 1);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.setShowLog(true);
        mmas_tsp_3opt.run();
        mmas_tsp_3opt.roundStatistics();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(42926.52299999999);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[38, 13, 0, 41, 24, 5, 31, 17, 25, 42, 4, 36, 3, 21, 29, 14, 6, 9, 12, 30, 11, 46, 44, 20, 19, 32, 2, 45, 35, 37, 8, 16, 18, 1, 10, 39, 40, 23, 15, 43, 27, 34, 28, 33, 7, 22, 26, 38]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(46539.28);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(5539.94);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(9.60);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(45249.93);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(3282.93);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(12.41);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(45727.83);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(4702.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(10.61);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(44801.28);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(3512.19);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(12.47);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(44315.67);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(3094.83);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(8.86);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(44593.51);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(2878.04);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(10.62);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(44798.19);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(4365.93);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(9.42);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(44453.05);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(2964.83);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(10.02);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(47044.34);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(5796.51);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(12.03);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(46008.59);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(4725.25);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(12.24);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(44852.64);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(3134.83);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(12.43);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(44515.76);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(3121.64);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(10.87);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(46502.97);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(5028.82);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(12.00);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(44498.92);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(3057.84);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(12.19);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(42926.52);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(45890.23);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(4779.48);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(0.979);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(11.74);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(42926.52);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(25000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville78_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville78));
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 1);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.setShowLog(true);
        mmas_tsp_3opt.run();
        mmas_tsp_3opt.roundStatistics();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(52497.89099999997);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[37, 77, 32, 57, 2, 22, 65, 27, 53, 62, 34, 28, 33, 59, 69, 7, 54, 51, 26, 38, 64, 13, 71, 72, 0, 74, 41, 24, 5, 31, 56, 17, 25, 42, 4, 36, 3, 21, 29, 66, 11, 46, 58, 44, 20, 19, 45, 35, 8, 48, 16, 67, 68, 15, 43, 73, 40, 23, 70, 52, 47, 75, 39, 55, 61, 1, 10, 18, 76, 60, 30, 14, 6, 9, 12, 78, 49, 63, 50, 37]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(56732.71);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(6390.16);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(18.69);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(60634.80);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(9367.97);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(18.93);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(56237.76);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(6076.10);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(18.27);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(55767.27);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(5173.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(16.96);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(58335.76);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(7879.45);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(16.58);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(56363.96);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(6600.82);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(1.013);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(17.42);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(58004.77);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(8171.46);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(17.47);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(57868.95);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(7908.50);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(18.76);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(59286.50);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(9235.77);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(17.58);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(56560.17);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(6578.65);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.013);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(16.73);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(55950.35);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(5749.70);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(15.63);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(59154.74);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(7896.44);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(19.68);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(57124.34);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(7054.61);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(16.94);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(56262.64);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(5978.23);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(16.86);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(52497.89);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(58749.35);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(7518.05);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(0.987);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(19.27);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(52497.89);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(45000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

    @Test
    public void test_MMAS_tsp_joinville125_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromJoinville(new File(joinville125));
        MMAS_TSP_3OPT mmas_tsp_3opt = new MMAS_TSP_3OPT(graph, 1500, 1);
        mmas_tsp_3opt.setStatisticInterval(100);
        mmas_tsp_3opt.setShowLog(true);
        mmas_tsp_3opt.run();
        mmas_tsp_3opt.roundStatistics();
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getBestSoFar()).isEqualTo(64724.60799999997);
        assertThat(TestUtils.getTourString(mmas_tsp_3opt.getGlobalStatistics())).isEqualTo("[57, 2, 45, 110, 88, 19, 79, 92, 32, 117, 123, 113, 22, 90, 51, 93, 64, 13, 94, 71, 72, 91, 0, 83, 74, 41, 24, 5, 31, 98, 38, 26, 116, 96, 65, 97, 54, 7, 69, 99, 59, 100, 33, 104, 28, 102, 34, 62, 53, 27, 43, 108, 73, 119, 40, 23, 70, 52, 103, 105, 109, 55, 39, 75, 47, 118, 101, 18, 122, 61, 1, 10, 121, 112, 89, 76, 107, 50, 63, 49, 60, 30, 78, 12, 9, 14, 6, 115, 3, 21, 36, 42, 4, 17, 56, 87, 106, 25, 86, 124, 29, 66, 11, 46, 44, 20, 58, 84, 77, 35, 37, 80, 8, 48, 16, 67, 114, 120, 68, 82, 15, 81, 111, 85, 95, 57]");

        // Iteration 100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIteration()).isEqualTo(100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationMean()).isEqualTo(74124.14);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getIterationSd()).isEqualTo(13289.65);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBranchFactor()).isEqualTo(1.152);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getDiversity()).isEqualTo(28.74);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(0).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIteration()).isEqualTo(200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationMean()).isEqualTo(72415.87);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getIterationSd()).isEqualTo(12149.99);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getDiversity()).isEqualTo(23.02);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(1).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIteration()).isEqualTo(300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationMean()).isEqualTo(76042.65);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getIterationSd()).isEqualTo(14149.88);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getDiversity()).isEqualTo(24.82);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(2).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIteration()).isEqualTo(400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationMean()).isEqualTo(72830.08);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getIterationSd()).isEqualTo(12454.86);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getDiversity()).isEqualTo(25.32);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(3).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIteration()).isEqualTo(500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationMean()).isEqualTo(69921.88);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getIterationSd()).isEqualTo(9192.39);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBranchFactor()).isEqualTo(0.976);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getDiversity()).isEqualTo(26.39);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(4).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 600
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIteration()).isEqualTo(600);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationMean()).isEqualTo(75821.77);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getIterationSd()).isEqualTo(14359.39);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBranchFactor()).isEqualTo(0.976);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getDiversity()).isEqualTo(24.61);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(5).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 700
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIteration()).isEqualTo(700);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationMean()).isEqualTo(78901.56);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getIterationSd()).isEqualTo(14596.05);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBranchFactor()).isEqualTo(1.272);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getDiversity()).isEqualTo(30.02);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(6).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 800
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIteration()).isEqualTo(800);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationMean()).isEqualTo(74673.76);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getIterationSd()).isEqualTo(13692.05);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBranchFactor()).isEqualTo(1.004);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getDiversity()).isEqualTo(25.98);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(7).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 900
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIteration()).isEqualTo(900);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationMean()).isEqualTo(78129.66);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getIterationSd()).isEqualTo(15493.55);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBranchFactor()).isEqualTo(1.020);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getDiversity()).isEqualTo(24.35);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(8).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1000
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIteration()).isEqualTo(1000);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationMean()).isEqualTo(77449.00);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getIterationSd()).isEqualTo(14460.70);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBranchFactor()).isEqualTo(1.004);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getDiversity()).isEqualTo(25.06);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(9).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1100
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIteration()).isEqualTo(1100);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationMean()).isEqualTo(71598.43);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getIterationSd()).isEqualTo(11956.74);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getDiversity()).isEqualTo(24.18);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(10).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1200
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIteration()).isEqualTo(1200);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationMean()).isEqualTo(74900.02);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getIterationSd()).isEqualTo(13160.31);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getDiversity()).isEqualTo(28.26);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(11).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1300
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIteration()).isEqualTo(1300);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationMean()).isEqualTo(72905.44);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getIterationSd()).isEqualTo(13080.50);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBranchFactor()).isEqualTo(0.976);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getDiversity()).isEqualTo(22.25);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(12).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1400
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIteration()).isEqualTo(1400);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationMean()).isEqualTo(74711.83);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getIterationSd()).isEqualTo(14088.44);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBranchFactor()).isEqualTo(0.976);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getDiversity()).isEqualTo(25.81);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(13).getBestSoFar()).isEqualTo(64724.61);

        // Iteration 1500
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIteration()).isEqualTo(1500);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationMean()).isEqualTo(71825.92);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getIterationSd()).isEqualTo(11743.94);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBranchFactor()).isEqualTo(0.984);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getDiversity()).isEqualTo(27.96);
        assertThat(mmas_tsp_3opt.getIterationStatistics().get(14).getBestSoFar()).isEqualTo(64724.61);

        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(50000);
        assertThat(mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);

        mmas_tsp_3opt.getGlobalStatistics().getTimeStatistics().forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
    }

}
