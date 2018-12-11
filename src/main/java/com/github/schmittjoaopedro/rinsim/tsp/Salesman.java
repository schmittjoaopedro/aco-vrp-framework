package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.pdp.VehicleDTO;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;

public class Salesman extends Vehicle {

    protected SalesmanStrategy strategy;

    public Salesman(VehicleDTO dto, SalesmanStrategy vs) {
        super(dto);
        strategy = vs;
    }

    @Override
    protected void tickImpl(TimeLapse time) {
        strategy.execute(time);
    }

    @Override
    public void initRoadPDP(RoadModel pRoadModel, PDPModel pPdpModel) {
        super.initRoadPDP(pRoadModel, pPdpModel);
        strategy.init(this, pRoadModel, pPdpModel);
    }

}
