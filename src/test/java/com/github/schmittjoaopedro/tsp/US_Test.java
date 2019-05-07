package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.aco.ls.us.USOperator;
import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.GraphFactory;
import com.github.schmittjoaopedro.tsp.graph.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Compatible with the C++ version proposed in:
 * <p>
 * M. Mavrovouniotis, F. M. Müller, S. Yang. Ant colony optimization with local search for dynamic traveling
 * salesman problems. IEEE Transactions on Cybernetics, vol. 47, no. 7, pp. 1743-1756, 2017.
 * -> https://mavrovouniotis.github.io/Codes/MMAS_US.zip
 * <p>
 * To compare these tests with the C program, remember that the random number generator can generate 0. Tho evict
 * this problem, run the algorithm with magnitude = -0.1
 */
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

    @Test
    public void test_unstringing_stringing_kroA100_route_in_order_asc() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(191387);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(23314);
        assertThat(getTourString(route)).isEqualTo("[0, 46, 29, 38, 84, 67, 72, 68, 80, 24, 86, 50, 60, 57, 92, 27, 66, 63, 39, 53, 1, 43, 49, 81, 94, 12, 75, 32, 36, 4, 95, 77, 51, 47, 99, 40, 70, 13, 2, 42, 45, 28, 33, 82, 54, 6, 8, 56, 19, 11, 26, 85, 34, 61, 59, 76, 22, 97, 90, 44, 31, 10, 14, 16, 58, 73, 20, 71, 9, 83, 35, 98, 37, 23, 17, 78, 52, 87, 15, 93, 21, 69, 65, 25, 64, 3, 18, 74, 96, 55, 79, 30, 88, 41, 7, 91, 5, 48, 89, 62, 0]");

    }

    @Test
    public void test_unstringing_stringing_kroA150_route_in_order_asc() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA150));
        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(287844);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(26885);
        assertThat(getTourString(route)).isEqualTo("[0, 129, 91, 7, 132, 137, 88, 30, 79, 121, 41, 138, 55, 142, 118, 96, 74, 18, 133, 52, 3, 64, 117, 123, 25, 128, 65, 69, 21, 93, 15, 87, 136, 78, 17, 23, 37, 103, 110, 101, 98, 35, 126, 58, 140, 73, 20, 16, 14, 10, 31, 108, 44, 90, 97, 22, 109, 76, 59, 61, 149, 34, 85, 26, 11, 19, 56, 6, 8, 116, 33, 82, 54, 148, 119, 114, 122, 42, 127, 135, 40, 70, 99, 47, 13, 2, 45, 28, 131, 111, 106, 29, 120, 100, 38, 95, 77, 51, 4, 36, 146, 102, 145, 32, 75, 12, 94, 125, 81, 115, 49, 43, 113, 143, 1, 53, 39, 63, 68, 72, 67, 84, 134, 139, 144, 86, 50, 124, 24, 80, 60, 57, 107, 66, 147, 141, 104, 27, 92, 130, 46, 112, 71, 9, 83, 105, 89, 48, 5, 62, 0]");
    }

    @Test
    public void test_unstringing_stringing_kroA200_route_in_order_asc() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(373938);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(30496);
        assertThat(getTourString(route)).isEqualTo("[0, 144, 84, 131, 39, 146, 11, 162, 92, 105, 148, 189, 48, 17, 109, 28, 183, 36, 178, 65, 152, 165, 95, 86, 125, 164, 103, 96, 80, 196, 44, 32, 99, 155, 179, 130, 141, 68, 188, 72, 2, 58, 40, 88, 153, 20, 139, 163, 22, 172, 149, 143, 101, 69, 75, 90, 94, 93, 181, 194, 112, 175, 132, 136, 42, 104, 4, 177, 151, 55, 195, 85, 138, 49, 167, 184, 61, 82, 71, 129, 70, 37, 38, 27, 147, 87, 199, 170, 140, 57, 97, 113, 102, 33, 89, 142, 24, 16, 145, 128, 133, 21, 7, 154, 182, 74, 53, 5, 108, 106, 156, 186, 150, 160, 124, 180, 1, 34, 168, 67, 29, 166, 157, 76, 79, 64, 176, 66, 30, 46, 119, 111, 126, 185, 134, 41, 54, 19, 63, 161, 159, 78, 12, 14, 122, 197, 190, 26, 192, 127, 59, 100, 3, 191, 13, 107, 73, 56, 35, 174, 9, 91, 98, 18, 118, 47, 83, 10, 51, 169, 121, 115, 187, 43, 62, 193, 50, 15, 117, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 171, 45, 110, 116, 114, 52, 0]");
    }

    @Test
    public void test_unstringing_stringing_kroA100_route_parity() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA100));
        int[] route = new int[graph.getVertexCount() + 1];
        int counter = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 == 0) {
                route[counter++] = i;
            }
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 != 0) {
                route[counter++] = i;
            }
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(159833);
        assertThat(getTourString(route)).isEqualTo("[0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(21794);
        assertThat(getTourString(route)).isEqualTo("[0, 62, 5, 48, 89, 18, 74, 91, 7, 41, 88, 30, 79, 55, 96, 3, 64, 25, 65, 69, 21, 93, 15, 87, 52, 78, 17, 23, 37, 98, 35, 83, 9, 71, 20, 73, 58, 16, 14, 10, 31, 44, 90, 97, 22, 59, 61, 76, 57, 60, 80, 24, 50, 86, 8, 6, 56, 19, 85, 34, 26, 11, 54, 82, 33, 28, 45, 42, 2, 13, 70, 40, 99, 47, 29, 38, 95, 77, 51, 4, 36, 32, 75, 12, 94, 81, 84, 67, 72, 49, 43, 1, 53, 39, 63, 68, 66, 27, 92, 46, 0]");

    }

    @Test
    public void test_unstringing_stringing_kroA150_route_parity() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA150));
        int[] route = new int[graph.getVertexCount() + 1];
        int counter = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 == 0) {
                route[counter++] = i;
            }
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 != 0) {
                route[counter++] = i;
            }
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(246711);
        assertThat(getTourString(route)).isEqualTo("[0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 100, 102, 104, 106, 108, 110, 112, 114, 116, 118, 120, 122, 124, 126, 128, 130, 132, 134, 136, 138, 140, 142, 144, 146, 148, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 101, 103, 105, 107, 109, 111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 133, 135, 137, 139, 141, 143, 145, 147, 149, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(28294);
        assertThat(getTourString(route)).isEqualTo("[0, 129, 91, 7, 41, 121, 79, 30, 88, 137, 132, 147, 141, 104, 66, 27, 92, 130, 57, 107, 60, 24, 80, 68, 63, 39, 53, 1, 143, 113, 43, 72, 49, 115, 81, 125, 94, 12, 75, 32, 145, 102, 146, 36, 4, 51, 77, 95, 38, 100, 120, 29, 106, 84, 67, 139, 134, 111, 131, 28, 45, 2, 13, 47, 99, 70, 40, 135, 127, 42, 122, 114, 119, 33, 82, 54, 148, 11, 19, 26, 85, 34, 149, 61, 59, 109, 76, 50, 124, 86, 144, 8, 116, 6, 56, 14, 16, 10, 31, 44, 22, 97, 90, 108, 46, 112, 9, 83, 71, 20, 73, 140, 58, 126, 35, 98, 101, 110, 103, 37, 23, 17, 78, 136, 87, 15, 93, 21, 69, 65, 128, 25, 123, 117, 64, 3, 118, 142, 55, 138, 96, 74, 18, 52, 133, 105, 89, 48, 5, 62, 0]");
    }

    @Test
    public void test_unstringing_stringing_kroA200_route_parity() {
        Graph graph = GraphFactory.createGraphFromTSP(new File(kroA200));
        int[] route = new int[graph.getVertexCount() + 1];
        int counter = 0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 == 0) {
                route[counter++] = i;
            }
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (i % 2 != 0) {
                route[counter++] = i;
            }
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(340562);
        assertThat(getTourString(route)).isEqualTo("[0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66, 68, 70, 72, 74, 76, 78, 80, 82, 84, 86, 88, 90, 92, 94, 96, 98, 100, 102, 104, 106, 108, 110, 112, 114, 116, 118, 120, 122, 124, 126, 128, 130, 132, 134, 136, 138, 140, 142, 144, 146, 148, 150, 152, 154, 156, 158, 160, 162, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 184, 186, 188, 190, 192, 194, 196, 198, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 101, 103, 105, 107, 109, 111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 133, 135, 137, 139, 141, 143, 145, 147, 149, 151, 153, 155, 157, 159, 161, 163, 165, 167, 169, 171, 173, 175, 177, 179, 181, 183, 185, 187, 189, 191, 193, 195, 197, 199, 0]");
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(30303);
        assertThat(getTourString(route)).isEqualTo("[0, 84, 144, 190, 26, 197, 122, 14, 12, 78, 159, 161, 63, 19, 54, 41, 134, 185, 126, 111, 119, 46, 30, 66, 176, 64, 79, 76, 157, 192, 127, 166, 29, 67, 168, 34, 1, 180, 124, 160, 150, 186, 156, 106, 108, 5, 53, 74, 154, 182, 21, 133, 7, 16, 24, 142, 89, 33, 57, 140, 170, 199, 147, 87, 97, 113, 102, 145, 128, 82, 61, 184, 167, 49, 85, 138, 71, 129, 38, 27, 37, 70, 55, 151, 177, 195, 4, 104, 42, 136, 132, 175, 112, 194, 181, 93, 94, 90, 149, 172, 22, 143, 69, 75, 101, 163, 139, 20, 153, 88, 40, 58, 2, 72, 188, 130, 179, 141, 68, 107, 13, 191, 59, 100, 3, 162, 92, 105, 148, 189, 18, 98, 91, 9, 174, 35, 56, 73, 99, 155, 32, 44, 196, 80, 96, 47, 169, 83, 10, 51, 86, 125, 95, 165, 164, 103, 118, 65, 152, 43, 187, 115, 121, 193, 50, 62, 178, 15, 117, 123, 137, 8, 77, 81, 6, 198, 25, 60, 135, 31, 23, 158, 173, 120, 45, 171, 28, 183, 36, 109, 17, 48, 11, 146, 39, 131, 110, 116, 114, 52, 0]");
    }

    @Test
    public void eternal_loop_test_due_to_double_usage() {
        Graph graph = new Graph();
        addVertex(graph, 0, 1393.0, 1368.0);
        addVertex(graph, 1, 1625.0, 1651.0);
        addVertex(graph, 2, 1424.0, 1728.0);
        addVertex(graph, 3, 1247.0, 1945.0);
        addVertex(graph, 4, 1234.0, 1946.0);
        addVertex(graph, 5, 1251.0, 1832.0);
        addVertex(graph, 6, 929.0, 1766.0);
        addVertex(graph, 7, 1787.0, 1009.0);
        addVertex(graph, 8, 1795.0, 962.0);
        addVertex(graph, 9, 1917.0, 687.0);

        addEdge(graph, 0, new double[]{0.0, 366.0, 361.0, 595.0, 599.0, 485.0, 611.0, 875.6343871174662, 571.0, 910.0082238716822});
        addEdge(graph, 1, new double[]{1927.002332823819, 0.0, 246.0304294961538, 479.0, 490.0, 570.1165542442657, 705.0, 1040.3091995749821, 911.0896580954092, 2256.025272420235});
        addEdge(graph, 2, new double[]{880.5189980553982, 215.0, 0.0, 280.0, 781.2338069842648, 202.0, 496.0, 805.0, 851.0, 2940.0784876087364});
        addEdge(graph, 3, new double[]{2740.575750864771, 1235.2050119397086, 280.0, 0.0, 13.0, 229.8297036515996, 365.0, 2660.6817160963956, 2909.4989726694057, 2333.341216606503});
        addEdge(graph, 4, new double[]{1018.0, 490.0, 375.1276882974077, 13.0, 0.0, 115.0, 376.21582350271683, 1171.1410585305293, 1133.0, 1995.379322405805});
        addEdge(graph, 5, new double[]{2013.691444088283, 605.9896505907768, 311.31551316443523, 113.0, 141.919622307118, 0.0, 396.07122434231457, 982.0, 1026.0, 1325.0});
        addEdge(graph, 6, new double[]{942.0, 705.0, 805.9093134981456, 365.0, 657.1166490020089, 329.0, 0.0, 1162.2402132460068, 3027.3696851150085, 1685.2115044851967});
        addEdge(graph, 7, new double[]{608.7798072513575, 1575.7953841371364, 805.0, 2576.1775551547, 1767.5359254029322, 2847.0027055359715, 2773.507336217307, 0.0, 134.23738707697896, 796.7984551072398});
        addEdge(graph, 8, new double[]{416.0, 1354.4820151510723, 2504.6809967493355, 1653.7573500967962, 1133.0, 2667.4207147192114, 2338.6814453712955, 103.98598005408093, 0.0, 301.0});
        addEdge(graph, 9, new double[]{593.0, 2016.6100150026998, 1736.956221514316, 3231.0289740550916, 1432.0, 3892.7711543941473, 4349.640702920234, 550.1834099685329, 560.1089506010246, 0.0});

        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(3606.5792541614537);
        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(3506.723853321856);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 5, 3, 4, 6, 7, 8, 9, 0]");
    }

    @Test
    public void few_vertices_to_execute_us_test() {
        Graph graph = new Graph();

        addVertex(graph, 0, 2678.0, 1825.0);
        addVertex(graph, 1, 2597.0, 1830.0);
        addVertex(graph, 2, 2573.0, 1969.0);
        addVertex(graph, 3, 1424.0, 1728.0);
        addVertex(graph, 4, 1393.0, 1368.0);
        addVertex(graph, 5, 1724.0, 1642.0);

        addEdge(graph, 0, new double[]{0.0, 81.0, 471.0690943652884, 2987.8136878846585, 1896.9566327780308, 2546.4633818669936});
        addEdge(graph, 1, new double[]{1508.0, 0.0, 206.06261057843287, 2285.1841979989163, 3469.787173650955, 1792.423169887199});
        addEdge(graph, 2, new double[]{1576.0, 141.0, 0.0, 1174.0, 1324.0, 2463.2213921129605});
        addEdge(graph, 3, new double[]{790.0, 1177.0, 1719.5585923433525, 0.0, 361.0, 716.5902443544664});
        addEdge(graph, 4, new double[]{855.6359692656566, 1290.0, 1324.0, 391.75284227934867, 0.0, 430.0});
        addEdge(graph, 5, new double[]{783.0, 893.0, 910.0, 796.3129478887017, 430.0, 0.0});

        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(3035.062610578433);

        USOperator us = new USOperator();
        assertThat(us.init(graph, route)).isFalse();
    }

    /**
     * The same test executed in the original version in C++ from Mavrovouniotis et al. causes the same behavior.
     *
     *  Code in C++ to run:
     *  int main(int argc, char *argv[]) {
     *
     * 	problem_size = 10;
     *
     * 	double** dists = generate_2D_matrix_double(problem_size, problem_size);
     *
     * 	tsp_file.xyvalues(problem_size, 0, 178.0, 24.0);
     * 	tsp_file.xyvalues(problem_size, 1, 241.0, 341.0);
     * 	tsp_file.xyvalues(problem_size, 2, 457.0, 334.0);
     * 	tsp_file.xyvalues(problem_size, 3, 776.0, 392.0);
     * 	tsp_file.xyvalues(problem_size, 4, 839.0, 620.0);
     * 	tsp_file.xyvalues(problem_size, 5, 953.0, 268.0);
     * 	tsp_file.xyvalues(problem_size, 6, 1178.0, 100.0);
     * 	tsp_file.xyvalues(problem_size, 7, 1256.0, 61.0);
     * 	tsp_file.xyvalues(problem_size, 8, 1429.0, 134.0);
     * 	tsp_file.xyvalues(problem_size, 9, 1323.0, 280.0);
     *
     * 	dists[0][0] = 0.0;
     * 	dists[0][1] = 900.0620925455916;
     * 	dists[0][2] = 1211.2323945258895;
     * 	dists[0][3] = 1359.1588235781057;
     * 	dists[0][4] = 1729.5632819696002;
     * 	dists[0][5] = 813.0;
     * 	dists[0][6] = 2970.5950447626465;
     * 	dists[0][7] = 1079.0;
     * 	dists[0][8] = 2843.6801218060277;
     * 	dists[0][9] = 3044.1064571351308;
     *
     * 	dists[1][0] = 1286.0;
     * 	dists[1][1] = 0.0;
     * 	dists[1][2] = 281.74406380296176;
     * 	dists[1][3] = 537.0;
     * 	dists[1][4] = 1252.7864153157623;
     * 	dists[1][5] = 786.4170177209927;
     * 	dists[1][6] = 967.0;
     * 	dists[1][7] = 2792.004622093467;
     * 	dists[1][8] = 1612.1302860995813;
     * 	dists[1][9] = 1084.0;
     *
     * 	dists[2][0] = 1670.8834457854196;
     * 	dists[2][1] = 216.0;
     * 	dists[2][2] = 0.0;
     * 	dists[2][3] = 334.1730777037129;
     * 	dists[2][4] = 477.0;
     * 	dists[2][5] = 500.0;
     * 	dists[2][6] = 758.0;
     * 	dists[2][7] = 844.0;
     * 	dists[2][8] = 1292.8086372840896;
     * 	dists[2][9] = 868.0;
     *
     * 	dists[3][0] = 2201.695317866939;
     * 	dists[3][1] = 871.3897525916698;
     * 	dists[3][2] = 324.0;
     * 	dists[3][3] = 0.0;
     * 	dists[3][4] = 237.0;
     * 	dists[3][5] = 611.1898891517641;
     * 	dists[3][6] = 497.0;
     * 	dists[3][7] = 583.0;
     * 	dists[3][8] = 702.0;
     * 	dists[3][9] = 671.966107970392;
     *
     * 	dists[4][0] = 628.0;
     * 	dists[4][1] = 1379.884019939828;
     * 	dists[4][2] = 1064.1688453443126;
     * 	dists[4][3] = 237.0;
     * 	dists[4][4] = 0.0;
     * 	dists[4][5] = 634.1111406890833;
     * 	dists[4][6] = 621.0;
     * 	dists[4][7] = 1078.86722820482;
     * 	dists[4][8] = 764.0;
     * 	dists[4][9] = 1413.8168064096124;
     *
     * 	dists[5][0] = 2045.6748205734464;
     * 	dists[5][1] = 723.0525901863624;
     * 	dists[5][2] = 500.0;
     * 	dists[5][3] = 487.4461530864222;
     * 	dists[5][4] = 1009.3055331870214;
     * 	dists[5][5] = 0.0;
     * 	dists[5][6] = 281.0;
     * 	dists[5][7] = 367.0;
     * 	dists[5][8] = 1283.105788034829;
     * 	dists[5][9] = 965.6670689518158;
     *
     * 	dists[6][0] = 1455.356029235959;
     * 	dists[6][1] = 2329.691092648013;
     * 	dists[6][2] = 758.0;
     * 	dists[6][3] = 1369.6398378152921;
     * 	dists[6][4] = 718.6983247969229;
     * 	dists[6][5] = 691.267866164247;
     * 	dists[6][6] = 0.0;
     * 	dists[6][7] = 225.8960395832207;
     * 	dists[6][8] = 253.0;
     * 	dists[6][9] = 231.0;
     *
     * 	dists[7][0] = 2464.243953635703;
     * 	dists[7][1] = 2398.766456375107;
     * 	dists[7][2] = 1628.5610430655327;
     * 	dists[7][3] = 824.429918560779;
     * 	dists[7][4] = 697.0;
     * 	dists[7][5] = 709.2948949970747;
     * 	dists[7][6] = 250.44996507920138;
     * 	dists[7][7] = 0.0;
     * 	dists[7][8] = 188.0;
     * 	dists[7][9] = 617.988873989192;
     *
     * 	dists[8][0] = 806.0;
     * 	dists[8][1] = 1206.0;
     * 	dists[8][2] = 992.0;
     * 	dists[8][3] = 1745.63472204523;
     * 	dists[8][4] = 2040.549780850201;
     * 	dists[8][5] = 495.0;
     * 	dists[8][6] = 548.0697944810614;
     * 	dists[8][7] = 206.3930332572461;
     * 	dists[8][8] = 0.0;
     * 	dists[8][9] = 333.1141012849151;
     *
     * 	dists[9][0] = 661.0;
     * 	dists[9][1] = 2371.058915729798;
     * 	dists[9][2] = 1708.508264109688;
     * 	dists[9][3] = 1036.232861045186;
     * 	dists[9][4] = 1674.615643905726;
     * 	dists[9][5] = 726.6351151690391;
     * 	dists[9][6] = 679.5784341617549;
     * 	dists[9][7] = 576.8431008085734;
     * 	dists[9][8] = 527.6165853671087;
     * 	dists[9][9] = 0.0;
     *
     * 	int tour[11] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
     *
     * 	double tour_length = 0.0;
     * 	for (int i = 0; i < problem_size; i++) {
     * 		tour_length += dists[tour[i]][tour[i + 1]];
     *        }
     * 	printf("Costs = %f\n", tour_length);
     *
     * 	tsp_file.distances(dists);
     *
     * 	genius.initialize();
     * 	genius.initnneighbour(tsp_file.task);
     * 	// Take the best ant for the considered interation;
     * 	genius.petittourne(tour[0], tsp_file.g); //initial
     * 	genius.ajoutenoeudprox(tour[0], tsp_file.task, tsp_file.d);
     * 	for (int i = 1; i < problem_size; i++) {
     * 		genius.ajoute_a_tourne(tour[i], tsp_file.g);
     * 		genius.ajoutenoeudprox(tour[i], tsp_file.task, tsp_file.d);
     *    } //copy the tour and calculates the nearest neighbours of the nodes.
     *
     * 	  //if (!genius.numerote_tourne()) return 1;
     * 	if (!genius.numerote_tourne()) {
     * 		//do nothing
     *    }
     * 	else {
     * 		//viz = MAXK;
     * 		genius.route_copy(tsp_file.task, tsp_file.g, tsp_file.d, tour_length);
     * 		//genius.showroute(problem_size);
     * 		//COPY THE RESULTING TOUR
     * 		pri = genius.t.ptr;
     * 		for (int i = 0; i < problem_size; i++) {
     * 			tour[i] = pri->noeud - 1;
     * 			//cout << "ant[" << i << "]= " << ant_population[best_sol].tour[i] << "\n";
     * 			pri = pri->prochain;
     *        }
     * 		tour[problem_size] = tour[0];
     *
     * 		tour_length = 0.0;
     * 		for (int i = 0; i < problem_size; i++) {
     * 			tour_length += dists[tour[i]][tour[i + 1]];
     * 			printf("%d -> %d ; ", tour[i], tour[i + 1]);
     *        }
     *
     * 		printf("\nCosts = %f\n", tour_length);
     *    }
     *
     * 	return 0;
     * }
     *
     */
    @Test
    public void eternal_loop_test() {
        Graph graph = new Graph();

        addVertex(graph, 0, 178.0, 24.0);
        addVertex(graph, 1, 241.0, 341.0);
        addVertex(graph, 2, 457.0, 334.0);
        addVertex(graph, 3, 776.0, 392.0);
        addVertex(graph, 4, 839.0, 620.0);
        addVertex(graph, 5, 953.0, 268.0);
        addVertex(graph, 6, 1178.0, 100.0);
        addVertex(graph, 7, 1256.0, 61.0);
        addVertex(graph, 8, 1429.0, 134.0);
        addVertex(graph, 9, 1323.0, 280.0);

        addEdge(graph, 0, new double[]{0.0, 900.0620925455916, 1211.2323945258895, 1359.1588235781057, 1729.5632819696002, 813.0, 2970.5950447626465, 1079.0, 2843.6801218060277, 3044.1064571351308});
        addEdge(graph, 1, new double[]{1286.0, 0.0, 281.74406380296176, 537.0, 1252.7864153157623, 786.4170177209927, 967.0, 2792.004622093467, 1612.1302860995813, 1084.0});
        addEdge(graph, 2, new double[]{1670.8834457854196, 216.0, 0.0, 334.1730777037129, 477.0, 500.0, 758.0, 844.0, 1292.8086372840896, 868.0});
        addEdge(graph, 3, new double[]{2201.695317866939, 871.3897525916698, 324.0, 0.0, 237.0, 611.1898891517641, 497.0, 583.0, 702.0, 671.966107970392});
        addEdge(graph, 4, new double[]{628.0, 1379.884019939828, 1064.1688453443126, 237.0, 0.0, 634.1111406890833, 621.0, 1078.86722820482, 764.0, 1413.8168064096124});
        addEdge(graph, 5, new double[]{2045.6748205734464, 723.0525901863624, 500.0, 487.4461530864222, 1009.3055331870214, 0.0, 281.0, 367.0, 1283.105788034829, 965.6670689518158});
        addEdge(graph, 6, new double[]{1455.356029235959, 2329.691092648013, 758.0, 1369.6398378152921, 718.6983247969229, 691.267866164247, 0.0, 225.8960395832207, 253.0, 231.0});
        addEdge(graph, 7, new double[]{2464.243953635703, 2398.766456375107, 1628.5610430655327, 824.429918560779, 697.0, 709.2948949970747, 250.44996507920138, 0.0, 188.0, 617.988873989192});
        addEdge(graph, 8, new double[]{806.0, 1206.0, 992.0, 1745.63472204523, 2040.549780850201, 495.0, 548.0697944810614, 206.3930332572461, 0.0, 333.1141012849151});
        addEdge(graph, 9, new double[]{661.0, 2371.058915729798, 1708.508264109688, 1036.232861045186, 1674.615643905726, 726.6351151690391, 679.5784341617549, 576.8431008085734, 527.6165853671087, 0.0});

        int[] route = new int[graph.getVertexCount() + 1];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            route[i] = i;
        }
        route[graph.getVertexCount()] = 0;
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(4076.1005156094852);
        USOperator us = new USOperator();
        us.setStopEternalLoops(true);
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(4076.1005156094852);
        assertThat(getTourString(route)).isEqualTo("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0]");
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

    private double fitnessEvaluation(Graph graph, int[] route) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(route[i], route[i + 1]).getCost();
        }
        return cost;
    }

    private String getTourString(int[] route) {
        StringBuilder tour = new StringBuilder("[");
        for (int vertex : route) {
            tour.append(vertex).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

}
