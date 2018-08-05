package com.github.schmittjoaopedro.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Vertex {

    private Object id;

    private double x;

    private double y;

    private Map<Object, Edge> adj = new HashMap<>();

    public Vertex(Object id) {
        super();
        setId(id);
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
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

    public Map<Object, Edge> getAdj() {
        return adj;
    }

    public Iterator<Edge> getEdges() {
        return adj.values().iterator();
    }
}
