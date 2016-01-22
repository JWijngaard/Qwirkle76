package com.company;

import com.company.Exceptions.TileAlreadyPlacedException;

import java.util.ArrayList;

/**
 * Created by Jelle on 13/01/16.
 */
public class Player {
    private int score;
    private String name;
    private Game myGame;

    public Player(String name) {
        this.name = name;
    }

    public int makemove(ArrayList<Move> moves) {
        myGame.getMyTryoutBoard().increaseMove();
        int atMove =myGame.getMyTryoutBoard().getAtMove();
        int pointsReceived = 0;
        for (int i = 0; i < moves.size(); i++) {
            try {
                myGame.getMyTryoutBoard().makeMove(moves.get(i).getShape(), moves.get(i).getColor(), moves.get(i).getC1(), moves.get(i).getC2(), atMove);
            }
            catch (TileAlreadyPlacedException e) {
                return -1;
            }
        }
        return -1;
    }
}
