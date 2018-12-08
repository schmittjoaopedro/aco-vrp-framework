package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.Simulator;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.pdp.ParcelDTO;
import com.github.rinde.rinsim.core.model.pdp.VehicleDTO;
import com.github.rinde.rinsim.geom.Point;
import com.github.rinde.rinsim.pdptw.common.*;
import com.github.rinde.rinsim.scenario.ScenarioController;
import com.github.rinde.rinsim.scenario.TimeOutEvent;
import com.github.rinde.rinsim.scenario.TimedEvent;
import com.github.rinde.rinsim.scenario.gendreau06.GendreauProblemClass;
import com.github.rinde.rinsim.ui.View;
import com.github.rinde.rinsim.ui.renderers.PDPModelRenderer;
import com.github.rinde.rinsim.ui.renderers.PlaneRoadModelRenderer;
import com.github.rinde.rinsim.ui.renderers.RoadUserRenderer;
import com.github.rinde.rinsim.util.TimeWindow;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Gendreau06Test {

    @Test
    public void simpleTest() {
        int numVehicles = 2;
        long endTime = minutes(15);
        double truckSpeed = 5;
        List<TimedEvent> events = new ArrayList<>();

        // Create depot position
        Point depotPosition = new Point(2.0, 2.5);
        events.add(AddDepotEvent.create(-1, depotPosition));

        // Create vehicles
        for (int i = 0; i < numVehicles; i++) {
            VehicleDTO vehicle = VehicleDTO.builder()
                    .startPosition(depotPosition)
                    .speed(truckSpeed)
                    .capacity(0)
                    .availabilityTimeWindow(TimeWindow.always())
                    .build();
            events.add(AddVehicleEvent.create(-1, vehicle));
        }

        // Create timeout event
        events.add(TimeOutEvent.create(endTime));

        // Create requests
        ParcelDTO parcel = Parcel.builder(new Point(2, 1), new Point(4, 1))
                .pickupTimeWindow(TimeWindow.create(0, 720000))
                .deliveryTimeWindow(TimeWindow.create(5, 720000))
                .neededCapacity(0)
                .orderAnnounceTime(0L)
                .pickupDuration(0L)
                .deliveryDuration(0L)
                .buildDTO();
        events.add(AddParcelEvent.create(parcel));

        ParcelDTO parcel2 = Parcel.builder(new Point(3, 1), new Point(1, 3))
                .pickupTimeWindow(TimeWindow.create(0, 720000))
                .deliveryTimeWindow(TimeWindow.create(5, 720000))
                .neededCapacity(0)
                .orderAnnounceTime(0L)
                .pickupDuration(0L)
                .deliveryDuration(0L)
                .buildDTO();
        events.add(AddParcelEvent.create(parcel2));

        // Create Gendreau scenario
        GendreauProblemInstance problemInstance = GendreauProblemInstance.create(events, 1000L,
                GendreauProblemClass.LONG_LOW_FREQ, 1, false, false);

        // Create simulator
        Simulator.Builder simBuilder = Simulator
                .builder()
                .setRandomSeed(123L)
                .addModel(
                        ScenarioController
                                .builder(problemInstance)
                                .withEventHandler(AddDepotEvent.class, AddDepotEvent.defaultHandler())
                                .withEventHandler(AddParcelEvent.class, AddParcelEvent.defaultHandler())
                                .withEventHandler(TimeOutEvent.class, TimeOutEvent.ignoreHandler())
                                .withEventHandler(AddVehicleEvent.class,
                                        (event, simulator) -> {
                                            simulator.register(new SimpleTruck(
                                                    event.getVehicleDTO(), new ClosestParcelStrategy()));
                                        }))
                .addModel(
                        View.builder()
                                .with(PlaneRoadModelRenderer.builder())
                                .with(RoadUserRenderer.builder())
                                .with(PDPModelRenderer.builder())
                                .withSpeedUp(50)
                                .withAutoClose()
                                .withAutoPlay());

        // Execute simulator
        final Simulator sim = simBuilder.build();
        sim.start();

        final StatisticsDTO dto = sim.getModelProvider().getModel(StatsTracker.class).getStatistics();

        final double distInOneTick = 30.0 / 3600.0;
        System.out.println(dto.simFinish);
        System.out.println(9 - 2.0 * distInOneTick + " = " + dto.totalDistance);
        System.out.println(dto.totalParcels);
        System.out.println(dto.overTime);
        System.out.println(dto.pickupTardiness);
        System.out.println(dto.deliveryTardiness);
        System.out.println(dto.totalVehicles);
        System.out.println(dto.movedVehicles);
    }

    static long minutes(long n) {
        return n * seconds(60);
    }

    static long seconds(long n) {
        return n * 1000;
    }
}
