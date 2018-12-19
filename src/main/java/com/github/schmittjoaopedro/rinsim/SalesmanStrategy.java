package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;

public interface SalesmanStrategy {

    void init(Vehicle vehicle, RoadModel rm, PDPModel pm);

    void execute(TimeLapse time);

}
