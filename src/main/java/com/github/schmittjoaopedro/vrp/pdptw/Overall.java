package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.pdptw.model.Solution;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;


public class Overall {
    private VehicleProperty vp;

    public Overall(VehicleProperty vp) {
        this.vp = vp;
    }

    public void run() {
        ProduceInitialSolution pis = new ProduceInitialSolution(vp);
        Solution Sb = pis.produceInitialSolution();

        Metaheuristic m = new Metaheuristic(50, 0.95, 4, vp); // initial Temperature , cooling ratio , MSNI
        int K = ((20 * vp.getTotalVehicles()) / 3) + 1;                                        // K

        K = 5;

        int gNoImpro = 0;
        double[] currentBestCost = Sb.cost();
        while (gNoImpro < K) {
            Solution s = Sb;
            Solution Sb_temp = m.tabu_Embedded_SA(s);

            if (compareSolutionCost(Sb_temp.cost(), currentBestCost) > 0) {
                currentBestCost = Sb_temp.cost();
                Sb = Sb_temp;
                gNoImpro = 0;

                /////////////////
                double[] cost = Sb.cost();
                System.out.println("better NV: " + (int) cost[0] + " TC: " + Maths.round(cost[1], 2) + " SD: " + Maths.round(cost[2], 2) + " WT: " + Maths.round(cost[3]));

            } else {

                /////////////////
                double[] cost = Sb.cost();
                System.out.println("worse NV: " + (int) cost[0] + " TC: " + Maths.round(cost[1], 2) + " SD: " + Maths.round(cost[2], 2) + " WT: " + Maths.round(cost[3]));

                gNoImpro++;
            }
        }

        Sb.printSolution();

        double[] cost = Sb.cost();
        System.out.println("NV: " + (int) cost[0] + " TC: " + cost[1] + " SD: " + cost[2] + " WT: " + cost[3]);

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
}