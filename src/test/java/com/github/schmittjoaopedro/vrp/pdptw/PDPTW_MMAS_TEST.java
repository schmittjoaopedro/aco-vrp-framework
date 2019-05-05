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
        assertThat(logs.get(0).toString()).isEqualTo("IT. 1       BSF: 919.02     BSF. SD: 0.00    IT. WORST: 3922.52     IT. BEST: 919.02      IT. MEAN: 3354.29     IT. SD: 408.40    BRANCH FACTOR: 0.990     DIV: 0.85      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 2       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3757.92     IT. BEST: 827.86      IT. MEAN: 3312.74     IT. SD: 405.26    BRANCH FACTOR: 1.105     DIV: 0.84      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 3       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3867.05     IT. BEST: 853.31      IT. MEAN: 3310.80     IT. SD: 420.99    BRANCH FACTOR: 1.105     DIV: 0.85      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 4       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3723.51     IT. BEST: 981.03      IT. MEAN: 3165.87     IT. SD: 370.83    BRANCH FACTOR: 1.105     DIV: 0.84      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 5       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3887.03     IT. BEST: 827.86      IT. MEAN: 3181.34     IT. SD: 442.16    BRANCH FACTOR: 1.105     DIV: 0.84      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 6       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3883.04     IT. BEST: 827.86      IT. MEAN: 3156.53     IT. SD: 402.17    BRANCH FACTOR: 1.105     DIV: 0.84      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 7       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3433.05     IT. BEST: 875.48      IT. MEAN: 3082.15     IT. SD: 380.02    BRANCH FACTOR: 1.105     DIV: 0.82      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 8       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3721.73     IT. BEST: 827.86      IT. MEAN: 3028.16     IT. SD: 390.29    BRANCH FACTOR: 0.990     DIV: 0.81      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 9       BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3436.07     IT. BEST: 853.31      IT. MEAN: 2840.81     IT. SD: 354.59    BRANCH FACTOR: 0.990     DIV: 0.78      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 10      BSF: 827.86     BSF. SD: 0.00    IT. WORST: 3557.89     IT. BEST: 827.86      IT. MEAN: 2826.03     IT. SD: 414.30    BRANCH FACTOR: 0.990     DIV: 0.78      ");
        assertThat(solver.getFinalSolution()).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-90-87-86-83-82-84-85-88-89-91-0\n" +
                "0-98-96-95-94-92-93-102-97-100-99-0\n" +
                "0-43-42-41-40-44-46-101-45-48-51-50-52-49-47-0\n" +
                "0-67-65-62-74-72-61-64-68-66-69-0\n" +
                "0-32-33-31-35-104-37-38-39-36-34-29-28-0\n" +
                "0-5-3-7-8-10-11-9-6-4-2-1-75-0\n" +
                "0-81-78-76-71-70-73-77-79-80-63-0\n" +
                "0-20-24-25-27-30-26-103-23-22-21-0\n" +
                "0-13-17-18-19-15-16-14-12-0\n" +
                "0-57-55-54-53-56-58-60-59-0\n" +
                "Requests\n" +
                "43-44-45-46-42\n" +
                "51-47-48-50-49\n" +
                "21-22-23-24-25-26-27\n" +
                "32-33-34-35-38\n" +
                "15-16-18-19-20-17\n" +
                "0-1-2-3-4-5\n" +
                "36-37-39-40-41\n" +
                "10-11-12-13-14\n" +
                "6-7-8-9\n" +
                "28-29-30-31\n" +
                "Num. Vehicles = 10\n" +
                "Cost = 827.8646991698504\n" +
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
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 1494.46    BSF. SD: 0.00    IT. WORST: 3111.35     IT. BEST: 1495.85     IT. MEAN: 2708.52     IT. SD: 232.88    BRANCH FACTOR: 1.720     DIV: 0.68      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 1432.78    BSF. SD: 0.00    IT. WORST: 2549.50     IT. BEST: 1432.78     IT. MEAN: 1971.23     IT. SD: 244.58    BRANCH FACTOR: 1.243     DIV: 0.46      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 2017.75     IT. BEST: 1360.74     IT. MEAN: 1611.05     IT. SD: 145.07    BRANCH FACTOR: 1.103     DIV: 0.24      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1635.12     IT. BEST: 1360.74     IT. MEAN: 1481.26     IT. SD: 58.44     BRANCH FACTOR: 0.991     DIV: 0.15      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1747.08     IT. BEST: 1360.74     IT. MEAN: 1501.88     IT. SD: 81.77     BRANCH FACTOR: 0.991     DIV: 0.16      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1583.99     IT. BEST: 1368.10     IT. MEAN: 1479.93     IT. SD: 53.58     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1668.61     IT. BEST: 1360.74     IT. MEAN: 1481.09     IT. SD: 68.18     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1769.28     IT. BEST: 1368.10     IT. MEAN: 1483.04     IT. SD: 77.75     BRANCH FACTOR: 1.028     DIV: 0.14      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1823.76     IT. BEST: 1360.74     IT. MEAN: 1488.58     IT. SD: 97.88     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 1360.74    BSF. SD: 0.00    IT. WORST: 1673.82     IT. BEST: 1360.74     IT. MEAN: 1486.79     IT. SD: 65.50     BRANCH FACTOR: 0.991     DIV: 0.14      ");
        assertThat(solver.getFinalSolution()).contains("Instance = lrc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-98-60-73-78-79-7-46-4-100-70-0\n" +
                "0-20-18-48-21-23-22-49-19-25-24-57-101-0\n" +
                "0-92-62-50-67-0\n" +
                "0-61-42-44-43-38-41-72-54-103-80-0\n" +
                "0-69-88-53-12-10-13-16-17-47-104-0\n" +
                "0-27-30-84-106-56-66-0\n" +
                "0-81-39-36-40-35-37-93-96-0\n" +
                "0-65-83-64-51-76-89-63-85-95-91-0\n" +
                "0-2-45-1-3-5-8-6-55-68-105-0\n" +
                "0-90-99-97-75-58-77-0\n" +
                "0-82-14-15-11-9-87-59-74-86-52-0\n" +
                "0-33-32-28-102-26-29-31-34-71-94-0\n" +
                "Requests\n" +
                "3-38-51-42-52\n" +
                "10-11-8-9-12-31\n" +
                "26-48\n" +
                "23-33-21-22-29\n" +
                "5-6-25-28-36\n" +
                "13-46-30\n" +
                "20-19-43-18\n" +
                "34-41-27-45-49\n" +
                "24-0-1-2-35\n" +
                "47-50-40\n" +
                "4-7-44-32-39\n" +
                "17-14-15-16-37\n" +
                "Num. Vehicles = 12\n" +
                "Cost = 1360.7396501885041\n" +
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
        assertThat(logs.get(0).toString()).isEqualTo("IT. 10      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 9224.86     IT. BEST: 2704.57     IT. MEAN: 7212.31     IT. SD: 1023.94   BRANCH FACTOR: 0.995     DIV: 0.60      ");
        assertThat(logs.get(1).toString()).isEqualTo("IT. 20      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 5017.15     IT. BEST: 2704.57     IT. MEAN: 3805.19     IT. SD: 569.19    BRANCH FACTOR: 0.995     DIV: 0.24      ");
        assertThat(logs.get(2).toString()).isEqualTo("IT. 30      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 4005.43     IT. BEST: 2704.57     IT. MEAN: 3031.96     IT. SD: 255.88    BRANCH FACTOR: 0.995     DIV: 0.09      ");
        assertThat(logs.get(3).toString()).isEqualTo("IT. 40      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3748.40     IT. BEST: 2704.57     IT. MEAN: 2994.94     IT. SD: 147.62    BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(4).toString()).isEqualTo("IT. 50      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3199.70     IT. BEST: 2704.57     IT. MEAN: 2942.02     IT. SD: 98.54     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(5).toString()).isEqualTo("IT. 60      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3095.75     IT. BEST: 2704.57     IT. MEAN: 2963.33     IT. SD: 76.95     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(6).toString()).isEqualTo("IT. 70      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3161.72     IT. BEST: 2704.57     IT. MEAN: 2955.60     IT. SD: 91.53     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(7).toString()).isEqualTo("IT. 80      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3148.29     IT. BEST: 2704.57     IT. MEAN: 2971.01     IT. SD: 83.30     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(8).toString()).isEqualTo("IT. 90      BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3074.37     IT. BEST: 2704.57     IT. MEAN: 2932.53     IT. SD: 94.34     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(logs.get(9).toString()).isEqualTo("IT. 100     BSF: 2704.57    BSF. SD: 0.00    IT. WORST: 3129.72     IT. BEST: 2704.57     IT. MEAN: 2961.08     IT. SD: 94.87     BRANCH FACTOR: 0.995     DIV: 0.07      ");
        assertThat(solver.getFinalSolution()).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-170-134-50-156-112-168-79-205-29-87-42-123-0\n" +
                "0-30-120-19-192-196-97-14-96-130-28-74-149-0\n" +
                "0-60-211-82-180-84-191-125-4-72-17-0\n" +
                "0-113-155-78-175-13-43-2-90-67-39-107-212-0\n" +
                "0-190-5-10-193-46-128-106-167-207-34-95-158-0\n" +
                "0-93-55-135-58-202-184-199-37-81-138-0\n" +
                "0-57-118-83-143-176-36-206-33-121-165-188-108-0\n" +
                "0-101-144-119-166-35-126-71-9-1-99-53-201-0\n" +
                "0-20-41-85-80-31-25-172-77-110-162-0\n" +
                "0-133-48-26-152-40-153-169-89-105-15-59-198-0\n" +
                "0-164-210-66-147-160-47-91-70-0\n" +
                "0-32-171-65-86-115-94-51-174-136-189-0\n" +
                "0-177-3-88-8-186-127-98-157-137-183-0\n" +
                "0-62-131-44-102-146-208-68-76-0\n" +
                "0-148-103-197-203-124-141-69-200-0\n" +
                "0-73-116-12-129-11-6-122-139-0\n" +
                "0-114-159-38-150-22-151-16-140-204-187-142-111-63-56-0\n" +
                "0-161-104-18-54-185-132-7-181-117-49-0\n" +
                "0-21-23-182-75-163-194-145-195-52-92-0\n" +
                "0-45-178-27-173-154-209-24-61-100-64-179-109-0\n" +
                "Requests\n" +
                "32-91-85-46-71-92\n" +
                "101-56-65-68-19-9\n" +
                "100-50-48-37-1\n" +
                "8-60-27-61-84-45\n" +
                "102-22-2-6-90-99\n" +
                "24-34-105-55-36\n" +
                "35-76-66-21-23-49\n" +
                "5-57-77-89-42-33\n" +
                "47-17-13-26-51\n" +
                "70-82-25-54-10-31\n" +
                "30-79-41-88\n" +
                "20-40-93-52-63\n" +
                "95-0-4-53-72\n" +
                "58-28-78-38\n" +
                "74-67-104-80\n" +
                "7-64-43-3\n" +
                "73-75-11-62-86-15-81\n" +
                "69-87-97-59-12\n" +
                "16-98-103-14-44\n" +
                "18-83-39-94-96-29\n" +
                "Num. Vehicles = 20\n" +
                "Cost = 2704.5677663304737\n" +
                "Penalty = 0.0");
    }
}
