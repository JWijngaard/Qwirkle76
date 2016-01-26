package com.company;

import com.company.Exceptions.WrongCommandoException;
import com.company.Exceptions.WrongNameException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ServerController extends Thread{

    private ServerModel serverModel = new ServerModel();
    private ServerView serverVieuw = new ServerView();
    List<Socket> lobbyTwo = serverModel.getLobbyClientIDTwo();
    List<Socket> lobbyThree = serverModel.getLobbyClientIDThree();
    List<Socket> lobbyFour = serverModel.getLobbyClientIDFour();
    List<String> playerNameList = serverModel.myList;
    List<Socket> clients = serverModel.getClientList();
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
                System.out.println(inComing);
                while (inComing != null) {
                    List<String> words = Arrays.asList((inComing.split("\\s+")));
                    if (inComing.startsWith("hello")) {
                        if (words.get(1).equals("Place") || words.get(1).equals("trade") || words.get(1).equals("join") || words.get(1).equals("players?")) {
                            out.println("error 2");
                            throw new WrongNameException("error 2");
                        } else if (!playerNameList.contains(words.get(1))) {
                            out.println("hello_from_the_other_side ");
                            String result = "joinlobby";
                            for (Socket z : clients) {
                                if (!(serverModel.getPlayerName(z) == null)) {
                                    result += " " + serverModel.getPlayerName(z);
                                }
                            }
                            out.println(result);
                            serverVieuw.logMessage("Player: " + words.get(1) + " joined the server!");
                            playerNameList.add(words.get(1));
                        } else {
                            System.out.println("-4");
                            out.println("error 0");
                            throw new WrongCommandoException("error 0");
                        }
                    } else if (words.get(0).equals("players?")) {
                        //TODO: Send to all players
                    } else if (words.get(0).equals("join")) {
                        if (words.get(1).equals("2")) {
                            lobbyTwo.add(socket);
                            if (lobbyTwo.size() != 2) {
                                out.println("joint lobby 2 waiting for players");
                            }
                        } else if (words.get(1).equals("3")) {
                            lobbyThree.add(socket);
                            if (lobbyTwo.size() != 3) {
                                out.println("joint lobby 3 waiting for players");
                            }
                        } else if (words.get(1).equals("4")) {
                            lobbyFour.add(socket);
                            if (lobbyTwo.size() != 4) {
                                out.println("joint lobby 4 waiting for players");
                            }
                        } else {
                            out.println("error 0");
                            throw new WrongCommandoException("error 0");
                        }
                    } else {
                        out.println("error 0");
                        System.out.println("error 0");
                    }
                    out.flush();
                    inComing = in.readLine();
                }
                ServerThread.shutDown();
            }
        }catch (IOException e) {
            ServerThread.shutDown();
        }
    }

    public void parserMoves() {

    }

}
