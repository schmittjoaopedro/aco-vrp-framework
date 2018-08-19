package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.aco.MMAS;
import com.github.schmittjoaopedro.aco.ls.us.USOperator;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.graph.Vertex;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class US_Test {

    private String kroA100;

    private String kroA150;

    private String kroA200;

    @Before
    public void beforeClass() {
        kroA100 = getClass().getClassLoader().getResource("tsp/KroA100.tsp").getFile();
        kroA150 = getClass().getClassLoader().getResource("tsp/kroA150.tsp").getFile();
        kroA200 = getClass().getClassLoader().getResource("tsp/kroA200.tsp").getFile();
    }

    /**
     * Compatible with the C++ version proposed in:
     *
     * M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for dynamic traveling
     * salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7, pp. 1743-1756, 2017.
     * -> https://mavrovouniotis.github.io/Codes/MMAS_US.zip
     */
    @Test
    public void test_unstringing_stringing_kroA100() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        List<Vertex> route = new ArrayList<>();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route.add(graph.getVertex(i));
        }
        route.add(route.get(0));
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(191387);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 0]");
        USOperator us = new USOperator();
        us.init(graph, route, fitnessEvaluation(graph, route));
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(23314);
        assertThat(getTourString(route)).isEqualTo("[0, 46, 29, 38, 84, 67, 72, 68, 80, 24, 86, 50, 60, 57, 92, 27, 66, 63, 39, 53, 1, 43, 49, 81, 94, 12, 75, 32, 36, 4, 95, 77, 51, 47, 99, 40, 70, 13, 2, 42, 45, 28, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 97, 90, 44, 31, 10, 14, 16, 58, 73, 20, 71, 9, 83, 35, 98, 37, 23, 17, 78, 52, 87, 15, 93, 21, 69, 65, 25, 64, 3, 18, 74, 96, 55, 79, 30, 88, 41, 7, 91, 5, 48, 89, 62, 0]");

    }

    @Test
    public void test_unstringing_stringing_kroA150() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA150));
        List<Vertex> route = new ArrayList<>();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route.add(graph.getVertex(i));
        }
        route.add(route.get(0));
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(287844);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 0]");
        USOperator us = new USOperator();
        us.init(graph, route, fitnessEvaluation(graph, route));
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(27755);
        assertThat(getTourString(route)).isEqualTo("[0, 129, 91, 7, 41, 121, 88, 30, 79, 138, 55, 142, 118, 96, 74, 18, 133, 52, 3, 64, 117, 123, 25, 128, 65, 69, 21, 93, 15, 87, 136, 78, 17, 23, 37, 103, 110, 101, 98, 35, 126, 58, 140, 73, 20, 16, 14, 10, 31, 108, 44, 90, 97, 22, 59, 61, 76, 109, 57, 107, 60, 24, 80, 68, 72, 67, 84, 134, 139, 124, 50, 86, 144, 8, 116, 6, 56, 19, 149, 34, 85, 26, 11, 148, 54, 82, 33, 119, 114, 122, 42, 127, 135, 40, 70, 99, 47, 13, 2, 45, 28, 131, 111, 106, 29, 120, 100, 38, 95, 77, 51, 4, 36, 146, 102, 145, 32, 75, 12, 94, 125, 81, 115, 49, 43, 113, 1, 143, 63, 39, 53, 137, 132, 147, 141, 104, 66, 27, 92, 130, 46, 112, 71, 9, 83, 105, 89, 48, 5, 62, 0]");

    }

    private double fitnessEvaluation(Graph graph, List<Vertex> route) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(route.get(i).getId(), route.get(i + 1).getId()).getCost();
        }
        return cost;
    }

    private String getTourString(List<Vertex> route) {
        StringBuilder tour = new StringBuilder("[");
        for (Vertex vertex : route) {
            tour.append(vertex.getId()).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

}
