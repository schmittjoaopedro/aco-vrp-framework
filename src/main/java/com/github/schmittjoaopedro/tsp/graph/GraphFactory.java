package com.github.schmittjoaopedro.tsp.graph;

import com.github.schmittjoaopedro.tsp.utils.Maths;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public final class GraphFactory {

    public static Graph createGraphFromTSP(File file) {
        try {
            Graph graph = new Graph();
            FileInputStream fisTargetFile = new FileInputStream(file);
            String fileContent[] = IOUtils.toString(fisTargetFile, "UTF-8").split("\n");
            String distanceType = "EUC_2D";
            boolean started = false;
            for (int l = 0; l < fileContent.length; l++) {
                fileContent[l] = fileContent[l].replaceAll("\r", StringUtils.EMPTY);
                if (fileContent[l].equals("EOF")) {
                    break;
                }
                if (fileContent[l].startsWith("EDGE_WEIGHT_TYPE")) {
                    distanceType = fileContent[l].split(":")[1].trim();
                }
                if (started) {
                    String[] data = fileContent[l].split(" ");
                    Vertex vertex = new Vertex(Integer.valueOf(data[0]) - 1);
                    vertex.setX(Double.valueOf(data[1]));
                    vertex.setY(Double.valueOf(data[2]));
                    graph.addVertex(vertex);
                }
                if (fileContent[l].equals("NODE_COORD_SECTION")) {
                    started = true;
                }
            }
            Iterator<Vertex> iter1 = graph.getVertices();
            int count = 0;
            while (iter1.hasNext()) {
                Vertex i = iter1.next();
                Iterator<Vertex> iter2 = graph.getVertices();
                while (iter2.hasNext()) {
                    Vertex j = iter2.next();
                    if (!i.getId().equals(j.getId())) {
                        Edge edge = new Edge(count++);
                        edge.setFrom(i);
                        i.getAdj().put(j.getId(), edge);
                        edge.setTo(j);
                        if (distanceType.equals("EUC_2D")) {
                            edge.setCost(Maths.getTSPEuclideanDistance(i, j));
                        } else if (distanceType.equals("GEO")) {
                            edge.setCost(Maths.getTSPGeoDistance(i, j));
                        } else {
                            throw new RuntimeException("Distance type not defined!");
                        }
                        graph.addEdge(edge);
                    }
                }
            }
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Graph createGraphFromJoinville(File file) {
        try {
            Graph graph = new Graph();
            FileInputStream fisTargetFile = new FileInputStream(file);
            String fileContent[] = IOUtils.toString(fisTargetFile, "UTF-8").split("\n");
            boolean coordinates = false;
            boolean distances = false;
            int edgesId = 0;
            for (String line : fileContent) {
                line = line.replaceAll("\r", StringUtils.EMPTY);
                if (line.equals("EOF")) break;
                if (line.equals("Vertices (X Y)")) {
                    coordinates = true;
                    distances = false;
                    continue;
                }
                if (line.equals("Costs (N1 N2 COST)")) {
                    coordinates = false;
                    distances = true;
                    continue;
                }
                if (coordinates) {
                    String[] data = line.split(" ");
                    Vertex vertex = new Vertex(Integer.valueOf(data[0]));
                    vertex.setX(Double.valueOf(data[1]));
                    vertex.setY(Double.valueOf(data[2]));
                    graph.addVertex(vertex);
                }
                if (distances) {
                    String[] data = line.split(" ");
                    Edge edge = new Edge(edgesId++);
                    edge.setFrom(graph.getVertex(Integer.valueOf(data[0])));
                    edge.setTo(graph.getVertex(Integer.valueOf(data[1])));
                    edge.setCost(Double.valueOf(data[2]));
                    edge.getFrom().getAdj().put(edge.getToId(), edge);
                    graph.addEdge(edge);
                }
            }
            return graph;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
