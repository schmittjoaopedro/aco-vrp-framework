package com.github.schmittjoaopedro.aco.ls.us;

import com.github.schmittjoaopedro.graph.Graph;

public class coordG {


    int task;
    coord g[] = new coord[routeG.MAXN + 1];
    double d[][] = new double[routeG.MAXN + 1][routeG.MAXN + 1];

    void xyvalues(int n, int cities, double x, double y)
    {
        task = n;
        g[cities + 1] = new coord();
        g[cities + 1].x = x;
        g[cities + 1].y = y;
    }

    void distances()
    {
        // cout << "new" << endl;
        for (int i = 1; i <= (task); i++)
        {
            for (int j = 1; j <= (task); j++)
            {
                if (i != j) {
                    d[i][j] = Math.sqrt(Math.pow((g[i].x - g[j].x), 2) + Math.pow((g[i].y - g[j].y), 2)) + 0.5;
                    d[i][j] = (int)d[i][j] * 1.0;
                    // cout << d[i][j] << " , ";
                }
                // d[j][i] = d[i][j];
            }
        }
        // cout << "" << endl;
    }

    void distances(Graph graph)
    {
        for (int i = 1; i <= (task); i++)
        {
            for (int j = 1; j <= (task); j++)
            {
                if (i != j) {
                    d[i][j] = graph.getEdge(i-1, j-1).getCost();
                }
            }
        }
    }

}
