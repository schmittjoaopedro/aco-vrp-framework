package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.pdptw.model.Customers;
import com.github.schmittjoaopedro.vrp.pdptw.model.Route;
import com.github.schmittjoaopedro.vrp.pdptw.model.Solution;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;

import java.util.Vector;

public class NeighborhoodFunc {

    private VehicleProperty vp;

    public NeighborhoodFunc(VehicleProperty vp) {
        this.vp = vp;
    }

    /*
     *	PD-shift, PD-exchange, and PD-rearrange
     */

    // PD-shift
    private Vector<Solution> PDS(Solution s) {
        Vector<Solution> nPDS = new Vector<Solution>();
        Vector<Solution> newSolutionList;
        // select route pairs
        for (int i = 0; i < s.size(); i++) {
            for (int j = i + 1; j < s.size(); j++) {
                //shift operation
                newSolutionList = shift(s, i, j); // used twice
                nPDS.addAll(newSolutionList);
                newSolutionList = shift(s, j, i);
                nPDS.addAll(newSolutionList);
            }
        }
        return nPDS;
    }

    private Vector<Solution> shift(Solution s, int r1Index, int r2Index) {
        Vector<Solution> result = new Vector<Solution>();
        Solution newSolution;
        // select PD pairs
        int pickup;
        int delivery;
        for (int i = 0; i < s.get(r1Index).size() - 1; i++) {
            pickup = i;
            delivery = Customers.getSingleInstance().findPair(s.get(r1Index), pickup);
            if (delivery > 0) {
                newSolution = s.clone(r1Index, r2Index);
                if (newSolution.shift(r1Index, r2Index, pickup, delivery, this.vp)) {
                    if (s.size() == newSolution.size() + 1) {
                        newSolution.setRecorded(1);
                        if (r1Index < r2Index) {
                            moveForwardRecordedRoutes(newSolution, -1, r2Index - 1);
                        } else {
                            moveForwardRecordedRoutes(newSolution, -1, r2Index);
                        }
                    } else {
                        newSolution.setRecorded(2);
                        moveForwardRecordedRoutes(newSolution, r1Index, r2Index);
                    }

                    result.add(newSolution);
                }
            }
        }
        return result;
    }

    // PD-exchange
    private Vector<Solution> PDE(Solution s) {
        Vector<Solution> nPDE = new Vector<Solution>();
        Vector<Solution> newSolutionList;
        // select route pairs
        for (int i = 0; i < s.size(); i++) {
            for (int j = i + 1; j < s.size(); j++) {
                //exchange operation
                newSolutionList = exchange(s, i, j);
                nPDE.addAll(newSolutionList);
            }
        }
        return nPDE;
    }

    private Vector<Solution> exchange(Solution s, int r1Index, int r2Index) {
        Vector<Solution> result = new Vector<Solution>();
        Solution newSolution;
        // select PD pairs
        int pickup1;
        int delivery1;
        int pickup2;
        int delivery2;
        for (int i = 0; i < s.get(r1Index).size() - 1; i++) {
            for (int j = 0; j < s.get(r2Index).size() - 1; j++) {
                pickup1 = i;
                pickup2 = j;
                delivery1 = Customers.getSingleInstance().findPair(s.get(r1Index), pickup1);
                delivery2 = Customers.getSingleInstance().findPair(s.get(r2Index), pickup2);
                if (delivery1 > 0 && delivery2 > 0) {
                    newSolution = s.clone(r1Index, r2Index);
                    if (newSolution.exchange(r1Index, r2Index, pickup1, delivery1, pickup2, delivery2, this.vp)) {
                        moveForwardRecordedRoutes(newSolution, r1Index, r2Index);
                        result.add(newSolution);
                    }
                }
            }
        }
        return result;
    }

    public Vector<Solution> findNeighbors(Solution s) {
        Vector<Solution> nPDS = PDS(s);
        Vector<Solution> nPDE = PDE(s);
        Vector<Solution> neighbors = new Vector<Solution>();
        if (nPDS.size() != 0) {
            neighbors.addAll(nPDS);
        }
        if (nPDE.size() != 0) {
            neighbors.addAll(nPDE);
        }
        if (neighbors.size() == 0) {
            neighbors.add(s);
        }
        return neighbors;
    }

    // PD-rearrange
    private Vector<Solution> PDR(Solution s, int times) {
        Vector<Solution> result = new Vector<Solution>();
        int routeIndex = -1;
        int pickup = -1;
        int delivery = -1;
        Solution newSolution;
        for (int i = 0; i < times; i++) { // optimize N times
            routeIndex = (int) Math.random() * (s.size() + 1);
            pickup = (int) (Math.random() * (s.get(routeIndex).size() - 1));
            for (int j = 0; j < s.get(routeIndex).size(); j++) {
                if (s.get(routeIndex).get(pickup).getDemand() > 0) {
                    break;
                } else {
                    pickup = (pickup + 1) % s.get(routeIndex).size();
                }
            }
            if ((delivery = Customers.getSingleInstance().findPair(s.get(routeIndex), pickup)) > 0) {
                // insert into new(maybe the same) feasible position
                newSolution = s.clone(routeIndex);
                if (newSolution.rearrange(routeIndex, pickup, delivery, this.vp)) {
                    result.add(newSolution);
                }
            }

        }
        if (result.size() == 0) {
            result.add(s);
        }
        return result;
    }

    // PD-rearrange
    private Solution PDR_BSF(Solution s) {
        int pickup, delivery;
        Solution newSolution;
        Solution bestSolution = s;
        for (int routeIndex = 0; routeIndex < s.size(); routeIndex++) { // optimize N routes
            for (int i = 0; i < s.get(routeIndex).size() - 1; i++) {
                pickup = i;
                delivery = Customers.getSingleInstance().findPair(s.get(routeIndex), pickup);
                if (delivery > 0) {
                    newSolution = s.clone(routeIndex);
                    if (newSolution.rearrange(routeIndex, pickup, delivery, this.vp)) {
                        if (compareSolutionCost(newSolution.cost(), bestSolution.cost()) > 0) {
                            bestSolution = newSolution;
                        }
                    }
                }
            }
        }
        return bestSolution;
    }

    private Solution findBestNeighbor(Vector<Solution> neighbors) {
        int index = 0;
        double[] currentBestCost = neighbors.get(0).cost();
        for (int i = 1; i < neighbors.size(); i++) {
            if (compareSolutionCost(neighbors.get(i).cost(), currentBestCost) > 0) {
                currentBestCost = neighbors.get(i).cost();
                index = i;
            }
        }
        return neighbors.get(index);
    }

    // Cost(S) = a*M + b*Dist(S) + c*ST(S) + d*WT(S)
    // a < b < c < d
    private int compareSolutionCost(double[] targetCost, double[] currentBestCost) {
        if (Math.round(targetCost[0]) != Math.round(currentBestCost[0])) {
            return (Math.round(targetCost[0]) < Math.round(currentBestCost[0])) ? 1 : -1;
        }
        for (int i = 1; i < 4; i++) {
            if (targetCost[i] != currentBestCost[i]) {
                return (targetCost[i] < currentBestCost[i]) ? 1 : -1;
            }
        }
        return 0;
    }

    private void moveForwardRecordedRoutes(Solution newSolution, int r1Index, int r2Index) {
        if (newSolution.getRecorded() == 1) {
            if (r1Index == -1) {
                Route r2 = newSolution.removeAt(r2Index);
                newSolution.insertAt(0, r2);
            } else {
                Route r1 = newSolution.removeAt(r1Index);
                newSolution.insertAt(0, r1);
            }
        }
        if (newSolution.getRecorded() == 2) {
            if (r1Index < r2Index) {
                Route r2 = newSolution.removeAt(r2Index);
                Route r1 = newSolution.removeAt(r1Index);
                newSolution.insertAt(0, r2);
                newSolution.insertAt(0, r1);
            } else {
                Route r1 = newSolution.removeAt(r1Index);
                Route r2 = newSolution.removeAt(r2Index);
                newSolution.insertAt(0, r1);
                newSolution.insertAt(0, r2);
            }
        }
    }

    /**
     * Descent Local Search
     * <p>
     * Input: a solution S
     * Output: a local optimal solution Sb
     */
    public Solution DLS_PDS_PDE(Solution s) {
        Solution sBest = s; // Sb <- S
        double[] currentBestCost = sBest.cost();
        while (true) {
            // Select the best solution Sb' with minimum object cost within a defined neighborhood of Sb
            Vector<Solution> neighbors = findNeighbors(sBest);
            Solution sNewBest = findBestNeighbor(neighbors);
            // If Cost(Sb') < Cost(Sb)
            if (compareSolutionCost(sNewBest.cost(), currentBestCost) > 0) {
                // Sb <- Sb'
                currentBestCost = sNewBest.cost();
                sBest = sNewBest;
            } else {
                break;
            }
        }
        return sBest;
    }

    public Solution DLS_PDR(Solution s, int times) {
        Solution sBest = s;
        double[] currentBestCost = sBest.cost();
        while (true) {
            Vector<Solution> neighbors = PDR(sBest, times);
            Solution sNewBest = findBestNeighbor(neighbors);
            if (compareSolutionCost(sNewBest.cost(), currentBestCost) > 0) {
                currentBestCost = sNewBest.cost();
                sBest = sNewBest;
            } else {
                break;
            }
        }
        return sBest;
    }

    public Solution DLS_PDR(Solution s) {
        Solution sBest = s;
        while (true) {
            Solution sNewBest = PDR_BSF(s);
            if (compareSolutionCost(sNewBest.cost(), sBest.cost()) > 0) {
                sBest = sNewBest;
            } else {
                break;
            }
        }
        return sBest;
    }
}
