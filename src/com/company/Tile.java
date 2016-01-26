package com.company;

import java.util.ArrayList;

/**
 * Created by Jelle on 12/01/16.
 */
public class Tile {
    private int shape;
    private int color;
    private int c1;
    private int c2;
    private int atMove = -1;

    public Tile(int c1, int c2) {
        this.c1 = c1;
        this.c2 = c2;
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

    public int getAtMove() {
        return atMove;
    }

    public void setAtMove(int currentMove) {
        atMove = currentMove;
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
