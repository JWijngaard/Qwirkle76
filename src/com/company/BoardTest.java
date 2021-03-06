package com.company;

import com.company.Exceptions.IllegalMoveException;
import com.company.Exceptions.TileAlreadyPlacedException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jelle on 20/01/16.
 */
public class BoardTest {


    @org.junit.Test
    public void testLeftNeighbor() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(5,2,2,2,1);
        Tile myTestTile = testBoard.leftNeighbor(testBoard.getBoard()[2][3]);
        assertEquals(2, myTestTile.getColor());
        assertEquals(5, myTestTile.getShape());
    }

    @org.junit.Test
    public void testUpperNeigbor() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(7,3,5,5,1);
        Tile myTestTile = testBoard.upperNeigbor(testBoard.getBoard()[6][5]);
        assertEquals(3, myTestTile.getColor());
        assertEquals(7, myTestTile.getShape());
    }

    @org.junit.Test
    public void testRightNeighbor() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(7,3,5,5,1);
        Tile myTestTile = testBoard.rightNeighbor(testBoard.getBoard()[5][4]);
        assertEquals(3, myTestTile.getColor());
        assertEquals(7, myTestTile.getShape());
    }

    @org.junit.Test
    public void testLowerNeighbor() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(7,3,5,5,1);
        Tile myTestTile = testBoard.lowerNeighbor(testBoard.getBoard()[4][5]);
        assertEquals(3, myTestTile.getColor());
        assertEquals(7, myTestTile.getShape());
        testBoard.leftNeighbor(testBoard.getBoard()[19][19]);
    }

    @org.junit.Test
    public void testMakeMove() throws Exception {
        Board testBoard1 = new Board();
        testBoard1.makeMove(1,1,1,1,1);
        try {
            testBoard1.makeMove(2, 2, 1, 1,1);
        }
        catch (TileAlreadyPlacedException e) {
            e.printStackTrace();
        }
        assertEquals(1, testBoard1.getBoard()[1][1].getShape());
        assertEquals(1, testBoard1.getBoard()[1][1].getColor());
    }

    @Test
    public void testCheckLegalSituationActuallyLegal() throws Exception {
        Board testBoard = new Board();
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(1, 5, 1, 1, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(2, 5, 1, 2, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(3, 5, 1, 3, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(4, 5, 2, 2, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(5, 5, 3, 2, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(0, 5, 1, 4, 1);
        System.out.println(testBoard.checkLegalSituation());
        testBoard.makeMove(0, 5, 4, 2, 1);
        System.out.println(testBoard.toString());
        testBoard.makeMove(5, 3, 3, 4, 1);
        testBoard.makeMove(5, 4, 3, 3, 1);
        System.out.println(testBoard.checkLegalSituation());
        System.out.println(testBoard.toString());
        assertEquals(true, testBoard.checkLegalSituation());
    }

    @Test
    public void testCheckLegalSituationActuallyLegal2() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(2, 2, 1, 2, 1);
        testBoard.makeMove(3, 2, 1, 3, 1);
        testBoard.makeMove(4, 2, 1, 4, 1);
        testBoard.makeMove(2, 1, 2, 2, 1);
        testBoard.makeMove(2, 3, 3, 2, 1);
        testBoard.makeMove(2, 5, 0, 2, 1);
        assertEquals(true, testBoard.checkLegalSituation());
    }

    @Test
    public void testCheckLegalSituationActuallyLegal3() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(2,0,0,4,1);
        testBoard.makeMove(2,1,1,4,1);
        testBoard.makeMove(2,2,2,4,1);
        testBoard.makeMove(2,3,3,4,1);
        testBoard.makeMove(2,4,4,4,1);
        testBoard.makeMove(2,5,5,4,1);
        assertEquals(true, testBoard.checkLegalSituation());
    }

    @Test
    public void testCheckLegalSituationActuallyLegal4() throws Exception {
        Board testBoard = new Board();
        testBoard.makeMove(0,2,0,4,1);
        testBoard.makeMove(4,2,1,4,1);
        testBoard.makeMove(5,2,2,4,1);
        testBoard.makeMove(1,2,3,4,1);
        testBoard.makeMove(2,2,4,4,1);
        assertEquals(true, testBoard.checkLegalSituation());
    }

    @Test
    public void testCheckLegalSituationNotLegalSameNeighbor() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 0, 0, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 0, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 5, 0, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 2, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 0, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }

    @Test
    public void testCheckLegalSituationNotLegalDifferentColorNeighbor() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(2, 5, 1, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 4, 1, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 2, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 3, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 1, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }

    @Test
    public void testCheckLegalSituationNotLegalDifferentShapeNeighbor() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(2, 5, 1, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 5, 1, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 2, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 3, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 1, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 4, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 4, 3, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 3, 3, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }

    @Test
    public void testCheckLegalSituationNotLegalDifferentColorInColumn() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(2, 5, 1, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 5, 1, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 2, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 3, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 1, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 4, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 4, 3, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 4, 5, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }

    @Test
    public void testCheckLegalSituationNotLegalSameTileInColumn() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(2, 5, 1, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 5, 1, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 2, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 3, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 1, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 4, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 4, 3, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 5, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }

    @Test
    public void testCheckLegalSituationNotLegalTileIsAlone() throws Exception {
        try {
            Board testBoard = new Board();
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 5, 1, 1, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(2, 5, 1, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(3, 5, 1, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(4, 5, 2, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 5, 3, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 1, 4, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(0, 5, 4, 2, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(5, 4, 3, 3, 1);
            System.out.println(testBoard.checkLegalSituation());
            testBoard.makeMove(1, 1, 11, 11, 1);
            System.out.println(testBoard.toString());
            assertEquals(false, testBoard.checkLegalSituation());
        }
        catch (IllegalMoveException e) {
        }
    }
}