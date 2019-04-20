package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.vrp.pdptw.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        // input problem data
        File customers = new File("C:\\projects\\aco-vrp-framework\\src\\main\\resources\\pdp_100\\lr209.txt");
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

            /*Solution s = new Solution();
            Route r1 = new Route();
            Route r2 = new Route();
            Route r3 = new Route();
            Route r4 = new Route();
            Route r5 = new Route();
            Route r6 = new Route();
            Route r7 = new Route();
            Route r8 = new Route();
            Route r9 = new Route();
            Route r10 = new Route();
            Route r11 = new Route();
            Route r12 = new Route();

            r1.add(Customers.getSingleInstance().get(65));
            r1.add(Customers.getSingleInstance().get(71));
            r1.add(Customers.getSingleInstance().get(51));
            r1.add(Customers.getSingleInstance().get(9));
            r1.add(Customers.getSingleInstance().get(35));
            r1.add(Customers.getSingleInstance().get(34));
            r1.add(Customers.getSingleInstance().get(3));
            r1.add(Customers.getSingleInstance().get(77));

            r2.add(Customers.getSingleInstance().get(63));
            r2.add(Customers.getSingleInstance().get(64));
            r2.add(Customers.getSingleInstance().get(11));
            r2.add(Customers.getSingleInstance().get(90));
            r2.add(Customers.getSingleInstance().get(10));
            r2.add(Customers.getSingleInstance().get(31));

            r3.add(Customers.getSingleInstance().get(48));
            r3.add(Customers.getSingleInstance().get(47));
            r3.add(Customers.getSingleInstance().get(36));
            r3.add(Customers.getSingleInstance().get(19));
            r3.add(Customers.getSingleInstance().get(49));
            r3.add(Customers.getSingleInstance().get(46));
            r3.add(Customers.getSingleInstance().get(102));
            r3.add(Customers.getSingleInstance().get(82));
            r3.add(Customers.getSingleInstance().get(7));
            r3.add(Customers.getSingleInstance().get(52));

            r4.add(Customers.getSingleInstance().get(62));
            r4.add(Customers.getSingleInstance().get(88));
            r4.add(Customers.getSingleInstance().get(30));
            r4.add(Customers.getSingleInstance().get(20));
            r4.add(Customers.getSingleInstance().get(66));
            r4.add(Customers.getSingleInstance().get(32));
            r4.add(Customers.getSingleInstance().get(70));
            r4.add(Customers.getSingleInstance().get(1));

            r5.add(Customers.getSingleInstance().get(83));
            r5.add(Customers.getSingleInstance().get(45));
            r5.add(Customers.getSingleInstance().get(18));
            r5.add(Customers.getSingleInstance().get(8));
            r5.add(Customers.getSingleInstance().get(84));
            r5.add(Customers.getSingleInstance().get(17));
            r5.add(Customers.getSingleInstance().get(5));
            r5.add(Customers.getSingleInstance().get(60));

            r6.add(Customers.getSingleInstance().get(21));
            r6.add(Customers.getSingleInstance().get(72));
            r6.add(Customers.getSingleInstance().get(39));
            r6.add(Customers.getSingleInstance().get(23));
            r6.add(Customers.getSingleInstance().get(67));
            r6.add(Customers.getSingleInstance().get(55));
            r6.add(Customers.getSingleInstance().get(54));
            r6.add(Customers.getSingleInstance().get(4));
            r6.add(Customers.getSingleInstance().get(25));
            r6.add(Customers.getSingleInstance().get(101));

            r7.add(Customers.getSingleInstance().get(59));
            r7.add(Customers.getSingleInstance().get(37));
            r7.add(Customers.getSingleInstance().get(14));
            r7.add(Customers.getSingleInstance().get(44));
            r7.add(Customers.getSingleInstance().get(38));
            r7.add(Customers.getSingleInstance().get(86));
            r7.add(Customers.getSingleInstance().get(43));
            r7.add(Customers.getSingleInstance().get(100));
            r7.add(Customers.getSingleInstance().get(98));
            r7.add(Customers.getSingleInstance().get(93));

            r8.add(Customers.getSingleInstance().get(50));
            r8.add(Customers.getSingleInstance().get(33));
            r8.add(Customers.getSingleInstance().get(29));
            r8.add(Customers.getSingleInstance().get(79));
            r8.add(Customers.getSingleInstance().get(81));
            r8.add(Customers.getSingleInstance().get(78));
            r8.add(Customers.getSingleInstance().get(68));
            r8.add(Customers.getSingleInstance().get(24));
            r8.add(Customers.getSingleInstance().get(80));
            r8.add(Customers.getSingleInstance().get(12));

            r9.add(Customers.getSingleInstance().get(96));
            r9.add(Customers.getSingleInstance().get(85));
            r9.add(Customers.getSingleInstance().get(91));
            r9.add(Customers.getSingleInstance().get(16));
            r9.add(Customers.getSingleInstance().get(61));
            r9.add(Customers.getSingleInstance().get(99));
            r9.add(Customers.getSingleInstance().get(6));
            r9.add(Customers.getSingleInstance().get(89));

            r10.add(Customers.getSingleInstance().get(28));
            r10.add(Customers.getSingleInstance().get(26));
            r10.add(Customers.getSingleInstance().get(73));
            r10.add(Customers.getSingleInstance().get(41));
            r10.add(Customers.getSingleInstance().get(22));
            r10.add(Customers.getSingleInstance().get(75));
            r10.add(Customers.getSingleInstance().get(56));
            r10.add(Customers.getSingleInstance().get(74));
            r10.add(Customers.getSingleInstance().get(2));
            r10.add(Customers.getSingleInstance().get(58));

            r11.add(Customers.getSingleInstance().get(94));
            r11.add(Customers.getSingleInstance().get(92));
            r11.add(Customers.getSingleInstance().get(42));
            r11.add(Customers.getSingleInstance().get(15));
            r11.add(Customers.getSingleInstance().get(57));
            r11.add(Customers.getSingleInstance().get(87));
            r11.add(Customers.getSingleInstance().get(97));
            r11.add(Customers.getSingleInstance().get(104));
            r11.add(Customers.getSingleInstance().get(95));
            r11.add(Customers.getSingleInstance().get(13));

            r12.add(Customers.getSingleInstance().get(27));
            r12.add(Customers.getSingleInstance().get(103));
            r12.add(Customers.getSingleInstance().get(69));
            r12.add(Customers.getSingleInstance().get(76));
            r12.add(Customers.getSingleInstance().get(40));
            r12.add(Customers.getSingleInstance().get(53));

            s.add(r1);
            s.add(r2);
            s.add(r3);
            s.add(r4);
            s.add(r5);
            s.add(r6);
            s.add(r7);
            s.add(r8);
            s.add(r9);
            s.add(r10);
            s.add(r11);
            s.add(r12);

            s.printSolution();

            double[] cost = s.cost();
            System.out.println("NV: " + (int) cost[0] + " TC: " + cost[1] + " SD: " + cost[2] + " WT: " + cost[3]);*/

        } else {
            System.out.println("data input error!");
        }
    }
}
