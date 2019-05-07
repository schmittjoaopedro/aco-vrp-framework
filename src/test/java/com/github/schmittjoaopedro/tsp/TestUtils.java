package com.github.schmittjoaopedro.tsp;

import com.github.schmittjoaopedro.tsp.graph.Vertex;
import com.github.schmittjoaopedro.statistic.GlobalStatistics;
import com.github.schmittjoaopedro.statistic.IterationStatistic;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static String getTourString(GlobalStatistics globalStatistics) {
        StringBuilder tour = new StringBuilder("[");
        Map<Integer, Integer> tourNodes = new HashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Vertex vertex : globalStatistics.getBestRoute()) {
            tour.append(vertex.getId()).append(", ");
            if (!tourNodes.containsKey(vertex.getId())) {
                tourNodes.put(vertex.getId(), 1);
            }
        }
        tourNodes.forEach((key, count) -> atomicInteger.addAndGet(count));
        if (atomicInteger.get() != globalStatistics.getBestRoute().size() - 1) {
            throw new RuntimeException("Invalid number of vertices");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }

    public static void checkValues(List<IterationStatistic> iterationStatistics, int iteration, double mean, double sd, double branchFactor, double div, double bsf) {
        assertThat(iterationStatistics.get(iteration - 1).getIteration()).isEqualTo(iteration);
        assertThat(round(iterationStatistics.get(iteration - 1).getIterationMean())).isEqualTo(round(mean));
        assertThat(round(iterationStatistics.get(iteration - 1).getIterationSd())).isEqualTo(round(sd));
        assertThat(round(iterationStatistics.get(iteration - 1).getBranchFactor())).isEqualTo(round(branchFactor));
        assertThat(round(iterationStatistics.get(iteration - 1).getDiversity())).isEqualTo(round(div));
        assertThat(round(iterationStatistics.get(iteration - 1).getBestSoFar())).isEqualTo(round(bsf));
    }

    public static void printTest10(List<IterationStatistic> iterationStatistics, String variableName) {
        for (IterationStatistic iter : iterationStatistics) {
            if (iter.getIteration() % 10 == 0 || (iter.getIteration() - 1) % 10 == 0 || (iter.getIteration() - 5) % 10 == 0)
                System.out.println(
                        "TestUtils.checkValues(" + variableName + ".getIterationStatistics(), " +
                                ((int) iter.getIteration()) + ", " +
                                iter.getIterationMean() + ", " +
                                iter.getIterationSd() + ", " +
                                iter.getBranchFactor() + ", " +
                                iter.getDiversity() + ", " +
                                iter.getBestSoFar() + ");"
                );
        }
    }

    public static void printTest100(List<IterationStatistic> iterationStatistics, String variableName) {
        for (IterationStatistic iter : iterationStatistics) {
            if (iter.getIteration() % 100 == 0 || (iter.getIteration() - 1) % 100 == 0 || (iter.getIteration() - 50) % 100 == 0)
                System.out.println(
                        "TestUtils.checkValues(" + variableName + ".getIterationStatistics(), " +
                                ((int) iter.getIteration()) + ", " +
                                iter.getIterationMean() + ", " +
                                iter.getIterationSd() + ", " +
                                iter.getBranchFactor() + ", " +
                                iter.getDiversity() + ", " +
                                iter.getBestSoFar() + ");"
                );
        }
    }

    public static double round(double value) {
        return Double.valueOf(String.format(Locale.US, "%.2f", value));
    }

    public static void printRoutes(List<IterationStatistic> iterationStatistics, String variableName) {
        for (IterationStatistic iter : iterationStatistics) {
            if ((iter.getIteration() - 1) == 0 || (iter.getIteration() - 1) % 10 == 0 || iter.getIteration() == iterationStatistics.size())
                System.out.println(
                        "assertThat(TestUtils.getTourString(" + variableName + ".getIterationStatistics().get(" + ((int) iter.getIteration() - 1) +
                                ").getTour())).isEqualTo(\"" +
                                getTourString(iterationStatistics.get((int) iter.getIteration() - 1).getTour()) +
                                "\");"
                );
        }
        for (IterationStatistic iter : iterationStatistics) {
            if ((iter.getIteration() - 1) == 0 || (iter.getIteration() - 1) % 10 == 0 || iter.getIteration() == iterationStatistics.size()) {
                int[] mvbsTour = iterationStatistics.get((int) iter.getIteration() - 1).getMvsbTour();
                if (mvbsTour != null) {
                    System.out.println(
                            "assertThat(TestUtils.getTourString(" + variableName + ".getIterationStatistics().get(" + ((int) iter.getIteration() - 1) +
                                    ").getMvsbTour())).isEqualTo(\"" +
                                    getTourString(mvbsTour) +
                                    "\");"
                    );
                }
            }
        }
    }

    public static String getTourString(int[] tourAnt) {
        StringBuilder tour = new StringBuilder("[");
        for (int vertex : tourAnt) {
            tour.append(vertex).append(", ");
        }
        tour.delete(tour.length() - 2, tour.length()).append("]");
        return tour.toString();
    }
}
