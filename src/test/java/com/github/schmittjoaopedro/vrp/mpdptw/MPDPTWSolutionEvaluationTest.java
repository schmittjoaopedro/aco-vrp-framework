package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTWSolutionEvaluationTest {

    private static final String rootDirectory;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void slackTimesCalculationTest() {

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
        requests[0].requestId = 0;
        requests[0].nodeId = 1;
        requests[0].twStart = 10.0;
        requests[0].twEnd = 14.0;
        requests[0].isPickup = true;
        requests[1] = new Request();
        requests[1].requestId = 0;
        requests[1].nodeId = 2;
        requests[1].twStart = 8.0;
        requests[1].twEnd = 12.0;
        requests[1].isPickup = true;
        requests[2] = new Request();
        requests[2].requestId = 0;
        requests[2].nodeId = 3;
        requests[2].twStart = 13.0;
        requests[2].twEnd = 17.0;
        requests[2].isDeliver = true;
        problemInstance.setRequests(requests);

        problemInstance.setNumNodes(4);
        problemInstance.setNumReq(1);

        List<Request>[] pickups = new ArrayList[1];
        pickups[0] = new ArrayList<>();
        pickups[0].add(requests[0]);
        pickups[0].add(requests[1]);
        Request[] deliveries = new Request[1];
        deliveries[0] = requests[2];
        problemInstance.setPickups(pickups);
        problemInstance.setDelivery(deliveries);

        Solution ant = SolutionUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList(Arrays.asList(0, 1, 2, 3, 0)));
        ant.requests.add(new ArrayList(Arrays.asList(0)));

        problemInstance.solutionEvaluation(ant);
        /*
         Differently from Savelsbergh, were the sample calculation on the paper results in the following slack-times
         [5.0, 1.5, 1.5, 4.0, 4.5], here we result [11.0, 1.5, 1.5, 4.0, 4.5] because the start depot departure time
         value is always 0.0.
         */
        assertThat(ant.departureSlackTimes.get(0)).hasSize(ant.tours.get(0).size());
        assertThat(ant.departureSlackTimes.get(0)).containsExactly(5.0, 1.5, 1.5, 4.0, 4.5);
        assertThat(ant.arrivalSlackTimes.get(0)).containsExactly(5.0, 5.0, 1.5, 6.0, 4.5);
        assertThat(ant.waitingTimes.get(0)).containsExactly(0.0, 3.5, 0.0, 2.0, 0.0);
        assertThat(ant.totalCost).isEqualTo(2.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.capacityPenalty).isEqualTo(0.0);
    }

    @Test
    public void timeWindowsWithServiceTimeFeasibilityTest() throws IOException {
        // Check if the service time of the target point is not considered in the
        // end time windows summation
        ProblemInstance problemInstance = DataReader.getProblemInstance(Paths.get(rootDirectory, "l_4_25_3.txt").toFile());
        Solution ant = SolutionUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 3, 1, 26, 25, 5, 2, 4, 27, 6, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 12, 23, 13, 22, 24, 11, 14, 0)));
        ant.tours.add(new ArrayList<>(Arrays.asList(0, 19, 9, 16, 7, 15, 8, 18, 10, 20, 17, 21, 0)));
        ant.requests.add(new ArrayList<>(Arrays.asList(1, 0, 7)));
        ant.requests.add(new ArrayList<>(Arrays.asList(6, 3)));
        ant.requests.add(new ArrayList<>(Arrays.asList(4, 5, 2)));
        problemInstance.solutionEvaluation(ant);
        assertThat(ant.totalCost).isEqualTo(4713.749202925907);
        assertThat(ant.feasible).isTrue();

        assertThat(ant.tourCosts.get(0)).isEqualTo(1530.3967130440456);
        assertThat(ant.tourCosts.get(1)).isEqualTo(1451.9543342625707);
        assertThat(ant.tourCosts.get(2)).isEqualTo(1731.3981556192914);

        assertThat(ant.arrivalTime.get(0)).containsExactly(
                0.0,
                174.6568063374571,
                296.6032220907093,
                343.2774637324938,
                739.3665696657308,
                926.0753696729744,
                955.4862520126799,
                1101.2875774848562,
                1173.657442002907,
                1415.2972485992689,
                1647.1192493115518);
        assertThat(ant.arrivalTime.get(1)).containsExactly(
                0.0,
                169.9941175452845,
                556.9556540446997,
                753.613094292872,
                1060.3436117662186,
                1250.1490621841633,
                1440.3086968966181,
                1511.3086968966181,
                1557.8052720784074);
        assertThat(ant.arrivalTime.get(2)).containsExactly(
                0.0,
                75.8023746329889,
                108.1630544079868,
                392.53506464226706,
                501.7822308486631,
                644.0130895768738,
                724.7873786937557,
                805.3068795428117,
                915.9696830006927,
                1269.611938842433,
                1511.2517454387948,
                1620.0950557840074,
                1841.3981556192914);

        assertThat(ant.departureTime.get(0)).containsExactly(
                0.0,
                184.6568063374571,
                306.6032220907093,
                390.0,
                749.3665696657308,
                926.0753696729744,
                965.4862520126799,
                1111.2875774848562,
                1183.657442002907,
                1425.2972485992689,
                1647.1192493115518);
        assertThat(ant.departureTime.get(1)).containsExactly(
                0.0,
                179.9941175452845,
                566.9556540446997,
                763.613094292872,
                1070.3436117662186,
                1296.0,
                1450.3086968966181,
                1521.3086968966181,
                1557.8052720784074);
        assertThat(ant.departureTime.get(2)).containsExactly(
                0.0,
                85.8023746329889,
                118.1630544079868,
                402.53506464226706,
                511.7822308486631,
                654.0130895768738,
                734.7873786937557,
                815.3068795428117,
                925.9696830006927,
                1279.611938842433,
                1521.2517454387948,
                1630.0950557840074,
                1841.3981556192914);

        assertThat(ant.waitingTimes.get(0)).containsExactly(0.0, 0.0, 0.0, 36.7225362675062, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertThat(ant.waitingTimes.get(1)).containsExactly(0.0, 0.0, 0.0, 0.0, 0.0, 35.85093781583669, 0.0, 0.0, 0.0);
        assertThat(ant.waitingTimes.get(2)).containsExactly(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        assertThat(ant.delays.get(0)).containsExactly(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertThat(ant.delays.get(1)).containsExactly(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assertThat(ant.delays.get(2)).containsExactly(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        assertThat(ant.departureSlackTimes.get(0)).containsExactly(
                43.43495878264997,
                43.43495878264997,
                43.43495878264997,
                6.712422515143771,
                6.712422515143771,
                6.712422515143771,
                6.712422515143771,
                6.712422515143771,
                277.8807506884482,
                277.8807506884482,
                277.8807506884482);
        assertThat(ant.departureSlackTimes.get(1)).containsExactly(
                122.04434595530029,
                122.04434595530029,
                122.04434595530029,
                128.54224091921856,
                128.54224091921856,
                92.69130310338187,
                92.69130310338187,
                92.69130310338187,
                367.19472792159263);
        assertThat(ant.departureSlackTimes.get(2)).containsExactly(
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                83.6018443807086,
                83.6018443807086);

        assertThat(ant.arrivalSlackTimes.get(0)).containsExactly(
                43.43495878264997,
                43.43495878264997,
                43.43495878264997,
                43.43495878264997,
                6.712422515143771,
                6.712422515143771,
                6.712422515143771,
                6.712422515143771,
                277.8807506884482,
                277.8807506884482,
                277.8807506884482);
        assertThat(ant.arrivalSlackTimes.get(1)).containsExactly(
                122.04434595530029,
                122.04434595530029,
                122.04434595530029,
                128.54224091921856,
                128.54224091921856,
                128.54224091921856,
                92.69130310338187,
                92.69130310338187,
                367.19472792159263);
        assertThat(ant.arrivalSlackTimes.get(2)).containsExactly(
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                21.748254561205158,
                83.6018443807086,
                83.6018443807086);

        assertThat(ant.capacity).containsExactly(0.0, 50.0, 85.0, 15.0, 102.0, 120.0, 0.0, 50.0,
                76.0, 33.0, 61.0, 51.0, 23.0, 74.0, 0.0, 62.0, 45.0, 61.0, 90.0, 23.0,
                85.0, 0.0, 81.0, 50.0, 47.0, 91.0, 66.0, 61.0);

        assertThat(ant.capacityPenalty).isZero();
        assertThat(ant.timeWindowPenalty).isZero();
    }

}
