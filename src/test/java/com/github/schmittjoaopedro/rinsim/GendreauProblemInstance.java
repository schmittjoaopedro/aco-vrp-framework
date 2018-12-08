package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.core.model.ModelBuilder;
import com.github.rinde.rinsim.core.model.pdp.DefaultPDPModel;
import com.github.rinde.rinsim.core.model.pdp.TimeWindowPolicy;
import com.github.rinde.rinsim.core.model.road.RoadModelBuilders;
import com.github.rinde.rinsim.core.model.time.TimeModel;
import com.github.rinde.rinsim.geom.Point;
import com.github.rinde.rinsim.pdptw.common.PDPRoadModel;
import com.github.rinde.rinsim.pdptw.common.StatsStopConditions;
import com.github.rinde.rinsim.pdptw.common.StatsTracker;
import com.github.rinde.rinsim.scenario.Scenario;
import com.github.rinde.rinsim.scenario.StopCondition;
import com.github.rinde.rinsim.scenario.StopConditions;
import com.github.rinde.rinsim.scenario.TimedEvent;
import com.github.rinde.rinsim.scenario.gendreau06.GendreauProblemClass;
import com.github.rinde.rinsim.util.TimeWindow;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import javax.measure.Measure;
import javax.measure.quantity.Velocity;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import java.util.List;

public class GendreauProblemInstance extends Scenario {

    public static final Point MIN = new Point(0.0D, 0.0D);
    public static final Point MAX = new Point(5.0D, 5.0D);
    public static final Measure<Double, Velocity> MAX_SPEED;
    private final ImmutableList<TimedEvent> events;
    private final ImmutableSet<ModelBuilder<?, ?>> modelBuilders;
    private final String problemInstanceId;
    private final long tickSize;
    private final boolean allowDiversion;
    private final GendreauProblemClass problemClass;

    public GendreauProblemInstance(ImmutableList<TimedEvent> events, ImmutableSet<ModelBuilder<?, ?>> modelBuilders, String problemInstanceId, long tickSize, boolean allowDiversion, GendreauProblemClass problemClass) {
        if (events == null) {
            throw new NullPointerException("Null events");
        } else {
            this.events = events;
            if (modelBuilders == null) {
                throw new NullPointerException("Null modelBuilders");
            } else {
                this.modelBuilders = modelBuilders;
                if (problemInstanceId == null) {
                    throw new NullPointerException("Null problemInstanceId");
                } else {
                    this.problemInstanceId = problemInstanceId;
                    this.tickSize = tickSize;
                    this.allowDiversion = allowDiversion;
                    if (problemClass == null) {
                        throw new NullPointerException("Null problemClass");
                    } else {
                        this.problemClass = problemClass;
                    }
                }
            }
        }
    }

    public static GendreauProblemInstance create(List<? extends TimedEvent> pEvents, long ts, GendreauProblemClass problemClass, int instanceNumber, boolean diversion, boolean realtime) {
        TimeModel.Builder timeModelBuilder = TimeModel.builder()
                .withTickLength(ts)
                .withTimeUnit(SI.MILLI(SI.SECOND));
        ImmutableSet<ModelBuilder<?, ?>> modelBuilders =
                ImmutableSet.<ModelBuilder<?, ?>>builder()
                        .add(realtime ? timeModelBuilder.withRealTime() : timeModelBuilder)
                        .add(
                                PDPRoadModel.builder(
                                        RoadModelBuilders.plane()
                                                .withMinPoint(MIN)
                                                .withMaxPoint(MAX)
                                                .withDistanceUnit(SI.KILOMETER)
                                                .withSpeedUnit(MAX_SPEED.getUnit())
                                                .withMaxSpeed(MAX_SPEED.getValue()))
                                        .withAllowVehicleDiversion(diversion))
                        .add(
                                DefaultPDPModel.builder()
                                        .withTimeWindowPolicy(TimeWindowPolicy.TimeWindowPolicies.TARDY_ALLOWED))
                        .add(
                                StatsTracker.builder())
                        .build();
        return new GendreauProblemInstance(
                ImmutableList.<TimedEvent>copyOf(pEvents),
                modelBuilders,
                Integer.toString(instanceNumber),
                ts,
                diversion,
                problemClass);
    }

    public TimeWindow getTimeWindow() {
        return TimeWindow.always();
    }

    public StopCondition getStopCondition() {
        return StopConditions.and(StatsStopConditions.vehiclesDoneAndBackAtDepot(), StatsStopConditions.timeOutEvent(), new StopCondition[0]);
    }

    public ImmutableList<TimedEvent> getEvents() {
        return this.events;
    }

    public ImmutableSet<ModelBuilder<?, ?>> getModelBuilders() {
        return this.modelBuilders;
    }

    public String getProblemInstanceId() {
        return this.problemInstanceId;
    }

    long getTickSize() {
        return this.tickSize;
    }

    boolean getAllowDiversion() {
        return this.allowDiversion;
    }

    public GendreauProblemClass getProblemClass() {
        return this.problemClass;
    }

    public String toString() {
        return "GendreauProblemInstance{events=" + this.events + ", modelBuilders=" + this.modelBuilders + ", problemInstanceId=" + this.problemInstanceId + ", tickSize=" + this.tickSize + ", allowDiversion=" + this.allowDiversion + ", problemClass=" + this.problemClass + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GendreauProblemInstance)) {
            return false;
        } else {
            GendreauProblemInstance that = (GendreauProblemInstance) o;
            return this.events.equals(that.getEvents()) && this.modelBuilders.equals(that.getModelBuilders()) && this.problemInstanceId.equals(that.getProblemInstanceId()) && this.tickSize == that.getTickSize() && this.allowDiversion == that.getAllowDiversion() && this.problemClass.equals(that.getProblemClass());
        }
    }

    public int hashCode() {
        int h = 1;
        h = h * 1000003;
        h ^= this.events.hashCode();
        h *= 1000003;
        h ^= this.modelBuilders.hashCode();
        h *= 1000003;
        h ^= this.problemInstanceId.hashCode();
        h *= 1000003;
        h = (int) ((long) h ^ this.tickSize >>> 32 ^ this.tickSize);
        h *= 1000003;
        h ^= this.allowDiversion ? 1231 : 1237;
        h *= 1000003;
        h ^= this.problemClass.hashCode();
        return h;
    }

    static {
        MAX_SPEED = Measure.valueOf(30.0D, NonSI.KILOMETERS_PER_HOUR);
    }

}
