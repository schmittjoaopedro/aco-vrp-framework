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
import java.util.ArrayList;

import com.github.schmittjoaopedro.vrp.dvrptwacs.InOut.Distance_type;

public class VRPTW {
    /*
     * ################################################
     * ########## ACO algorithms for the TSP ##########
     * ################################################
     *
     * Version: 1.0
     * File: TSP.c
     * Author: Thomas Stuetzle
     * Purpose: TSP related procedures, distance computation, neighbour lists
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

    private Utilities utilities;

    //maximum number of vehicle allowed for constructing the solution or the size of the vehicle fleet
    private int nrVehicles;

    //the capacity of each vehicle
    private int capacity;

    //arraylist which keeps all the customer requests defined in the VRPTW instance
    private ArrayList<Request> requests;

    //arraylist which keeps the ids of the available requests known at the current time
    //initially it contains the ids of the apriori requests, having available time = 0
    private ArrayList<Integer> idAvailableRequests;

    //arraylist which keeps the list of dynamic requests, which are not known in advance, having
    //available time > 0
    private ArrayList<Request> dynamicRequests;

    //number of cities/customers, except the depot
    public int n;

    public Problem instance;

    public VRPTW(Utilities utilities) {
        this.utilities = utilities;
    }

    public VRPTW(int nrVehicles_, int capacity_, Utilities utilities) {
        this.utilities = utilities;
        this.nrVehicles = nrVehicles_;
        this.capacity = capacity_;
    }

    public VRPTW(int nrVehicles_, int capacity_, ArrayList<Request> list, Utilities utilities) {
        this(nrVehicles_, capacity_, utilities);
        this.requests = new ArrayList<Request>(list.size());
        for (int i = 0; i < list.size(); i++) {
            this.requests.set(i, list.get(i));
        }
    }

    public int getNrVehicles() {
        return nrVehicles;
    }

    public void setNoVehicles(int nrVehicles) {
        this.nrVehicles = nrVehicles;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Request> requests) {
        this.requests = requests;
    }

    public ArrayList<Integer> getIdAvailableRequests() {
        return idAvailableRequests;
    }

    public void setIdAvailableRequests(ArrayList<Integer> idAvailableRequests) {
        this.idAvailableRequests = idAvailableRequests;
    }

    public ArrayList<Request> getDynamicRequests() {
        return dynamicRequests;
    }

    public void setDynamicRequests(ArrayList<Request> dynamicRequests) {
        this.dynamicRequests = dynamicRequests;
    }

    public double dtrunc(double x) {
        int k;

        k = (int) x;
        x = (double) k;
        return x;
    }

    /*
     * FUNCTION: the following four functions implement different ways of
     * computing distances for TSPLIB instances
     * INPUT: two node indices
     * OUTPUT: distance between the two nodes
     */

    //compute Euclidean distances between two nodes rounded to next integer for TSPLIB instances
    public double euclidianDistance(int i, int j) {
        double xd = 0, yd = 0;

        xd = instance.nodes[i].x - instance.nodes[j].x;
        yd = instance.nodes[i].y - instance.nodes[j].y;

        double r = Math.sqrt(xd * xd + yd * yd);

        return r;
    }

    //compute ceiling distance between two nodes rounded to next integer for TSPLIB instances
    public int ceil_distance(int i, int j) {
        double xd = instance.nodes[i].x - instance.nodes[j].x;
        double yd = instance.nodes[i].y - instance.nodes[j].y;
        double r = Math.sqrt(xd * xd + yd * yd);

        return (int) Math.ceil(r);
    }

    //compute geometric distance between two nodes rounded to next integer for TSPLIB instances
    public int geo_distance(int i, int j)
    {
        double deg, min;
        double lati, latj, longi, longj;
        double q1, q2, q3;
        int dd;
        double x1 = instance.nodes[i].x, x2 = instance.nodes[j].x, y1 = instance.nodes[i].y, y2 = instance.nodes[j].y;

        deg = dtrunc(x1);
        min = x1 - deg;
        lati = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = dtrunc(x2);
        min = x2 - deg;
        latj = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;

        deg = dtrunc(y1);
        min = y1 - deg;
        longi = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = dtrunc(y2);
        min = y2 - deg;
        longj = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;

        q1 = Math.cos(longi - longj);
        q2 = Math.cos(lati - latj);
        q3 = Math.cos(lati + latj);
        dd = (int) (6378.388 * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
        return dd;
    }

    //compute ATT distance between two nodes rounded to next integer for TSPLIB instances
    public int att_distance(int i, int j)
    {
        double xd = instance.nodes[i].x - instance.nodes[j].x;
        double yd = instance.nodes[i].y - instance.nodes[j].y;
        double rij = Math.sqrt((xd * xd + yd * yd) / 10.0);
        double tij = dtrunc(rij);
        int dij;

        if (tij < rij)
            dij = (int) tij + 1;
        else
            dij = (int) tij;
        return dij;
    }

    //computes the matrix of all intercity/inter customers distances
    public double[][] compute_distances(double scalingValue)
    {
        int i, j;
        int size = n;
        //include also the depot city in the distance matrix: it will correspond to index 0 for row and column
        double matrix[][] = new double[size + 1][size + 1];

        for (i = 0; i < size + 1; i++) {
            for (j = 0; j < size + 1; j++) {
                if (InOut.distance_type == Distance_type.ATT) {
                    matrix[i][j] = att_distance(i, j);
                } else if (InOut.distance_type == Distance_type.CEIL_2D) {
                    matrix[i][j] = ceil_distance(i, j);
                } else if (InOut.distance_type == Distance_type.EUC_2D) {
                    matrix[i][j] = euclidianDistance(i, j);
                    if (scalingValue != 0) {
                        matrix[i][j] *= scalingValue;
                    }
                } else if (InOut.distance_type == Distance_type.GEO) {
                    matrix[i][j] = geo_distance(i, j);
                }
            }
        }
        return matrix;
    }

    //computes nearest neighbor lists of depth nn for each city
    public int[][][] compute_nn_lists(Ants ants) {
        int i, node, nn, count1, count2;

        int size = n;
        double[] distance_vector = new double[size + 1];
        int[] help_vector = new int[size + 1];

        nn = ants.nn_ants;
        if (nn >= size + 1)
            nn = size - 2;
        ants.nn_ants = nn;

        int[][][] result = new int[2][][];
        int[][] m_nnear = new int[size + 1][nn];
        int[][] m_nnear_all = new int[size + 1][nn];  //include also the depot city

        for (node = 0; node < size + 1; node++) { /* compute candidate-sets for all nodes */
            for (i = 0; i < size + 1; i++) { /* Copy distances from nodes to the others */
                distance_vector[i] = instance.distance[node][i];
                //distance_vector[i] = 0.9 * VRPTW.instance.distance[node][i] + 0.1 * vrp.getRequests().get(i).getEndWindow();
                help_vector[i] = i;
            }
            distance_vector[node] = Integer.MAX_VALUE; /* city itself is not nearest neighbor */
            utilities.sort2(distance_vector, help_vector, 0, size);

            count1 = 0; i = -1;
            while (count1 < nn) {
                i++;
                //include in the nn_list of a node only the nodes that are known (available)
                //if ((help_vector[i] != 0) && (vrp.getIdAvailableRequests().contains(i))) {
                if (help_vector[i] != 0) {
                    m_nnear[node][count1] = help_vector[i];
                    count1++;
                }
                else {
                    continue;
                }
            }

            count2 = 0; i = -1;
            while (count2 < nn) {
                i++;
                //include in the nn_list of a node only the nodes that are known (available)
                //if (vrp.getIdAvailableRequests().contains(i)) {
                m_nnear_all[node][count2] = help_vector[i];
                count2++;
                //}

            }
        }

        //return m_nnear;
        result[0] = m_nnear;
        result[1] = m_nnear_all;
        return result;
    }

    //compute the tour length of tour t taking also into account the depot city
    public double compute_tour_length(ArrayList<Integer> t) {
        int i;
        double sum = 0;

        if (t.size() > 1) {
            for (i = 0; i < t.size() - 1; i++) {
                sum += instance.distance[t.get(i) + 1][t.get(i + 1) + 1];
            }
        }

        return sum;
    }

    //compute the tour length of tour t taking also into account the depot city
    public double compute_tour_length1(ArrayList<Integer> t) {
        int i;
        double sum = 0;

        if (t.size() > 1) {
            for (i = 0; i < t.size() - 1; i++) {
                sum += instance.distance[t.get(i)][t.get(i + 1)];
            }
        }

        return sum;
    }

    public double compute_tour_length_(ArrayList<Integer> t) {
        int i;
        double sum = 0;

        if (t.size() > 1) {
            sum += instance.distance[0][t.get(1) + 1];
            for (i = 1; i < t.size() - 2; i++) {
                sum += instance.distance[t.get(i) + 1][t.get(i + 1) + 1];
            }
            sum += instance.distance[t.get(t.size() - 2) + 1][0];
        }

        return sum;
    }

}
