package com.company;

import java.util.Scanner;

/**
 * Created by Jelle on 12/01/16.
 */
public class ServerView {

    public ServerView() {

    }

    public void logMessage(String message){
        System.out.println("Server: '" + message + "'");
    }

    public String askUserInput(String message) {
        System.out.println(message);
        Scanner userInputScanner = new Scanner(System.in);
        String myInput = null;
        while (myInput == null) {
            if (userInputScanner.hasNext()) {
                myInput = userInputScanner.nextLine();
            }
        }
        return myInput;
    }
}
