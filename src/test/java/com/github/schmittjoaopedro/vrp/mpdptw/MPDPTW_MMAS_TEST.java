package com.github.schmittjoaopedro.vrp.mpdptw;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
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
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 17412.37   BSF. SD: 0.00    IT. WORST: 21685.26    IT. BEST: 17412.37    IT. MEAN: 19670.08    IT. SD: 1043.76   BRANCH FACTOR: 0.990     DIV: 0.98      ");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 17058.92   BSF. SD: 0.00    IT. WORST: 22574.03    IT. BEST: 17058.92    IT. MEAN: 19985.31    IT. SD: 1128.29   BRANCH FACTOR: 1.827     DIV: 0.96      ");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 17058.92   BSF. SD: 0.00    IT. WORST: 21291.34    IT. BEST: 17267.36    IT. MEAN: 19465.14    IT. SD: 970.42    BRANCH FACTOR: 0.990     DIV: 0.92      ");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 15633.10   BSF. SD: 0.00    IT. WORST: 21219.29    IT. BEST: 15633.10    IT. MEAN: 18601.03    IT. SD: 1083.70   BRANCH FACTOR: 1.721     DIV: 0.77      ");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 15633.10   BSF. SD: 0.00    IT. WORST: 19715.72    IT. BEST: 15681.73    IT. MEAN: 17630.24    IT. SD: 1081.70   BRANCH FACTOR: 0.990     DIV: 0.64      ");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 15303.60   BSF. SD: 0.00    IT. WORST: 18539.47    IT. BEST: 15303.60    IT. MEAN: 16323.91    IT. SD: 803.66    BRANCH FACTOR: 1.048     DIV: 0.26      ");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 15303.60   BSF. SD: 0.00    IT. WORST: 17611.99    IT. BEST: 15303.60    IT. MEAN: 16060.29    IT. SD: 624.92    BRANCH FACTOR: 0.990     DIV: 0.26      ");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 17434.81    IT. BEST: 15202.22    IT. MEAN: 15729.94    IT. SD: 477.22    BRANCH FACTOR: 1.038     DIV: 0.16      ");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 17562.19    IT. BEST: 15202.22    IT. MEAN: 15787.15    IT. SD: 450.24    BRANCH FACTOR: 0.990     DIV: 0.21      ");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 15202.22   BSF. SD: 0.00    IT. WORST: 16774.05    IT. BEST: 15202.22    IT. MEAN: 15738.64    IT. SD: 431.42    BRANCH FACTOR: 0.990     DIV: 0.18      ");
        assertThat(solver.getFinalSolution()).contains("Instance = w_4_100_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-48-44-16-49-15-46-17-7-45-8-47-0\n" +
                "0-19-94-27-18-28-26-95-96-20-21-36-37-29-0\n" +
                "0-1-51-102-101-2-100-63-50-52-103-64-53-0\n" +
                "0-91-42-92-93-86-69-85-41-87-71-70-43-72-0\n" +
                "0-59-57-60-75-76-13-14-58-31-30-73-74-32-0\n" +
                "0-24-10-11-22-38-9-54-23-39-12-55-40-25-56-0\n" +
                "0-80-67-33-97-81-66-98-99-34-35-65-68-0\n" +
                "0-78-88-5-77-79-3-4-89-90-6-0\n" +
                "0-83-82-84-61-62-0\n" +
                "Requests\n" +
                "15-14-5-2\n" +
                "6-32-8-11\n" +
                "0-16-34-21\n" +
                "31-13-29-23\n" +
                "19-18-25-4-9-24\n" +
                "7-3-12-17\n" +
                "27-22-10-33\n" +
                "26-30-1\n" +
                "28-20\n" +
                "Num. Vehicles = 9\n" +
                "Cost = 15202.218798841075\n" +
                "Penalty = 0.0\n" +
                "Total time (ms) = ");
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
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 1       BSF: 22062.96   BSF. SD: 0.00    IT. WORST: 26443.83    IT. BEST: 22062.96    IT. MEAN: 24131.55    IT. SD: 1011.14   BRANCH FACTOR: 0.990     DIV: 1.00      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 2       BSF: 20657.80   BSF. SD: 0.00    IT. WORST: 26862.76    IT. BEST: 20657.80    IT. MEAN: 23953.56    IT. SD: 1110.20   BRANCH FACTOR: 1.952     DIV: 0.99      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 3       BSF: 20657.80   BSF. SD: 0.00    IT. WORST: 26222.27    IT. BEST: 21375.75    IT. MEAN: 23350.67    IT. SD: 1075.56   BRANCH FACTOR: 0.990     DIV: 0.95      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 4       BSF: 20115.41   BSF. SD: 0.00    IT. WORST: 25204.62    IT. BEST: 20115.41    IT. MEAN: 22693.02    IT. SD: 1049.13   BRANCH FACTOR: 1.452     DIV: 0.86      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 5       BSF: 20115.41   BSF. SD: 0.00    IT. WORST: 23581.92    IT. BEST: 19739.14    IT. MEAN: 21374.74    IT. SD: 1002.66   BRANCH FACTOR: 0.990     DIV: 0.63      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 6       BSF: 19090.57   BSF. SD: 0.00    IT. WORST: 22568.50    IT. BEST: 19090.57    IT. MEAN: 20913.70    IT. SD: 840.14    BRANCH FACTOR: 1.317     DIV: 0.39      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 7       BSF: 18922.97   BSF. SD: 0.00    IT. WORST: 22477.40    IT. BEST: 18922.97    IT. MEAN: 19748.68    IT. SD: 800.59    BRANCH FACTOR: 1.221     DIV: 0.34      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 8       BSF: 18533.95   BSF. SD: 0.00    IT. WORST: 20698.53    IT. BEST: 18533.95    IT. MEAN: 19145.43    IT. SD: 480.64    BRANCH FACTOR: 1.125     DIV: 0.14      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 9       BSF: 18533.95   BSF. SD: 0.00    IT. WORST: 21083.77    IT. BEST: 18398.31    IT. MEAN: 18805.72    IT. SD: 561.75    BRANCH FACTOR: 0.990     DIV: 0.11      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 10      BSF: 17887.38   BSF. SD: 0.00    IT. WORST: 18881.26    IT. BEST: 17887.38    IT. MEAN: 18527.96    IT. SD: 104.68    BRANCH FACTOR: 1.202     DIV: 0.02      PEN. RATE: 1.00    FEASIBLE: F");
        assertThat(solver.getFinalSolution()).contains("Instance = w_4_100_1.txt\n" +
                "Best solution feasibility = false\n" +
                "Routes\n" +
                "0-30-91-73-74-83-82-59-57-38-58-31-84-39-32-92-93-40-60-0\n" +
                "0-80-42-54-55-41-56-43-81-36-37-0\n" +
                "0-98-69-101-48-1-97-2-100-102-70-49-99-71-72-103-0\n" +
                "0-23-52-20-50-19-18-21-51-53-24-22-25-0\n" +
                "0-63-33-16-15-7-8-64-34-35-17-0\n" +
                "0-5-78-77-79-9-10-11-28-4-27-12-26-29-3-6-0\n" +
                "0-65-46-45-66-61-44-47-67-86-62-68-85-87-0\n" +
                "0-95-75-76-13-14-88-94-96-89-90-0\n" +
                "Requests\n" +
                "9-31-24-28-19-18-12\n" +
                "27-13-17-11\n" +
                "33-23-34-15-0\n" +
                "7-16-6\n" +
                "21-10-5-2\n" +
                "1-26-3-8\n" +
                "22-14-20-29\n" +
                "32-25-4-30\n" +
                "Num. Vehicles = 8\n" +
                "Cost = 17887.379592436664\n" +
                "Penalty = 9823.56395480934\n" +
                "Total time (ms) = ");
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
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 2       BSF: 5212.75    BSF. SD: 0.00    IT. WORST: 6935.02     IT. BEST: 5558.84     IT. MEAN: 5981.13     IT. SD: 321.59    BRANCH FACTOR: 0.962     DIV: 0.62      ");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 4       BSF: 4677.73    BSF. SD: 0.00    IT. WORST: 6465.59     IT. BEST: 4677.73     IT. MEAN: 5821.37     IT. SD: 384.39    BRANCH FACTOR: 1.577     DIV: 0.60      ");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 6       BSF: 4677.73    BSF. SD: 0.00    IT. WORST: 6785.71     IT. BEST: 5163.57     IT. MEAN: 5908.00     IT. SD: 354.57    BRANCH FACTOR: 1.577     DIV: 0.61      ");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 8       BSF: 4677.73    BSF. SD: 0.00    IT. WORST: 6752.86     IT. BEST: 4677.73     IT. MEAN: 5678.89     IT. SD: 480.74    BRANCH FACTOR: 1.577     DIV: 0.59      ");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 10      BSF: 4654.24    BSF. SD: 0.00    IT. WORST: 6192.06     IT. BEST: 4708.40     IT. MEAN: 5487.16     IT. SD: 378.27    BRANCH FACTOR: 1.808     DIV: 0.57      ");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 12      BSF: 4585.85    BSF. SD: 0.00    IT. WORST: 6271.95     IT. BEST: 4585.85     IT. MEAN: 5346.04     IT. SD: 443.60    BRANCH FACTOR: 1.808     DIV: 0.50      ");
        assertThat(iterationStatistics.get(6).toString()).isEqualTo("IT. 14      BSF: 4585.85    BSF. SD: 0.00    IT. WORST: 6370.41     IT. BEST: 4585.85     IT. MEAN: 5304.48     IT. SD: 421.30    BRANCH FACTOR: 1.346     DIV: 0.46      ");
        assertThat(iterationStatistics.get(7).toString()).isEqualTo("IT. 16      BSF: 4585.85    BSF. SD: 0.00    IT. WORST: 5865.27     IT. BEST: 4585.85     IT. MEAN: 5001.26     IT. SD: 385.31    BRANCH FACTOR: 1.269     DIV: 0.37      ");
        assertThat(iterationStatistics.get(8).toString()).isEqualTo("IT. 18      BSF: 4585.85    BSF. SD: 0.00    IT. WORST: 5725.30     IT. BEST: 4585.85     IT. MEAN: 4828.74     IT. SD: 313.52    BRANCH FACTOR: 1.269     DIV: 0.24      ");
        assertThat(iterationStatistics.get(9).toString()).isEqualTo("IT. 20      BSF: 4585.85    BSF. SD: 0.00    IT. WORST: 5689.37     IT. BEST: 4585.85     IT. MEAN: 4815.63     IT. SD: 335.30    BRANCH FACTOR: 1.077     DIV: 0.23      ");
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
                "Penalty = 0.0\n" +
                "Total time (ms) = ");

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
        assertThat(iterationStatistics.get(0).toString()).isEqualTo("IT. 5       BSF: 7818.02    BSF. SD: 0.00    IT. WORST: 9046.88     IT. BEST: 7818.02     IT. MEAN: 8565.49     IT. SD: 306.77    BRANCH FACTOR: 1.608     DIV: 0.36      ");
        assertThat(iterationStatistics.get(1).toString()).isEqualTo("IT. 10      BSF: 7744.09    BSF. SD: 0.00    IT. WORST: 8854.22     IT. BEST: 7747.37     IT. MEAN: 8248.88     IT. SD: 314.45    BRANCH FACTOR: 1.647     DIV: 0.34      ");
        assertThat(iterationStatistics.get(2).toString()).isEqualTo("IT. 15      BSF: 7635.73    BSF. SD: 0.00    IT. WORST: 8691.44     IT. BEST: 7652.42     IT. MEAN: 7938.34     IT. SD: 244.72    BRANCH FACTOR: 1.569     DIV: 0.29      ");
        assertThat(iterationStatistics.get(3).toString()).isEqualTo("IT. 20      BSF: 7631.97    BSF. SD: 0.00    IT. WORST: 8174.85     IT. BEST: 7631.97     IT. MEAN: 7717.19     IT. SD: 143.08    BRANCH FACTOR: 1.314     DIV: 0.13      ");
        assertThat(iterationStatistics.get(4).toString()).isEqualTo("IT. 25      BSF: 7631.97    BSF. SD: 0.00    IT. WORST: 7831.65     IT. BEST: 7631.97     IT. MEAN: 7649.45     IT. SD: 36.63     BRANCH FACTOR: 1.118     DIV: 0.07      ");
        assertThat(iterationStatistics.get(5).toString()).isEqualTo("IT. 30      BSF: 7631.97    BSF. SD: 0.00    IT. WORST: 7674.45     IT. BEST: 7631.97     IT. MEAN: 7634.22     IT. SD: 8.65      BRANCH FACTOR: 1.039     DIV: 0.01      ");
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
                "Penalty = 0.0\n" +
                "Total time (ms) = ");
    }
}
