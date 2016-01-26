package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;


/**
 * Created by Jelle on 12/01/16.
 */
public class ServerController extends Thread {

    List<String> myList = new ArrayList<String>();
    private ServerView serverView = new ServerView();
    private ServerModel serverModel = new ServerModel();
    List<Socket> clients = serverModel.getClientList();
    Map<String, Socket> playerClientID = serverModel.getPlayerClientID();
    Map<Socket, String> clientIDPlayer = serverModel.getClientIDPlayer();
    List<Socket> lobbyTwo = new ArrayList<>();
    List<Socket> lobbyThree = new ArrayList<>();
    List<Socket> lobbyFour = new ArrayList<>();
    List<String> answer = new ArrayList<>();

    public ServerController() {

        Socket socket;
        int portNumber;
        ServerSocket serverSocket = null;

        try {
            Scanner kb = new Scanner(System.in);
            int port;
            while (true)
                try {
                    serverView.logMessage("Please enter the portnumber: ");
                    port = Integer.parseInt(kb.nextLine());
                    serverView.logMessageInt("The choosen portNumber is: ", port);
                    portNumber = port;
                    break;
                } catch (NumberFormatException nfe) {
                    serverView.logMessage("The choosen portnumber is not valid");
                }
            serverSocket = new ServerSocket(portNumber);
            serverView.logMessage("Server is Waiting for Players");
        } catch (IOException e) {
            e.printStackTrace();
            serverView.logMessage("The choosen portnumber is already in use");
        }

        while (true) {
            try {
                socket = serverSocket.accept();
                serverView.logMessage("Connection Established on " + socket);
                clients.add(socket);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            } catch (IOException e) {
                serverView.logMessage("The connection could not be established, because portnumber is already in use");
                break;
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
                serverView.logMessage("IO error in server thread");
            }

            try {
                while (true) {
                    line = in.readLine();
                    while (true) {
                        if(lobbyTwo.size() == 2){
                            lobbyTwo.remove(0);
                            lobbyTwo.remove(1);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        List<String> words = Arrays.asList((line.split("\\s+")));
                        //TODO: Parser
                        try {
                            if (words.get(0).equals("hello")) {
                                try {
                                    if (words.get(1).equals("Place") || words.get(1).equals("trade") || words.get(1).equals("join") || words.get(1).equals("players?")) {
                                        out.print("error 2");
                                    } else if (!myList.contains(words.get(1))) {
                                        out.println("hello_from_the_other_side");
                                        serverView.logMessage("Player: " + words.get(1) + " joined the server!");
                                        myList.add(words.get(1));
                                        playerClientID.put(words.get(1), socket);
                                        clientIDPlayer.put(socket, words.get(1));
                                    } else {
                                        out.println("error 2");
                                    }
                                } catch (Exception e) {
                                    out.println("error 0");
                                }
                            } else if (words.get(0).equals("players?")) {
                                sendToAllTCPAllPlayers(myList);
                            }
                            else if (words.get(0).equals("join")) {
                                try {
                                    System.out.println("01");
                                    if(words.get(1).equals("2")){
                                        System.out.println(serverModel.getPlayerName(socket));
                                        lobbyTwo.add(socket);
                                        out.println(" ");
                                    }else if(words.get(1).equals("3")){
                                        lobbyThree.add(socket);
                                    }else if(words.get(1).equals("4")){
                                        lobbyFour.add(socket);
                                    }
                                }catch (Exception e) {
                                    out.println("error 0");
                                }
                                //TODO: client joins game
                            } else {
                                out.println("error 0");
                                out.flush();
                            }
                            out.flush();
                            break;
                        } catch (Exception e) {
                            out.println("error 0");
                        }
                        //if(Hoij in een game zit dan doe dit met een WHILE LOOp)
                        try {
                            if (words.get(0).equals("Place")) {
                                //TODO: Place Stone
                                try {
                                    for (String str : words)
                                        for (String s : str.split(","))
                                            answer.add(s);
                                    try {
                                        if (answer.size() == 5) {
                                            int s = Integer.parseInt(answer.get(1));
                                            int c = Integer.parseInt(answer.get(2));
                                            int c1 = Integer.parseInt(answer.get(3));
                                            int c2 = Integer.parseInt(answer.get(4));
                                        } else if (answer.size() == 9) {
                                            int s = Integer.parseInt(answer.get(1));
                                            int c = Integer.parseInt(answer.get(2));
                                            int c1 = Integer.parseInt(answer.get(3));
                                            int c2 = Integer.parseInt(answer.get(4));
                                            int sC = Integer.parseInt(answer.get(5));
                                            int cC = Integer.parseInt(answer.get(6));
                                            int c1C = Integer.parseInt(answer.get(7));
                                            int c2C = Integer.parseInt(answer.get(8));
                                        } else if (answer.size() == 13) {
                                            int s = Integer.parseInt(answer.get(1));
                                            int c = Integer.parseInt(answer.get(2));
                                            int c1 = Integer.parseInt(answer.get(3));
                                            int c2 = Integer.parseInt(answer.get(4));
                                            int sC = Integer.parseInt(answer.get(5));
                                            int cC = Integer.parseInt(answer.get(6));
                                            int c1C = Integer.parseInt(answer.get(7));
                                            int c2C = Integer.parseInt(answer.get(8));
                                            int sCC = Integer.parseInt(answer.get(9));
                                            int cCC = Integer.parseInt(answer.get(10));
                                            int c1CC = Integer.parseInt(answer.get(11));
                                            int c2CC = Integer.parseInt(answer.get(12));
                                        } else if (answer.size() == 17) {
                                            int s = Integer.parseInt(answer.get(1));
                                            int c = Integer.parseInt(answer.get(2));
                                            int c1 = Integer.parseInt(answer.get(3));
                                            int c2 = Integer.parseInt(answer.get(4));
                                            int sC = Integer.parseInt(answer.get(5));
                                            int cC = Integer.parseInt(answer.get(6));
                                            int c1C = Integer.parseInt(answer.get(7));
                                            int c2C = Integer.parseInt(answer.get(8));
                                            int sCC = Integer.parseInt(answer.get(9));
                                            int cCC = Integer.parseInt(answer.get(10));
                                            int c1CC = Integer.parseInt(answer.get(11));
                                            int c2CC = Integer.parseInt(answer.get(12));
                                            int sCCC = Integer.parseInt(answer.get(13));
                                            int cCCC = Integer.parseInt(answer.get(14));
                                            int c1CCC = Integer.parseInt(answer.get(15));
                                            int c2CCC = Integer.parseInt(answer.get(16));
                                        } else if (answer.size() == 21) {
                                            int s = Integer.parseInt(answer.get(1));
                                            int c = Integer.parseInt(answer.get(2));
                                            int c1 = Integer.parseInt(answer.get(3));
                                            int c2 = Integer.parseInt(answer.get(4));
                                            int sC = Integer.parseInt(answer.get(5));
                                            int cC = Integer.parseInt(answer.get(6));
                                            int c1C = Integer.parseInt(answer.get(7));
                                            int c2C = Integer.parseInt(answer.get(8));
                                            int sCC = Integer.parseInt(answer.get(9));
                                            int cCC = Integer.parseInt(answer.get(10));
                                            int c1CC = Integer.parseInt(answer.get(11));
                                            int c2CC = Integer.parseInt(answer.get(12));
                                            int sCCC = Integer.parseInt(answer.get(13));
                                            int cCCC = Integer.parseInt(answer.get(14));
                                            int c1CCC = Integer.parseInt(answer.get(15));
                                            int c2CCC = Integer.parseInt(answer.get(16));
                                            int sCCCC = Integer.parseInt(answer.get(17));
                                            int cCCCC = Integer.parseInt(answer.get(18));
                                            int c1CCCC = Integer.parseInt(answer.get(19));
                                            int c2CCCC = Integer.parseInt(answer.get(20));
                                        } else {
                                            out.println("error 0");
                                        }
                                    } catch (Exception e) {
                                        out.println("error 0");
                                    }
                                } catch (Exception e) {
                                    out.println("error 0");
                                }
                                answer.clear();
                            } else if (words.get(0).equals("trade")) {
                                //TODO: Trade Stone
                                for (String str : words)
                                    for (String s : str.split(","))
                                        answer.add(s);
                                if (answer.size() == 5) {
                                    int s = Integer.parseInt(answer.get(1));
                                    int c = Integer.parseInt(answer.get(2));
                                    int c1 = Integer.parseInt(answer.get(3));
                                    int c2 = Integer.parseInt(answer.get(4));
                                } else if (answer.size() == 9) {
                                    int s = Integer.parseInt(answer.get(1));
                                    int c = Integer.parseInt(answer.get(2));
                                    int c1 = Integer.parseInt(answer.get(3));
                                    int c2 = Integer.parseInt(answer.get(4));
                                    int sC = Integer.parseInt(answer.get(5));
                                    int cC = Integer.parseInt(answer.get(6));
                                    int c1C = Integer.parseInt(answer.get(7));
                                    int c2C = Integer.parseInt(answer.get(8));
                                } else if (answer.size() == 13) {
                                    int s = Integer.parseInt(answer.get(1));
                                    int c = Integer.parseInt(answer.get(2));
                                    int c1 = Integer.parseInt(answer.get(3));
                                    int c2 = Integer.parseInt(answer.get(4));
                                    int sC = Integer.parseInt(answer.get(5));
                                    int cC = Integer.parseInt(answer.get(6));
                                    int c1C = Integer.parseInt(answer.get(7));
                                    int c2C = Integer.parseInt(answer.get(8));
                                    int sCC = Integer.parseInt(answer.get(9));
                                    int cCC = Integer.parseInt(answer.get(10));
                                    int c1CC = Integer.parseInt(answer.get(11));
                                    int c2CC = Integer.parseInt(answer.get(12));
                                } else if (answer.size() == 17) {
                                    int s = Integer.parseInt(answer.get(1));
                                    int c = Integer.parseInt(answer.get(2));
                                    int c1 = Integer.parseInt(answer.get(3));
                                    int c2 = Integer.parseInt(answer.get(4));
                                    int sC = Integer.parseInt(answer.get(5));
                                    int cC = Integer.parseInt(answer.get(6));
                                    int c1C = Integer.parseInt(answer.get(7));
                                    int c2C = Integer.parseInt(answer.get(8));
                                    int sCC = Integer.parseInt(answer.get(9));
                                    int cCC = Integer.parseInt(answer.get(10));
                                    int c1CC = Integer.parseInt(answer.get(11));
                                    int c2CC = Integer.parseInt(answer.get(12));
                                    int sCCC = Integer.parseInt(answer.get(13));
                                    int cCCC = Integer.parseInt(answer.get(14));
                                    int c1CCC = Integer.parseInt(answer.get(15));
                                    int c2CCC = Integer.parseInt(answer.get(16));
                                } else if (answer.size() == 21) {
                                    int s = Integer.parseInt(answer.get(1));
                                    int c = Integer.parseInt(answer.get(2));
                                    int c1 = Integer.parseInt(answer.get(3));
                                    int c2 = Integer.parseInt(answer.get(4));
                                    int sC = Integer.parseInt(answer.get(5));
                                    int cC = Integer.parseInt(answer.get(6));
                                    int c1C = Integer.parseInt(answer.get(7));
                                    int c2C = Integer.parseInt(answer.get(8));
                                    int sCC = Integer.parseInt(answer.get(9));
                                    int cCC = Integer.parseInt(answer.get(10));
                                    int c1CC = Integer.parseInt(answer.get(11));
                                    int c2CC = Integer.parseInt(answer.get(12));
                                    int sCCC = Integer.parseInt(answer.get(13));
                                    int cCCC = Integer.parseInt(answer.get(14));
                                    int c1CCC = Integer.parseInt(answer.get(15));
                                    int c2CCC = Integer.parseInt(answer.get(16));
                                    int sCCCC = Integer.parseInt(answer.get(17));
                                    int cCCCC = Integer.parseInt(answer.get(18));
                                    int c1CCCC = Integer.parseInt(answer.get(19));
                                    int c2CCCC = Integer.parseInt(answer.get(20));
                                } else {
                                    out.println("error 0");
                                }
                                answer.clear();
                            } else {
                                out.println("error 0");
                                out.flush();
                            }
                            out.flush();
                            break;
                        } catch (Exception e) {
                            out.println("error 0");
                        }
                    }
                }
            } catch (IOException e) {
                line = this.getName();
                serverView.logMessage("IO Error/ Client" + line + " terminated abruptly");
            } catch (NullPointerException e) {
                line = this.getName();
                serverView.logMessage("Client " + line + " Closed");
            } finally {
                try {
                    System.out.println("Connection Closing..");
                    if (in != null) {
                        in.close();
                        serverView.logMessage("Socket Input Stream Closed");
                    }

                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }

                } catch (IOException ie) {
                    serverView.logMessage("Socket Close Error");
                }
            }
        }
    }

    public void sendToAllTCPAllPlayers(List<String> message) {
        for (Socket z : clients) {
            if (z != null) {
                PrintStream outToClient;
                try {
                    outToClient = new PrintStream(z.getOutputStream());
                    outToClient.println(message);
                } catch (IOException e) {
                    serverView.logMessage("Caught an IO exception trying to send to TCP connections");
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendToClientMessage(String message, Socket socket) {
        PrintStream outToClient;
        try {
            outToClient = new PrintStream(socket.getOutputStream());
            outToClient.println(message);
        } catch (IOException e) {
            serverView.logMessage("Caught an IO exception trying to send to TCP connections");
            e.printStackTrace();
        }
    }
}