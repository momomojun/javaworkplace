package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    // define a constant for the server port
    private static final int PORT = 8080;
    // store a Map of all client handlers.
    // We use a thread-safe List because it will be modified from multiple threads.
    private Map<String, ClientHandler> loggedInClients = new ConcurrentHashMap<>();

    private UserService userService;
    public Server() {
        this.userService = new UserService(); // 初始化 UserService
    }

    public static void main(String[] args) {
        new Server().startServer();
    }

    public void startServer() {
        System.out.println("Chat Server begins working for the server port" + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // we use infinite loop to continuously accept new connections  
            while (true) {
                try {
                    // use serverSocket.accept() to block until a client connects
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("new Client has connected: " + clientSocket.getRemoteSocketAddress());

                    // create a dedicated handler for this new client
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this, userService);
                    
                    // start the handler with a new thread
                    // the main server thread will come back to accept another new connection
                    new Thread(clientHandler).start();

                } catch (IOException e) {
                    System.err.println("connection to client has something wrong: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("we cannot start the server in Port: " + PORT + ": " + e.getMessage());
        }
    }

    // Broadcast a message to all other connected clients.
    // message: the message content
    // sender: the client who sent this message (we don't want to send it back to him)
    public void broadcastMessage(String message, ClientHandler sender) {
        // We synchronize on the list while iterating to prevent other threads from modifying it at the same time
        for (ClientHandler client : loggedInClients.values()) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    // private message to a specific user
    // recipientUsername: the username of the recipient
    // message: the message content
    // return: true if sent successfully
    public boolean sendPrivateMessage(String recipientUsername, String message) {
        ClientHandler recipient = loggedInClients.get(recipientUsername);
        if (recipient != null) {
            recipient.sendMessage(message);
            return true;
        } else {
            return false;
        }
    }

    // username: the username of the logged-in client
    // client: the client handler
    // when a client successfully logs in, register it to the map
    public synchronized void registerClient(String username, ClientHandler client) {
        loggedInClients.put(username, client);
        System.out.println("User: " + username + " has logged in.");
        broadcastMessage(username + " joined the chat room", client);
    }
    // When a client disconnects, remove it from the list.
    // client: the client handler to remove
    public void removeClient(String username, ClientHandler client) {
        if (username != null) {
            loggedInClients.remove(username);
            System.out.println("Client: " + username + " stopped connection.");
            broadcastMessage(username + " leave the chat group.", client);
        }
    }
}
