package com.github.schmittjoaopedro.tsp.aco.ls;

import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

public class LocalSearchUtils {

    public static int[] createSubTourMap(int[] route, int phase, int subTourLength) {
        int[] subTourMap = new int[subTourLength];
        subTourMap[0] = route[phase];
        for (int i = 1; i < subTourLength; i++) {
            subTourMap[i] = route[phase + i];
        }
        return subTourMap;
    }

    public static Graph createSubGraph(int[] subTourMap, Graph graph, int toId, int subTourLength) {
        Graph newGraph = new Graph();
        for (int i = 0; i < subTourLength; i++) {
            Vertex vertex = new Vertex(i);
            vertex.setX(graph.getVertex(subTourMap[i]).getX());
            vertex.setY(graph.getVertex(subTourMap[i]).getY());
            newGraph.addVertex(vertex);
        }
        int edgeId = 0;
        for (int i = 0; i < subTourLength; i++) {
            for (int j = 0; j < subTourLength; j++) {
                if (i != j) {
                    Edge edge = new Edge(edgeId++);
                    edge.setFrom(newGraph.getVertex(i));
                    edge.setTo(newGraph.getVertex(j));
                    edge.setCost(graph.getEdge(subTourMap[i], subTourMap[j]).getCost());
                    edge.getFrom().getAdj().put(edge.getToId(), edge);
                    newGraph.addEdge(edge);
                }
            }
        }
        for (int i = 1; i < subTourLength; i++) {
            newGraph.getEdge(i, 0).setCost(graph.getEdge(subTourMap[i], toId).getCost());
        }
        return newGraph;
    }

    public static int[] createSubTour(int[] subTourMap) {
        int[] vertexTour = new int[subTourMap.length + 1];
        for (int i = 0; i < subTourMap.length; i++) {
            vertexTour[i] = i;
        }
        vertexTour[subTourMap.length] = vertexTour[0];
        return vertexTour;
    }

    public static double fitnessEvaluation(Graph graph, int[] tour) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(tour[i], tour[i + 1]).getCost();
        }
        return cost;
    }

    public static int[] getResult(boolean subTourOptimization, int[] subTourMap, int[] tour, int[] route, int phase) {
        if (subTourOptimization) {
            for (int i = 1; i < subTourMap.length; i++) {
                route[phase + i] = subTourMap[tour[i]];
            }
            return route;
        } else {
            return tour;
        }
    }

    public static boolean isSymmetric(int[][] distances) {
        for (int i = 0; i < distances.length; i++) {
            for (int j = i; j < distances.length; j++) {
                if (distances[i][j] != distances[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Title: Transforming Asymmetric into Symmetric Travelling Salesman Problems
     * Authors: Roy JONKER and Ton VOLGENANT
     */
    public static double[][] asymmetricToSymmetric(double[][] asymmetric, double M, double INF) {
        int n = asymmetric.length;
        // C" = | U' C'|
        //      | C  U"|
        double[][] symmetric = new double[2 * n][2 * n];

        // Define U'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                symmetric[i][j] = INF; // Inf+
            }
        }

        // Define C' (Transpose of C)
        for (int i = 0; i < n; i++) {
            for (int j = n; j < 2 * n; j++) {
                if (i == j - n) {
                    symmetric[i][j] = -M; // -M
                } else {
                    symmetric[i][j] = asymmetric[j - n][i];
                }
            }
        }

        // Define C
        for (int i = n; i < 2 * n; i++) {
            for (int j = 0; j < n; j++) {
                if (i - n == j) {
                    symmetric[i][j] = -M; // -M
                } else {
                    symmetric[i][j] = asymmetric[i - n][j];
                }
            }
        }

        // Define U"
        for (int i = n; i < 2 * n; i++) {
            for (int j = n; j < 2 * n; j++) {
                symmetric[i][j] = INF; // Inf+
            }
        }

        // C"
        return symmetric;
    }

    public static int[][] asymmetricToSymmetric(int[][] asymmetric, int M, int INF) {
        int n = asymmetric.length;
        // C" = | U' C'|
        //      | C  U"|
        int[][] symmetric = new int[2 * n][2 * n];

        // Define U'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                symmetric[i][j] = INF; // Inf+
            }
        }

        // Define C' (Transpose of C)
        for (int i = 0; i < n; i++) {
            for (int j = n; j < 2 * n; j++) {
                if (i == j - n) {
                    symmetric[i][j] = -M; // -M
                } else {
                    symmetric[i][j] = asymmetric[j - n][i];
                }
            }
        }

        // Define C
        for (int i = n; i < 2 * n; i++) {
            for (int j = 0; j < n; j++) {
                if (i - n == j) {
                    symmetric[i][j] = -M; // -M
                } else {
                    symmetric[i][j] = asymmetric[i - n][j];
                }
            }
        }

        // Define U"
        for (int i = n; i < 2 * n; i++) {
            for (int j = n; j < 2 * n; j++) {
                symmetric[i][j] = INF; // Inf+
            }
        }

        // C"
        return symmetric;
    }

    public static int[] convertToAssymetricTour(int[] tour) {
        int[] newTour = new int[2 * tour.length - 1];
        for (int i = 0; i < newTour.length; i++) {
            if (i % 2 == 0) {
                newTour[i] = tour[i / 2];
            } else {
                newTour[i] = tour.length - 1 + newTour[i - 1];
            }
        }
        return newTour;
    }

    public static int[][] createNNList(int n, int nn_ls, int distances[][]) {
        double[] distanceVector = new double[n];
        int[] helpVector = new int[n];
        int[][] nnList = new int[n][nn_ls];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (i != j) {
                    distanceVector[i] = distances[j][i];
                    helpVector[i] = i;
                }
            }
            distanceVector[j] = Double.MAX_VALUE;
            sort(distanceVector, helpVector, 0, n - 1);
            for (int i = 0; i < nn_ls; i++) {
                nnList[j][i] = helpVector[i];
            }
        }
        return nnList;
    }

    private static void swap(double v[], int[] v2, int i, int j) {
        double tmpCost = v[i];
        int tempVertex = v2[i];
        v[i] = v[j];
        v2[i] = v2[j];
        v[j] = tmpCost;
        v2[j] = tempVertex;
    }

    private static void sort(double v[], int[] v2, int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap(v, v2, ++last, k);
        swap(v, v2, left, last);
        sort(v, v2, left, last);
        sort(v, v2, last + 1, right);
    }
}
