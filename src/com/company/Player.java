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
        int atMove =myGame.getMyTryoutBoard().getAtMove();
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
                return calculatePoints(moves);
            }
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public int calculatePoints(ArrayList<Move> moves) {
        int points = moves.size();
        if (sameColumn(moves)) {
            for (int i = 0; i < moves.size(); i++) {
                Tile leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(myGame.getMyTryoutBoard().getBoard()[moves.get(i).getC1()][moves.get(i).getC2()]);
                Tile rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(myGame.getMyTryoutBoard().getBoard()[moves.get(i).getC1()][moves.get(i).getC2()]);
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
            }
        }
        else if (sameRow(moves)) {
            for (int i = 0; i < moves.size(); i++) {
                Tile lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(myGame.getMyTryoutBoard().getBoard()[moves.get(i).getC1()][moves.get(i).getC2()]);
                Tile upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(myGame.getMyTryoutBoard().getBoard()[moves.get(i).getC1()][moves.get(i).getC2()]);
                if (lowerNeighbor.getColor() != 6) {
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
            }
        }
        if (myGame.getMyBoard().getQwirkles() < myGame.getMyTryoutBoard().getQwirkles()) {
            points += 6;
        }
        return points;
    }
}
