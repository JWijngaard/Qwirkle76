package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

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
                                    result += splittedString[i];
                                }
                                String[] strings = result.split(",");
                                int[] ints = new int[20];
                                for (int j = 0; j < strings.length; j++) {
                                    ints[j] = Integer.parseInt(strings[j]);
                                }
                                ArrayList<Move> moves = new ArrayList<>();
                                if (ints.length > 4) {
                                    for(int k = 0; k < (ints.length/4); k++) {
                                        Move move = new Move(ints[0],ints[1],ints[3],ints[2]);
                                        moves.add(move);
                                    }
                                }
                                Game myGame = serverController.getInGame().get(this);
                                String myName = serverController.getConnectedClientToName().get(this);
                                Player me = new Player("place");
                                for (Player player : myGame.getMyPlayers()) {
                                    if (myName.equals(player.getName())) {
                                        me = player;
                                    }
                                }
                                int points = me.makeMoveGetPoints(moves);
                                if (points != -1) {
                                    sendToClient(points);
                                    setNextTurn();
                                } else {
                                    error(out, 1);
                                }
                            }
                        } else if (serverController.getInGame().containsKey(this)) {
                            //Zit ingame heeft geen turn
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
}
