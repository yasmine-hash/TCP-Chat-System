package org.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean readOnly = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter username:");

            username = in.readLine();

            if (username == null || username.trim().isEmpty()) {

                readOnly = true;
                username = "READ_ONLY_" + socket.getPort();
                out.println("You are in READ ONLY MODE.");

            } else {

                ServerUI.log(username + " connected.");
            }

            TCPServer.addUser(username, this);

            TCPServer.broadcast(username + " joined the chat.");

            String message;

            while ((message = in.readLine()) != null) {

                if (message.equalsIgnoreCase("bye") || message.equalsIgnoreCase("end")) {

                    TCPServer.broadcast(username + " left the chat.");
                    ServerUI.log(username + " disconnected.");

                    TCPServer.removeUser(username);
                    socket.close();
                    return;
                }

                if (message.equalsIgnoreCase("allUsers")) {

                    out.println("Active users: " + TCPServer.getUsers());
                    continue;
                }

                if (readOnly) {

                    out.println("READ ONLY MODE - cannot send messages.");
                    continue;
                }

                String time = LocalTime.now()
                        .format(DateTimeFormatter.ofPattern("HH:mm"));

                String formatted = username + " [" + time + "]: " + message;

                ServerUI.log(formatted);

                TCPServer.broadcast(formatted);
            }

        } catch (IOException e) {

            ServerUI.log("Connection lost with " + username);

        } finally {

            try {

                TCPServer.removeUser(username);

                if (!socket.isClosed()) {
                    socket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}