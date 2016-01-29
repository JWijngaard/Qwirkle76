package com.company;

import com.company.Exceptions.NotEnoughTilesInBagException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jelle on 12/01/16.
 */
public class Game {
    private ArrayList<Player> myPlayers = new ArrayList<Player>();
    private Board myBoard;
    private Board myTryoutBoard;
    private ArrayList<Tile> bagOfStones = new ArrayList<>();
    private Player turn;

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

    public void setTurn(Player player) {
        turn = player;
    }

    public Player getTurn() {
        return turn;
    }

    public Player nextTurn() {
        int currentPlayerIndex = myPlayers.indexOf(turn);
        if (currentPlayerIndex == (myPlayers.size() -1)) {
            turn = myPlayers.get(0);
        }
        else turn = myPlayers.get((currentPlayerIndex + 1));
        return turn;
    }

    public void addToBag(Tile t) {
        bagOfStones.add(t);
    }

    public void distributeTiles() {
        for (int i = 0; i < myPlayers.size(); i++) {
            Player thisPlayer = myPlayers.get(i);
            CopyOnWriteArrayList<Tile> newHand = new CopyOnWriteArrayList<>();
            for (int k = 0; k < 6; k++) {
                Random randomGenerator = new Random();
                int j = randomGenerator.nextInt(bagOfStones.size());
                newHand.add(bagOfStones.get(j));
                bagOfStones.remove(j);
            }
            thisPlayer.setMyHand(newHand);
        }
    }

    public void setPlayersInGame() {
        for (Player player : myPlayers) {
            player.setMyGame(this);
        }
    }

    public void addNewTiles(Player player, int amount) throws NotEnoughTilesInBagException {
        if (!(bagOfStones.size() < amount)) {
            for (int i = 0; i < amount; i++) {
                Random randomGenerator = new Random();
                int j = randomGenerator.nextInt(bagOfStones.size());
                player.addTileToHand(bagOfStones.get(j));
                bagOfStones.remove(j);
            }
        }
        else throw new NotEnoughTilesInBagException("Sorry, not enough tiles left to trade. (bag empty or near empty)");
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
