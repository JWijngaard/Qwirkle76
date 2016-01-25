package com.company;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jelle on 12/01/16.
 */
public class ServerModel {

    private Map<String, Socket> playerClientID;
    private Map<Socket, String> clientIDPlayer;
    private List<Socket> clients;

    public ServerModel() {
        playerClientID = new HashMap<>();
        clientIDPlayer = new HashMap<>();
        clients = new ArrayList<>();
    }

    public List<Socket> getClientList() {
        return clients;
    }

    public Map<String, Socket> getPlayerClientID() {
        return playerClientID;
    }

    public Map<Socket, String> getClientIDPlayer() {
        return clientIDPlayer;
    }

    public String getPlayerName(Socket socket) {
        String playerName = clientIDPlayer.get(socket);
        return playerName;
    }
}
