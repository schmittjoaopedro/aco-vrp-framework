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

import java.util.List;
import java.util.concurrent.Semaphore;

public class SalesmanAcsDvrptwStrategy implements SalesmanStrategy {

    protected LightSpeedHeuristic SIMULATED_HEURISTIC = new LightSpeedHeuristic();

    protected Salesman salesman;

    protected RoadModel roadModel;

    protected PDPModel pdpModel;

    protected Depot depot;

    protected Semaphore semaphore;

    protected DeliveryListener deliveryListener;

    protected List<String> traceRoute;

    public SalesmanAcsDvrptwStrategy(Semaphore semaphore, DeliveryListener deliveryListener) {
        this.semaphore = semaphore;
        this.deliveryListener = deliveryListener;
    }

    @Override
    public void init(Vehicle vehicle, RoadModel rm, PDPModel pm) {
        roadModel = rm;
        pdpModel = pm;
        depot = rm.getObjectsOfType(Depot.class).iterator().next();
        salesman = (Salesman) vehicle;
        salesman.setDepot(depot);
    }

    @Override
    public void execute(TimeLapse time) {
        Parcel deliverableParcel;
        int parcelId;
        boolean isBeganServiceTime;
        if (salesman.isActive() && time.hasTimeLeft()) {
            try {
                acquireLock();
                deliverableParcel = getParcelToDeliver();
                if (deliverableParcel != null) {
                    // The delivery location ID is related to the same of parcel id (request).
                    parcelId = ((IdPoint) deliverableParcel.getDeliveryLocation()).id;
                    // When the salesman achieves the destination at right time, the service starts
                    isBeganServiceTime = Math.round(salesman.getBeginService()[parcelId]) <= time.getTime();
                    if (isBeganServiceTime) {
                        // Notify the a delivery is being executed
                        deliveryListener.onParcelDelivered(deliverableParcel);
                        // First make the pickup (in depot) to after start do to the delivery
                        pickupParcel(time, deliverableParcel);
                        deliveryParcel(time, deliverableParcel);
                        logRoute(time, deliverableParcel, parcelId);
                    }
                } else {
                    // If all parcels were delivered go to depot
                    backToDepot(time);
                }
            } finally {
                releaseLock();
            }
        }
    }

    private void backToDepot(TimeLapse time) {
        roadModel.moveTo(salesman, depot, time);
        if (roadModel.equalPosition(salesman, depot)) {
            time.consumeAll();
        }
    }

    private void logRoute(TimeLapse time, Parcel deliverableParcel, int parcelId) {
        if (getTraceRoute() != null) {
            getTraceRoute().add(DebuggerUtils.getSalesmanCurrentRoute(pdpModel, salesman, time, deliverableParcel, parcelId));
        }
    }

    private void deliveryParcel(TimeLapse time, Parcel deliverableParcel) {
        if (PDPModel.ParcelState.IN_CARGO.equals(pdpModel.getParcelState(deliverableParcel))) {
            roadModel.moveTo(salesman, deliverableParcel, time, SIMULATED_HEURISTIC);
            if (roadModel.equalPosition(salesman, deliverableParcel)) { // If reached the position make the delivery
                pdpModel.service(salesman, deliverableParcel, time);
            }
        }
    }

    private void pickupParcel(TimeLapse time, Parcel deliverableParcel) {
        if (!pdpModel.getParcelState(deliverableParcel).isPickedUp()) {
            roadModel.moveTo(salesman, deliverableParcel, time, SIMULATED_HEURISTIC);
            if (roadModel.equalPosition(salesman, deliverableParcel)) { // If reached the position make the pickup
                pdpModel.service(salesman, deliverableParcel, time);
            }
        }
    }

    private Parcel getParcelToDeliver() {
        Parcel nextParcel = null;
        int deliveredParcelIndex = getLastCommittedIndex();
        if (deliveredParcelIndex < salesman.getRoute().size()) {
            nextParcel = salesman.getRoute().get(deliveredParcelIndex);
        }
        return nextParcel;
    }

    private int getLastCommittedIndex() {
        int i = 0;
        for (Parcel parcel : salesman.getRoute()) {
            if (pdpModel.getParcelState(parcel).isDelivered()) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    private void acquireLock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void releaseLock() {
        semaphore.release();
    }

    public List<String> getTraceRoute() {
        return traceRoute;
    }

    public void setTraceRoute(List<String> traceRoute) {
        this.traceRoute = traceRoute;
    }
}