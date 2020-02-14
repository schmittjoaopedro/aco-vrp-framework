package com.github.schmittjoaopedro.vrp.thesis;

public class MathUtils {

    public static double getEuclideanDistance(double x1, double x2, double y1, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

    public static double round(double value) {
        return round(value, 2); // Use same number of decimal places as Coelho
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double gap(double literature, double experiment) {
        return (100.0 * experiment / literature) - 100.0;
    }

}
