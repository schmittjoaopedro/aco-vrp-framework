package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class Controller {

    // The scaling value is used to scale all time-related values.
    private double scalingValue;

    private int addedNodes;

    private int idLastAvailableNode;

    // Get the position of the last committed node in the tour designated by indexTour from the best so far ant
    public int getLastCommittedPos(int indexTour, Ants ants) {
        int pos = 0;
        int node;
        int tourLength;
        if (indexTour < ants.getBestSoFarAnt().getUsedVehicles()) {
            tourLength = ants.getBestSoFarAnt().getTours().get(indexTour).size();
            for (int i = 1; i < tourLength - 1; i++) {
                node = ants.getBestSoFarAnt().getTours().get(indexTour).get(i);
                if (ants.getCommittedNodes()[node]) {
                    pos++;
                } else {
                    break;
                }
            }
        }
        return pos;
    }

    // Add to the ant's solution the committed nodes from each tour of the best so far solution
    public void addCommittedNodes(Ants ants, Ant a, VRPTW instance) {
        int index, city, current_city;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = instance.getRequests();
        int startIndex = 1, pos;

        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int indexTour = 0; indexTour < ants.getBestSoFarAnt().getUsedVehicles(); indexTour++) {
            pos = getLastCommittedPos(indexTour, ants);
            lastCommittedIndexes.add(pos);
        }

        for (int i = 0; i < lastCommittedIndexes.size(); i++) {
            // We have at least one committed node in the i-th tour (i.e. tour with index i)
            index = lastCommittedIndexes.get(i);
            if (index > 0) {
                // If the number of vehicles (tours) from the ant solution is less than the index of the
                // tour from the best so far solution, add new (empty) tours in the ant's solution
                if (a.getUsedVehicles() < (i + 1)) {
                    a.setUsedVehicles(i + 1);
                    for (int l = startIndex; l < a.getUsedVehicles(); l++) {
                        a.getTours().add(l, new ArrayList<>());
                        a.getTours().get(l).add(-1);
                        a.getTourLengths().add(l, 0.0);
                        a.getCurrentQuantity().add(l, 0.0);
                        a.getCurrentTime().add(l, 0.0);
                    }
                    startIndex = i + 1;
                }

                int lastPos = a.getTours().get(i).size() - 1;
                current_city = a.getTours().get(i).get(lastPos);
                current_city++;
                // Add in the ant's i-th tour all the committed nodes from the i-th tour of the best so far solution
                for (int j = 1; j <= index; j++) {
                    city = ants.getBestSoFarAnt().getTours().get(i).get(j);
                    distance = instance.getProblem().getDistance()[current_city][city + 1];
                    arrivalTime = a.getCurrentTime().get(i) + reqList.get(current_city).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    if (beginService > reqList.get(city + 1).getEndWindow()) {
                        System.out.println("Method addCommitedNodes: solution infeasible..");
                    }
                    // Add committed node to the ant's tour
                    a.getTours().get(i).add(j, city);
                    a.getVisited()[city] = true;
                    a.setToVisit(a.getToVisit() - 1);
                    a.getCurrentTime().set(i, beginService);
                    a.getBeginService()[city + 1] = beginService;
                    a.getCurrentQuantity().set(i, a.getCurrentQuantity().get(i) + reqList.get(city + 1).getDemand());
                    current_city = city + 1;
                }
            }
        }
    }

    // Get a list of new available (known) nodes at the given time moment
    public ArrayList<Integer> countNoAvailableNodes(ArrayList<Request> dynamicRequests, double time) {
        int i, id;
        int pos = getIdLastAvailableNode();
        ArrayList<Integer> nodesList = new ArrayList<Integer>();
        for (i = pos; i < dynamicRequests.size(); i++) {
            if (time >= dynamicRequests.get(i).getAvailableTime()) {
                id = dynamicRequests.get(i).getId() - 1;
                nodesList.add(id);
            } else {
                break;
            }
        }
        setIdLastAvailableNode(i);
        return nodesList;
    }

    // Check if there are any nodes in the tours of the best so far solution that should be marked as committed
    public boolean checkNewCommittedNodes(Ants ants, int indexTimeSlice, double lengthTimeSlice) {
        int indexTour = 0;
        int tourLength = 0;
        int node, startPos, count = 0;
        Ant bestAnt = ants.getRestartBestAnt();
        while (indexTour < bestAnt.getUsedVehicles()) {
            if (count >= 50) {
                System.out.println("Index tour=" + indexTour + ", used vehicles=" + bestAnt.getUsedVehicles() + ", tour length=" + tourLength);
            }
            // Skip for already committed nodes
            tourLength = bestAnt.getTours().get(indexTour).size();
            startPos = getLastCommittedPos(indexTour, ants);
            for (int i = startPos + 1; i < tourLength - 1; i++) {
                node = bestAnt.getTours().get(indexTour).get(i);
                // Check condition for a node to be committed
                if (bestAnt.getBeginService()[node + 1] <= indexTimeSlice * lengthTimeSlice) {
                    if (!ants.getCommittedNodes()[node]) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    indexTour++;
                    break;
                }
            }
            // If all the nodes from this tour are committed, move to the next tour for checking if it contains nodes that must be committed
            if (indexTour < bestAnt.getUsedVehicles()) {
                startPos = getLastCommittedPos(indexTour, ants);
                tourLength = bestAnt.getTours().get(indexTour).size();
                if (startPos == tourLength - 2) {
                    indexTour++;
                }
            }
            count++;
        }
        return false;
    }

    /**
     * Commit nodes from the tours of the best so far solution, that will have their position fixed when
     * they will be copied in the ants'solutions block part of the best solution that is being/has been visited
     */
    public void commitNodes(Ants ants, int indexTimeSlice, double lengthTimeSlice) {
        int indexTour = 0;
        int tourLength;
        int node, startPos, count = 0;
        Ant bestAnt = ants.getBestSoFarAnt();
        while (indexTour < bestAnt.getUsedVehicles()) {
            // Skip for already committed nodes
            tourLength = bestAnt.getTours().get(indexTour).size();
            if (count >= 50) {
                System.out.println("Index tour=" + indexTour + ", used vehicles=" + bestAnt.getUsedVehicles() + ", tour length=" + tourLength);
            }
            startPos = getLastCommittedPos(indexTour, ants);
            for (int i = startPos + 1; i < tourLength - 1; i++) {
                node = bestAnt.getTours().get(indexTour).get(i);
                // Check condition for a node to be committed
                if ((bestAnt.getBeginService()[node + 1] <= indexTimeSlice * lengthTimeSlice) && (!ants.getCommittedNodes()[node])) {
                    ants.getCommittedNodes()[node] = true;
                } else {
                    indexTour++;
                    break;
                }
            }
            // If all the nodes from this tour were committed (the depot from the start and
            // end of a tour are assumed to be committed by default), move to the next tour
            if (indexTour < bestAnt.getUsedVehicles()) {
                startPos = getLastCommittedPos(indexTour, ants);
                tourLength = bestAnt.getTours().get(indexTour).size();
                if (startPos == tourLength - 2) {
                    indexTour++;
                }
            }
            count++;
        }
    }

    // Get the list with the unvisited customers
    public ArrayList unRoutedCustomers(Ant ant, VRPTW vrp) {
        ArrayList<Integer> l = new ArrayList<>(ant.getToVisit());
        ArrayList<Integer> idKnownRequests = vrp.getIdAvailableRequests();
        int count = 0;
        // Collect nodes missing from the ant's solution; depot is considered to be visisted by default
        for (int city : idKnownRequests) {
            if (ant.getVisited()[city] == false) {
                l.add(city);
                count++;
                if (count == ant.getToVisit()) {
                    break;
                }
            }
        }
        return l;
    }

    /**
     * Check if there are any committed tours (i.e. tours that contain at least one committed node that
     * should be included in the ant's solution)
     */
    public boolean checkCommittedTours(Ants ants) {
        int lastPos;

        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int index = 0; index < ants.getBestSoFarAnt().getUsedVehicles(); index++) {
            lastPos = getLastCommittedPos(index, ants);
            lastCommittedIndexes.add(lastPos);
        }

        for (int index : lastCommittedIndexes) {
            if (index > 0) {
                return true;
            }
        }

        return false;
    }

    public int getAddedNodes() {
        return addedNodes;
    }

    public void setAddedNodes(int addedNodes) {
        this.addedNodes = addedNodes;
    }

    public int getIdLastAvailableNode() {
        return idLastAvailableNode;
    }

    public void setIdLastAvailableNode(int idLastAvailableNode) {
        this.idLastAvailableNode = idLastAvailableNode;
    }

    public double getScalingValue() {
        return scalingValue;
    }

    public void setScalingValue(double scalingValue) {
        this.scalingValue = scalingValue;
    }
}
