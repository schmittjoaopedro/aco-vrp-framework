package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_ADTSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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
    public void test_mmas_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_5_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 1000, 0.5, 100, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(26092.130892718294);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[98, 37, 23, 17, 93, 21, 87, 15, 69, 65, 25, 64, 3, 96, 55, 88, 79, 30, 41, 7, 91, 0, 92, 27, 66, 57, 60, 86, 50, 24, 80, 68, 39, 53, 63, 43, 1, 81, 94, 75, 12, 32, 4, 36, 51, 77, 95, 38, 49, 72, 67, 84, 29, 13, 47, 99, 40, 70, 2, 28, 45, 42, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 44, 97, 90, 10, 14, 16, 31, 46, 62, 48, 5, 89, 18, 74, 52, 78, 83, 9, 71, 20, 58, 73, 35, 98]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 38178.21317357259, 3467.308561540421, 0.0, 54.55510204081633, 32328.65077577526);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 29777.270005143815, 2822.528079641859, 0.52, 16.769795918367347, 26065.651909820674);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 29603.350701393174, 3262.282378476368, 0.5, 15.601632653061225, 26034.651909820674);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 101, 39572.65958362142, 3395.86531964317, 0.48, 35.04, 34102.467806655);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 150, 34770.155463947616, 3481.9669085957203, 0.52, 19.036734693877552, 29429.726600641377);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 200, 32289.464292532764, 3549.7369055500426, 0.5, 15.266938775510203, 27925.537050035313);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 201, 39144.02544265612, 2992.028501617014, 0.485, 31.313469387755102, 33307.00718173466);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 250, 31199.783518046854, 3319.420605668188, 0.52, 12.834285714285715, 27770.160185547098);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 300, 31157.657501802176, 3634.6156900552064, 0.5, 12.751836734693878, 27221.861298303273);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 301, 39408.733787279554, 2833.4949620739167, 0.48, 40.4, 34499.85968685082);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 350, 32132.662371752445, 3193.232711334285, 0.51, 15.053061224489795, 27289.94999624658);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 400, 31165.847520727944, 4188.702461751659, 0.515, 14.194285714285714, 27036.55436205018);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 401, 40042.3771294375, 3462.969190370186, 0.48, 31.664489795918367, 34184.03091822131);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 450, 29218.36180065546, 3318.2145976350625, 0.5, 9.835102040816327, 26054.59147377136);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 500, 28623.6030816331, 3812.6681335707744, 0.5, 7.174693877551021, 25781.31555024789);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 501, 38544.68034006976, 2947.535283761019, 0.48, 28.808163265306124, 33241.96864949058);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 550, 32893.216642667816, 3439.5365559029447, 0.505, 12.68734693877551, 27833.136953816535);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 600, 32059.567584078803, 3811.058732590335, 0.5, 12.751836734693878, 26580.131742751048);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 601, 38951.71126035126, 2880.90896462305, 0.475, 38.59673469387755, 34002.495521402554);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 650, 32098.381511227246, 3497.922252182971, 0.505, 13.721632653061224, 27525.050085950184);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 700, 32783.04240563993, 3143.000464788653, 0.5, 12.933061224489796, 26943.87654663843);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 701, 38611.84523532782, 2498.852118199641, 0.48, 26.858775510204083, 34600.82030193262);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 750, 30272.388122142256, 3263.234414075534, 0.515, 8.83265306122449, 27260.964305312835);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 800, 29751.785683691807, 3519.5709091766503, 0.505, 9.088163265306122, 25865.875404496823);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 801, 38337.217471496195, 3114.4137734480732, 0.485, 28.50938775510204, 31788.682929949755);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 850, 32853.571264472004, 3620.802351457926, 0.575, 12.754285714285714, 28468.865916073075);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 900, 30850.42516667802, 3227.478270996619, 0.515, 11.71265306122449, 27525.06361834603);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 901, 39326.28785956637, 2698.8740143037885, 0.5, 35.01795918367347, 35124.83282526013);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 950, 30497.200341031283, 3447.8083995273246, 0.5, 9.05795918367347, 26970.91229451435);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1000, 29263.766521235048, 3310.7020715797394, 0.515, 8.715918367346939, 26092.130892718294);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(10000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_75_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 1000, 0.75, 100, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(32236.10157581001);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[83, 9, 62, 5, 48, 91, 7, 41, 88, 30, 79, 55, 3, 52, 87, 15, 21, 93, 69, 25, 64, 65, 74, 96, 18, 0, 27, 92, 66, 57, 39, 63, 53, 1, 72, 68, 43, 49, 81, 32, 75, 94, 12, 36, 4, 77, 51, 29, 84, 67, 38, 95, 47, 13, 70, 40, 99, 2, 42, 28, 45, 33, 6, 8, 56, 86, 50, 60, 80, 24, 82, 54, 26, 11, 19, 85, 34, 59, 76, 61, 97, 22, 44, 90, 46, 31, 10, 14, 16, 35, 98, 73, 58, 20, 71, 89, 78, 17, 23, 37, 83]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 46674.92006367681, 4044.540696645703, 0.0, 57.10857142857143, 38024.36775076261);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 34427.545627146064, 3663.6373407964184, 0.5, 9.337142857142856, 31385.350805203656);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 35084.59963117429, 3801.283932617535, 0.5, 9.79265306122449, 30971.33198487293);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 101, 45085.70665509562, 3405.281642652314, 0.47, 39.23265306122449, 39317.83163223085);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 150, 34943.54901684388, 4561.768461024701, 0.51, 12.128163265306123, 29848.76992735183);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 200, 34179.336001930926, 4083.383839452873, 0.5, 12.74938775510204, 29487.024782754444);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 201, 47350.449049223214, 3961.773780782225, 0.49, 38.74612244897959, 40048.33294829465);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 250, 37800.30334576669, 4548.466799145494, 0.5, 10.64326530612245, 31817.320413295623);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 300, 38257.9706242085, 4867.794619770098, 0.5, 8.970612244897959, 31773.17132689214);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 301, 48054.052593109496, 3207.132824602199, 0.485, 36.57061224489796, 41324.521781294075);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 350, 39343.58468314814, 4996.2147198782195, 0.54, 12.361632653061225, 33711.08248105755);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 400, 35016.29725658598, 3969.02430805928, 0.5, 8.595102040816327, 31254.070015508165);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 401, 43949.023488994295, 2966.043196467966, 0.485, 40.715102040816326, 37356.165832754574);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 450, 36106.56211894075, 3609.9480965244297, 0.5, 13.546938775510204, 30923.58417674117);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 500, 34065.062500216605, 2991.9040941476246, 0.5, 12.190204081632652, 30343.53546854367);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 501, 46412.14059971099, 4664.158597068245, 0.47, 40.1134693877551, 36433.1945376229);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 550, 33155.42066551689, 3523.659276868866, 0.515, 7.778775510204081, 30609.693465994926);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 600, 33832.90947025077, 3127.6595179564015, 0.5, 11.292244897959185, 30181.69396255565);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 601, 45630.1826647737, 2586.680632341927, 0.475, 35.751020408163264, 41017.777847856865);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 650, 33321.768222556944, 3420.7370865606786, 0.5, 6.843265306122449, 30978.80508848676);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 700, 33174.73231370943, 3391.551118501377, 0.5, 6.474285714285714, 30439.59144613059);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 701, 44743.99661524987, 3297.348282608382, 0.485, 36.34448979591837, 38036.700239108264);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 750, 37576.80019226887, 3113.6086823234696, 0.505, 10.58530612244898, 32355.09601708186);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 800, 35136.459759904144, 3684.930897165538, 0.5, 11.021224489795918, 31653.140067248813);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 801, 47826.817250079795, 3725.4473973584613, 0.475, 41.47428571428571, 41163.98374564756);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 850, 36309.72229052808, 3197.6826755257166, 0.53, 19.345306122448978, 31360.95964972462);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 900, 37363.96848697813, 3238.3422803635253, 0.5, 20.431020408163267, 30521.5011722713);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 901, 45778.587163160235, 2646.9184770906722, 0.46, 41.0865306122449, 40249.81917770054);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 950, 39030.107230022964, 3809.401422994219, 0.515, 15.79265306122449, 33088.89324228875);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1000, 36986.25206300431, 3901.86137805786, 0.5, 12.978775510204082, 32236.10157581001);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(10000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_5_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 100, 0.5, 10, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(30577.058119669073);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[62, 5, 48, 89, 9, 71, 20, 73, 58, 98, 37, 23, 17, 78, 52, 15, 87, 69, 65, 21, 93, 83, 35, 14, 16, 10, 31, 46, 44, 90, 97, 22, 76, 61, 59, 34, 85, 26, 11, 19, 56, 8, 6, 86, 54, 82, 33, 2, 13, 47, 99, 70, 40, 42, 45, 28, 38, 29, 95, 77, 51, 36, 4, 32, 75, 12, 94, 81, 43, 49, 72, 67, 84, 24, 80, 68, 63, 39, 53, 1, 50, 60, 92, 27, 66, 57, 0, 91, 7, 41, 88, 79, 30, 55, 25, 64, 3, 96, 74, 18, 62]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 38178.21317357259, 3467.308561540421, 0.0, 54.55510204081633, 32328.65077577526);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 5, 33483.58107580091, 3630.063877834294, 0.69, 19.792653061224488, 29020.921464548264);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 10, 31835.5043716717, 2701.1709470640217, 0.595, 18.703673469387756, 27966.206921635494);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 11, 39977.618655063394, 3076.837919166634, 0.485, 40.19183673469388, 33839.73521832499);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 15, 36324.14832876702, 3195.1857870258314, 0.63, 22.417142857142856, 31251.930733587684);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 20, 35641.953770485896, 3067.734577901374, 0.5, 19.984489795918368, 30254.0692483489);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 21, 41462.528071889865, 3517.003273209553, 0.495, 37.304489795918364, 33472.69377779319);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 25, 35511.60383319219, 2853.9038891560363, 0.52, 21.271836734693878, 31630.643337355872);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 30, 33268.43951252221, 2789.4006329413824, 0.575, 11.951020408163265, 29993.91714409562);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 31, 41221.840902116994, 3163.3864189403066, 0.49, 30.880816326530613, 35248.70056344236);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 35, 35404.85876808952, 3538.1956932806265, 0.59, 17.751836734693878, 30650.655470726393);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 40, 33291.39642756059, 2599.2541131367484, 0.545, 16.819591836734695, 29950.83490599858);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 41, 40928.63637363412, 3181.916309571221, 0.54, 42.43102040816326, 35244.14319939926);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 45, 34689.572364897525, 2475.194377699577, 0.68, 27.099591836734692, 30813.32339351036);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 32466.8310491929, 3037.6416715436135, 0.59, 17.85142857142857, 29170.619051048347);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 51, 39598.02671534126, 2988.3927230337913, 0.515, 37.071020408163264, 34664.82713465943);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 55, 36569.894100864505, 3971.893080384495, 0.575, 22.06612244897959, 31456.906427547925);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 60, 33716.67819969991, 3452.530598945243, 0.55, 19.284897959183674, 28287.366744645096);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 61, 39699.80760034537, 3347.7340490114607, 0.555, 35.96163265306122, 33614.204376019814);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 65, 36800.24239908844, 3423.5952457669136, 0.555, 21.296326530612244, 31182.560416267454);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 70, 34533.725173037856, 2875.594728413163, 0.545, 16.568163265306122, 29848.771157192332);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 71, 41529.30193885172, 3858.836356963541, 0.505, 36.88979591836735, 34228.836148154);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 75, 35880.99277550378, 3290.366518191615, 0.575, 22.13142857142857, 31211.055699739787);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 80, 35663.645711591475, 3779.61791996789, 0.5, 18.812244897959182, 29926.031624693183);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 81, 40748.464772373656, 3202.3095628421465, 0.5, 38.54775510204082, 31973.8642990513);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 85, 34783.94959113823, 2603.9406540486552, 0.505, 23.315102040816328, 30249.723075165108);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 90, 34122.87986233184, 2224.7127648969154, 0.495, 19.36408163265306, 30171.339961184403);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 91, 37762.013050371155, 2108.1365920842413, 0.47, 35.40081632653061, 33491.77295131011);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 95, 35613.114416995544, 1994.8788420990109, 0.595, 21.459591836734695, 32010.042816498524);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 34967.12189958117, 2847.6557943688117, 0.665, 21.547755102040817, 30577.058119669073);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(3000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_75_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 100, 0.75, 10, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(35438.85374944973);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[66, 60, 50, 86, 24, 80, 67, 84, 29, 95, 51, 77, 4, 36, 12, 32, 75, 94, 81, 43, 1, 53, 39, 63, 68, 72, 49, 38, 47, 13, 42, 40, 70, 99, 2, 45, 28, 33, 82, 54, 11, 19, 85, 26, 34, 59, 76, 61, 56, 8, 6, 27, 92, 57, 46, 90, 97, 22, 44, 31, 14, 16, 10, 58, 20, 73, 71, 35, 98, 23, 37, 83, 9, 48, 5, 62, 0, 41, 88, 30, 79, 55, 96, 74, 18, 52, 93, 15, 21, 69, 87, 78, 17, 89, 64, 65, 25, 3, 91, 7, 66]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 46674.92006367681, 4044.540696645703, 0.0, 57.10857142857143, 38024.36775076261);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 5, 39502.80707390053, 2771.394888713798, 0.62, 16.893061224489795, 35368.71261526927);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 10, 39285.188575408676, 3430.336600613992, 0.5, 15.8, 35360.57084218228);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 11, 44366.62720219644, 3141.97339954573, 0.475, 38.36489795918367, 39364.09558506259);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 15, 41105.51396248492, 3418.240313246499, 0.66, 26.600816326530612, 35633.690584916876);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 20, 39683.72395215908, 3219.648577917739, 0.515, 21.859591836734694, 33806.62210893431);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 21, 46063.92481705226, 3533.130708489876, 0.46, 40.015510204081636, 40241.402306185744);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 25, 43306.42336888867, 2646.0150221562067, 0.74, 30.070204081632653, 38776.6594008578);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 30, 42519.55017515201, 3469.468412299919, 0.66, 25.004897959183673, 37007.915516378394);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 31, 48362.543041800825, 3643.385418152438, 0.52, 43.605714285714285, 41922.25996212657);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 35, 40647.02032640996, 3121.3065199748103, 0.57, 22.89142857142857, 36189.71057651895);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 40, 40281.14788274519, 3122.163275747736, 0.62, 23.65142857142857, 34103.883250249004);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 41, 44349.958324947715, 3498.500987136264, 0.525, 41.541224489795916, 39046.75197933677);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 45, 40552.81102716461, 3308.7635011589737, 0.51, 25.89061224489796, 34877.405012871255);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 37367.547483941955, 3241.1039887317656, 0.52, 14.551836734693877, 33478.40655363701);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 51, 44709.35018499974, 3416.387206059754, 0.475, 29.375510204081632, 37507.039229057234);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 55, 41133.03838933954, 3156.5683136162397, 0.56, 22.459591836734695, 35223.09068092596);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 60, 37412.19549263049, 3395.897674443244, 0.545, 15.17469387755102, 33089.13455690108);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 61, 47864.85133000516, 3263.3877911546815, 0.47, 38.33714285714286, 42257.175136782425);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 65, 42042.911668272835, 4263.224501713796, 0.63, 23.555102040816326, 36727.988605175924);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 70, 37659.2016363915, 3120.3933297868275, 0.555, 11.761632653061225, 34578.84865966825);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 71, 44435.71969002901, 2481.365668371806, 0.49, 27.557551020408162, 39101.553985442966);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 75, 41976.309268937875, 3414.8158868978285, 0.665, 22.824489795918367, 36441.57295223885);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 80, 42028.756339413376, 3161.5535624336726, 0.58, 23.96, 35811.318951039364);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 81, 46634.84785017132, 3798.5900614724137, 0.445, 47.12, 39647.005127919);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 85, 39226.385099820815, 3277.441609894579, 0.68, 22.653061224489797, 34859.70994943178);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 90, 39503.68795183272, 4140.637780732297, 0.5, 21.499591836734695, 33572.069248692314);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 91, 46179.393350991515, 3307.384749544892, 0.5, 37.38530612244898, 40933.69853934303);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 95, 42247.12919155305, 2721.4320493780333, 0.655, 23.90204081632653, 36943.247009007726);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 40115.598670161104, 3608.1047070373174, 0.495, 18.919183673469387, 35438.85374944973);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(3000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA200_with_seed_1_mag_0_5_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 1000, 0.5, 100, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(36457.178132921756);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[189, 148, 105, 92, 162, 98, 18, 91, 9, 174, 118, 65, 152, 43, 187, 178, 117, 15, 62, 50, 193, 115, 121, 169, 47, 83, 51, 10, 125, 86, 164, 95, 165, 103, 196, 44, 80, 96, 32, 99, 155, 179, 130, 188, 68, 141, 73, 56, 35, 107, 13, 191, 59, 3, 100, 192, 127, 166, 40, 2, 72, 58, 88, 163, 139, 20, 153, 101, 69, 75, 143, 93, 94, 90, 149, 181, 112, 194, 132, 175, 42, 104, 136, 4, 55, 151, 177, 195, 85, 138, 49, 184, 167, 172, 22, 168, 67, 34, 1, 180, 124, 29, 157, 76, 150, 160, 79, 64, 30, 66, 119, 46, 106, 108, 156, 5, 186, 53, 61, 82, 71, 129, 70, 37, 38, 27, 147, 87, 199, 170, 140, 57, 97, 113, 102, 145, 128, 33, 89, 24, 142, 16, 7, 21, 133, 74, 182, 154, 111, 126, 185, 41, 54, 134, 63, 19, 161, 159, 78, 12, 176, 122, 14, 197, 26, 190, 144, 131, 110, 116, 114, 52, 0, 84, 39, 146, 11, 45, 171, 120, 173, 158, 23, 31, 135, 60, 25, 6, 198, 81, 77, 8, 123, 137, 36, 183, 28, 17, 109, 48, 189]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 53748.67166971817, 3212.9809340498327, 0.0, 113.30938775510204, 48357.01249279261);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 46179.74049694536, 2572.523071750816, 0.495, 27.823673469387757, 42502.117389054045);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 44812.751666373406, 2520.182579942645, 0.4875, 27.195102040816327, 41360.73077228234);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 101, 55875.77155172196, 2902.958719194997, 0.4675, 69.07755102040817, 49258.41726985859);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 150, 44333.7090549279, 3340.028377178192, 0.4975, 18.140408163265306, 40772.520001094636);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 200, 43983.26243098194, 2929.7275234043527, 0.4975, 19.411428571428573, 39407.264300730836);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 201, 55159.53927366798, 2623.505872716304, 0.485, 58.457959183673466, 49022.97322866402);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 250, 48187.06236285743, 3431.9566031041677, 0.4975, 30.356734693877552, 41588.67484297993);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 300, 44636.61635127175, 3050.8422095989827, 0.5, 20.48081632653061, 39774.455006930104);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 301, 52965.935000578684, 2586.3730655117615, 0.4675, 67.32, 47015.07870060606);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 350, 48760.998612922405, 3464.2893762926647, 0.4975, 33.734693877551024, 41983.64353746881);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 400, 45105.05539421361, 3132.101322803862, 0.505, 22.62938775510204, 40271.01583642679);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 401, 54361.65509469042, 3009.808815564203, 0.49, 63.83020408163265, 49150.451676718774);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 450, 45874.89378509558, 3235.174656340318, 0.515, 22.11591836734694, 40518.69904864893);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 500, 42062.564211225945, 2995.6052909002924, 0.5075, 10.948571428571428, 39033.60222122305);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 501, 55811.063528613115, 2580.54421234365, 0.4825, 70.00571428571429, 51004.298174860305);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 550, 48378.96249969376, 3467.7265711981277, 0.5025, 24.279183673469387, 42789.122195358366);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 600, 47683.89109470805, 2999.8702519394574, 0.5025, 27.431836734693878, 40782.37197250562);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 601, 56594.06683236181, 3376.416929129737, 0.475, 66.39020408163265, 50650.30437097142);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 650, 48186.599641820474, 3504.842135206666, 0.4975, 26.831020408163266, 42811.26177740389);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 700, 47802.13479325948, 2656.5207069090998, 0.5375, 28.427755102040816, 42686.96363116968);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 701, 57692.30325460072, 3160.5457892064715, 0.495, 77.90938775510205, 50615.9890918713);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 750, 48811.56574367436, 3179.642634046515, 0.53, 24.013061224489796, 43576.84328324598);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 800, 48004.284202070485, 2490.799864290957, 0.505, 26.493877551020407, 41319.93152509309);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 801, 56117.76765509714, 3230.005506509068, 0.4625, 79.2138775510204, 49529.71998491855);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 850, 46353.03568298523, 3013.852449627071, 0.515, 25.900408163265308, 41197.14376404888);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 900, 47108.083536469276, 3514.8483307398906, 0.515, 24.991836734693877, 39754.31266347175);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 901, 53079.0700887106, 2688.008698706495, 0.4775, 70.50285714285714, 48545.164471676515);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 950, 41062.57001066494, 3120.1928562710823, 0.5025, 10.78204081632653, 37465.44689084184);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1000, 40949.07945007211, 3933.807065558131, 0.5025, 11.625306122448979, 36457.178132921756);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(50000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA200_with_seed_1_mag_0_75_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 1000, 0.75, 100, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(47135.782063333194);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[166, 192, 127, 59, 100, 3, 146, 11, 39, 131, 84, 144, 190, 26, 197, 122, 14, 116, 114, 110, 52, 0, 173, 158, 120, 45, 171, 23, 135, 60, 137, 123, 8, 81, 77, 178, 15, 117, 50, 62, 187, 115, 121, 169, 193, 43, 152, 65, 47, 83, 10, 51, 95, 125, 86, 164, 165, 103, 196, 44, 73, 56, 35, 13, 191, 107, 148, 105, 92, 162, 48, 17, 109, 28, 36, 183, 31, 198, 6, 25, 189, 18, 91, 9, 174, 118, 98, 96, 80, 32, 99, 155, 179, 188, 130, 141, 68, 2, 72, 58, 40, 88, 139, 163, 20, 153, 101, 75, 69, 143, 149, 90, 94, 93, 181, 194, 175, 132, 112, 104, 42, 136, 4, 195, 177, 55, 151, 38, 129, 37, 70, 71, 61, 82, 167, 184, 49, 138, 85, 27, 87, 170, 199, 113, 97, 57, 140, 147, 33, 89, 142, 16, 24, 145, 102, 128, 7, 133, 21, 74, 108, 106, 156, 46, 119, 185, 126, 111, 154, 182, 5, 53, 186, 30, 66, 54, 41, 134, 63, 19, 161, 159, 78, 12, 176, 64, 150, 1, 180, 124, 160, 79, 157, 76, 29, 67, 168, 34, 22, 172, 166]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 61426.22073743515, 4293.194635102264, 0.0, 112.34775510204082, 54711.84985208566);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 55387.33294620256, 2850.5074364157895, 0.495, 33.270204081632656, 49709.23895144428);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 54569.580219688614, 3718.3533421308425, 0.495, 37.627755102040815, 48683.47124320849);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 101, 64926.4705360411, 3704.981528373864, 0.465, 78.22530612244898, 58501.50692044397);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 150, 56842.26356558243, 3858.4332399029986, 0.495, 39.38612244897959, 49601.26842068353);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 200, 53502.71958676954, 3772.7724721589607, 0.505, 30.23591836734694, 47617.36535410196);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 201, 66440.82316830456, 3844.9504517923137, 0.47, 75.82285714285715, 61186.61220144138);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 250, 54335.56084369049, 3239.738440271658, 0.4975, 31.015510204081632, 48962.11313415055);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 300, 54349.61299852369, 3536.753190218173, 0.555, 30.87183673469388, 47740.23411807978);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 301, 65229.14174700738, 3078.344334565177, 0.5325, 74.33061224489796, 58144.02171749696);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 350, 52057.27621032917, 3238.828482243485, 0.5225, 13.140408163265306, 48445.83462919027);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 400, 52075.068847441755, 3941.988091375668, 0.5, 17.645714285714284, 47474.472102066924);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 401, 64134.22534463072, 3937.737284184355, 0.4775, 61.14204081632653, 57694.58415077074);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 450, 53567.80881078572, 3886.274573493198, 0.495, 30.97469387755102, 48472.987670265655);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 500, 51547.821452599106, 3540.551395887288, 0.5, 22.21551020408163, 46983.12707030799);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 501, 63481.82890388659, 2951.0036822025777, 0.475, 72.58285714285714, 58555.93667969786);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 550, 53213.83713328491, 2811.980252676464, 0.5025, 25.219591836734693, 47908.49419511964);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 600, 50729.19848519932, 3503.5533818126382, 0.4925, 25.831020408163266, 45036.1051727976);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 601, 62651.3115041743, 3205.0441351677755, 0.4725, 68.95836734693877, 57752.586667390475);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 650, 50621.22448088857, 3673.0997985936265, 0.5075, 18.8, 45441.75284839639);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 700, 52905.86291285054, 3687.3148521364305, 0.5, 16.29877551020408, 45367.06472654724);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 701, 63623.93538879692, 3406.7443406046173, 0.475, 69.54367346938776, 56985.65837349696);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 750, 53118.01337827768, 3861.808085854446, 0.5, 28.555918367346937, 47699.40713590018);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 800, 54439.76314187029, 4354.517853163853, 0.5, 36.26857142857143, 47305.394869126925);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 801, 66996.57654118868, 3527.1635166055576, 0.4575, 78.62857142857143, 60470.328570617945);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 850, 57030.297551677, 3290.5073480532587, 0.5, 39.13469387755102, 51349.41991381065);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 900, 57835.20558719723, 3654.9081776746766, 0.515, 31.177142857142858, 49672.58508537177);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 901, 66498.71178506681, 3050.7842602179685, 0.46, 84.71510204081633, 58424.64522109543);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 950, 54947.87625226431, 3391.3900375068456, 0.5175, 24.12734693877551, 48195.89375903024);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1000, 51659.86273319382, 3951.5007527029993, 0.51, 20.467755102040815, 47135.782063333194);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(50000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA200_with_seed_1_mag_0_5_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 100, 0.5, 10, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(44874.526172962025);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[189, 18, 91, 174, 9, 98, 13, 191, 107, 35, 56, 73, 99, 155, 32, 44, 196, 96, 103, 164, 95, 165, 47, 169, 10, 51, 83, 86, 125, 80, 179, 130, 68, 141, 188, 72, 2, 58, 40, 166, 192, 127, 76, 157, 79, 160, 124, 180, 1, 34, 168, 67, 29, 88, 139, 20, 163, 153, 101, 69, 75, 143, 172, 22, 149, 90, 94, 93, 194, 181, 175, 132, 112, 104, 42, 136, 85, 138, 195, 4, 177, 151, 55, 70, 38, 37, 27, 147, 87, 199, 140, 170, 57, 113, 97, 61, 82, 129, 49, 167, 184, 71, 145, 128, 102, 33, 89, 24, 16, 142, 21, 133, 7, 182, 154, 74, 53, 186, 150, 5, 106, 108, 156, 119, 46, 30, 66, 185, 126, 111, 41, 134, 54, 19, 161, 63, 159, 78, 12, 176, 64, 14, 122, 190, 197, 26, 144, 110, 116, 114, 52, 0, 84, 131, 146, 39, 11, 48, 109, 17, 28, 183, 36, 178, 152, 65, 43, 187, 193, 50, 15, 62, 115, 121, 117, 123, 8, 77, 81, 198, 25, 6, 120, 173, 158, 23, 31, 135, 60, 137, 45, 171, 148, 105, 92, 162, 3, 100, 59, 118, 189]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 53748.67166971817, 3212.9809340498327, 0.0, 113.30938775510204, 48357.01249279261);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 5, 49426.74995303124, 3189.9206374579153, 0.6275, 39.83265306122449, 44331.51827914597);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 10, 48592.4037417354, 2707.5903574989047, 0.505, 31.117551020408165, 43783.79563542268);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 11, 56370.57402758427, 3779.626404392757, 0.4725, 72.32816326530612, 48710.31865523836);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 15, 52236.24072607956, 3275.118592372108, 0.5725, 36.14612244897959, 47212.68383147952);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 20, 51113.37319295193, 3161.4896537639042, 0.53, 46.02040816326531, 43046.017311151256);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 21, 55004.59631947998, 2631.7381704222826, 0.49, 80.31591836734694, 49424.48266344604);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 25, 53798.508192820846, 2900.845482994302, 0.6675, 52.671020408163265, 47618.42884461129);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 30, 47746.02634318942, 2835.3329856481564, 0.6275, 43.04897959183673, 43967.00140571787);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 31, 54138.79501494812, 3298.1333329863505, 0.4975, 72.66285714285715, 48861.86463271711);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 35, 50156.86205851333, 3018.7539631204995, 0.665, 38.115102040816325, 45983.283634252555);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 40, 47430.332661396016, 3035.709696121654, 0.52, 27.555918367346937, 42935.79675750745);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 41, 54338.42138400073, 3330.9283606254826, 0.5, 65.53551020408163, 48165.007455497995);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 45, 49093.83246914457, 2914.1393377435593, 0.6575, 36.85469387755102, 44792.80889840666);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 46204.38137149113, 1939.3672758078442, 0.595, 29.804897959183673, 43391.073127170115);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 51, 54927.756816114466, 2461.173420193508, 0.5175, 71.72, 49772.39631980841);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 55, 51504.60225118894, 2045.3427757975883, 0.72, 54.81306122448979, 47391.51377507189);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 60, 51885.934822817966, 2853.055414878373, 0.5275, 43.288979591836735, 46437.89959390085);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 61, 56900.1578271149, 2623.7784117437645, 0.4875, 81.31836734693877, 53324.92447468524);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 65, 52603.7659954166, 3104.3977682090813, 0.67, 52.81551020408163, 47304.618923304464);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 70, 50198.730218166274, 3225.503279790393, 0.525, 42.92163265306122, 44851.98031292396);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 71, 54479.00046758665, 2628.6074349828505, 0.535, 75.21714285714286, 49199.577952786785);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 75, 52433.17026212267, 3425.1146165535324, 0.5525, 38.99673469387755, 46589.05377129616);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 80, 51640.6032101651, 2789.2610165554825, 0.5525, 38.70367346938775, 46589.05377129616);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 81, 57158.24161505121, 3232.8454121822097, 0.4725, 64.57959183673469, 51056.70265638338);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 85, 51429.62346292686, 2723.776704589999, 0.6575, 45.221224489795915, 47062.35662936022);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 90, 49804.19574437294, 2779.6371521890464, 0.5225, 40.30285714285714, 44945.2124594849);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 91, 56383.65286925644, 3728.706201709392, 0.495, 75.64489795918368, 50216.554339076865);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 95, 50284.96246113117, 2054.7066842766876, 0.585, 32.226938775510206, 46227.424753110456);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 51867.13265655383, 2931.870212652805, 0.535, 35.72408163265306, 44874.526172962025);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(10000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

    @Test
    public void test_mmas_asymmetric_dynamic_tsp_kroA200_with_seed_1_mag_0_75_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(graph, 0.8, 100, 0.75, 10, 1.0, 5.0);
        mmas_adtsp.setMmasSeed(1);
        mmas_adtsp.setDbgpSeed(1);
        mmas_adtsp.setStatisticInterval(1);
        mmas_adtsp.setShowLog(false);
        mmas_adtsp.run();
        assertThat(mmas_adtsp.getGlobalStatistics().getBestSoFarTC()).isEqualTo(50917.69414187545);
        assertThat(TestUtils.getTourString(mmas_adtsp.getGlobalStatistics())).isEqualTo("[198, 6, 25, 135, 60, 31, 23, 120, 173, 158, 131, 84, 144, 190, 26, 197, 122, 14, 12, 176, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 156, 106, 154, 182, 7, 21, 133, 142, 89, 33, 140, 170, 57, 97, 113, 102, 145, 24, 16, 128, 195, 4, 177, 151, 55, 70, 37, 38, 27, 87, 147, 199, 71, 129, 82, 61, 184, 167, 172, 22, 143, 90, 149, 75, 69, 93, 94, 49, 138, 85, 136, 104, 42, 112, 132, 175, 194, 181, 20, 163, 139, 153, 101, 67, 168, 34, 1, 180, 124, 29, 40, 107, 191, 13, 35, 56, 73, 99, 32, 44, 196, 155, 96, 80, 18, 91, 9, 174, 98, 105, 148, 92, 162, 48, 17, 109, 28, 183, 36, 123, 137, 8, 81, 77, 117, 50, 193, 121, 115, 187, 43, 62, 15, 178, 189, 118, 103, 83, 10, 51, 152, 65, 47, 169, 165, 95, 125, 86, 164, 179, 141, 68, 188, 130, 2, 72, 58, 88, 157, 76, 160, 79, 150, 186, 53, 74, 108, 5, 64, 159, 78, 30, 46, 66, 192, 127, 166, 59, 100, 3, 146, 39, 0, 52, 114, 116, 110, 11, 45, 171, 198]");

        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 1, 61426.22073743515, 4293.194635102264, 0.0, 112.34775510204082, 54711.84985208566);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 5, 58963.62448609394, 3740.580395424846, 0.625, 45.033469387755105, 52230.99847932848);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 10, 55838.61308911412, 3250.174886209876, 0.5275, 38.71673469387755, 49780.66024227424);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 11, 64578.17293177684, 3590.5896967061835, 0.475, 80.21551020408164, 58602.134604193656);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 15, 57390.045728750556, 3019.804859505889, 0.715, 44.26285714285714, 52609.32168320582);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 20, 56480.67621058385, 3113.107448166355, 0.52, 32.62204081632653, 50735.216989997294);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 21, 67936.71338787637, 3304.11301228335, 0.475, 77.68571428571428, 61979.16139999294);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 25, 60629.7395836482, 2725.570789931977, 0.6025, 55.12489795918367, 55754.86957626452);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 30, 60095.01081733485, 2755.369697473823, 0.57, 45.91918367346939, 54136.19959179298);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 31, 66101.13401386462, 3664.150284647906, 0.46, 83.72, 57877.78331940263);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 35, 58614.3332128942, 3488.705250390453, 0.7125, 47.11265306122449, 51581.51853521762);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 40, 54014.82773913688, 3224.8820876666696, 0.6425, 38.900408163265304, 49045.59369117379);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 41, 64675.7244783264, 4063.234520799062, 0.5775, 73.99428571428571, 58274.47054694195);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 45, 61562.466443182406, 3394.832728205323, 0.575, 40.80734693877551, 55934.32147106977);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 50, 59880.47867896126, 3167.253278459621, 0.51, 39.55591836734694, 53290.727513355014);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 51, 65731.29842833827, 3957.7038166717457, 0.5425, 76.84489795918367, 60222.82651647401);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 55, 57045.56131622743, 2638.8228771531194, 0.645, 46.31836734693878, 51896.104730006045);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 60, 55638.13992599008, 3238.253292352269, 0.54, 35.57142857142857, 49245.947395051204);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 61, 64096.19028176205, 2673.574641104356, 0.5025, 69.51673469387755, 58805.3197878188);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 65, 55567.21599078475, 2822.952993624816, 0.6475, 35.35836734693878, 50219.190977420825);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 70, 54854.61714924698, 2957.4099606592968, 0.595, 24.969795918367346, 48316.859297169525);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 71, 61115.835247137315, 2689.436395154568, 0.4675, 72.45061224489795, 56460.960970819455);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 75, 56420.5902438851, 3431.816077131829, 0.64, 40.68489795918367, 51934.547564290704);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 80, 56513.60983610631, 3270.6337143169903, 0.5175, 37.137142857142855, 51155.8053474556);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 81, 65658.72208264844, 3472.636505002713, 0.4725, 70.25714285714285, 60242.57376618474);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 85, 63140.037575556365, 2727.3864027983336, 0.5725, 50.434285714285714, 58071.890033667354);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 90, 58767.85909489782, 3278.8552047747526, 0.555, 36.010612244897956, 53450.98310340334);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 91, 64491.43437778085, 2948.508887996698, 0.525, 78.71265306122449, 58928.97018292099);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 95, 59979.47479553029, 3410.972375589792, 0.6375, 48.501224489795916, 54951.16866120587);
        TestUtils.checkValues(mmas_adtsp.getIterationStatistics(), 100, 55577.6522071362, 3343.7477293059833, 0.5725, 35.44897959183673, 50917.69414187545);

        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Execution")).isLessThan(8000);
        assertThat(mmas_adtsp.getGlobalStatistics().getTimeStatistics().get("MMAS Initialization")).isLessThan(150);
    }

}
