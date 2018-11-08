package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Statistics {

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

    public Statistics() {
        super();
    }

    public Statistics(boolean isDiscreteTime) {
        this.isDiscreteTime = isDiscreteTime;
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

}
