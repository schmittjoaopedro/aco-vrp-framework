package com.github.schmittjoaopedro.tsp.utils;

public class Validation {

    public static void check(boolean condition, String error) {
        if (condition) {
            throw new RuntimeException(error);
        }
    }

    public static void check(boolean condition) {
        if (condition) {
            throw new RuntimeException("Assert error");
        }
    }
}
