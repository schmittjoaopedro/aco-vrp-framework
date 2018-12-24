package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.core.model.pdp.PDPModel;
import com.github.rinde.rinsim.core.model.pdp.Parcel;
import com.github.rinde.rinsim.core.model.road.RoadModel;
import com.github.rinde.rinsim.core.model.time.TimeLapse;
import com.github.schmittjoaopedro.rinsim.IdPoint;
import com.github.schmittjoaopedro.rinsim.Salesman;
import com.github.schmittjoaopedro.vrp.dvrptwacs.Ants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DebuggerUtils {

    public static void logSalesman(PDPModel pdpModel, Salesman salesman, TimeLapse time, Parcel nextParcel, int parcelId, boolean console, boolean logFile) {
        try {
            String msg = getSalesmanCurrentRoute(pdpModel, salesman, time, nextParcel, parcelId);
            msg += "\n";
            if (console) {
                System.out.print(msg);
            }
            if (logFile) {
                FileUtils.writeStringToFile(new File("C:\\Temp\\salesman-" + salesman.getId()), msg, true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @NotNull
    public static String getSalesmanCurrentRoute(PDPModel pdpModel, Salesman salesman, TimeLapse time, Parcel nextParcel, int parcelId) {
        String msg = "Salesman = " +
                StringUtils.rightPad(String.valueOf(salesman.getId()), 2, " ") +
                "\tParcel = " +
                StringUtils.rightPad(String.valueOf(parcelId), 3, " ") +
                "\tTimeWindow = [ " +
                StringUtils.leftPad(String.valueOf(nextParcel.getDeliveryTimeWindow().begin()), 2, " ") +
                " , " +
                StringUtils.leftPad(String.valueOf(nextParcel.getDeliveryTimeWindow().end()), 2, " ") +
                " ]\tBeginService = " +
                StringUtils.leftPad(String.format(Locale.US, "%.2f", salesman.getBeginService()[parcelId]), 6, " ") +
                "\tCurrentTime = " +
                StringUtils.rightPad(String.valueOf(time.getTime()), 3, " ") +
                "\tRoute = ";
        for (Parcel parcel : salesman.getRoute()) {
            long id = ((IdPoint) parcel.getDeliveryLocation()).id;
            msg += pdpModel.getParcelState(parcel).isPickedUp() ? "*" : " ";
            msg += StringUtils.leftPad(String.valueOf(id), 2, " ");
            msg += pdpModel.getParcelState(parcel).isDelivered() ? "*" : " ";
            if (!salesman.getRoute().get(salesman.getRoute().size() - 1).equals(parcel)) {
                msg += ",";
            }
        }
        return msg;
    }

    public static void compareRoutes(RoadModel roadModel, Ants ants) {
        // Compare routes
        List<Salesman> salesmen = roadModel.getObjectsOfType(Salesman.class).stream().collect(Collectors.toList());
        salesmen.sort(Comparator.comparing(Salesman::getId));
        for (int i = 0; i < ants.bestSoFarAnt.tours.size(); i++) {
            int salesmanSize = salesmen.get(i).getRoute().size();
            int antSize = ants.bestSoFarAnt.tours.get(i).size();
            if (salesmanSize != antSize - 2) {
                throw new RuntimeException("Error on route size");
            }
            for (int j = 0; j < salesmen.get(i).getRoute().size(); j++) {
                int parcelId = ((IdPoint) salesmen.get(i).getRoute().get(j).getDeliveryLocation()).id;
                int tourId = ants.bestSoFarAnt.tours.get(i).get(j + 1) + 1;
                if (parcelId != tourId) {
                    throw new RuntimeException("Error on route node");
                }
            }
        }
    }

    public static void printAllRoutesAndDeliveries(RoadModel roadModel, PDPModel pdpModel, Set<Parcel> knowParcels, boolean logConsole) {
        List<Salesman> salesmen = roadModel.getObjectsOfType(Salesman.class).stream().collect(Collectors.toList());
        Map<Integer, Integer> parcelsNumber = new HashMap<>();
        salesmen.sort(Comparator.comparing(Salesman::getId));
        int parcelsInSalesmen = 0;
        for (Salesman salesman : salesmen) {
            String msg = "Salesman " + salesman.getId() + "\t";
            for (Parcel parcel : salesman.getRoute()) {
                int parcelId = ((IdPoint) parcel.getDeliveryLocation()).id;
                if (pdpModel.getParcelState(parcel).isPickedUp()) {
                    msg += "*";
                } else {
                    msg += " ";
                }
                msg += parcelId - 1;
                if (pdpModel.getParcelState(parcel).isDelivered()) {
                    msg += "*";
                } else {
                    msg += " ";
                }
                msg += " -> ";
                parcelsInSalesmen++;
                if (!parcelsNumber.containsKey(parcelId)) {
                    parcelsNumber.put(parcelId, 1);
                } else {
                    parcelsNumber.put(parcelId, 1 + parcelsNumber.get(parcelId));
                }
            }
            if (logConsole) {
                System.out.println(msg);
            }
        }
        if (parcelsInSalesmen != knowParcels.size()) {
            throw new RuntimeException("Invalid numbers of parcels in salesman related to the known parcels");
        }
        for (Map.Entry<Integer, Integer> parcelNumber : parcelsNumber.entrySet()) {
            if (parcelNumber.getValue() > 1) {
                throw new RuntimeException("Duplicated parcel in different vehicles " + parcelNumber.getKey());
            }
        }
    }

    public static void logRinSimRouteTest(Map<String, List<String>> salesmenRoutes) {
        for (Map.Entry<String, List<String>> salesman : salesmenRoutes.entrySet()) {
            String key = salesman.getKey();
            System.out.println("lineCount = 0;");
            for (String line : salesman.getValue()) {
                System.out.println("assertThat(salesmenRoutes.get(\"" + key + "\").get(lineCount++)).isEqualTo(\"" + line + "\");");
            }
        }
    }
}
