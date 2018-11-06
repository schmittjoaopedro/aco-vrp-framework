package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.Random;

public class Utilities {

    private Random random;

    //auxiliary routine for sorting an integer array
    public void swap2(double v[], int v2[], int i, int j) {
        double tmp1;
        int tmp2;
        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;
        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }

    //recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
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

    //generate a random number that is uniformly distributed in [0,1]
    public double random01() {
        if (random == null) {
            random = new Random();
        }
        return random.nextDouble();
    }

    public void setSeed(int seed) {
        random = new Random(seed);
    }

}
