package com.github.schmittjoaopedro.vrp.dvrptwacs;


import com.github.schmittjoaopedro.vrp.dvrptwacs.InOut.Distance_type;

import java.util.ArrayList;

public class VRPTW {

    private Utilities utilities;

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

    public VRPTW(int capacity_, Utilities utilities) {
        this.utilities = utilities;
        this.capacity = capacity_;
    }

    public VRPTW(int capacity_, ArrayList<Request> list, Utilities utilities) {
        this(capacity_, utilities);
        this.requests = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            this.requests.set(i, list.get(i));
        }
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

    public double doubleTrunc(double x) {
        return (double) (int) x;
    }

    //compute Euclidean distances between two nodes rounded to next integer for TSPLIB instances
    public double euclideanDistance(int i, int j) {
        double xd = instance.nodes[i].x - instance.nodes[j].x;
        double yd = instance.nodes[i].y - instance.nodes[j].y;
        return Math.sqrt(xd * xd + yd * yd);
    }

    //compute ceiling distance between two nodes rounded to next integer for TSPLIB instances
    public int ceil_distance(int i, int j) {
        double xd = instance.nodes[i].x - instance.nodes[j].x;
        double yd = instance.nodes[i].y - instance.nodes[j].y;
        double r = Math.sqrt(xd * xd + yd * yd);
        return (int) Math.ceil(r);
    }

    //compute geometric distance between two nodes rounded to next integer for TSPLIB instances
    public int geoDistance(int i, int j) {
        double deg, min;
        double lati, latj, longi, longj;
        double q1, q2, q3;
        int dd;
        double x1 = instance.nodes[i].x, x2 = instance.nodes[j].x, y1 = instance.nodes[i].y, y2 = instance.nodes[j].y;
        deg = doubleTrunc(x1);
        min = x1 - deg;
        lati = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = doubleTrunc(x2);
        min = x2 - deg;
        latj = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = doubleTrunc(y1);
        min = y1 - deg;
        longi = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = doubleTrunc(y2);
        min = y2 - deg;
        longj = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        q1 = Math.cos(longi - longj);
        q2 = Math.cos(lati - latj);
        q3 = Math.cos(lati + latj);
        dd = (int) (6378.388 * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
        return dd;
    }

    //compute ATT distance between two nodes rounded to next integer for TSPLIB instances
    public int attDistance(int i, int j) {
        double xd = instance.nodes[i].x - instance.nodes[j].x;
        double yd = instance.nodes[i].y - instance.nodes[j].y;
        double rij = Math.sqrt((xd * xd + yd * yd) / 10.0);
        double tij = doubleTrunc(rij);
        int dij;
        if (tij < rij)
            dij = (int) tij + 1;
        else
            dij = (int) tij;
        return dij;
    }

    //computes the matrix of all intercity/inter customers distances
    public double[][] computeDistances(double scalingValue, InOut inOut) {
        int i, j;
        int size = n;
        //include also the depot city in the distance matrix: it will correspond to index 0 for row and column
        double matrix[][] = new double[size + 1][size + 1];
        for (i = 0; i < size + 1; i++) {
            for (j = 0; j < size + 1; j++) {
                if (inOut.distance_type == Distance_type.ATT) {
                    matrix[i][j] = attDistance(i, j);
                } else if (inOut.distance_type == Distance_type.CEIL_2D) {
                    matrix[i][j] = ceil_distance(i, j);
                } else if (inOut.distance_type == Distance_type.EUC_2D) {
                    matrix[i][j] = euclideanDistance(i, j);
                    if (scalingValue != 0) {
                        matrix[i][j] *= scalingValue;
                    }
                } else if (inOut.distance_type == Distance_type.GEO) {
                    matrix[i][j] = geoDistance(i, j);
                }
            }
        }
        return matrix;
    }

    //computes nearest neighbor lists of depth nn for each city
    public int[][][] computeNnLists(Ants ants) {
        int i, node, nn, count1, count2;
        int size = n;
        double[] distanceVector = new double[size + 1];
        int[] helpVector = new int[size + 1];
        nn = ants.nnAnts;
        if (nn >= size + 1)
            nn = size - 2;
        ants.nnAnts = nn;
        int[][][] result = new int[2][][];
        int[][] mNnear = new int[size + 1][nn];
        int[][] mNnearAll = new int[size + 1][nn];  //include also the depot city
        for (node = 0; node < size + 1; node++) { /* compute candidate-sets for all nodes */
            for (i = 0; i < size + 1; i++) { /* Copy distances from nodes to the others */
                distanceVector[i] = instance.distance[node][i];
                helpVector[i] = i;
            }
            distanceVector[node] = Integer.MAX_VALUE; /* city itself is not nearest neighbor */
            utilities.sort2(distanceVector, helpVector, 0, size);
            count1 = 0;
            i = -1;
            while (count1 < nn) {
                i++;
                //include in the nnList of a node only the nodes that are known (available)
                if (helpVector[i] != 0) {
                    mNnear[node][count1] = helpVector[i];
                    count1++;
                } else {
                    continue;
                }
            }
            count2 = 0;
            i = -1;
            while (count2 < nn) {
                i++;
                //include in the nnList of a node only the nodes that are known (available)
                mNnearAll[node][count2] = helpVector[i];
                count2++;
            }
        }
        result[0] = mNnear;
        result[1] = mNnearAll;
        return result;
    }

    //compute the tour length of tour t taking also into account the depot city
    public double computeTourLength(ArrayList<Integer> tour) {
        int i;
        double sum = 0;
        if (tour.size() > 1) {
            for (i = 0; i < tour.size() - 1; i++) {
                sum += instance.distance[tour.get(i) + 1][tour.get(i + 1) + 1];
            }
        }
        return sum;
    }

    public double computeTourLengthWithDepot(ArrayList<Integer> tour) {
        int i;
        double sum = 0;
        if (tour.size() > 1) {
            sum += instance.distance[0][tour.get(1) + 1];
            for (i = 1; i < tour.size() - 2; i++) {
                sum += instance.distance[tour.get(i) + 1][tour.get(i + 1) + 1];
            }
            sum += instance.distance[tour.get(tour.size() - 2) + 1][0];
        }
        return sum;
    }

}
