package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SequentialFeasibleMPDPTW;
import com.github.schmittjoaopedro.vrp.mpdptw.aco.SequentialInfeasible;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MPDPTW_MMAS_TEST {

    private static final String rootDirectory;

    private static int seed = 1;

    static {
        rootDirectory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("mpdptw").getFile().substring(1)).toString();
    }

    @Test
    public void mpdptw_sequential_feasible_test() throws IOException {
        String problem = "w_4_100_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 10, seed, 0.8, 1, true);
        solver.setSolutionBuilderClass(SequentialFeasibleMPDPTW.class);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 17364.22   IT. WORST: 22124.68    IT. BEST: 17364.22    IT. MEAN: 19739.02    IT. SD: 969.48    BRANCH FACTOR: 0.990     DIV: 0.98      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 16707.86   IT. WORST: 22212.62    IT. BEST: 16707.86    IT. MEAN: 19575.32    IT. SD: 1251.90   BRANCH FACTOR: 1.865     DIV: 0.96      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 16707.86   IT. WORST: 21160.09    IT. BEST: 17150.12    IT. MEAN: 19251.82    IT. SD: 1055.28   BRANCH FACTOR: 0.990     DIV: 0.91      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 16610.43   IT. WORST: 21797.75    IT. BEST: 16610.43    IT. MEAN: 18590.18    IT. SD: 973.76    BRANCH FACTOR: 1.385     DIV: 0.79      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 16610.43   IT. WORST: 19375.35    IT. BEST: 16646.12    IT. MEAN: 17872.71    IT. SD: 662.90    BRANCH FACTOR: 0.990     DIV: 0.55      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 16542.72   IT. WORST: 19769.93    IT. BEST: 16542.72    IT. MEAN: 17683.80    IT. SD: 757.73    BRANCH FACTOR: 1.087     DIV: 0.43      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 15910.98   IT. WORST: 18773.36    IT. BEST: 15910.98    IT. MEAN: 17254.79    IT. SD: 522.42    BRANCH FACTOR: 1.135     DIV: 0.30      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 15910.98   IT. WORST: 17764.54    IT. BEST: 15910.98    IT. MEAN: 16239.55    IT. SD: 482.93    BRANCH FACTOR: 0.990     DIV: 0.10      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 15910.98   IT. WORST: 18073.38    IT. BEST: 15910.98    IT. MEAN: 16085.48    IT. SD: 494.69    BRANCH FACTOR: 0.990     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 15910.98   IT. WORST: 16613.62    IT. BEST: 15910.98    IT. MEAN: 15925.03    IT. SD: 99.37     BRANCH FACTOR: 0.990     DIV: 0.00      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = w_4_100_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-50-44-5-3-97-52-13-51-14-4-46-45-98-99-53-6-47-0\n" +
                "0-31-42-57-41-63-43-30-32-58-64-0\n" +
                "0-19-61-62-59-18-60-75-76-20-21-36-37-0\n" +
                "0-67-85-70-73-74-66-86-69-87-65-68-71-72-0\n" +
                "0-23-1-54-22-24-55-56-91-25-92-93-2-0\n" +
                "0-48-101-102-49-100-16-103-15-17-0\n" +
                "0-78-38-82-39-40-77-79-83-84-0\n" +
                "0-7-8-9-11-88-10-89-12-90-0\n" +
                "0-33-94-80-27-28-26-95-34-35-96-81-29-0\n" +
                "Requests\n" +
                "16-14-1-33-4\n" +
                "9-13-18-21\n" +
                "6-20-19-25-11\n" +
                "22-29-23-24\n" +
                "7-0-17-31\n" +
                "15-34-5\n" +
                "26-12-28\n" +
                "2-3-30\n" +
                "10-32-27-8\n" +
                "Num. Vehicles = 9\n" +
                "Cost = 15910.97914089543\n" +
                "Penalty = 0.0");
    }

    @Test
    public void mpdptw_sequential_infeasible_test() throws IOException {
        String problem = "w_4_100_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 10, seed, 0.8, 1, true);
        solver.setSolutionBuilderClass(SequentialInfeasible.class);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 22564.36   IT. WORST: 26127.46    IT. BEST: 22564.36    IT. MEAN: 24409.79    IT. SD: 921.66    BRANCH FACTOR: 0.990     DIV: 1.00      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 22564.36   IT. WORST: 25870.07    IT. BEST: 22078.00    IT. MEAN: 24046.70    IT. SD: 945.56    BRANCH FACTOR: 0.990     DIV: 0.97      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 22564.36   IT. WORST: 25982.70    IT. BEST: 21686.59    IT. MEAN: 23842.73    IT. SD: 954.11    BRANCH FACTOR: 0.990     DIV: 0.93      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 21346.57   IT. WORST: 25229.86    IT. BEST: 21346.57    IT. MEAN: 23446.02    IT. SD: 892.78    BRANCH FACTOR: 1.692     DIV: 0.83      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 20703.30   IT. WORST: 24025.82    IT. BEST: 20703.30    IT. MEAN: 22296.72    IT. SD: 832.76    BRANCH FACTOR: 1.481     DIV: 0.69      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 19857.02   IT. WORST: 23505.07    IT. BEST: 19857.02    IT. MEAN: 21353.49    IT. SD: 794.86    BRANCH FACTOR: 1.144     DIV: 0.47      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 19332.89   IT. WORST: 22824.29    IT. BEST: 19332.89    IT. MEAN: 20215.10    IT. SD: 642.85    BRANCH FACTOR: 1.183     DIV: 0.22      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 19118.76   IT. WORST: 21169.88    IT. BEST: 19118.76    IT. MEAN: 19612.95    IT. SD: 417.12    BRANCH FACTOR: 1.087     DIV: 0.14      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 19118.76   IT. WORST: 22254.56    IT. BEST: 18725.66    IT. MEAN: 19914.66    IT. SD: 888.68    BRANCH FACTOR: 0.990     DIV: 0.29      PEN. RATE: 1.00    ");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 18764.82   IT. WORST: 22621.65    IT. BEST: 18764.82    IT. MEAN: 20039.15    IT. SD: 948.97    BRANCH FACTOR: 1.096     DIV: 0.32      PEN. RATE: 1.00    ");
        assertThat(solver.getFinalSolution()).contains("Instance = w_4_100_1.txt\n" +
                "Best solution feasibility = false\n" +
                "Routes\n" +
                "0-54-66-78-77-23-7-67-55-79-8-22-24-65-56-68-25-0\n" +
                "0-31-97-33-73-74-30-32-16-98-99-34-35-15-17-0\n" +
                "0-83-36-37-39-38-10-59-11-9-60-12-40-82-84-0\n" +
                "0-70-92-91-93-69-71-72-51-13-52-14-50-53-0\n" +
                "0-75-76-3-1-42-41-43-2-4-5-6-0\n" +
                "0-63-19-18-57-58-64-20-21-88-89-90-0\n" +
                "0-85-86-87-27-94-46-26-95-96-45-44-47-28-29-0\n" +
                "0-48-100-101-102-80-61-81-62-103-49-0\n" +
                "Requests\n" +
                "17-22-26-7-2\n" +
                "9-33-10-24-5\n" +
                "28-11-12-3-19\n" +
                "23-31-16-4\n" +
                "25-1-0-13\n" +
                "21-6-18-30\n" +
                "29-8-32-14\n" +
                "15-34-27-20\n" +
                "Num. Vehicles = 8\n" +
                "Cost = 18764.824102864066\n" +
                "Penalty = 11600.052786421777");
    }

    @Test
    public void mpdptw_large_4_25_1_test() throws Exception {
        String problem = "l_4_25_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 20, seed, 0.2, 2, true);
        solver.setSolutionBuilderClass(SequentialFeasibleMPDPTW.class);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 2       BSF: 5212.75    IT. WORST: 6935.02     IT. BEST: 5558.84     IT. MEAN: 5981.13     IT. SD: 321.59    BRANCH FACTOR: 0.962     DIV: 0.62      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 4       BSF: 4677.73    IT. WORST: 6465.59     IT. BEST: 4677.73     IT. MEAN: 5821.37     IT. SD: 384.39    BRANCH FACTOR: 1.577     DIV: 0.60      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 6       BSF: 4677.73    IT. WORST: 6785.71     IT. BEST: 5163.57     IT. MEAN: 5908.00     IT. SD: 354.57    BRANCH FACTOR: 1.577     DIV: 0.61      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 8       BSF: 4677.73    IT. WORST: 6752.86     IT. BEST: 4677.73     IT. MEAN: 5678.89     IT. SD: 480.74    BRANCH FACTOR: 1.577     DIV: 0.59      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 10      BSF: 4654.24    IT. WORST: 6192.06     IT. BEST: 4708.40     IT. MEAN: 5487.16     IT. SD: 378.27    BRANCH FACTOR: 1.808     DIV: 0.57      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 12      BSF: 4585.85    IT. WORST: 6271.95     IT. BEST: 4585.85     IT. MEAN: 5346.04     IT. SD: 443.60    BRANCH FACTOR: 1.808     DIV: 0.50      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 14      BSF: 4585.85    IT. WORST: 6370.41     IT. BEST: 4585.85     IT. MEAN: 5304.48     IT. SD: 421.30    BRANCH FACTOR: 1.346     DIV: 0.46      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 16      BSF: 4585.85    IT. WORST: 5865.27     IT. BEST: 4585.85     IT. MEAN: 5001.26     IT. SD: 385.31    BRANCH FACTOR: 1.269     DIV: 0.37      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 18      BSF: 4585.85    IT. WORST: 5725.30     IT. BEST: 4585.85     IT. MEAN: 4828.74     IT. SD: 313.52    BRANCH FACTOR: 1.269     DIV: 0.24      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 20      BSF: 4585.85    IT. WORST: 5689.37     IT. BEST: 4585.85     IT. MEAN: 4815.63     IT. SD: 335.30    BRANCH FACTOR: 1.077     DIV: 0.23      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = l_4_25_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-7-5-3-1-13-14-8-4-6-2-0\n" +
                "0-15-18-16-19-20-21-17-0\n" +
                "0-23-11-22-24-25-10-9-12-0\n" +
                "Requests\n" +
                "2-1-0-4\n" +
                "5-6\n" +
                "7-3\n" +
                "Num. Vehicles = 3\n" +
                "Cost = 4585.850993238565\n" +
                "Penalty = 0.0");

    }

    @Test
    public void mpdptw_normal_8_50_1_test() throws Exception {
        String problem = "n_8_50_1.txt";
        ProblemInstance instance = DataReader.getMpdptwInstance(Paths.get(rootDirectory, problem).toFile());
        Solver solver = new Solver(problem, instance, 30, seed, 0.2, 5, true);
        solver.setSolutionBuilderClass(SequentialFeasibleMPDPTW.class);
        solver.setParallel(false);
        solver.run();
        List<IterationStatistic> iterationStatistics = solver.getIterationStatistics();
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 5       BSF: 7818.02    IT. WORST: 9046.88     IT. BEST: 7818.02     IT. MEAN: 8565.49     IT. SD: 306.77    BRANCH FACTOR: 1.608     DIV: 0.36      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 10      BSF: 7744.09    IT. WORST: 8854.22     IT. BEST: 7747.37     IT. MEAN: 8248.88     IT. SD: 314.45    BRANCH FACTOR: 1.647     DIV: 0.34      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 15      BSF: 7635.73    IT. WORST: 8691.44     IT. BEST: 7652.42     IT. MEAN: 7938.34     IT. SD: 244.72    BRANCH FACTOR: 1.569     DIV: 0.29      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 20      BSF: 7631.97    IT. WORST: 8174.85     IT. BEST: 7631.97     IT. MEAN: 7717.19     IT. SD: 143.08    BRANCH FACTOR: 1.314     DIV: 0.13      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 25      BSF: 7631.97    IT. WORST: 7831.65     IT. BEST: 7631.97     IT. MEAN: 7649.45     IT. SD: 36.63     BRANCH FACTOR: 1.118     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 30      BSF: 7631.97    IT. WORST: 7674.45     IT. BEST: 7631.97     IT. MEAN: 7634.22     IT. SD: 8.65      BRANCH FACTOR: 1.039     DIV: 0.01      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = n_8_50_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-29-33-31-32-28-30-34-0\n" +
                "0-36-35-37-39-38-40-41-0\n" +
                "0-25-26-24-27-0\n" +
                "0-22-11-19-18-12-17-20-21-23-0\n" +
                "0-5-8-6-3-4-1-9-2-7-10-0\n" +
                "0-42-43-49-47-45-44-46-50-48-0\n" +
                "0-15-13-14-16-0\n" +
                "Requests\n" +
                "6\n" +
                "7\n" +
                "5\n" +
                "4-2\n" +
                "1-0\n" +
                "8-9\n" +
                "3\n" +
                "Num. Vehicles = 7\n" +
                "Cost = 7631.966003915072\n" +
                "Penalty = 0.0");
    }
}
