package com.github.schmittjoaopedro.vrp.thesis.algorithms;

public abstract class Operator {

    private double weight;

    private double score;

    private double count;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public void increaseCount() {
        ++count;
    }

    public void addScore(double score) {
        this.score += score;
    }

    public void resetOperator() {
        weight = 1.0;
        score = 0.0;
        count = 0.0;
    }
}
