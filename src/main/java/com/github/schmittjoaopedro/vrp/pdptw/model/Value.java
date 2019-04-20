package com.github.schmittjoaopedro.vrp.pdptw.model;

public class Value {
	
	private double[] dtValue;
	
	public Value(double distance,double distance2) {
		dtValue = new double[] {distance,distance2};
	}
	
	public double getDistance() {
		return dtValue[0];
	}
	
	public double getTime() {
		return dtValue[1];
	}
}
