package com.CharmFlex;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static String SERVER_IP;
    private static int PORT;
    private static Socket socket;
    private static BufferedReader input;
    private static PrintWriter output;
    private static boolean isPlayer;

    public static void init() throws IOException {
        socket = new Socket(SERVER_IP, PORT);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        isPlayer = false;
        Timer timer = new Timer(1000,
                e -> infoTransfer(GameManager.selfName + "o0o v" + GameManager.opponentName + "o0o v" + GameManager.playerBlack));
        timer.setRepeats(false);
        timer.start();
        infoReceive();
    }

    public static void setServerIP(String ip) {
        Client.SERVER_IP = ip;
    }

    public static void setPort(String port) {
        Client.PORT = Integer.parseInt(port);
    }


    public static void infoTransfer(String info) {
        output.println(info);
    }

    public static void infoReceive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String info = input.readLine();
                        if (info.equals("Opponent join")) {
                            GameManager.opponentIsOnline(true);
                        }
                        else if (info.equals("Opponent left")) {
                            GameManager.gameWindow.disposeWindow();
                            GameManager.go2Menu();
                            closeSocket();
                            break;
                        }
                        else if (info.equals("You Lose")) {
                            GameManager.setGameEnd();
                            GameManager.popEndGameMsg(false);
                        }
                        else {
                            GameManager.moveInfoReceiver(info);
                        }
                    }
                    catch (IOException e) {
                        GameManager.gameWindow.disposeWindow();
                        break;
                    }
                }
            }
        }).start();
    }

    public static void closeSocket() {
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}


