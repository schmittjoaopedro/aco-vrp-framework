package com.github.schmittjoaopedro.tsp.aco;

import com.github.schmittjoaopedro.tsp.aco.ls.opt2.Opt2Operator;
import com.github.schmittjoaopedro.tsp.graph.Edge;
import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.graph.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Adapted from Dorigo and Stutzle MMAS original implementation to work with graph structures
 * of adjacent lists.
 * <p>
 * Heuristic from: http://www.aco-metaheuristic.org/aco-code/public-software.html
 * Software based on: http://adibaba.github.io/ACOTSPJava/
 * <p>
 * Algorithm reference:
 * <p>
 * - T. Stützle and H. H. Hoos, Improving the Ant System: A detailed report on the MAX-MIN Ant System.
 * Technical report AIDA-96-12, FG Intellektik, FB Informatik, TU Darmstadt, 1996. TR.AIDA-96-12.ps.gz
 * Later published in part as The Max-Min Ant System and Local Search for the Travelling Salesman Problem,
 * IEEE International Conference on Evolutionary Computation, Piscataway, T. Bäck, Z. Michalewicz and
 * X. Yao (Eds.), IEEE Press, pp. 309-314, 1997. ICEC97.ps.gz
 * <p>
 * - Max-Min Ant System T. Stützle and H. H. Hoos, MAX-MIN Ant System. Future Generation Computer Systems.
 * 16(8):889--914,2000.
 * <p>
 * -
 */
public class MMAS {

    private double EPSILON = 0.1;

    private double alpha;

    private double beta;

    private double q_0;

    private double rho;

    private double trailMax;

    private double trailMin;

    private double lambda;

    private int foundBest;

    private int restartFoundBest;

    private int restartIteration;

    private double calculatedBranchFact;

    private int nAnts;

    private int depth;

    private int currentIteration;

    private int uGb = Integer.MAX_VALUE;

    private List<Ant> antPopulation;

    private Ant bestSoFar;

    private Ant restartBest;

    private Ant previousBestSoFar;

    private Graph graph;

    private double[][] pheromone;

    private double[][] heuristic;

    private double[][] total;

    private Vertex[][] nnList;

    private Random random;

    private double branchFac = 1.00001;

    private boolean symmetric = false;

    private double probabilities[];

    public MMAS(Graph graph) {
        this.graph = graph;
    }

    public void allocateAnts() {
        antPopulation = new ArrayList<>();
        for (int i = 0; i < getnAnts(); i++) {
            antPopulation.add(i, new Ant());
        }
        bestSoFar = new Ant();
        restartBest = new Ant();
        previousBestSoFar = new Ant();
    }

    public void allocateStructures() {
        pheromone = new double[graph.getVertexCount()][graph.getVertexCount()];
        heuristic = new double[graph.getVertexCount()][graph.getVertexCount()];
        total = new double[graph.getVertexCount()][graph.getVertexCount()];
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            pheromone[edge.getFromId()][edge.getToId()] = 0.0;
            heuristic[edge.getFromId()][edge.getToId()] = 0.0;
            total[edge.getFromId()][edge.getToId()] = 0.0;
        }
        nnList = new Vertex[graph.getVertexCount()][depth];
        probabilities = new double[depth + 1];

    }

    public void initTry() {
        initTry(false);
    }

    public void initTry(boolean localSearch) {
        lambda = 0.05;
        restartIteration = 1;
        bestSoFar.setCost(Double.MAX_VALUE);
        foundBest = 0;
        trailMax = 1.0 / ((rho) * nnTour(localSearch));
        trailMin = trailMax / (2.0 * graph.getVertexCount());
        initPheromoneTrails(trailMax);
        computeTotalInfo();
    }

    private void initPheromoneTrails(double initialTrail) {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            pheromone[edge.getFromId()][edge.getToId()] = initialTrail;
        }
    }

    public void initHeuristicInfo() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            heuristic[edge.getFromId()][edge.getToId()] = 1.0 / (edge.getCost() + EPSILON);
        }
    }

    public void computeNNList() {
        double[] distanceVector = new double[graph.getVertexCount()];
        Vertex[] helpVector = new Vertex[graph.getVertexCount()];
        for (int j = 0; j < graph.getVertexCount(); j++) {
            for (int i = 0; i < graph.getVertexCount(); i++) {
                if (i != j) {
                    distanceVector[i] = graph.getEdge(j, i).getCost();
                    helpVector[i] = graph.getVertex(i);
                }
            }
            distanceVector[j] = Double.MAX_VALUE;
            sort(distanceVector, helpVector, 0, graph.getVertexCount() - 1);
            for (int i = 0; i < depth; i++) {
                nnList[j][i] = helpVector[i];
            }
        }
    }

    private void swap(double v[], Vertex v2[], int i, int j) {
        double tmpCost = v[i];
        Vertex tempVertex = v2[i];
        v[i] = v[j];
        v2[i] = v2[j];
        v[j] = tmpCost;
        v2[j] = tempVertex;
    }

    private void sort(double v[], Vertex v2[], int left, int right) {
        int k, last;
        if (left >= right)
            return;
        swap(v, v2, left, (left + right) / 2);
        last = left;
        for (k = left + 1; k <= right; k++)
            if (v[k] < v[left])
                swap(v, v2, ++last, k);
        swap(v, v2, left, last);
        sort(v, v2, left, last);
        sort(v, v2, last + 1, right);
    }

    private double nnTour(boolean localSearch) {
        int phase = 0;
        double help;
        Ant ant = antPopulation.get(0);
        antEmptyMemory(ant);
        placeAnt(ant, phase);
        while (phase < graph.getVertexCount() - 1) {
            phase++;
            chooseClosestNext(ant, phase);
        }
        ant.getTour()[graph.getVertexCount()] = ant.getTour()[0];
        if (localSearch) {
            Opt2Operator opt2Operator = new Opt2Operator();
            opt2Operator.init(graph, random, ant.getTour());
            opt2Operator.optimize();
            ant.setTour(opt2Operator.getResult());
        }
        ant.setCost(fitnessEvaluation(ant.getTour()));
        help = ant.getCost();
        antEmptyMemory(ant);
        return help;
    }

    public void antEmptyMemory(Ant ant) {
        ant.setTour(new int[graph.getVertexCount() + 1]);
        ant.setVisited(new boolean[graph.getVertexCount()]);
        ant.setCost(null);
    }

    public void placeAnt(Ant ant, int step) {
        int rnd = (int) (random.nextDouble() * (double) graph.getVertexCount());
        ant.getTour()[step] = rnd;
        ant.getVisited()[rnd] = true;
    }

    private void chooseClosestNext(Ant ant, int phase) {
        Vertex next = null;
        Vertex current = graph.getVertex(ant.getTour()[phase - 1]);
        double min = Double.MAX_VALUE;
        Iterator<Edge> adj = current.getEdges();
        Edge edge;
        while (adj.hasNext()) {
            edge = adj.next();
            if (!ant.getVisited()[edge.getToId()]) {
                if (edge.getCost() < min) {
                    next = edge.getTo();
                    min = edge.getCost();
                }
            }
        }
        ant.getTour()[phase] = next.getId();
        ant.getVisited()[next.getId()] = true;
    }

    public double fitnessEvaluation(int[] tour) {
        double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(tour[i], tour[i + 1]).getCost();
        }
        return cost;
    }

    public void constructSolutions() {
        int step = 0;
        for (Ant ant : antPopulation) {
            antEmptyMemory(ant);
        }
        for (Ant ant : antPopulation) {
            placeAnt(ant, step);
        }
        while (step < graph.getVertexCount() - 1) {
            step++;
            for (int k = 0; k < antPopulation.size(); k++) {
                neighbourChooseAndMoveToNext(antPopulation.get(k), step);
            }
        }
        for (Ant ant : antPopulation) {
            ant.getTour()[graph.getVertexCount()] = ant.getTour()[0];
            ant.setCost(fitnessEvaluation(ant.getTour()));
        }
    }

    public void searchControl() {
        if (getCurrentIteration() % 100 == 0) {
            calculatedBranchFact = nodeBranching(lambda);
            if (calculatedBranchFact < branchFac && getCurrentIteration() - restartFoundBest > 250) {
                restartBest.setCost(Double.MAX_VALUE);
                initPheromoneTrails(trailMax);
                computeTotalInfo();
                restartIteration = getCurrentIteration();
            }
        }
    }

    public double calculateDiversity() {
        double avgDistance = 0.0;
        for (int k = 0; k < antPopulation.size() - 1; k++) {
            for (int j = k + 1; j < antPopulation.size(); j++) {
                avgDistance += (double) distanceBetweenAnts(antPopulation.get(k), antPopulation.get(j));
            }
        }
        return avgDistance / ((double) nAnts * (double) (nAnts - 1) / 2.0);
    }

    private int distanceBetweenAnts(Ant a1, Ant a2) {
        int pos, prev;
        int distance = 0;
        int[] pos2 = new int[graph.getVertexCount()];
        for (int i = 0; i < graph.getVertexCount(); i++) {
            pos2[a2.getTour()[i]] = i; // For each node whats store the index in the route of a2
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int j = a1.getTour()[i];
            int h = a1.getTour()[i + 1];
            pos = pos2[j];
            if (pos - 1 < 0)
                prev = graph.getVertexCount() - 1;
            else
                prev = pos - 1;
            if (a2.getTour()[pos + 1] == h)
                ; /* do nothing, edge is common with best solution found so far */
            else if (a2.getTour()[prev] == h)
                ; /* do nothing, edge is common with best solution found so far */
            else { /* edge (j,h) does not occur in ant a2 */
                distance++;
            }
        }
        return distance;
    }

    public void neighbourChooseAndMoveToNext(Ant ant, int phase) {
        Vertex help;
        int current = ant.getTour()[phase - 1];
        int select = 0;
        double rnd;
        double partialSum;
        double sumProb = 0.0;
        if ((q_0 > 0.0) && (random.nextDouble() < q_0)) {
            neighbourChooseBestNext(ant, phase);
            return;
        }
        for (int i = 0; i < depth; i++) {
            if (ant.getVisited()[nnList[current][i].getId()]) {
                probabilities[i] = 0.0;
            } else {
                probabilities[i] = total[current][nnList[current][i].getId()];
                sumProb += probabilities[i];
            }
        }
        if (sumProb <= 0.0) {
            chooseBestNext(ant, phase);
        } else {
            rnd = random.nextDouble() * sumProb;
            partialSum = probabilities[select];
            while (partialSum <= rnd) {
                select++;
                partialSum += probabilities[select];
            }
            if (select == depth) {
                neighbourChooseBestNext(ant, phase);
                return;
            }
            help = nnList[current][select];
            ant.getTour()[phase] = help.getId();
            ant.getVisited()[help.getId()] = true;
        }
    }

    void neighbourChooseBestNext(Ant ant, int phase) {
        int current;
        int next;
        int temp;
        double valueBest;
        double help;
        next = graph.getVertexCount();
        current = ant.getTour()[phase - 1];
        valueBest = -1.0;
        for (int i = 0; i < depth; i++) {
            temp = nnList[current][i].getId();
            if (!ant.getVisited()[temp]) {
                help = total[current][temp];
                if (help > valueBest) {
                    valueBest = help;
                    next = temp;
                }
            }
        }
        if (next == graph.getVertexCount()) {
            chooseBestNext(ant, phase);
        } else {
            ant.getTour()[phase] = next;
            ant.getVisited()[next] = true;
        }
    }

    void chooseBestNext(Ant ant, int phase) {
        int current = ant.getTour()[phase - 1];
        int next = graph.getVertexCount();
        double valueBest = -1.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (!ant.getVisited()[i]) {
                if (total[current][i] > valueBest) {
                    next = i;
                    valueBest = total[current][i];
                }
            }
        }
        ant.getTour()[phase] = next;
        ant.getVisited()[next] = true;
    }

    public void computeTotalInfo() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        int fromId, toId;
        while (edges.hasNext()) {
            edge = edges.next();
            fromId = edge.getFromId();
            toId = edge.getToId();
            total[fromId][toId] = Math.pow(pheromone[fromId][toId], alpha) * Math.pow(heuristic[fromId][toId], beta);
        }
    }

    public void computeNnListTotalInfo() {
        int h;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < getDepth(); j++) {
                h = nnList[i][j].getId();
                if (pheromone[i][h] < pheromone[h][i])
                    /* force pheromone trails to be symmetric as much as possible */
                    pheromone[h][i] = pheromone[i][h];
                total[i][h] = Math.pow(pheromone[i][h], alpha) * Math.pow(heuristic[i][h], beta);
                total[h][i] = total[i][h];
            }
        }
    }

    public boolean updateBestSoFar() {
        Ant iterationBest = findBest();
        boolean found = false;
        if (iterationBest.getCost() < bestSoFar.getCost()) {
            found = true;
            copyFromTo(iterationBest, bestSoFar);
            copyFromTo(iterationBest, restartBest);
            foundBest = getCurrentIteration();
            restartFoundBest = getCurrentIteration();
            calculatedBranchFact = nodeBranching(lambda);
        }
        return found;
    }

    public void updateRestartBest() {
        Ant iterationBest = findBest();
        if (iterationBest.getCost() < restartBest.getCost()) {
            copyFromTo(iterationBest, restartBest);
            restartFoundBest = getCurrentIteration();
        }
    }

    public void setPheromoneBounds() {
        double p_x = Math.exp(Math.log(0.05) / graph.getVertexCount());
        trailMin = 1.0 * (1.0 - p_x) / (p_x * ((depth + 1) / 2));
        trailMax = 1.0 / (rho * bestSoFar.getCost());
        trailMin = trailMax * trailMin;
    }

    public void setPheromoneBoundsForLS() {
        trailMax = 1.0 / (rho * bestSoFar.getCost());
        trailMin = trailMax / (2.0 * graph.getVertexCount());
    }

    private double nodeBranching(double l) {
        double min;
        double max;
        double cutoff;
        double avg = 0.0;
        double numBranches[] = new double[graph.getVertexCount()];
        for (int m = 0; m < graph.getVertexCount(); m++) {
            min = pheromone[m][nnList[m][1].getId()];
            max = min;
            for (int i = 1; i < depth; i++) {
                if (pheromone[m][nnList[m][i].getId()] > max)
                    max = pheromone[m][nnList[m][i].getId()];
                if (pheromone[m][nnList[m][i].getId()] < min)
                    min = pheromone[m][nnList[m][i].getId()];
            }
            cutoff = min + l * (max - min);
            for (int i = 0; i < depth; i++) {
                if (pheromone[m][nnList[m][i].getId()] > cutoff)
                    numBranches[m] += 1.0;
            }
        }
        for (int m = 0; m < graph.getVertexCount(); m++) {
            avg += numBranches[m];
        }
        return (avg / (double) (graph.getVertexCount() * 2));
    }

    public void copyFromTo(Ant from, Ant to) {
        antEmptyMemory(to);
        for (int i = 0; i < from.getTour().length; i++) {
            to.getTour()[i] = from.getTour()[i];
            if (i < to.getVisited().length) {
                to.getVisited()[i] = true;
            }
        }
        to.setCost(from.getCost());
    }

    public Ant findBest() {
        Ant ant = antPopulation.get(0);
        double min = antPopulation.get(0).getCost();
        for (int k = 1; k < nAnts; k++) {
            if (antPopulation.get(k).getCost() < min) {
                min = antPopulation.get(k).getCost();
                ant = antPopulation.get(k);
            }
        }
        return ant;
    }

    public Ant findWorst() {
        Ant ant = antPopulation.get(0);
        double max = antPopulation.get(0).getCost();
        for (int k = 1; k < nAnts; k++) {
            if (antPopulation.get(k).getCost() > max) {
                max = antPopulation.get(k).getCost();
                ant = antPopulation.get(k);
            }
        }
        return ant;
    }

    public void pheromoneUpdate() {
        Ant iterationBest;
        if (getCurrentIteration() % uGb == 0) {
            iterationBest = findBest();
            globalPheromoneDeposit(iterationBest);
        } else {
            if (uGb == 1 && (getCurrentIteration() - restartFoundBest > 50)) {
                globalPheromoneDeposit(bestSoFar);
            } else {
                globalPheromoneDeposit(restartBest);
            }
        }
        uGb = 25;
    }

    public void updateUGB() {
        if ((getCurrentIteration() - restartIteration) < 25)
            uGb = 25;
        else if ((getCurrentIteration() - restartIteration) < 75)
            uGb = 5;
        else if ((getCurrentIteration() - restartIteration) < 125)
            uGb = 3;
        else if ((getCurrentIteration() - restartIteration) < 250)
            uGb = 2;
        else
            uGb = 1;
    }

    public void evaporation() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            pheromone[edge.getFromId()][edge.getToId()] = (1.0 - rho) * pheromone[edge.getFromId()][edge.getToId()];
        }
    }

    public void evaporationLocalSearch() {
        int helpCity;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < getDepth(); j++) {
                helpCity = nnList[i][j].getId();
                pheromone[i][helpCity] = (1 - rho) * pheromone[i][helpCity];
                if (pheromone[i][helpCity] < trailMin) {
                    pheromone[i][helpCity] = trailMin;
                }
            }
        }
    }

    public void checkPheromoneTrailLimits() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            if (pheromone[edge.getFromId()][edge.getToId()] < trailMin) {
                pheromone[edge.getFromId()][edge.getToId()] = trailMin;
            } else if (pheromone[edge.getFromId()][edge.getToId()] > trailMax) {
                pheromone[edge.getFromId()][edge.getToId()] = trailMax;
            }
        }
    }

    public void globalPheromoneDeposit(Ant ant) {
        double dTau = 1.0 / ant.getCost();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int j = ant.getTour()[i];
            int h = ant.getTour()[i + 1];
            pheromone[j][h] = pheromone[j][h] + dTau;
            if (symmetric) {
                pheromone[h][j] = pheromone[j][h];
            }
        }
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setQ_0(double q_0) {
        this.q_0 = q_0;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public int getnAnts() {
        return nAnts;
    }

    public void setnAnts(int nAnts) {
        this.nAnts = nAnts;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    public void setCurrentIteration(int currentIteration) {
        this.currentIteration = currentIteration;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getQ_0() {
        return q_0;
    }

    public double getRho() {
        return rho;
    }

    public double getTrailMax() {
        return trailMax;
    }

    public double getTrailMin() {
        return trailMin;
    }

    public double getLambda() {
        return lambda;
    }

    public int getFoundBest() {
        return foundBest;
    }

    public int getRestartFoundBest() {
        return restartFoundBest;
    }

    public int getRestartIteration() {
        return restartIteration;
    }

    public double getCalculatedBranchFact() {
        return calculatedBranchFact;
    }

    public int getDepth() {
        return depth;
    }

    public int getuGb() {
        return uGb;
    }

    public void setuGb(int uGb) {
        this.uGb = uGb;
    }

    public List<Ant> getAntPopulation() {
        return antPopulation;
    }

    public Ant getBestSoFar() {
        return bestSoFar;
    }

    public Ant getRestartBest() {
        return restartBest;
    }

    public Ant getPreviousBestSoFar() {
        return previousBestSoFar;
    }

    public boolean isSymmetric() {
        return symmetric;
    }

    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }

    public double getEPSILON() {
        return EPSILON;
    }

    public void setEPSILON(double EPSILON) {
        this.EPSILON = EPSILON;
    }

    public Vertex[][] getNnList() {
        return nnList;
    }

    public double[][] getPheromone() {
        return pheromone;
    }

    public void setPheromone(double[][] pheromone) {
        this.pheromone = pheromone;
    }

    public double[][] getTotal() {
        return total;
    }
}
