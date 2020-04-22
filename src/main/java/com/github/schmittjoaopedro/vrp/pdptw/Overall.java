package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import com.github.schmittjoaopedro.vrp.pdptw.model.Solution;
import com.github.schmittjoaopedro.vrp.pdptw.model.Tabu;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;


public class Overall {
    private VehicleProperty vp;
    private String name;

    public Overall(String name, VehicleProperty vp) {
        this.name = name;
        this.vp = vp;
    }

    public void run() {
        Long time = System.currentTimeMillis();
        ProduceInitialSolution pis = new ProduceInitialSolution(vp);
        // The better solution produced from Modified Solomon’s Insertion Algorithm
        Solution Sb = pis.produceInitialSolution();
        // initial Temperature , cooling ratio , MSNI
        Metaheuristic m = new Metaheuristic(50, 0.95, 4, vp);
        // the maximum number of children in a K-ary tree
        //int K = 5;
        int K = ((20 * vp.getTotalVehicles()) / 3) + 1;
        int gNoImpro = 0;
        Tabu.getSingleInstance().clear();
        double[] currentBestCost = Sb.cost();
        while (gNoImpro < K) {
            Solution s = Sb;
            Solution Sb_temp = m.tabu_Embedded_SA(s);
            // If Cost(Sb') < Cost(Sb) Then
            if (compareSolutionCost(Sb_temp.cost(), currentBestCost) > 0) {
                currentBestCost = Sb_temp.cost();
                Sb = Sb_temp;
                gNoImpro = 0;
                double[] cost = Sb.cost();
                System.out.print("\ngNoImpro = " + gNoImpro);
                System.out.print("\nbetter NV: " + (int) cost[0] + " TC: " + Maths.round(cost[1], 2) + " SD: " + Maths.round(cost[2], 2) + " WT: " + Maths.round(cost[3]) + " ");
            } else {
                System.out.print(".");
                double[] cost = Sb.cost();
                //System.out.println("gNoImpro = " + gNoImpro);
                //System.out.println("worse NV: " + (int) cost[0] + " TC: " + Maths.round(cost[1], 2) + " SD: " + Maths.round(cost[2], 2) + " WT: " + Maths.round(cost[3]));
                gNoImpro++;
            }
        }
        Sb.printSolution();
        double[] cost = Sb.cost();
        double timeSec = ((System.currentTimeMillis() - time) / 1000.0);
        System.out.println("Problem " + name);
        System.out.println("NV\tTC\tSD\tWT\tCT");
        System.out.println((int) cost[0] + "\t" + cost[1] + "\t" + cost[2] + "\t" + cost[3] + "\t" + timeSec);
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
