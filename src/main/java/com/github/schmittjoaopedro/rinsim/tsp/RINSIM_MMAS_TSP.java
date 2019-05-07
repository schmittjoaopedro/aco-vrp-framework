package com.github.schmittjoaopedro.rinsim.tsp;

import com.github.rinde.rinsim.core.Simulator;
import com.github.rinde.rinsim.core.model.pdp.*;
import com.github.rinde.rinsim.core.model.road.RoadModelBuilders;
import com.github.rinde.rinsim.core.model.time.TimeModel;
import com.github.rinde.rinsim.geom.Point;
import com.github.rinde.rinsim.pdptw.common.*;
import com.github.rinde.rinsim.scenario.*;
import com.github.rinde.rinsim.ui.View;
import com.github.rinde.rinsim.ui.renderers.PDPModelRenderer;
import com.github.rinde.rinsim.ui.renderers.PlaneRoadModelRenderer;
import com.github.rinde.rinsim.ui.renderers.RoadUserRenderer;
import com.github.rinde.rinsim.util.TimeWindow;
import com.github.schmittjoaopedro.rinsim.PathRenderer;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.rinsim.VertexPoint;
import com.github.schmittjoaopedro.tsp.algorithms.MMAS_TSP;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.eclipse.swt.graphics.RGB;

import javax.measure.Measure;
import javax.measure.quantity.Velocity;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import java.util.ArrayList;
import java.util.List;

public class RINSIM_MMAS_TSP implements Runnable {

    public static final Measure<Double, Velocity> MAX_SPEED = Measure.valueOf(30.0D, NonSI.KILOMETERS_PER_HOUR);

    private Simulator simulator;

    private StatisticsDTO statistics;

    private static final RGB GRAY = new RGB(200, 200, 200);

    private static final RGB BLUE = new RGB(0, 0, 255);

    private static final RGB PURPLE = new RGB(255, 0, 255);

    public RINSIM_MMAS_TSP(Graph graph, MMAS_TSP mmasTsp, boolean showGui) {
        long endTime = Maths.minutes(15);
        double truckSpeed = 30;
        int tickSize = 1;
        Vertex depotVertex;
        Point depotPoint, tempPoint;
        VehicleDTO vehicle;
        Vertex requestVertex;
        ParcelDTO parcelDTO;
        double maxX, maxY, minX, minY;
        double margin = 100;
        Scenario scenario;
        List<TimedEvent> events = new ArrayList<>();

        // Depot
        depotVertex = graph.getVertex(0);
        depotPoint = new VertexPoint(depotVertex);
        maxX = minX = depotPoint.x;
        maxY = minY = depotPoint.y;
        events.add(AddDepotEvent.create(-1, depotPoint));

        // Create vehicles
        vehicle = VehicleDTO.builder()
                .startPosition(depotPoint)
                .speed(truckSpeed)
                .capacity(0)
                .availabilityTimeWindow(TimeWindow.always())
                .build();
        events.add(AddVehicleEvent.create(-1, vehicle));

        // Create timeout event
        events.add(TimeOutEvent.create(endTime));

        // Create requests
        for (int i = 1; i < graph.getVertexCount(); i++) {
            requestVertex = graph.getVertex(i);
            tempPoint = new VertexPoint(requestVertex);
            parcelDTO = Parcel.builder(depotPoint, tempPoint)
                    .pickupTimeWindow(TimeWindow.always())
                    .deliveryTimeWindow(TimeWindow.always())
                    .neededCapacity(0)
                    .orderAnnounceTime(0L)
                    .pickupDuration(0L)
                    .deliveryDuration(0L)
                    .buildDTO();
            events.add(AddParcelEvent.create(parcelDTO));
            maxX = Math.max(maxX, tempPoint.x);
            maxY = Math.max(maxY, tempPoint.y);
            minX = Math.min(minX, tempPoint.x);
            minY = Math.min(minY, tempPoint.y);
        }
        maxX += margin;
        maxY += margin;
        minX -= margin;
        minY -= margin;

        // Create scenario
        scenario = Scenario.builder()
                // Configure when the simulator will stop to run
                .setStopCondition(
                        StopConditions.and(
                                StatsStopConditions.vehiclesDoneAndBackAtDepot(), StatsStopConditions.timeOutEvent(), new StopCondition[0]))
                // Add events that will happen during the simulation
                .addEvents(events)
                .instanceId("TSP(" + graph.getVertexCount() + ")")
                // Configure time model
                .addModel(TimeModel.builder()
                        .withTimeUnit(SI.MILLI(SI.SECOND))
                        .withTickLength(tickSize))
                // Create road model
                .addModel(PDPRoadModel.builder(
                        RoadModelBuilders.plane()
                                .withMinPoint(new Point(minX, minY))
                                .withMaxPoint(new Point(maxX, maxY))
                                .withDistanceUnit(SI.METER)
                                .withSpeedUnit(MAX_SPEED.getUnit())
                                .withMaxSpeed(MAX_SPEED.getValue())))
                // Allow pickup products after time windows ends
                .addModel(DefaultPDPModel.builder()
                        .withTimeWindowPolicy(TimeWindowPolicy.TimeWindowPolicies.TARDY_ALLOWED))
                // Collect statistics
                .addModel(StatsTracker.builder())
                .build();

        // Create simulator
        Simulator.Builder simulatorBuilder = Simulator.builder();
        simulatorBuilder.setRandomSeed(123L);
        // Add events handler
        simulatorBuilder.addModel(
                ScenarioController
                        .builder(scenario)
                        .withEventHandler(AddDepotEvent.class, AddDepotEvent.defaultHandler())
                        .withEventHandler(AddParcelEvent.class, AddParcelEvent.defaultHandler())
                        .withEventHandler(TimeOutEvent.class, TimeOutEvent.ignoreHandler())
                        .withEventHandler(AddVehicleEvent.class,
                                (evt, sim) -> sim.register(new Salesman(evt.getVehicleDTO(), new SalesmanMmasTspStrategy(mmasTsp)))
                        ));
        if (showGui) {
            // Add GUI visualizer
            simulatorBuilder.addModel(
                    View.builder()
                            .with(PlaneRoadModelRenderer.builder())
                            .with(RoadUserRenderer.builder()
                                    .withColorAssociation(Depot.class, GRAY)
                                    .withColorAssociation(Parcel.class, BLUE)
                                    .withColorAssociation(Vehicle.class, PURPLE))
                            .with(PDPModelRenderer.builder())
                            .with(PathRenderer.builder())
                            .with(StatsPanel.builder())
                            .withResolution(1600, 768)
                            .withAutoPlay()
                            .withAutoClose()
                            // For testing we allow to change the speed up via the args.
                            .withSpeedUp(150)
                            .withTitleAppendix("Experiment example"));
        }
        simulator = simulatorBuilder.build();
    }

    @Override
    public void run() {
        // Execute simulator
        simulator.start();
        statistics = simulator.getModelProvider().getModel(StatsTracker.class).getStatistics();
    }

    public StatisticsDTO getStatistics() {
        return statistics;
    }
}
