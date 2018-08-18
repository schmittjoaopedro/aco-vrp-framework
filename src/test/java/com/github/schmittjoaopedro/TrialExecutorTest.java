package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_ADTSP;
import com.github.schmittjoaopedro.tools.IterationStatistic;
import com.github.schmittjoaopedro.tools.TrialExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class TrialExecutorTest {

    @Test
    public void trial_mmas_adtsp_kroA100_mag_0_75_fre_100_test() {
        int trials = 15;
        List<Runnable> algs = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            MMAS_ADTSP mmas_adtsp = new MMAS_ADTSP(getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile(), 0.8, 1000, 0.75, 100);
            //MMAS_MEM_MADTSP mmas_adtsp = new MMAS_MEM_MADTSP(getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile(), 0.8, 100, 0.1, 10);
            //MMAS_MADTSP mmas_adtsp = new MMAS_MADTSP(getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile(), 0.8, 100, 0.1, 10);
            mmas_adtsp.setDbgpSeed(1);
            mmas_adtsp.setMmasSeed(i);
            mmas_adtsp.setStatisticInterval(1);
            mmas_adtsp.setShowLog(false);
            algs.add(mmas_adtsp);
        }
        TrialExecutor trialExecutor = new TrialExecutor();
        trialExecutor.runAlgorithms(algs);
        List<List<IterationStatistic>> results = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            //results.add(((MMAS_MEM_MADTSP) algs.get(i)).getIterationStatistics());
            //results.add(((MMAS_MADTSP) algs.get(i)).getIterationStatistics());
            results.add(((MMAS_ADTSP) algs.get(i)).getIterationStatistics());
        }
        List<IterationStatistic> result = trialExecutor.getUnifiedStatistics(results);

        printTest(result);

        checkValues(result, 1, 46497.295563813444, 3619.6582887632603, 0.0, 57.23085714285714, 39702.02092143756);
        checkValues(result, 50, 36977.67030563103, 3690.4982791248212, 0.5096666666666666, 13.151183673469388, 32357.342481832096);
        checkValues(result, 100, 36905.28604320763, 3873.7315559204576, 0.5013333333333334, 12.554394557823128, 31986.49847718039);
        checkValues(result, 101, 45234.25313702095, 3233.8117594900928, 0.471, 39.368326530612244, 39219.09373513631);
        checkValues(result, 150, 34195.72540261023, 3984.828690527376, 0.5083333333333333, 10.158204081632652, 29899.909681641497);
        checkValues(result, 200, 33309.11827603101, 3762.990669289703, 0.5013333333333333, 10.085115646258503, 29305.15966682778);
        checkValues(result, 201, 46891.03542802885, 3161.208915868789, 0.48633333333333345, 34.59199999999999, 40892.93708943096);
        checkValues(result, 250, 39762.99429861867, 3644.2098943842084, 0.5106666666666667, 15.081034013605441, 34157.18390799236);
        checkValues(result, 300, 38225.760850233986, 3873.171123006587, 0.504, 12.97039455782313, 33076.147392788705);
        checkValues(result, 301, 47355.64886679858, 3332.8502287929637, 0.47900000000000004, 37.838095238095235, 40973.5301912013);
        checkValues(result, 350, 36795.58022492085, 3866.224477464964, 0.516, 13.970068027210885, 32035.145563446637);
        checkValues(result, 400, 35821.41617724428, 3898.56164873153, 0.501, 12.01213605442177, 31348.226181987262);
        checkValues(result, 401, 44945.195199391404, 3360.7219596515156, 0.47900000000000004, 39.46144217687074, 38967.5291504418);
        checkValues(result, 450, 34676.47211051632, 3476.311556621472, 0.5103333333333333, 12.266503401360545, 30614.540470909877);
        checkValues(result, 500, 34078.20382922768, 3399.078113842747, 0.5023333333333333, 12.026775510204082, 30067.79843594976);
        checkValues(result, 501, 45318.903832076794, 3651.765699702315, 0.484, 34.325605442176865, 38067.97483470992);
        checkValues(result, 550, 34488.07496885861, 3574.5164074232694, 0.5109999999999999, 11.333387755102041, 30520.81141027292);
        checkValues(result, 600, 34440.14900396311, 3809.4619896, 0.5, 11.36712925170068, 29858.400073930603);
        checkValues(result, 601, 46006.68141783139, 3013.4482458943444, 0.48166666666666663, 32.58634013605442, 40480.873284472975);
        checkValues(result, 650, 38219.76610037383, 3797.9527666743447, 0.5076666666666667, 14.120489795918367, 33294.12589281612);
        checkValues(result, 700, 36817.300131099364, 3437.3889476314775, 0.503, 12.855292517006802, 32107.40574366108);
        checkValues(result, 701, 46006.18768987931, 3358.964699102375, 0.48299999999999993, 36.29991836734693, 40195.76053586291);
        checkValues(result, 750, 38002.29572400046, 3657.8853557315297, 0.5166666666666666, 15.843156462585034, 32648.31770004277);
        checkValues(result, 800, 36881.4638655483, 3820.263788193958, 0.5006666666666667, 13.562503401360546, 32050.039644181543);
        checkValues(result, 801, 45803.775729887646, 3422.668906732358, 0.4776666666666666, 36.755374149659865, 39637.75186847798);
        checkValues(result, 850, 35877.93090326028, 3489.1750338020897, 0.5210000000000001, 15.047292517006802, 31482.962874889352);
        checkValues(result, 900, 35773.04452842472, 3687.0690850022215, 0.5036666666666666, 13.598911564625853, 30836.10315549452);
        checkValues(result, 901, 46773.43822543896, 3412.8696055797113, 0.481, 36.84952380952381, 40676.92876152646);
        checkValues(result, 950, 38930.69077188282, 3773.5300142314472, 0.5106666666666667, 15.127292517006802, 33536.53964744547);
        checkValues(result, 1000, 37735.608475590874, 4104.889452835012, 0.505, 14.3694149659864, 32466.815209466382);
    }

    private void printTest(List<IterationStatistic> results) {
        for (IterationStatistic iter : results) {
            /*System.out.println(
                    "checkValues(mmas_adtsp, " +
                            ((int) iter.getIteration()) + ", " +
                            iter.getIterationMean() + ", " +
                            iter.getIterationSd() + ", " +
                            iter.getBranchFactor() + ", " +
                            iter.getDiversity() + ", " +
                            iter.getBestSoFar() + ");"
            );*/
            System.out.println(iter);
        }
    }

    private void checkValues(List<IterationStatistic> results, int iteration, double mean, double sd, double branchFactor, double div, double bsf) {
        assertThat(results.get(iteration - 1).getIteration()).isEqualTo(iteration);
        assertThat(round(results.get(iteration - 1).getIterationMean())).isEqualTo(round(mean));
        assertThat(round(results.get(iteration - 1).getIterationSd())).isEqualTo(round(sd));
        assertThat(round(results.get(iteration - 1).getBranchFactor())).isEqualTo(round(branchFactor));
        assertThat(round(results.get(iteration - 1).getDiversity())).isEqualTo(round(div));
        assertThat(round(results.get(iteration - 1).getBestSoFar())).isEqualTo(round(bsf));
    }

    private double round(double value) {
        return Double.valueOf(String.format(Locale.US, "%.2f", value));
    }
}
