package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.pdptw.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        // input problem data
        File customers = new File("C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdp_100\\lc101.txt");
        FileReader customersInput;
        BufferedReader customersBR;

        VehicleProperty vp = null;
        try {
            customersInput = new FileReader(customers);
            customersBR = new BufferedReader(customersInput);

            String s;
            String[] data;
            // set up vehicleProperty
            if ((s = customersBR.readLine()) != null) {
                data = s.split("\t");
                vp = new VehicleProperty(Integer.parseInt(data[0]), Integer.parseInt(data[1]));        // set totalVehicles,capacity
            }

            // set up Customers
            while ((s = customersBR.readLine()) != null) {
                data = s.split("\t");
                Customers.getSingleInstance().add(new Customer(Integer.parseInt(data[0]), Integer.parseInt(data[3]), Integer.parseInt(data[6]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            }

            // set up DTTable
            for (int i = 0; i < Customers.getSingleInstance().size(); i++) {
                for (int j = i + 1; j < Customers.getSingleInstance().size(); j++) {
                    Key k = new Key(j, i);
                    int Xi = Customers.getSingleInstance().get(i).getX();
                    int Yi = Customers.getSingleInstance().get(i).getY();
                    int Xj = Customers.getSingleInstance().get(j).getX();
                    int Yj = Customers.getSingleInstance().get(j).getY();
                    double distance = Math.sqrt(Math.pow(Xi - Xj, 2) + Math.pow(Yi - Yj, 2));

                    Value v = new Value(distance, distance);
                    DTTable.getSingleInstance().put(k, v);
                }
            }
            DTTable.getSingleInstance().compile();

            customersBR.close();
            customersInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (Customers.getSingleInstance().size() != 0 && DTTable.getSingleInstance().size() != 0) {
            Long time = System.currentTimeMillis();
            Overall overall = new Overall(vp);
            overall.run();
            System.out.println("Time: " + ((System.currentTimeMillis() - time) / 1000.0));
        } else {
            System.out.println("data input error!");
        }
    }
}
