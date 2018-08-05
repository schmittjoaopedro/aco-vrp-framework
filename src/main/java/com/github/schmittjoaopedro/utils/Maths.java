package com.github.schmittjoaopedro.utils;

import com.github.schmittjoaopedro.aco.Ant;
import com.github.schmittjoaopedro.graph.Vertex;

import java.util.List;

public class Maths {

    public static double getEuclideanDistance(Vertex n1, Vertex n2) {
        double x1 = n1.getX() - n2.getX();
        double y1 = n1.getY() - n2.getY();
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static double getTSPEuclideanDistance(Vertex n1, Vertex n2) {
        double x1 = n1.getX() - n2.getX();
        double y1 = n1.getY() - n2.getY();
        return (int) (Math.sqrt(x1 * x1 + y1 * y1) + 0.5);
    }

    public static double getPopultionStd(List<Ant> population) {
        double dev = 0.0;
        double mean = getPopMean(population);
        if (population.size() <= 1)
            return dev;
        for (int j = 0; j < population.size(); j++) {
            dev += (population.get(j).getCost() - mean) * (population.get(j).getCost() - mean);
        }
        return Math.sqrt(dev / (double) (population.size() - 1));
    }

    public static double getPopMean(List<Ant> antPopulation) {
        double cost = 0.0;
        for (Ant ant : antPopulation) {
            cost += ant.getCost();
        }
        return cost / (double) antPopulation.size();
    }
}
