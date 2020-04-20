package com.github.schmittjoaopedro.vrp.ortools;

import com.google.ortools.constraintsolver.Assignment;
import com.google.ortools.constraintsolver.FirstSolutionStrategy;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.constraintsolver.main;

import java.util.logging.Logger;


/**
 * Minimal TSP using distance matrix.
 */
public class TSP {

    static {
        System.loadLibrary("jniortools");
    }

    private static final Logger logger = Logger.getLogger(TSP.class.getName());

    static class DataModel {
        public final long[][] distanceMatrix = {
                {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
                {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
                {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
                {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
                {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
                {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
                {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
                {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
                {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
                {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
                {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
                {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
                {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},
        };
        public final int vehicleNumber = 1;
        public final int depot = 0;
    }

    /// @brief Print the solution.
    static void printSolution(RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        // Solution cost.
        logger.info("Objective: " + solution.objectiveValue() + "miles");
        // Inspect solution.
        logger.info("Route:");
        long routeDistance = 0;
        String route = "";
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            route += manager.indexToNode(index) + " -> ";
            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += manager.indexToNode(routing.end(0));
        logger.info(route);
        logger.info("Route distance: " + routeDistance + "miles");
    }

    public static void main(String[] args) throws Exception {
        // Instantiate the data problem.
        final DataModel data = new DataModel();

        // Create Routing Index Manager
        RoutingIndexManager manager =
                new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        // Create Routing Model.
        RoutingModel routing = new RoutingModel(manager);

        // Create and register a transit callback.
        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    // Convert from routing variable Index to user NodeIndex.
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.distanceMatrix[fromNode][toNode];
                });

        // Define cost of each arc.
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Setting first solution heuristic.
        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        // Solve the problem.
        Assignment solution = routing.solveWithParameters(searchParameters);

        // Print solution on console.
        printSolution(routing, manager, solution);
    }
}