package com.github.schmittjoaopedro.vrp.pdptw;

import java.util.Vector;

import com.github.schmittjoaopedro.vrp.pdptw.model.Customer;
import com.github.schmittjoaopedro.vrp.pdptw.model.InsertionInformation;
import com.github.schmittjoaopedro.vrp.pdptw.model.Route;
import com.github.schmittjoaopedro.vrp.pdptw.model.Solution;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;

public class ReorderRecordedRoutes {
    private VehicleProperty vp;

    public ReorderRecordedRoutes(VehicleProperty vp) {
        this.vp = vp;
    }

    public void reorder(int routeIndex, Solution s) {
        InsertionHeuristic ih = new InsertionHeuristic(s.get(routeIndex), this.vp);
        Route newRoute = new Route();

        // initialize a seed pd pair
        int pickupIndex = ih.selectSeed();
        Customer delivery = ih.getUnroutedCustomers().remove(pickupIndex + 1);
        Customer pickup = ih.getUnroutedCustomers().remove(pickupIndex);
        newRoute.add(pickup); // pickup
        newRoute.add(delivery); // delivery

        while (ih.getUnroutedCustomers().size() != 0) {
            // get all c1 values
            Vector<InsertionInformation> temp = ih.computeMinimalC1(newRoute);
            if (temp.size() == 0) {
                break;
            } else {
                // get best c2 value
                InsertionInformation best = ih.computeMaximalC2(temp);
                // insert pd pair
                ih.insert(newRoute, best);
            }
        }
        // replace the route in s with new route
        if (ih.getUnroutedCustomers().size() == 0) {
            s.removeAt(routeIndex);
            s.insertAt(routeIndex, newRoute);
        }
    }
}
