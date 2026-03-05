package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChatClientUI extends Application {

    private static TextArea chatArea;

    private TCPClient client = new TCPClient();

    public static void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    @Override
    public void start(Stage stage) {

        Label title = new Label("TCP Chat Client");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        Button connectButton = new Button("Connect");

        chatArea = new TextArea();
        chatArea.setEditable(false);

        TextField messageField = new TextField();
        messageField.setPromptText("Type message...");

        Button sendButton = new Button("Send");

        HBox connectBox = new HBox(10, usernameField, connectButton);

        HBox messageBox = new HBox(10, messageField, sendButton);

        VBox root = new VBox(10, title, connectBox, chatArea, messageBox);

        root.setPadding(new Insets(10));

        connectButton.setOnAction(e -> {

            String username = usernameField.getText();

            client.connect("localhost", 6000, username);

            appendMessage("Connected as " + username);

        });

        sendButton.setOnAction(e -> {

            String message = messageField.getText();

            if (!message.isEmpty()) {

                client.sendMessage(message);

                messageField.clear();

            }

        });

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("Chat Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}