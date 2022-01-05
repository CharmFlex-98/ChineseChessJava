package com.CharmFlex;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 7777;
    private static final Map<String, ClientHandler> clients = new HashMap<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket, clients);
            pool.execute(client);
        }
    }
}
