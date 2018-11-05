package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * ACO algorithms for the TSP
 *
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 *
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 */
public class Parse {
    /***************************************************************************
     * Program's name: ACOTSPJava
     *
     * Command line parser for 'ACO algorithms for the TSP'
     *
     * Copyright (C) 2014 Adrian Wilke
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
     * You should have received a copy of the GNU General Public License along
     * with this program; if not, write to the Free Software Foundation, Inc.,
     * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
     ***************************************************************************/

    static void parse_commandline(String antSystem, int runNumber, Ants ants, InOut inOut) {

        // Choice of ONE algorithm
        int algorithmCount = 0;
        if (antSystem.equals("u")) {
            algorithmCount++;
        }
        if (antSystem.equals("z")) {
            algorithmCount++;
        }
        if (algorithmCount > 1) {
            System.err.println("Error: More than one ACO algorithm enabled in the command line.");
            System.exit(1);
        } else if (algorithmCount == 1) {
            ants.as_flag = false;
            ants.acs_flag = false;
        }

        if (antSystem.equals("u")) {
            ants.as_flag = true;
            inOut.set_default_as_parameters(ants);
            LoggerOutput.log("\nRun basic Ant System #" + (runNumber + 1));
        }
        if (antSystem.equals("z")) {
            ants.acs_flag = true;
            inOut.set_default_acs_parameters(ants);
            LoggerOutput.log("\nRun Ant Colony System #" + (runNumber + 1));
        }

    }
}
