package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.model.pdp.*;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;

import java.util.ArrayList;
import java.util.List;

public class Salesman extends Vehicle {

    protected SalesmanStrategy strategy;

    private List<Parcel> route;

    private Depot depot;

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

    public List<Parcel> getRoute() {
        if (route == null) route = new ArrayList<>();
        return route;
    }

    public void setRoute(List<Parcel> route) {
        this.route = route;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
}
