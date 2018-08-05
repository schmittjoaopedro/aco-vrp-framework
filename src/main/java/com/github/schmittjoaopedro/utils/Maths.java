package com.github.schmittjoaopedro.utils;

import com.github.schmittjoaopedro.graph.Vertex;

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

}
