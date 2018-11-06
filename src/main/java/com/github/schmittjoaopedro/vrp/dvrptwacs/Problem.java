package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Problem {

    public String name; /* instance name */

    public Point[] nodes; /* array of classes containing coordinates of nodes */

    public double[][] distance; /* distance matrix: distance[i][j] gives distance */

    public int[][] nn_list; /* nearest neighbor list; contains for each node i a sorted list of n_near nearest neighbors */

    public int[][] nn_list_all; /* nearest neighbor list; contains for each node i a sorted list of n_near nearest neighbors including the depot*/

}
