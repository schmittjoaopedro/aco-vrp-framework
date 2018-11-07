package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class InOut {

    enum Distance_type {EUC_2D, CEIL_2D, GEO, ATT}

    public Distance_type distance_type;

    public int nTours; /* counter of number constructed tours */
    public int iteration; /* iteration counter */
    public int max_tries; /* maximum number of independent tries */
    public int max_tours; /* maximum number of tour constructions in one try */
    public int max_iterations; /* maximum number of iterations */

    public double max_time; /* maximal allowed run time of a try */
    public double timeUsed; /* time used until some given event */
    public double timePassed; /* time passed until some moment */
    public int currentTimeSlice;
    public int optimal; /* optimal solution or bound to find */

    public double branch_fac; /* If branching factor < branch_fac => update trails */
    public double lambda; /* Parameter to determine branching factor */

    public int foundBest; /* iteration in which best solution is found */

    public int noEvaluations = 0;
    public int noSolutions = 0;   /* counter for the total number of feasible solutions */
    public boolean isDiscreteTime = false;

    public InOut() {
        super();
    }

    public InOut(boolean isDiscreteTime) {
        this.isDiscreteTime = isDiscreteTime;
    }

    public void setDefaultAsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = -1;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
    }

    public void setDefaultAcsParameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.nAnts = 10;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 1.0;
        ants.rho = 0.9;
        //used in local pheromone update
        ants.local_rho = 0.9;
        ants.q0 = 0.9;
    }

    //set default parameter settings
    public void setDefaultParameters(Ants ants) {
        /* number of ants and number of nearest neighbors in tour construction */
        ants.nAnts = 25;
        ants.nnAnts = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q0 = 0.0;
        ants.pheromonePreservation = 0.3;
        max_tries = 10;
        max_tours = 10 * 20;
        //10 seconds allowed for running time; it is used in the termination condition of ACO
        max_time = 100;
        //maximum number of allowed iterations until the ACO algorithm stops
        max_iterations = 3000;
        optimal = 1;
        branch_fac = 1.00001;
        ants.uGb = Integer.MAX_VALUE;
        ants.acsFlag = false;
        ants.asFlag = false;
        distance_type = Distance_type.EUC_2D;
    }

    //compute the average node lambda-branching factor, where l designates the lambda value
    public double node_branching(double l, VRPTW vrptw, Ants ants) {
        int i, m;
        double min, max, cutoff;
        double avg;
        double[] num_branches;
        num_branches = new double[vrptw.n + 1];
        for (m = 0; m < (vrptw.n + 1); m++) {
            /* determine max, min to calculate the cutoff value */
            min = ants.pheromone[m][vrptw.instance.nnList[m][0]];
            max = ants.pheromone[m][vrptw.instance.nnList[m][0]];
            for (i = 1; i < ants.nnAnts; i++) {
                if (ants.pheromone[m][vrptw.instance.nnList[m][i]] > max)
                    max = ants.pheromone[m][vrptw.instance.nnList[m][i]];
                if (ants.pheromone[m][vrptw.instance.nnList[m][i]] < min)
                    min = ants.pheromone[m][vrptw.instance.nnList[m][i]];
            }
            cutoff = min + l * (max - min);
            for (i = 0; i < ants.nnAnts; i++) {
                if (ants.pheromone[m][vrptw.instance.nnList[m][i]] > cutoff)
                    num_branches[m] += 1.;
            }
        }
        avg = 0.;
        for (m = 0; m < (vrptw.n + 1); m++) {
            avg += num_branches[m];
        }
        /* Norm branching factor to minimal value 1 */
        return (avg / (double) ((vrptw.n + 1) * 2));
    }

    public float average(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++)
            sum += array[i];
        return (float) sum / (float) array.length;
    }

    public float variance(int[] array) {
        double var = 0;
        double average = average(array);
        for (int i = 0; i < array.length; i++)
            var += (array[i] - average) * (array[i] - average);
        return (float) var / (float) array.length;
    }

    //initialize the program
    public void initProgram(String antSystem, int runNumber, VRPTW vrptw, double scalingValue, Ants ants, LoggerOutput loggerOutput) {
        setDefaultParameters(ants);
        Parse parse = new Parse();
        parse.parseCommandline(antSystem, runNumber, ants, this, loggerOutput);
        //compute distance matrix between cities and allocate ants
        if (ants.nAnts < 0)
            ants.nAnts = vrptw.n;
        vrptw.instance.distance = vrptw.computeDistances(scalingValue, this);
        ants.allocateAnts(vrptw);
    }

}
