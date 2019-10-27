package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Request;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LarsenEdodtw {

    private static final String BASE_LIT_DIR = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\";
    private static final String[] PROBLEM_SIZES = {"100", "200", "400", "600", "800", "1000"};

    public static void main(String[] args) throws Exception {
        //edodtwByProblemSize();
        //edodtwByDistributionType();
        edodtwByDynamicDegree();
    }

    private static void edodtwByDynamicDegree() throws Exception {
        // Static columns
        System.out.printf(Locale.US, "Static;\n");
        double overallMean = 0.0;
        int totalCounter = 0;
        for (String problemSize : PROBLEM_SIZES) {
            File[] instanceFiles = listFiles(BASE_LIT_DIR + "pdp_" + problemSize);
            for (File instanceFile : instanceFiles) {
                Instance instance = Reader.getInstance(instanceFile);
                double instanceEdodtw = 0.0;
                for (Request request : instance.requests) {
                    instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                }
                instanceEdodtw /= instance.requests.length;
                overallMean += instanceEdodtw;
                totalCounter++;
            }
        }
        overallMean /= totalCounter;
        System.out.printf(Locale.US, "%.2f;", overallMean);
        // Urgency columns
        System.out.printf(Locale.US, "\na_0.1;a_0.25;a_0.5;a_0.75;a_1.0;\n");
        for (String a : InstanceUtils.dynamic_urgency_suffixes) {
            overallMean = 0.0;
            totalCounter = 0;
            for (String problemSize : PROBLEM_SIZES) {
                File[] instanceFiles = listFiles(BASE_LIT_DIR + "pdp_" + problemSize);
                for (File instanceFile : instanceFiles) {
                    File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + a + ".txt");
                    Instance instance = Reader.getInstance(file);
                    double instanceEdodtw = 0.0;
                    for (Request request : instance.requests) {
                        instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                    }
                    instanceEdodtw /= instance.requests.length;
                    overallMean += instanceEdodtw;
                    totalCounter++;
                }
            }
            overallMean /= totalCounter;
            System.out.printf(Locale.US, "%.2f;", overallMean);
        }
        // A-priori columns
        System.out.printf(Locale.US, "\nq_0.1;q_0.25;q_0.5;q_0.75;q_1.0;\n");
        for (String a : InstanceUtils.dynamic_apriori_suffixes) {
            overallMean = 0.0;
            totalCounter = 0;
            for (String problemSize : PROBLEM_SIZES) {
                File[] instanceFiles = listFiles(BASE_LIT_DIR + "pdp_" + problemSize);
                for (File instanceFile : instanceFiles) {
                    File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + a + ".txt");
                    Instance instance = Reader.getInstance(file);
                    double instanceEdodtw = 0.0;
                    for (Request request : instance.requests) {
                        instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                    }
                    instanceEdodtw /= instance.requests.length;
                    overallMean += instanceEdodtw;
                    totalCounter++;
                }
            }
            overallMean /= totalCounter;
            System.out.printf(Locale.US, "%.2f;", overallMean);
        }
    }

    private static void edodtwByDistributionType() throws Exception {
        System.out.printf(Locale.US, "Instance;Static;a_0.1;a_0.25;a_0.5;a_0.75;a_1.0;q_0.1;q_0.25;q_0.5;q_0.75;q_1.0\n");
        String[] problemTypes = {"lc1", "lc2", "lr1", "lr2", "lrc1", "lrc2"};
        for (String type : problemTypes) {
            double overallMean = 0.0;
            int nTot = 0;
            // Static column
            for (String problemSize : PROBLEM_SIZES) {
                File[] instanceFiles = listFilesByPrefix(BASE_LIT_DIR + "pdp_" + problemSize, "100".equalsIgnoreCase(problemSize) ? type : type.toUpperCase());
                for (File instanceFile : instanceFiles) {
                    Instance instance = Reader.getInstance(instanceFile);
                    double instanceEdodtw = 0.0;
                    for (Request request : instance.requests) {
                        instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                    }
                    instanceEdodtw /= instance.requests.length;
                    overallMean += instanceEdodtw;
                    nTot++;
                }
            }
            overallMean /= nTot;
            System.out.printf(Locale.US, "%s;%.2f;", type, overallMean);
            // Urgency columns
            for (String a : InstanceUtils.dynamic_urgency_suffixes) {
                overallMean = 0.0;
                nTot = 0;
                for (String problemSize : PROBLEM_SIZES) {
                    File[] instanceFiles = listFilesByPrefix(BASE_LIT_DIR + "pdp_" + problemSize, "100".equalsIgnoreCase(problemSize) ? type : type.toUpperCase());
                    for (File instanceFile : instanceFiles) {
                        File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + a + ".txt");
                        Instance instance = Reader.getInstance(file);
                        double instanceEdodtw = 0.0;
                        for (Request request : instance.requests) {
                            instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                        }
                        instanceEdodtw /= instance.requests.length;
                        overallMean += instanceEdodtw;
                        nTot++;
                    }
                }
                overallMean /= nTot;
                System.out.printf(Locale.US, "%.2f;", overallMean);
            }
            // A-priori columns
            for (String q : InstanceUtils.dynamic_apriori_suffixes) {
                overallMean = 0.0;
                nTot = 0;
                for (String problemSize : PROBLEM_SIZES) {
                    File[] instanceFiles = listFilesByPrefix(BASE_LIT_DIR + "pdp_" + problemSize, "100".equalsIgnoreCase(problemSize) ? type : type.toUpperCase());
                    for (File instanceFile : instanceFiles) {
                        File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + q + ".txt");
                        Instance instance = Reader.getInstance(file);
                        double instanceEdodtw = 0.0;
                        for (Request request : instance.requests) {
                            instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                        }
                        instanceEdodtw /= instance.requests.length;
                        overallMean += instanceEdodtw;
                        nTot++;
                    }
                }
                overallMean /= nTot;
                System.out.printf(Locale.US, "%.2f;", overallMean);
            }
            System.out.println("");
        }
    }

    private static void edodtwByProblemSize() throws Exception {
        System.out.printf(Locale.US, "Instance;Static;a_0.1;a_0.25;a_0.5;a_0.75;a_1.0;q_0.1;q_0.25;q_0.5;q_0.75;q_1.0\n");
        for (String problemSize : PROBLEM_SIZES) {
            // Static column
            double overallMean = 0.0;
            File[] instanceFiles = listFiles(BASE_LIT_DIR + "pdp_" + problemSize);
            for (File instanceFile : instanceFiles) {
                Instance instance = Reader.getInstance(instanceFile);
                double instanceEdodtw = 0.0;
                for (Request request : instance.requests) {
                    instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                }
                instanceEdodtw /= instance.requests.length;
                overallMean += instanceEdodtw;
            }
            overallMean /= instanceFiles.length;
            System.out.printf(Locale.US, "%s;%.2f;", problemSize, overallMean);
            // Urgency columns
            for (String a : InstanceUtils.dynamic_urgency_suffixes) {
                overallMean = 0.0;
                for (File instanceFile : instanceFiles) {
                    File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + a + ".txt");
                    Instance instance = Reader.getInstance(file);
                    double instanceEdodtw = 0.0;
                    for (Request request : instance.requests) {
                        instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                    }
                    instanceEdodtw /= instance.requests.length;
                    overallMean += instanceEdodtw;
                }
                overallMean /= instanceFiles.length;
                System.out.printf(Locale.US, "%.2f;", overallMean);
            }
            // A-priori columns
            for (String q : InstanceUtils.dynamic_apriori_suffixes) {
                overallMean = 0.0;
                for (File instanceFile : instanceFiles) {
                    File file = new File(BASE_LIT_DIR + "dpdptw\\" + problemSize + "-tasks\\" + instanceFile.getName().replaceAll(".txt", "") + "_" + q + ".txt");
                    Instance instance = Reader.getInstance(file);
                    double instanceEdodtw = 0.0;
                    for (Request request : instance.requests) {
                        instanceEdodtw += (instance.depot.twEnd - (request.pickupTask.twEnd - request.announceTime)) / instance.depot.twEnd;
                    }
                    instanceEdodtw /= instance.requests.length;
                    overallMean += instanceEdodtw;
                }
                overallMean /= instanceFiles.length;
                System.out.printf(Locale.US, "%.2f;", overallMean);
            }
            System.out.println("");
        }
    }

    private static File[] listFiles(String dir) {
        File folder = Paths.get(dir).toFile();
        return folder.listFiles();
    }

    private static File[] listFilesByPrefix(String dir, String prefix) {
        File folder = Paths.get(dir).toFile();
        List<File> files = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.getName().startsWith(prefix)) {
                files.add(file);
            }
        }
        return files.toArray(new File[]{});
    }

}
