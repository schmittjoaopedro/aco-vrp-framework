package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.geom.GeomHeuristic;
import com.github.rinde.rinsim.geom.Graph;
import com.github.rinde.rinsim.geom.Point;

import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Length;
import javax.measure.quantity.Velocity;
import javax.measure.unit.Unit;

public class SimulatedHeuristic implements GeomHeuristic {

    @Override
    public double estimateCost(Graph<?> graph, Point point, Point point1) {
        return 0;
    }

    @Override
    public double calculateCost(Graph<?> graph, Point point, Point point1) {
        return 0;
    }

    @Override
    public double calculateTravelTime(Graph<?> graph, Point point, Point point1, Unit<Length> unit, Measure<Double, Velocity> measure, Unit<Duration> unit1) {
        return 0;
    }
}
