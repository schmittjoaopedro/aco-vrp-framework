package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
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
        assertThat(logs.get(0).toString()).isEqualTo("IT. 1       BSF: 905.10     BSF. SD: 0.00    IT. WORST: 3922.52     IT. BEST: 905.10      IT. MEAN: 3361.86     IT. SD: 408.71    BRANCH FACTOR: 0.990     DIV: 0.85      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 2       BSF: 905.10     BSF. SD: 0.00    IT. WORST: 3885.60     IT. BEST: 1172.83     IT. MEAN: 3378.32     IT. SD: 387.65    BRANCH FACTOR: 0.990     DIV: 0.85      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 3       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3960.15     IT. BEST: 827.86      IT. MEAN: 3275.45     IT. SD: 423.51    BRANCH FACTOR: 1.038     DIV: 0.85      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 4       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3622.10     IT. BEST: 827.86      IT. MEAN: 3187.08     IT. SD: 393.71    BRANCH FACTOR: 1.038     DIV: 0.85      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 5       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3770.95     IT. BEST: 886.54      IT. MEAN: 3203.72     IT. SD: 403.19    BRANCH FACTOR: 1.038     DIV: 0.83      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 6       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3732.69     IT. BEST: 850.03      IT. MEAN: 3135.73     IT. SD: 398.55    BRANCH FACTOR: 1.038     DIV: 0.83      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 7       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3493.57     IT. BEST: 978.41      IT. MEAN: 3081.37     IT. SD: 363.87    BRANCH FACTOR: 1.038     DIV: 0.81      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 8       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3592.06     IT. BEST: 913.43      IT. MEAN: 3060.20     IT. SD: 369.19    BRANCH FACTOR: 1.038     DIV: 0.81      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 9       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3577.77     IT. BEST: 902.73      IT. MEAN: 2912.10     IT. SD: 413.71    BRANCH FACTOR: 1.038     DIV: 0.79      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 10      BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3538.21     IT. BEST: 827.86      IT. MEAN: 2843.75     IT. SD: 398.48    BRANCH FACTOR: 1.038     DIV: 0.79      ");
        assertThat(solver.getFinalSolution()).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-5-3-7-8-10-11-9-6-4-2-1-75-0\n" +
                "0-67-65-62-74-72-61-64-68-66-69-0\n" +
                "0-32-33-31-35-104-37-38-39-36-34-29-28-0\n" +
                "0-43-42-41-40-44-46-101-45-48-51-50-52-49-47-0\n" +
                "0-13-17-18-19-15-16-14-12-0\n" +
                "0-20-24-25-27-30-26-103-23-22-21-0\n" +
                "0-81-78-76-71-70-73-77-79-80-63-0\n" +
                "0-98-96-95-94-92-93-102-97-100-99-0\n" +
                "0-90-87-86-83-82-84-85-88-89-91-0\n" +
                "0-57-55-54-53-56-58-60-59-0\n" +
                "Requests\n" +
                "3-5-2-0-4-1\n" +
                "34-33-32-35-38\n" +
                "19-20-17-15-18-16\n" +
                "22-21-27-24-25-23-26\n" +
                "6-7-9-8\n" +
                "12-13-14-10-11\n" +
                "39-40-41-36-37\n" +
                "47-48-49-50-51\n" +
                "44-46-43-45-42\n" +
                "28-31-29-30\n" +
                "Num. Vehicles = 10\n" +
                "Cost = 827.8646991698506\n" +
                "Penalty = 0.0\n" +
                "Total time (ms) = ");

    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, 100, seed, 0.2, 10, true);
        solver.setLsActive(true);
        solver.run();
        List<IterationStatistic> logs = solver.getIterationStatistics();
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 1550.93    BSF. SD: 0.00    IT. WORST: 3252.26     IT. BEST: 1599.35     IT. MEAN: 2627.90     IT. SD: 237.35    BRANCH FACTOR: 1.561     DIV: 0.65      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 1435.94    BSF. SD: 0.00    IT. WORST: 2650.52     IT. BEST: 1559.03     IT. MEAN: 2126.18     IT. SD: 201.97    BRANCH FACTOR: 1.692     DIV: 0.54      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 1376.48    BSF. SD: 0.00    IT. WORST: 1976.93     IT. BEST: 1376.48     IT. MEAN: 1672.46     IT. SD: 121.71    BRANCH FACTOR: 1.215     DIV: 0.28      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1830.46     IT. BEST: 1347.54     IT. MEAN: 1504.68     IT. SD: 95.31     BRANCH FACTOR: 1.112     DIV: 0.18      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1659.89     IT. BEST: 1347.54     IT. MEAN: 1473.68     IT. SD: 65.76     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1637.15     IT. BEST: 1347.54     IT. MEAN: 1479.14     IT. SD: 59.83     BRANCH FACTOR: 0.991     DIV: 0.15      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1665.82     IT. BEST: 1347.54     IT. MEAN: 1477.33     IT. SD: 64.37     BRANCH FACTOR: 0.991     DIV: 0.16      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1704.51     IT. BEST: 1361.48     IT. MEAN: 1462.36     IT. SD: 61.87     BRANCH FACTOR: 0.991     DIV: 0.15      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1688.40     IT. BEST: 1347.54     IT. MEAN: 1466.04     IT. SD: 67.21     BRANCH FACTOR: 0.991     DIV: 0.15      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 1347.54    BSF. SD: 0.00    IT. WORST: 1585.08     IT. BEST: 1347.54     IT. MEAN: 1451.81     IT. SD: 46.24     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(solver.getFinalSolution()).contains("Instance = lrc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-61-42-43-44-40-38-41-72-71-93-94-80-0\n" +
                "0-81-39-36-35-37-54-103-96-0\n" +
                "0-20-18-48-21-23-22-49-19-25-24-57-101-0\n" +
                "0-33-32-28-102-26-29-31-34-0\n" +
                "0-82-14-15-11-9-87-59-74-86-52-0\n" +
                "0-69-88-53-12-10-13-16-17-47-104-0\n" +
                "0-27-30-84-106-56-66-0\n" +
                "0-92-62-50-67-0\n" +
                "0-65-83-64-51-76-89-63-85-95-91-0\n" +
                "0-98-60-73-78-79-7-46-4-100-70-0\n" +
                "0-2-45-1-3-5-8-6-55-68-105-0\n" +
                "0-90-99-97-75-58-77-0\n" +
                "Requests\n" +
                "33-22-23-20-21-37\n" +
                "43-19-18-29\n" +
                "9-8-10-11-12-31\n" +
                "17-16-14-15\n" +
                "44-7-4-32-39\n" +
                "36-28-6-5-25\n" +
                "13-46-30\n" +
                "48-26\n" +
                "34-45-27-41-49\n" +
                "51-38-42-3-52\n" +
                "1-24-0-2-35\n" +
                "47-50-40\n" +
                "Num. Vehicles = 12\n" +
                "Cost = 1347.5384416664247\n" +
                "Penalty = 0.0\n" +
                "Total time (ms) = ");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        Solver solver = new Solver(problem, instance, 100, seed, 0.2, 10, true);
        solver.setLsActive(true);
        solver.run();
        List<IterationStatistic> logs = solver.getIterationStatistics();
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 9155.05     IT. BEST: 2704.57     IT. MEAN: 7432.07     IT. SD: 950.14    BRANCH FACTOR: 0.995     DIV: 0.60      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 5443.93     IT. BEST: 2704.57     IT. MEAN: 3845.83     IT. SD: 608.58    BRANCH FACTOR: 0.995     DIV: 0.24      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3971.00     IT. BEST: 2704.57     IT. MEAN: 3037.91     IT. SD: 255.47    BRANCH FACTOR: 0.995     DIV: 0.09      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3748.40     IT. BEST: 2704.57     IT. MEAN: 2993.11     IT. SD: 144.19    BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3199.70     IT. BEST: 2704.57     IT. MEAN: 2942.02     IT. SD: 98.54     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3095.75     IT. BEST: 2704.57     IT. MEAN: 2963.33     IT. SD: 76.95     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3161.72     IT. BEST: 2704.57     IT. MEAN: 2955.60     IT. SD: 91.53     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3148.29     IT. BEST: 2704.57     IT. MEAN: 2971.01     IT. SD: 83.30     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3074.37     IT. BEST: 2704.57     IT. MEAN: 2932.53     IT. SD: 94.34     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3129.72     IT. BEST: 2704.57     IT. MEAN: 2961.08     IT. SD: 94.87     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(solver.getFinalSolution()).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-20-41-85-80-31-25-172-77-110-162-0\n" +
                "0-148-103-197-203-124-141-69-200-0\n" +
                "0-62-131-44-102-146-208-68-76-0\n" +
                "0-60-211-82-180-84-191-125-4-72-17-0\n" +
                "0-30-120-19-192-196-97-14-96-130-28-74-149-0\n" +
                "0-114-159-38-150-22-151-16-140-204-187-142-111-63-56-0\n" +
                "0-170-134-50-156-112-168-79-205-29-87-42-123-0\n" +
                "0-21-23-182-75-163-194-145-195-52-92-0\n" +
                "0-190-5-10-193-46-128-106-167-207-34-95-158-0\n" +
                "0-73-116-12-129-11-6-122-139-0\n" +
                "0-101-144-119-166-35-126-71-9-1-99-53-201-0\n" +
                "0-113-155-78-175-13-43-2-90-67-39-107-212-0\n" +
                "0-164-210-66-147-160-47-91-70-0\n" +
                "0-133-48-26-152-40-153-169-89-105-15-59-198-0\n" +
                "0-57-118-83-143-176-36-206-33-121-165-188-108-0\n" +
                "0-45-178-27-173-154-209-24-61-100-64-179-109-0\n" +
                "0-32-171-65-86-115-94-51-174-136-189-0\n" +
                "0-177-3-88-8-186-127-98-157-137-183-0\n" +
                "0-161-104-18-54-185-132-7-181-117-49-0\n" +
                "0-93-55-135-58-202-184-199-37-81-138-0\n" +
                "Requests\n" +
                "13-26-17-47-51\n" +
                "104-80-67-74\n" +
                "58-28-38-78\n" +
                "1-50-100-48-37\n" +
                "101-65-56-68-19-9\n" +
                "73-75-15-11-62-86-81\n" +
                "46-71-92-91-85-32\n" +
                "44-103-14-16-98\n" +
                "2-102-6-22-90-99\n" +
                "3-43-64-7\n" +
                "57-33-77-42-5-89\n" +
                "27-8-60-45-84-61\n" +
                "79-30-41-88\n" +
                "25-70-31-82-54-10\n" +
                "76-49-66-21-23-35\n" +
                "94-96-83-39-18-29\n" +
                "20-40-93-52-63\n" +
                "0-95-72-53-4\n" +
                "97-12-87-69-59\n" +
                "55-34-24-105-36\n" +
                "Num. Vehicles = 20\n" +
                "Cost = 2704.5677663304737\n" +
                "Penalty = 0.0\n" +
                "Total time (ms) =");
    }
}
