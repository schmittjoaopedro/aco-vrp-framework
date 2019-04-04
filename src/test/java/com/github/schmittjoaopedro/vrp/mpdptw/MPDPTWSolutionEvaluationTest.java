package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

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

        Ant ant = AntUtils.createEmptyAnt(problemInstance);
        ant.tours.add(new ArrayList(Arrays.asList(0, 1, 2, 3, 0)));
        ant.requests.add(new ArrayList(Arrays.asList(0)));

        problemInstance.solutionEvaluation(ant);
        /*
         Differently from Savelsbergh, were the sample calculation on the paper results in the following slack-times
         [5.0, 1.5, 1.5, 4.0, 4.5], here we result [4.5, 1.5, 1.5, 4.0] because the end depot delivery is allocated in the
         first position of the array.
         It means that, we disregard the slack time of the depot departure time in the first position. This is
         OK as we are evaluation solution cost in terms of distance and not total travel time.
         */
        assertThat(ant.slackTimes).hasSize(ant.tours.get(0).size() - 1);
        assertThat(ant.slackTimes).containsExactly(4.5, 1.5, 1.5, 4.0);
        assertThat(ant.totalCost).isEqualTo(2.0);
        assertThat(ant.feasible).isTrue();
        assertThat(ant.timeWindowPenalty).isEqualTo(0.0);
        assertThat(ant.capacityPenalty).isEqualTo(0.0);
    }

}
