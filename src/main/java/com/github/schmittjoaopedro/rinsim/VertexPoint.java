package com.github.schmittjoaopedro.rinsim;

import com.github.rinde.rinsim.geom.Point;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

public class VertexPoint extends Point {

    private Vertex vertex;

    public VertexPoint(double pX, double pY) {
        super(pX, pY);
    }

    public VertexPoint(Vertex vertex) {
        super(vertex.getX(), vertex.getY());
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
}
