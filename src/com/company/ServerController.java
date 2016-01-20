package com.company;

/**
 * Created by Jelle on 12/01/16.
 */
public class ServerController {
    private static ServerView myView;
    private ServerModel myModel;

    public ServerController() {
        myView = new ServerView(this);
        myModel = new ServerModel();
    }

    public void start() {
        myView.askPort();
    }

}
