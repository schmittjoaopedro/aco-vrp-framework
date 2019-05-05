package com.github.schmittjoaopedro.vrp.pdptw;

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
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 1 BFS = 850.0302242304471, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 3 BFS = 827.8646991698505, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 2300 BFS = 827.8646991698504, feasible = true");
        assertThat(logs.get(3)).contains("Instance = lc103.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-90-87-86-83-82-84-85-88-89-91-0\n" +
                "0-81-78-76-71-70-73-77-79-80-63-0\n" +
                "0-98-96-95-94-92-93-102-97-100-99-0\n" +
                "0-13-17-18-19-15-16-14-12-0\n" +
                "0-20-24-25-27-30-26-103-23-22-21-0\n" +
                "0-32-33-31-35-104-37-38-39-36-34-29-28-0\n" +
                "0-43-42-41-40-44-46-101-45-48-51-50-52-49-47-0\n" +
                "0-5-3-7-8-10-11-9-6-4-2-1-75-0\n" +
                "0-57-55-54-53-56-58-60-59-0\n" +
                "0-67-65-62-74-72-61-64-68-66-69-0\n" +
                "Requests\n" +
                "46-44-45-43-42\n" +
                "40-41-37-36-39\n" +
                "51-49-48-50-47\n" +
                "6-8-9-7\n" +
                "12-11-10-14-13\n" +
                "20-16-19-17-18-15\n" +
                "21-22-23-25-24-27-26\n" +
                "1-4-3-0-2-5\n" +
                "28-29-31-30\n" +
                "33-34-38-35-32\n" +
                "Cost = 827.8646991698504\n" +
                "Num. vehicles = 10");
    }

    @Test
    public void pdptw_lrc102_test() throws IOException {
        String problem = "lrc102.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw100Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 1 BFS = 1846.0425611930614, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 2 BFS = 1709.9232131740312, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 5 BFS = 1662.5768561781263, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 235 BFS = 1661.213186734334, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 244 BFS = 1654.231677125334, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 367 BFS = 1622.3334499560463, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 399 BFS = 1611.721915305937, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 500 BFS = 1595.1971752585712, feasible = true");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 1218 BFS = 1574.5150862397754, feasible = true");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 1677 BFS = 1563.5504554244733, feasible = true");
        assertThat(logs.get(10)).contains("Instance = lrc102.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-82-11-73-79-7-55-100-70-0\n" +
                "0-96-106-72-104-38-41-40-43-35-37-0\n" +
                "0-91-64-99-86-57-74-59-52-0\n" +
                "0-42-36-44-81-54-68-0\n" +
                "0-92-95-62-29-30-32-103-89-0\n" +
                "0-39-71-67-84-56-66-0\n" +
                "0-61-90-0\n" +
                "0-69-88-53-78-10-13-17-12-0\n" +
                "0-33-28-27-26-31-34-50-93-94-80-0\n" +
                "0-14-47-15-16-9-87-97-75-102-58-0\n" +
                "0-65-83-19-23-18-48-21-25-77-105-0\n" +
                "0-2-45-1-3-5-8-101-6-46-4-60-98-0\n" +
                "0-85-63-76-51-22-49-20-24-0\n" +
                "Requests\n" +
                "42-52-40-4\n" +
                "20-18-50-35-21\n" +
                "31-51-47-28\n" +
                "17-22-41\n" +
                "14-13-48-30\n" +
                "34-27-19\n" +
                "29\n" +
                "46-39-33-5\n" +
                "12-16-11-49-15\n" +
                "36-25-6-7-45\n" +
                "9-8-32-43-38\n" +
                "0-23-1-24-2-3\n" +
                "37-44-10-26\n" +
                "Cost = 1563.5504554244733\n" +
                "Num. vehicles = 13");
    }

    @Test
    public void pdptw_LC1_2_1_test() throws IOException {
        String problem = "LC1_2_1.txt";
        ProblemInstance instance = DataReader.getPdptwInstance(Paths.get(pdptw200Directory, problem).toFile());
        ALNS alns = new ALNS(instance, maxIterations, new Random(1));
        alns.execute();
        List<String> logs = alns.getLog();
        assertThat(logs.get(0)).isEqualTo("NEW BEST = Iter 1 BFS = 3708.5361392022273, feasible = true");
        assertThat(logs.get(1)).isEqualTo("NEW BEST = Iter 2 BFS = 3181.643817017193, feasible = true");
        assertThat(logs.get(2)).isEqualTo("NEW BEST = Iter 3 BFS = 3122.5601781883315, feasible = true");
        assertThat(logs.get(3)).isEqualTo("NEW BEST = Iter 4 BFS = 2909.126888314069, feasible = true");
        assertThat(logs.get(4)).isEqualTo("NEW BEST = Iter 14 BFS = 2870.6530014460327, feasible = true");
        assertThat(logs.get(5)).isEqualTo("NEW BEST = Iter 18 BFS = 2750.9150892715834, feasible = true");
        assertThat(logs.get(6)).isEqualTo("NEW BEST = Iter 37 BFS = 2747.091113571079, feasible = true");
        assertThat(logs.get(7)).isEqualTo("NEW BEST = Iter 40 BFS = 2704.567766330474, feasible = true");
        assertThat(logs.get(8)).isEqualTo("NEW BEST = Iter 400 BFS = 2704.5677663304737, feasible = true");
        assertThat(logs.get(9)).isEqualTo("NEW BEST = Iter 1500 BFS = 2704.567766330473, feasible = true");
        assertThat(logs.get(10)).contains("Instance = LC1_2_1.txt\n" +
                "Best solution feasibility = true\n" +
                "Routes\n" +
                "0-177-3-88-8-186-127-98-157-137-183-0\n" +
                "0-113-155-78-175-13-43-2-90-67-39-107-212-0\n" +
                "0-20-41-85-80-31-25-172-77-110-162-0\n" +
                "0-21-23-182-75-163-194-145-195-52-92-0\n" +
                "0-45-178-27-173-154-209-24-61-100-64-179-109-0\n" +
                "0-60-211-82-180-84-191-125-4-72-17-0\n" +
                "0-133-48-26-152-40-153-169-89-105-15-59-198-0\n" +
                "0-62-131-44-102-146-208-68-76-0\n" +
                "0-190-5-10-193-46-128-106-167-207-34-95-158-0\n" +
                "0-57-118-83-143-176-36-206-33-121-165-188-108-0\n" +
                "0-170-134-50-156-112-168-79-205-29-87-42-123-0\n" +
                "0-161-104-18-54-185-132-7-181-117-49-0\n" +
                "0-101-144-119-166-35-126-71-9-1-99-53-201-0\n" +
                "0-73-116-12-129-11-6-122-139-0\n" +
                "0-30-120-19-192-196-97-14-96-130-28-74-149-0\n" +
                "0-32-171-65-86-115-94-51-174-136-189-0\n" +
                "0-148-103-197-203-124-141-69-200-0\n" +
                "0-93-55-135-58-202-184-199-37-81-138-0\n" +
                "0-164-210-66-147-160-47-91-70-0\n" +
                "0-114-159-38-150-22-151-16-140-204-187-142-111-63-56-0\n" +
                "Requests\n" +
                "95-0-4-53-72\n" +
                "60-27-8-84-61-45\n" +
                "51-17-13-47-26\n" +
                "14-44-16-98-103\n" +
                "39-94-96-83-18-29\n" +
                "1-37-48-100-50\n" +
                "70-82-31-25-54-10\n" +
                "38-78-28-58\n" +
                "6-102-99-90-22-2\n" +
                "49-76-23-21-35-66\n" +
                "71-32-92-91-46-85\n" +
                "97-12-69-87-59\n" +
                "57-77-42-5-89-33\n" +
                "3-64-43-7\n" +
                "68-19-56-101-65-9\n" +
                "40-63-20-93-52\n" +
                "80-74-67-104\n" +
                "105-24-34-55-36\n" +
                "88-79-30-41\n" +
                "62-86-73-75-11-81-15\n" +
                "Cost = 2704.567766330473\n" +
                "Num. vehicles = 20");
    }
}
