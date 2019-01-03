package com.github.schmittjoaopedro.tsp.utils;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.aco.MMAS;

import java.util.List;

public class DebugLogger {

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

}
