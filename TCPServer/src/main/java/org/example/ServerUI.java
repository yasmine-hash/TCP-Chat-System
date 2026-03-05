package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ServerUI extends Application {

    public static TextArea logArea;
    public static ListView<String> usersList;

    private boolean serverRunning = false;

    @Override
    public void start(Stage stage) {

        Label title = new Label("TCP Chat Server Monitor");

        Button serverButton = new Button("Start Server");

        usersList = new ListView<>();

        logArea = new TextArea();
        logArea.setEditable(false);

        Label usersLabel = new Label("Connected Users");
        Label logsLabel = new Label("Server Logs");

        VBox usersBox = new VBox(5, usersLabel, usersList);
        VBox logsBox = new VBox(5, logsLabel, logArea);

        HBox center = new HBox(10, usersBox, logsBox);

        VBox root = new VBox(10, title, serverButton, center);
        root.setPadding(new Insets(10));

        serverButton.setOnAction(e -> {

            if (!serverRunning) {

                serverRunning = true;
                serverButton.setText("Stop Server");

                log("Server starting...");

                new Thread(TCPServer::startServer).start();

            } else {

                serverRunning = false;
                serverButton.setText("Start Server");

                TCPServer.stopServer();

            }
        });

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Server Monitor");
        stage.setScene(scene);
        stage.show();
    }

    public static void log(String message) {

        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
        });

    }

    public static void addUser(String username) {

        Platform.runLater(() -> {
            usersList.getItems().add(username);
        });

    }

    public static void removeUser(String username) {

        Platform.runLater(() -> {
            usersList.getItems().remove(username);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}