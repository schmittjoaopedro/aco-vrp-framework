package com.github.schmittjoaopedro.vrp.pdptw.model;

import java.util.HashMap;
import java.util.Map;

// distance and time for pairs of places
public class DTTable {

    private static DTTable dtTable = new DTTable();

    private HashMap<Key, Value> hashmap;

    private Value[][] dtMatrix;

    private DTTable() {
        hashmap = new HashMap<Key, Value>();
    }

    public void put(Key key, Value value) {
        hashmap.put(key, value);
    }

    public void compile() {
        int maxStart = 0;
        int maxEnd = 0;
        for (Key keys : hashmap.keySet()) {
            maxStart = Math.max(maxStart, keys.getStart());
            maxEnd = Math.max(maxEnd, keys.getEnd());
        }
        dtMatrix = new Value[maxStart + 1][maxEnd + 1];
        for (Map.Entry<Key, Value> entry : hashmap.entrySet()) {
            dtMatrix[entry.getKey().getStart()][entry.getKey().getEnd()] = entry.getValue();
        }
    }

    public Value get(Key key) {
        //return hashmap.get(key);
        return dtMatrix[key.getStart()][key.getEnd()];
    }

    public int size() {
        return hashmap.size();
    }

    public Value getDTForDepot(int id) {
        return this.get(new Key(Customers.getSingleInstance().get(0).getID(), id));
    }

    public static DTTable getSingleInstance() {
        return dtTable;
    }
}