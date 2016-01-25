package com.company;

import com.company.Exceptions.IllegalMoveException;
import com.company.Exceptions.TileAlreadyPlacedException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jelle on 12/01/16.
 */
public class Board {

    private Tile[][] board;
    private int atMove = 0;
    private int qwirkles = 0;

    public int getQwirkles() {
        try {
            this.checkLegalSituation();
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
        }
        return qwirkles;
    }

    public Board() {
        board = new Tile[20][20];
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void makeMove(int s, int c, int c1, int c2, int atMove) throws TileAlreadyPlacedException {
        if (this.board[c1][c2].getColor() == 6 && this.board[c1][c2].getShape() == 6) {
            this.board[c1][c2].setColor(c);
            this.board[c1][c2].setShape(s);
            this.board[c1][c2].setAtMove(atMove);
        }
        else {
           throw new TileAlreadyPlacedException("A tile has already been placed at " + c1 + "," + c2);
        }
    }

    @Override
    public String toString() {
        String string = "  ";
        for (int k = 0; k < board.length; k++) {
            if (k < 10) {
                string += " " + k + "|";
            } else {
                string += k + "|";
            }
        } string += "\n";
        for(int i = 0; i < board.length; i++) {
            if (i == 0) {
              string += i + " ";
            } else if (i < 10 && i != 0) {
                string += i + " ";
            } else {
                string += i + "";
            }
            for (int j = 0; j < board[i].length; j++) {
                if ((board[i][j].getShape() != 6)) {
                    if (board[i][j].getShape() == 0) {
                        string += "!";
                    } else if (board[i][j].getShape() == 1) {
                        string += "@";
                    } else if (board[i][j].getShape() == 2) {
                        string += "#";
                    } else if (board[i][j].getShape() == 3) {
                        string += "$";
                    } else if (board[i][j].getShape() == 4) {
                        string += "%";
                    } else if (board[i][j].getShape() == 5) {
                        string += "*";
                    }
                } else {
                    string += "S";
                }
                if ((board[i][j].getColor() != 6)) {
                    string += "" + board[i][j].getColor() + "|";
                } else {
                    string += "C|";
                }
            } string += "\n";
            string += " |";
            for (int j = 0; j < board[i].length; j++) {
                string += "__|";
            } string += "\n";
        }

        return string;
    }

    public void increaseMove() {
        atMove += 1;
    }

    public int getAtMove() {
        return atMove;
    }

    public Tile leftNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int leftC2 = myC2 - 1;
        try {
            return board[myC1][leftC2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return new Tile(99999999,999999);
        }
    }

    public Tile upperNeigbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int upperC1 = myC1 - 1;
        try {
            return board[upperC1][myC2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return new Tile(45342,34534);
        }
    }

    public Tile rightNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int rightC2 = myC2 + 1;
        try {
            return board[myC1][rightC2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return new Tile(76786,9878);
        }
    }

    public Tile lowerNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int lowerC1 = myC1 + 1;
        try {
            return board[lowerC1][myC2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return new Tile(234,2341);
        }
    }


    //Check eerst of kleur of shape, als niet dan fout. Als wel ga verder met alleen kleur of shape. Als geen blokje dan reset naar 6.
    //Ook checken of iederen uberhaupt wel een neighbour heeft.
    //Checken of een dubbele er in zit door ze allemaal in een nieuwe ArrayList te plaatsen.
    public boolean checkLegalSituation() throws IllegalMoveException {
        boolean LegalSituationRows = true;
        boolean LegalSituationColumns = true;
        qwirkles = 0;
        //First we will check rows, based on color. (left to right)
        for(int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length - 1; j++) {
                boolean currentLegalSituation = true;
                Tile me = board[i][j];
                if (me.getColor() == rightNeighbor(me).getColor() && me.getColor() != 6 && me.getShape() != rightNeighbor(me).getShape()) {
                    int rightColor = me.getColor();
                    ArrayList<Integer> shapes = new ArrayList<Integer>();
                    shapes.add(me.getShape());
                    me = rightNeighbor(me);
                    if (rightColor == rightNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                        shapes.add(me.getShape());
                        me = rightNeighbor(me);
                        if (rightColor == rightNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                            shapes.add(me.getShape());
                            me = rightNeighbor(me);
                            if (rightColor == rightNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                                shapes.add(me.getShape());
                                me = rightNeighbor(me);
                                if (rightColor == rightNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                                    shapes.add(me.getShape());
                                    qwirkles += 1;
                                    me = rightNeighbor(me);
                                }
                                else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                }
                                else {
                                    currentLegalSituation = false;
                                }
                            }
                            else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                            }
                            else {
                                currentLegalSituation = false;
                            }
                        }
                        else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                        }
                        else {
                            currentLegalSituation = false;
                        }
                    }
                    else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                    }
                    else {
                        currentLegalSituation = false;
                    }
                }
                else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                }
                else {
                    currentLegalSituation = false;
                }
                if (me.getShape() == rightNeighbor(me).getShape() && me.getShape() != 6 && me.getColor() != rightNeighbor(me).getColor()) {
                    int rightShape = me.getShape();
                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    colors.add(me.getColor());
                    me = rightNeighbor(me);
                    if (currentLegalSituation == false) {
                        currentLegalSituation = true;
                    }
                    if (rightShape == rightNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                        colors.add(me.getColor());
                        me = rightNeighbor(me);
                        if (rightShape == rightNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                            colors.add(me.getColor());
                            me = rightNeighbor(me);
                            if (rightShape == rightNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                colors.add(me.getColor());
                                me = rightNeighbor(me);
                                if (rightShape == rightNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                    colors.add(me.getColor());
                                    me = rightNeighbor(me);
                                    if (rightShape == rightNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                        colors.add(me.getColor());
                                        me = rightNeighbor(me);
                                        qwirkles += 1;
                                    }
                                    else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                                    }
                                    else {
                                        currentLegalSituation = false;
                                    }
                                }
                                else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                                }
                                else {
                                    currentLegalSituation = false;
                                }
                            }
                            else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                            }
                            else {
                                currentLegalSituation = false;
                            }
                        }
                        else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                        }
                        else {
                            currentLegalSituation = false;
                        }
                    }
                    else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                    }
                    else {
                        currentLegalSituation = false;
                    }
                }
                else if (me.getShape() == 6 || rightNeighbor(me).getShape() == 6) {

                }
                else {
                    currentLegalSituation = false;
                }
                if (currentLegalSituation == false) {
                    LegalSituationRows = false;
                }
            }
        }


        //Now, let's check columns. (top to bottom)
        for(int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length - 1; j++) {
                boolean currentLegalSituation = true;
                Tile me = board[i][j];
                if (me.getColor() == lowerNeighbor(me).getColor() && me.getColor() != 6 && me.getShape() != lowerNeighbor(me).getShape()) {
                    int rightColor = me.getColor();
                    ArrayList<Integer> shapes = new ArrayList<Integer>();
                    shapes.add(me.getShape());
                    me = lowerNeighbor(me);
                    if (rightColor == lowerNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                        shapes.add(me.getShape());
                        me = lowerNeighbor(me);
                        if (rightColor == lowerNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                            shapes.add(me.getShape());
                            me = lowerNeighbor(me);
                            if (rightColor == lowerNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                                shapes.add(me.getShape());
                                me = lowerNeighbor(me);
                                if (rightColor == lowerNeighbor(me).getColor() && !shapes.contains(me.getShape())) {
                                    shapes.add(me.getShape());
                                    qwirkles += 1;
                                    me = lowerNeighbor(me);
                                }
                                else if (me.getColor() == 6 || lowerNeighbor(me).getColor() == 6) {

                                }
                                else {
                                    currentLegalSituation = false;
                                }
                            }
                            else if (me.getColor() == 6 || lowerNeighbor(me).getColor() == 6) {

                            }
                            else {
                                currentLegalSituation = false;
                            }
                        }
                        else if (me.getColor() == 6 || lowerNeighbor(me).getColor() == 6) {

                        }
                        else {
                            currentLegalSituation = false;
                        }
                    }
                    else if (me.getColor() == 6 || lowerNeighbor(me).getColor() == 6) {

                    }
                    else {
                        currentLegalSituation = false;
                    }
                }
                else if (me.getColor() == 6 || lowerNeighbor(me).getColor() == 6) {

                }
                else {
                    currentLegalSituation = false;
                }
                if (me.getShape() == lowerNeighbor(me).getShape() && me.getShape() != 6 && me.getColor() != lowerNeighbor(me).getColor()) {
                    int rightShape = me.getShape();
                    ArrayList<Integer> colors = new ArrayList<Integer>();
                    colors.add(me.getColor());
                    me = lowerNeighbor(me);
                    if (currentLegalSituation == false) {
                        currentLegalSituation = true;
                    }
                    if (rightShape == lowerNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                        colors.add(me.getColor());
                        me = lowerNeighbor(me);
                        if (rightShape == lowerNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                            colors.add(me.getColor());
                            me = lowerNeighbor(me);
                            if (rightShape == lowerNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                colors.add(me.getColor());
                                me = lowerNeighbor(me);
                                if (rightShape == lowerNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                    colors.add(me.getColor());
                                    me = lowerNeighbor(me);
                                    if (rightShape == lowerNeighbor(me).getShape() && !colors.contains(me.getColor())) {
                                        colors.add(me.getColor());
                                        me = lowerNeighbor(me);
                                        qwirkles += 1;
                                    }
                                    else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                                    }
                                    else {
                                        currentLegalSituation = false;
                                    }
                                }
                                else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                                }
                                else {
                                    currentLegalSituation = false;
                                }
                            }
                            else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                            }
                            else {
                                currentLegalSituation = false;
                            }
                        }
                        else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                        }
                        else {
                            currentLegalSituation = false;
                        }
                    }
                    else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                    }
                    else {
                        currentLegalSituation = false;
                    }
                }
                else if (me.getShape() == 6 || lowerNeighbor(me).getShape() == 6) {

                }
                else {
                    currentLegalSituation = false;
                }
                if (currentLegalSituation == false) {
                    LegalSituationColumns = false;
                }
            }
        }
        boolean hasTileOnItsOwn = false;
        for(int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length - 1; j++) {
                Tile me = board[i][j];
                if (me.getColor() != 6) {
                    if (leftNeighbor(me).getColor() == 6 && rightNeighbor(me).getColor() == 6 && upperNeigbor(me).getColor() == 6 && lowerNeighbor(me).getColor() ==6) {
                        hasTileOnItsOwn = true;
                    }
                }
            }
        }

        if (LegalSituationColumns && LegalSituationRows && ! hasTileOnItsOwn) {
            return true;
        }
        else {
            return false;
        }
    }
    //Einde van checkcurrentLegalSituation;
}
