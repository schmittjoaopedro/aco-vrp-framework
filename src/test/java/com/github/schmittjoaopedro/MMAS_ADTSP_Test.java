package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_ADTSP;
import com.github.schmittjoaopedro.graph.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_ADTSP_Test {

    private String kroA100;

    private String kroA200;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA100_with_seed_1() {
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(kroA100, 0.8, 1000, 1, 0.5, 100);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(26092.130892718294);
        assertThat(getTourString(mmas_adtsp)).isEqualTo("[98, 37, 23, 17, 93, 21, 87, 15, 69, 65, 25, 64, 3, 96, 55, 88, 79, 30, 41, 7, 91, 0, 92, 27, 66, 57, 60, 86, 50, 24, 80, 68, 39, 53, 63, 43, 1, 81, 94, 75, 12, 32, 4, 36, 51, 77, 95, 38, 49, 72, 67, 84, 29, 13, 47, 99, 40, 70, 2, 28, 45, 42, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 44, 97, 90, 10, 14, 16, 31, 46, 62, 48, 5, 89, 18, 74, 52, 78, 83, 9, 71, 20, 58, 73, 35, 98]");

        checkValues(mmas_adtsp, 1, 38178.21317357259, 3467.308561540421, 0.0, 54.55510204081633, 32328.65077577526);
        checkValues(mmas_adtsp, 50, 29777.270005143815, 2822.528079641859, 0.52, 16.769795918367347, 26065.651909820674);
        checkValues(mmas_adtsp, 100, 29603.350701393174, 3262.282378476368, 0.5, 15.601632653061225, 26034.651909820674);
        checkValues(mmas_adtsp, 101, 39572.65958362142, 3395.86531964317, 0.48, 35.04, 34102.467806655);
        checkValues(mmas_adtsp, 150, 34770.155463947616, 3481.9669085957203, 0.52, 19.036734693877552, 29429.726600641377);
        checkValues(mmas_adtsp, 200, 32289.464292532764, 3549.7369055500426, 0.5, 15.266938775510203, 27925.537050035313);
        checkValues(mmas_adtsp, 201, 39144.02544265612, 2992.028501617014, 0.485, 31.313469387755102, 33307.00718173466);
        checkValues(mmas_adtsp, 250, 31199.783518046854, 3319.420605668188, 0.52, 12.834285714285715, 27770.160185547098);
        checkValues(mmas_adtsp, 300, 31157.657501802176, 3634.6156900552064, 0.5, 12.751836734693878, 27221.861298303273);
        checkValues(mmas_adtsp, 301, 39408.733787279554, 2833.4949620739167, 0.48, 40.4, 34499.85968685082);
        checkValues(mmas_adtsp, 350, 32132.662371752445, 3193.232711334285, 0.51, 15.053061224489795, 27289.94999624658);
        checkValues(mmas_adtsp, 400, 31165.847520727944, 4188.702461751659, 0.515, 14.194285714285714, 27036.55436205018);
        checkValues(mmas_adtsp, 401, 40042.3771294375, 3462.969190370186, 0.48, 31.664489795918367, 34184.03091822131);
        checkValues(mmas_adtsp, 450, 29218.36180065546, 3318.2145976350625, 0.5, 9.835102040816327, 26054.59147377136);
        checkValues(mmas_adtsp, 500, 28623.6030816331, 3812.6681335707744, 0.5, 7.174693877551021, 25781.31555024789);
        checkValues(mmas_adtsp, 501, 38544.68034006976, 2947.535283761019, 0.48, 28.808163265306124, 33241.96864949058);
        checkValues(mmas_adtsp, 550, 32893.216642667816, 3439.5365559029447, 0.505, 12.68734693877551, 27833.136953816535);
        checkValues(mmas_adtsp, 600, 32059.567584078803, 3811.058732590335, 0.5, 12.751836734693878, 26580.131742751048);
        checkValues(mmas_adtsp, 601, 38951.71126035126, 2880.90896462305, 0.475, 38.59673469387755, 34002.495521402554);
        checkValues(mmas_adtsp, 650, 32098.381511227246, 3497.922252182971, 0.505, 13.721632653061224, 27525.050085950184);
        checkValues(mmas_adtsp, 700, 32783.04240563993, 3143.000464788653, 0.5, 12.933061224489796, 26943.87654663843);
        checkValues(mmas_adtsp, 701, 38611.84523532782, 2498.852118199641, 0.48, 26.858775510204083, 34600.82030193262);
        checkValues(mmas_adtsp, 750, 30272.388122142256, 3263.234414075534, 0.515, 8.83265306122449, 27260.964305312835);
        checkValues(mmas_adtsp, 800, 29751.785683691807, 3519.5709091766503, 0.505, 9.088163265306122, 25865.875404496823);
        checkValues(mmas_adtsp, 801, 38337.217471496195, 3114.4137734480732, 0.485, 28.50938775510204, 31788.682929949755);
        checkValues(mmas_adtsp, 850, 32853.571264472004, 3620.802351457926, 0.575, 12.754285714285714, 28468.865916073075);
        checkValues(mmas_adtsp, 900, 30850.42516667802, 3227.478270996619, 0.515, 11.71265306122449, 27525.06361834603);
        checkValues(mmas_adtsp, 901, 39326.28785956637, 2698.8740143037885, 0.5, 35.01795918367347, 35124.83282526013);
        checkValues(mmas_adtsp, 950, 30497.200341031283, 3447.8083995273246, 0.5, 9.05795918367347, 26970.91229451435);
        checkValues(mmas_adtsp, 1000, 29263.766521235048, 3310.7020715797394, 0.515, 8.715918367346939, 26092.130892718294);
    }

    private void checkValues(MMAS_ADTSP mmas_adtsp, int iteration, double mean, double sd, double branchFactor, double div, double bsf) {
        assertThat(mmas_adtsp.getIterationStatistics().get(iteration - 1).getIteration()).isEqualTo(iteration);
        assertThat(round(mmas_adtsp.getIterationStatistics().get(iteration - 1).getIterationMean())).isEqualTo(round(mean));
        assertThat(round(mmas_adtsp.getIterationStatistics().get(iteration - 1).getIterationSd())).isEqualTo(round(sd));
        assertThat(round(mmas_adtsp.getIterationStatistics().get(iteration - 1).getBranchFactor())).isEqualTo(round(branchFactor));
        assertThat(round(mmas_adtsp.getIterationStatistics().get(iteration - 1).getDiversity())).isEqualTo(round(div));
        assertThat(round(mmas_adtsp.getIterationStatistics().get(iteration - 1).getBestSoFar())).isEqualTo(round(bsf));
    }

    private String getTourString(MMAS_ADTSP MMAS_ATSP) {
        StringBuilder tour = new StringBuilder("[");
        for (Vertex vertex : MMAS_ATSP.getGlobalStatistics().getBestRoute()) {
            tour.append(vertex.getId()).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

    private double round(double value) {
        return Double.valueOf(String.format(Locale.US, "%.2f", value));
    }
}
