package com.github.schmittjoaopedro.aco;

import com.github.schmittjoaopedro.graph.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ant {

    private List<Vertex> tour;

    private Set<Vertex> visited;

    private Double cost;

    public List<Vertex> getTour() {
        if (tour == null) tour = new ArrayList<>();
        return tour;
    }

    public void setTour(List<Vertex> tour) {
        this.tour = tour;
    }

    public Set<Vertex> getVisited() {
        if (visited == null) visited = new HashSet<>();
        return visited;
    }

    public void setVisited(Set<Vertex> visited) {
        this.visited = visited;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
