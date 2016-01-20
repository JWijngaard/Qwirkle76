package com.company;

import java.util.Scanner;

/**
 * Created by Jelle on 12/01/16.
 */
public class ServerView {
    private ServerController myController;
    private Scanner myScanner = new Scanner(System.in);
    public ServerView(ServerController serverController) {
        myController = serverController;
    }

    public int askPort() {
        System.out.println("test");
        while (!myScanner.hasNextInt()) {
            System.out.println("Please enter the desired port number for the server to run on.");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        return 1;
    }

}
