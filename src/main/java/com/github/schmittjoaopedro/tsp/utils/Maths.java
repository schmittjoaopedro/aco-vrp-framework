package com.github.schmittjoaopedro.tsp.utils;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.List;

public class Maths {

    public static double getTSPGeoDistance(Vertex n1, Vertex n2) {
        double deg, min;
        double lati, latj, longi, longj;
        double q1, q2, q3;
        int dd;
        double x1 = n1.getX(), x2 = n2.getX(), y1 = n1.getY(), y2 = n2.getY();

        deg = (int) x1;
        min = x1 - deg;
        lati = Math.PI * (deg + 5 * min / 3) / 180;
        deg = (int) x2;
        min = x2 - deg;
        latj = Math.PI * (deg + 5 * min / 3) / 180;

        deg = (int) y1;
        min = y1 - deg;
        longi = Math.PI * (deg + 5 * min / 3) / 180;
        deg = (int) y2;
        min = y2 - deg;
        longj = Math.PI * (deg + 5 * min / 3) / 180;

        q1 = Math.cos(longi - longj);
        q2 = Math.cos(lati - latj);
        q3 = Math.cos(lati + latj);
        dd = (int) (6378.388 * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
        return dd;
    }

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
