package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.GreedyInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.RegretInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.insertion.SequentialInsertion;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.ExpensiveNodeRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.ExpensiveRequestRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.RandomRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.operators.removal.ShawRemoval;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.ExchangeRequestOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.InsertionMethod.PickupMethod;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateNodeOperator;
import com.github.schmittjoaopedro.vrp.mpdptw.operators.RelocateRequestOperator;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_UtilsTest {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        assertThat(problemInstance.getNumNodes()).isEqualTo(26);
        assertThat(problemInstance.getNumReq()).isEqualTo(8);
        assertThat(problemInstance.getNumMaxVehicles()).isEqualTo(15);
        assertThat(problemInstance.getVehicleCapacity()).isEqualTo(200);
        // Depot
        assertThat(problemInstance.getDepot().nodeId).isEqualTo(0);
        assertThat(problemInstance.getDepot().x).isEqualTo(250);
        assertThat(problemInstance.getDepot().y).isEqualTo(250);
        assertThat(problemInstance.getDepot().twStart).isEqualTo(0);
        assertThat(problemInstance.getDepot().twEnd).isEqualTo(1925);

        // Request 1
        assertThat(problemInstance.getPickups(0).get(0).nodeId).isEqualTo(1);
        assertThat(problemInstance.getPickups(0).get(0).requestId).isEqualTo(0);
        assertThat(problemInstance.getPickups(0).get(0).x).isEqualTo(396);
        assertThat(problemInstance.getPickups(0).get(0).y).isEqualTo(417);
        assertThat(problemInstance.getPickups(0).get(0).demand).isEqualTo(13);
        assertThat(problemInstance.getPickups(0).get(0).twStart).isEqualTo(520);
        assertThat(problemInstance.getPickups(0).get(0).twEnd).isEqualTo(1130);
        assertThat(problemInstance.getPickups(0).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(0).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(0).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(0).nodeId).isEqualTo(2);
        assertThat(problemInstance.getDelivery(0).requestId).isEqualTo(0);
        assertThat(problemInstance.getDelivery(0).x).isEqualTo(51);
        assertThat(problemInstance.getDelivery(0).y).isEqualTo(286);
        assertThat(problemInstance.getDelivery(0).demand).isEqualTo(-13);
        assertThat(problemInstance.getDelivery(0).twStart).isEqualTo(1169);
        assertThat(problemInstance.getDelivery(0).twEnd).isEqualTo(1779);
        assertThat(problemInstance.getDelivery(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(0).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(0).isDeliver).isTrue();

        // Request 2
        assertThat(problemInstance.getPickups(1).get(0).nodeId).isEqualTo(3);
        assertThat(problemInstance.getPickups(1).get(0).requestId).isEqualTo(1);
        assertThat(problemInstance.getPickups(1).get(0).x).isEqualTo(288);
        assertThat(problemInstance.getPickups(1).get(0).y).isEqualTo(439);
        assertThat(problemInstance.getPickups(1).get(0).demand).isEqualTo(16);
        assertThat(problemInstance.getPickups(1).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(1).get(0).twEnd).isEqualTo(593);
        assertThat(problemInstance.getPickups(1).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(1).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(1).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(1).get(1).nodeId).isEqualTo(4);
        assertThat(problemInstance.getPickups(1).get(1).requestId).isEqualTo(1);
        assertThat(problemInstance.getPickups(1).get(1).x).isEqualTo(399);
        assertThat(problemInstance.getPickups(1).get(1).y).isEqualTo(175);
        assertThat(problemInstance.getPickups(1).get(1).demand).isEqualTo(13);
        assertThat(problemInstance.getPickups(1).get(1).twStart).isEqualTo(457);
        assertThat(problemInstance.getPickups(1).get(1).twEnd).isEqualTo(1067);
        assertThat(problemInstance.getPickups(1).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(1).get(1).isPickup).isTrue();
        assertThat(problemInstance.getPickups(1).get(1).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(1).get(2).nodeId).isEqualTo(5);
        assertThat(problemInstance.getPickups(1).get(2).requestId).isEqualTo(1);
        assertThat(problemInstance.getPickups(1).get(2).x).isEqualTo(247);
        assertThat(problemInstance.getPickups(1).get(2).y).isEqualTo(306);
        assertThat(problemInstance.getPickups(1).get(2).demand).isEqualTo(33);
        assertThat(problemInstance.getPickups(1).get(2).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(1).get(2).twEnd).isEqualTo(366);
        assertThat(problemInstance.getPickups(1).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(1).get(2).isPickup).isTrue();
        assertThat(problemInstance.getPickups(1).get(2).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(1).nodeId).isEqualTo(6);
        assertThat(problemInstance.getDelivery(1).requestId).isEqualTo(1);
        assertThat(problemInstance.getDelivery(1).x).isEqualTo(274);
        assertThat(problemInstance.getDelivery(1).y).isEqualTo(261);
        assertThat(problemInstance.getDelivery(1).demand).isEqualTo(-62);
        assertThat(problemInstance.getDelivery(1).twStart).isEqualTo(1417);
        assertThat(problemInstance.getDelivery(1).twEnd).isEqualTo(1925);
        assertThat(problemInstance.getDelivery(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(1).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(1).isDeliver).isTrue();

        // Request 3
        assertThat(problemInstance.getPickups(2).get(0).nodeId).isEqualTo(7);
        assertThat(problemInstance.getPickups(2).get(0).requestId).isEqualTo(2);
        assertThat(problemInstance.getPickups(2).get(0).x).isEqualTo(176);
        assertThat(problemInstance.getPickups(2).get(0).y).isEqualTo(199);
        assertThat(problemInstance.getPickups(2).get(0).demand).isEqualTo(28);
        assertThat(problemInstance.getPickups(2).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(2).get(0).twEnd).isEqualTo(399);
        assertThat(problemInstance.getPickups(2).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(2).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(2).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(2).nodeId).isEqualTo(8);
        assertThat(problemInstance.getDelivery(2).requestId).isEqualTo(2);
        assertThat(problemInstance.getDelivery(2).x).isEqualTo(461);
        assertThat(problemInstance.getDelivery(2).y).isEqualTo(182);
        assertThat(problemInstance.getDelivery(2).demand).isEqualTo(-28);
        assertThat(problemInstance.getDelivery(2).twStart).isEqualTo(339);
        assertThat(problemInstance.getDelivery(2).twEnd).isEqualTo(949);
        assertThat(problemInstance.getDelivery(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(2).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(2).isDeliver).isTrue();

        // Request 4
        assertThat(problemInstance.getPickups(3).get(0).nodeId).isEqualTo(9);
        assertThat(problemInstance.getPickups(3).get(0).requestId).isEqualTo(3);
        assertThat(problemInstance.getPickups(3).get(0).x).isEqualTo(489);
        assertThat(problemInstance.getPickups(3).get(0).y).isEqualTo(159);
        assertThat(problemInstance.getPickups(3).get(0).demand).isEqualTo(29);
        assertThat(problemInstance.getPickups(3).get(0).twStart).isEqualTo(1138);
        assertThat(problemInstance.getPickups(3).get(0).twEnd).isEqualTo(1748);
        assertThat(problemInstance.getPickups(3).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(3).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(3).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(3).get(1).nodeId).isEqualTo(10);
        assertThat(problemInstance.getPickups(3).get(1).requestId).isEqualTo(3);
        assertThat(problemInstance.getPickups(3).get(1).x).isEqualTo(323);
        assertThat(problemInstance.getPickups(3).get(1).y).isEqualTo(29);
        assertThat(problemInstance.getPickups(3).get(1).demand).isEqualTo(7);
        assertThat(problemInstance.getPickups(3).get(1).twStart).isEqualTo(847);
        assertThat(problemInstance.getPickups(3).get(1).twEnd).isEqualTo(1457);
        assertThat(problemInstance.getPickups(3).get(1).serviceTime).isEqualTo(0);
        assertThat(problemInstance.getPickups(3).get(1).isPickup).isTrue();
        assertThat(problemInstance.getPickups(3).get(1).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(3).get(2).nodeId).isEqualTo(11);
        assertThat(problemInstance.getPickups(3).get(2).requestId).isEqualTo(3);
        assertThat(problemInstance.getPickups(3).get(2).x).isEqualTo(385);
        assertThat(problemInstance.getPickups(3).get(2).y).isEqualTo(58);
        assertThat(problemInstance.getPickups(3).get(2).demand).isEqualTo(24);
        assertThat(problemInstance.getPickups(3).get(2).twStart).isEqualTo(115);
        assertThat(problemInstance.getPickups(3).get(2).twEnd).isEqualTo(725);
        assertThat(problemInstance.getPickups(3).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(3).get(2).isPickup).isTrue();
        assertThat(problemInstance.getPickups(3).get(2).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(3).nodeId).isEqualTo(12);
        assertThat(problemInstance.getDelivery(3).requestId).isEqualTo(3);
        assertThat(problemInstance.getDelivery(3).x).isEqualTo(226);
        assertThat(problemInstance.getDelivery(3).y).isEqualTo(175);
        assertThat(problemInstance.getDelivery(3).demand).isEqualTo(-60);
        assertThat(problemInstance.getDelivery(3).twStart).isEqualTo(1404);
        assertThat(problemInstance.getDelivery(3).twEnd).isEqualTo(1925);
        assertThat(problemInstance.getDelivery(3).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(3).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(3).isDeliver).isTrue();

        // Request 5
        assertThat(problemInstance.getPickups(4).get(0).nodeId).isEqualTo(13);
        assertThat(problemInstance.getPickups(4).get(0).requestId).isEqualTo(4);
        assertThat(problemInstance.getPickups(4).get(0).x).isEqualTo(471);
        assertThat(problemInstance.getPickups(4).get(0).y).isEqualTo(368);
        assertThat(problemInstance.getPickups(4).get(0).demand).isEqualTo(19);
        assertThat(problemInstance.getPickups(4).get(0).twStart).isEqualTo(138);
        assertThat(problemInstance.getPickups(4).get(0).twEnd).isEqualTo(748);
        assertThat(problemInstance.getPickups(4).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(4).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(4).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(4).nodeId).isEqualTo(14);
        assertThat(problemInstance.getDelivery(4).requestId).isEqualTo(4);
        assertThat(problemInstance.getDelivery(4).x).isEqualTo(490);
        assertThat(problemInstance.getDelivery(4).y).isEqualTo(286);
        assertThat(problemInstance.getDelivery(4).demand).isEqualTo(-19);
        assertThat(problemInstance.getDelivery(4).twStart).isEqualTo(232);
        assertThat(problemInstance.getDelivery(4).twEnd).isEqualTo(842);
        assertThat(problemInstance.getDelivery(4).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(4).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(4).isDeliver).isTrue();

        // Request 6
        assertThat(problemInstance.getPickups(5).get(0).nodeId).isEqualTo(15);
        assertThat(problemInstance.getPickups(5).get(0).requestId).isEqualTo(5);
        assertThat(problemInstance.getPickups(5).get(0).x).isEqualTo(253);
        assertThat(problemInstance.getPickups(5).get(0).y).isEqualTo(50);
        assertThat(problemInstance.getPickups(5).get(0).demand).isEqualTo(27);
        assertThat(problemInstance.getPickups(5).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(5).get(0).twEnd).isEqualTo(546);
        assertThat(problemInstance.getPickups(5).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(5).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(5).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(5).get(1).nodeId).isEqualTo(16);
        assertThat(problemInstance.getPickups(5).get(1).requestId).isEqualTo(5);
        assertThat(problemInstance.getPickups(5).get(1).x).isEqualTo(0);
        assertThat(problemInstance.getPickups(5).get(1).y).isEqualTo(356);
        assertThat(problemInstance.getPickups(5).get(1).demand).isEqualTo(13);
        assertThat(problemInstance.getPickups(5).get(1).twStart).isEqualTo(796);
        assertThat(problemInstance.getPickups(5).get(1).twEnd).isEqualTo(1406);
        assertThat(problemInstance.getPickups(5).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(5).get(1).isPickup).isTrue();
        assertThat(problemInstance.getPickups(5).get(1).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(5).nodeId).isEqualTo(17);
        assertThat(problemInstance.getDelivery(5).requestId).isEqualTo(5);
        assertThat(problemInstance.getDelivery(5).x).isEqualTo(209);
        assertThat(problemInstance.getDelivery(5).y).isEqualTo(212);
        assertThat(problemInstance.getDelivery(5).demand).isEqualTo(-40);
        assertThat(problemInstance.getDelivery(5).twStart).isEqualTo(1238);
        assertThat(problemInstance.getDelivery(5).twEnd).isEqualTo(1848);
        assertThat(problemInstance.getDelivery(5).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(5).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(5).isDeliver).isTrue();

        // Request 7
        assertThat(problemInstance.getPickups(6).get(0).nodeId).isEqualTo(18);
        assertThat(problemInstance.getPickups(6).get(0).requestId).isEqualTo(6);
        assertThat(problemInstance.getPickups(6).get(0).x).isEqualTo(144);
        assertThat(problemInstance.getPickups(6).get(0).y).isEqualTo(35);
        assertThat(problemInstance.getPickups(6).get(0).demand).isEqualTo(26);
        assertThat(problemInstance.getPickups(6).get(0).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(6).get(0).twEnd).isEqualTo(549);
        assertThat(problemInstance.getPickups(6).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(6).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(6).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(6).get(1).nodeId).isEqualTo(19);
        assertThat(problemInstance.getPickups(6).get(1).requestId).isEqualTo(6);
        assertThat(problemInstance.getPickups(6).get(1).x).isEqualTo(154);
        assertThat(problemInstance.getPickups(6).get(1).y).isEqualTo(374);
        assertThat(problemInstance.getPickups(6).get(1).demand).isEqualTo(36);
        assertThat(problemInstance.getPickups(6).get(1).twStart).isEqualTo(647);
        assertThat(problemInstance.getPickups(6).get(1).twEnd).isEqualTo(1257);
        assertThat(problemInstance.getPickups(6).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(6).get(1).isPickup).isTrue();
        assertThat(problemInstance.getPickups(6).get(1).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(6).get(2).nodeId).isEqualTo(20);
        assertThat(problemInstance.getPickups(6).get(2).requestId).isEqualTo(6);
        assertThat(problemInstance.getPickups(6).get(2).x).isEqualTo(452);
        assertThat(problemInstance.getPickups(6).get(2).y).isEqualTo(484);
        assertThat(problemInstance.getPickups(6).get(2).demand).isEqualTo(26);
        assertThat(problemInstance.getPickups(6).get(2).twStart).isEqualTo(988);
        assertThat(problemInstance.getPickups(6).get(2).twEnd).isEqualTo(1598);
        assertThat(problemInstance.getPickups(6).get(2).serviceTime).isEqualTo(0);
        assertThat(problemInstance.getPickups(6).get(2).isPickup).isTrue();
        assertThat(problemInstance.getPickups(6).get(2).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(6).nodeId).isEqualTo(21);
        assertThat(problemInstance.getDelivery(6).requestId).isEqualTo(6);
        assertThat(problemInstance.getDelivery(6).x).isEqualTo(485);
        assertThat(problemInstance.getDelivery(6).y).isEqualTo(428);
        assertThat(problemInstance.getDelivery(6).demand).isEqualTo(-88);
        assertThat(problemInstance.getDelivery(6).twStart).isEqualTo(1200);
        assertThat(problemInstance.getDelivery(6).twEnd).isEqualTo(1810);
        assertThat(problemInstance.getDelivery(6).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(6).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(6).isDeliver).isTrue();

        // Request 8
        assertThat(problemInstance.getPickups(7).get(0).nodeId).isEqualTo(22);
        assertThat(problemInstance.getPickups(7).get(0).requestId).isEqualTo(7);
        assertThat(problemInstance.getPickups(7).get(0).x).isEqualTo(441);
        assertThat(problemInstance.getPickups(7).get(0).y).isEqualTo(7);
        assertThat(problemInstance.getPickups(7).get(0).demand).isEqualTo(20);
        assertThat(problemInstance.getPickups(7).get(0).twStart).isEqualTo(102);
        assertThat(problemInstance.getPickups(7).get(0).twEnd).isEqualTo(712);
        assertThat(problemInstance.getPickups(7).get(0).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(7).get(0).isPickup).isTrue();
        assertThat(problemInstance.getPickups(7).get(0).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(7).get(1).nodeId).isEqualTo(23);
        assertThat(problemInstance.getPickups(7).get(1).requestId).isEqualTo(7);
        assertThat(problemInstance.getPickups(7).get(1).x).isEqualTo(235);
        assertThat(problemInstance.getPickups(7).get(1).y).isEqualTo(288);
        assertThat(problemInstance.getPickups(7).get(1).demand).isEqualTo(18);
        assertThat(problemInstance.getPickups(7).get(1).twStart).isEqualTo(0);
        assertThat(problemInstance.getPickups(7).get(1).twEnd).isEqualTo(350);
        assertThat(problemInstance.getPickups(7).get(1).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(7).get(1).isPickup).isTrue();
        assertThat(problemInstance.getPickups(7).get(1).isDeliver).isFalse();

        assertThat(problemInstance.getPickups(7).get(2).nodeId).isEqualTo(24);
        assertThat(problemInstance.getPickups(7).get(2).requestId).isEqualTo(7);
        assertThat(problemInstance.getPickups(7).get(2).x).isEqualTo(392);
        assertThat(problemInstance.getPickups(7).get(2).y).isEqualTo(1);
        assertThat(problemInstance.getPickups(7).get(2).demand).isEqualTo(11);
        assertThat(problemInstance.getPickups(7).get(2).twStart).isEqualTo(315);
        assertThat(problemInstance.getPickups(7).get(2).twEnd).isEqualTo(925);
        assertThat(problemInstance.getPickups(7).get(2).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getPickups(7).get(2).isPickup).isTrue();
        assertThat(problemInstance.getPickups(7).get(2).isDeliver).isFalse();

        assertThat(problemInstance.getDelivery(7).nodeId).isEqualTo(25);
        assertThat(problemInstance.getDelivery(7).requestId).isEqualTo(7);
        assertThat(problemInstance.getDelivery(7).x).isEqualTo(60);
        assertThat(problemInstance.getDelivery(7).y).isEqualTo(5);
        assertThat(problemInstance.getDelivery(7).demand).isEqualTo(-49);
        assertThat(problemInstance.getDelivery(7).twStart).isEqualTo(754);
        assertThat(problemInstance.getDelivery(7).twEnd).isEqualTo(1364);
        assertThat(problemInstance.getDelivery(7).serviceTime).isEqualTo(10);
        assertThat(problemInstance.getDelivery(7).isPickup).isFalse();
        assertThat(problemInstance.getDelivery(7).isDeliver).isTrue();

        for (int i = 0; i < problemInstance.getNumNodes(); i++) {
            for (int j = 0; j < problemInstance.getNumNodes(); j++) {
                if (i == j) {
                    assertThat(problemInstance.dist(i, j)).isEqualTo(0.0);
                } else {
                    assertThat(problemInstance.dist(i, j)).isGreaterThan(1.0);
                    assertThat(problemInstance.dist(i, j)).isLessThan(1925.0);
                }
            }
        }
    }

    @Test
    public void requestPathTreeTest() throws IOException {
        // Biggest problem
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_100_1.txt").toFile());
        OptimalRequestSolver optimalRequestSolver[] = new OptimalRequestSolver[problemInstance.getNumReq()];
        for (int i = 0; i < problemInstance.getNumReq(); i++) {
            optimalRequestSolver[i] = new OptimalRequestSolver(i, problemInstance);
            optimalRequestSolver[i].optimize();
        }
        // Request 0
        assertThat(optimalRequestSolver[0].getBestRoute()).containsExactly(0, 1, 2, 0);
        assertThat(optimalRequestSolver[0].getBestCost()).isEqualTo(813.085941499155);
        // Request 1
        assertThat(optimalRequestSolver[1].getBestRoute()).containsExactly(0, 4, 9, 5, 7, 8, 6, 3, 10, 0);
        assertThat(optimalRequestSolver[1].getBestCost()).isEqualTo(1149.711175106366);
        // Request 2
        assertThat(optimalRequestSolver[2].getBestRoute()).containsExactly(0, 11, 12, 0);
        assertThat(optimalRequestSolver[2].getBestCost()).isEqualTo(532.3857383729111);
        // Request 3
        assertThat(optimalRequestSolver[3].getBestRoute()).containsExactly(0, 15, 13, 14, 16, 0);
        assertThat(optimalRequestSolver[3].getBestCost()).isEqualTo(1061.6855639710548);
        // Request 4
        assertThat(optimalRequestSolver[4].getBestRoute()).containsExactly(0, 21, 22, 20, 17, 18, 19, 23, 0);
        assertThat(optimalRequestSolver[4].getBestCost()).isEqualTo(875.265402601825);
        // Request 5
        assertThat(optimalRequestSolver[5].getBestRoute()).containsExactly(0, 24, 26, 25, 27, 0);
        assertThat(optimalRequestSolver[5].getBestCost()).isEqualTo(722.1723776822522);
        // Request 6
        assertThat(optimalRequestSolver[6].getBestRoute()).containsExactly(0, 29, 33, 31, 32, 28, 30, 34, 0);
        assertThat(optimalRequestSolver[6].getBestCost()).isEqualTo(1236.0014167050563);
        // Request 7
        assertThat(optimalRequestSolver[7].getBestRoute()).containsExactly(0, 36, 37, 35, 39, 38, 40, 41, 0);
        assertThat(optimalRequestSolver[7].getBestCost()).isEqualTo(1004.1913518887611);
        // Request 8
        assertThat(optimalRequestSolver[8].getBestRoute()).containsExactly(0, 42, 45, 46, 47, 44, 43, 48, 0);
        assertThat(optimalRequestSolver[8].getBestCost()).isEqualTo(821.8483892607513);
        // Request 9
        assertThat(optimalRequestSolver[9].getBestRoute()).containsExactly(0, 49, 50, 0);
        assertThat(optimalRequestSolver[9].getBestCost()).isEqualTo(582.8854799990843);
        // Request 10
        assertThat(optimalRequestSolver[10].getBestRoute()).containsExactly(0, 53, 54, 51, 52, 55, 0);
        assertThat(optimalRequestSolver[10].getBestCost()).isEqualTo(1169.5806164824348);
        // Request 11
        assertThat(optimalRequestSolver[11].getBestRoute()).containsExactly(0, 59, 58, 56, 57, 60, 0);
        assertThat(optimalRequestSolver[11].getBestCost()).isEqualTo(1275.6271683834632);
        // Request 12
        assertThat(optimalRequestSolver[12].getBestRoute()).containsExactly(0, 61, 64, 63, 62, 65, 0);
        assertThat(optimalRequestSolver[12].getBestCost()).isEqualTo(1207.8891040790406);
        // Request 13
        assertThat(optimalRequestSolver[13].getBestRoute()).containsExactly(0, 69, 68, 66, 67, 70, 0);
        assertThat(optimalRequestSolver[13].getBestCost()).isEqualTo(708.7979579237583);
        // Request 14
        assertThat(optimalRequestSolver[14].getBestRoute()).containsExactly(0, 73, 72, 74, 71, 75, 0);
        assertThat(optimalRequestSolver[14].getBestCost()).isEqualTo(938.0159397936376);
        // Request 15
        assertThat(optimalRequestSolver[15].getBestRoute()).containsExactly(0, 80, 76, 79, 81, 77, 78, 82, 83, 0);
        assertThat(optimalRequestSolver[15].getBestCost()).isEqualTo(1361.351237818792);
        // Request 16
        assertThat(optimalRequestSolver[16].getBestRoute()).containsExactly(0, 84, 85, 86, 0);
        assertThat(optimalRequestSolver[16].getBestCost()).isEqualTo(829.586163078039);
        // Request 17
        assertThat(optimalRequestSolver[17].getBestRoute()).containsExactly(0, 87, 88, 0);
        assertThat(optimalRequestSolver[17].getBestCost()).isEqualTo(455.3452443620958);
        // Request 18
        assertThat(optimalRequestSolver[18].getBestRoute()).containsExactly(0, 89, 90, 0);
        assertThat(optimalRequestSolver[18].getBestCost()).isEqualTo(743.2515149172218);
        // Request 19
        assertThat(optimalRequestSolver[19].getBestRoute()).containsExactly(0, 94, 93, 91, 92, 95, 0);
        assertThat(optimalRequestSolver[19].getBestCost()).isEqualTo(1171.1807642343833);
        // Request 20
        assertThat(optimalRequestSolver[20].getBestRoute()).containsExactly(0, 96, 97, 0);
        assertThat(optimalRequestSolver[20].getBestCost()).isEqualTo(660.9125819749347);
        // Request 21
        assertThat(optimalRequestSolver[21].getBestRoute()).containsExactly(0, 98, 101, 99, 104, 100, 103, 102, 105, 0);
        assertThat(optimalRequestSolver[21].getBestCost()).isEqualTo(1036.5618362699545);
    }

    @Test
    public void relocateSimpleLocalSearchTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 24, 22, 23, 3, 4, 6, 25, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 7, 15, 8, 13, 14, 1, 2, 16, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 10, 11, 9, 12, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 18, 19, 20, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(2, 5, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6)));

        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(5779.107690950086);
        assertThat(ant.timeWindowPenalty).isEqualTo(1954.7132147852114);
        assertThat(ant.feasible).isFalse();

        RelocateNodeOperator relocateNodeOperator = new RelocateNodeOperator(problemInstance);
        Solution improvedAnt = relocateNodeOperator.relocate(ant);
        assertThat(improvedAnt.totalCost).isEqualTo(5401.660690829858);
        assertThat(improvedAnt.timeWindowPenalty).isEqualTo(2135.1224851281822);
        assertThat(improvedAnt.feasible).isFalse();
    }

    @Test
    public void relocateLoopLocalSearchTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 24, 22, 16, 23, 17, 25, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 7, 8, 4, 3, 1, 6, 2, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 13, 14, 11, 10, 9, 12, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 18, 19, 20, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 2, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6)));

        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(5866.440173144294);
        assertThat(ant.timeWindowPenalty).isEqualTo(1394.7584899599913);
        assertThat(ant.feasible).isFalse();

        RelocateNodeOperator relocateNodeOperator = new RelocateNodeOperator(problemInstance);
        Solution improvedAnt = relocateNodeOperator.relocate(ant);
        assertThat(improvedAnt.totalCost).isEqualTo(5260.047338910622);
        assertThat(improvedAnt.timeWindowPenalty).isEqualTo(1346.9846593923335);
        assertThat(improvedAnt.feasible).isFalse();
    }

    @Test
    public void expensiveRequestsRemovalOperatorFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 18, 16, 19, 20, 21, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 7, 11, 22, 24, 25, 10, 9, 8, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7, 2, 3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4768.957285204959);
        assertThat(ant.timeWindowPenalty).isEqualTo(814.3582706522368);
        assertThat(ant.feasible).isFalse();

        ExpensiveRequestRemoval removalOperator = new ExpensiveRequestRemoval(new Random(1), problemInstance);
        List<Req> removedRequests = removalOperator.removeRequests(ant, 6);

        assertThat(removedRequests).hasSize(6);
        assertThat(removedRequests.get(0).vehicleId).isEqualTo(1);
        assertThat(removedRequests.get(0).requestId).isEqualTo(6);
        assertThat(removedRequests.get(0).cost).isEqualTo(-699.1726886684014);
        assertThat(removedRequests.get(1).vehicleId).isEqualTo(1);
        assertThat(removedRequests.get(1).requestId).isEqualTo(5);
        assertThat(removedRequests.get(1).cost).isEqualTo(-906.7745789247228);
        assertThat(removedRequests.get(2).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(2).requestId).isEqualTo(7);
        assertThat(removedRequests.get(2).cost).isEqualTo(-710.5402020765637);
        assertThat(removedRequests.get(3).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(3).requestId).isEqualTo(0);
        assertThat(removedRequests.get(3).cost).isEqualTo(-683.7227097531211);
        assertThat(removedRequests.get(4).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(4).requestId).isEqualTo(2);
        assertThat(removedRequests.get(4).cost).isEqualTo(-115.13005610108814);
        assertThat(removedRequests.get(5).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(5).requestId).isEqualTo(3);
        assertThat(removedRequests.get(5).cost).isEqualTo(-856.2361060820575);

        problemInstance.solutionEvaluation(ant);
        assertThat(ant.feasible).isFalse(); // Infeasible number of requests. We expect this error because we removed the requests

        assertThat(ant.tours).hasSize(3);
        assertThat(ant.requests).hasSize(3);
        assertThat(ant.tourCosts).hasSize(3);
        assertThat(ant.totalCost).isEqualTo(600797.3809435989);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isFalse();

        SequentialInsertion insertionOperator = new SequentialInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(2, 0, 5);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void randomRequestsRemovalOperatorFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 18, 16, 19, 20, 21, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 7, 11, 22, 24, 25, 10, 9, 8, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7, 2, 3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4768.957285204959);
        assertThat(ant.timeWindowPenalty).isEqualTo(814.3582706522368);
        assertThat(ant.feasible).isFalse();

        RandomRemoval removalOperator = new RandomRemoval(new Random(1), problemInstance);
        List<Req> removedRequests = removalOperator.removeRequests(ant, 6);

        assertThat(removedRequests).hasSize(6);
        assertThat(removedRequests.get(0).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(0).requestId).isEqualTo(7);
        assertThat(removedRequests.get(0).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(1).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(1).requestId).isEqualTo(0);
        assertThat(removedRequests.get(1).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(2).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(2).requestId).isEqualTo(4);
        assertThat(removedRequests.get(2).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(3).vehicleId).isEqualTo(1);
        assertThat(removedRequests.get(3).requestId).isEqualTo(5);
        assertThat(removedRequests.get(3).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(4).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(4).requestId).isEqualTo(3);
        assertThat(removedRequests.get(4).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(5).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(5).requestId).isEqualTo(1);
        assertThat(removedRequests.get(5).cost).isEqualTo(0.0);

        problemInstance.solutionEvaluation(ant);
        assertThat(ant.feasible).isFalse(); // Infeasible number of requests. We expect this error because we removed the requests

        assertThat(ant.tours).hasSize(3);
        assertThat(ant.requests).hasSize(3);
        assertThat(ant.tourCosts).hasSize(3);
        assertThat(ant.totalCost).isEqualTo(601853.3803409035);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isFalse();

        SequentialInsertion insertionOperator = new SequentialInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests);

        assertThat(ant.tours).hasSize(5);
        assertThat(ant.requests).hasSize(5);
        assertThat(ant.tourCosts).hasSize(5);
        assertThat(ant.totalCost).isEqualTo(5849.68719667147);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 7, 13, 14, 8, 1, 2, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(4)).containsExactly(0, 15, 16, 17, 0);
        assertThat(ant.requests.get(0)).containsExactly(1);
        assertThat(ant.requests.get(1)).containsExactly(6);
        assertThat(ant.requests.get(2)).containsExactly(2, 4, 0);
        assertThat(ant.requests.get(3)).containsExactly(3, 7);
        assertThat(ant.requests.get(4)).containsExactly(5);
    }

    @Test
    public void shawRequestsRemovalOperatorFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 18, 16, 19, 20, 21, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 7, 11, 22, 24, 25, 10, 9, 8, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7, 2, 3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4768.957285204959);
        assertThat(ant.timeWindowPenalty).isEqualTo(814.3582706522368);
        assertThat(ant.feasible).isFalse();

        ShawRemoval removalOperator = new ShawRemoval(new Random(1), problemInstance);
        List<Req> removedRequests = removalOperator.removeRequests(ant, 6);

        assertThat(removedRequests).hasSize(6);
        assertThat(removedRequests.get(0).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(0).requestId).isEqualTo(2);
        assertThat(removedRequests.get(0).cost).isEqualTo(0.0);
        assertThat(removedRequests.get(1).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(1).requestId).isEqualTo(4);
        assertThat(removedRequests.get(1).cost).isEqualTo(107.96758772891057);
        assertThat(removedRequests.get(2).vehicleId).isEqualTo(1);
        assertThat(removedRequests.get(2).requestId).isEqualTo(6);
        assertThat(removedRequests.get(2).cost).isEqualTo(142.08800090085018);
        assertThat(removedRequests.get(3).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(3).requestId).isEqualTo(0);
        assertThat(removedRequests.get(3).cost).isEqualTo(456.6399018920707);
        assertThat(removedRequests.get(4).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(4).requestId).isEqualTo(1);
        assertThat(removedRequests.get(4).cost).isEqualTo(224.39696967650877);
        assertThat(removedRequests.get(5).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(5).requestId).isEqualTo(3);
        assertThat(removedRequests.get(5).cost).isEqualTo(286.38610301479366);
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.tours).hasSize(3);
        assertThat(ant.requests).hasSize(3);
        assertThat(ant.tourCosts).hasSize(3);
        assertThat(ant.totalCost).isEqualTo(601987.4790937051);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isFalse();

        SequentialInsertion insertionOperator = new SequentialInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests);

        assertThat(ant.totalCost).isEqualTo(5687.798144910946);
        assertThat(ant.tours).hasSize(5);
        assertThat(ant.requests).hasSize(5);
        assertThat(ant.tourCosts).hasSize(5);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 15, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 7, 22, 24, 8, 25, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 11, 10, 9, 12, 0);
        assertThat(ant.tours.get(4)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(4, 1);
        assertThat(ant.requests.get(1)).containsExactly(5, 0);
        assertThat(ant.requests.get(2)).containsExactly(7, 2);
        assertThat(ant.requests.get(3)).containsExactly(3);
        assertThat(ant.requests.get(4)).containsExactly(6);
    }

    @Test
    public void mostExpensiveNodesRequestsRemovalOperatorFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 7, 8, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 18, 19, 20, 21, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 22, 24, 25, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 11, 10, 9, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        ant.requests.add(new ArrayList<>(Arrays.asList(2)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(6178.199194042737);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();

        ExpensiveNodeRemoval removalOperator = new ExpensiveNodeRemoval(new Random(1), problemInstance);
        List<Req> removedRequests = removalOperator.removeRequests(ant, 6);

        assertThat(removedRequests).hasSize(6);
        assertThat(removedRequests.get(0).vehicleId).isEqualTo(3);
        assertThat(removedRequests.get(0).requestId).isEqualTo(6);
        assertThat(removedRequests.get(0).cost).isEqualTo(-422.0393360326907);
        assertThat(removedRequests.get(1).vehicleId).isEqualTo(1);
        assertThat(removedRequests.get(1).requestId).isEqualTo(5);
        assertThat(removedRequests.get(1).cost).isEqualTo(-482.98138421076436);
        assertThat(removedRequests.get(2).vehicleId).isEqualTo(2);
        assertThat(removedRequests.get(2).requestId).isEqualTo(2);
        assertThat(removedRequests.get(2).cost).isEqualTo(-417.32115184854763);
        assertThat(removedRequests.get(3).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(3).requestId).isEqualTo(0);
        assertThat(removedRequests.get(3).cost).isEqualTo(-395.39044354167004);
        assertThat(removedRequests.get(4).vehicleId).isEqualTo(0);
        assertThat(removedRequests.get(4).requestId).isEqualTo(4);
        assertThat(removedRequests.get(4).cost).isEqualTo(-27.060201600181017);
        assertThat(removedRequests.get(5).vehicleId).isEqualTo(4);
        assertThat(removedRequests.get(5).requestId).isEqualTo(7);
        assertThat(removedRequests.get(5).cost).isEqualTo(-355.4200364911517);

        problemInstance.solutionEvaluation(ant);
        assertThat(ant.feasible).isFalse(); // Infeasible number of requests. We expect this error because we removed the requests

        assertThat(ant.tours).hasSize(6);
        assertThat(ant.requests).hasSize(6);
        assertThat(ant.tourCosts).hasSize(6);
        assertThat(ant.totalCost).isEqualTo(601516.006141649);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isFalse();

        SequentialInsertion insertionOperator = new SequentialInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests);

        assertThat(ant.totalCost).isEqualTo(5971.441973850669);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.tours).hasSize(7);
        assertThat(ant.requests).hasSize(7);
        assertThat(ant.tourCosts).hasSize(7);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 1, 2, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 15, 16, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 22, 24, 25, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.tours.get(4)).containsExactly(0, 0);
        assertThat(ant.tours.get(5)).containsExactly(0, 7, 11, 8, 10, 9, 12, 0);
        assertThat(ant.tours.get(6)).containsExactly(0, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4, 0);
        assertThat(ant.requests.get(1)).containsExactly(5);
        assertThat(ant.requests.get(2)).containsExactly(7);
        assertThat(ant.requests.get(3)).containsExactly(6);
        assertThat(ant.requests.get(4)).isEmpty();
        assertThat(ant.requests.get(5)).containsExactly(3, 2);
        assertThat(ant.requests.get(6)).isEmpty();

    }

    @Test
    public void greedyInsertionOrderFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        GreedyInsertion insertionOperator = new GreedyInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Random, 0);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 2, 0);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void simpleInsertionOrderFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        GreedyInsertion insertionOperator = new GreedyInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Simple, 0);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 2, 0);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void cheapestInsertionOrderFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        GreedyInsertion insertionOperator = new GreedyInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Cheapest, 0);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 2, 0);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void greedyInsertionMethodFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        GreedyInsertion insertionOperator = new GreedyInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Simple, 0);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 2, 0);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void regret3NoNoiseInsertionMethodFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        RegretInsertion insertionOperator = new RegretInsertion(problemInstance, new Random(1), "3", 0.0);
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Simple, 0);

        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours).hasSize(5);
        assertThat(ant.requests).hasSize(5);
        assertThat(ant.tourCosts).hasSize(5);
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.tours.get(4)).containsExactly(0, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 0, 2);
        assertThat(ant.requests.get(2)).containsExactly(7, 3);
        assertThat(ant.requests.get(3)).containsExactly(6);
        assertThat(ant.requests.get(4)).isEmpty();
    }

    @Test
    public void expensiveInsertionOrderFromCoelhoTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 4, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 16, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5)));
        List<Req> removedRequests = new ArrayList<>();
        removedRequests.add(new Req(2, 2, 0));
        removedRequests.add(new Req(2, 7, 0));
        removedRequests.add(new Req(0, 0, 0));
        removedRequests.add(new Req(0, 4, 0));
        removedRequests.add(new Req(2, 3, 0));
        removedRequests.add(new Req(1, 6, 0));

        GreedyInsertion insertionOperator = new GreedyInsertion(problemInstance, new Random(1));
        problemInstance.solutionEvaluation(ant);
        insertionOperator.insertRequests(ant, removedRequests, PickupMethod.Expensive, 0);

        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(5, 2, 0);
        assertThat(ant.requests.get(2)).containsExactly(3, 7);
        assertThat(ant.requests.get(3)).containsExactly(6);
    }

    @Test
    public void relocateRequestOperatorTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 1, 2, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 15, 18, 16, 19, 20, 21, 17, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 23, 7, 11, 22, 24, 25, 10, 9, 8, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 4, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(5, 6)));
        ant.requests.add(new ArrayList<>(Arrays.asList(7, 2, 3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4768.957285204959);
        assertThat(ant.timeWindowPenalty).isEqualTo(814.3582706522368);
        assertThat(ant.feasible).isFalse();

        RelocateRequestOperator relocateRequestOperator = new RelocateRequestOperator(problemInstance, new Random(1));
        ant = relocateRequestOperator.relocate(ant);

        assertThat(ant.totalCost).isEqualTo(5108.8608230373175);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours).hasSize(4);
        assertThat(ant.requests).hasSize(4);
        assertThat(ant.tourCosts).hasSize(4);
        assertThat(ant.tours.get(0)).containsExactly(0, 5, 3, 13, 14, 4, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 18, 19, 20, 21, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 23, 11, 22, 24, 25, 10, 9, 12, 0);
        assertThat(ant.tours.get(3)).containsExactly(0, 7, 15, 8, 1, 16, 2, 17, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 4);
        assertThat(ant.requests.get(1)).containsExactly(6);
        assertThat(ant.requests.get(2)).containsExactly(7, 3);
        assertThat(ant.requests.get(3)).containsExactly(2, 0, 5);
        assertThat(ant.tourCosts.get(0)).isEqualTo(797.3809435990038);
        assertThat(ant.tourCosts.get(1)).isEqualTo(1256.3149262813738);
        assertThat(ant.tourCosts.get(2)).isEqualTo(1589.7484360038004);
        assertThat(ant.tourCosts.get(3)).isEqualTo(1465.416517153139);
    }

    @Test
    public void exchangeRequestOperatorTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_3.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 3, 19, 26, 18, 25, 5, 4, 27, 20, 6, 21, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 12, 23, 13, 22, 24, 11, 14, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 1, 9, 16, 2, 7, 15, 8, 10, 17, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 5, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6, 3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 0, 2)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(5917.526871322101);
        assertThat(ant.timeWindowPenalty).isEqualTo(5345.170189114092);
        assertThat(ant.feasible).isFalse();

        ExchangeRequestOperator exchangeRequestOperator = new ExchangeRequestOperator(problemInstance, new Random(1));
        ant = exchangeRequestOperator.exchange(ant);

        assertThat(ant.totalCost).isEqualTo(4744.995443563417);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.tours).hasSize(3);
        assertThat(ant.requests).hasSize(3);
        assertThat(ant.tourCosts).hasSize(3);
        assertThat(ant.tours.get(0)).containsExactly(0, 3, 1, 26, 25, 5, 2, 4, 27, 6, 0);
        assertThat(ant.tours.get(1)).containsExactly(0, 12, 23, 13, 22, 24, 11, 14, 0);
        assertThat(ant.tours.get(2)).containsExactly(0, 19, 9, 16, 7, 15, 8, 18, 10, 17, 20, 21, 0);
        assertThat(ant.requests.get(0)).containsExactly(1, 7, 0);
        assertThat(ant.requests.get(1)).containsExactly(6, 3);
        assertThat(ant.requests.get(2)).containsExactly(4, 2, 5);
        assertThat(ant.tourCosts.get(0)).isEqualTo(1530.3967130440456);
        assertThat(ant.tourCosts.get(1)).isEqualTo(1451.9543342625707);
        assertThat(ant.tourCosts.get(2)).isEqualTo(1762.644396256801);
    }

    @Test
    public void timeWindowsWithServiceTimeFeasibilityTest() throws IOException {
        // Check if the service time of the target point is not considered in the
        // end time windows summation
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_3.txt").toFile());
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 3, 1, 26, 25, 5, 2, 4, 27, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 12, 23, 13, 22, 24, 11, 14, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 19, 9, 16, 7, 15, 8, 18, 10, 20, 17, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 0, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6, 3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 5, 2)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4713.749202925907);
        assertThat(ant.feasible).isTrue();
    }

    @Test
    public void slackTimeLuCalculationTest() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "n_4_25_1.txt").toFile());
        ArrayList<Integer> route = new ArrayList<>(Arrays.asList(0, 5, 3, 13, 14, 4, 6, 0));
        double[] slackTimes = problemInstance.calculateSlackTimesLu(route);
        assertThat(slackTimes).hasSize(route.size());
        assertThat(slackTimes).containsExactly(159.9197004287602, 159.9197004287602, 186.28051010531715, 186.28051010531715, 186.28051010531715, 257.7465420002027, 310.0, 321.5992424351118);
    }

    @Test
    public void slackTimeSavelsberghTest() throws Exception {
        ProblemInstance problemInstance = new ProblemInstance();
        problemInstance.setDepot(new Depot());

        problemInstance.getDepot().nodeId = 0;
        problemInstance.getDepot().twStart = 6.0;
        problemInstance.getDepot().twEnd = 18.0;

        double[][] distances = new double[4][4];
        distances[0][1] = 0.5;
        distances[1][2] = 0.5;
        distances[2][3] = 0.5;
        distances[3][0] = 0.5;
        problemInstance.setDistances(distances);

        Request[] requests = new Request[3];
        requests[0] = new Request();
        requests[0].nodeId = 1;
        requests[0].twStart = 10.0;
        requests[0].twEnd = 14.0;
        requests[1] = new Request();
        requests[1].nodeId = 2;
        requests[1].twStart = 8.0;
        requests[1].twEnd = 12.0;
        requests[2] = new Request();
        requests[2].nodeId = 3;
        requests[2].twStart = 13.0;
        requests[2].twEnd = 17.0;
        problemInstance.setRequests(requests);

        ArrayList<Integer> route = new ArrayList(Arrays.asList(0, 1, 2, 3, 0));
        double[] slackTimes = problemInstance.slackTimesSavelsbergh(route, false);
        assertThat(slackTimes).hasSize(route.size());
        assertThat(slackTimes).containsExactly(5.0, 1.5, 1.5, 4.0, 4.5);

    }

    @Test
    public void evaluateCostWithoutNodes() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "l_4_25_3.txt").toFile());
        ArrayList<Integer> route = new ArrayList<>(Arrays.asList(0, 3, 4, 1, 2, 5, 6, 0));
        assertThat(problemInstance.costEvaluation(route)).isEqualTo(1786.408235901457);
        //Without requests
        assertThat(problemInstance.costEvaluation(route, 0)).isEqualTo(1155.0871770093245);
        assertThat(problemInstance.costEvaluation(route, 1)).isEqualTo(623.9389060056287);
        //Without nodes
        assertThat(problemInstance.costEvaluation(route, 0, 1)).isEqualTo(1165.5318171592444);
        assertThat(problemInstance.costEvaluation(route, 0, 2)).isEqualTo(1735.6868649597677);
        assertThat(problemInstance.costEvaluation(route, 1, 3)).isEqualTo(1463.0481036927035);
        assertThat(problemInstance.costEvaluation(route, 1, 4)).isEqualTo(1081.3127795412488);
        assertThat(problemInstance.costEvaluation(route, 1, 5)).isEqualTo(1735.7989979810277);
        assertThat(problemInstance.costEvaluation(route, 1, 6)).isEqualTo(1404.2251389434082);
    }

    @Test
    public void bestSolutionCoelhoRoute_w_8_25_3_test() throws IOException {
        ProblemInstance problemInstance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_25_3.txt").toFile());
        //Solution cost:2950.7741
        Solution ant = SolutionUtils.createNewSolution(problemInstance);
        //Route:1 cost:1237.5600 arr:1337 len:10 Dep-20-24-22-23-18-25-26-19-21-27-Dep-
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 21, 25, 23, 24, 19, 26, 27, 20, 22, 28, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 5, 6)));
        //Route:2 cost:1713.2141 arr:1873 len:18 Dep-8-5-7-0-14-2-15-13-10-12-4-1-6-3-16-17-9-11-Dep-
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 9, 6, 8, 1, 15, 3, 16, 14, 11, 13, 5, 2, 7, 4, 17, 18, 10, 12, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(0, 1, 2, 3)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(2950.7741171841853);
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.feasible).isTrue();
    }

}
