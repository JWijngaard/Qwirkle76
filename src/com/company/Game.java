package com.company;

import java.util.ArrayList;

/**
 * Created by Jelle on 12/01/16.
 */
public class Game {
    private ArrayList<Player> myPlayers;
    private Board myBoard;
    private Board myTryoutBoard;
    private ArrayList<Tile> bagOfStones;

    public Game(ArrayList<Player> myPlayers) {
        myBoard = new Board();
        myTryoutBoard = new Board();
        this.myPlayers = myPlayers;
    }

    public void fillMyBag() {
        int k = 0;
        for (int l = 0; l < 2; l++) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    bagOfStones.add(new Tile(-100, -100));
                    bagOfStones.get(k).setColor(i);
                    bagOfStones.get(k).setShape(j);
                    k++;
                }
            }
        }
    }

    public ArrayList<Tile> getBagOfStones() {
        return bagOfStones;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getMyTryoutBoard() {
        return myTryoutBoard;
    }

    public ArrayList<Player> getMyPlayers() {
        return myPlayers;
    }
}
