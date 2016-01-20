package com.company;

import com.company.Exceptions.TileAlreadyPlacedException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jelle on 12/01/16.
 */
public class Board {
    public static void main(String[] args) {
        Board testBoard = new Board();
        testBoard.board[2][2].setColor(1);
        testBoard.board[2][2].setShape(1);
        System.out.println(testBoard.toString());
        try {
            testBoard.makeMove(1,1,3,10);
        } catch (TileAlreadyPlacedException e) {
            e.printStackTrace();
        }
        try {
            testBoard.makeMove(1,1,1,1);
        } catch (TileAlreadyPlacedException e) {
            e.printStackTrace();
        }
        try {
            testBoard.makeMove(1,1,1,2);
        } catch (TileAlreadyPlacedException e) {
            e.printStackTrace();
        }
        System.out.println(testBoard.checkLegalSituation());
        System.out.println(testBoard.toString());
        try {
            testBoard.makeMove(1,1,1,1);
        } catch (TileAlreadyPlacedException e) {
            e.printStackTrace();
        }
    }

    private Tile[][] board;

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

    public void makeMove(int s, int c, int c1, int c2) throws TileAlreadyPlacedException {
        if (this.board[c1][c2].getColor() == 6 && this.board[c1][c2].getShape() == 6) {
            this.board[c1][c2].setColor(c);
            this.board[c1][c2].setShape(s);
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

    public Tile leftNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int leftC2 = myC2 - 1;
        return board[myC1][leftC2];
    }

    public Tile upperNeigbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int upperC1 = myC1 - 1;
        return board[upperC1][myC2];
    }

    public Tile rightNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int rightC2 = myC2 + 1;
        return  board[myC1][rightC2];
    }

    public Tile lowerNeighbor(Tile me) {
        int myC1 = me.getC1();
        int myC2 = me.getC2();
        int lowerC1 = myC1 + 1;
        return board[lowerC1][myC2];
    }


    //Check eerst of kleur of shape, als niet dan fout. Als wel ga verder met alleen kleur of shape. Als geen blokje dan reset naar 6.
    //Ook checken of iederen uberhaupt wel een neighbour heeft.
    //Checken of een dubbele er in zit door ze allemaal in een nieuwe ArrayList te plaatsen.
    public boolean checkLegalSituation() {
        boolean legalSituation = true;
        //First we will check rows, based on color. (left to right)
        for(int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length - 1; j++) {
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
                                                    System.out.println("Wtf is hier gaande?!");
                                                }
                                                else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                                }
                                                else {
                                                    legalSituation = false;
                                                }
                                            }
                                            else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                            }
                                            else {
                                                legalSituation = false;
                                            }
                                        }
                                        else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                        }
                                        else {
                                            legalSituation = false;
                                        }
                                    }
                                    else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                    }
                                    else {
                                        legalSituation = false;
                                    }
                                }
                                else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                                }
                                else {
                                    legalSituation = false;
                                }
                            }
                            else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                            }
                            else {
                                legalSituation = false;
                            }
                        }
                        else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                        }
                        else {
                            legalSituation = false;
                        }
                    }
                    else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                    }
                    else {
                        legalSituation = false;
                    }
                }
                else if (me.getColor() == 6 || rightNeighbor(me).getColor() == 6) {

                }
                else {
                    legalSituation = false;
                }
            }
        }
        return legalSituation;
    }
    //Einde van checkLegalSituation;
}
