package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class TCPServer {

    public static Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    private static ServerSocket serverSocket;
    private static boolean running = false;

    public static void startServer() {

        if (running) {
            ServerUI.log("Server already running.");
            return;
        }

        try {

            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/config.properties"));

            int port = Integer.parseInt(props.getProperty("server.port"));

            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));

            running = true;

            ServerUI.log("Server started on port " + port);
            ServerUI.log("Waiting for clients...");

            while (running) {

                Socket clientSocket = serverSocket.accept();

                ServerUI.log("New client connection from "
                        + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }

        } catch (IOException e) {

            ServerUI.log("Server error: " + e.getMessage());

        }
    }

    // ⭐ NEW METHOD
    public static void stopServer() {

        running = false;

        try {

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                ServerUI.log("Server stopped.");
            }

        } catch (IOException e) {
            ServerUI.log("Error stopping server: " + e.getMessage());
        }
    }

    public static void broadcast(String message) {

        for (ClientHandler client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public static String getUsers() {
        return String.join(", ", clients.keySet());
    }

    public static void addUser(String username, ClientHandler handler) {

        clients.put(username, handler);
        ServerUI.addUser(username);
    }

    public static void removeUser(String username) {

        clients.remove(username);
        ServerUI.removeUser(username);
    }
}