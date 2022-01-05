package com.CharmFlex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Map<String, ClientHandler> clients;
    private String selfName;
    private String opponentName;
    BufferedReader input;
    PrintWriter output;
    public byte playerBlack;
    public boolean isPaired;
    private final byte waitingTime; // in seconds

    public ClientHandler (Socket clientSocket, Map<String, ClientHandler> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.isPaired = false;
        waitingTime = 60;
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private void removeFromMap(String selfName) {
        clients.remove(selfName);
    }

    private void disconnectServer() {
        output.println("Opponent left");
        System.out.println("\n" + selfName + " left the matching room!");
        removeFromMap(selfName);
        System.out.println("total player in server (waiting room + matching room) = " + clients.size());
    }

    @Override
    public void run() {
        String names;
        try {
            clientSocket.setSoTimeout(3000);
            try{
                names = input.readLine();
            }
            catch (SocketException e) {
                System.out.println("\nStranger joined and will make him out!");
                return;
            }
            if (!names.contains("o0o v")) {
                System.out.println("\nStranger joined and will make him out! (He typed something unrelated)");
                return;
            }
            selfName = names.split("o0o v")[0];
            opponentName = names.split("o0o v")[1];
            playerBlack = Byte.parseByte(names.split("o0o v")[2]);
            clientSocket.setSoTimeout(0);

            // check if double account
            if (clients.get(selfName) != null) {
                output.println("Opponent left");
                return;
            }

            // login success
            clients.put(selfName, this);
            System.out.println("\n" + selfName + " join!");
            System.out.println("total player in server (waiting room + matching room) = " + clients.size());

            // wait for other player to join
            long startTime = System.nanoTime();
            while (clients.get(opponentName) == null || !clients.get(opponentName).opponentName.equals(selfName)) {
               if (System.nanoTime() - startTime >= waitingTime*Math.pow(10, 9)) {
                   output.println("Opponent left");
                   System.out.println("\n" + selfName + " left the waiting room!");
                   removeFromMap(selfName);
                   System.out.println("total player in server (waiting room + matching room) = " + clients.size());
                   return;
                }
            }

            // check if same side
            if (playerBlack == clients.get(opponentName).playerBlack) {
                disconnectServer();
                return;
            }
            output.println("Opponent join");
            isPaired = true;
            System.out.println("------------------" +
                    selfName + " and " + opponentName + " has entered matching room!" +
                    "-------------------");
            while (true) {
                String info = input.readLine();
                if (info.startsWith("Pos: ")) {
                    clients.get(opponentName).output.println(info);
                }
                else if (info.equals("You Lose")) {
                    clients.get(opponentName).output.println(info);
                }
            }
        } catch (IOException | NullPointerException n) {
            if (clients.get(opponentName) != null) {
                clients.get(opponentName).output.println("Opponent left");
            }
        }
        disconnectServer();
    }
}
