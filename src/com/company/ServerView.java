package com.company;

/**
 * Created by Jelle on 12/01/16.
 */
public class ServerView {

    public ServerView() {

    }

    public void logMessage(String message){
        System.out.println("Server: " + message);
    }

    public void logMessageInt(String message, int messageInt){
        System.out.println("Server: " + message + messageInt);
    }

    public void logInt(int messageInt){
        System.out.println("Server: " + messageInt);
    }
}
