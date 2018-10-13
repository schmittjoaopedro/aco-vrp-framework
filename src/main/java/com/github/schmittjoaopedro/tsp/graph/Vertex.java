package com.github.schmittjoaopedro.tsp.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Vertex {

    private Integer id;

    private double x;

    private double y;

    private Map<Integer, Edge> adj = new HashMap<>();

    public Vertex(Integer id) {
        super();
        setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Map<Integer, Edge> getAdj() {
        return adj;
    }

    public Iterator<Edge> getEdges() {
        return adj.values().iterator();
    }

    @Override
    public String toString() {
        return super.toString() + " (" + getId() + ")";
    }
}
