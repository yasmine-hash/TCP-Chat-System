# TCP Chat System

## Overview

This project implements a **TCP-based chat application** using **Java, JavaFX, and socket programming**.  
The system follows a **client–server architecture** where multiple clients can connect to a central server and communicate through broadcast messages.

The server manages client connections, maintains a list of active users, and broadcasts messages to all connected clients. The client application provides a graphical interface for users to connect and participate in the chat.

---

# System Architecture

The application is composed of two main components:

## TCPServer
The server manages incoming client connections and message broadcasting.

Key responsibilities:
- Accept multiple client connections
- Manage active users
- Broadcast messages to all clients
- Log server events
- Display connected users through a monitoring interface

The server listens for connections using a `ServerSocket` and creates a new `ClientHandler` thread for each connected client. :contentReference[oaicite:0]{index=0}

---

## TCPClient
The client application allows users to connect to the server and send messages.

Key features:
- Graphical user interface built with JavaFX
- Username-based chat participation
- Real-time message reception
- Sending messages to all connected users

The client maintains a socket connection with the server and continuously listens for incoming messages using a background thread. :contentReference[oaicite:1]{index=1}

---

# Project Structure
TCP-Chat-System
│
├── TCPServer
│ ├── pom.xml
│ └── src/main/java/org/example
│ ├── ServerUI.java
│ ├── TCPServer.java
│ └── ClientHandler.java
│
├── TCPClient
│ ├── pom.xml
│ └── src/main/java/org/example
│ ├── ChatClientUI.java
│ └── TCPClient.java
│
└── README.md


---

# Server Components

## ServerUI
A JavaFX graphical interface that allows monitoring and controlling the server.

Features:
- Start/Stop server button
- Display server logs
- Display list of connected users

The UI updates logs and user lists using JavaFX's `Platform.runLater()` to ensure thread-safe updates. :contentReference[oaicite:2]{index=2}

---

## TCPServer
The main server class responsible for:

- Starting the server
- Accepting client connections
- Managing connected users
- Broadcasting messages

Connected clients are stored in a `ConcurrentHashMap` to ensure thread-safe access. :contentReference[oaicite:3]{index=3}

---

## ClientHandler
Each connected client is handled by a dedicated thread.

Responsibilities:
- Receive messages from a client
- Broadcast messages to other clients
- Handle client disconnection
- Manage commands such as:
  - `bye` / `end` → disconnect
  - `allUsers` → list connected users

Messages are timestamped before being broadcast to all users. :contentReference[oaicite:4]{index=4}

---

# Client Components

## ChatClientUI
A JavaFX graphical interface allowing users to:

- Enter a username
- Connect to the server
- Send messages
- View chat history

Messages are displayed inside a `TextArea` component. :contentReference[oaicite:5]{index=5}

---

## TCPClient
Handles the network communication with the server.

Responsibilities:
- Establish TCP connection
- Send messages to the server
- Listen for incoming messages
- Update the UI asynchronously

Incoming messages are processed in a background thread to keep the GUI responsive. :contentReference[oaicite:6]{index=6}

---

# Technologies Used

- Java
- JavaFX
- TCP Socket Programming
- Multithreading
- Maven
- ConcurrentHashMap

---

# How to Run the Application

## 1 Start the Server

Run:
ServerUI.java


Steps:
1. Launch the application.
2. Click **Start Server**.
3. The server begins listening for incoming connections.

---

## 2 Start the Client

Run:
ChatClientUI.java


Steps:
1. Enter a username.
2. Click **Connect**.
3. Start sending messages.

Multiple client instances can connect simultaneously.

---

# Supported Commands

| Command | Description |
|------|------|
| `bye` or `end` | Disconnect from the chat |
| `allUsers` | Display list of connected users |

---

# Example Message Format
Yasmine [14:35]: Hello everyone!

---

# UML Diagrams

The project includes the following diagrams:

- Class Diagram
- Deployment Diagram

These diagrams illustrate the software architecture and communication between clients and the server.

---

# Demo Video

A short demo video demonstrates:

- Source code walkthrough
- Server startup
- Multiple clients connecting
- Real-time message broadcasting

---
