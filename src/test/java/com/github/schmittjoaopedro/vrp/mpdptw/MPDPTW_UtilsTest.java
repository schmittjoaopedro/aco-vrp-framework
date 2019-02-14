package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_UtilsTest {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        assertThat(problemInstance.noNodes).isEqualTo(26);
        assertThat(problemInstance.noReq).isEqualTo(8);
        assertThat(problemInstance.noMaxVehicles).isEqualTo(15);
        assertThat(problemInstance.vehicleCapacity).isEqualTo(200);
        // Depot
        assertThat(problemInstance.depot.nodeId).isEqualTo(0);
        assertThat(problemInstance.depot.x).isEqualTo(250);
        assertThat(problemInstance.depot.y).isEqualTo(250);
        assertThat(problemInstance.depot.twStart).isEqualTo(0);
        assertThat(problemInstance.depot.twEnd).isEqualTo(1925);

        // Request 1
        assertThat(problemInstance.pickups.get(0).get(0).nodeId).isEqualTo(1);
        assertThat(problemInstance.pickups.get(0).get(0).requestId).isEqualTo(0);
        assertThat(problemInstance.pickups.get(0).get(0).x).isEqualTo(396);
        assertThat(problemInstance.pickups.get(0).get(0).y).isEqualTo(417);
        assertThat(problemInstance.pickups.get(0).get(0).demand).isEqualTo(13);
        assertThat(problemInstance.pickups.get(0).get(0).twStart).isEqualTo(520);
        assertThat(problemInstance.pickups.get(0).get(0).twEnd).isEqualTo(1130);
        assertThat(problemInstance.pickups.get(0).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(0).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(0).get(0).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(0).nodeId).isEqualTo(2);
        assertThat(problemInstance.delivery.get(0).requestId).isEqualTo(0);
        assertThat(problemInstance.delivery.get(0).x).isEqualTo(51);
        assertThat(problemInstance.delivery.get(0).y).isEqualTo(286);
        assertThat(problemInstance.delivery.get(0).demand).isEqualTo(-13);
        assertThat(problemInstance.delivery.get(0).twStart).isEqualTo(1169);
        assertThat(problemInstance.delivery.get(0).twEnd).isEqualTo(1779);
        assertThat(problemInstance.delivery.get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(0).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(0).isDeliver).isTrue();

        // Request 2
        assertThat(problemInstance.pickups.get(1).get(0).nodeId).isEqualTo(3);
        assertThat(problemInstance.pickups.get(1).get(0).requestId).isEqualTo(1);
        assertThat(problemInstance.pickups.get(1).get(0).x).isEqualTo(288);
        assertThat(problemInstance.pickups.get(1).get(0).y).isEqualTo(439);
        assertThat(problemInstance.pickups.get(1).get(0).demand).isEqualTo(16);
        assertThat(problemInstance.pickups.get(1).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(1).get(0).twEnd).isEqualTo(593);
        assertThat(problemInstance.pickups.get(1).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(1).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(1).get(0).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(1).get(1).nodeId).isEqualTo(4);
        assertThat(problemInstance.pickups.get(1).get(1).requestId).isEqualTo(1);
        assertThat(problemInstance.pickups.get(1).get(1).x).isEqualTo(399);
        assertThat(problemInstance.pickups.get(1).get(1).y).isEqualTo(175);
        assertThat(problemInstance.pickups.get(1).get(1).demand).isEqualTo(13);
        assertThat(problemInstance.pickups.get(1).get(1).twStart).isEqualTo(457);
        assertThat(problemInstance.pickups.get(1).get(1).twEnd).isEqualTo(1067);
        assertThat(problemInstance.pickups.get(1).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(1).get(1).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(1).get(1).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(1).get(2).nodeId).isEqualTo(5);
        assertThat(problemInstance.pickups.get(1).get(2).requestId).isEqualTo(1);
        assertThat(problemInstance.pickups.get(1).get(2).x).isEqualTo(247);
        assertThat(problemInstance.pickups.get(1).get(2).y).isEqualTo(306);
        assertThat(problemInstance.pickups.get(1).get(2).demand).isEqualTo(33);
        assertThat(problemInstance.pickups.get(1).get(2).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(1).get(2).twEnd).isEqualTo(366);
        assertThat(problemInstance.pickups.get(1).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(1).get(2).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(1).get(2).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(1).nodeId).isEqualTo(6);
        assertThat(problemInstance.delivery.get(1).requestId).isEqualTo(1);
        assertThat(problemInstance.delivery.get(1).x).isEqualTo(274);
        assertThat(problemInstance.delivery.get(1).y).isEqualTo(261);
        assertThat(problemInstance.delivery.get(1).demand).isEqualTo(-62);
        assertThat(problemInstance.delivery.get(1).twStart).isEqualTo(1417);
        assertThat(problemInstance.delivery.get(1).twEnd).isEqualTo(1925);
        assertThat(problemInstance.delivery.get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(1).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(1).isDeliver).isTrue();

        // Request 3
        assertThat(problemInstance.pickups.get(2).get(0).nodeId).isEqualTo(7);
        assertThat(problemInstance.pickups.get(2).get(0).requestId).isEqualTo(2);
        assertThat(problemInstance.pickups.get(2).get(0).x).isEqualTo(176);
        assertThat(problemInstance.pickups.get(2).get(0).y).isEqualTo(199);
        assertThat(problemInstance.pickups.get(2).get(0).demand).isEqualTo(28);
        assertThat(problemInstance.pickups.get(2).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(2).get(0).twEnd).isEqualTo(399);
        assertThat(problemInstance.pickups.get(2).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(2).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(2).get(0).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(2).nodeId).isEqualTo(8);
        assertThat(problemInstance.delivery.get(2).requestId).isEqualTo(2);
        assertThat(problemInstance.delivery.get(2).x).isEqualTo(461);
        assertThat(problemInstance.delivery.get(2).y).isEqualTo(182);
        assertThat(problemInstance.delivery.get(2).demand).isEqualTo(-28);
        assertThat(problemInstance.delivery.get(2).twStart).isEqualTo(339);
        assertThat(problemInstance.delivery.get(2).twEnd).isEqualTo(949);
        assertThat(problemInstance.delivery.get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(2).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(2).isDeliver).isTrue();

        // Request 4
        assertThat(problemInstance.pickups.get(3).get(0).nodeId).isEqualTo(9);
        assertThat(problemInstance.pickups.get(3).get(0).requestId).isEqualTo(3);
        assertThat(problemInstance.pickups.get(3).get(0).x).isEqualTo(489);
        assertThat(problemInstance.pickups.get(3).get(0).y).isEqualTo(159);
        assertThat(problemInstance.pickups.get(3).get(0).demand).isEqualTo(29);
        assertThat(problemInstance.pickups.get(3).get(0).twStart).isEqualTo(1138);
        assertThat(problemInstance.pickups.get(3).get(0).twEnd).isEqualTo(1748);
        assertThat(problemInstance.pickups.get(3).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(3).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(3).get(0).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(3).get(1).nodeId).isEqualTo(10);
        assertThat(problemInstance.pickups.get(3).get(1).requestId).isEqualTo(3);
        assertThat(problemInstance.pickups.get(3).get(1).x).isEqualTo(323);
        assertThat(problemInstance.pickups.get(3).get(1).y).isEqualTo(29);
        assertThat(problemInstance.pickups.get(3).get(1).demand).isEqualTo(7);
        assertThat(problemInstance.pickups.get(3).get(1).twStart).isEqualTo(847);
        assertThat(problemInstance.pickups.get(3).get(1).twEnd).isEqualTo(1457);
        assertThat(problemInstance.pickups.get(3).get(1).serviceTime).isEqualTo(0);
        assertThat(problemInstance.pickups.get(3).get(1).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(3).get(1).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(3).get(2).nodeId).isEqualTo(11);
        assertThat(problemInstance.pickups.get(3).get(2).requestId).isEqualTo(3);
        assertThat(problemInstance.pickups.get(3).get(2).x).isEqualTo(385);
        assertThat(problemInstance.pickups.get(3).get(2).y).isEqualTo(58);
        assertThat(problemInstance.pickups.get(3).get(2).demand).isEqualTo(24);
        assertThat(problemInstance.pickups.get(3).get(2).twStart).isEqualTo(115);
        assertThat(problemInstance.pickups.get(3).get(2).twEnd).isEqualTo(725);
        assertThat(problemInstance.pickups.get(3).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(3).get(2).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(3).get(2).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(3).nodeId).isEqualTo(12);
        assertThat(problemInstance.delivery.get(3).requestId).isEqualTo(3);
        assertThat(problemInstance.delivery.get(3).x).isEqualTo(226);
        assertThat(problemInstance.delivery.get(3).y).isEqualTo(175);
        assertThat(problemInstance.delivery.get(3).demand).isEqualTo(-60);
        assertThat(problemInstance.delivery.get(3).twStart).isEqualTo(1404);
        assertThat(problemInstance.delivery.get(3).twEnd).isEqualTo(1925);
        assertThat(problemInstance.delivery.get(3).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(3).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(3).isDeliver).isTrue();

        // Request 5
        assertThat(problemInstance.pickups.get(4).get(0).nodeId).isEqualTo(13);
        assertThat(problemInstance.pickups.get(4).get(0).requestId).isEqualTo(4);
        assertThat(problemInstance.pickups.get(4).get(0).x).isEqualTo(471);
        assertThat(problemInstance.pickups.get(4).get(0).y).isEqualTo(368);
        assertThat(problemInstance.pickups.get(4).get(0).demand).isEqualTo(19);
        assertThat(problemInstance.pickups.get(4).get(0).twStart).isEqualTo(138);
        assertThat(problemInstance.pickups.get(4).get(0).twEnd).isEqualTo(748);
        assertThat(problemInstance.pickups.get(4).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(4).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(4).get(0).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(4).nodeId).isEqualTo(14);
        assertThat(problemInstance.delivery.get(4).requestId).isEqualTo(4);
        assertThat(problemInstance.delivery.get(4).x).isEqualTo(490);
        assertThat(problemInstance.delivery.get(4).y).isEqualTo(286);
        assertThat(problemInstance.delivery.get(4).demand).isEqualTo(-19);
        assertThat(problemInstance.delivery.get(4).twStart).isEqualTo(232);
        assertThat(problemInstance.delivery.get(4).twEnd).isEqualTo(842);
        assertThat(problemInstance.delivery.get(4).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(4).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(4).isDeliver).isTrue();

        // Request 6
        assertThat(problemInstance.pickups.get(5).get(0).nodeId).isEqualTo(15);
        assertThat(problemInstance.pickups.get(5).get(0).requestId).isEqualTo(5);
        assertThat(problemInstance.pickups.get(5).get(0).x).isEqualTo(253);
        assertThat(problemInstance.pickups.get(5).get(0).y).isEqualTo(50);
        assertThat(problemInstance.pickups.get(5).get(0).demand).isEqualTo(27);
        assertThat(problemInstance.pickups.get(5).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(5).get(0).twEnd).isEqualTo(546);
        assertThat(problemInstance.pickups.get(5).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(5).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(5).get(0).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(5).get(1).nodeId).isEqualTo(16);
        assertThat(problemInstance.pickups.get(5).get(1).requestId).isEqualTo(5);
        assertThat(problemInstance.pickups.get(5).get(1).x).isEqualTo(0);
        assertThat(problemInstance.pickups.get(5).get(1).y).isEqualTo(356);
        assertThat(problemInstance.pickups.get(5).get(1).demand).isEqualTo(13);
        assertThat(problemInstance.pickups.get(5).get(1).twStart).isEqualTo(796);
        assertThat(problemInstance.pickups.get(5).get(1).twEnd).isEqualTo(1406);
        assertThat(problemInstance.pickups.get(5).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(5).get(1).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(5).get(1).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(5).nodeId).isEqualTo(17);
        assertThat(problemInstance.delivery.get(5).requestId).isEqualTo(5);
        assertThat(problemInstance.delivery.get(5).x).isEqualTo(209);
        assertThat(problemInstance.delivery.get(5).y).isEqualTo(212);
        assertThat(problemInstance.delivery.get(5).demand).isEqualTo(-40);
        assertThat(problemInstance.delivery.get(5).twStart).isEqualTo(1238);
        assertThat(problemInstance.delivery.get(5).twEnd).isEqualTo(1848);
        assertThat(problemInstance.delivery.get(5).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(5).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(5).isDeliver).isTrue();

        // Request 7
        assertThat(problemInstance.pickups.get(6).get(0).nodeId).isEqualTo(18);
        assertThat(problemInstance.pickups.get(6).get(0).requestId).isEqualTo(6);
        assertThat(problemInstance.pickups.get(6).get(0).x).isEqualTo(144);
        assertThat(problemInstance.pickups.get(6).get(0).y).isEqualTo(35);
        assertThat(problemInstance.pickups.get(6).get(0).demand).isEqualTo(26);
        assertThat(problemInstance.pickups.get(6).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(6).get(0).twEnd).isEqualTo(549);
        assertThat(problemInstance.pickups.get(6).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(6).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(6).get(0).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(6).get(1).nodeId).isEqualTo(19);
        assertThat(problemInstance.pickups.get(6).get(1).requestId).isEqualTo(6);
        assertThat(problemInstance.pickups.get(6).get(1).x).isEqualTo(154);
        assertThat(problemInstance.pickups.get(6).get(1).y).isEqualTo(374);
        assertThat(problemInstance.pickups.get(6).get(1).demand).isEqualTo(36);
        assertThat(problemInstance.pickups.get(6).get(1).twStart).isEqualTo(647);
        assertThat(problemInstance.pickups.get(6).get(1).twEnd).isEqualTo(1257);
        assertThat(problemInstance.pickups.get(6).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(6).get(1).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(6).get(1).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(6).get(2).nodeId).isEqualTo(20);
        assertThat(problemInstance.pickups.get(6).get(2).requestId).isEqualTo(6);
        assertThat(problemInstance.pickups.get(6).get(2).x).isEqualTo(452);
        assertThat(problemInstance.pickups.get(6).get(2).y).isEqualTo(484);
        assertThat(problemInstance.pickups.get(6).get(2).demand).isEqualTo(26);
        assertThat(problemInstance.pickups.get(6).get(2).twStart).isEqualTo(988);
        assertThat(problemInstance.pickups.get(6).get(2).twEnd).isEqualTo(1598);
        assertThat(problemInstance.pickups.get(6).get(2).serviceTime).isEqualTo(0);
        assertThat(problemInstance.pickups.get(6).get(2).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(6).get(2).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(6).nodeId).isEqualTo(21);
        assertThat(problemInstance.delivery.get(6).requestId).isEqualTo(6);
        assertThat(problemInstance.delivery.get(6).x).isEqualTo(485);
        assertThat(problemInstance.delivery.get(6).y).isEqualTo(428);
        assertThat(problemInstance.delivery.get(6).demand).isEqualTo(-88);
        assertThat(problemInstance.delivery.get(6).twStart).isEqualTo(1200);
        assertThat(problemInstance.delivery.get(6).twEnd).isEqualTo(1810);
        assertThat(problemInstance.delivery.get(6).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(6).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(6).isDeliver).isTrue();

        // Request 8
        assertThat(problemInstance.pickups.get(7).get(0).nodeId).isEqualTo(22);
        assertThat(problemInstance.pickups.get(7).get(0).requestId).isEqualTo(7);
        assertThat(problemInstance.pickups.get(7).get(0).x).isEqualTo(441);
        assertThat(problemInstance.pickups.get(7).get(0).y).isEqualTo(7);
        assertThat(problemInstance.pickups.get(7).get(0).demand).isEqualTo(20);
        assertThat(problemInstance.pickups.get(7).get(0).twStart).isEqualTo(102);
        assertThat(problemInstance.pickups.get(7).get(0).twEnd).isEqualTo(712);
        assertThat(problemInstance.pickups.get(7).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(7).get(0).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(7).get(0).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(7).get(1).nodeId).isEqualTo(23);
        assertThat(problemInstance.pickups.get(7).get(1).requestId).isEqualTo(7);
        assertThat(problemInstance.pickups.get(7).get(1).x).isEqualTo(235);
        assertThat(problemInstance.pickups.get(7).get(1).y).isEqualTo(288);
        assertThat(problemInstance.pickups.get(7).get(1).demand).isEqualTo(18);
        assertThat(problemInstance.pickups.get(7).get(1).twStart).isEqualTo(0);
        assertThat(problemInstance.pickups.get(7).get(1).twEnd).isEqualTo(350);
        assertThat(problemInstance.pickups.get(7).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(7).get(1).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(7).get(1).isDeliver).isFalse();

        assertThat(problemInstance.pickups.get(7).get(2).nodeId).isEqualTo(24);
        assertThat(problemInstance.pickups.get(7).get(2).requestId).isEqualTo(7);
        assertThat(problemInstance.pickups.get(7).get(2).x).isEqualTo(392);
        assertThat(problemInstance.pickups.get(7).get(2).y).isEqualTo(1);
        assertThat(problemInstance.pickups.get(7).get(2).demand).isEqualTo(11);
        assertThat(problemInstance.pickups.get(7).get(2).twStart).isEqualTo(315);
        assertThat(problemInstance.pickups.get(7).get(2).twEnd).isEqualTo(925);
        assertThat(problemInstance.pickups.get(7).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickups.get(7).get(2).isPickup).isTrue();
        assertThat(problemInstance.pickups.get(7).get(2).isDeliver).isFalse();

        assertThat(problemInstance.delivery.get(7).nodeId).isEqualTo(25);
        assertThat(problemInstance.delivery.get(7).requestId).isEqualTo(7);
        assertThat(problemInstance.delivery.get(7).x).isEqualTo(60);
        assertThat(problemInstance.delivery.get(7).y).isEqualTo(5);
        assertThat(problemInstance.delivery.get(7).demand).isEqualTo(-49);
        assertThat(problemInstance.delivery.get(7).twStart).isEqualTo(754);
        assertThat(problemInstance.delivery.get(7).twEnd).isEqualTo(1364);
        assertThat(problemInstance.delivery.get(7).serviceTime).isEqualTo(10);
        assertThat(problemInstance.delivery.get(7).isPickup).isFalse();
        assertThat(problemInstance.delivery.get(7).isDeliver).isTrue();

        for (int i = 0; i < problemInstance.distances.length; i++) {
            for (int j = 0; j < problemInstance.distances[i].length; j++) {
                if (i == j) {
                    assertThat(problemInstance.distances[i][j]).isEqualTo(0.0);
                } else {
                    assertThat(problemInstance.distances[i][j]).isGreaterThan(1.0);
                    assertThat(problemInstance.distances[i][j]).isLessThan(1925.0);
                }
            }
        }
    }

    @Test
    public void requestPathTreeTest() throws IOException {
        // Biggest problem
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "w_8_100_1.txt").toFile());
        OptimalRequestSolver optimalRequestSolver[] = new OptimalRequestSolver[problemInstance.noReq];
        for (int i = 0; i < problemInstance.noReq; i++) {
            optimalRequestSolver[i] = new OptimalRequestSolver(i, problemInstance);
            optimalRequestSolver[i].optimize();
        }
        // Request 0
        assertThat(optimalRequestSolver[0].getBestRoute()).containsExactly(0, 1, 2, 0);
        assertThat(optimalRequestSolver[0].getBestCost()).isEqualTo(813.0);
        // Request 1
        assertThat(optimalRequestSolver[1].getBestRoute()).containsExactly(0, 4, 9, 5, 7, 8, 6, 3, 10, 0);
        assertThat(optimalRequestSolver[1].getBestCost()).isEqualTo(1150.0);
        // Request 2
        assertThat(optimalRequestSolver[2].getBestRoute()).containsExactly(0, 11, 12, 0);
        assertThat(optimalRequestSolver[2].getBestCost()).isEqualTo(533.0);
        // Request 3
        assertThat(optimalRequestSolver[3].getBestRoute()).containsExactly(0, 15, 13, 14, 16, 0);
        assertThat(optimalRequestSolver[3].getBestCost()).isEqualTo(1062.0);
        // Request 4
        assertThat(optimalRequestSolver[4].getBestRoute()).containsExactly(0, 21, 22, 20, 17, 18, 19, 23, 0);
        assertThat(optimalRequestSolver[4].getBestCost()).isEqualTo(876.0);
        // Request 5
        assertThat(optimalRequestSolver[5].getBestRoute()).containsExactly(0, 24, 26, 25, 27, 0);
        assertThat(optimalRequestSolver[5].getBestCost()).isEqualTo(722.0);
        // Request 6
        assertThat(optimalRequestSolver[6].getBestRoute()).containsExactly(0, 29, 33, 31, 32, 28, 30, 34, 0);
        assertThat(optimalRequestSolver[6].getBestCost()).isEqualTo(1236.0);
        // Request 7
        assertThat(optimalRequestSolver[7].getBestRoute()).containsExactly(0, 36, 37, 35, 39, 38, 40, 41, 0);
        assertThat(optimalRequestSolver[7].getBestCost()).isEqualTo(1003.0);
        // Request 8
        assertThat(optimalRequestSolver[8].getBestRoute()).containsExactly(0, 42, 45, 46, 47, 44, 43, 48, 0);
        assertThat(optimalRequestSolver[8].getBestCost()).isEqualTo(821.0);
        // Request 9
        assertThat(optimalRequestSolver[9].getBestRoute()).containsExactly(0, 49, 50, 0);
        assertThat(optimalRequestSolver[9].getBestCost()).isEqualTo(583.0);
        // Request 10
        assertThat(optimalRequestSolver[10].getBestRoute()).containsExactly(0, 53, 54, 51, 52, 55, 0);
        assertThat(optimalRequestSolver[10].getBestCost()).isEqualTo(1169.0);
        // Request 11
        assertThat(optimalRequestSolver[11].getBestRoute()).containsExactly(0, 59, 58, 56, 57, 60, 0);
        assertThat(optimalRequestSolver[11].getBestCost()).isEqualTo(1276.0);
        // Request 12
        assertThat(optimalRequestSolver[12].getBestRoute()).containsExactly(0, 61, 64, 63, 62, 65, 0);
        assertThat(optimalRequestSolver[12].getBestCost()).isEqualTo(1209.0);
        // Request 13
        assertThat(optimalRequestSolver[13].getBestRoute()).containsExactly(0, 69, 68, 66, 67, 70, 0);
        assertThat(optimalRequestSolver[13].getBestCost()).isEqualTo(708.0);
        // Request 14
        assertThat(optimalRequestSolver[14].getBestRoute()).containsExactly(0, 73, 72, 74, 71, 75, 0);
        assertThat(optimalRequestSolver[14].getBestCost()).isEqualTo(937.0);
        // Request 15
        assertThat(optimalRequestSolver[15].getBestRoute()).containsExactly(0, 80, 76, 79, 81, 77, 78, 82, 83, 0);
        assertThat(optimalRequestSolver[15].getBestCost()).isEqualTo(1359.0);
        // Request 16
        assertThat(optimalRequestSolver[16].getBestRoute()).containsExactly(0, 84, 85, 86, 0);
        assertThat(optimalRequestSolver[16].getBestCost()).isEqualTo(830.0);
        // Request 17
        assertThat(optimalRequestSolver[17].getBestRoute()).containsExactly(0, 87, 88, 0);
        assertThat(optimalRequestSolver[17].getBestCost()).isEqualTo(455.0);
        // Request 18
        assertThat(optimalRequestSolver[18].getBestRoute()).containsExactly(0, 89, 90, 0);
        assertThat(optimalRequestSolver[18].getBestCost()).isEqualTo(743.0);
        // Request 19
        assertThat(optimalRequestSolver[19].getBestRoute()).containsExactly(0, 94, 93, 91, 92, 95, 0);
        assertThat(optimalRequestSolver[19].getBestCost()).isEqualTo(1170.0);
        // Request 20
        assertThat(optimalRequestSolver[20].getBestRoute()).containsExactly(0, 96, 97, 0);
        assertThat(optimalRequestSolver[20].getBestCost()).isEqualTo(661.0);
        // Request 21
        assertThat(optimalRequestSolver[21].getBestRoute()).containsExactly(0, 98, 101, 99, 104, 100, 103, 102, 105, 0);
        assertThat(optimalRequestSolver[21].getBestCost()).isEqualTo(1037.0);
    }

    @Test
    public void relocateSimpleLocalSearchTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        Ant ant = AntUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 24, 22, 23, 3, 4, 6, 25, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 7, 15, 8, 13, 14, 1, 2, 16, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 10, 11, 9, 12, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 18, 19, 20, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(2, 5, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6)));

        problemInstance.fitnessEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(6909.0);
        assertThat(ant.timeWindowPenalty).isEqualTo(2011.0);
        assertThat(ant.feasible).isFalse();

        LocalSearch localSearch = new LocalSearch(problemInstance);
        Ant improvedAnt = localSearch.relocate(ant);
        assertThat(improvedAnt.totalCost).isEqualTo(6495.0);
        assertThat(improvedAnt.timeWindowPenalty).isEqualTo(1956.0);
        assertThat(improvedAnt.feasible).isFalse();
    }

    @Test
    public void relocateLoopLocalSearchTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        Ant ant = AntUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 24, 22, 16, 23, 17, 25, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 7, 8, 4, 3, 1, 6, 2, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 13, 14, 11, 10, 9, 12, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 18, 19, 20, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 2, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6)));

        problemInstance.fitnessEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(6786.0);
        assertThat(ant.timeWindowPenalty).isEqualTo(1422.0);
        assertThat(ant.feasible).isFalse();

        LocalSearch localSearch = new LocalSearch(problemInstance);
        Ant improvedAnt = localSearch.relocate(ant);
        assertThat(improvedAnt.totalCost).isEqualTo(6086.0);
        assertThat(improvedAnt.timeWindowPenalty).isEqualTo(0.0);
        assertThat(improvedAnt.feasible).isTrue();
    }

    @Test
    public void feasibilityLocalSearch() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Ant ant = AntUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 18, 16, 19, 20, 21, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 7, 11, 22, 24, 25, 10, 9, 8, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7, 2, 3)));
        problemInstance.fitnessEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(5305.0);
        assertThat(ant.timeWindowPenalty).isEqualTo(844.0);
        assertThat(ant.feasible).isFalse();

        FeasibilitySearch feasibilitySearch = new FeasibilitySearch(problemInstance);
        Ant improvedAnt = feasibilitySearch.feasibility(ant);
        assertThat(improvedAnt.feasible).isTrue();
        assertThat(improvedAnt.totalCost).isEqualTo(8307.0);
        assertThat(improvedAnt.timeWindowPenalty).isEqualTo(0.0);
    }
}
