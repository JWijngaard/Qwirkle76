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

    public ServerController() {

        int portNumber = 4000;
        Socket socket;
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
                        System.out.println(words);
                        System.out.println(myList);
                        //TODO: Parser
                        if (words.get(0).equals("Hello")) {
                            if(!myList.contains(words.get(1))) {
                                out.println("Hello_from_the_other_side");
                                myList.add(words.get(1));
                            }else {
                                out.println("User name : " + words.get(1) + " is already in use");
                            }
                        }else {
                            out.println("");
                            out.flush();
                        }
                        out.flush();
                        System.out.println(myList);
                        System.out.println("-1");
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
}












