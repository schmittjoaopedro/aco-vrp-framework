package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.List;

public class LoggerOutput {

    private boolean print;

    private List<String> logOutput = new ArrayList<>();

    public void log(String message) {
        if (print) {
            System.out.println("assertThat(LoggerOutput.get(lineCount++)).isEqualTo(\"" + message + "\");");
        }
        logOutput.add(message);
    }

    public String get(int i) {
        return logOutput.get(i);
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}
