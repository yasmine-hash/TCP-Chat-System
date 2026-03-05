package org.example;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class TCPClient {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void connect(String serverAddress, int port, String username) {

        try {

            socket = new Socket(serverAddress, port);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            // Receive messages thread
            new Thread(() -> {

                String response;

                try {

                    while ((response = in.readLine()) != null) {

                        String msg = response;

                        Platform.runLater(() ->
                                ChatClientUI.appendMessage(msg)
                        );

                    }

                } catch (IOException e) {

                    Platform.runLater(() ->
                            ChatClientUI.appendMessage("Disconnected from server.")
                    );

                }

            }).start();

            // send username to server
            out.println(username);

        } catch (IOException e) {

            ChatClientUI.appendMessage("Connection failed.");

        }
    }

    public void sendMessage(String message) {

        if (out != null) {
            out.println(message);
        }

    }

    public void disconnect() {

        try {

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}