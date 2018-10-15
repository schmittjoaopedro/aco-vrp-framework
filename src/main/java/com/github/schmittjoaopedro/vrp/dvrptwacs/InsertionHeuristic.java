package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InsertionHeuristic {

    public static void insertUnRoutedCustomers(Ant ant, VRPTW vrp, ArrayList<Integer> unVisitedNodes, int startIndexTour, ArrayList<Integer> startPos) {
        boolean ok = true;
        double c11, c12, c1, c1_, c2, value1, value2, value3, value4, earliestTime, latestTime;
        double bestC1Score;
        double bestC2Score;
        double distances[][] = vrp.getProblem().getDistance();
        double arrivalTime, beginService = 0.0, newArrivalTime, newBeginService, oldBeginService, newQuantity, newDistance;
        int previousCity, nextCity, bestPos = 1, bestCustomer, bestIndexTour = 0, bestIndexInsertion = 0;
        int cust;
        double mu = 1.0, alfa1 = 0.1, alfa2 = 0.9, alfa3 = 0.2, lambda = 2.0;  //alfa1 = 0.1, alfa2 = 0.9
        ArrayList<Request> reqList = vrp.getRequests();
        Insertion ins, bestInsertion;
        int startIndex = 0;
        // It contains for each un-routed customer its best feasible insertion place
        // this list is used when deciding the best un-routed customer to be inserted in the unfeasible solution
        ArrayList<Insertion> bestInsertions = new ArrayList<Insertion>();
        // Keep track of nodes (customers) inserted/included in the solution by the insertion heuristic
        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>(unVisitedNodes.size());
        for (int node : unVisitedNodes) {
            visited.put(node, false);
        }
        computeRouteVariables(ant, vrp, startIndexTour);
        while (ok) {
            if (bestInsertions != null && bestInsertions.size() > 0) {
                bestInsertions.clear();
            }
            // Find the best possible feasible position to insert an un-routed customer into a tour
            // then insert it and update the score
            for (int customer : unVisitedNodes) {
                // Check if the customer was not included/inserted in the solution in the meantime by the insertion heuristic
                if (!visited.get(customer)) {
                    bestC1Score = Double.MAX_VALUE;
                    for (int indexTour = startIndexTour; indexTour < ant.getUsedVehicles(); indexTour++) {
                        if (indexTour > startPos.size() - 1) {
                            startIndex = 1;
                        } else {
                            startIndex = startPos.get(indexTour) + 1;
                        }
                        // Check for a feasible insertion place within the considered tour
                        for (int pos = startIndex; pos < ant.getTours().get(indexTour).size(); pos++) {
                            if (isFeasibleInsertion(ant, vrp, customer, indexTour, pos - 1, pos)) {
                                // Compute the score (value) for the c1 metric
                                previousCity = ant.getTours().get(indexTour).get(pos - 1);
                                nextCity = ant.getTours().get(indexTour).get(pos);
                                c11 = distances[previousCity + 1][customer + 1] + distances[customer + 1][nextCity + 1] - mu * distances[previousCity + 1][nextCity + 1];
                                arrivalTime = ant.getBeginService()[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + distances[previousCity + 1][customer + 1];
                                beginService = Math.max(arrivalTime, reqList.get(customer + 1).getStartWindow());
                                newArrivalTime = beginService + reqList.get(customer + 1).getServiceTime() + distances[customer + 1][nextCity + 1];
                                newBeginService = Math.max(newArrivalTime, reqList.get(nextCity + 1).getStartWindow());
                                oldBeginService = ant.getBeginService()[nextCity + 1];
                                c12 = newBeginService - oldBeginService;
                                c1 = alfa1 * c11 + alfa2 * c12;
                                if (c1 < bestC1Score) {
                                    bestC1Score = c1;
                                    bestIndexTour = indexTour;
                                    bestPos = pos;
                                }
                            }
                        }
                    }
                    // We have a best feasible insertion position for the unrouted node denoted by "customer"
                    if (bestC1Score != Double.MAX_VALUE) {
                        ins = new Insertion(customer, bestIndexTour, bestPos, bestC1Score);
                        bestInsertions.add(ins);
                    }
                }
            }
            bestC2Score = Double.MAX_VALUE;
            Insertion insert;
            // Decide the best customer to be inserted in the solution
            if (bestInsertions != null && bestInsertions.size() > 0) {
                for (int i = 0; i < bestInsertions.size(); i++) {
                    insert = bestInsertions.get(i);
                    cust = insert.getCustomer();
                    c1_ = insert.getScore();
                    c2 = lambda * distances[0][cust + 1] - c1_;
                    if (c2 < bestC2Score) {
                        bestC2Score = c2;
                        bestIndexInsertion = i;
                    }
                }
            }
            // We have the best customer to be inserted in the solution, now we should perform the insertion
            // of the selected best customer
            if (bestC2Score != Double.MAX_VALUE) {
                bestInsertion = bestInsertions.get(bestIndexInsertion);
                bestCustomer = bestInsertion.getCustomer();
                bestIndexTour = bestInsertion.getIndexTour();
                bestPos = bestInsertion.getPreviousNode();
                ant.getTours().get(bestIndexTour).add(bestPos, bestCustomer);
                ant.getVisited()[bestCustomer] = true;
                ant.setToVisit(ant.getToVisit() - 1);
                newQuantity = ant.getCurrentQuantity().get(bestIndexTour) + reqList.get(bestCustomer + 1).getDemand();
                ant.getCurrentQuantity().set(bestIndexTour, newQuantity);
                visited.put(bestCustomer, true);
                // Update earliest time and latest time lists to include the value for the newly inserted customer
                previousCity = ant.getTours().get(bestIndexTour).get(bestPos - 1);
                nextCity = ant.getTours().get(bestIndexTour).get(bestPos + 1);
                value1 = reqList.get(bestCustomer + 1).getStartWindow();
                value2 = ant.getEarliestTime().get(bestIndexTour).get(bestPos - 1) + distances[previousCity + 1][bestCustomer + 1] + reqList.get(previousCity + 1).getServiceTime();
                earliestTime = Math.max(value1, value2);
                value3 = reqList.get(bestCustomer + 1).getEndWindow();
                value4 = ant.getLatestTime().get(bestIndexTour).get(bestPos) - distances[bestCustomer + 1][nextCity + 1] - reqList.get(bestCustomer + 1).getServiceTime();
                latestTime = Math.min(value3, value4);
                ant.getEarliestTime().get(bestIndexTour).add(bestPos, earliestTime);
                ant.getLatestTime().get(bestIndexTour).add(bestPos, latestTime);
                updateRouteVariables(ant, vrp, bestInsertion);
                // Update the begin service times for the nodes that come after the inserted customer on the route
                // also update the begin service time for the inserted node itself
                for (int j = bestPos; j < ant.getTours().get(bestIndexTour).size(); j++) {
                    previousCity = ant.getTours().get(bestIndexTour).get(j - 1);
                    cust = ant.getTours().get(bestIndexTour).get(j);
                    arrivalTime = ant.getBeginService()[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + distances[previousCity + 1][cust + 1];
                    beginService = Math.max(arrivalTime, reqList.get(cust + 1).getStartWindow());
                    ant.getBeginService()[cust + 1] = beginService;
                }
                ant.getCurrentTime().set(bestIndexTour, beginService);
                ok = true;
            } else {
                ok = false;
            }
        }
    }

    private static void updateRouteVariables(Ant ant, VRPTW vrp, Insertion ins) {
        int previousCity, nextCity, bestPos, bestIndexTour;
        double value1, value2, oldLatestTime, newLatestTime, oldEarliestTime, newEarliestTime;
        double distances[][] = vrp.getProblem().getDistance();
        ArrayList<Request> reqList = vrp.getRequests();
        bestIndexTour = ins.getIndexTour();
        bestPos = ins.getPreviousNode();
        for (int k = bestPos - 1; k >= 0; k--) {
            previousCity = ant.getTours().get(bestIndexTour).get(k);
            nextCity = ant.getTours().get(bestIndexTour).get(k + 1);
            oldLatestTime = ant.getLatestTime().get(bestIndexTour).get(k);
            value1 = ant.getLatestTime().get(bestIndexTour).get(k + 1) - distances[previousCity + 1][nextCity + 1] - reqList.get(previousCity + 1).getServiceTime();
            newLatestTime = Math.min(oldLatestTime, value1);
            if (oldLatestTime != newLatestTime) {
                ant.getLatestTime().get(bestIndexTour).set(k, newLatestTime);
            } else {
                break;
            }
        }
        for (int k = bestPos + 1; k < ant.getTours().get(bestIndexTour).size(); k++) {
            previousCity = ant.getTours().get(bestIndexTour).get(k - 1);
            nextCity = ant.getTours().get(bestIndexTour).get(k);
            oldEarliestTime = ant.getEarliestTime().get(bestIndexTour).get(k);
            value2 = ant.getEarliestTime().get(bestIndexTour).get(k - 1) + distances[previousCity + 1][nextCity + 1] + reqList.get(previousCity + 1).getServiceTime();
            newEarliestTime = Math.max(oldEarliestTime, value2);
            if (oldEarliestTime != newEarliestTime) {
                ant.getEarliestTime().get(bestIndexTour).set(k, newEarliestTime);
            } else {
                break;
            }
        }
    }

    private static void computeRouteVariables(Ant ant, VRPTW vrp, int startTourIndex) {
        ArrayList<Request> reqList = vrp.getRequests();
        double distances[][] = vrp.getProblem().getDistance();
        // For every customer already included in the solution, compute route variables (earliest time a
        // delivery can be made at a customer and the latest time a delivery can be made at a customer)
        // used when checking feasibility of an insertion
        int city, previousCity, nextCity;
        double value, value1, value2;
        // Move forward in every tour and compute the values for the earliest time
        for (int index = startTourIndex; index < ant.getUsedVehicles(); index++) {
            int tourLength = ant.getTours().get(index).size();
            if (ant.getEarliestTime() == null) {
                System.out.println("Earliest time is null");
            }
            ant.getEarliestTime().add(index, new ArrayList<>(tourLength));
            for (int i = 0; i < tourLength; i++) {
                city = ant.getTours().get(index).get(i);
                // The current city (customer) is the depot
                if (((city + 1) == 0) && (i == 0)) {
                    value = reqList.get(0).getStartWindow();
                    ant.getEarliestTime().get(index).add(i, value);
                } else {
                    previousCity = ant.getTours().get(index).get(i - 1);
                    value1 = reqList.get(city + 1).getStartWindow();
                    value2 = ant.getEarliestTime().get(index).get(i - 1) + distances[previousCity + 1][city + 1] + reqList.get(previousCity + 1).getServiceTime();
                    value = Math.max(value1, value2);
                    ant.getEarliestTime().get(index).add(i, value);
                }
            }
        }
        // Move backward in every tour and compute the values for the latest time
        for (int index = startTourIndex; index < ant.getUsedVehicles(); index++) {
            int tourLength = ant.getTours().get(index).size();
            ant.getLatestTime().add(index, new ArrayList<>(Collections.nCopies(tourLength, 0.0)));
            for (int i = tourLength - 1; i >= 0; i--) {
                city = ant.getTours().get(index).get(i);
                if (i == tourLength - 1) {
                    value = reqList.get(0).getEndWindow();
                    value1 = reqList.get(city + 1).getEndWindow();
                    value2 = value - distances[city + 1][0] - reqList.get(city + 1).getServiceTime();
                    value = Math.min(value1, value2);
                    ant.getLatestTime().get(index).set(i, value);
                } else {
                    nextCity = ant.getTours().get(index).get(i + 1);
                    value1 = reqList.get(city + 1).getEndWindow();
                    value2 = ant.getLatestTime().get(index).get(i + 1) - distances[city + 1][nextCity + 1] - reqList.get(city + 1).getServiceTime();
                    value = Math.min(value1, value2);
                    ant.getLatestTime().get(index).set(i, value);
                }
            }
        }
    }

    /*
     * Check if it is feasible to insert the client denoted by customer, in the tour designated by
     * indexTour; the customer is checked if it can be inserted between the nodes at position previousPos and nextPos
     */
    static boolean isFeasibleInsertion(Ant ant, VRPTW vrp, int customer, int indexTour, int previousPos, int nextPos) {
        boolean isFeasible1 = false;
        double distances[][] = vrp.getProblem().getDistance();
        double currentQuantity, arrivalTime, beginService, earliestTime, latestTime;
        double value1, value2, value3, value4;
        ArrayList<Request> reqList = vrp.getRequests();
        int previousCity = ant.getTours().get(indexTour).get(previousPos);
        int nextCity = ant.getTours().get(indexTour).get(nextPos);
        currentQuantity = ant.getCurrentQuantity().get(indexTour) + reqList.get(customer + 1).getDemand();
        arrivalTime = ant.getBeginService()[previousCity + 1] + reqList.get(previousCity + 1).getServiceTime() + distances[previousCity + 1][customer + 1];
        beginService = Math.max(arrivalTime, reqList.get(customer + 1).getStartWindow());
        value1 = reqList.get(customer + 1).getStartWindow();
        value2 = ant.getEarliestTime().get(indexTour).get(previousPos) + distances[previousCity + 1][customer + 1] + reqList.get(previousCity + 1).getServiceTime();
        earliestTime = Math.max(value1, value2);
        value3 = reqList.get(customer + 1).getEndWindow();
        value4 = ant.getLatestTime().get(indexTour).get(nextPos) - distances[customer + 1][nextCity + 1] - reqList.get(customer + 1).getServiceTime();
        latestTime = Math.min(value3, value4);
        if ((currentQuantity <= vrp.getCapacity()) && (earliestTime <= latestTime) && (beginService <= reqList.get(customer + 1).getEndWindow())) {
            isFeasible1 = true;
        }
        return isFeasible1;
    }

    static class Insertion {

        // Id of the un-routed customer that should be inserted in a route in the best feasible insertion place
        private int customer;

        // Id of the route or the index of the tour given by the best feasible insertion place
        private int indexTour;

        /*
         * The previous node from the selected tour given by the best feasible insertion place.
         * The un-routed customer will be inserted in the tour having the index = indexTour and in front
         * of this node/customer/city
         */
        private int previousNode;

        /*
         * Computed value for the metric for a certain insertion place, considered when deciding which is
         * the best feasible insertion place for an un-routed customer
         */
        private double score;

        public Insertion(int customer_, int indexTour_, int previousNode_, double score_) {
            this.customer = customer_;
            this.indexTour = indexTour_;
            this.previousNode = previousNode_;
            this.score = score_;
        }

        public int getCustomer() {
            return customer;
        }

        public int getIndexTour() {
            return indexTour;
        }

        public int getPreviousNode() {
            return previousNode;
        }

        public double getScore() {
            return score;
        }
    }
}
