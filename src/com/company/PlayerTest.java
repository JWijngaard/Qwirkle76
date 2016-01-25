package com.company;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jelle on 24/01/16.
 */
public class PlayerTest {

    ArrayList<Move> myMoves = new ArrayList<Move>();
    Move move1 = new Move(2,2,1,2);
    Move move2 = new Move(3,2,1,3);
    Move move3 = new Move(4,2,1,4);
    Move move4 = new Move(2,1,2,2);
    Move move5 = new Move(2,3,3,2);
    Move move6 = new Move(2,5,0,2);
    Move move7 = new Move(2,4,4,2);
    Move move8 = new Move(2,0,5,2);
    Move move9 = new Move(2,1,6,2);

    @Test
    public void testSameRowAndTestSameColumn() throws Exception {
        Player testPlayer = new Player("Pieter");
        myMoves.clear();
        myMoves.add(move1);
        myMoves.add(move2);
        myMoves.add(move3);
        System.out.println(myMoves);
        assertEquals(testPlayer.sameRow(myMoves), true);
        assertEquals(testPlayer.sameColumn(myMoves), false);
        System.out.println(testPlayer.sameRow(myMoves));
        System.out.println(testPlayer.sameColumn(myMoves));
        myMoves.clear();
        myMoves.add(move1);
        myMoves.add(move4);
        myMoves.add(move5);
        System.out.println(myMoves);
        assertEquals(testPlayer.sameColumn(myMoves), true);
        assertEquals(testPlayer.sameRow(myMoves), false);
        System.out.println(testPlayer.sameColumn(myMoves));
        System.out.println(testPlayer.sameRow(myMoves));
    }

    @Test
    public void testMakeMoveGetPoints() throws Exception {
        Player testPlayer = new Player("Pieter");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(testPlayer);
        Game myTestGame = new Game(players);
        testPlayer.setMyGame(myTestGame);
        myMoves.clear();
        myMoves.add(move1);
        myMoves.add(move2);
        myMoves.add(move3);
        assertEquals(3, testPlayer.makeMoveGetPoints(myMoves));
        System.out.println(myTestGame.getMyTryoutBoard().toString());
        myMoves.clear();
        myMoves.add(move4);
        myMoves.add(move5);
        myMoves.add(move6);
        assertEquals(7, testPlayer.makeMoveGetPoints(myMoves));
        System.out.println(myTestGame.getMyTryoutBoard().toString());
        myMoves.clear();
        myMoves.add(move7);
        myMoves.add(move8);
        assertEquals(15, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move9);
        assertEquals(-1, testPlayer.makeMoveGetPoints(myMoves));
    }
}