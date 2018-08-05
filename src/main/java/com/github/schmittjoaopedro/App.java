package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_TSP;

public class App {

    public static void main(String[] args) {

        String problemInstance = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\tsp\\kroA100.tsp";

        MMAS_TSP algorithm = new MMAS_TSP(
                problemInstance, // Problem instance
                0.02, // Evaporation (rho)
                1500, // Max iterations
                1 // Seed
        );
        algorithm.run();
    }

}
