package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class Controller {

    private int addedNodes;

    private int idLastAvailableNode;

    // Get the position of the last committed node in the tour designated by indexTour from the best so far ant
    public static int getLastCommittedPos(int indexTour, Ants ants) {
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
}
