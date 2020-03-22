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
        return ((100.0 * experiment / literature) - 100.0) / 100.0;
    }

    public static void main(String[] args) {
        System.out.println("25%");
        System.out.println(gap(200, 150));
        System.out.println(gap(200, 200));
        System.out.println(gap(200, 250));

        System.out.println("50%");
        System.out.println(gap(300, 150));
        System.out.println(gap(300, 300));
        System.out.println(gap(300, 450));

        System.out.println("150%");
        System.out.println(gap(100, -50));
        System.out.println(gap(100, 100));
        System.out.println(gap(100, 250));

        // Antiga
        //System.out.println(1.0 - ((170.07 / 6.0) / (294.47 / 6.0))); // 0.42%
        //System.out.println(gap(170.07 / 6.0, 294.47 / 6.0)); // 0.73%



    }


}
