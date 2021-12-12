package com.github.schmittjoaopedro.vrp.runners;

import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Request;
import com.github.schmittjoaopedro.vrp.mpdptw.Solution;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.LinkedList;

public class ExactSolver {

    private static String rootDirectory;

    private static String bestSolution = null;

    private static ProblemInstance inst;

    private static double bestCost = Double.MAX_VALUE;

    static {
        try {
            rootDirectory = Paths.get(ExactSolver.class.getClassLoader().getResource("mpdptw").toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        inst = DataReader.getMpdptwInstance(Paths.get(rootDirectory, "w_8_25_3.txt").toFile());
        LinkedList<Integer> route = new LinkedList<>();
        route.addLast(0);
        buildNextNode(new boolean[inst.getNumNodes()], 0, 0.0, 0.0, 0.0, route);
    }

    private static void buildNextNode(boolean[] visited, int prev, double currentTime, double cost, double demand, LinkedList<Integer> route) {
        Request req;
        boolean infeasible = false;
        for (int i = 1; i < inst.getNumNodes(); i++) {
            req = inst.getRequest(i);
            if (canBeVisited(visited, req)) {
                double newCost = cost + inst.dist(prev, i);
                double newCurrentTime = currentTime + inst.dist(prev, i);
                newCurrentTime = Math.max(newCurrentTime, inst.twStart(i));
                if (newCurrentTime > inst.twEnd(i)) {
                    infeasible = true;
                    break;
                }
                double newDemand = demand + inst.demand(i);
                if (newDemand > inst.getVehicleCapacity()) {
                    infeasible = true;
                    break;
                }
                newCurrentTime += inst.serviceTime(i);
                visited[i] = true;
                route.addLast(i);
                buildNextNode(visited, i, newCurrentTime, newCost, newDemand, route);
                route.removeLast();
                visited[i] = false;
            }
        }

        double newCost = cost + inst.dist(prev, 0);
        double newCurrentTime = currentTime + inst.dist(prev, 0);
        newCurrentTime = Math.max(newCurrentTime, inst.twStart(0));
        if (newCurrentTime > inst.twEnd(0)) {
            return;
        }
        double newDemand = demand + inst.demand(0);
        if (newDemand > inst.getVehicleCapacity()) {
            return;
        }
        newCurrentTime += inst.serviceTime(0);

        int status = areAllRequestsVisited(visited);
        if (infeasible) {
            System.out.println(StringUtils.join(route.toArray(), ' '));
        }
        if (infeasible && status == 1) {
            route.addLast(0);
            buildNextNode(visited, 0, newCurrentTime, newCost, demand, route);
            route.removeLast();
        } else if (status == 2) {
            if (newCost < bestCost) {
                bestCost = newCost;
                bestSolution = StringUtils.join(route.toArray(), ' ');
                System.out.println(bestCost + " = " + bestSolution);
            }
        }
    }

    private static boolean canBeVisited(boolean[] visited, Request req) {
        if (!visited[req.nodeId]) {
            if (req.isDeliver) {
                for (Request pickup : inst.getPickups(req.requestId)) {
                    if (!visited[pickup.nodeId]) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static int areAllRequestsVisited(boolean[] visited) {
        Request req;
        int allVisited = 2;
        for (int i = 1; i < inst.getNumNodes(); i++) {
            req = inst.getRequest(i);
            if (visited[i]) {
                for (Request pickup : inst.getPickups(req.requestId)) {
                    if (!visited[pickup.nodeId]) {
                        return 0;
                    }
                }
                if (!visited[inst.getDelivery(req.requestId).nodeId]) {
                    return 0;
                }
            } else {
                allVisited = 1;
            }
        }
        return allVisited;
    }

}
