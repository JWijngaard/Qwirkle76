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
    List<Socket> clients;
    List<String> playerNames;
    Map<String, Socket> playerClientID;
    Map<Socket, String> clientIDPlayer;
    Map<Socket, Game> socketGame;
    Map<String, Socket> test;
    List<Socket> lobbyTwo;
    List<Socket> lobbyThree;
    List<Socket> lobbyFour;

    public ServerModel() {
        myList = new ArrayList<>();
        playerNames = new ArrayList<>();
        clients = new ArrayList<>();
        playerClientID = new HashMap<>();
        clientIDPlayer = new HashMap<>();
        test = new HashMap<>();
        socketGame = new HashMap<>();
        lobbyTwo = new ArrayList<>();
        lobbyThree = new ArrayList<>();
        lobbyFour = new ArrayList<>();
    }



    public Map<String, Socket> getTest() {
        return test;
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


    public List<Socket> getLobbyClientIDTwo() {
        return lobbyTwo;
    }
    public List<Socket> getLobbyClientIDThree() {
        return lobbyThree;
    }
    public List<Socket> getLobbyClientIDFour() {
        return lobbyFour;
    }

    public List<String> getPlayername() {
        return playerNames;
    }
}
