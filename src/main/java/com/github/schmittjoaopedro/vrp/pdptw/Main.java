package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.pdptw.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    /*private static final String[] instances = {"lc101"};*/

    private static final String[] instances = {
            "lc101", "lc102", "lc103", "lc104", "lc105", "lc106", "lc107", "lc108",
            "lc109", "lc201", "lc202", "lc203", "lc204", "lc205", "lc206", "lc207",
            "lc208", "lr101", "lr102", "lr103", "lr104", "lr105", "lr106", "lr107",
            "lr108", "lr109", "lr110", "lr111", "lr112", "lr201", "lr202", "lr203",
            "lr204", "lr205", "lr206", "lr207", "lr208", "lr209", "lr210", "lr211",
            "lrc101", "lrc102", "lrc103", "lrc104", "lrc105", "lrc106", "lrc107", "lrc108",
            "lrc201", "lrc202", "lrc203", "lrc204", "lrc205", "lrc206", "lrc207", "lrc208"
    };

    public static void main(String[] args) {
        for (String name : instances) {
            // input problem data
            System.out.println("\n===============================");
            System.out.println("Starting problem " + name);
            File customers = new File("C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdp_100\\" + name + ".txt");
            FileReader customersInput;
            BufferedReader customersBR;

            VehicleProperty vp = null;
            try {
                Customers.reset();
                DTTable.reset();
                Tabu.reset();

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
                Tabu.init(Customers.getSingleInstance().size());

                customersBR.close();
                customersInput.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (Customers.getSingleInstance().size() != 0 && DTTable.getSingleInstance().size() != 0) {
                Overall overall = new Overall(name, vp);
                overall.run();
            } else {
                System.out.println("data input error!");
            }
        }
    }
}
