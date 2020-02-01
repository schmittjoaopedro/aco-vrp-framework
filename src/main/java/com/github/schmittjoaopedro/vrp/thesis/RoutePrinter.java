package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.algorithms.operators.insertion.RouteTimes;
import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class RoutePrinter {

    private double maxX = Double.MIN_VALUE;

    private double maxY = Double.MIN_VALUE;

    private double minX = Double.MAX_VALUE;

    private double minY = Double.MAX_VALUE;

    private int width;

    private int height;

    private String folderPath;

    private Color idle = Color.black;

    private Color transition = Color.blue;

    private Color committed = Color.green;

    public RoutePrinter(Instance instance, String folderPath, int width, int height) {
        this.folderPath = folderPath;
        this.width = width;
        this.height = height;
        // Get map bounds
        for (Task task : instance.tasks) {
            double xCoord = task.x;
            double yCoord = task.y;
            maxX = Math.max(maxX, xCoord);
            maxY = Math.max(maxY, yCoord);
            minX = Math.min(minX, xCoord);
            minY = Math.min(minY, yCoord);
        }
        maxX = Math.max(maxX, instance.depot.x);
        maxY = Math.max(maxY, instance.depot.y);
        minX = Math.min(minX, instance.depot.x);
        minY = Math.min(minY, instance.depot.y);
    }

    public void printRoute(Instance instance, Solution solution, int iteration) {
        double margin = 20;
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
            for (int k = 0; k < solution.tours.size(); k++) {
                RouteTimes routeTimes = new RouteTimes(solution.tours.get(k), instance);
                for (int i = 0; i < solution.tours.get(k).size() - 1; i++) {
                    Color color;
                    Task task = null;
                    if (i == 0) {
                        xCoordSource = instance.depot.x;
                        yCoordSource = instance.depot.y;
                    } else {
                        task = instance.getTask(solution.tours.get(k).get(i));
                        xCoordSource = task.x;
                        yCoordSource = task.y;
                    }
                    if (instance.isDepot(solution.tours.get(k).get(i + 1))) {
                        xCoordTarget = instance.depot.x;
                        yCoordTarget = instance.depot.y;
                        color = idle;
                        double distToDepot = instance.dist(solution.tours.get(k).get(i), solution.tours.get(k).get(i + 1));
                        if (instance.depot.twEnd - distToDepot < instance.currentTime) {
                            color = transition;
                        }
                        if (iteration == 25000) {
                            color = committed;
                            drawTruck(width, height, margin, maxX, maxY, indexHtml, xCoordTarget, yCoordTarget, xCoordTarget, yCoordTarget, routeTimes.departureTime[i], routeTimes.arrivalTime[i + 1], instance.currentTime);
                        } else if (task.isCommitted()) {
                            if (instance.depot.twEnd - distToDepot > instance.currentTime) {
                                drawTruck(width, height, margin, maxX, maxY, indexHtml, xCoordSource, yCoordSource, xCoordSource, yCoordSource, routeTimes.departureTime[i], routeTimes.arrivalTime[i + 1], instance.currentTime);
                            } else {
                                drawTruck(width, height, margin, maxX, maxY, indexHtml, xCoordSource, yCoordSource, xCoordTarget, yCoordTarget, instance.depot.twEnd - distToDepot, instance.depot.twEnd, instance.currentTime);
                            }
                        }
                    } else {
                        task = instance.getTask(solution.tours.get(k).get(i + 1));
                        xCoordTarget = task.x;
                        yCoordTarget = task.y;
                        switch (task.status) {
                            case Transition:
                                color = transition;
                                drawTruck(width, height, margin, maxX, maxY, indexHtml, xCoordSource, yCoordSource, xCoordTarget, yCoordTarget, routeTimes.departureTime[i], routeTimes.arrivalTime[i + 1], instance.currentTime);
                                break;
                            case Committed:
                                color = committed;
                                break;
                            default:
                                color = idle;
                                break;
                        }
                    }
                    indexHtml.append("\n\t<line ");
                    if (color == idle) {
                        indexHtml.append("stroke-dasharray=\"2, 2\" ");
                    }
                    indexHtml.append("x1=\"");
                    indexHtml.append(((width / maxX) * xCoordSource) + margin);
                    indexHtml.append("\" y1=\"");
                    indexHtml.append(((height / maxY) * yCoordSource) + margin);
                    indexHtml.append("\" x2=\"");
                    indexHtml.append(((width / maxX) * xCoordTarget) + margin);
                    indexHtml.append("\" y2=\"");
                    indexHtml.append(((height / maxY) * yCoordTarget) + margin);
                    indexHtml.append("\" style=\"stroke:rgb(");
                    indexHtml.append(color.getRed()).append(",").append(color.getGreen()).append(",").append(color.getBlue());
                    indexHtml.append(");stroke-width:2\" />"); // without arrow
                    //indexHtml.append(");stroke-width:2\" marker-end=\"url(#arrow)\" />"); // with arrow
                }
            }
            for (Request req : instance.requests) {
                if (req.pickupTask.x != req.deliveryTask.x || req.pickupTask.y != req.deliveryTask.y) {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.pickupTask.x, req.pickupTask.y, "red", String.valueOf(req.requestId));
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.deliveryTask.x, req.deliveryTask.y, "blue", String.valueOf(req.requestId));
                } else {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.pickupTask.x, req.pickupTask.y, "gray", String.valueOf(req.requestId));
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.deliveryTask.x, req.deliveryTask.y, "gray", String.valueOf(req.requestId));
                }
            }
            drawNode(width, height, margin, maxX, maxY, indexHtml, instance.depot.x, instance.depot.y, "black", "0");
            indexHtml.append("\n</svg>");
            FileUtils.writeStringToFile(Paths.get(folderPath, instance.name + "-" + iteration + ".html").toFile(), indexHtml.toString(), "UTF-8");
        } catch (IOException e) {
        }
    }

    public void staticPrintRoute(Instance instance, Solution solution) {
        double margin = 5;
        // Draw html
        try {
            StringBuilder indexHtml = new StringBuilder();
            indexHtml.append("<svg width=\"").append(width + margin * 2).append("\" height=\"").append(height + margin * 2).append("\">");
            indexHtml.append("\t<defs>\n");
            indexHtml.append("\t\t<marker id=\"arrow\" markerWidth=\"10\" markerHeight=\"10\" refX=\"0\" refY=\"3\" orient=\"auto\" markerUnits=\"strokeWidth\">\n");
            indexHtml.append("\t\t\t<path d=\"M0,0 L0,6 L9,3 z\" fill=\"#f00\" />\n");
            indexHtml.append("\t\t</marker>\n");
            indexHtml.append("\t</defs>");
            for (Request req : instance.requests) {
                if (req.pickupTask.x != req.pickupTask.x || req.deliveryTask.y != req.deliveryTask.x) {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.pickupTask.x, req.pickupTask.y, "red", String.valueOf(req.requestId));
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.deliveryTask.x, req.deliveryTask.y, "blue", String.valueOf(req.requestId));
                } else {
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.pickupTask.x, req.pickupTask.y, "gray", String.valueOf(req.requestId));
                    drawNode(width, height, margin, maxX, maxY, indexHtml, req.deliveryTask.x, req.deliveryTask.y, "gray", String.valueOf(req.requestId));
                }
            }
            drawNode(width, height, margin, maxX, maxY, indexHtml, instance.depot.x, instance.depot.y, "black", "0");
            double xCoordSource, yCoordSource, xCoordTarget, yCoordTarget;
            for (int k = 0; k < solution.tours.size(); k++) {
                Color color = new Color((int) (Math.random() * 255.0), (int) (Math.random() * 255.0), (int) (Math.random() * 255.0));
                for (int i = 0; i < solution.tours.get(k).size() - 1; i++) {
                    Task task;
                    if (i == 0) {
                        xCoordSource = instance.depot.x;
                        yCoordSource = instance.depot.y;
                    } else {
                        task = instance.getTask(solution.tours.get(k).get(i));
                        xCoordSource = task.x;
                        yCoordSource = task.y;
                    }
                    if (instance.isDepot(solution.tours.get(k).get(i + 1))) {
                        xCoordTarget = instance.depot.x;
                        yCoordTarget = instance.depot.y;
                    } else {
                        task = instance.getTask(solution.tours.get(k).get(i + 1));
                        xCoordTarget = task.x;
                        yCoordTarget = task.y;
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
                    indexHtml.append(color.getRed()).append(",").append(color.getGreen()).append(",").append(color.getBlue());
                    indexHtml.append(");stroke-width:2\" />"); // without arrow
                    //indexHtml.append(");stroke-width:2\" marker-end=\"url(#arrow)\" />"); // with arrow
                }
            }
            indexHtml.append("\n</svg>");
            FileUtils.writeStringToFile(Paths.get(folderPath, instance.name + ".html").toFile(), indexHtml.toString(), "UTF-8");
        } catch (IOException e) {
        }
    }

    private static void drawTruck(int width, int height, double margin, double maxX, double maxY, StringBuilder indexHtml,
                                  double xSource, double ySource, double xTarget, double yStart,
                                  double minTime, double maxTime, double currentTime) {
        double timeFactor = Math.min(1.0, (currentTime - minTime) / (maxTime - minTime));
        double xS = ((width / maxX) * xSource) + margin;
        double yS = ((height / maxY) * ySource) + margin;
        double xT = ((width / maxX) * xTarget) + margin;
        double yT = ((height / maxY) * yStart) + margin;
        double x = (xS + (xT - xS) * timeFactor);
        double y = (yS + (yT - yS) * timeFactor);
        indexHtml.append("\n\t<image xlink:href=\"../truck.png\" height=\"40\" width=\"40\" x=\"");
        indexHtml.append(x - 20.0);
        indexHtml.append("\" y=\"");
        indexHtml.append(y - 20.0);
        indexHtml.append("\" />");
    }

    private static void drawNode(int width, int height, double margin, double maxX, double maxY, StringBuilder indexHtml, double xCoord, double yCoord, String color, String text) {
        double x = ((width / maxX) * xCoord) + margin;
        double y = ((height / maxY) * yCoord) + margin;
        indexHtml.append("\n\t<circle cx=\"");
        indexHtml.append(x);
        indexHtml.append("\" cy=\"");
        indexHtml.append(y);
        indexHtml.append("\" r=\"4\" fill=\"" + color + "\" />");
        indexHtml.append("<text x=\"" + x + "\" y=\"" + y + "\" fill=\"black\">" + text + "</text>");
    }

}
