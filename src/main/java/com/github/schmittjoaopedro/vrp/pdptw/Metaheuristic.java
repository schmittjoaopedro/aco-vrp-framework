package com.github.schmittjoaopedro.vrp.pdptw;

import java.util.Vector;

import com.github.schmittjoaopedro.vrp.pdptw.model.Customer;
import com.github.schmittjoaopedro.vrp.pdptw.model.Customers;
import com.github.schmittjoaopedro.vrp.pdptw.model.Route;
import com.github.schmittjoaopedro.vrp.pdptw.model.Solution;
import com.github.schmittjoaopedro.vrp.pdptw.model.Tabu;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;

public class Metaheuristic {

    private final int INITIALTEMPERATURE;
    private int TEMPERATURE;
    private double COOLINGRATIO;
    private int MSNI;        // max number of iterations without improvement in annealing procedure

    private VehicleProperty vp;
    private NeighborhoodFunc nf;
    private ReorderRecordedRoutes rrr;

    public Metaheuristic(int initialTemperature, double coolingRatio, int msni, VehicleProperty vp) {
        this.INITIALTEMPERATURE = initialTemperature;
        this.TEMPERATURE = initialTemperature;
        this.COOLINGRATIO = coolingRatio;
        this.MSNI = msni;
        this.vp = vp;
        this.nf = new NeighborhoodFunc(this.vp);
        this.rrr = new ReorderRecordedRoutes(this.vp);
    }

	/**
	 * Tabu embedded SA
	 * @param input a solution S
	 * @return sBest a local optimal solution
	 */
	public Solution tabu_Embedded_SA(Solution input) {
        // restart simulated annealing
        this.TEMPERATURE = this.INITIALTEMPERATURE;
        //Tabu.getSingleInstance().clear();
        // Obtain a solution Sb from DSL within nPDS(S) U nPDE(S)
        Solution Sb = nf.DLS_PDS_PDE(input);
        // Sb <- Perform a DSL withing NPR(S)
        Sb = nf.DLS_PDR(Sb, 15); // PDR 15 times
        int NoImpr = 0;
        Solution s = Sb;
        double[] currentBestCost = Sb.cost();
        while (NoImpr < this.MSNI) {
        	// Use METROPOLIS_PROC to obtain a feasible solution S' which was not recorded in
			// TABU set, from neighborhood nPDS(S) U nPDE(S), record the two routes witch
			// PD-Shift/PD-Exchange operator performed
            Solution s_temp = metropolis_Proc(s);
            // Reduce number of vehicles by moving PD-Pairs out from one route and insert them
			// into other routes based on criteria of minimal increment in objective cost
            s_temp = reduceVehicleNumber(s_temp);
            // Use Solomon's Insertion Procedure to respectively re-order the two recorded routes of S'
			// in Step 5.1
            if (s_temp.getRecorded() > 0) {
                if (s_temp.getRecorded() == 1) {
                    rrr.reorder(0, s_temp);
                } else {
                    rrr.reorder(1, s_temp);
                    rrr.reorder(0, s_temp);
                }
            }
            // Sb' <- perform a DSL within nPDS(S') U nPDE(S')
            Solution Sb_temp = nf.DLS_PDS_PDE(s_temp);
            // Sb' <- perform a DSL within nPDR(Sb')
            Sb_temp = nf.DLS_PDR(Sb_temp, 15); // PDR 15 times
			// If Cost(Sb') < Cost(Sb) then
            if (compareSolutionCost(Sb_temp.cost(), currentBestCost) > 0) {
                currentBestCost = Sb_temp.cost();
                Sb = Sb_temp;
                NoImpr = 0;
            } else {
                NoImpr++;
            }
            s = Sb_temp;
        }
        return Sb;
    }

    private Solution reduceVehicleNumber(Solution s_temp) {
        // find the shortest route to work on
        int[] list = new int[s_temp.size()];
        for (int i = 0; i < list.length; i++) {
            list[i] = s_temp.get(i).size();
        }
        int times = 0;
        int shortestRoute = 0;
        Solution result = null;
        while (times < list.length) {
            // try route with smallest size first
            shortestRoute = findShortest(list);
            // insertion trial
            result = insertionTrial(shortestRoute, s_temp);
            if (result != null) {
                break;
            }
            list[shortestRoute] = 0;
            times++;
        }
        if (result != null) {
            return result;
        } else {
            return s_temp;
        }
    }

    private int findShortest(int[] list) {
        int min = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == 0) {
                continue;
            } else {
                if (min == -1) {
                    min = i;
                } else {
                    if (list[i] < list[min]) {
                        min = i;
                    }
                }
            }

        }
        return min;
    }

    private Solution insertionTrial(int shortestRoute, Solution s_temp) {
        Solution result = s_temp.clone();
        Route source = result.removeAt(shortestRoute);
        boolean isSuccessful = false;
        int currentIndex = -1;
        for (int i = 0; i < source.size() / 2; i++) {
            // pick a PD-pair
            Customer pickup = null;
            Customer delivery = null;
            for (int j = currentIndex + 1; j < source.size(); j++) {
                if (source.get(j).getDemand() > 0) {
                    currentIndex = j;
                    pickup = source.get(j);
                    delivery = source.get(Customers.getSingleInstance().findPair(source, j));
                    break;
                }
            }
            isSuccessful = findBestInsertion(pickup, delivery, result);
            if (!isSuccessful) {
                break;
            }
        }
        if (!isSuccessful) {
            return null;
        } else {
            if (s_temp.getRecorded() == 1) {
                if (shortestRoute == 0) {
                    result.setRecorded(0);
                }
            }
            if (s_temp.getRecorded() == 2) {
                if (shortestRoute == 0 || shortestRoute == 1) {
                    result.setRecorded(1);
                }
            }
            return result;
        }

    }

    private boolean findBestInsertion(Customer pickup, Customer delivery, Solution result) {
        int targetRoute = -1;
        int pickupInsertion = -1;
        int deliveryInsertion = -1;
        double[] currentBestCost = null;
        for (int i = 0; i < result.size(); i++) {
            for (int m = 0; m < result.get(i).size() + 1; m++) {
                for (int n = m; n < result.get(i).size() + 1; n++) {
                    if (result.get(i).isInsertionFeasible(pickup, delivery, m, n, this.vp)) {
                        if (currentBestCost == null) {
                            targetRoute = i;
                            pickupInsertion = m;
                            deliveryInsertion = n;
                            currentBestCost = result.get(i).costIncrement(pickup, delivery, m, n);
                        } else {
                            if (compareCostIncrement(result.get(i).costIncrement(pickup, delivery, m, n), currentBestCost) > 0) {
                                targetRoute = i;
                                pickupInsertion = m;
                                deliveryInsertion = n;
                                currentBestCost = result.get(i).costIncrement(pickup, delivery, m, n);
                            }
                        }
                    }
                }
            }
        }
        if (targetRoute != -1) {
            // insert
            result.get(targetRoute).insertAt(deliveryInsertion, delivery);
            result.get(targetRoute).insertAt(pickupInsertion, pickup);
            return true;
        } else {
            return false;
        }
    }

    private int compareCostIncrement(double[] targetCost, double[] currentBestCost) {
        for (int i = 0; i < 3; i++) {
            if (targetCost[i] != currentBestCost[i]) {
                return (targetCost[i] < currentBestCost[i]) ? 1 : -1;
            }
        }
        return 0;
    }

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

    private Solution metropolis_Proc(Solution s) {
        Vector<Solution> neighbors = nf.findNeighbors(s);
        Solution newSolution;
        int delta;
        double prob;
        // while not yet accept an neighboring solution of S do
        while (true) {
            int random = (int) (Math.random() * neighbors.size());
            // S' <- get a random neighbor of S which is not in tabu set, within nPDS(S') U nPDE(S')
            int newRandom = random;
            newSolution = neighbors.get(random);
            while (Tabu.getSingleInstance().isExist(newSolution.getEigenvalue())) {
                newRandom = (newRandom + 1) % neighbors.size();
                if (newRandom == random) {
                    return s;
                } else {
                    newSolution = neighbors.get(newRandom);
                }
            }
            // \delta <- SACost(S') - SACost(S)
            delta = newSolution.saCost() - s.saCost();
            if (delta <= 0) {
                prob = 1;
            } else {
                prob = Math.pow(Math.E, -(delta / TEMPERATURE));
            }
            if (Math.random() * 100 <= prob * 100) {
            	// Update global annealing temperature: T <- collingRation * temperature
                TEMPERATURE = (int) (COOLINGRATIO * TEMPERATURE);
                if (TEMPERATURE == 0) {
                    TEMPERATURE = 1;
                }
                // Record S' into TABU set
                Tabu.getSingleInstance().offerAndPoll(newSolution.getEigenvalue());
                break;
            }
        }
        return newSolution;
    }
}
