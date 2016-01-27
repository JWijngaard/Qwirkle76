//package com.company;
//
//import com.company.Exceptions.TileAlreadyPlacedException;
//
//import java.util.ArrayList;
//
///**
// * Created by Jelle on 26/01/16.
// */
//public class ComputerPlayer extends Player {
//    public ComputerPlayer(String name) {
//        super(name);
//    }
//
//    public static void main(String[] args) {
//        ComputerPlayer test = new ComputerPlayer("pieter");
//        ArrayList<Player> players = new ArrayList<>();
//        players.add(test);
//        myGame = new Game(players);
//        myGame.fillMyBag();
//        for (int i = 0; i < 6; i++) {
//            test.addTileToHand(myGame.getBagOfStones().get(i));
//        }
//        try {
//            myGame.getMyBoard().makeMove(3, 2, 6, 6, 1);
//            myGame.getMyBoard().makeMove(4,2,6,7,1);
//        } catch (TileAlreadyPlacedException e) {
//            e.printStackTrace();
//        }
//        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
//        System.out.println(myGame.getMyBoard().toString());
//        System.out.println(test.getScore());
//        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
//        System.out.println(myGame.getMyBoard().toString());
//        System.out.println(test.getScore());
//        test.setScore(test.makeMoveGetPoints(test.askForMove()) + test.getScore());
//        System.out.println(myGame.getMyBoard().toString());
//        System.out.println(test.getScore());
//    }
//
//    @Override
//    public void myTurn() {
//        ArrayList<Move> askForMoveResult = this.askForMove();
//        if (!(askForMoveResult.isEmpty())) {
//            makeMoveGetPoints(askForMoveResult);
//        } else {
//            //TODO: Implement trade!
//        }
//    }
//
//    @Override
//    public ArrayList<Move> askForMove() {
//        //TODO: implement
//        ArrayList<Move> bestMove = new ArrayList<>();
//        int bestMoveAmount = 0;
//        Board theBoard = myGame.getMyBoard();
//        for (int i = 0; i < theBoard.getBoard().length; i++) {
//            for (int j = 0; j < theBoard.getBoard().length; j++) {
//                if (theBoard.getBoard()[i][j].getShape() != 6) {
//                    for (int k = 0; k < getMyHand().size(); k++) {
//                        Tile me = getMyHand().get(k);
//                        ArrayList<Move> moves = new ArrayList<>();
//                        Move myMove1 = new Move(me.getShape(), me.getColor(), i, (j-1));
//                        moves.add(myMove1);
//                        int amount1 = makeMoveGetPointsWithoutActuallyMakingTheMove(moves);
//                        if (amount1 > bestMoveAmount) {
//                            bestMove = moves;
//                        }
//                        moves.clear();
//                        Move myMove2 = new Move(me.getShape(), me.getColor(), i, (j+1));
//                        moves.add(myMove2);
//                        int amount2 = makeMoveGetPointsWithoutActuallyMakingTheMove(moves);
//                        if (amount2 > bestMoveAmount) {
//                            bestMove = moves;
//                        }
//                        moves.clear();
//                        Move myMove3 = new Move(me.getShape(), me.getColor(), (i-1), j);
//                        moves.add(myMove3);
//                        int amount3 = makeMoveGetPointsWithoutActuallyMakingTheMove(moves);
//                        if (amount3 > bestMoveAmount) {
//                            bestMove = moves;
//                        }
//                        moves.clear();
//                        Move myMove4 = new Move(me.getShape(), me.getColor(), (i+1), j);
//                        moves.add(myMove4);
//                        int amount4 = makeMoveGetPointsWithoutActuallyMakingTheMove(moves);
//                        if (amount4 > bestMoveAmount) {
//                            bestMove = moves;
//                        }
//                        moves.clear();
//                    }
//                }
//            }
//        }
//        return bestMove;
//    }
//
//    @Override
//    public ArrayList<Tile> askForTrade() {
//        ArrayList<Tile> whichTiles = new ArrayList<>();
//        whichTiles.add(getMyHand().get(0));
//        return whichTiles;
//        //TODO: Maybe make this smarter?
//    }
//}
