package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;

import java.io.File;

public class App {

    public static void main(String[] args) {
        String problemInstance = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\tsp\\kroA100.tsp";
        File file = new File(problemInstance);
        Graph graph = GraphFactory.createGraphFromTSP(file);
        MMAS_TSP algorithm = new MMAS_TSP(
                graph, // Problem instance
                0.02, // Evaporation (rho)
                1500, // Max iterations
                1 // Seed
        );
        algorithm.run();
    }

}
