package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.pdp.*;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;

import java.util.ArrayList;
import java.util.List;

public class Salesman extends Vehicle {

    private int id;

    protected SalesmanStrategy strategy;

    private List<Parcel> route;

    private double[] beginService;

    private Depot depot;

    private boolean active = false;

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

    public double[] getBeginService() {
        return beginService;
    }

    public void setBeginService(double[] beginService) {
        this.beginService = beginService;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
