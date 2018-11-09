package com.github.schmittjoaopedro.vrp.dvrptwacs;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Utilities {

    private Random random;

    //auxiliary routine for sorting an integer array
    public void swap2(double v[], int v2[], int i, int j) {
        double tmp1;
        int tmp2;
        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;
        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }

    //recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
    public void sort2(double v[], int v2[], int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap2(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap2(v, v2, ++last, k);
        swap2(v, v2, left, last);
        sort2(v, v2, left, last);
        sort2(v, v2, last + 1, right);
    }

    //generate a random number that is uniformly distributed in [0,1]
    public double random01() {
        if (random == null) {
            random = new Random();
        }
        return random.nextDouble();
    }

    public void setSeed(int seed) {
        random = new Random(seed);
    }

    public void plotResult(VRPTW vrptw, Ant bestSoFarAnt, int width, int height) {
        // Valid colors
        Map<Integer, String> colors = new HashMap<>();
        colors.put(0, "0,0,0");
        colors.put(1, "255,0,0");
        colors.put(2, "0,255,0");
        colors.put(3, "0,0,255");
        colors.put(4, "255,255,0");
        colors.put(5, "0,255,255");
        colors.put(6, "255,0,255");
        colors.put(7, "192,192,192");
        colors.put(8, "128,128,128");
        colors.put(9, "128,0,0");
        colors.put(10, "128,128,0");
        colors.put(11, "0,128,0");
        colors.put(12, "128,0,128");
        colors.put(13, "0,128,128");
        colors.put(14, "0,0,128");
        colors.put(15, "255,127,80");
        colors.put(16, "255,69,0");
        colors.put(17, "124,252,0");
        colors.put(18, "47,79,79");
        colors.put(19, "25,25,112");
        // Get map bounds
        double margin = 5;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        List<Integer> requests = vrptw.getRequests().stream().map(req -> req.getId()).collect(Collectors.toList());
        for (int idRequest : requests) {
            double xCoord = vrptw.getRequests().get(idRequest).getxCoord();
            double yCoord = vrptw.getRequests().get(idRequest).getyCoord();
            maxX = Math.max(maxX, xCoord);
            maxY = Math.max(maxY, yCoord);
            minX = Math.min(minX, xCoord);
            minY = Math.min(minY, yCoord);
        }
        // Draw html
        List<Integer> dynamicRequests = vrptw.getDynamicRequests().stream().map(req -> req.getId()).collect(Collectors.toList());
        try {
            StringBuilder indexHtml = new StringBuilder();
            indexHtml.append("<svg width=\"").append(width + margin * 2).append("\" height=\"").append(height + margin * 2).append("\">");
            indexHtml.append("\t<defs>\n");
            indexHtml.append("\t\t<marker id=\"arrow\" markerWidth=\"10\" markerHeight=\"10\" refX=\"0\" refY=\"3\" orient=\"auto\" markerUnits=\"strokeWidth\">\n");
            indexHtml.append("\t\t\t<path d=\"M0,0 L0,6 L9,3 z\" fill=\"#f00\" />\n");
            indexHtml.append("\t\t</marker>\n");
            indexHtml.append("\t</defs>");
            for (int idRequest : requests) {
                double xCoord = vrptw.getRequests().get(idRequest).getxCoord();
                double yCoord = vrptw.getRequests().get(idRequest).getyCoord();
                indexHtml.append("\n\t<circle cx=\"");
                indexHtml.append(((width / maxX) * xCoord) + margin);
                indexHtml.append("\" cy=\"");
                indexHtml.append(((height / maxY) * yCoord) + margin);
                if (dynamicRequests.contains(idRequest)) {
                    indexHtml.append("\" r=\"4\" fill=\"red\" />");
                } else {
                    indexHtml.append("\" r=\"4\" fill=\"black\" />");
                }
            }
            for (int k = 0; k < bestSoFarAnt.usedVehicles; k++) {
                for (int i = 0; i < bestSoFarAnt.tours.get(k).size() - 1; i++) {
                    double xCoordSource = vrptw.getRequests().get(bestSoFarAnt.tours.get(k).get(i) + 1).getxCoord();
                    double yCoordSource = vrptw.getRequests().get(bestSoFarAnt.tours.get(k).get(i) + 1).getyCoord();
                    double xCoordTarget = vrptw.getRequests().get(bestSoFarAnt.tours.get(k).get(i + 1) + 1).getxCoord();
                    double yCoordTarget = vrptw.getRequests().get(bestSoFarAnt.tours.get(k).get(i + 1) + 1).getyCoord();
                    indexHtml.append("\n\t<line x1=\"");
                    indexHtml.append(((width / maxX) * xCoordSource) + margin);
                    indexHtml.append("\" y1=\"");
                    indexHtml.append(((height / maxY) * yCoordSource) + margin);
                    indexHtml.append("\" x2=\"");
                    indexHtml.append(((width / maxX) * xCoordTarget) + margin);
                    indexHtml.append("\" y2=\"");
                    indexHtml.append(((height / maxY) * yCoordTarget) + margin);
                    indexHtml.append("\" style=\"stroke:rgb(");
                    indexHtml.append(colors.get(k));
                    indexHtml.append(");stroke-width:2\" />"); // without arrow
                    //indexHtml.append(");stroke-width:2\" marker-end=\"url(#arrow)\" />"); // with arrow
                }
            }
            indexHtml.append("\n</svg>");
            FileUtils.writeStringToFile(new File("C:\\temp\\index.html"), indexHtml.toString(), "UTF-8");
        } catch (IOException e) {
        }
    }

}
