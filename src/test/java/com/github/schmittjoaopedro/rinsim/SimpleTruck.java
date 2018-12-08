package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.pdp.VehicleDTO;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;

public class SimpleTruck extends Vehicle {

    protected VehicleStrategy strategy;

    public SimpleTruck(VehicleDTO dto, VehicleStrategy vs) {
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
