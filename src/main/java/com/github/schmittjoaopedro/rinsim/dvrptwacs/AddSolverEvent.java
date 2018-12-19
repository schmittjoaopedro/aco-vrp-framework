package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.scenario.TimedEvent;

public class AddSolverEvent implements TimedEvent {

    private long time;

    public AddSolverEvent(long time) {
        this.time = time;
    }

    public static AddSolverEvent create(long time) {
        return new AddSolverEvent(time);
    }

    @Override
    public long getTime() {
        return time;
    }
}
