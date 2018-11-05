package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;
import java.util.List;

public class LoggerOutput {

    private boolean print;

    private List<String> logOutput = new ArrayList<>();

    public void reset() {
        logOutput.clear();
    }

    public void log(String message) {
        if (print) {
            //System.out.println(message);
            System.out.println("assertThat(LoggerOutput.get(lineCount++)).isEqualTo(\"" + message + "\");");
        }
        logOutput.add(message);
    }

    public String get(int i) {
        return logOutput.get(i);
    }

    public List<String> getLog() {
        return logOutput;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}
