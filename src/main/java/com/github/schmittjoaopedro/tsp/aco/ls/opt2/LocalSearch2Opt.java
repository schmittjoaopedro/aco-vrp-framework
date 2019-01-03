package com.github.schmittjoaopedro.tsp.aco.ls.opt2;

import java.util.Random;

public class LocalSearch2Opt {

    static boolean dlb_flag = true;

    static int[] generate_random_permutation(Random random, int n) {
        int i, help, node, tot_assigned = 0;
        double rnd;
        int[] r;

        r = new int[n];

        for (i = 0; i < n; i++)
            r[i] = i;

        for (i = 0; i < n; i++) {
            /* find (randomly) an index for a free unit */
            rnd = random.nextDouble();
            node = (int) (rnd * (n - tot_assigned));
            assert (i + node < n);
            help = r[i];
            r[i] = r[i + node];
            r[i + node] = help;
            tot_assigned++;
        }
        return r;
    }

    public static void twoOpt(Random random, int tour[], int n, int[][] distance, int nn_list[][], int nn_ls) {
        boolean gotoExchange = false;

        int c1, c2; /* cities considered for an exchange */
        int s_c1, s_c2; /* successor cities of c1 and c2 */
        int p_c1, p_c2; /* predecessor cities of c1 and c2 */
        int pos_c1, pos_c2; /* positions of cities c1, c2 */
        int i, j, h, l;
        int help;
        boolean improvement_flag;
        int h1 = 0, h2 = 0, h3 = 0, h4 = 0;
        int radius; /* radius of nn-search */
        int gain = 0;
        int[] random_vector;
        int[] pos; /* positions of cities in tour */
        boolean[] dlb; /* vector containing don't look bits */

        pos = new int[n];
        dlb = new boolean[n];
        for (i = 0; i < n; i++) {
            pos[tour[i]] = i;
            dlb[i] = false;
        }

        improvement_flag = true;
        random_vector = generate_random_permutation(random, n);

        while (improvement_flag) {

            improvement_flag = false;

            for (l = 0; l < n; l++) {

                c1 = random_vector[l];
                // DEBUG ( assert ( c1 < n && c1 >= 0); )
                if (dlb_flag && dlb[c1])
                    continue;
                pos_c1 = pos[c1];
                s_c1 = tour[pos_c1 + 1];
                radius = distance[c1][s_c1];

                /* First search for c1's nearest neighbours, use successor of c1 */
                for (h = 0; h < nn_ls; h++) {
                    c2 = nn_list[c1][h]; /* exchange partner, determine its position */
                    if (radius > distance[c1][c2]) {
                        s_c2 = tour[pos[c2] + 1];
                        gain = -radius + distance[c1][c2] + distance[s_c1][s_c2] - distance[c2][s_c2];
                        if (gain < 0) {
                            h1 = c1;
                            h2 = s_c1;
                            h3 = c2;
                            h4 = s_c2;
                            gotoExchange = true;
                            break;
                        }
                    } else
                        break;
                }

                if (gotoExchange) {
                    /* Search one for next c1's h-nearest neighbours, use predecessor c1 */
                    if (pos_c1 > 0)
                        p_c1 = tour[pos_c1 - 1];
                    else
                        p_c1 = tour[n - 1];
                    radius = distance[p_c1][c1];
                    for (h = 0; h < nn_ls; h++) {
                        c2 = nn_list[c1][h]; /* exchange partner, determine its position */
                        if (radius > distance[c1][c2]) {
                            pos_c2 = pos[c2];
                            if (pos_c2 > 0)
                                p_c2 = tour[pos_c2 - 1];
                            else
                                p_c2 = tour[n - 1];
                            if (p_c2 == c1)
                                continue;
                            if (p_c1 == c2)
                                continue;
                            gain = -radius + distance[c1][c2] + distance[p_c1][p_c2] - distance[p_c2][c2];
                            if (gain < 0) {
                                h1 = p_c1;
                                h2 = c1;
                                h3 = p_c2;
                                h4 = c2;
                                gotoExchange = true;
                                break;
                            }
                        } else
                            break;
                    }
                }

                if (!gotoExchange) {
                    /* No exchange */
                    dlb[c1] = true;
                    continue;
                }

                if (gotoExchange) {
                    gotoExchange = false;
                    improvement_flag = true;
                    dlb[h1] = false;
                    dlb[h2] = false;
                    dlb[h3] = false;
                    dlb[h4] = false;
                    /* Now perform move */
                    if (pos[h3] < pos[h1]) {
                        help = h1;
                        h1 = h3;
                        h3 = help;
                        help = h2;
                        h2 = h4;
                        h4 = help;
                    }
                    if (pos[h3] - pos[h2] < n / 2 + 1) {
                        /* reverse inner part from pos[h2] to pos[h3] */
                        i = pos[h2];
                        j = pos[h3];
                        while (i < j) {
                            c1 = tour[i];
                            c2 = tour[j];
                            tour[i] = c2;
                            tour[j] = c1;
                            pos[c1] = j;
                            pos[c2] = i;
                            i++;
                            j--;
                        }
                    } else {
                        /* reverse outer part from pos[h4] to pos[h1] */
                        i = pos[h1];
                        j = pos[h4];
                        if (j > i)
                            help = n - (j - i) + 1;
                        else
                            help = (i - j) + 1;
                        help = help / 2;
                        for (h = 0; h < help; h++) {
                            c1 = tour[i];
                            c2 = tour[j];
                            tour[i] = c2;
                            tour[j] = c1;
                            pos[c1] = j;
                            pos[c2] = i;
                            i--;
                            j++;
                            if (i < 0)
                                i = n - 1;
                            if (j >= n)
                                j = 0;
                        }
                        tour[n] = tour[0];
                    }
                } else {
                    dlb[c1] = true;
                }

            }
        }
    }

}
