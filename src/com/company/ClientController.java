package com.company;

import java.io.*;
import java.net.*;

public class ClientController {
    public static void main(String args[]) throws IOException{


        InetAddress address = InetAddress.getLocalHost();
        Socket socketOne = null;
        String line;
        BufferedReader out = null;
        BufferedReader in = null;
        PrintWriter outServer = null;

        try {
            socketOne = new Socket(address, 4000);
            out = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socketOne.getInputStream()));
            outServer = new PrintWriter(socketOne.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : " + address);
        String response = null;
        try{
            line = out.readLine();
            while(line != null){
                outServer.println(line);
                // TODO:  Parser
                outServer.flush();
                response = in.readLine();
                System.out.println(response);
                line = out.readLine();

            }
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{
            in.close();
            outServer.close();
            out.close();
            socketOne.close();
            System.out.println("Connection Closed");

        }

    }
    }
