package com.github.schmittjoaopedro.vrp.dvrptwacs;

/**
 * ACO algorithms for the TSP
 * <p>
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 * <p>
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 */
public class Timer {
    /*
     * ################################################
     * ########## ACO algorithms for the TSP ##########
     * ################################################
     *
     * Version: 1.0
     * File: dos_timer.c
     * Author: Thomas Stuetzle
     * Purpose: routines for measuring elapsed time (CPU or real)
     * Check: README.txt and legal.txt
     * Copyright (C) 2004 Thomas Stuetzle
     */

    /***************************************************************************
     * Program's name: acotsp
     *
     * Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
     * symmetric TSP
     *
     * Copyright (C) 2004 Thomas Stuetzle
     *
     * This program is free software; you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation; either version 2 of the License, or
     * (at your option) any later version.
     *
     * This program is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with this program; if not, write to the Free Software
     * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
     *
     * email: stuetzle no@spam informatik.tu-darmstadt.de
     * mail address: Universitaet Darmstadt
     * Fachbereich Informatik
     * Hochschulstr. 10
     * D-64283 Darmstadt
     * Germany
     ***************************************************************************/

    private long startTime;

    private InOut inOut;

    public Timer(InOut inOut) {
        this.inOut = inOut;
    }

    //virtual and real time of day are computed and stored to allow at later time the computation of the elapsed time
    public void start_timers() {
        startTime = getCurrentTime();
    }

    //return the time used in seconds (virtual or real, depending on type)
    public double elapsed_time() {
        return (getCurrentTime() - startTime) / 1000.0;
    }

    public long getCurrentTime() {
        if (inOut.isDiscreteTime) {
            double x = inOut.currentTimeSlice;
            double noSolCur = inOut.noSolutions;
            double noSolEst = getNoSolEstimated(x);
            long time = (long) ((((x - 1) + (noSolCur / noSolEst)) / 50.0) * 100.0 * 1000.0);
            return Math.max(time, 0);
        } else {
            return System.currentTimeMillis();
        }
    }

    public double getNoSolEstimated(double x) {
        // Calculated with Excel
        return 0.0195 * Math.pow(x, 4.0) - 0.6707 * Math.pow(x, 3.0) + 18.528 * Math.pow(x, 2) + 20.967 * x + 12.558;
    }
}
