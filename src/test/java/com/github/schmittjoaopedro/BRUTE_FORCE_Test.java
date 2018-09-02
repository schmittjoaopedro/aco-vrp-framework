package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.algorithms.BRUTE_FORCE_TSP;
import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.Vertex;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BRUTE_FORCE_Test {

    private String ulysses16;

    @Before
    public void beforeClass() {
        ulysses16 = getClass().getClassLoader().getResource("tsp/ulysses16.tsp").getFile();
    }


    @Test
    public void test_brute_force_algorithm() {
        Graph graph = new Graph();

        addVertex(graph, 0, 1, 1);
        addVertex(graph, 1, 2, 2);
        addVertex(graph, 2, 3, 3);
        addVertex(graph, 3, 4, 4);
        addVertex(graph, 4, 5, 5);
        addVertex(graph, 5, 6, 6);

        addEdge(graph, 0, new double[]{10, 10, 10, 10,  1, 10});
        addEdge(graph, 1, new double[]{10, 10,  1, 10, 10, 10});
        addEdge(graph, 2, new double[]{10, 10, 10,  1, 10, 10});
        addEdge(graph, 3, new double[]{10, 10, 10, 10, 10,  1});
        addEdge(graph, 4, new double[]{10,  1, 10, 10, 10, 10});
        addEdge(graph, 5, new double[]{ 1, 10, 10, 10, 10, 10});

        BRUTE_FORCE_TSP brute_force_tsp = new BRUTE_FORCE_TSP(graph);
        brute_force_tsp.run();
        assertThat(brute_force_tsp.getCost()).isEqualTo(6);
        assertThat(brute_force_tsp.getTourString()).isEqualTo("[0, 4, 1, 2, 3, 5, 0]");
    }

    private void addVertex(Graph g, int id, double x, double y) {
        Vertex vertex = new Vertex(id);
        vertex.setX(x);
        vertex.setY(y);
        g.addVertex(vertex);
    }

    private void addEdge(Graph g, int id, double... costs) {
        for (int i = 0; i < g.getVertexCount(); i++) {
            if (i == id) continue;
            Edge edge = new Edge(g.getEdgesSize());
            edge.setFrom(g.getVertex(id));
            edge.setTo(g.getVertex(i));
            edge.setCost(costs[i]);
            g.addEdge(edge);
            edge.getFrom().getAdj().put(edge.getToId(), edge);
        }
    }
}
