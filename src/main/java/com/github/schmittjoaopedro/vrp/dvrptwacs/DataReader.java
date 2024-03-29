package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This is a data reader for reading the input data set that will be partitioned into k clusters
 */
public class DataReader {

    //file name
    private String fileName = "R103.txt";

    private String rootDir;

    public DataReader(String rootDir, String fileName) {
        this.fileName = fileName;
        this.rootDir = rootDir;
    }

    //Read the data from the given file/
    public VRPTW read(Utilities utilities) {
        VRPTW vrp = new VRPTW(utilities);
        vrp.instance = new Problem();
        ArrayList<Request> reqList = new ArrayList<>();
        ArrayList<Request> dynamicReqList = new ArrayList<>();
        ArrayList<Integer> availableNodes = new ArrayList<>();
        //we are dealing with a input file with txt extension
        if (fileName.endsWith(".txt")) {
            boolean foundGeneralSection = false;
            boolean foundRequestsSection = false;
            File file = Paths.get(rootDir, fileName).toFile();
            BufferedReader in;
            if (file.exists()) {
                vrp.instance.name = fileName;
                try {
                    in = new BufferedReader(new FileReader(file));
                    String line = in.readLine();
                    while (line != null) {
                        if (foundGeneralSection) {
                            readGeneralRecord(line, vrp);
                            foundGeneralSection = false;
                        }
                        if (foundRequestsSection) {
                            if (!line.trim().equals(""))
                                readRequestRecord(line, reqList, dynamicReqList, availableNodes);
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
                vrp.setRequests(reqList);
                vrp.setDynamicRequests(dynamicReqList);
                vrp.setIdAvailableRequests(availableNodes);
                int nrCustomerRequests = reqList.size() - 1;
                vrp.n = nrCustomerRequests;
                vrp.instance.nodes = new Point[reqList.size()];
                for (int i = 0; i < reqList.size(); i++) {
                    vrp.instance.nodes[i] = new Point();
                    Request r = reqList.get(i);
                    vrp.instance.nodes[i].x = r.getxCoord();
                    vrp.instance.nodes[i].y = r.getyCoord();
                }
            }
        }
        return vrp;
    }

    /**
     * Read the first section of the input file containing the size of the vehicle fleet (vehicle number)
     * and the capacity of each vehicle
     *
     * @param line        string to extract the record from
     * @param vrpInstance reference to the VRPTW instance to be populated with the extracted data
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
        vrpInstance.setCapacity(values[1]);
    }

    /**
     * Read the requests section of the input file containing the list of the customers ' requests
     *
     * @param line    string to extract the record from
     * @param reqList reference to the list of requests to be populated with the extracted data
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
        //we have a dynamic customer request
        if (values[7] > 0) {
            dynamicReqList.add(req);
        }
        //keep separately the IDs of apriori requests/nodes which are available from the beginning, as part of DVRPTW instance
        else {
            //exclude the depot from the list of available nodes, since it is known by default that it is available (known)
            //substract 1, since the id/customer number of the first customer, except the depot, starts from 1
            if (values[0] > 0) {
                idCity = values[0] - 1;
                availableNodes.add(idCity);
            }
        }
    }

}
