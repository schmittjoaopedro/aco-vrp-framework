package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class LocalSearch {

    private LoggerOutput loggerOutput;

    public LocalSearch(LoggerOutput loggerOutput) {
        this.loggerOutput = loggerOutput;
    }

    public boolean checkFeasibleTourRelocationMultiple(Ant a, VRPTW vrptw, int indexTourSource, int indexTourDestination, int i, int j) {
        int city, previousCity, prevCity, nextCity, currentCity;
        double currentQuantity, arrivalTime, currentTime = 0.0, beginService, distance;
        ArrayList<Request> reqList = vrptw.getRequests();
        city = a.tours.get(indexTourSource).get(i);
        currentQuantity = a.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
        if (currentQuantity > vrptw.getCapacity()) {
            return false;
        }
        //check time window constraints in source tour
        for (int pos = i + 1; pos < a.tours.get(indexTourSource).size(); pos++) {
            if (pos == (i + 1)) {
                prevCity = a.tours.get(indexTourSource).get(pos - 2);
                currentCity = a.tours.get(indexTourSource).get(pos);
                currentTime = a.beginService[prevCity + 1];
            } else {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(pos);
            }
            distance = vrptw.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        previousCity = a.tours.get(indexTourDestination).get(j - 1);
        nextCity = a.tours.get(indexTourDestination).get(j);
        arrivalTime = a.beginService[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + vrptw.instance.distance[previousCity + 1][city + 1];
        beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
        if (beginService > reqList.get(city + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;
        arrivalTime = currentTime + reqList.get(city + 1).getServiceTime() + vrptw.instance.distance[city + 1][nextCity + 1];
        beginService = Math.max(arrivalTime, reqList.get(nextCity + 1).getStartWindow());
        if (beginService > reqList.get(nextCity + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;
        //check time window constraints in destination tour
        for (int pos = j + 1; pos < a.tours.get(indexTourDestination).size(); pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            distance = vrptw.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        return true;
    }

    public void updateBeginServiceRelocationMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        ArrayList<Request> reqList = vrp.getRequests();
        //update of begin service times for the source tour
        for (int pos = i; pos < a.tours.get(indexTourSource).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourSource).get(pos - 1);
            currentCity = a.tours.get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Relocation Multiple indexTourSource: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourSource, beginService);
        //update of begin service times for the destination tour
        for (int pos = j; pos < a.tours.get(indexTourDestination).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Relocation Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourDestination, beginService);
    }

    //skip committed (defined) nodes
    //relocateMultipleRoute is performed only once and the best improvement (which minimizes most the
    //total traveled distance) is applied
    public Ant relocateMultipleRoute(Ants ants, Controller controller, Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        int lastPos;
        Ant bestImprovedAnt = new Ant();
        bestImprovedAnt.tours = new ArrayList();
        bestImprovedAnt.tourLengths = new ArrayList<>();
        bestImprovedAnt.beginService = new double[vrptw.n + 1];
        bestImprovedAnt.currentTime = new ArrayList<>();
        bestImprovedAnt.currentQuantity = new ArrayList<>();
        bestImprovedAnt.usedVehicles = 1;
        for (int j = 0; j < bestImprovedAnt.usedVehicles; j++) {
            bestImprovedAnt.tours.add(j, new ArrayList<>());
            bestImprovedAnt.tourLengths.add(j, 0.0);
        }
        bestImprovedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        bestImprovedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        bestImprovedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            bestImprovedAnt.costObjectives[indexObj] = 0;
        }
        bestImprovedAnt.earliestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        bestImprovedAnt.latestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tourLengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tourLengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);

        ants.copy_from_to(a, bestImprovedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);

        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        for (int indexTourSource = 0; indexTourSource < a.usedVehicles; indexTourSource++) {
            for (int indexTourDestination = 0; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved/relocated
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the relocation's destination
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size(); j++) {
                            //check if results a feasible solution (i.e. no time window constraint is violated)
                            feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                            if (feasible) {
                                //obtain the neighbour solution corresponding to the relocation operator
                                city = temp.tours.get(indexTourSource).get(i);
                                temp.tours.get(indexTourSource).remove(i);
                                temp.tours.get(indexTourDestination).add(j, city);
                                newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourSource, newQuantity1);
                                newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                //also update the current time of the source and destination tours
                                updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                //update total traveled distance and lengths of source and destination tours
                                sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                newDistance1 = temp.tourLengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                // new distance 2
                                destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                newDistance2 = temp.tourLengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                // total distance
                                newTotalDistance = temp.totalTourLength - temp.tourLengths.get(indexTourSource) - temp.tourLengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                temp.totalTourLength = newTotalDistance;
                                temp.tourLengths.set(indexTourSource, newDistance1);
                                temp.tourLengths.set(indexTourDestination, newDistance2);
                                //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                    temp.tours.remove(indexTourSource);
                                    temp.usedVehicles--;
                                }
                                //if some improvement is obtained in the total traveled distance
                                if ((temp.totalTourLength < bestImprovedAnt.totalTourLength)
                                        || (temp.usedVehicles < bestImprovedAnt.usedVehicles)) {
                                    ants.copy_from_to(temp, bestImprovedAnt, vrptw);
                                }
                                //restore previous solution constructed by ant
                                ants.copy_from_to(a, temp, vrptw);
                            }
                        }
                    }
                }
            }
        }
        return bestImprovedAnt;
    }


    //skip committed (defined) nodes
    //relocateMultipleRouteIterated is performed multiple times until no further improvement (which minimizes most the
    //total traveled distance or reduces the number of used vehicles) is possible
    public Ant relocateMultipleRouteIterated(Ants ants, Controller controller, Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tourLengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tourLengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tourLengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tourLengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                loggerOutput.log("Inside relocateMultipleRouteIterated; count=" + count);
            }
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(improvedAnt, temp, vrptw);
            for (int indexTourSource = 0; indexTourSource < temp.usedVehicles; indexTourSource++) {
                for (int indexTourDestination = 0; indexTourDestination < temp.usedVehicles; indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        //index of the element to be moved/relocated
                        if (indexTourSource > lastCommitedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                        }
                        //index of the relocation's destination
                        if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.tours.get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.tours.get(indexTourDestination).size(); j++) {
                                //check if results a feasible solution (i.e. no time window constraint is violated)
                                feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                if (feasible) {
                                    //obtain the neighbour solution corresponding to the relocation operator
                                    city = temp.tours.get(indexTourSource).get(i);
                                    temp.tours.get(indexTourSource).remove(i);
                                    temp.tours.get(indexTourDestination).add(j, city);
                                    newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                    temp.currentQuantity.set(indexTourSource, newQuantity1);
                                    newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                    temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                    //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                    //also update the current time of the source and destination tours
                                    updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    //update total traveled distance and lengths of source and destination tours
                                    sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                    sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                    newDistance1 = temp.tourLengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                    // new distance 2
                                    destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                    destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                    newDistance2 = temp.tourLengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                    // new total distance
                                    newTotalDistance = temp.totalTourLength - temp.tourLengths.get(indexTourSource) - temp.tourLengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                    temp.totalTourLength = newTotalDistance;
                                    temp.tourLengths.set(indexTourSource, newDistance1);
                                    temp.tourLengths.set(indexTourDestination, newDistance2);
                                    //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                    if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                        temp.tours.remove(indexTourSource);
                                        temp.tourLengths.remove(indexTourSource);
                                        temp.currentQuantity.remove(indexTourSource);
                                        temp.currentTime.remove(indexTourSource);
                                        temp.usedVehicles--;
                                    }
                                    //performing the rounding of the two numbers up to 10 decimals so that in the
                                    //comparison of the 2 double values to consider only the first 10 most significant decimals
                                    round1 = Math.round(temp.totalTourLength * tempNo) / tempNo;
                                    round2 = Math.round(improvedAnt.totalTourLength * tempNo) / tempNo;
                                    //if some improvement is obtained in the total traveled distance
                                    if (((round1 < round2) && (temp.usedVehicles == improvedAnt.usedVehicles))
                                            || (temp.usedVehicles < improvedAnt.usedVehicles)) {
                                        ants.copy_from_to(temp, improvedAnt, vrptw);
                                        foundImprovement = true;
                                    }
                                    //restore previous solution constructed by ant
                                    ants.copy_from_to(a, temp, vrptw);
                                }
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }


    //skip committed (defined) nodes
    //relocateMultipleRouteIterated is performed multiple times until no further improvement (which minimizes most the
    //total traveled distance or reduces the number of used vehicles) is possible
    //try to relocate nodes from the tour having the minimum number of visited nodes, in an attempt
    //to reduce (minimize) the number of tours (i.e. used vehicles)
    public Ant relocateMultipleRouteIteratedShortest(Ants ants, Controller controller, Ant a, VRPTW vrptw) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int indexTourSource, lastPos;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tourLengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tourLengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tourLengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<Integer>());
            temp.tourLengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<Integer>();
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        while (foundImprovement) {
            foundImprovement = false;
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(a, temp, vrptw);
            indexTourSource = findShortestTour(a);
            for (int indexTourDestination = 0; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved/relocated
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the relocation's destination
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size(); j++) {
                            //check if results a feasible solution (i.e. no time window constraint is violated)
                            feasible = checkFeasibleTourRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                            if (feasible) {
                                //obtain the neighbour solution corresponding to the relocation operator
                                city = temp.tours.get(indexTourSource).get(i);
                                temp.tours.get(indexTourSource).remove(i);
                                temp.tours.get(indexTourDestination).add(j, city);
                                newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourSource, newQuantity1);
                                newQuantity2 = temp.currentQuantity.get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                //also update the current time of the source and destination tours
                                updateBeginServiceRelocationMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                //update total traveled distance and lengths of source and destination tours
                                sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                sourceNextCity = temp.tours.get(indexTourSource).get(i);
                                newDistance1 = temp.tourLengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city + 1] - vrptw.instance.distance[city + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][sourceNextCity + 1];
                                // new distance 2
                                destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                newDistance2 = temp.tourLengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city + 1] + vrptw.instance.distance[city + 1][destinationNextCity + 1];
                                // new total distance
                                newTotalDistance = temp.totalTourLength - temp.tourLengths.get(indexTourSource) - temp.tourLengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                temp.totalTourLength = newTotalDistance;
                                temp.tourLengths.set(indexTourSource, newDistance1);
                                temp.tourLengths.set(indexTourDestination, newDistance2);
                                //if the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                if (temp.tours.get(indexTourSource).size() == 2 && temp.tours.get(indexTourSource).get(0) == -1 && temp.tours.get(indexTourSource).get(1) == -1) {
                                    temp.tours.remove(indexTourSource);
                                    temp.usedVehicles--;
                                }
                                //if some improvement is obtained in the total traveled distance
                                if (((temp.totalTourLength < improvedAnt.totalTourLength) && (temp.usedVehicles == improvedAnt.usedVehicles))
                                        || (temp.usedVehicles < improvedAnt.usedVehicles)) {
                                    ants.copy_from_to(temp, improvedAnt, vrptw);
                                    foundImprovement = true;
                                }
                                //restore previous solution constructed by ant
                                ants.copy_from_to(a, temp, vrptw);
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }

    public boolean checkFeasibleTourExchangeMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity, city1, city2;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService, currentQuantity;
        ArrayList<Request> reqList = vrp.getRequests();
        //check vehicle capacity tour constraints for source and destination tours
        city1 = a.tours.get(indexTourSource).get(i);
        city2 = a.tours.get(indexTourDestination).get(j);
        currentQuantity = a.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
        if (currentQuantity > vrp.getCapacity()) {
            return false;
        }
        currentQuantity = a.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
        if (currentQuantity > vrp.getCapacity()) {
            return false;
        }
        //check feasibility for source tour regarding time windows constraints
        for (int pos = i; pos < a.tours.get(indexTourSource).size(); pos++) {
            if (pos == i) {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourDestination).get(j);
                currentTime = a.beginService[prevCity + 1];
            } else if (pos == (i + 1)) {
                prevCity = a.tours.get(indexTourDestination).get(j);
                currentCity = a.tours.get(indexTourSource).get(pos);
            } else {
                prevCity = a.tours.get(indexTourSource).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(pos);
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        //check feasibility for destination tour regarding time windows constraints
        for (int pos = j; pos < a.tours.get(indexTourDestination).size(); pos++) {
            if (pos == j) {
                prevCity = a.tours.get(indexTourDestination).get(pos - 1);
                currentCity = a.tours.get(indexTourSource).get(i);
                currentTime = a.beginService[prevCity + 1];
            } else if (pos == (j + 1)) {
                prevCity = a.tours.get(indexTourSource).get(i);
                currentCity = a.tours.get(indexTourDestination).get(pos);
            } else {
                prevCity = a.tours.get(indexTourDestination).get(pos - 1);
                currentCity = a.tours.get(indexTourDestination).get(pos);
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        return true;
    }


    public void updateBeginServiceExchangeMultiple(Ant a, VRPTW vrp, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        ArrayList<Request> reqList = vrp.getRequests();
        for (int pos = i; pos < a.tours.get(indexTourSource).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourSource).get(pos - 1);
            currentCity = a.tours.get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Exchange Multiple indexTourSource: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourSource, beginService);
        for (int pos = j; pos < a.tours.get(indexTourDestination).size() - 1; pos++) {
            prevCity = a.tours.get(indexTourDestination).get(pos - 1);
            currentCity = a.tours.get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = a.beginService[prevCity + 1];
            }
            distance = vrp.instance.distance[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            a.beginService[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                loggerOutput.log("Exchange Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        a.currentTime.set(indexTourDestination, beginService);
    }

    public Ant exchangeMultipleRoute(Ants ants, Controller controller, Ant a, VRPTW vrptw) {
        boolean feasible;
        int city1, city2, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        int lastPos;
        Ant bestImprovedAnt = new Ant();
        bestImprovedAnt.tours = new ArrayList();
        bestImprovedAnt.tourLengths = new ArrayList<>();
        bestImprovedAnt.beginService = new double[vrptw.n + 1];
        bestImprovedAnt.currentTime = new ArrayList<>();
        bestImprovedAnt.currentQuantity = new ArrayList<>();
        bestImprovedAnt.usedVehicles = 1;
        for (int j = 0; j < bestImprovedAnt.usedVehicles; j++) {
            bestImprovedAnt.tours.add(j, new ArrayList<>());
            bestImprovedAnt.tourLengths.add(j, 0.0);
        }
        bestImprovedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        bestImprovedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        bestImprovedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            bestImprovedAnt.costObjectives[indexObj] = 0;
        }
        bestImprovedAnt.earliestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        bestImprovedAnt.latestTime = new ArrayList(bestImprovedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tourLengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tourLengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, bestImprovedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        for (int indexTourSource = 0; indexTourSource < (a.usedVehicles - 1); indexTourSource++) {
            for (int indexTourDestination = indexTourSource + 1; indexTourDestination < a.usedVehicles; indexTourDestination++) {
                if (indexTourSource != indexTourDestination) {
                    //index of the element to be moved from the source tour
                    if (indexTourSource > lastCommitedIndexes.size() - 1) {
                        startIndexSource = 1;
                    } else {
                        startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                    }
                    //index of the element to be moved from the destination tour
                    if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                        startIndexDestination = 1;
                    } else {
                        startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                    }
                    for (int i = startIndexSource; i < a.tours.get(indexTourSource).size() - 1; i++) {
                        for (int j = startIndexDestination; j < a.tours.get(indexTourDestination).size() - 1; j++) {
                            if (indexTourSource <= indexTourDestination) {
                                //check if results a feasible solution (i.e. no time window constraint is violated)
                                feasible = checkFeasibleTourExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                if (feasible) {
                                    //obtain the neighbour solution corresponding to the relocation operator
                                    city1 = temp.tours.get(indexTourSource).get(i);
                                    city2 = temp.tours.get(indexTourDestination).get(j);
                                    temp.tours.get(indexTourSource).set(i, city2);
                                    temp.tours.get(indexTourDestination).set(j, city1);
                                    newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
                                    temp.currentQuantity.set(indexTourSource, newQuantity1);
                                    newQuantity2 = temp.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
                                    temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                    //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                    //also update the current time of the source and destination tours
                                    updateBeginServiceExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    //update total traveled distance and lengths of source and destination tours
                                    sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                    sourceNextCity = temp.tours.get(indexTourSource).get(i + 1);
                                    newDistance1 = temp.tourLengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city1 + 1] - vrptw.instance.distance[city1 + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][city2 + 1] + vrptw.instance.distance[city2 + 1][sourceNextCity + 1];
                                    // new distance 2
                                    destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                    destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                    newDistance2 = temp.tourLengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][city2 + 1] - vrptw.instance.distance[city2 + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city1 + 1] + vrptw.instance.distance[city1 + 1][destinationNextCity + 1];
                                    // new total distance
                                    newTotalDistance = temp.totalTourLength - temp.tourLengths.get(indexTourSource) - temp.tourLengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                    temp.totalTourLength = newTotalDistance;
                                    temp.tourLengths.set(indexTourSource, newDistance1);
                                    temp.tourLengths.set(indexTourDestination, newDistance2);
                                    //if some improvement is obtained in the total traveled distance
                                    if (temp.totalTourLength < bestImprovedAnt.totalTourLength) {
                                        ants.copy_from_to(temp, bestImprovedAnt, vrptw);
                                    }
                                    //restore previous solution constructed by ant
                                    ants.copy_from_to(a, temp, vrptw);
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestImprovedAnt;
    }

    public Ant exchangeMultipleRouteIterated(Ants ants, Controller controller, Ant a, VRPTW vrptw) {
        boolean feasible;
        int city1, city2, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = vrptw.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        Ant improvedAnt = new Ant();
        improvedAnt.tours = new ArrayList();
        improvedAnt.tourLengths = new ArrayList<>();
        improvedAnt.beginService = new double[vrptw.n + 1];
        improvedAnt.currentTime = new ArrayList<>();
        improvedAnt.currentQuantity = new ArrayList<>();
        improvedAnt.usedVehicles = 1;
        for (int j = 0; j < improvedAnt.usedVehicles; j++) {
            improvedAnt.tours.add(j, new ArrayList<>());
            improvedAnt.tourLengths.add(j, 0.0);
        }
        improvedAnt.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        improvedAnt.toVisit = vrptw.getIdAvailableRequests().size();
        improvedAnt.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            improvedAnt.costObjectives[indexObj] = 0;
        }
        improvedAnt.earliestTime = new ArrayList(improvedAnt.usedVehicles);
        improvedAnt.latestTime = new ArrayList(improvedAnt.usedVehicles);
        Ant temp = new Ant();
        temp.tours = new ArrayList();
        temp.tourLengths = new ArrayList<>();
        temp.beginService = new double[vrptw.n + 1];
        temp.currentTime = new ArrayList<>();
        temp.currentQuantity = new ArrayList<>();
        temp.usedVehicles = 1;
        for (int j = 0; j < temp.usedVehicles; j++) {
            temp.tours.add(j, new ArrayList<>());
            temp.tourLengths.add(j, 0.0);
        }
        temp.visited = new boolean[vrptw.n];
        //the another node is the depot, which is by default visited by each salesman and added in its tour
        temp.toVisit = vrptw.getIdAvailableRequests().size();
        temp.costObjectives = new double[2];
        for (int indexObj = 0; indexObj < 2; indexObj++) {
            temp.costObjectives[indexObj] = 0;
        }
        temp.earliestTime = new ArrayList(temp.usedVehicles);
        temp.latestTime = new ArrayList(temp.usedVehicles);
        ants.copy_from_to(a, improvedAnt, vrptw);
        ants.copy_from_to(a, temp, vrptw);
        ArrayList<Integer> lastCommitedIndexes = new ArrayList<Integer>();
        for (int index = 0; index < ants.bestSoFarAnt.usedVehicles; index++) {
            lastPos = controller.getLastCommitedPos(index);
            lastCommitedIndexes.add(lastPos);
        }
        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                loggerOutput.log("Inside exchangeMultipleRouteIterated; count=" + count);
            }
            ants.copy_from_to(improvedAnt, a, vrptw);
            ants.copy_from_to(improvedAnt, temp, vrptw);
            for (int indexTourSource = 0; indexTourSource < (temp.usedVehicles - 1); indexTourSource++) {
                for (int indexTourDestination = indexTourSource + 1; indexTourDestination < temp.usedVehicles; indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        //index of the element to be moved from the source tour
                        if (indexTourSource > lastCommitedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommitedIndexes.get(indexTourSource) + 1;
                        }
                        //index of the element to be moved from the destination tour
                        if (indexTourDestination > lastCommitedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommitedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.tours.get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.tours.get(indexTourDestination).size() - 1; j++) {
                                if (indexTourSource <= indexTourDestination) {
                                    //check if results a feasible solution (i.e. no time window constraint is violated)
                                    feasible = checkFeasibleTourExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                    if (feasible) {
                                        //obtain the neighbour solution corresponding to the relocation operator
                                        city1 = temp.tours.get(indexTourSource).get(i);
                                        city2 = temp.tours.get(indexTourDestination).get(j);
                                        temp.tours.get(indexTourSource).set(i, city2);
                                        temp.tours.get(indexTourDestination).set(j, city1);
                                        newQuantity1 = temp.currentQuantity.get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
                                        temp.currentQuantity.set(indexTourSource, newQuantity1);
                                        newQuantity2 = temp.currentQuantity.get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
                                        temp.currentQuantity.set(indexTourDestination, newQuantity2);
                                        //update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                        //also update the current time of the source and destination tours
                                        updateBeginServiceExchangeMultiple(temp, vrptw, indexTourSource, indexTourDestination, i, j);
                                        //update total traveled distance and lengths of source and destination tours
                                        sourcePrevCity = temp.tours.get(indexTourSource).get(i - 1);
                                        sourceNextCity = temp.tours.get(indexTourSource).get(i + 1);
                                        newDistance1 = temp.tourLengths.get(indexTourSource) - vrptw.instance.distance[sourcePrevCity + 1][city1 + 1] - vrptw.instance.distance[city1 + 1][sourceNextCity + 1] + vrptw.instance.distance[sourcePrevCity + 1][city2 + 1] + vrptw.instance.distance[city2 + 1][sourceNextCity + 1];
                                        // new distance 2
                                        destinationPrevCity = temp.tours.get(indexTourDestination).get(j - 1);
                                        destinationNextCity = temp.tours.get(indexTourDestination).get(j + 1);
                                        newDistance2 = temp.tourLengths.get(indexTourDestination) - vrptw.instance.distance[destinationPrevCity + 1][city2 + 1] - vrptw.instance.distance[city2 + 1][destinationNextCity + 1] + vrptw.instance.distance[destinationPrevCity + 1][city1 + 1] + vrptw.instance.distance[city1 + 1][destinationNextCity + 1];
                                        // new total distance
                                        newTotalDistance = temp.totalTourLength - temp.tourLengths.get(indexTourSource) - temp.tourLengths.get(indexTourDestination) + newDistance1 + newDistance2;
                                        temp.totalTourLength = newTotalDistance;
                                        temp.tourLengths.set(indexTourSource, newDistance1);
                                        temp.tourLengths.set(indexTourDestination, newDistance2);
                                        //performing the rounding of the two numbers up to 10 decimals so that in the
                                        //comparison of the 2 double values to consider only the first 10 most significant decimals
                                        round1 = Math.round(temp.totalTourLength * tempNo) / tempNo;
                                        round2 = Math.round(improvedAnt.totalTourLength * tempNo) / tempNo;
                                        //if some improvement is obtained in the total traveled distance
                                        if (round1 < round2) {
                                            ants.copy_from_to(temp, improvedAnt, vrptw);
                                            foundImprovement = true;
                                        }
                                        //restore previous solution constructed by ant
                                        ants.copy_from_to(a, temp, vrptw);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }


    //find the index of the tour having the smallest number of visited nodes (cities/customers)
    public int findShortestTour(Ant a) {
        int indexTour = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.usedVehicles; i++) {
            if (min > a.tours.get(i).size()) {
                min = a.tours.get(i).size();
                indexTour = i;
            }
        }
        return indexTour;
    }
}
