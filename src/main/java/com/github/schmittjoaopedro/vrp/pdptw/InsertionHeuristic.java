package com.github.schmittjoaopedro.vrp.pdptw;

import java.util.Vector;

import com.github.schmittjoaopedro.vrp.pdptw.model.Customer;
import com.github.schmittjoaopedro.vrp.pdptw.model.Customers;
import com.github.schmittjoaopedro.vrp.pdptw.model.DTTable;
import com.github.schmittjoaopedro.vrp.pdptw.model.InsertionInformation;
import com.github.schmittjoaopedro.vrp.pdptw.model.Key;
import com.github.schmittjoaopedro.vrp.pdptw.model.Route;
import com.github.schmittjoaopedro.vrp.pdptw.model.VehicleProperty;

public class InsertionHeuristic {
	private Vector<Customer> unroutedCustomers;
	
	private VehicleProperty vp;
	
	public InsertionHeuristic(VehicleProperty vp) {
		this.vp = vp;
		unroutedCustomers = new Vector<Customer>(Customers.getSingleInstance().size()-1);
		
		for (int i=1;i<Customers.getSingleInstance().size();i++) {
			if (Customers.getSingleInstance().get(i).getDemand() > 0) {
				Customer pickup = Customers.getSingleInstance().get(i);
				Customer delivery = Customers.getSingleInstance().findPair(pickup);
				unroutedCustomers.add(pickup);
				unroutedCustomers.add(delivery);
			}
		}
	}
	
	public InsertionHeuristic(Route target,VehicleProperty vp) {
		this.vp = vp;
		unroutedCustomers = new Vector<Customer>(target.size());
		
		for (int i=0;i<target.size();i++) {
			if (target.get(i).getDemand() > 0) {
				Customer pickup = target.get(i);
				Customer delivery = target.get(Customers.getSingleInstance().findPair(target, i));
				unroutedCustomers.add(pickup);
				unroutedCustomers.add(delivery);
			}
		}
	}
	
	public int selectSeed() {
		int best = 0;
		double max = DTTable.getSingleInstance().getDTForDepot(unroutedCustomers.get(0).getID()).getDistance()
				+ DTTable.getSingleInstance().getDTForDepot(unroutedCustomers.get(1).getID()).getDistance();
		for (int i=1;i<unroutedCustomers.size()/2;i++) {
			double combinedDistance = DTTable.getSingleInstance().getDTForDepot(unroutedCustomers.get(2*i).getID()).getDistance()
								+ DTTable.getSingleInstance().getDTForDepot(unroutedCustomers.get(2*i+1).getID()).getDistance();
			
			if (combinedDistance > max) {
				max = combinedDistance;
				best = 2*i;
			}
		}
		
		return best;
	}
	
	/*
	 *	select best feasible insertion 
	 */
	public Vector<InsertionInformation> computeMinimalC1(Route partial) {
		Vector<InsertionInformation> temp = new Vector<InsertionInformation>();
		for (int i=0;i<unroutedCustomers.size()/2;i++) {
			InsertionInformation ii = computeMinimalC1ForAPair(partial,2*i);
			if (ii != null) {
				temp.add(ii);
			}
		}
		return temp;
	} 

	private InsertionInformation computeMinimalC1ForAPair(Route partial,int pickupIndex) {
		InsertionInformation min = null;
		for (int i=0;i<partial.size()+1;i++) {
			for (int j=i;j<partial.size()+1;j++) {
				InsertionInformation temp = computeC1ForAPair(partial,pickupIndex,i,j);
				if (temp != null) {
					if (min == null) {
						min = temp;
					}
					else {
						if (temp.getValue() < min.getValue()) {
							min = temp;
						}
					}
				}
			}
		}
		return min;
	}
	
	private InsertionInformation computeC1ForAPair(Route partial,int pickupIndex,int pickupInsertionIndex,int deliveryInsertionIndex) {
		Customer pickup = unroutedCustomers.get(pickupIndex);
		Customer delivery = unroutedCustomers.get(pickupIndex+1);
		boolean isFeasible = partial.isInsertionFeasible(pickup, delivery, pickupInsertionIndex, deliveryInsertionIndex, vp);
		if (!isFeasible) {
			return null;
		}
		else {
			// compute c1 value
			double value;
			if (pickupInsertionIndex == deliveryInsertionIndex) {
				if (pickupInsertionIndex == 0) {
					value = c1(Customers.getSingleInstance().get(0),pickup,partial.get(0))
							+ c1(pickup,delivery,partial.get(0));
				}
				else if (pickupInsertionIndex == partial.size()) {
					value = c1(partial.get(partial.size()-1),pickup,Customers.getSingleInstance().get(0))
							+ c1(pickup,delivery,Customers.getSingleInstance().get(0));
				}
				else {
					value = c1(partial.get(pickupInsertionIndex-1),pickup,partial.get(pickupInsertionIndex))
							+ c1(pickup,delivery,partial.get(pickupInsertionIndex));
				}
			}
			else {
				if (pickupInsertionIndex == 0 && deliveryInsertionIndex == partial.size()) {
					value = c1(Customers.getSingleInstance().get(0),pickup,partial.get(0))
							+ c1(partial.get(partial.size()-1),delivery,Customers.getSingleInstance().get(0));
				}
				else if (pickupInsertionIndex == 0 && deliveryInsertionIndex != partial.size()) {
					value = c1(Customers.getSingleInstance().get(0),pickup,partial.get(0))
							+ c1(partial.get(deliveryInsertionIndex-1),delivery,partial.get(deliveryInsertionIndex));
				}
				else if (pickupInsertionIndex != 0 && deliveryInsertionIndex == partial.size()) {
					value = c1(partial.get(pickupInsertionIndex-1),pickup,partial.get(pickupInsertionIndex))
							+ c1(partial.get(partial.size()-1),delivery,Customers.getSingleInstance().get(0));
				}
				else {
					value = c1(partial.get(pickupInsertionIndex-1),pickup,partial.get(pickupInsertionIndex))
							+ c1(partial.get(deliveryInsertionIndex-1),delivery,partial.get(deliveryInsertionIndex));
				}
			}
			return new InsertionInformation(value, pickupIndex, pickupInsertionIndex, deliveryInsertionIndex);
		}
	}
	
	// select best case with highest c2 value
	public InsertionInformation computeMaximalC2(Vector<InsertionInformation> temp) {
		return selectMaximalC2(computeRelevantC2(temp));
	}

	private InsertionInformation selectMaximalC2(Vector<InsertionInformation> temp) {
		int best = 0;
		for (int i=1;i<temp.size();i++) {
			if (temp.get(i).getValue() > temp.get(best).getValue()) {
				best = i;
			}
		}
		return temp.get(best);
	}

	// c2(i(u*),u*,j(u*))= d0u - c1(i(u),u,j(u))
	private Vector<InsertionInformation> computeRelevantC2(Vector<InsertionInformation> temp) {
		for (int i=0;i<temp.size();i++) {
			Customer pickup = unroutedCustomers.get(temp.get(i).getPickupIndex());
			Customer delivery = unroutedCustomers.get(temp.get(i).getPickupIndex()+1);
			temp.get(i).setValue(DTTable.getSingleInstance().getDTForDepot(pickup.getID()).getDistance()
								+ DTTable.getSingleInstance().getDTForDepot(delivery.getID()).getDistance()
								- temp.get(i).getValue());
		}
		return temp;
	}
	
	// insert best case to relevant route
	public void insert(Route route,InsertionInformation ii) {
		int pickupInsertion = ii.getPickupInsertionIndex();
		int deliveryInsertion = ii.getDeliveryInsertionIndex();
		Customer delivery = unroutedCustomers.remove(ii.getPickupIndex()+1);
		Customer pickup = unroutedCustomers.remove(ii.getPickupIndex());
		
		route.insertAt(pickupInsertion, pickup);
		route.insertAt(deliveryInsertion+1, delivery);
	}

	// c1(i(u),u,j(u)) = min[c1(ip-1,u,ip)]
	private double c1(Customer i,Customer u,Customer j) {
		double value;
		if (i.getID() == 0 && j.getID() == 0) {
			value = 2*DTTable.getSingleInstance().getDTForDepot(u.getID()).getDistance();
		}
		else if (i.getID() == 0 && j.getID() != 0) {
			value = DTTable.getSingleInstance().getDTForDepot(u.getID()).getDistance()
					+ DTTable.getSingleInstance().get(new Key(u.getID(),j.getID())).getDistance()
					- DTTable.getSingleInstance().getDTForDepot(j.getID()).getDistance();
		}
		else if (i.getID() != 0 && j.getID() == 0) {
			value = DTTable.getSingleInstance().getDTForDepot(u.getID()).getDistance()
					+ DTTable.getSingleInstance().get(new Key(u.getID(),i.getID())).getDistance()
					- DTTable.getSingleInstance().getDTForDepot(i.getID()).getDistance(); 
		}
		else {
			value = DTTable.getSingleInstance().get(new Key(i.getID(),u.getID())).getDistance()
					+ DTTable.getSingleInstance().get(new Key(u.getID(),j.getID())).getDistance()
					- DTTable.getSingleInstance().get(new Key(i.getID(),j.getID())).getDistance();
		}
		return value;
	}
	
	public Vector<Customer> getUnroutedCustomers() {
		return this.unroutedCustomers;
	}
}
