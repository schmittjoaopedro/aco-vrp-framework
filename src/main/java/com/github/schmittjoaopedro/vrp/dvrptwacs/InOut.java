package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class InOut {

    enum Distance_type {EUC_2D, CEIL_2D, GEO, ATT}

    public Distance_type distance_type;

    public int n_tours; /* counter of number constructed tours */
    public int iteration; /* iteration counter */
    public int max_tries; /* maximum number of independent tries */
    public int max_tours; /* maximum number of tour constructions in one try */
    public int max_iterations; /* maximum number of iterations */

    public double max_time; /* maximal allowed run time of a try */
    public double time_used; /* time used until some given event */
    public double time_passed; /* time passed until some moment */
    public int currentTimeSlice;
    public int optimal; /* optimal solution or bound to find */

    public double branch_fac; /* If branching factor < branch_fac => update trails */
    public double lambda; /* Parameter to determine branching factor */

    public int found_best; /* iteration in which best solution is found */
    public int restart_found_best; /* iteration in which restart-best solution is found */

    public double pheromonePreservation;
    public int noEvaluations = 0;
    public int noSolutions = 0;   /* counter for the total number of feasible solutions */
    public boolean isDiscreteTime = false;

    public InOut() {
        super();
    }

    public InOut(boolean isDiscreteTime) {
        this.isDiscreteTime = isDiscreteTime;
    }

    public void set_default_as_parameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.n_ants = -1;
        ants.nn_ants = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q_0 = 0.0;
    }

    public void set_default_acs_parameters(Ants ants) {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        ants.n_ants = 10;
        ants.nn_ants = 20;
        ants.alpha = 1.0;
        ants.beta = 1.0;
        ants.rho = 0.9;
        //used in local pheromone update
        ants.local_rho = 0.9;
        ants.q_0 = 0.9;
    }

    //set default parameter settings
    public void set_default_parameters(Ants ants) {
        /* number of ants and number of nearest neighbors in tour construction */
        ants.n_ants = 25;
        ants.nn_ants = 20;
        ants.alpha = 1.0;
        ants.beta = 2.0;
        ants.rho = 0.5;
        ants.q_0 = 0.0;
        max_tries = 10;
        max_tours = 10 * 20;
        //10 seconds allowed for running time; it is used in the termination condition of ACO
        max_time = 100;
        //maximum number of allowed iterations until the ACO algorithm stops
        max_iterations = 3000;
        optimal = 1;
        branch_fac = 1.00001;
        ants.u_gb = Integer.MAX_VALUE;
        ants.acs_flag = false;
        ants.as_flag = false;
        distance_type = Distance_type.EUC_2D;
        pheromonePreservation = 0.3;
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
            min = ants.pheromone[m][vrptw.instance.nn_list[m][0]];
            max = ants.pheromone[m][vrptw.instance.nn_list[m][0]];
            for (i = 1; i < ants.nn_ants; i++) {
                if (ants.pheromone[m][vrptw.instance.nn_list[m][i]] > max)
                    max = ants.pheromone[m][vrptw.instance.nn_list[m][i]];
                if (ants.pheromone[m][vrptw.instance.nn_list[m][i]] < min)
                    min = ants.pheromone[m][vrptw.instance.nn_list[m][i]];
            }
            cutoff = min + l * (max - min);
            for (i = 0; i < ants.nn_ants; i++) {
                if (ants.pheromone[m][vrptw.instance.nn_list[m][i]] > cutoff)
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
    public void init_program(String antSystem, int runNumber, VRPTW vrptw, double scalingValue, Ants ants, LoggerOutput loggerOutput) {
        set_default_parameters(ants);
        Parse parse = new Parse();
        parse.parse_commandline(antSystem, runNumber, ants, this, loggerOutput);
        //compute distance matrix between cities and allocate ants
        if (ants.n_ants < 0)
            ants.n_ants = vrptw.n;
        vrptw.instance.distance = vrptw.compute_distances(scalingValue, this);
        ants.allocate_ants(vrptw);
    }

}
