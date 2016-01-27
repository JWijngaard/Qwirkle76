package com.company;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ServerModel {
    List<String> myList;
    private List<Socket> clients;
    private Map<String, Socket> playerClientID;
    private Map<Socket, String> clientIDPlayer;
    private Map<Socket, Game> socketGame;
    List<Socket> lobbyTwo;
    List<Socket> lobbyThree;
    List<Socket> lobbyFour;

    public ServerModel() {
        myList = new ArrayList<>();
        clients = new ArrayList<>();
        playerClientID = new HashMap<>();
        clientIDPlayer = new HashMap<>();
        socketGame =new HashMap<>();
        lobbyTwo = new ArrayList<>();
        lobbyThree = new ArrayList<>();
        lobbyFour = new ArrayList<>();
    }

    public List<Socket> getClientList() {
        return clients;
    }

    public Map<String, Socket> getPlayerClientID() {
        return playerClientID;
    }

    public Map<Socket, Game> getSocketGame() {
        return socketGame;
    }

    public Map<Socket, String> getClientIDPlayer() {
        return clientIDPlayer;
    }

    public String getPlayerName(Socket socket) {
        String playerName = clientIDPlayer.get(socket);
        return playerName;
    }

    public List<Socket> getLobbyClientIDTwo() {
        return lobbyTwo;
    }
    public List<Socket> getLobbyClientIDThree() {
        return lobbyThree;
    }
    public List<Socket> getLobbyClientIDFour() {
        return lobbyFour;
    }
}
