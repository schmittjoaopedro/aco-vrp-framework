package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This is a data reader for reading the input data set that will be partitioned into k clusters.
 * <p>
 * Original version: https://github.com/ralucanecula/DVRPTW_ACS
 */
public class DataReader {

    private String fileName;

    private String rootDirectory;

    public DataReader(String rootDirectory, String fileName) {
        this.fileName = fileName;
        this.rootDirectory = rootDirectory;
    }

    /**
     * Read the data from the given file.
     *
     * @return the data from the file.
     */
    public VRPTW read() {
        VRPTW vrp = new VRPTW();
        vrp.setProblem(new Problem());
        vrp.setRequests(new ArrayList<>());
        vrp.setDynamicRequests(new ArrayList<>());
        vrp.setIdAvailableRequests(new ArrayList<>());
        // We are dealing with a input file with txt extension
        if (fileName.endsWith(".txt")) {
            boolean foundGeneralSection = false;
            boolean foundRequestsSection = false;
            File file = Paths.get(rootDirectory, fileName).toFile();
            if (file.exists()) {
                vrp.getProblem().setName(fileName);
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new FileReader(file));
                    String line = in.readLine();
                    while (line != null) {
                        if (foundGeneralSection) {
                            readGeneralRecord(line, vrp);
                            foundGeneralSection = false;
                        }
                        if (foundRequestsSection && !line.trim().equals("")) {
                            readRequestRecord(line, vrp.getRequests(), vrp.getDynamicRequests(), vrp.getIdAvailableRequests());
                        }
                        if (line.startsWith("NUMBER")) {
                            foundGeneralSection = true;
                        }
                        if (line.startsWith("CUST NO.")) {
                            foundRequestsSection = true;
                        }
                        line = in.readLine();
                    }
                    in.close();
                } catch (FileNotFoundException ignored) {
                } catch (IOException e) {
                    System.out.println("Error occurred while reading file: " + file + " " + e.getMessage());
                }
                int numRequests = vrp.getRequests().size();
                vrp.setN(numRequests - 1);
                setNodePoints(vrp, numRequests);
            }
        }
        return vrp;
    }

    private void setNodePoints(VRPTW vrp, int numRequests) {
        vrp.getProblem().setNodes(new Point[numRequests]);
        for (int i = 0; i < numRequests; i++) {
            vrp.getProblem().getNodes()[i] = new Point();
            Request r = vrp.getRequests().get(i);
            vrp.getProblem().getNodes()[i].setX(r.getxCoordinate());
            vrp.getProblem().getNodes()[i].setY(r.getyCoordinate());
        }
    }

    /**
     * Read the first section of the input file containing the size of the vehicle fleet (vehicle number)
     * and the capacity of each vehicle.
     *
     * @param line        string to extract the record from.
     * @param vrpInstance reference to the VRPTW instance to be populated with the extracted data.
     */
    private void readGeneralRecord(String line, VRPTW vrpInstance) {
        String[] strRecord = line.trim().split("\\s+");
        int[] values = new int[2];
        for (int i = 0; i < strRecord.length; i++) {
            try {
                values[i] = Integer.parseInt(strRecord[i]);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException " + e.getMessage() + " record=" + strRecord[i] + " line=" + line);
            }
        }
        vrpInstance.setNumVehicles(values[0]);
        vrpInstance.setCapacity(values[1]);
    }

    /**
     * Read the requests section of the input file containing the list of the customers' requests.
     *
     * @param line    string to extract the record from.
     * @param reqList reference to the list of requests to be populated with the extracted data.
     */
    private void readRequestRecord(String line, ArrayList<Request> reqList, ArrayList<Request> dynamicReqList, ArrayList<Integer> availableNodes) {
        String[] strRecord = line.trim().split("\\s+");
        int[] values = new int[8];
        int idCity;
        for (int i = 0; i < strRecord.length; i++) {
            try {
                values[i] = Integer.parseInt(strRecord[i]);

            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException " + e.getMessage() + " record=" + strRecord[i] + " line=" + line);
            }
        }
        Request req = new Request(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
        reqList.add(req);
        // We have a dynamic customer request
        if (values[7] > 0) {
            dynamicReqList.add(req);
        }
        // Keep separately the IDs of apriori requests/nodes which are available from the beginning, as part of DVRPTW instance
        else {
            // Exclude the depot from the list of available nodes, since it is known by default that it is available (known)
            // subtract 1, since the id/customer number of the first customer, except the depot, starts from 1.
            if (values[0] > 0) {
                idCity = values[0] - 1;
                availableNodes.add(idCity);
            }
        }
    }
}
