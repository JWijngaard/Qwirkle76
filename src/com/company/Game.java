package com.company;

import java.util.ArrayList;

/**
 * Created by Jelle on 12/01/16.
 */
public class Game {
    private ArrayList<Player> myPlayers;
    private Board myBoard;
    private Board myTryoutBoard;

    public Game(ArrayList<Player> myPlayers) {
        this.myPlayers = myPlayers;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getMyTryoutBoard() {
        return myTryoutBoard;
    }
}
