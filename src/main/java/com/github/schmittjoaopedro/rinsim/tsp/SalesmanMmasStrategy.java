package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.model.pdp.Depot;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.*;

public class SalesmanMmasStrategy implements SalesmanStrategy {

    protected MMAS_TSP mmasTspSolver;

    protected Vehicle vehicle;
    protected RoadModel roadModel;
    protected PDPModel pdpModel;
    protected Depot depot;
    protected List<Parcel> deliveryOrder;

    int pickedUpParcelIndex = 0;
    int deliveredParcelIndex = 0;

    public SalesmanMmasStrategy(MMAS_TSP mmasTspSolver) {
        this.mmasTspSolver = mmasTspSolver;
    }

    @Override
    public void init(Vehicle v, RoadModel rm, PDPModel pm) {
        final Set<Depot> set;
        if (!(vehicle == null && roadModel == null && pdpModel == null)) {
            throw new RuntimeException("init can be called only once!");
        }
        vehicle = v;
        roadModel = rm;
        pdpModel = pm;
        set = rm.getObjectsOfType(Depot.class);
        if (!(set.size() == 1)) {
            throw new RuntimeException("This strategy only supports problems with one depot.");
        }
        depot = set.iterator().next();
        mmasTspSolver.run();
    }

    @Override
    public void execute(TimeLapse time) {
        while (time.hasTimeLeft()) {
            Parcel nextParcel = null;
            buildParcels();
            if (pickedUpParcelIndex < deliveryOrder.size()) {
                nextParcel = deliveryOrder.get(pickedUpParcelIndex);
            } else if (deliveredParcelIndex < deliveryOrder.size()) {
                nextParcel = deliveryOrder.get(deliveredParcelIndex);
            }
            if (nextParcel != null && isParcelAvailable(nextParcel)) {
                roadModel.moveTo(vehicle, nextParcel, time);
                if (roadModel.equalPosition(vehicle, nextParcel)) {
                    pdpModel.service(vehicle, nextParcel, time);
                    if (pdpModel.getParcelState(nextParcel).isDelivered()) {
                        deliveredParcelIndex++;
                    } else if (pdpModel.getParcelState(nextParcel).isPickedUp()) {
                        pickedUpParcelIndex++;
                    }
                }
            } else {
                roadModel.moveTo(vehicle, depot, time);
                if (roadModel.equalPosition(vehicle, depot)) {
                    time.consumeAll();
                }
            }
        }
    }

    public boolean isParcelAvailable(Parcel parcel) {
        final Set<Parcel> parcels = new HashSet<>(pdpModel.getParcels(PDPModel.ParcelState.AVAILABLE));
        if (!pdpModel.getContents(vehicle).isEmpty()) {
            parcels.addAll(pdpModel.getContents(vehicle));
        }
        return parcels.contains(parcel);
    }

    public void buildParcels() {
        if (deliveryOrder == null) {
            Parcel nextParcel;
            deliveryOrder = new ArrayList<>();
            List<Vertex> bestRoute = mmasTspSolver.getGlobalStatistics().getBestRoute();
            List<Vertex> bestRouteWithoutDepot = new ArrayList<>();
            int depotIdx = getDepotIndex(bestRoute);
            if (depotIdx == 0 || depotIdx == bestRoute.size() - 1) {
                bestRouteWithoutDepot.addAll(bestRoute.subList(1, bestRoute.size() - 1));
            } else {
                bestRouteWithoutDepot.addAll(bestRoute.subList(depotIdx + 1, bestRoute.size() - 1));
                bestRouteWithoutDepot.addAll(bestRoute.subList(0, depotIdx));
            }
            for (Vertex vertex : bestRouteWithoutDepot) {
                nextParcel = getParcel(vertex);
                deliveryOrder.add(nextParcel);
            }
        }
    }

    private int getDepotIndex(List<Vertex> bestRoute) {
        Set<Depot> depots = roadModel.getObjectsOfType(Depot.class);
        Depot depot = depots.iterator().next();
        TspPoint depotPoint = (TspPoint) roadModel.getPosition(depot);
        return bestRoute.indexOf(depotPoint.getVertex());
    }


    public Parcel getParcel(Vertex vertex) {
        Parcel currentParcel = null;
        TspPoint tspPoint;
        Iterator<Parcel> parcels = pdpModel.getParcels(PDPModel.ParcelState.AVAILABLE).iterator();
        while (parcels.hasNext()) {
            currentParcel = parcels.next();
            tspPoint = (TspPoint) currentParcel.getDeliveryLocation();
            if (vertex.equals(tspPoint.getVertex())) {
                break;
            }
        }
        return currentParcel;
    }

}
