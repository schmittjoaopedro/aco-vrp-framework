package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.core.model.pdp.Depot;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.schmittjoaopedro.rinsim.IdPoint;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.rinsim.SalesmanStrategy;

public class SalesmanAcsDvrptwStrategy implements SalesmanStrategy {

    private static int globalVehicleIdx;


    protected static final SimulatedHeuristic SIMULATED_HEURISTIC = new SimulatedHeuristic();
    protected Salesman salesman;
    protected RoadModel roadModel;
    protected PDPModel pdpModel;
    protected Depot depot;

    private int deliveredParcelIndex = 0;

    public SalesmanAcsDvrptwStrategy() {
    }

    @Override
    public void init(Vehicle vehicle, RoadModel rm, PDPModel pm) {
        roadModel = rm;
        pdpModel = pm;
        depot = rm.getObjectsOfType(Depot.class).iterator().next();
        salesman = (Salesman) vehicle;
        salesman.setDepot(depot);
        salesman.setId(getAndIncrementVehicleIdx());
        deliveredParcelIndex = 0;
    }

    @Override
    public void execute(TimeLapse time) {
        if (time.hasTimeLeft() && !salesman.getRoute().isEmpty()) {
            Parcel nextParcel = null;

            if (deliveredParcelIndex < salesman.getRoute().size()) {
                nextParcel = salesman.getRoute().get(deliveredParcelIndex);
            }

            try {
                if (nextParcel != null) {
                    int parcelId = ((IdPoint) nextParcel.getDeliveryLocation()).id;
                    if (salesman.getId() == 7) {
                        String msg = "Salesman = " + salesman.getId();
                        msg += " Parcel = " + parcelId;
                        msg += " TimeWindow = " + nextParcel.getDeliveryTimeWindow().begin() + "," + nextParcel.getDeliveryTimeWindow().end();
                        msg += " BeginService = " + salesman.getBeginService()[parcelId];
                        msg += " CurrentTime = " + time.getTime();
                        System.out.println(msg);
                    }
                    if (Math.round(salesman.getBeginService()[parcelId]) <= time.getTime()) {
                        // First make the pickup (in depot) to after start do to the delivery
                        if (!pdpModel.getParcelState(nextParcel).isPickedUp()) {
                            roadModel.moveTo(salesman, nextParcel, time, SIMULATED_HEURISTIC);
                            if (roadModel.equalPosition(salesman, nextParcel)) { // If reached the position make the pickup
                                pdpModel.service(salesman, nextParcel, time);
                            }
                        }
                        if (PDPModel.ParcelState.IN_CARGO.equals(pdpModel.getParcelState(nextParcel))) {
                            roadModel.moveTo(salesman, nextParcel, time, SIMULATED_HEURISTIC);
                            if (roadModel.equalPosition(salesman, nextParcel)) { // If reached the position make the pickup/delivery
                                pdpModel.service(salesman, nextParcel, time);
                            }
                        }
                        if (pdpModel.getParcelState(nextParcel).isDelivered()) {
                            deliveredParcelIndex++;
                        }
                    }
                } else {
                    // If all parcels were delivered go to depot
                    roadModel.moveTo(salesman, depot, time);
                    if (roadModel.equalPosition(salesman, depot)) {
                        time.consumeAll();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static synchronized int getAndIncrementVehicleIdx() {
        int vehicleIdx = SalesmanAcsDvrptwStrategy.globalVehicleIdx;
        SalesmanAcsDvrptwStrategy.globalVehicleIdx++;
        return vehicleIdx;
    }

}