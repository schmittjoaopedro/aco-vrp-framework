package com.github.schmittjoaopedro.vrp.mpdptw;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

public class DynamicInstancesGenerator {

    private static final String outputFolder = "C:\\Temp\\dpdptw";

    private static Random random = new Random(1);

    public static void generateDynamicInstances(ProblemInstance instance) {
        double[] varyingTimes = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        double[] varyingRequests = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};

        for (double a : varyingTimes) {
            generateDataSetP1(instance, a);
        }
        for (int i = 0; i < 10; i++) {
            for (double q : varyingRequests) {
                generateDataSetP2(instance, q, i);
            }
        }
    }

    private static void generateDataSetP1(ProblemInstance instance, double a) {
        // Generate data-set P1
        for (Request req : instance.getRequests()) {
            // Generate dynamism for pickup requests only
            if (req.isPickup) {
                Request delivery = instance.getDelivery(req.requestId);
                // tr_latest := min {t_close(vr+), t_close(vr-) - t(vr+, vr-) - t_service(vr+)} - t(v_home, vr+)
                req.announceTime = Math.min(req.twEnd, delivery.twEnd - instance.dist(req.nodeId, delivery.nodeId) - req.serviceTime) -
                        instance.dist(instance.getDepot().nodeId, req.nodeId);
                req.announceTime = a * req.announceTime;
                delivery.announceTime = req.announceTime;
            }
        }
        generateInstanceFile(instance, "_a_" + a);
    }

    private static void generateDataSetP2(ProblemInstance instance, double q, int sample) {
        // Generate data-set P1
        for (Request req : instance.getRequests()) {
            // Generate dynamism for pickup requests only
            if (req.isPickup) {
                Request delivery = instance.getDelivery(req.requestId);
                // tr_latest := min {t_close(vr+), t_close(vr-) - t(vr+, vr-) - t_service(vr+)} - t(v_home, vr+)
                req.announceTime = Math.min(req.twEnd, delivery.twEnd - instance.dist(req.nodeId, delivery.nodeId) - req.serviceTime) -
                        instance.dist(instance.getDepot().nodeId, req.nodeId);
                if (random.nextDouble() <= q) {
                    req.announceTime = 0;
                    delivery.announceTime = 0;
                } else {
                    delivery.announceTime = req.announceTime;
                }
            }
        }
        generateInstanceFile(instance, "_q_" + sample + "_" + q);
    }

    private static void generateInstanceFile(ProblemInstance instance, String suffix) {
        StringBuilder fileContent = new StringBuilder();
        // Header data
        fileContent.append(String.format("%d\t%d\t1\r\n", instance.getNumMaxVehicles(), (int) instance.getVehicleCapacity()));
        // Add depot
        Depot depot = instance.getDepot();
        fileContent.append(String.format("0\t%d\t%d\t0\t%d\t%d\t0\t0\t0\r\n", (int) depot.x, (int) depot.y, (int) depot.twStart, (int) depot.twEnd));
        // Add requests
        for (Request req : instance.getRequests()) {
            int pickupNode = 0;
            int deliveryNode = 0;
            if (req.isPickup) {
                deliveryNode = instance.getDelivery(req.requestId).nodeId;
            } else {
                pickupNode = instance.getPickups(req.requestId).get(0).nodeId;
            }
            fileContent.append(String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\r\n", req.nodeId, (int) req.x, (int) req.y, (int) req.demand,
                    (int) req.twStart, (int) req.twEnd, (int) req.serviceTime, pickupNode, deliveryNode, (int) req.announceTime));
        }
        fileContent.append("\r\n");
        try {
            String fileName = instance.getFileName().split("\\.")[0] + suffix + ".txt";
            FileUtils.writeStringToFile(Paths.get(outputFolder, fileName).toFile(), fileContent.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getRandom(double upperBound) {
        return random.nextDouble() * upperBound;
    }

}
