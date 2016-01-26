package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ServerThread extends Thread {
    ServerSocket serverSocket;
    private TUIView serverVieuw = new TUIView();
    private ServerModel serverModel = new ServerModel();
    List<Socket> clients = serverModel.getClientList();
    private int port;
    static Socket socket;


    public ServerThread() throws IOException {
        port = askUserInput();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server created (adress: " + serverSocket.getInetAddress() + ", port: " + port + ")");
        } catch (IOException e) {
            System.out.println("ERROR: could not create a ServerSocket on port " + port + ". It might already be in use");
            System.exit(0);
        }
        serverVieuw.logMessage("Server is Waiting for Players");
        socketAccept();
    }


    public void socketAccept() {
        while (true) {
            try {
                socket = serverSocket.accept();
                serverVieuw.logMessage("Client connected " + socket);
                clients.add(socket);
                ServerController serverController = new ServerController(socket);
                serverController.start();
            } catch (IOException e) {
                serverVieuw.logMessage("The connection could not be established, because portnumber is already in use");
            }
        }
    }
    public int askUserInput() {
        serverVieuw.logMessage("Please enter the portnumber:");
        Scanner userInputScanner = new Scanner(System.in);
        int port = 0;
        while (port == 0) {
            if (userInputScanner.hasNext()) {
                try {
                    port = Integer.parseInt(userInputScanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: port " + userInputScanner + " is not an integer");
                    System.exit(0);
                }
            }
        }
        serverVieuw.logMessageInt("The choosen portNumber is: ", port);
        return port;
    }

    public static void shutDown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerThread s = new ServerThread();
    }
}
