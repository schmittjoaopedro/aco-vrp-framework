package com.github.schmittjoaopedro.tsp.graph;

public class Edge {

    private Integer id;

    private double cost;

    private boolean editable = true;

    private Vertex from;

    private Vertex to;

    public Edge(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (this.isEditable()) {
            this.cost = cost;
        }
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public int getFromId() {
        return (int) getFrom().getId();
    }

    public int getToId() {
        return (int) getTo().getId();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
