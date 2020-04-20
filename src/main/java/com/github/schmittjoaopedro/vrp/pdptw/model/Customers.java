package com.github.schmittjoaopedro.vrp.pdptw.model;

import java.util.Vector;

public class Customers {

    private static Customers cs = new Customers();

    private Vector<Customer> customerList;

    private Customers() {
        customerList = new Vector<Customer>();
    }

    public void add(Customer c) {
        customerList.add(c);
    }

    public Customer get(int index) {
        return customerList.get(index);
    }

    public int size() {
        return customerList.size();
    }

    public int findPair(Route target, int pickupIndex) {

        for (int i = pickupIndex + 1; i < target.size(); i++) {
            if (target.get(pickupIndex).getDelivery() == target.get(i).getID()) {
                return i;
            }
        }
        return -1;
    }

    public Customer findPair(Customer pickup) {

        Customer delivery = null;
        for (int i = 1; i < Customers.getSingleInstance().size(); i++) {
            if (this.get(i).getDemand() < 0) {
                if (this.get(i).getPickup() == pickup.getID()) {
                    delivery = this.get(i);
                    break;
                }
            }
        }
        return delivery;
    }

    public static Customers getSingleInstance() {
        return cs;
    }
}