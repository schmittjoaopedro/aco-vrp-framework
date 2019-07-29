package com.github.schmittjoaopedro.vrp.mpdptw.alns;

public class Parameters {

    /*
     * Parameters section.
     *
     * (sigma1 > sigma2 > sigma3)
     */

    // Ropke and Pisinger
    /*private static final int DEFAULT_MAX_ITERATIONS = 25000;
    private double tolerance = 0.05; // (w)
    private double coolingRate = 0.99975; // reduction factor of acceptance methods (c)
    private double sigma1 = 33; // reward for finding a new global best solution
    private double sigma2 = 9; // reward for finding an improving, not global best, solution
    private double sigma3 = 13; // reward for finding an accepted non-improving solution
    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private int dMin(double n) { return (int) Math.min(30.0, 0.1 * n); }
    private int dMax(double n) { return (int) Math.min(60.0, 0.4 * n); }
    private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }
    private String acceptMethod = "SA";
    private double minWeight = 1.0;
    private int segment = 100;
    private double noiseControl = 0.025;*/

    // Lutz
    /*private static final int DEFAULT_MAX_ITERATIONS = 10000;
    private double tolerance = 0.01; // (w)
    private double coolingRate = 0.9997; // reduction factor of acceptance methods (c)
    private double sigma1 = 135; // reward for finding a new global best solution
    private double sigma2 = 70; // reward for finding an improving, not global best, solution
    private double sigma3 = 25; // reward for finding an accepted non-improving solution
    private double rho = 0.35; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private int dMin(double n) { return (int) (0.075 * n); }
    private int dMax(double n) { return (int) (0.275 * n); }
    private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }
    private String acceptMethod = "TA";
    private double minWeight = 1.0;
    private int segment = 100;
    private double noiseControl = 0.43;*/

    // Coelho
    /*private static final int DEFAULT_MAX_ITERATIONS = 100000;
    private double tolerance = 0.05; // (w)
    private double coolingRate = 0.9995; // reduction factor of acceptance methods (c)
    private double sigma1 = 33; // reward for finding a new global best solution
    private double sigma2 = 20; // reward for finding an improving, not global best, solution
    private double sigma3 = 13; // reward for finding an accepted non-improving solution
    private double rho = 0.1; // Reaction factor, controls how quickly the weight adjustment reacts to changes in the operators performance. (p)
    private String acceptMethod = "SA";
    private double minWeight = 1.0;
    private int segment = 100;
    private double noiseControl = 0.025;
    private int dMin(double n) { return (int) Math.max(1, Math.min(6, 0.15 * n)); }
    private int dMax(double n) { return (int) Math.min(18, 0.4 * n); }*/

    //private double getInitialT() { return (1.0 + tolerance) * initialCost; }
    //private double getInitialT() { return (initialCost * tolerance) / Math.log(2); }

    public double tolerance;

    public double minT;

    public double initialT;

    public double sigma1;

    public double sigma2;

    public double sigma3;

    public double rho;

    public String acceptMethod;

    public double minWeight;

    public double noiseControl;

    public int segment;

    public double initialCost;

    public double coolingRate;

}
