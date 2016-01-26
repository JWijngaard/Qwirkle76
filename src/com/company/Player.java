package com.company;

import com.company.Exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jelle on 13/01/16.
 */
public class Player {
    private int score;
    private String name;
    protected static Game myGame;
    private TUIView myView = new TUIView();
    private CopyOnWriteArrayList<Tile> myHand = new CopyOnWriteArrayList<Tile>();

    public static void main(String[] args) {
        Player test = new Player("pieter");
        ArrayList<Player> players = new ArrayList<>();
        players.add(test);
        myGame = new Game(players);
        myGame.fillMyBag();
        myGame.distributeTiles();
        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
        System.out.println(myGame.getMyBoard().toString());
        System.out.println(test.getScore());
        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
        System.out.println(myGame.getMyBoard().toString());
        System.out.println(test.getScore());
        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
        System.out.println(myGame.getMyBoard().toString());
        System.out.println(test.getScore());
    }

    public void setMyHand(CopyOnWriteArrayList<Tile> myHand) {
        this.myHand = myHand;
    }

    public void tradeTiles(ArrayList<Tile> tilesToTrade) throws NotEnoughTilesInBagException, DontHaveTileException {
        boolean allInHand = true;
        for (Tile tile : tilesToTrade) {
            if (!myHand.contains(tile)) {
                allInHand = false;
            }
        }
        if (allInHand) {
            myGame.addNewTiles(this, tilesToTrade.size());
            for (Tile tile : tilesToTrade) {
                myHand.remove(tile);
            }
        } else {
            throw new DontHaveTileException("Don't cheat: you can't trade tiles you don't own.");
        }
    }

    public String myHandToString() {
        String result = " ";
        for (Tile z : myHand) {
            if (z.getShape() == 0) {
                result += "!";
            }
            else if (z.getShape() == 1) {
                result += "@";
            }
            else if (z.getShape() == 2) {
                result += "#";
            }
            else if (z.getShape() == 3) {
                result += "$";
            }
            else if (z.getShape() == 4) {
                result += "%";
            }
            else if (z.getShape() == 5) {
                result += "*";
            }
            result += z.getColor();
            result += "(";
            result += z.getShape();
            result += ")";
            result += " ";
        }
        return result;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public CopyOnWriteArrayList<Tile> getMyHand() {
        return myHand;
    }

    public ArrayList<Tile> askForTrade() {
        List<String> ints = new ArrayList<>();
        myView.logMessage("The current board is as follows: \n" + myGame.getMyBoard().toString() + "\n");
        myView.logMessage("Remember, you only have the following stones:" + myHandToString());
        List<String> words = Arrays.asList((myView.askUserInput("What tiles would you like to trade? (s,c)").split("\\s+")));
        for (String z : words) {
            for (String s : z.split(",")) {
                ints.add(s);
            }
        }
        ArrayList<Tile> trades = new ArrayList<>();
        while (!ints.isEmpty()) {
            int s = Integer.parseInt(ints.get(0));
            ints.remove(0);
            int c = Integer.parseInt(ints.get(0));
            ints.remove(0);
            Tile newTile = new Tile(-100, -100);
            newTile.setColor(c);
            newTile.setShape(s);
            trades.add(newTile);
        }
        return trades;
    }

    public void myTurn() {
        String userInput = myView.askUserInput("Would you like to trade (enter t) or place (enter p) tiles?");
        if (userInput == "t") {
            askForTrade();
        } else if (userInput == "p") {
            askForMove();
        } else {
            System.out.println("Please do as you are asked: enter t or p.");
            myTurn();
        }
    }

    public ArrayList<Move> askForMove() {
        List<String> ints = new ArrayList<>();
        myView.logMessage("The current board is as follows: \n" + myGame.getMyBoard().toString() + "\n");
        myView.logMessage("Remember, you only have the following stones:" + myHandToString());
        List<String> words = Arrays.asList((myView.askUserInput("What move would you like to make? (s,c,x,y)").split("\\s+")));
        for (String z : words) {
            for (String s : z.split(",")) {
                ints.add(s);
            }
        }
        ArrayList<Move> moves = new ArrayList<>();
        while (!ints.isEmpty()) {
            int s = Integer.parseInt(ints.get(0));
            ints.remove(0);
            int c = Integer.parseInt(ints.get(0));
            ints.remove(0);
            int x = Integer.parseInt(ints.get(0));
            ints.remove(0);
            int y = Integer.parseInt(ints.get(0));
            ints.remove(0);
            moves.add(new Move(s,c,y,x));
        }
        return moves;
    }

    public Player(String name) {
        this.name = name;
    }

    public void setMyGame(Game myGame) {
        this.myGame = myGame;
    }

    public void addTileToHand(Tile tile) {
        myHand.add(tile);
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

    public void checkTileInHand (Tile tile) throws DontHaveTileException {
        for (Tile z : myHand) {
            if (z.getShape() == tile.getShape() && z.getColor() == tile.getColor()) {
                myHand.remove(z);
                return;
            }
        }
        throw new DontHaveTileException("You don't have this tile mate.");
    }

    public int makeMoveGetPoints(ArrayList<Move> moves) {
        for (Move z : moves) {
            try {
                checkTileInHand(z.getTileWithoutCoordinates());
            }
            catch (DontHaveTileException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                return -1;
            }
        }
        myGame.getMyTryoutBoard().increaseMove();
        int myPoints = 0;
        int atMove = myGame.getMyTryoutBoard().getAtMove();
        try {
            checkSameRowOrColumn(moves);
            for (int i = 0; i < moves.size(); i++) {
                try {
                    myGame.getMyTryoutBoard().makeMove(moves.get(i).getShape(), moves.get(i).getColor(), moves.get(i).getC1(), moves.get(i).getC2(), atMove);
                    System.out.println(myGame.getMyTryoutBoard().toString());
                } catch (TileAlreadyPlacedException e) {
                    System.out.println(e.getStackTrace());
                    for(int k = 0; k < myGame.getMyTryoutBoard().getBoard().length; k++) {
                        for (int j = 0; j < myGame.getMyTryoutBoard().getBoard()[k].length; j++) {
                            myGame.getMyTryoutBoard().getBoard()[k][j].setColor(myGame.getMyBoard().getBoard()[k][j].getColor());
                            myGame.getMyTryoutBoard().getBoard()[k][j].setShape(myGame.getMyBoard().getBoard()[k][j].getShape());
                        }
                    }
                    return -1;
                }
            }
        }
        catch (TilesNotInSameRowOrColumnException e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
        try {
            myGame.getMyTryoutBoard().checkLegalSituation();
            System.out.println("TESTOBAMA");
            System.out.println(moves);
            int myBoardQwirkles = myGame.getMyBoard().getQwirkles();
            int myTryoutBoardQwirkles = myGame.getMyTryoutBoard().getQwirkles();
            System.out.println(myBoardQwirkles + "" + myTryoutBoardQwirkles + "ZIJN DE QWIRKLES VAN BOARD EN TRYOUT");
            int qwirkles = myGame.getMyTryoutBoard().getQwirkles() - myGame.getMyBoard().getQwirkles();
            myPoints = calculatePoints(moves, qwirkles);
            for(int i = 0; i < myGame.getMyBoard().getBoard().length; i++) {
                for (int j = 0; j < myGame.getMyBoard().getBoard()[i].length; j++) {
                    myGame.getMyBoard().getBoard()[i][j].setColor(myGame.getMyTryoutBoard().getBoard()[i][j].getColor());
                    myGame.getMyBoard().getBoard()[i][j].setShape(myGame.getMyTryoutBoard().getBoard()[i][j].getShape());
                }
            }
            return myPoints;
            }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            for(int i = 0; i < myGame.getMyTryoutBoard().getBoard().length; i++) {
                for (int j = 0; j < myGame.getMyTryoutBoard().getBoard()[i].length; j++) {
                    myGame.getMyTryoutBoard().getBoard()[i][j].setColor(myGame.getMyBoard().getBoard()[i][j].getColor());
                    myGame.getMyTryoutBoard().getBoard()[i][j].setShape(myGame.getMyBoard().getBoard()[i][j].getShape());
                }
            }
            return -1;
        }
    }

    public int makeMoveGetPointsWithoutActuallyMakingTheMove(ArrayList<Move> moves) {
        for (Move z : moves) {
            try {
                checkTileInHand(z.getTileWithoutCoordinates());
            }
            catch (DontHaveTileException e) {
                System.out.println(e.getMessage());
                return -1;
            }
        }
        myGame.getMyTryoutBoard().increaseMove();
        int myPoints = 0;
        int atMove = myGame.getMyTryoutBoard().getAtMove();
        try {
            checkSameRowOrColumn(moves);
            for (int i = 0; i < moves.size(); i++) {
                try {
                    myGame.getMyTryoutBoard().makeMove(moves.get(i).getShape(), moves.get(i).getColor(), moves.get(i).getC1(), moves.get(i).getC2(), atMove);
                    System.out.println(myGame.getMyTryoutBoard().toString());
                } catch (TileAlreadyPlacedException e) {
                    System.out.println(e.getStackTrace());
                    for(int k = 0; k < myGame.getMyTryoutBoard().getBoard().length; k++) {
                        for (int j = 0; j < myGame.getMyTryoutBoard().getBoard()[k].length; j++) {
                            myGame.getMyTryoutBoard().getBoard()[k][j].setColor(myGame.getMyBoard().getBoard()[k][j].getColor());
                            myGame.getMyTryoutBoard().getBoard()[k][j].setShape(myGame.getMyBoard().getBoard()[k][j].getShape());
                        }
                    }
                    return -1;
                }
            }
        }
        catch (TilesNotInSameRowOrColumnException e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
        try {
            myGame.getMyTryoutBoard().checkLegalSituation();
            System.out.println("TESTOBAMA");
            System.out.println(moves);
            int myBoardQwirkles = myGame.getMyBoard().getQwirkles();
            int myTryoutBoardQwirkles = myGame.getMyTryoutBoard().getQwirkles();
            System.out.println(myBoardQwirkles + "" + myTryoutBoardQwirkles + "ZIJN DE QWIRKLES VAN BOARD EN TRYOUT");
            int qwirkles = myGame.getMyTryoutBoard().getQwirkles() - myGame.getMyBoard().getQwirkles();
            myPoints = calculatePoints(moves, qwirkles);
            return myPoints;
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            for(int i = 0; i < myGame.getMyTryoutBoard().getBoard().length; i++) {
                for (int j = 0; j < myGame.getMyTryoutBoard().getBoard()[i].length; j++) {
                    myGame.getMyTryoutBoard().getBoard()[i][j].setColor(myGame.getMyBoard().getBoard()[i][j].getColor());
                    myGame.getMyTryoutBoard().getBoard()[i][j].setShape(myGame.getMyBoard().getBoard()[i][j].getShape());
                }
            }
            return -1;
        }
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
            currentC2 = myGame.getMyTryoutBoard().leftNeighbor(myGame.getMyTryoutBoard().getBoard()[currentC1][currentC2]).getC2();
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

    public int calculatePoints(ArrayList<Move> moves, int qwirkles) {
        try {
            System.out.println(qwirkles);
            myGame.getMyTryoutBoard().checkLegalSituation();
            int points = moves.size();
            System.out.println("currentPoins:");
            System.out.println(points);
            System.out.println(sameRow(moves));
            if (sameColumn(moves)) {
                Tile me = myGame.getMyTryoutBoard().getBoard()[getLowestColumn(moves)][moves.get(0).getC2()];
                int mes = 0;
                while (me.getColor() != 6) {
                    System.out.println("me = " + me.getColor());
                    Tile leftNeighbor = myGame.getMyTryoutBoard().leftNeighbor(me);
                    Tile rightNeighbor = myGame.getMyTryoutBoard().rightNeighbor(me);
                    if (leftNeighbor.getColor() != 6) {
                        if (rightNeighbor.getColor() == 6) {
                            points += 2;
                        }
                        else points += 1;
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
                } // while loop
                if (mes > moves.size()) {
                    points += mes - moves.size();
                }
            } else if (sameRow(moves)) {
                Tile me = myGame.getMyTryoutBoard().getBoard()[moves.get(0).getC1()][getLowestRow(moves)];
                System.out.println("CURRENT C1 IS: " + me.getC1());
                System.out.println("CURRENT C2 IS: " + me.getC2());
                System.out.println("IKKOMBIJSAMEROW");
                int mes = 0;
                System.out.println(me.getColor());
                while (me.getColor() != 6) {
                    System.out.println("me = " + me.getColor());
                    System.out.println("me.getShape =" + me.getShape());
                    Tile upperNeigbor = myGame.getMyTryoutBoard().upperNeigbor(me);
                    Tile lowerNeighbor = myGame.getMyTryoutBoard().lowerNeighbor(me);
                    if (upperNeigbor.getColor() != 6) {
                        if (lowerNeighbor.getColor() == 6) {
                            points += 2;
                        }
                        else points += 1;
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
                } // end while loop
                if (mes > moves.size()) {
                    points += mes - moves.size();
                }
            }
            if (qwirkles > 0) {
                points += 6;
            }
            System.out.println(myGame.getMyTryoutBoard().toString());
            return points;
        }
        catch (IllegalMoveException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
