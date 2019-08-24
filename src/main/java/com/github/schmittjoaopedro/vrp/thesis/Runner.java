package com.github.schmittjoaopedro.vrp.thesis;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;
import java.util.Random;

public class Runner {

    private static int maxIterations = 25000;

    private static final String pdptw100Directory;
    private static final String pdptw200Directory;
    private static final String pdptw400Directory;
    private static final String pdptw600Directory;
    private static final String pdptw800Directory;
    private static final String pdptw1000Directory;

    private static String[] instances_100 = {
            "lc101", "lc102", "lc103", "lc104", "lc105", "lc106", "lc107", "lc108", "lc109",
            "lc201", "lc202", "lc203", "lc204", "lc205", "lc206", "lc207", "lc208",
            "lr101", "lr102", "lr103", "lr104", "lr105", "lr106", "lr107", "lr108", "lr109", "lr110", "lr111", "lr112",
            "lr201", "lr202", "lr203", "lr204", "lr205", "lr206", "lr207", "lr208", "lr209", "lr210", "lr211",
            "lrc101", "lrc102", "lrc103", "lrc104", "lrc105", "lrc106", "lrc107", "lrc108",
            "lrc201", "lrc202", "lrc203", "lrc204", "lrc205", "lrc206", "lrc207", "lrc208"
    };

    private static String[] instances_200 = {
            "lc1_2_1", "lc1_2_2", "lc1_2_3", "lc1_2_4", "lc1_2_5", "lc1_2_6", "lc1_2_7", "lc1_2_8", "lc1_2_9", "lc1_2_10",
            "lc2_2_1", "lc2_2_2", "lc2_2_3", "lc2_2_4", "lc2_2_5", "lc2_2_6", "lc2_2_7", "lc2_2_8", "lc2_2_9", "lc2_2_10",
            "lr1_2_1", "lr1_2_2", "lr1_2_3", "lr1_2_4", "lr1_2_5", "lr1_2_6", "lr1_2_7", "lr1_2_8", "lr1_2_9", "lr1_2_10",
            "lr2_2_1", "lr2_2_2", "lr2_2_3", "lr2_2_4", "lr2_2_5", "lr2_2_6", "lr2_2_7", "lr2_2_8", "lr2_2_9", "lr2_2_10",
            "lrc1_2_1", "lrc1_2_2", "lrc1_2_3", "lrc1_2_4", "lrc1_2_5", "lrc1_2_6", "lrc1_2_7", "lrc1_2_8", "lrc1_2_9", "lrc1_2_10",
            "lrc2_2_1", "lrc2_2_2", "lrc2_2_3", "lrc2_2_4", "lrc2_2_5", "lrc2_2_6", "lrc2_2_7", "lrc2_2_8", "lrc2_2_9", "lrc2_2_10"
    };

    private static String[] instances_400 = {
            "lc1_4_1", "lc1_4_2", "lc1_4_3", "lc1_4_4", "lc1_4_5", "lc1_4_6", "lc1_4_7", "lc1_4_8", "lc1_4_9", "lc1_4_10",
            "lc2_4_1", "lc2_4_2", "lc2_4_3", "lc2_4_4", "lc2_4_5", "lc2_4_6", "lc2_4_7", "lc2_4_8", "lc2_4_9", "lc2_4_10",
            "lr1_4_1", "lr1_4_2", "lr1_4_3", "lr1_4_4", "lr1_4_5", "lr1_4_6", "lr1_4_7", "lr1_4_8", "lr1_4_9", "lr1_4_10",
            "lr2_4_1", "lr2_4_2", "lr2_4_3", "lr2_4_4", "lr2_4_5", "lr2_4_6", "lr2_4_7", "lr2_4_8", "lr2_4_9", "lr2_4_10",
            "lrc1_4_1", "lrc1_4_2", "lrc1_4_3", "lrc1_4_4", "lrc1_4_5", "lrc1_4_6", "lrc1_4_7", "lrc1_4_8", "lrc1_4_9", "lrc1_4_10",
            "lrc2_4_1", "lrc2_4_2", "lrc2_4_3", "lrc2_4_4", "lrc2_4_5", "lrc2_4_6", "lrc2_4_7", "lrc2_4_8", "lrc2_4_9", "lrc2_4_10"
    };

    private static String[] instances_600 = {
            "lc1_6_1", "lc1_6_2", "lc1_6_3", "lc1_6_4", "lc1_6_5", "lc1_6_6", "lc1_6_7", "lc1_6_8", "lc1_6_9", "lc1_6_10",
            "lc2_6_1", "lc2_6_2", "lc2_6_3", "lc2_6_4", "lc2_6_5", "lc2_6_6", "lc2_6_7", "lc2_6_8", "lc2_6_9", "lc2_6_10",
            "lr1_6_1", "lr1_6_2", "lr1_6_3", "lr1_6_4", "lr1_6_5", "lr1_6_6", "lr1_6_7", "lr1_6_8", "lr1_6_9", "lr1_6_10",
            "lr2_6_1", "lr2_6_2", "lr2_6_3", "lr2_6_4", "lr2_6_5", "lr2_6_6", "lr2_6_7", "lr2_6_8", "lr2_6_9", "lr2_6_10",
            "lrc1_6_1", "lrc1_6_2", "lrc1_6_3", "lrc1_6_4", "lrc1_6_5", "lrc1_6_6", "lrc1_6_7", "lrc1_6_8", "lrc1_6_9", "lrc1_6_10",
            "lrc2_6_1", "lrc2_6_2", "lrc2_6_3", "lrc2_6_4", "lrc2_6_5", "lrc2_6_6", "lrc2_6_7", "lrc2_6_8", "lrc2_6_9", "lrc2_6_10"
    };

    private static String[] instances_800 = {
            "lc1_8_1", "lc1_8_2", "lc1_8_3", "lc1_8_4", "lc1_8_5", "lc1_8_6", "lc1_8_7", "lc1_8_8", "lc1_8_9", "lc1_8_10",
            "lc2_8_1", "lc2_8_2", "lc2_8_3", "lc2_8_4", "lc2_8_5", "lc2_8_6", "lc2_8_7", "lc2_8_8", "lc2_8_9", "lc2_8_10",
            "lr1_8_1", "lr1_8_2", "lr1_8_3", "lr1_8_4", "lr1_8_5", "lr1_8_6", "lr1_8_7", "lr1_8_8", "lr1_8_9", "lr1_8_10",
            "lr2_8_1", "lr2_8_2", "lr2_8_3", "lr2_8_4", "lr2_8_5", "lr2_8_6", "lr2_8_7", "lr2_8_8", "lr2_8_9", "lr2_8_10",
            "lrc1_8_1", "lrc1_8_2", "lrc1_8_3", "lrc1_8_4", "lrc1_8_5", "lrc1_8_6", "lrc1_8_7", "lrc1_8_8", "lrc1_8_9", "lrc1_8_10",
            "lrc2_8_1", "lrc2_8_2", "lrc2_8_3", "lrc2_8_4", "lrc2_8_5", "lrc2_8_6", "lrc2_8_7", "lrc2_8_8", "lrc2_8_9", "lrc2_8_10"
    };

    private static String[] instances_1000 = {
            "LC1_10_1", "LC1_10_2", "LC1_10_3", "LC1_10_4", "LC1_10_5", "LC1_10_6", "LC1_10_7", "LC1_10_8", "LC1_10_9", "LC1_10_10",
            "LC2_10_1", "LC2_10_2", "LC2_10_3", "LC2_10_4", "LC2_10_5", "LC2_10_6", "LC2_10_7", "LC2_10_8", "LC2_10_9", "LC2_10_10",
            "LR1_10_1", "LR1_10_2", "LR1_10_3", "LR1_10_4", "LR1_10_5", "LR1_10_6", "LR1_10_7", "LR1_10_8", "LR1_10_9", "LR1_10_10",
            "LR2_10_1", "LR2_10_2", "LR2_10_3", "LR2_10_4", "LR2_10_5", "LR2_10_6", "LR2_10_7", "LR2_10_8", "LR2_10_9", "LR2_10_10",
            "LRC1_10_1", "LRC1_10_2", "LRC1_10_3", "LRC1_10_4", "LRC1_10_5", "LRC1_10_6", "LRC1_10_7", "LRC1_10_8", "LRC1_10_9", "LRC1_10_10",
            "LRC2_10_1", "LRC2_10_2", "LRC2_10_3", "LRC2_10_4", "LRC2_10_5", "LRC2_10_6", "LRC2_10_7", "LRC2_10_10"
    };

    static {
        pdptw100Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_100").getFile().substring(1)).toString();
        pdptw200Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_200").getFile().substring(1)).toString();
        pdptw400Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_400").getFile().substring(1)).toString();
        pdptw600Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_600").getFile().substring(1)).toString();
        pdptw800Directory = Paths.get(Runner.class.getClassLoader().getResource("pdp_800").getFile().substring(1)).toString();
        pdptw1000Directory = Paths.get(Runner.class.getClassLoader().getResource("pdptw1000").getFile().substring(1)).toString();
    }

    public static void main(String[] args) throws Exception {
        executeProblemSolver(pdptw100Directory, "lc101");
        /*System.out.println("pdp100");
        for (String instance : instances_100) {
            executeProblemSolver(pdptw100Directory, instance);
        }
        System.out.println("pdp200");
        for (String instance : instances_200) {
            executeProblemSolver(pdptw200Directory, instance);
        }
        System.out.println("pdp400");
        for (String instance : instances_400) {
            executeProblemSolver(pdptw400Directory, instance);
        }
        System.out.println("pdp600");
        for (String instance : instances_600) {
            executeProblemSolver(pdptw600Directory, instance);
        }
        System.out.println("pdp800");
        for (String instance : instances_800) {
            executeProblemSolver(pdptw800Directory, instance);
        }
        System.out.println("pdp1000");
        for (String instance : instances_1000) {
            executeProblemSolver(pdptw1000Directory, instance);
        }*/
    }

    private static void executeProblemSolver(String directory, String problem) throws Exception {
        Long time = System.currentTimeMillis();
        Instance instance = Reader.getInstance(Paths.get(directory, problem + ".txt").toFile());
        Solver solver = new Solver(instance, new Random(1), maxIterations, true, true);
        solver.setPrintConsole(true);
        solver.enableLocalSearch();
        solver.init();
        solver.run();
        Solution solution = solver.getSolutionBest();
        time = System.currentTimeMillis() - time;
        Double timeMinutes = time / (1000.0 * 60.0);
        System.out.println(StringUtils.rightPad(instance.name, 10) + " = " + solution + " time(m) = " + MathUtils.round(timeMinutes));
    }
}
