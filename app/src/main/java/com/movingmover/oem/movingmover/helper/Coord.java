package com.movingmover.oem.movingmover.helper;

public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if(object != null && object instanceof Coord) {
            Coord coord = (Coord) object;
            if(x == coord.x && y == coord.y) {
                return true;
            }
        }
        return false;
    }
}
