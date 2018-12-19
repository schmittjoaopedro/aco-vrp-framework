package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.model.pdp.Depot;
import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.Vehicle;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.rinsim.SalesmanStrategy;
import com.github.schmittjoaopedro.rinsim.VertexPoint;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.*;

public class SalesmanMmasTspStrategy implements SalesmanStrategy {

    protected MMAS_TSP mmasTspSolver;

    protected Salesman salesman;
    protected RoadModel roadModel;
    protected PDPModel pdpModel;
    protected Depot depot;

    private int pickedUpParcelIndex = 0;
    private int deliveredParcelIndex = 0;

    public SalesmanMmasTspStrategy(MMAS_TSP mmasTspSolver) {
        this.mmasTspSolver = mmasTspSolver;
    }

    @Override
    public void init(Vehicle vehicle, RoadModel rm, PDPModel pm) {
        roadModel = rm;
        pdpModel = pm;
        depot = rm.getObjectsOfType(Depot.class).iterator().next();
        salesman = (Salesman) vehicle;
        salesman.setDepot(depot);
        mmasTspSolver.run();
    }

    @Override
    public void execute(TimeLapse time) {
        while (time.hasTimeLeft()) {
            Parcel nextParcel = null;
            buildParcels();
            // First make all pickups (in depot) to after start do to the deliveries
            if (pickedUpParcelIndex < salesman.getRoute().size()) {
                nextParcel = salesman.getRoute().get(pickedUpParcelIndex);
            } else if (deliveredParcelIndex < salesman.getRoute().size()) {
                nextParcel = salesman.getRoute().get(deliveredParcelIndex);
            }
            // If there is a parcel AVAILABLE move to that position
            if (nextParcel != null && isParcelAvailable(nextParcel)) {
                roadModel.moveTo(salesman, nextParcel, time);
                if (roadModel.equalPosition(salesman, nextParcel)) { // If reached the position make the pickup/delivery
                    pdpModel.service(salesman, nextParcel, time);
                    if (pdpModel.getParcelState(nextParcel).isDelivered()) {
                        deliveredParcelIndex++;
                    } else if (pdpModel.getParcelState(nextParcel).isPickedUp()) {
                        pickedUpParcelIndex++;
                    }
                }
            } else {
                // If all parcels were delivered go to depot
                roadModel.moveTo(salesman, depot, time);
                if (roadModel.equalPosition(salesman, depot)) {
                    time.consumeAll();
                }
            }
        }
    }

    public boolean isParcelAvailable(Parcel parcel) {
        final Set<Parcel> parcels = new HashSet<>(pdpModel.getParcels(PDPModel.ParcelState.AVAILABLE));
        if (!pdpModel.getContents(salesman).isEmpty()) {
            parcels.addAll(pdpModel.getContents(salesman));
        }
        return parcels.contains(parcel);
    }

    public void buildParcels() {
        if (salesman.getRoute().isEmpty()) {
            Parcel nextParcel;
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
                salesman.getRoute().add(nextParcel);
            }
        }
    }

    private int getDepotIndex(List<Vertex> bestRoute) {
        Set<Depot> depots = roadModel.getObjectsOfType(Depot.class);
        Depot depot = depots.iterator().next();
        VertexPoint depotPoint = (VertexPoint) roadModel.getPosition(depot);
        return bestRoute.indexOf(depotPoint.getVertex());
    }


    public Parcel getParcel(Vertex vertex) {
        Parcel currentParcel = null;
        VertexPoint vertexPoint;
        Iterator<Parcel> parcels = pdpModel.getParcels(PDPModel.ParcelState.AVAILABLE).iterator();
        while (parcels.hasNext()) {
            currentParcel = parcels.next();
            vertexPoint = (VertexPoint) currentParcel.getDeliveryLocation();
            if (vertex.equals(vertexPoint.getVertex())) {
                break;
            }
        }
        return currentParcel;
    }

}
