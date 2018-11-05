package com.github.schmittjoaopedro.vrp.dvrptwacs;

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

    private Random random;

    //for setting the content to be inserted in the Excel file
    private String tours[];
    private int nrCities[];
    private double subtoursCost[];
    private double totalCost;
    private double longestSubtour;
    private double amplitude;

    //for verbose output: save at each 5 iteration the best (minimum) total cost of all m subtours
    private ArrayList<Double> iterTotalCost;

    //for verbose output: save at each 5 iteration the cost of the longest tour and the number of the corresponding iteration
    private ArrayList<Double> iterLongestCost;
    private ArrayList<Integer> iterNumber;

    //for displaying text with color at console of Linux based systems
    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[31m";

    //auxiliary routine for sorting an integer array
    public void swap2(double v[], int v2[], int i, int j) {
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
    public void sort2(double v[], int v2[], int left, int right) {
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
    public void swap2_(Double v[], Integer v2[], int i, int j) {
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
    public void sort2_(Double v[], Integer v2[], int left, int right) {
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
    public double random01() {
        if (random == null) {
            random = new Random();
        }

        return random.nextDouble();
    }

    public void setSeed(int seed) {
        random = new Random(seed);
    }

    public double mean(Ants ants) {
        double sum = 0.0;
        for (Ant ant : ants.ants) {
            sum += ant.total_tour_length;
        }
        return sum / ants.n_ants;
    }

    public String[] getTours() {
        return tours;
    }

    public void setTours(String[] tours) {
        this.tours = tours;
    }

    public int[] getNrCities() {
        return nrCities;
    }

    public void setNrCities(int[] nrCities) {
        this.nrCities = nrCities;
    }

    public double[] getSubtoursCost() {
        return subtoursCost;
    }

    public void setSubtoursCost(double[] subtoursCost) {
        this.subtoursCost = subtoursCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<Double> getIterTotalCost() {
        return iterTotalCost;
    }

    public void setIterTotalCost(ArrayList<Double> iterTotalCost) {
        this.iterTotalCost = iterTotalCost;
    }

    public ArrayList<Double> getIterLongestCost() {
        return iterLongestCost;
    }

    public ArrayList<Integer> getIterNumber() {
        return iterNumber;
    }

    public void setIterLongestCost(ArrayList<Double> iterLongestCost) {
        this.iterLongestCost = iterLongestCost;
    }

    public void setIterNumber(ArrayList<Integer> iterNumber) {
        this.iterNumber = iterNumber;
    }

    public double getLongestSubtour() {
        return longestSubtour;
    }

    public void setLongestSubtour(double longestSubtour) {
        this.longestSubtour = longestSubtour;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
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


}
