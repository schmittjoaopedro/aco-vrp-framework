package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.pdp.Depot;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.rinde.rinsim.geom.Point;

import java.util.HashSet;
import java.util.Set;

public class ClosestParcelStrategy implements VehicleStrategy {

    protected Vehicle vehicle;
    protected RoadModel roadModel;
    protected PDPModel pdpModel;
    protected Parcel target;
    protected Depot depot;

    public ClosestParcelStrategy() {
    }

    @Override
    public void init(Vehicle v, RoadModel rm, PDPModel pm) {
        if (!(vehicle == null && roadModel == null && pdpModel == null)) {
            throw new RuntimeException("init can be called only once!");
        }
        vehicle = v;
        roadModel = rm;
        pdpModel = pm;

        final Set<Depot> set = rm.getObjectsOfType(Depot.class);
        if (!(set.size() == 1)) {
            throw new RuntimeException("This strategy only supports problems with one depot.");
        }
        depot = set.iterator().next();
    }

    @Override
    public void execute(TimeLapse time) {
        while (time.hasTimeLeft()) {
            final Set<Parcel> parcels = new HashSet<>(pdpModel.getParcels(PDPModel.ParcelState.AVAILABLE));
            if (!pdpModel.getContents(vehicle).isEmpty()) {
                parcels.addAll(pdpModel.getContents(vehicle));
            }
            double dist = Double.POSITIVE_INFINITY;
            Parcel closest = null;
            for (final Parcel p : parcels) {
                final Point pos = pdpModel.containerContains(vehicle, p) ? p
                        .getDeliveryLocation() : roadModel.getPosition(p);
                final double d = Point.distance(roadModel.getPosition(vehicle), pos);
                if (d < dist) {
                    dist = d;
                    closest = p;
                }
            }
            if (closest != null) {
                roadModel.moveTo(vehicle, closest, time);
                if (roadModel.equalPosition(vehicle, closest)) {
                    pdpModel.service(vehicle, closest, time);
                }
            } else {
                roadModel.moveTo(vehicle, depot, time);
                if (roadModel.equalPosition(vehicle, depot)) {
                    time.consumeAll();
                }
            }
        }
    }
}
