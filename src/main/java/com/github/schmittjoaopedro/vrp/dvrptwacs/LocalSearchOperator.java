package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class LocalSearchOperator {

    /**
     * Skip committed (defined) nodes.
     * This method is performed multiple times until no further improvement (which minimizes most the
     * total traveled distance or reduces the number of used vehicles) is possible.
     */
    public Ant relocateMultipleRouteIterated(Ants ants, Ant ant, VRPTW instance) {
        boolean feasible;
        int city, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = instance.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        double distance[][] = instance.getProblem().getDistance();
        Ant improvedAnt = ants.createDefaultAnt(instance.getN(), instance.getIdAvailableRequests().size());
        Ant temp = ants.createDefaultAnt(instance.getN(), instance.getIdAvailableRequests().size());
        ants.copyFromTo(ant, improvedAnt, instance);
        ants.copyFromTo(ant, temp, instance);

        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.getBestSoFarAnt().getUsedVehicles(); index++) {
            lastPos = Controller.getLastCommittedPos(index, ants);
            lastCommittedIndexes.add(lastPos);
        }

        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                System.out.println("Inside relocateMultipleRouteIterated; count=" + count);
            }
            ants.copyFromTo(improvedAnt, ant, instance);
            ants.copyFromTo(improvedAnt, temp, instance);
            for (int indexTourSource = 0; indexTourSource < temp.getUsedVehicles(); indexTourSource++) {
                for (int indexTourDestination = 0; indexTourDestination < temp.getUsedVehicles(); indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        // Index of the element to be moved/relocated
                        if (indexTourSource > lastCommittedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommittedIndexes.get(indexTourSource) + 1;
                        }
                        // Index of the relocation's destination
                        if (indexTourDestination > lastCommittedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommittedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.getTours().get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.getTours().get(indexTourDestination).size(); j++) {
                                // Check if results a feasible solution (i.e. no time window constraint is violated)
                                feasible = checkFeasibleTourRelocationMultiple(temp, instance, indexTourSource, indexTourDestination, i, j);
                                if (feasible) {
                                    // Obtain the neighbour solution corresponding to the relocation operator
                                    city = temp.getTours().get(indexTourSource).get(i);
                                    temp.getTours().get(indexTourSource).remove(i);
                                    temp.getTours().get(indexTourDestination).add(j, city);
                                    newQuantity1 = temp.getCurrentQuantity().get(indexTourSource) - reqList.get(city + 1).getDemand();
                                    temp.getCurrentQuantity().set(indexTourSource, newQuantity1);
                                    newQuantity2 = temp.getCurrentQuantity().get(indexTourDestination) + reqList.get(city + 1).getDemand();
                                    temp.getCurrentQuantity().set(indexTourDestination, newQuantity2);
                                    // Update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                    // also update the current time of the source and destination tours
                                    updateBeginServiceRelocationMultiple(temp, instance, indexTourSource, indexTourDestination, i, j);

                                    // Update total traveled distance and lengths of source and destination tours
                                    sourcePrevCity = temp.getTours().get(indexTourSource).get(i - 1);
                                    sourceNextCity = temp.getTours().get(indexTourSource).get(i);
                                    newDistance1 = temp.getTourLengths().get(indexTourSource) - distance[sourcePrevCity + 1][city + 1] -
                                            distance[city + 1][sourceNextCity + 1] + distance[sourcePrevCity + 1][sourceNextCity + 1];

                                    destinationPrevCity = temp.getTours().get(indexTourDestination).get(j - 1);
                                    destinationNextCity = temp.getTours().get(indexTourDestination).get(j + 1);
                                    newDistance2 = temp.getTourLengths().get(indexTourDestination) - distance[destinationPrevCity + 1][destinationNextCity + 1] +
                                            distance[destinationPrevCity + 1][city + 1] + distance[city + 1][destinationNextCity + 1];

                                    newTotalDistance = temp.getTotalTourLength() - temp.getTourLengths().get(indexTourSource) -
                                            temp.getTourLengths().get(indexTourDestination) + newDistance1 + newDistance2;
                                    temp.setTotalTourLength(newTotalDistance);
                                    temp.getTourLengths().set(indexTourSource, newDistance1);
                                    temp.getTourLengths().set(indexTourDestination, newDistance2);

                                    // If the source tour becomes empty (no city is visited except the depot), remove this empty tour
                                    if (temp.getTours().get(indexTourSource).size() == 2 && temp.getTours().get(indexTourSource).get(0) == -1 && temp.getTours().get(indexTourSource).get(1) == -1) {
                                        temp.getTours().remove(indexTourSource);
                                        temp.getTourLengths().remove(indexTourSource);
                                        temp.getCurrentQuantity().remove(indexTourSource);
                                        temp.getCurrentTime().remove(indexTourSource);
                                        temp.setUsedVehicles(temp.getUsedVehicles() - 1);
                                    }
                                    // Performing the rounding of the two numbers up to 10 decimals so that in the
                                    // comparison of the 2 double values to consider only the first 10 most significant decimals
                                    round1 = Math.round(temp.getTotalTourLength() * tempNo) / tempNo;
                                    round2 = Math.round(improvedAnt.getTotalTourLength() * tempNo) / tempNo;
                                    // If some improvement is obtained in the total traveled distance
                                    if (((round1 < round2) && (temp.getUsedVehicles() == improvedAnt.getUsedVehicles()))
                                            || (temp.getUsedVehicles() < improvedAnt.getUsedVehicles())) {
                                        ants.copyFromTo(temp, improvedAnt, instance);
                                        foundImprovement = true;
                                    }
                                    // Restore previous solution constructed by ant
                                    ants.copyFromTo(ant, temp, instance);
                                }
                            }
                        }
                    }
                }
            }
        }
        return improvedAnt;
    }

    private boolean checkFeasibleTourRelocationMultiple(Ant ant, VRPTW instance, int indexTourSource, int indexTourDestination, int i, int j) {
        int previousCity, prevCity, nextCity, currentCity;
        double arrivalTime, currentTime = 0.0, beginService, distance;
        ArrayList<Request> reqList = instance.getRequests();
        int city = ant.getTours().get(indexTourSource).get(i);
        double currentQuantity = ant.getCurrentQuantity().get(indexTourDestination) + reqList.get(city + 1).getDemand();
        double distances[][] = instance.getProblem().getDistance();

        if (currentQuantity > instance.getCapacity()) {
            return false;
        }

        // Check time window constraints in source tour
        for (int pos = i + 1; pos < ant.getTours().get(indexTourSource).size(); pos++) {
            if (pos == (i + 1)) {
                prevCity = ant.getTours().get(indexTourSource).get(pos - 2);
                currentCity = ant.getTours().get(indexTourSource).get(pos);
                currentTime = ant.getBeginService()[prevCity + 1];
            } else {
                prevCity = ant.getTours().get(indexTourSource).get(pos - 1);
                currentCity = ant.getTours().get(indexTourSource).get(pos);
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }

        previousCity = ant.getTours().get(indexTourDestination).get(j - 1);
        nextCity = ant.getTours().get(indexTourDestination).get(j);

        arrivalTime = ant.getBeginService()[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + distances[previousCity + 1][city + 1];
        beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
        if (beginService > reqList.get(city + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;

        arrivalTime = currentTime + reqList.get(city + 1).getServiceTime() + distances[city + 1][nextCity + 1];
        beginService = Math.max(arrivalTime, reqList.get(nextCity + 1).getStartWindow());
        if (beginService > reqList.get(nextCity + 1).getEndWindow()) {
            return false;
        }
        currentTime = beginService;

        // Check time window constraints in destination tour
        for (int pos = j + 1; pos < ant.getTours().get(indexTourDestination).size(); pos++) {
            prevCity = ant.getTours().get(indexTourDestination).get(pos - 1);
            currentCity = ant.getTours().get(indexTourDestination).get(pos);
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }

        return true;
    }

    private void updateBeginServiceRelocationMultiple(Ant ant, VRPTW instance, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        double distances[][] = instance.getProblem().getDistance();
        ArrayList<Request> reqList = instance.getRequests();

        // Update of begin service times for the source tour
        for (int pos = i; pos < ant.getTours().get(indexTourSource).size() - 1; pos++) {
            prevCity = ant.getTours().get(indexTourSource).get(pos - 1);
            currentCity = ant.getTours().get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = ant.getBeginService()[prevCity + 1];
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            ant.getBeginService()[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                System.out.println("Relocation Multiple indexTourSource: Unfeasible solution..");
            }
        }
        ant.getCurrentTime().set(indexTourSource, beginService);

        // Update of begin service times for the destination tour
        for (int pos = j; pos < ant.getTours().get(indexTourDestination).size() - 1; pos++) {
            prevCity = ant.getTours().get(indexTourDestination).get(pos - 1);
            currentCity = ant.getTours().get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = ant.getBeginService()[prevCity + 1];
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            ant.getBeginService()[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                System.out.println("Relocation Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        ant.getCurrentTime().set(indexTourDestination, beginService);

    }

    public Ant exchangeMultipleRouteIterated(Ants ants, Ant ant, VRPTW instance) {
        boolean feasible;
        int city1, city2, sourcePrevCity, sourceNextCity, destinationPrevCity, destinationNextCity;
        int startIndexSource, startIndexDestination;
        double newQuantity1, newQuantity2, newDistance1, newDistance2, newTotalDistance;
        ArrayList<Request> reqList = instance.getRequests();
        boolean foundImprovement = true;
        int lastPos;
        double tempNo = Math.pow(10, 10);
        double round1, round2;
        double distances[][] = instance.getProblem().getDistance();
        Ant improvedAnt = ants.createDefaultAnt(instance.getN(), instance.getIdAvailableRequests().size());
        Ant temp = ants.createDefaultAnt(instance.getN(), instance.getIdAvailableRequests().size());

        ants.copyFromTo(ant, improvedAnt, instance);
        ants.copyFromTo(ant, temp, instance);

        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.getBestSoFarAnt().getUsedVehicles(); index++) {
            lastPos = Controller.getLastCommittedPos(index, ants);
            lastCommittedIndexes.add(lastPos);
        }

        int count = 0;
        while (foundImprovement) {
            foundImprovement = false;
            count++;
            if (count > 100) {
                System.out.println("Inside exchangeMultipleRouteIterated; count=" + count);
            }
            ants.copyFromTo(improvedAnt, ant, instance);
            ants.copyFromTo(improvedAnt, temp, instance);

            for (int indexTourSource = 0; indexTourSource < (temp.getUsedVehicles() - 1); indexTourSource++) {
                for (int indexTourDestination = indexTourSource + 1; indexTourDestination < temp.getUsedVehicles(); indexTourDestination++) {
                    if (indexTourSource != indexTourDestination) {
                        // Index of the element to be moved from the source tour
                        if (indexTourSource > lastCommittedIndexes.size() - 1) {
                            startIndexSource = 1;
                        } else {
                            startIndexSource = lastCommittedIndexes.get(indexTourSource) + 1;
                        }
                        // Index of the element to be moved from the destination tour
                        if (indexTourDestination > lastCommittedIndexes.size() - 1) {
                            startIndexDestination = 1;
                        } else {
                            startIndexDestination = lastCommittedIndexes.get(indexTourDestination) + 1;
                        }
                        for (int i = startIndexSource; i < temp.getTours().get(indexTourSource).size() - 1; i++) {
                            for (int j = startIndexDestination; j < temp.getTours().get(indexTourDestination).size() - 1; j++) {
                                if (indexTourSource <= indexTourDestination) {
                                    // Check if results a feasible solution (i.e. no time window constraint is violated)
                                    feasible = checkFeasibleTourExchangeMultiple(temp, instance, indexTourSource, indexTourDestination, i, j);
                                    if (feasible) {
                                        //obtain the neighbour solution corresponding to the relocation operator
                                        city1 = temp.getTours().get(indexTourSource).get(i);
                                        city2 = temp.getTours().get(indexTourDestination).get(j);
                                        temp.getTours().get(indexTourSource).set(i, city2);
                                        temp.getTours().get(indexTourDestination).set(j, city1);
                                        newQuantity1 = temp.getCurrentQuantity().get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
                                        temp.getCurrentQuantity().set(indexTourSource, newQuantity1);
                                        newQuantity2 = temp.getCurrentQuantity().get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
                                        temp.getCurrentQuantity().set(indexTourDestination, newQuantity2);

                                        // Update the begin service times of the nodes from the source and destination tours of the obtained neighbour solution
                                        // also update the current time of the source and destination tours
                                        updateBeginServiceExchangeMultiple(temp, instance, indexTourSource, indexTourDestination, i, j);

                                        // Update total traveled distance and lengths of source and destination tours
                                        sourcePrevCity = temp.getTours().get(indexTourSource).get(i - 1);
                                        sourceNextCity = temp.getTours().get(indexTourSource).get(i + 1);
                                        newDistance1 = temp.getTourLengths().get(indexTourSource) - distances[sourcePrevCity + 1][city1 + 1] -
                                                distances[city1 + 1][sourceNextCity + 1] + distances[sourcePrevCity + 1][city2 + 1] + distances[city2 + 1][sourceNextCity + 1];

                                        destinationPrevCity = temp.getTours().get(indexTourDestination).get(j - 1);
                                        destinationNextCity = temp.getTours().get(indexTourDestination).get(j + 1);
                                        newDistance2 = temp.getTourLengths().get(indexTourDestination) - distances[destinationPrevCity + 1][city2 + 1] -
                                                distances[city2 + 1][destinationNextCity + 1] + distances[destinationPrevCity + 1][city1 + 1] + distances[city1 + 1][destinationNextCity + 1];

                                        newTotalDistance = temp.getTotalTourLength() - temp.getTourLengths().get(indexTourSource) -
                                                temp.getTourLengths().get(indexTourDestination) + newDistance1 + newDistance2;
                                        temp.setTotalTourLength(newTotalDistance);
                                        temp.getTourLengths().set(indexTourSource, newDistance1);
                                        temp.getTourLengths().set(indexTourDestination, newDistance2);

                                        // Performing the rounding of the two numbers up to 10 decimals so that in the
                                        // comparison of the 2 double values to consider only the first 10 most significant decimals
                                        round1 = Math.round(temp.getTotalTourLength() * tempNo) / tempNo;
                                        round2 = Math.round(improvedAnt.getTotalTourLength() * tempNo) / tempNo;
                                        // If some improvement is obtained in the total traveled distance
                                        if (round1 < round2) {
                                            ants.copyFromTo(temp, improvedAnt, instance);
                                            foundImprovement = true;
                                        }
                                        // Restore previous solution constructed by ant
                                        ants.copyFromTo(ant, temp, instance);
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

    private boolean checkFeasibleTourExchangeMultiple(Ant ant, VRPTW instance, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity, city1, city2;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService, currentQuantity;
        ArrayList<Request> reqList = instance.getRequests();
        double distances[][] = instance.getProblem().getDistance();

        // Check vehicle capacity tour constraints for source and destination tours
        city1 = ant.getTours().get(indexTourSource).get(i);
        city2 = ant.getTours().get(indexTourDestination).get(j);
        currentQuantity = ant.getCurrentQuantity().get(indexTourSource) - reqList.get(city1 + 1).getDemand() + reqList.get(city2 + 1).getDemand();
        if (currentQuantity > instance.getCapacity()) {
            return false;
        }
        currentQuantity = ant.getCurrentQuantity().get(indexTourDestination) - reqList.get(city2 + 1).getDemand() + reqList.get(city1 + 1).getDemand();
        if (currentQuantity > instance.getCapacity()) {
            return false;
        }

        // Check feasibility for source tour regarding time windows constraints
        for (int pos = i; pos < ant.getTours().get(indexTourSource).size(); pos++) {
            if (pos == i) {
                prevCity = ant.getTours().get(indexTourSource).get(pos - 1);
                currentCity = ant.getTours().get(indexTourDestination).get(j);
                currentTime = ant.getBeginService()[prevCity + 1];
            } else if (pos == (i + 1)) {
                prevCity = ant.getTours().get(indexTourDestination).get(j);
                currentCity = ant.getTours().get(indexTourSource).get(pos);
            } else {
                prevCity = ant.getTours().get(indexTourSource).get(pos - 1);
                currentCity = ant.getTours().get(indexTourSource).get(pos);
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }

        // Check feasibility for destination tour regarding time windows constraints
        for (int pos = j; pos < ant.getTours().get(indexTourDestination).size(); pos++) {
            if (pos == j) {
                prevCity = ant.getTours().get(indexTourDestination).get(pos - 1);
                currentCity = ant.getTours().get(indexTourSource).get(i);
                currentTime = ant.getBeginService()[prevCity + 1];
            } else if (pos == (j + 1)) {
                prevCity = ant.getTours().get(indexTourSource).get(i);
                currentCity = ant.getTours().get(indexTourDestination).get(pos);
            } else {
                prevCity = ant.getTours().get(indexTourDestination).get(pos - 1);
                currentCity = ant.getTours().get(indexTourDestination).get(pos);
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                return false;
            }
            currentTime = beginService;
        }
        return true;

    }

    private void updateBeginServiceExchangeMultiple(Ant ant, VRPTW instance, int indexTourSource, int indexTourDestination, int i, int j) {
        int currentCity, prevCity;
        double currentTime = 0.0;
        double distance, arrivalTime, beginService = 0.0;
        ArrayList<Request> reqList = instance.getRequests();
        double distances[][] = instance.getProblem().getDistance();

        for (int pos = i; pos < ant.getTours().get(indexTourSource).size() - 1; pos++) {
            prevCity = ant.getTours().get(indexTourSource).get(pos - 1);
            currentCity = ant.getTours().get(indexTourSource).get(pos);
            if (pos == i) {
                currentTime = ant.getBeginService()[prevCity + 1];
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            ant.getBeginService()[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                System.out.println("Exchange Multiple indexTourSource: Unfeasible solution..");
            }
        }
        ant.getCurrentTime().set(indexTourSource, beginService);

        for (int pos = j; pos < ant.getTours().get(indexTourDestination).size() - 1; pos++) {
            prevCity = ant.getTours().get(indexTourDestination).get(pos - 1);
            currentCity = ant.getTours().get(indexTourDestination).get(pos);
            if (pos == j) {
                currentTime = ant.getBeginService()[prevCity + 1];
            }
            distance = distances[prevCity + 1][currentCity + 1];
            arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
            beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
            currentTime = beginService;
            ant.getBeginService()[currentCity + 1] = beginService;
            if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                System.out.println("Exchange Multiple indexTourDestination: Unfeasible solution..");
            }
        }
        ant.getCurrentTime().set(indexTourDestination, beginService);
    }

}
