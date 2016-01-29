package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jelle on 27/01/16.
 */
public class ServerController {
    private ServerView myView;
    private static int port;
    protected volatile ArrayList<Game> myGames = new ArrayList<>();
    protected volatile HashMap<String, ConnectedClient> nameToConnectedClient;
    protected volatile HashMap<ConnectedClient, String> connectedClientToName;
    protected volatile HashMap<ConnectedClient, Game> inGame;
    protected volatile HashMap<ConnectedClient, Integer> waitingForGame;
    protected volatile HashMap<Integer, ArrayList<ConnectedClient>> waitingForGameAmount;

    public ServerController() {
        myView = new ServerView();
        port = Integer.parseInt(myView.askUserInput("Please enter the desired portnumber."));
        nameToConnectedClient = new HashMap<>();
        connectedClientToName = new HashMap<>();
        inGame = new HashMap<>();
        waitingForGame = new HashMap<>();
        waitingForGameAmount = new HashMap<>();
        populateWaitingForGameAmount();
        myView = new ServerView();
    }

    public static void main(String[] args) {
        try {
            ServerController me = new ServerController();
            ServerSocket listener = new ServerSocket(port);
            Socket server;
            while (true) {
                server = listener.accept();
                ConnectedClient connectMe = new ConnectedClient(server, me);
                Thread myThread = new Thread(connectMe);
                myThread.start();
                System.out.println("Server: 'A client just connected'");
            }
        }
        catch (IOException e) {
            System.out.println("An IOException has occurred.");
            e.printStackTrace();
        }
    }

    public void sendToAllClients(String message) {
        for (ConnectedClient client : getConnectedClientToName().keySet()) {
            client.sendToClient(message);
        }
    }

    public void sendToAllClientsIngame(Game game, String message) {
        for (Player player : game.getMyPlayers()) {
            ConnectedClient myConnectedClient = getNameToConnectedClient().get(player.getName());
            myConnectedClient.sendToClient(message);
        }
    }

    public void populateWaitingForGameAmount() {
        for (int i = 2; i < 5; i++) {
            waitingForGameAmount.put(i, new ArrayList<ConnectedClient>());
        }
    }

    public HashMap<String, ConnectedClient> getNameToConnectedClient() {
        return nameToConnectedClient;
    }

    public HashMap<ConnectedClient, String> getConnectedClientToName() {
        return connectedClientToName;
    }

    public HashMap<ConnectedClient, Game> getInGame() {
        return inGame;
    }

    public HashMap<ConnectedClient, Integer> getWaitingForGame() {
        return waitingForGame;
    }

    public HashMap<Integer, ArrayList<ConnectedClient>> getWaitingForGameAmount() {
        return waitingForGameAmount;
    }

    public ArrayList<Game> getMyGames() {
        return myGames;
    }

    public ServerView getMyView() {
        return myView;
    }
}
