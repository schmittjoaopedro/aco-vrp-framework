package com.github.schmittjoaopedro.graph;

import com.github.schmittjoaopedro.utils.Validation;

import java.util.*;

public class Graph {

    private Map<Object, Vertex> vertices = new HashMap<>();

    private Set<Edge> edges = new HashSet<>();

    public Iterator<Vertex> getVertices() {
        return vertices.values().iterator();
    }

    public void addVertex(Vertex vertex) {
        Validation.check(vertex.getId() == null, "Not valid null ID");
        Validation.check(vertices.containsKey(vertex.getId()), "Vertex: " + vertex.getId() + " already registered");
        vertices.put(vertex.getId(), vertex);
    }

    public void addEdge(Edge edge) {
        Validation.check(edge.getFrom().getId() == null, "Not valid ID of 'from' vertex");
        Validation.check(edge.getTo().getId() == null, "Not valid ID of 'to' vertex");
        Validation.check(!vertices.containsKey(edge.getFrom().getId()), "Vertex: " + edge.getFrom().getId() + " already not registered");
        Validation.check(!vertices.containsKey(edge.getTo().getId()), "Vertex: " + edge.getTo().getId() + " already not registered");
        edges.add(edge);
    }

    public Vertex getVertex(Object id) {
        Validation.check(id == null, "Id can't be null");
        Validation.check(!vertices.containsKey(id), "Vertex: " + id + " not registered");
        return vertices.get(id);
    }

    public Edge getEdge(Object fromId, Object toId) {
        Validation.check(fromId == null || toId == null, "Both ID's can't be null");
        Validation.check(!vertices.containsKey(fromId), "Vertex: " + fromId + " not registered");
        Validation.check(!vertices.containsKey(toId), "Vertex: " + toId + " not registered");
        return vertices.get(fromId).getAdj().get(toId);
    }

    public Iterator<Edge> getAdj(Object fromId) {
        Validation.check(fromId == null, "ID can't be null.");
        Validation.check(!vertices.containsKey(fromId), "Vertex: " + fromId + " not registered");
        return vertices.get(fromId).getAdj().values().iterator();
    }

    public Iterator<Edge> getEdges() {
        return edges.iterator();
    }

    public int getVertexCount() {
        return vertices.size();
    }
}
