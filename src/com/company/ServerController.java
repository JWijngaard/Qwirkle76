package com.company;

import com.company.Exceptions.WrongCommandoException;
import com.company.Exceptions.WrongNameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by JulianStellaard on 26/01/16.
 */
//class ServerController extends Thread{
//
//    ServerThread serverThread;
//    ServerView serverView = new ServerView();
//    Socket socket = null;
//    BufferedReader in = null;
//    PrintWriter out = null;
//    String inComing;
//
//    public ServerController(Socket s) {
//        this.socket = s;
//    }
//
//    public void run() {
//        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream());
//        } catch (IOException e) {
//            //Exception
//            System.out.println("Exception");
//        }
//        try {
//            while (true) {
//                parserCommando();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WrongNameException e) {
//            e.getMessage();
//        } catch (WrongCommandoException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void parserCommando() throws WrongNameException, WrongCommandoException, IOException {
//        try {
//            while(true) {
//                inComing = in.readLine();
//                while (inComing != null) {
//                    List<String> words = Arrays.asList((inComing.split("\\s+")));
//                    System.out.println("begin");
//                    System.out.println(serverThread.lobbyTwo);
////                    if(serverThread.socketGame.get(socket) != null) {
////                        System.out.println("move");
////                        parserMoves(serverThread.socketGame.get(socket));
////                    }
////                    if(serverThread.lobbyTwo.size() == 2 && serverThread.lobbyTwo.contains(socket)) {
////                        System.out.println("lol");
////                        String name1 = serverThread.getPlayerName(serverThread.lobbyTwo.get(0));
////                        String name2 = serverThread.getPlayerName(serverThread.lobbyTwo.get(1));
////                        Player player1 = new Player(name1);
////                        Player player2 = new Player(name2);
////                        ArrayList<Player> players = new ArrayList<>();
////                        players.add(player1);
////                        players.add(player2);
////                        Game myGame = new Game(players);
////                        myGame.fillMyBag();
////                        myGame.distributeTiles();
////                        serverThread.socketGame.put(serverThread.lobbyTwo.get(0), myGame);
////                        serverThread.socketGame.put(serverThread.lobbyTwo.get(1), myGame);
////                        System.out.println(serverThread.lobbyTwo);
////                        System.out.println("wha");
////                        serverThread.lobbyTwo.remove(0);
////                        serverThread.lobbyTwo.remove(0);
////                    }
//                    if (inComing.startsWith("hello")) {
//                        if (words.get(1).equals("Place") || words.get(1).equals("trade") || words.get(1).equals("join") || words.get(1).equals("players?")) {
//                            out.println("error 2");
//                            throw new WrongNameException("error 2");
//                        } else if (!serverThread.myList.contains(words.get(1))) {
//                            out.println("hello_from_the_other_side ");
//                            String result = "joinlobby";
//                            for (Socket z : serverThread.clients) {
//                                if (!(serverThread.getPlayerName(z) == null)) {
//                                    result += " " + serverThread.getPlayerName(z);
//                                }
//                            }
//                            out.println(result);
//                            serverView.logMessage("Player: " + words.get(1) + " joined the server!");
//                            serverThread.myList.add(words.get(1));
//                        } else {
//                            out.println("error 0");
//                            throw new WrongCommandoException("error 0");
//                        }
//                    }
//                    else if (words.get(0).equals("players?")) {
//                        //TODO: Send to all players
//                    }
//                    else if (words.get(0).equals("join")) {
//                        if (words.get(1).equals("2")) {
//                            serverThread.lobbyTwo.add(socket);
//                            if (serverThread.lobbyTwo.size() != 2) {
//                                out.println("joint lobby 2 waiting for players");
//                                System.out.println(serverThread.lobbyTwo);
//                            }
//                        } else if (words.get(1).equals("3")) {
//                            serverThread.lobbyThree.add(socket);
//                            if (serverThread.lobbyTwo.size() != 3) {
//                                out.println("joint lobby 3 waiting for players");
//                            }
//                        } else if (words.get(1).equals("4")) {
//                            serverThread.lobbyFour.add(socket);
//                            if (serverThread.lobbyTwo.size() != 4) {
//                                out.println("joint lobby 4 waiting for players");
//                            }
//                        } else {
//                            out.println("error 0");
//                            throw new WrongCommandoException("error 0");
//                        }
//                    }
//                    else {
//                        out.println("error 0");
//                        System.out.println("-2");
//                        System.out.println("error 0");
//                    }
//                    System.out.println("-8");
//                    out.flush();
//                    inComing = in.readLine();
//                }
//            }
//        }catch (IOException e) {
//            ServerThread.shutDown();
//        }
//    }
//
//    public void parserMoves(Game myGame) {
//        try {
//
//            while(true) {
//                inComing = in.readLine();
//                while (inComing != null) {
//                    List<String> words = Arrays.asList((inComing.split("\\s+")));
//                }
//                ServerThread.shutDown();
//            }
//        }catch (IOException e) {
//            ServerThread.shutDown();
//        }
//    }
//
//}
