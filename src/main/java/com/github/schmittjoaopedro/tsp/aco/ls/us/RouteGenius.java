package com.github.schmittjoaopedro.tsp.aco.ls.us;

import java.util.HashSet;
import java.util.Set;

public class RouteGenius {

    public int MAX_K;

    public int MAX_K1;

    public float MAX_REAL = Float.MAX_VALUE;

    public Tour t;

    public NextNode[] p1;

    public NextNode[] p2;

    public float deltaIn;

    public float deltaOut;

    public boolean findEternalLoops = false;

    private Set<String> loopDetector = new HashSet<>();

    private CoordinatesGenius coordinatesGenius;

    public RouteGenius(CoordinatesGenius coordinatesGenius) {
        this.coordinatesGenius = coordinatesGenius;
        MAX_K = 3;
        MAX_K1 = 15;
        t = new Tour();
        t.nodeIntern = new int[coordinatesGenius.MAX_N + 1];
    }

    public RouteGenius(CoordinatesGenius coordinatesGenius, int MAX_K, int MAX_K1) {
        this.coordinatesGenius = coordinatesGenius;
        this.MAX_K = MAX_K;
        this.MAX_K1 = MAX_K1;
        t = new Tour();
        t.nodeIntern = new int[coordinatesGenius.MAX_N + 1];
    }

    public int getNext(int a, Coordinate g[]) {
        return (g[a].tourElem.next.node);
    }

    public int getPrev(int a, Coordinate g[]) {
        return (g[a].tourElem.prev.node);
    }

    public int getRank(int a, Coordinate g[]) {
        return (g[a].tourElem.rank);
    }

    public void link(int n1, int n2, Coordinate g[]) {
        g[n1].tourElem.next = g[n2].tourElem;
        g[n2].tourElem.prev = g[n1].tourElem;
    }

    public void inverse(int depart, int arrive, Coordinate g[]) {
        TourElem d, a, p, pp;
        int lng = 0;
        d = g[arrive].tourElem;
        a = g[depart].tourElem;
        p = d.next;
        while (d != a) {
            lng = lng + 1;
            pp = p.next;
            p.next = d;
            d.prev = p;
            d = p;
            p = pp;
        }
    }

    public void initialize() {
        p1 = new NextNode[coordinatesGenius.MAX_N + 1];
        p2 = new NextNode[coordinatesGenius.MAX_N + 1];
        for (int i = 0; i <= coordinatesGenius.MAX_N; i++) {
            t.nodeIntern[i] = 0;
        }
    }

    public void initNeighborhood(int n) {
        for (int i = 1; i <= n; i++) {
            p1[i] = new NextNode();
            p1[i].nn = new int[MAX_K1];
            p2[i] = new NextNode();
            p2[i].nn = new int[MAX_K1];
            for (int j = 1; j <= MAX_K + 1; j++) {
                p1[i].nn[j] = -1;
                p2[i].nn[j] = -1;
            }
            p1[i].theMostFarWay = 1;
            p2[i].theMostFarWay = 1;
            p1[i].maxDist = MAX_REAL;
            p2[i].maxDist = MAX_REAL;
        }
    }

    public void littleTurns(int city, Coordinate g[]) {
        t.nodeIntern[city + 1] = 1;
        t.nodesNumber = 1;
        t.ptr = new TourElem();
        t.ptr.node = city + 1;
        t.ptr.next = t.ptr;
        t.ptr.prev = t.ptr;
        g[city + 1].tourElem = t.ptr;
    }

    public void addNextNode(int aj, int n, float d[][]) {
        int nMaximum = 0;
        int k1;
        float vMaximum;
        float dist;
        k1 = MAX_K;
        aj = aj + 1;
        for (int i = 1; i <= n; i++) {
            if (i != aj) {
                if (p1[i].maxDist > d[i][aj]) {
                    p1[i].nn[p1[i].theMostFarWay] = aj;
                    if (p1[i].nn[k1 + 1] < 0) {
                        p1[i].nn[k1 + 1] = p1[i].theMostFarWay;
                    } else if (p1[i].maxDist < d[i][p1[i].nn[k1 + 1]]) {
                        p1[i].nn[k1 + 1] = p1[i].theMostFarWay;
                    }
                    vMaximum = 0;
                    for (int j = 1; j <= k1; j++) {
                        if (p1[i].nn[j] < 0) {
                            nMaximum = j;
                            vMaximum = MAX_REAL;
                            break;
                        } else {
                            dist = d[i][p1[i].nn[j]];
                            if (dist > vMaximum) {
                                nMaximum = j;
                                vMaximum = dist;
                            }
                        }
                    }
                    p1[i].theMostFarWay = nMaximum;
                    p1[i].maxDist = vMaximum;
                } else if (p1[i].nn[k1 + 1] < 0) {
                    p1[i].nn[k1 + 1] = aj;
                } else if (d[i][aj] < d[i][p1[i].nn[k1 + 1]]) {
                    p1[i].nn[k1 + 1] = aj;
                }
                if (p2[i].maxDist > d[aj][i]) {
                    p2[i].nn[p2[i].theMostFarWay] = aj;
                    if (p2[i].nn[k1 + 1] < 0) {
                        p2[i].nn[k1 + 1] = p2[i].theMostFarWay;
                    } else if (p2[i].maxDist < d[p2[i].nn[k1 + 1]][i]) {
                        p2[i].nn[k1 + 1] = p2[i].theMostFarWay;
                    }
                    vMaximum = 0;
                    for (int j = 1; j <= k1; j++) {
                        if (p2[i].nn[j] < 0) {
                            nMaximum = j;
                            vMaximum = MAX_REAL;
                            break;
                        } else {
                            dist = d[p2[i].nn[j]][i];
                            if (dist > vMaximum) {
                                nMaximum = j;
                                vMaximum = dist;
                            }
                        }
                    }
                    p2[i].theMostFarWay = nMaximum;
                    p2[i].maxDist = vMaximum;
                } else if (p2[i].nn[k1 + 1] < 0) {
                    p2[i].nn[k1 + 1] = aj;
                } else if (d[aj][i] < d[p2[i].nn[k1 + 1]][i]) {
                    p2[i].nn[k1 + 1] = aj;
                }
            }
        }
    }

    public void addOneTurns(int ind, Coordinate g[]) {
        TourElem p = t.ptr.prev;
        t.nodeIntern[ind + 1] = 1;
        t.nodesNumber++;
        t.ptr.prev = new TourElem();
        t.ptr.prev.node = ind + 1;
        t.ptr.prev.prev = p;
        t.ptr.prev.next = t.ptr;
        p.next = t.ptr.prev;
        g[ind + 1].tourElem = t.ptr.prev;
    }

    public boolean numberedTurns() {
        TourElem w;
        int i, j;
        boolean indTrue;
        int en[] = new int[coordinatesGenius.MAX_N + 1];
        for (i = 0; i <= coordinatesGenius.MAX_N; i++) {
            en[i] = 0;
        }
        indTrue = true;
        w = t.ptr;
        for (i = 1; i <= coordinatesGenius.MAX_N; i++) {
            w.rank = i;
            en[w.node] = 1;
            w = w.next;
            if (w == t.ptr) break;
        }
        for (j = 0; j <= coordinatesGenius.MAX_N; j++) {
            if (en[j] != t.nodeIntern[j]) {
                indTrue = false;
                break;
            }
        }
        if ((i != t.nodesNumber) || (!indTrue)) {
            System.out.println("\nWRONG ROUTE ASSIGNMENT ### END OF THE PROGRAM  \n");
            return false;
        } else {
            return true;
        }
    }

    private void add(int x, int k1, Coordinate g[], float d[][]) {
        int som1, som2, i, j, k, l, xi, xj, xk, xl, x_prev_nj, x_next_ni, ni, nl, nk, nj,
                next_ni = 0, next_nj, next_nl = 0, next_nk, prev_nk, prev_nl = 0, prev_nj, prev_ni = 0,
                xk1, yk1, neighbor_x_seen, neighbor_x2_seen, nl_worth;
        float delta, newDelta;
        TourElem px;
        Nodes nodes = new Nodes();
        nl = 0;
        xi = 0;
        xj = 0;
        xk = 0;
        xl = 0;
        xk1 = Math.min(t.nodesNumber, k1);
        yk1 = Math.min(t.nodesNumber - 1, k1);
        delta = MAX_REAL;
        for (i = 1; i <= xk1; i++) {
            ni = p2[x].nn[i];
            if (t.nodeIntern[ni] == 1) {
                next_ni = getNext(ni, g);
                prev_ni = getPrev(ni, g);
                xi = getRank(ni, g);
            }
            neighbor_x_seen = 0; /* FALSE */
            for (j = 1; (((j <= xk1) || (!(neighbor_x_seen == 1))) && (t.nodeIntern[ni] == 1)); j++) {
                nj = p1[x].nn[j];
                if (nj == next_ni) {
                    neighbor_x_seen = 1; /* TRUE */
                }
                if ((j > xk1) && (!(neighbor_x_seen == 1))) {
                    neighbor_x_seen = 1; /* TRUE */
                    nj = next_ni;
                }
                if (t.nodeIntern[nj] == 1) {
                    xj = getRank(nj, g);
                }
                if ((xj != xi) && (t.nodeIntern[nj] == 1)) {
                    next_nj = getNext(nj, g);
                    prev_nj = getPrev(nj, g);
                    neighbor_x2_seen = 0;  /* FALSE */
                    for (l = 1; l <= (2 * yk1 + 1); l++) {
                        nl_worth = 0; /* FALSE */
                        if (l <= yk1) {
                            nl = p1[next_ni].nn[l];
                        } else if ((l == (yk1 + 1)) && (!(neighbor_x2_seen == 1))) {
                            nl = getNext(next_ni, g);
                        } else if (l > (yk1 + 1)) {
                            nl = p2[prev_nj].nn[l - yk1 - 1];
                        } else {
                            nl_worth = 1; /* TRUE */
                        }
                        if ((nl == getNext(next_ni, g)) && (l <= yk1)) {
                            neighbor_x2_seen = 1; /* TRUE */
                        }
                        if ((!(nl_worth == 1)) && (t.nodeIntern[nl] == 1)) {
                            xl = getRank(nl, g);
                            prev_nl = getPrev(nl, g);
                            next_nl = getNext(nl, g);
                        }
                        if ((!((l == (yk1 + 1)) && (neighbor_x2_seen == 1))) && (t.nodeIntern[nl] == 1)) {
                            if (((xj > xi) && ((xl < xi) || (xl > xj))) || ((xj < xi) && ((xl > xj) && (xl < xi)))) {
                                if (l <= yk1) {
                                    newDelta = d[ni][x] + d[x][nj] + d[next_ni][nl] + d[next_nj][next_nl] - d[ni][next_ni] - d[nj][next_nj] - d[nl][next_nl];
                                    som1 = nj;
                                    while (som1 != next_ni) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = nl;
                                    while (som1 != next_nj) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    if (newDelta < delta) {
                                        delta = newDelta;
                                        nodes.typeToAdd = 1;
                                        nodes.i = ni;
                                        nodes.x = x;
                                        nodes.j = nj;
                                        nodes.si = next_ni;
                                        nodes.l = nl;
                                        nodes.sj = next_nj;
                                        nodes.sl = next_nl;
                                    }
                                } /* END THEN OF if (l <= yk1) */ else {
                                    newDelta = d[ni][x] + d[x][nj] + d[nl][prev_nj] + d[prev_nl][prev_ni] - d[prev_ni][ni] - d[prev_nj][nj] - d[prev_nl][nl];
                                    som1 = prev_nj;
                                    while (som1 != ni) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = prev_ni;
                                    while (som1 != nl) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    if (newDelta < delta) {
                                        delta = newDelta;
                                        nodes.typeToAdd = 3;
                                        nodes.i = ni;
                                        nodes.x = x;
                                        nodes.j = nj;
                                        nodes.l = nl;
                                        nodes.pi = prev_ni;
                                        nodes.pj = prev_nj;
                                        nodes.pl = prev_nl;
                                    }
                                } /* END ELSE OF if (l <= yk1) */
                                if ((((xl != getRank(next_nj, g)) && (l <= (yk1 + 1))) ||
                                        ((xl != getRank(prev_ni, g)) && (l > (yk1 + 1)))) && (xj != getRank(next_ni, g))) {
                                    for (k = 1; k <= yk1; k++) {
                                        if (l <= yk1 + 1) {
                                            nk = p2[next_nj].nn[k];
                                        } else {
                                            nk = p1[prev_ni].nn[k];
                                        }
                                        if (t.nodeIntern[nk] == 1) {
                                            xk = getRank(nk, g);
                                        }
                                        x_next_ni = getRank(next_ni, g);
                                        x_prev_nj = getRank(prev_nj, g);
                                        if ((((((xj > x_next_ni) && (xk > x_next_ni) &&
                                                (xk <= xj)) || ((xj < x_next_ni) &&
                                                ((xk > x_next_ni) || (xk <= xj)))) &&
                                                (l <= (yk1 + 1))) || ((((x_prev_nj > xi) &&
                                                (xk >= xi) && (xk < x_prev_nj)) ||
                                                ((x_prev_nj < xi) && ((xk >= xi) ||
                                                        (xk < x_prev_nj)))) && (l > (yk1 + 1)))) &&
                                                (t.nodeIntern[nk] == 1)) {
                                            next_nk = getNext(nk, g);
                                            prev_nk = getPrev(nk, g);
                                            if (l <= (yk1 + 1)) {
                                                newDelta = d[ni][x] + d[x][nj] +
                                                        d[nk][next_nj] +
                                                        d[prev_nl][prev_nk] + d[next_ni][nl] -
                                                        d[ni][next_ni] - d[nj][next_nj] -
                                                        d[prev_nl][nl] - d[prev_nk][nk];
                                                som1 = nj;
                                                while (som1 != nk) {
                                                    som2 = getPrev(som1, g);
                                                    newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                som1 = prev_nk;
                                                while (som1 != next_ni) {
                                                    som2 = getPrev(som1, g);
                                                    newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                if (newDelta < delta) {
                                                    delta = newDelta;
                                                    nodes.typeToAdd = 2;
                                                    nodes.i = ni;
                                                    nodes.x = x;
                                                    nodes.j = nj;
                                                    nodes.k = nk;
                                                    nodes.sj = next_nj;
                                                    nodes.pk = prev_nk;
                                                    nodes.pl = prev_nl;
                                                    nodes.si = next_ni;
                                                    nodes.l = nl;
                                                }
                                            } /* END THEN OF if (l <= (yk1+1)) */ else {
                                                newDelta = d[ni][x] + d[x][nj] +
                                                        d[prev_ni][nk] +
                                                        d[next_nk][next_nl] + d[nl][prev_nj] -
                                                        d[prev_ni][ni] - d[prev_nj][nj] -
                                                        d[nl][next_nl] - d[nk][next_nk];
                                                som1 = nk;
                                                while (som1 != ni) {
                                                    som2 = getPrev(som1, g);
                                                    newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                som1 = prev_nj;
                                                while (som1 != next_nk) {
                                                    som2 = getPrev(som1, g);
                                                    newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                if (newDelta < delta) {
                                                    delta = newDelta;
                                                    nodes.typeToAdd = 4;
                                                    nodes.i = ni;
                                                    nodes.x = x;
                                                    nodes.j = nj;
                                                    nodes.k = nk;
                                                    nodes.pj = prev_nj;
                                                    nodes.sk = next_nk;
                                                    nodes.sl = next_nl;
                                                    nodes.pi = prev_ni;
                                                    nodes.l = nl;
                                                }
                                            } /* END ELSE OF if (l <= (yk1+1)) */
                                        } /* END OF if ((((((xj > x_next_ni) && ...*/
                                    } /* END OF for (k = 1 ; k <= yk1 ; k++) */
                                } /* END OF if ((((xl != getRank (next_nj,g)) ... */
                            } /* END OF if (((xj > xi) && ((xl < xi) ||... */
                        } /* END OF if ((!((l = ( yk1+1 )) && ... */
                    } /* END OF for (l = 1 ; l <= ( 2*yk1+1 ) ; l++) */
                } /* END OF if ((xj != xi) && ... */
            } /* END OF for (j = 1 ; (((j <= xk1)... */
        } /* END OF for (i = 1 ; i <= xk1 ; i++) */
        deltaIn = delta;
        px = new TourElem();
        g[x].tourElem = px;
        px.node = nodes.x;
        t.nodesNumber++;
        t.nodeIntern[nodes.x] = 1;
        /*System.out.printf("Stringing = %d\n", nodes.typeToAdd);
        System.out.printf("i = %d\n", nodes.i);
        System.out.printf("j = %d\n", nodes.j);
        System.out.printf("k = %d\n", nodes.k);
        System.out.printf("l = %d\n", nodes.l);
        System.out.printf("pi = %d\n", nodes.pi);
        System.out.printf("pj = %d\n", nodes.pj);
        System.out.printf("pk = %d\n", nodes.pk);
        System.out.printf("pl = %d\n", nodes.pl);
        System.out.printf("si = %d\n", nodes.si);
        System.out.printf("sj = %d\n", nodes.sj);
        System.out.printf("sk = %d\n", nodes.sk);
        System.out.printf("sl = %d\n", nodes.sl);
        System.out.printf("x = %d\n", nodes.x);*/
        switch (nodes.typeToAdd) {
            case 1:
                link(nodes.i, nodes.x, g);
                link(nodes.x, nodes.j, g);
                inverse(nodes.j, nodes.si, g);
                link(nodes.si, nodes.l, g);
                inverse(nodes.l, nodes.sj, g);
                link(nodes.sj, nodes.sl, g);
                break;
            case 2:
                link(nodes.i, nodes.x, g);
                link(nodes.x, nodes.j, g);
                inverse(nodes.j, nodes.k, g);
                link(nodes.k, nodes.sj, g);
                link(nodes.pl, nodes.pk, g);
                inverse(nodes.pk, nodes.si, g);
                link(nodes.si, nodes.l, g);
                break;
            case 3:  /* inverse type 1 */
                link(nodes.pl, nodes.pi, g);
                inverse(nodes.pi, nodes.l, g);
                link(nodes.l, nodes.pj, g);
                inverse(nodes.pj, nodes.i, g);
                link(nodes.i, nodes.x, g);
                link(nodes.x, nodes.j, g);
                break;
            case 4:  /* inverse type 2 */
                link(nodes.l, nodes.pj, g);
                inverse(nodes.pj, nodes.sk, g);
                link(nodes.sk, nodes.sl, g);
                link(nodes.pi, nodes.k, g);
                inverse(nodes.k, nodes.i, g);
                link(nodes.i, nodes.x, g);
                link(nodes.x, nodes.j, g);
                break;
        }
    }

    private void remove(int x, Coordinate g[], float d[][]) {
        Nodes nodes = new Nodes();
        float delta, newDelta;
        int ni, ni2, ni3, nj, nk, nl, j, k, l, xi, xj, xk, xl, xi2,
                next_nj, next_nl, next_nk, prev_nj, prev_nl, prev_nk, som1, som2,
                seen_ni2, seem_ni3, xk1, o;
        xk1 = Math.min(MAX_K, t.nodesNumber - 1);
        delta = MAX_REAL;
        if (t.ptr.node == x) {
            o = 0;
            do {
                t.ptr = g[o + 1].tourElem;
                o = o + 1;
            } while ((t.nodeIntern[o] == 1) && (t.ptr.node == x));
            numberedTurns();
        } /* END OF if (t->ptr->noeud == x) */
        ni = getPrev(x, g);
        xi = getRank(ni, g);
        ni2 = getNext(getNext(ni, g), g);
        xi2 = getRank(ni2, g);
        if (ni2 == x) {
            seen_ni2 = 1; /* TRUE */
        } else {
            seen_ni2 = 0; /* FALSE */
        }
        for (j = 1; ((j <= xk1) || (!(seen_ni2 == 1))); j++) {
            if (j > xk1) {
                nj = ni2;
                seen_ni2 = 1; /* TRUE */
            } else {
                nj = p1[ni].nn[j];
            }
            if (nj != x) {
                next_nj = getNext(nj, g);
                xj = getRank(nj, g);
                ni3 = getNext(ni2, g);
                if ((ni3 == ni) || (ni3 == nj) || (ni3 == x)) {
                    seem_ni3 = 1; /* TRUE */
                } else {
                    seem_ni3 = 0; /* FALSE */
                }
                for (l = 1; ((l <= xk1) || (!(seem_ni3 == 1))); l++) {
                    if (l > xk1) {
                        nl = ni3;
                        seem_ni3 = 1; /* TRUE */
                    } else {
                        nl = p1[ni2].nn[l];
                    }
                    if (!((nl == ni) || (nl == nj) || (nl == x))) {
                        next_nl = getNext(nl, g);
                        prev_nl = getPrev(nl, g);
                        xl = getRank(nl, g);
                        if (((xj > xi) && ((xl > xj) || (xl < xi))) || ((xj < xi) && (xl > xj) && (xl < xi))) {
                            newDelta = d[ni][nj] + d[ni2][nl] + d[next_nj][next_nl] - d[ni][x] - d[x][ni2] - d[nj][next_nj] - d[nl][next_nl];
                            som1 = nj;
                            while (som1 != ni2) {
                                som2 = getPrev(som1, g);
                                newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                som1 = som2;
                            }
                            som1 = nl;
                            while (som1 != next_nj) {
                                som2 = getPrev(som1, g);
                                newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                som1 = som2;
                            }
                            if (newDelta < delta) {
                                delta = newDelta;
                                nodes.typeToDel = 1;
                                nodes.i = ni;
                                nodes.x = x;
                                nodes.j = nj;
                                nodes.i2 = ni2;
                                nodes.sj = next_nj;
                                nodes.sl = next_nl;
                                nodes.l = nl;
                            }
                        } /* END THEN OF if ((( xj > xi ) && (( xl > xj )... */ else {
                            for (k = 1; k <= xk1; k++) {
                                nk = p2[next_nj].nn[k];
                                xk = getRank(nk, g);
                                next_nk = getNext(nk, g);
                                if (((xj > xl) && (xk < xj) && (xk >= xl)) || ((xj < xl) && ((xk < xj) || (xk >= xl)))) {
                                    newDelta = d[ni][nj] + d[next_nk][prev_nl] + d[ni2][nl] + d[nk][next_nj] -
                                            d[ni][x] - d[x][ni2] - d[prev_nl][nl] - d[nk][next_nk] - d[nj][next_nj];
                                    som1 = nj;
                                    while (som1 != next_nk) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = prev_nl;
                                    while (som1 != ni2) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    if (newDelta < delta) {
                                        delta = newDelta;
                                        nodes.typeToDel = 2;
                                        nodes.i = ni;
                                        nodes.k = nk;
                                        nodes.j = nj;
                                        nodes.sj = next_nj;
                                        nodes.sk = next_nk;
                                        nodes.pl = prev_nl;
                                        nodes.i2 = ni2;
                                        nodes.l = nl;
                                        nodes.x = x;
                                    }
                                } /* END OF if (((xj > xl) && (xk < xj) && ... */
                            } /* END OF for (k = 1 ; k <= xk1 ; k++) */
                        } /* END ELSE OF if ((( xj > xi ) && (( xl > xj )... */
                    } /* END OF if (!((nl==ni) || (nl==nj) || (nl==x))) */
                } /* END OF for (l = 1;((l <= xk1) || (!seem_ni3));l++) */
            } /* END OF if (nj != x) */
        } /* END OF for (j =1 ; ((j <= xk1) || (!seen_ni2)) ; j++) */
        for (j = 1; j <= xk1; j++) {
            nj = p2[ni2].nn[j];
            if (nj != x) {
                prev_nj = getPrev(nj, g);
                xj = getRank(nj, g);
                for (l = 1; l <= xk1; l++) {
                    nl = p2[ni].nn[l];
                    next_nl = getNext(nl, g);
                    prev_nl = getPrev(nl, g);
                    xl = getRank(nl, g);
                    if (!((nl == ni2) || (nl == nj) || (nl == x))) {
                        if (((xi2 > xj) && ((xl > xi2) || (xl < xj))) || ((xi2 < xj) && (xl > xi2) && (xl < xj))) {
                            newDelta = d[nj][ni2] + d[nl][ni] + d[prev_nl][prev_nj] - d[ni][x] - d[x][ni2] - d[prev_nj][nj] - d[prev_nl][nl];
                            som1 = ni;
                            while (som1 != nj) {
                                som2 = getPrev(som1, g);
                                newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                som1 = som2;
                            }
                            som1 = prev_nj;
                            while (som1 != nl) {
                                som2 = getPrev(som1, g);
                                newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                som1 = som2;
                            }
                            if (newDelta < delta) {
                                delta = newDelta;
                                nodes.typeToDel = 3;
                                nodes.i2 = ni2;
                                nodes.j = nj;
                                nodes.i = ni;
                                nodes.l = nl;
                                nodes.pj = prev_nj;
                                nodes.pl = prev_nl;
                                nodes.x = x;
                            }
                        } /* END THEN OF if (((xi2>xj) && ((xl>xi2) ||... */ else {
                            for (k = 1; k <= xk1; k++) {
                                nk = p1[prev_nj].nn[k];
                                prev_nk = getPrev(nk, g);
                                xk = getRank(nk, g);
                                if (((xl > xj) && (xk > xj) && (xk <= xl)) || ((xl < xj) && ((xk > xj) || (xk <= xl)))) {
                                    newDelta = d[nj][ni2] + d[next_nl][prev_nk] +
                                            d[nl][ni] + d[prev_nj][nk] - d[ni][x] -
                                            d[x][ni2] - d[nl][next_nl] -
                                            d[prev_nk][nk] - d[prev_nj][nj];
                                    som1 = ni;
                                    while (som1 != next_nl) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = prev_nk;
                                    while (som1 != nj) {
                                        som2 = getPrev(som1, g);
                                        newDelta = newDelta - d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    if (newDelta < delta) {
                                        delta = newDelta;
                                        nodes.typeToDel = 4;
                                        nodes.i2 = ni2;
                                        nodes.j = nj;
                                        nodes.pk = prev_nk;
                                        nodes.sl = next_nl;
                                        nodes.i = ni;
                                        nodes.l = nl;
                                        nodes.k = nk;
                                        nodes.pj = prev_nj;
                                        nodes.x = x;
                                    }
                                } /* END OF if (((xl>xj) && (xk >xj) && ... */
                            } /* END OF for (k = 1 ; k <= xk1 ; k++) */
                        } /* END ELSE OF if (((xi2>xj) && ((xl>xi2) ||... */
                    } /* END OF if (!((nl==ni2) || (nl==nj) || (nl==x))) */
                } /* END OF for (l = 1 ; l <= xk1 ; l++) */
            } /* END OF if (nj != x) */
        } /* END OF for (j = 1 ; j <= xk1 ; j++) */
        deltaOut = delta;
        t.nodesNumber--;
        t.nodeIntern[x] = 0;
        /*System.out.printf("Unstringing = %d\n", nodes.typeToDel);
        System.out.printf("i = %d\n", nodes.i);
        System.out.printf("i2 = %d\n", nodes.i2);
        System.out.printf("j = %d\n", nodes.j);
        System.out.printf("k = %d\n", nodes.k);
        System.out.printf("l = %d\n", nodes.l);
        System.out.printf("pj = %d\n", nodes.pj);
        System.out.printf("pk = %d\n", nodes.pk);
        System.out.printf("pl = %d\n", nodes.pl);
        System.out.printf("sj = %d\n", nodes.sj);
        System.out.printf("sk = %d\n", nodes.sk);
        System.out.printf("sl = %d\n", nodes.sl);
        System.out.printf("x = %d\n", nodes.x);*/
        switch (nodes.typeToDel) {
            case 1:
                link(nodes.i, nodes.j, g);
                inverse(nodes.j, nodes.i2, g);
                link(nodes.i2, nodes.l, g);
                inverse(nodes.l, nodes.sj, g);
                link(nodes.sj, nodes.sl, g);
                break;
            case 2:
                link(nodes.i, nodes.j, g);
                inverse(nodes.j, nodes.sk, g);
                link(nodes.sk, nodes.pl, g);
                inverse(nodes.pl, nodes.i2, g);
                link(nodes.i2, nodes.l, g);
                link(nodes.k, nodes.sj, g);
                break;
            case 3:  /* inverse du type 1 */
                inverse(nodes.i, nodes.j, g);
                link(nodes.j, nodes.i2, g);
                link(nodes.pl, nodes.pj, g);
                inverse(nodes.pj, nodes.l, g);
                link(nodes.l, nodes.i, g);
                break;
            case 4:  /* inverse du type 2 */
                link(nodes.pj, nodes.k, g);
                link(nodes.l, nodes.i, g);
                inverse(nodes.i, nodes.sl, g);
                link(nodes.sl, nodes.pk, g);
                inverse(nodes.pk, nodes.j, g);
                link(nodes.j, nodes.i2, g);
                break;
        }
        numberedTurns();
    }

    public void routeCopy(int n, Coordinate g[], float d[][], double tour) {
        Coordinate g2[] = new Coordinate[coordinatesGenius.MAX_N + 1];
        Tour t2 = new Tour();
        t2.nodeIntern = new int[coordinatesGenius.MAX_N + 1];
        TourElem pt;
        TourElem w;
        int x, cpt;
        int neigh;
        float exc, cost_t;
        cpt = 0;
        neigh = MAX_K;
        for (int i = 0; i <= coordinatesGenius.MAX_N; i++) {
            g2[i] = new Coordinate();
            if (g[i] != null) {
                g2[i].x = g[i].x;
                g2[i].y = g[i].y;
                g2[i].tourElem = null;
                t2.nodeIntern[i] = t.nodeIntern[i];
            }
        }
        for (int i = 1; i <= n; i++) {
            if (g2[i].tourElem == null) g2[i].tourElem = new TourElem();
        }
        for (int i = 1; i <= n; i++) {
            pt = g2[i].tourElem;
            pt.node = i;
            pt.next = g2[g[i].tourElem.next.node].tourElem;
            pt.prev = g2[g[i].tourElem.prev.node].tourElem;
        }
        t2.nodesNumber = t.nodesNumber;
        t2.ptr = g2[1].tourElem;
        /********************numbered turns 2***********************************/
        w = t2.ptr;
        for (int i = 1; i <= coordinatesGenius.MAX_N; i++) {
            w.rank = i;
            w = w.next;
            if (w == t2.ptr) break;
        }
        /******************End of numbered turns 2********************************/
        exc = (float) tour;
        x = 1;
        do {
            remove(x, g, d);
            neigh++;
            add(x, neigh, g, d);
            numberedTurns();
            neigh--;
            if ((deltaOut + deltaIn) < 0) {
                cost_t = exc + (deltaOut + deltaIn);
                if (exc > cost_t) {
                    exc = cost_t;
                    cpt = 0;
                    for (int i = 1; i <= coordinatesGenius.MAX_N; i++) {
                        if (g[i] != null) {
                            g2[i].x = g[i].x;
                            g2[i].y = g[i].y;
                            g2[i].tourElem = null;
                            t2.nodeIntern[i] = t.nodeIntern[i];
                        }
                    }
                    for (int i = 1; i <= n; i++) {
                        if (g2[i].tourElem == null) {
                            g2[i].tourElem = new TourElem();
                        }
                    }
                    for (int i = 1; i <= n; i++) {
                        pt = g2[i].tourElem;
                        pt.node = i;
                        pt.next = g2[g[i].tourElem.next.node].tourElem;
                        pt.prev = g2[g[i].tourElem.prev.node].tourElem;
                    }
                    t2.nodesNumber = t.nodesNumber;
                    t2.ptr = g2[1].tourElem;
                    /********************numbered turns 2***********************************/
                    w = t2.ptr;
                    for (int i = 1; i <= coordinatesGenius.MAX_N; i++) {
                        w.rank = i;
                        w = w.next;
                        if (w == t2.ptr) break;
                    }
                    /******************End of numbered turns 2*****************************/
                }
            }
            x = getNext(x, g2);
            cpt++;
            for (int i = 1; i <= coordinatesGenius.MAX_N; i++) {
                if (g[i] != null) {
                    g[i].x = g2[i].x;
                    g[i].y = g2[i].y;
                    g[i].tourElem = null;
                    t.nodeIntern[i] = t2.nodeIntern[i];
                }
            }
            for (int i = 1; i <= n; i++) {
                if (g[i].tourElem == null) g[i].tourElem = new TourElem();
            }
            for (int i = 1; i <= n; i++) {
                pt = g[i].tourElem;
                pt.node = i;
                pt.next = g[g2[i].tourElem.next.node].tourElem;
                pt.prev = g[g2[i].tourElem.prev.node].tourElem;
            }
            t.nodesNumber = t2.nodesNumber;
            t.ptr = g[1].tourElem;
            numberedTurns();
            if (findEternalLoops) {
                if (loopDetector.contains(deltaOut + "+" + deltaIn)) {
                    break;
                }  else {
                    loopDetector.add(deltaOut + "+" + deltaIn);
                }
            }
        } while (cpt != n);
    }
}
