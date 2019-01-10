package com.github.schmittjoaopedro.tsp.utils;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.aco.MMAS;
import com.github.schmittjoaopedro.tsp.aco.ls.opt3.Opt3Operator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DebugLogger {

    public static MMAS currentMMAS;

    public static Opt3Operator current3opt;

    public static void logAntRoutes(List<Ant> antPop) {
        for (Ant a : antPop) {
            for (int i : a.getTour()) {
                System.out.print(i + ",");
            }
            System.out.println("");
        }
    }

    public static void logTotalInformation(MMAS mmas) {
        for (int i = 0; i < mmas.getTotal().length; i++) {
            for (int j = 0; j < mmas.getTotal().length; j++) {
                System.out.print(mmas.getTotal()[i][j] + ",");
            }
            System.out.println("");
        }
    }

    public static void logNNList(MMAS mmas) {
        for (int i = 0; i < mmas.getNnList().length; i++) {
            for (int j = 0; j < mmas.getNnList()[i].length; j++) {
                System.out.print(mmas.getNnList()[i][j].getId() + ",");
            }
            System.out.println("");
        }
    }

    public static void validIntegrityLocalSearch(int tour[], double costOld, double costNew) {
        if (costOld > costNew) {
            throw new RuntimeException("Invalid route cost: costOld > costNew");
        }
        Set<Integer> integrityIds = new HashSet<>();
        for (int i = 0; i < tour.length - 1; i++) {
            integrityIds.add(tour[i]);
        }
        if (integrityIds.size() != tour.length - 1) {
            throw new RuntimeException("Invalid route cost: ha duplicated IDs");
        }
    }

    public static double getLocalSearchCost() {
        return currentMMAS.fitnessEvaluation(current3opt.getResult());
    }

}
