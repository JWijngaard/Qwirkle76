package com.company;

import com.company.Exceptions.DontHaveTileException;
import com.company.Exceptions.NotEnoughTilesInBagException;
import com.company.Exceptions.WrongCommandoException;
import com.company.Exceptions.WrongNameException;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ServerThread extends Thread {
    ServerSocket serverSocket;
    private ServerView serverVieuw = new ServerView();

    List<String> myList = new ArrayList<>();
    List<Socket> clients = new ArrayList<>();
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
                            while(true) {
                                if (inComing.startsWith("hello")) {
                                    if (words.get(1).equals("place") || words.get(1).equals("trade") || words.get(1).equals("join") || words.get(1).equals("players?")) {
                                        error("1", socket);
                                    } else if (!myList.contains(words.get(1))) {
                                        sendToTCP("hello_from_the_other_side", socket);
                                        String result = "joinlobby";
                                        for (Socket z : clients) {
                                            if (!(getPlayerName(z) == null)) {
                                                result += " " + clientIDPlayer.get(z);
                                            }
                                        }
                                        sendToTCP(result, socket);
                                        serverView.logMessage("Player: " + words.get(1) + " joined the server!");
                                        myList.add(words.get(1));
                                        clientIDPlayer.put(socket, words.get(1));
                                        playerClientID.put(words.get(1), socket);
                                    } else {
                                        error("0", socket);
                                    }
                                    out.flush();
                                    inComing = in.readLine();
                                }
                                break;
                            }
                            while(true) {
                                if (words.get(0).equals("players?")) {
                                    //TODO: Send to all players
                                }
                                else if (words.get(0).equals("join")) {
                                    if (words.get(1).equals("2")) {
                                        lobbyTwo.add(socket);
                                        sendToTCP("joint lobby 2 waiting for players", socket);
                                    } else if (words.get(1).equals("3")) {
                                        lobbyThree.add(socket);
                                        sendToTCP("joint lobby 3 waiting for players", socket);
                                    } else if (words.get(1).equals("4")) {
                                        lobbyFour.add(socket);
                                        sendToTCP("joint lobby 4 waiting for players", socket);
                                    } else {
                                        error("0", socket);
                                    }
                                }
                                else {
                                    error("0", socket);
                                    System.out.println("error 0");
                                }
                                
                                break;
                            }
                            while (true) {
                                if (socketGame.get(socket) != null) {
                                    System.out.println("Player " + clientIDPlayer.get(socket) + " joint a Game");
                                    parserMoves(socketGame.get(socket));
                                } else if (lobbyTwo.size() == 2 && lobbyTwo.contains(socket)) {
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
                                    for (Player player : myGame.getMyPlayers()) {
                                        newStones(player.getMyHand(), playerClientID.get(player.getName()));
                                    }
                                    myGame.setTurn(player1);
                                    System.out.println(myGame.getMyPlayers().get(0).getName());
                                    System.out.println(playerClientID.get(myGame.getMyPlayers().get(0).getName()));
                                    System.out.println(myGame.getMyPlayers().get(1).getName());
                                    System.out.println(playerClientID.get(myGame.getMyPlayers().get(1).getName()));
                                    nextTurn(myGame);
                                    socketGame.put(lobbyTwo.get(0), myGame);
                                    socketGame.put(lobbyTwo.get(1), myGame);
                                    lobbyTwo.remove(0);
                                    lobbyTwo.remove(0);
                                    while (true) {
                                        if (socketGame.get(socket) != null) {
                                            System.out.println("Player " + clientIDPlayer.get(socket) + " joint a Game");
                                            parserMoves(socketGame.get(socket));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (IOException e) {
                ServerThread.shutDown();
            }
        }

        public void nextTurn(Game myGame) {
            sendToAllTCP("turn " + myGame.nextTurn().getName(), myGame);
        }

        public void sendToAllTCP(String message, Game myGame) {
            for (Player p : myGame.getMyPlayers()) {
                String player = p.getName();
                Socket s = playerClientID.get(player);
                if(player != null) {
                    try {
                        PrintStream outToClient = new PrintStream(s.getOutputStream());
                        outToClient.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void sendToTCP(String message, Socket socket) {
            if(socket != null) {
                try {
                    PrintStream outToClient = new PrintStream(socket.getOutputStream());
                    outToClient.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void newStones(CopyOnWriteArrayList<Tile> newTiles, Socket socket) {
            String result = "newstones";
            for (Tile tile : newTiles) {
                result += " ";
                result += tile.getShape();
                result += ",";
                result += tile.getColor();
            }
            sendToTCP(result,socket);
        }

        public void error(String value, Socket socket) {
            String message = "error ";
            sendToTCP(message + value, socket);
        }

        public void parserMoves(Game myGame) {
            try {
                out.flush();
                while(true) {
                    inComing = in.readLine();
                    if(clientIDPlayer.get(socket).equals(myGame.getTurn().getName())) {
                        while (inComing != null) {
                            System.out.println("-1");
                            List<String> words = Arrays.asList((inComing.split("\\s+")));
                            if (words.get(0).equals("place")) {
                                String myName = clientIDPlayer.get(socket);
                                ArrayList<Player> myPlayerList = myGame.getMyPlayers();
                                Player me = new Player("Kees");
                                for (Player player : myPlayerList) {
                                    if (player.getName() == myName) {
                                        me = player;
                                    }
                                }
                                ArrayList<Move> moves = new ArrayList<>();
                                String result = "";
                                for (String string : words) {
                                    System.out.println(words.get(0));
                                    for (int i = 1; i < words.size(); i++) {
                                        result += words.get(i);
                                        result += ",";
                                    }

                                }
                                String[] strArray = result.split(",");
                                ArrayList<Integer> intArray = new ArrayList<>(strArray.length);
                                for(int i = 0; i < strArray.length; i++) {
                                    intArray.add(Integer.parseInt(strArray[i]));
                                }
                                int countPlaced = 0;
                                while (intArray.size() > 3) {
                                    Move move = new Move(intArray.get(0),intArray.get(1),intArray.get(3),intArray.get(2));
                                    moves.add(move);
                                    for (int i = 0; i < 4; i++) {
                                        intArray.remove(i);
                                    }
                                    countPlaced++;
                                }
                                int pointsGained = me.makeMoveGetPoints(moves);
                                if (pointsGained == -1) {
                                    error("1", socket);
                                    serverView.logMessage("Player tried to place a tile but failed miserably.");
                                } else {
                                    me.setScore((me.getScore() + pointsGained));
                                    words.set(0, "placed");
                                    String myResult = "";
                                    for (String word : words) {
                                        myResult += word;
                                        myResult += " ";
                                    }
                                    sendToAllTCP(myResult, myGame);
                                    serverView.logMessage("Player " + clientIDPlayer + " succesfully placed " + countPlaced + " tiles.");
                                    nextTurn(myGame);
                                }
                            } else if (words.get(0).equals("trade")) {
                                String myName = clientIDPlayer.get(socket);
                                ArrayList<Player> myPlayerList = myGame.getMyPlayers();
                                Player me = new Player("Kees");
                                for (Player player : myPlayerList) {
                                    if (player.getName() == myName) {
                                        me = player;
                                    }
                                }
                                ArrayList<Tile> tiles = new ArrayList<>();
                                String result = "";
                                for (String string : words) {
                                    if (string != "trade") {
                                        result += string;
                                    }
                                }
                                String[] strArray = result.split(",");
                                ArrayList<Integer> intArray = new ArrayList<>(strArray.length);
                                for(int i = 0; i < strArray.length; i++) {
                                    intArray.set(i, Integer.parseInt(strArray[i]));
                                }
                                int countTrades = 0;
                                while (intArray.size() > 1) {
                                    Tile tile = new Tile(-100,-100);
                                    tile.setShape(intArray.get(0));
                                    tile.setColor(intArray.get(1));
                                    tiles.add(tile);
                                    for (int i = 0; i < 2; i++) {
                                        intArray.remove(i);
                                    }
                                    countTrades++;
                                }
                                try {
                                    ArrayList<Tile> oldHand = new ArrayList<>();
                                    Collections.copy(oldHand, me.myHand);
                                    me.tradeTiles(tiles);
                                    CopyOnWriteArrayList<Tile> newTiles = new CopyOnWriteArrayList<>();
                                    for (Tile tile : me.myHand) {
                                        if (!(oldHand.contains(tile))) {
                                            newTiles.add(tile);
                                        }
                                    }
                                    newStones(newTiles, socket);
                                    words.set(0, "traded");
                                    String myResult = "";
                                    result += clientIDPlayer.get(socket);
                                    result += " ";
                                    result += countTrades;
                                    sendToAllTCP(myResult, myGame);
                                    serverView.logMessage("Player " + clientIDPlayer.get(socket) + " succesfully traded" +countTrades + " tiles.");
                                    nextTurn(myGame);
                                } catch (NotEnoughTilesInBagException e) {
                                    error("1", socket);
                                    serverView.logMessage(e.getMessage());
                                    serverView.logMessage("Player tried to trade a tile but failed miserably.");

                                } catch (DontHaveTileException e) {
                                    error("1", socket);
                                    serverView.logMessage(e.getMessage());
                                    serverView.logMessage("Player tried to trade a tile but failed miserably.");
                                }
                            }else {
                                error("0", socket);
                                serverView.logMessage("error 0");
                            }
                            out.flush();
                            break;
//                            inComing = in.readLine();
                        }
                    }else  {
                        System.out.println("error 1");
                        sendToTCP("1", socket);
                        out.flush();
                    }
                    ServerThread.shutDown();
                }
            }catch (IOException e) {
                ServerThread.shutDown();
            }
        }

    }

}


