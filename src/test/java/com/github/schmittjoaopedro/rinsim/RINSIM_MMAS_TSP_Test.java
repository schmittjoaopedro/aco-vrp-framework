package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.pdptw.common.StatisticsDTO;
import com.github.schmittjoaopedro.rinsim.tsp.RINSIM_MMAS_TSP;
import com.github.schmittjoaopedro.tsp.TestUtils;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

public class RINSIM_MMAS_TSP_Test {

    private String kroA100;

    private String kroA200;

    private boolean showGui = false;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/KroA200.tsp").getFile();
    }

    @Test
    public void test_MMAS_tsp_kroA100_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        RINSIM_MMAS_TSP rinsimMmasTsp = new RINSIM_MMAS_TSP(graph, mmas_tsp, showGui);
        rinsimMmasTsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFar()).isEqualTo(21644);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[86, 50, 60, 57, 66, 27, 92, 46, 0, 62, 5, 48, 89, 9, 83, 78, 52, 18, 74, 91, 7, 41, 88, 30, 79, 55, 96, 3, 64, 25, 65, 69, 21, 15, 87, 93, 17, 23, 37, 98, 35, 71, 20, 73, 58, 16, 14, 10, 31, 44, 90, 97, 22, 76, 59, 61, 34, 85, 26, 11, 19, 56, 8, 6, 54, 82, 33, 28, 45, 42, 2, 13, 70, 40, 99, 47, 29, 38, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 1, 53, 39, 63, 43, 49, 84, 67, 72, 68, 80, 24, 86]");
        StatisticsDTO statisticsDTO = rinsimMmasTsp.getStatistics();
        assertThat(statisticsDTO.timeUnit.toString()).isEqualTo("ms");
        assertThat(statisticsDTO.distanceUnit.toString()).isEqualTo("m");
        assertThat(statisticsDTO.speedUnit.toString()).isEqualTo("km/h");
        assertThat(statisticsDTO.totalDistance).isEqualTo(21648.0, offset(0.3));
        assertThat(statisticsDTO.totalTravelTime).isEqualTo(2597792.0);
        assertThat(statisticsDTO.totalPickups).isEqualTo(99);
        assertThat(statisticsDTO.totalDeliveries).isEqualTo(99);
        assertThat(statisticsDTO.totalParcels).isEqualTo(99);
        assertThat(statisticsDTO.acceptedParcels).isEqualTo(99);
        assertThat(statisticsDTO.pickupTardiness).isEqualTo(0);
        assertThat(statisticsDTO.deliveryTardiness).isEqualTo(0);
        assertThat(statisticsDTO.vehiclesAtDepot).isEqualTo(1);
        assertThat(statisticsDTO.totalVehicles).isEqualTo(1);
        assertThat(statisticsDTO.movedVehicles).isEqualTo(1);
        assertThat(statisticsDTO.simFinish).isTrue();
    }

    @Test
    public void test_MMAS_tsp_kroA200_with_seed_1() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        MMAS_TSP mmas_tsp = new MMAS_TSP(graph, 0.02, 1500, 1);
        mmas_tsp.setStatisticInterval(100);
        RINSIM_MMAS_TSP rinsimMmasTsp = new RINSIM_MMAS_TSP(graph, mmas_tsp, showGui);
        rinsimMmasTsp.run();
        assertThat(mmas_tsp.getGlobalStatistics().getBestSoFar()).isEqualTo(30074);
        assertThat(TestUtils.getTourString(mmas_tsp.getGlobalStatistics())).isEqualTo("[15, 117, 178, 65, 152, 118, 91, 9, 174, 98, 18, 189, 48, 17, 109, 28, 183, 36, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 110, 116, 114, 52, 0, 131, 84, 144, 14, 122, 197, 26, 190, 39, 146, 11, 92, 148, 105, 162, 3, 100, 59, 192, 127, 166, 157, 76, 79, 64, 30, 66, 176, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 156, 106, 108, 74, 154, 182, 21, 133, 7, 16, 24, 142, 89, 33, 57, 140, 170, 199, 147, 87, 97, 113, 102, 145, 128, 82, 61, 71, 129, 38, 27, 37, 70, 195, 177, 55, 151, 4, 104, 42, 136, 132, 175, 112, 194, 181, 75, 69, 143, 101, 153, 20, 139, 163, 22, 172, 149, 90, 93, 94, 85, 138, 49, 184, 167, 53, 5, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 191, 13, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 103, 164, 165, 95, 125, 86, 51, 10, 83, 47, 169, 121, 115, 187, 43, 62, 193, 50, 15]");
        StatisticsDTO statisticsDTO = rinsimMmasTsp.getStatistics();
        assertThat(statisticsDTO.timeUnit.toString()).isEqualTo("ms");
        assertThat(statisticsDTO.distanceUnit.toString()).isEqualTo("m");
        assertThat(statisticsDTO.speedUnit.toString()).isEqualTo("km/h");
        assertThat(statisticsDTO.totalDistance).isEqualTo(30079, offset(0.2));
        assertThat(statisticsDTO.totalTravelTime).isEqualTo(3609482.0);
        assertThat(statisticsDTO.totalPickups).isEqualTo(199);
        assertThat(statisticsDTO.totalDeliveries).isEqualTo(199);
        assertThat(statisticsDTO.totalParcels).isEqualTo(199);
        assertThat(statisticsDTO.acceptedParcels).isEqualTo(199);
        assertThat(statisticsDTO.pickupTardiness).isEqualTo(0);
        assertThat(statisticsDTO.deliveryTardiness).isEqualTo(0);
        assertThat(statisticsDTO.vehiclesAtDepot).isEqualTo(1);
        assertThat(statisticsDTO.totalVehicles).isEqualTo(1);
        assertThat(statisticsDTO.movedVehicles).isEqualTo(1);
        assertThat(statisticsDTO.simFinish).isTrue();
    }

}
