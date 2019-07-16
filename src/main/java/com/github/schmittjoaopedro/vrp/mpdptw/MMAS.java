package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.vrp.mpdptw.aco.SequentialInfeasible;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SolutionBuilder;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.InsertionHeuristic;

import java.util.*;

public class MMAS {

    private ProblemInstance instance;

    private double upperBound;

    private double alpha;

    private double beta;

    private double rho;

    private double trailMax;

    private double trailMin;

    private double lambda;

    private int restartFoundBest;

    private int restartIteration;

    private double calculatedBranchFact;

    private int nAnts;

    private int foundBest;

    private int depth;

    private int currentIteration;

    private int uGb = Integer.MAX_VALUE;

    private List<Solution> antPopulation;

    private Solution bestSoFar;

    private Solution restartBest;

    private double[][] pheromoneNodes;

    private boolean parallel = Boolean.FALSE;

    private int[][] nnList;

    private Random random;

    private SolutionBuilder solutionBuilder;

    private double branchFac = 1.00001;

    public MMAS(ProblemInstance instance) {
        this.instance = instance;
    }

    public void allocateAnts() {
        antPopulation = new ArrayList<>();
        for (int i = 0; i < getnAnts(); i++) {
            antPopulation.add(i, SolutionUtils.createEmptyAnt(instance));
        }
        bestSoFar = SolutionUtils.createEmptyAnt(instance);
        restartBest = SolutionUtils.createEmptyAnt(instance);
    }

    public void allocateStructures() {
        pheromoneNodes = new double[instance.getNumNodes()][instance.getNumNodes()];
        for (int i = 0; i < instance.getNumNodes(); i++) {
            for (int j = 0; j < instance.getNumNodes(); j++) {
                pheromoneNodes[i][j] = 0.0;
            }
        }
        nnList = new int[instance.getNumNodes()][depth];
    }

    public void computeNNList() {
        double[] distanceVector = new double[instance.getNumNodes()];
        int[] helpVector = new int[instance.getNumNodes()];
        for (int i = 0; i < instance.getNumNodes(); i++) {
            for (int j = 0; j < instance.getNumNodes(); j++) {
                if (i != j && instance.isFeasible(i, j)) {
                    distanceVector[j] = instance.dist(i, j);
                } else {
                    distanceVector[j] = Double.MAX_VALUE;
                }
                if (i != j) {
                    helpVector[j] = j;
                }
            }
            sort(distanceVector, helpVector, 0, instance.getNumNodes() - 1);
            for (int j = 0; j < depth; j++) {
                nnList[i][j] = helpVector[j];
            }
        }
    }

    private void swap(double v[], int v2[], int i, int j) {
        double tmpCost = v[i];
        int tempVertex = v2[i];
        v[i] = v[j];
        v2[i] = v2[j];
        v[j] = tmpCost;
        v2[j] = tempVertex;
    }

    private void sort(double v[], int v2[], int left, int right) {
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

    public void initTry() {
        lambda = 0.05;
        restartIteration = 1;
        foundBest = 0;
        trailMax = 1.0 / ((rho) * nnTour());
        trailMin = trailMax / (2.0 * instance.getNumNodes());
        initPheromoneTrails(trailMax);
    }

    private void initPheromoneTrails(double initialTrail) {
        for (int i = 0; i < instance.getNumNodes(); i++) {
            for (int j = 0; j < instance.getNumNodes(); j++) {
                if (i != j) {
                    pheromoneNodes[i][j] = initialTrail;
                }
            }
        }
    }

    public void constructSolutions() {
        solutionBuilder.constructSolutions();
    }

    public Solution findBest() {
        Solution ant = antPopulation.get(0);
        double min = antPopulation.get(0).totalCost;
        for (int k = 1; k < nAnts; k++) {
            if (ant.feasible) {
                if (antPopulation.get(k).feasible && antPopulation.get(k).totalCost < min) {
                    min = antPopulation.get(k).totalCost;
                    ant = antPopulation.get(k);
                }
            } else {
                if (antPopulation.get(k).totalCost < min || antPopulation.get(k).feasible) {
                    min = antPopulation.get(k).totalCost;
                    ant = antPopulation.get(k);
                }
            }
        }
        return ant;
    }

    public boolean updateBestSoFar() {
        Solution iterationBest = findBest();
        boolean found = false;
        if (bestSoFar.tours.isEmpty() || instance.getBest(bestSoFar, iterationBest) != bestSoFar) {
            found = true;
            SolutionUtils.copyFromTo(iterationBest, bestSoFar);
            SolutionUtils.copyFromTo(iterationBest, restartBest);
            foundBest = getCurrentIteration();
            restartFoundBest = getCurrentIteration();
            calculatedBranchFact = nodeBranching(lambda);
        }
        return found;
    }

    public void setPheromoneBounds() {
        double p_x = Math.exp(Math.log(0.05) / instance.getNumNodes());
        trailMin = 1.0 * (1.0 - p_x) / (p_x * ((depth + 1) / 2));
        trailMax = 1.0 / (rho * bestSoFar.totalCost);
        trailMin = trailMax * trailMin;
    }

    public void setPheromoneBoundsForLS() {
        trailMax = 1.0 / (rho * bestSoFar.totalCost);
        trailMin = trailMax / (2.0 * instance.getNumNodes());
    }

    public void updateRestartBest() {
        Solution iterationBest = findBest();
        if (instance.getBest(restartBest, iterationBest) != restartBest) {
            SolutionUtils.copyFromTo(iterationBest, restartBest);
            restartFoundBest = getCurrentIteration();
        }
    }

    public void evaporation() {
        for (int i = 0; i < pheromoneNodes.length; i++) {
            for (int j = 0; j < pheromoneNodes[i].length; j++) {
                if (i != j) {
                    pheromoneNodes[i][j] = (1.0 - rho) * pheromoneNodes[i][j];
                }
            }
        }
    }

    public void pheromoneUpdate() {
        Solution iterationBest;
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

    public void globalPheromoneDeposit(Solution ant) {
        int from, to;
        double dTau;
        if (SequentialInfeasible.class.equals(solutionBuilder)) {
            double max = 0;
            for (Solution a : antPopulation) {
                max = Math.max(max, a.totalCost);
                max = Math.max(max, a.timeWindowPenalty);
            }
            double factTotal = ant.totalCost / max;
            double factPenalt = ant.timeWindowPenalty / max;
            dTau = 1.0 / (ant.totalCost * factTotal + ant.timeWindowPenalty * factPenalt);
        } else {
            dTau = 1.0 / ant.totalCost;
        }
        for (int i = 0; i < ant.tours.size(); i++) {
            for (int j = 0; j < ant.tours.get(i).size() - 1; j++) {
                from = ant.tours.get(i).get(j);
                to = ant.tours.get(i).get(j + 1);
                pheromoneNodes[from][to] = pheromoneNodes[from][to] + dTau;
                //pheromoneNodes[to][from] = pheromoneNodes[from][to]; // We can't consider this problem asymmetric due to time windows
            }
        }
    }

    public void checkPheromoneTrailLimits() {
        for (int i = 0; i < pheromoneNodes.length; i++) {
            for (int j = 0; j < pheromoneNodes.length; j++) {
                if (i != j) {
                    if (pheromoneNodes[i][j] < trailMin) {
                        pheromoneNodes[i][j] = trailMin;
                    } else if (pheromoneNodes[i][j] > trailMax) {
                        pheromoneNodes[i][j] = trailMax;
                    }
                }
            }
        }
    }

    public void searchControl() {
        if (getCurrentIteration() % 100 == 0) {
            calculatedBranchFact = nodeBranching(lambda);
            if (calculatedBranchFact < branchFac && getCurrentIteration() - restartFoundBest > 250) {
                System.out.println("Restart trails!!!");
                restartBest.totalCost = Double.MAX_VALUE;
                initPheromoneTrails(trailMax);
                restartIteration = getCurrentIteration();
                solutionBuilder.onSearchControlExecute();
            }
        }
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

    private double nnTour() {
        InsertionHeuristic insertionHeuristic = new InsertionHeuristic(instance, random);
        Solution solution = insertionHeuristic.createInitialSolution();
        return solution.totalCost;
        /*OptimalRequestSolver optimalRequestSolver;
        upperBound = 0.0;
        for (int r = 0; r < instance.getNumReq(); r++) {
            optimalRequestSolver = new OptimalRequestSolver(r, instance);
            optimalRequestSolver.optimize();
            upperBound += optimalRequestSolver.getBestCost();
        }
        return upperBound;*/
    }

    public Solution findWorst() {
        Solution ant = antPopulation.get(0);
        double max = antPopulation.get(0).totalCost;
        for (int k = 1; k < nAnts; k++) {
            if (antPopulation.get(k).totalCost > max) {
                max = antPopulation.get(k).totalCost;
                ant = antPopulation.get(k);
            }
        }
        return ant;
    }

    public double getPenaltyRate() {
        double count = 0;
        for (Solution ant : antPopulation) {
            if (!ant.feasible) {
                count++;
            }
        }
        return count / (double) antPopulation.size();
    }

    public double calculateDiversity() {
        double avgDistance = 0.0;
        for (int k = 0; k < antPopulation.size() - 1; k++) {
            for (int j = k + 1; j < antPopulation.size(); j++) {
                avgDistance += (double) distanceBetweenAnts(antPopulation.get(k), antPopulation.get(j));
            }
        }
        double difference = avgDistance / ((double) nAnts * (double) (nAnts - 1) / 2.0);
        return difference / instance.getNumNodes();
    }

    private int distanceBetweenAnts(Solution a1, Solution a2) {
        int curr, next, distance = 0;
        Set<String> edgesA1 = new HashSet<>();

        for (int k = 0; k < a1.tours.size(); k++) {
            for (int i = 0; i < a1.tours.get(k).size() - 1; i++) {
                curr = a1.tours.get(k).get(i);
                next = a1.tours.get(k).get(i + 1);
                edgesA1.add(curr + "-" + next);
            }
        }

        for (int k = 0; k < a2.tours.size(); k++) {
            for (int i = 0; i < a2.tours.get(k).size() - 1; i++) {
                curr = a2.tours.get(k).get(i);
                next = a2.tours.get(k).get(i + 1);
                if (!edgesA1.contains(curr + "-" + next)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public double nodeBranching() {
        return nodeBranching(lambda);
    }

    private double nodeBranching(double l) {
        double min;
        double max;
        double cutoff;
        double avg = 0.0;
        double numBranches[] = new double[instance.getNumNodes()];
        for (int m = 1; m < instance.getNumNodes(); m++) {
            min = Double.MAX_VALUE;
            max = Double.MIN_VALUE;
            for (int i = 0; i < instance.getNumNodes(); i++) {
                if (m != i) {
                    max = Math.max(pheromoneNodes[m][i], max);
                    min = Math.min(pheromoneNodes[m][i], min);
                }
            }
            cutoff = min + l * (max - min);
            for (int i = 0; i < depth; i++) {
                if (m != i && pheromoneNodes[m][i] > cutoff)
                    numBranches[m] += 1.0;
            }
        }
        for (int m = 0; m < instance.getNumNodes(); m++) {
            avg += numBranches[m];
        }
        return (avg / (double) instance.getNumNodes());
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public void setnAnts(int nAnts) {
        this.nAnts = nAnts;
    }

    public int getnAnts() {
        return nAnts;
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

    public Solution getBestSoFar() {
        return bestSoFar;
    }

    public void setBestSoFar(Solution bestSoFar) {
        this.bestSoFar = bestSoFar;
    }

    public double getCalculatedBranchFact() {
        return calculatedBranchFact;
    }

    public int getFoundBest() {
        return foundBest;
    }

    public List<Solution> getAntPopulation() {
        return antPopulation;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    public boolean isParallel() {
        return parallel;
    }

    public double[][] getPheromoneNodes() {
        return pheromoneNodes;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public Random getRandom() {
        return random;
    }

    public void setSolutionBuilder(Class<? extends SolutionBuilder> solutionBuilderClass) {
        if (instance == null || random == null) {
            throw new RuntimeException("Random and Instance must be defined firstly.");
        }
        try {
            solutionBuilder = solutionBuilderClass.getConstructor().newInstance();
            solutionBuilder.init(instance, random, this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
