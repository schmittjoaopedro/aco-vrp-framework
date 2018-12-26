package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class Defaults {

    public static void executeNewParcelInsertion(Ants ants, VRPTW_ACS vrptwAcs, DynamicController dynamicController,
                                                 VRPTW vrptw, ArrayList<Integer> unroutedList, LoggerOutput loggerOutput,
                                                 InsertionHeuristic insertionHeuristic) {
        //skip over committed (defined) nodes when performing insertion heuristic
        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        int lastPos;
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = dynamicController.getLastCommittedPos(ants.bestSoFarAnt, index);
            lastCommittedIndexes.add(lastPos);
        }
        insertionHeuristic.insertUnroutedCustomers(ants.bestSoFarAnt, vrptw, unroutedList, 0, lastCommittedIndexes);
        //if there are still remaining unvisited cities from the ones that are available
        //insert an empty tour and add cities in it following nearest-neighbour heuristic
        int indexTour;
        while (ants.bestSoFarAnt.toVisit > 0) {
            ants.bestSoFarAnt.usedVehicles++;
            indexTour = ants.bestSoFarAnt.usedVehicles - 1;
            ants.bestSoFarAnt.tours.add(indexTour, new ArrayList<Integer>());
            ants.bestSoFarAnt.tours.get(indexTour).add(-1);
            ants.bestSoFarAnt.tourLengths.add(indexTour, 0.0);
            ants.bestSoFarAnt.currentQuantity.add(indexTour, 0.0);
            ants.bestSoFarAnt.currentTime.add(indexTour, 0.0);
            //try to add as many unvisited cities/nodes as possible in this newly created tour
            //following the nearest neighbour heuristic
            ants.chooseClosestNn(ants.bestSoFarAnt, indexTour, vrptw, vrptwAcs);
            //try to insert remaining cities using insertion heuristic
            if (ants.bestSoFarAnt.toVisit > 0) {
                //determine nodes that are not visited yet in the current ant's solution
                unroutedList = ants.getNonRoutedCustomers(ants.bestSoFarAnt, vrptw.getIdAvailableRequests());
                //skip over committed (defined) nodes when performing insertion heuristic
                lastCommittedIndexes = new ArrayList<>();
                for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
                    lastPos = dynamicController.getLastCommittedPos(ants.bestSoFarAnt, index);
                    lastCommittedIndexes.add(lastPos);
                }
                insertionHeuristic.insertUnroutedCustomers(ants.bestSoFarAnt, vrptw, unroutedList, indexTour, lastCommittedIndexes);
            }
            //add the depot again to end this tour
            ants.bestSoFarAnt.tours.get(indexTour).add(-1);
        }
        double sum = 0.0;
        for (int i = 0; i < ants.bestSoFarAnt.usedVehicles; i++) {
            ants.bestSoFarAnt.tourLengths.set(i, vrptw.computeTourLengthWithDepot(ants.bestSoFarAnt.tours.get(i)));
            sum += ants.bestSoFarAnt.tourLengths.get(i);
        }
        ants.bestSoFarAnt.totalTourLength = sum;
        double scalledValue = 0.0;
        if (dynamicController.scalingValue != 0) {
            scalledValue = ants.bestSoFarAnt.totalTourLength / dynamicController.scalingValue;
        }
        loggerOutput.log("Best ant after inserting the new available nodes>> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
    }

    public static void finishLogs(DynamicController dynamicController, LoggerOutput loggerOutput, Ants ants, VRPTW_ACS vrptwAcs, Statistics statistics, VRPTW vrptw) {
        //end of the working day; try final improvements of the best so far solution
        //by applying iterated relocate multiple route and exchange multiple route local search operators
        double scalledValue = 0.0;
        if (dynamicController.scalingValue != 0) {
            scalledValue = ants.bestSoFarAnt.totalTourLength / dynamicController.scalingValue;
        }
        loggerOutput.log("Final best solution >> No. of used vehicles=" + ants.bestSoFarAnt.usedVehicles + " total tours length=" + ants.bestSoFarAnt.totalTourLength + " (scalled value = " + scalledValue + ")");
        String finalRouteStr = "";
        for (int i = 0; i < ants.bestSoFarAnt.usedVehicles; i++) {
            int tourLength = ants.bestSoFarAnt.tours.get(i).size();
            finalRouteStr = "";
            for (int j = 0; j < tourLength; j++) {
                int city = ants.bestSoFarAnt.tours.get(i).get(j);
                city = city + 1;  //so as to correspond to the city indexes from the VRPTW input file
                finalRouteStr += (city + " ");
            }
            loggerOutput.log(finalRouteStr);
        }
        loggerOutput.log("Total number of evaluations: " + statistics.noEvaluations);
        loggerOutput.log("Total number of feasible solutions: " + statistics.noSolutions);
        boolean isValid = vrptwAcs.checkFeasibility(ants.bestSoFarAnt, vrptw, true);
        if (isValid) {
            loggerOutput.log("The final solution is valid (feasible)..");
            //utilities.plotResult(vrptw, ants.bestSoFarAnt, 1800, 900);
        } else {
            loggerOutput.log("The final solution is not valid (feasible)..");
        }
    }

    //initialize the program
    public static void initProgram(VRPTW vrptw, DynamicController dynamicController, DistanceType distanceType,
                                   Ants ants, Statistics statistics, LoggerOutput loggerOutput, String antSystem) {
        Defaults.setDefaultParameters(ants, statistics);
        parseCommandline(ants, loggerOutput, antSystem);
        //compute distance matrix between cities and allocate ants
        if (ants.nAnts < 0)
            ants.nAnts = vrptw.n;
        // Adjust distances between nodes (cities) according to this scale value
        vrptw.instance.distance = vrptw.computeDistances(dynamicController.scalingValue, distanceType);
        ants.allocateAnts(vrptw);
        ants.pheromone = new double[vrptw.n + 1][vrptw.n + 1];
        dynamicController.allocateStructures(vrptw);
        //Calculate scale request values
        dynamicController.scaleRequestTimes(vrptw);
        //compute nearest neighborhood lists
        int[][][] result = vrptw.computeNnLists(ants);
        vrptw.instance.nnList = result[0];
        vrptw.instance.nnListAll = result[1];
    }

    public static void parseCommandline(Ants ants, LoggerOutput loggerOutput, String antSystem) {
        // Choice of ONE algorithm
        int algorithmCount = 0;
        if (antSystem.equals("u")) {
            algorithmCount++;
        }
        if (antSystem.equals("z")) {
            algorithmCount++;
        }
        if (algorithmCount > 1) {
            System.err.println("Error: More than one ACO algorithm enabled in the command line.");
            System.exit(1);
        } else if (algorithmCount == 1) {
            ants.asFlag = false;
            ants.acsFlag = false;
        }
        if (antSystem.equals("u")) {
            ants.asFlag = true;
            Defaults.setDefaultAsParameters(ants);
            loggerOutput.log("\nRun basic Ant System #1");
        }
        if (antSystem.equals("z")) {
            ants.acsFlag = true;
            Defaults.setDefaultAcsParameters(ants);
            loggerOutput.log("\nRun Ant Colony System #1");
        }
    }

    //set default parameter settings
    public static void setDefaultParameters(Ants ants, Statistics statistics) {
        /* number of ants and number of nearest neighbors in tour construction */
        ants.nAnts = 25;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
        ants.pheromonePreservation = 0.3;
        statistics.max_tries = 10;
        statistics.max_tours = 10 * 20;
        //10 seconds allowed for running time; it is used in the termination condition of ACO
        statistics.max_time = 100;
        //maximum number of allowed iterations until the ACO algorithm stops
        statistics.max_iterations = 3000;
        statistics.optimal = 1;
        statistics.branch_fac = 1.00001;
        ants.uGb = Integer.MAX_VALUE;
        ants.acsFlag = false;
        ants.asFlag = false;
    }

    public static void setDefaultAsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = -1;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
    }

    public static void setDefaultAcsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = 10;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 1.0;
        ants.rho = 0.9;
        //used in local pheromone update
        ants.local_rho = 0.9;
        ants.q0 = 0.9;
    }

}
