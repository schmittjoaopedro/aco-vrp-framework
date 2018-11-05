package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Insertion {

    //id of the unrouted customer that should be inserted in a route in the best feasible insertion place
    public int customer;

    //id of the route or the index of the tour given by the best feasible insertion place
    public int indexTour;

    //the previous node from the selected tour given by the best feasible insertion place
    //the unrouted customer will be inserted in the the tour having the index = indexTour and in front
    //of this node/customer/city
    public int previousNode;

    //computed value for the metric for a certain insertion place, considered when deciding which is the best feasible insertion place
    //for an unrouted customer
    public double score;

    public Insertion() {}

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
