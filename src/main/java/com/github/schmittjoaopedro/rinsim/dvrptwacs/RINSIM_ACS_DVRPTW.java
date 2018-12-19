package com.github.schmittjoaopedro.rinsim.dvrptwacs;

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
import com.github.schmittjoaopedro.rinsim.IdPoint;
import com.github.schmittjoaopedro.rinsim.PathRenderer;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.graphics.RGB;

import javax.measure.Measure;
import javax.measure.quantity.Velocity;
import javax.measure.unit.SI;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Things to do:
 * -> Read points from DVRPTW file format
 * -> Read parcels from DVRPTW file format
 * -> How to create vehicles dynamically?
 * -> Create route solutions using NN strategy
 */
public class RINSIM_ACS_DVRPTW implements Runnable {

    private static final double truckSpeed = Double.MAX_VALUE;

    public static final Measure<Double, Velocity> MAX_SPEED = Measure.valueOf(truckSpeed, SI.METERS_PER_SECOND);

    private static final RGB GRAY = new RGB(200, 200, 200);

    private static final RGB BLUE = new RGB(0, 0, 255);

    private static final RGB PURPLE = new RGB(255, 0, 255);

    private String problemName;

    private int numVehicles = 0;

    private int vehicleCapacity = 0;

    private List<TimedEvent> events = new ArrayList<>();

    private IdPoint depotPoint;

    private double maxX;

    private double maxY;

    private double minX;

    private double minY;

    private long depotEndTime;

    private int tickSize = 1;

    private int timeSlices = 50;

    private Scenario scenario;

    private Simulator simulator;

    private int workingDay = 100; // Simulate in seconds a working day

    private double scaleValue;

    public RINSIM_ACS_DVRPTW(File problemFile, boolean useGui) throws Exception {
        Iterator<String> problemDataLines = Arrays.asList(FileUtils.readFileToString(problemFile, "UTF-8").split("\n")).iterator();
        setProblemName(problemFile);
        setVehicleInformation(problemDataLines);
        setRequestInformation(problemDataLines);
        setVehicleEvents();
        events.add(AddSolverEvent.create(0));
        setTimeOutEvent();
        setScenario(problemFile);
        setSimulator(problemFile, useGui);
    }

    @Override
    public void run() {
        simulator.start();
    }

    private void setSimulator(File problemFile, boolean useGui) {
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
                        .withEventHandler(AddSolverEvent.class, (evt, sim) -> sim.register(new Solver(problemFile, 1, timeSlices, workingDay)))
                        .withEventHandler(AddVehicleEvent.class,
                                (evt, sim) -> sim.register(new Salesman(evt.getVehicleDTO(), new SalesmanAcsDvrptwStrategy()))
                        ));
        if (useGui) {
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
                            .withResolution(1024, 768)
                            .withAutoPlay()
                            .withAutoClose()
                            // For testing we allow to change the speed up via the args.
                            //.withSpeedUp(150)
                            .withTitleAppendix("Experiment example"));
        }
        simulator = simulatorBuilder.build();
    }

    private void setScenario(File problemFile) {
        // Create scenario
        scenario = Scenario.builder()
                // Configure when the simulator will stop to execute
                .setStopCondition(
                        StopConditions.and(
                                StatsStopConditions.vehiclesDoneAndBackAtDepot(), StatsStopConditions.timeOutEvent(), new StopCondition[0]))
                // Add events that will happen during the simulation
                .addEvents(events)
                .instanceId("DVRPTW(" + problemFile + ")")
                // Configure time model
                /*.addModel(TimeModel.builder()
                        .withTimeUnit(SI.SECOND)
                        .withTickLength(tickSize))*/
                .addModel(TimeModel.builder()
                        .withTimeUnit(SI.SECOND)
                        .withTickLength(tickSize)
                        .withRealTime())
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
    }

    private void setTimeOutEvent() {
        // Create timeout event
        events.add(TimeOutEvent.create(Maths.minutes(15)));
    }

    private void setVehicleEvents() {
        VehicleDTO vehicle;
        // Create vehicles RinSim events
        for (int k = 0; k < numVehicles; k++) {
            vehicle = VehicleDTO.builder()
                    .startPosition(depotPoint)
                    .speed(truckSpeed)
                    .capacity(vehicleCapacity)
                    .availabilityTimeWindow(TimeWindow.create(0, depotEndTime))
                    .build();
            events.add(AddVehicleEvent.create(-1, vehicle));
        }
    }

    private void setRequestInformation(Iterator<String> problemDataLines) {
        String nextLine, requestData[];
        ParcelDTO requestParcel;
        IdPoint deliveryPoint;
        double px, py;
        int id;
        long startDeliver, endDeliver, demand, serviceTime, announceTime;
        // Skip header information
        while (!problemDataLines.next().contains("CUSTOMER")) ;
        problemDataLines.next(); // Skip header information
        problemDataLines.next(); // Skip blank line
        // Read depot information and create RinSim event
        nextLine = problemDataLines.next();
        requestData = getTabulatedData(nextLine);
        id = Integer.parseInt(requestData[0]);
        px = Double.parseDouble(requestData[1]);
        py = Double.parseDouble(requestData[2]);
        depotEndTime = Long.parseLong(requestData[5]);
        calculateScaleValue();
        depotEndTime = getScaleValue(depotEndTime);
        minX = maxX = px;
        minY = maxY = py;
        depotPoint = new IdPoint(id, px, py);
        events.add(AddDepotEvent.create(-1, depotPoint));
        // Read request information and create RinSim event
        nextLine = problemDataLines.next();
        while (!nextLine.trim().isEmpty()) {
            requestData = getTabulatedData(nextLine);
            id = Integer.parseInt(requestData[0]);
            px = Long.valueOf(requestData[1]);
            py = Long.valueOf(requestData[2]);
            demand = Long.valueOf(requestData[3]);
            startDeliver = getScaleValue(Long.valueOf(requestData[4]));
            endDeliver = getScaleValue(Long.valueOf(requestData[5]));
            serviceTime = getScaleValue(Long.valueOf(requestData[6]));
            announceTime = getScaleValue(Long.valueOf(requestData[7]));
            deliveryPoint = new IdPoint(id, px, py);
            requestParcel = Parcel.builder(depotPoint, deliveryPoint)
                    .pickupTimeWindow(TimeWindow.create(announceTime, depotEndTime))
                    .deliveryTimeWindow(TimeWindow.create(startDeliver, endDeliver))
                    .neededCapacity(demand)
                    .orderAnnounceTime(announceTime)
                    .pickupDuration(0L)
                    .deliveryDuration(serviceTime)
                    .buildDTO();
            events.add(AddParcelEvent.create(requestParcel));
            maxX = Math.max(maxX, px);
            maxY = Math.max(maxY, py);
            minX = Math.min(minX, px);
            minY = Math.min(minY, py);
            if (!problemDataLines.hasNext()) {
                break;
            }
            nextLine = problemDataLines.next();
        }
    }

    private void setVehicleInformation(Iterator<String> problemDataLines) {
        String[] temp;
        VehicleDTO vehicle;
        while (!problemDataLines.next().contains("VEHICLE")) ;
        problemDataLines.next(); // Skip line with "NUMBER		 CAPACITY";
        temp = getTabulatedData(problemDataLines.next());
        numVehicles = Integer.valueOf(temp[0]);
        vehicleCapacity = Integer.valueOf(temp[1]);
        if (numVehicles == 0 || vehicleCapacity == 0) {
            throw new RuntimeException("No vehicle information");
        }
    }

    private void setProblemName(File problemFile) {
        problemName = problemFile.getName();
        if (problemName.contains(".txt")) {
            problemName = problemName.replace(".txt", StringUtils.EMPTY);
        }
    }

    private String[] getTabulatedData(String line) {
        String temp[] = line.split("\t");
        List<String> normalizedData = new ArrayList<>();
        for (String inf : temp) {
            inf = inf.replace("\n", StringUtils.EMPTY);
            inf = inf.replace("\r", StringUtils.EMPTY);
            inf = inf.trim();
            if (!inf.isEmpty()) {
                normalizedData.add(inf);
            }
        }
        return normalizedData.toArray(new String[]{});
    }

    private void calculateScaleValue() {
        scaleValue = (double) workingDay / depotEndTime;
    }

    private long getScaleValue(long time) {
        return Math.round((double) time * scaleValue);
    }
}
