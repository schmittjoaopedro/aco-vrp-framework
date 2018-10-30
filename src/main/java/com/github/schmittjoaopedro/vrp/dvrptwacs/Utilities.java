package com.github.schmittjoaopedro.vrp.dvrptwacs;

import com.github.schmittjoaopedro.vrp.dvrptwacs.Ants.Ant;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * ACO algorithms for the TSP
 * <p>
 * This code is based on the ACOTSP project of Thomas Stuetzle.
 * It was initially ported from C to Java by Adrian Wilke.
 * <p>
 * Project website: http://adibaba.github.io/ACOTSPJava/
 * Source code: https://github.com/adibaba/ACOTSPJava/
 */
public class Utilities {
    /*
     * ################################################
     * ########## ACO algorithms for the TSP ##########
     * ################################################
     *
     * Version: 1.0
     * File: utilities.c
     * Author: Thomas Stuetzle
     * Purpose: some additional useful procedures
     * Check: README and gpl.txt
     * Copyright (C) 2002 Thomas Stuetzle
     */

    /***************************************************************************
     * Program's name: acotsp
     *
     * Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
     * symmetric TSP
     *
     * Copyright (C) 2004 Thomas Stuetzle
     *
     * This program is free software; you can redistribute it and/or modify
     * it under the terms of the GNU General Public License as published by
     * the Free Software Foundation; either version 2 of the License, or
     * (at your option) any later version.
     *
     * This program is distributed in the hope that it will be useful,
     * but WITHOUT ANY WARRANTY; without even the implied warranty of
     * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
     * GNU General Public License for more details.
     *
     * You should have received a copy of the GNU General Public License
     * along with this program; if not, write to the Free Software
     * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
     *
     * email: stuetzle no@spam informatik.tu-darmstadt.de
     * mail address: Universitaet Darmstadt
     * Fachbereich Informatik
     * Hochschulstr. 10
     * D-64283 Darmstadt
     * Germany
     ***************************************************************************/

    private static Random random;

    static int seed;
    static int rowIndex = 1;

    //private static String filePath = "output/Experiments Fuzzy cMeans.xlsx";

    //private static String filePath = "../../VRP/Experimente multi-objective VRPTW/Rulari multi-objective VRPTW - MoACO_D-ACS.xlsx";
    //private static String filePath1 = "../../../Jurnale/jurnal Swarm Intelligence (Springer)/Experimente/Fronturi Pareto algoritmi (pe parcurs)/ACO MinMax_vers. noua/ParetoFront_" + TSP_ACO.instanceName + " (m=" + MTsp.m + ")_amplitude_";
    //private static String filePath1 = "../../VRP/Experimente multi-objective VRPTW/ParetoFront VRPTW_" + VRPTW.instance.name + " (MoACO_D-ACS)" + ".xlsx";
    private static String filePath2 = "../../VRP/Experimente multi-objective VRPTW/fisiere intermediare/Rulari MoACO_D-ACS_" + VRPTW.instance.name + "_no of vehicles and total distance.txt";
    //private static String filePath3 = "../../Doctorat/VRP/VRPTW Solomon instances - data points.xlsx";
    //private static String filePath4 = "../../VRP/Experimente dynamic VRPTW Solomon/Rulari DVRPTW_ACS MinSum/";
    //private static String filePath4 = "output/";
    private static String filePath4 = "C:\\projects\\DVRPTW_ACS\\dvrptw-acs\\output_count_solutions";
    //private static String filePath5 = "../../VRP/Experimente dynamic VRPTW Solomon/Rulari DVRPTW_ACS MinSum/Rulari DVRPTW_ACS MinSum.xlsx";
    //private static String filePath5 = "output/Rulari DVRPTW_ACS MinSum.xlsx";
    //private static String filePath5 = "output_count_solutions/Rulari DVRPTW_ACS MinSum_instante statice_30 rulari.xlsx";

    //for setting the content to be inserted in the Excel file
    private static String tours[];
    private static int nrCities[];
    private static double subtoursCost[];
    private static double totalCost;
    private static double longestSubtour;
    private static double amplitude;

    //for verbose output: save at each 5 iteration the best (minimum) total cost of all m subtours
    private static ArrayList<Double> iterTotalCost;

    //for verbose output: save at each 5 iteration the cost of the longest tour and the number of the corresponding iteration
    private static ArrayList<Double> iterLongestCost;
    private static ArrayList<Integer> iterNumber;

    //for displaying text with color at console of Linux based systems
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    //auxiliary routine for sorting an integer array
    static void swap2(double v[], int v2[], int i, int j) {
        double tmp1;
        int tmp2;

        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;

        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }

    //recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
    static void sort2(double v[], int v2[], int left, int right) {
        int k, last;

        if (left >= right)
            return;
        swap2(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap2(v, v2, ++last, k);
        swap2(v, v2, left, last);
        sort2(v, v2, left, last);
        sort2(v, v2, last + 1, right);
    }

    //auxiliary routine for sorting an integer array and a double array
    static void swap2_(Double v[], Integer v2[], int i, int j) {
        double tmp1;
        int tmp2;

        tmp1 = v[i];
        v[i] = v[j];
        v[j] = tmp1;

        tmp2 = v2[i];
        v2[i] = v2[j];
        v2[j] = tmp2;
    }

    //recursive routine (quicksort) for sorting one array; second array does the same sequence of swaps
    static void sort2_(Double v[], Integer v2[], int left, int right) {
        int k, last;

        if (left >= right)
            return;
        swap2_(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap2_(v, v2, ++last, k);
        swap2_(v, v2, left, last);
        sort2_(v, v2, left, last);
        sort2_(v, v2, last + 1, right);
    }

    //generate a random number that is uniformly distributed in [0,1]
    static double random01() {
        if (random == null) {
            random = new Random();
        }

        return random.nextDouble();
    }

    static void setSeed(int seed) {
        random = new Random(seed);
    }

    static double mean() {
        double sum = 0.0;
        for (Ant ant : Ants.ants) {
            sum += ant.total_tour_length;
        }
        return sum / Ants.n_ants;
    }

    //save in a .txt output file the best solution resulted after a run to be later used when
    //computing the Pareto front
    static void writeParetoSolutions(ArrayList<Ant> bestSoFarPareto) {
        File f = new File(filePath2);
        double[] objValues = new double[2];

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

            for (int i = 0; i < bestSoFarPareto.size(); i++) {
                if (rowIndex > 0 && i != 0) {
                    bw.newLine();
                }
                bw.write(rowIndex + "\t");
                //get values total cost and amplitude
                for (int indexObj = 0; indexObj < 2; indexObj++) {
                    objValues[indexObj] = bestSoFarPareto.get(i).costObjectives[indexObj];
                }
                bw.write(objValues[0] + "\t");
                //write cost of each individual subtour
                for (int j = 0; j < bestSoFarPareto.get(i).tour_lengths.size(); j++) {
                    bw.write(bestSoFarPareto.get(i).tour_lengths.get(j) + "\t");
                }
                bw.write(objValues[1] + "\t");

                rowIndex++;
            }
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("error writing file");
        }
    }

    static void writeFinalSolution(int trial, String fileName, double scalledValue, boolean isValid) {
        String name = filePath4 + fileName;
        File f = new File(name);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));

            if (trial > 0) {
                bw.newLine();
            }
            bw.write("Run Ant Colony System #" + (trial + 1));

            bw.write("\nFinal best solution >> No. of used vehicles=" + Ants.best_so_far_ant.usedVehicles + " total tours length=" + Ants.best_so_far_ant.total_tour_length + " (scalled value = " + scalledValue + ")");
            bw.newLine();
            for (int i = 0; i < Ants.best_so_far_ant.usedVehicles; i++) {
                int tourLength = Ants.best_so_far_ant.tours.get(i).size();
                for (int j = 0; j < tourLength; j++) {
                    int city = Ants.best_so_far_ant.tours.get(i).get(j);
                    city = city + 1;  //so as to correspond to the city indexes from the VRPTW input file
                    bw.write(city + " ");
                }
                bw.newLine();
            }
            bw.write("\nTotal number of evaluations: " + InOut.noEvaluations);
            bw.write("\nAdded nodes=" + Controller.addedNodes);
            if (isValid) {
                bw.write("\nThe final solution is valid (feasible)..");
            } else {
                bw.write("\nThe final solution is not valid (feasible)..");
            }

            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("error writing file");
            e.printStackTrace();
        }
    }

    public static String[] getTours() {
        return tours;
    }

    public static void setTours(String[] tours) {
        Utilities.tours = tours;
    }

    public static int[] getNrCities() {
        return nrCities;
    }

    public static void setNrCities(int[] nrCities) {
        Utilities.nrCities = nrCities;
    }

    public static double[] getSubtoursCost() {
        return subtoursCost;
    }

    public static void setSubtoursCost(double[] subtoursCost) {
        Utilities.subtoursCost = subtoursCost;
    }

    public static double getTotalCost() {
        return totalCost;
    }

    public static void setTotalCost(double totalCost) {
        Utilities.totalCost = totalCost;
    }

    public static ArrayList<Double> getIterTotalCost() {
        return iterTotalCost;
    }

    public static void setIterTotalCost(ArrayList<Double> iterTotalCost) {
        Utilities.iterTotalCost = iterTotalCost;
    }

    public static ArrayList<Double> getIterLongestCost() {
        return iterLongestCost;
    }

    public static ArrayList<Integer> getIterNumber() {
        return iterNumber;
    }

    public static void setIterLongestCost(ArrayList<Double> iterLongestCost) {
        Utilities.iterLongestCost = iterLongestCost;
    }

    public static void setIterNumber(ArrayList<Integer> iterNumber) {
        Utilities.iterNumber = iterNumber;
    }

    public static double getLongestSubtour() {
        return longestSubtour;
    }

    public static void setLongestSubtour(double longestSubtour) {
        Utilities.longestSubtour = longestSubtour;
    }

    public static double getAmplitude() {
        return amplitude;
    }

    public static void setAmplitude(double amplitude) {
        Utilities.amplitude = amplitude;
    }

    public static ArrayList<ArrayList<Integer>> readSolution(String fileName) {
        File file = new File("input/" + fileName);
        BufferedReader in = null;
        int value;

        ArrayList<ArrayList<Integer>> solution = new ArrayList();
        ArrayList<Integer> tour;

        if (file.exists()) {
            try {
                in = new BufferedReader(new FileReader(file));
                String line = in.readLine();
                while (line != null) {
                    tour = new ArrayList<Integer>();
                    String[] strRecord = line.trim().split(" ");
                    for (int i = 0; i < strRecord.length; i++) {
                        try {
                            value = Integer.parseInt(strRecord[i]) - 1;
                            tour.add(value);
                        } catch (NumberFormatException e) {
                            System.out.println("NumberFormatException " + e.getMessage() + " record=" + strRecord[i] + " line=" + line);
                        }
                    }
                    solution.add(tour);
                    line = in.readLine();
                }
                in.close();
            } catch (FileNotFoundException ignored) {
            } catch (IOException e) {
                System.out.println("Error occurred while reading file: " + file + " " + e.getMessage());
            }


        }

        return solution;

    }

    public static boolean checkFeasibility(Ant a, VRPTW vrp, boolean printNoNodes) {
        boolean isFeasible = true;
        int currentCity, prevCity, addedNodes = 0;
        double currentQuantity, currentTime;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = vrp.getRequests();

        for (int indexTour = 0; indexTour < a.usedVehicles; indexTour++) {
            currentQuantity = reqList.get(0).getDemand();
            currentTime = 0.0;
            for (int currentPos = 1; currentPos < a.tours.get(indexTour).size(); currentPos++) {
                if (currentPos < a.tours.get(indexTour).size() - 1) {
                    addedNodes++;
                }
				/*if (addedNodes == 0) {
					System.out.println("Possible empty tour: " + a.tours.get(indexTour).size());
				}*/
                prevCity = a.tours.get(indexTour).get(currentPos - 1);
                currentCity = a.tours.get(indexTour).get(currentPos);
                currentQuantity += reqList.get(currentCity + 1).getDemand();

                distance = VRPTW.instance.distance[prevCity + 1][currentCity + 1];
                arrivalTime = currentTime + reqList.get(prevCity + 1).getServiceTime() + distance;
                beginService = Math.max(arrivalTime, reqList.get(currentCity + 1).getStartWindow());
                if (beginService > reqList.get(currentCity + 1).getEndWindow()) {
                    //isFeasible = false;
                    System.out.println("Time window constraint violated");
                    return false;
                }
                currentTime = beginService;

            }
            if (currentQuantity > vrp.getCapacity()) {
                //isFeasible = false;
                System.out.println("Capacity constraint violated");
                return false;
            }
        }
        if (printNoNodes) {
            LoggerOutput.log("Added nodes=" + addedNodes);
        }
        Controller.addedNodes = addedNodes;
        return isFeasible;

    }

}
