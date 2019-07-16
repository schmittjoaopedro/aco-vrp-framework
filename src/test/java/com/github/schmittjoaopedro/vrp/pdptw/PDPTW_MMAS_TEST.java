package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.statistic.IterationStatistic;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.Solver;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_MMAS_TEST {

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    private static int seed = 1;

    static {
        pdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_lc103_test() throws IOException {
        String problem = "lc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, 10, seed, 0.2, 1, true);
        solver.setLsActive(true);
        solver.run();
        List<IterationStatistic> logs = solver.getIterationStatistics();
        assertThat(logs.get(0).toString()).isEqualTo("IT. 1       BSF: 992.50     IT. WORST: 3922.52     IT. BEST: 992.50      IT. MEAN: 3355.76     IT. SD: 399.49    BRANCH FACTOR: 0.990     DIV: 0.85      FEASIBLE: 1.0");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 2       BSF: 827.86     IT. WORST: 3840.88     IT. BEST: 827.86      IT. MEAN: 3339.21     IT. SD: 423.05    BRANCH FACTOR: 1.152     DIV: 0.84      FEASIBLE: 1.0");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 3       BSF: 827.86     IT. WORST: 3737.75     IT. BEST: 919.72      IT. MEAN: 3297.56     IT. SD: 395.80    BRANCH FACTOR: 1.152     DIV: 0.84      FEASIBLE: 1.0");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 4       BSF: 827.86     IT. WORST: 3521.65     IT. BEST: 827.86      IT. MEAN: 3157.83     IT. SD: 383.71    BRANCH FACTOR: 1.152     DIV: 0.84      FEASIBLE: 1.0");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 5       BSF: 827.86     IT. WORST: 3756.15     IT. BEST: 827.86      IT. MEAN: 3196.43     IT. SD: 412.28    BRANCH FACTOR: 1.152     DIV: 0.83      FEASIBLE: 1.0");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 6       BSF: 827.86     IT. WORST: 3883.04     IT. BEST: 862.74      IT. MEAN: 3142.73     IT. SD: 400.17    BRANCH FACTOR: 1.152     DIV: 0.84      FEASIBLE: 1.0");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 7       BSF: 827.86     IT. WORST: 3537.93     IT. BEST: 827.86      IT. MEAN: 3097.74     IT. SD: 387.27    BRANCH FACTOR: 1.152     DIV: 0.81      FEASIBLE: 1.0");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 8       BSF: 827.86     IT. WORST: 3592.06     IT. BEST: 827.86      IT. MEAN: 3010.59     IT. SD: 379.16    BRANCH FACTOR: 0.990     DIV: 0.81      FEASIBLE: 1.0");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 9       BSF: 827.86     IT. WORST: 3476.35     IT. BEST: 827.86      IT. MEAN: 2891.77     IT. SD: 381.58    BRANCH FACTOR: 0.990     DIV: 0.79      FEASIBLE: 1.0");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 10      BSF: 827.86     IT. WORST: 3538.21     IT. BEST: 862.74      IT. MEAN: 2807.01     IT. SD: 396.25    BRANCH FACTOR: 0.990     DIV: 0.78      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-5-3-7-8-10-11-9-6-4-2-1-75-0\n" +
                "0-98-96-95-94-92-93-102-97-100-99-0\n" +
                "0-81-78-76-71-70-73-77-79-80-63-0\n" +
                "0-20-24-25-27-30-26-103-23-22-21-0\n" +
                "0-32-33-31-35-104-37-38-39-36-34-29-28-0\n" +
                "0-67-65-62-74-72-61-64-68-66-69-0\n" +
                "0-43-42-41-40-44-46-101-45-48-51-50-52-49-47-0\n" +
                "0-90-87-86-83-82-84-85-88-89-91-0\n" +
                "0-13-17-18-19-15-16-14-12-0\n" +
                "0-57-55-54-53-56-58-60-59-0\n" +
                "Requests\n" +
                "1-4-3-5-2-0\n" +
                "49-48-47-51-50\n" +
                "39-40-41-37-36\n" +
                "12-14-10-11-13\n" +
                "17-19-18-20-16-15\n" +
                "32-35-34-33-38\n" +
                "25-22-21-24-23-26-27\n" +
                "45-44-46-43-42\n" +
                "7-6-8-9\n" +
                "31-28-29-30\n" +
                "Num. Vehicles = 10\n" +
                "Cost = 827.8646991698505\n" +
                "Penalty = 0.0");

    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, 100, seed, 0.2, 10, true);
        solver.setLsActive(true);
        solver.run();
        List<IterationStatistic> logs = solver.getIterationStatistics();
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 1374.88    IT. WORST: 3059.26     IT. BEST: 1528.56     IT. MEAN: 2541.32     IT. SD: 235.65    BRANCH FACTOR: 0.991     DIV: 0.66      FEASIBLE: 1.0");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 1360.74    IT. WORST: 2470.83     IT. BEST: 1360.74     IT. MEAN: 1847.80     IT. SD: 240.42    BRANCH FACTOR: 1.000     DIV: 0.39      FEASIBLE: 1.0");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 1347.54    IT. WORST: 1945.53     IT. BEST: 1352.96     IT. MEAN: 1573.44     IT. SD: 133.91    BRANCH FACTOR: 1.140     DIV: 0.24      FEASIBLE: 1.0");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 1347.54    IT. WORST: 1690.96     IT. BEST: 1347.54     IT. MEAN: 1477.89     IT. SD: 72.23     BRANCH FACTOR: 1.019     DIV: 0.17      FEASIBLE: 1.0");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 1347.54    IT. WORST: 1766.62     IT. BEST: 1347.54     IT. MEAN: 1466.10     IT. SD: 69.21     BRANCH FACTOR: 0.991     DIV: 0.16      FEASIBLE: 1.0");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 1347.54    IT. WORST: 1686.24     IT. BEST: 1347.54     IT. MEAN: 1470.75     IT. SD: 64.61     BRANCH FACTOR: 0.991     DIV: 0.15      FEASIBLE: 1.0");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 1347.54    IT. WORST: 1596.68     IT. BEST: 1347.54     IT. MEAN: 1458.98     IT. SD: 56.54     BRANCH FACTOR: 0.991     DIV: 0.15      FEASIBLE: 1.0");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 1347.54    IT. WORST: 1696.75     IT. BEST: 1347.54     IT. MEAN: 1476.47     IT. SD: 75.20     BRANCH FACTOR: 0.991     DIV: 0.16      FEASIBLE: 1.0");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 1347.54    IT. WORST: 1688.40     IT. BEST: 1347.54     IT. MEAN: 1468.59     IT. SD: 75.89     BRANCH FACTOR: 0.991     DIV: 0.15      FEASIBLE: 1.0");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 1347.54    IT. WORST: 1626.26     IT. BEST: 1347.54     IT. MEAN: 1466.67     IT. SD: 56.27     BRANCH FACTOR: 0.991     DIV: 0.16      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = lrc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-65-83-64-51-76-89-63-85-95-91-0\n" +
                "0-20-18-48-21-23-22-49-19-25-24-57-101-0\n" +
                "0-69-88-53-12-10-13-16-17-47-104-0\n" +
                "0-81-39-36-35-37-54-103-96-0\n" +
                "0-27-30-84-106-56-66-0\n" +
                "0-82-14-15-11-9-87-59-74-86-52-0\n" +
                "0-98-60-73-78-79-7-46-4-100-70-0\n" +
                "0-2-45-1-3-5-8-6-55-68-105-0\n" +
                "0-61-42-43-44-40-38-41-72-71-93-94-80-0\n" +
                "0-33-32-28-102-26-29-31-34-0\n" +
                "0-92-62-50-67-0\n" +
                "0-90-99-97-75-58-77-0\n" +
                "Requests\n" +
                "34-45-27-41-49\n" +
                "9-8-10-11-12-31\n" +
                "36-28-6-5-25\n" +
                "43-19-18-29\n" +
                "13-46-30\n" +
                "44-7-4-32-39\n" +
                "51-38-42-3-52\n" +
                "1-24-0-2-35\n" +
                "33-22-23-20-21-37\n" +
                "17-16-14-15\n" +
                "48-26\n" +
                "47-50-40\n" +
                "Num. Vehicles = 12\n" +
                "Cost = 1347.538441666425\n" +
                "Penalty = 0.0");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, 100, seed, 0.2, 10, true);
        solver.setLsActive(true);
        solver.run();
        List<IterationStatistic> logs = solver.getIterationStatistics();
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 2704.57    IT. WORST: 9350.90     IT. BEST: 2750.92     IT. MEAN: 7313.19     IT. SD: 1132.64   BRANCH FACTOR: 0.995     DIV: 0.61      FEASIBLE: 1.0");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 2704.57    IT. WORST: 5017.15     IT. BEST: 2704.57     IT. MEAN: 3767.39     IT. SD: 555.12    BRANCH FACTOR: 0.995     DIV: 0.23      FEASIBLE: 1.0");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 2704.57    IT. WORST: 4005.43     IT. BEST: 2704.57     IT. MEAN: 3039.63     IT. SD: 259.22    BRANCH FACTOR: 0.995     DIV: 0.09      FEASIBLE: 1.0");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 2704.57    IT. WORST: 3748.40     IT. BEST: 2704.57     IT. MEAN: 2994.94     IT. SD: 147.62    BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 2704.57    IT. WORST: 3199.70     IT. BEST: 2704.57     IT. MEAN: 2942.02     IT. SD: 98.54     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 2704.57    IT. WORST: 3095.75     IT. BEST: 2704.57     IT. MEAN: 2963.33     IT. SD: 76.95     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 2704.57    IT. WORST: 3161.72     IT. BEST: 2704.57     IT. MEAN: 2955.60     IT. SD: 91.53     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 2704.57    IT. WORST: 3148.29     IT. BEST: 2704.57     IT. MEAN: 2971.01     IT. SD: 83.30     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 2704.57    IT. WORST: 3074.37     IT. BEST: 2704.57     IT. MEAN: 2932.53     IT. SD: 94.34     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 2704.57    IT. WORST: 3129.72     IT. BEST: 2704.57     IT. MEAN: 2961.08     IT. SD: 94.87     BRANCH FACTOR: 0.995     DIV: 0.07      FEASIBLE: 1.0");
        assertThat(solver.getFinalSolution()).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-57-118-83-143-176-36-206-33-121-165-188-108-0\n" +
                "0-30-120-19-192-196-97-14-96-130-28-74-149-0\n" +
                "0-114-159-38-150-22-151-16-140-204-187-142-111-63-56-0\n" +
                "0-21-23-182-75-163-194-145-195-52-92-0\n" +
                "0-161-104-18-54-185-132-7-181-117-49-0\n" +
                "0-62-131-44-102-146-208-68-76-0\n" +
                "0-133-48-26-152-40-153-169-89-105-15-59-198-0\n" +
                "0-60-211-82-180-84-191-125-4-72-17-0\n" +
                "0-164-210-66-147-160-47-91-70-0\n" +
                "0-113-155-78-175-13-43-2-90-67-39-107-212-0\n" +
                "0-177-3-88-8-186-127-98-157-137-183-0\n" +
                "0-101-144-119-166-35-126-71-9-1-99-53-201-0\n" +
                "0-170-134-50-156-112-168-79-205-29-87-42-123-0\n" +
                "0-20-41-85-80-31-25-172-77-110-162-0\n" +
                "0-73-116-12-129-11-6-122-139-0\n" +
                "0-32-171-65-86-115-94-51-174-136-189-0\n" +
                "0-190-5-10-193-46-128-106-167-207-34-95-158-0\n" +
                "0-45-178-27-173-154-209-24-61-100-64-179-109-0\n" +
                "0-148-103-197-203-124-141-69-200-0\n" +
                "0-93-55-135-58-202-184-199-37-81-138-0\n" +
                "Requests\n" +
                "49-76-21-35-23-66\n" +
                "56-65-101-68-19-9\n" +
                "62-73-81-86-75-11-15\n" +
                "103-44-98-16-14\n" +
                "59-69-87-97-12\n" +
                "58-78-28-38\n" +
                "70-54-82-25-31-10\n" +
                "48-50-100-37-1\n" +
                "41-79-88-30\n" +
                "45-84-61-27-8-60\n" +
                "53-95-0-4-72\n" +
                "42-57-77-89-5-33\n" +
                "46-71-85-91-92-32\n" +
                "51-47-26-17-13\n" +
                "43-3-64-7\n" +
                "52-63-40-93-20\n" +
                "102-99-2-90-6-22\n" +
                "83-94-96-39-18-29\n" +
                "67-74-104-80\n" +
                "105-55-24-34-36\n" +
                "Num. Vehicles = 20\n" +
                "Cost = 2704.567766330474\n" +
                "Penalty = 0.0");
    }
}
