package com.github.schmittjoaopedro.vrp.pdptw.model;

public class VehicleProperty {
	private int TOTALVEHICLES;
	private int CAPACITY;
	private int SPEED;
	
	public VehicleProperty(int totalVehicles,int capacity) {					// for the moment, only capacity is used
		this.TOTALVEHICLES =  totalVehicles;
		this.CAPACITY = capacity;
	}

	public int getTotalVehicles() {
		return TOTALVEHICLES;
	}

	public int getCapacity() {
		return CAPACITY;
	}

	public int getSpeed() {
		return SPEED;
	}
}
