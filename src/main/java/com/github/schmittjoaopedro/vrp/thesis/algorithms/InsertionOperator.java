package com.github.schmittjoaopedro.vrp.thesis.algorithms;

import com.github.schmittjoaopedro.vrp.thesis.problem.Solution;

public abstract class InsertionOperator extends Operator {

    public abstract void insert(Solution solution, int useNoise);

}
