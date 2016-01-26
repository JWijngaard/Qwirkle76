package com.company;

/**
 * Created by Jelle on 22/01/16.
 */
public class Move {
    private int shape;
    private int color;
    private int c1;
    private int c2;

    public Move(int s, int c, int c1, int c2) {
        this.shape = s;
        this.color = c;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Tile getTileWithoutCoordinates() {
        Tile tile = new Tile(-100, -100);
        tile.setColor(color);
        tile.setShape(shape);
        return tile;
    }

    public int getShape() {
        return shape;
    }

    public int getColor() {
        return color;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }
}
