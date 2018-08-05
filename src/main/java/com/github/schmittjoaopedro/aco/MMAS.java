package com.github.schmittjoaopedro.aco;

import com.github.schmittjoaopedro.graph.Edge;
import com.github.schmittjoaopedro.graph.Graph;
import com.github.schmittjoaopedro.graph.Vertex;

import java.util.*;

/**
 * Adapted from Dorigo and Stutzle MMAS original implementation to work with graph structures
 * of adjacent lists.
 */
public class MMAS {

    private static final double EPSILON = 0.1;

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

    private List<Ant> antPopulation = new ArrayList<>();

    private Ant bestSoFar;

    private Ant restartBest;

    private Ant previousBestSoFar;

    private Graph graph;

    private Map<Edge, Double> pheromone;

    private Map<Edge, Double> heuristic;

    private Map<Edge, Double> total;

    private Vertex[][] nnList;

    private Random random;

    private double branchFac = 1.00001;

    private boolean symmetric = false;

    public MMAS(Graph graph) {
        this.graph = graph;
    }

    public void allocateAnts() {
        for (int i = 0; i < getnAnts(); i++) {
            antPopulation.add(i, new Ant());
        }
        bestSoFar = new Ant();
        restartBest = new Ant();
        previousBestSoFar = new Ant();
    }

    public void allocateStructures() {
        pheromone = new HashMap<>();
        heuristic = new HashMap<>();
        total = new HashMap<>();
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            pheromone.put(edge, 0.0);
            heuristic.put(edge, 0.0);
            total.put(edge, 0.0);
        }
        nnList = new Vertex[graph.getVertexCount()][depth];

    }

    public void initTry() {
        lambda = 0.05;
        restartIteration = 1;
        bestSoFar.setCost(Double.MAX_VALUE);
        foundBest = 0;
        trailMax = 1.0 / ((rho) * nnTour());
        trailMin = trailMax / (2.0 * graph.getVertexCount());
        initPheromoneTrails(trailMax);
        computeTotalInfo();
    }

    private void initPheromoneTrails(double initialTrail) {
        Iterator<Edge> edges = graph.getEdges();
        while (edges.hasNext()) {
            pheromone.put(edges.next(), initialTrail);
        }
    }

    public void initHeuristicInfo() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            heuristic.put(edge, 1.0 / (edge.getCost() + EPSILON));
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

    private double nnTour() {
        int phase = 0;
        double help;
        Ant ant = antPopulation.get(0);
        antEmptyMemory(ant);
        placeAnt(ant, phase);
        while (phase < graph.getVertexCount() - 1) {
            phase++;
            chooseClosestNext(ant, phase);
        }
        ant.getTour().add(graph.getVertexCount(), ant.getTour().get(0));
        ant.setCost(fitnessEvaluation(ant.getTour()));
        help = ant.getCost();
        antEmptyMemory(ant);
        return help;
    }

    private void antEmptyMemory(Ant ant) {
        ant.getTour().clear();
        ant.getVisited().clear();
        ant.setCost(null);
    }

    private void placeAnt(Ant ant, int step) {
        int rnd = (int) (random.nextDouble() * (double) graph.getVertexCount());
        ant.getTour().add(step, graph.getVertex(rnd));
        ant.getVisited().add(graph.getVertex(rnd));
    }

    private void chooseClosestNext(Ant ant, int phase) {
        Vertex next = null;
        Vertex current = ant.getTour().get(phase - 1);
        double min = Double.MAX_VALUE;
        Iterator<Edge> adj = current.getEdges();
        Edge edge;
        while (adj.hasNext()) {
            edge = adj.next();
            if (!ant.getVisited().contains(edge.getTo())) {
                if (edge.getCost() < min) {
                    next = edge.getTo();
                    min = edge.getCost();
                }
            }
        }
        ant.getTour().add(phase, next);
        ant.getVisited().add(next);
    }

    private double fitnessEvaluation(List<Vertex> tour) {
        Double cost = 0.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            cost += graph.getEdge(tour.get(i).getId(), tour.get(i + 1).getId()).getCost();
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
            ant.getTour().add(ant.getTour().get(0));
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
            pos2[(int) a2.getTour().get(i).getId()] = i;
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int j = (int) a1.getTour().get(i).getId();
            int h = (int) a1.getTour().get(i + 1).getId();
            pos = pos2[j];
            if (pos - 1 < 0)
                prev = graph.getVertexCount() - 1;
            else
                prev = pos - 1;
            if ((int) a2.getTour().get(pos + 1).getId() == h)
                ; /* do nothing, edge is common with best solution found so far */
            else if ((int) a2.getTour().get(prev).getId() == h)
                ; /* do nothing, edge is common with best solution found so far */
            else { /* edge (j,h) does not occur in ant a2 */
                distance++;
            }
        }
        return distance;
    }

    private void neighbourChooseAndMoveToNext(Ant ant, int phase) {
        Vertex help;
        int current;
        int select = 0;
        double rnd;
        double partialSum;
        double sumProb = 0.0;
        double probabilities[] = new double[depth + 1];
        if ((q_0 > 0.0) && (random.nextDouble() < q_0)) {
            neighbourChooseBestNext(ant, phase);
            return;
        }
        current = (int) ant.getTour().get(phase - 1).getId();
        for (int i = 0; i < depth; i++) {
            if (ant.getVisited().contains(nnList[current][i])) {
                probabilities[i] = 0.0;
            } else {
                probabilities[i] = total.get(graph.getEdge(current, nnList[current][i].getId()));
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
            ant.getTour().add(phase, help);
            ant.getVisited().add(help);
        }
    }

    void neighbourChooseBestNext(Ant ant, int phase) {
        int current;
        int next;
        int temp;
        double valueBest;
        double help;
        next = graph.getVertexCount();
        current = (int) ant.getTour().get(phase - 1).getId();
        valueBest = -1.0;
        for (int i = 0; i < depth; i++) {
            temp = (int) nnList[current][i].getId();
            if (!ant.getVisited().contains(graph.getVertex(temp))) {
                help = total.get(graph.getEdge(current, temp));
                if (help > valueBest) {
                    valueBest = help;
                    next = temp;
                }
            }
        }
        if (next == graph.getVertexCount()) {
            chooseBestNext(ant, phase);
        } else {
            ant.getTour().add(phase, graph.getVertex(next));
            ant.getVisited().add(graph.getVertex(next));
        }
    }

    void chooseBestNext(Ant ant, int phase) {
        int current = (int) ant.getTour().get(phase - 1).getId();
        int next = graph.getVertexCount();
        double valueBest = -1.0;
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (!ant.getVisited().contains(graph.getVertex(i))) {
                if (total.get(graph.getEdge(current, i)) > valueBest) {
                    next = i;
                    valueBest = total.get(graph.getEdge(current, i));
                }
            }
        }
        ant.getTour().add(phase, graph.getVertex(next));
        ant.getVisited().add(graph.getVertex(next));
    }

    public void computeTotalInfo() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            total.put(edge, Math.pow(pheromone.get(edge), alpha) * Math.pow(heuristic.get(edge), beta));
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
            min = pheromone.get(graph.getEdge(m, nnList[m][1].getId()));
            max = min;
            for (int i = 1; i < depth; i++) {
                if (pheromone.get(graph.getEdge(m, nnList[m][i].getId())) > max)
                    max = pheromone.get(graph.getEdge(m, nnList[m][i].getId()));
                if (pheromone.get(graph.getEdge(m, nnList[m][i].getId())) < min)
                    min = pheromone.get(graph.getEdge(m, nnList[m][i].getId()));
            }
            cutoff = min + l * (max - min);
            for (int i = 0; i < depth; i++) {
                if (pheromone.get(graph.getEdge(m, nnList[m][i].getId())) > cutoff)
                    numBranches[m] += 1.0;
            }
        }
        for (int m = 0; m < graph.getVertexCount(); m++) {
            avg += numBranches[m];
        }
        return (avg / (double) (graph.getVertexCount() * 2));
    }

    private void copyFromTo(Ant from, Ant to) {
        antEmptyMemory(to);
        for (Vertex vertex : from.getTour()) {
            to.getTour().add(vertex);
            to.getVisited().add(vertex);
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
            pheromone.put(edge, (1.0 - rho) * pheromone.get(edge));
        }
    }

    public void checkPheromoneTrailLimits() {
        Iterator<Edge> edges = graph.getEdges();
        Edge edge;
        while (edges.hasNext()) {
            edge = edges.next();
            if (pheromone.get(edge) < trailMin) {
                pheromone.put(edge, trailMin);
            } else if (pheromone.get(edge) > trailMax) {
                pheromone.put(edge, trailMax);
            }
        }
    }

    private void globalPheromoneDeposit(Ant ant) {
        double dTau = 1.0 / ant.getCost();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int j = (int) ant.getTour().get(i).getId();
            int h = (int) ant.getTour().get(i + 1).getId();
            pheromone.put(graph.getEdge(j, h), pheromone.get(graph.getEdge(j, h)) + dTau);
            if (symmetric) {
                pheromone.put(graph.getEdge(h, j), pheromone.get(graph.getEdge(h, j)) + dTau);
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

    public Map<Edge, Double> getPheromone() {
        return pheromone;
    }

    public Map<Edge, Double> getHeuristic() {
        return heuristic;
    }

    public Map<Edge, Double> getTotal() {
        return total;
    }
}
