package com.github.schmittjoaopedro.vrp.dvrptwacs;

/**
 * ACO algorithms for the TSP
 *
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 *
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 */
public class InOut {
    /*
     * ################################################
     * ########## ACO algorithms for the TSP ##########
     * ################################################
     *
     * Version: 1.0
     * File: InOut.c
     * Author: Thomas Stuetzle
     * Purpose: mainly input / output / statistic routines
     * Check: README and gpl.txt
     * Copyright (C) 2002 Thomas Stuetzle
     */

    /***************************************************************************
     * Program's name: acotsp
     *
     * Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
     * symmetric TSP
     *
     * Copyright (C) 2004 Thomas Stuetzle
     *
     * This program is free software; you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation; either version 2 of the License, or
     * (at your option) any later version.
     *
     * This program is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with this program; if not, write to the Free Software
     * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
     *
     * email: stuetzle no@spam informatik.tu-darmstadt.de
     * mail address: Universitaet Darmstadt
     * Fachbereich Informatik
     * Hochschulstr. 10
     * D-64283 Darmstadt
     * Germany
     ***************************************************************************/

    enum Distance_type {EUC_2D, CEIL_2D, GEO, ATT};

    static Distance_type distance_type;

    static int best_in_try;
    static int best_found_at;
    static double time_best_found;
    static double time_total_run;

    static int n_try; /* try counter */
    static int n_tours; /* counter of number constructed tours */
    static int iteration; /* iteration counter */
    static int max_tries; /* maximum number of independent tries */
    static int max_tours; /* maximum number of tour constructions in one try */
    public static int max_iterations; /* maximum number of iterations */

    static double max_time; /* maximal allowed run time of a try */
    static double time_used; /* time used until some given event */
    static double time_passed; /* time passed until some moment */
    static int currentTimeSlice;
    static int optimal; /* optimal solution or bound to find */

    static double branching_factor; /* average node branching factor when searching */
    static double branch_fac; /* If branching factor < branch_fac => update trails */
    static double lambda; /* Parameter to determine branching factor */

    static int found_best; /* iteration in which best solution is found */
    static int restart_found_best; /* iteration in which restart-best solution is found */

    static String inputFile;
    static int opt;

    static double pheromonePreservation;
    static int noEvaluations = 0;
    static int noSolutions = 0;   /* counter for the total number of feasible solutions */
    public static boolean isDiscreteTime = false;

    static void set_default_as_parameters() {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        Ants.n_ants = -1;
        Ants.nn_ants = 20;

        Ants.alpha = 1.0;
        Ants.beta = 2.0;
        Ants.rho = 0.5;
        Ants.q_0 = 0.0;
    }

    static void set_default_acs_parameters() {
        /* number of ants (-1 means MTsp.instance.n size) and number of nearest neighbors in tour construction */
        Ants.n_ants = 10;   //10  //1
        //Ants.nn_ants = 15;
        Ants.nn_ants = 20;    //20  //50  //30
        //Ants.nn_ants = (MTsp.n / 2) + 10;

        Ants.alpha = 1.0;
        Ants.beta = 1.0;  //2.0  1.0
        Ants.rho = 0.9;   //0.1  //0.9
        //used in local pheromone update
        Ants.local_rho = 0.9;   //0.1  //0.9
        Ants.q_0 = 0.9; //0.9
    }

    //set default parameter settings
    static void set_default_parameters() {
        /* number of ants and number of nearest neighbors in tour construction */
        Ants.n_ants = 25;
        Ants.nn_ants = 20;

        Ants.alpha = 1.0;
        Ants.beta = 2.0;
        Ants.rho = 0.5;
        Ants.q_0 = 0.0;
        max_tries = 10;
        max_tours = 10 * 20;
        //10 seconds allowed for running time; it is used in the termination condition of ACO
        //max_time = 15.0 * VRPTW.m;
        max_time = 100;  //100  //300
        //maximum number of allowed iterations until the ACO algorithm stops
        max_iterations = 3000;
        optimal = 1;
        branch_fac = 1.00001;    //1.00001  //1.2  //1.1
        Ants.u_gb = Integer.MAX_VALUE;
        Ants.acs_flag = false;
        Ants.as_flag = false;
        distance_type = Distance_type.EUC_2D;

        pheromonePreservation = 0.3;   //0.3  //0.0
    }

    //compute the average node lambda-branching factor, where l designates the lambda value
    static double node_branching(double l, VRPTW vrptw) {
        int i, m;
        double min, max, cutoff;
        double avg;
        double[] num_branches;

        num_branches = new double[vrptw.n + 1];

        for (m = 0; m < (vrptw.n + 1); m++) {
            /* determine max, min to calculate the cutoff value */
            min = Ants.pheromone[m][vrptw.instance.nn_list[m][0]];
            max = Ants.pheromone[m][vrptw.instance.nn_list[m][0]];
            for (i = 1; i < Ants.nn_ants; i++) {
                if (Ants.pheromone[m][vrptw.instance.nn_list[m][i]] > max)
                    max = Ants.pheromone[m][vrptw.instance.nn_list[m][i]];
                if (Ants.pheromone[m][vrptw.instance.nn_list[m][i]] < min)
                    min = Ants.pheromone[m][vrptw.instance.nn_list[m][i]];
            }
            cutoff = min + l * (max - min);

            for (i = 0; i < Ants.nn_ants; i++) {
                if (Ants.pheromone[m][vrptw.instance.nn_list[m][i]] > cutoff)
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

    public static float average(int[] array) {
        int sum = 0;


        for (int  i = 0; i < array.length; i++)
            sum += array[i];

        return (float)sum / (float)array.length;
    }

    public static float variance(int[] array){
        double var = 0;

        double average = average(array);
        for (int  i = 0; i < array.length; i++)
            var += (array[i] - average)*(array[i] - average);

        return (float)var/(float)array.length;
    }

    //initialize the program
    static void init_program(String antSystem, int runNumber, VRPTW vrptw, double scalingValue) {

        set_default_parameters();
        Parse.parse_commandline(antSystem, runNumber);

        //compute distance matrix between cities and allocate ants
        if (Ants.n_ants < 0)
            Ants.n_ants = vrptw.n;

        vrptw.instance.distance = vrptw.compute_distances(scalingValue);
        Ants.allocate_ants(vrptw);
    }

}
