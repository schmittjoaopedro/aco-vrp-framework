package com.github.schmittjoaopedro.vrp.pdptw.model;

public class Key {

    private int[] key;

    public Key(int p1, int p2) {
        if (p1 > p2) {
            key = new int[]{p1, p2};
        } else {
            key = new int[]{p2, p1};
        }
    }

    public int getStart() {
        return key[0];
    }

    public int getEnd() {
        return key[1];
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Key) {
            if (((Key) obj).getStart() == this.getStart() && ((Key) obj).getEnd() == this.getEnd())
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        String temp = String.valueOf(this.getStart()) + String.valueOf(this.getEnd());
        int a = this.getStart();
        int count = 0;
        while (a != 0) {
            a = a / 10;
            count++;
        }
        return Integer.parseInt(String.valueOf(count) + temp);
    }
}
