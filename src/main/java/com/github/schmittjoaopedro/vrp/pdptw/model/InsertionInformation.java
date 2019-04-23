package com.github.schmittjoaopedro.vrp.pdptw.model;

public class InsertionInformation {
	private double value;
	private int pickupIndex;
	private int pickupInsertionIndex;
	private int deliveryInsertionIndex;
	
	public InsertionInformation(double value2,int pI,int pII,int dII) {
		this.value = value2;
		this.pickupIndex = pI;
		this.pickupInsertionIndex = pII;
		this.deliveryInsertionIndex = dII;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double d) {
		this.value = d;
	}

	public int getPickupIndex() {
		return pickupIndex;
	}

	public int getPickupInsertionIndex() {
		return pickupInsertionIndex;
	}

	public int getDeliveryInsertionIndex() {
		return deliveryInsertionIndex;
	}
}
