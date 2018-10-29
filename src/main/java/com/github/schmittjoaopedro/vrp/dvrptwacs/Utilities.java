package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.Random;

public class Utilities {

    private int seed;

    private Random random;

    public double doubleTruncate(double x) {
        return (double) ((int) x);
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public boolean checkFeasibility(Ant ant, VRPTW vrp, Controller controller, boolean printNoNodes) {
        int currentCity, prevCity, addedNodes = 0;
        double currentQuantity, currentTime;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = vrp.getRequests();
        for (int indexTour = 0; indexTour < ant.getUsedVehicles(); indexTour++) {
            currentQuantity = reqList.get(0).getDemand();
            currentTime = 0.0;
            for (int currentPos = 1; currentPos < ant.getTours().get(indexTour).size(); currentPos++) {
                if (currentPos < ant.getTours().get(indexTour).size() - 1) {
                    addedNodes++;
                }
                prevCity = ant.getTours().get(indexTour).get(currentPos - 1);
                currentCity = ant.getTours().get(indexTour).get(currentPos);
                currentQuantity += reqList.get(currentCity + 1).getDemand();

                distance = vrp.getProblem().getDistance()[prevCity + 1][currentCity + 1];
                arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
                if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                    System.out.println("Time window constraint violated");
                    return false;
                }
                currentTime = beginService;
            }
            if (currentQuantity > vrp.getCapacity()) {
                System.out.println("Capacity constraint violated");
                return false;
            }
        }
        if (printNoNodes) {
            System.out.println("Added nodes=" + addedNodes);
        }
        controller.setAddedNodes(addedNodes);
        return true;

    }

    // Recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
    public void sort2(double v[], int v2[], int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap2(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap2(v, v2, ++last, k);
        swap2(v, v2, left, last);
        sort2(v, v2, left, last);
        sort2(v, v2, last + 1, right);
    }

    // Generate a random number that is uniformly distributed in [0,1]
    public double random01() {
        if (random == null) {
            random = new Random();
        }
        return random.nextDouble();
    }

    // Auxiliary routine for sorting an integer array
    private void swap2(double v[], int v2[], int i, int j) {
        double tmp1;
        int tmp2;

        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;

        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
