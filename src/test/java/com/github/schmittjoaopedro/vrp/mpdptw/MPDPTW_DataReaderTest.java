package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_DataReaderTest {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_large_4_25_1_test() throws IOException {
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_25_1.txt").toFile());
        assertThat(problemInstance.noNodes).isEqualTo(26);
        assertThat(problemInstance.noReq).isEqualTo(25);
        assertThat(problemInstance.noMaxVehicles).isEqualTo(15);
        assertThat(problemInstance.vehicleCapacity).isEqualTo(200);
        // Depot
        assertThat(problemInstance.depot.vertexId).isEqualTo(0);
        assertThat(problemInstance.depot.x).isEqualTo(250);
        assertThat(problemInstance.depot.y).isEqualTo(250);
        assertThat(problemInstance.depot.twStart).isEqualTo(0);
        assertThat(problemInstance.depot.twEnd).isEqualTo(1925);

        // Request 1
        assertThat(problemInstance.pickUpRequests[0].vertexId).isEqualTo(1);
        assertThat(problemInstance.pickUpRequests[0].requestId).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[0].x).isEqualTo(396);
        assertThat(problemInstance.pickUpRequests[0].y).isEqualTo(417);
        assertThat(problemInstance.pickUpRequests[0].demand).isEqualTo(13);
        assertThat(problemInstance.pickUpRequests[0].twStart).isEqualTo(520);
        assertThat(problemInstance.pickUpRequests[0].twEnd).isEqualTo(1130);
        assertThat(problemInstance.pickUpRequests[0].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[0].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[0].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[0].vertexId).isEqualTo(2);
        assertThat(problemInstance.deliveryRequests[0].requestId).isEqualTo(0);
        assertThat(problemInstance.deliveryRequests[0].x).isEqualTo(51);
        assertThat(problemInstance.deliveryRequests[0].y).isEqualTo(286);
        assertThat(problemInstance.deliveryRequests[0].demand).isEqualTo(-13);
        assertThat(problemInstance.deliveryRequests[0].twStart).isEqualTo(1169);
        assertThat(problemInstance.deliveryRequests[0].twEnd).isEqualTo(1779);
        assertThat(problemInstance.deliveryRequests[0].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[0].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[0].isDeliver).isTrue();

        // Request 2
        assertThat(problemInstance.pickUpRequests[1].vertexId).isEqualTo(3);
        assertThat(problemInstance.pickUpRequests[1].requestId).isEqualTo(1);
        assertThat(problemInstance.pickUpRequests[1].x).isEqualTo(288);
        assertThat(problemInstance.pickUpRequests[1].y).isEqualTo(439);
        assertThat(problemInstance.pickUpRequests[1].demand).isEqualTo(16);
        assertThat(problemInstance.pickUpRequests[1].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[1].twEnd).isEqualTo(593);
        assertThat(problemInstance.pickUpRequests[1].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[1].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[1].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[2].vertexId).isEqualTo(4);
        assertThat(problemInstance.pickUpRequests[2].requestId).isEqualTo(1);
        assertThat(problemInstance.pickUpRequests[2].x).isEqualTo(399);
        assertThat(problemInstance.pickUpRequests[2].y).isEqualTo(175);
        assertThat(problemInstance.pickUpRequests[2].demand).isEqualTo(13);
        assertThat(problemInstance.pickUpRequests[2].twStart).isEqualTo(457);
        assertThat(problemInstance.pickUpRequests[2].twEnd).isEqualTo(1067);
        assertThat(problemInstance.pickUpRequests[2].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[2].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[2].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[3].vertexId).isEqualTo(5);
        assertThat(problemInstance.pickUpRequests[3].requestId).isEqualTo(1);
        assertThat(problemInstance.pickUpRequests[3].x).isEqualTo(247);
        assertThat(problemInstance.pickUpRequests[3].y).isEqualTo(306);
        assertThat(problemInstance.pickUpRequests[3].demand).isEqualTo(33);
        assertThat(problemInstance.pickUpRequests[3].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[3].twEnd).isEqualTo(366);
        assertThat(problemInstance.pickUpRequests[3].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[3].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[3].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[1].vertexId).isEqualTo(6);
        assertThat(problemInstance.deliveryRequests[1].requestId).isEqualTo(1);
        assertThat(problemInstance.deliveryRequests[1].x).isEqualTo(274);
        assertThat(problemInstance.deliveryRequests[1].y).isEqualTo(261);
        assertThat(problemInstance.deliveryRequests[1].demand).isEqualTo(-62);
        assertThat(problemInstance.deliveryRequests[1].twStart).isEqualTo(1417);
        assertThat(problemInstance.deliveryRequests[1].twEnd).isEqualTo(1925);
        assertThat(problemInstance.deliveryRequests[1].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[1].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[1].isDeliver).isTrue();

        // Request 3
        assertThat(problemInstance.pickUpRequests[4].vertexId).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[4].requestId).isEqualTo(2);
        assertThat(problemInstance.pickUpRequests[4].x).isEqualTo(176);
        assertThat(problemInstance.pickUpRequests[4].y).isEqualTo(199);
        assertThat(problemInstance.pickUpRequests[4].demand).isEqualTo(28);
        assertThat(problemInstance.pickUpRequests[4].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[4].twEnd).isEqualTo(399);
        assertThat(problemInstance.pickUpRequests[4].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[4].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[4].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[2].vertexId).isEqualTo(8);
        assertThat(problemInstance.deliveryRequests[2].requestId).isEqualTo(2);
        assertThat(problemInstance.deliveryRequests[2].x).isEqualTo(461);
        assertThat(problemInstance.deliveryRequests[2].y).isEqualTo(182);
        assertThat(problemInstance.deliveryRequests[2].demand).isEqualTo(-28);
        assertThat(problemInstance.deliveryRequests[2].twStart).isEqualTo(339);
        assertThat(problemInstance.deliveryRequests[2].twEnd).isEqualTo(949);
        assertThat(problemInstance.deliveryRequests[2].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[2].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[2].isDeliver).isTrue();

        // Request 4
        assertThat(problemInstance.pickUpRequests[5].vertexId).isEqualTo(9);
        assertThat(problemInstance.pickUpRequests[5].requestId).isEqualTo(3);
        assertThat(problemInstance.pickUpRequests[5].x).isEqualTo(489);
        assertThat(problemInstance.pickUpRequests[5].y).isEqualTo(159);
        assertThat(problemInstance.pickUpRequests[5].demand).isEqualTo(29);
        assertThat(problemInstance.pickUpRequests[5].twStart).isEqualTo(1138);
        assertThat(problemInstance.pickUpRequests[5].twEnd).isEqualTo(1748);
        assertThat(problemInstance.pickUpRequests[5].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[5].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[5].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[6].vertexId).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[6].requestId).isEqualTo(3);
        assertThat(problemInstance.pickUpRequests[6].x).isEqualTo(323);
        assertThat(problemInstance.pickUpRequests[6].y).isEqualTo(29);
        assertThat(problemInstance.pickUpRequests[6].demand).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[6].twStart).isEqualTo(847);
        assertThat(problemInstance.pickUpRequests[6].twEnd).isEqualTo(1457);
        assertThat(problemInstance.pickUpRequests[6].serviceTime).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[6].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[6].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[7].vertexId).isEqualTo(11);
        assertThat(problemInstance.pickUpRequests[7].requestId).isEqualTo(3);
        assertThat(problemInstance.pickUpRequests[7].x).isEqualTo(385);
        assertThat(problemInstance.pickUpRequests[7].y).isEqualTo(58);
        assertThat(problemInstance.pickUpRequests[7].demand).isEqualTo(24);
        assertThat(problemInstance.pickUpRequests[7].twStart).isEqualTo(115);
        assertThat(problemInstance.pickUpRequests[7].twEnd).isEqualTo(725);
        assertThat(problemInstance.pickUpRequests[7].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[7].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[7].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[3].vertexId).isEqualTo(12);
        assertThat(problemInstance.deliveryRequests[3].requestId).isEqualTo(3);
        assertThat(problemInstance.deliveryRequests[3].x).isEqualTo(226);
        assertThat(problemInstance.deliveryRequests[3].y).isEqualTo(175);
        assertThat(problemInstance.deliveryRequests[3].demand).isEqualTo(-60);
        assertThat(problemInstance.deliveryRequests[3].twStart).isEqualTo(1404);
        assertThat(problemInstance.deliveryRequests[3].twEnd).isEqualTo(1925);
        assertThat(problemInstance.deliveryRequests[3].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[3].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[3].isDeliver).isTrue();

        // Request 5
        assertThat(problemInstance.pickUpRequests[8].vertexId).isEqualTo(13);
        assertThat(problemInstance.pickUpRequests[8].requestId).isEqualTo(4);
        assertThat(problemInstance.pickUpRequests[8].x).isEqualTo(471);
        assertThat(problemInstance.pickUpRequests[8].y).isEqualTo(368);
        assertThat(problemInstance.pickUpRequests[8].demand).isEqualTo(19);
        assertThat(problemInstance.pickUpRequests[8].twStart).isEqualTo(138);
        assertThat(problemInstance.pickUpRequests[8].twEnd).isEqualTo(748);
        assertThat(problemInstance.pickUpRequests[8].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[8].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[8].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[4].vertexId).isEqualTo(14);
        assertThat(problemInstance.deliveryRequests[4].requestId).isEqualTo(4);
        assertThat(problemInstance.deliveryRequests[4].x).isEqualTo(490);
        assertThat(problemInstance.deliveryRequests[4].y).isEqualTo(286);
        assertThat(problemInstance.deliveryRequests[4].demand).isEqualTo(-19);
        assertThat(problemInstance.deliveryRequests[4].twStart).isEqualTo(232);
        assertThat(problemInstance.deliveryRequests[4].twEnd).isEqualTo(842);
        assertThat(problemInstance.deliveryRequests[4].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[4].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[4].isDeliver).isTrue();

        // Request 6
        assertThat(problemInstance.pickUpRequests[9].vertexId).isEqualTo(15);
        assertThat(problemInstance.pickUpRequests[9].requestId).isEqualTo(5);
        assertThat(problemInstance.pickUpRequests[9].x).isEqualTo(253);
        assertThat(problemInstance.pickUpRequests[9].y).isEqualTo(50);
        assertThat(problemInstance.pickUpRequests[9].demand).isEqualTo(27);
        assertThat(problemInstance.pickUpRequests[9].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[9].twEnd).isEqualTo(546);
        assertThat(problemInstance.pickUpRequests[9].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[9].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[9].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[10].vertexId).isEqualTo(16);
        assertThat(problemInstance.pickUpRequests[10].requestId).isEqualTo(5);
        assertThat(problemInstance.pickUpRequests[10].x).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[10].y).isEqualTo(356);
        assertThat(problemInstance.pickUpRequests[10].demand).isEqualTo(13);
        assertThat(problemInstance.pickUpRequests[10].twStart).isEqualTo(796);
        assertThat(problemInstance.pickUpRequests[10].twEnd).isEqualTo(1406);
        assertThat(problemInstance.pickUpRequests[10].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[10].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[10].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[5].vertexId).isEqualTo(17);
        assertThat(problemInstance.deliveryRequests[5].requestId).isEqualTo(5);
        assertThat(problemInstance.deliveryRequests[5].x).isEqualTo(209);
        assertThat(problemInstance.deliveryRequests[5].y).isEqualTo(212);
        assertThat(problemInstance.deliveryRequests[5].demand).isEqualTo(-40);
        assertThat(problemInstance.deliveryRequests[5].twStart).isEqualTo(1238);
        assertThat(problemInstance.deliveryRequests[5].twEnd).isEqualTo(1848);
        assertThat(problemInstance.deliveryRequests[5].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[5].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[5].isDeliver).isTrue();

        // Request 7
        assertThat(problemInstance.pickUpRequests[11].vertexId).isEqualTo(18);
        assertThat(problemInstance.pickUpRequests[11].requestId).isEqualTo(6);
        assertThat(problemInstance.pickUpRequests[11].x).isEqualTo(144);
        assertThat(problemInstance.pickUpRequests[11].y).isEqualTo(35);
        assertThat(problemInstance.pickUpRequests[11].demand).isEqualTo(26);
        assertThat(problemInstance.pickUpRequests[11].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[11].twEnd).isEqualTo(549);
        assertThat(problemInstance.pickUpRequests[11].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[11].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[11].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[12].vertexId).isEqualTo(19);
        assertThat(problemInstance.pickUpRequests[12].requestId).isEqualTo(6);
        assertThat(problemInstance.pickUpRequests[12].x).isEqualTo(154);
        assertThat(problemInstance.pickUpRequests[12].y).isEqualTo(374);
        assertThat(problemInstance.pickUpRequests[12].demand).isEqualTo(36);
        assertThat(problemInstance.pickUpRequests[12].twStart).isEqualTo(647);
        assertThat(problemInstance.pickUpRequests[12].twEnd).isEqualTo(1257);
        assertThat(problemInstance.pickUpRequests[12].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[12].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[12].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[13].vertexId).isEqualTo(20);
        assertThat(problemInstance.pickUpRequests[13].requestId).isEqualTo(6);
        assertThat(problemInstance.pickUpRequests[13].x).isEqualTo(452);
        assertThat(problemInstance.pickUpRequests[13].y).isEqualTo(484);
        assertThat(problemInstance.pickUpRequests[13].demand).isEqualTo(26);
        assertThat(problemInstance.pickUpRequests[13].twStart).isEqualTo(988);
        assertThat(problemInstance.pickUpRequests[13].twEnd).isEqualTo(1598);
        assertThat(problemInstance.pickUpRequests[13].serviceTime).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[13].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[13].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[6].vertexId).isEqualTo(21);
        assertThat(problemInstance.deliveryRequests[6].requestId).isEqualTo(6);
        assertThat(problemInstance.deliveryRequests[6].x).isEqualTo(485);
        assertThat(problemInstance.deliveryRequests[6].y).isEqualTo(428);
        assertThat(problemInstance.deliveryRequests[6].demand).isEqualTo(-88);
        assertThat(problemInstance.deliveryRequests[6].twStart).isEqualTo(1200);
        assertThat(problemInstance.deliveryRequests[6].twEnd).isEqualTo(1810);
        assertThat(problemInstance.deliveryRequests[6].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[6].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[6].isDeliver).isTrue();

        // Request 8
        assertThat(problemInstance.pickUpRequests[14].vertexId).isEqualTo(22);
        assertThat(problemInstance.pickUpRequests[14].requestId).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[14].x).isEqualTo(441);
        assertThat(problemInstance.pickUpRequests[14].y).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[14].demand).isEqualTo(20);
        assertThat(problemInstance.pickUpRequests[14].twStart).isEqualTo(102);
        assertThat(problemInstance.pickUpRequests[14].twEnd).isEqualTo(712);
        assertThat(problemInstance.pickUpRequests[14].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[14].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[14].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[15].vertexId).isEqualTo(23);
        assertThat(problemInstance.pickUpRequests[15].requestId).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[15].x).isEqualTo(235);
        assertThat(problemInstance.pickUpRequests[15].y).isEqualTo(288);
        assertThat(problemInstance.pickUpRequests[15].demand).isEqualTo(18);
        assertThat(problemInstance.pickUpRequests[15].twStart).isEqualTo(0);
        assertThat(problemInstance.pickUpRequests[15].twEnd).isEqualTo(350);
        assertThat(problemInstance.pickUpRequests[15].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[15].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[15].isDeliver).isFalse();

        assertThat(problemInstance.pickUpRequests[16].vertexId).isEqualTo(24);
        assertThat(problemInstance.pickUpRequests[16].requestId).isEqualTo(7);
        assertThat(problemInstance.pickUpRequests[16].x).isEqualTo(392);
        assertThat(problemInstance.pickUpRequests[16].y).isEqualTo(1);
        assertThat(problemInstance.pickUpRequests[16].demand).isEqualTo(11);
        assertThat(problemInstance.pickUpRequests[16].twStart).isEqualTo(315);
        assertThat(problemInstance.pickUpRequests[16].twEnd).isEqualTo(925);
        assertThat(problemInstance.pickUpRequests[16].serviceTime).isEqualTo(10);
        assertThat(problemInstance.pickUpRequests[16].isPickup).isTrue();
        assertThat(problemInstance.pickUpRequests[16].isDeliver).isFalse();

        assertThat(problemInstance.deliveryRequests[7].vertexId).isEqualTo(25);
        assertThat(problemInstance.deliveryRequests[7].requestId).isEqualTo(7);
        assertThat(problemInstance.deliveryRequests[7].x).isEqualTo(60);
        assertThat(problemInstance.deliveryRequests[7].y).isEqualTo(5);
        assertThat(problemInstance.deliveryRequests[7].demand).isEqualTo(-49);
        assertThat(problemInstance.deliveryRequests[7].twStart).isEqualTo(754);
        assertThat(problemInstance.deliveryRequests[7].twEnd).isEqualTo(1364);
        assertThat(problemInstance.deliveryRequests[7].serviceTime).isEqualTo(10);
        assertThat(problemInstance.deliveryRequests[7].isPickup).isFalse();
        assertThat(problemInstance.deliveryRequests[7].isDeliver).isTrue();

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

}
