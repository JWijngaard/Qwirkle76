package com.company;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    public static void main(String args[]) throws IOException{

        InetAddress address = InetAddress.getByName(args[0]);
        TUIView tuiView = new TUIView();
        ClientModel clientModel = new ClientModel();
        Socket socketOne = null;
        String line;
        BufferedReader out = null;
        BufferedReader in = null;
        PrintWriter outServer = null;
        String response;
        int portNumber;
        int port;
        String playername = null;

        try {
            while(true)
                try {
                    Scanner kb = new Scanner(System.in);
                    tuiView.logMessage("Please enter the portnumber: ");
                    port = Integer.parseInt(kb.nextLine());
                    tuiView.logMessageInt("The choosen portNumber is: ", port);
                    portNumber = port;
                    break;
                } catch (NumberFormatException nfe) {
                    tuiView.logMessage("The choosen portnumber is not valid");
                }
            socketOne = new Socket(address, portNumber);
            out = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socketOne.getInputStream()));
            outServer = new PrintWriter(socketOne.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }
        tuiView.logMessage("Client Address : " + address);
        try{
            line = out.readLine();
            List<String> clientName = Arrays.asList((line.split("\\s+")));
            if(clientName.get(0).equals("hello")) {
                playername = clientName.get(1);
            }
            while(line != null){
                try {
                    Thread.sleep(100);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                outServer.println(line);
                outServer.flush();
                response = in.readLine();
                System.out.println(response); //This has to go! after we finish
                // TODO:  Parser
                List<String> words = Arrays.asList((response.split("\\s+")));
                if (words.get(0).equals("error")) {
                    if(words.get(1).isEmpty()){
                        tuiView.logMessage(words.get(0));
                        //TODO: Do something
                    }else if(words.get(1).equals("0")){
                        tuiView.logMessage(words.get(0) + " " +  words.get(1));
                        //TODO: Do something
                    }
                    else if(words.get(1).equals("1")){
                        tuiView.logMessage(words.get(0) + " " + words.get(1));
                        //TODO: Do something

                    }else if(words.get(1).equals("2")){
                        tuiView.logMessage(words.get(0) + " " + words.get(1));
                        //TODO: Do something
                    }else if(words.get(1).equals("3")) {
                        tuiView.logMessage(words.get(0) + " " + words.get(1));
                        //TODO: Do something
                    }else if(words.get(1).equals("4")) {
                        tuiView.logMessage(words.get(0) + " " + words.get(1));
                        //TODO: Do something
                    }else {
                        //TODO: Do something
                    }

                }else if(words.get(0).equals("endgame")) {
                    //TODO: End game
                }else if (words.get(0).equals("turn")) {
                    if(words.get(1).equals(playername)){
                        System.out.println("Its your Turn please make a move!");
                        //TODO: Your turn
                    }
                    //TODO: give turn to client
                } else if (words.get(0).equals("")) {

                }
                outServer.flush();
                line = out.readLine();

                if(line.equals("Quit")){
                    break;
                }

            }
        }
        catch(IOException e){
            e.printStackTrace();
            tuiView.logMessage("Socket read Error");
        }
        finally{
            in.close();
            outServer.close();
            out.close();
            socketOne.close();
            tuiView.logMessage("Connection Closed");
        }

    }
}
