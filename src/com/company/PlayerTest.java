package com.company;

import com.company.Exceptions.IllegalMoveException;
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
    Move move10 = new Move(0,2,1,6);
    Move move11 = new Move(1,2,1,5);
    Move move12 = new Move(0,2,0,4);
    Move move13 = new Move(1,2,3,4);
    Move move14 = new Move(2,2,4,4);
    Move move15 = new Move(3,2,5,4);
    Move move17 = new Move(5,2,2,4);
    Move move18 = new Move(1,4,4,3);
    Move move19 = new Move(0,4,4,4);
    Move move20 = new Move(3,4,4,5);
    Move move21 = new Move(4,4,4,6);
    Move move22 = new Move(5,4,4,7);

    public void stepOne(Player player) {
        player.addTileToHand(move1.getTileWithoutCoordinates());
        player.addTileToHand(move2.getTileWithoutCoordinates());
        player.addTileToHand(move3.getTileWithoutCoordinates());
        player.addTileToHand(move4.getTileWithoutCoordinates());
        player.addTileToHand(move5.getTileWithoutCoordinates());
        player.addTileToHand(move6.getTileWithoutCoordinates());
        player.addTileToHand(move7.getTileWithoutCoordinates());
        player.addTileToHand(move8.getTileWithoutCoordinates());
        player.addTileToHand(move9.getTileWithoutCoordinates());
        player.addTileToHand(move10.getTileWithoutCoordinates());
        player.addTileToHand(move11.getTileWithoutCoordinates());
        player.addTileToHand(move12.getTileWithoutCoordinates());
        player.addTileToHand(move13.getTileWithoutCoordinates());
        player.addTileToHand(move14.getTileWithoutCoordinates());
        player.addTileToHand(move15.getTileWithoutCoordinates());
        player.addTileToHand(move17.getTileWithoutCoordinates());
        player.addTileToHand(move18.getTileWithoutCoordinates());
        player.addTileToHand(move19.getTileWithoutCoordinates());
        player.addTileToHand(move20.getTileWithoutCoordinates());
        player.addTileToHand(move21.getTileWithoutCoordinates());
        player.addTileToHand(move22.getTileWithoutCoordinates());
    }

    @Test
    public void testSameRowAndTestSameColumn() throws Exception {
        Player testPlayer = new Player("Pieter");
        stepOne(testPlayer);
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
        stepOne(testPlayer);
        myMoves.clear();
        myMoves.add(move1);
        myMoves.add(move2);
        myMoves.add(move3);
        assertEquals(3, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move4);
        myMoves.add(move5);
        myMoves.add(move6);
        assertEquals(7, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move7);
        myMoves.add(move8);
        assertEquals(15, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move9);
        assertEquals(-1, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move10);
        myMoves.add(move11);
        assertEquals(11, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move12);
        assertEquals(7, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move13);
        myMoves.add(move14);
        myMoves.add(move17);
        assertEquals(10, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move15);
        assertEquals(17, testPlayer.makeMoveGetPoints(myMoves));
    }

    @Test
    public void testMakeMoveGetPoints2() throws Exception {
        Player testPlayer = new Player("Pieter");
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(testPlayer);
        Game myTestGame = new Game(players);
        testPlayer.setMyGame(myTestGame);
        stepOne(testPlayer);
        myMoves.clear();
        myMoves.add(move8);
        assertEquals(1, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move7);
        assertEquals(2, testPlayer.makeMoveGetPoints(myMoves));
        assertEquals(-1, testPlayer.makeMoveGetPoints(myMoves));
        myMoves.clear();
        myMoves.add(move18);
        myMoves.add(move19);
        myMoves.add(move20);
        myMoves.add(move21);
        myMoves.add(move22);
        assertEquals(14, testPlayer.makeMoveGetPoints(myMoves));
    }
}