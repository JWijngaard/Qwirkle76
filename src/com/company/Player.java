package com.company;

import com.company.Exceptions.IllegalMoveException;
import com.company.Exceptions.TileAlreadyPlacedException;
import com.company.Exceptions.TilesNotInSameRowOrColumnException;

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

    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }

    public boolean sameRow(ArrayList<Move> moves) {
        boolean sameRow = true;
        int x = moves.get(0).getC1();
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getC1() != x) {
                sameRow = false;
            }
        }
        return sameRow;
    }

    public boolean sameColumn(ArrayList<Move> moves) {
        boolean sameColumn = true;
        int x = moves.get(0).getC2();
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getC2() != x) {
                sameColumn = false;
            }
        }
        return sameColumn;
    }

    public boolean checkSameRowOrColumn (ArrayList<Move> moves) throws TilesNotInSameRowOrColumnException {
        if (sameRow(moves) || sameColumn(moves)) {
            return true;
        }
        else throw new TilesNotInSameRowOrColumnException("I'm afraid I cannot let you do that. Place tiles only in 1 row or column.");
    }

    public int makeMoveGetPoints(ArrayList<Move> moves) {
        myGame.getMyTryoutBoard().increaseMove();
        int myPoints = 0;
        int atMove = myGame.getMyTryoutBoard().getAtMove();
        try {
            checkSameRowOrColumn(moves);
            for (int i = 0; i < moves.size(); i++) {
                try {
                    myGame.getMyTryoutBoard().makeMove(moves.get(i).getShape(), moves.get(i).getColor(), moves.get(i).getC1(), moves.get(i).getC2(), atMove);
                } catch (TileAlreadyPlacedException e) {
                    System.out.println(e.getStackTrace());
                    return -1;
                }
            }
        }
        catch (TilesNotInSameRowOrColumnException e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
        try {
            if (myGame.getMyTryoutBoard().checkLegalSituation()) {
                System.out.println("TESTOBAMA");
                System.out.println(moves);
                myPoints = calculatePoints(moves);
            }
            else {
                System.out.println("TESTSPAGHETTIMONSTER");
                myPoints = -1;
            }
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            return -1;
        }
        System.out.println(myGame.getMyTryoutBoard().toString());
        return myPoints;
    }

    public int getLowestRow(ArrayList<Move> moves) {
        Move lowest = moves.get(0);
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getC2() < lowest.getC2()) {
                lowest = moves.get(i);
            }
        }
        int currentC1 = lowest.getC1();
        int currentC2 = lowest.getC2();
        while (myGame.getMyTryoutBoard().leftNeighbor(myGame.getMyTryoutBoard().getBoard()[currentC1][currentC2]).getColor() != 6) {
            currentC2 = myGame.getMyTryoutBoard().leftNeighbor(myGame.getMyTryoutBoard().getBoard()[currentC1][currentC2]).getC1();
        }
        return currentC2;
    }

    public int getLowestColumn(ArrayList<Move> moves) {
        Move lowest = moves.get(0);
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).getC1() < lowest.getC1()) {
                lowest = moves.get(i);
            }
        }
        int currentC1 = lowest.getC1();
        int currentC2 = lowest.getC2();
        while (myGame.getMyTryoutBoard().upperNeigbor(myGame.getMyTryoutBoard().getBoard()[currentC1][currentC2]).getColor() != 6) {
            currentC1 = myGame.getMyTryoutBoard().upperNeigbor(myGame.getMyTryoutBoard().getBoard()[currentC1][currentC2]).getC1();
        }
        return currentC1;
    }

    public int calculatePoints(ArrayList<Move> moves) {
        try {
            myGame.getMyTryoutBoard().checkLegalSituation();
            System.out.println("ISDITWELLEGALDAN?" + myGame.getMyTryoutBoard().checkLegalSituation());
            int points = moves.size();
            System.out.println("samecolumn: samerow:");
            System.out.println(sameColumn(moves) + "::::" + sameRow(moves));
            System.out.println("currentPoins:");
            System.out.println(points);
            if (sameColumn(moves)) {
                Tile me = myGame.getMyTryoutBoard().getBoard()[getLowestColumn(moves)][moves.get(0).getC2()];
                int mes = 0;
                while (me.getColor() != 6) {
                    System.out.println("me = " + me.getColor());
                    Tile leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(me);
                    Tile rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(me);
                    if (leftNeighbor.getColor() != 6) {
                        points += 2;
                        leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(leftNeighbor);
                        if (leftNeighbor.getColor() != 6) {
                            points += 1;
                            leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(leftNeighbor);
                            if (leftNeighbor.getColor() != 6) {
                                points += 1;
                                leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(leftNeighbor);
                                if (leftNeighbor.getColor() != 6) {
                                    points += 1;
                                    leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(leftNeighbor);
                                    if (leftNeighbor.getColor() != 6) {
                                        points += 1;
                                    }
                                }
                            }
                        }
                    }
                    if (rightNeighbor.getColor() != 6) {
                        System.out.println("IFDLIDSHFLJDSLGJHAFSJGHDFHGJLDFGHAFGHADKFSHGKQFHGJDSHG");
                        points += 2;
                        rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(rightNeighbor);
                        if (rightNeighbor.getColor() != 6) {
                            points += 1;
                            rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(rightNeighbor);
                            if (rightNeighbor.getColor() != 6) {
                                points += 1;
                                rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(rightNeighbor);
                                if (rightNeighbor.getColor() != 6) {
                                    points += 1;
                                    rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(rightNeighbor);
                                    if (rightNeighbor.getColor() != 6) {
                                        points += 1;
                                    }
                                }
                            }
                        }
                    }
                    me = myGame.getMyTryoutBoard().lowerNeighbor(me);
                    mes += 1;
                    System.out.println("mes:" + mes);
                    if (mes > moves.size()) {
                        points += mes - moves.size();
                    }
                }
            } else if (sameRow(moves)) {
                Tile me = myGame.getMyTryoutBoard().getBoard()[getLowestRow(moves)][moves.get(0).getC1()];
                int mes = 0;
                while (me.getColor() != 6) {
                    System.out.println("me = " + me.getColor());
                    Tile upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(me);
                    Tile lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(me);
                    if (upperNeigbor.getColor() != 6) {
                        points += 2;
                        upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(upperNeigbor);
                        if (upperNeigbor.getColor() != 6) {
                            points += 1;
                            upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(upperNeigbor);
                            if (upperNeigbor.getColor() != 6) {
                                points += 1;
                                upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(upperNeigbor);
                                if (upperNeigbor.getColor() != 6) {
                                    points += 1;
                                    upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(upperNeigbor);
                                    if (upperNeigbor.getColor() != 6) {
                                        points += 1;
                                    }
                                }
                            }
                        }
                    }
                    if (lowerNeighbor.getColor() != 6) {
                        System.out.println("IFDLIDSHFLJDSLGJHAFSJGHDFHGJLDFGHAFGHADKFSHGKQFHGJDSHG");
                        points += 2;
                        lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(lowerNeighbor);
                        if (lowerNeighbor.getColor() != 6) {
                            points += 1;
                            lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(lowerNeighbor);
                            if (lowerNeighbor.getColor() != 6) {
                                points += 1;
                                lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(lowerNeighbor);
                                if (lowerNeighbor.getColor() != 6) {
                                    points += 1;
                                    lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(lowerNeighbor);
                                    if (lowerNeighbor.getColor() != 6) {
                                        points += 1;
                                    }
                                }
                            }
                        }
                    }
                    me = myGame.getMyTryoutBoard().rightNeighbor(me);
                    mes += 1;
                    System.out.println("mes:" + mes);
                    if (mes > moves.size()) {
                        points += mes - moves.size();
                    }
                }
            }
            if (myGame.getMyBoard().getQwirkles() < myGame.getMyTryoutBoard().getQwirkles()) {
                points += 6;
            }
            return points;
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
