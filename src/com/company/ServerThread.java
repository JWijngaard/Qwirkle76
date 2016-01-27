package com.company;

import com.company.Exceptions.WrongCommandoException;
import com.company.Exceptions.WrongNameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ServerThread extends Thread {
    ServerSocket serverSocket;
    private TUIView serverVieuw = new TUIView();
//    private ServerModel serverModel = new ServerModel();

    List<String> myList = new ArrayList<>();
    List<Socket> clients = new ArrayList<>();
    List<String> playerNames = new ArrayList<>();
    Map<String, Socket> playerClientID = new HashMap<>();
    Map<Socket, String> clientIDPlayer = new HashMap<>();
    Map<Socket, Game> socketGame = new HashMap<>();
    List<Socket> lobbyTwo = new ArrayList<>();
    List<Socket> lobbyThree = new ArrayList<>();
    List<Socket> lobbyFour =  new ArrayList<>();

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

    public String getPlayerName(Socket socket) {
        String playerName = clientIDPlayer.get(socket);
        return playerName;
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

    class ServerController extends Thread{

        ServerView serverView = new ServerView();
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String inComing;

        public ServerController(Socket s) {
            this.socket = s;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                //Exception
                System.out.println("Exception");
            }
            try {
                while (true) {
                    parserCommando();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WrongNameException e) {
                e.getMessage();
            } catch (WrongCommandoException e) {
                e.printStackTrace();
            }

        }

        public void parserCommando() throws WrongNameException, WrongCommandoException, IOException {
            try {
                while(true) {
                    inComing = in.readLine();
                    while (inComing != null) {
                        List<String> words = Arrays.asList((inComing.split("\\s+")));
                        while (true) {
                            if (socketGame.get(socket) != null) {
                                parserMoves(socketGame.get(socket));
                            }else if (lobbyTwo.size() == 2 && lobbyTwo.contains(socket)) {
                                String name1 = getPlayerName(lobbyTwo.get(0));
                                String name2 = getPlayerName(lobbyTwo.get(1));
                                Player player1 = new Player(name1);
                                Player player2 = new Player(name2);
                                ArrayList<Player> players = new ArrayList<>();
                                players.add(player1);
                                players.add(player2);
                                Game myGame = new Game(players);
                                myGame.fillMyBag();
                                myGame.distributeTiles();
                                socketGame.put(lobbyTwo.get(0), myGame);
                                socketGame.put(lobbyTwo.get(1), myGame);
                                System.out.println(lobbyTwo);
                                System.out.println("wha" + socket);
                                lobbyTwo.remove(0);
                                lobbyTwo.remove(0);
                                 while (true) {
                                     if (socketGame.get(socket) != null) {
                                         parserMoves(socketGame.get(socket));
                                     }
                                 }
                            }else {
                                 break;
                             }
                        }
                        if (inComing.startsWith("hello")) {
                            if (words.get(1).equals("Place") || words.get(1).equals("trade") || words.get(1).equals("join") || words.get(1).equals("players?")) {
                                out.println("error 2");
                                throw new WrongNameException("error 2");
                            } else if (!myList.contains(words.get(1))) {
                                out.println("hello_from_the_other_side ");
                                String result = "joinlobby";
                                for (Socket z : clients) {
                                    if (!(getPlayerName(z) == null)) {
                                        result += " " + getPlayerName(z);
                                    }
                                }
                                out.println(result);
                                serverView.logMessage("Player: " + words.get(1) + " joined the server!");
                                myList.add(words.get(1));
                            } else {
                                out.println("error 0");
                                throw new WrongCommandoException("error 0");
                            }
                        }
                        else if (words.get(0).equals("players?")) {
                            //TODO: Send to all players
                        }
                        else if (words.get(0).equals("join")) {
                            if (words.get(1).equals("2")) {
                                lobbyTwo.add(socket);
                                out.println("joint lobby 2 waiting for players");
                            } else if (words.get(1).equals("3")) {
                                lobbyThree.add(socket);
                                out.println("joint lobby 3 waiting for players");
                            } else if (words.get(1).equals("4")) {
                                lobbyFour.add(socket);
                                out.println("joint lobby 4 waiting for players");
                            } else {
                                out.println("error 0");
                                throw new WrongCommandoException("error 0");
                            }
                        }
                        else {
                            out.println("error 0");
                            System.out.println("error 0");
                        }
                        out.flush();
                        inComing = in.readLine();
                    }
                }
            }catch (IOException e) {
                ServerThread.shutDown();
            }
        }

        public void parserMoves(Game myGame) {
            try {
                out.println("joint a Game");
                out.flush();
                while(true) {
                    inComing = in.readLine();
                    while (inComing != null) {
                        List<String> words = Arrays.asList((inComing.split("\\s+")));
                        
                        out.flush();
                        inComing = in.readLine();
                    }
                    ServerThread.shutDown();
                }
            }catch (IOException e) {
                ServerThread.shutDown();
            }
        }

    }

}


