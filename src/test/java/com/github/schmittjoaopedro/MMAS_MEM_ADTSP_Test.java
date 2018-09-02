package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_MEM_ADTSP;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.graph.Vertex;
import com.github.schmittjoaopedro.tools.IterationStatistic;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class MMAS_MEM_ADTSP_Test {

    private String kroA100;

    private String kroA200;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_1_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 100, 0.1, 10);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(23854.88035088098);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[36, 4, 77, 51, 95, 38, 29, 47, 99, 70, 40, 13, 2, 42, 45, 28, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 97, 22, 44, 90, 31, 10, 14, 16, 58, 73, 20, 71, 9, 83, 89, 48, 5, 62, 0, 91, 7, 41, 79, 30, 88, 55, 96, 74, 18, 3, 64, 65, 25, 69, 21, 15, 87, 93, 52, 78, 17, 23, 37, 35, 98, 46, 92, 27, 66, 57, 60, 50, 86, 24, 80, 68, 63, 39, 53, 1, 43, 49, 72, 67, 84, 81, 94, 75, 32, 12, 36]");

        checkValues(mmas_mem_adtsp, 1, 31671.967452268564, 1903.8917677261363, 0.0, 54.80326530612245, 28431.703154969287);
        checkValues(mmas_mem_adtsp, 5, 28475.25551786645, 2003.5333031458701, 0.75, 18.423673469387754, 25305.185861547292);
        checkValues(mmas_mem_adtsp, 10, 27774.981083478866, 2558.684718035813, 0.62, 12.68734693877551, 24314.48943678901);
        checkValues(mmas_mem_adtsp, 11, 31042.600471609556, 1974.1903654146706, 0.62, 16.257142857142856, 26194.44572264648);
        checkValues(mmas_mem_adtsp, 15, 29306.962105812436, 2009.7427346216123, 0.76, 27.760816326530612, 25452.613511949443);
        checkValues(mmas_mem_adtsp, 20, 28952.590927344143, 2376.5539223108876, 0.885, 22.50857142857143, 25239.435419299258);
        checkValues(mmas_mem_adtsp, 21, 29118.06202947433, 2155.59583616179, 0.89, 21.398367346938777, 25664.468545104424);
        checkValues(mmas_mem_adtsp, 25, 27536.95577877744, 1938.141010077493, 0.895, 24.030204081632654, 24166.361395534062);
        checkValues(mmas_mem_adtsp, 30, 27408.351429878938, 2248.2156561237016, 0.895, 20.853877551020407, 24166.361395534062);
        checkValues(mmas_mem_adtsp, 31, 27721.558876074407, 1271.462210363916, 1.0, 24.63020408163265, 25318.155080606793);
        checkValues(mmas_mem_adtsp, 35, 28208.405076775452, 1690.156070121379, 1.055, 25.759183673469387, 24912.76485402279);
        checkValues(mmas_mem_adtsp, 40, 28183.721058489253, 1944.1648713533534, 1.105, 27.74530612244898, 24533.91662844865);
        checkValues(mmas_mem_adtsp, 41, 28430.869721992745, 1584.783407874172, 1.12, 27.751836734693878, 25356.282091144993);
        checkValues(mmas_mem_adtsp, 45, 28316.884488939777, 1935.8711805349894, 1.195, 26.382857142857144, 25190.400718316072);
        checkValues(mmas_mem_adtsp, 50, 28115.463220322774, 1823.3653232093711, 1.235, 26.771428571428572, 24931.906060057332);
        checkValues(mmas_mem_adtsp, 51, 28030.070295056626, 1968.2817272471, 1.25, 26.101224489795918, 24819.315573200434);
        checkValues(mmas_mem_adtsp, 55, 27626.111904417085, 2222.296214707746, 1.28, 24.994285714285713, 24603.59885961103);
        checkValues(mmas_mem_adtsp, 60, 27330.782505517196, 2002.0090244628084, 1.32, 23.524897959183672, 24508.134209337615);
        checkValues(mmas_mem_adtsp, 61, 28028.207967788203, 2176.7636595420277, 1.275, 25.207346938775512, 24835.157986372065);
        checkValues(mmas_mem_adtsp, 65, 28276.264574761335, 2611.3461491325515, 1.34, 24.173061224489796, 24212.157986372065);
        checkValues(mmas_mem_adtsp, 70, 27437.390410632706, 1807.8591573593708, 1.32, 22.662857142857142, 24149.11457431346);
        checkValues(mmas_mem_adtsp, 71, 28180.7286483282, 2466.88710972315, 1.315, 26.749387755102042, 24130.498664079558);
        checkValues(mmas_mem_adtsp, 75, 27207.26445399614, 2145.189946253612, 1.315, 23.90204081632653, 24130.498664079558);
        checkValues(mmas_mem_adtsp, 80, 27514.713654174906, 1751.6141791888522, 1.315, 24.768979591836736, 24130.498664079558);
        checkValues(mmas_mem_adtsp, 81, 28807.829108174843, 2122.4287372521226, 1.31, 24.202448979591836, 24645.371706486738);
        checkValues(mmas_mem_adtsp, 85, 27433.920227047423, 1612.226338317941, 1.355, 22.615510204081634, 24219.879824768155);
        checkValues(mmas_mem_adtsp, 90, 27264.292758982872, 1693.219503892166, 1.355, 23.28, 24219.879824768155);
        checkValues(mmas_mem_adtsp, 91, 28478.796995241206, 2239.762831914114, 1.4, 24.812244897959182, 24952.26428744718);
        checkValues(mmas_mem_adtsp, 95, 27669.853862907275, 1879.9178019641354, 1.405, 25.06612244897959, 23854.88035088098);
        checkValues(mmas_mem_adtsp, 100, 27540.360368993748, 1765.4778327773251, 1.445, 26.03183673469388, 23854.88035088098);
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_1_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 1000, 0.1, 100);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(23147.150201020875);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[81, 94, 12, 75, 32, 36, 4, 51, 77, 95, 38, 29, 47, 99, 70, 40, 13, 2, 42, 45, 28, 33, 82, 54, 11, 26, 85, 34, 19, 56, 6, 8, 86, 50, 60, 57, 66, 27, 92, 0, 91, 7, 79, 30, 88, 41, 55, 96, 74, 18, 3, 64, 65, 25, 69, 21, 15, 87, 93, 52, 78, 17, 23, 37, 35, 98, 58, 20, 73, 71, 83, 9, 89, 48, 5, 62, 46, 31, 10, 14, 16, 44, 90, 97, 22, 59, 76, 61, 24, 80, 68, 63, 39, 53, 1, 43, 49, 72, 67, 84, 81]");

        checkValues(mmas_mem_adtsp, 1, 31671.967452268564, 1903.8917677261363, 0.0, 54.80326530612245, 28431.703154969287);
        checkValues(mmas_mem_adtsp, 50, 25324.859394492512, 2009.051228108438, 0.5, 13.917551020408164, 22977.023991545542);
        checkValues(mmas_mem_adtsp, 100, 25205.626305376965, 1919.6086399102267, 0.5, 10.364897959183674, 22821.21036944161);
        checkValues(mmas_mem_adtsp, 101, 27647.70592938289, 2530.8596693255213, 0.5, 12.376326530612245, 24043.797955906153);
        checkValues(mmas_mem_adtsp, 150, 26291.61684901917, 2394.1264716964743, 0.84, 14.86530612244898, 23203.92877920546);
        checkValues(mmas_mem_adtsp, 200, 27579.665697799457, 3003.0243165846346, 0.855, 18.488163265306124, 23022.607627959573);
        checkValues(mmas_mem_adtsp, 201, 29455.253196154532, 2622.961975722751, 0.83, 25.0, 24471.22267571443);
        checkValues(mmas_mem_adtsp, 250, 26774.927363831153, 1969.6559502886419, 0.905, 21.307755102040815, 23671.530360279357);
        checkValues(mmas_mem_adtsp, 300, 27320.86353215198, 2277.8550193326664, 0.9, 22.190204081632654, 23434.20331555045);
        checkValues(mmas_mem_adtsp, 301, 28239.76930029549, 2125.122740661076, 0.89, 22.041632653061225, 24982.005733427042);
        checkValues(mmas_mem_adtsp, 350, 27503.597775521157, 2047.2380505674178, 0.995, 22.516734693877552, 23620.70972541396);
        checkValues(mmas_mem_adtsp, 400, 26857.304971343623, 2396.4076730659467, 1.005, 20.500408163265305, 23393.06105915955);
        checkValues(mmas_mem_adtsp, 401, 27265.474481904992, 1959.322762256089, 1.025, 22.367346938775512, 24175.564701366693);
        checkValues(mmas_mem_adtsp, 450, 28315.977863557706, 1884.8610085329085, 1.05, 24.94857142857143, 23998.564701366697);
        checkValues(mmas_mem_adtsp, 500, 27610.06314212069, 2308.173659909456, 1.06, 22.493061224489797, 23750.870809325665);
        checkValues(mmas_mem_adtsp, 501, 28339.07325655299, 2473.6616371022897, 1.055, 24.969795918367346, 24946.073707723055);
        checkValues(mmas_mem_adtsp, 550, 28357.30810642761, 2053.588879733307, 1.105, 23.957551020408165, 23470.35638952157);
        checkValues(mmas_mem_adtsp, 600, 28402.71196930171, 2305.2090282764043, 1.14, 22.204897959183672, 23215.35638952157);
        checkValues(mmas_mem_adtsp, 601, 29597.389964986527, 1618.9565321238915, 1.14, 25.70938775510204, 26411.81619655356);
        checkValues(mmas_mem_adtsp, 650, 27612.990652663, 2145.073960856569, 1.24, 18.50122448979592, 23574.337800733872);
        checkValues(mmas_mem_adtsp, 700, 27022.857376927062, 2374.1831357815586, 1.11, 16.742857142857144, 23518.337800733872);
        checkValues(mmas_mem_adtsp, 701, 27739.40339769182, 2101.8270962211855, 1.145, 23.58122448979592, 24053.790290171346);
        checkValues(mmas_mem_adtsp, 750, 26992.989605027014, 2468.103902543802, 1.23, 18.161632653061226, 22915.50734490156);
        checkValues(mmas_mem_adtsp, 800, 26812.606524154897, 2372.1934989643996, 1.04, 16.57387755102041, 22678.50734490156);
        checkValues(mmas_mem_adtsp, 801, 27654.127350799437, 2098.5668463556917, 1.0, 21.25877551020408, 23682.903045384784);
        checkValues(mmas_mem_adtsp, 850, 26472.090985921663, 2136.4528326705595, 1.01, 18.528163265306123, 23298.50933810001);
        checkValues(mmas_mem_adtsp, 900, 26571.19828064118, 2212.291048787445, 1.07, 19.04734693877551, 22941.787325871435);
        checkValues(mmas_mem_adtsp, 901, 28876.367708538663, 2378.7658413035574, 1.06, 23.502857142857142, 24909.177558616324);
        checkValues(mmas_mem_adtsp, 950, 27228.690816035705, 2508.3909196452687, 1.255, 24.4865306122449, 23329.93033468129);
        checkValues(mmas_mem_adtsp, 1000, 27277.586174933804, 2564.289121448508, 1.235, 21.70122448979592, 23147.150201020875);
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_5_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 100, 0.5, 10);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(30256.959914015813);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[63, 39, 53, 1, 43, 49, 72, 67, 84, 38, 29, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 47, 99, 40, 70, 13, 2, 28, 45, 42, 54, 82, 33, 8, 6, 56, 86, 50, 60, 24, 80, 68, 57, 92, 27, 66, 88, 41, 7, 91, 0, 62, 5, 48, 89, 9, 83, 35, 98, 37, 23, 17, 93, 21, 15, 87, 69, 65, 64, 25, 3, 96, 55, 30, 79, 74, 18, 52, 78, 71, 20, 73, 58, 10, 14, 16, 31, 46, 90, 97, 22, 44, 59, 76, 61, 19, 11, 26, 85, 34, 63]");

        checkValues(mmas_mem_adtsp, 1, 39000.247953448736, 3282.359634636903, 0.0, 55.57061224489796, 34158.42633851369);
        checkValues(mmas_mem_adtsp, 5, 33774.14826637872, 3772.5356402789876, 0.835, 26.991836734693877, 28958.97738428895);
        checkValues(mmas_mem_adtsp, 10, 33163.18263128281, 3045.5309043566835, 0.505, 22.58122448979592, 28028.342589271353);
        checkValues(mmas_mem_adtsp, 11, 41240.76795401538, 3296.9700524049595, 0.49, 46.41877551020408, 36244.96259316336);
        checkValues(mmas_mem_adtsp, 15, 38214.61084213032, 3077.4181521479613, 1.705, 40.85795918367347, 32050.53381746599);
        checkValues(mmas_mem_adtsp, 20, 36692.70206099816, 3086.5299130335857, 1.695, 38.351836734693876, 30123.660303191227);
        checkValues(mmas_mem_adtsp, 21, 39518.57040720933, 2725.8665026851877, 1.565, 44.965714285714284, 34122.52567236245);
        checkValues(mmas_mem_adtsp, 25, 36653.4015448637, 2840.1000317650637, 2.02, 41.816326530612244, 31153.409605028515);
        checkValues(mmas_mem_adtsp, 30, 35249.16234304293, 2596.0071248567874, 2.085, 38.2604081632653, 30869.84536512383);
        checkValues(mmas_mem_adtsp, 31, 35755.77220495781, 3911.580973230799, 1.965, 38.93551020408163, 30622.611405383024);
        checkValues(mmas_mem_adtsp, 35, 34256.165175130496, 3039.011361367364, 2.1, 36.97959183673469, 28767.03808239477);
        checkValues(mmas_mem_adtsp, 40, 33039.463723337154, 2321.317148613347, 2.09, 33.120816326530615, 28438.526911511235);
        checkValues(mmas_mem_adtsp, 41, 38597.47031346521, 3373.9995161004877, 1.91, 38.53387755102041, 32964.205275793196);
        checkValues(mmas_mem_adtsp, 45, 36810.43924581212, 2651.4626473533385, 2.0, 38.20734693877551, 31540.53637590475);
        checkValues(mmas_mem_adtsp, 50, 34964.62611036571, 2966.0289064694152, 2.205, 36.66204081632653, 29837.20846280936);
        checkValues(mmas_mem_adtsp, 51, 38694.17050134478, 3658.1557937477564, 2.14, 40.29714285714286, 32340.168318952798);
        checkValues(mmas_mem_adtsp, 55, 38177.70176539482, 3458.3299440613787, 2.275, 41.077551020408166, 30588.03162783119);
        checkValues(mmas_mem_adtsp, 60, 37621.915741281955, 3572.686534464222, 2.275, 41.51020408163265, 30588.03162783119);
        checkValues(mmas_mem_adtsp, 61, 37233.680820075664, 2851.523212782881, 2.215, 40.72816326530612, 30831.991355218575);
        checkValues(mmas_mem_adtsp, 65, 37146.24141491643, 2827.831050703631, 2.245, 40.62530612244898, 30758.709583473705);
        checkValues(mmas_mem_adtsp, 70, 35346.50038778709, 2701.789181862414, 2.305, 37.542857142857144, 28925.38059282869);
        checkValues(mmas_mem_adtsp, 71, 36035.38914018816, 3126.4085267567502, 2.3, 39.05061224489796, 30379.86398067201);
        checkValues(mmas_mem_adtsp, 75, 34599.66818419128, 2472.657342831239, 2.33, 36.33632653061225, 29580.269803691255);
        checkValues(mmas_mem_adtsp, 80, 34499.47710921514, 3139.688460826419, 2.33, 33.924897959183674, 29580.269803691255);
        checkValues(mmas_mem_adtsp, 81, 39500.75406372977, 3253.797459094246, 2.21, 44.030204081632654, 32228.479227861513);
        checkValues(mmas_mem_adtsp, 85, 38696.67550470519, 2886.950924715998, 2.21, 43.986938775510204, 32228.479227861513);
        checkValues(mmas_mem_adtsp, 90, 37459.01952517759, 3583.608706987801, 2.39, 42.096326530612245, 31820.904862162006);
        checkValues(mmas_mem_adtsp, 91, 36866.56722104131, 3751.197772438725, 2.395, 42.22857142857143, 31244.436119769754);
        checkValues(mmas_mem_adtsp, 95, 36827.25993146562, 3350.867162542154, 2.435, 40.83428571428571, 30922.757999264206);
        checkValues(mmas_mem_adtsp, 100, 36026.34156245383, 2626.937054266689, 2.445, 41.06448979591837, 30256.959914015813);
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_5_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 1000, 0.5, 100);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(29092.618832926928);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[68, 63, 39, 53, 1, 43, 49, 72, 67, 84, 38, 29, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 47, 99, 70, 40, 2, 42, 45, 28, 13, 54, 82, 33, 6, 8, 56, 19, 11, 34, 26, 85, 61, 59, 76, 22, 97, 90, 44, 31, 10, 14, 16, 20, 73, 58, 71, 83, 35, 98, 37, 23, 17, 93, 21, 15, 87, 69, 65, 25, 64, 3, 96, 55, 30, 79, 88, 41, 7, 91, 0, 62, 5, 48, 89, 9, 18, 74, 52, 78, 46, 27, 92, 66, 57, 60, 86, 50, 24, 80, 68]");

        checkValues(mmas_mem_adtsp, 1, 39000.247953448736, 3282.359634636903, 0.0, 55.57061224489796, 34158.42633851369);
        checkValues(mmas_mem_adtsp, 50, 32684.506558007775, 2903.8171463327308, 0.505, 18.212244897959184, 26751.00944319023);
        checkValues(mmas_mem_adtsp, 100, 31101.528363565685, 4716.464567523638, 0.5, 12.14530612244898, 26302.001485670193);
        checkValues(mmas_mem_adtsp, 101, 40543.65253771616, 2419.4872789773594, 0.48, 39.78204081632653, 37133.92976394659);
        checkValues(mmas_mem_adtsp, 150, 35910.39801986621, 5006.371690369376, 1.31, 25.505306122448978, 28203.478291324507);
        checkValues(mmas_mem_adtsp, 200, 34486.89989044737, 5231.3757735952095, 1.08, 23.986122448979593, 27607.199728513056);
        checkValues(mmas_mem_adtsp, 201, 38507.051645173364, 3340.037974234576, 1.045, 39.1534693877551, 32991.11531780052);
        checkValues(mmas_mem_adtsp, 250, 34187.00187337571, 3000.4483460200013, 1.48, 27.9665306122449, 28106.27351920288);
        checkValues(mmas_mem_adtsp, 300, 32828.966215630964, 3003.516330338339, 1.425, 25.671836734693876, 27235.7049691864);
        checkValues(mmas_mem_adtsp, 301, 38471.404742837294, 2769.126589047137, 1.31, 42.80408163265306, 33932.630936258945);
        checkValues(mmas_mem_adtsp, 350, 33645.56916335535, 3037.9316287688025, 1.79, 31.726530612244897, 27582.92558650231);
        checkValues(mmas_mem_adtsp, 400, 33351.705544873555, 2929.27016311199, 1.505, 28.368163265306123, 27219.979023905642);
        checkValues(mmas_mem_adtsp, 401, 37923.1865355478, 2410.6954073971433, 1.375, 40.01877551020408, 33725.0819503066);
        checkValues(mmas_mem_adtsp, 450, 34925.16942521141, 3132.6098161362975, 1.885, 36.23428571428571, 28452.469532041796);
        checkValues(mmas_mem_adtsp, 500, 33168.509714306805, 2308.130548215234, 1.74, 34.02040816326531, 28233.38467851035);
        checkValues(mmas_mem_adtsp, 501, 39855.18080235753, 4125.763964547306, 1.68, 38.412244897959184, 33250.24340563787);
        checkValues(mmas_mem_adtsp, 550, 35519.48794440203, 2889.6019809174973, 2.1, 34.26204081632653, 29883.991722977313);
        checkValues(mmas_mem_adtsp, 600, 35589.11914915117, 3301.518455386736, 1.915, 35.72163265306123, 28646.799892350355);
        checkValues(mmas_mem_adtsp, 601, 38295.722330200144, 2529.9442631137103, 1.73, 41.795102040816325, 34521.09596714699);
        checkValues(mmas_mem_adtsp, 650, 35277.50399483224, 2755.543618554852, 2.065, 35.17469387755102, 29966.224604570547);
        checkValues(mmas_mem_adtsp, 700, 35006.997811974354, 3073.4431576530937, 1.93, 33.16, 28985.220973934793);
        checkValues(mmas_mem_adtsp, 701, 36936.37722023291, 2985.7928612709416, 1.83, 37.38938775510204, 31192.039442135818);
        checkValues(mmas_mem_adtsp, 750, 33539.579352430876, 2837.1641180131883, 2.035, 28.58530612244898, 28085.6802371391);
        checkValues(mmas_mem_adtsp, 800, 32655.469208160284, 2689.0528589032215, 1.68, 24.964897959183673, 27805.457923615253);
        checkValues(mmas_mem_adtsp, 801, 37426.54369154951, 2290.218708062072, 1.54, 36.711020408163265, 33319.7335402732);
        checkValues(mmas_mem_adtsp, 850, 35689.81328544508, 2847.8540754402957, 1.815, 33.42938775510204, 28790.227144283508);
        checkValues(mmas_mem_adtsp, 900, 36162.87066623461, 3099.9422768430018, 1.6, 28.93795918367347, 28696.964019025727);
        checkValues(mmas_mem_adtsp, 901, 37776.55225078792, 2998.786412102575, 1.62, 39.17469387755102, 32291.410183217813);
        checkValues(mmas_mem_adtsp, 950, 35874.27260632837, 3028.921868488576, 1.865, 37.01632653061225, 30026.110049221563);
        checkValues(mmas_mem_adtsp, 1000, 34713.69648500396, 3380.324081008932, 1.68, 36.881632653061224, 29092.618832926928);
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_75_freq_10() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 100, 0.75, 10);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(35849.740145438955);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[26, 11, 19, 85, 34, 61, 59, 76, 22, 97, 90, 44, 31, 10, 16, 14, 58, 73, 20, 71, 9, 83, 98, 17, 23, 37, 35, 48, 5, 62, 0, 46, 91, 7, 41, 88, 30, 79, 55, 96, 74, 18, 89, 78, 87, 15, 21, 93, 52, 65, 64, 3, 69, 25, 66, 57, 27, 92, 50, 86, 56, 6, 8, 54, 82, 33, 45, 28, 2, 42, 13, 70, 40, 99, 47, 51, 77, 95, 29, 67, 84, 38, 4, 36, 12, 32, 75, 94, 81, 43, 72, 49, 1, 53, 39, 63, 68, 24, 80, 60, 26]");

        checkValues(mmas_mem_adtsp, 1, 46813.33363202887, 3211.946543021583, 0.0, 56.90775510204082, 39102.466113931514);
        checkValues(mmas_mem_adtsp, 5, 39599.46161380731, 3574.735763073008, 0.62, 16.902857142857144, 35793.021310850745);
        checkValues(mmas_mem_adtsp, 10, 40180.980895298264, 4164.783865309641, 0.525, 15.244081632653062, 34009.056434961676);
        checkValues(mmas_mem_adtsp, 11, 48203.995461323655, 3982.58288687091, 0.475, 43.23591836734694, 42654.05958149932);
        checkValues(mmas_mem_adtsp, 15, 40161.30768200496, 3678.520202292988, 1.655, 41.59510204081633, 35020.936769367385);
        checkValues(mmas_mem_adtsp, 20, 38977.60053806807, 3925.4466837749237, 1.815, 36.83591836734694, 32458.36178657126);
        checkValues(mmas_mem_adtsp, 21, 44700.77730728271, 3329.587331868826, 1.51, 42.587755102040816, 39656.84952578225);
        checkValues(mmas_mem_adtsp, 25, 44898.247244283615, 3607.557510574754, 1.795, 41.993469387755106, 36063.89840991347);
        checkValues(mmas_mem_adtsp, 30, 44402.08828774766, 3198.7548215345787, 1.795, 41.68326530612245, 36063.89840991347);
        checkValues(mmas_mem_adtsp, 31, 44940.35874638533, 3731.1802990515857, 1.73, 42.519183673469385, 37333.82642831613);
        checkValues(mmas_mem_adtsp, 35, 44173.119477021006, 3711.4748866580176, 1.89, 42.01714285714286, 36923.4667432241);
        checkValues(mmas_mem_adtsp, 40, 42412.76484578352, 4135.647450836298, 2.105, 43.03591836734694, 35482.130747558294);
        checkValues(mmas_mem_adtsp, 41, 42623.75372578472, 3662.7359253508994, 2.01, 43.27673469387755, 36037.14202730256);
        checkValues(mmas_mem_adtsp, 45, 40812.04450215691, 3563.5609853618116, 2.215, 40.27428571428572, 33963.4529747994);
        checkValues(mmas_mem_adtsp, 50, 41169.808772624245, 3412.1182010049024, 2.215, 39.369795918367345, 33963.4529747994);
        checkValues(mmas_mem_adtsp, 51, 41565.09956565105, 3675.71251555494, 2.145, 41.8465306122449, 34901.44772117682);
        checkValues(mmas_mem_adtsp, 55, 40181.24497850673, 4194.439988549772, 2.28, 38.9730612244898, 33428.069956548294);
        checkValues(mmas_mem_adtsp, 60, 38089.81958794267, 3306.516832668838, 2.315, 36.674285714285716, 31646.093626011174);
        checkValues(mmas_mem_adtsp, 61, 45079.43875982403, 3694.013742186096, 2.22, 46.24, 39432.41209639642);
        checkValues(mmas_mem_adtsp, 65, 42631.46680552855, 3097.856347215129, 2.405, 44.06040816326531, 35543.48926299357);
        checkValues(mmas_mem_adtsp, 70, 41225.351954287726, 2518.7320397050466, 2.415, 42.28408163265306, 34983.788118236465);
        checkValues(mmas_mem_adtsp, 71, 43405.7003951768, 3837.2876361752196, 2.345, 43.49387755102041, 37708.66137382889);
        checkValues(mmas_mem_adtsp, 75, 42465.05666880312, 3180.080930285096, 2.485, 43.0865306122449, 36855.672082413235);
        checkValues(mmas_mem_adtsp, 80, 42987.228221897945, 4024.0985086709106, 2.565, 43.2130612244898, 36466.05975659491);
        checkValues(mmas_mem_adtsp, 81, 41915.03558980171, 3363.9381956900866, 2.5, 41.543673469387755, 34141.43060804401);
        checkValues(mmas_mem_adtsp, 85, 41265.955193934584, 3374.668069181376, 2.535, 41.42367346938776, 33283.17691880134);
        checkValues(mmas_mem_adtsp, 90, 40762.88238891378, 3180.239028341925, 2.535, 38.86693877551021, 32983.65748523466);
        checkValues(mmas_mem_adtsp, 91, 43807.49046025831, 3207.0411239860537, 2.42, 43.78204081632653, 37742.50466004754);
        checkValues(mmas_mem_adtsp, 95, 44184.80102286926, 3727.5249412824483, 2.555, 43.12571428571429, 35849.740145438955);
        checkValues(mmas_mem_adtsp, 100, 42830.78178235091, 2725.5476044865654, 2.665, 42.2130612244898, 35849.740145438955);
    }

    @Test
    public void test_mmas_mem_asymmetric_dynamic_tsp_kroA100_with_seed_1_mag_0_75_freq_100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_MEM_ADTSP mmas_mem_adtsp = new MMAS_MEM_ADTSP(graph, 0.8, 1000, 0.75, 100);
        mmas_mem_adtsp.setMmasSeed(1);
        mmas_mem_adtsp.setDbgpSeed(1);
        mmas_mem_adtsp.setStatisticInterval(1);
        mmas_mem_adtsp.setShowLog(false);
        mmas_mem_adtsp.run();
        assertThat(mmas_mem_adtsp.getGlobalStatistics().getBestSoFar()).isEqualTo(32326.680745082165);
        assertThat(getTourString(mmas_mem_adtsp)).isEqualTo("[31, 14, 16, 10, 58, 73, 20, 71, 9, 83, 89, 5, 48, 91, 7, 41, 88, 30, 79, 55, 3, 64, 65, 25, 69, 21, 93, 87, 15, 52, 78, 17, 23, 37, 35, 98, 18, 74, 96, 62, 0, 27, 92, 66, 57, 56, 8, 6, 86, 50, 60, 80, 24, 68, 63, 39, 53, 1, 72, 49, 67, 84, 29, 51, 77, 95, 4, 36, 12, 32, 75, 94, 81, 43, 38, 47, 99, 40, 70, 13, 2, 42, 28, 45, 33, 54, 82, 26, 11, 19, 85, 34, 59, 76, 61, 44, 90, 97, 22, 46, 31]");

        checkValues(mmas_mem_adtsp, 1, 46813.33363202887, 3211.946543021583, 0.0, 56.90775510204082, 39102.466113931514);
        checkValues(mmas_mem_adtsp, 50, 39443.07290079631, 3744.79615297905, 0.5, 15.20734693877551, 32506.772493624958);
        checkValues(mmas_mem_adtsp, 100, 39079.89273164137, 3827.444213736607, 0.5, 15.249795918367347, 31862.843998618562);
        checkValues(mmas_mem_adtsp, 101, 46056.3667674322, 3461.990711444772, 0.47, 43.19265306122449, 38566.23480144427);
        checkValues(mmas_mem_adtsp, 150, 37773.00258936762, 3990.5811111227445, 1.45, 25.78448979591837, 30415.34344861163);
        checkValues(mmas_mem_adtsp, 200, 37051.096275000804, 3320.6720568679925, 1.45, 22.525714285714287, 29924.684173596524);
        checkValues(mmas_mem_adtsp, 201, 46226.13709837318, 3948.729868718614, 1.205, 41.53469387755102, 40070.26096537813);
        checkValues(mmas_mem_adtsp, 250, 42565.276755169, 3419.780864372922, 1.76, 37.62285714285714, 34513.09797027479);
        checkValues(mmas_mem_adtsp, 300, 40105.55012359815, 3153.30123715039, 1.66, 35.72489795918367, 34513.09797027479);
        checkValues(mmas_mem_adtsp, 301, 44935.87546644194, 3760.371886721194, 1.44, 43.12979591836735, 37696.785272858855);
        checkValues(mmas_mem_adtsp, 350, 39572.11614068059, 3729.653516597786, 2.03, 35.98530612244898, 32431.20410536945);
        checkValues(mmas_mem_adtsp, 400, 38599.632148674114, 4196.250462518666, 1.84, 34.695510204081636, 31746.954134585652);
        checkValues(mmas_mem_adtsp, 401, 41619.79139306576, 2545.0617401633267, 1.76, 41.39918367346939, 37020.84187433434);
        checkValues(mmas_mem_adtsp, 450, 38323.253459199404, 3026.6577625520126, 2.13, 33.015510204081636, 31436.12958218601);
        checkValues(mmas_mem_adtsp, 500, 37763.72274209091, 3454.0047823856457, 1.87, 28.55265306122449, 30635.089837575626);
        checkValues(mmas_mem_adtsp, 501, 41390.77282142188, 2476.8579954460865, 1.61, 39.561632653061224, 36571.684262704555);
        checkValues(mmas_mem_adtsp, 550, 37492.175735162615, 3112.5752607766954, 1.84, 35.1265306122449, 30742.693606907982);
        checkValues(mmas_mem_adtsp, 600, 36618.69345420751, 3154.737071861262, 1.685, 30.306122448979593, 30280.4512539116);
        checkValues(mmas_mem_adtsp, 601, 44981.962736964095, 2990.348694579615, 1.53, 42.59265306122449, 39749.188170783855);
        checkValues(mmas_mem_adtsp, 650, 39361.54510750556, 3854.3588249327886, 2.03, 36.9134693877551, 34054.806832815826);
        checkValues(mmas_mem_adtsp, 700, 39394.781737779034, 2770.19806362495, 1.735, 30.438367346938776, 32896.85871757041);
        checkValues(mmas_mem_adtsp, 701, 42022.68201484372, 3206.2494246549013, 1.675, 40.52734693877551, 36618.56345906919);
        checkValues(mmas_mem_adtsp, 750, 40010.86035322166, 2523.1574483806926, 1.915, 36.391020408163264, 33606.702420824404);
        checkValues(mmas_mem_adtsp, 800, 39644.13970309873, 3274.7207302255188, 1.7, 33.91836734693877, 33071.43188220673);
        checkValues(mmas_mem_adtsp, 801, 44252.05802566186, 4028.7986698731324, 1.56, 42.987755102040815, 38401.61274482111);
        checkValues(mmas_mem_adtsp, 850, 38094.834877176756, 2781.2324752265336, 1.94, 31.261224489795918, 31411.212968474603);
        checkValues(mmas_mem_adtsp, 900, 37144.930950131136, 3116.515994014384, 1.74, 28.260408163265307, 29993.997552888817);
        checkValues(mmas_mem_adtsp, 901, 43333.52072566531, 3645.790211127233, 1.64, 37.138775510204084, 37723.714757843234);
        checkValues(mmas_mem_adtsp, 950, 40538.087507866054, 3443.207914208309, 1.895, 33.94122448979592, 32942.98088377498);
        checkValues(mmas_mem_adtsp, 1000, 40511.72425508875, 3216.984862431338, 1.66, 33.140408163265306, 32326.680745082165);
    }

    private void printTest(MMAS_MEM_ADTSP mmas_mem_adtsp) {
        for (IterationStatistic iter : mmas_mem_adtsp.getIterationStatistics()) {
            System.out.println(
                    "checkValues(mmas_mem_adtsp, " +
                            ((int) iter.getIteration()) + ", " +
                            iter.getIterationMean() + ", " +
                            iter.getIterationSd() + ", " +
                            iter.getBranchFactor() + ", " +
                            iter.getDiversity() + ", " +
                            iter.getBestSoFar() + ");"
            );
        }
    }

    private void checkValues(MMAS_MEM_ADTSP mmas_mem_adtsp, int iteration, double mean, double sd, double branchFactor, double div, double bsf) {
        assertThat(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getIteration()).isEqualTo(iteration);
        assertThat(round(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getIterationMean())).isEqualTo(round(mean));
        assertThat(round(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getIterationSd())).isEqualTo(round(sd));
        assertThat(round(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getBranchFactor())).isEqualTo(round(branchFactor));
        assertThat(round(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getDiversity())).isEqualTo(round(div));
        assertThat(round(mmas_mem_adtsp.getIterationStatistics().get(iteration - 1).getBestSoFar())).isEqualTo(round(bsf));
    }

    private String getTourString(MMAS_MEM_ADTSP mmas_mem_adtsp) {
        StringBuilder tour = new StringBuilder("[");
        for (Vertex vertex : mmas_mem_adtsp.getGlobalStatistics().getBestRoute()) {
            tour.append(vertex.getId()).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

    private double round(double value) {
        return Double.valueOf(String.format(Locale.US, "%.2f", value));
    }
}
