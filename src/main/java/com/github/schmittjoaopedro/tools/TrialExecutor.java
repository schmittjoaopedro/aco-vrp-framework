package com.github.schmittjoaopedro.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrialExecutor {

    public void runAlgorithms(List<Runnable> algorithms) {
        ExecutorService executorService = Executors.newFixedThreadPool(algorithms.size());
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
            double worst = 0.0;
            double best = 0.0;
            double branchFactor = 0.0;

            for (List<IterationStatistic> result : results) {
                iterationStatistic.setIteration(result.get(i).getIteration());
                iterationSd += result.get(i).getIterationSd();
                diversity += result.get(i).getDiversity();
                mean += result.get(i).getIterationMean();
                bestSoFar += result.get(i).getBestSoFar();
                worst += result.get(i).getIterationWorst();
                best += result.get(i).getIterationBest();
                branchFactor += result.get(i).getBranchFactor();
            }

            iterationSd /= results.size();
            diversity /= results.size();
            mean /= results.size();
            bestSoFar /= results.size();
            worst /= results.size();
            best /= results.size();
            branchFactor /= results.size();

            iterationStatistic.setIterationSd(iterationSd);
            iterationStatistic.setDiversity(diversity);
            iterationStatistic.setIterationMean(mean);
            iterationStatistic.setBestSoFar(bestSoFar);
            iterationStatistic.setIterationWorst(worst);
            iterationStatistic.setIterationBest(best);
            iterationStatistic.setBranchFactor(branchFactor);
        }

        return iterationStatistics;
    }

}
