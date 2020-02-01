package com.github.schmittjoaopedro.vrp.thesis.utils;

import com.github.schmittjoaopedro.vrp.thesis.problem.Instance;
import com.github.schmittjoaopedro.vrp.thesis.problem.Reader;
import com.github.schmittjoaopedro.vrp.thesis.problem.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkData {

    public static final String STATIC_PATH = "C:\\projects\\aco-vrp-framework\\src\\main\\resources\\";
    public static final String STATIC_SIZES[] = {"100", "200", "400", "600", "800", "1000"};

    public static void main(String[] args) throws Exception {
        List<Instance> instances = new ArrayList<>();
        for (String problemSize : STATIC_SIZES) {
            for (File file : new File(STATIC_PATH + "pdp_" + problemSize).listFiles()) {
                instances.add(Reader.getInstance(file));
            }
        }
        for (Instance instance : instances) {
            for (Task task : instance.tasks) {
                System.out.println(task.twEnd - task.twStart);
                //System.out.println(task.demand);
            }
            //System.out.println(instance.depot.twEnd - instance.depot.twStart);
        }
    }

}
