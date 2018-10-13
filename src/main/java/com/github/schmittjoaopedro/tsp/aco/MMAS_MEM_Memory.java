package com.github.schmittjoaopedro.tsp.aco;

import com.github.schmittjoaopedro.tsp.graph.Graph;
import com.github.schmittjoaopedro.tsp.tools.MVBS;

import java.util.*;

public class MMAS_MEM_Memory {

    private int shortMemorySize;

    private double immigrantRate;

    private double mutationProbability;

    private Ant[] shortMemory;

    private Random random;

    private Graph graph;

    private MMAS mmas;

    private MVBS mvbs;

    public MMAS_MEM_Memory(Graph graph, MMAS mmas) {
        this.graph = graph;
        this.mmas = mmas;
    }

    public void initialize() {
        shortMemory = new Ant[shortMemorySize];
        initMemoryRandomly();
    }

    public void updateShortTermMemory() {
        int imSize = (int) (immigrantRate * shortMemorySize);
        Ant[] immigrants = new Ant[imSize];
        for (int i = 0; i < imSize; i++) {
            immigrants[i] = generateMemoryBasedImmigrant(mmas.findBest());
        }
        List<Ant> antsUnion = new ArrayList<>();
        Set<Double> antsCosts = new HashSet<>();
        antsUnion.add(mmas.getRestartBest());
        antsCosts.add(mmas.getRestartBest().getCost());
        for (Ant ant : mmas.getAntPopulation()) {
            if (!antsCosts.contains(ant.getCost())) {
                antsUnion.add(ant);
                antsCosts.add(ant.getCost());
            }
        }
        if (antsUnion.size() < shortMemorySize) {
            for (Ant ant : mmas.getAntPopulation()) {
                if (!antsUnion.contains(ant) && antsUnion.size() < shortMemorySize) {
                    antsUnion.add(ant);
                }
            }
        }
        Ant[] sortedAnts = antsUnion.toArray(new Ant[0]);
        Arrays.sort(sortedAnts, Comparator.comparing(Ant::getCost));
        for (int i = 0; i < shortMemorySize; ++i) {
            shortMemory[i].setCost(mmas.fitnessEvaluation(shortMemory[i].getTour()));
        }
        Arrays.sort(shortMemory, Comparator.comparing(Ant::getCost));
        for (int i = 0; i < shortMemorySize; ++i) {
            if (!isValidAnt(shortMemory[i]) || sortedAnts[i].getCost() < shortMemory[i].getCost()) {
                mmas.copyFromTo(sortedAnts[i], shortMemory[i]);
            }
        }
        for (int i = shortMemorySize - 1; i > shortMemorySize - imSize - 1; i--) {
            shortMemory[i] = immigrants[shortMemorySize - 1 - i];
        }
        Arrays.sort(shortMemory, Comparator.comparing(Ant::getCost));
    }

    public void pheromoneUpdate(int iteration) {
        if (iteration % mmas.getuGb() == 0) {
            mmas.globalPheromoneDeposit(mmas.findBest());
        } else if (mmas.getuGb() == 1 && (iteration - mmas.getRestartFoundBest() > 50)) {
            mmas.globalPheromoneDeposit(mmas.getBestSoFar());
        } else {
            for (Ant ant : shortMemory) {
                mmas.globalPheromoneDeposit(ant);
            }
        }
        mmas.setuGb(25);
    }

    private void initMemoryRandomly() {
        for (int k = 0; k < shortMemorySize; k++) {
            shortMemory[k] = new Ant();
            mmas.antEmptyMemory(shortMemory[k]);
            randomWalk(shortMemory[k]);
            shortMemory[k].setCost(mmas.fitnessEvaluation(shortMemory[k].getTour()));
        }
    }

    private void randomWalk(Ant ant) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            ant.getTour()[i] = i;
            ant.getVisited()[i] = true;
        }
        for (int i = 0; i < graph.getVertexCount(); i++) {
            int v1 = (int) (random.nextDouble() * graph.getVertexCount());
            int v2 = (int) (random.nextDouble() * graph.getVertexCount());
            int aux = ant.getTour()[v1];
            ant.getTour()[v1] = ant.getTour()[v2];
            ant.getTour()[v2] = aux;
        }
        ant.getTour()[graph.getVertexCount()] = ant.getTour()[0];
    }

    private Ant generateMemoryBasedImmigrant(Ant iterationBest) {
        Ant copyAnt = new Ant();
        mmas.copyFromTo(iterationBest, copyAnt);
        for (int i = 1; i < copyAnt.getTour().length - 1; i++) {
            if (random.nextDouble() < mutationProbability) {
                int index = (int) ((random.nextDouble() * (copyAnt.getTour().length - 2)) + 1);
                int aux = copyAnt.getTour()[i];
                copyAnt.getTour()[i] = copyAnt.getTour()[index];
                copyAnt.getTour()[index] = aux;
            }
        }
        copyAnt.setCost(mmas.fitnessEvaluation(copyAnt.getTour()));
        return copyAnt;
    }

    private boolean isValidAnt(Ant ant) {
        if (mvbs != null) {
            return mvbs.isValid(ant);
        } else {
            return true;
        }
    }

    public int getShortMemorySize() {
        return shortMemorySize;
    }

    public void setShortMemorySize(int shortMemorySize) {
        this.shortMemorySize = shortMemorySize;
    }

    public double getImmigrantRate() {
        return immigrantRate;
    }

    public void setImmigrantRate(double immigrantRate) {
        this.immigrantRate = immigrantRate;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public MVBS getMvbs() {
        return mvbs;
    }

    public void setMvbs(MVBS mvbs) {
        this.mvbs = mvbs;
    }
}
