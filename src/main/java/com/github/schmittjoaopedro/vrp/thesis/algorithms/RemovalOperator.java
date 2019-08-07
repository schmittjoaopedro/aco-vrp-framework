package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

public abstract class RemovalOperator extends Operator {

    public abstract void remove(Solution solution, int q);

}
