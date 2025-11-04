package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

// we implement Runnable so that it can run on a separate thread.
// each ClientHandler instance handles one client's connection.
// 
public class ClientHandler implements Runnable {

    private Socket socket;
    // Server is the main server class
    private Server server;
    // used for communication with the client
    private UserService userService;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket, Server server, UserService userService) {
        this.socket = socket;
        this.server = server;
        this.userService = userService;
    }

    // Override
    public void run() {
        try {
            // initialize input and output streams
            // (true) means PrintWriter will auto-flush,
            // ensuring messages are sent immediately
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // We keep asking the client to login or register until successful
            if (!handleAuthentication()) {
                // if authentication fails (e.g., client disconnects)
                // end this thread
                return;
            }

            // --- Messaging Loop ---
            // Only reached here after successful authentication
            handleMessaging();

        } catch (SocketException e) {
            // SocketException is usually triggered
            // when the client suddenly closes the connection
            // unauthenticated user suddenly disconnected
            System.out.println(
                    "Client " + (username != null ? username : "unauthenticated user ") + " suddenly disconnected");
        } catch (IOException e) {
            System.err.println("ClientHandler wrong: " + e.getMessage());
        } finally {
            // Cleanup Phase
            // We clean up resources no matter what
            System.out.println("Cleaning up client: " + username);

            server.removeClient(this.username, this);
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // throws IOException
    // --- Authentication Loop ---
    // return: true if login successful
    private boolean handleAuthentication() throws IOException {
        out.println("Welcome to the JZ Chat Server!");
        out.println("register or login in:");
        out.println("SIGNUP <Username> <password>");
        out.println("LOGIN <Username> <password>");
        out.println("---------------------------");

        String line;
        while ((line = in.readLine()) != null) {
            // because the client may send empty lines
            // I meet many times so add this check
            if (line.trim().isEmpty()) {
                continue;
            }
            // SPLIT the command
            String[] parts = line.trim().split("\\s+", 3); // 分割命令，最多3部分
            if (parts.length < 3) {
                out.println("ERROR: wrong command format");
                continue;
            }

            String command = parts[0].toUpperCase();
            String user = parts[1];
            String pass = parts[2];

            switch (command) {
                case "SIGNUP":
                    if (userService.register(user, pass)) {
                        out.println("SIGNUP_SUCCESS: register successful.");
                    } else {
                        out.println("SIGNUP_FAILED: username already exists");
                    }
                    break;

                case "LOGIN":
                    if (userService.login(user, pass)) {
                        this.username = user;
                        out.println("LOGIN_SUCCESS: welcome, " + this.username + "!");
                        // Notify the server about this new logged-in client
                        server.registerClient(this.username, this);
                        return true;
                    } else {
                        out.println("LOGIN_FAILED: wrong username or password");
                    }
                    break;

                default:
                    out.println("ERROR: unknown command " + command);
            }
        }
        return false;
    }

    // --- Messaging Loop ---
    // throws IOException
    private void handleMessaging() throws IOException {
        out.println("You have joined the chat room");
        out.println("direct messages will be sent to all users.");
        out.println("use /msg <username> <message> to send private message.");
        out.println("---------------------------");

        String clientMessage;
        while ((clientMessage = in.readLine()) != null) {

            if (clientMessage.trim().toLowerCase().startsWith("/msg")) {
                // split into 3 parts: command, recipient, message
                String[] parts = clientMessage.trim().split("\\s+", 3);
                if (parts.length < 3) {
                    out.println("SERVER: the message format is wrong");
                    continue;
                }
                String recipient = parts[1];
                String message = parts[2];

                // send private message
                boolean sent = server.sendPrivateMessage(recipient, "(private chat) " + this.username + ": " + message);

                if (sent) {
                    // echo back to self
                    out.println("(message to " + recipient + "): " + message);
                } else {
                    out.println("SERVER: wrong username '" + recipient + "' or user not online.");
                }

            } else {
                // --- Handle broadcast message ---
                System.out.println(this.username + " (broadcast) message: " + clientMessage);
                server.broadcastMessage(this.username + ": " + clientMessage, this);
            }
        }
    }

    // Send a message to this specific client.
    // message: the message to send
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    // Get the username of this client
    public String getUsername() {
        return this.username;
    }
}
