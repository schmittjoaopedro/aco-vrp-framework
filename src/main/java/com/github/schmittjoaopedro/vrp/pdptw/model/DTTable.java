package com.github.schmittjoaopedro.vrp.pdptw.model;

import java.util.HashMap;

// distance and time for pairs of places
public class DTTable {
	
	private static DTTable dtTable = new DTTable();
	
	private HashMap<Key,Value> hashmap;
	
	private DTTable() {
		hashmap = new HashMap<Key,Value>();
	}
	
	public void put(Key key,Value value) {
		hashmap.put(key,value);
	}
	
	public Value get(Key key) {
		return hashmap.get(key);
	}
	
	public int size() {
		return hashmap.size();
	}
	
	public Value getDTForDepot(int id) {
		return this.get(new Key(Customers.getSingleInstance().get(0).getID(),id));
	}
	
	public static DTTable getSingleInstance() {
		return dtTable;
	}
}