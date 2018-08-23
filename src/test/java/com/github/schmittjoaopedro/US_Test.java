package com.github.schmittjoaopedro;

import com.github.schmittjoaopedro.aco.ls.us.USOperator;
import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.GraphFactory;
import com.github.schmittjoaopedro.graph.Vertex;
import org.junit.Before;
import org.junit.Ignore;
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
    @Ignore
    public void eternal_loop_test() {
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

        USOperator us = new USOperator();
        us.init(graph, route);
        us.optimize();
        route = us.getResult();
        assertThat(fitnessEvaluation(graph, route)).isEqualTo(30303);
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
