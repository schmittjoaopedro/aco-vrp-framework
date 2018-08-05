package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.MMAS_ADTSP;

public class App {

    public static void main(String[] args) {

        String problemInstance = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\tsp\\kroA100.tsp";

        MMAS_ADTSP algorithm = new MMAS_ADTSP(
                problemInstance, // Problem instance
                0.5, // Magnitude
                100, // Frequency
                0.02, // Evaporation (rho)
                1500, // Max iterations
                1 // Seed
        );
        algorithm.run();
    }

}
