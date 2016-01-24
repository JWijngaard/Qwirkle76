package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.net.Socket;


/**
 * Created by Jelle on 12/01/16.
 */
public class ServerController extends Thread {

    List<String> myList = new ArrayList<String>();
    List<Socket> clients = new ArrayList<>();
    public ServerController() {
        Socket socket;
        int portNumber = 8901;
        ServerSocket serverSocket = null;
        System.out.println("Server is Waiting for Players");
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server error");

        }

        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("connection Established");
                clients.add(socket);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");

            }
        }

    }

    class ServerThread extends Thread {

        String line = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Socket socket = null;

        public ServerThread(Socket s) {
            this.socket = s;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IO error in server thread");
            }

            try {
                while (true) {
                    line = in.readLine();
                    while (line != null) {
                        List<String> words = Arrays.asList((line.split("\\s+")));
                        //TODO: Parser
                        if (words.get(0).equals("hello")) {
                            if (!myList.contains(words.get(1))) {
                                out.println("hello_from_the_other_side");
                                myList.add(words.get(1));

                            } else {
                                out.println("User name : " + words.get(1) + " is already in use");
                            }
                        } else if (words.get(0).equals("Place")) {
                            //TODO: Place Stone
                        } else if (words.get(0).equals("trade")) {
                            //TODO: Trade Stone
                        } else if (words.get(0).equals("join")) {
                            //TODO: client joins game
                        } else if (words.get(0).equals("players?")) {
                            sendToAllTCPAllPlayers(myList);
                        } else {
                            out.println("");
                            out.flush();
                        }
                        out.flush();
                        System.out.println(myList);
                        break;
                    }
                }
            } catch (IOException e) {

                line = this.getName(); //reused String line for getting thread name
                System.out.println("IO Error/ Client " + line + " terminated abruptly");
            } catch (NullPointerException e) {
                line = this.getName(); //reused String line for getting thread name
                System.out.println("Client " + line + " Closed");
            } finally {
                try {
                    System.out.println("Connection Closing..");
                    if (in != null) {
                        in.close();
                        System.out.println(" Socket Input Stream Closed");
                    }

                    if (out != null) {
                        out.close();
                        System.out.println("Socket Out Closed");
                    }
                    if (socket != null) {
                        socket.close();
                        System.out.println("Socket Closed");
                    }

                } catch (IOException ie) {
                    System.out.println("Socket Close Error");
                }
            }
        }
    }

    public void sendToAllTCPAllPlayers(List<String> message) {
        for (Socket z : clients) {
            if (z != null) {
                //basically this chunk of code declares output and input streams
                //for each socket in your array of saved sockets
                PrintStream outToClient = null;
                try {
                    outToClient = new PrintStream(z.getOutputStream());
                    outToClient.println(message);
                } catch (IOException e) {
                    System.out.println("Caught an IO exception trying "
                            + "to send to TCP connections");
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendToAllTCPMessage(String message) {
        for (Socket z : clients) {
            if (z != null) {
                //basically this chunk of code declares output and input streams
                //for each socket in your array of saved sockets
                PrintStream outToClient = null;
                try {
                    outToClient = new PrintStream(z.getOutputStream());
                    outToClient.println(message);
                } catch (IOException e) {
                    System.out.println("Caught an IO exception trying "
                            + "to send to TCP connections");
                    e.printStackTrace();
                }
            }
        }
    }
}












