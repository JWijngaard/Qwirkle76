package com.company;

public class Main {

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println(args[0]);
            if (args[0].equals("-S") || args[0].equals("-s")) {
                System.out.println("Now starting Qwirkle76 game server!");
                ServerController myServerController = new ServerController();
                myServerController.start();
            } else if (args[0] == "-G" || args[0] == "-g") {
                System.out.println("Now starting Qwirkle76 game client in GUI mode!");
            } else if (args[0] == "-T" || args[0] == "-t") {
                System.out.println("Now starting Qwirkle76 game client in TUI mode!");
            } else {
                System.out.println("If you want to use our Qwirkle76 package, you should start it by typing" +
                        " 'java Qwirkle76 -[option]'. Replace [option] with either -G, -T or -S, depending on" +
                        " whether you want to launch our GUI version of Qwirkle, our TUI version of Qwirkle" +
                        " or our Qwirkle server (using the Qwirkle group 5 protocol) respectively.");
            }
        } else {
            System.out.println("If you want to use our Qwirkle76 package, you should start it by typing" +
                    " 'java Qwirkle76 -[option]'. Replace [option] with either -G, -T or -S, depending on" +
                    " whether you want to launch our GUI version of Qwirkle, our TUI version of Qwirkle" +
                    " or our Qwirkle server (using the Qwirkle group 5 protocol) respectively.");
        }
    }
}
