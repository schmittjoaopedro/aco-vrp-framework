package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.pdptw.common.StatisticsDTO;
import com.github.schmittjoaopedro.rinsim.dvrptwacs.DebuggerUtils;
import com.github.schmittjoaopedro.rinsim.dvrptwacs.RINSIM_ACS_DVRPTW;
import com.github.schmittjoaopedro.vrp.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.dvrptwacs.LoggerOutput;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RINSIM_ACS_DVRPTW_Test {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dvrptw").getFile().substring(1)).toString();
    }

    @Test
    public void rinsim_dynamic_vrp_time_window_acs_r103_0_1_test() throws Exception {
        File file = Paths.get(rootDirectory, "r103-0.1.txt").toFile();
        RINSIM_ACS_DVRPTW rinsimAcsDvrptw = new RINSIM_ACS_DVRPTW(file, false);
        rinsimAcsDvrptw.run();
        Map<String, List<String>> salesmenRoutes = rinsimAcsDvrptw.getSalesmenRouteTrace();
        LoggerOutput loggerOutput = rinsimAcsDvrptw.getSolverLogs();
        StatisticsDTO statisticsDTO = rinsimAcsDvrptw.getStatistics();

        // ACS assert
        int lineCount = 0;
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("DVRPTW_ACS MinSum >> Solving dynamic VRPTW instance: r103-0.1");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("No. of customers' requests (except the depot): 100, among which 97 are a-priori known (available nodes excluding the depot) and 3 are dynamic requests");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("\nRun Ant Colony System #1");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Scalling value = 0.43478260869565216");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("\nInitial (nearest neighbour tour) total tour length: 576.5586588877334 (scalled value = 1326.0849154417867); Number of vehicles used: 14");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=562.5376010977425 (scalled value = 1293.8364825248077)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=560.1327697937832 (scalled value = 1288.3053705257014)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=541.0653625954865 (scalled value = 1244.450333969619)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("1 new nodes became available (known): 11 ");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Number of total available (known) nodes (excluding the depot): 98");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Best ant after inserting the new available nodes>> No. of used vehicles=15 total tours length=570.2314666498314 (scalled value = 1311.5323732946124)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=15 total tours length=569.1183542719467 (scalled value = 1308.9722148254775)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=15 total tours length=560.6605711416032 (scalled value = 1289.5193136256873)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=566.3952447434705 (scalled value = 1302.709062909982)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=544.9207194427544 (scalled value = 1253.317654718335)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=539.8254035768805 (scalled value = 1241.5984282268253)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("1 new nodes became available (known): 30 ");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Number of total available (known) nodes (excluding the depot): 99");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Best ant after inserting the new available nodes>> No. of used vehicles=15 total tours length=561.9950536359795 (scalled value = 1292.588623362753)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=552.03983112901 (scalled value = 1269.691611596723)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=550.0637974965097 (scalled value = 1265.1467342419724)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("1 new nodes became available (known): 100 ");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Number of total available (known) nodes (excluding the depot): 100");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Best ant after inserting the new available nodes>> No. of used vehicles=14 total tours length=561.6958560453328 (scalled value = 1291.9004689042654)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=560.6126550340819 (scalled value = 1289.4091065783882)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=556.1009040362512 (scalled value = 1279.032079283378)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=555.0428414576861 (scalled value = 1276.598535352678)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=554.9951576454781 (scalled value = 1276.4888625845997)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=554.4593046282453 (scalled value = 1275.256400644964)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=554.0709380594851 (scalled value = 1274.3631575368158)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=550.1378687174846 (scalled value = 1265.3170980502146)");
        assertThat(loggerOutput.get(lineCount++)).isEqualTo("Updated Best so far ant >> No. of used vehicles=14 total tours length=550.0901849052761 (scalled value = 1265.207425282135)");

        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 76 	TimeWindow = [ 32 , 36 ]	BeginService =  31.74	CurrentTime = 33 	Route = *76 , 79 , 29 , 24 , 68 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 79 	TimeWindow = [ 40 , 44 ]	BeginService =  40.43	CurrentTime = 41 	Route = *76*,*79 ,  3 , 29 , 68 , 24 , 80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 3  	TimeWindow = [  0 , 86 ]	BeginService =  46.35	CurrentTime = 47 	Route = *76*,*79*,* 3 , 29 , 68 , 24 , 80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 29 	TimeWindow = [  0 , 83 ]	BeginService =  54.82	CurrentTime = 56 	Route = *76*,*79*,* 3*,*29 , 68 , 24 , 80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 68 	TimeWindow = [ 62 , 66 ]	BeginService =  62.89	CurrentTime = 64 	Route = *76*,*79*,* 3*,*29*,*68 , 24 , 80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 24 	TimeWindow = [  0 , 83 ]	BeginService =  71.52	CurrentTime = 73 	Route = *76*,*79*,* 3*,*29*,*68*,*24 , 80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 80 	TimeWindow = [ 79 , 83 ]	BeginService =  79.87	CurrentTime = 81 	Route = *76*,*79*,* 3*,*29*,*68*,*24*,*80 , 12 ");
        assertThat(salesmenRoutes.get("Salesman-0").get(lineCount++)).isEqualTo("Salesman = 0 	Parcel = 12 	TimeWindow = [  0 , 89 ]	BeginService =  86.97	CurrentTime = 88 	Route = *76*,*79*,* 3*,*29*,*68*,*24*,*80*,*12 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-1").get(lineCount++)).isEqualTo("Salesman = 1 	Parcel = 40 	TimeWindow = [ 37 , 41 ]	BeginService =  36.96	CurrentTime = 38 	Route = *40 , 53 ");
        assertThat(salesmenRoutes.get("Salesman-1").get(lineCount++)).isEqualTo("Salesman = 1 	Parcel = 53 	TimeWindow = [ 41 , 46 ]	BeginService =  44.22	CurrentTime = 45 	Route = *40*,*53 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 95 	TimeWindow = [  0 , 89 ]	BeginService =   6.46	CurrentTime = 7  	Route = *95 , 42 , 43 , 38 , 86 , 16 , 61 ,  5 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 42 	TimeWindow = [ 13 , 18 ]	BeginService =  16.05	CurrentTime = 17 	Route = *95*,*42 , 43 , 38 , 86 , 16 , 61 ,  5 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 43 	TimeWindow = [  0 , 80 ]	BeginService =  24.33	CurrentTime = 25 	Route = *95*,*42*,*43 , 87 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 87 	TimeWindow = [ 40 , 45 ]	BeginService =  40.43	CurrentTime = 41 	Route = *95*,*42*,*43*,*87 , 97 , 59 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 97 	TimeWindow = [  0 , 88 ]	BeginService =  46.63	CurrentTime = 48 	Route = *95*,*42*,*43*,*87*,*97 , 37 , 98 , 100 , 59 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 37 	TimeWindow = [  0 , 86 ]	BeginService =  53.19	CurrentTime = 54 	Route = *95*,*42*,*43*,*87*,*97*,*37 , 98 , 100 , 59 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 98 	TimeWindow = [  0 , 86 ]	BeginService =  58.15	CurrentTime = 59 	Route = *95*,*42*,*43*,*87*,*97*,*37*,*98 , 100 , 59 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 100	TimeWindow = [ 80 , 85 ]	BeginService =  80.43	CurrentTime = 81 	Route = *95*,*42*,*43*,*87*,*97*,*37*,*98*,*100 , 59 ");
        assertThat(salesmenRoutes.get("Salesman-2").get(lineCount++)).isEqualTo("Salesman = 2 	Parcel = 59 	TimeWindow = [  0 , 88 ]	BeginService =  87.70	CurrentTime = 89 	Route = *95*,*42*,*43*,*87*,*97*,*37*,*98*,*100*,*59 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 27 	TimeWindow = [ 16 , 20 ]	BeginService =  16.09	CurrentTime = 17 	Route = *27 , 69 , 88 ,  8 , 46 , 47 , 48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 69 	TimeWindow = [ 22 , 26 ]	BeginService =  23.60	CurrentTime = 25 	Route = *27*,*69 , 88 ,  8 , 46 , 47 , 48 , 82 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 88 	TimeWindow = [ 32 , 37 ]	BeginService =  33.20	CurrentTime = 34 	Route = *27*,*69*,*88 ,  8 , 46 , 47 , 48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 8  	TimeWindow = [ 41 , 46 ]	BeginService =  45.53	CurrentTime = 47 	Route = *27*,*69*,*88*,* 8 , 46 , 47 , 48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 46 	TimeWindow = [  0 , 80 ]	BeginService =  53.98	CurrentTime = 55 	Route = *27*,*69*,*88*,* 8*,*46 , 47 , 48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 47 	TimeWindow = [  0 , 80 ]	BeginService =  62.68	CurrentTime = 64 	Route = *27*,*69*,*88*,* 8*,*46*,*47 , 48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 48 	TimeWindow = [  0 , 83 ]	BeginService =  69.81	CurrentTime = 71 	Route = *27*,*69*,*88*,* 8*,*46*,*47*,*48 , 82 ");
        assertThat(salesmenRoutes.get("Salesman-3").get(lineCount++)).isEqualTo("Salesman = 3 	Parcel = 82 	TimeWindow = [  0 , 85 ]	BeginService =  76.50	CurrentTime = 77 	Route = *27*,*69*,*88*,* 8*,*46*,*47*,*48*,*82 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 31 	TimeWindow = [  0 , 88 ]	BeginService =   7.59	CurrentTime = 9  	Route = *31 , 62 , 11 , 63 , 10 , 90 , 32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 62 	TimeWindow = [ 25 , 30 ]	BeginService =  25.22	CurrentTime = 26 	Route = *31*,*62 , 11 , 63 , 10 , 32 , 90 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 11 	TimeWindow = [ 29 , 33 ]	BeginService =  33.07	CurrentTime = 34 	Route = *31*,*62*,*11 , 63 , 10 , 90 , 32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 63 	TimeWindow = [  0 , 80 ]	BeginService =  40.92	CurrentTime = 42 	Route = *31*,*62*,*11*,*63 , 10 , 90 , 32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 10 	TimeWindow = [ 54 , 58 ]	BeginService =  53.91	CurrentTime = 55 	Route = *31*,*62*,*11*,*63*,*10 , 90 , 32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 90 	TimeWindow = [  0 , 81 ]	BeginService =  61.34	CurrentTime = 62 	Route = *31*,*62*,*11*,*63*,*10*,*90 , 32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 32 	TimeWindow = [  0 , 81 ]	BeginService =  67.63	CurrentTime = 69 	Route = *31*,*62*,*11*,*63*,*10*,*90*,*32 , 70 ");
        assertThat(salesmenRoutes.get("Salesman-4").get(lineCount++)).isEqualTo("Salesman = 4 	Parcel = 70 	TimeWindow = [ 79 , 83 ]	BeginService =  79.13	CurrentTime = 80 	Route = *31*,*62*,*11*,*63*,*10*,*90*,*32*,*70 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 50 	TimeWindow = [  0 , 88 ]	BeginService =   7.38	CurrentTime = 8  	Route = *50 , 33 , 81 , 30 ,  9 , 66 , 20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 33 	TimeWindow = [ 16 , 20 ]	BeginService =  16.09	CurrentTime = 17 	Route = *50*,*33 , 81 , 30 ,  9 , 66 , 20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 30 	TimeWindow = [ 31 , 35 ]	BeginService =  30.87	CurrentTime = 32 	Route = *50*,*33*,*30 ,  9 , 66 , 20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 9  	TimeWindow = [ 42 , 47 ]	BeginService =  42.17	CurrentTime = 43 	Route = *50*,*33*,*30*,* 9 , 66 , 20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 66 	TimeWindow = [ 55 , 60 ]	BeginService =  55.22	CurrentTime = 56 	Route = *50*,*33*,*30*,* 9*,*66 , 20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 20 	TimeWindow = [  0 , 82 ]	BeginService =  63.45	CurrentTime = 64 	Route = *50*,*33*,*30*,* 9*,*66*,*20 , 51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 51 	TimeWindow = [  0 , 84 ]	BeginService =  71.31	CurrentTime = 72 	Route = *50*,*33*,*30*,* 9*,*66*,*20*,*51 ,  1 ");
        assertThat(salesmenRoutes.get("Salesman-5").get(lineCount++)).isEqualTo("Salesman = 5 	Parcel = 1  	TimeWindow = [  0 , 89 ]	BeginService =  80.89	CurrentTime = 82 	Route = *50*,*33*,*30*,* 9*,*66*,*20*,*51*,* 1 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 94 	TimeWindow = [  0 , 90 ]	BeginService =   5.24	CurrentTime = 6  	Route = *94 , 96 , 99 ,  6 , 59 , 37 , 98 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 96 	TimeWindow = [  0 , 89 ]	BeginService =  11.32	CurrentTime = 12 	Route = *94*,*96 , 99 ,  6 , 59 , 37 , 98 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 5  	TimeWindow = [  0 , 87 ]	BeginService =  18.98	CurrentTime = 21 	Route = *94*,*96*,* 5 , 84 , 17 , 93 , 59 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 84 	TimeWindow = [ 44 , 48 ]	BeginService =  43.91	CurrentTime = 45 	Route = *94*,*96*,* 5*,*84 , 17 , 60 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 17 	TimeWindow = [ 68 , 73 ]	BeginService =  68.26	CurrentTime = 69 	Route = *94*,*96*,* 5*,*84*,*17 , 60 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 60 	TimeWindow = [  0 , 87 ]	BeginService =  78.11	CurrentTime = 79 	Route = *94*,*96*,* 5*,*84*,*17*,*60 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-6").get(lineCount++)).isEqualTo("Salesman = 6 	Parcel = 89 	TimeWindow = [  0 , 92 ]	BeginService =  86.39	CurrentTime = 87 	Route = *94*,*96*,* 5*,*84*,*17*,*60*,*89 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 18 	TimeWindow = [  0 , 89 ]	BeginService =   6.87	CurrentTime = 8  	Route = *18 , 45 , 84 , 17 , 83 , 60 ");
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 45 	TimeWindow = [ 14 , 18 ]	BeginService =  17.37	CurrentTime = 18 	Route = *18*,*45 , 84 , 17 , 83 , 60 ");
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 83 	TimeWindow = [  0 , 86 ]	BeginService =  25.22	CurrentTime = 27 	Route = *18*,*45*,*83 , 84 , 17 , 60 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 99 	TimeWindow = [ 36 , 40 ]	BeginService =  36.09	CurrentTime = 37 	Route = *18*,*45*,*83*,*99 ,  6 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 6  	TimeWindow = [ 43 , 47 ]	BeginService =  43.22	CurrentTime = 44 	Route = *18*,*45*,*83*,*99*,* 6 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-7").get(lineCount++)).isEqualTo("Salesman = 7 	Parcel = 13 	TimeWindow = [ 69 , 73 ]	BeginService =  69.13	CurrentTime = 70 	Route = *18*,*45*,*83*,*99*,* 6*,*13 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 2  	TimeWindow = [  0 , 88 ]	BeginService =   7.83	CurrentTime = 9  	Route = * 2 , 57 , 15 , 41 , 75 , 56 ,  4 , 80 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 57 	TimeWindow = [  0 , 85 ]	BeginService =  14.71	CurrentTime = 16 	Route = * 2*,*57 , 15 , 41 , 75 , 56 ,  4 , 80 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 15 	TimeWindow = [ 27 , 31 ]	BeginService =  26.52	CurrentTime = 28 	Route = * 2*,*57*,*15 , 87 , 97 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 41 	TimeWindow = [ 42 , 47 ]	BeginService =  42.17	CurrentTime = 43 	Route = * 2*,*57*,*15*,*41 , 74 , 72 , 21 , 58 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 75 	TimeWindow = [  0 , 83 ]	BeginService =  50.03	CurrentTime = 51 	Route = * 2*,*57*,*15*,*41*,*75 , 56 ,  4 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 56 	TimeWindow = [ 57 , 61 ]	BeginService =  56.52	CurrentTime = 58 	Route = * 2*,*57*,*15*,*41*,*75*,*56 ,  4 ");
        assertThat(salesmenRoutes.get("Salesman-8").get(lineCount++)).isEqualTo("Salesman = 8 	Parcel = 4  	TimeWindow = [ 65 , 69 ]	BeginService =  64.78	CurrentTime = 66 	Route = * 2*,*57*,*15*,*41*,*75*,*56*,* 4 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 73 	TimeWindow = [  0 , 87 ]	BeginService =   8.75	CurrentTime = 10 	Route = *73 , 22 , 74 , 72 , 21 , 58 ");
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 22 	TimeWindow = [ 42 , 47 ]	BeginService =  42.17	CurrentTime = 43 	Route = *73*,*22 , 75 , 56 ,  4 ");
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 74 	TimeWindow = [ 65 , 69 ]	BeginService =  64.78	CurrentTime = 66 	Route = *73*,*22*,*74 , 72 , 21 , 58 ");
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 72 	TimeWindow = [  0 , 86 ]	BeginService =  70.51	CurrentTime = 72 	Route = *73*,*22*,*74*,*72 , 21 , 58 ");
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 21 	TimeWindow = [  0 , 87 ]	BeginService =  76.80	CurrentTime = 78 	Route = *73*,*22*,*74*,*72*,*21 , 58 ");
        assertThat(salesmenRoutes.get("Salesman-9").get(lineCount++)).isEqualTo("Salesman = 9 	Parcel = 58 	TimeWindow = [ 87 , 91 ]	BeginService =  86.96	CurrentTime = 88 	Route = *73*,*22*,*74*,*72*,*21*,*58 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 92 	TimeWindow = [  8 , 12 ]	BeginService =   7.99	CurrentTime = 9  	Route = *92 , 91 , 14 , 44 , 87 , 97 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 91 	TimeWindow = [  0 , 84 ]	BeginService =  15.65	CurrentTime = 17 	Route = *92*,*91 , 14 , 44 , 87 , 97 , 13 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 14 	TimeWindow = [  0 , 81 ]	BeginService =  23.91	CurrentTime = 25 	Route = *92*,*91*,*14 , 44 , 38 , 86 , 16 , 61 , 85 , 98 , 37 , 97 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 44 	TimeWindow = [ 30 , 34 ]	BeginService =  30.72	CurrentTime = 32 	Route = *92*,*91*,*14*,*44 , 38 , 86 , 16 , 61 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 38 	TimeWindow = [ 36 , 40 ]	BeginService =  39.77	CurrentTime = 41 	Route = *92*,*91*,*14*,*44*,*38 , 86 , 16 , 17 , 60 , 89 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 86 	TimeWindow = [  0 , 80 ]	BeginService =  49.79	CurrentTime = 51 	Route = *92*,*91*,*14*,*44*,*38*,*86 , 16 , 61 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 16 	TimeWindow = [  0 , 83 ]	BeginService =  56.89	CurrentTime = 58 	Route = *92*,*91*,*14*,*44*,*38*,*86*,*16 , 61 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 61 	TimeWindow = [  0 , 84 ]	BeginService =  63.18	CurrentTime = 64 	Route = *92*,*91*,*14*,*44*,*38*,*86*,*16*,*61 , 85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 85 	TimeWindow = [  0 , 85 ]	BeginService =  69.47	CurrentTime = 70 	Route = *92*,*91*,*14*,*44*,*38*,*86*,*16*,*61*,*85 , 93 ");
        assertThat(salesmenRoutes.get("Salesman-10").get(lineCount++)).isEqualTo("Salesman = 10	Parcel = 93 	TimeWindow = [ 82 , 86 ]	BeginService =  81.74	CurrentTime = 83 	Route = *92*,*91*,*14*,*44*,*38*,*86*,*16*,*61*,*85*,*93 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 36 	TimeWindow = [ 18 , 22 ]	BeginService =  18.00	CurrentTime = 19 	Route = *36 , 64 , 49 , 19 ,  7 , 52 ");
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 64 	TimeWindow = [ 32 , 36 ]	BeginService =  31.74	CurrentTime = 33 	Route = *36*,*64 , 49 , 19 ,  7 , 52 ");
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 49 	TimeWindow = [ 47 , 51 ]	BeginService =  46.96	CurrentTime = 48 	Route = *36*,*64*,*49 , 19 ,  7 , 52 ");
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 19 	TimeWindow = [  0 , 81 ]	BeginService =  56.54	CurrentTime = 58 	Route = *36*,*64*,*49*,*19 ,  7 , 52 ");
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 7  	TimeWindow = [  0 , 86 ]	BeginService =  65.75	CurrentTime = 67 	Route = *36*,*64*,*49*,*19*,* 7 , 52 ");
        assertThat(salesmenRoutes.get("Salesman-11").get(lineCount++)).isEqualTo("Salesman = 11	Parcel = 52 	TimeWindow = [  0 , 90 ]	BeginService =  74.40	CurrentTime = 75 	Route = *36*,*64*,*49*,*19*,* 7*,*52 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 26 	TimeWindow = [  0 , 90 ]	BeginService =   4.86	CurrentTime = 6  	Route = *26 , 39 , 23 , 67 , 55 , 25 , 54 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 39 	TimeWindow = [ 19 , 23 ]	BeginService =  19.40	CurrentTime = 20 	Route = *26*,*39 , 23 , 67 , 55 , 25 , 54 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 23 	TimeWindow = [ 30 , 34 ]	BeginService =  29.57	CurrentTime = 31 	Route = *26*,*39*,*23 , 67 , 55 , 25 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 67 	TimeWindow = [ 36 , 40 ]	BeginService =  39.13	CurrentTime = 40 	Route = *26*,*39*,*23*,*67 , 55 , 25 , 54 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 55 	TimeWindow = [ 59 , 63 ]	BeginService =  59.13	CurrentTime = 60 	Route = *26*,*39*,*23*,*67*,*55 , 25 , 54 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 25 	TimeWindow = [ 75 , 79 ]	BeginService =  74.78	CurrentTime = 76 	Route = *26*,*39*,*23*,*67*,*55*,*25 , 54 ");
        assertThat(salesmenRoutes.get("Salesman-12").get(lineCount++)).isEqualTo("Salesman = 12	Parcel = 54 	TimeWindow = [  0 , 86 ]	BeginService =  84.37	CurrentTime = 85 	Route = *26*,*39*,*23*,*67*,*55*,*25*,*54 ");
        lineCount = 0;
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 71 	TimeWindow = [  0 , 78 ]	BeginService =  17.24	CurrentTime = 18 	Route = *71 , 65 , 78 , 34 , 35 , 77 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 65 	TimeWindow = [ 22 , 27 ]	BeginService =  26.07	CurrentTime = 27 	Route = *71*,*65 , 78 , 34 , 35 , 81 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 78 	TimeWindow = [ 42 , 46 ]	BeginService =  41.74	CurrentTime = 43 	Route = *71*,*65*,*78 , 34 , 35 , 81 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 34 	TimeWindow = [  0 , 80 ]	BeginService =  48.26	CurrentTime = 49 	Route = *71*,*65*,*78*,*34 , 35 , 81 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 35 	TimeWindow = [ 62 , 67 ]	BeginService =  62.17	CurrentTime = 63 	Route = *71*,*65*,*78*,*34*,*35 , 81 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 81 	TimeWindow = [  0 , 83 ]	BeginService =  72.44	CurrentTime = 73 	Route = *71*,*65*,*78*,*34*,*35*,*81 , 77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 77 	TimeWindow = [ 78 , 82 ]	BeginService =  81.64	CurrentTime = 83 	Route = *71*,*65*,*78*,*34*,*35*,*81*,*77 , 28 ");
        assertThat(salesmenRoutes.get("Salesman-13").get(lineCount++)).isEqualTo("Salesman = 13	Parcel = 28 	TimeWindow = [  0 , 93 ]	BeginService =  91.83	CurrentTime = 93 	Route = *71*,*65*,*78*,*34*,*35*,*81*,*77*,*28 ");

        assertThat(statisticsDTO.totalDistance).isEqualTo(4989.422621257718);
        assertThat(statisticsDTO.totalTravelTime).isEqualTo(0.0);// Light speed
        assertThat(statisticsDTO.totalPickups).isEqualTo(100);
        assertThat(statisticsDTO.totalDeliveries).isEqualTo(100);
        assertThat(statisticsDTO.totalParcels).isEqualTo(100);
        assertThat(statisticsDTO.acceptedParcels).isEqualTo(100);
        assertThat(statisticsDTO.pickupTardiness).isEqualTo(0);
        assertThat(statisticsDTO.deliveryTardiness).isEqualTo(45); // The simulater is not synchronized with the algorithm, for while we are ignoring this
        assertThat(statisticsDTO.simulationTime).isEqualTo(97);
        assertThat(statisticsDTO.vehiclesAtDepot).isEqualTo(25);
        assertThat(statisticsDTO.totalVehicles).isEqualTo(25);
        assertThat(statisticsDTO.movedVehicles).isEqualTo(14);
    }

}
