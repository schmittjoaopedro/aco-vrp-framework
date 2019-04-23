package com.github.schmittjoaopedro.vrp.pdptw;

import com.github.schmittjoaopedro.tsp.tools.IterationStatistic;
import com.github.schmittjoaopedro.vrp.dvrptw.DVRPTW_ACS_Test;
import com.github.schmittjoaopedro.vrp.mpdptw.DataReader;
import com.github.schmittjoaopedro.vrp.mpdptw.ProblemInstance;
import com.github.schmittjoaopedro.vrp.mpdptw.alns.ALNS;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class PDPTW_ALNS_TEST {

    int maxIterations = 10000;

    private static final String pdptw100Directory;

    private static final String pdptw200Directory;

    static {
        pdptw100Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(DVRPTW_ACS_Test.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
    }

    @Test
    public void pdptw_lc103_test() throws IOException {
        String problem = "lc103.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 3 BFS = 866.2187926090526, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 8 BFS = 827.8646991698505, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 1500 BFS = 827.8646991698504, feasible = true");
        assertThat(logs.get(3)).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-32-33-31-35-104-37-38-39-36-34-29-28-0\n" +
                "0-81-78-76-71-70-73-77-79-80-63-0\n" +
                "0-98-96-95-94-92-93-102-97-100-99-0\n" +
                "0-90-87-86-83-82-84-85-88-89-91-0\n" +
                "0-43-42-41-40-44-46-101-45-48-51-50-52-49-47-0\n" +
                "0-5-3-7-8-10-11-9-6-4-2-1-75-0\n" +
                "0-57-55-54-53-56-58-60-59-0\n" +
                "0-67-65-62-74-72-61-64-68-66-69-0\n" +
                "0-13-17-18-19-15-16-14-12-0\n" +
                "0-20-24-25-27-30-26-103-23-22-21-0\n" +
                "Requests\n" +
                "19-16-18-17-20-15\n" +
                "41-37-39-40-36\n" +
                "48-49-47-50-51\n" +
                "46-45-43-44-42\n" +
                "25-22-21-23-26-24-27\n" +
                "3-1-2-4-0-5\n" +
                "29-31-30-28\n" +
                "35-38-32-33-34\n" +
                "6-9-8-7\n" +
                "12-10-14-13-11\n" +
                "Cost = 827.8646991698504\n" +
                "Num. vehicles = 10\n" +
                "Total time (s) = ");
    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 1 BFS = 1846.0425611930614, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 49 BFS = 1772.4975353520224, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 51 BFS = 1745.5114987995514, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 206 BFS = 1678.8990495061578, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 207 BFS = 1649.5172848831412, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 1009 BFS = 1629.544545395455, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 4662 BFS = 1627.6462097833737, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 6100 BFS = 1623.7357488805214, feasible = true");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 6669 BFS = 1574.5150862397754, feasible = true");
        assertThat(logs.get(10)).contains("Instance = lrc102.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-85-63-76-51-22-49-20-24-0\n" +
                "0-65-83-19-23-21-18-48-25-77-105-0\n" +
                "0-14-47-15-16-9-87-97-75-102-58-0\n" +
                "0-2-45-1-3-5-8-101-6-46-4-60-98-0\n" +
                "0-92-95-62-29-30-32-103-89-0\n" +
                "0-33-28-27-26-31-34-50-93-94-80-0\n" +
                "0-36-38-44-40-35-37-72-104-96-106-0\n" +
                "0-91-64-99-86-57-74-59-52-0\n" +
                "0-42-41-43-68-0\n" +
                "0-69-88-53-78-10-13-17-12-0\n" +
                "0-82-11-73-79-7-55-100-70-0\n" +
                "0-39-71-67-84-56-66-0\n" +
                "0-61-90-0\n" +
                "0-81-54-0\n" +
                "Requests\n" +
                "37-26-44-10\n" +
                "32-38-43-9-8\n" +
                "7-6-25-45-36\n" +
                "0-23-1-24-3-2\n" +
                "14-48-30-13\n" +
                "11-16-12-15-49\n" +
                "50-18-35-20-17\n" +
                "28-31-51-47\n" +
                "21-22\n" +
                "39-5-46-33\n" +
                "40-52-42-4\n" +
                "34-19-27\n" +
                "29\n" +
                "41\n" +
                "Cost = 1574.5150862397754\n" +
                "Num. vehicles = 14\n" +
                "Total time (s) = ");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 1 BFS = 3609.63444252752, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 2 BFS = 3466.633103897806, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 3 BFS = 3292.4725138168624, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 6 BFS = 3069.979722975791, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 9 BFS = 2914.0989881480077, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 13 BFS = 2704.5677663304737, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 100 BFS = 2704.567766330473, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 7700 BFS = 2704.5677663304728, feasible = true");
        assertThat(logs.get(8)).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-60-211-82-180-84-191-125-4-72-17-0\n" +
                "0-30-120-19-192-196-97-14-96-130-28-74-149-0\n" +
                "0-45-178-27-173-154-209-24-61-100-64-179-109-0\n" +
                "0-32-171-65-86-115-94-51-174-136-189-0\n" +
                "0-21-23-182-75-163-194-145-195-52-92-0\n" +
                "0-101-144-119-166-35-126-71-9-1-99-53-201-0\n" +
                "0-133-48-26-152-40-153-169-89-105-15-59-198-0\n" +
                "0-93-55-135-58-202-184-199-37-81-138-0\n" +
                "0-57-118-83-143-176-36-206-33-121-165-188-108-0\n" +
                "0-190-5-10-193-46-128-106-167-207-34-95-158-0\n" +
                "0-177-3-88-8-186-127-98-157-137-183-0\n" +
                "0-170-134-50-156-112-168-79-205-29-87-42-123-0\n" +
                "0-113-155-78-175-13-43-2-90-67-39-107-212-0\n" +
                "0-73-116-12-129-11-6-122-139-0\n" +
                "0-148-103-197-203-124-141-69-200-0\n" +
                "0-20-41-85-80-31-25-172-77-110-162-0\n" +
                "0-62-131-44-102-146-208-68-76-0\n" +
                "0-164-210-66-147-160-47-91-70-0\n" +
                "0-161-104-18-54-185-132-7-181-117-49-0\n" +
                "0-114-159-38-150-22-151-16-140-204-187-142-111-63-56-0\n" +
                "Requests\n" +
                "37-48-50-100-1\n" +
                "101-19-68-65-56-9\n" +
                "94-18-39-96-83-29\n" +
                "20-40-63-52-93\n" +
                "14-98-44-16-103\n" +
                "77-5-57-42-89-33\n" +
                "70-10-54-25-31-82\n" +
                "105-24-36-55-34\n" +
                "76-66-23-35-49-21\n" +
                "6-2-102-22-90-99\n" +
                "95-0-4-53-72\n" +
                "46-92-91-85-32-71\n" +
                "60-45-27-8-84-61\n" +
                "3-43-7-64\n" +
                "74-67-104-80\n" +
                "26-47-17-51-13\n" +
                "58-78-38-28\n" +
                "41-79-88-30\n" +
                "87-12-59-69-97\n" +
                "11-62-86-73-15-81-75\n" +
                "Cost = 2704.5677663304728\n" +
                "Num. vehicles = 20\n" +
                "Total time (s) = ");
    }
}
