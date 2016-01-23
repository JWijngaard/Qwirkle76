package com.company;

import java.io.*;
import java.net.*;

public class ClientController {
    public static void main(String args[]) throws IOException{


        InetAddress address=InetAddress.getLocalHost();
        Socket socketOne = null;
        String line = null;
        BufferedReader out = null;
        BufferedReader in = null;
        PrintWriter os = null;

        try {
            socketOne = new Socket(address, 8901); // You can use static final constant PORT_NUM
            out = new BufferedReader(new InputStreamReader(System.in));
            in =new BufferedReader(new InputStreamReader(socketOne.getInputStream()));
            os = new PrintWriter(socketOne.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        System.out.println("Client Address : "+address);
        System.out.println("Enter Data to echo Server ( Enter QUIT to end):");

        String response = null;
        try{
            line = out.readLine();
            while(line != null){
                os.println(line);
                os.flush();
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
            os.close();
            out.close();
            socketOne.close();
            System.out.println("Connection Closed");

        }

    }
    }
