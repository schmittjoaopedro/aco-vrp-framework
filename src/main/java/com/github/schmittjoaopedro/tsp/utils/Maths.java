package com.github.schmittjoaopedro.tsp.utils;

import com.github.schmittjoaopedro.tsp.aco.Ant;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.List;
import java.util.function.Function;

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

    public static double getEuclideanDistance(double x1, double x2, double y1, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
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

    public static double getStd(List<Double> costs) {
        double dev = 0.0;
        double mean = getMean(costs);
        if (costs.size() <= 1)
            return dev;
        for (int j = 0; j < costs.size(); j++) {
            dev += (costs.get(j) - mean) * (costs.get(j) - mean);
        }
        return Math.sqrt(dev / (double) (costs.size() - 1));
    }

    public static double sd(double numArray[]) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for (double num : numArray) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    public static double getPopMean(List<Ant> antPopulation) {
        double cost = 0.0;
        for (Ant ant : antPopulation) {
            cost += ant.getCost();
        }
        return cost / (double) antPopulation.size();
    }

    public static double getMean(List<Double> costs) {
        double mean = 0.0;
        for (Double cost : costs) {
            mean += cost;
        }
        return mean / (double) costs.size();
    }


    public static long minutes(long n) {
        return n * seconds(60);
    }

    public static long seconds(long n) {
        return n * 1000;
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double round(double value) {
        return round(value, 6); // Use same number of decimal places as Coelho
    }

    public static int getValueByInequality(int[][] matrix, FunctionWithTwoArgs<Integer, Integer, Integer> function) {
        int min = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                min = function.apply(min, matrix[i][j]);
            }
        }
        return min;
    }

}
