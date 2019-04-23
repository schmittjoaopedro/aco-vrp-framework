package com.github.schmittjoaopedro.vrp.pdptw.model;

public class Customer {
	
	private int customerID;
	private int demand;				// positive for pickup, negative for delivery
	private int service_time;
	private int e_time;
	private int l_time;
	private int pickup;
	private int delivery;
	private int X;
	private int Y;
	
	public Customer(int id,int demand,int service_time,int e_time,int l_time,int pickup,int delivery,int X,int Y) {
		this.customerID=id;
		this.demand=demand;
		this.service_time=service_time;
		this.e_time=e_time;
		this.l_time=l_time;
		this.pickup=pickup;
		this.delivery=delivery;
		this.X=X;
		this.Y=Y;
	}
	
	public int getID() {
		return customerID;
	}

	public int getDemand() {
		return demand;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public int getService_time() {
		return service_time;
	}

	public void setService_time(int service_time) {
		this.service_time = service_time;
	}

	public int getE_time() {
		return e_time;
	}

	public void setE_time(int e_time) {
		this.e_time = e_time;
	}

	public int getL_time() {
		return l_time;
	}

	public void setL_time(int l_time) {
		this.l_time = l_time;
	}

	public int getPickup() {
		return pickup;
	}

	public void setPickup(int pickup) {
		this.pickup = pickup;
	}

	public int getDelivery() {
		return delivery;
	}

	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	@Override
	public Customer clone() {
		return new Customer(this.getID(),this.getDemand(),this.getService_time(),this.getE_time(),this.getL_time(),this.getPickup(),this.getDelivery(),this.getX(),this.getY());
	}
}