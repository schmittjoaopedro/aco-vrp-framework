package com.github.schmittjoaopedro.vrp.pdptw.model;

import java.util.Vector;

public class Route {

    private Vector<Customer> route;

    public Route() {
        route = new Vector<Customer>();
        route.add(Customers.getSingleInstance().get(0));
        route.add(Customers.getSingleInstance().get(0));
    }

    public void add(Customer c) {
        route.add(route.size() - 1, c);
    }

    public Customer remove() {
        return route.remove(route.size() - 1);
    }

    public void insertAt(int index, Customer c) {
        route.add(index + 1, c);
    }

    public Customer removeAt(int index) {
        return route.remove(index + 1);
    }

    public Customer get(int index) {
        return route.get(index + 1);
    }

    public int size() {
        return route.size() - 2;
    }

    @Override
    public Route clone() {
        Route r = new Route();
        for (int i = 0; i < this.size(); i++) {
            r.add(this.get(i).clone());
        }
        return r;
    }

    public double[] cost() {
        int p1, p2;
        double TC = 0;
        double WT = 0;        // waiting time
        double ST = 0;        // service time
        double TT = 0;        // travel time

        TC += DTTable.getSingleInstance().getDTForDepot(this.get(0).getID()).getDistance();
        ST += this.get(0).getService_time();
        TT += DTTable.getSingleInstance().getDTForDepot(this.get(0).getID()).getTime();

        for (int i = 1; i < this.size(); i++) {
            p1 = this.get(i).getID();
            p2 = this.get(i - 1).getID();
            TC += DTTable.getSingleInstance().get(p1, p2).getDistance();
            ST += this.get(i).getService_time();
            TT += DTTable.getSingleInstance().get(p1, p2).getTime();
            if ((this.get(i).getE_time() - ST - TT) > 0) {
                WT += this.get(i).getE_time() - ST - TT;
            }
        }

        TC += DTTable.getSingleInstance().getDTForDepot(this.get(this.size() - 1).getID()).getDistance();
        TT += DTTable.getSingleInstance().getDTForDepot(this.get(this.size() - 1).getID()).getTime();

        double SD = ST + TT + WT;

        return new double[]{TC, SD, WT};
    }

    // cost increment when insert a PD pair
    public double[] costIncrement(Customer pickup, Customer delivery, int pickupInsertion, int deliveryInsertion) {
        Route target = this.clone();
        target.insertAt(deliveryInsertion, delivery);
        target.insertAt(pickupInsertion, pickup);

        double[] oldCost = this.cost();
        double[] newCost = target.cost();
        double[] costIncrement = new double[3];
        for (int i = 0; i < 3; i++) {
            costIncrement[i] = newCost[i] - oldCost[i];
        }

        return costIncrement;
    }

    // test whether it is feasible to insert into this gap
    public boolean isInsertionFeasible(Customer pickup, Customer delivery, int pInsert, int dInsert, VehicleProperty vp) {
        // precedence constraint
        if (pInsert > dInsert) {
            return false;
        }

        /*Route result = this.clone();
        result.insertAt(dInsert, delivery);
        result.insertAt(pInsert, pickup);
        // capacity constraint
        int currentCapacity = 0;
        for (int i = 0; i < result.size(); i++) {
            currentCapacity += result.get(i).getDemand();
            if (currentCapacity > vp.getCapacity()) {
                return false;
            }
        }
        // time window constraint
        double finishServiceTime = 0;
        double arrivalTime;
        for (int i = 0; i < result.size(); i++) {
            if (i == 0) {
                arrivalTime = DTTable.getSingleInstance().getDTForDepot(result.get(0).getID()).getTime();
                finishServiceTime = Math.max(arrivalTime, result.get(0).getE_time())
                        + result.get(0).getService_time();
            } else {
                arrivalTime = finishServiceTime + DTTable.getSingleInstance().get(new Key(result.get(i - 1).getID(), result.get(i).getID())).getTime();
                finishServiceTime = Math.max(arrivalTime, result.get(i).getE_time())
                        + result.get(i).getService_time();
            }
            if (arrivalTime > result.get(i).getL_time()) {
                return false;
            }
        }
        // check depot time window
        arrivalTime = finishServiceTime + DTTable.getSingleInstance().getDTForDepot(result.get(result.size() - 1).getID()).getTime();
        if (arrivalTime > Customers.getSingleInstance().get(0).getL_time()) {
            return false;
        }
        return true;*/
        dInsert++;
        int size = size() + 2;

        // capacity constraint
        int currentCapacity = 0;
        for (int i = 0; i < size; i++) {
            currentCapacity += getNode(i, pInsert, dInsert, pickup, delivery).getDemand();
            if (currentCapacity > vp.getCapacity()) {
                return false;
            }
        }
        // time window constraint
        double finishServiceTime = 0;
        double arrivalTime;
        Customer prev, curr;
        for (int i = 0; i < size; i++) {
            curr = getNode(i, pInsert, dInsert, pickup, delivery);
            if (i == 0) {
                arrivalTime = DTTable.getSingleInstance().getDTForDepot(curr.getID()).getTime();
                finishServiceTime = Math.max(arrivalTime, curr.getE_time()) + curr.getService_time();
            } else {
                prev = getNode(i - 1, pInsert, dInsert, pickup, delivery);
                arrivalTime = finishServiceTime + DTTable.getSingleInstance().get(prev.getID(), curr.getID()).getTime();
                finishServiceTime = Math.max(arrivalTime, curr.getE_time()) + curr.getService_time();
            }
            if (arrivalTime > curr.getL_time()) {
                return false;
            }
        }
        // check depot time window
        curr = getNode(size - 1, pInsert, dInsert, pickup, delivery);
        arrivalTime = finishServiceTime + DTTable.getSingleInstance().getDTForDepot(curr.getID()).getTime();
        if (arrivalTime > Customers.getSingleInstance().get(0).getL_time()) {
            return false;
        }
        return true;
    }

    private Customer getNode(int pos, int pIdx, int dIdx, Customer pickup, Customer delivery) {
        if (pos < pIdx) {
            return get(pos);
        } else if (pos == pIdx) {
            return pickup;
        } else if (pos < dIdx) {
            return get(pos - 1);
        } else if (pos == dIdx) {
            return delivery;
        } else {
            return get(pos - 2);
        }
    }

}
