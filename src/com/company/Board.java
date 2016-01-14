package com.company;

/**
 * Created by Jelle on 12/01/16.
 */
public class Board {
    public static void main(String[] args) {
        Board testBoard = new Board();
        testBoard.board[2][2].setColor(1);
        testBoard.board[2][2].setShape(1);
        System.out.println(testBoard.toString());
    }

    private Tile[][] board;

    public Board() {
        board = new Tile[20][20];
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Tile();
            }
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
                string += i + " " ;
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
}
