package com.github.schmittjoaopedro.vrp.pdptw.model;

import java.util.Vector;

public class Solution {
	
	private Vector<Route> routes;
	private int recorded;
	
	public Solution() {
		routes = new Vector<Route>();
		recorded = 0;
	}
	
	public void add(Route r) {
		routes.add(r);
	}
	
	public Route remove() {
		return routes.remove(routes.size()-1);
	}
	
	public void insertAt(int index,Route r) {
		routes.add(index, r);
	}
	
	public Route removeAt(int index) {
		return routes.remove(index);
	}
	
	public Route get(int index) {
		return routes.get(index);
	}
	
	public int size() {
		return routes.size();
	}
	
	@Override
	public Solution clone() {
		Solution s = new Solution();
		for (int i=0;i<this.size();i++) {
			s.add(this.get(i).clone());
		}
		return s;
	}
	
	public int getRecorded() {
		return recorded;
	}

	public void setRecorded(int recorded) {
		this.recorded = recorded;
	}

	public int saCost() {
		double[] data = cost();
		int saCost = (int)(data[1]+0.01*data[1]*data[3]);
		return saCost;
	}
	
	public Eigenvalue getEigenvalue() {
		double[] data = cost();
		return new Eigenvalue((int)data[0],(int)data[1],(int)data[2],(int)data[3]);
	}
	
	// shift
	public boolean shift(int r1Index,int r2Index,int i,int j,VehicleProperty vp) {
		
		Route sourceRoute = routes.get(r1Index);
		Route targetRoute = routes.get(r2Index);
		
		// remove pd pairs from source route
		Customer pickup = sourceRoute.removeAt(i);
		Customer delivery = sourceRoute.removeAt(j-1);
		
		// insert pd pairs to target route , insertion may fail
		
		if (insertPDPair(targetRoute,pickup,delivery,vp)) {
			// if isEmpty(route) then delete this route
			if (sourceRoute.size() == 0) {
				routes.remove(r1Index);
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	// exchange
	public boolean exchange(int r1Index, int r2Index, int i, int j, int m, int n,VehicleProperty vp) {
	
		Route targetRoute1 = routes.get(r2Index);
		Route targetRoute2 = routes.get(r1Index);
		
		// remove pd pairs from relevant routes
		Customer pickup1 = targetRoute2.removeAt(i);
		Customer delivery1 = targetRoute2.removeAt(j-1);
		Customer pickup2 = targetRoute1.removeAt(m);
		Customer delivery2 = targetRoute1.removeAt(n-1);
		
		// insert to relevant route , insertion may fail
		if (insertPDPair(targetRoute1,pickup1,delivery1,vp) && insertPDPair(targetRoute2,pickup2,delivery2,vp)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// rearrange
	public boolean rearrange(int route, int i,int j,VehicleProperty vp) {
		Route target = this.get(route);
		// remove
		Customer pickup = target.removeAt(i);
		Customer delivery = target.removeAt(j-1);
		
		// insert
		return insertPDPair(target,pickup,delivery,vp);
	}
	
	// find a feasible place to insert pickup-delivery pair
	private boolean insertPDPair(Route targetRoute, Customer pickup,Customer delivery,VehicleProperty vp) {
		
		for (int i=0;i<targetRoute.size()+1;i++) {
			for (int j=i;j<targetRoute.size()+1;j++) {
				
				if (targetRoute.isInsertionFeasible(pickup,delivery,i,j,vp)) {
					targetRoute.insertAt(j, delivery);
					targetRoute.insertAt(i, pickup);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public double[] cost() {
		int NV = this.size();
		double TC =0;
		double SD =0;
		double WT =0;
		
		double[] routeCost;
		for (int i=0;i<this.size();i++) {
			routeCost = this.get(i).cost();
			TC += routeCost[0];
			SD += routeCost[1];
			WT += routeCost[2];
		}
		
		return new double[] {NV,TC,SD,WT};
	}
	
	// print solution
	public void printSolution() {
		System.out.println();
		System.out.println("Consequence: ");
		for (int i=0;i<this.size();i++) {
			System.out.print(this.get(i).size()+" ");
			System.out.print("Route "+(i+1)+": ");
			for (int j=0;j<this.get(i).size();j++) {
				System.out.print(this.get(i).get(j).getID()+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}