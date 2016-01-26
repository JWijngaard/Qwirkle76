//package com.company;
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
//        test.setScore(test.makeMoveGetPoints(((ComputerPlayer) test).askForMove()) + test.getScore());
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
//    public ArrayList<Move> askForMove() {
//        ArrayList<Move> bestMove = new ArrayList<>();
//        int bestMoveAmount = 0;
//        for (Tile z : this.getMyHand()) {
//            for (int i = 0; i < 20; i++) {
//                for (int j = 0; j < 20; j++) {
//                    ArrayList<Move> moves = new ArrayList<>();
//                    moves.add(new Move(z.getShape(), z.getColor(),i,j));
//                    int amount = this.makeMoveGetPointsWithoutActuallyMakingTheMove(moves);
//                    if (amount > bestMoveAmount) {
//                        bestMoveAmount = amount;
//                        bestMove = moves;
//                    }
//                }
//            }
//        }
//        return bestMove;
//    }
//}
