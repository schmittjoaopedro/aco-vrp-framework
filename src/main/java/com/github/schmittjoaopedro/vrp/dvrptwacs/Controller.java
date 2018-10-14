package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Controller {

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

}
