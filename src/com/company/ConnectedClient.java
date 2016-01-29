package com.company;

import com.company.Exceptions.DontHaveTileException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jelle on 27/01/16.
 */
public class ConnectedClient implements Runnable {
    private Socket server;
    private String line;
    private ServerController serverController;

    public ConnectedClient(Socket server, ServerController serverController) {
        this.server = server;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintStream out = new PrintStream(server.getOutputStream());
            while ((line = in.readLine()) != null && !line.equals("quit")) {
                String[] splittedString = line.split("\\s+");
                if (splittedString[0].equals("hello") && !serverController.getConnectedClientToName().containsKey(this)) {
                    try {
                        if (!serverController.getNameToConnectedClient().containsKey(splittedString[1])) {
                            serverController.getNameToConnectedClient().put(splittedString[1], this);
                            serverController.getConnectedClientToName().put(this, splittedString[1]);
                            sendToClient("hello_from_the_other_side");
                            String joinLobbyStringAlle = "joinlobby ";
                            joinLobbyStringAlle += splittedString[1];
                            serverController.sendToAllClients(joinLobbyStringAlle);
                            sendToClient(joinLobbyStringMe());
                        } else error(out, 2);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        error(out, 2);
                    }
                } else if (!serverController.getConnectedClientToName().containsKey(this)) {
                    error(out, 2);
                } else {
                    if (splittedString[0].equals("join") && !serverController.getInGame().containsKey(this)) {
                        try {
                            serverController.getWaitingForGame().put(this, Integer.parseInt(splittedString[1]));
                            serverController.getWaitingForGameAmount().get(Integer.parseInt(splittedString[1])).add(this);
                            ArrayList<ConnectedClient> clients = serverController.getWaitingForGameAmount().get(Integer.parseInt(splittedString[1]));
                            checkStartGame(clients, Integer.parseInt(splittedString[1]));
                            if (serverController.getWaitingForGameAmount().get(Integer.parseInt(splittedString[1])).contains(this)) {
                                String result = "Player just joined queue [" + splittedString[1] + "]: ";
                                result += serverController.getConnectedClientToName().get(this);
                                serverController.getMyView().logMessage(result);
                            } else {
                                String result = "Player just skipped queue [" + splittedString[1] + "]: ";
                                result += serverController.getConnectedClientToName().get(this);
                                serverController.getMyView().logMessage(result);
                            }
                        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            error(out, 0);
                        }
                    } else if (!(serverController.getWaitingForGame().containsKey(this) || serverController.getInGame().containsKey(this))) {
                        error(out, 0);
                    } else {
                        if (serverController.getInGame().containsKey(this) &&
                                serverController.getInGame().get(this).getTurn().getName().equals(serverController.getConnectedClientToName().get(this))) {
                            //Zit ingame en heeft turn
                            if (splittedString[0].equals("place")) {
                                String result = "";
                                for (int i = 1; i < splittedString.length; i++) {
                                    result += splittedString[i] + ",";
                                }
                                System.out.println(result);
                                String[] strings = result.split(",");
                                int[] ints = new int[23];
                                for (int j = 0; j < strings.length; j++) {
                                    ints[j] = Integer.parseInt(strings[j]);
                                }
                                ArrayList<Move> moves = new ArrayList<>();
                                int amount = 0;
                                try {
                                    if (splittedString.length > 1) {
                                        Move move = new Move(ints[0],ints[1],ints[3],ints[2]);
                                        out.println("" + ints[0] + ints[1] + ints[3] +ints[2]);
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                    if (splittedString.length > 2) {
                                        Move move = new Move(ints[4],ints[5],ints[7],ints[6]);
                                        out.println("" + ints[4] + ints[5] + ints[7] +ints[6] + "Jep I got here");
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                    if (splittedString.length > 3) {
                                        Move move = new Move(ints[8],ints[9],ints[11],ints[10]);
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                    if (splittedString.length > 4) {
                                        Move move = new Move(ints[12],ints[13],ints[15],ints[14]);
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                    if (splittedString.length > 5) {
                                        Move move = new Move(ints[16],ints[17],ints[19],ints[18]);
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                    if (splittedString.length > 6) {
                                        Move move = new Move(ints[20], ints[21], ints[23], ints[22]);
                                        if (!(move.getC1() == 0 && move.getC2() == 0 && move.getColor() == 0 && move.getShape() == 0)) {
                                            moves.add(move);
                                            amount++;
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    moves.clear();
                                }
                                Game myGame = serverController.getInGame().get(this);
                                String myName = serverController.getConnectedClientToName().get(this);
                                Player me = null;
                                for (Player player : myGame.getMyPlayers()) {
                                    if (myName.equals(player.getName())) {
                                        me = player;
                                    }
                                }
                                int points = 0;
                                if (me != null) {
                                    points = me.makeMoveGetPoints(moves);
                                    sendToClient("My tiles are: " + me.myHandToString());
                                }
                                if (points != -1) {
                                    splittedString[0] = "placed " + serverController.getConnectedClientToName().get(this) + " " + points + " ";
                                    sendToClient(newTiles(myGame,amount));
                                    String result2 = "";
                                    for (String string : splittedString) {
                                        result2 += string + " ";
                                    }
                                    serverController.sendToAllClientsIngame(myGame, result2);
                                    setNextTurn();
                                } else {
                                    out.println("wtf");
                                    error(out, 1);
                                }
                            }
                            if (splittedString[0].equals("trade")) {
                                int amount = 0;
                                Game myGame = serverController.getInGame().get(this);
                                ArrayList<Tile> tilesToTrade = new ArrayList<>();
                                for (String string : splittedString) {
                                    if (!string.equals("trade")) {
                                        String[] strings = string.split(",");
                                        int[] ints = new int[6];
                                        try {
                                            ints[0] = Integer.parseInt(strings[0]);
                                            ints[1] = Integer.parseInt(strings[1]);
                                            Tile tile = new Tile(-100, -100);
                                            tile.setColor(ints[1]);
                                            tile.setShape(ints[0]);
                                            System.out.println(tile);
                                            tilesToTrade.add(tile);
                                        } catch (ArrayIndexOutOfBoundsException e) {

                                        }
                                    }
                                }
                                String myName = serverController.getConnectedClientToName().get(this);
                                Player me = null;
                                for (Player player : myGame.getMyPlayers()) {
                                    if (myName.equals(player.getName())) {
                                        me = player;
                                        System.out.println("test 2");
                                    }
                                }
                                try {
                                    for (Tile tile : tilesToTrade) {
                                        me.checkTileInHand(tile);
                                        amount++;
                                        System.out.println("test 3");
                                    }
                                } catch (DontHaveTileException e) {
                                    System.out.println("test 4");
                                    tilesToTrade = null;
                                    amount = 0;
                                    error(out, 1);
                                    serverController.getMyView().logMessage("Wrong trade command.");
                                }
                                for (Tile tile : tilesToTrade) {
                                    myGame.addToBag(tile);
                                    System.out.println("test 5");
                                }
                                if (amount > 0) {
                                    sendToClient(newTiles(myGame, amount));
                                    System.out.println("test 6");
                                    setNextTurn();
                                }
                            }
                        } else if (serverController.getInGame().containsKey(this)) {
                            error(out, 1);
                        }
                    }
                }
            }
            server.close();
        } catch (IOException e) {
            System.out.println("An IOException occurred: ");
            e.printStackTrace();
        }
    }

    public void error(PrintStream out, int code) {
        String result = "error ";
        result += code;
        sendToClient(result);
    }

    public void checkStartGame(ArrayList<ConnectedClient> clients, int amount) {
        if (clients.size() == amount) {
            ArrayList<Player> myPlayers = new ArrayList<>();
            ArrayList<ConnectedClient> myClients = new ArrayList<>();
            for (ConnectedClient client : clients) {
                String myName =serverController.getConnectedClientToName().get(client);
                myPlayers.add(new Player(myName));
                myClients.add(client);
                serverController.getWaitingForGame().remove(client);
            }
            ArrayList<Game> myGames = serverController.getMyGames();
            Game newGame = new Game(myPlayers);
            myGames.add(newGame);
            for (int j = 0; j < myClients.size(); j++) {
                serverController.getInGame().put(myClients.get(j), newGame);
            }
            serverController.getWaitingForGameAmount().put(amount, new ArrayList<ConnectedClient>());
            newGame.setPlayersInGame();
            newGame.fillMyBag();
            newGame.distributeTiles();
            sendStartCommand();
            setATurn();
        }
    }

    public void setATurn() {
        Game inThisGame = serverController.getInGame().get(this);
        inThisGame.setTurn(inThisGame.getMyPlayers().get(0));
        String result = "turn ";
        result += inThisGame.getMyPlayers().get(0).getName();
        serverController.sendToAllClientsIngame(inThisGame, result);
        serverController.getMyView().logMessage("Turn: " + inThisGame.getMyPlayers().get(0).getName());
    }

    public void setNextTurn() {
        Game inThisGame = serverController.getInGame().get(this);
        inThisGame.nextTurn();
        String result = "turn ";
        String nextTurnName = inThisGame.getTurn().getName();
        result += nextTurnName;
        serverController.sendToAllClientsIngame(inThisGame,result);
        serverController.getMyView().logMessage("Turn: " + inThisGame.getTurn().getName());
    }

    public void sendStartCommand() {
        Game inThisGame = serverController.getInGame().get(this);
        String result = "start ";
        String result2 = "";
        for (Player player : serverController.getInGame().get(this).getMyPlayers()) {
            String name = player.getName();
            String tiles = "";
            System.out.println(player.getMyHand());
            for (Tile tile : player.getMyHand()) {
                tiles += tile.getShape();
                tiles += ",";
                tiles += tile.getColor();
                tiles += " ";
            }
            System.out.println(player.getMyHand());
            result += name + " ";
            result2 += name + "(tiles " + tiles +  ") en ";
        }
        result2 += "dat was het";
        serverController.sendToAllClientsIngame(inThisGame,result);
        serverController.sendToAllClientsIngame(inThisGame,result2);
        serverController.getMyView().logMessage("Started a game with " + result2);
    }

    public void sendToClient(Object message) {
        try {
            PrintStream out = new PrintStream(server.getOutputStream());
            out.println(message);
            serverController.getMyView().logMessage("Sent " + message + " to client " + serverController.getConnectedClientToName().get(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String joinLobbyStringMe() {
        String result = "joinlobby ";
        for (int i = 0; i < serverController.getNameToConnectedClient().size(); i++) {
            Object[] myKeySet = serverController.getNameToConnectedClient().keySet().toArray();
            String current = (String) myKeySet[i];
            result += current + " ";
        }
        return result;
    }

    public String newTiles(Game game, int amount) {
        ArrayList<Tile> bagOfStones = game.getBagOfStones();
        CopyOnWriteArrayList<Tile> newHand = new CopyOnWriteArrayList<>();
        for (int k = 0; k < amount; k++) {
            Random randomGenerator = new Random();
            int j = randomGenerator.nextInt(bagOfStones.size());
            newHand.add(bagOfStones.get(j));
            bagOfStones.remove(j);
        }
        String result = "newstones ";
        for (Tile tile : newHand) {
            result += tile.getShape();
            result += ",";
            result += tile.getColor();
            result += " ";
        }
        return result;
    }
}
