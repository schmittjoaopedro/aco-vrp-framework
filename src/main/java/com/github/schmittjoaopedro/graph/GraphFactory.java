package com.github.schmittjoaopedro.graph;

import com.github.schmittjoaopedro.utils.Maths;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public final class GraphFactory {

    public static Graph createGraphFromTSP(File file) {
        try {
            Graph graph = new Graph();
            FileInputStream fisTargetFile = new FileInputStream(file);
            String fileContent[] = IOUtils.toString(fisTargetFile, "UTF-8").split("\n");
            boolean started = false;
            for (int l = 0; l < fileContent.length; l++) {
                if (fileContent[l].equals("EOF")) {
                    break;
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
                        edge.setCost(Maths.getTSPEuclideanDistance(i, j));
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

}
