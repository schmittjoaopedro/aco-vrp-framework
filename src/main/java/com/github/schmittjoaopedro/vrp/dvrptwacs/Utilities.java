package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Utilities {

    private int seed;

    public double doubleTruncate(double x) {
        return (double) ((int) x);
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    // Recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
    public void sort2(double v[], int v2[], int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap2(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap2(v, v2, ++last, k);
        swap2(v, v2, left, last);
        sort2(v, v2, left, last);
        sort2(v, v2, last + 1, right);
    }

    // Auxiliary routine for sorting an integer array
    private void swap2(double v[], int v2[], int i, int j) {
        double tmp1;
        int tmp2;

        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;

        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }
}
