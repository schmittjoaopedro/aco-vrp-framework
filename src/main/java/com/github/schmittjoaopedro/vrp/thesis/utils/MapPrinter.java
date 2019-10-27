package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MapPrinter {

    public static void printResult(Solution bestSoFar, Instance instance, int width, int height, String filename) {
        double margin = 20;
        // Get map bounds
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE, minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        for (Task request : instance.tasks) {
            double xCoord = request.x;
            double yCoord = request.y;
            maxX = Math.max(maxX, xCoord);
            maxY = Math.max(maxY, yCoord);
            minX = Math.min(minX, xCoord);
            minY = Math.min(minY, yCoord);
        }
        // Draw html
        try {
            StringBuilder indexHtml = new StringBuilder();
            indexHtml.append("<svg width=\"").append(width + margin * 2).append("\" height=\"").append(height + margin * 2).append("\">");
            indexHtml.append("\t<defs>\n");
            indexHtml.append("\t\t<marker id=\"arrow\" markerWidth=\"10\" markerHeight=\"10\" refX=\"0\" refY=\"3\" orient=\"auto\" markerUnits=\"strokeWidth\">\n");
            indexHtml.append("\t\t\t<path d=\"M0,0 L0,6 L9,3 z\" fill=\"#f00\" />\n");
            indexHtml.append("\t\t</marker>\n");
            indexHtml.append("\t</defs>");
            double xCoordSource, yCoordSource, xCoordTarget, yCoordTarget;
            if (bestSoFar != null) {
                for (int k = 0; k < bestSoFar.tours.size(); k++) {
                    int r = (int) (Math.random() * 255);
                    int g = (int) (Math.random() * 255);
                    int b = (int) (Math.random() * 255);
                    for (int i = 0; i < bestSoFar.tours.get(k).size() - 1; i++) {
                        if (i == 0) {
                            xCoordSource = instance.depot.x;
                            yCoordSource = instance.depot.y;
                        } else {
                            xCoordSource = instance.tasks[bestSoFar.tours.get(k).get(i) - 1].x;
                            yCoordSource = instance.tasks[bestSoFar.tours.get(k).get(i) - 1].y;
                        }
                        if (i == bestSoFar.tours.get(k).size() - 2) {
                            xCoordTarget = instance.depot.x;
                            yCoordTarget = instance.depot.y;
                        } else {
                            xCoordTarget = instance.tasks[bestSoFar.tours.get(k).get(i + 1) - 1].x;
                            yCoordTarget = instance.tasks[bestSoFar.tours.get(k).get(i + 1) - 1].y;
                        }
                        indexHtml.append("\n\t<line x1=\"");
                        indexHtml.append(((width / maxX) * xCoordSource) + margin);
                        indexHtml.append("\" y1=\"");
                        indexHtml.append(((height / maxY) * yCoordSource) + margin);
                        indexHtml.append("\" x2=\"");
                        indexHtml.append(((width / maxX) * xCoordTarget) + margin);
                        indexHtml.append("\" y2=\"");
                        indexHtml.append(((height / maxY) * yCoordTarget) + margin);
                        indexHtml.append("\" style=\"stroke:rgb(");
                        indexHtml.append(r).append(",").append(g).append(",").append(b);
                        indexHtml.append(");stroke-width:2\" />"); // without arrow
                        //indexHtml.append(");stroke-width:2\" marker-end=\"url(#arrow)\" />"); // with arrow
                    }
                }
            }
            for (Task request : instance.tasks) {
                if (request.isPickup) {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, request.x, request.y, "red", String.valueOf(request.nodeId));
                } else {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, request.x, request.y, "blue", String.valueOf(request.nodeId));
                }
            }
            drawNode(width, height, margin, maxX, maxY, indexHtml, instance.depot.x, instance.depot.y, "black", "0");
            indexHtml.append("\n</svg>");
            FileUtils.writeStringToFile(new File("C:\\Temp\\" + filename + ".html"), indexHtml.toString(), "UTF-8");
        } catch (IOException e) {
        }
    }

    private static void drawNode(int width, int height, double margin, double maxX, double maxY, StringBuilder indexHtml, double xCoord, double yCoord, String color, String text) {
        double x = ((width / maxX) * xCoord) + margin;
        double y = ((height / maxY) * yCoord) + margin;
        indexHtml.append("\n\t<circle cx=\"");
        indexHtml.append(x);
        indexHtml.append("\" cy=\"");
        indexHtml.append(y);
        indexHtml.append("\" r=\"5\" fill=\"" + color + "\" />");
        indexHtml.append("<text x=\"" + (x - 14) + "\" y=\"" + (y - 8) + "\" fill=\"black\" font-size=\"28\" font-family=\"Sans,Arial\">" + text + "</text>");
    }

}
