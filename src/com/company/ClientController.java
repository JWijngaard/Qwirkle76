package com.company;

import com.company.Exceptions.WrongCommandoException;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class ClientController {

    TUIView clientView = new TUIView();
    protected int port;
    protected String IPAdress;
    protected Socket socket = null;
    protected BufferedReader inSer;
    protected BufferedReader inOwn;
    protected PrintWriter outSer;

    public ClientController() throws IOException, WrongCommandoException {
        port = askUserInputPort();
        IPAdress = askUserInputAddress();
        try {
            socket = new Socket(IPAdress, port);
        } catch (IOException e) {
            System.out.println("ERROR: could not create a socket on " + IPAdress + " and port " + port);
        }
        run();
    }

    public int askUserInputPort() {
        clientView.logMessage("Please enter the portnumber:");
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
        clientView.logMessageInt("The choosen portNumber is: ", port);
        return port;
    }

    public String askUserInputAddress() {
        clientView.logMessage("Please enter the IP-Adress:");
        Scanner userInputScanner = new Scanner(System.in);
        String message = "";
        while (message.equals("")) {
            if (userInputScanner.hasNext()) {
                try {
                    message = userInputScanner.nextLine();
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: IP-Adress " + userInputScanner + " is not vallid");
                    System.exit(0);
                }
            }
        }
        clientView.logMessage("The choosen IP-Adress is: " + message);
        return message;
    }

    public void run() throws IOException, WrongCommandoException {
        inOwn = new BufferedReader(new InputStreamReader(System.in));
        inSer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSer = new PrintWriter(socket.getOutputStream());
        try {
            String messageOut = "hello julian";
            outSer.println(messageOut);
            outSer.flush();
        }catch (Exception e) {
            throw new WrongCommandoException("error 2");
        }
        try {
            System.out.println(inSer.readLine());
            String message = inSer.readLine();
            while (message != null) {
                System.out.println(message);
                String ownMessage = inOwn.readLine();
                outSer.println(ownMessage);
                outSer.flush();
                message = inSer.readLine();
            }
            ServerThread.shutDown();
        } catch (IOException e) {
            e.printStackTrace();
            ServerThread.shutDown();
        }
    }

    public static void main(String[] args) throws IOException, WrongCommandoException {
        ClientController c = new ClientController();
    }
}
