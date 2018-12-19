package com.github.schmittjoaopedro.rinsim;


import com.github.rinde.rinsim.geom.Point;

public class IdPoint extends Point {

    public int id;

    public IdPoint(int id, double px, double py) {
        super(px, py);
        this.id = id;
    }

}
