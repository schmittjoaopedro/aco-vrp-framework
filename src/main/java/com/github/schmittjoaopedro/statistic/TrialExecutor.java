package com.github.schmittjoaopedro.statistic;

import com.github.schmittjoaopedro.tsp.utils.Maths;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrialExecutor {

    public void runAlgorithms(List<Runnable> algorithms) {
        ExecutorService executorService = Executors.newFixedThreadPool(6);//algorithms.size());
        for (Runnable runnable : algorithms) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) ;
    }

    public List<IterationStatistic> getUnifiedStatistics(List<List<IterationStatistic>> results) {
        List<IterationStatistic> iterationStatistics = new ArrayList<>();
        int iteration = results.get(0).size();
        for (int i = 0; i < iteration; i++) {
            IterationStatistic iterationStatistic = new IterationStatistic();
            iterationStatistics.add(iterationStatistic);

            double iterationSd = 0.0;
            double diversity = 0.0;
            double mean = 0.0;
            double bestSoFar = 0.0;
            double bestSoFarNV = 0.0;
            double bestSoFars[] = new double[results.size()];
            double bestSoFarSd = 0.0;
            double worst = 0.0;
            double best = 0.0;
            double bestNV = 0.0;
            double branchFactor = 0.0;
            double feasible = 0.0;

            int count = 0;
            for (List<IterationStatistic> result : results) {
                iterationStatistic.setIteration(result.get(i).getIteration());
                iterationSd += result.get(i).getIterationSd();
                diversity += result.get(i).getDiversity();
                mean += result.get(i).getIterationMean();
                bestSoFar += result.get(i).getBestSoFar();
                bestSoFarNV += result.get(i).getBestSoFarNV();
                bestSoFars[count++] = result.get(i).getBestSoFar();
                worst += result.get(i).getIterationWorst();
                best += result.get(i).getIterationBest();
                bestNV += result.get(i).getIterationBestNV();
                branchFactor += result.get(i).getBranchFactor();
                feasible += result.get(i).getFeasible();
            }

            iterationSd /= results.size();
            diversity /= results.size();
            mean /= results.size();
            bestSoFar /= results.size();
            bestSoFarNV /= results.size();
            worst /= results.size();
            best /= results.size();
            bestNV /= results.size();
            branchFactor /= results.size();
            bestSoFarSd = Maths.sd(bestSoFars);
            feasible /= results.size();

            iterationStatistic.setIterationSd(iterationSd);
            iterationStatistic.setDiversity(diversity);
            iterationStatistic.setIterationMean(mean);
            iterationStatistic.setBestSoFar(bestSoFar);
            iterationStatistic.setBestSoFarNV(bestSoFarNV);
            iterationStatistic.setBestSoFarSd(bestSoFarSd);
            iterationStatistic.setIterationWorst(worst);
            iterationStatistic.setIterationBest(best);
            iterationStatistic.setIterationBestNV(bestNV);
            iterationStatistic.setBranchFactor(branchFactor);
            iterationStatistic.setFeasible(feasible);
        }

        return iterationStatistics;
    }

    public GlobalStatistics getGlobalStatistics(List<List<IterationStatistic>> results) {
        GlobalStatistics globalStatistics = new GlobalStatistics();
        int iteration = results.get(0).size();
        double bsfTC = Double.MAX_VALUE;
        double bsfNV = Double.MAX_VALUE;
        for (int i = 0; i < iteration; i++) {
            for (List<IterationStatistic> result : results) {
                if (result.get(i).getBestSoFarNV() < bsfNV) {
                    bsfTC = result.get(i).getBestSoFar();
                    bsfNV = result.get(i).getBestSoFarNV();
                } else if (result.get(i).getBestSoFarNV() == bsfNV && result.get(i).getBestSoFar() < bsfTC) {
                    bsfTC = result.get(i).getBestSoFar();
                    bsfNV = result.get(i).getBestSoFarNV();
                }
            }
        }
        globalStatistics.setBestSoFarTC(bsfTC);
        globalStatistics.setBestSoFarNV(bsfNV);
        return globalStatistics;
    }

}
