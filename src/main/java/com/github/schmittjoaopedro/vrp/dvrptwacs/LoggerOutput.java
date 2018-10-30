package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.List;

public class LoggerOutput {

    private static boolean print;

    private static List<String> logOutput = new ArrayList<>();

    public static void reset() {
        logOutput.clear();
    }

    public static void log(String message) {
        if (print) {
            System.out.println(message);
        }
        logOutput.add(message);
    }

    public List<String> getLog() {
        return logOutput;
    }

    public static boolean isPrint() {
        return print;
    }

    public static void setPrint(boolean print) {
        LoggerOutput.print = print;
    }
}
