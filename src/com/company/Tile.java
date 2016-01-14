package com.company;

/**
 * Created by Jelle on 12/01/16.
 */
public class Tile {
    private int shape;
    private int color;

    public Tile() {
        shape = 6;
        color = 6;
    }

    public void setShape(int newShape) {
        shape = newShape;
    }
    public void setColor(int newColor) {
        color = newColor;
    }

    public int getShape() {
        return shape;
    }

    public int getColor() {
        return color;
    }
}
