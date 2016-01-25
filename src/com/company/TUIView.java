package com.company;

/**
 * Created by Jelle on 12/01/16.
 */
public class TUIView {
    public TUIView() {

    }
    public void logMessage(String message){
        System.out.println("Client: " + message);
    }

    public void logMessageInt(String message, int messageInt){
        System.out.println("Client: " + message + messageInt);
    }

    public void logInt(int messageInt){
        System.out.println("Client: " + messageInt);
    }
}
