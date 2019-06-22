package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.DynamicInstancesGenerator;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_UtilsTest {

    private static final String staticRootDirectory;


    private static final String dynamicRootDirectory;


    static {
        staticRootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        dynamicRootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("dpdptw_100").getFile().substring(1)).toString();
    }

    @Test
    public void readInstanceTest() throws Exception {
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(staticRootDirectory, "lc101.txt").toFile());

        assertThat(instance.getNumMaxVehicles()).isEqualTo(25);
        assertThat(instance.getVehicleCapacity()).isEqualTo(200.0);
        assertThat(instance.getNumNodes()).isEqualTo(107);
        assertThat(instance.getNumReq()).isEqualTo(53);
        assertThat(instance.getRequests().length).isEqualTo(106);

        Request temp;
        Request req103 = instance.getRequest(103);
        Request req105 = instance.getRequest(105);
        Request req99 = instance.getRequest(99);
        Request req55 = instance.getRequest(55);
        Request req19 = instance.getRequest(19);

        // Based on this request, we can see that are points where pickup and delivery is in the same place.
        assertThat(req103.requestId).isEqualTo(11);
        assertThat(req103.demand).isEqualTo(-10);
        assertThat(req103.isDeliver).isTrue();
        assertThat(req103.isPickup).isFalse();
        assertThat(req103.serviceTime).isEqualTo(90);
        assertThat(req103.twStart).isEqualTo(732);
        assertThat(req103.twEnd).isEqualTo(777);
        assertThat(req103.nodeId).isEqualTo(103);
        assertThat(req103.x).isEqualTo(28);
        assertThat(req103.y).isEqualTo(55);
        temp = instance.getPickups(11).get(0);
        assertThat(temp.requestId).isEqualTo(11);
        assertThat(temp.demand).isEqualTo(10);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(0);
        assertThat(temp.twStart).isEqualTo(732);
        assertThat(temp.twEnd).isEqualTo(777);
        assertThat(temp.nodeId).isEqualTo(23);
        assertThat(temp.x).isEqualTo(28);
        assertThat(temp.y).isEqualTo(55);

        assertThat(req105.requestId).isEqualTo(19);
        assertThat(req105.demand).isEqualTo(-10);
        assertThat(req105.isDeliver).isTrue();
        assertThat(req105.isPickup).isFalse();
        assertThat(req105.serviceTime).isEqualTo(90);
        assertThat(req105.twStart).isEqualTo(665);
        assertThat(req105.twEnd).isEqualTo(716);
        assertThat(req105.nodeId).isEqualTo(105);
        assertThat(req105.x).isEqualTo(5);
        assertThat(req105.y).isEqualTo(45);
        temp = instance.getPickups(19).get(0);
        assertThat(temp.requestId).isEqualTo(19);
        assertThat(temp.demand).isEqualTo(10);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(0);
        assertThat(temp.twStart).isEqualTo(665);
        assertThat(temp.twEnd).isEqualTo(716);
        assertThat(temp.nodeId).isEqualTo(36);
        assertThat(temp.x).isEqualTo(5);
        assertThat(temp.y).isEqualTo(45);

        assertThat(req99.requestId).isEqualTo(52);
        assertThat(req99.demand).isEqualTo(-20);
        assertThat(req99.isDeliver).isTrue();
        assertThat(req99.isPickup).isFalse();
        assertThat(req99.serviceTime).isEqualTo(90);
        assertThat(req99.twStart).isEqualTo(743);
        assertThat(req99.twEnd).isEqualTo(820);
        assertThat(req99.nodeId).isEqualTo(99);
        assertThat(req99.x).isEqualTo(55);
        assertThat(req99.y).isEqualTo(80);
        temp = instance.getPickups(52).get(0);
        assertThat(temp.requestId).isEqualTo(52);
        assertThat(temp.demand).isEqualTo(20);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(647);
        assertThat(temp.twEnd).isEqualTo(726);
        assertThat(temp.nodeId).isEqualTo(100);
        assertThat(temp.x).isEqualTo(55);
        assertThat(temp.y).isEqualTo(85);

        assertThat(req55.requestId).isEqualTo(31);
        assertThat(req55.demand).isEqualTo(-40);
        assertThat(req55.isDeliver).isTrue();
        assertThat(req55.isPickup).isFalse();
        assertThat(req55.serviceTime).isEqualTo(90);
        assertThat(req55.twStart).isEqualTo(95);
        assertThat(req55.twEnd).isEqualTo(158);
        assertThat(req55.nodeId).isEqualTo(55);
        assertThat(req55.x).isEqualTo(42);
        assertThat(req55.y).isEqualTo(15);
        temp = instance.getPickups(31).get(0);
        assertThat(temp.requestId).isEqualTo(31);
        assertThat(temp.demand).isEqualTo(40);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(35);
        assertThat(temp.twEnd).isEqualTo(87);
        assertThat(temp.nodeId).isEqualTo(57);
        assertThat(temp.x).isEqualTo(40);
        assertThat(temp.y).isEqualTo(15);

        assertThat(req19.requestId).isEqualTo(9);
        assertThat(req19.demand).isEqualTo(10);
        assertThat(req19.isDeliver).isFalse();
        assertThat(req19.isPickup).isTrue();
        assertThat(req19.serviceTime).isEqualTo(90);
        assertThat(req19.twStart).isEqualTo(278);
        assertThat(req19.twEnd).isEqualTo(345);
        assertThat(req19.nodeId).isEqualTo(19);
        assertThat(req19.x).isEqualTo(15);
        assertThat(req19.y).isEqualTo(80);
        temp = instance.getDelivery(9);
        assertThat(temp.requestId).isEqualTo(9);
        assertThat(temp.demand).isEqualTo(-10);
        assertThat(temp.isDeliver).isTrue();
        assertThat(temp.isPickup).isFalse();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(384);
        assertThat(temp.twEnd).isEqualTo(429);
        assertThat(temp.nodeId).isEqualTo(15);
        assertThat(temp.x).isEqualTo(20);
        assertThat(temp.y).isEqualTo(80);

        assertThat(instance.dist(103, 105)).isEqualTo(25.079872407968907);
        assertThat(instance.dist(103, 99)).isEqualTo(36.796738985948195);
        assertThat(instance.dist(103, 55)).isEqualTo(42.37924020083418);
        assertThat(instance.dist(103, 19)).isEqualTo(28.178005607210743);
        assertThat(instance.dist(105, 103)).isEqualTo(25.079872407968907);
        assertThat(instance.dist(105, 99)).isEqualTo(61.032778078668514);
        assertThat(instance.dist(105, 55)).isEqualTo(47.634021455258214);
        assertThat(instance.dist(105, 19)).isEqualTo(36.40054944640259);
        assertThat(instance.dist(99, 103)).isEqualTo(36.796738985948195);
        assertThat(instance.dist(99, 105)).isEqualTo(61.032778078668514);
        assertThat(instance.dist(99, 55)).isEqualTo(66.2872536767062);
        assertThat(instance.dist(99, 19)).isEqualTo(40.0);
        assertThat(instance.dist(55, 103)).isEqualTo(42.37924020083418);
        assertThat(instance.dist(55, 105)).isEqualTo(47.634021455258214);
        assertThat(instance.dist(55, 99)).isEqualTo(66.2872536767062);
        assertThat(instance.dist(55, 19)).isEqualTo(70.3846574190711);
        assertThat(instance.dist(19, 103)).isEqualTo(28.178005607210743);
        assertThat(instance.dist(19, 105)).isEqualTo(36.40054944640259);
        assertThat(instance.dist(19, 99)).isEqualTo(40.0);
        assertThat(instance.dist(19, 55)).isEqualTo(70.3846574190711);
    }

    @Test
    public void readDynamicInstanceTest() throws Exception {
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(dynamicRootDirectory, "lc101_a_0.8.txt").toFile());

        assertThat(instance.getNumMaxVehicles()).isEqualTo(25);
        assertThat(instance.getVehicleCapacity()).isEqualTo(200.0);
        assertThat(instance.getNumNodes()).isEqualTo(107);
        assertThat(instance.getNumReq()).isEqualTo(53);
        assertThat(instance.getRequests().length).isEqualTo(106);

        Request temp;
        Request req103 = instance.getRequest(103);
        Request req105 = instance.getRequest(105);
        Request req99 = instance.getRequest(99);
        Request req55 = instance.getRequest(55);
        Request req19 = instance.getRequest(19);

        // Based on this request, we can see that are points where pickup and delivery is in the same place.
        assertThat(req103.requestId).isEqualTo(11);
        assertThat(req103.demand).isEqualTo(-10);
        assertThat(req103.isDeliver).isTrue();
        assertThat(req103.isPickup).isFalse();
        assertThat(req103.serviceTime).isEqualTo(90);
        assertThat(req103.twStart).isEqualTo(732);
        assertThat(req103.twEnd).isEqualTo(777);
        assertThat(req103.nodeId).isEqualTo(103);
        assertThat(req103.x).isEqualTo(28);
        assertThat(req103.y).isEqualTo(55);
        assertThat(req103.announceTime).isEqualTo(611);
        temp = instance.getPickups(11).get(0);
        assertThat(temp.requestId).isEqualTo(11);
        assertThat(temp.demand).isEqualTo(10);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(0);
        assertThat(temp.twStart).isEqualTo(732);
        assertThat(temp.twEnd).isEqualTo(777);
        assertThat(temp.nodeId).isEqualTo(23);
        assertThat(temp.x).isEqualTo(28);
        assertThat(temp.y).isEqualTo(55);
        assertThat(temp.announceTime).isEqualTo(611);

        assertThat(req105.requestId).isEqualTo(19);
        assertThat(req105.demand).isEqualTo(-10);
        assertThat(req105.isDeliver).isTrue();
        assertThat(req105.isPickup).isFalse();
        assertThat(req105.serviceTime).isEqualTo(90);
        assertThat(req105.twStart).isEqualTo(665);
        assertThat(req105.twEnd).isEqualTo(716);
        assertThat(req105.nodeId).isEqualTo(105);
        assertThat(req105.x).isEqualTo(5);
        assertThat(req105.y).isEqualTo(45);
        assertThat(req105.announceTime).isEqualTo(544);
        temp = instance.getPickups(19).get(0);
        assertThat(temp.requestId).isEqualTo(19);
        assertThat(temp.demand).isEqualTo(10);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(0);
        assertThat(temp.twStart).isEqualTo(665);
        assertThat(temp.twEnd).isEqualTo(716);
        assertThat(temp.nodeId).isEqualTo(36);
        assertThat(temp.x).isEqualTo(5);
        assertThat(temp.y).isEqualTo(45);
        assertThat(temp.announceTime).isEqualTo(544);

        assertThat(req99.requestId).isEqualTo(52);
        assertThat(req99.demand).isEqualTo(-20);
        assertThat(req99.isDeliver).isTrue();
        assertThat(req99.isPickup).isFalse();
        assertThat(req99.serviceTime).isEqualTo(90);
        assertThat(req99.twStart).isEqualTo(743);
        assertThat(req99.twEnd).isEqualTo(820);
        assertThat(req99.nodeId).isEqualTo(99);
        assertThat(req99.x).isEqualTo(55);
        assertThat(req99.y).isEqualTo(80);
        assertThat(req99.announceTime).isEqualTo(549);
        temp = instance.getPickups(52).get(0);
        assertThat(temp.requestId).isEqualTo(52);
        assertThat(temp.demand).isEqualTo(20);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(647);
        assertThat(temp.twEnd).isEqualTo(726);
        assertThat(temp.nodeId).isEqualTo(100);
        assertThat(temp.x).isEqualTo(55);
        assertThat(temp.y).isEqualTo(85);
        assertThat(temp.announceTime).isEqualTo(549);

        assertThat(req55.requestId).isEqualTo(31);
        assertThat(req55.demand).isEqualTo(-40);
        assertThat(req55.isDeliver).isTrue();
        assertThat(req55.isPickup).isFalse();
        assertThat(req55.serviceTime).isEqualTo(90);
        assertThat(req55.twStart).isEqualTo(95);
        assertThat(req55.twEnd).isEqualTo(158);
        assertThat(req55.nodeId).isEqualTo(55);
        assertThat(req55.x).isEqualTo(42);
        assertThat(req55.y).isEqualTo(15);
        assertThat(req55.announceTime).isEqualTo(24);
        temp = instance.getPickups(31).get(0);
        assertThat(temp.requestId).isEqualTo(31);
        assertThat(temp.demand).isEqualTo(40);
        assertThat(temp.isDeliver).isFalse();
        assertThat(temp.isPickup).isTrue();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(35);
        assertThat(temp.twEnd).isEqualTo(87);
        assertThat(temp.nodeId).isEqualTo(57);
        assertThat(temp.x).isEqualTo(40);
        assertThat(temp.y).isEqualTo(15);
        assertThat(temp.announceTime).isEqualTo(24);

        assertThat(req19.requestId).isEqualTo(9);
        assertThat(req19.demand).isEqualTo(10);
        assertThat(req19.isDeliver).isFalse();
        assertThat(req19.isPickup).isTrue();
        assertThat(req19.serviceTime).isEqualTo(90);
        assertThat(req19.twStart).isEqualTo(278);
        assertThat(req19.twEnd).isEqualTo(345);
        assertThat(req19.nodeId).isEqualTo(19);
        assertThat(req19.x).isEqualTo(15);
        assertThat(req19.y).isEqualTo(80);
        assertThat(req19.announceTime).isEqualTo(235);
        temp = instance.getDelivery(9);
        assertThat(temp.requestId).isEqualTo(9);
        assertThat(temp.demand).isEqualTo(-10);
        assertThat(temp.isDeliver).isTrue();
        assertThat(temp.isPickup).isFalse();
        assertThat(temp.serviceTime).isEqualTo(90);
        assertThat(temp.twStart).isEqualTo(384);
        assertThat(temp.twEnd).isEqualTo(429);
        assertThat(temp.nodeId).isEqualTo(15);
        assertThat(temp.x).isEqualTo(20);
        assertThat(temp.y).isEqualTo(80);
        assertThat(temp.announceTime).isEqualTo(235);

        assertThat(instance.dist(103, 105)).isEqualTo(25.079872407968907);
        assertThat(instance.dist(103, 99)).isEqualTo(36.796738985948195);
        assertThat(instance.dist(103, 55)).isEqualTo(42.37924020083418);
        assertThat(instance.dist(103, 19)).isEqualTo(28.178005607210743);
        assertThat(instance.dist(105, 103)).isEqualTo(25.079872407968907);
        assertThat(instance.dist(105, 99)).isEqualTo(61.032778078668514);
        assertThat(instance.dist(105, 55)).isEqualTo(47.634021455258214);
        assertThat(instance.dist(105, 19)).isEqualTo(36.40054944640259);
        assertThat(instance.dist(99, 103)).isEqualTo(36.796738985948195);
        assertThat(instance.dist(99, 105)).isEqualTo(61.032778078668514);
        assertThat(instance.dist(99, 55)).isEqualTo(66.2872536767062);
        assertThat(instance.dist(99, 19)).isEqualTo(40.0);
        assertThat(instance.dist(55, 103)).isEqualTo(42.37924020083418);
        assertThat(instance.dist(55, 105)).isEqualTo(47.634021455258214);
        assertThat(instance.dist(55, 99)).isEqualTo(66.2872536767062);
        assertThat(instance.dist(55, 19)).isEqualTo(70.3846574190711);
        assertThat(instance.dist(19, 103)).isEqualTo(28.178005607210743);
        assertThat(instance.dist(19, 105)).isEqualTo(36.40054944640259);
        assertThat(instance.dist(19, 99)).isEqualTo(40.0);
        assertThat(instance.dist(19, 55)).isEqualTo(70.3846574190711);
    }

    @Test
    @Ignore
    public void instanceGeneratorTest() throws Exception {
        String problem = "pdp_1000";
        String path = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource(problem).getFile().substring(1)).toString();;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            ProblemInstance problemInstance = DataReader.getPdptwInstance(Paths.get(path, file.getName()).toFile());
            DynamicInstancesGenerator.generateDynamicInstances(problemInstance);
        }
    }

}
