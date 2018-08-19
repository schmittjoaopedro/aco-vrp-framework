package com.github.schmittjoaopedro.aco.ls.us;

public class routeG {

    public static int NBGRAND = 5;
    public static int MAXK = 3;
    public static int MINK = 4;
    public static int MAXN = 500;
    public static int MAXK1 = 15;
    public static double MAXREAL = 1.7e38;

    tourne t = new tourne();
    tourne t2 = new tourne();
    proxnoeud p1[] = new proxnoeud[MAXN + 1];
    proxnoeud p2[] = new proxnoeud[MAXN + 1];

    double deltain, deltaout;

    /*********************************************************************************************/
    int suiv(int a, coord g[])
    {
        return(g[a].ptrtourne.prochain.noeud);
    }
/**********END OF SUIV****************/

    /*********************************************************************************************/
    int prec(int a, coord g[])
    {
        return(g[a].ptrtourne.precedent.noeud);
    }
/**********END OF SUIV****************/

    /*********************************************************************************************/
    int ordre(int a, coord g[])
    {
        return(g[a].ptrtourne.rang);
    }
/**********END OF ORDRE****************/

    /*********************************************************************************************/
    void chemin(int n1, int n2, coord g[])
    {
	/* THIS PROCEDURE CHANGE THE ORIENTATION OF THE POINTERS
	OF THE NODE n1 WITH THE NODE n2 AND VICE-VERSA. */
        g[n1].ptrtourne.prochain = g[n2].ptrtourne;
        g[n2].ptrtourne.precedent = g[n1].ptrtourne;

    } /* END OF CHEMIN  */

    /*********************************************************************************************/
    void inverse(int depart, int arrive, coord g[])
    {

        /* THIS PROCEDURE REVERSE THE PATH FROM DEPARTURE TO ARRIVAL. */

        tourneelem d, a, p, pp;
        int lng;
        int cptlng = 0;
        int lngtotal = 0;

        cptlng = cptlng + 1;
        lng = 0;
        a = g[depart].ptrtourne;
        d = g[arrive].ptrtourne;
        p = d.prochain;
        while (d != a)
        {
            lng = lng + 1;
            pp = p.prochain;
            p.prochain = d;
            d.precedent = p;
            d = p;
            p = pp;
        }
        lngtotal = lngtotal + lng;

    } /* END OF INVERSE */

    /********************************************************************/

    /*********************************************************************************************/
    void initialize()
    {
        int   lesk;
        for (lesk = 0; lesk <= MAXN; lesk++) t.noeudinterne[lesk] = 0;
    }

    /*********************************************************************************************/
    void initnneighbour(int n)
    {
        int i, j;

        for (i = 1; i <= n; i++)
        {
            p1[i] = new proxnoeud();
            p2[i] = new proxnoeud();
            for (j = 1; j <= MAXK + 1; j++)
            {
                p1[i].nn[j] = -1;
                p2[i].nn[j] = -1;
            }
            p1[i].leplusloin = 1;
            p2[i].leplusloin = 1;
            p1[i].maxdist = MAXREAL;
            p2[i].maxdist = MAXREAL;
        }

    }

    /*********************************************************************************************/
    void petittourne(int city, coord[] g)
    {
        t.noeudinterne[city + 1] = 1;
        t.nbredenoeuds = 1;
        t.ptr = new tourneelem();
        t.ptr.noeud = city + 1;
        t.ptr.prochain = t.ptr;
        t.ptr.precedent = t.ptr;
        g[city + 1].ptrtourne = t.ptr;
    }

    /*********************************************************************************************/
    void ajoutenoeudprox(int aj, int n, double d[][])
    {
        int i, nmaximum = 0, j;
        int k1;

        double vmaximum, dist;

        k1 = MAXK;
        aj = aj + 1;
        for (i = 1; i <= n; i++)
        {
            if (i != aj)
            {
                if (p1[i].maxdist > d[i][aj])
                {
                    p1[i].nn[p1[i].leplusloin] = aj;
                    if (p1[i].nn[k1 + 1] < 0)
                        p1[i].nn[k1 + 1] = p1[i].leplusloin;
                    else if (p1[i].maxdist < d[i][p1[i].nn[k1 + 1]])
                        p1[i].nn[k1 + 1] = p1[i].leplusloin;
                    vmaximum = 0;
                    for (j = 1; j <= k1; j++)
                    {
                        if (p1[i].nn[j] < 0)
                        {
                            nmaximum = j;
                            vmaximum = MAXREAL;
                            break;
                        }
                        else
                        {
                            dist = d[i][p1[i].nn[j]];
                            if (dist > vmaximum)
                            {
                                nmaximum = j;
                                vmaximum = dist;
                            }
                        }
                    }
                    p1[i].leplusloin = nmaximum;
                    p1[i].maxdist = vmaximum;
                }
                else if (p1[i].nn[k1 + 1] < 0)
                    p1[i].nn[k1 + 1] = aj;
                else if (d[i][aj] < d[i][p1[i].nn[k1 + 1]])
                    p1[i].nn[k1 + 1] = aj;
                if (p2[i].maxdist > d[aj][i])
                {
                    p2[i].nn[p2[i].leplusloin] = aj;
                    if (p2[i].nn[k1 + 1] < 0)
                        p2[i].nn[k1 + 1] = p2[i].leplusloin;
                    else if (p2[i].maxdist < d[p2[i].nn[k1 + 1]][i])
                        p2[i].nn[k1 + 1] = p2[i].leplusloin;
                    vmaximum = 0;
                    for (j = 1; j <= k1; j++)
                    {
                        if (p2[i].nn[j] < 0)
                        {
                            nmaximum = j;
                            vmaximum = MAXREAL;
                            break;
                        }
                        else
                        {
                            dist = d[p2[i].nn[j]][i];
                            if (dist > vmaximum)
                            {
                                nmaximum = j;
                                vmaximum = dist;
                            }
                        }
                    }
                    p2[i].leplusloin = nmaximum;
                    p2[i].maxdist = vmaximum;
                }
                else if (p2[i].nn[k1 + 1] < 0)
                    p2[i].nn[k1 + 1] = aj;
                else if (d[aj][i] < d[p2[i].nn[k1 + 1]][i])
                    p2[i].nn[k1 + 1] = aj;
            }
        }
    }

    /*********************************************************************************************/
    void ajoute_a_tourne(int ind, coord g[])
    {
	/* THIS PROCEDURE ADD TO THE ROUTE A NODE LABELED ind
	AND THE POINTER g[ind].ptr IS DIRECTIONED TO IT. */

        tourneelem p;

        p = t.ptr.precedent;
        t.noeudinterne[ind + 1] = 1;
        t.nbredenoeuds++;
        t.ptr.precedent = new tourneelem();
        t.ptr.precedent.noeud = ind + 1;
        t.ptr.precedent.precedent = p;
        t.ptr.precedent.prochain = t.ptr;
        p.prochain = t.ptr.precedent;
        g[ind + 1].ptrtourne = t.ptr.precedent;
    }

    /*********************************************************************************************/
    boolean numerote_tourne()
    {
        tourneelem w;
        int i, j, en[] = new int[MAXN + 1];
        boolean indtrue;

        for (i = 0; i <= MAXN; i++) en[i] = 0;
        indtrue = true;
        w = t.ptr;
        for (i = 1; i <= MAXN; i++)
        {
            w.rang = i;
            en[w.noeud] = 1;
            w = w.prochain;
            if (w == t.ptr) break;
        }
        for (j = 0; j <= MAXN; j++)
        {
            if (en[j] != t.noeudinterne[j])
            {
                indtrue = false;
                break;
            }
        }
        if ((i != t.nbredenoeuds) || (!indtrue))
        {
            System.out.printf("\nWRONG ROUTE ASSIGNMENT ### END OF THE PROGRAM  \n");
            return false;
        }
        else return true;
    }

    /*********************************************************************************************/
    void ajoutx(int x, int k1, coord g[], double d[][])
    {

        int som1, som2, i, j, k, l, xi, xj, xk, xl,
                xprecnj, xsuivni, ni, nl, nk, nj,
                suivni = 0, suivnj, suivnl = 0, suivnk,
                precnk, precnl = 0, precnj, precni = 0,
                xk1, yk1,
                voisinx_vu, deja_vuxi2, nlvaut0;

        double delta, nouvdelta;

        noeuds noeuds = new noeuds();

        tourneelem px;

        i = 0; j = 0; k = 0; l = 0;
        ni = 0; nj = 0; nk = 0; nl = 0;
        xi = 0; xj = 0; xk = 0; xl = 0;
        xk1 = Math.min(t.nbredenoeuds, k1);
        yk1 = Math.min(t.nbredenoeuds - 1, k1);
        delta = MAXREAL;

        for (i = 1; i <= xk1; i++)
        {
            ni = p2[x].nn[i];
            if (t.noeudinterne[ni] == 1)
            {
                suivni = suiv(ni, g);
                precni = prec(ni, g);
                xi = ordre(ni, g);
            }
            voisinx_vu = 0; /* FALSE */
            for (j = 1; (((j <= xk1) || (voisinx_vu == 0)) &&
                    (t.noeudinterne[ni] == 1)); j++)
            {
                nj = p1[x].nn[j];
                if (nj == suivni) voisinx_vu = 1; /* TRUE */
                if ((j > xk1) && (voisinx_vu == 0))
                {
                    voisinx_vu = 1; /* TRUE */
                    nj = suivni;
                }
                if (t.noeudinterne[nj] == 1) xj = ordre(nj, g);
                if ((xj != xi) && (t.noeudinterne[nj] == 1))
                {
                    suivnj = suiv(nj, g);
                    precnj = prec(nj, g);
                    deja_vuxi2 = 0;  /* FALSE */
                    for (l = 1; l <= (2 * yk1 + 1); l++)
                    {
                        nlvaut0 = 0; /* FALSE */
                        if (l <= yk1)
                            nl = p1[suivni].nn[l];
                        else
                        if ((l == (yk1 + 1)) && (deja_vuxi2 == 0))
                            nl = suiv(suivni, g);
                        else
                        if (l > (yk1 + 1))
                            nl = p2[precnj].nn[l - yk1 - 1];
                        else nlvaut0 = 1; /* TRUE */
                        if ((nl == suiv(suivni, g)) && (l <= yk1))
                            deja_vuxi2 = 1; /* TRUE */
                        if ((nlvaut0 == 0) && (t.noeudinterne[nl] == 1))
                        {
                            xl = ordre(nl, g);
                            precnl = prec(nl, g);
                            suivnl = suiv(nl, g);
                        }
                        if ((!((l == (yk1 + 1)) && (deja_vuxi2 == 1))) &&
                                (t.noeudinterne[nl] == 1))
                        {
                            if (((xj > xi) && ((xl < xi) || (xl > xj))) ||
                                    ((xj < xi) && ((xl > xj) && (xl < xi))))
                            {
                                if (l <= yk1)
                                {
                                    nouvdelta = d[ni][x] + d[x][nj] + d[suivni][nl] +
                                            d[suivnj][suivnl] - d[ni][suivni] -
                                            d[nj][suivnj] - d[nl][suivnl];
                                    som1 = nj;
                                    while (som1 != suivni)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = nl;
                                    while (som1 != suivnj)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    //                        delta = min (delta , nouvdelta);
                                    if (nouvdelta < delta)
                                    {
                                        delta = nouvdelta;
                                        /*.s pour successeur et .p pour predecesseur*/
                                        noeuds.typeajout = 1;
                                        noeuds.i = ni;
                                        noeuds.x = x;
                                        noeuds.j = nj;
                                        noeuds.si = suivni;
                                        noeuds.l = nl;
                                        noeuds.sj = suivnj;
                                        noeuds.sl = suivnl;
                                    }
                                } /* END THEN OF if (l <= yk1) */
                                else
                                {
                                    nouvdelta = d[ni][x] + d[x][nj] + d[nl][precnj] +
                                            d[precnl][precni] - d[precni][ni] -
                                            d[precnj][nj] - d[precnl][nl];
                                    som1 = precnj;
                                    while (som1 != ni)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = precni;
                                    while (som1 != nl)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    //                        delta = min ( delta, nouvdelta );
                                    if (nouvdelta < delta)
                                    {
                                        delta = nouvdelta;
                                        noeuds.typeajout = 3;
                                        noeuds.i = ni;
                                        noeuds.x = x;
                                        noeuds.j = nj;
                                        noeuds.l = nl;
                                        noeuds.pi = precni;
                                        noeuds.pj = precnj;
                                        noeuds.pl = precnl;
                                    }
                                } /* END ELSE OF if (l <= yk1) */
                                if ((((xl != ordre(suivnj, g)) && (l <= (yk1 + 1))) ||
                                        ((xl != ordre(precni, g)) && (l > (yk1 + 1)))) &&
                                        (xj != ordre(suivni, g)))
                                {
                                    for (k = 1; k <= yk1; k++)
                                    {
                                        if (l <= yk1 + 1)
                                            nk = p2[suivnj].nn[k];
                                        else
                                            nk = p1[precni].nn[k];
                                        if (t.noeudinterne[nk] == 1)
                                            xk = ordre(nk, g);
                                        xsuivni = ordre(suivni, g);
                                        xprecnj = ordre(precnj, g);
                                        if ((((((xj > xsuivni) && (xk > xsuivni) &&
                                                (xk <= xj)) || ((xj < xsuivni) &&
                                                ((xk > xsuivni) || (xk <= xj)))) &&
                                                (l <= (yk1 + 1))) || ((((xprecnj > xi) &&
                                                (xk >= xi) && (xk < xprecnj)) ||
                                                ((xprecnj < xi) && ((xk >= xi) ||
                                                        (xk < xprecnj)))) && (l >(yk1 + 1)))) &&
                                                (t.noeudinterne[nk] == 1))
                                        {
                                            suivnk = suiv(nk, g);
                                            precnk = prec(nk, g);
                                            if (l <= (yk1 + 1))
                                            {
                                                nouvdelta = d[ni][x] + d[x][nj] +
                                                        d[nk][suivnj] +
                                                        d[precnl][precnk] + d[suivni][nl] -
                                                        d[ni][suivni] - d[nj][suivnj] -
                                                        d[precnl][nl] - d[precnk][nk];
                                                som1 = nj;
                                                while (som1 != nk)
                                                {
                                                    som2 = prec(som1, g);
                                                    nouvdelta = nouvdelta -
                                                            d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                som1 = precnk;
                                                while (som1 != suivni)
                                                {
                                                    som2 = prec(som1, g);
                                                    nouvdelta = nouvdelta -
                                                            d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                //                                  delta = min ( delta, nouvdelta );
                                                if (nouvdelta < delta)
                                                {
                                                    delta = nouvdelta;
                                                    noeuds.typeajout = 2;
                                                    noeuds.i = ni;
                                                    noeuds.x = x;
                                                    noeuds.j = nj;
                                                    noeuds.k = nk;
                                                    noeuds.sj = suivnj;
                                                    noeuds.pk = precnk;
                                                    noeuds.pl = precnl;
                                                    noeuds.si = suivni;
                                                    noeuds.l = nl;
                                                }
                                            } /* END THEN OF if (l <= (yk1+1)) */
                                            else
                                            {
                                                nouvdelta = d[ni][x] + d[x][nj] +
                                                        d[precni][nk] +
                                                        d[suivnk][suivnl] + d[nl][precnj] -
                                                        d[precni][ni] - d[precnj][nj] -
                                                        d[nl][suivnl] - d[nk][suivnk];
                                                som1 = nk;
                                                while (som1 != ni)
                                                {
                                                    som2 = prec(som1, g);
                                                    nouvdelta = nouvdelta -
                                                            d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                som1 = precnj;
                                                while (som1 != suivnk)
                                                {
                                                    som2 = prec(som1, g);
                                                    nouvdelta = nouvdelta -
                                                            d[som2][som1] + d[som1][som2];
                                                    som1 = som2;
                                                }
                                                //                                  delta = min ( delta, nouvdelta );
                                                if (nouvdelta < delta)
                                                {
                                                    delta = nouvdelta;
                                                    noeuds.typeajout = 4;
                                                    noeuds.i = ni;
                                                    noeuds.x = x;
                                                    noeuds.j = nj;
                                                    noeuds.k = nk;
                                                    noeuds.pj = precnj;
                                                    noeuds.sk = suivnk;
                                                    noeuds.sl = suivnl;
                                                    noeuds.pi = precni;
                                                    noeuds.l = nl;
                                                }
                                            } /* END ELSE OF if (l <= (yk1+1)) */
                                        } /* END OF if ((((((xj > xsuivni) && ...*/
                                    } /* END OF for (k = 1 ; k <= yk1 ; k++) */
                                } /* END OF if ((((xl != ordre (suivnj,g)) ... */
                            } /* END OF if (((xj > xi) && ((xl < xi) ||... */
                        } /* END OF if ((!((l = ( yk1+1 )) && ... */
                    } /* END OF for (l = 1 ; l <= ( 2*yk1+1 ) ; l++) */
                } /* END OF if ((xj != xi) && ... */
            } /* END OF for (j = 1 ; (((j <= xk1)... */
        } /* END OF for (i = 1 ; i <= xk1 ; i++) */
        deltain = delta;
        px = new tourneelem();
        g[x].ptrtourne = px;
        px.noeud = noeuds.x;
        t.nbredenoeuds++;
        t.noeudinterne[noeuds.x] = 1;
        switch (noeuds.typeajout)
        {
            case 1:
                chemin(noeuds.i, noeuds.x, g);
                chemin(noeuds.x, noeuds.j, g);
                inverse(noeuds.j, noeuds.si, g);
                chemin(noeuds.si, noeuds.l, g);
                inverse(noeuds.l, noeuds.sj, g);
                chemin(noeuds.sj, noeuds.sl, g);
                break;
            case 2:
                chemin(noeuds.i, noeuds.x, g);
                chemin(noeuds.x, noeuds.j, g);
                inverse(noeuds.j, noeuds.k, g);
                chemin(noeuds.k, noeuds.sj, g);
                chemin(noeuds.pl, noeuds.pk, g);
                inverse(noeuds.pk, noeuds.si, g);
                chemin(noeuds.si, noeuds.l, g);
                break;
            case 3:  /* inverse du type 1 */
                chemin(noeuds.pl, noeuds.pi, g);
                inverse(noeuds.pi, noeuds.l, g);
                chemin(noeuds.l, noeuds.pj, g);
                inverse(noeuds.pj, noeuds.i, g);
                chemin(noeuds.i, noeuds.x, g);
                chemin(noeuds.x, noeuds.j, g);
                break;
            case 4:  /* inverse du type 2 */
                chemin(noeuds.l, noeuds.pj, g);
                inverse(noeuds.pj, noeuds.sk, g);
                chemin(noeuds.sk, noeuds.sl, g);
                chemin(noeuds.pi, noeuds.k, g);
                inverse(noeuds.k, noeuds.i, g);
                chemin(noeuds.i, noeuds.x, g);
                chemin(noeuds.x, noeuds.j, g);
                break;
        }

    }

    /*********************************************************************************************/

    void oterx(int x, int k1, coord g[], double d[][])


    {

	/* THIS PROCEDURE IS THE OPOSITE OPERATION OF THE PROCEDURE
	ajoutx.
	THE RESULT IS IN t AND IS UPDATED LIKE THE K1 NEAREST
	NEIGHBOORS.   */

        noeuds noeuds = new noeuds();

        double delta, nouvdelta;

        int ni, ni2, ni3, nj, nk, nl, j, k, l, xi, xj, xk, xl, xi2,
                suivnj, suivnl, suivnk, precnj, precnl, precnk, som1, som2,
                vu_ni2, vu_ni3,
                xk1, o;

        xk1 = Math.min(MAXK, t.nbredenoeuds - 1);
        delta = MAXREAL;
        if (t.ptr.noeud == x)
        {
            o = 0;
            do
            {
                t.ptr = g[o + 1].ptrtourne;
                o = o + 1;
            } while ((t.noeudinterne[o] == 1) && (t.ptr.noeud == x));
            numerote_tourne();
        } /* END OF if (t->ptr->noeud == x) */
        ni = prec(x, g);
        xi = ordre(ni, g);
        ni2 = suiv(suiv(ni, g), g);
        xi2 = ordre(ni2, g);
        if (ni2 == x)
            vu_ni2 = 1; /* TRUE */
        else vu_ni2 = 0; /* FALSE */
        for (j = 1; ((j <= xk1) || (vu_ni2 == 0)); j++)
        {
            if (j > xk1)
            {
                nj = ni2;
                vu_ni2 = 1; /* TRUE */
            }
            else nj = p1[ni].nn[j];
            if (nj != x)
            {
                suivnj = suiv(nj, g);
                xj = ordre(nj, g);
                ni3 = suiv(ni2, g);
                if ((ni3 == ni) || (ni3 == nj) || (ni3 == x))
                    vu_ni3 = 1; /* TRUE */
                else vu_ni3 = 0; /* FALSE */
                for (l = 1; ((l <= xk1) || (vu_ni3 == 0)); l++)
                {
                    if (l > xk1)
                    {
                        nl = ni3;
                        vu_ni3 = 1; /* TRUE */
                    }
                    else nl = p1[ni2].nn[l];
                    if (!((nl == ni) || (nl == nj) || (nl == x)))
                    {
                        suivnl = suiv(nl, g);
                        precnl = prec(nl, g);
                        xl = ordre(nl, g);
                        if (((xj > xi) && ((xl > xj) || (xl < xi))) ||
                                ((xj < xi) && (xl > xj) && (xl < xi)))
                        {
                            nouvdelta = d[ni][nj] + d[ni2][nl] + d[suivnj][suivnl] -
                                    d[ni][x] - d[x][ni2] - d[nj][suivnj] -
                                    d[nl][suivnl];
                            som1 = nj;
                            while (som1 != ni2)
                            {
                                som2 = prec(som1, g);
                                nouvdelta = nouvdelta - d[som2][som1] +
                                        d[som1][som2];
                                som1 = som2;
                            }
                            som1 = nl;
                            while (som1 != suivnj)
                            {
                                som2 = prec(som1, g);
                                nouvdelta = nouvdelta - d[som2][som1] +
                                        d[som1][som2];
                                som1 = som2;
                            }
                            //                 delta = min ( delta, nouvdelta );
                            System.out.println(nouvdelta + " < " + delta + " = " + 1);
                            if (nouvdelta < delta)
                            {
                                delta = nouvdelta;
                                noeuds.typeretrait = 1;
                                noeuds.i = ni;
                                noeuds.x = x;
                                noeuds.j = nj;
                                noeuds.i2 = ni2;
                                noeuds.sj = suivnj;
                                noeuds.sl = suivnl;
                                noeuds.l = nl;
                            }
                        } /* END THEN OF if ((( xj > xi ) && (( xl > xj )... */
                        else
                        {
                            for (k = 1; k <= xk1; k++)
                            {
                                nk = p2[suivnj].nn[k];
                                xk = ordre(nk, g);
                                suivnk = suiv(nk, g);
                                if (((xj > xl) && (xk < xj) && (xk >= xl)) ||
                                        ((xj < xl) && ((xk < xj) || (xk >= xl))))
                                {
                                    nouvdelta = d[ni][nj] + d[suivnk][precnl] +
                                            d[ni2][nl] + d[nk][suivnj] -
                                            d[ni][x] - d[x][ni2] -
                                            d[precnl][nl] - d[nk][suivnk] -
                                            d[nj][suivnj];
                                    som1 = nj;
                                    while (som1 != suivnk)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = precnl;
                                    while (som1 != ni2)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    //                        delta = min ( delta, nouvdelta );
                                    System.out.println(nouvdelta + " < " + delta + " = " + 2);
                                    if (nouvdelta < delta)
                                    {
                                        delta = nouvdelta;
                                        noeuds.typeretrait = 2;
                                        noeuds.i = ni;
                                        noeuds.k = nk;
                                        noeuds.j = nj;
                                        noeuds.sj = suivnj;
                                        noeuds.sk = suivnk;
                                        noeuds.pl = precnl;
                                        noeuds.i2 = ni2;
                                        noeuds.l = nl;
                                        noeuds.x = x;
                                    }
                                } /* END OF if (((xj > xl) && (xk < xj) && ... */
                            } /* END OF for (k = 1 ; k <= xk1 ; k++) */
                        } /* END ELSE OF if ((( xj > xi ) && (( xl > xj )... */
                    } /* END OF if (!((nl==ni) || (nl==nj) || (nl==x))) */
                } /* END OF for (l = 1;((l <= xk1) || (!vu_ni3));l++) */
            } /* END OF if (nj != x) */
        } /* END OF for (j =1 ; ((j <= xk1) || (!vu_ni2)) ; j++) */

        for (j = 1; j <= xk1; j++)
        {
            nj = p2[ni2].nn[j];
            if (nj != x)
            {
                suivnj = suiv(nj, g);
                precnj = prec(nj, g);
                xj = ordre(nj, g);
                for (l = 1; l <= xk1; l++)
                {
                    nl = p2[ni].nn[l];
                    suivnl = suiv(nl, g);
                    precnl = prec(nl, g);
                    xl = ordre(nl, g);
                    if (!((nl == ni2) || (nl == nj) || (nl == x)))
                    {
                        if (((xi2 > xj) && ((xl > xi2) || (xl < xj))) ||
                                ((xi2 < xj) && (xl > xi2) && (xl < xj)))
                        {
                            nouvdelta = d[nj][ni2] + d[nl][ni] + d[precnl][precnj] -
                                    d[ni][x] - d[x][ni2] - d[precnj][nj] -
                                    d[precnl][nl];
                            som1 = ni;
                            while (som1 != nj)
                            {
                                som2 = prec(som1, g);
                                nouvdelta = nouvdelta - d[som2][som1] +
                                        d[som1][som2];
                                som1 = som2;
                            }
                            som1 = precnj;
                            while (som1 != nl)
                            {
                                som2 = prec(som1, g);
                                nouvdelta = nouvdelta - d[som2][som1] +
                                        d[som1][som2];
                                som1 = som2;
                            }
                            //                  delta = min ( delta,nouvdelta );
                            System.out.println(nouvdelta + " < " + delta + " = " + 3);
                            if (nouvdelta < delta)
                            {
                                delta = nouvdelta;
                                noeuds.typeretrait = 3;
                                noeuds.i2 = ni2;
                                noeuds.j = nj;
                                noeuds.i = ni;
                                noeuds.l = nl;
                                noeuds.pj = precnj;
                                noeuds.pl = precnl;
                                noeuds.x = x;
                            }
                        } /* END THEN OF if (((xi2>xj) && ((xl>xi2) ||... */
                        else
                        {
                            for (k = 1; k <= xk1; k++)
                            {
                                nk = p1[precnj].nn[k];
                                suivnk = suiv(nk, g);
                                precnk = prec(nk, g);
                                xk = ordre(nk, g);
                                if (((xl>xj) && (xk >xj) && (xk <= xl)) ||
                                        ((xl<xj) && ((xk>xj) || (xk <= xl))))
                                {
                                    nouvdelta = d[nj][ni2] + d[suivnl][precnk] +
                                            d[nl][ni] + d[precnj][nk] - d[ni][x] -
                                            d[x][ni2] - d[nl][suivnl] -
                                            d[precnk][nk] - d[precnj][nj];
                                    som1 = ni;
                                    while (som1 != suivnl)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    som1 = precnk;
                                    while (som1 != nj)
                                    {
                                        som2 = prec(som1, g);
                                        nouvdelta = nouvdelta -
                                                d[som2][som1] + d[som1][som2];
                                        som1 = som2;
                                    }
                                    //                         delta = min ( delta, nouvdelta );
                                    System.out.println(nouvdelta + " < " + delta + " = " + 4);
                                    if (nouvdelta < delta)
                                    {
                                        delta = nouvdelta;
                                        noeuds.typeretrait = 4;
                                        noeuds.i2 = ni2;
                                        noeuds.j = nj;
                                        noeuds.pk = precnk;
                                        noeuds.sl = suivnl;
                                        noeuds.i = ni;
                                        noeuds.l = nl;
                                        noeuds.k = nk;
                                        noeuds.pj = precnj;
                                        noeuds.x = x;
                                    }
                                } /* END OF if (((xl>xj) && (xk >xj) && ... */
                            } /* END OF for (k = 1 ; k <= xk1 ; k++) */
                        } /* END ELSE OF if (((xi2>xj) && ((xl>xi2) ||... */
                    } /* END OF if (!((nl==ni2) || (nl==nj) || (nl==x))) */
                } /* END OF for (l = 1 ; l <= xk1 ; l++) */
            } /* END OF if (nj != x) */
        } /* END OF for (j = 1 ; j <= xk1 ; j++) */

	  /* THE FOLLOWING PROCEDURE IS SIMILAR TO THAT IN THE ajoutx.
	  IT PERFORMS THE UNASSIGNMENT OF THE x NODE IN t AND HERE
	  IT'S DONNE THE RENUMERATION OF THE ROUTE */
        deltaout = delta;
        g[x].ptrtourne = null;
        t.nbredenoeuds--;
        t.noeudinterne[x] = 0;
        switch (noeuds.typeretrait)
        {
            case 1:
                chemin(noeuds.i, noeuds.j, g);
                inverse(noeuds.j, noeuds.i2, g);
                chemin(noeuds.i2, noeuds.l, g);
                inverse(noeuds.l, noeuds.sj, g);
                chemin(noeuds.sj, noeuds.sl, g);
                break;
            case 2:
                chemin(noeuds.i, noeuds.j, g);
                inverse(noeuds.j, noeuds.sk, g);
                chemin(noeuds.sk, noeuds.pl, g);
                inverse(noeuds.pl, noeuds.i2, g);
                chemin(noeuds.i2, noeuds.l, g);
                chemin(noeuds.k, noeuds.sj, g);
                break;
            case 3:  /* inverse du type 1 */
                inverse(noeuds.i, noeuds.j, g);
                chemin(noeuds.j, noeuds.i2, g);
                chemin(noeuds.pl, noeuds.pj, g);
                inverse(noeuds.pj, noeuds.l, g);
                chemin(noeuds.l, noeuds.i, g);
                break;
            case 4:  /* inverse du type 2 */
                chemin(noeuds.pj, noeuds.k, g);
                chemin(noeuds.l, noeuds.i, g);
                inverse(noeuds.i, noeuds.sl, g);
                chemin(noeuds.sl, noeuds.pk, g);
                inverse(noeuds.pk, noeuds.j, g);
                chemin(noeuds.j, noeuds.i2, g);
                break;
        }
        numerote_tourne();

    } /* END OF OTERX */

    void route_copy(int n, coord g[], double d[][], double tour)
    {

	/* THIS IS THE POST OPTIMIZATION PROCEDURE OF THE PROCEDURE AJOUTEX.
	THE IDEA IS THE SAME, THEREFORE HERE THE UNASSIGNMENT AND
	REASSIGNMENT OF THE ONE NODE IS ONLY POSSIBLE IF IT CAUSES
	IMPROVEMENT IN THE ROUTE'S COST. */

        coord g2[] = new coord[MAXN + 1];

        tourne t2 = new tourne();

        //tourneelem *impp;

        tourneelem xx, yy;

        tourneelem pt;

        tourneelem w;

        // int rx, x, cpt;

        int x, cpt;

        int neigh;

        double exc, coutt;

        cpt = 0;
        // rx = 1;
        neigh = MAXK;


	/* THIS PROCEDURE COPIES THE ROUTE t OF g IN t2 OF g2.
	NOTE THAT THIS PROCEDURE ONLY WORKS IF THE NUMBER OF
	ASSIGNED NODES IS EQUAL TO n.  */

        for (int i = 0; i <= MAXN; i++)
        {
            g2[i] = new coord();
            if (g[i] != null) {
                g2[i].x = g[i].x;
                g2[i].y = g[i].y;
                g2[i].ptrtourne = null;
            }
            t2.noeudinterne[i] = t.noeudinterne[i];
        }
        for (int i = 1; i <= n; i++)
        {
            if (g2[i].ptrtourne == null) g2[i].ptrtourne = new tourneelem();
        }
        for (int i = 1; i <= n; i++)
        {
            pt = g2[i].ptrtourne;
            pt.noeud = i;
            pt.prochain = g2[g[i].ptrtourne.prochain.noeud].ptrtourne;
            pt.precedent = g2[g[i].ptrtourne.precedent.noeud].ptrtourne;
        }
        t2.nbredenoeuds = t.nbredenoeuds;
        t2.ptr = g2[1].ptrtourne;

        /********************Numerote_tourne 2***********************************/
        w = t2.ptr;
        for (int i = 1; i <= MAXN; i++)
        {
            w.rang = i;
            w = w.prochain;
            if (w == t2.ptr) break;
        }
        /******************End of Numerote_tourne 2********************************/

        // exc = 0;
        // yy = t2.ptr;
        //xx = t2.ptr->prochain;
        // do {
        //     exc += d[yy->noeud][xx->noeud];
        //     yy = xx;
        //     xx = xx->prochain;
        //   } while ( yy != t2.ptr);
        exc = tour;
        x = 1;

        do
        {
            oterx(x, neigh, g, d);
            neigh++;
            ajoutx(x, neigh, g, d);
            numerote_tourne();
            neigh--;
            //        coutt = calculcoutt (d);
            //        if (exc > coutt)
            if ((deltaout + deltain) < 0)
            {
                // coutt = calculcoutt (d);
                coutt = exc + (deltaout + deltain);
                if (exc > coutt)
                {
                    exc = coutt;
                    cpt = 0;
                    for (int i = 0; i <= MAXN; i++)
                    {
                        if (g[i] != null) {
                            g2[i].x = g[i].x;
                            g2[i].y = g[i].y;
                        }
                        g2[i].ptrtourne = null;
                        t2.noeudinterne[i] = t.noeudinterne[i];
                    }
                    for (int i = 1; i <= n; i++)
                    {
                        if (g2[i].ptrtourne == null) g2[i].ptrtourne = new tourneelem();
                    }
                    for (int i = 1; i <= n; i++)
                    {
                        pt = g2[i].ptrtourne;
                        pt.noeud = i;
                        pt.prochain = g2[g[i].ptrtourne.prochain.noeud].ptrtourne;
                        pt.precedent = g2[g[i].ptrtourne.precedent.noeud].ptrtourne;
                    }
                    t2.nbredenoeuds = t.nbredenoeuds;
                    t2.ptr = g2[1].ptrtourne;
                    /********************Numerote_tourne 2***********************************/
                    w = t2.ptr;
                    for (int i = 1; i <= MAXN; i++)
                    {
                        w.rang = i;
                        w = w.prochain;
                        if (w == t2.ptr) break;
                    }
                    /******************End of Numerote_tourne 2*****
                     ***************************/
                }
            }
            x = suiv(x, g2);
            cpt++;



		/* THIS PROCEDURE COPIES THE ROUTE t2 OF g IN t OF g.
		NOTE THAT THIS PROCEDURE ONLY WORKS IF THE NUMBER OF
		ASSIGNED NODES IS EQUAL TO n.  */

            for (int i = 0; i <= MAXN; i++)
            {
                if (g[i] != null) {
                    g[i].x = g2[i].x;
                    g[i].y = g2[i].y;
                    g[i].ptrtourne = null;
                }
                t.noeudinterne[i] = t2.noeudinterne[i];
            }
            for (int i = 1; i <= n; i++)
            {
                if (g[i].ptrtourne == null) g[i].ptrtourne = new tourneelem();
            }
            for (int i = 1; i <= n; i++)
            {
                pt = g[i].ptrtourne;
                pt.noeud = i;
                pt.prochain = g[g2[i].ptrtourne.prochain.noeud].ptrtourne;
                pt.precedent = g[g2[i].ptrtourne.precedent.noeud].ptrtourne;
            }
            t.nbredenoeuds = t2.nbredenoeuds;
            t.ptr = g[1].ptrtourne;
            numerote_tourne();
        } while (cpt != n);
        //cout << exc << endl;

    }

/******************************************************************/


}
