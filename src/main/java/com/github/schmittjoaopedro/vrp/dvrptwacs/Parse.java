package com.github.schmittjoaopedro.vrp.dvrptwacs;

public class Parse {

    public void parse_commandline(String antSystem, int runNumber, Ants ants, InOut inOut, LoggerOutput loggerOutput) {
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
            loggerOutput.log("\nRun basic Ant System #" + (runNumber + 1));
        }
        if (antSystem.equals("z")) {
            ants.acs_flag = true;
            inOut.set_default_acs_parameters(ants);
            loggerOutput.log("\nRun Ant Colony System #" + (runNumber + 1));
        }
    }

}
