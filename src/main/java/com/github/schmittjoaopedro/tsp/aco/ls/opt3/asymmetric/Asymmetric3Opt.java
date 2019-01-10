package com.github.schmittjoaopedro.tsp.aco.ls.opt3.asymmetric;

public class Asymmetric3Opt {

    /*
     There is a problem associated with the asymmetric to symmetric conversion. The 3-opt should consider the vertices
     linked with their ghosts representations as a single unit, for example, instead to consider 0 and 5 as to separated
     vertices both should be considered a single unit, like 0-5. Then the matrix instead of be considered as
        G = [(0,5),(5,1),(1,6),(6,2),(2,7),(7,3),(3,8),(8,4),(4,9),(9,0)]
      should be processed as
        G = [(0-5, 1-6), (1-6, 2-7), (2-7, 3-8), (3-8, 4-9), (4-9, 0-5)]
    */
    public static void threeOpt(int[] asymmetricTour, int[][] symmetricDistances) {
        AsymmetricNode temp;
        AsymmetricNode[] symmetricTour = new AsymmetricNode[asymmetricTour.length];
        int tourLength = asymmetricTour.length;
        int asymmetricBoundary = tourLength - 1;
        // Create a new route to represent the vertices linked used to get the costs of the symmetric distance matrix
        for (int i = 0; i < tourLength; i++) {
            temp = new AsymmetricNode();
            temp.inputNode = asymmetricTour[i];
            temp.outputNode = asymmetricTour[i] + asymmetricBoundary;
            symmetricTour[i] = temp;
        }
        // Execute local search
        threeOpt(symmetricTour, symmetricDistances);
        // Retrieve the original asymmetric vertices
        for (int i = 0; i < tourLength; i++) {
            temp = symmetricTour[i];
            asymmetricTour[i] = temp.inputNode;
        }
    }

    private static void threeOpt(AsymmetricNode[] tour, int[][] distances) {
        int N = tour.length;
        boolean improvement = true;
        AsymmetricNode newTour[] = new AsymmetricNode[tour.length];
        System.arraycopy(tour, 0, newTour, 0, tour.length);
        while (improvement) {
            improvement = false;
            for (int i = 1; i < N; i++) {
                for (int j = i + 2; j < N; j++) {
                    for (int k = j + 2; k < N; k++) {
                        if (execute3Swap(newTour, distances, i, j, k)) {
                            improvement = true;
                            System.arraycopy(newTour, 0, tour, 0, N);
                        }
                    }
                }
            }
        }
    }

    private static boolean execute3Swap(AsymmetricNode[] tour, int[][] dist, int i, int j, int k) {
        // [...,A-B,...,C-D,...,E-F,...]
        AsymmetricNode a = tour[i - 1];
        AsymmetricNode b = tour[i];
        AsymmetricNode c = tour[j - 1];
        AsymmetricNode d = tour[j];
        AsymmetricNode e = tour[k - 1];
        AsymmetricNode f = tour[k];
        double d0 = dist[a.outputNode][b.inputNode] + dist[c.outputNode][d.inputNode] + dist[e.outputNode][f.inputNode];
        double d1 = dist[a.outputNode][c.inputNode] + dist[b.outputNode][d.inputNode] + dist[e.outputNode][f.inputNode];
        double d2 = dist[a.outputNode][b.inputNode] + dist[c.outputNode][e.inputNode] + dist[d.outputNode][f.inputNode];
        double d3 = dist[a.outputNode][d.inputNode] + dist[e.outputNode][b.inputNode] + dist[c.outputNode][f.inputNode];
        double d4 = dist[f.outputNode][b.inputNode] + dist[c.outputNode][d.inputNode] + dist[e.outputNode][a.inputNode];
        if (d0 > d1) {
            reverse(tour, i, j);
            return true;
        } else if (d0 > d2) {
            reverse(tour, j, k);
            return true;
        } else if (d0 > d4) {
            reverse(tour, i, k);
            return true;
        } else if (d0 > d3) {
            rearrange(tour, i, j, k);
            return true;
        }
        return false;
    }

    private static void rearrange(AsymmetricNode[] route, int i, int j, int k) {
        AsymmetricNode routeIJ[] = new AsymmetricNode[j - i];
        AsymmetricNode routeJK[] = new AsymmetricNode[k - j];
        System.arraycopy(route, i, routeIJ, 0, routeIJ.length);
        System.arraycopy(route, j, routeJK, 0, routeJK.length);
        int countIJ = 0;
        int countJK = 0;
        for (int t = 0; t < route.length; t++) {
            if (t > i - 1 && t < i + routeJK.length) {
                route[t] = routeJK[countJK];
                countJK++;
            } else if (t >= i + routeJK.length && t < i + routeJK.length + routeIJ.length) {
                route[t] = routeIJ[countIJ];
                countIJ++;
            }
        }
    }

    private static void reverse(AsymmetricNode[] tour, int i, int j) {
        if (i > j) {
            int aux = i;
            i = j;
            j = aux;
        }
        AsymmetricNode sub[] = new AsymmetricNode[j - i];
        System.arraycopy(tour, i, sub, 0, sub.length);
        for (int k = i; k < i + sub.length; k++) {
            tour[k] = sub[j - k - 1];
        }
    }

}
