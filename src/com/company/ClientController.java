package com.company;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;
import java.io.BufferedReader;

/**
 * Created by Jelle on 12/01/16.
 */
public class ClientController {

    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;



    public ClientController(String serverAddress) throws Exception{
        // Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void main(String[] args) throws Exception {
        String serverAddress = "130.89.232.171";
        ClientController client = new ClientController(serverAddress);
    }
}